package tt.mantenimiento;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import tt.general.Accesos;
import tt.general.CatHistorico;
import tt.general.CatListados;
import tt.general.Login;

@Named
@ConversationScoped

public class ManTblPiezas implements Serializable {

    private static final long serialVersionUID = 7997486784762638L;
    @Inject
    Login cbean;
    private List<CatListados> paises;
    private List<CatListados> clientes;
    private List<CatListados> attn;

    private List<CatListados> bodegas;
    private List<CatListados> ubicaciones;

    private List<CatListados> piezas;

    private List<CatHistorico> historico;

    private CatTblPiezas cattblpiezas;
    private List<CatTblPiezas> piezasencabezado;
    private CatTblPiezasDetalle cattblpiezasdetalle;
    private List<CatTblPiezasDetalle> piezasdetalle;

    private String cod_lis_pie, por_qbo, cod_pai, cod_pro, cod_mov, doc_tra,
            cod_sol, fec_tra, det_obs, flg_ing, det_sta;
    private String cod_enc, cod_det, cod_pie, cod_bod, cod_ubi;
    private Double det_can, det_cos;
    private String det_sta2;

    private String det_atn, nom_sol, flg_xxx;

    private Date mfecha;

    private String nombrereporte, nombreexportar;
    private Map<String, Object> parametros;

    private Accesos macc;

    public ManTblPiezas() {
    }

    //*********************** Configuraciones Principal ********************************
    public void iniciarventana() {

        macc = new Accesos();
        parametros = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");

        mfecha = Date.from(Instant.now());
        cod_lis_pie = "";
        por_qbo = "";
        cod_pai = cbean.getCod_pai();
        cod_pro = "0";
        cod_mov = "0";
        doc_tra = "special_parts_form";
        cod_sol = "0";
        fec_tra = format.format(mfecha);
        det_obs = "";
        flg_ing = "1";
        det_sta = "0";
        cod_enc = "";
        cod_det = "";
        cod_pie = "0";
        cod_bod = "0";
        cod_ubi = "";
        det_can = 0.0;
        det_cos = 0.0;
        det_sta2 = "0";

        det_atn = "";
        nom_sol = "";
        flg_xxx = "";

        paises = new ArrayList<>();
        clientes = new ArrayList<>();
        attn = new ArrayList<>();
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();

        piezas = new ArrayList<>();

        historico = new ArrayList<>();

        cattblpiezas = new CatTblPiezas();
        piezasencabezado = new ArrayList<>();
        cattblpiezasdetalle = new CatTblPiezasDetalle();
        piezasdetalle = new ArrayList<>();

        llenarPaises();
        llenarAttn();
        llenarClientes();
        llenarPiezas();
        llenarBodegas();

    }

    public void cerrarventana() {
        cod_lis_pie = null;
        por_qbo = null;
        cod_pai = null;
        cod_pro = null;
        cod_mov = null;
        doc_tra = null;
        cod_sol = null;
        fec_tra = null;
        det_obs = null;
        flg_ing = null;
        det_sta = null;
        cod_enc = null;
        cod_det = null;
        cod_pie = null;
        cod_bod = null;
        cod_ubi = null;
        det_can = 0.0;
        det_cos = 0.0;
        det_sta2 = null;

        det_atn = null;
        nom_sol = null;
        flg_xxx = null;

        paises = null;
        clientes = null;
        attn = null;
        bodegas = null;
        ubicaciones = null;

        piezas = null;

        historico = null;

        cattblpiezas = null;
        piezasencabezado = null;
        cattblpiezasdetalle = null;
        piezasdetalle = null;

        parametros = null;
        macc = null;

    }

    public void nuevo() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        mfecha = Date.from(Instant.now());

        cod_lis_pie = "";
        por_qbo = "";
        cod_pai = cbean.getCod_pai();
        cod_pro = "0";
        cod_mov = "0";
        doc_tra = "special_parts_form";
        cod_sol = "0";
        fec_tra = format.format(mfecha);
        det_obs = "";
        flg_ing = "1";
        det_sta = "0";
        cod_enc = "";
        cod_det = "";
        cod_pie = "0";
        cod_bod = "0";
        cod_ubi = "";
        det_can = 0.0;
        det_cos = 0.0;
        det_sta2 = "0";

        det_atn = "";
        nom_sol = "";
        flg_xxx = "";

        cattblpiezas = null;
        piezasencabezado = null;
        cattblpiezasdetalle = null;
        piezasdetalle.clear();

