package tt.productos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.CatTransaccionesDetalle;
import tt.general.ListaCortaTransaccionExistencias;
import tt.general.Login;

@Named
@ConversationScoped

public class InvPedidos implements Serializable {

    private static final long serialVersionUID = 7468359726542638L;
    @Inject
    Login cbean;

    private Accesos macc;

    private List<CatListados> paises;
    private List<CatListados> proveedores;
    private List<CatListados> areas;
    private List<CatListados> movimientos;
    private List<CatListados> especialistas;

    private List<CatListados> articulos;
    private List<CatListados> unidades;
    private List<CatListados> bodegas;
    private List<CatListados> ubicaciones;
    private List<CatListados> lotes;

    private CatPedidos catpedidos;
    private CatPedidosDetalles catpedidosdetalles;
    private List<CatPedidos> encabezado;
    private List<CatPedidosDetalles> detalles;

    private List<CatPedidos> encabezadotransac;
    private List<CatPedidosDetalles> detallestransac;

    private CatPedidosDetalles catpedidosdetallesagregar;
    private List<CatPedidosDetalles> detallesagregar;

    private List<CatTransaccionesDetalle> historicotransac;
    private List<ListaCortaTransaccionExistencias> hisexitransac;

    private String cod_ped, cod_pai, fec_ped, flg_ing_sal, tip_mov, doc_tra, ord_com,
            cod_cli_pro, det_obs, cod_usu, flg_anu, cod_esp, cor_mov, cod_are, obs_anu, det_sta, flg_val;

    private String det_ped_det, det_cod_bod, det_cod_ubi, det_cod_art, det_det_lot, det_fec_ven;
    private Double det_can_sol, det_can_ent, det_can_pen, det_det_cos, det_can_sol_con;
    private String det_uni_med_con, det_det_sta, det_tra_mae, det_tra_det, det_fec_tra, det_lot_sug;
    private String unimedori, nomunimed, rut_ima, nombrearchivo;

    private String mae_tra, fec_tra, mindex, add_ped_det, menabled, sum_cli_buscar;

    private Date mfechatra, mfechaven, mfechatransac;

    private String mheight, nomprod;

    private static DefaultStreamedContent mimagen;

    private String sta_cod_ped, sta_flg_val, sta_num_fac, sta_cli_buscar;
    private CatPedidos catpedidospedidos;
    private List<CatPedidos> encabezadopedidos;
    private List<CatPedidosDetalles> detallespedidos;

    //****************** Operaciones iniciales *********************************
    public void iniciarventana() {
        macc = new Accesos();

        //******* Fechas ***********
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfechatra = Date.from(Instant.now());
        mfechaven = null;
        mfechatransac = Date.from(Instant.now());

        //******* Suministrar **********
        mae_tra = "";
        fec_tra = format.format(mfechatra);
        mindex = "1";
        menabled = "false";
        sum_cli_buscar = "";

        //******* Encabezado *******
        cod_ped = "";
        cod_pai = cbean.getCod_pai();
        fec_ped = format.format(mfechatra);
        flg_ing_sal = "true";
        tip_mov = "0";
        doc_tra = "";
        ord_com = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        cod_esp = "0";
        cor_mov = "";
        cod_are = "0";
        obs_anu = "";
        det_sta = "PENDIENTE";
        rut_ima = "";
        nombrearchivo = "Sin Seleccionar";
        flg_val = "false";

        //****** Detalles ********
        det_ped_det = "";
        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_cod_art = "0";
        det_det_lot = "";
        det_fec_ven = "";
        det_can_sol = 0.0;
        det_can_ent = 0.0;
        det_can_pen = 0.0;
        det_det_cos = 0.0;
        det_can_sol_con = 0.0;
        det_uni_med_con = "0";
        det_det_sta = "PENDIENTE";
        det_tra_mae = "";
        det_tra_det = "";
        det_fec_tra = format.format(mfechatra);
        unimedori = "0";
        nomunimed = "";
        det_lot_sug = "";

        //********Status *************
        catpedidospedidos = new CatPedidos();
        encabezadopedidos = new ArrayList<>();
        detallespedidos = new ArrayList<>();

        //****** Agregar *************
        add_ped_det = "";

        //***** Listados ******
        paises = new ArrayList<>();
        proveedores = new ArrayList<>();
        areas = new ArrayList<>();
        movimientos = new ArrayList<>();
        especialistas = new ArrayList<>();

        articulos = new ArrayList<>();
        unidades = new ArrayList<>();
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        lotes = new ArrayList<>();

        catpedidos = new CatPedidos();
        catpedidosdetalles = new CatPedidosDetalles();
        catpedidosdetallesagregar = new CatPedidosDetalles();

        encabezado = new ArrayList<>();
        detalles = new ArrayList<>();

        encabezadotransac = new ArrayList<>();
        detallestransac = new ArrayList<>();
        detallesagregar = new ArrayList<>();
        historicotransac = new ArrayList<>();
        hisexitransac = new ArrayList<>();

        llenarPaises();
        llenarProveedores();
        llenarAreas();
        llenarEspecialistas();
        llenarUnidades();
        llenarArticulos();

        llenarMovimientos();
        llenarBodegas();

        llenarPedidosTransac();

    }

    public void cerrarventana() {
        //******* Fechas ***********
        mfechatra = null;
        mfechaven = null;
        mfechatransac = null;

        //******* Suministrar **********
        mae_tra = null;
        fec_tra = null;
        mindex = null;
        sum_cli_buscar = null;

        //******* Encabezdo *******
        cod_ped = null;
        cod_pai = null;
        fec_ped = null;
        flg_ing_sal = null;
        tip_mov = null;
        doc_tra = null;
        ord_com = null;
        cod_cli_pro = null;
        det_obs = null;
        cod_usu = null;
        flg_anu = null;
        cod_esp = null;
        cor_mov = null;
        cod_are = null;
        obs_anu = null;
        det_sta = null;
        rut_ima = null;
        nombrearchivo = null;
        flg_val = null;

        //****** Detalles ********
        det_ped_det = null;
        det_cod_bod = null;
        det_cod_ubi = null;
        det_cod_art = null;
        det_det_lot = null;
        det_fec_ven = null;
        det_can_sol = 0.0;
        det_can_ent = 0.0;
        det_can_pen = 0.0;
        det_det_cos = 0.0;
        det_can_sol_con = 0.0;
        det_uni_med_con = null;
        det_det_sta = null;
        det_tra_mae = null;
        det_tra_det = null;
        det_fec_tra = null;
        unimedori = null;
        nomunimed = null;
        det_lot_sug = null;

        //******** Status ************
        sta_cod_ped = null;
        sta_flg_val = null;
        sta_num_fac = null;
        sta_cli_buscar = null;

        catpedidospedidos = null;
        encabezadopedidos = null;
        detallespedidos = null;

        //****** Agregar *************
        add_ped_det = "";

        //***** Listados ******
        paises = null;
        proveedores = null;
        areas = null;
        movimientos = null;
        especialistas = null;

        articulos = null;
        unidades = null;
        bodegas = null;
        ubicaciones = null;
        lotes = null;

        catpedidos = null;
        catpedidosdetalles = null;
        catpedidosdetallesagregar = null;

        encabezado = null;
        detalles = null;

        encabezadotransac = null;
        detallestransac = null;
        detallesagregar = null;
        historicotransac = null;
        hisexitransac = null;
        macc = null;

    }

