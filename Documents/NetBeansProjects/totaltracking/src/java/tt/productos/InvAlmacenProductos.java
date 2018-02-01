package tt.productos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatBodegas;
import tt.general.CatListados;
import tt.general.CatMovimientosInv;
import tt.general.CatPaises;
import tt.general.CatProveedores;
import tt.general.CatTransacciones;
import tt.general.CatTransaccionesDetalle;
import tt.general.CatUbicaciones;
import tt.general.ListaCortaTransaccionExistencias;
import tt.general.Login;

@Named
@ConversationScoped

public class InvAlmacenProductos implements Serializable {

    private static final long serialVersionUID = 7997486764852638L;
    @Inject
    Login cbean;

    private Accesos macc;

    private List<CatPaises> paises;
    private List<CatProveedores> proveedores;
    private List<CatAreasInv> areas;
    private List<CatMovimientosInv> movimientos;
    private List<CatListados> especialistas;

    private List<CatArticulos> articulos;
    private List<CatListados> unidades;
    private List<CatBodegas> bodegas;
    private List<CatUbicaciones> ubicaciones;
    private List<CatLotes> lotes;

    private List<CatMovimientosInv> movimientostotal;

    private CatLotesVencimiento catlotesvencimiento;
    private List<CatLotesVencimiento> lotesvencimiento;

    private List<CatPaises> paisesbus;
    private List<CatBodegas> bodegasbus;
    private List<CatUbicaciones> ubicacionesbus;

    private CatTransacciones cattransacciones;
    private List<CatTransacciones> encabezado;
    private CatTransaccionesDetalle cattransaccionesdetalle;
    private List<CatTransaccionesDetalle> detalles;
    private List<CatTransaccionesDetalle> Querys;

    private List<ListaCortaTransaccionExistencias> hisexi;

    private String cod_tra, cod_pai, fec_tra, flg_ing_sal, tip_mov, doc_tra, ord_com, cod_cli_pro, det_obs, cod_usu, flg_anu, nompai, nommov, nomclipro;
    private String cod_esp, cor_mov, cod_are, obs_anu, det_pol, otr_sol;

    private String cod_det, cod_bod, cod_ubi, cod_art, det_lot, fec_ven, nombod, nomubi, nomart, uni_med_con;
    private Double det_can, det_cos, det_can_con;

    private String anu_tip_mov;

    private String tra_tip_mov, tra_cod_bod;

    private String ven_cod_bod;

    private String boolinout, booleditable, fecbus, idbus, movbus, paibus, bodbus,
            ubibus, probus, lotbus, chklot, chkfecven, titulo, img_pro, nomprod, unimedori,
            nombreunidad, tipoaccion;
    private Date mfecha, mfechaven;

    private String editfecvennomart;
    private Date datefecvenedit;

    private int tipoInicio;

    private String mheight;

    private static DefaultStreamedContent mimagen;

    public InvAlmacenProductos() {
    }

    //****************************************** Inicio ******************************************
    public void iniciarventana() {

        tipoInicio = 0;
        macc = new Accesos();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfecha = Date.from(Instant.now());
        mfechaven = null;

        cod_tra = "";
        cod_pai = cbean.getCod_pai();
        fec_tra = format.format(mfecha);
        flg_ing_sal = "false";
        tip_mov = "0";
        doc_tra = "";
        ord_com = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        nompai = "";
        nommov = "";
        nomclipro = "";
        cod_esp = "0";
        cor_mov = "";
        cod_are = "0";
        obs_anu = "";
        det_pol = "";
        otr_sol = "";

        cod_det = "";
        cod_bod = "0";
        cod_ubi = "0";
        cod_art = "0";
        det_lot = "";
        fec_ven = "";
        det_can = 0.0;
        det_cos = 0.0;
        nombod = "";
        nomubi = "";
        nomart = "";
        uni_med_con = "0";
        unimedori = "0";

        titulo = "Proveedor";
        boolinout = "false";
        booleditable = "true";
        chkfecven = "false";
        img_pro = "";
        nomprod = "";
        nombreunidad = "";

        mimagen = null;
        mheight = "260";

        paises = new ArrayList<>();
        proveedores = new ArrayList<>();
        movimientos = new ArrayList<>();
        movimientostotal = new ArrayList<>();
        especialistas = new ArrayList<>();
        areas = new ArrayList<>();

        lotes = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        bodegas = new ArrayList<>();
        articulos = new ArrayList<>();
        unidades = new ArrayList<>();

        cattransacciones = new CatTransacciones();
        encabezado = new ArrayList<>();

        cattransaccionesdetalle = new CatTransaccionesDetalle();
        detalles = new ArrayList<>();
        hisexi = new ArrayList<>();
        Querys = new ArrayList<>();

        paisesbus = new ArrayList<>();
        bodegasbus = new ArrayList<>();
        ubicacionesbus = new ArrayList<>();

        catlotesvencimiento = new CatLotesVencimiento();
        lotesvencimiento = new ArrayList<>();

        llenarPaises();
        llenarProveedores();
        llenarMovimientos();
        llenarAreas();
        llenarEspecialistas();
        llenarUnidades();

        llenarBodegas();
        llenarArticulos();

    }

    public void nuevo() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfecha = Date.from(Instant.now());
        cod_tra = "";
        cod_pai = cbean.getCod_pai();
        fec_tra = format.format(mfecha);
        flg_ing_sal = "false";
        tip_mov = "0";
        doc_tra = "";
        ord_com = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        nompai = "";
        nommov = "";
        nomclipro = "";
        cod_esp = "0";
        cor_mov = "";
        cod_are = "0";
        obs_anu = "";
        det_pol = "";
        otr_sol = "";

        cod_det = "";
        cod_bod = "0";
        cod_ubi = "0";
        cod_art = "0";
        det_lot = "";
        fec_ven = "";
        det_can = 0.0;
        det_cos = 0.0;
        nombod = "";
        nomubi = "";
        nomart = "";
        uni_med_con = "0";
        unimedori = "0";
        tipoaccion = "insert";

        titulo = "Proveedor";
        boolinout = "false";
        booleditable = "true";
        chkfecven = "false";
        img_pro = "";
        nomprod = "";
        nombreunidad = "";

        mimagen = null;
        mheight = "260";

        cattransaccionesdetalle = null;
        detalles.clear();