        llenarAttn();

    }

    //*********************** Llenado de Lista de Catálogos ********************************
    public void llenarPaises() {
        try {

            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by nom_pai;";
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
            System.out.println("Error en el llenado de Paises en ManTblPiezas. " + e.getMessage());
        }
    }

    public void llenarAttn() {
        try {

            attn.clear();

            String mQuery = "select distinct ifnull(ane.det_att,'') as det_att "
                    + "from tbl_pie_ane as ane "
                    + "left join tbl_pie as mae on ane.cod_lis_pie = mae.cod_lis_pie "
                    + "where mae.cod_pai = " + cod_pai + " "
                    + "order by ane.det_att;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                attn.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Attn en ManTblPiezas. " + e.getMessage());
        }
    }

    public void llenarClientes() {
        try {

            clientes.clear();

            String mQuery = "select cli.cod_cli, concat(cli.nom_cli,' (',pai.nom_pai,')') as nompai from cat_cli as cli "
                    + "left join cat_pai as pai on cli.cod_pai = pai.cod_pai "
                    + "order by cli.cod_pai, cli.nom_cli;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Clientes en ManTblPiezas. " + e.getMessage());
        }
    }

    public void llenarPiezas() {
        String mQuery = "";
        try {

            piezas.clear();

            mQuery = "select cod_pie, concat(ifnull(cod_ref,''),'--',ifnull(nom_pie,'')) as nompie from cat_pie;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Piezas en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {

            bodegas.clear();

            mQuery = "select id_bod, nom_bod "
                    + "from cat_bodegas "
                    + "where cod_pai=" + cod_pai + " "
                    + "order by id_bod;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select distinct des_ubi from tbl_pie_his where cod_pai = " + cod_pai + " and cod_bod = " + cod_bod + " and cod_pie = " + cod_pie + " order by des_ubi;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Ubicaciones en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

//**************************** Funciones Búsqueda *****************************
    public void iniciarBuscar() {
        llenarEncabezado();

    }

    public void cerrarBuscar() {
        RequestContext.getCurrentInstance().execute("PF('wDTIngBuscarEspParFor').clearFilters()");
        RequestContext.getCurrentInstance().update("frmBuscarEspParForm");
    }

    public void llenarEncabezado() {
        try {
            cattblpiezas = null;
            piezasencabezado.clear();

            String mQuery = "select enc.cod_lis_pie, enc.por_qbo, enc.cod_pai, enc.cod_pro, enc.cod_mov,   "
                    + "enc.doc_tra, enc.cod_sol, date_format(enc.fec_tra,'%d/%b/%Y') as fec_tra, enc.det_obs,   "
                    + "enc.flg_ing, enc.det_sta,   "
                    + "pai.nom_pai, ifnull(cli.nom_cli,'--------') as nom_pro, mov.nom_mov,   "
                    + "case enc.det_sta when 0 then 'ACTIVE' when 1 then 'CANCELED' end as detsta   "
                    + "from tbl_pie as enc   "
                    + "left join cat_pai as pai on enc.cod_pai = pai.cod_pai   "
                    + "left join cat_cli as cli on enc.cod_pro = cli.cod_cli   "
                    + "left join cat_mov as mov on enc.cod_mov = mov.id_mov   "
                    + "where enc.cod_pai = " + cod_pai + " "
                    + "and enc.doc_tra = 'special_parts_form' "
                    + "and enc.det_sta = 0 "
                    + "order by enc.cod_pai, enc.fec_tra, enc.cod_pro, enc.cod_lis_pie;";
            //System.out.println(mQuery);
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezasencabezado.add(new CatTblPiezas(
                        resVariable.getString(1),
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
                        resVariable.getString(15)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Encabezado en ManTblPiezas. " + e.getMessage());
        }
    }

    public void llenarDetalle() {
        try {
            cattblpiezasdetalle = null;
            piezasdetalle.clear();

            String mQuery = "select det.cod_enc, det.cod_det, det.cod_pie, det.cod_bod, det.cod_ubi, "
                    + "det.det_can, det.det_cos, det.det_sta, pie.nom_pie,bod.nom_bod,det.cod_ubi "
                    + "from tbl_pie_det as det "
                    + "left join cat_pie as pie on det.cod_pie = pie.cod_pie "
                    + "left join cat_bodegas as bod on det.cod_bod = bod.id_bod "
                    + "where det.cod_enc = " + cod_lis_pie + " and det.det_sta = 0 "
                    + "order by det.cod_det;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezasdetalle.add(new CatTblPiezasDetalle(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11)
                ));

            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalle en ManTblPiezas. " + e.getMessage());
        }
    }

//**************************** Operaciones de Grid Detalle ***********
    public boolean validardetalle() {
        boolean mvalidar = true;
        String mQuery = "";

        if ("0".equals(cod_bod)) {
            addMessage("Add", "You have to select a Warehouse", 2);
            return false;
        }

        if ("0".equals(cod_pie)) {
            addMessage("Add", "You have to select a Part", 2);
            return false;
        }

        if (det_can == 0.0) {
            addMessage("Add", "You have to enter a quantity above zero", 2);
            return false;
        }
        if ("0".equals(cod_pai)) {
            addMessage("Add", "You have to select a Country", 2);
            return false;
        } else {
            try {
                Double existencia = 0.0, msolicita = 0.0;

                //Accesos acc = new Accesos();
                mQuery = "select ifnull( (select exi.can_exi "
                        + " from tbl_pie_his as exi "
                        + " where "
                        + " exi.fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + " and exi.det_sta = 0 "
                        + " and exi.cod_pai = " + cod_pai + " "
                        + " and exi.cod_bod = " + cod_bod + " "
                        + " and exi.des_ubi = '" + cod_ubi + "' "
                        + " and exi.cod_pie = " + cod_pie + " "
                        + " order by exi.fec_his desc, exi.ord_dia desc limit 1),0) as existencia;";

                if (!piezasdetalle.isEmpty()) {
                    for (int i = 0; i < piezasdetalle.size(); i++) {
                        if (cod_pie.equals(piezasdetalle.get(i).getCod_pie())
                                && cod_bod.equals(piezasdetalle.get(i).getCod_bod())
                                && cod_ubi.equals(piezasdetalle.get(i).getCod_ubi())) {
                            msolicita = msolicita + Double.valueOf(piezasdetalle.get(i).getDet_can());
                        }
                    }
                }

                macc.Conectar();
                //System.out.println("cantidad grid: " + existencia + " Existencia:" + acc.doubleQuerySQLvariable(mQuery) + " Cantidad sol: " + pie_det_can);
                existencia = macc.doubleQuerySQLvariable(mQuery); // - det_can;
                macc.Desconectar();
                //System.out.println(" Existencia: " + existencia);
                if ((msolicita + det_can) > existencia) {
                    mvalidar = false;
                    addMessage("Parts", "Required Quantity is greater than stock", 2);

                }

            } catch (Exception e) {
                mvalidar = false;
                System.out.println("Error en Validación Existencia agregardetalle de ManInventarioPiezas. " + e.getMessage() + " Query: " + mQuery);
            }
        }
        return mvalidar;
    }

    public void agregardetalle() {
        try {
            if (validardetalle()) {

                macc.Conectar();
                int correlativo = 0, insert = 0;

                for (int i = 0; i < piezasdetalle.size(); i++) {

                    if (cod_pie.equals(piezasdetalle.get(i).getCod_pie())
                            && cod_bod.equals(piezasdetalle.get(i).getCod_bod())
                            && cod_ubi.equals(piezasdetalle.get(i).getCod_ubi())) {
                        insert = 1;
                        piezasdetalle.set(i, new CatTblPiezasDetalle(
                                piezasdetalle.get(i).getCod_enc(),
                                piezasdetalle.get(i).getCod_det(),
                                piezasdetalle.get(i).getCod_pie(),
                                piezasdetalle.get(i).getCod_bod(),
                                piezasdetalle.get(i).getCod_ubi(),
                                String.valueOf(Double.valueOf(piezasdetalle.get(i).getDet_can()) + det_can),
                                piezasdetalle.get(i).getDet_cos(),
                                piezasdetalle.get(i).getDet_sta(),
                                piezasdetalle.get(i).getNompie(),
                                piezasdetalle.get(i).getNombod(),
                                piezasdetalle.get(i).getNomubi()
                        ));
                    }

                    if (Integer.valueOf(piezasdetalle.get(i).getCod_det()) > correlativo) {
                        correlativo = Integer.valueOf(piezasdetalle.get(i).getCod_det());
                    }
                }

                if (insert == 0) {
                    piezasdetalle.add(new CatTblPiezasDetalle(
                            "",
                            String.valueOf(correlativo + 1),
                            cod_pie,
                            cod_bod,
                            cod_ubi,
                            String.valueOf(det_can),
                            String.valueOf(det_cos),
                            det_sta,
                            macc.strQuerySQLvariable("select nom_pie from cat_pie where cod_pie =" + cod_pie + ";"),
                            macc.strQuerySQLvariable("select nom_bod from cat_bodegas where cod_pai = " + cod_pai + " and id_bod =" + cod_bod + ";"),
                            cod_ubi
                    ));
                }
                macc.Desconectar();
                cod_pie = "0";
                //cod_bod = "0";
                cod_ubi = "";
                det_can = 0.0;
                det_cos = 0.0;
                det_sta = "0";
                cattblpiezasdetalle = null;

            }
        } catch (Exception e) {
            System.out.println("Error en Agregar Detalle ManTblPiezas." + e.getMessage());
        }
    }

    public void eliminardetalle() {
        if ("".equals(cod_det)) {
            addMessage("Remove", "You have to select a Record", 2);
        } else {
            for (int i = 0; i < piezasdetalle.size(); i++) {
                if (cod_det.equals(piezasdetalle.get(i).getCod_det())) {
                    piezasdetalle.remove(i);
                    cod_det = "";
                    cod_pie = "0";
                    cod_bod = "0";
                    cod_ubi = "";
                    det_can = 0.0;
                    det_cos = 0.0;
                    det_sta = "0";
                    cattblpiezasdetalle = null;
                }
            }

        }
    }

//**************************** Operaciones de Mantenimiento de Tablas ***********
    public boolean validardatos() {
        boolean mvalidar = true;
        if ("0".equals(cod_pai)) {
            addMessage("Save", "You have to select a Country", 2);
            return false;
        }
        if ("0".equals(cod_pro)) {
            addMessage("Save", "You have to select a Costumer", 2);
            return false;
        }
        if ("".equals(por_qbo)) {
            addMessage("Save", "You have to enter an Ivoice", 2);
            return false;
        }
        if ("0".equals(flg_xxx)) {
            addMessage("Save", "You have to select an Action", 2);
            return false;
        }
        if (piezasdetalle.isEmpty()) {
            addMessage("Save", "You have to enter an Item in the List", 2);
            return false;
        }
        return mvalidar;
    }

    public void guardar() {
        if (validardatos()) {
            try {
                macc.Conectar();
                String mQuery;
                if ("".equals(cod_lis_pie)) {
                    mQuery = "select ifnull(max(cod_lis_pie),0)+1 as codigo from tbl_pie;";
                    cod_lis_pie = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into tbl_pie (cod_lis_pie,por_qbo,cod_pai,cod_pro,cod_mov,"
                            + "doc_tra,cod_sol,fec_tra,det_obs,flg_ing,det_sta) "
                            + "values (" + cod_lis_pie + ",'" + por_qbo + "'," + cod_pai + ","
                            + cod_pro + "," + cod_mov + ",'" + doc_tra + "'," + cod_sol + ","
                            + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + ",'"
                            + det_obs + "',1," + det_sta + ");";
                    macc.dmlSQLvariable(mQuery);

                    mQuery = "insert into tbl_pie_ane (cod_lis_pie,nom_sol,flg_xxx,det_att) "
                            + "values (" + cod_lis_pie + ",'" + nom_sol + "'," + flg_xxx + ",'" + det_atn + "');";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update tbl_pie SET "
                            + " por_qbo ='" + por_qbo + "' "
                            + ",cod_pai = " + cod_pai
                            + ",cod_pro = " + cod_pro
                            + ",cod_mov = " + cod_mov
                            + ",doc_tra ='" + doc_tra + "' "
                            + ",cod_sol = " + cod_sol
                            + ",fec_tra = " + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + " "
                            + ",det_obs ='" + det_obs + "' "
                            + ",flg_ing = " + flg_ing
                            + ",det_sta = " + det_sta + " "
                            + "WHERE cod_lis_pie =" + cod_lis_pie + ";";
                    macc.dmlSQLvariable(mQuery);

                    mQuery = "update tbl_pie_ane set "
                            + "nom_sol = '" + nom_sol + "',"
                            + "flg_xxx = " + flg_xxx + ","
                            + "det_att = '" + det_atn + "' "
                            + "where cod_lis_pie =" + cod_lis_pie + ";";
                    macc.dmlSQLvariable(mQuery);
                    mQuery = " delete from tbl_pie_det where cod_enc=" + cod_lis_pie + ";";
                    macc.dmlSQLvariable(mQuery);

                    // ********************** Borrado Lógico del Histórico *************************************
                    mQuery = " update tbl_pie_his set det_sta=1, fec_mod=now(), cod_usu=" + cbean.getCod_usu()
                            + " where flg_ing=" + flg_ing + " and cod_enc=" + cod_lis_pie + " and det_sta=0;";
                    macc.dmlSQLvariable(mQuery);

                }
                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);

                //-----------               mAccesos.dmlSQLvariable(mQuery);
                String mValues = "";

                for (int i = 0; i < piezasdetalle.size(); i++) {
//              ********************************** Existencias ****************************************

                    Double mCantidad;
                    mCantidad = Double.valueOf(piezasdetalle.get(i).getDet_can().replace(",", ""));
                    //código correlativo existencia histórica de artículo
                    String cod_cor_exi_art = macc.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                            + "as codigo from tbl_pie_his;");
                    //Código correlativo diario existencia histórica de artículo
                    String cor_dia = macc.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                            + "as cordia from tbl_pie_his "
                            + "where "
                            + "cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                            + "and fec_his=STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                            + ";");

                    //Costo promedio
                    Double cPromedio, exisAnt, cunitario, mNuevaExistencia;
                    if ("1".equals(cod_cor_exi_art)) {
                        cPromedio = Double.valueOf(piezasdetalle.get(i).getDet_cos().replace(",", ""));
                        exisAnt = 0.0;
                    } else {
                        cPromedio = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //Existencia Anterior
                        exisAnt = macc.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    }
                    //Inserta Registro

                    mNuevaExistencia = (exisAnt - mCantidad);
                    if (mNuevaExistencia < 0.0) {
                        mNuevaExistencia = 0.0;
                    }

                    cunitario = Double.valueOf(piezasdetalle.get(i).getDet_cos().replace(",", ""));
                    mQuery = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                            + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu,cod_pai,cod_bod,des_ubi) "
                            + "VALUES (" + cod_cor_exi_art + "," + piezasdetalle.get(i).getCod_pie() + ","
                            + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + "," + cor_dia + "," + flg_ing + ","
                            + cod_lis_pie + "," + (i + 1) + "," + mCantidad + "," + cunitario + ","
                            + mNuevaExistencia + ","
                            + cPromedio + "," + "0" + "," + "now()" + "," + cbean.getCod_usu() + ","
                            + cod_pai + "," + piezasdetalle.get(i).getCod_bod() + ",'" + piezasdetalle.get(i).getCod_ubi() + "');";

                    macc.dmlSQLvariable(mQuery);

                    // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                    String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                            + "from tbl_pie_his where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and ord_dia >" + cor_dia + " "
                            + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                            + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                            + ";");
                    contasiguientes = String.valueOf(
                            Integer.valueOf(contasiguientes)
                            + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                    + "from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                    + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                    + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                    + ";")));

                    Double nuevacantidad = mNuevaExistencia;
                    if ("0".equals(contasiguientes) == false) {
                        try {
                            historico.clear();

                            //Double cos_uni_sal = 0.0;
                            ResultSet resvariable;
                            resvariable = macc.querySQLvariable("select "
                                    + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                    + "fec_mod,cod_usu "
                                    + "from tbl_pie_his "
                                    + "where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                    + "and ord_dia > " + cor_dia + " "
                                    + "and cod_pie=" + cod_pie + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + cod_ubi + "' "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc "
                                    + ";");
                            while (resvariable.next()) {
                                historico.add(new CatHistorico(
                                        resvariable.getString(1),
                                        resvariable.getString(2),
                                        resvariable.getString(3),
                                        resvariable.getString(4),
                                        resvariable.getString(5),
                                        resvariable.getString(6),
                                        resvariable.getString(7),
                                        resvariable.getString(8),
                                        resvariable.getString(9),
                                        resvariable.getString(10),
                                        resvariable.getString(11),
                                        resvariable.getString(12),
                                        resvariable.getString(13),
                                        resvariable.getString(14)
                                ));
                            }
                            resvariable.close();

                            resvariable = macc.querySQLvariable("select "
                                    + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                    + "fec_mod,cod_usu "
                                    + "from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                    + "and cod_pie=" + cod_pie + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + cod_ubi + "' "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc "
                                    + ";");
                            while (resvariable.next()) {
                                historico.add(new CatHistorico(
                                        resvariable.getString(1),
                                        resvariable.getString(2),
                                        resvariable.getString(3),
                                        resvariable.getString(4),
                                        resvariable.getString(5),
                                        resvariable.getString(6),
                                        resvariable.getString(7),
                                        resvariable.getString(8),
                                        resvariable.getString(9),
                                        resvariable.getString(10),
                                        resvariable.getString(11),
                                        resvariable.getString(12),
                                        resvariable.getString(13),
                                        resvariable.getString(14)
                                ));
                            }
                            resvariable.close();
                            for (CatHistorico seriehistorica1 : historico) {

                                if ("0".equals(seriehistorica1.getFlg_ing())) {
                                    cPromedio = (cPromedio * nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()) * Double.valueOf(seriehistorica1.getDet_cos())) / (nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()));
                                    nuevacantidad = nuevacantidad + Double.valueOf(seriehistorica1.getDet_can());

                                } else {
                                    nuevacantidad = nuevacantidad - Double.valueOf(seriehistorica1.getDet_can());

                                }

                                mQuery = "update tbl_pie_his set "
                                        + "cos_pro= " + cPromedio + " ,"
                                        + "can_exi= " + nuevacantidad + " "
                                        + "where "
                                        + "cod_his = " + seriehistorica1.getCod_his()
                                        + ";";
                                macc.dmlSQLvariable(mQuery);
                            }

                        } catch (Exception e) {
                            System.out.println("Error en actualización de costos posteriores Agregar. " + e.getMessage());
                        }

                    }

                    // Tratamiento tabla bol_exi_pai
                    String mContador = macc.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and ing_sal=" + flg_ing + " "
                            + "and cod_pie=" + piezasdetalle.get(i).getCod_pie()
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                                + "VALUES ("
                                + cod_pai + ","
                                + piezasdetalle.get(i).getCod_pie() + ","
                                + flg_ing + ","
                                + piezasdetalle.get(i).getDet_can()
                                + ");";

                    } else {
                        mQuery = "update bol_exi_pai set "
                                + "det_exi= (det_exi + " + piezasdetalle.get(i).getDet_can() + ") "
                                + "where "
                                + "cod_pai=" + cod_pai + " "
                                + "and ing_sal=" + flg_ing + " "
                                + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + ";";

                    }

                    macc.dmlSQLvariable(mQuery);

                    // Tratamiento tabla tbl_existencias
                    mContador = macc.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and cod_bod=" + piezasdetalle.get(i).getCod_bod() + " "
                            + "and cod_pie=" + piezasdetalle.get(i).getCod_pie()
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                                + "VALUES ("
                                + macc.strQuerySQLvariable("select (ifnull(max(cod_exi),0) + 1) as codigo from tbl_existencias;") + ","
                                + piezasdetalle.get(i).getCod_pie() + ","
                                + cod_pai + ","
                                + piezasdetalle.get(i).getCod_bod() + ",0,"
                                + piezasdetalle.get(i).getDet_can()
                                + ",0);";

                    } else if ("0".equals(flg_ing)) {
                        mQuery = " update tbl_existencias set det_can=(det_can+" + piezasdetalle.get(i).getDet_can() + ") "
                                + " where cod_pai=" + cod_pai + " and cod_bod = " + piezasdetalle.get(i).getCod_bod()
                                + " and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " ;";
                    } else {
                        mQuery = " update tbl_existencias set det_can=(det_can-" + piezasdetalle.get(i).getDet_can() + ") "
                                + " where cod_pai=" + cod_pai + " and cod_bod = " + piezasdetalle.get(i).getCod_bod()
                                + " and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " ;";
                    }

                    macc.dmlSQLvariable(mQuery);