    //********************************** Llenar Catálogos **********************
    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from inv_cat_pai order by nom_pai;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
            System.out.println("Error en el llenado de Paises en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarProveedores() {
        String mQuery = "";
        try {

            proveedores.clear();

            mQuery = "select cod_cli,nom_cli "
                    + "from inv_cat_cli where cod_pai = " + cod_pai + " order by nom_cli;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
            System.out.println("Error en el llenado de Proveedores en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAreas() {
        try {
            areas.clear();

            String mQuery = "select cod_are, nom_are "
                    + "from inv_cat_are order by nom_are;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                areas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Áreas en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarMovimientos() {
        String mQuery = "";
        try {

            movimientos.clear();

            mQuery = "select id_mov, nom_mov "
                    + "from inv_cat_mov "
                    + "where cod_pai =" + cod_pai + " "
                    + "and flg_tip = 1 "
                    + "order by nom_mov;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                movimientos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Movimientos en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEspecialistas() {
        String mQuery = "";
        especialistas.clear();
        try {
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

        } catch (Exception e) {
            System.out.println("Error en el llenado de Especiaistas en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarArticulos() {
        String mQuery = "";
        try {
            lotes.clear();
            articulos.clear();

            mQuery = "select art.id_art, concat(ifnull(art.cod_art,''),'--',ifnull(cod_alt,''),'--',art.det_nom) as art "
                    + "from inv_cat_articulo as art "
                    + "where art.cod_pai = " + cod_pai + " "
                    + "order by art.cod_art; ";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                articulos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Artículos en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
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
            System.out.println("Error en el llenado de Unidades de Medida en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {

            bodegas.clear();

            mQuery = "select id_bod, nom_bod "
                    + "from inv_cat_bodegas "
                    + "where cod_pai=" + cod_pai + " "
                    + "order by nom_bod;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
            System.out.println("Error en el llenado de Bodegas en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select ubi.id_ubi,ubi.nom_ubi "
                    + "from inv_cat_ubicaciones as ubi "
                    + "where ubi.cod_bod = " + det_cod_bod + " "
                    + "order by ubi.nom_ubi;";
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
            System.out.println("Error en el llenado de Ubicaciones en ManPedidos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarLotes() {
        try {

            lotes.clear();

            String mQuery = "select '' as lote union all "
                    + "(select t.lote from (select distinct trim(det.det_lot) as lote  "
                    + "from inv_tbl_transacciones as mae  "
                    + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra  "
                    + "where  "
                    + "mae.cod_pai = " + cod_pai + " "
                    + "AND det.cod_bod = " + det_cod_bod + " "
                    + "AND det.cod_art = " + det_cod_art + " "
                    + ") as t "
                    + "where ("
                    + "  select exi_act_lot "
                    + "  FROM inv_tbl_historico "
                    + "  where cod_pai = " + cod_pai + " "
                    + "  and cod_bod = " + det_cod_bod + " "
                    + "  and cod_art = " + det_cod_art + " "
                    + "  and det_lot = t.lote "
                    + "  order by fec_tra desc, ord_dia desc limit 1 "
                    + ") > 0 "
                    + "order by t.lote asc) "
                    + ";";

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
            System.out.println("Error en el llenado de Lotes en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarPedidos() {
        try {
            catpedidos = null;
            encabezado.clear();

            String mQuery = "select  "
                    + "ped.cod_ped,  "
                    + "ped.cod_pai,  "
                    + "case date_format(ped.fec_ped,'%d/%m/%Y') when '00/00/0000' then '' else date_format(ped.fec_ped,'%d/%m/%Y') end as fecped,  "
                    + "ped.flg_ing_sal,  "
                    + "ped.tip_mov,  "
                    + "ped.doc_tra,  "
                    + "ped.ord_com, "
                    + "ped.cod_cli_pro,  "
                    + "ped.det_obs,  "
                    + "ped.cod_usu,  "
                    + "ped.flg_anu,  "
                    + "ped.cod_esp,  "
                    + "ped.cor_mov,  "
                    + "ped.cod_are,  "
                    + "ped.obs_anu,  "
                    + "ped.det_sta,  "
                    + "ifnull(usu.det_nom,'') as nomusu, "
                    + "ifnull(cli.nom_cli,'') as nomcli, "
                    + "ped.flg_val,"
                    + "case ped.flg_val "
                    + "when 'true' then 'Vale' "
                    + "when 'false' then 'Orden' "
                    + "when 'cotiza' then 'Cotización' "
                    + "when 'repos' then 'Repos/Cambio' "
                    + "end as valflg "
                    + "from inv_tbl_pedidos as ped "
                    + "left join cat_usu as usu on ped.cod_pai = usu.cod_pai and ped.cod_usu = usu.cod_usu "
                    + "left join inv_cat_cli as cli on ped.cod_pai = cli.cod_pai and ped.cod_cli_pro = cli.cod_cli "
                    + "where ped.cod_pai = " + cod_pai + " "
                    + "and ped.cod_usu = " + cod_usu + " "
                    + "and ped.det_sta in ('PENDIENTE') "
                    + "order by ped.cod_ped;";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezado.add(new CatPedidos(
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
                        resVariable.getString(20)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Pedidos en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarPedidosDetalles() {
        try {
            catpedidosdetalles = null;
            detalles.clear();

            String mQuery = "select "
                    + "det.cod_ped, "
                    + "det.ped_det, "
                    + "det.cod_bod, "
                    + "det.cod_ubi, "
                    + "det.cod_art, "
                    + "det.det_lot, "
                    + "det.fec_ven, "
                    + "det.can_sol, "
                    + "det.can_ent, "
                    + "det.can_pen, "
                    + "det.det_cos, "
                    + "det.can_sol_con, "
                    + "det.uni_med_con, "
                    + "det.det_sta, "
                    + "'',  "
                    + "'',  "
                    + "concat(ifnull(art.cod_art,''),'--',ifnull(art.cod_alt,''),'--', art.det_nom) as nomart, "
                    + "art.cod_art, "
                    + "art.cod_alt, "
                    + "det.tra_mae, "
                    + "det.tra_det, "
                    + "det.fec_tra, "
                    + "emb.nom_emb, "
                    + "det.lot_sug "
                    + "from inv_tbl_pedidos_det as det "
                    + "left join inv_cat_articulo as art on det.cod_art = art.id_art "
                    + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                    + "where det.cod_ped = " + cod_ped + " "
                    + "order by det.cod_ped;";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detalles.add(new CatPedidosDetalles(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getDouble(8),
                        resVariable.getDouble(9),
                        resVariable.getDouble(10),
                        resVariable.getDouble(11),
                        resVariable.getDouble(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20),
                        resVariable.getString(21),
                        resVariable.getString(22),
                        resVariable.getString(23),
                        resVariable.getString(24)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalles Pedidos en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarPedidosTransac() {
        try {
            String mwhere = "";
            catpedidos = null;
            encabezadotransac.clear();

            catpedidosdetalles = null;
            catpedidosdetallesagregar = null;
            detallestransac.clear();

            if (!sum_cli_buscar.equals("")) {
                mwhere = " and ucase(cli.nom_cli) like '%" + sum_cli_buscar.toUpperCase() + "%' ";
            } else {
                mwhere = "";
            }

            String mQuery = "select  "
                    + "ped.cod_ped,  "
                    + "ped.cod_pai,  "
                    + "case date_format(ped.fec_ped,'%d/%m/%Y') when '00/00/0000' then '' else date_format(ped.fec_ped,'%d/%m/%Y') end as fecped,  "
                    + "ped.flg_ing_sal,  "
                    + "ped.tip_mov,  "
                    + "ped.doc_tra,  "
                    + "ped.ord_com, "
                    + "ped.cod_cli_pro,  "
                    + "ped.det_obs,  "
                    + "ped.cod_usu,  "
                    + "ped.flg_anu,  "
                    + "ped.cod_esp,  "
                    + "ped.cor_mov,  "
                    + "ped.cod_are,  "
                    + "ped.obs_anu,  "
                    + "ped.det_sta,  "
                    + "ifnull(usu.det_nom,'') as nomusu, "
                    + "ifnull(cli.nom_cli,'') as nomcli, "
                    + "ped.flg_val, "
                    + "case ped.flg_val "
                    + "when 'true' then 'Vale' "
                    + "when 'false' then 'Orden' "
                    + "when 'cotiza' then 'Cotización' "
                    + "when 'repos' then 'Repos/Cambio' "
                    + "end as valflg "
                    + "from inv_tbl_pedidos as ped "
                    + "left join cat_usu as usu on ped.cod_pai = usu.cod_pai and ped.cod_usu = usu.cod_usu "
                    + "left join inv_cat_cli as cli on ped.cod_pai = cli.cod_pai and ped.cod_cli_pro = cli.cod_cli "
                    + "where ped.cod_pai = " + cod_pai + " "
                    + "and ped.det_sta in ('PENDIENTE','ENTREGA PARCIAL') "
                    + mwhere
                    + "order by ped.cod_ped;";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezadotransac.add(new CatPedidos(
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
                        resVariable.getString(20)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Pedidos Transac en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarPedidosDetallesTransac() {
        try {
            catpedidosdetalles = null;
            catpedidosdetallesagregar = null;
            detallestransac.clear();

            String mQuery = "select "
                    + "det.cod_ped, "
                    + "det.ped_det, "
                    + "det.cod_bod, "
                    + "det.cod_ubi, "
                    + "det.cod_art, "
                    + "det.det_lot, "
                    + "det.fec_ven, "
                    + "det.can_sol, "
                    + "det.can_ent, "
                    + "det.can_pen, "
                    + "det.det_cos, "
                    + "det.can_sol_con, "
                    + "det.uni_med_con, "
                    + "det.det_sta, "
                    + "'',  "
                    + "'',  "
                    + "concat(ifnull(art.cod_art,''),'--',ifnull(art.cod_alt,''),'--', art.det_nom) as nomart, "
                    + "art.cod_art, "
                    + "art.cod_alt, "
                    + "det.tra_mae, "
                    + "det.tra_det, "
                    + "det.fec_tra, "
                    + "emb.nom_emb, "
                    + "det.lot_sug "
                    + "from inv_tbl_pedidos_det as det "
                    + "left join inv_cat_articulo as art on det.cod_art = art.id_art "
                    + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                    + "where det.cod_ped = " + cod_ped + " "
                    + "and det.det_sta in ('PENDIENTE', 'ENTREGA PARCIAL', 'CANCELADO')"
                    + "order by det.cod_ped;";

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detallestransac.add(new CatPedidosDetalles(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getDouble(8),
                        resVariable.getDouble(9),
                        resVariable.getDouble(10),
                        resVariable.getDouble(11),
                        resVariable.getDouble(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20),
                        resVariable.getString(21),
                        resVariable.getString(22),
                        resVariable.getString(23),
                        resVariable.getString(24)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalles Pedidos Transac en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarPedidosStatus() {
        try {
            String mwhere = "";
            catpedidospedidos = null;
            encabezadopedidos.clear();

            detallespedidos.clear();

            if (sta_cli_buscar == null) {
                sta_cli_buscar = "";
            }

            if (!sta_cli_buscar.trim().equals("")) {
                mwhere = " and upper(cli.nom_cli) like '%" + sta_cli_buscar.toUpperCase() + "%' ";
            }

            String mQuery = "select  "
                    + "ped.cod_ped,  "
                    + "ped.cod_pai,  "
                    + "case date_format(ped.fec_ped,'%d/%m/%Y') when '00/00/0000' then '' else date_format(ped.fec_ped,'%d/%m/%Y') end as fecped,  "
                    + "ped.flg_ing_sal,  "
                    + "ped.tip_mov,  "
                    + "ped.doc_tra,  "
                    + "ped.ord_com, "
                    + "ped.cod_cli_pro,  "
                    + "ped.det_obs,  "
                    + "ped.cod_usu,  "
                    + "ped.flg_anu,  "
                    + "ped.cod_esp,  "
                    + "ped.cor_mov,  "
                    + "ped.cod_are,  "
                    + "ped.obs_anu,  "
                    + "ped.det_sta,  "
                    + "ifnull(usu.det_nom,'') as nomusu, "
                    + "ifnull(cli.nom_cli,'') as nomcli, "
                    + "ped.flg_val,"
                    + "case ped.flg_val "
                    + "when 'true' then 'Vale' "
                    + "when 'false' then 'Orden' "
                    + "when 'cotiza' then 'Cotización' "
                    + "when 'repos' then 'Repos/Cambio' "
                    + "end as valflg "
                    + "from inv_tbl_pedidos as ped "
                    + "left join cat_usu as usu on ped.cod_pai = usu.cod_pai and ped.cod_usu = usu.cod_usu "
                    + "left join inv_cat_cli as cli on ped.cod_pai = cli.cod_pai and ped.cod_cli_pro = cli.cod_cli "
                    + "where ped.cod_pai = " + cod_pai + " "
                    + mwhere
                    + "order by ped.cod_ped desc;";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezadopedidos.add(new CatPedidos(
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
                        resVariable.getString(20)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Pedidos en ManPedidos. " + e.getMessage());
        }
    }

    public void llenarPedidosDetallesStatus() {
        try {
            detallespedidos.clear();

            String mQuery = "select "
                    + "det.cod_ped, "
                    + "det.ped_det, "
                    + "det.cod_bod, "
                    + "det.cod_ubi, "
                    + "det.cod_art, "
                    + "det.det_lot, "
                    + "det.fec_ven, "
                    + "det.can_sol, "
                    + "det.can_ent, "
                    + "det.can_pen, "
                    + "det.det_cos, "
                    + "det.can_sol_con, "
                    + "det.uni_med_con, "
                    + "det.det_sta, "
                    + "'',  "
                    + "'',  "
                    + "concat(ifnull(art.cod_art,''),'--',ifnull(art.cod_alt,''),'--', art.det_nom) as nomart, "
                    + "art.cod_art, "
                    + "art.cod_alt, "
                    + "det.tra_mae, "
                    + "det.tra_det, "
                    + "det.fec_tra, "
                    + "emb.nom_emb, "
                    + "det.lot_sug "
                    + "from inv_tbl_pedidos_det as det "
                    + "left join inv_cat_articulo as art on det.cod_art = art.id_art "
                    + "left join inv_cat_embalaje as emb on art.det_emb = emb.id_emb "
                    + "where det.cod_ped = " + sta_cod_ped + " "
                    + "order by det.cod_ped;";

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detallespedidos.add(new CatPedidosDetalles(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getDouble(8),
                        resVariable.getDouble(9),
                        resVariable.getDouble(10),
                        resVariable.getDouble(11),
                        resVariable.getDouble(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20),
                        resVariable.getString(21),
                        resVariable.getString(22),
                        resVariable.getString(23),
                        resVariable.getString(24)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalles Pedidos en ManPedidos. " + e.getMessage());
        }
    }

    //*********************** Detalle Pedidos **********************************
    public boolean validardetalle() {
        boolean mvalidar = true;

        if ("0".equals(cod_pai)) {
            addMessage("Validar Datos", "Debe Seleccionar un País", 2);
            return false;
        }
        if ("0".equals(det_cod_art)) {
            addMessage("Validar Datos", "Debe Seleccionar un Producto", 2);
            return false;
        }
        if (det_can_sol <= 0.0) {
            addMessage("Validar Datos", "Debe Ingresar una Cantidad mayor que Cero", 2);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;
        if ("0".equals(det_uni_med_con)) {
            addMessage("Validar Datos", "Debe Seleccionar una Unidad de Medida", 2);
            return false;
        } else if (!unimedori.equals(det_uni_med_con)) {
            macc.Conectar();
            try {
                mFactor = macc.doubleQuerySQLvariable("select ifnull(val_ini/val_sal,0.0) as factor "
                        + "from inv_cat_embalaje_rel "
                        + "where uni_med_ent = " + unimedori + " "
                        + "and uni_med_sal = " + det_uni_med_con + ";");
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
                            + "and uni_med_ent = " + det_uni_med_con + ";");
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

        if (mvalidar) {
            det_can_sol_con = det_can_sol;
            det_can_sol = det_can_sol * mFactor;
        }

        return mvalidar;
    }

    public void agregardetalle() {
        try {
            if (validardetalle()) {
                macc.Conectar();
                int correlativo = 0, insert = 0;

                for (int i = 0; i < detalles.size(); i++) {

                    if (det_cod_art.equals(detalles.get(i).getCod_art())
                            && det_uni_med_con.equals(detalles.get(i).getUni_med_con())) {

                        insert = 1;
                        detalles.set(i, new CatPedidosDetalles(
                                detalles.get(i).getCod_ped(),
                                detalles.get(i).getPed_det(),
                                detalles.get(i).getCod_bod(),
                                detalles.get(i).getCod_ubi(),
                                detalles.get(i).getCod_art(),
                                detalles.get(i).getDet_lot(),
                                detalles.get(i).getFec_ven(),
                                detalles.get(i).getCan_sol() + det_can_sol,
                                detalles.get(i).getCan_ent(),
                                detalles.get(i).getCan_pen() + det_can_sol,
                                detalles.get(i).getDet_cos(),
                                detalles.get(i).getCan_sol_con() + det_can_sol_con,
                                detalles.get(i).getUni_med_con(),
                                detalles.get(i).getDet_sta(),
                                detalles.get(i).getNombod(),
                                detalles.get(i).getNomubi(),
                                detalles.get(i).getNomart(),
                                detalles.get(i).getRefart(),
                                detalles.get(i).getAltart(),
                                detalles.get(i).getTra_mae(),
                                detalles.get(i).getTra_det(),
                                detalles.get(i).getFec_tra(),
                                detalles.get(i).getNomunimed(),
                                detalles.get(i).getLot_sug()
                        ));
                    }

                    if (Integer.valueOf(detalles.get(i).getPed_det()) > correlativo) {
                        correlativo = Integer.valueOf(detalles.get(i).getPed_det());
                    }
                }

                if (insert == 0) {
                    detalles.add(new CatPedidosDetalles(
                            "",
                            String.valueOf(correlativo + 1),
                            det_cod_bod,
                            det_cod_ubi,
                            det_cod_art,
                            det_det_lot,
                            det_fec_ven,
                            det_can_sol,
                            det_can_ent,
                            det_can_sol,
                            det_det_cos,
                            det_can_sol_con,
                            det_uni_med_con,
                            det_det_sta,
                            "",
                            "",
                            macc.strQuerySQLvariable("select det_nom from inv_cat_articulo where id_art =" + det_cod_art + ";"),
                            macc.strQuerySQLvariable("select cod_art from inv_cat_articulo where id_art =" + det_cod_art + ";"),
                            macc.strQuerySQLvariable("select cod_alt from inv_cat_articulo where id_art =" + det_cod_art + ";"),
                            det_tra_mae,
                            det_tra_det,
                            det_fec_tra,
                            macc.strQuerySQLvariable("select nom_emb from inv_cat_embalaje where id_emb =" + det_uni_med_con + ";"),
                            det_lot_sug
                    ));
                }

                det_cod_bod = "0";
                det_cod_ubi = "0";
                det_cod_art = "0";
                det_can_sol = 0.0;
                unimedori = "";
                nomunimed = "";
                det_can_sol_con = 0.0;
                det_uni_med_con = "0";
                det_lot_sug = "";
                catpedidosdetalles = null;
                macc.Desconectar();
            }
        } catch (Exception e) {
            macc.Desconectar();
            System.out.println("Error en Agregar Detalle ManPedidos. " + e.getMessage());
        }
    }

    public void eliminardetalle() {
        if ("".equals(det_ped_det)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover", 2);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (det_ped_det.equals(detalles.get(i).getPed_det())) {
                    if ("PENDIENTE".equals(detalles.get(i).getDet_sta())) {
                        detalles.remove(i);
                    } else {
                        addMessage("Eliminar Detalles", "Este Item del Detalle ya ha iniciado un proceso en otra instancia y no puede modificarse", 2);
                    }
                }
            }
            det_cod_bod = "0";
            det_cod_ubi = "0";
            det_cod_art = "0";
            det_can_sol = 0.0;
            unimedori = "";
            nomunimed = "";
            det_can_sol_con = 0.0;
            det_uni_med_con = "0";
            det_lot_sug = "";
            catpedidosdetalles = null;
        }
    }

    //*************************** Pedidos **************************************
    public void nuevopedido() {

        //******* Fechas ***********
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfechatra = Date.from(Instant.now());
        mfechaven = null;

        //******* Encabezado *******
        cod_ped = "";
        cod_pai = cbean.getCod_pai();
        fec_ped = format.format(mfechatra);
        flg_ing_sal = "true";
        tip_mov = "0";
        doc_tra = "";
        ord_com = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        cod_esp = "0";
        cor_mov = "";
        cod_are = "0";
        obs_anu = "";
        det_sta = "PENDIENTE";
        rut_ima = "";
        nombrearchivo = "Sin Seleccionar";
        flg_val = "false";

        //****** Detalles ********
        det_ped_det = "";
        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_cod_art = "0";
        det_det_lot = "";
        det_fec_ven = "";
        det_can_sol = 0.0;
        det_can_ent = 0.0;
        det_can_pen = 0.0;
        det_det_cos = 0.0;
        det_can_sol_con = 0.0;
        det_uni_med_con = "0";
        det_det_sta = "PENDIENTE";
        det_tra_mae = "";
        det_tra_det = "";
        det_fec_tra = format.format(mfechatra);
        unimedori = "0";
        nomunimed = "";
        det_lot_sug = "";

        catpedidos = null;
        catpedidosdetalles = null;
        detalles.clear();

    }

    public boolean validardatospedidos() {
        boolean mvalidar = true;
        if ("0".equals(cod_pai)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País", 2);
        }
        if (detalles.isEmpty()) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Agregar al menos un Detalle", 2);
        }

        return mvalidar;
    }

    public void guardarpedido() {

        if (validardatospedidos()) {
            String mQuery = "", mValores = "";
            int mCorrela = 1;

            macc.Conectar();
            try {
                if ("".equals(cod_ped)) {
                    mQuery = "select ifnull(max(cod_ped),0)+1 as codigo from inv_tbl_pedidos;";
                    cod_ped = macc.strQuerySQLvariable(mQuery);

                    mQuery = "insert into inv_tbl_pedidos (cod_ped,cod_pai,fec_ped,flg_ing_sal,tip_mov,doc_tra,ord_com,"
                            + "cod_cli_pro,det_obs,cod_usu,flg_anu,cod_esp,cor_mov,cod_are,obs_anu,det_sta,rut_ima,det_blo,usu_edi,fec_edi,flg_val) values( "
                            + cod_ped + "," + cod_pai + ",str_to_date('" + fec_ped + "','%d/%m/%Y'),'" + flg_ing_sal + "'," + tip_mov
                            + ",'" + doc_tra + "','" + ord_com + "'," + cod_cli_pro + ",'" + det_obs + "'," + cod_usu
                            + "," + flg_anu + "," + cod_esp + ",null," + cod_are + ",'" + obs_anu + "','" + det_sta
                            + "','" + rut_ima + "',null," + cbean.getCod_usu() + ",now(),'" + flg_val + "');";

                } else {
                    mQuery = "update inv_tbl_pedidos set "
                            + "fec_ped = str_to_date('" + fec_ped + "', '%d/%m/%Y'), "
                            + "ord_com = '" + ord_com + "', "
                            + "det_obs = '" + det_obs + "', "
                            + "cod_usu =  " + cod_usu + ",  "
                            + "cod_esp =  " + cod_esp + ",  "
                            + "cod_are =  " + cod_are + ",  "
                            + "rut_ima = '" + rut_ima + "', "
                            + "flg_val = '" + flg_val + "', "
                            + "usu_edi =  " + cbean.getCod_usu() + ", "
                            + "fec_edi =  now() "
                            + "where cod_ped = " + cod_ped + " "
                            + "and cod_pai = " + cod_pai + ";";
                }

                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from inv_tbl_pedidos_det where cod_ped = " + cod_ped + ";";
                macc.dmlSQLvariable(mQuery);

                for (int i = 0; i < detalles.size(); i++) {

                    mValores = mValores + ",(" + cod_ped + "," + mCorrela + ",0,0," + detalles.get(i).getCod_art() + ",'',null,"
                            + detalles.get(i).getCan_sol() + ",0.0," + detalles.get(i).getCan_pen() + ",0.0," + detalles.get(i).getCan_sol_con()
                            + "," + detalles.get(i).getUni_med_con() + ",'" + detalles.get(i).getDet_sta() + "',null,null,null,'" + detalles.get(i).getLot_sug() + "')";
                    mCorrela = mCorrela + 1;
                }
                mQuery = "insert into inv_tbl_pedidos_det "
                        + "(cod_ped,ped_det,cod_bod,cod_ubi,cod_art,det_lot,fec_ven,can_sol,can_ent,can_pen,det_cos,can_sol_con,uni_med_con,det_sta,tra_mae,tra_det,fec_tra,lot_sug) "
                        + "VALUES " + mValores.substring(1) + ";";
                macc.dmlSQLvariable(mQuery);

                if (!"".equals(rut_ima)) {

                    // ****************************  Inserta Imagen en tabla ************************************************************
                    try {
                        FileInputStream fis = null;
                        PreparedStatement ps = null;
                        try {
                            macc.Conectar().setAutoCommit(false);
                            File mfile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(rut_ima));
                            fis = new FileInputStream(mfile);

                            mQuery = "update inv_tbl_pedidos set det_blo = ? where cod_ped = ?";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setBinaryStream(1, fis, (int) mfile.length());
                            ps.setString(2, cod_ped);

                            ps.executeUpdate();
                            macc.Conectar().commit();

                        } catch (Exception ex) {

                        } finally {
                            try {
                                ps.close();
                                fis.close();
                            } catch (Exception ex) {

                            }
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }

                addMessage("Guardar Requisición", "Información almacenada con Éxito", 1);
                llenarPedidosTransac();
                nuevopedido();
            } catch (Exception e) {
                addMessage("Guardar Requisición", "Error al guardar la información", 2);
            }
            macc.Desconectar();
        }
    }

    public void iniciarimagen() {
        macc.Conectar();
        try {

            String mQuery = "";
            Blob miBlob = null;
            mimagen = null;
            nomprod = "NINGUNO";
            if ("0".equals(det_cod_art) || "".equals(det_cod_art)) {
                mQuery = "select det_ima from inv_cat_articulo_img where id_art = 0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 200.0);
                img = null;

                data = null;

                nomprod = "NINGUNO";
            } else {
                mQuery = "select det_ima from inv_cat_articulo_img where id_art = " + det_cod_art + ";";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                if (miBlob != null) {
                    byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                    InputStream is = new ByteArrayInputStream(data);

                    mimagen = new DefaultStreamedContent(is, "image/jpeg");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 200.0);
                    img = null;

                    data = null;
                } else {
                    mQuery = "select det_ima from inv_cat_articulo_img where id_art = 0;";
                    miBlob = macc.blobQuerySQLvariable(mQuery);
                    byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                    InputStream is = new ByteArrayInputStream(data);

                    mimagen = new DefaultStreamedContent(is, "image/jpeg");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 200.0);
                    img = null;

                    data = null;
                }

                nomprod = macc.strQuerySQLvariable("Select concat(cod_art,'--',det_nom) as nompro from inv_cat_articulo where id_art = " + det_cod_art + ";");

            }

        } catch (Exception e) {
            System.out.println("Error en Iniciar Imagen de ManPedidos. " + e.getMessage());
        }
        macc.Desconectar();
    }

    public void descargaranexos() {
        if ("".equals(sta_cod_ped) || "0".equals(sta_cod_ped)) {
            addMessage("Descargar", "Debe seleccionar un registro", 2);
        } else {
            byte[] fileBytes;
            String mQuery, rIma = "";
            macc.Conectar();
            try {
                mQuery = "select det_blo from inv_tbl_pedidos where cod_ped=" + sta_cod_ped + ";";
                rIma = macc.strQuerySQLvariable("select rut_ima from inv_tbl_pedidos where cod_ped=" + sta_cod_ped + ";");

                ResultSet rs = macc.querySQLvariable(mQuery);
                if (rs.next()) {
                    File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                    String destinationO = mIMGFile.getPath().replace("config.xml", "");

                    fileBytes = rs.getBytes(1);
                    OutputStream targetFile = new FileOutputStream(destinationO + rIma.replace("/resources/images/temp/", ""));
                    targetFile.write(fileBytes);
                    targetFile.flush();
                    targetFile.close();
                    mIMGFile = null;
                    try {
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("window.open('" + "/totaltracking/faces" + rIma + "', '_blank')");
                    } catch (Exception e) {
                        System.out.println("Error en redireccionar a descarga en InvPedidos. " + e.getMessage());
                    }

                }
                rs.close();
                rIma = "";

            } catch (Exception e) {
                System.out.println("Error en descargar archivo InvPedidos. " + e.getMessage());
            }
            macc.Desconectar();
        }
    }

    public void cerrarbuscar() {
        catpedidos = null;
        encabezado.clear();
    }

    //**************** Funciones Copiar Archivos ***********************
    public void upload(FileUploadEvent event) {
        try {
            nombrearchivo = event.getFile().getFileName();

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                copyFile("inv_ped_" + event.getFile().getFileName().toLowerCase(), event.getFile().getInputstream());
            } catch (Exception e) {
                nombrearchivo = "Sin Seleccionar";
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            addMessage("Cargar Archivo", "El Archivo " + event.getFile().getFileName() + " No puedo. " + e.getMessage(), 2);
            System.out.println("Error en subir archivo Mantenimiento." + e.getMessage());
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            rut_ima = "/resources/images/temp/" + fileName;

            destination = mIMGFile.getPath().replace("config.xml", "");

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                File mfile = new File(destination + fileName);
                if (mfile.exists()) {
                    mfile.delete();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName.toLowerCase()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            nombrearchivo = "Sin Seleccionar";
            addMessage("Copiar Archivo Inventario", "El Archivo en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    //************************ Suministrar *************************************
    public void nuevosuministrar() {
        //******* Fechas ***********
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfechatransac = Date.from(Instant.now());
        mfechaven = null;

        //******* Encabezado *******
        mae_tra = "";
        cod_ped = "";
        cod_pai = cbean.getCod_pai();
        fec_tra = format.format(mfechatransac);
        flg_ing_sal = "true";
        tip_mov = "0";
        doc_tra = "";
        ord_com = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        cod_esp = "0";
        cor_mov = "";
        cod_are = "0";
        obs_anu = "";
        det_sta = "PENDIENTE";
        rut_ima = "";
        nombrearchivo = "Sin Seleccionar";
        sum_cli_buscar = "";

        //******* Detalles *********
        det_ped_det = "";
        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_det_lot = "";
        det_fec_ven = "";
        mfechaven = null;
        det_cod_art = "0";
        det_can_sol = 0.0;
        det_can_ent = 0.0;
        det_can_pen = 0.0;
        unimedori = "";
        nomunimed = "";
        det_can_sol_con = 0.0;
        det_uni_med_con = "0";
        catpedidos = null;
        catpedidosdetalles = null;
        catpedidosdetallesagregar = null;
        detallestransac.clear();
        detallesagregar.clear();
    }

    public boolean validardetallesuministrar() {
        boolean mvalidar = true;

        if (det_fec_ven.equals("00/00/0000")) {
            det_fec_ven = "";
        }
        if (det_fec_ven == null) {
            det_fec_ven = "";
        }
        if (det_det_lot == null) {
            det_det_lot = "";
        }
        if ("0".equals(cod_pai)) {
            addMessage("Validar Datos", "Debe Seleccionar un País", 2);
            return false;
        }
        if ("0".equals(cod_ped)) {
            addMessage("Validar Datos", "Debe Seleccionar un Pedido", 2);
            return false;
        }
        if ("0".equals(det_ped_det)) {
            addMessage("Validar Datos", "Debe Seleccionar un Detalle de Pedido", 2);
            return false;
        }
        if ("0".equals(det_cod_art)) {
            addMessage("Validar Datos", "Debe Seleccionar un Producto", 2);
            return false;
        }
        if ("0".equals(det_cod_bod)) {
            addMessage("Validar Datos", "Debe escoger una Bodega", 2);
            return false;
        }
        if (det_can_ent <= 0.0) {
            addMessage("Validar Datos", "Debe Ingresar una Cantidad mayor que Cero", 2);
            return false;
        }
        if (det_can_pen < det_can_ent) {
            addMessage("Validar Datos", "La Cantidad Entregada no puede ser mayor que la Pendiente de Entregar", 2);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;
        if ("0".equals(det_uni_med_con)) {
            addMessage("Validar Datos", "Debe Seleccionar un Unidad de Medida", 2);
            return false;
        } else if (!unimedori.equals(det_uni_med_con)) {
            macc.Conectar();
            try {
                mFactor = macc.doubleQuerySQLvariable("select ifnull(val_ini/val_sal,0.0) as factor "
                        + "from inv_cat_embalaje_rel "
                        + "where uni_med_ent = " + unimedori + " "
                        + "and uni_med_sal = " + det_uni_med_con + ";");
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
                            + "and uni_med_ent = " + det_uni_med_con + ";");
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

        //*************************** Verificación de Existencias *************************************************
        Double mExi = 0.0;
        macc.Conectar();
        try {
            mExi = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                    + "from inv_tbl_historico "
                    + "where "
                    + "cod_art=" + det_cod_art + " "
                    + "and cod_pai = " + cod_pai + " "
                    + "and cod_bod=" + det_cod_bod + " "
                    + "and det_lot='" + det_det_lot + "' "
                    + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                    + "order by fec_tra desc,"
                    + "ord_dia desc "
                    + "limit 1;");
        } catch (Exception e) {
            mExi = 0.0;
        }
        macc.Desconectar();
        Double exigrid = 0.0;
        if (!detallesagregar.isEmpty()) {
            for (int i = 0; i < detallesagregar.size(); i++) {
                if (det_cod_bod.equals(detallesagregar.get(i).getCod_bod())
                        && det_cod_art.equals(detallesagregar.get(i).getCod_art())
                        && det_det_lot.equals(detallesagregar.get(i).getDet_lot())) {
                    exigrid = exigrid + detallesagregar.get(i).getCan_ent();
                }
            }
        }

        if ((det_can_ent * mFactor) + exigrid > mExi) {
            if ("".equals(det_det_lot)) {
                addMessage("Validar Datos", "La Cantidad Solicitada Sobrepasa las Existencias en esta Bodega", 2);
            } else {
                addMessage("Validar Datos", "La Cantidad Solicitada del Lote " + det_det_lot + " Sobrepasa las Existencias en esta Bodega", 2);
            }
            return false;
        }

        if (mvalidar) {
            det_can_sol_con = det_can_ent;
            det_can_ent = det_can_ent * mFactor;
        }

        return mvalidar;

    }

    public void agregardetallesuministrar() {
        try {
            if (validardetallesuministrar()) {
                macc.Conectar();
                int correlativo = 0, insert = 0;

                for (int i = 0; i < detallesagregar.size(); i++) {

                    if (det_cod_art.equals(detallesagregar.get(i).getCod_art())
                            && det_uni_med_con.equals(detallesagregar.get(i).getUni_med_con())) {

                        insert = 1;
                        detallesagregar.set(i, new CatPedidosDetalles(
                                detallesagregar.get(i).getCod_ped(),
                                detallesagregar.get(i).getPed_det(),
                                detallesagregar.get(i).getCod_bod(),
                                detallesagregar.get(i).getCod_ubi(),
                                detallesagregar.get(i).getCod_art(),
                                detallesagregar.get(i).getDet_lot(),
                                detallesagregar.get(i).getFec_ven(),
                                detallesagregar.get(i).getCan_sol(),
                                detallesagregar.get(i).getCan_ent() + det_can_ent,
                                detallesagregar.get(i).getCan_pen() - det_can_ent,
                                detallesagregar.get(i).getDet_cos(),
                                detallesagregar.get(i).getCan_sol_con() + det_can_sol_con,
                                detallesagregar.get(i).getUni_med_con(),
                                detallesagregar.get(i).getDet_sta(),
                                detallesagregar.get(i).getNombod(),
                                detallesagregar.get(i).getNomubi(),
                                detallesagregar.get(i).getNomart(),
                                detallesagregar.get(i).getRefart(),
                                detallesagregar.get(i).getAltart(),
                                detallesagregar.get(i).getTra_mae(),
                                detallesagregar.get(i).getTra_det(),
                                detallesagregar.get(i).getFec_tra(),
                                detallesagregar.get(i).getNomunimed(),
                                detallesagregar.get(i).getLot_sug()
                        ));
                    }

                    if (Integer.valueOf(detallesagregar.get(i).getPed_det()) > correlativo) {
                        correlativo = Integer.valueOf(detallesagregar.get(i).getPed_det());
                    }
                }

                if (insert == 0) {
                    detallesagregar.add(new CatPedidosDetalles(
                            cod_ped,
                            det_ped_det,
                            det_cod_bod,
                            det_cod_ubi,
                            det_cod_art,
                            det_det_lot,
                            det_fec_ven,
                            det_can_sol,
                            det_can_ent,
                            det_can_pen - det_can_ent,
                            det_det_cos,
                            det_can_sol_con,
                            det_uni_med_con,
                            det_det_sta,
                            macc.strQuerySQLvariable("select nom_bod from inv_cat_bodegas where id_bod =" + det_cod_bod + ";"),
                            "",
                            macc.strQuerySQLvariable("select det_nom from inv_cat_articulo where id_art =" + det_cod_art + ";"),
                            macc.strQuerySQLvariable("select cod_art from inv_cat_articulo where id_art =" + det_cod_art + ";"),
                            macc.strQuerySQLvariable("select cod_alt from inv_cat_articulo where id_art =" + det_cod_art + ";"),
                            det_tra_mae,
                            det_tra_det,
                            fec_tra,
                            macc.strQuerySQLvariable("select nom_emb from inv_cat_embalaje where id_emb =" + det_uni_med_con + ";"),
                            ""
                    ));
                }

                det_cod_bod = "0";
                det_cod_ubi = "0";
                det_det_lot = "";
                det_fec_ven = "";
                mfechaven = null;
                det_cod_art = "0";
                det_can_sol = 0.0;
                det_can_ent = 0.0;
                det_can_pen = 0.0;
                unimedori = "";
                nomunimed = "";
                det_lot_sug = "";
                det_can_sol_con = 0.0;
                det_uni_med_con = "0";
                catpedidosdetalles = null;
                catpedidosdetallesagregar = null;
                macc.Desconectar();
                RequestContext.getCurrentInstance().update("frmReq");
            }
        } catch (Exception e) {
            macc.Desconectar();
            System.out.println("Error en Agregar Detalle a Suministrar ManPedidos. " + e.getMessage());
        }
    }

    public void eliminardetallesuministrar() {
        if ("".equals(add_ped_det)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover", 2);
        } else {
            for (int i = 0; i < detallesagregar.size(); i++) {
                if (det_ped_det.equals(detallesagregar.get(i).getPed_det())) {
                    detallesagregar.remove(i);
                }
            }
            det_cod_bod = "0";
            det_cod_ubi = "0";
            det_det_lot = "";
            det_fec_ven = "";
            mfechaven = null;
            det_cod_art = "0";
            det_can_sol = 0.0;
            det_can_ent = 0.0;
            det_can_pen = 0.0;
            unimedori = "";
            nomunimed = "";
            det_lot_sug = "";
            det_can_sol_con = 0.0;
            det_uni_med_con = "0";
            catpedidosdetalles = null;
            catpedidosdetallesagregar = null;
        }
    }

    public void guardarsuministrar() {
        try {
            if (suministrar()) {
                addMessage("Suministrar", "La información se almacenó exitosamente", 1);
                nuevosuministrar();
                llenarPedidosTransac();
            }
        } catch (Exception e) {
            System.out.println("Error en Guardar Suministrar ManPedidos. " + e.getMessage());
        }
    }

    public boolean suministrar() {
        String mQuery = "";
        String mValores = "", mhistoria = "", embalaje, tipoaccion = "";
        ResultSet resVariable;
        try {
            macc.Conectar();
            if ("".equals(mae_tra)) {
                tipoaccion = "insert";
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_transacciones;";
                mae_tra = macc.strQuerySQLvariable(mQuery);

                cor_mov = macc.strQuerySQLvariable("select ifnull(max(cor_mov),0) + 1 as cod from inv_tbl_transacciones "
                        + "where tip_mov=" + tip_mov + " and cod_pai=" + cod_pai + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");

                mQuery = "insert into inv_tbl_transacciones (cod_tra,cod_pai,fec_tra,flg_ing_sal,tip_mov,doc_tra,ord_com,"
                        + "cod_cli_pro,det_obs,cod_usu,flg_anu,cod_esp,cor_mov,cod_are,obs_anu,usu_edi,fec_edi,det_pol,tra_anu) "
                        + "values (" + mae_tra + "," + cod_pai + ",str_to_date('" + fec_tra + "','%d/%m/%Y'),'" + flg_ing_sal
                        + "'," + tip_mov + ",'" + doc_tra + "','" + ord_com + "'," + cod_cli_pro + ",'" + det_obs + "',"
                        + cod_usu + ",0," + cod_esp + "," + cor_mov + "," + cod_are + ",'" + obs_anu + "'," + cod_usu + ",now(),null,0);";
                macc.dmlSQLvariable(mQuery);
            } else {
                tipoaccion = "update";
                historicotransac.clear();
                String flganterior = macc.strQuerySQLvariable("select flg_ing_sal from inv_tbl_transacciones where cod_tra=" + mae_tra + ";");
                String codpai = macc.strQuerySQLvariable("select cod_pai from inv_tbl_transacciones where cod_tra=" + mae_tra + ";");

                mQuery = "update inv_tbl_transacciones set "
                        + "cod_pai=" + cod_pai + ", "
                        + "doc_tra='" + doc_tra + "',"
                        + "ord_com='" + ord_com + "',"
                        + "cod_cli_pro=" + cod_cli_pro + ","
                        + "det_obs='" + det_obs + "', "
                        + "cod_usu=" + cod_usu + ", "
                        + "cod_esp=" + cod_esp + ", "
                        + "cod_are=" + cod_are + ", "
                        + "obs_anu='" + obs_anu + "', "
                        + "usu_edi=" + cod_usu + ", "
                        + "fec_edi=now() "
                        + "where cod_tra=" + mae_tra + ";";
                macc.dmlSQLvariable(mQuery);

                //************************** Reorganizar Existencias antes de borrar *********************************************************
                mQuery = "select cod_tra, cod_det, cod_bod, cod_ubi, cod_art, det_lot, date_format(fec_ven,'%d/%m/%Y'), det_can,'','','',det_can_con, uni_med_con,det_cos,'','','0' "
                        + "FROM inv_tbl_transacciones_det where cod_tra = " + mae_tra + ";";
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    historicotransac.add(new CatTransaccionesDetalle(
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
                String modcan;
                if (!historicotransac.isEmpty()) {
                    for (int i = 0; i < historicotransac.size(); i++) {
                        if ("true".equals(flganterior)) {
                            modcan = " + " + historicotransac.get(i).getDet_can();
                        } else {
                            modcan = " - " + historicotransac.get(i).getDet_can();
                        }

                        mQuery = "update inv_tbl_lot_ven SET "
                                + "exi_act = (exi_act " + modcan + ") "
                                + "WHERE "
                                + "cod_pai = " + codpai + " "
                                + "AND cod_bod = " + historicotransac.get(i).getCod_bod() + " "
                                + "AND cod_art = " + historicotransac.get(i).getCod_art() + " "
                                + "AND num_lot = '" + historicotransac.get(i).getDet_lot() + "' ";
                        macc.dmlSQLvariable(mQuery);

                    }
                }
                //************************* Fin Reorganizar Histórico ************************************************************************

                mQuery = "delete from inv_tbl_transacciones_det where cod_tra=" + mae_tra + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from inv_tbl_historico where cod_mov =" + mae_tra + " and flg_ing_sal='" + flg_ing_sal + "';";
                macc.dmlSQLvariable(mQuery);
            }

            int mCorrela = 1, ord_dia = 0;
            int id_tra = 0;
            Double cos_uni, cos_pro, exi_ant, exi_act, exi_act_lot, exi_ant_tot, exi_act_tot;

            for (int i = 0; i < detallesagregar.size(); i++) {
                cos_uni = 0.0;
                cos_pro = 0.0;
                exi_ant = 0.0;
                exi_act = 0.0;
                exi_act_lot = 0.0;
                exi_ant_tot = 0.0;
                exi_act_tot = 0.0;

                mValores = mValores + ",(" + mae_tra + "," + mCorrela + "," + detallesagregar.get(i).getCod_bod() + "," + detallesagregar.get(i).getCod_ubi() + ","
                        + detallesagregar.get(i).getCod_art() + ",'" + detallesagregar.get(i).getDet_lot() + "',str_to_date('" + detallesagregar.get(i).getFec_ven() + "','%d/%m/%Y'),"
                        + detallesagregar.get(i).getCan_ent() + "," + detallestransac.get(i).getDet_cos() + "," + detallestransac.get(i).getCan_sol_con() + ","
                        + detallestransac.get(i).getUni_med_con() + ")";

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
                            + "AND cod_art = " + detallesagregar.get(i).getCod_art() + ";";
                    ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                }

                if (tipoaccion.equals("update") && "true".equals(flg_ing_sal)) {
                    mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tbl_historico "
                            + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                            + "AND cod_pai = " + cod_pai + " "
                            + "AND cod_art = " + detallesagregar.get(i).getCod_art() + ";";
                    ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));
                }

                //****************************************************************************************************************************
                //*********************** COSTO UNITARIO *************************************************************************************
                //****************************************************************************************************************************
                cos_uni = detallesagregar.get(i).getDet_cos() / detallesagregar.get(i).getCan_ent();

                //****************************************************************************************************************************
                //*********************** COSTO PROMEDIO Y EXISTENCIA ANTERIOR ***************************************************************
                //**************************************************************************************************************************** 
                if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art=" + detallesagregar.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;

                } else if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tbl_historico "
                        + "where "
                        + "cod_art = " + detallesagregar.get(i).getCod_art() + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and  fec_tra < STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1
                        && macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art = " + detallesagregar.get(i).getCod_art() + " "
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
                    if ("false".equals(flg_ing_sal)) {
                        cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_bod * cos_pro),0) + "
                                + (detallesagregar.get(i).getCan_sol() * cos_uni) + ")"
                                + "/(IFNULL(exi_act_bod,0)+" + detallesagregar.get(i).getCan_ent() + ") as Cpromedio "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detallesagregar.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    } else {
                        cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from inv_tbl_historico "
                                + "where "
                                + "cod_art=" + detallesagregar.get(i).getCod_art() + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    }
                    //***********  Existencia Anterior Bodega *******************************************
                    exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + detallesagregar.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod=" + detallesagregar.get(i).getCod_bod() + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //********** Existencia Anterior Lote **********************************************
                    exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + detallesagregar.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod=" + detallesagregar.get(i).getCod_bod() + " "
                            + "and det_lot='" + detallesagregar.get(i).getDet_lot() + "' "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    exi_ant_tot = macc.doubleQuerySQLvariable("select ifnull(exi_act_tot,0) as exisant "
                            + "from inv_tbl_historico "
                            + "where "
                            + "cod_art=" + detallesagregar.get(i).getCod_art() + " "
                            + "and cod_pai = " + cod_pai + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //******************* Tratamiento de Existencias **************************************
                if ("false".equals(flg_ing_sal)) {
                    exi_act = exi_ant + detallesagregar.get(i).getCan_ent();
                    exi_act_lot = exi_act_lot + detallesagregar.get(i).getCan_ent();
                    exi_act_tot = exi_ant_tot + detallesagregar.get(i).getCan_ent();
                } else {
                    exi_act = exi_ant - detallesagregar.get(i).getCan_ent();
                    exi_act_lot = exi_act_lot - detallesagregar.get(i).getCan_ent();
                    exi_act_tot = exi_ant_tot - detallesagregar.get(i).getCan_ent();
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
                        + ord_dia + "," + mae_tra + "," + mCorrela
                        + "," + cod_pai + "," + detallesagregar.get(i).getCod_bod() + ","
                        + detallesagregar.get(i).getCod_ubi() + "," + detallesagregar.get(i).getCod_art() + ","
                        + detallesagregar.get(i).getCan_ent() + "," + exi_ant + "," + exi_act + "," + cos_uni + ","
                        + cos_pro + ",'" + detallesagregar.get(i).getDet_lot() + "'," + exi_act_lot + "," + exi_ant_tot + "," + exi_act_tot + ")";

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
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexitransac.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexitransac.add(new ListaCortaTransaccionExistencias(
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
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + "order by fec_tra asc, "
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexitransac.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexitransac) {
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
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + detallesagregar.get(i).getCod_bod() + " "
                        + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detallesagregar.get(i).getCod_bod() + " "
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexitransac.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod=" + detallesagregar.get(i).getCod_bod() + " "
                                + "and cod_art=" + detallesagregar.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexitransac.add(new ListaCortaTransaccionExistencias(
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
                                + "and cod_bod=" + detallesagregar.get(i).getCod_bod() + " "
                                + "and cod_art=" + detallesagregar.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexitransac.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexitransac) {
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
                        + "and cod_bod = " + detallesagregar.get(i).getCod_bod() + " "
                        + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                        + "and det_lot ='" + detallesagregar.get(i).getDet_lot() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tbl_historico where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detallesagregar.get(i).getCod_bod() + " "
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + "and det_lot ='" + detallesagregar.get(i).getDet_lot() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexitransac.clear();
                        resVariable = macc.querySQLvariable("select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detallesagregar.get(i).getCod_bod() + " "
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + "and det_lot ='" + detallesagregar.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexitransac.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();
                        resVariable = macc.querySQLvariable("(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tbl_historico "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + detallesagregar.get(i).getCod_bod() + " "
                                + "and cod_art = " + detallesagregar.get(i).getCod_art() + " "
                                + "and det_lot ='" + detallesagregar.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc "
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexitransac.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4)
                            ));
                        }
                        resVariable.close();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexitransac) {
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
                mQuery = "update inv_tbl_pedidos_det set "
                        + "can_pen = can_pen - " + detallesagregar.get(i).getCan_ent() + ", "
                        + "can_ent = can_ent + " + detallesagregar.get(i).getCan_ent() + " "
                        + "where cod_ped = " + detallesagregar.get(i).getCod_ped() + " "
                        + "and ped_det = " + detallesagregar.get(i).getPed_det() + ";";
                macc.dmlSQLvariable(mQuery);

                if ("0".equals(macc.strQuerySQLvariable("select ifnull(can_pen,0) from inv_tbl_pedidos_det where cod_ped=" + detallesagregar.get(i).getCod_ped() + " and ped_det=" + detallesagregar.get(i).getPed_det() + ";"))) {
                    mQuery = "update inv_tbl_pedidos_det set "
                            + "det_sta = 'ENTREGADO' "
                            + "where cod_ped = " + detallesagregar.get(i).getCod_ped() + " "
                            + "and ped_det = " + detallesagregar.get(i).getPed_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update inv_tbl_pedidos_det set "
                            + "det_sta = 'ENTREGA PARCIAL' "
                            + "where cod_ped = " + detallesagregar.get(i).getCod_ped() + " "
                            + "and ped_det = " + detallesagregar.get(i).getPed_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                mQuery = "insert into inv_rel_ped_tra (cor_rel,cod_ped,ped_det,cod_tra,tra_det,usu_mod,fec_mod) VALUES ("
                        + macc.strQuerySQLvariable("select ifnull(max(cor_rel),0) + 1 as cod from inv_rel_ped_tra;")
                        + "," + detallesagregar.get(i).getCod_ped() + "," + detallesagregar.get(i).getPed_det()
                        + "," + mae_tra + "," + mCorrela + "," + cbean.getCod_usu() + ", now());";
                macc.dmlSQLvariable(mQuery);

                if (Objects.equals(macc.doubleQuerySQLvariable("select count(cod_ped) as conta from inv_tbl_pedidos_det where cod_ped = " + detallesagregar.get(i).getCod_ped() + " and det_sta in ('CANCELADO');"), macc.doubleQuerySQLvariable("select count(cod_ped) as conta from inv_tbl_pedidos_det where cod_ped = " + detallesagregar.get(i).getCod_ped() + ";"))) {
                    mQuery = "update inv_tbl_pedidos set "
                            + "det_sta = 'CANCELADO' "
                            + "where cod_ped = " + detallesagregar.get(i).getCod_ped() + ";";
                    //+ "and ped_det = " + detallesagregar.get(i).getPed_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                } else if (Objects.equals(macc.doubleQuerySQLvariable("select count(cod_ped) as conta from inv_tbl_pedidos_det where cod_ped = " + detallesagregar.get(i).getCod_ped() + " and det_sta in ('ENTREGADO','CANCELADO');"), macc.doubleQuerySQLvariable("select count(cod_ped) as conta from inv_tbl_pedidos_det where cod_ped = " + detallesagregar.get(i).getCod_ped() + ";"))) {
                    mQuery = "update inv_tbl_pedidos set "
                            + "det_sta = 'FINALIZADO' "
                            + "where cod_ped = " + detallesagregar.get(i).getCod_ped() + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update inv_tbl_pedidos set "
                            + "det_sta = 'ENTREGA PARCIAL' "
                            + "where cod_ped = " + detallesagregar.get(i).getCod_ped() + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                mCorrela = mCorrela + 1;
            }
            mQuery = "insert into inv_tbl_transacciones_det (cod_tra,cod_det,cod_bod,cod_ubi,cod_art,det_lot,fec_ven,det_can,det_cos,det_can_con,uni_med_con) "
                    + "VALUES " + mValores.substring(1) + ";";
            macc.dmlSQLvariable(mQuery);

            macc.Desconectar();

            return true;

        } catch (SQLException | NumberFormatException e) {
            addMessage("Guardar Movimiento de Inventario", "Error al momento de Guardar la Información." + e.getMessage(), 2);
            System.out.println("Error en Guardar Movimiento de Inventario Productos ManPedidos. " + e.getMessage() + " Query: " + mQuery + " mValores: " + mValores);
            return false;
        }

    }

    //******************* Cambio de Estado *************************************
    public void iniciarcambioestado() {
        sta_cod_ped = "";
        sta_flg_val = "false";
        sta_num_fac = "";
        sta_cli_buscar = "";

        catpedidospedidos = null;
        detallespedidos.clear();

        llenarPedidosStatus();

    }

    public void cerrarcambioestado() {
        sta_cod_ped = "";
        sta_flg_val = "";
        sta_num_fac = "";
        sta_cli_buscar = "";

        catpedidospedidos = null;
        encabezadopedidos.clear();
        detallespedidos.clear();
    }

    public void cambiarestado() {
        String mQuery = "";
        if ("".equals(sta_cod_ped) || "0".equals(sta_cod_ped)) {
            addMessage("Guardar", "Debe seleccionar un registro", 2);
        } else {
            mQuery = "update inv_tbl_pedidos set "
                    + "doc_tra = '" + sta_num_fac + "', "
                    + "flg_val = '" + sta_flg_val + "', "
                    + "usu_edi =  " + cbean.getCod_usu() + ", "
                    + "fec_edi = now() "
                    + "where cod_ped = " + sta_cod_ped + " "
                    + "and cod_pai = " + cod_pai + ";";

            macc.Conectar();
            macc.dmlSQLvariable(mQuery);
            macc.Desconectar();

            iniciarcambioestado();
        }
    }

    //**************************** Controles ***********************************
    public void onSelectRowPedidos(SelectEvent event) {
        cod_ped = ((CatPedidos) event.getObject()).getCod_ped();
        fec_ped = ((CatPedidos) event.getObject()).getFec_ped();
        doc_tra = ((CatPedidos) event.getObject()).getDoc_tra();
        ord_com = ((CatPedidos) event.getObject()).getOrd_com();
        cod_cli_pro = ((CatPedidos) event.getObject()).getCod_cli_pro();
        det_obs = ((CatPedidos) event.getObject()).getDet_obs();
        cod_esp = ((CatPedidos) event.getObject()).getCod_esp();
        cod_are = ((CatPedidos) event.getObject()).getCod_are();
        det_sta = ((CatPedidos) event.getObject()).getDet_sta();
        nombrearchivo = "Sin Seleccionar";
        flg_val = ((CatPedidos) event.getObject()).getFlg_val();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if ("00/00/0000".equals(fec_ped)) {
            fec_ped = "";
            mfechatra = null;
        } else {
            try {
                mfechatra = format.parse(fec_ped);
            } catch (Exception ex) {
                mfechatra = null;
            }
        }

        llenarPedidosDetalles();
        catpedidos = null;
        encabezado.clear();
        RequestContext.getCurrentInstance().update("frmSearchReq");
        RequestContext.getCurrentInstance().update("frmReq");
        RequestContext.getCurrentInstance().execute("PF('wSearchReq').hide()");

    }

    public void onSelectRowDetPedidos(SelectEvent event) {
        det_ped_det = ((CatPedidosDetalles) event.getObject()).getPed_det();
        det_det_sta = ((CatPedidosDetalles) event.getObject()).getDet_sta();
    }

    public void onUnselectRowDetPedidos() {
        det_ped_det = "";
        det_det_sta = "PENDIENTE";
    }

    public void onSelectRowSuministroMaestro(SelectEvent event) {
        cod_ped = ((CatPedidos) event.getObject()).getCod_ped();
        doc_tra = ((CatPedidos) event.getObject()).getDoc_tra();
        ord_com = ((CatPedidos) event.getObject()).getOrd_com();
        cod_cli_pro = ((CatPedidos) event.getObject()).getCod_cli_pro();
        cod_esp = ((CatPedidos) event.getObject()).getCod_esp();
        cod_are = ((CatPedidos) event.getObject()).getCod_are();

        llenarPedidosDetallesTransac();

        //****** Detalles ************
        det_ped_det = "";
        det_cod_art = "0";
        det_can_sol = 0.0;
        det_can_pen = 0.0;
        det_can_sol_con = 0.0;
        det_uni_med_con = "0";
        det_det_sta = "PENDIENTE";
        unimedori = "0";
        det_lot_sug = "";

        //****** Agregar *************
        add_ped_det = "";
        detallesagregar.clear();

    }

    public void onSelectRowSuministroDetalle(SelectEvent event) {
        det_ped_det = ((CatPedidosDetalles) event.getObject()).getPed_det();
        det_cod_art = ((CatPedidosDetalles) event.getObject()).getCod_art();
        det_can_sol = ((CatPedidosDetalles) event.getObject()).getCan_sol();
        det_can_pen = ((CatPedidosDetalles) event.getObject()).getCan_pen();

        det_can_ent = ((CatPedidosDetalles) event.getObject()).getCan_pen();

        det_can_sol_con = ((CatPedidosDetalles) event.getObject()).getCan_sol_con();

        det_det_sta = ((CatPedidosDetalles) event.getObject()).getDet_sta();

        macc.Conectar();
        unimedori = macc.strQuerySQLvariable("select det_emb from inv_cat_articulo where id_art =" + det_cod_art + ";");
        det_uni_med_con = unimedori;
        macc.Desconectar();

        det_lot_sug = ((CatPedidosDetalles) event.getObject()).getLot_sug();

        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_det_lot = "";
        mfechaven = null;
        det_fec_ven = "";
    }

    public void onSelectRowSuministroAgregar(SelectEvent event) {
        add_ped_det = ((CatPedidosDetalles) event.getObject()).getPed_det();

    }

    public void onSelectRowPedidosStatus(SelectEvent event) {
        sta_cod_ped = ((CatPedidos) event.getObject()).getCod_ped();
        sta_flg_val = ((CatPedidos) event.getObject()).getFlg_val();
        sta_num_fac = ((CatPedidos) event.getObject()).getDoc_tra();

        llenarPedidosDetallesStatus();
    }

    public void seleccionarproducto() {
        macc.Conectar();
        unimedori = macc.strQuerySQLvariable("select det_emb from inv_cat_articulo where id_art =" + det_cod_art + ";");
        if (unimedori == null) {
            unimedori = "0";
        }
        det_uni_med_con = unimedori;
        nomunimed = macc.strQuerySQLvariable("select nom_emb from inv_cat_embalaje where id_emb =(select det_emb from inv_cat_articulo where id_art = " + det_cod_art + ") ;");
        macc.Desconectar();
    }

    public void seleccionarbodega() {
        llenarUbicaciones();
        llenarLotes();
    }

    public void onselectlote() {
        if (det_fec_ven == null) {
            det_fec_ven = "";
        }
        if (!"".equals(det_det_lot)) {
            macc.Conectar();

            det_fec_ven = macc.strQuerySQLvariable("select case ifnull(det.fec_ven,'') when '' then '' else date_format(det.fec_ven,'%d/%m/%Y') end as fecven "
                    + "from inv_tbl_transacciones as mae "
                    + "left join inv_tbl_transacciones_det as det on mae.cod_tra = det.cod_tra "
                    + "where mae.cod_pai=" + cod_pai + " and det.cod_art=" + det_cod_art + " and det.det_lot='" + det_det_lot + "' order by det.fec_ven desc limit 1;");
            macc.Desconectar();

            if ("".equals(det_fec_ven)) {
                mfechaven = null;
            } else {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    mfechaven = format.parse(det_fec_ven);
                } catch (Exception e) {

                }
            }
        }
    }

    public void onSelectMovimiento() {
        if ("0".equals(tip_mov)) {
            cor_mov = "";
        } else {
            macc.Conectar();
            cor_mov = macc.strQuerySQLvariable("select case ifnull(det_abr,'') "
                    + "when '' then right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2) "
                    + "else concat(det_abr,right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') "
                    + "end as abr from inv_cat_mov where id_mov=" + tip_mov + ";")
                    + macc.strQuerySQLvariable("select LPAD(ifnull(max(cor_mov),0) + 1, 4, '0') from inv_tbl_transacciones "
                            + "where cod_pai=" + cod_pai + " and tip_mov= " + tip_mov + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");
            macc.Desconectar();
        }
    }

    public void onSelectFacturado() {
        if (sta_flg_val.equals("false")) {
            sta_num_fac = "";
        }
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabgenreq":
                mindex = "0";
                menabled = "true";
                nuevosuministrar();
                llenarPedidosTransac();
                break;
            case "tabdetreq":
                mindex = "1";
                menabled = "false";
                nuevopedido();
                break;
        }

    }

    public void dateSelectedPedido(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_ped = format.format(date);
    }

    public void dateSelectedTransac(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_tra = format.format(date);
    }

    public void dateSelectedVencimiento(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        det_fec_ven = format.format(date);
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

    //*************************** Getter y Setter ******************************
    public List<CatListados> getPaises() {
        return paises;
    }

    public void setPaises(List<CatListados> paises) {
        this.paises = paises;
    }

    public List<CatListados> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<CatListados> proveedores) {
        this.proveedores = proveedores;
    }

    public List<CatListados> getAreas() {
        return areas;
    }

    public void setAreas(List<CatListados> areas) {
        this.areas = areas;
    }

    public List<CatListados> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<CatListados> movimientos) {
        this.movimientos = movimientos;
    }

    public List<CatListados> getEspecialistas() {
        return especialistas;
    }

    public void setEspecialistas(List<CatListados> especialistas) {
        this.especialistas = especialistas;
    }

    public List<CatListados> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<CatListados> articulos) {
        this.articulos = articulos;
    }

    public List<CatListados> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<CatListados> unidades) {
        this.unidades = unidades;
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

    public CatPedidos getCatpedidos() {
        return catpedidos;
    }

    public void setCatpedidos(CatPedidos catpedidos) {
        this.catpedidos = catpedidos;
    }

    public CatPedidosDetalles getCatpedidosdetalles() {
        return catpedidosdetalles;
    }

    public void setCatpedidosdetalles(CatPedidosDetalles catpedidosdetalles) {
        this.catpedidosdetalles = catpedidosdetalles;
    }

    public List<CatPedidos> getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(List<CatPedidos> encabezado) {
        this.encabezado = encabezado;
    }

    public List<CatPedidosDetalles> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CatPedidosDetalles> detalles) {
        this.detalles = detalles;
    }

    public List<CatPedidos> getEncabezadotransac() {
        return encabezadotransac;
    }

    public void setEncabezadotransac(List<CatPedidos> encabezadotransac) {
        this.encabezadotransac = encabezadotransac;
    }

    public List<CatPedidosDetalles> getDetallestransac() {
        return detallestransac;
    }

    public void setDetallestransac(List<CatPedidosDetalles> detallestransac) {
        this.detallestransac = detallestransac;
    }

    public List<CatPedidosDetalles> getDetallesagregar() {
        return detallesagregar;
    }

    public void setDetallesagregar(List<CatPedidosDetalles> detallesagregar) {
        this.detallesagregar = detallesagregar;
    }

    public List<CatTransaccionesDetalle> getHistoricotransac() {
        return historicotransac;
    }

    public void setHistoricotransac(List<CatTransaccionesDetalle> historicotransac) {
        this.historicotransac = historicotransac;
    }

    public String getCod_ped() {
        return cod_ped;
    }

    public void setCod_ped(String cod_ped) {
        this.cod_ped = cod_ped;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
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

    public String getCod_cli_pro() {
        return cod_cli_pro;
    }

    public void setCod_cli_pro(String cod_cli_pro) {
        this.cod_cli_pro = cod_cli_pro;
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

    public String getObs_anu() {
        return obs_anu;
    }

    public void setObs_anu(String obs_anu) {
        this.obs_anu = obs_anu;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_ped_det() {
        return det_ped_det;
    }

    public void setDet_ped_det(String det_ped_det) {
        this.det_ped_det = det_ped_det;
    }

    public String getDet_cod_bod() {
        return det_cod_bod;
    }

    public void setDet_cod_bod(String det_cod_bod) {
        this.det_cod_bod = det_cod_bod;
    }

    public String getDet_cod_ubi() {
        return det_cod_ubi;
    }

    public void setDet_cod_ubi(String det_cod_ubi) {
        this.det_cod_ubi = det_cod_ubi;
    }

    public String getDet_cod_art() {
        return det_cod_art;
    }

    public void setDet_cod_art(String det_cod_art) {
        this.det_cod_art = det_cod_art;
    }

    public String getDet_det_lot() {
        return det_det_lot;
    }

    public void setDet_det_lot(String det_det_lot) {
        this.det_det_lot = det_det_lot;
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

    public Double getDet_det_cos() {
        return det_det_cos;
    }

    public void setDet_det_cos(Double det_det_cos) {
        this.det_det_cos = det_det_cos;
    }

    public Double getDet_can_sol_con() {
        return det_can_sol_con;
    }

    public void setDet_can_sol_con(Double det_can_sol_con) {
        this.det_can_sol_con = det_can_sol_con;
    }

    public String getDet_uni_med_con() {
        return det_uni_med_con;
    }

    public void setDet_uni_med_con(String det_uni_med_con) {
        this.det_uni_med_con = det_uni_med_con;
    }

    public String getDet_det_sta() {
        return det_det_sta;
    }

    public void setDet_det_sta(String det_det_sta) {
        this.det_det_sta = det_det_sta;
    }

    public String getDet_tra_mae() {
        return det_tra_mae;
    }

    public void setDet_tra_mae(String det_tra_mae) {
        this.det_tra_mae = det_tra_mae;
    }

    public String getDet_tra_det() {
        return det_tra_det;
    }

    public void setDet_tra_det(String det_tra_det) {
        this.det_tra_det = det_tra_det;
    }

    public String getDet_fec_tra() {
        return det_fec_tra;
    }

    public void setDet_fec_tra(String det_fec_tra) {
        this.det_fec_tra = det_fec_tra;
    }

    public String getUnimedori() {
        return unimedori;
    }

    public void setUnimedori(String unimedori) {
        this.unimedori = unimedori;
    }

    public String getNomunimed() {
        return nomunimed;
    }

    public void setNomunimed(String nomunimed) {
        this.nomunimed = nomunimed;
    }

    public String getRut_ima() {
        return rut_ima;
    }

    public void setRut_ima(String rut_ima) {
        this.rut_ima = rut_ima;
    }

    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    public Date getMfechatra() {
        return mfechatra;
    }

    public void setMfechatra(Date mfechatra) {
        this.mfechatra = mfechatra;
    }

    public Date getMfechaven() {
        return mfechaven;
    }

    public void setMfechaven(Date mfechaven) {
        this.mfechaven = mfechaven;
    }

    public String getMindex() {
        return mindex;
    }

    public void setMindex(String mindex) {
        this.mindex = mindex;
    }

    public Date getMfechatransac() {
        return mfechatransac;
    }

    public void setMfechatransac(Date mfechatransac) {
        this.mfechatransac = mfechatransac;
    }

    public CatPedidosDetalles getCatpedidosdetallesagregar() {
        return catpedidosdetallesagregar;
    }

    public void setCatpedidosdetallesagregar(CatPedidosDetalles catpedidosdetallesagregar) {
        this.catpedidosdetallesagregar = catpedidosdetallesagregar;
    }

    public String getMenabled() {
        return menabled;
    }

    public void setMenabled(String menabled) {
        this.menabled = menabled;
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
        InvPedidos.mimagen = mimagen;
    }

    public String getNomprod() {
        return nomprod;
    }

    public void setNomprod(String nomprod) {
        this.nomprod = nomprod;
    }

    public String getFlg_val() {
        return flg_val;
    }

    public void setFlg_val(String flg_val) {
        this.flg_val = flg_val;
    }

    public String getDet_lot_sug() {
        return det_lot_sug;
    }

    public void setDet_lot_sug(String det_lot_sug) {
        this.det_lot_sug = det_lot_sug;
    }

    public String getSta_cod_ped() {
        return sta_cod_ped;
    }

    public void setSta_cod_ped(String sta_cod_ped) {
        this.sta_cod_ped = sta_cod_ped;
    }

    public String getSta_flg_val() {
        return sta_flg_val;
    }

    public void setSta_flg_val(String sta_flg_val) {
        this.sta_flg_val = sta_flg_val;
    }

    public String getSta_num_fac() {
        return sta_num_fac;
    }

    public void setSta_num_fac(String sta_num_fac) {
        this.sta_num_fac = sta_num_fac;
    }

    public CatPedidos getCatpedidospedidos() {
        return catpedidospedidos;
    }

    public void setCatpedidospedidos(CatPedidos catpedidospedidos) {
        this.catpedidospedidos = catpedidospedidos;
    }

    public List<CatPedidos> getEncabezadopedidos() {
        return encabezadopedidos;
    }

    public void setEncabezadopedidos(List<CatPedidos> encabezadopedidos) {
        this.encabezadopedidos = encabezadopedidos;
    }

    public List<CatPedidosDetalles> getDetallespedidos() {
        return detallespedidos;
    }

    public void setDetallespedidos(List<CatPedidosDetalles> detallespedidos) {
        this.detallespedidos = detallespedidos;
    }

    public String getSta_cli_buscar() {
        return sta_cli_buscar;
    }

    public void setSta_cli_buscar(String sta_cli_buscar) {
        this.sta_cli_buscar = sta_cli_buscar;
    }

    public String getSum_cli_buscar() {
        return sum_cli_buscar;
    }

    public void setSum_cli_buscar(String sum_cli_buscar) {
        this.sum_cli_buscar = sum_cli_buscar;
    }

}