        llenarMovimientos();
        llenarProveedores();

    }

    public void cerrarventana() {
        cod_tra = null;
        cod_pai = null;
        fec_tra = null;
        flg_ing_sal = null;
        tip_mov = null;
        doc_tra = null;
        ord_com = null;
        cod_cli_pro = null;
        det_obs = null;
        cod_usu = null;
        flg_anu = null;
        nompai = null;
        nommov = null;
        nomclipro = null;
        cod_esp = null;
        cor_mov = null;
        cod_are = null;
        obs_anu = null;
        det_pol = null;
        otr_sol = null;

        cod_det = null;
        cod_bod = null;
        cod_ubi = null;
        cod_art = null;
        det_lot = null;
        fec_ven = null;
        det_can = 0.0;
        det_cos = 0.0;
        nombod = null;
        nomubi = null;
        nomart = null;
        uni_med_con = null;
        unimedori = null;

        titulo = null;
        boolinout = null;
        booleditable = null;
        chkfecven = null;
        img_pro = null;
        nomprod = null;
        nombreunidad = null;

        mimagen = null;
        mheight = null;

        paises = null;
        proveedores = null;
        movimientos = null;
        movimientostotal = null;
        especialistas = null;
        areas = null;

        lotes = null;
        ubicaciones = null;
        bodegas = null;
        articulos = null;
        unidades = null;

        cattransacciones = null;
        encabezado = null;

        cattransaccionesdetalle = null;
        detalles = null;
        hisexi = null;
        Querys = null;

        paisesbus = null;
        bodegasbus = null;
        ubicacionesbus = null;

        catlotesvencimiento = null;
        lotesvencimiento = null;

        macc = null;

    }

    //************************** Llenar catálogos ****************************
    public void llenarMovimientos() {
        String mQuery = "", tipmov;
        try {
            if ("false".equals(flg_ing_sal)) {
                tipmov = "0";
            } else {
                tipmov = "1";
            }

            movimientos.clear();

            mQuery = "select id_mov, nom_mov, "
                    + "case flg_tip when '0' then 'Entrada' when '1' then 'Salida' end as flg_tip,"
                    + "cod_pai,det_abr "
                    + "from inv_cat_mov "
                    + "where flg_tip = " + tipmov + " "
                    + "and cod_pai = " + cod_pai + " "
                    + "order by id_mov;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                movimientos.add(new CatMovimientosInv(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Movimientos en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMovimientosTotal() {
        String mQuery = "";
        try {

            movimientostotal.clear();

            mQuery = "select id_mov, nom_mov, "
                    + "case flg_tip when '0' then 'Entrada' when '1' then 'Salida' end as flg_tip,"
                    + "cod_pai,det_abr "
                    + "from inv_cat_mov "
                    + "where cod_pai =" + cod_pai + " "
                    + "order by id_mov;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                movimientostotal.add(new CatMovimientosInv(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Movimientos Total en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarProveedores() {
        String mQuery = "";
        try {

            proveedores.clear();

            if ("false".equals(flg_ing_sal)) {
                mQuery = "select cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie,det_dir  "
                        + "from inv_cat_pro where cod_pai = " + cod_pai + " order by cod_pro;";
                titulo = "Proveedores";
            } else {
                mQuery = "select cod_cli,cod_pai,nom_cli,per_con,tel_con,det_mai,flg_pro_pie,det_dir  "
                        + "from inv_cat_cli where cod_pai = " + cod_pai + " order by cod_cli;";
                titulo = "Clientes";
            }

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                proveedores.add(new CatProveedores(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Proveedores en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAreas() {
        try {
            areas.clear();

            String mQuery = "select cod_are, nom_are "
                    + "from inv_cat_are order by cod_are;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                areas.add(new CatAreasInv(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Áreas en ManInventarioProductos. " + e.getMessage());
        }
    }

    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from inv_cat_pai order by cod_pai;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

            bodegas.clear();
            ubicaciones.clear();
            lotes.clear();
            proveedores.clear();
            cod_bod = "0";
            cod_ubi = "0";
            cod_art = "0";
            det_lot = "";
        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en ManInventarioProductos. " + e.getMessage());
        }
    }

    public void llenarEspecialistas() {
        String mQuery = "";
        especialistas.clear();
        try {
            if ("true".equals(flg_ing_sal)) {
                mQuery = "select usu.cod_usu, usu.det_nom "
                        + "from cat_usu as usu "
                        + "left join inv_cat_usu_esp as esp on usu.cod_usu = esp.cod_usu "
                        + "where usu.cod_pai = " + cod_pai + " "
                        + "and esp.flg_act_des = 1 "
                        + "order by usu.cod_usu;";
                ResultSet resVariable;

                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    especialistas.add(new CatListados(
                            resVariable.getString(1),
                            resVariable.getString(2)
                    ));
                }
                resVariable.close();
                macc.Desconectar();
            }

        } catch (Exception e) {
            System.out.println("Error en el llenado de Especiaistas en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUnidades() {
        String mQuery = "";
        unidades.clear();
        try {

            mQuery = "select id_emb, nom_emb "
                    + "from inv_cat_embalaje "
                    + "order by nom_emb;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
            System.out.println("Error en el llenado de Unidades de Medida en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {

            bodegas.clear();

            mQuery = "select id_bod, nom_bod,cod_pai "
                    + "from inv_cat_bodegas "
                    + "where cod_pai=" + cod_pai + " "
                    + "order by id_bod;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3), ""
                ));
            }
            resVariable.close();
            macc.Desconectar();

            ubicaciones.clear();
            lotes.clear();
            cod_ubi = "0";
            det_lot = "";
            mfechaven = null;
            chkfecven = "false";
        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en InvAlmacenProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select ubi.id_ubi,ubi.cod_bod,ubi.nom_ubi,bod.nom_bod "
                    + "from inv_cat_ubicaciones as ubi "
                    + "left join inv_cat_bodegas as bod on bod.id_bod = ubi.cod_bod "
                    + "where bod.cod_pai = " + cod_pai + " "
                    + "and ubi.cod_bod=" + cod_bod + " "
                    + "order by ubi.cod_bod,ubi.id_ubi;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new CatUbicaciones(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4), "", ""
                ));
            }
            resVariable.close();
            macc.Desconectar();

            lotes.clear();
            det_lot = "";
            mfechaven = null;
            chkfecven = "false";
        } catch (Exception e) {
            System.out.println("Error en el llenado de Ubicaciones en InvAlmacenProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarArticulos() {
        String mQuery = "";
        try {
            lotes.clear();
            articulos.clear();

            mQuery = "select art.id_art, art.cod_art, art.det_mar, art.det_fam, art.det_nom, art.det_des, art.det_lot, art.fec_ven, "
                    + "art.flg_ref, art.tem_ref, art.img_pro, art.det_emb, art.reg_san, emb.nom_emb as nomemb,art.pai_ori,art.cod_pai,cod_alt "
                    + "from inv_cat_articulo as art "
                    + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                    + "where art.cod_pai = " + cod_pai + " "
                    + "order by art.id_art,art.cod_art,art.det_emb; ";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                articulos.add(new CatArticulos(
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
                        resVariable.getString(16),
                        resVariable.getString(17)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Artículos en InvAlmacenProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarLotes() {
        try {
            String mQuery = "";
            lotes.clear();

            if (flg_ing_sal.equals("false")) {
                mQuery = "select t.lote from (select distinct trim(det.det_lot) as lote  "
                        + "from inv_tbl_transacciones as mae  "
                        + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra  "
                        + "where  "
                        + "mae.cod_pai = " + cod_pai + " "
                        + "AND det.cod_bod = " + cod_bod + " "
                        + "AND det.cod_art = " + cod_art + " "
                        + ") as t "
                        + "where ("
                        + "  select exi_act_lot "
                        + "  FROM inv_tbl_historico "
                        + "  where cod_pai = " + cod_pai + " "
                        + "  and cod_bod = " + cod_bod + " "
                        + "  and cod_art = " + cod_art + " "
                        + "  and det_lot = t.lote "
                        + "  order by fec_tra desc, ord_dia desc limit 1 "
                        + ") > 0 "
                        + "order by t.lote asc "
                        + ";";
            } else {
                mQuery = "select '' as lote union all "
                        + "(select t.lote from (select distinct trim(det.det_lot) as lote  "
                        + "from inv_tbl_transacciones as mae  "
                        + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra  "
                        + "where  "
                        + "mae.cod_pai = " + cod_pai + " "
                        + "AND det.cod_bod = " + cod_bod + " "
                        + "AND det.cod_art = " + cod_art + " "
                        + ") as t "
                        + "where ("
                        + "  select exi_act_lot "
                        + "  FROM inv_tbl_historico "
                        + "  where cod_pai = " + cod_pai + " "
                        + "  and cod_bod = " + cod_bod + " "
                        + "  and cod_art = " + cod_art + " "
                        + "  and det_lot = t.lote "
                        + "  order by fec_tra desc, ord_dia desc limit 1 "
                        + ") > 0 "
                        + "order by t.lote asc) "
                        + ";";
            }

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lotes.add(new CatLotes(
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Lotes en ManInventarioProductos. " + e.getMessage());
        }
    }

    //************************* Detalle **************************************
    public boolean validardetalle() {
        boolean mvalidar = true;

        if (fec_ven.equals("00/00/0000")) {
            fec_ven = "";
        }
        if (fec_ven == null) {
            fec_ven = "";
        }
        if (det_lot == null) {
            det_lot = "";
        }
        if ("0".equals(cod_pai)) {
            addMessage("Validar Datos", "Debe Seleccionar un País", 2);
            return false;
        }
        if ("0".equals(cod_art)) {
            addMessage("Validar Datos", "Debe Seleccionar un Producto", 2);
            return false;
        }
        if ("0".equals(cod_bod)) {
            addMessage("Validar Datos", "Debe escoger una Bodega", 2);
            return false;
        }
        if (det_can <= 0.0) {
            addMessage("Validar Datos", "Debe Ingresar una Cantidad mayor que Cero", 2);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;
        if ("0".equals(uni_med_con)) {
            addMessage("Validar Datos", "Debe Seleccionar un Unidad de Medida", 2);
            return false;
        } else if (!unimedori.equals(uni_med_con)) {
            macc.Conectar();
            try {
                mFactor = macc.doubleQuerySQLvariable("select ifnull(val_ini/val_sal,0.0) as factor "
                        + "from inv_cat_embalaje_rel "
                        + "where uni_med_ent = " + unimedori + " "
                        + "and uni_med_sal = " + uni_med_con + ";");
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
                            + "and uni_med_ent = " + uni_med_con + ";");
                } catch (Exception e) {
                    mFactor = 0.0;
                }
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                addMessage("Validar Datos", "No Existe relación Definida Válida entre Unidades de Medida Seleccionadas", 2);
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
                    + "cod_art=" + cod_art + " "
                    + "and cod_pai = " + cod_pai + " "
                    + "and cod_bod=" + cod_bod + " "
                    + "and det_lot='" + det_lot.trim() + "' "
                    + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                    + "order by fec_tra desc,"
                    + "ord_dia desc "
                    + "limit 1;");
        } catch (Exception e) {
            mExi = 0.0;
        }
        macc.Desconectar();
        Double exigrid = 0.0;
        if (!detalles.isEmpty()) {
            for (int i = 0; i < detalles.size(); i++) {
                if (cod_bod.equals(detalles.get(i).getCod_bod())
                        && cod_art.equals(detalles.get(i).getCod_art())
                        && det_lot.equals(detalles.get(i).getDet_lot())) {
                    exigrid = exigrid + Double.valueOf(detalles.get(i).getDet_can());
                }
            }
        }

        if ("true".equals(flg_ing_sal)) {

            if ((det_can * mFactor) + exigrid > mExi) {
                if ("".equals(det_lot)) {
                    addMessage("Validar Datos", "La Cantidad Solicitada Sobrepasa las Existencias en esta Bodega", 2);
                } else {
                    addMessage("Validar Datos", "La Cantidad Solicitada del Lote " + det_lot + " Sobrepasa las Existencias en esta Bodega", 2);
                }
                return false;
            }

        } else {
            Double mMax = 0.0;
            macc.Conectar();
            try {
                mMax = Double.valueOf(macc.strQuerySQLvariable("select ifnull(pun_max,0.0) "
                        + "from inv_cat_pun_ale "
                        + "where cod_bod = " + cod_bod + " "
                        + "and cod_art = " + cod_art
                        + ";"));
            } catch (Exception e) {
                mMax = 0.0;
            }
            macc.Desconectar();
            if (mMax > 0.0) {
                if (mExi + det_can * mFactor + exigrid > mMax) {
                    addMessage("Validar Datos", "La Cantidad Ingresada Sobrepasa el Máximo permitido en esta Bodega", 2);
                    return false;
                }
            }

        }

        if (mvalidar) {
            det_can_con = det_can;
            det_can = det_can * mFactor;
        }

        return mvalidar;
    }

    public void agregardetalle() {
        try {
            if (validardetalle()) {
                macc.Conectar();
                int correlativo = 0, insert = 0;
                Double mcantidad;
                String costo, ord_ant;

                if ("false".equals(boolinout)) {
                    costo = String.valueOf(det_cos);
                } else {
                    costo = String.valueOf(det_can * macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + cod_art + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;"));
                }

                for (int i = 0; i < detalles.size(); i++) {

                    if (cod_art.equals(detalles.get(i).getCod_art())
                            && cod_bod.equals(detalles.get(i).getCod_bod())
                            && cod_ubi.equals(detalles.get(i).getCod_ubi())
                            && det_lot.equals(detalles.get(i).getDet_lot())
                            && fec_ven.equals(detalles.get(i).getFec_ven())
                            && uni_med_con.equals(detalles.get(i).getUni_med_con())) {

                        if ("false".equals(boolinout)) {
                            mcantidad = Double.valueOf(detalles.get(i).getDet_can()) + det_can;
                        } else {
                            mcantidad = Double.valueOf(detalles.get(i).getDet_can()) + det_can;
                        }

                        insert = 1;
                        detalles.set(i, new CatTransaccionesDetalle(
                                detalles.get(i).getCod_tra(),
                                detalles.get(i).getCod_det(),
                                detalles.get(i).getCod_bod(),
                                detalles.get(i).getCod_ubi(),
                                detalles.get(i).getCod_art(),
                                detalles.get(i).getDet_lot(),
                                detalles.get(i).getFec_ven(),
                                String.valueOf(mcantidad),
                                detalles.get(i).getNombod(),
                                detalles.get(i).getNomubi(),
                                detalles.get(i).getNomart(),
                                String.valueOf(Double.valueOf(detalles.get(i).getDet_can_con()) + det_can_con),
                                detalles.get(i).getUni_med_con(),
                                String.valueOf(Double.valueOf(detalles.get(i).getDet_cos()) + Double.valueOf(costo)),
                                detalles.get(i).getCodrefart(),
                                detalles.get(i).getNomunimed(),
                                detalles.get(i).getOrd_dia_ant()
                        ));
                    }

                    if (Integer.valueOf(detalles.get(i).getCod_det()) > correlativo) {
                        correlativo = Integer.valueOf(detalles.get(i).getCod_det());
                    }
                }

                if (insert == 0) {

                    if (cod_tra.equals("") || cod_tra.equals("0")) {
                        ord_ant = "0";
                    } else {
                        ord_ant = macc.strQuerySQLvariable("select ifnull(ord_dia,0) "
                                + "from inv_tbl_historico "
                                + "where cod_mov = " + cod_tra + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "AND cod_bod = " + cod_bod + " "
                                + "AND cod_ubi = " + cod_ubi + " "
                                + "AND cod_art = " + cod_art + " "
                                + "AND det_lot = '" + det_lot.trim() + "' "
                                + "limit 1;");
                        if (ord_ant == null) {
                            ord_ant = "0";
                        }
                        if (ord_ant.equals("")) {
                            ord_ant = "0";
                        }
                    }

                    detalles.add(new CatTransaccionesDetalle(
                            "",
                            String.valueOf(correlativo + 1),
                            cod_bod,
                            cod_ubi,
                            cod_art,
                            det_lot.trim(),
                            fec_ven,
                            String.valueOf(det_can),
                            macc.strQuerySQLvariable("select nom_bod from inv_cat_bodegas where cod_pai = " + cod_pai + " and id_bod =" + cod_bod + ";"),
                            macc.strQuerySQLvariable("select nom_ubi from inv_cat_ubicaciones where id_ubi = " + cod_ubi + " and cod_bod =" + cod_bod + ";"),
                            macc.strQuerySQLvariable("select det_nom from inv_cat_articulo where id_art =" + cod_art + ";"),
                            String.valueOf(det_can_con),
                            uni_med_con,
                            costo,
                            macc.strQuerySQLvariable("select cod_art from inv_cat_articulo where id_art =" + cod_art + ";"),
                            macc.strQuerySQLvariable("select nom_emb from inv_cat_embalaje where id_emb =" + uni_med_con + ";"),
                            ord_ant
                    ));
                }
                macc.Desconectar();
                cod_art = "0";
                cod_bod = "0";
                cod_ubi = "0";
                det_can = 0.0;
                det_cos = 0.0;
                uni_med_con = "0";
                nombreunidad = "";
                unimedori = "0";
                det_lot = "";
                chkfecven = "false";
                fec_ven = "";
                mfechaven = null;
                cattransaccionesdetalle = null;
                ord_ant = null;
            }
        } catch (Exception e) {
            System.out.println("Error en Agregar Detalle ManInventarioProductos." + e.getMessage());
        }
    }

    public void eliminardetalle() {
        if ("".equals(cod_det)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover.", 2);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (cod_det.equals(detalles.get(i).getCod_det())) {
                    detalles.remove(i);
                }
            }
            cod_art = "0";
            cod_bod = "0";
            cod_ubi = "0";
            det_can = 0.0;
            det_cos = 0.0;
            uni_med_con = "0";
            nombreunidad = "";
            unimedori = "0";
            det_lot = "";
            cattransaccionesdetalle = null;
        }
    }

    //************************ Operaciones Transaccionales *********************
    public boolean validardatos() {
        boolean mvalidar = true;
        if ("0".equals(cod_pai)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País", 2);
        }
        if ("0".equals(tip_mov)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Tipo de Movimiento", 2);
        }
        if (detalles.isEmpty()) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Agregar al menos un Detalle", 2);
        }

        if (!"".equals(cod_tra)) {
            macc.Conectar();
            if ("1".equals(macc.strQuerySQLvariable("select flg_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Documento ya ha sido Anulado", 2);
            }

            if ("2".equals(macc.strQuerySQLvariable("select flg_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Documento es Reversión de Anulación y no puede modificarse", 2);
            }
            macc.Desconectar();
        }
        return mvalidar;
    }

    public void guardar() {
        if (validardatos()) {
            if ("".equals(cod_tra)) {
                tipoaccion = "insert";
            } else {
                tipoaccion = "update";
            }

            if (guardarmovimiento()) {
                addMessage("Guardar Movimiento de Inventario", "Información almacenada con Éxito.", 1);
                nuevo();
            }

        }
    }

    public boolean guardarmovimiento() {
        String mQuery = "";
        String mValores = "", mhistoria = "";
        String codtraanu = "";
        ResultSet resVariable;
        try {
            macc.Conectar();
            if (tipoaccion.equals("insert") || tipoaccion.equals("move") || tipoaccion.equals("cancel")) {
                if (tipoaccion.equals("cancel")) {
                    codtraanu = cod_tra;
                    mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_transacciones;";
                    cod_tra = macc.strQuerySQLvariable(mQuery);

                    cor_mov = macc.strQuerySQLvariable("select ifnull(max(cor_mov),0) + 1 as cod from inv_tbl_transacciones "
                            + "where tip_mov=" + tip_mov + " and cod_pai=" + cod_pai + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");

                    mQuery = "insert into inv_tbl_transacciones (cod_tra,cod_pai,fec_tra,flg_ing_sal,tip_mov,doc_tra,ord_com,"
                            + "cod_cli_pro,det_obs,cod_usu,flg_anu,cod_esp,cor_mov,cod_are,obs_anu,usu_edi,fec_edi,det_pol,tra_anu,otr_sol) "
                            + "values (" + cod_tra + "," + cod_pai + ",str_to_date('" + fec_tra + "','%d/%m/%Y'),'" + flg_ing_sal
                            + "'," + tip_mov + ",'" + doc_tra + "','" + ord_com + "'," + cod_cli_pro + ",'" + det_obs + "',"
                            + cod_usu + ",0," + cod_esp + "," + cor_mov + "," + cod_are + ",'" + obs_anu + "'," + cod_usu + ",now(),'"
                            + det_pol + "'," + codtraanu + ",'" + otr_sol + "');";
                } else {
                    mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_transacciones;";
                    cod_tra = macc.strQuerySQLvariable(mQuery);

                    cor_mov = macc.strQuerySQLvariable("select ifnull(max(cor_mov),0) + 1 as cod from inv_tbl_transacciones "
                            + "where tip_mov=" + tip_mov + " and cod_pai=" + cod_pai + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");

                    mQuery = "insert into inv_tbl_transacciones (cod_tra,cod_pai,fec_tra,flg_ing_sal,tip_mov,doc_tra,ord_com,"
                            + "cod_cli_pro,det_obs,cod_usu,flg_anu,cod_esp,cor_mov,cod_are,obs_anu,usu_edi,fec_edi,det_pol,tra_anu,otr_sol) "
                            + "values (" + cod_tra + "," + cod_pai + ",str_to_date('" + fec_tra + "','%d/%m/%Y'),'" + flg_ing_sal
                            + "'," + tip_mov + ",'" + doc_tra + "','" + ord_com + "'," + cod_cli_pro + ",'" + det_obs + "',"
                            + cod_usu + ",0," + cod_esp + "," + cor_mov + "," + cod_are + ",'" + obs_anu + "'," + cod_usu + ",now(),'"
                            + det_pol + "',0,'" + otr_sol + "');";
                }

                macc.dmlSQLvariable(mQuery);
            }

            if (!"".equals(cod_tra) && tipoaccion.equals("update")) {
                mQuery = "update inv_tbl_transacciones set "
                        + "cod_pai =" + cod_pai + ", "
                        + "fec_tra =" + "str_to_date('" + fec_tra + "','%d/%m/%Y')" + ", "
                        + "flg_ing_sal = '" + flg_ing_sal + "', "
                        + "tip_mov =" + tip_mov + ", "
                        + "doc_tra ='" + doc_tra + "',"
                        + "ord_com ='" + ord_com + "',"
                        + "cod_cli_pro =" + cod_cli_pro + ","
                        + "det_obs ='" + det_obs + "', "
                        + "cod_usu =" + cod_usu + ", "
                        + "cod_esp =" + cod_esp + ", "
                        + "cor_mov =" + Integer.valueOf(cor_mov.substring(cor_mov.length()-4)) + ", "
                        + "cod_are =" + cod_are + ", "
                        + "obs_anu ='" + obs_anu + "', "
                        + "usu_edi =" + cod_usu + ", "
                        + "fec_edi = now(), "
                        + "det_pol ='" + det_pol + "', "
                        + "otr_sol ='" + otr_sol + "' "
                        + "where cod_tra =" + cod_tra + ";";
                

                if (reestructurarmodificacion()) {

                    macc.dmlSQLvariable(mQuery);

                    mQuery = "delete from inv_tbl_transacciones_det where cod_tra=" + cod_tra + ";";
                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from inv_tbl_historico where cod_mov =" + cod_tra + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    addMessage("Guardar Movimiento de Inventario", "Error al Modificar registros", 2);
                    macc.Desconectar();
                    return false;
                }
            }

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

                mValores = mValores + ",(" + cod_tra + "," + mCorrela + "," + detalles.get(i).getCod_bod() + "," + detalles.get(i).getCod_ubi() + ","
                        + detalles.get(i).getCod_art() + ",'" + detalles.get(i).getDet_lot() + "',str_to_date('" + detalles.get(i).getFec_ven() + "','%d/%m/%Y'),"
                        + detalles.get(i).getDet_can() + "," + detalles.get(i).getDet_cos() + "," + detalles.get(i).getDet_can_con() + ","
                        + detalles.get(i).getUni_med_con() + ")";

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
            mQuery = "insert into inv_tbl_transacciones_det (cod_tra,cod_det,cod_bod,cod_ubi,cod_art,det_lot,fec_ven,det_can,det_cos,det_can_con,uni_med_con) VALUES " + mValores.substring(1) + ";";
            macc.dmlSQLvariable(mQuery);
            macc.Desconectar();

            return true;

        } catch (Exception e) {
            addMessage("Guardar Movimiento de Inventario", "Error al momento de Guardar la Información." + e.getMessage(), 2);
            macc.Desconectar();
            System.out.println("Error en Guardar Movimiento de Inventario Productos ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery + " mValores: " + mValores);
            return false;
        }

    }

    public void llenarDetalleHistorico() {
        String mQuery = "";
        try {
            Querys.clear();
            mQuery = "select "
                    + "det.cod_tra, "
                    + "det.cod_det, "
                    + "det.cod_bod, "
                    + "det.cod_ubi, "
                    + "det.cod_art, "
                    + "det.det_lot, "
                    + "if(date_format(det.fec_ven,'%d/%m/%Y')='00/00/0000','',date_format(det.fec_ven,'%d/%m/%Y')) as fecven,"
                    + "det.det_can,"
                    + "bod.nom_bod,"
                    + "ubi.nom_ubi,"
                    + "art.det_nom,"
                    + "det.det_can_con, "
                    + "det.uni_med_con, "
                    + "case enc.flg_ing_sal "
                    + "when 'false' then det.det_cos "
                    + "when 'true' then (det.det_can * (select ifnull(his.cos_pro,0) as cospro from inv_tbl_historico as his where his.cod_art=det.cod_art and his.cod_pai = enc.cod_pai and  his.fec_tra <= enc.fec_tra order by his.fec_tra desc, his.ord_dia desc  limit 1)) "
                    + "end as det_cos, "
                    + "art.cod_art, "
                    + "if(date_format(enc.fec_tra,'%d/%m/%Y')='00/00/0000','',date_format(enc.fec_tra,'%d/%m/%Y')) as fectra,"
                    + "(select ifnull(his.ord_dia,0) from inv_tbl_historico as his where his.cod_mov = " + cod_tra + " and det_mov = det.cod_det) as ant_ord "
                    + "FROM inv_tbl_transacciones_det as det "
                    + "left join inv_tbl_transacciones as enc on det.cod_tra = enc.cod_tra "
                    + "left join inv_cat_bodegas as bod on det.cod_bod = bod.id_bod and enc.cod_pai = bod.cod_pai "
                    + "left join inv_cat_ubicaciones as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                    + "left join inv_cat_articulo as art on det.cod_art = art.id_art "
                    + "left join inv_cat_embalaje as emb on det.uni_med_con = emb.id_emb "
                    + "where det.cod_tra = " + cod_tra + " "
                    + "order by det.cod_det;";
            ResultSet resVariable;

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                Querys.add(new CatTransaccionesDetalle(
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
                        resVariable.getString(16),
                        resVariable.getString(17)
                ));
            }
            resVariable.close();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalle Histórico ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean reestructurarmodificacion() {
        String mQuery = "";
        ResultSet resVariable;
        int mCorrela = 1, ord_dia = 0;
        int id_tra = 0;
        Double cos_uni, cos_pro, exi_ant, exi_act, exi_act_lot, exi_ant_tot, exi_act_tot;

        try {
            llenarDetalleHistorico();

            for (int i = 0; i < Querys.size(); i++) {
                cos_uni = 0.0;
                cos_pro = 0.0;
                exi_ant = 0.0;
                exi_act = 0.0;
                exi_act_lot = 0.0;
                exi_ant_tot = 0.0;
                exi_act_tot = 0.0;

                //****************************************************************************************************************************
                //*********************** COSTO PROMEDIO Y EXISTENCIA ANTERIOR ***************************************************************
                //****************************************************************************************************************************
                if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + Querys.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra <= STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y');") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + Querys.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra < STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y');") < 1
                        && macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + Querys.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y')"
                                + "and flg_ing_sal = 'false' "
                                + ";") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;
                    exi_ant_tot = 0.0;

                } else {
                    //************* Costo Promedio y Existencia anteriores  **************
                    cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + Querys.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and fec_tra <= STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                            + "and ord_dia < " + Querys.get(i).getOrd_dia_ant() + " "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //***********  Existencia Anterior Bodega ********************************************
                    exi_act = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + Querys.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod=" + Querys.get(i).getCod_bod() + " "
                            + "and fec_tra <= STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                            + "and ord_dia < " + Querys.get(i).getOrd_dia_ant() + " "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //********** Existencia Anterior Lote **********************************************
                    exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + Querys.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod=" + Querys.get(i).getCod_bod() + " "
                            + "and det_lot='" + Querys.get(i).getDet_lot().trim() + "' "
                            + "and fec_tra <= STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                            + "and ord_dia < " + Querys.get(i).getOrd_dia_ant() + " "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //********** Existencia Anterior Total ********************************************
                    exi_act_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + Querys.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and fec_tra <= STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                            + "and ord_dia < " + Querys.get(i).getOrd_dia_ant() + " "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //***********************************************************************************************************
                //************************** MODIFICACIÓN DE REGISTROS POSTERIORES TOTALES **********************************
                //***********************************************************************************************************
                Double contasiguientes;

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tbl_historico where "
                        + "fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                        + "and ord_dia > " + Querys.get(i).getOrd_dia_ant() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_art = " + Querys.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + Querys.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and ord_dia > " + Querys.get(i).getOrd_dia_ant() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art=" + Querys.get(i).getCod_art() + " "
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
                                + "fec_tra > STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art=" + Querys.get(i).getCod_art() + " "
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
                                exi_act_tot = exi_ant_tot + (seriehistorica1.getDet_can());
                                cos_pro = ((cos_pro * exi_ant_tot + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_ant_tot + (seriehistorica1.getDet_can())));
                                mQuery = "update inv_tbl_historico set "
                                        + "cos_pro = " + cos_pro + ","
                                        + "exi_ant_tot = " + exi_ant_tot + ","
                                        + "exi_act_tot = " + exi_act_tot + " "
                                        + "where "
                                        + "cod_tra = " + seriehistorica1.getCod_tra()
                                        + ";";

                            } else {
                                exi_ant_tot = exi_act_tot;
                                exi_act_tot = exi_ant_tot - (seriehistorica1.getDet_can());
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
                        + "fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                        + "and ord_dia > " + Querys.get(i).getOrd_dia_ant() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + Querys.get(i).getCod_bod() + " "
                        + "and cod_art = " + Querys.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + Querys.get(i).getCod_bod() + " "
                                + "and cod_art = " + Querys.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and ord_dia > " + Querys.get(i).getOrd_dia_ant() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + Querys.get(i).getCod_bod() + " "
                                + "and cod_art=" + Querys.get(i).getCod_art() + " "
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
                                + "fec_tra > STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + Querys.get(i).getCod_bod() + " "
                                + "and cod_art=" + Querys.get(i).getCod_art() + " "
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
                                exi_act = exi_ant + (seriehistorica1.getDet_can());
                            } else {
                                exi_ant = exi_act;
                                exi_act = exi_ant - (seriehistorica1.getDet_can());
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
                        + "fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                        + "and ord_dia > " + Querys.get(i).getOrd_dia_ant() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + Querys.get(i).getCod_bod() + " "
                        + "and cod_art = " + Querys.get(i).getCod_art() + " "
                        + "and det_lot ='" + Querys.get(i).getDet_lot() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + Querys.get(i).getCod_bod() + " "
                                + "and cod_art = " + Querys.get(i).getCod_art() + " "
                                + "and det_lot ='" + Querys.get(i).getDet_lot() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and ord_dia > " + Querys.get(i).getOrd_dia_ant() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + Querys.get(i).getCod_bod() + " "
                                + "and cod_art = " + Querys.get(i).getCod_art() + " "
                                + "and det_lot ='" + Querys.get(i).getDet_lot() + "' "
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
                                + "fec_tra > STR_TO_DATE('" + Querys.get(i).getNomunimed() + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + Querys.get(i).getCod_bod() + " "
                                + "and cod_art = " + Querys.get(i).getCod_art() + " "
                                + "and det_lot ='" + Querys.get(i).getDet_lot() + "' "
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
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void iniciaranular() {
        String mQuery;
        boolean mValidar = true;
        anu_tip_mov = "0";

        if ("".equals(cod_tra)) {
            mValidar = false;
            addMessage("Anular de Inventario Productos", "Debe elegir un Registro", 2);
        } else {

            macc.Conectar();
            mQuery = "select flg_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";";

            if ("1".equals(macc.strQuerySQLvariable(mQuery))) {
                mValidar = false;
                addMessage("Anular de Inventario Productos", "Este Documento ya ha sido Anulado", 2);
            }

            if ("2".equals(macc.strQuerySQLvariable(mQuery))) {
                mValidar = false;
                addMessage("Anular de Inventario Productos", "Este Documento es Reversión de Anulación y no puede modificarse", 2);
            }

            macc.Desconectar();

        }

        if (mValidar) {
            try {
                mQuery = "select  "
                        + "date_format(enc.fec_tra,'%d/%m/%Y') as fectra,"
                        + "enc.flg_ing_sal, "
                        + "enc.tip_mov, "
                        + "enc.doc_tra, "
                        + "enc.ord_com, "
                        + "enc.cod_cli_pro, "
                        + "enc.det_obs, "
                        + "ifnull(enc.cod_esp,''),"
                        + "ifnull(enc.cod_are,''), "
                        + "ifnull(enc.otr_sol,'') "
                        + "FROM inv_tbl_transacciones as enc "
                        + "where enc.cod_pai = " + cod_pai + " and enc.cod_tra = " + cod_tra + ";";
                ResultSet resVariable;
                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    fec_tra = resVariable.getString(1);
                    flg_ing_sal = resVariable.getString(2);
                    tip_mov = resVariable.getString(3);
                    doc_tra = resVariable.getString(4);
                    ord_com = resVariable.getString(5);
                    cod_cli_pro = resVariable.getString(6);
                    det_obs = resVariable.getString(7);
                    cod_esp = resVariable.getString(8);
                    cod_are = resVariable.getString(9);
                    otr_sol = resVariable.getString(10);

                }
                resVariable.close();
                llenarDetalleMovimiento();
                if ("false".equals(flg_ing_sal)) {
                    flg_ing_sal = "true";
                } else {
                    flg_ing_sal = "false";
                }
                llenarMovimientos();
                //******************* PRUEBA ***********************
                System.out.println(cod_tra);
                //**************************************************

                RequestContext.getCurrentInstance().update("frmAnularInvArt");
                RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').show()");
            } catch (Exception e) {
                addMessage("Anular de Inventario Productos", "Error al momento Inicializar la Anulación. " + e.getMessage(), 2);
                System.out.println("Error al Anular de Inventario de Productos. " + e.getMessage());
                nuevo();
            }

            macc.Desconectar();

        }

    }

    public void anular() {
        String mQuery;
        boolean mValidar = true;

        if ("".equals(obs_anu)) {
            mValidar = false;
            addMessage("Anular Movimiento de Inventario", "Debe ingresar un comentario sobre la anulación del documento", 2);
        }

        if ("0".equals(anu_tip_mov)) {
            mValidar = false;
            addMessage("Anular Movimiento de Inventario", "Debe seleccionar un tipo de documento de Reversión", 2);
        }

        if (mValidar) {
            macc.Conectar();
            String mCorMov = macc.strQuerySQLvariable("select ifnull(max(cor_mov),0) + 1 as cod from inv_tbl_transacciones "
                    + "where tip_mov=" + anu_tip_mov + " and cod_pai=" + cod_pai + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");
            String mDocu = macc.strQuerySQLvariable("select case ifnull(det_abr,'') when '' then right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2) else concat(det_abr,right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') end as abr from inv_cat_mov where id_mov=" + anu_tip_mov + ";")
                    + macc.strQuerySQLvariable("select LPAD('" + mCorMov + "', 4, '0');");
            obs_anu = obs_anu + ". Se generó automáticamente el Documento de Reversión: "
                    + mDocu;
            tip_mov = anu_tip_mov;
            cod_cli_pro = "0";
            cod_esp = "0";
            cod_are = "0";
            otr_sol = "";
            String mfechatra = fec_tra;
            String mflagingsal = flg_ing_sal;
            mQuery = "update inv_tbl_transacciones set flg_anu = 1, obs_anu='" + obs_anu + "' where cod_tra=" + cod_tra + ";";

            //******************* PRUEBA ***********************
            System.out.println(mQuery);
            //**************************************************

            macc.dmlSQLvariable(mQuery);

            //**************************************************************************************************************
            //******************* INSERTAR EN TABLA HISTÓRICA DE ANULADOS **************************************************
            mQuery = "insert into inv_tbl_his_transacciones_anu (cod_tra,cod_det,cod_bod,cod_ubi,cod_art,det_lot,fec_ven,det_can,det_cos,det_can_con,uni_med_con,usu_anu,fec_anu) "
                    + "SELECT cod_tra, cod_det, cod_bod, cod_ubi, cod_art, det_lot, fec_ven, det_can, det_cos, det_can_con, uni_med_con," + cbean.getCod_usu() + ",now() "
                    + "FROM inv_tbl_transacciones_det where cod_tra = " + cod_tra + ";";

           

            macc.dmlSQLvariable(mQuery);

            //**************************************************************************************************************
            macc.Desconectar();

            //cod_tra = ""; //************** SE MODIFICÓ CON TIPOACCIÓN = "CANCEL" **********************************
            tipoaccion = "cancel";
            obs_anu = "";

            if (guardarmovimiento()) {
                macc.Conectar();
                mQuery = "select cod_tra from inv_tbl_transacciones "
                        + "where fec_tra = str_to_date('" + mfechatra + "','%d/%m/%Y') "
                        + "and flg_ing_sal='" + mflagingsal + "' "
                        + "and tip_mov = " + anu_tip_mov + " "
                        + "and cor_mov = " + mCorMov + ";";
                cod_tra = macc.strQuerySQLvariable(mQuery);
                mQuery = "update inv_tbl_transacciones set flg_anu = 2 where cod_tra=" + cod_tra + ";";
                cod_tra = "";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                RequestContext.getCurrentInstance().update("frmInventarioPro");
                RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').hide()");
                addMessage("Anular Movimiento de Inventario", "Información Anulada con éxito", 1);
            } else {
                addMessage("Anular Movimiento de Inventario", "Error al momento de anular el Documento", 2);
            }
            cattransacciones = null;
            encabezado.clear();
            RequestContext.getCurrentInstance().update("frmBuscarIng");
            anu_tip_mov = "0";
            nuevo();
        }

    }

    public void cerraranular() {
        nuevo();
    }

    public void iniciartraslado() {
        boolean mValidar = true;

        if ("false".equals(flg_ing_sal)) {
            mValidar = false;
            addMessage("Traslado Productos", "Debe seleccionar SALIDA como tipo de movimiento", 2);
        }
        if (!"".equals(cod_tra)) {
            mValidar = false;
            addMessage("Traslado Productos", "No se puede realizar traslados en una modificación. Debe generar un nuevo Documento", 2);
        }

        if (mValidar && validardatos()) {
            try {
                tra_tip_mov = "0";
                tra_cod_bod = "0";
                flg_ing_sal = "false";
                llenarMovimientos();
                RequestContext.getCurrentInstance().update("frmTrasladoInvArt");
                RequestContext.getCurrentInstance().execute("PF('wTrasladoInvArt').show()");
            } catch (Exception e) {
                addMessage("Traslado Productos", "Error al momento Inicializar Traslado. " + e.getMessage(), 2);
                System.out.println("Error al Trasladar Productos iniciartraslado. " + e.getMessage());
                nuevo();
            }

        }

    }

    public void trasladar() {
        boolean mValidar = true;

        if ("0".equals(tra_tip_mov)) {
            mValidar = false;
            addMessage("Traslado Productos", "Debe Seleccionar un Documento de Ingreso para el Traslado", 2);
        }

        if ("0".equals(tra_cod_bod)) {
            mValidar = false;
            addMessage("Traslado Productos", "Debe seleccionar una Bodega Destino", 2);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (tra_cod_bod.equals(detalles.get(i).getCod_bod())) {
                    mValidar = false;
                }
            }
            if (!mValidar) {
                addMessage("Traslado Productos", "Existen productos relacionados con la Bodega destino en la Lista de Detalle", 2);
            }

            for (int i = 0; i < detalles.size(); i++) {
                //*************************** Verificación de Puntos Máximos *************************************************
                Double mExi = 0.0;
                macc.Conectar();
                try {
                    mExi = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
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
                } catch (Exception e) {
                    mExi = 0.0;
                }
                macc.Desconectar();
                Double exigrid = 0.0;
                if (!detalles.isEmpty()) {
                    for (int j = 0; j < detalles.size(); j++) {
                        if (cod_bod.equals(detalles.get(j).getCod_bod())
                                && cod_art.equals(detalles.get(j).getCod_art())
                                && det_lot.equals(detalles.get(j).getDet_lot())) {
                            exigrid = exigrid + Double.valueOf(detalles.get(j).getDet_can());
                        }
                    }
                }

                Double mMax = 0.0;
                macc.Conectar();
                try {
                    mMax = Double.valueOf(macc.strQuerySQLvariable("select ifnull(pun_max,0.0) "
                            + "from inv_cat_pun_ale "
                            + "where cod_bod = " + tra_cod_bod + " "
                            + "and cod_art = " + detalles.get(i).getCod_art()
                            + ";"));
                } catch (Exception e) {
                    mMax = 0.0;
                }
                macc.Desconectar();
                if (mMax > 0.0) {
                    if ((mExi + Double.valueOf(detalles.get(i).getDet_can()) + exigrid) > mMax) {
                        addMessage("Traslado Productos", "La Cantidad Ingresada de '" + detalles.get(i).getNomart() + "' Sobrepasa el Máximo permitido en Bodega Destino", 2);
                        mValidar = false;
                    }
                }

            }
        }

        if (mValidar) {
            flg_ing_sal = "true";
            tipoaccion = "move";
            if (guardarmovimiento()) {
                macc.Conectar();
                macc.dmlSQLvariable("update inv_tbl_transacciones set flg_anu = 3 where cod_tra=" + cod_tra + ";");
                macc.Desconectar();

                flg_ing_sal = "false";
                tip_mov = tra_tip_mov;
                cod_tra = "";
                tipoaccion = "move";
                macc.Conectar();
                for (int i = 0; i < detalles.size(); i++) {
                    detalles.set(i, new CatTransaccionesDetalle(
                            detalles.get(i).getCod_tra(),
                            detalles.get(i).getCod_det(),
                            tra_cod_bod,
                            detalles.get(i).getCod_ubi(),
                            detalles.get(i).getCod_art(),
                            detalles.get(i).getDet_lot(),
                            detalles.get(i).getFec_ven(),
                            detalles.get(i).getDet_can(),
                            detalles.get(i).getNombod(),
                            detalles.get(i).getNomubi(),
                            detalles.get(i).getNomart(),
                            detalles.get(i).getDet_can_con(),
                            detalles.get(i).getUni_med_con(),
                            macc.strQuerySQLvariable("select (ifnull(cos_pro,0) * " + detalles.get(i).getDet_can() + ") as cospro "
                                    + "from inv_tbl_historico "
                                    + "where "
                                    + "cod_art = " + detalles.get(i).getCod_art() + " "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc, "
                                    + "ord_dia desc "
                                    + "limit 1;"),
                            detalles.get(i).getCodrefart(),
                            detalles.get(i).getNomunimed(),
                            detalles.get(i).getOrd_dia_ant()
                    ));
                }
                macc.Desconectar();

                if (guardarmovimiento()) {
                    macc.Conectar();
                    macc.dmlSQLvariable("update inv_tbl_transacciones set flg_anu = 4 where cod_tra=" + cod_tra + ";");
                    macc.Desconectar();
                    addMessage("Traslado Productos", "Traslado realizado con éxito", 1);
                } else {
                    addMessage("Traslado Productos", "Error al momento de guardar Entrada Traslado", 2);
                }
                RequestContext.getCurrentInstance().update("frmInventarioPro");
                RequestContext.getCurrentInstance().execute("PF('wTrasladoInvArt').hide()");
                nuevo();
            } else {
                addMessage("Traslado Productos", "Error al momento de guardar Salida Traslado", 2);
            }

        }

    }

    public void cerrartraslado() {
        nuevo();
    }

    public void iniciarvencidos() {
        ven_cod_bod = "0";
    }

    public void llenarVencidos() {
        String mQuery = "";
        if ("0".equals(ven_cod_bod)) {
            addMessage("Cargar Vencidos", "Debe Seleccionar una Bodega Origen", 2);
        } else {
            try {
                flg_ing_sal = "true";
                inout();
                cattransaccionesdetalle = null;
                detalles.clear();
                mQuery = "select 0 as tra, 0 as dettra,t.cod_bod, t.cod_ubi, t.id_art, t.detlot, "
                        + "date_format(t.fecven,'%d/%m/%Y'), t.detcan, t.nombod, t.nomubi, t.nomart,t.detcancon,t.det_emb,t.costo,t.cod_art, t.nomemb, '0' as ant "
                        + "from ( "
                        + "select "
                        + "det.cod_bod, "
                        + "0 as cod_ubi, "
                        + "art.id_art, "
                        + "ifnull(det.det_lot,'') as detlot, "
                        + "det.fec_ven as fecven, "
                        + "ifnull((select his.exi_act_lot from inv_tbl_historico as his where his.cod_pai = art.cod_pai and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.det_lot = det.det_lot and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as detcan, "
                        + "ifnull(bod.nom_bod,'') as nombod, "
                        + "ifnull(ubi.nom_ubi,'') as nomubi, "
                        + "concat(art.cod_art,'--',ifnull(art.cod_alt,''),'--',art.det_nom) as nomart, "
                        + "ifnull((select his.exi_act_lot from inv_tbl_historico as his where his.cod_pai = art.cod_pai and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.det_lot = det.det_lot and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as detcancon, "
                        + "art.det_emb, "
                        + "0.0 as costo, "
                        + "art.cod_art, "
                        + "ifnull(emb.nom_emb,'') as nomemb "
                        + "from inv_cat_articulo as art "
                        + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                        + "left join inv_tbl_transacciones_det as det on art.id_art = det.cod_art "
                        + "left join inv_tbl_transacciones as mae on det.cod_tra = mae.cod_tra and art.cod_pai = mae.cod_pai "
                        + "left join cat_pai as pai on art.cod_pai = pai.cod_pai "
                        + "left join inv_cat_bodegas as bod on art.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                        + "left join inv_cat_ubicaciones as ubi on bod.id_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                        + "where art.cod_pai = " + cod_pai + " "
                        + "and mae.flg_ing_sal = 'false' "
                        + "group by "
                        + "det.cod_bod, "
                        + "art.id_art, "
                        + "art.det_emb, "
                        + "det.det_lot, "
                        + "det.fec_ven, "
                        + "pai.nom_pai, "
                        + "bod.nom_bod, "
                        + "ubi.nom_ubi, "
                        + "art.cod_art, "
                        + "art.det_nom, "
                        + "emb.nom_emb ) as t "
                        + "where t.detcan > 0 "
                        + "and date_format(t.fecven,'%d/%m/%Y') <> '00/00/0000' "
                        + "and t.fecven < str_to_date('" + fec_tra + "','%d/%m/%Y') "
                        + "and t.cod_bod = " + ven_cod_bod + " "
                        + "order by t.id_art;";

                ResultSet resVariable;
                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                int mCorrela = 1;
                while (resVariable.next()) {
                    detalles.add(new CatTransaccionesDetalle(
                            resVariable.getString(1),
                            String.valueOf(mCorrela),
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
                    mCorrela = mCorrela + 1;
                }
                resVariable.close();
                macc.Desconectar();

                RequestContext.getCurrentInstance().update("frmInventarioPro");
                RequestContext.getCurrentInstance().execute("PF('wCargarVencidos').hide()");
            } catch (Exception e) {
                System.out.println("Error en el llenado de Vencidos ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
            }
        }
    }

    public void cerrarvencidos() {
        ven_cod_bod = "0";
        nuevo();
    }

    //************************ Funcionalidad de Controles **********************
    public void inout() {
        tip_mov = "0";
        cod_are = "0";
        obs_anu = "";
        cod_esp = "0";
        cor_mov = "";
        doc_tra = "";
        det_pol = "";
        ord_com = "";
        cod_cli_pro = "0";
        det_obs = "";
        otr_sol = "";
        nomclipro = "";
        cod_det = "";
        cod_bod = "0";
        cod_ubi = "0";
        cod_art = "0";
        det_lot = "";
        fec_ven = "";
        det_can = 0.0;
        det_cos = 0.0;
        nombod = "";
        nomubi = "";
        nomart = "";
        uni_med_con = "0";
        nombreunidad = "";
        chkfecven = "false";
        mfechaven = null;
        detalles.clear();
        lotes.clear();
        llenarMovimientos();
        llenarProveedores();
        llenarEspecialistas();
        if ("true".equals(flg_ing_sal)) {
            titulo = "Cliente";
            booleditable = "false";
            boolinout = "true";
        } else {
            titulo = "Proveedor";
            booleditable = "true";
            boolinout = "false";
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_det = ((CatTransaccionesDetalle) event.getObject()).getCod_det();
        /*
        cod_bod = ((CatTransaccionesDetalle) event.getObject()).getCod_bod();
        cod_ubi = ((CatTransaccionesDetalle) event.getObject()).getCod_ubi();
        cod_art = ((CatTransaccionesDetalle) event.getObject()).getCod_art();
        det_lot = ((CatTransaccionesDetalle) event.getObject()).getDet_lot();
        fec_ven = ((CatTransaccionesDetalle) event.getObject()).getFec_ven();
        det_can = Double.valueOf(((CatTransaccionesDetalle) event.getObject()).getDet_can());
        nombod = ((CatTransaccionesDetalle) event.getObject()).getNombod();
        nomubi = ((CatTransaccionesDetalle) event.getObject()).getNomubi();
        nomart = ((CatTransaccionesDetalle) event.getObject()).getNomart();
        if ("00/00/0000".equals(fec_ven)) {
            fec_ven = "";
        }
         */
    }

    public void seleccionarpais() {
        llenarMovimientos();
        llenarEspecialistas();
        llenarProveedores();
        llenarArticulos();
        llenarBodegas();
        cod_are = "0";
        obs_anu = "";
        cod_esp = "0";
        otr_sol = "";
        cor_mov = "";
    }

    public void onSelectMovimiento() {
        if ("0".equals(tip_mov)) {
            cor_mov = "";
        } else {
            macc.Conectar();
            cor_mov = macc.strQuerySQLvariable("select case ifnull(det_abr,'') when '' then right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2) else concat(det_abr,right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') end as abr from inv_cat_mov where id_mov=" + tip_mov + ";")
                    + macc.strQuerySQLvariable("select LPAD(ifnull(max(cor_mov),0) + 1, 4, '0') from inv_tbl_transacciones "
                            + "where cod_pai=" + cod_pai + " and tip_mov= " + tip_mov + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");
            macc.Desconectar();
        }
    }

    public void seleccionarproducto() {
        llenarBodegas();
        cod_bod = "0";
        macc.Conectar();
        unimedori = macc.strQuerySQLvariable("select det_emb from inv_cat_articulo where id_art =" + cod_art + ";");
        if (unimedori == null) {
            unimedori = "0";
        }
        uni_med_con = unimedori;
        nombreunidad = macc.strQuerySQLvariable("select nom_emb from inv_cat_embalaje where id_emb =(select det_emb from inv_cat_articulo where id_art = " + cod_art + ") ;");
        macc.Desconectar();
    }

    public void seleccionarbodega() {
        llenarUbicaciones();
        llenarLotes();
        det_lot = "";
        mfechaven = null;
        chkfecven = "false";
        cod_ubi = "0";
        det_lot = "";
    }

    public void seleccionarUbicaciones() {

    }

    public void iniciarimagen() {
        macc.Conectar();
        try {

            String mQuery = "";
            Blob miBlob = null;
            if ("0".equals(cod_art) || "".equals(cod_art)) {
                mQuery = "select det_ima from inv_cat_articulo_img where id_art=0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 270.0);
                img = null;

                data = null;
                img_pro = "";
                nomprod = "NINGUNO";
            } else {
                mQuery = "select det_ima from inv_cat_articulo_img where id_art=" + cod_art + ";";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                if (miBlob != null) {
                    byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                    InputStream is = new ByteArrayInputStream(data);

                    mimagen = new DefaultStreamedContent(is, "image/jpeg");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 270.0);
                    img = null;

                    data = null;
                } else {
                    mQuery = "select det_ima from inv_cat_articulo_img where id_art=0;";
                    miBlob = macc.blobQuerySQLvariable(mQuery);
                    byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                    InputStream is = new ByteArrayInputStream(data);

                    mimagen = new DefaultStreamedContent(is, "image/jpeg");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 270.0);
                    img = null;

                    data = null;
                }
                img_pro = "";
                nomprod = macc.strQuerySQLvariable("Select concat(cod_art,'--',det_nom) as nompro from inv_cat_articulo where id_art=" + cod_art + ";");

            }

        } catch (Exception e) {
            System.out.println("Error en Iniciar Imagen de ManInventarioProductos. " + e.getMessage());
        }
        macc.Desconectar();
    }

    public void onCloseImg() {
        img_pro = "";
        nomprod = "";
        mimagen = null;
        mheight = "260";

    }

    public void onselectlote() {
        if (fec_ven == null) {
            fec_ven = "";
        }
        if (!"".equals(det_lot)) {
            chkfecven = "false";
            macc.Conectar();
            fec_ven = macc.strQuerySQLvariable("select case ifnull(det.fec_ven,'') when '' then '' else date_format(det.fec_ven,'%d/%m/%Y') end as fecven "
                    + "from inv_tbl_transacciones as mae "
                    + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                    + "where mae.cod_pai=" + cod_pai + " and det.cod_art=" + cod_art + " and det.det_lot='" + det_lot + "' order by det.fec_ven desc limit 1;");
            macc.Desconectar();

            if ("".equals(fec_ven)) {
                mfechaven = null;
            } else {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    mfechaven = format.parse(fec_ven);
                } catch (Exception e) {

                }
            }
        }
    }

    public void onchkfecvenchange() {

        if (det_lot == null) {
            det_lot = "";
        }
        if ("true".equals(chkfecven)) {

            if ("".equals(det_lot.trim())) {

                chkfecven = "false";
                fec_ven = "";
                mfechaven = null;

            } else if (mfechaven == null) {

                macc.Conectar();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                if ("0".equals(macc.strQuerySQLvariable("select count(det.cod_art) as conta "
                        + "from inv_tbl_transacciones as mae "
                        + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                        + "where mae.cod_pai=" + cod_pai + " and det.cod_art=" + cod_art + " and det.det_lot='" + det_lot + "';"))) {

                    mfechaven = Date.from(Instant.now());
                    fec_ven = format.format(mfechaven);

                } else {
                    chkfecven = "false";
                    fec_ven = macc.strQuerySQLvariable("select case ifnull(det.fec_ven,'') when '' then '' else date_format(det.fec_ven,'%d/%m/%Y') end as fecven "
                            + "from inv_tbl_transacciones as mae "
                            + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                            + "where mae.cod_pai=" + cod_pai + " and det.cod_art=" + cod_art + " and det.det_lot='" + det_lot + "' order by det.fec_ven desc limit 1;");
                    try {
                        mfechaven = format.parse(fec_ven);
                    } catch (Exception e) {

                    }
                }
                macc.Desconectar();
            } else {
                chkfecven = "false";
            }

        } else {
            fec_ven = "";
            mfechaven = null;
        }
    }

    public void dateSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_tra = format.format(date);
    }

    public void dateVenSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_ven = format.format(date);
    }

    // *************************** Consulta Existencias ****************************
    public void iniciarventanaexistencias(int tipoini) {
        if (tipoini == 1) {
            iniciarventana();
            tipoInicio = 1;
        }
        paibus = cbean.getCod_pai();
        bodbus = "0";
        ubibus = "0";
        probus = "0";
        chklot = "false";
        lotbus = "";
        lotesvencimiento.clear();
        bodegasbus.clear();
        ubicacionesbus.clear();
        llenarPaisesbus();
        llenarBodegasbus();
        catlotesvencimiento = null;
        RequestContext.getCurrentInstance().execute("PF('wDTExistenciasLot').clearFilters()");
        RequestContext.getCurrentInstance().update("frmExistenciasLot");

    }

    public void cerrarventanaexistencias() {

        paibus = "0";
        bodbus = "0";
        ubibus = "0";
        probus = "0";
        chklot = "false";
        lotbus = "";

        if (tipoInicio == 1) {
            cerrarventana();
        }

        RequestContext.getCurrentInstance().execute("PF('wDTExistenciasLot').clearFilters()");
        RequestContext.getCurrentInstance().update("frmExistenciasLot");
    }

    public void llenarPaisesbus() {
        try {

            paisesbus.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from inv_cat_pai order by cod_pai;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paisesbus.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises Búsqueda en ManInventarioProductos. " + e.getMessage());
        }
    }

    public void llenarBodegasbus() {
        String mQuery = "";
        try {

            bodegasbus.clear();

            mQuery = "select id_bod, nom_bod,cod_pai "
                    + "from inv_cat_bodegas "
                    + "where cod_pai=" + paibus + " "
                    + "order by id_bod;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegasbus.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3), ""
                ));
            }
            resVariable.close();
            macc.Desconectar();
            ubicacionesbus.clear();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas Búsqueda en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicacionesbus() {
        String mQuery = "";
        try {

            ubicacionesbus.clear();

            mQuery = "select ubi.id_ubi,ubi.cod_bod,ubi.nom_ubi,bod.nom_bod "
                    + "from inv_cat_ubicaciones as ubi "
                    + "left join inv_cat_bodegas as bod on bod.id_bod = ubi.cod_bod "
                    + "where bod.cod_pai = " + paibus + " "
                    + "and ubi.cod_bod=" + bodbus + " "
                    + "order by ubi.cod_bod,ubi.id_ubi;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicacionesbus.add(new CatUbicaciones(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4), "", ""
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Ubicaciones Búsqueda en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onchklotchange() {
        lotbus = "";
    }

    public void consultarexistencias() {
        lotbus = lotbus.trim().replace("'", "");
        llenarExistenciasLotes();
    }

    public void llenarExistenciasLotes() {
        String mQuery = "", mWhere = "";
        try {

            catlotesvencimiento = null;
            lotesvencimiento.clear();

            if (!"0".equals(paibus)) {
                mWhere = mWhere + " and t.cod_pai = " + paibus + " ";
            }
            if (!"0".equals(bodbus)) {
                mWhere = mWhere + " and t.cod_bod = " + bodbus + " ";
            }
            if (!"0".equals(ubibus)) {
                mWhere = mWhere + " and t.cod_ubi = " + ubibus + " ";
            }
            if (!"0".equals(probus)) {
                mWhere = mWhere + " and t.id_art = " + probus + " ";
            }

            if ("false".equals(chklot)) {
                mQuery = "select  t.cod_pai, t.cod_bod, t.cod_ubi, t.id_art, t.det_emb, t.detlot, t.fecven, t.existencia, t.nompai,t.nombod, '' as nomubi, t.nomart, t.nomemb from ( "
                        + "select  "
                        + "art.cod_pai, "
                        + "det.cod_bod, "
                        + "0 as cod_ubi, "
                        + "art.id_art, "
                        + "art.det_emb, "
                        + "'TODOS' as detlot, "
                        + "'' as fecven, "
                        + "ifnull((select his.exi_act_bod from inv_tbl_historico as his where his.cod_pai = art.cod_pai and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia, "
                        + "ifnull(pai.nom_pai,'') as nompai, "
                        + "ifnull(bod.nom_bod,'') as nombod, "
                        + "concat(art.cod_art,'--',ifnull(art.cod_alt,''),'--',art.det_nom) as nomart, "
                        + "ifnull(emb.nom_emb,'') as nomemb  "
                        + "from inv_cat_articulo as art "
                        + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                        + "left join inv_tbl_transacciones_det as det on art.id_art = det.cod_art  "
                        + "left join inv_tbl_transacciones as mae on det.cod_tra = mae.cod_tra and art.cod_pai = mae.cod_pai "
                        + "left join cat_pai as pai on art.cod_pai = pai.cod_pai "
                        + "left join inv_cat_bodegas as bod on art.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                        + "where art.cod_pai = " + paibus + " "
                        + "and mae.flg_ing_sal = 'false' "
                        + "group by art.cod_pai, "
                        + "det.cod_bod, "
                        + "art.id_art, "
                        + "art.det_emb, "
                        + "pai.nom_pai, "
                        + "bod.nom_bod, "
                        + "art.cod_art, "
                        + "art.cod_alt, "
                        + "art.det_nom, "
                        + "emb.nom_emb ) as t "
                        + "where t.existencia > 0 "
                        + mWhere
                        + "order by t.id_art; ";
            } else {
                if (!"".equals(lotbus)) {
                    mWhere = mWhere + " and t.detlot='" + lotbus + "' ";
                }
                mQuery = "select  t.cod_pai, t.cod_bod, t.cod_ubi, t.id_art, t.det_emb, t.detlot, t.fecven, t.existencia, t.nompai,t.nombod, '' as nomubi, t.nomart, t.nomemb from ( "
                        + "select  "
                        + "art.cod_pai, "
                        + "det.cod_bod, "
                        + "0 as cod_ubi, "
                        + "art.id_art, "
                        + "art.det_emb, "
                        + "ifnull(det.det_lot,'') as detlot, "
                        + "case ifnull(date_format(det.fec_ven,'%d/%m/%Y'),'00/00/0000') when '00/00/0000' then '' else date_format(det.fec_ven,'%d/%m/%Y') end as fecven, "
                        + "ifnull((select his.exi_act_lot from inv_tbl_historico as his where his.cod_pai = art.cod_pai and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.det_lot = det.det_lot and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia, "
                        + "ifnull(pai.nom_pai,'') as nompai, "
                        + "ifnull(bod.nom_bod,'') as nombod, "
                        + "concat(art.cod_art,'--',ifnull(art.cod_alt,''),'--',art.det_nom) as nomart, "
                        + "ifnull(emb.nom_emb,'') as nomemb  "
                        + "from inv_cat_articulo as art "
                        + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                        + "left join inv_tbl_transacciones_det as det on art.id_art = det.cod_art  "
                        + "left join inv_tbl_transacciones as mae on det.cod_tra = mae.cod_tra and art.cod_pai = mae.cod_pai "
                        + "left join cat_pai as pai on art.cod_pai = pai.cod_pai "
                        + "left join inv_cat_bodegas as bod on art.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                        + "where art.cod_pai = " + paibus + " "
                        + "and mae.flg_ing_sal = 'false' "
                        + "group by art.cod_pai, "
                        + "det.cod_bod, "
                        + "art.id_art, "
                        + "art.det_emb, "
                        + "det.det_lot, "
                        + "det.fec_ven, "
                        + "pai.nom_pai, "
                        + "bod.nom_bod, "
                        + "art.cod_art, "
                        + "art.cod_alt, "
                        + "art.det_nom, "
                        + "emb.nom_emb ) as t "
                        + "where t.existencia > 0 "
                        + mWhere
                        + "order by t.id_art;";
            }
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lotesvencimiento.add(new CatLotesVencimiento(
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
                        resVariable.getString(13)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Lotes y Vencimiento en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    //************************************ Buscar Movimiento ********************************
    public void cerrarbuscar() {
        encabezado.clear();
    }

    public void llenarEncabezado() {
        String mQuery = "";
        try {
            cattransacciones = null;
            encabezado.clear();

            mQuery = "select  "
                    + "enc.cod_tra, "
                    + "enc.cod_pai, "
                    + "date_format(enc.fec_tra,'%d/%m/%Y') as fectra,"
                    + "enc.flg_ing_sal, "
                    + "enc.tip_mov, "
                    + "enc.doc_tra, "
                    + "enc.ord_com, "
                    + "enc.cod_cli_pro, "
                    + "enc.det_obs, "
                    + "enc.cod_usu, "
                    + "enc.flg_anu,"
                    + "pai.nom_pai,"
                    + "mov.nom_mov,"
                    + "case enc.flg_ing_sal "
                    + "when 'true' then ifnull((select cli.nom_cli from inv_cat_cli as cli where cli.cod_cli = enc.cod_cli_pro),'') "
                    + "when 'false' then ifnull((select pro.nom_pro from inv_cat_pro as pro where pro.cod_pro = enc.cod_cli_pro),'') "
                    + "end as nomclipro, "
                    + "ifnull(enc.cod_esp,''),"
                    + "ifnull(enc.cor_mov,''),"
                    + "ifnull(enc.cod_are,''),"
                    + "enc.obs_anu, "
                    + "(concat((select case ifnull(det_abr,'') when '' then right(year(enc.fec_tra),2) else concat(det_abr,right(year(enc.fec_tra),2),'-') end as abr from inv_cat_mov where id_mov=enc.tip_mov),(select LPAD(enc.cor_mov, 4, '0')) )) as coddoc, "
                    + "det_pol,tra_anu, ifnull(enc.otr_sol,'') "
                    + "FROM inv_tbl_transacciones as enc "
                    + "left join inv_cat_pai as pai on enc.cod_pai = pai.cod_pai "
                    + "left join inv_cat_mov as mov on enc.tip_mov = mov.id_mov "
                    + "where enc.flg_anu = 0 and enc.cod_pai = " + cod_pai + " "
                    + "order by enc.fec_tra desc, enc.flg_ing_sal, enc.tip_mov;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezado.add(new CatTransacciones(
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
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20),
                        resVariable.getString(21),
                        resVariable.getString(22)
                ));
            }
            resVariable.close();
            macc.Desconectar();
            RequestContext.getCurrentInstance().execute("PF('wDTIngBuscar').clearFilters()");
            RequestContext.getCurrentInstance().update("frmBuscarIng");
        } catch (Exception e) {
            System.out.println("Error en el llenado de Movimientos para Modificar ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarDetalleMovimiento() {
        String mQuery = "";
        try {
            cattransaccionesdetalle = null;
            detalles.clear();
            mQuery = "select "
                    + "det.cod_tra, "
                    + "det.cod_det, "
                    + "det.cod_bod, "
                    + "det.cod_ubi, "
                    + "det.cod_art, "
                    + "det.det_lot, "
                    + "if(date_format(det.fec_ven,'%d/%m/%Y')='00/00/0000','',date_format(det.fec_ven,'%d/%m/%Y')) as fecven,"
                    + "det.det_can,"
                    + "bod.nom_bod,"
                    + "ubi.nom_ubi,"
                    + "art.det_nom,"
                    + "det.det_can_con, "
                    + "det.uni_med_con, "
                    + "case enc.flg_ing_sal "
                    + "when 'false' then det.det_cos "
                    + "when 'true' then (det.det_can * (select ifnull(his.cos_pro,0) as cospro from inv_tbl_historico as his where his.cod_art=det.cod_art and his.cod_pai = enc.cod_pai and  his.fec_tra <= enc.fec_tra order by his.fec_tra desc, his.ord_dia desc  limit 1)) "
                    + "end as det_cos, "
                    + "art.cod_art, "
                    + "emb.nom_emb, "
                    + "(select ifnull(his.ord_dia,0) from inv_tbl_historico as his where his.cod_mov = " + cod_tra + " and det_mov = det.cod_det) as ant_ord "
                    + "FROM inv_tbl_transacciones_det as det "
                    + "left join inv_tbl_transacciones as enc on det.cod_tra = enc.cod_tra "
                    + "left join inv_cat_bodegas as bod on det.cod_bod = bod.id_bod and enc.cod_pai = bod.cod_pai "
                    + "left join inv_cat_ubicaciones as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                    + "left join inv_cat_articulo as art on det.cod_art = art.id_art "
                    + "left join inv_cat_embalaje as emb on det.uni_med_con = emb.id_emb "
                    + "where det.cod_tra = " + cod_tra + " "
                    + "order by det.cod_det;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detalles.add(new CatTransaccionesDetalle(
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
                        resVariable.getString(16),
                        resVariable.getString(17)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalle Movimientos para Modificar ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onRowSelectBuscar(SelectEvent event) {
        cod_tra = ((CatTransacciones) event.getObject()).getCod_tra();
        fec_tra = ((CatTransacciones) event.getObject()).getFec_tra();
        flg_ing_sal = ((CatTransacciones) event.getObject()).getFlg_ing_sal();
        tip_mov = ((CatTransacciones) event.getObject()).getTip_mov();
        doc_tra = ((CatTransacciones) event.getObject()).getDoc_tra();
        det_pol = ((CatTransacciones) event.getObject()).getDet_pol();
        ord_com = ((CatTransacciones) event.getObject()).getOrd_com();
        cod_cli_pro = ((CatTransacciones) event.getObject()).getCod_cli_pro();
        det_obs = ((CatTransacciones) event.getObject()).getDet_obs();
        cod_esp = ((CatTransacciones) event.getObject()).getCod_esp();
        cod_are = ((CatTransacciones) event.getObject()).getCod_are();
        obs_anu = ((CatTransacciones) event.getObject()).getObs_anu();
        otr_sol = ((CatTransacciones) event.getObject()).getOtr_sol();
        macc.Conectar();
        cor_mov = macc.strQuerySQLvariable("select case ifnull(det_abr,'') when '' then right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2) else concat(det_abr,right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') end as abr from inv_cat_mov where id_mov=" + tip_mov + ";")
                + macc.strQuerySQLvariable("select LPAD('" + ((CatTransacciones) event.getObject()).getCor_mov() + "', 4, '0');");
        macc.Desconectar();
        if ("true".equals(flg_ing_sal)) {
            titulo = "Cliente";
        } else {
            titulo = "Proveedor";
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            mfecha = format.parse(fec_tra);
        } catch (Exception ex) {

        }
        boolinout = flg_ing_sal;
        chkfecven = "false";
        cattransaccionesdetalle = null;
        detalles.clear();
        llenarDetalleMovimiento();
        llenarMovimientos();
        llenarProveedores();

        RequestContext.getCurrentInstance().execute("PF('wDTIngBuscar').clearFilters()");
        RequestContext.getCurrentInstance().execute("PF('wBuscarIng').hide()");
        RequestContext.getCurrentInstance().update("frmBuscarIng");
    }

    //*************************** Cambiar Fecha Vencimiento a Lote  ********************************
    public void iniciareditfechaven() {
        Boolean mValidar = true;
        if (det_lot == null) {
            det_lot = "";
        }
        if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País", 2);
        }
        if ("".equals(cod_art) || "0".equals(cod_art)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Producto", 2);
        }
        if ("".equals(det_lot.trim())) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar o Ingresar un Número de Lote", 2);
        }
        if (mValidar) {
            macc.Conectar();
            editfecvennomart = macc.strQuerySQLvariable("select concat(cod_art,' - ',det_nom) as art from inv_cat_articulo where cod_pai=" + cod_pai + " and id_art=" + cod_art + ";");
            macc.Desconectar();
            datefecvenedit = mfechaven;

            RequestContext.getCurrentInstance().update("frmEditFecVen");
            RequestContext.getCurrentInstance().execute("PF('wEditFecVen').show()");

        }
    }

    public void cerrareditfechaven() {
        editfecvennomart = "";
        datefecvenedit = null;
    }

    public void guardareditfechaven() {
        Boolean mValidar = true;

        if (datefecvenedit == null) {
            mValidar = false;
            addMessage("Modificar Fecha Vencimiento", "Debe Ingresar una Fecha de Vencimiento", 2);
        }

        if (mValidar) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                macc.Conectar();
                macc.dmlSQLvariable("update inv_tbl_transacciones_det as det "
                        + "left join inv_tbl_transacciones as mae on det.cod_tra = mae.cod_tra "
                        + "set fec_ven=str_to_date('" + format.format(datefecvenedit) + "','%d/%m/%Y') "
                        + "where mae.cod_pai=" + cod_pai + " "
                        + "and det.cod_art=" + cod_art + " "
                        + "and det.det_lot ='" + det_lot + "';"
                );
                macc.dmlSQLvariable("update inv_tbl_lot_ven "
                        + "set fec_ven=str_to_date('" + format.format(datefecvenedit) + "','%d/%m/%Y') "
                        + "where cod_pai=" + cod_pai + " "
                        + "and cod_art=" + cod_art + " "
                        + "and num_lot ='" + det_lot + "';"
                );
                macc.Desconectar();
                addMessage("Modificar Fecha Vencimiento", "Información almacenada con Éxito", 1);
            } catch (Exception ex) {
                addMessage("Modificar Fecha Vencimiento", "Error al guardar la información", 2);
                System.out.println("Error en Guardar Modificación Fec Ven de Inventario Productos ManInventarioProductos. " + ex.getMessage());
            }
        }

    }

    //****************************** IMPRIMIR ********************************************
    private String nombrereporte; //, nombreexportar;
    private Map<String, Object> parametros;

    public void imprimir() {
        try {
            parametros = new HashMap<>();
            parametros.put("cod_tra", cod_tra);
            parametros.put("cod_pai", cod_pai);
            nombrereporte = "/reportes/MovimientoAlmacen.jasper";
            //nombreexportar = "Movimiento_" + cor_mov;
            verPDF();
            parametros = null;
        } catch (Exception e) {
            System.out.println("Error en imprimir InvAlmacenProductos." + e.getMessage());
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
        } catch (Exception e) {
            System.out.println("Error en verPDF en InvAlmacenProductos." + e.getMessage());
        }
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

    //****************** GETTER Y SETTERS **********************************************
    public List<CatLotes> getLotes() {
        return lotes;
    }

    public void setLotes(List<CatLotes> lotes) {
        this.lotes = lotes;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public List<CatBodegas> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<CatBodegas> bodegas) {
        this.bodegas = bodegas;
    }

    public List<CatUbicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<CatUbicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<CatPaises> getPaisesbus() {
        return paisesbus;
    }

    public void setPaisesbus(List<CatPaises> paisesbus) {
        this.paisesbus = paisesbus;
    }

    public List<CatBodegas> getBodegasbus() {
        return bodegasbus;
    }

    public void setBodegasbus(List<CatBodegas> bodegasbus) {
        this.bodegasbus = bodegasbus;
    }

    public List<CatUbicaciones> getUbicacionesbus() {
        return ubicacionesbus;
    }

    public void setUbicacionesbus(List<CatUbicaciones> ubicacionesbus) {
        this.ubicacionesbus = ubicacionesbus;
    }

    public List<CatProveedores> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<CatProveedores> proveedores) {
        this.proveedores = proveedores;
    }

    public List<CatMovimientosInv> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<CatMovimientosInv> movimientos) {
        this.movimientos = movimientos;
    }

    public List<CatMovimientosInv> getMovimientostotal() {
        return movimientostotal;
    }

    public void setMovimientostotal(List<CatMovimientosInv> movimientostotal) {
        this.movimientostotal = movimientostotal;
    }

    public List<CatArticulos> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<CatArticulos> articulos) {
        this.articulos = articulos;
    }

    public CatTransacciones getCattransacciones() {
        return cattransacciones;
    }

    public void setCattransacciones(CatTransacciones cattransacciones) {
        this.cattransacciones = cattransacciones;
    }

    public List<CatTransacciones> getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(List<CatTransacciones> encabezado) {
        this.encabezado = encabezado;
    }

    public CatTransaccionesDetalle getCattransaccionesdetalle() {
        return cattransaccionesdetalle;
    }

    public void setCattransaccionesdetalle(CatTransaccionesDetalle cattransaccionesdetalle) {
        this.cattransaccionesdetalle = cattransaccionesdetalle;
    }

    public List<CatTransaccionesDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CatTransaccionesDetalle> detalles) {
        this.detalles = detalles;
    }

    public CatLotesVencimiento getCatlotesvencimiento() {
        return catlotesvencimiento;
    }

    public void setCatlotesvencimiento(CatLotesVencimiento catlotesvencimiento) {
        this.catlotesvencimiento = catlotesvencimiento;
    }

    public List<CatLotesVencimiento> getLotesvencimiento() {
        return lotesvencimiento;
    }

    public void setLotesvencimiento(List<CatLotesVencimiento> lotesvencimiento) {
        this.lotesvencimiento = lotesvencimiento;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getFlg_ing_sal() {
        return flg_ing_sal;
    }

    public void setFlg_ing_sal(String flg_ing_sal) {
        this.flg_ing_sal = flg_ing_sal;
    }

    public String getTip_mov() {
        return tip_mov;
    }

    public void setTip_mov(String tip_mov) {
        this.tip_mov = tip_mov;
    }

    public String getDoc_tra() {
        return doc_tra;
    }

    public void setDoc_tra(String doc_tra) {
        this.doc_tra = doc_tra;
    }

    public String getOrd_com() {
        return ord_com;
    }

    public void setOrd_com(String ord_com) {
        this.ord_com = ord_com;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getFlg_anu() {
        return flg_anu;
    }

    public void setFlg_anu(String flg_anu) {
        this.flg_anu = flg_anu;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNommov() {
        return nommov;
    }

    public void setNommov(String nommov) {
        this.nommov = nommov;
    }

    public String getNomclipro() {
        return nomclipro;
    }

    public void setNomclipro(String nomclipro) {
        this.nomclipro = nomclipro;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
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

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getDet_lot() {
        return det_lot;
    }

    public void setDet_lot(String det_lot) {
        this.det_lot = det_lot;
    }

    public String getFec_ven() {
        return fec_ven;
    }

    public void setFec_ven(String fec_ven) {
        this.fec_ven = fec_ven;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public String getNombod() {
        return nombod;
    }

    public void setNombod(String nombod) {
        this.nombod = nombod;
    }

    public String getNomubi() {
        return nomubi;
    }

    public void setNomubi(String nomubi) {
        this.nomubi = nomubi;
    }

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
    }

    public String getBoolinout() {
        return boolinout;
    }

    public void setBoolinout(String boolinout) {
        this.boolinout = boolinout;
    }

    public String getBooleditable() {
        return booleditable;
    }

    public void setBooleditable(String booleditable) {
        this.booleditable = booleditable;
    }

    public String getFecbus() {
        return fecbus;
    }

    public void setFecbus(String fecbus) {
        this.fecbus = fecbus;
    }

    public String getIdbus() {
        return idbus;
    }

    public void setIdbus(String idbus) {
        this.idbus = idbus;
    }

    public String getProbus() {
        return probus;
    }

    public void setProbus(String probus) {
        this.probus = probus;
    }

    public String getLotbus() {
        return lotbus;
    }

    public void setLotbus(String lotbus) {
        this.lotbus = lotbus;
    }

    public String getChklot() {
        return chklot;
    }

    public void setChklot(String chklot) {
        this.chklot = chklot;
    }

    public String getMovbus() {
        return movbus;
    }

    public void setMovbus(String movbus) {
        this.movbus = movbus;
    }

    public String getPaibus() {
        return paibus;
    }

    public void setPaibus(String paibus) {
        this.paibus = paibus;
    }

    public String getBodbus() {
        return bodbus;
    }

    public void setBodbus(String bodbus) {
        this.bodbus = bodbus;
    }

    public String getUbibus() {
        return ubibus;
    }

    public void setUbibus(String ubibus) {
        this.ubibus = ubibus;
    }

    public String getChkfecven() {
        return chkfecven;
    }

    public void setChkfecven(String chkfecven) {
        this.chkfecven = chkfecven;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImg_pro() {
        return img_pro;
    }

    public void setImg_pro(String img_pro) {
        this.img_pro = img_pro;
    }

    public String getNomprod() {
        return nomprod;
    }

    public void setNomprod(String nomprod) {
        this.nomprod = nomprod;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

    public Date getMfechaven() {
        return mfechaven;
    }

    public void setMfechaven(Date mfechaven) {
        this.mfechaven = mfechaven;
    }

    public List<CatAreasInv> getAreas() {
        return areas;
    }

    public void setAreas(List<CatAreasInv> areas) {
        this.areas = areas;
    }

    public String getCod_esp() {
        return cod_esp;
    }

    public void setCod_esp(String cod_esp) {
        this.cod_esp = cod_esp;
    }

    public String getCor_mov() {
        return cor_mov;
    }

    public void setCor_mov(String cor_mov) {
        this.cor_mov = cor_mov;
    }

    public String getCod_are() {
        return cod_are;
    }

    public void setCod_are(String cod_are) {
        this.cod_are = cod_are;
    }

    public Double getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(Double det_cos) {
        this.det_cos = det_cos;
    }

    public List<CatListados> getEspecialistas() {
        return especialistas;
    }

    public void setEspecialistas(List<CatListados> especialistas) {
        this.especialistas = especialistas;
    }

    public String getEditfecvennomart() {
        return editfecvennomart;
    }

    public void setEditfecvennomart(String editfecvennomart) {
        this.editfecvennomart = editfecvennomart;
    }

    public Date getDatefecvenedit() {
        return datefecvenedit;
    }

    public void setDatefecvenedit(Date datefecvenedit) {
        this.datefecvenedit = datefecvenedit;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public DefaultStreamedContent getMimagen() {
        return mimagen;
    }

    public void setMimagen(DefaultStreamedContent mimagen) {
        InvAlmacenProductos.mimagen = mimagen;
    }

    public String getCod_cli_pro() {
        return cod_cli_pro;
    }

    public void setCod_cli_pro(String cod_cli_pro) {
        this.cod_cli_pro = cod_cli_pro;
    }

    public String getObs_anu() {
        return obs_anu;
    }

    public void setObs_anu(String obs_anu) {
        this.obs_anu = obs_anu;
    }

    public String getUni_med_con() {
        return uni_med_con;
    }

    public void setUni_med_con(String uni_med_con) {
        this.uni_med_con = uni_med_con;
    }

    public Double getDet_can_con() {
        return det_can_con;
    }

    public void setDet_can_con(Double det_can_con) {
        this.det_can_con = det_can_con;
    }

    public List<CatListados> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<CatListados> unidades) {
        this.unidades = unidades;
    }

    public String getNombreunidad() {
        return nombreunidad;
    }

    public void setNombreunidad(String nombreunidad) {
        this.nombreunidad = nombreunidad;
    }

    public String getAnu_tip_mov() {
        return anu_tip_mov;
    }

    public void setAnu_tip_mov(String anu_tip_mov) {
        this.anu_tip_mov = anu_tip_mov;
    }

    public String getTra_tip_mov() {
        return tra_tip_mov;
    }

    public void setTra_tip_mov(String tra_tip_mov) {
        this.tra_tip_mov = tra_tip_mov;
    }

    public String getTra_cod_bod() {
        return tra_cod_bod;
    }

    public void setTra_cod_bod(String tra_cod_bod) {
        this.tra_cod_bod = tra_cod_bod;
    }

    public String getVen_cod_bod() {
        return ven_cod_bod;
    }

    public void setVen_cod_bod(String ven_cod_bod) {
        this.ven_cod_bod = ven_cod_bod;
    }

    public String getDet_pol() {
        return det_pol;
    }

    public void setDet_pol(String det_pol) {
        this.det_pol = det_pol;
    }
    
    

    /*
    public void stockinicial() {
        detalles.add(new CatTransaccionesDetalle("", "1", "1", "0", "496", "51051.79.05", "1/9/2017", "8", "", "", "", "8", "33", "198.621205693442", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "2", "1", "0", "496", "51051.03.03", "1/1/2018", "6", "", "", "", "6", "33", "148.965904270081", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "3", "1", "0", "496", "51051.05.01", "1/1/2018", "1831", "", "", "", "1831", "33", "45459.4284530865", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "4", "1", "0", "496", "51051.09.01", "28/2/2018", "8064", "", "", "", "8064", "33", "200210.175338989", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "5", "1", "0", "496", "51051.75.07", "1/4/2017", "36", "", "", "", "36", "33", "893.795425620488", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "6", "1", "0", "496", "51051.75.02", "1/2/2017", "12", "", "", "", "12", "33", "297.931808540163", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "7", "1", "0", "497", "50053.77.09", "1/9/2017", "20", "", "", "", "20", "33", "498.92102270854", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "8", "1", "0", "497", "50053.78.10", "1/12/2017", "128", "", "", "", "128", "33", "3193.09454533466", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "9", "1", "0", "497", "50053.05.03", "1/1/2018", "199", "", "", "", "199", "33", "4964.26417594997", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "10", "1", "0", "497", "50053.05.09", "28/2/2018", "5376", "", "", "", "5376", "33", "134109.970904056", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "11", "1", "0", "498", "50160.71.01", "1/9/2017", "8", "", "", "", "8", "33", "292.612470082602", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "12", "1", "0", "498", "50160.73.02", "1/5/2018", "12", "", "", "", "12", "33", "438.918705123903", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "13", "1", "0", "498", "50160.74.01", "1/6/2018", "12", "", "", "", "12", "33", "438.918705123903", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "14", "1", "0", "498", "50160.74.02", "1/8/2018", "36", "", "", "", "36", "33", "1316.75611537171", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "15", "1", "0", "499", "50520.24.01", "1/1/2018", "154", "", "", "", "154", "33", "4077.43999725992", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "16", "1", "0", "499", "50520.26.13", "1/3/2018", "4", "", "", "", "4", "33", "105.907532396362", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "17", "1", "0", "499", "50520.29.08", "1/7/2018", "422", "", "", "", "422", "33", "11173.2446678161", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "18", "1", "0", "499", "50520.30.02", "31/8/2018", "1344", "", "", "", "1344", "33", "35584.9308851775", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "19", "1", "0", "500", "50549.43.02", "1/8/2018", "3856", "", "", "", "3856", "33", "136097.436863076", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "20", "1", "0", "500", "50549.39.03", "1/8/2016", "36", "", "", "", "36", "33", "1270.6192238254", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "21", "1", "0", "500", "50549.44.01", "31/8/2018", "4032", "", "", "", "4032", "33", "142309.353068445", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "22", "1", "0", "501", "50560.79.04", "1/9/2017", "26", "", "", "", "26", "33", "1468.7800638834", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "23", "1", "0", "501", "50560.82.02", "31/3/2018", "36", "", "", "", "36", "33", "2033.69547306933", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "24", "1", "0", "502", "50110.27.04", "28/2/2018", "17472", "", "", "", "17472", "33", "611726.985434836", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "25", "1", "0", "502", "50110.26.04", "1/1/2018", "875", "", "", "", "875", "33", "30635.3658571132", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "26", "1", "0", "502", "50110.26.05", "1/1/2018", "1344", "", "", "", "1344", "33", "47055.9219565258", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "27", "1", "0", "503", "51090.86.02", "1/12/2017", "5213", "", "", "", "5213", "33", "121339.087996676", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "28", "1", "0", "503", "51090.02.01", "31/12/2017", "6720", "", "", "", "6720", "33", "156416.39580619", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "29", "1", "0", "504", "50092.92.03", "1/11/2017", "19", "", "", "", "19", "33", "408.969712114214", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "30", "1", "0", "504", "50092.03.08", "1/1/2018", "7", "", "", "", "7", "33", "150.673051831553", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "31", "1", "0", "504", "50092.07.01", "1/1/2018", "2503", "", "", "", "2503", "33", "53876.3783906252", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "32", "1", "0", "504", "50092.07.04", "28/2/2018", "8064", "", "", "", "8064", "33", "173575.355709949", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "33", "1", "0", "505", "50531.12.06", "1/3/2018", "21", "", "", "", "21", "33", "763.975373032066", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "34", "1", "0", "505", "50531.13.02", "1/4/2018", "12", "", "", "", "12", "33", "436.557356018323", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "35", "1", "0", "505", "50531.15.06", "1/7/2018", "20", "", "", "", "20", "33", "727.595593363872", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "36", "1", "0", "505", "50531.17.02", "30/9/2018", "15664", "", "", "", "15664", "33", "569852.868722585", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "37", "1", "0", "506", "50961.68.03", "1/8/2017", "4", "", "", "", "4", "33", "93.8809964485342", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "38", "1", "0", "506", "50961.68.05", "1/9/2017", "12", "", "", "", "12", "33", "281.642989345603", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "39", "1", "0", "506", "50961.01.02", "1/12/2017", "12", "", "", "", "12", "33", "281.642989345603", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "40", "1", "0", "506", "50961.02.01", "1/12/2017", "336", "", "", "", "336", "33", "7886.00370167687", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "41", "1", "0", "506", "50961.05.01", "1/1/2018", "2688", "", "", "", "2688", "33", "63088.029613415", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "42", "1", "0", "506", "50961.06.05", "31/3/2018", "4032", "", "", "", "4032", "33", "94632.0444201225", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "43", "1", "0", "506", "50961.66.02", "1/4/2017", "728", "", "", "", "728", "33", "17086.3413536332", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "44", "1", "0", "506", "50961.64.07", "1/1/2017", "96", "", "", "", "96", "33", "2253.14391476482", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "45", "1", "0", "507", "50372.71.01", "1/12/2017", "108", "", "", "", "108", "33", "5904.18462359509", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "46", "1", "0", "508", "50380.91.01", "1/12/2017", "108", "", "", "", "108", "33", "5904.18462359509", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "47", "1", "0", "509", "50390.31.01", "30/9/2018", "108", "", "", "", "108", "33", "2854.92646592915", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "48", "1", "0", "510", "45140.06.02", "1/6/2018", "9", "", "", "", "9", "32", "4661.19838704875", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "49", "1", "0", "511", "12091.31.10", "1/6/2018", "17", "", "", "", "17", "56", "1368.3221308339", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "50", "1", "0", "511", "12091.31.30", "1/6/2018", "20", "", "", "", "20", "56", "1609.79074215752", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "51", "1", "0", "512", "10301.32.10", "1/7/2018", "8", "", "", "", "8", "56", "641.844813890768", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "52", "1", "0", "512", "10301.32.30", "1/7/2018", "10", "", "", "", "10", "56", "802.306017363459", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "53", "1", "0", "512", "10301.33.10", "1/11/2018", "20", "", "", "", "20", "56", "1604.61203472692", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "54", "1", "0", "513", "11280.89.30", "1/5/2018", "4", "", "", "", "4", "56", "568.330417686647", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "55", "1", "0", "513", "11280.90.10.", "1/9/2018", "10", "", "", "", "10", "56", "1420.82604421662", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "56", "1", "0", "513", "11280.90.20", "1/9/2018", "30", "", "", "", "30", "56", "4262.47813264985", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "57", "1", "0", "514", "14060.52.30", "1/7/2018", "1", "", "", "", "1", "56", "171.78", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "58", "1", "0", "515", "11810.96.10", "1/4/2018", "7", "", "", "", "7", "56", "775.27799289595", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "59", "1", "0", "515", "11810.94.10", "1/11/2017", "3", "", "", "", "3", "56", "332.261996955407", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "60", "1", "0", "515", "11810.98.10", "1/12/2018", "3", "", "", "", "3", "56", "332.261996955407", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "61", "1", "0", "518", "45630.25.13", "1/6/2018", "19", "", "", "", "19", "32", "6590.24218960787", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "62", "1", "0", "519", "10950.55.10", "1/2/2018", "90", "", "", "", "90", "56", "3808.31664473684", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "63", "1", "0", "520", "05751.57.10", "1/4/2018", "1", "", "", "", "1", "56", "212.985771868493", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "64", "1", "0", "520", "05751.63.10", "1/10/2018", "4", "", "", "", "4", "56", "851.94308747397", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "65", "1", "0", "521", "2016.04", "30/6/2022", "30", "", "", "", "30", "56", "5656.74692811777", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "66", "1", "0", "521", "2016.05", "30/6/2022", "199", "", "", "", "199", "56", "37523.0879565146", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "67", "1", "0", "521", "2017.01", "31/12/2024", "108", "", "", "", "108", "56", "20364.288941224", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "68", "1", "0", "522", "05761.78.30", "1/9/2018", "8", "", "", "", "8", "56", "2448.17328602748", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "69", "1", "0", "522", "05761.09.30", "1/12/2018", "40", "", "", "", "40", "56", "12240.8664301374", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "70", "1", "0", "523", "05761.45.30", "1/6/2018", "9", "", "", "", "9", "1", "11180.4129298487", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "71", "1", "0", "523", "05761.46.30", "1/6/2018", "3", "", "", "", "3", "1", "3726.80430994958", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "72", "1", "0", "523", "05761.91.30", "1/11/2018", "5", "", "", "", "5", "1", "6211.34051658263", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "73", "1", "0", "523", "05761.02.30", "1/11/2018", "10", "", "", "", "10", "1", "12422.6810331653", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "74", "1", "0", "523", "05761.11.30", "1/1/2019", "9", "", "", "", "9", "1", "11180.4129298487", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "75", "1", "0", "523", "05761.25.30", "1/2/2019", "31", "", "", "", "31", "1", "38510.3112028123", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "76", "1", "0", "524", "", "", "724", "", "", "", "724", "1", "23553.9078305452", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "77", "1", "0", "525", "", "", "1166", "", "", "", "1166", "1", "41077.6668434712", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "78", "1", "0", "526", "160808", "30/8/2019", "7", "", "", "", "7", "31", "4830.37390377652", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "79", "1", "0", "527", "160511", "1/5/2018", "1", "", "", "", "1", "31", "507.459000905672", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "80", "1", "0", "527", "1609065", "1/9/2018", "3", "", "", "", "3", "31", "1522.37700271702", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "81", "1", "0", "527", "1611016", "1/11/2018", "1", "", "", "", "1", "31", "507.459000905672", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "82", "1", "0", "528", "", "", "1000", "", "", "", "1000", "33", "0", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "83", "1", "0", "529", "05840.35.10", "1/2/2019", "72", "", "", "", "72", "56", "4972.64199652856", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "84", "1", "0", "530", "05820.10.10", "1/1/2018", "17", "", "", "", "17", "56", "1342.93713350629", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "85", "1", "0", "530", "05820.12.10", "1/6/2018", "120", "", "", "", "120", "56", "9479.55623651499", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "86", "1", "0", "531", "MJOS10638", "29/9/2019", "5", "", "", "", "5", "31", "1897.3", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "87", "1", "0", "369", "33940", "30/11/2018", "42", "", "", "", "42", "32", "2398.3148289606", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "88", "1", "0", "369", "33940", "30/11/2018", "42", "", "", "", "42", "32", "2398.3148289606", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "89", "1", "0", "371", "40340", "30/11/2019", "8", "", "", "", "8", "32", "5065.57628977316", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "90", "1", "0", "372", "65890", "31/1/2019", "1", "", "", "", "1", "32", "480.146935126117", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "91", "1", "0", "373", "26411", "31/1/2019", "1", "", "", "", "1", "32", "516.279274366912", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "92", "1", "0", "373", "26421", "30/9/2019", "20", "", "", "", "20", "32", "10325.5854873382", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "93", "1", "0", "374", "26412", "31/1/2019", "2", "", "", "", "2", "32", "963.068674378564", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "94", "1", "0", "374", "26422", "30/9/2019", "20", "", "", "", "20", "32", "9630.68674378564", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "95", "1", "0", "378", "54621", "31/3/2019", "5", "", "", "", "5", "32", "2097.13479304394", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "96", "1", "0", "378", "54631", "30/9/2019", "5", "", "", "", "5", "32", "2097.13479304394", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "97", "1", "0", "379", "54622", "31/3/2019", "5", "", "", "", "5", "32", "3374.30765913175", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "98", "1", "0", "379", "54632", "30/9/2019", "5", "", "", "", "5", "32", "3374.30765913175", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "99", "1", "0", "380", "54623", "31/3/2019", "5", "", "", "", "5", "32", "6692.66126093446", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "100", "1", "0", "380", "54633", "30/9/2019", "5", "", "", "", "5", "32", "6692.66126093446", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "101", "1", "0", "381", "119640", "30/4/2019", "50", "", "", "", "50", "33", "19564.6777187788", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "102", "1", "0", "382", "107690", "28/2/2019", "71", "", "", "", "71", "33", "46825.8005588105", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "103", "1", "0", "383", "110560", "30/9/2017", "24", "", "", "", "24", "33", "19792.2828216735", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "104", "1", "0", "384", "130040", "30/9/2018", "26", "", "", "", "26", "33", "21801.063213999", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "105", "1", "0", "390", "34460", "30/4/2018", "4", "", "", "", "4", "32", "7178.91904862651", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "106", "1", "0", "397", "5H1082", "30/11/2016", "2", "", "", "", "2", "32", "733.734282537866", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "107", "1", "0", "397", "6G0085", "15/12/2017", "2", "", "", "", "2", "32", "733.734282537866", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "108", "1", "0", "397", "6K0087", "30/3/2018", "2", "", "", "", "2", "32", "733.734282537866", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "109", "1", "0", "402", "5J0015", "30/7/2016", "4", "", "", "", "4", "32", "5396.87127316638", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "110", "1", "0", "402", "7A0027", "30/5/2018", "3", "", "", "", "3", "32", "4047.65345487478", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "111", "1", "0", "404", "6D2614", "15/7/2017", "3", "", "", "", "3", "32", "3351.64470384415", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "112", "1", "0", "405", "6C2612", "30/6/2017", "2", "", "", "", "2", "32", "779.090706213096", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "113", "1", "0", "408", "7A0087", "15/12/2017", "2", "", "", "", "2", "32", "3264.23762059144", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "114", "1", "0", "410", "280300", "30/4/2017", "1", "", "", "", "1", "32", "1333.20121140056", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "115", "1", "0", "415", "241300", "31/12/2016", "1", "", "", "", "1", "32", "1836.64219285832", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "116", "1", "0", "420", "7B0046", "30/1/2018", "2", "", "", "", "2", "32", "4051.82351983498", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "117", "1", "0", "420", "6E0040", "2/2/2017", "2", "", "", "", "2", "32", "4051.82351983498", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "118", "1", "0", "421", "64082926", "30/4/2018", "1", "", "", "", "1", "32", "1857.25763440182", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "119", "1", "0", "421", "64088556", "31/5/2018", "18", "", "", "", "18", "32", "33430.6374192328", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "120", "1", "0", "422", "64085409", "31/3/2018", "2", "", "", "", "2", "32", "7285.20188104019", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "121", "1", "0", "423", "64073837", "31/12/2017", "1", "", "", "", "1", "32", "3225.12843124418", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "122", "1", "0", "423", "64084746", "31/5/2018", "2", "", "", "", "2", "32", "6450.25686248836", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "123", "1", "0", "426", "", "", "14", "", "", "", "14", "96", "1091.85870172181", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "124", "1", "0", "428", "213628X", "12/11/2016", "200", "", "", "", "200", "32", "2117.32970954327", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "125", "1", "0", "429", "81306141X", "30/3/2018", "300", "", "", "", "300", "32", "1404.56902668462", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "126", "1", "0", "430", "219701X", "30/10/2018", "200", "", "", "", "200", "32", "1757.34841798867", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "127", "1", "0", "431", "220647X", "30/3/2018", "300", "", "", "", "300", "32", "2635.74420256554", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "128", "1", "0", "431", "220541X", "28/2/2017", "300", "", "", "", "300", "32", "2635.74420256554", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "129", "1", "0", "432", "218648X", "30/9/2017", "900", "", "", "", "900", "32", "7903.98432282628", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "130", "1", "0", "434", "118044X", "30/6/2017", "100", "", "", "", "100", "32", "1023.08114289373", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "131", "1", "0", "435", "119028X", "4/6/2017", "1", "", "", "", "1", "32", "466.679608421268", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "132", "1", "0", "437", "133039XA", "3/5/2017", "1", "", "", "", "1", "32", "501.711437016087", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "133", "1", "0", "437", "133358XA", "20/9/2017", "1", "", "", "", "1", "32", "501.711437016087", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "134", "1", "0", "438", "212535X", "30/12/2016", "100", "", "", "", "100", "32", "556.438906885081", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "135", "1", "0", "439", "022045XB", "16/7/2017", "600", "", "", "", "600", "32", "6274.22798290045", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "136", "1", "0", "441", "239006XB", "20/9/2018", "100", "", "", "", "100", "32", "4513.68736530541", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "137", "1", "0", "443", "223639X", "30/8/2018", "100", "", "", "", "100", "32", "615.864224952678", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "138", "1", "0", "450", "133393B", "24/8/2017", "1", "", "", "", "1", "32", "681.3748502676", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "139", "1", "0", "454", "161023XB", "7/12/2016", "100", "", "", "", "100", "32", "724.851257835472", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "140", "1", "0", "455", "162009XD", "22/1/2017", "1", "", "", "", "1", "32", "475.247471460342", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "141", "1", "0", "455", "162010XA", "30/11/2017", "4", "", "", "", "4", "32", "1900.98988584137", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "142", "1", "0", "456", "167028XA", "5/3/2018", "800", "", "", "", "800", "32", "5780.93865290324", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "143", "1", "0", "457", "168014XD", "27/2/2017", "1", "", "", "", "1", "32", "453.036368983055", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "144", "1", "0", "457", "168015XC", "16/10/2017", "2", "", "", "", "2", "32", "906.072737966109", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "145", "1", "0", "458", "036082X", "11/4/2017", "600", "", "", "", "600", "32", "4518.21117797034", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "146", "1", "0", "459", "057041XB", "7/6/2017", "2", "", "", "", "2", "32", "944.911569237237", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "147", "1", "0", "460", "037104XA", "4/4/2017", "500", "", "", "", "500", "32", "3596.01932683725", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "148", "1", "0", "461", "058038XB", "3/7/2017", "1", "", "", "", "1", "32", "458.701597423242", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "149", "1", "0", "461", "058039XA", "25/2/2018", "2", "", "", "", "2", "32", "917.403194846485", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "150", "1", "0", "462", "004070XA", "11/3/2017", "100", "", "", "", "100", "32", "786.441562339544", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "151", "1", "0", "463", "005037X", "23/1/2017", "1", "", "", "", "1", "32", "461.587326671536", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "152", "1", "0", "463", "005038XC", "6/9/2017", "2", "", "", "", "2", "32", "923.174653343072", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "153", "1", "0", "464", "035100XA", "26/10/2016", "100", "", "", "", "100", "32", "753.013506183511", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "154", "1", "0", "464", "035101X", "4/4/2017", "100", "", "", "", "100", "32", "753.013506183511", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "155", "1", "0", "465", "055041XA", "20/6/2017", "1", "", "", "", "1", "32", "475.797805336923", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "156", "1", "0", "465", "055041XC", "20/6/2017", "3", "", "", "", "3", "32", "1427.39341601077", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "157", "1", "0", "466", "187018X", "24/5/2018", "3900", "", "", "", "3900", "32", "28522.5251426864", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "158", "1", "0", "467", "188011X", "7/4/2017", "2", "", "", "", "2", "32", "933.707017532593", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "159", "1", "0", "467", "188013X", "29/11/2017", "4", "", "", "", "4", "32", "1867.41403506519", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "160", "1", "0", "468", "043055X", "30/10/2017", "1600", "", "", "", "1600", "32", "14078.3675151607", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "161", "1", "0", "469", "068044XA", "8/3/2017", "2", "", "", "", "2", "32", "906.116578386278", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "162", "1", "0", "469", "068046X", "18/1/2018", "3", "", "", "", "3", "32", "1359.17486757942", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "163", "1", "0", "470", "132034X", "7/10/2017", "2400", "", "", "", "2400", "32", "18575.6007454042", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "164", "1", "0", "471", "133018X", "13/4/2017", "1", "", "", "", "1", "32", "561.462164530342", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "165", "1", "0", "471", "133020X", "19/1/2018", "1", "", "", "", "1", "32", "561.462164530342", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "166", "1", "0", "472", "135025XE", "15/1/2018", "1200", "", "", "", "1200", "32", "18814.2012439913", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "167", "1", "0", "473", "136016XB", "20/5/2018", "4", "", "", "", "4", "32", "1900.37084179073", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "168", "1", "0", "474", "189004X", "30/11/2017", "7200", "", "", "", "7200", "32", "66908.7428777612", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "169", "1", "0", "478", "091063XB", "29/8/2017", "1000", "", "", "", "1000", "32", "4461.73117818087", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "170", "1", "0", "479", "092033XD", "18/12/2017", "1", "", "", "", "1", "32", "888.037263485136", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "171", "1", "0", "479", "092034X", "20/9/2018", "6", "", "", "", "6", "32", "5328.22358091082", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "172", "1", "0", "480", "139032XA", "10/10/2017", "800", "", "", "", "800", "32", "6808.8687572485", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "173", "1", "0", "481", "140018XA", "13/9/2018", "1", "", "", "", "1", "32", "560.678689038631", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "174", "1", "0", "481", "140018XC", "13/9/2018", "2", "", "", "", "2", "32", "1121.35737807726", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "175", "1", "0", "482", "550130", "8/12/2017", "9", "", "", "", "9", "31", "1095.35051999856", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "176", "1", "0", "482", "603118", "17/1/2018", "30", "", "", "", "30", "31", "3651.16839999519", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "177", "1", "0", "482", "639113", "19/9/2018", "30", "", "", "", "30", "31", "3651.16839999519", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "178", "1", "0", "484", "177026X", "30/11/2017", "1", "", "", "", "1", "33", "467.245730183934", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "179", "1", "0", "484", "177016X", "30/10/2016", "1", "", "", "", "1", "33", "467.245730183934", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "180", "1", "0", "485", "60101148134001", "1/2/2019", "2", "", "", "", "2", "1", "896.191556839128", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "181", "1", "0", "485", "60101119724001", "1/5/2018", "28", "", "", "", "28", "1", "12546.6817957478", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "182", "1", "0", "485", "60101148134001", "1/2/2019", "8", "", "", "", "8", "1", "3584.76622735651", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "183", "1", "0", "485", "60101159701001", "1/5/2019", "8", "", "", "", "8", "1", "3584.76622735651", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "184", "1", "0", "485", "60101149614001", "1/3/2019", "4", "", "", "", "4", "1", "1792.38311367826", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "185", "1", "0", "485", "60101151244002", "1/4/2019", "4", "", "", "", "4", "1", "1792.38311367826", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "186", "1", "0", "486", "F166925K", "1/5/2019", "48", "", "", "", "48", "1", "14819.2765151312", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "187", "1", "0", "486", "E1613392", "1/5/2018", "12", "", "", "", "12", "1", "3704.81912878279", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "188", "1", "0", "486", "E163725Q", "1/11/2018", "12", "", "", "", "12", "1", "3704.81912878279", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "189", "1", "0", "486", "F169061Q", "1/11/2019", "48", "", "", "", "48", "1", "14819.2765151312", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "190", "1", "0", "486", "E160806L", "1/5/2018", "9", "", "", "", "9", "1", "2778.61434658709", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "191", "1", "0", "486", "F166925K", "1/5/2019", "1", "", "", "", "1", "1", "308.734927398566", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "192", "1", "0", "487", "548151", "30/4/2017", "3", "", "", "", "3", "1", "1247.77208298388", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "193", "1", "0", "487", "623190", "30/11/2017", "18", "", "", "", "18", "1", "7486.63249790327", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "194", "1", "0", "489", "516108", "30/3/2017", "2", "", "", "", "2", "32", "888.467460521805", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "195", "1", "0", "489", "621104", "30/5/2018", "1", "", "", "", "1", "32", "444.233730260902", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "196", "1", "0", "489", "621105", "30/5/2018", "1", "", "", "", "1", "32", "444.233730260902", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "197", "1", "0", "490", "233533", "30/8/2016", "1", "", "", "", "1", "32", "984.077969189425", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "198", "1", "0", "490", "233619", "28/2/2017", "1", "", "", "", "1", "32", "984.077969189425", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "199", "1", "0", "491", "025040X", "17/10/2017", "2", "", "", "", "2", "32", "6292.17605121492", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "200", "1", "0", "492", "L1G5", "1/7/2017", "1", "", "", "", "1", "31", "252.45", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "201", "1", "0", "492", "L1H3", "1/5/2017", "2", "", "", "", "2", "31", "504.9", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "202", "1", "0", "493", "97121", "30/9/2016", "2", "", "", "", "2", "32", "1032.48", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "203", "1", "0", "494", "14199288", "", "7", "", "", "", "7", "1", "3902.78", "", ""));
        detalles.add(new CatTransaccionesDetalle("", "204", "1", "0", "495", "607351", "30/7/2017", "2", "", "", "", "2", "32", "2043.49688475796", "", ""));

    }
     */

    public String getOtr_sol() {
        return otr_sol;
    }

    public void setOtr_sol(String otr_sol) {
        this.otr_sol = otr_sol;
    }
}