//              ********************************* Fin Existencias ************************************
                    mValues = mValues + "," + "("
                            + cod_lis_pie + ","
                            + (i + 1) + ","
                            + piezasdetalle.get(i).getCod_pie() + ","
                            + piezasdetalle.get(i).getCod_bod() + ",'"
                            + piezasdetalle.get(i).getCod_ubi() + "',"
                            + piezasdetalle.get(i).getDet_can() + ","
                            + piezasdetalle.get(i).getDet_cos() + ","
                            + piezasdetalle.get(i).getDet_sta()
                            + ")";

                }
                // ******************* Inserta Detalles*****************
                mValues = mValues.substring(1);
                mQuery = "insert into tbl_pie_det(cod_enc,cod_det,"
                        + "cod_pie,cod_bod,cod_ubi,det_can,det_cos,det_sta) "
                        + "values " + mValues + ";";

                macc.dmlSQLvariable(mQuery);
                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);
                //*************** Devolver a estatus normal el registro editado en histórico ******************
                /*mQuery = " update tbl_pie_his set det_sta = 0 "
                        + " where cod_enc=" + cod_lis_pie + ";";
                mAccesos.dmlSQLvariable(mQuery);*/

                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Almacén ManTblPiezas. " + e.getMessage());
            }
            nuevo();
        }
    }

    public void eliminar() {
        if (cod_lis_pie.equals("") || cod_lis_pie.equals("0")) {
            addMessage("Cancel", "You have to select a Record from Edit List", 2);
        } else {
            try {
                macc.Conectar();
                String mQuery;

                mQuery = "update tbl_pie SET "
                        + "det_sta = 1 "
                        + "WHERE cod_lis_pie =" + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);

                mQuery = " update tbl_pie_det set det_sta = 1 where cod_enc=" + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);

                // ********************** Borrado Lógico del Histórico *************************************
                mQuery = " update tbl_pie_his set det_sta=1, fec_mod=now(), cod_usu=" + cbean.getCod_usu()
                        + " where flg_ing=1 and cod_enc=" + cod_lis_pie + " and det_sta=0;";
                macc.dmlSQLvariable(mQuery);

                String mValues = "";

                for (int i = 0; i < piezasdetalle.size(); i++) {
//              ********************************** Existencias ****************************************

                    Double mCantidad;
                    mCantidad = Double.valueOf(piezasdetalle.get(i).getDet_can().replace(",", ""));

                    //Código correlativo diario existencia histórica de artículo
                    String cor_dia = macc.strQuerySQLvariable("select ord_dia "
                            + "as cordia from tbl_pie_his "
                            + "where "
                            + "cod_enc=" + cod_lis_pie + ";");

                    //Costo promedio
                    Double cPromedio;

                    cPromedio = macc.doubleQuerySQLvariable("select cos_pro from tbl_pie_his "
                            + "where "
                            + "cod_enc=" + cod_lis_pie + ";");
                    // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                    String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                            + "from tbl_pie_his where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and ord_dia >" + cor_dia + " "
                            + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                            + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                            + ";");
                    contasiguientes = String.valueOf(
                            Integer.valueOf(contasiguientes)
                            + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                    + "from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                    + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                    + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                    + ";")));

                    Double nuevacantidad = macc.doubleQuerySQLvariable("select can_exi + det_can from tbl_pie_his "
                            + "where "
                            + "cod_enc=" + cod_lis_pie + ";");
                    if (!"0".equals(contasiguientes)) {
                        try {
                            historico.clear();

                            //Double cos_uni_sal = 0.0;
                            ResultSet resvariable;
                            resvariable = macc.querySQLvariable("select "
                                    + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                    + "fec_mod,cod_usu "
                                    + "from tbl_pie_his "
                                    + "where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                    + "and ord_dia > " + cor_dia + " "
                                    + "and cod_pie=" + cod_pie + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + cod_ubi + "' "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc "
                                    + ";");
                            while (resvariable.next()) {
                                historico.add(new CatHistorico(
                                        resvariable.getString(1),
                                        resvariable.getString(2),
                                        resvariable.getString(3),
                                        resvariable.getString(4),
                                        resvariable.getString(5),
                                        resvariable.getString(6),
                                        resvariable.getString(7),
                                        resvariable.getString(8),
                                        resvariable.getString(9),
                                        resvariable.getString(10),
                                        resvariable.getString(11),
                                        resvariable.getString(12),
                                        resvariable.getString(13),
                                        resvariable.getString(14)
                                ));
                            }
                            resvariable.close();

                            resvariable = macc.querySQLvariable("select "
                                    + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                    + "fec_mod,cod_usu "
                                    + "from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                    + "and cod_pie=" + cod_pie + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + cod_ubi + "' "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc "
                                    + ";");
                            while (resvariable.next()) {
                                historico.add(new CatHistorico(
                                        resvariable.getString(1),
                                        resvariable.getString(2),
                                        resvariable.getString(3),
                                        resvariable.getString(4),
                                        resvariable.getString(5),
                                        resvariable.getString(6),
                                        resvariable.getString(7),
                                        resvariable.getString(8),
                                        resvariable.getString(9),
                                        resvariable.getString(10),
                                        resvariable.getString(11),
                                        resvariable.getString(12),
                                        resvariable.getString(13),
                                        resvariable.getString(14)
                                ));
                            }
                            resvariable.close();

                            for (CatHistorico seriehistorica1 : historico) {

                                if ("0".equals(seriehistorica1.getFlg_ing())) {
                                    cPromedio = (cPromedio * nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()) * Double.valueOf(seriehistorica1.getDet_cos())) / (nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()));
                                    nuevacantidad = nuevacantidad + Double.valueOf(seriehistorica1.getDet_can());

                                } else {
                                    nuevacantidad = nuevacantidad - Double.valueOf(seriehistorica1.getDet_can());

                                }

                                mQuery = "update tbl_pie_his set "
                                        + "cos_pro= " + cPromedio + " ,"
                                        + "can_exi= " + nuevacantidad + " "
                                        + "where "
                                        + "cod_his = " + seriehistorica1.getCod_his()
                                        + ";";
                                macc.dmlSQLvariable(mQuery);
                            }

                        } catch (Exception e) {
                            System.out.println("Error en actualización de costos posteriores Cancelar ManTblPiezas. " + e.getMessage());
                        }

                    }

                    // Tratamiento tabla bol_exi_pai
                    String mContador = macc.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and ing_sal=" + flg_ing + " "
                            + "and cod_pie=" + piezasdetalle.get(i).getCod_pie()
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                                + "VALUES ("
                                + cod_pai + ","
                                + piezasdetalle.get(i).getCod_pie() + ","
                                + flg_ing + ","
                                + piezasdetalle.get(i).getDet_can()
                                + ");";

                    } else {
                        mQuery = "update bol_exi_pai set "
                                + "det_exi= (det_exi + " + piezasdetalle.get(i).getDet_can() + ") "
                                + "where "
                                + "cod_pai=" + cod_pai + " "
                                + "and ing_sal=" + flg_ing + " "
                                + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + ";";

                    }

                    macc.dmlSQLvariable(mQuery);

                    // Tratamiento tabla tbl_existencias
                    mContador = macc.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and cod_bod=" + piezasdetalle.get(i).getCod_bod() + " "
                            + "and cod_pie=" + piezasdetalle.get(i).getCod_pie()
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                                + "VALUES ("
                                + macc.strQuerySQLvariable("select (ifnull(max(cod_exi),0) + 1) as codigo from tbl_existencias;") + ","
                                + piezasdetalle.get(i).getCod_pie() + ","
                                + cod_pai + ","
                                + piezasdetalle.get(i).getCod_bod() + ",0,"
                                + piezasdetalle.get(i).getDet_can()
                                + ",0);";

                    } else {
                        mQuery = " update tbl_existencias set det_can=(det_can+" + piezasdetalle.get(i).getDet_can() + ") "
                                + " where cod_pai=" + cod_pai + " and cod_bod = " + piezasdetalle.get(i).getCod_bod()
                                + " and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " ;";
                    }

                    macc.dmlSQLvariable(mQuery);

