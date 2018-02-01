package tt.productos;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.CatTransaccionesDetalle;
import tt.general.ListaCortaTransaccionExistencias;
import tt.general.Login;

@Named
@ConversationScoped

public class InvCompras implements Serializable {

    @Inject
    Login cbean;

    private Accesos macc;

    //*************************************** Solicitudes ***********************************************
    private CatSolMae catmaestro;
    private List<CatSolMae> maestro;
    private CatSolDet catdetalles;
    private List<CatSolDet> detalles;

    private List<CatListados> proveedores;
    private List<CatListados> productos;
    private List<CatListados> unidades;

    private String cod_mae, cod_alt, cod_pai, cod_pro, fec_sol, cod_usu_sol, cod_usu_apr,
            cod_usu_rec, det_obs, det_sta, fec_cie;

    private String det_cod_det, det_cod_ite, det_des_ite;
    private Double det_can_sol, det_can_ent, det_can_pen, det_cos_uni, det_can_sol_con;
    private String det_cod_uni_med, det_fec_cie, det_det_sta;

    private String rec_cod_rec, rec_fec_rec;
    private Double rec_det_can;
    private String rec_cod_usu_rec, rec_cod_his, rec_det_sta;

    private Date dfechasol, dfechacier, dfecharec;
    private String tabindex, coduniori, nomuniori;

    //*********************************** Entregas ****************************************************
    private CatSolMae catmaestroent;
    private List<CatSolMae> maestroent;
    private CatSolDet catdetallesent;
    private List<CatSolDet> detallesent;

    private List<CatListados> operaciones;
    private List<CatListados> bodegas;
    private List<CatListados> ubicaciones;
    private List<CatListados> lotes;

    private CatEntregas cattransaccionesdetalle;
    private List<CatEntregas> entregadetalles;
    private List<CatTransaccionesDetalle> historico;
    private List<ListaCortaTransaccionExistencias> hisexi;

    private CatDetLotComp catdetlotcomp;
    private List<CatDetLotComp> detallelotes;

    private String eent_cod_mae;

    private String eent_cod_alt, eent_cod_pai, eent_cod_pro, eent_fec_sol, eent_cod_usu_sol, eent_cod_usu_apr,
            eent_cod_usu_rec, eent_det_obs, eent_det_sta, eent_fec_cie, eent_qbo;

    private String ent_cod_det, ent_cod_ite, ent_des_ite;
    private Double ent_can_sol, ent_can_ent, ent_can_pen, ent_cos_uni, ent_can_sol_con;
    private String ent_cod_uni_med, ent_fec_cie, ent_det_sta;

    private String tra_orden, tra_tipooperacion, tra_correlaoperacion, tra_observaciones, tra_bodega, tra_ubicacion, tra_lote, tra_vencimiento, tra_unimed, tra_fectra, tra_poliza;
    private Double tra_cantidad, tra_costototal, tra_canconver;

    private String det_lot_det_lot, det_lot_fec_ven, det_lot_boolean;
    private Double det_lot_can_lot;

    private Date dfechatransac, dfechavenc, ddetlotfecven;

    private String menabled;

    public InvCompras() {
    }

    public void iniciarventana() {
        macc = new Accesos();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        dfechasol = Date.from(Instant.now());
        dfechacier = null;
        dfecharec = Date.from(Instant.now());

        cod_mae = "";
        cod_alt = "";
        cod_pai = cbean.getCod_pai();
        cod_pro = "0";
        fec_sol = format.format(dfechasol);
        cod_usu_sol = cbean.getCod_usu();
        cod_usu_apr = "0";
        cod_usu_rec = "0";
        det_obs = "";
        det_sta = "0";
        fec_cie = "";

        det_cod_det = "";
        det_cod_ite = "0";
        det_des_ite = "";
        det_can_sol = 0.0;
        det_can_ent = 0.0;
        det_can_pen = 0.0;
        det_fec_cie = format.format(dfecharec);
        det_cos_uni = 0.0;
        det_can_sol_con = 0.0;
        det_cod_uni_med = "0";
        det_det_sta = "0";

        rec_cod_rec = "";
        rec_fec_rec = format.format(dfecharec);
        rec_det_can = 0.0;
        rec_cod_usu_rec = "0";
        rec_cod_his = "";
        rec_det_sta = "0";

        eent_cod_mae = "";

        eent_cod_alt = "";
        eent_cod_pai = "0";
        eent_cod_pro = "0";
        eent_fec_sol = "";
        eent_cod_usu_sol = "";
        eent_cod_usu_apr = "0";
        eent_cod_usu_rec = "0";
        eent_det_obs = "";
        eent_det_sta = "0";
        eent_fec_cie = "";
        eent_qbo = "";

        ent_cod_det = "";
        ent_cod_ite = "0";
        ent_des_ite = "";
        ent_can_sol = 0.0;
        ent_can_ent = 0.0;
        ent_can_pen = 0.0;
        ent_fec_cie = "";
        ent_cos_uni = 0.0;
        ent_can_sol_con = 0.0;
        ent_cod_uni_med = "0";
        ent_det_sta = "0";

        dfechatransac = Date.from(Instant.now());
        dfechavenc = null;

        tra_fectra = format.format(dfechatransac);
        tra_orden = "";
        tra_tipooperacion = "0";
        tra_correlaoperacion = "";
        tra_observaciones = "";
        tra_bodega = "0";
        tra_ubicacion = "0";
        tra_lote = "";
        tra_vencimiento = "";
        tra_unimed = "0";
        tra_cantidad = 0.0;
        tra_costototal = 0.0;
        tra_canconver = 0.0;
        tra_poliza = "";

        tabindex = "0";
        menabled = "false";

        coduniori = "0";
        nomuniori = "";

        det_lot_det_lot = "";
        det_lot_fec_ven = "";
        det_lot_boolean = "true";
        ddetlotfecven = null;

        proveedores = new ArrayList<>();
        productos = new ArrayList<>();
        unidades = new ArrayList<>();

        catmaestro = new CatSolMae();
        catdetalles = new CatSolDet();
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();

        operaciones = new ArrayList<>();
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        lotes = new ArrayList<>();

        catmaestroent = new CatSolMae();
        catdetallesent = new CatSolDet();
        maestroent = new ArrayList<>();
        detallesent = new ArrayList<>();

        cattransaccionesdetalle = new CatEntregas();
        entregadetalles = new ArrayList<>();
        historico = new ArrayList<>();
        hisexi = new ArrayList<>();

        catdetlotcomp = new CatDetLotComp();
        detallelotes = new ArrayList<>();

        llenarProveedores();
        llenarPoductos();
        llenarUnidades();

        llenarMaestroEnt();

        llenarOperaciones();
        llenarBodegas();

    }

    public void cerrarventana() {
        dfechasol = null;
        dfechacier = null;
        dfecharec = null;

        cod_mae = null;
        cod_alt = null;
        cod_pai = null;
        cod_pro = null;
        fec_sol = null;
        cod_usu_sol = null;
        cod_usu_apr = null;
        cod_usu_rec = null;
        det_obs = null;
        det_sta = null;
        fec_cie = null;

        det_cod_det = null;
        det_cod_ite = null;
        det_des_ite = null;
        det_can_sol = null;
        det_can_ent = null;
        det_can_pen = null;
        det_fec_cie = null;
        det_cos_uni = null;
        det_can_sol_con = null;
        det_cod_uni_med = null;
        det_det_sta = null;

        rec_cod_rec = null;
        rec_fec_rec = null;
        rec_det_can = null;
        rec_cod_usu_rec = null;
        rec_cod_his = null;
        rec_det_sta = null;

        eent_cod_mae = null;
        eent_cod_alt = null;
        eent_cod_pai = null;
        eent_cod_pro = null;
        eent_fec_sol = null;
        eent_cod_usu_sol = null;
        eent_cod_usu_apr = null;
        eent_cod_usu_rec = null;
        eent_det_obs = null;
        eent_det_sta = null;
        eent_fec_cie = null;
        eent_qbo = null;

        ent_cod_det = null;
        ent_cod_ite = null;
        ent_des_ite = null;
        ent_can_sol = null;
        ent_can_ent = null;
        ent_can_pen = null;
        ent_fec_cie = null;
        ent_cos_uni = null;
        ent_can_sol_con = null;
        ent_cod_uni_med = null;
        ent_det_sta = null;

        tra_fectra = null;
        tra_orden = null;
        tra_tipooperacion = null;
        tra_correlaoperacion = null;
        tra_observaciones = null;
        tra_bodega = null;
        tra_ubicacion = null;
        tra_lote = null;
        tra_vencimiento = null;
        tra_unimed = null;
        tra_cantidad = null;
        tra_costototal = null;
        tra_canconver = null;
        tra_poliza = null;

        dfechatransac = null;
        dfechavenc = null;

        tabindex = null;
        menabled = null;

        coduniori = null;
        nomuniori = null;

        det_lot_det_lot = null;
        det_lot_fec_ven = null;
        det_lot_boolean = null;
        ddetlotfecven = null;

        proveedores = null;
        productos = null;
        unidades = null;

        catmaestro = null;
        catdetalles = null;
        maestro = null;
        detalles = null;

        operaciones = null;
        bodegas = null;
        ubicaciones = null;
        lotes = null;

        catmaestroent = null;
        catdetallesent = null;
        maestroent = null;
        detallesent = null;

        cattransaccionesdetalle = null;
        entregadetalles = null;
        historico = null;
        hisexi = null;

        catdetlotcomp = null;
        detallelotes = null;

        macc = null;
    }

