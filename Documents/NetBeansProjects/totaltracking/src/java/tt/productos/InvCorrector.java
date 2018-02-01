package tt.productos;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.CatTransacciones;
import tt.general.CatTransaccionesDetalle;
import tt.general.ListaCortaTransaccionExistencias;
import tt.general.Login;

@Named
@ConversationScoped
public class InvCorrector implements Serializable {

    private static final long serialVersionUID = 8411654786768318L;
    @Inject
    Login cbean;

    private List<CatListados> paises;

    private List<CatTransacciones> encabezado;
    private List<CatTransaccionesDetalle> detalles;
    private List<ListaCortaTransaccionExistencias> hisexi;

    private String cod_pai, cod_tra, fec_tra, flg_ing_sal, statusaccion, tra_anu, tipoaccion, fechabuscar, lock;
    private int totalregistros, registroactual;
    private Date mfecha;

    private Accesos macc;

    public InvCorrector() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_pai = cbean.getCod_pai();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            macc.Conectar();
            mfecha = format.parse(macc.strQuerySQLvariable("select date_format(min(fec_tra),'%d/%m/%Y') as fectra from inv_tbl_transacciones where cod_pai = " + cod_pai + ";"));
            macc.Desconectar();
        } catch (Exception e) {
            mfecha = Date.from(Instant.now());
        }
        fechabuscar = format.format(mfecha);

        lock = "false";

        flg_ing_sal = "";
        fec_tra = "";
        tra_anu = "0";
        tipoaccion = "";
        totalregistros = 0;
        registroactual = 0;

        paises = new ArrayList<>();
        encabezado = new ArrayList<>();
        detalles = new ArrayList<>();
        hisexi = new ArrayList<>();

        llenarPaises();

        statusaccion = "Proceso Inicial realizado satisfactoriamente";
        RequestContext.getCurrentInstance().update("frmCorrectorSaldos");

    }

    public void cerrarventana() {
        statusaccion = null;
        cod_pai = null;
        flg_ing_sal = null;
        fec_tra = null;
        tra_anu = null;
        mfecha = null;
        fechabuscar = null;
        lock = null;
        tipoaccion = null;
        totalregistros = 0;
        registroactual = 0;
        encabezado = null;
        detalles = null;
        hisexi = null;
        paises = null;

        macc = null;
    }

    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from inv_cat_pai order by nom_pai;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Países en InvCorrector. " + e.getMessage());
        }
    }

    public void cargarsolicitudes() {
        String mQuery = "";
        ResultSet resVariable;

        try {
            encabezado.clear();
            mQuery = "select   "
                    + "enc.cod_tra, "
                    + "enc.cod_pai, "
                    + "date_format(enc.fec_tra,'%d/%m/%Y') as fectra, "
                    + "enc.flg_ing_sal, "
                    + "ifnull(enc.tra_anu ,0) as traanu "
                    + "FROM inv_tbl_transacciones as enc "
                    + "left join inv_cat_pai as pai on enc.cod_pai = pai.cod_pai  "
                    + "left join inv_cat_mov as mov on enc.tip_mov = mov.id_mov  "
                    + "where enc.cod_pai = " + cod_pai + " "
                    + "and enc.fec_tra >= str_to_date('" + fechabuscar + "','%d/%m/%Y') "
                    + "order by enc.fec_tra asc, enc.flg_anu asc, enc.cod_tra asc;";

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezado.add(new CatTransacciones(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "",
                        "", "", resVariable.getString(5), ""
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en Corregir Encabezado. " + e.getMessage() + " Query: " + mQuery);
        }

        statusaccion = "Carga de Solicitudes realizado con éxito";
        totalregistros = encabezado.size();
        registroactual = 0;
        RequestContext.getCurrentInstance().update("frmCorrectorSaldos");
    }

    public void corregir() {

        if (!encabezado.isEmpty()) {

            String mQuery = "";
            ResultSet resVariable;
            lock = "true";
            statusaccion = "Inicia proceso Recálculo de Saldos";
            RequestContext.getCurrentInstance().update("frmCorrectorSaldos");

            mQuery = "delete "
                    + "FROM inv_tbl_historico "
                    + "where cod_pai = " + cod_pai + " "
                    + "and fec_tra >= str_to_date('" + fechabuscar + "','%d/%m/%Y') ;";

            macc.Conectar();
            macc.dmlSQLvariable(mQuery);
            macc.Desconectar();

            detalles.clear();
            hisexi.clear();

            for (int i = 0; i < encabezado.size(); i++) {

                cod_tra = encabezado.get(i).getCod_tra();
                fec_tra = encabezado.get(i).getFec_tra();
                flg_ing_sal = encabezado.get(i).getFlg_ing_sal();
                tra_anu = encabezado.get(i).getTra_anu();

                statusaccion = "Recalculando Saldo de solicitud: " + cod_tra + " de Fecha: " + fec_tra;
                registroactual = i + 1;
                RequestContext.getCurrentInstance().update("frmCorrectorSaldos");

                try {
                    detalles.clear();
                    mQuery = "select cod_tra, cod_det, cod_bod, cod_ubi, cod_art, "
                            + "det_lot, date_format(fec_ven,'%d/%m/%Y') as fecven, det_can, '','','', det_can_con, uni_med_con, det_cos, '','','0' "
                            + "FROM inv_tbl_transacciones_det "
                            + "where cod_tra = " + cod_tra + " "
                            + "order by cod_det;";

                    macc.Conectar();
                    resVariable = macc.querySQLvariable(mQuery);
                    while (resVariable.next()) {
                        detalles.add(new CatTransaccionesDetalle(
                                cod_tra,
                                resVariable.getString(2),
                                resVariable.getString(3),
                                resVariable.getString(4),
                                resVariable.getString(5),
                                resVariable.getString(6),
                                resVariable.getString(7),
                                resVariable.getString(8),
                                resVariable.getString(9),
                                resVariable.getString(10),
                                resVariable.getString(11),
                                resVariable.getString(12),
                                resVariable.getString(13),
                                resVariable.getString(14),
                                resVariable.getString(15),
                                resVariable.getString(16),
                                resVariable.getString(17)
                        ));
                    }
                    resVariable.close();
                    macc.Desconectar();

                } catch (Exception e) {
                    System.out.println("Error en Corregir Detalle. " + e.getMessage() + " Query: " + mQuery);
                    addMessage("Recalcular Saldos de Productos", "Error en Corregir Detalle. " + e.getMessage(), 2);
                }

                guardarmovimiento();

                statusaccion = "Finalizando recálculo de saldo de solicitud: " + cod_tra;
                RequestContext.getCurrentInstance().update("frmCorrectorSaldos");

            }

            lock = "false";

            statusaccion = "Proceso finalizado";
            RequestContext.getCurrentInstance().update("frmCorrectorSaldos");
        } else {
            statusaccion = "Debe cargar datos para procesar";
            addMessage("Recalcular Saldos de Productos", "No existen registros para este criterio de búsqueda", 2);
        }
    }

    public boolean guardarmovimientoXX() {
        String mQuery = "";
        String mValores = "", mhistoria = "", embalaje;
        ResultSet resVariable;
        try {
            macc.Conectar();

            int mCorrela = 1, ord_dia = 0;
            int id_tra = 0;
            Double cos_uni, cos_pro, exi_ant, exi_act, exi_act_lot, exi_ant_tot, exi_act_tot;
            for (int i = 0; i < detalles.size(); i++) {
                cos_uni = 0.0;
                cos_pro = 0.0;
                exi_ant = 0.0;
                exi_act = 0.0;
                exi_act_lot = 0.0;
                exi_ant_tot = 0.0;
                exi_act_tot = 0.0;

                if (tra_anu.equals("0")) {
                    tipoaccion = "insert";
                } else {
                    tipoaccion = "cancel";
                }

                //*************** Tratamiento Histórico de movimientos  *********************************************************************** 
                //****************************************************************************************************************************
                //*********************** CORRELATIVO HISTORIA *******************************************************************************
                //****************************************************************************************************************************
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_historico;";
                id_tra = Integer.valueOf(macc.strQuerySQLvariable(mQuery));

                //****************************************************************************************************************************
                //*********************** ORDEN DIARIO ***************************************************************************************
                //****************************************************************************************************************************
                if (tipoaccion.equals("insert")) {
                    mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + ";";
                    ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                }

                if (tipoaccion.equals("cancel")) {

                    mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                            + "AND cod_mov in (" + tra_anu + "," + cod_tra + ") "
                            + ";";
                    ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                    mQuery = "update inv_tbl_historico set ord_dia = ord_dia + 1 "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                            + "AND ord_dia >= " + ord_dia + " "
                            + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                //****************************************************************************************************************************
                //*********************** COSTO UNITARIO *************************************************************************************
                //****************************************************************************************************************************
                cos_uni = Double.valueOf(detalles.get(i).getDet_cos()) / Double.valueOf(detalles.get(i).getDet_can());

                //****************************************************************************************************************************
                //*********************** COSTO PROMEDIO Y EXISTENCIA ANTERIOR ***************************************************************
                //****************************************************************************************************************************
                if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1
                        && macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y')"
                                + "and flg_ing_sal = 'false' "
                                + ";") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else {
                    //************* Costo Promedio y Existencia anteriores  **************
                    if ("false".equals(flg_ing_sal) && "insert".equals(tipoaccion)) {
                        cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_tot * cos_pro),0) + "
                                + (Double.valueOf(detalles.get(i).getDet_can()) * cos_uni) + ")"
                                + "/(IFNULL(exi_act_tot,0)+" + detalles.get(i).getDet_can() + ") as Cpromedio "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    }
                    if ("false".equals(flg_ing_sal) && "cancel".equals(tipoaccion)) {
                        if (ord_dia == 1) {
                            cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        } else {
                            cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        }

                    }
                    if ("true".equals(flg_ing_sal) && "insert".equals(tipoaccion)) {
                        cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    }
                    if ("true".equals(flg_ing_sal) && "cancel".equals(tipoaccion)) {
                        if (ord_dia == 1) {
                            cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        } else {
                            cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        }
                    }

                    if ("cancel".equals(tipoaccion)) {
                        if (ord_dia == 1) {
                            //***********  Existencia Anterior Bodega ********************************************
                            exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Lote **********************************************
                            exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                                    + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Total ********************************************
                            exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        } else {
                            //***********  Existencia Anterior Bodega ********************************************
                            exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Lote **********************************************
                            exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                                    + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Total ********************************************
                            exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        }
                    } else {
                        //***********  Existencia Anterior Bodega ********************************************
                        exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //********** Existencia Anterior Lote **********************************************
                        exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //********** Existencia Anterior Total ********************************************
                        exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }

                }

                //******************* Tratamiento de Existencias **************************************
                if ("false".equals(flg_ing_sal)) {
                    exi_act = exi_ant + Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_lot = exi_act_lot + Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_tot = exi_ant_tot + Double.valueOf(detalles.get(i).getDet_can());
                } else {
                    exi_act = exi_ant - Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_lot = exi_act_lot - Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_tot = exi_ant_tot - Double.valueOf(detalles.get(i).getDet_can());
                    //*********** Valida que las Existencias no sean menor que Cero ******************
                    if (exi_act < 0.0) {
                        exi_act = 0.0;
                    }
                    if (exi_act_lot < 0.0) {
                        exi_act_lot = 0.0;
                    }
                    if (exi_act_tot < 0.0) {
                        exi_act_tot = 0.0;
                    }
                }

                mhistoria = "(" + id_tra + ",'" + flg_ing_sal + "',str_to_date('" + fec_tra + "','%d/%m/%Y'),"
                        + ord_dia + "," + cod_tra + "," + mCorrela
                        + "," + cod_pai + "," + detalles.get(i).getCod_bod() + ","
                        + detalles.get(i).getCod_ubi() + "," + detalles.get(i).getCod_art() + ","
                        + detalles.get(i).getDet_can() + "," + exi_ant + "," + exi_act + "," + cos_uni + ","
                        + cos_pro + ",'" + detalles.get(i).getDet_lot().trim() + "'," + exi_act_lot + "," + exi_ant_tot + "," + exi_act_tot + ")";
                mQuery = "insert into inv_tbl_historico (cod_tra,flg_ing_sal,fec_tra,ord_dia,cod_mov,det_mov,cod_pai,cod_bod,"
                        + "cod_ubi,cod_art,det_can,exi_ant_bod,exi_act_bod,cos_uni,cos_pro,det_lot,exi_act_lot,exi_ant_tot,exi_act_tot) VALUES "
                        + mhistoria + ";";
                macc.dmlSQLvariable(mQuery);

                //***********************************************************************************************************
                //************************** MODIFICACIÓN DE REGISTROS POSTERIORES TOTALES **********************************
                //***********************************************************************************************************
                Double contasiguientes;

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        mQuery = "select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";";
                        resVariable = macc.querySQLvariable(mQuery);

                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        mQuery = "select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";";
                        resVariable = macc.querySQLvariable(mQuery);

                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_ant_tot = exi_act_tot;
                                //cos_pro = ((cos_pro * exi_act_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_act_tot + (seriehistorica1.getDet_can())));
                                exi_act_tot = exi_act_tot + (seriehistorica1.getDet_can());
                                if ("cancel".equals(tipoaccion)) {
                                    if (!"0".equals(macc.strQuerySQLvariable("select ifnull(flg_anu,0) from inv_tbl_transacciones where cod_tra =(select cod_mov from inv_tbl_historico where cod_tra =" + seriehistorica1.getCod_tra() + ")"))
                                            && !"3".equals(macc.strQuerySQLvariable("select ifnull(flg_anu,0) from inv_tbl_transacciones where cod_tra =(select cod_mov from inv_tbl_historico where cod_tra =" + seriehistorica1.getCod_tra() + ")"))) {
                                        mQuery = "update inv_tbl_historico set "
                                                + "cos_pro = " + cos_pro + ","
                                                + "cos_uni = " + cos_pro + ","
                                                + "exi_ant_tot = " + exi_ant_tot + ","
                                                + "exi_act_tot = " + exi_act_tot + " "
                                                + "where "
                                                + "cod_tra = " + seriehistorica1.getCod_tra()
                                                + ";";
                                    } else {
                                        cos_pro = ((cos_pro * exi_ant_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_ant_tot + (seriehistorica1.getDet_can())));
                                        mQuery = "update inv_tbl_historico set "
                                                + "cos_pro = " + cos_pro + ","
                                                + "exi_ant_tot = " + exi_ant_tot + ","
                                                + "exi_act_tot = " + exi_act_tot + " "
                                                + "where "
                                                + "cod_tra = " + seriehistorica1.getCod_tra()
                                                + ";";
                                    }

                                } else {
                                    cos_pro = ((cos_pro * exi_ant_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_ant_tot + (seriehistorica1.getDet_can())));
                                    mQuery = "update inv_tbl_historico set "
                                            + "cos_pro = " + cos_pro + ","
                                            + "exi_ant_tot = " + exi_ant_tot + ","
                                            + "exi_act_tot = " + exi_act_tot + " "
                                            + "where "
                                            + "cod_tra = " + seriehistorica1.getCod_tra()
                                            + ";";
                                }
                            } else {
                                exi_ant_tot = exi_act_tot;
                                exi_act_tot = exi_act_tot - (seriehistorica1.getDet_can());
                                if (exi_act_tot < 0.0) {
                                    exi_act_tot = 0.0;
                                }
                                mQuery = "update inv_tbl_historico set "
                                        + "cos_pro = " + cos_pro + ","
                                        + "cos_uni = " + cos_pro + ","
                                        + "exi_ant_tot = " + exi_ant_tot + ","
                                        + "exi_act_tot = " + exi_act_tot + " "
                                        + "where "
                                        + "cod_tra = " + seriehistorica1.getCod_tra()
                                        + ";";
                            }

                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por País. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //***********************************************************************************************************
                //************************* MODIFICACIÓN DE REGISTROS POSTERIORES POR BODEGA ********************************
                //***********************************************************************************************************
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_ant = exi_act;
                                //cos_pro = ((cos_pro * exi_act + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_act + (seriehistorica1.getDet_can())));
                                exi_act = exi_act + (seriehistorica1.getDet_can());
                            } else {
                                exi_ant = exi_act;
                                exi_act = exi_act - (seriehistorica1.getDet_can());
                                if (exi_act < 0.0) {
                                    exi_act = 0.0;
                                }
                            }
                            mQuery = "update inv_tbl_historico set "
                                    + "exi_ant_bod = " + exi_ant + ","
                                    + "exi_act_bod = " + exi_act + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_tra()
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Bodega. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //***********************************************************************************************************
                //*************************** MODIFICACIÓN DE REGISTROS POSTERIORES POR LOTE ********************************
                //***********************************************************************************************************
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_act_lot = exi_act_lot + (seriehistorica1.getDet_can());

                            } else {
                                exi_act_lot = exi_act_lot - (seriehistorica1.getDet_can());
                                if (exi_act_lot < 0.0) {
                                    exi_act_lot = 0.0;
                                }
                            }
                            mQuery = "update inv_tbl_historico set "
                                    + "exi_act_lot = " + exi_act_lot + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_tra()
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Lote. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //******************************* Fin Tratamiento Histórico de movimientos *****************************
                mCorrela = mCorrela + 1;

            }

            macc.Desconectar();

            return true;

        } catch (Exception e) {
            System.out.println("Error en Guardar Movimiento de Inventario Productos Corregir. " + e.getMessage() + " Query: " + mQuery + " mValores: " + mValores);
            return false;
        }

    }

    public boolean guardarmovimiento() {
        String mQuery = "";
        String mValores = "", mhistoria = "", embalaje;
        ResultSet resVariable;
        try {
            macc.Conectar();

            int mCorrela = 1, ord_dia = 0;
            int id_tra = 0;
            String codtraanu = "";
            String flganu = "";
            Double cos_uni, cos_pro, exi_ant, exi_act, exi_act_lot, exi_ant_tot, exi_act_tot;
            for (int i = 0; i < detalles.size(); i++) {
                cos_uni = 0.0;
                cos_pro = 0.0;
                exi_ant = 0.0;
                exi_act = 0.0;
                exi_act_lot = 0.0;
                exi_ant_tot = 0.0;
                exi_act_tot = 0.0;
                flganu = macc.strQuerySQLvariable("select flg_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";");

                switch (flganu) {
                    case "0":
                        tipoaccion = "insert";
                        break;
                    case "1":
                        tipoaccion = "insert";
                        break;
                    case "2":
                        tipoaccion = "cancel";
                        codtraanu = macc.strQuerySQLvariable("select tra_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";");
                        break;
                    case "3":
                        tipoaccion = "move";
                        break;
                    case "4":
                        tipoaccion = "move";
                        break;

                }

                //*************** Tratamiento Histórico de movimientos  *********************************************************************** 
                //****************************************************************************************************************************
                //*********************** CORRELATIVO HISTORIA *******************************************************************************
                //****************************************************************************************************************************
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_historico;";
                id_tra = Integer.valueOf(macc.strQuerySQLvariable(mQuery));

                //****************************************************************************************************************************
                //*********************** ORDEN DIARIO ***************************************************************************************
                //****************************************************************************************************************************
                if (tipoaccion.equals("insert") || tipoaccion.equals("move")) {
                    mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + ";";
                    ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                }

                if (tipoaccion.equals("update")) {
                    if (!detalles.get(i).getOrd_dia_ant().equals("0") && !detalles.get(i).getOrd_dia_ant().equals("")) {
                        ord_dia = Integer.valueOf(detalles.get(i).getOrd_dia_ant());
                        mQuery = "update inv_tbl_historico set ord_dia = ord_dia + 1 "
                                + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                                + "AND cod_pai = " + cod_pai + " "
                                + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                                + "AND ord_dia >= " + ord_dia + " "
                                + ";";
                        macc.dmlSQLvariable(mQuery);
                    } else {
                        if ("true".equals(flg_ing_sal)) {
                            mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                                    + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                                    + "AND cod_pai = " + cod_pai + " "
                                    + "AND cod_art = " + detalles.get(i).getCod_art() + ";";
                            ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                        }

                        if ("false".equals(flg_ing_sal)) {
                            mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                                    + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                                    + "AND cod_pai = " + cod_pai + " "
                                    + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                                    + "AND flg_ing_sal = 'false' "
                                    + ";";
                            ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                            mQuery = "update inv_tbl_historico set ord_dia = ord_dia + 1 "
                                    + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                                    + "AND cod_pai = " + cod_pai + " "
                                    + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                                    + "AND ord_dia >= " + ord_dia + " "
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }
                    }
                }

                if (tipoaccion.equals("cancel")) {

                    mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                            + "AND cod_mov in (" + codtraanu + "," + cod_tra + ") "
                            + ";";
                    ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                    mQuery = "update inv_tbl_historico set ord_dia = ord_dia + 1 "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                            + "AND ord_dia >= " + ord_dia + " "
                            + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                //****************************************************************************************************************************
                //*********************** COSTO UNITARIO *************************************************************************************
                //****************************************************************************************************************************
                cos_uni = Double.valueOf(detalles.get(i).getDet_cos()) / Double.valueOf(detalles.get(i).getDet_can());

                //****************************************************************************************************************************
                //*********************** COSTO PROMEDIO Y EXISTENCIA ANTERIOR ***************************************************************
                //****************************************************************************************************************************
                if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1
                        && macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y')"
                                + "and flg_ing_sal = 'false' "
                                + ";") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else {
                    //************* Costo Promedio y Existencia anteriores  **************
                    if ("false".equals(flg_ing_sal) && "insert".equals(tipoaccion)) {
                        cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_tot * cos_pro),0) + "
                                + (Double.valueOf(detalles.get(i).getDet_can()) * cos_uni) + ")"
                                + "/(IFNULL(exi_act_tot,0)+" + detalles.get(i).getDet_can() + ") as Cpromedio "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }
                    if ("false".equals(flg_ing_sal) && "cancel".equals(tipoaccion)) {
                        if (ord_dia == 1) {
                            cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        } else {
                            cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        }

                    }
                    if ("false".equals(flg_ing_sal) && "move".equals(tipoaccion)) {
                        cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }
                    if ("true".equals(flg_ing_sal) && !"update".equals(tipoaccion) && !"cancel".equals(tipoaccion)) {
                        cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }
                    if ("update".equals(tipoaccion) || "cancel".equals(tipoaccion)) {
                        if ("update".equals(tipoaccion)) {
                            //*********** si es Update *******
                            if ("false".equals(flg_ing_sal)) {
                                if (ord_dia == 1) {
                                    cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_tot * cos_pro),0) + "
                                            + (Double.valueOf(detalles.get(i).getDet_can()) * cos_uni) + ")"
                                            + "/(IFNULL(exi_act_tot,0)+" + detalles.get(i).getDet_can() + ") as Cpromedio "
                                            + "from inv_tbl_historico "
                                            + "where "
                                            + "cod_art = " + detalles.get(i).getCod_art() + " "
                                            + "and cod_pai = " + cod_pai + " "
                                            + "and fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                            + "order by fec_tra desc,"
                                            + "ord_dia desc "
                                            + "limit 1;");
                                } else {
                                    cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_tot * cos_pro),0) + "
                                            + (Double.valueOf(detalles.get(i).getDet_can()) * cos_uni) + ")"
                                            + "/(IFNULL(exi_act_tot,0)+" + detalles.get(i).getDet_can() + ") as Cpromedio "
                                            + "from inv_tbl_historico "
                                            + "where "
                                            + "cod_art = " + detalles.get(i).getCod_art() + " "
                                            + "and cod_pai = " + cod_pai + " "
                                            + "and fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                            + "and ord_dia < " + ord_dia + " "
                                            + "order by fec_tra desc,"
                                            + "ord_dia desc "
                                            + "limit 1;");
                                }

                            } else //*******************************
                            {
                                if (ord_dia == 1) {
                                    cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                            + "from inv_tbl_historico "
                                            + "where "
                                            + "cod_art=" + detalles.get(i).getCod_art() + " "
                                            + "and cod_pai = " + cod_pai + " "
                                            + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                            + "order by fec_tra desc,"
                                            + "ord_dia desc "
                                            + "limit 1;");
                                } else {
                                    cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                            + "from inv_tbl_historico "
                                            + "where "
                                            + "cod_art=" + detalles.get(i).getCod_art() + " "
                                            + "and cod_pai = " + cod_pai + " "
                                            + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                            + "and ord_dia < " + ord_dia + " "
                                            + "order by fec_tra desc,"
                                            + "ord_dia desc "
                                            + "limit 1;");
                                }
                            }
                        } else if ("true".equals(flg_ing_sal)) {
                            //*******************************
                            if (ord_dia == 1) {
                                cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                        + "from inv_tbl_historico "
                                        + "where "
                                        + "cod_art=" + detalles.get(i).getCod_art() + " "
                                        + "and cod_pai = " + cod_pai + " "
                                        + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                        + "order by fec_tra desc,"
                                        + "ord_dia desc "
                                        + "limit 1;");
                            } else {
                                cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                        + "from inv_tbl_historico "
                                        + "where "
                                        + "cod_art=" + detalles.get(i).getCod_art() + " "
                                        + "and cod_pai = " + cod_pai + " "
                                        + "and  fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                        + "and ord_dia < " + ord_dia + " "
                                        + "order by fec_tra desc,"
                                        + "ord_dia desc "
                                        + "limit 1;");
                            }
                        }

                        if (ord_dia == 1) {

                            //***********  Existencia Anterior Bodega ********************************************
                            exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Lote **********************************************
                            exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                                    + "and fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Total ********************************************
                            exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        } else {
                            //***********  Existencia Anterior Bodega ********************************************
                            exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Lote **********************************************
                            exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                    + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                                    + "and fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //********** Existencia Anterior Total ********************************************
                            exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art=" + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "and ord_dia < " + ord_dia + " "
                                    + "order by fec_tra desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");
                        }

                    } else {
                        //***********  Existencia Anterior Bodega ********************************************
                        exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //********** Existencia Anterior Lote **********************************************
                        exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //********** Existencia Anterior Total ********************************************
                        exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }

                }

                //******************* Tratamiento de Existencias **************************************
                if ("false".equals(flg_ing_sal)) {
                    exi_act = exi_ant + Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_lot = exi_act_lot + Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_tot = exi_ant_tot + Double.valueOf(detalles.get(i).getDet_can());
                } else {
                    exi_act = exi_ant - Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_lot = exi_act_lot - Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_tot = exi_ant_tot - Double.valueOf(detalles.get(i).getDet_can());
                    //*********** Valida que las Existencias no sean menor que Cero ******************
                    if (exi_act < 0.0) {
                        exi_act = 0.0;
                    }
                    if (exi_act_lot < 0.0) {
                        exi_act_lot = 0.0;
                    }
                    if (exi_act_tot < 0.0) {
                        exi_act_tot = 0.0;
                    }
                }

                mhistoria = "(" + id_tra + ",'" + flg_ing_sal + "',str_to_date('" + fec_tra + "','%d/%m/%Y'),"
                        + ord_dia + "," + cod_tra + "," + mCorrela
                        + "," + cod_pai + "," + detalles.get(i).getCod_bod() + ","
                        + detalles.get(i).getCod_ubi() + "," + detalles.get(i).getCod_art() + ","
                        + detalles.get(i).getDet_can() + "," + exi_ant + "," + exi_act + "," + cos_uni + ","
                        + cos_pro + ",'" + detalles.get(i).getDet_lot().trim() + "'," + exi_act_lot + "," + exi_ant_tot + "," + exi_act_tot + ")";
                mQuery = "insert into inv_tbl_historico (cod_tra,flg_ing_sal,fec_tra,ord_dia,cod_mov,det_mov,cod_pai,cod_bod,"
                        + "cod_ubi,cod_art,det_can,exi_ant_bod,exi_act_bod,cos_uni,cos_pro,det_lot,exi_act_lot,exi_ant_tot,exi_act_tot) VALUES "
                        + mhistoria + ";";
                macc.dmlSQLvariable(mQuery);

                //***********************************************************************************************************
                //************************** MODIFICACIÓN DE REGISTROS POSTERIORES TOTALES **********************************
                //***********************************************************************************************************
                Double contasiguientes;

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_ant_tot = exi_act_tot;
                                exi_act_tot = exi_act_tot + (seriehistorica1.getDet_can());
                                if ("update".equals(tipoaccion) || "cancel".equals(tipoaccion)) {
                                    if (!"0".equals(macc.strQuerySQLvariable("select ifnull(flg_anu,0) from inv_tbl_transacciones where cod_tra =(select cod_mov from inv_tbl_historico where cod_tra =" + seriehistorica1.getCod_tra() + ")"))
                                            && !"3".equals(macc.strQuerySQLvariable("select ifnull(flg_anu,0) from inv_tbl_transacciones where cod_tra =(select cod_mov from inv_tbl_historico where cod_tra =" + seriehistorica1.getCod_tra() + ")"))) {
                                        mQuery = "update inv_tbl_transacciones_det set "
                                                + "det_cos = (det_can * " + cos_pro + " ) "
                                                + "where "
                                                + "cod_tra = " + "(select cod_mov from inv_tbl_historico where cod_tra =" + seriehistorica1.getCod_tra() + ") "
                                                + "and cod_det = " + "(select det_mov from inv_tbl_historico where cod_tra =" + seriehistorica1.getCod_tra() + ") "
                                                + ";";
                                        macc.dmlSQLvariable(mQuery);
                                        mQuery = "update inv_tbl_historico set "
                                                + "cos_pro = " + cos_pro + ","
                                                + "cos_uni = " + cos_pro + ","
                                                + "exi_ant_tot = " + exi_ant_tot + ","
                                                + "exi_act_tot = " + exi_act_tot + " "
                                                + "where "
                                                + "cod_tra = " + seriehistorica1.getCod_tra()
                                                + ";";
                                    } else {
                                        cos_pro = ((cos_pro * exi_ant_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_ant_tot + (seriehistorica1.getDet_can())));
                                        mQuery = "update inv_tbl_historico set "
                                                + "cos_pro = " + cos_pro + ","
                                                + "exi_ant_tot = " + exi_ant_tot + ","
                                                + "exi_act_tot = " + exi_act_tot + " "
                                                + "where "
                                                + "cod_tra = " + seriehistorica1.getCod_tra()
                                                + ";";
                                    }

                                } else {
                                    cos_pro = ((cos_pro * exi_ant_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_ant_tot + (seriehistorica1.getDet_can())));
                                    mQuery = "update inv_tbl_historico set "
                                            + "cos_pro = " + cos_pro + ","
                                            + "exi_ant_tot = " + exi_ant_tot + ","
                                            + "exi_act_tot = " + exi_act_tot + " "
                                            + "where "
                                            + "cod_tra = " + seriehistorica1.getCod_tra()
                                            + ";";
                                }

                            } else {
                                exi_ant_tot = exi_act_tot;
                                exi_act_tot = exi_act_tot - (seriehistorica1.getDet_can());
                                if (exi_act_tot < 0.0) {
                                    exi_act_tot = 0.0;
                                }
                                mQuery = "update inv_tbl_historico set "
                                        + "cos_pro = " + cos_pro + ","
                                        + "cos_uni = " + cos_pro + ","
                                        + "exi_ant_tot = " + exi_ant_tot + ","
                                        + "exi_act_tot = " + exi_act_tot + " "
                                        + "where "
                                        + "cod_tra = " + seriehistorica1.getCod_tra()
                                        + ";";
                            }

                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por País. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //***********************************************************************************************************
                //************************* MODIFICACIÓN DE REGISTROS POSTERIORES POR BODEGA ********************************
                //***********************************************************************************************************
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_ant = exi_act;
                                //cos_pro = ((cos_pro * exi_act + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_act + (seriehistorica1.getDet_can())));
                                exi_act = exi_act + (seriehistorica1.getDet_can());
                            } else {
                                exi_ant = exi_act;
                                exi_act = exi_act - (seriehistorica1.getDet_can());
                                if (exi_act < 0.0) {
                                    exi_act = 0.0;
                                }
                            }
                            mQuery = "update inv_tbl_historico set "
                                    + "exi_ant_bod = " + exi_ant + ","
                                    + "exi_act_bod = " + exi_act + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_tra()
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Bodega. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //***********************************************************************************************************
                //*************************** MODIFICACIÓN DE REGISTROS POSTERIORES POR LOTE ********************************
                //***********************************************************************************************************
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_act_lot = exi_act_lot + (seriehistorica1.getDet_can());

                            } else {
                                exi_act_lot = exi_act_lot - (seriehistorica1.getDet_can());
                                if (exi_act_lot < 0.0) {
                                    exi_act_lot = 0.0;
                                }
                            }
                            mQuery = "update inv_tbl_historico set "
                                    + "exi_act_lot = " + exi_act_lot + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_tra()
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Lote. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //******************************* Fin Tratamiento Histórico de movimientos *****************************
                mCorrela = mCorrela + 1;

            }

            macc.Desconectar();

            return true;

        } catch (Exception e) {
            System.out.println("Error en Guardar Movimiento de Inventario Productos Corregir. " + e.getMessage() + " Query: " + mQuery + " mValores: " + mValores);
            return false;
        }
    }

    public void onSelectPais() {
        encabezado.clear();
        totalregistros = 0;
        registroactual = 0;
        statusaccion = "Modificación de Criterio País realizado con éxito";
        RequestContext.getCurrentInstance().update("frmCorrectorSaldos");
    }

    public void dateSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fechabuscar = format.format(date);
        encabezado.clear();
        totalregistros = 0;
        registroactual = 0;
        statusaccion = "Modificación de Criterio Fecha realizado con éxito";
        RequestContext.getCurrentInstance().update("frmCorrectorSaldos");
    }

    //*********************************** Mensajería ************************************
    public void addMessage(String summary, String detail, int tipo) {
        FacesMessage message = new FacesMessage();
        switch (tipo) {
            case 1:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
                break;
            case 2:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
                break;
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<CatListados> getPaises() {
        return paises;
    }

    public void setPaises(List<CatListados> paises) {
        this.paises = paises;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getStatusaccion() {
        return statusaccion;
    }

    public void setStatusaccion(String statusaccion) {
        this.statusaccion = statusaccion;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public int getTotalregistros() {
        return totalregistros;
    }

    public void setTotalregistros(int totalregistros) {
        this.totalregistros = totalregistros;
    }

    public int getRegistroactual() {
        return registroactual;
    }

    public void setRegistroactual(int registroactual) {
        this.registroactual = registroactual;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

}