//              ********************************* Fin Existencias ************************************
                }

                macc.Desconectar();
                addMessage("Cancel", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Cancel", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Cancelar Almacén ManTblPiezas. " + e.getMessage());
            }
            nuevo();
        }

    }

//**************************** Funciones Varias ***********
    public void onRowSelectEncabezado(SelectEvent event) {

        cod_lis_pie = ((CatTblPiezas) event.getObject()).getCod_lis_pie();
        por_qbo = ((CatTblPiezas) event.getObject()).getPor_qbo();
        cod_pai = ((CatTblPiezas) event.getObject()).getCod_pai();
        cod_pro = ((CatTblPiezas) event.getObject()).getCod_pro();
        cod_mov = ((CatTblPiezas) event.getObject()).getCod_mov();
        doc_tra = ((CatTblPiezas) event.getObject()).getDoc_tra();
        cod_sol = ((CatTblPiezas) event.getObject()).getCod_sol();
        fec_tra = ((CatTblPiezas) event.getObject()).getFec_tra();
        det_obs = ((CatTblPiezas) event.getObject()).getDet_obs();
        flg_ing = ((CatTblPiezas) event.getObject()).getFlg_ing();
        det_sta = ((CatTblPiezas) event.getObject()).getDet_sta();
        macc.Conectar();
        det_atn = macc.strQuerySQLvariable("select det_att from tbl_pie_ane where cod_lis_pie =" + cod_lis_pie + ";");
        nom_sol = macc.strQuerySQLvariable("select nom_sol from tbl_pie_ane where cod_lis_pie =" + cod_lis_pie + ";");
        flg_xxx = macc.strQuerySQLvariable("select flg_xxx from tbl_pie_ane where cod_lis_pie =" + cod_lis_pie + ";");
        macc.Desconectar();

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
            mfecha = format.parse(fec_tra);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");
            fec_tra = format2.format(mfecha);
        } catch (Exception ex) {
            mfecha = null;
            fec_tra = "";
        }

        llenarDetalle();

        RequestContext.getCurrentInstance().execute("PF('wBuscarEspParForm').hide()");
        //RequestContext.getCurrentInstance().update("frmInventarioPie");

    }

    public void onRowSelect(SelectEvent event) {
        cod_enc = ((CatTblPiezasDetalle) event.getObject()).getCod_enc();
        cod_det = ((CatTblPiezasDetalle) event.getObject()).getCod_det();
        cod_pie = ((CatTblPiezasDetalle) event.getObject()).getCod_pie();
        cod_bod = ((CatTblPiezasDetalle) event.getObject()).getCod_bod();
        cod_ubi = ((CatTblPiezasDetalle) event.getObject()).getCod_ubi();
        //det_can = (CatTblPiezasDetalle) event.getObject()).getDet_can();
        //det_cos = ((CatTblPiezasDetalle) event.getObject()).getDet_cos();
        det_sta = ((CatTblPiezasDetalle) event.getObject()).getDet_sta();
    }

    public void seleccionarpais() {
        llenarAttn();
        llenarBodegas();
        cod_bod = "0";
        det_atn = "";
        ubicaciones.clear();
        piezasdetalle.clear();
    }

    public void seleccionarpieza() {
        llenarBodegas();
        cod_bod = "0";
        llenarUbicaciones();
        cod_ubi = "0";
    }

    public void seleccionarbod() {
        llenarUbicaciones();
        cod_ubi = "0";

    }

    public void dateSelectedAction(SelectEvent f) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        if (piezasdetalle.isEmpty()) {
            Date date = (Date) f.getObject();
            fec_tra = format.format(date);
            cod_bod = "0";
            ubicaciones.clear();
        } else {
            try {
                mfecha = format.parse(fec_tra);
            } catch (Exception e) {

            }
            addMessage("Edit Date", "You can´t change Date because asociated details exists", 2);
        }

    }

    //************************* REPORTES ********************************
    public void ejecutarreporte() {
        try {
            parametros.clear();
            parametros.put("cod_lis_pie", cod_lis_pie);
            nombrereporte = "/reportes/man_special_part_form.jasper";
            nombreexportar = "Special_Parts_Form_" + por_qbo;

            verPDF();

        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte de ManTblPiezas. " + e.getMessage());
        }
    }

    public void verPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, macc.Conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en verPDF en ManTblPiezas." + e.getMessage());
        }
    }

    //************************* MENSAJES ********************************
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