    //**************** Llenado de Catálogos *************
    public void llenarProveedores() {
        try {
            proveedores.clear();

            String mQuery = "select cod_pro, nom_pro "
                    + "from inv_cat_pro "
                    + "where cod_pai = " + cod_pai + " "
                    + "order by nom_pro;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                proveedores.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Proveedores en InvCompras. " + e.getMessage());
        }
    }

    public void llenarPoductos() {
        try {
            productos.clear();

            String mQuery = "select id_art, concat(ifnull(cod_art,''),'--', ifnull(cod_alt,''),'--',ifnull(det_nom,'') ) as art "
                    + "from inv_cat_articulo "
                    + "where cod_pai = " + cod_pai + " "
                    + "order by cod_art;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                productos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Productos en InvCompras. " + e.getMessage());
        }
    }

    public void llenarUnidades() {
        try {
            unidades.clear();

            String mQuery = "select id_emb, nom_emb "
                    + "from inv_cat_embalaje "
                    + "order by nom_emb;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                unidades.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Unidades en InvCompras. " + e.getMessage());
        }
    }

    public void llenarOperaciones() {
        String mQuery = "";
        try {
            operaciones.clear();

            mQuery = "select id_mov, nom_mov "
                    + "from inv_cat_mov "
                    + "where flg_tip = 0 "
                    + "and cod_pai = " + cod_pai + " "
                    + "order by nom_mov;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                operaciones.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Operaciones en InvCompras. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {

            bodegas.clear();

            mQuery = "select id_bod, nom_bod "
                    + "from inv_cat_bodegas "
                    + "where cod_pai = " + cod_pai + " "
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
            System.out.println("Error en el llenado de Bodegas en InvCompras. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select ubi.id_ubi,ubi.nom_ubi "
                    + "from inv_cat_ubicaciones as ubi "
                    + "left join inv_cat_bodegas as bod on bod.id_bod = ubi.cod_bod "
                    + "where bod.cod_pai = " + cod_pai + " "
                    + "and ubi.cod_bod=" + tra_bodega + " "
                    + "order by ubi.id_ubi;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Ubicaciones en InvCompras. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarLotes() {
        String mQuery = "";
        try {

            lotes.clear();

            mQuery = "select '' as lote   "
                    + " "
                    + "union all   "
                    + " "
                    + "(select distinct lote from ( "
                    + "select t.lote from (select distinct trim(det.det_lot) as lote    "
                    + "from inv_tbl_transacciones as mae    "
                    + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra    "
                    + "where    "
                    + "mae.cod_pai = " + cod_pai + " "
                    + "AND det.cod_art = " + ent_cod_ite + " "
                    + ") as t   "
                    + "where (  "
                    + " select exi_act_lot   "
                    + " FROM inv_tbl_historico   "
                    + " where cod_pai = " + cod_pai + " "
                    + " and cod_art = " + ent_cod_ite + " "
                    + " and det_lot = t.lote   "
                    + " order by fec_tra desc, ord_dia desc limit 1   "
                    + ") > 0   "
                    + "  "
                    + "union all   "
                    + "  "
                    + "select distinct trim(det_lot) as lote   "
                    + "from inv_sol_det_lot as dlo   "
                    + "left join inv_sol_mae as mae on dlo.cod_mae = mae.cod_mae   "
                    + "left join inv_sol_det as det on mae.cod_mae = det.cod_mae and dlo.cod_det = det.cod_det   "
                    + "where   "
                    + "mae.cod_pai = " + cod_pai + " "
                    + "and det.cod_ite = " + ent_cod_ite + " "
                    + ") as temp "
                    + "order by lote); ";

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lotes.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Lotes en InvCompras. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    //*************** Solicitudes **********************
    public void nuevo() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        dfechasol = Date.from(Instant.now());
        dfechacier = null;
        dfecharec = Date.from(Instant.now());

        cod_mae = "";
        cod_alt = "";
        cod_pai = cbean.getCod_pai();
        cod_pro = "0";
        fec_sol = format.format(dfechasol);
        cod_usu_sol = cbean.getCod_usu();
        cod_usu_apr = "0";
        cod_usu_rec = "0";
        det_obs = "";
        det_sta = "0";
        fec_cie = "";

        det_cod_det = "";
        det_cod_ite = "0";
        det_des_ite = "";
        det_can_sol = 0.0;
        det_can_ent = 0.0;
        det_can_pen = 0.0;
        det_fec_cie = "";
        det_cos_uni = 0.0;
        det_can_sol_con = 0.0;
        det_cod_uni_med = "0";
        det_det_sta = "0";

        rec_cod_rec = "";
        rec_fec_rec = format.format(dfecharec);
        rec_det_can = 0.0;
        rec_cod_usu_rec = "0";
        rec_cod_his = "";
        rec_det_sta = "0";

        tabindex = "0";
        coduniori = null;
        nomuniori = null;

        catdetalles = null;
        detalles.clear();

    }

    public boolean validardetalle() {
        boolean mvalidar = true;

        if ("0".equals(det_cod_ite)) {
            addMessage("Validar", "Debe Seleccionar un Item", 2);
            return false;
        }

        if ("0".equals(cod_pai)) {
            addMessage("Validar", "Debe Seleccionar un País", 2);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;
        if ("0".equals(det_cod_uni_med)) {
            addMessage("Validar", "Debe Seleccionar un Unidad de Medida", 2);
            return false;
        } else if (!coduniori.equals(det_cod_uni_med)) {
            macc.Conectar();
            try {
                mFactor = macc.doubleQuerySQLvariable("select ifnull(val_ini/val_sal,0.0) as factor "
                        + "from inv_cat_embalaje_rel "
                        + "where uni_med_ent = " + coduniori + " "
                        + "and uni_med_sal = " + det_cod_uni_med + ";");
            } catch (Exception e) {
                mFactor = 0.0;
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                try {
                    mFactor = macc.doubleQuerySQLvariable("select ifnull(val_sal/val_ini,0.0) as factor "
                            + "from inv_cat_embalaje_rel "
                            + "where uni_med_sal = " + coduniori + " "
                            + "and uni_med_ent = " + det_cod_uni_med + ";");
                } catch (Exception e) {
                    mFactor = 0.0;
                }
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                addMessage("Validar", "No Existe relación Definida Válida entre Unidades de Medida Seleccionadas", 2);
                macc.Desconectar();
                return false;
            }
            macc.Desconectar();
        } else {
            mFactor = 1.0;
        }

        if (mvalidar) {
            det_can_sol = det_can_sol_con;
            det_can_sol = det_can_sol * mFactor;
        }

        if (det_can_sol == 0.0) {
            addMessage("Validar", "Debe Ingresar una Cantidad Superior a Cero", 2);
            return false;
        }

        return mvalidar;

    }

    public void agregardetalle() {
        if (validardetalle()) {
            int correlativo = 0;
            try {
                for (int i = 0; i < detalles.size(); i++) {
                    if (Integer.valueOf(detalles.get(i).getCod_det()) > correlativo) {
                        correlativo = Integer.valueOf(detalles.get(i).getCod_det());
                    }
                }

                String estado, codigo, unidad;

                macc.Conectar();
                estado = "PENDIENTE";
                codigo = macc.strQuerySQLvariable("select ifnull(cod_art,'') as codart from inv_cat_articulo where id_art=" + det_cod_ite + ";");
                unidad = macc.strQuerySQLvariable("select ifnull(nom_emb,'') as nomemb from inv_cat_embalaje where id_emb = " + det_cod_uni_med + ";");
                det_des_ite = macc.strQuerySQLvariable("select ifnull(det_nom,'') as nomart from inv_cat_articulo where id_art=" + det_cod_ite + ";");
                macc.Desconectar();

                detalles.add(new CatSolDet(
                        "",
                        String.valueOf(correlativo + 1),
                        det_cod_ite,
                        codigo,
                        det_des_ite,
                        det_can_sol,
                        det_can_ent,
                        det_can_sol,
                        det_can_sol_con,
                        det_cod_uni_med,
                        det_fec_cie,
                        det_cos_uni,
                        det_det_sta,
                        estado,
                        codigo,
                        unidad
                ));

                det_cod_det = "";
                det_cod_ite = "0";
                det_des_ite = "";
                det_can_sol = 0.0;
                det_can_ent = 0.0;
                det_can_pen = 0.0;
                det_fec_cie = "";
                det_cos_uni = 0.0;
                det_can_sol_con = 0.0;
                det_cod_uni_med = "0";
                det_det_sta = "0";

            } catch (Exception e) {
                System.out.println("Error en agregardetalle de ManSolicitudes." + e.getMessage());
            }
        }

    }

    public void eliminardetalle() {
        if ("".equals(det_cod_det)) {
            addMessage("Validar", "Debe Seleccionar un Registro", 2);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (det_cod_det.equals(detalles.get(i).getCod_det())) {
                    detalles.remove(i);
                }
            }

            det_cod_det = "";
            det_cod_ite = "0";
            det_des_ite = "";
            det_can_sol = 0.0;
            det_can_ent = 0.0;
            det_can_pen = 0.0;
            det_fec_cie = "";
            det_cos_uni = 0.0;
            det_can_sol_con = 0.0;
            det_cod_uni_med = "0";
            det_det_sta = "0";

        }
    }

    public boolean validarg() {
        boolean valida = true;
        if (detalles.isEmpty()) {
            valida = false;
            addMessage("Validar", "Debe intresar al menos un detalle", 2);
        }

        return valida;
    }

    public void guardar() {
        String mQuery = "";
        try {
            if (validarg()) {
                macc.Conectar();
                if ("".equals(cod_mae)) {

                    mQuery = "select ifnull(max(cod_mae),0)+1 as codigo from inv_sol_mae;";
                    cod_mae = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_sol_mae (cod_mae,cod_alt,cod_pai,cod_pro,fec_sol,cod_usu_sol,cod_usu_apr,cod_usu_rec, "
                            + "det_obs,det_sta,fec_cie,usu_mod,fec_mod) "
                            + "values (" + cod_mae + ",'" + cod_alt + "'," + cod_pai + "," + cod_pro + ",str_to_date('" + fec_sol + "','%d/%m/%Y'),"
                            + cod_usu_sol + "," + cod_usu_apr + "," + cod_usu_rec + ",'" + det_obs.replace("'", " ") + "'," + det_sta + ",null," + cod_usu_sol + ",now());";

                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from inv_sol_det where cod_mae = " + cod_mae + ";";
                    macc.dmlSQLvariable(mQuery);

                    String mValores = "";
                    int mCorrela = 1;
                    for (int i = 0; i < detalles.size(); i++) {
                        mValores = mValores + ",("
                                + cod_mae + ","
                                + mCorrela + ","
                                + detalles.get(i).getCod_art() + ",'"
                                + detalles.get(i).getDes_ite() + "',"
                                + detalles.get(i).getDet_can_sol() + ","
                                + detalles.get(i).getDet_can_ent() + ","
                                + detalles.get(i).getDet_can_pen() + ","
                                + detalles.get(i).getDet_can_con() + ","
                                + detalles.get(i).getCod_uni_med() + ","
                                + "null,"
                                + "0.0,"
                                + detalles.get(i).getDet_sta() + ","
                                + cod_usu_sol
                                + ",now())";
                        mCorrela = mCorrela + 1;
                    }
                    mValores = mValores.substring(1);
                    mQuery = "insert into inv_sol_det(cod_mae,cod_det,cod_ite,des_ite,det_can_sol,det_can_ent,det_can_pen,"
                            + "det_can_con,cod_uni_med,fec_cie,cos_uni,det_sta,usu_mod,fec_mod) values " + mValores + ";";

                    macc.dmlSQLvariable(mQuery);

                } else {
                    mQuery = "update inv_sol_mae set "
                            + "fec_sol = str_to_date('" + fec_sol + "','%d/%m/%Y %h:%i'),"
                            + "cod_alt = " + cod_alt + ", "
                            + "cod_pro = " + cod_pro + ", "
                            + "det_obs ='" + det_obs.replace("'", "") + "' "
                            + "where cod_mae=" + cod_mae + ";";

                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from inv_sol_det where cod_mae=" + cod_mae + ";";
                    macc.dmlSQLvariable(mQuery);
                    String mValores = "";
                    int mCorrela = 1;
                    for (int i = 0; i < detalles.size(); i++) {
                        mValores = mValores + ",("
                                + cod_mae + ","
                                + mCorrela + ","
                                + detalles.get(i).getCod_art() + ",'"
                                + detalles.get(i).getDes_ite() + "',"
                                + detalles.get(i).getDet_can_sol() + ","
                                + detalles.get(i).getDet_can_ent() + ","
                                + detalles.get(i).getDet_can_pen() + ","
                                + detalles.get(i).getDet_can_con() + ","
                                + detalles.get(i).getCod_uni_med() + ","
                                + "null,"
                                + "0.0,"
                                + detalles.get(i).getDet_sta() + ","
                                + cod_usu_sol
                                + ",now())";
                        mCorrela = mCorrela + 1;
                    }
                    mValores = mValores.substring(1);
                    mQuery = "insert into inv_sol_det(cod_mae,cod_det,cod_ite,des_ite,det_can_sol,det_can_ent,det_can_pen,"
                            + "det_can_con,cod_uni_med,fec_cie,cos_uni,det_sta,usu_mod,fec_mod) values " + mValores + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                macc.Desconectar();
                nuevo();
                llenarMaestroEnt();
                addMessage("Guardar", "Información almacenada con éxito", 1);
            }
        } catch (Exception e) {
            System.out.println("Error en guardar Solicitud." + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onRowSelectDet(SelectEvent event) {
        det_cod_det = ((CatSolDet) event.getObject()).getCod_det();

    }

    //*************** Entregas ************************
    public void llenarMaestroEnt() {
        try {
            maestroent.clear();

            String mQuery = "select  "
                    + "mae.cod_mae, "
                    + "mae.cod_alt, "
                    + "mae.cod_pai, "
                    + "mae.cod_pro, "
                    + "date_format(mae.fec_sol,'%d/%m/%Y') as fecsol, "
                    + "mae.cod_usu_sol, "
                    + "mae.cod_usu_apr, "
                    + "mae.cod_usu_rec, "
                    + "mae.det_obs, "
                    + "mae.det_sta, "
                    + "mae.fec_cie, "
                    + "pai.nom_pai, "
                    + "pro.nom_pro, "
                    + "us1.det_nom, "
                    + "us2.det_nom, "
                    + "case mae.det_sta "
                    + "when 0 then 'ESPERA' "
                    + "when 1 then 'CANCELADA' "
                    + "when 2 then 'ENTREGA PARCIAL' "
                    + "when 3 then 'CERRADA' "
                    + "end as estado "
                    + "FROM inv_sol_mae as mae "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join inv_cat_pro as pro on mae.cod_pai = pro.cod_pai and mae.cod_pro = pro.cod_pro "
                    + "left join cat_usu as us1 on mae.cod_usu_sol = us1.cod_usu "
                    + "left join cat_usu as us2 on mae.cod_usu_rec = us2.cod_usu "
                    + "where mae.cod_pai = " + cbean.getCod_pai() + " "
                    + "and mae.det_sta in (0,2) "
                    + "order by mae.cod_mae "
                    + ";";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                maestroent.add(new CatSolMae(
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
                        resVariable.getString(15),
                        resVariable.getString(16)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Solicitudes Entrega en InvCompras. " + e.getMessage());
        }
    }

    public void llenarDetalleEnt() {
        try {

            detallesent.clear();

            String mQuery = "select  "
                    + "det.cod_mae,  "
                    + "det.cod_det,  "
                    + "art.cod_art,  "
                    + "det.cod_ite,  "
                    + "det.des_ite, "
                    + "format(det.det_can_sol,4),  "
                    + "format(det.det_can_ent,4),  "
                    + "format(det.det_can_pen,4),  "
                    + "format(det_can_con,4), "
                    + "det.cod_uni_med,  "
                    + "det.fec_cie, "
                    + "det.cos_uni, "
                    + "det.det_sta,  "
                    + "case det.det_sta "
                    + "when 0 then 'PENDIENTE' "
                    + "when 1 then 'ENTREGA PARCIAL' "
                    + "when 2 then 'ENTREGADO' "
                    + "when 3 then 'CANCELADO' "
                    + "end as estado,  "
                    + "art.cod_art,  "
                    + "emb.nom_emb "
                    + "from inv_sol_det as det  "
                    + "left join inv_cat_articulo as art on det.cod_ite = art.id_art "
                    + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                    + "where det.cod_mae = " + eent_cod_mae + " "
                    + "order by det.cod_det;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detallesent.add(new CatSolDet(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getDouble(6),
                        resVariable.getDouble(7),
                        resVariable.getDouble(8),
                        resVariable.getDouble(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getDouble(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalles Entrega en InvCompras. " + e.getMessage());
        }
    }

    public void limpiar() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        llenarMaestroEnt();
        detallesent.clear();

        catmaestroent = null;
        catdetallesent = null;

        eent_cod_mae = "";

        eent_cod_alt = "";
        eent_cod_pai = "0";
        eent_cod_pro = "0";
        eent_fec_sol = "";
        eent_cod_usu_sol = "";
        eent_cod_usu_apr = "0";
        eent_cod_usu_rec = "0";
        eent_det_obs = "";
        eent_det_sta = "0";
        eent_fec_cie = "";
        eent_qbo = "";

        ent_cod_det = "";
        ent_cod_ite = "0";
        ent_des_ite = "";
        ent_can_sol = 0.0;
        ent_can_ent = 0.0;
        ent_can_pen = 0.0;
        ent_fec_cie = "";
        ent_cos_uni = 0.0;
        ent_can_sol_con = 0.0;
        ent_cod_uni_med = "0";
        ent_det_sta = "0";

        dfechatransac = Date.from(Instant.now());
        dfechavenc = null;

        tra_fectra = format.format(dfechatransac);
        tra_orden = "";
        tra_tipooperacion = "0";
        tra_correlaoperacion = "";
        tra_observaciones = "";
        tra_bodega = "0";
        tra_ubicacion = "0";
        tra_lote = "";
        tra_vencimiento = "";
        tra_unimed = "0";
        tra_cantidad = 0.0;
        tra_costototal = 0.0;
        tra_canconver = 0.0;
        tra_poliza = "";

        cattransaccionesdetalle = null;
        entregadetalles.clear();

    }

    public boolean validardetalleEntrega() {
        boolean mvalidar = true;

        if (tra_vencimiento.equals("00/00/0000")) {
            tra_vencimiento = "";
        }
        if (tra_vencimiento == null) {
            tra_vencimiento = "";
        }
        if (tra_lote == null) {
            tra_lote = "";
        }
        if ("0".equals(cod_pai)) {
            addMessage("Validar", "Debe Seleccionar un País", 2);
            return false;
        }
        if ("0".equals(ent_cod_ite) || "".equals(ent_cod_ite)) {
            addMessage("Validar", "Debe Seleccionar un Producto", 2);
            return false;
        }
        if ("0".equals(tra_bodega)) {
            addMessage("Validar", "Debe escoger una Bodega", 2);
            return false;
        }
        if (tra_cantidad > ent_can_pen) {
            addMessage("Validar", "La Cantidad Entregada no puede ser superior a la Cantidad Pendiente", 2);
            return false;
        }
        if (tra_cantidad <= 0.0) {
            addMessage("Validar", "Debe Ingresar una Cantidad mayor que Cero", 2);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;

        String unimedori = "";

        macc.Conectar();
        unimedori = macc.strQuerySQLvariable("select det_emb from inv_cat_articulo where id_art = " + ent_cod_ite + ";");
        macc.Desconectar();

        if ("0".equals(tra_unimed)) {
            addMessage("Validar", "Debe Seleccionar un Unidad de Medida", 2);
            return false;
        } else if (!unimedori.equals(tra_unimed)) {
            macc.Conectar();
            try {
                mFactor = macc.doubleQuerySQLvariable("select ifnull(val_ini/val_sal,0.0) as factor "
                        + "from inv_cat_embalaje_rel "
                        + "where uni_med_ent = " + unimedori + " "
                        + "and uni_med_sal = " + tra_unimed + ";");
            } catch (Exception e) {
                mFactor = 0.0;
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                try {
                    mFactor = macc.doubleQuerySQLvariable("select ifnull(val_sal/val_ini,0.0) as factor "
                            + "from inv_cat_embalaje_rel "
                            + "where uni_med_sal = " + unimedori + " "
                            + "and uni_med_ent = " + tra_unimed + ";");
                } catch (Exception e) {
                    mFactor = 0.0;
                }
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                addMessage("Validar", "No Existe relación Definida Válida entre Unidades de Medida Seleccionadas", 2);
                macc.Desconectar();
                return false;
            }
            macc.Desconectar();
        } else {
            mFactor = 1.0;
        }

        //*************************** Verificación de Existencias y Puntos Máximos *************************************************
        Double mExi = 0.0;
        macc.Conectar();
        try {
            mExi = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                    + "from inv_tbl_historico "
                    + "where "
                    + "cod_art=" + ent_cod_ite + " "
                    + "and cod_pai = " + cod_pai + " "
                    + "and cod_bod=" + tra_bodega + " "
                    + "and det_lot='" + tra_lote.trim() + "' "
                    + "and  fec_tra <= STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                    + "order by fec_tra desc,"
                    + "ord_dia desc "
                    + "limit 1;");
        } catch (Exception e) {
            mExi = 0.0;
        }
        macc.Desconectar();
        Double exigrid = 0.0;
        if (!entregadetalles.isEmpty()) {
            for (int i = 0; i < entregadetalles.size(); i++) {
                if (tra_bodega.equals(entregadetalles.get(i).getCod_bod())
                        && ent_cod_ite.equals(entregadetalles.get(i).getCod_art())
                        && tra_lote.equals(entregadetalles.get(i).getDet_lot())) {
                    exigrid = exigrid + Double.valueOf(entregadetalles.get(i).getDet_can());
                }
            }
        }

        Double mMax = 0.0;
        macc.Conectar();
        try {
            mMax = Double.valueOf(macc.strQuerySQLvariable("select ifnull(pun_max,0.0) "
                    + "from inv_cat_pun_ale "
                    + "where cod_bod = " + tra_bodega + " "
                    + "and cod_art = " + ent_cod_ite
                    + ";"));
        } catch (Exception e) {
            mMax = 0.0;
        }
        macc.Desconectar();
        if (mMax > 0.0) {
            if (mExi + tra_cantidad * mFactor + exigrid > mMax) {
                addMessage("Validar", "La Cantidad Ingresada Sobrepasa el Máximo permitido en esta Bodega", 2);
                return false;
            }
        }

        if (mvalidar) {
            tra_canconver = tra_cantidad;
            tra_cantidad = tra_cantidad * mFactor;
        }

        return mvalidar;
    }

    public void agregardetalleEntrega() {
        try {
            if (validardetalleEntrega()) {
                macc.Conectar();
                int correlativo = 0, insert = 0;
                Double mcantidad;

                for (int i = 0; i < entregadetalles.size(); i++) {

                    if (ent_cod_ite.equals(entregadetalles.get(i).getCod_art())
                            && tra_bodega.equals(entregadetalles.get(i).getCod_bod())
                            && tra_ubicacion.equals(entregadetalles.get(i).getCod_ubi())
                            && tra_lote.equals(entregadetalles.get(i).getDet_lot())
                            && tra_vencimiento.equals(entregadetalles.get(i).getFec_ven())
                            && tra_unimed.equals(entregadetalles.get(i).getUni_med_con())) {

                        mcantidad = Double.valueOf(entregadetalles.get(i).getDet_can()) + tra_cantidad;

                        insert = 1;
                        entregadetalles.set(i, new CatEntregas(
                                entregadetalles.get(i).getCod_tra(),
                                entregadetalles.get(i).getCod_det(),
                                entregadetalles.get(i).getCod_bod(),
                                entregadetalles.get(i).getCod_ubi(),
                                entregadetalles.get(i).getCod_art(),
                                entregadetalles.get(i).getDet_lot(),
                                entregadetalles.get(i).getFec_ven(),
                                String.valueOf(mcantidad),
                                entregadetalles.get(i).getNombod(),
                                entregadetalles.get(i).getNomubi(),
                                entregadetalles.get(i).getNomart(),
                                String.valueOf(Double.valueOf(entregadetalles.get(i).getDet_can_con()) + tra_canconver),
                                entregadetalles.get(i).getUni_med_con(),
                                entregadetalles.get(i).getDet_cos(),
                                entregadetalles.get(i).getCodrefart(),
                                entregadetalles.get(i).getNomunimed(),
                                entregadetalles.get(i).getSol_mae(),
                                entregadetalles.get(i).getSol_det()
                        ));
                    }

                    if (Integer.valueOf(entregadetalles.get(i).getCod_det()) > correlativo) {
                        correlativo = Integer.valueOf(entregadetalles.get(i).getCod_det());
                    }
                }

                if (insert == 0) {
                    entregadetalles.add(new CatEntregas(
                            "",
                            String.valueOf(correlativo + 1),
                            tra_bodega,
                            tra_ubicacion,
                            ent_cod_ite,
                            tra_lote.trim(),
                            tra_vencimiento,
                            String.valueOf(tra_cantidad),
                            macc.strQuerySQLvariable("select nom_bod from inv_cat_bodegas where cod_pai = " + cod_pai + " and id_bod =" + tra_bodega + ";"),
                            macc.strQuerySQLvariable("select nom_ubi from inv_cat_ubicaciones where id_ubi = " + tra_ubicacion + " and cod_bod =" + tra_bodega + ";"),
                            macc.strQuerySQLvariable("select det_nom from inv_cat_articulo where id_art =" + ent_cod_ite + ";"),
                            String.valueOf(tra_canconver),
                            tra_unimed,
                            String.valueOf(tra_costototal),
                            macc.strQuerySQLvariable("select cod_art from inv_cat_articulo where id_art =" + ent_cod_ite + ";"),
                            macc.strQuerySQLvariable("select nom_emb from inv_cat_embalaje where id_emb =" + tra_unimed + ";"),
                            eent_cod_mae,
                            ent_cod_det
                    ));
                }
                macc.Desconectar();

                catdetallesent = null;

                ent_cod_det = "";
                ent_cod_ite = "0";
                ent_des_ite = "";
                ent_can_sol = 0.0;
                ent_can_ent = 0.0;
                ent_can_pen = 0.0;
                ent_fec_cie = "";
                ent_cos_uni = 0.0;
                ent_can_sol_con = 0.0;
                ent_cod_uni_med = "0";
                ent_det_sta = "0";

                dfechavenc = null;

                tra_bodega = "0";
                tra_ubicacion = "0";
                tra_lote = "";
                tra_vencimiento = "";
                tra_unimed = "0";
                tra_cantidad = 0.0;
                tra_costototal = 0.0;
                tra_canconver = 0.0;

                cattransaccionesdetalle = null;

            }
        } catch (Exception e) {
            System.out.println("Error en Agregar Detalle InvCompras." + e.getMessage());
        }
    }

    public void eliminardetalleEntrega() {
        if ("".equals(tra_orden)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover", 2);
        } else {
            for (int i = 0; i < entregadetalles.size(); i++) {
                if (tra_orden.equals(entregadetalles.get(i).getCod_det())) {
                    entregadetalles.remove(i);
                }
            }

            catdetallesent = null;

            ent_cod_det = "";
            ent_cod_ite = "0";
            ent_des_ite = "";
            ent_can_sol = 0.0;
            ent_can_ent = 0.0;
            ent_can_pen = 0.0;
            ent_fec_cie = "";
            ent_cos_uni = 0.0;
            ent_can_sol_con = 0.0;
            ent_cod_uni_med = "0";
            ent_det_sta = "0";

            dfechavenc = null;

            tra_bodega = "0";
            tra_ubicacion = "0";
            tra_lote = "";
            tra_vencimiento = "";
            tra_unimed = "0";
            tra_cantidad = 0.0;
            tra_costototal = 0.0;
            tra_canconver = 0.0;

            cattransaccionesdetalle = null;

        }
    }

    public boolean validardatosEntrega() {
        boolean mvalidar = true;
        if ("0".equals(cod_pai)) {
            mvalidar = false;
            addMessage("Validar", "Debe Seleccionar un País", 2);
        }
        if ("0".equals(tra_tipooperacion)) {
            mvalidar = false;
            addMessage("Validar", "Debe Seleccionar un Tipo de Operación", 2);
        }
        if (entregadetalles.isEmpty()) {
            mvalidar = false;
            addMessage("Validar", "Debe Agregar al menos un Detalle", 2);
        }

        return mvalidar;
    }

    public void guardarEntrega() {
        if (validardatosEntrega()) {
            if (guardarmovimientoEntrega()) {
                addMessage("Guardar Movimiento de Inventario", "Información almacenada con Éxito", 1);
                limpiar();
            }
        }
    }

    public boolean guardarmovimientoEntrega() {
        String mQuery = "";
        String cod_tra = "", cor_mov = "", mValores = "", mhistoria = "", embalaje;
        ResultSet resVariable;
        try {
            macc.Conectar();

            mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_transacciones;";
            cod_tra = macc.strQuerySQLvariable(mQuery);

            cor_mov = macc.strQuerySQLvariable("select ifnull(max(cor_mov),0) + 1 as cod from inv_tbl_transacciones "
                    + "where tip_mov=" + tra_tipooperacion + " and cod_pai=" + cod_pai + " and year(fec_tra) = year(str_to_date('" + tra_fectra + "','%d/%m/%Y'));");

            mQuery = "insert into inv_tbl_transacciones (cod_tra,cod_pai,fec_tra,flg_ing_sal,tip_mov,doc_tra,ord_com,"
                    + "cod_cli_pro,det_obs,cod_usu,flg_anu,cod_esp,cor_mov,cod_are,obs_anu,usu_edi,fec_edi,det_pol,tra_anu) "
                    + "values (" + cod_tra + "," + cod_pai + ",str_to_date('" + tra_fectra + "','%d/%m/%Y'),'false'," + tra_tipooperacion
                    + ",'','" + eent_qbo + "'," + eent_cod_pro + ",'" + tra_observaciones + "'," + cbean.getCod_usu() + ",0,0," + cor_mov
                    + ",0,''," + cbean.getCod_usu() + ",now(),'" + tra_poliza + "',0);";
            macc.dmlSQLvariable(mQuery);

            int mCorrela = 1, ord_dia = 0;
            int id_tra = 0;
            Double cos_uni, cos_pro, exi_ant, exi_act, exi_act_lot, exi_ant_tot, exi_act_tot;
            for (int i = 0; i < entregadetalles.size(); i++) {
                cos_uni = 0.0;
                cos_pro = 0.0;
                exi_ant = 0.0;
                exi_act = 0.0;
                exi_act_lot = 0.0;
                exi_ant_tot = 0.0;
                exi_act_tot = 0.0;

                mValores = mValores + ",(" + cod_tra + "," + mCorrela + "," + entregadetalles.get(i).getCod_bod() + "," + entregadetalles.get(i).getCod_ubi() + ","
                        + entregadetalles.get(i).getCod_art() + ",'" + entregadetalles.get(i).getDet_lot() + "',str_to_date('" + entregadetalles.get(i).getFec_ven() + "','%d/%m/%Y'),"
                        + entregadetalles.get(i).getDet_can() + "," + entregadetalles.get(i).getDet_cos() + "," + entregadetalles.get(i).getDet_can_con()
                        + "," + entregadetalles.get(i).getUni_med_con() + ")";

                //*************** Tratamiento Histórico de movimientos  *********************************************************************** 
                //****************************************************************************************************************************
                //*********************** CORRELATIVO HISTORIA *******************************************************************************
                //****************************************************************************************************************************
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_historico;";
                id_tra = Integer.valueOf(macc.strQuerySQLvariable(mQuery));

                //****************************************************************************************************************************
                //*********************** ORDEN DIARIO ***************************************************************************************
                //****************************************************************************************************************************
                mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                        + "where fec_tra = str_to_date('" + tra_fectra + "','%d/%m/%Y') "
                        + "AND cod_pai = " + cod_pai + " "
                        + "AND cod_art =" + entregadetalles.get(i).getCod_art() + ";";
                ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));

                //****************************************************************************************************************************
                //*********************** COSTO UNITARIO *************************************************************************************
                //****************************************************************************************************************************
                cos_uni = Double.valueOf(entregadetalles.get(i).getDet_cos()) / Double.valueOf(entregadetalles.get(i).getDet_can());

                //****************************************************************************************************************************
                //*********************** COSTO PROMEDIO Y EXISTENCIA ANTERIOR ***************************************************************
                //****************************************************************************************************************************
                if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art=" + entregadetalles.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra <= STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y');") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;

                } else if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + entregadetalles.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra < STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y');") < 1
                        && macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y')"
                                + "and flg_ing_sal = 'false' "
                                + ";") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else {
                    //************* Costo Promedio y Existencia anteriores  **************

                    cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_tot * cos_pro),0) + "
                            + (Double.valueOf(entregadetalles.get(i).getDet_can()) * cos_uni) + ")"
                            + "/(IFNULL(exi_act_tot,0) + " + entregadetalles.get(i).getDet_can() + ") as Cpromedio "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art = " + entregadetalles.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and  fec_tra <= STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                            + "order by fec_tra desc, "
                            + "ord_dia desc "
                            + "limit 1;");

                    //***********  Existencia Anterior **************************************************
                    exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + entregadetalles.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod=" + entregadetalles.get(i).getCod_bod() + " "
                            + "and  fec_tra <= STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //********** Existencia Anterior Lote **********************************************
                    exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + entregadetalles.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod=" + entregadetalles.get(i).getCod_bod() + " "
                            + "and det_lot='" + entregadetalles.get(i).getDet_lot().trim() + "' "
                            + "and  fec_tra <= STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + entregadetalles.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and  fec_tra <= STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //******************* Tratamiento de Existencias **************************************
                exi_act = exi_ant + Double.valueOf(entregadetalles.get(i).getDet_can());
                exi_act_lot = exi_act_lot + Double.valueOf(entregadetalles.get(i).getDet_can());
                exi_act_tot = exi_ant_tot + Double.valueOf(entregadetalles.get(i).getDet_can());

                mhistoria = "(" + id_tra + ",'false',str_to_date('" + tra_fectra + "','%d/%m/%Y'),"
                        + ord_dia + "," + cod_tra + "," + mCorrela
                        + "," + cod_pai + "," + entregadetalles.get(i).getCod_bod() + ","
                        + entregadetalles.get(i).getCod_ubi() + "," + entregadetalles.get(i).getCod_art() + ","
                        + entregadetalles.get(i).getDet_can() + "," + exi_ant + "," + exi_act + "," + cos_uni + ","
                        + cos_pro + ",'" + entregadetalles.get(i).getDet_lot().trim() + "'," + exi_act_lot + "," + exi_ant_tot + "," + exi_act_tot + ")";
                mQuery = "insert into inv_tbl_historico (cod_tra,flg_ing_sal,fec_tra,ord_dia,cod_mov,det_mov,cod_pai,cod_bod,"
                        + "cod_ubi,cod_art,det_can,exi_ant_bod,exi_act_bod,cos_uni,cos_pro,det_lot,exi_act_lot,exi_ant_tot,exi_act_tot) VALUES "
                        + mhistoria + ";";
                macc.dmlSQLvariable(mQuery);

                //***********************************************************************************************************
                //*************************** MODIFICACIÓN DE REGISTROS POSTERIORES POR PAÍS ********************************
                //***********************************************************************************************************
                Double contasiguientes;

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
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
                                + "fec_tra > STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc, "
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
                                cos_pro = ((cos_pro * exi_act_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_act_tot + (seriehistorica1.getDet_can())));
                                exi_act_tot = exi_act_tot + (seriehistorica1.getDet_can());
                            } else {
                                exi_ant_tot = exi_act_tot;
                                exi_act_tot = exi_act_tot - (seriehistorica1.getDet_can());
                                if (exi_act_tot < 0.0) {
                                    exi_act_tot = 0.0;
                                }
                            }
                            mQuery = "update inv_tbl_historico set "
                                    + "cos_pro = " + cos_pro + ","
                                    + "exi_ant_tot = " + exi_ant_tot + ","
                                    + "exi_act_tot = " + exi_act_tot + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_tra()
                                    + ";";
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
                        + "fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
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
                                + "fec_tra > STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
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
                        + "fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                        + "and det_lot ='" + entregadetalles.get(i).getDet_lot() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + entregadetalles.get(i).getDet_lot() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + entregadetalles.get(i).getDet_lot() + "' "
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
                                + "fec_tra > STR_TO_DATE('" + tra_fectra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + entregadetalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + entregadetalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + entregadetalles.get(i).getDet_lot() + "' "
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
                mQuery = "update inv_sol_det set "
                        + "det_can_pen = det_can_pen - " + entregadetalles.get(i).getDet_can() + ", "
                        + "det_can_ent = det_can_ent + " + entregadetalles.get(i).getDet_can() + " "
                        + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + " "
                        + "and cod_det = " + entregadetalles.get(i).getSol_det() + ";";
                macc.dmlSQLvariable(mQuery);

                if ("0".equals(macc.strQuerySQLvariable("select ifnull(det_can_pen,0) "
                        + "from inv_sol_det where cod_mae=" + entregadetalles.get(i).getSol_mae() + " and cod_det=" + entregadetalles.get(i).getSol_det() + ";"))) {
                    mQuery = "update inv_sol_det set "
                            + "det_sta = 2 "
                            + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + " "
                            + "and cod_det = " + entregadetalles.get(i).getSol_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update inv_sol_det set "
                            + "det_sta = 1 "
                            + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + " "
                            + "and cod_det = " + entregadetalles.get(i).getSol_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                mQuery = "insert into inv_sol_rec (cod_rec,cod_mae,det_mae,fec_rec,det_can,det_can_con,cod_uni_med,flg_usu_alm,cod_usu_rec,cod_his,det_sta) VALUES ("
                        + macc.strQuerySQLvariable("select ifnull(max(cod_rec),0) + 1 as cod from inv_sol_rec;")
                        + "," + entregadetalles.get(i).getSol_mae() + "," + entregadetalles.get(i).getSol_det() + ",null," + entregadetalles.get(i).getDet_can() + ","
                        + entregadetalles.get(i).getDet_can_con() + "," + entregadetalles.get(i).getUni_med_con() + ",null," + cbean.getCod_usu() + "," + id_tra + ",0);";
                macc.dmlSQLvariable(mQuery);

                if (Objects.equals(macc.doubleQuerySQLvariable("select count(cod_det) as conta "
                        + "from inv_sol_det where cod_mae = " + entregadetalles.get(i).getSol_mae()
                        + " and det_sta in (3);"), macc.doubleQuerySQLvariable("select count(cod_det) as conta "
                                + "from inv_sol_det where cod_mae = " + entregadetalles.get(i).getSol_mae() + ";"))) {
                    mQuery = "update inv_sol_mae set "
                            + "det_sta = 1 "
                            + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + ";";

                    macc.dmlSQLvariable(mQuery);
                } else if (Objects.equals(macc.doubleQuerySQLvariable("select count(cod_mae) as conta "
                        + "from inv_sol_det where cod_mae = " + entregadetalles.get(i).getSol_mae()
                        + " and det_sta in (2,3);"), macc.doubleQuerySQLvariable("select count(cod_mae) as conta "
                                + "from inv_sol_det where cod_mae = " + entregadetalles.get(i).getSol_mae() + ";"))) {
                    mQuery = "update inv_sol_mae set "
                            + "det_sta = 3 "
                            + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update inv_sol_mae set "
                            + "det_sta = 2 "
                            + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                //**************** Detalle Lotes Compras ****************************
                mQuery = "update inv_sol_det_lot set "
                        + "det_can_pen = (det_can_pen - " + entregadetalles.get(i).getDet_can() + " ) "
                        + "where cod_mae = " + entregadetalles.get(i).getSol_mae() + " "
                        + "and cod_det = " + entregadetalles.get(i).getSol_det() + " "
                        + "and ucase(det_lot) = '" + entregadetalles.get(i).getDet_lot().toUpperCase().trim() + "' "
                        + ";";
                macc.dmlSQLvariable(mQuery);

                mCorrela = mCorrela + 1;

            }
            mQuery = "insert into inv_tbl_transacciones_det (cod_tra,cod_det,cod_bod,cod_ubi,cod_art,det_lot,fec_ven,det_can,det_cos,det_can_con,uni_med_con) VALUES " + mValores.substring(1) + ";";
            macc.dmlSQLvariable(mQuery);
            macc.Desconectar();

            return true;

        } catch (Exception e) {
            addMessage("Guardar Movimiento de Inventario", "Error al momento de Guardar la Información." + e.getMessage(), 2);
            System.out.println("Error en Guardar Movimiento de Inventario Productos InvCompras. " + e.getMessage() + " Query: " + mQuery + " mValores: " + mValores);
            return false;
        }

    }

    public void eliminarsolicitud() {
        macc.Conectar();
        if ("".equals(eent_cod_mae) || "0".equals(eent_cod_mae)) {
            addMessage("Eliminar", "Debe Seleccionar una Solicitud para remover", 2);
        } else if ("0".equals(macc.strQuerySQLvariable("select det_sta from inv_sol_mae where cod_mae =" + eent_cod_mae + ";"))) {
            macc.dmlSQLvariable("delete from inv_sol_det_lot where cod_mae = " + eent_cod_mae + ";");
            macc.dmlSQLvariable("delete from inv_sol_det where cod_mae = " + eent_cod_mae + ";");
            macc.dmlSQLvariable("delete from inv_sol_mae where cod_mae = " + eent_cod_mae + ";");
            limpiar();
        } else {
            addMessage("Eliminar", "Esta solicitud ya ha iniciado el proceso de suministro", 2);
        }
        macc.Desconectar();
    }

    public void onRowSelectEncEnt(SelectEvent event) {
        det_lot_boolean = "true";

        eent_cod_mae = ((CatSolMae) event.getObject()).getCod_mae();
        eent_cod_alt = ((CatSolMae) event.getObject()).getCod_alt();
        eent_cod_pai = ((CatSolMae) event.getObject()).getCod_pai();
        eent_cod_pro = ((CatSolMae) event.getObject()).getCod_pro();
        eent_fec_sol = ((CatSolMae) event.getObject()).getFec_sol();
        eent_cod_usu_sol = ((CatSolMae) event.getObject()).getCod_usu_sol();
        eent_cod_usu_apr = ((CatSolMae) event.getObject()).getCod_usu_apr();
        eent_cod_usu_rec = ((CatSolMae) event.getObject()).getCod_usu_rec();
        eent_det_obs = ((CatSolMae) event.getObject()).getDet_obs();
        eent_det_sta = ((CatSolMae) event.getObject()).getDet_sta();
        eent_fec_cie = ((CatSolMae) event.getObject()).getFec_cie();
        eent_qbo = ((CatSolMae) event.getObject()).getCod_alt();

        llenarDetalleEnt();
    }

    public void onRowSelectDetEnt(SelectEvent event) {
        det_lot_boolean = "false";

        ent_cod_det = ((CatSolDet) event.getObject()).getCod_det();
        ent_cod_ite = ((CatSolDet) event.getObject()).getCod_ite();
        ent_des_ite = ((CatSolDet) event.getObject()).getDes_ite();
        ent_can_sol = ((CatSolDet) event.getObject()).getDet_can_sol();
        ent_can_ent = ((CatSolDet) event.getObject()).getDet_can_ent();
        ent_can_pen = ((CatSolDet) event.getObject()).getDet_can_pen();
        ent_fec_cie = ((CatSolDet) event.getObject()).getFec_cie();
        ent_cos_uni = ((CatSolDet) event.getObject()).getCos_uni();
        ent_can_sol_con = ((CatSolDet) event.getObject()).getDet_can_con();
        ent_cod_uni_med = ((CatSolDet) event.getObject()).getCod_uni_med();
        ent_det_sta = ((CatSolDet) event.getObject()).getDet_sta();

        tra_cantidad = ent_can_pen;
        macc.Conectar();
        tra_unimed = macc.strQuerySQLvariable("select det_emb from inv_cat_articulo where id_art = " + ent_cod_ite + ";");
        macc.Desconectar();

        llenarLotes();

    }

    public void onRowUnselectDetEnt() {
        det_lot_boolean = "true";
    }

    public void onRowSelectEntregado(SelectEvent event) {
        det_lot_boolean = "true";

        tra_orden = ((CatTransaccionesDetalle) event.getObject()).getCod_det();

    }

    public void onSelectMovimiento() {
        if ("0".equals(tra_tipooperacion)) {
            tra_correlaoperacion = "";
        } else {
            macc.Conectar();
            tra_correlaoperacion = macc.strQuerySQLvariable("select case ifnull(det_abr,'') "
                    + "when '' then right(year(str_to_date('" + tra_fectra + "','%d/%m/%Y')),2) "
                    + "else concat(det_abr,right(year(str_to_date('" + tra_fectra + "','%d/%m/%Y')),2),'-') "
                    + "end as abr from inv_cat_mov where id_mov=" + tra_tipooperacion + ";")
                    + macc.strQuerySQLvariable("select LPAD(ifnull(max(cor_mov),0) + 1, 4, '0') from inv_tbl_transacciones "
                            + "where cod_pai=" + cod_pai + " and tip_mov= " + tra_tipooperacion + " and year(fec_tra) = year(str_to_date('" + tra_fectra + "','%d/%m/%Y'));");
            macc.Desconectar();
        }
    }

    public void seleccionarbodega() {
        tra_ubicacion = "0";
        tra_lote = "";
        tra_vencimiento = "";
        dfechavenc = null;
        llenarUbicaciones();
        //llenarLotes();
    }

    public void onselectlote() {
        if (dfechavenc == null) {
            tra_vencimiento = "";
        }
        if (!"".equals(tra_lote)) {
            macc.Conectar();
            tra_vencimiento = macc.strQuerySQLvariable("select case ifnull(det.fec_ven,'') when '' then '' else date_format(det.fec_ven,'%d/%m/%Y') end as fecven "
                    + "from inv_tbl_transacciones as mae "
                    + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                    + "where mae.cod_pai=" + cod_pai + " and det.cod_art=" + ent_cod_ite + " and det.det_lot='" + tra_lote + "' order by det.fec_ven desc limit 1;");

            if (tra_vencimiento == null || "".equals(tra_vencimiento)) {
                tra_vencimiento = macc.strQuerySQLvariable("select case date_format(fec_ven,'%d/%m/%Y') when '00/00/0000' then '' else date_format(fec_ven,'%d/%m/%Y') end as fecven "
                        + "from inv_sol_det_lot as dlo "
                        + "left join inv_sol_mae as mae on dlo.cod_mae = mae.cod_mae "
                        + "left join inv_sol_det as det on mae.cod_mae = det.cod_mae and dlo.cod_det = det.cod_det "
                        + "where "
                        + "mae.cod_pai = " + cod_pai + " "
                        + "and det.cod_ite = " + ent_cod_ite + " "
                        + "and dlo.det_lot='" + tra_lote + "' order by fec_ven desc limit 1;");
            }

            macc.Desconectar();

            if (tra_vencimiento == null) {
                tra_vencimiento = "";
            }

            if ("".equals(tra_vencimiento)) {
                dfechavenc = null;
            } else {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    dfechavenc = format.parse(tra_vencimiento);
                } catch (Exception e) {

                }
            }
        }
    }

    //************** Detalle Lotes *********************
    public void iniciarDetalleLotes() {
        boolean mvalidar = true;

        if (eent_cod_mae == null || eent_cod_mae.equals("") || eent_cod_mae.equals("0")) {
            addMessage("Validar", "Debe Seleccionar una Solicitud", 2);
            mvalidar = false;
        }

        if (ent_cod_det == null || ent_cod_det.equals("") || ent_cod_det.equals("0")) {
            addMessage("Validar", "Debe Seleccionar un Detalle de Solicitud", 2);
            mvalidar = false;
        }

        if (mvalidar) {
            det_lot_det_lot = "";
            det_lot_fec_ven = "";
            det_lot_can_lot = 0.0;
            ddetlotfecven = null;

            llenarDetalleLotes();

            RequestContext.getCurrentInstance().update("frmDetLotesCom");
            RequestContext.getCurrentInstance().execute("PF('wDetLotesCom').show()");

        }

    }

    public void cerrarDetalleLotes() {
        catdetlotcomp = null;
        detallelotes.clear();
    }

    public void llenarDetalleLotes() {
        String mQuery = "";
        try {
            catdetlotcomp = null;
            detallelotes.clear();

            mQuery = "select cod_mae, cod_det, det_lot, case date_format(fec_ven,'%d/%m/%Y') when '00/00/0000' then '' else date_format(fec_ven,'%d/%m/%Y') end as fecven , det_can_pen "
                    + "from inv_sol_det_lot "
                    + "where cod_mae = " + eent_cod_mae + " and cod_det = " + ent_cod_det + " "
                    + "order by det_lot;";

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detallelotes.add(new CatDetLotComp(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getDouble(5)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado Detalle de Lotes en InvCompras. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardetalleLote() {
        boolean mvalidar = true;

        if (det_lot_fec_ven == null) {
            det_lot_fec_ven = "";
        }
        if (det_lot_det_lot == null) {
            det_lot_det_lot = "";
        }
        if (det_lot_can_lot == 0.0) {
            addMessage("Validar", "La Cantidad de Lote debe ser mayor que Cero", 2);
            return false;
        }

        if (!detallelotes.isEmpty()) {
            double mcantidad = 0.0;
            for (int i = 0; i < detallelotes.size(); i++) {
                mcantidad = mcantidad + detallelotes.get(i).getDet_can_pen();
            }
            if (det_lot_can_lot + mcantidad > ent_can_pen) {
                addMessage("Validar", "La Cantidad de Lote no puede ser mayor que la Cantidad Solicitada Pendiente", 2);
                return false;
            }
        } else if (det_lot_can_lot > ent_can_pen) {
            addMessage("Validar", "La Cantidad de Lote no puede ser mayor que la Cantidad Solicitada Pendiente", 2);
            return false;
        }

        macc.Conectar();
        if (!"0".equals(macc.strQuerySQLvariable("select count(mae.cod_pai) as fecven from inv_tbl_transacciones as mae "
                + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                + "where mae.cod_pai = " + cod_pai + " "
                + "and det.cod_art = " + ent_cod_ite + " "
                + "and ucase(det.det_lot) = '" + det_lot_det_lot.toUpperCase().trim() + "';"))) {

            String newfecven = macc.strQuerySQLvariable("select date_format(fec_ven,'%d/%m/%Y') as fecven from inv_tbl_transacciones as mae "
                    + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                    + "where mae.cod_pai = " + cod_pai + " "
                    + "and det.cod_art = " + ent_cod_ite + " "
                    + "and ucase(det.det_lot) = '" + det_lot_det_lot.toUpperCase().trim() + "' "
                    + "limit 1;");
            if (newfecven == null) {
                newfecven = "";
            }

            if (!det_lot_fec_ven.equals(newfecven)) {
                addMessage("Validar", "Este Lote para este Producto ya existe en el histórico con otra fecha de vencimiento", 2);
                return false;
            }

        }

        macc.Desconectar();

        return mvalidar;
    }

    public void agregardetalleLote() {
        try {
            if (validardetalleLote()) {
                int insert = 0;
                String strinsert = "";

                for (int i = 0; i < detallelotes.size(); i++) {

                    if (eent_cod_mae.equals(detallelotes.get(i).getCod_mae())
                            && ent_cod_det.equals(detallelotes.get(i).getCod_det())
                            && det_lot_det_lot.equals(detallelotes.get(i).getDet_lot())) {

                        insert = 1;
                        detallelotes.set(i, new CatDetLotComp(
                                detallelotes.get(i).getCod_mae(),
                                detallelotes.get(i).getCod_det(),
                                detallelotes.get(i).getDet_lot(),
                                detallelotes.get(i).getFec_ven(),
                                detallelotes.get(i).getDet_can_pen() + det_lot_can_lot
                        ));
                    }
                }
                if (insert == 0) {
                    detallelotes.add(new CatDetLotComp(
                            eent_cod_mae,
                            ent_cod_det,
                            det_lot_det_lot,
                            det_lot_fec_ven,
                            det_lot_can_lot
                    ));
                    macc.Conectar();
                    macc.dmlSQLvariable("insert into inv_sol_det_lot (cod_mae,cod_det,det_lot,fec_ven,det_can_pen) "
                            + "VALUES (" + eent_cod_mae + ","
                            + ent_cod_det + ",'" + det_lot_det_lot
                            + "',str_to_date('" + det_lot_fec_ven + "','%d/%m/%Y'),"
                            + det_lot_can_lot + ");");
                    macc.Desconectar();
                } else {
                    for (int i = 0; i < detallelotes.size(); i++) {
                        strinsert = strinsert + ",(" + detallelotes.get(i).getCod_mae()
                                + "," + detallelotes.get(i).getCod_det()
                                + ",'" + detallelotes.get(i).getDet_lot()
                                + "',str_to_date('" + detallelotes.get(i).getFec_ven()
                                + "','%d/%m/%Y')," + detallelotes.get(i).getDet_can_pen() + ") ";
                    }
                    macc.Conectar();
                    macc.dmlSQLvariable("delete from inv_sol_det_lot where cod_mae = " + eent_cod_mae + " and cod_det = " + ent_cod_det + ";");
                    macc.dmlSQLvariable("insert into inv_sol_det_lot (cod_mae,cod_det,det_lot,fec_ven,det_can_pen) VALUES " + strinsert.substring(1));
                    macc.Desconectar();
                }

                catdetlotcomp = null;

                det_lot_det_lot = "";
                det_lot_fec_ven = "";
                det_lot_can_lot = 0.0;
                ddetlotfecven = null;

                llenarLotes();

            }
        } catch (Exception e) {
            System.out.println("Error en Agregar Detalle Lotes InvCompras." + e.getMessage());
        }
    }

    public void eliminardetalleLote() {
        if ("".equals(det_lot_det_lot)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover", 2);
        } else {
            for (int i = 0; i < detallelotes.size(); i++) {
                if (det_lot_det_lot.equals(detallelotes.get(i).getDet_lot())) {
                    macc.Conectar();
                    macc.dmlSQLvariable("delete from inv_sol_det_lot where cod_mae = " + eent_cod_mae
                            + " and cod_det = " + ent_cod_det
                            + " and ucase(det_lot) = '" + detallelotes.get(i).getDet_lot().toUpperCase() + "';");
                    macc.Desconectar();
                    detallelotes.remove(i);

                }
            }
            det_lot_det_lot = "";
            det_lot_fec_ven = "";
            det_lot_can_lot = 0.0;
            ddetlotfecven = null;
            llenarLotes();
        }
    }

    public void onRowSelectDetalleLote(SelectEvent event) {
        det_lot_det_lot = ((CatDetLotComp) event.getObject()).getDet_lot();
        det_lot_fec_ven = ((CatDetLotComp) event.getObject()).getFec_ven();
        det_lot_can_lot = ((CatDetLotComp) event.getObject()).getDet_can_pen();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            ddetlotfecven = format.parse(det_lot_fec_ven);
        } catch (Exception ex) {
            ddetlotfecven = null;
        }

    }

    //*************** Funciones Generales **************
    public void onSelectProducto() {
        macc.Conectar();
        coduniori = macc.strQuerySQLvariable("select ifnull(det_emb,0) as codemb from inv_cat_articulo where id_art=" + det_cod_ite + ";");
        nomuniori = macc.strQuerySQLvariable("select ifnull(nom_emb,'') as nomemb from inv_cat_embalaje where id_emb = (select det_emb from inv_cat_articulo where id_art=" + det_cod_ite + ");");
        det_cod_uni_med = coduniori;
        macc.Desconectar();
    }

    public void dateSelectedSol(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_sol = format.format(date);
    }

    public void dateSelectedTransac(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        tra_fectra = format.format(date);
    }

    public void dateSelectedVenc(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        tra_vencimiento = format.format(date);
    }

    public void dateSelectedVencLot(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        det_lot_fec_ven = format.format(date);
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabsolgen":
                tabindex = "0";
                menabled = "false";
                det_lot_boolean = "true";
                break;
            case "tabsolent":
                tabindex = "1";
                menabled = "true";
                det_lot_boolean = "true";
                catdetallesent = null;
                break;
        }

    }

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

    //************** Getter y Setter *******************
    public CatSolMae getCatmaestro() {
        return catmaestro;
    }

    public void setCatmaestro(CatSolMae catmaestro) {
        this.catmaestro = catmaestro;
    }

    public List<CatSolMae> getMaestro() {
        return maestro;
    }

    public void setMaestro(List<CatSolMae> maestro) {
        this.maestro = maestro;
    }

    public CatSolDet getCatdetalles() {
        return catdetalles;
    }

    public void setCatdetalles(CatSolDet catdetalles) {
        this.catdetalles = catdetalles;
    }

    public List<CatSolDet> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CatSolDet> detalles) {
        this.detalles = detalles;
    }

    public List<CatListados> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<CatListados> proveedores) {
        this.proveedores = proveedores;
    }

    public List<CatListados> getProductos() {
        return productos;
    }

    public void setProductos(List<CatListados> productos) {
        this.productos = productos;
    }

    public List<CatListados> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<CatListados> unidades) {
        this.unidades = unidades;
    }

    public String getCod_mae() {
        return cod_mae;
    }

    public void setCod_mae(String cod_mae) {
        this.cod_mae = cod_mae;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
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

    public String getCod_usu_sol() {
        return cod_usu_sol;
    }

    public void setCod_usu_sol(String cod_usu_sol) {
        this.cod_usu_sol = cod_usu_sol;
    }

    public String getCod_usu_apr() {
        return cod_usu_apr;
    }

    public void setCod_usu_apr(String cod_usu_apr) {
        this.cod_usu_apr = cod_usu_apr;
    }

    public String getCod_usu_rec() {
        return cod_usu_rec;
    }

    public void setCod_usu_rec(String cod_usu_rec) {
        this.cod_usu_rec = cod_usu_rec;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_cod_det() {
        return det_cod_det;
    }

    public void setDet_cod_det(String det_cod_det) {
        this.det_cod_det = det_cod_det;
    }

    public String getDet_cod_ite() {
        return det_cod_ite;
    }

    public void setDet_cod_ite(String det_cod_ite) {
        this.det_cod_ite = det_cod_ite;
    }

    public String getDet_des_ite() {
        return det_des_ite;
    }

    public void setDet_des_ite(String det_des_ite) {
        this.det_des_ite = det_des_ite;
    }

    public Double getDet_can_sol() {
        return det_can_sol;
    }

    public void setDet_can_sol(Double det_can_sol) {
        this.det_can_sol = det_can_sol;
    }

    public Double getDet_can_ent() {
        return det_can_ent;
    }

    public void setDet_can_ent(Double det_can_ent) {
        this.det_can_ent = det_can_ent;
    }

    public Double getDet_can_pen() {
        return det_can_pen;
    }

    public void setDet_can_pen(Double det_can_pen) {
        this.det_can_pen = det_can_pen;
    }

    public Double getDet_cos_uni() {
        return det_cos_uni;
    }

    public void setDet_cos_uni(Double det_cos_uni) {
        this.det_cos_uni = det_cos_uni;
    }

    public String getDet_det_sta() {
        return det_det_sta;
    }

    public void setDet_det_sta(String det_det_sta) {
        this.det_det_sta = det_det_sta;
    }

    public String getRec_cod_rec() {
        return rec_cod_rec;
    }

    public void setRec_cod_rec(String rec_cod_rec) {
        this.rec_cod_rec = rec_cod_rec;
    }

    public Double getRec_det_can() {
        return rec_det_can;
    }

    public void setRec_det_can(Double rec_det_can) {
        this.rec_det_can = rec_det_can;
    }

    public String getRec_cod_usu_rec() {
        return rec_cod_usu_rec;
    }

    public void setRec_cod_usu_rec(String rec_cod_usu_rec) {
        this.rec_cod_usu_rec = rec_cod_usu_rec;
    }

    public String getRec_cod_his() {
        return rec_cod_his;
    }

    public void setRec_cod_his(String rec_cod_his) {
        this.rec_cod_his = rec_cod_his;
    }

    public String getRec_det_sta() {
        return rec_det_sta;
    }

    public void setRec_det_sta(String rec_det_sta) {
        this.rec_det_sta = rec_det_sta;
    }

    public Date getDfechasol() {
        return dfechasol;
    }

    public void setDfechasol(Date dfechasol) {
        this.dfechasol = dfechasol;
    }

    public Date getDfechacier() {
        return dfechacier;
    }

    public void setDfechacier(Date dfechacier) {
        this.dfechacier = dfechacier;
    }

    public Date getDfecharec() {
        return dfecharec;
    }

    public void setDfecharec(Date dfecharec) {
        this.dfecharec = dfecharec;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getCoduniori() {
        return coduniori;
    }

    public void setCoduniori(String coduniori) {
        this.coduniori = coduniori;
    }

    public String getNomuniori() {
        return nomuniori;
    }

    public void setNomuniori(String nomuniori) {
        this.nomuniori = nomuniori;
    }

    public Double getDet_can_sol_con() {
        return det_can_sol_con;
    }

    public void setDet_can_sol_con(Double det_can_sol_con) {
        this.det_can_sol_con = det_can_sol_con;
    }

    public String getDet_cod_uni_med() {
        return det_cod_uni_med;
    }

    public void setDet_cod_uni_med(String det_cod_uni_med) {
        this.det_cod_uni_med = det_cod_uni_med;
    }

    public CatSolMae getCatmaestroent() {
        return catmaestroent;
    }

    public void setCatmaestroent(CatSolMae catmaestroent) {
        this.catmaestroent = catmaestroent;
    }

    public List<CatSolMae> getMaestroent() {
        return maestroent;
    }

    public void setMaestroent(List<CatSolMae> maestroent) {
        this.maestroent = maestroent;
    }

    public CatSolDet getCatdetallesent() {
        return catdetallesent;
    }

    public void setCatdetallesent(CatSolDet catdetallesent) {
        this.catdetallesent = catdetallesent;
    }

    public List<CatSolDet> getDetallesent() {
        return detallesent;
    }

    public void setDetallesent(List<CatSolDet> detallesent) {
        this.detallesent = detallesent;
    }

    public List<CatListados> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<CatListados> operaciones) {
        this.operaciones = operaciones;
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

    public List<CatListados> getLotes() {
        return lotes;
    }

    public void setLotes(List<CatListados> lotes) {
        this.lotes = lotes;
    }

    public String getTra_tipooperacion() {
        return tra_tipooperacion;
    }

    public void setTra_tipooperacion(String tra_tipooperacion) {
        this.tra_tipooperacion = tra_tipooperacion;
    }

    public String getTra_correlaoperacion() {
        return tra_correlaoperacion;
    }

    public void setTra_correlaoperacion(String tra_correlaoperacion) {
        this.tra_correlaoperacion = tra_correlaoperacion;
    }

    public String getTra_observaciones() {
        return tra_observaciones;
    }

    public void setTra_observaciones(String tra_observaciones) {
        this.tra_observaciones = tra_observaciones;
    }

    public String getTra_bodega() {
        return tra_bodega;
    }

    public void setTra_bodega(String tra_bodega) {
        this.tra_bodega = tra_bodega;
    }

    public String getTra_ubicacion() {
        return tra_ubicacion;
    }

    public void setTra_ubicacion(String tra_ubicacion) {
        this.tra_ubicacion = tra_ubicacion;
    }

    public String getTra_lote() {
        return tra_lote;
    }

    public void setTra_lote(String tra_lote) {
        this.tra_lote = tra_lote;
    }

    public Double getTra_cantidad() {
        return tra_cantidad;
    }

    public void setTra_cantidad(Double tra_cantidad) {
        this.tra_cantidad = tra_cantidad;
    }

    public Double getTra_costototal() {
        return tra_costototal;
    }

    public void setTra_costototal(Double tra_costototal) {
        this.tra_costototal = tra_costototal;
    }

    public Date getDfechatransac() {
        return dfechatransac;
    }

    public void setDfechatransac(Date dfechatransac) {
        this.dfechatransac = dfechatransac;
    }

    public Date getDfechavenc() {
        return dfechavenc;
    }

    public void setDfechavenc(Date dfechavenc) {
        this.dfechavenc = dfechavenc;
    }

    public String getTra_unimed() {
        return tra_unimed;
    }

    public void setTra_unimed(String tra_unimed) {
        this.tra_unimed = tra_unimed;
    }

    public String getMenabled() {
        return menabled;
    }

    public void setMenabled(String menabled) {
        this.menabled = menabled;
    }

    public CatEntregas getCattransaccionesdetalle() {
        return cattransaccionesdetalle;
    }

    public void setCattransaccionesdetalle(CatEntregas cattransaccionesdetalle) {
        this.cattransaccionesdetalle = cattransaccionesdetalle;
    }

    public List<CatEntregas> getEntregadetalles() {
        return entregadetalles;
    }

    public void setEntregadetalles(List<CatEntregas> entregadetalles) {
        this.entregadetalles = entregadetalles;
    }

    public String getTra_poliza() {
        return tra_poliza;
    }

    public void setTra_poliza(String tra_poliza) {
        this.tra_poliza = tra_poliza;
    }

    public CatDetLotComp getCatdetlotcomp() {
        return catdetlotcomp;
    }

    public void setCatdetlotcomp(CatDetLotComp catdetlotcomp) {
        this.catdetlotcomp = catdetlotcomp;
    }

    public List<CatDetLotComp> getDetallelotes() {
        return detallelotes;
    }

    public void setDetallelotes(List<CatDetLotComp> detallelotes) {
        this.detallelotes = detallelotes;
    }

    public String getDet_lot_det_lot() {
        return det_lot_det_lot;
    }

    public void setDet_lot_det_lot(String det_lot_det_lot) {
        this.det_lot_det_lot = det_lot_det_lot;
    }

    public String getDet_lot_boolean() {
        return det_lot_boolean;
    }

    public void setDet_lot_boolean(String det_lot_boolean) {
        this.det_lot_boolean = det_lot_boolean;
    }

    public Double getDet_lot_can_lot() {
        return det_lot_can_lot;
    }

    public void setDet_lot_can_lot(Double det_lot_can_lot) {
        this.det_lot_can_lot = det_lot_can_lot;
    }

    public Date getDdetlotfecven() {
        return ddetlotfecven;
    }

    public void setDdetlotfecven(Date ddetlotfecven) {
        this.ddetlotfecven = ddetlotfecven;
    }

    public Double getEnt_can_pen() {
        return ent_can_pen;
    }

    public void setEnt_can_pen(Double ent_can_pen) {
        this.ent_can_pen = ent_can_pen;
    }

}