//***************** GETTER Y SETTER ************************************
    public List<CatHistorico> getHistorico() {
        return historico;
    }

    public void setHistorico(List<CatHistorico> historico) {
        this.historico = historico;
    }

    public CatTblPiezas getCattblpiezas() {
        return cattblpiezas;
    }

    public void setCattblpiezas(CatTblPiezas cattblpiezas) {
        this.cattblpiezas = cattblpiezas;
    }

    public List<CatTblPiezas> getPiezasencabezado() {
        return piezasencabezado;
    }

    public void setPiezasencabezado(List<CatTblPiezas> piezasencabezado) {
        this.piezasencabezado = piezasencabezado;
    }

    public CatTblPiezasDetalle getCattblpiezasdetalle() {
        return cattblpiezasdetalle;
    }

    public void setCattblpiezasdetalle(CatTblPiezasDetalle cattblpiezasdetalle) {
        this.cattblpiezasdetalle = cattblpiezasdetalle;
    }

    public List<CatTblPiezasDetalle> getPiezasdetalle() {
        return piezasdetalle;
    }

    public void setPiezasdetalle(List<CatTblPiezasDetalle> piezasdetalle) {
        this.piezasdetalle = piezasdetalle;
    }

    public String getCod_lis_pie() {
        return cod_lis_pie;
    }

    public void setCod_lis_pie(String cod_lis_pie) {
        this.cod_lis_pie = cod_lis_pie;
    }

    public String getPor_qbo() {
        return por_qbo;
    }

    public void setPor_qbo(String por_qbo) {
        this.por_qbo = por_qbo;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_mov() {
        return cod_mov;
    }

    public void setCod_mov(String cod_mov) {
        this.cod_mov = cod_mov;
    }

    public String getDoc_tra() {
        return doc_tra;
    }

    public void setDoc_tra(String doc_tra) {
        this.doc_tra = doc_tra;
    }

    public String getCod_sol() {
        return cod_sol;
    }

    public void setCod_sol(String cod_sol) {
        this.cod_sol = cod_sol;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFlg_ing() {
        return flg_ing;
    }

    public void setFlg_ing(String flg_ing) {
        this.flg_ing = flg_ing;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getCod_enc() {
        return cod_enc;
    }

    public void setCod_enc(String cod_enc) {
        this.cod_enc = cod_enc;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getCod_ubi() {
        return cod_ubi;
    }

    public void setCod_ubi(String cod_ubi) {
        this.cod_ubi = cod_ubi;
    }

    public String getDet_sta2() {
        return det_sta2;
    }

    public void setDet_sta2(String det_sta2) {
        this.det_sta2 = det_sta2;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

    public List<CatListados> getPaises() {
        return paises;
    }

    public void setPaises(List<CatListados> paises) {
        this.paises = paises;
    }

    public List<CatListados> getClientes() {
        return clientes;
    }

    public void setClientes(List<CatListados> clientes) {
        this.clientes = clientes;
    }

    public List<CatListados> getAttn() {
        return attn;
    }

    public void setAttn(List<CatListados> attn) {
        this.attn = attn;
    }

    public List<CatListados> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<CatListados> bodegas) {
        this.bodegas = bodegas;
    }

    public List<CatListados> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<CatListados> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<CatListados> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatListados> piezas) {
        this.piezas = piezas;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public String getDet_atn() {
        return det_atn;
    }

    public void setDet_atn(String det_atn) {
        this.det_atn = det_atn;
    }

    public String getNom_sol() {
        return nom_sol;
    }

    public void setNom_sol(String nom_sol) {
        this.nom_sol = nom_sol;
    }

    public String getFlg_xxx() {
        return flg_xxx;
    }

    public void setFlg_xxx(String flg_xxx) {
        this.flg_xxx = flg_xxx;
    }

}
