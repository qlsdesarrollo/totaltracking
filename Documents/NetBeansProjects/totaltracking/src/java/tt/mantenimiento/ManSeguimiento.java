package tt.mantenimiento;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import tt.general.Accesos;
import tt.general.CatBodegas;
import tt.general.CatHistorico;
import tt.general.CatListados;
import tt.general.CatPaises;
import tt.seguridad.CatUsuarios;
import tt.general.Login;

@Named
@ConversationScoped

public class ManSeguimiento implements Serializable {

    private static final long serialVersionUID = 8791111238764288L;
    @Inject
    Login cbean;

    private Accesos macc;

    private List<CatHistorico> historico;
    private List<CatUsuarios> recibidores;
    private List<CatUsuarios> aprobadores;
    private List<CatPaises> paises;
    private List<CatBodegas> bodegas;
    private List<CatListados> ubicaciones;

    private CatSolicitudes catmaestro;
    private List<CatSolicitudes> maestro;
    private CatSolicitudesDetalle catdetalles;
    private List<CatSolicitudesDetalle> detalles;
    private CatSolicitudes catmaestroaprobar;
    private List<CatSolicitudes> maestroaprobar;
    private CatSolicitudesDetalle catdetallesaprobar;
    private List<CatSolicitudesDetalle> detallesaprobar;
    private CatSolicitudes catmaestrohistoria;
    private List<CatSolicitudes> maestrohistoria;
    private CatSolicitudesDetalle catdetalleshistoria;
    private List<CatSolicitudesDetalle> detalleshistoria;

    private String cod_mae, det_sta, fec_cie;
    private String det_can_sol, det_can_ent, det_can_pen, mdt_cod_det, mdt_cod_ite, mdt_des_ite, mdt_non_sto, mdt_det_sta, mdt_cos_uni, det_fec_cie;
    private String apr_cod_mae, his_cod_mae, his_cod_det;
    private String tabindex;
    private Date mfecha, mfecha2;
    private String aprobador, recibidapor, cod_pai, cod_alt, idbuscar, destino, cod_bod, des_ubi, booledit, vdocumento;

    private String nombrereporte, nombreexportar;
    private Map<String, Object> parametros;

    public ManSeguimiento() {
    }

    //************************ Inicio y cierre ************************************
    public void iniciarventana() {
        macc = new Accesos();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");

        tabindex = "0";
        mfecha = Date.from(Instant.now());
        mfecha2 = Date.from(Instant.now());

        cod_mae = "";

        apr_cod_mae = "";

        his_cod_mae = "";
        his_cod_det = "";

        det_sta = "";

        det_can_sol = "";
        det_can_ent = "";
        det_can_pen = "";
        mdt_cod_det = "";
        mdt_cod_ite = "";
        mdt_des_ite = "";
        mdt_non_sto = "1";
        mdt_det_sta = "";
        mdt_cos_uni = "0.0";

        det_fec_cie = format.format(mfecha);
        fec_cie = format.format(mfecha2);

        aprobador = "0";
        recibidapor = "0";
        cod_pai = cbean.getCod_pai();
        cod_bod = "0";
        des_ubi = "";
        cod_alt = "";
        idbuscar = "";
        destino = "1";
        vdocumento = "";

        booledit = "false";

        aprobadores = new ArrayList<>();
        recibidores = new ArrayList<>();

        paises = new ArrayList<>();
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();

        catmaestro = new CatSolicitudes();
        maestro = new ArrayList<>();

        catdetalles = new CatSolicitudesDetalle();
        detalles = new ArrayList<>();

        catmaestroaprobar = new CatSolicitudes();
        maestroaprobar = new ArrayList<>();

        catdetallesaprobar = new CatSolicitudesDetalle();
        detallesaprobar = new ArrayList<>();

        catmaestrohistoria = new CatSolicitudes();
        maestrohistoria = new ArrayList<>();

        catdetalleshistoria = new CatSolicitudesDetalle();
        detalleshistoria = new ArrayList<>();

        historico = new ArrayList<>();

        llenarPaises();
        llenarBodegas();
        llenarAprobadores();
        llenarRecibidores();
        llenarMaestro();
        llenarMaestroAprobar();

    }

    public void cerrarventana() {
        cod_mae = null;
        apr_cod_mae = null;
        his_cod_mae = null;
        his_cod_det = null;
        det_sta = null;
        det_can_sol = null;
        det_can_ent = null;
        det_can_pen = null;
        det_fec_cie = null;
        fec_cie = null;
        destino = null;
        vdocumento = null;

        aprobadores = null;
        recibidores = null;

        paises = null;
        bodegas = null;
        ubicaciones = null;

        catmaestro = null;
        maestro = null;

        catdetalles = null;
        detalles = null;

        catmaestroaprobar = null;
        maestroaprobar = null;

        catdetallesaprobar = null;
        detallesaprobar = null;

        catmaestrohistoria = null;
        maestrohistoria = null;

        catdetalleshistoria = null;
        detalleshistoria = null;

        historico = null;

        macc = null;
    }

    //******************* LLenado de Catálogos ***********************************
    public void llenarAprobadores() {
        try {
            aprobadores.clear();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai "
                    + "left join lis_esp as lis on usu.cod_usu = lis.cod_usu "
                    + "where lis.cod_lis = 2 "
                    + "order by cod_usu;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                aprobadores.add(new CatUsuarios(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Aprobadores ManSeguimiento. " + e.getMessage());
        }
    }

    public void llenarRecibidores() {
        try {
            recibidores.clear();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai "
                    + "left join lis_esp as lis on usu.cod_usu = lis.cod_usu "
                    + "where lis.cod_lis = 3 "
                    + "order by cod_usu;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                recibidores.add(new CatUsuarios(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Recibidores ManSeguimiento. " + e.getMessage());
        }
    }

    public void llenarPaises() {
        try {

            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
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

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en ManSeguimiento. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {

            bodegas.clear();

            mQuery = "select id_bod, nom_bod,cod_pai "
                    + "from cat_bodegas "
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

        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en ManSeguimiento. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select case ubix.ubi when null then '' else ubix.ubi end as location "
                    + "from (select distinct(des_ubi) as ubi "
                    + "from tbl_pie_his "
                    + "where cod_pie=" + mdt_cod_ite
                    + " and cod_pai=" + cod_pai
                    + " and cod_bod=" + cod_bod
                    + " and det_sta=0 "
                    + " order by fec_his desc) as ubix;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
            System.out.println("Error en el llenado de Ubicaciones en ManSeguimiento. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    //******************* Llenado de Listas **************************************
    public void llenarMaestro() {
        try {
            catmaestro = null;
            maestro.clear();

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%b/%Y'), "
                    + "mae.cod_usu_sol, "
                    + "mae.cod_usu_apr, mae.cod_usu_rec, mae.cod_dep, mae.det_uso, mae.cod_maq, "
                    + "case mae.det_sta "
                    + "when 0 then 'AWAITING APPROVAL' "
                    + "when 1 then 'CANCELED' "
                    + "when 2 then 'APPROVED' "
                    + "when 3 then 'DENIED' "
                    + "when 4 then 'PENDING' "
                    + "when 5 then 'COMPLETED' end as sta, "
                    + "mae.det_obs, "
                    + "date_format(mae.fec_cie,'%d/%b/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM sol_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.det_sta in (2,4) "
                    + "order by mae.fec_sol desc;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                maestro.add(new CatSolicitudes(
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
                        resVariable.getString(18)
                ));
            }
            resVariable.close();
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Seguimiento Solicitud. " + e.getMessage());
        }
    }

    public void llenarDetalles() {
        try {
            catdetalles = null;
            detalles.clear();

            String mQuery = "select  "
                    + "det.cod_mae, det.cod_det, det.cod_pai, det.cod_bod, "
                    + "det.cod_ubi, det.cod_ite, det.des_ite, det.det_can_sol, det.det_can_ent, "
                    + "det.det_can_pen, det.non_sto, "
                    + "case det.det_sta "
                    + "when 0 then 'PENDING' "
                    + "when 1 then 'BACKORDER' "
                    + "when 2 then 'CANCELED' "
                    + "when 3 then 'DELIVERED' "
                    + "end as sta , "
                    + "det.fec_cie, "
                    + "det.cos_uni, "
                    + "pai.nom_pai, bod.nom_bod, det.cod_ubi "
                    + "from sol_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on det.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                    + "where det.cod_mae = " + cod_mae + " "
                    + "and det.det_sta in (0,1) "
                    + "order by det.cod_det asc;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detalles.add(new CatSolicitudesDetalle(
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
            System.out.println("Error en el llenado Detalles en Seguimiento Solicitud. " + e.getMessage());
        }
    }

    public void llenarMaestroAprobar() {
        try {
            catmaestroaprobar = null;
            maestroaprobar.clear();

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%b/%Y'), "
                    + "mae.cod_usu_sol, "
                    + "mae.cod_usu_apr, mae.cod_usu_rec, mae.cod_dep, mae.det_uso, mae.cod_maq, "
                    + "case mae.det_sta "
                    + "when 0 then 'AWAITING APPROVAL' "
                    + "when 1 then 'CANCELED' "
                    + "when 2 then 'APPROVED' "
                    + "when 3 then 'DENIED' "
                    + "when 4 then 'PENDING' "
                    + "when 5 then 'COMPLETED' end as sta, "
                    + "mae.det_obs, "
                    + "date_format(mae.fec_cie,'%d/%b/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM sol_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.det_sta = 0 "
                    + "order by mae.fec_sol desc, mae.cod_mae desc;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                maestroaprobar.add(new CatSolicitudes(
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
                        resVariable.getString(18)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Seguimiento Solicitud. " + e.getMessage());
        }
    }

    public void llenarDetallesAprobar() {
        try {
            catdetallesaprobar = null;
            detallesaprobar.clear();

            String mQuery = "select  "
                    + "det.cod_mae, det.cod_det, det.cod_pai, det.cod_bod, "
                    + "det.cod_ubi, det.cod_ite, det.des_ite, det.det_can_sol, det.det_can_ent, "
                    + "det.det_can_pen, det.non_sto, "
                    + "case det.det_sta "
                    + "when 0 then 'PENDING' "
                    + "when 1 then 'BACKORDER' "
                    + "when 2 then 'CANCELED' "
                    + "when 3 then 'DELIVERED' "
                    + "end as sta , "
                    + "det.fec_cie, "
                    + "det.cos_uni, "
                    + "pai.nom_pai, bod.nom_bod, det.cod_ubi "
                    + "from sol_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on det.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                    + "where det.cod_mae = " + apr_cod_mae + " order by det.cod_det asc;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detallesaprobar.add(new CatSolicitudesDetalle(
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
            System.out.println("Error en el llenado Detalles en Seguimiento Solicitud. " + e.getMessage());
        }
    }

    public void llenarMaestroHistoria() {
        try {
            catmaestrohistoria = null;
            maestrohistoria.clear();
            String mwhere;
            if ("".equals(idbuscar)) {
                mwhere = " ";
            } else {
                mwhere = "where "
                        + "mae.cod_mae in (" + idbuscar + ") ";
            }

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%b/%Y'), "
                    + "mae.cod_usu_sol, "
                    + "mae.cod_usu_apr, mae.cod_usu_rec, mae.cod_dep, mae.det_uso, mae.cod_maq, "
                    + "case mae.det_sta "
                    + "when 0 then 'AWAITING APPROVAL' "
                    + "when 1 then 'CANCELED' "
                    + "when 2 then 'APPROVED' "
                    + "when 3 then 'DENIED' "
                    + "when 4 then 'PENDING' "
                    + "when 5 then 'COMPLETED' end as sta, "
                    + "mae.det_obs, "
                    + "date_format(mae.fec_cie,'%d/%b/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM sol_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + mwhere
                    + "order by mae.fec_sol desc, mae.cod_mae desc;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                maestrohistoria.add(new CatSolicitudes(
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
                        resVariable.getString(18)
                ));
            }
            resVariable.close();
            macc.Desconectar();
            idbuscar = "";
            catdetalleshistoria = null;
            detalleshistoria.clear();
        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro Historia en Seguimiento Solicitud. " + e.getMessage());
        }
    }

    public void llenarDetallesHistoria() {
        try {
            catdetalleshistoria = null;
            detalleshistoria.clear();

            String mQuery = "select "
                    + "detrec.cod_mae, "
                    + "detrec.det_mae, "
                    + "his.cod_pai,"
                    + "his.cod_bod,"
                    + "detrec.cod_rec,"
                    + "his.cod_pie,"
                    + "det.des_ite,"
                    + "det.det_can_sol,"
                    + "detrec.det_can,"
                    + "0 as det_can_pen,"
                    + "det.non_sto, "
                    + "case detrec.det_sta "
                    + "when 0 then 'PENDING' "
                    + "when 1 then 'BACKORDER' "
                    + "when 2 then 'CANCELED' "
                    + "when 3 then 'DELIVERED' "
                    + "end as estado,"
                    + "date_format(detrec.fec_rec,'%d/%b/%Y') as fecrec,"
                    + "his.cos_pro,"
                    + "pai.nom_pai,"
                    + "his.des_ubi,"
                    + "detrec.cod_his "
                    + "from sol_det_rec as detrec "
                    + "left join sol_det as det on detrec.cod_mae = det.cod_mae and detrec.det_mae = det.cod_det "
                    + "left join tbl_pie_his as his on detrec.cod_his = his.cod_his "
                    + "left join cat_pai as pai on his.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on his.cod_pai = bod.cod_pai and his.cod_bod = bod.id_bod "
                    + "where detrec.cod_mae = " + his_cod_mae + " "
                    + "order by detrec.det_mae, his.cod_pie, detrec.fec_rec;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detalleshistoria.add(new CatSolicitudesDetalle(
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
            System.out.println("Error en el llenado Detalles Historia en Seguimiento Solicitud. " + e.getMessage());
        }
    }

    //****************** Aprobaciones **************************
    public void aprobar() {
        String mQuery;
        //Accesos mAccesos = new Accesos();
        macc.Conectar();

        if (!"".equals(apr_cod_mae) && !"0".equals(aprobador)) {

            try {
//                mQuery = "update tbl_rel_man_sol_req set det_sta_sol_req=2 "
//                        + "where cod_sol_req= " + apr_cod_mae + " and flg_sol_req='SOL'";
//                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "update sol_mae set det_sta=2, cod_usu_apr=" + aprobador
                        + " where cod_mae=" + apr_cod_mae + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Approve", "Purchase order has been Approved.", 1);
            } catch (Exception e) {
                addMessage("Aprrove", "Error while Approving. " + e.getMessage(), 2);
                System.out.println("Error al Aprobar Solicitud. " + e.getMessage());
            }
            maestrohistoria.clear();
            detalles.clear();
            detallesaprobar.clear();
            detalleshistoria.clear();
            llenarMaestro();
            llenarMaestroAprobar();

        } else {
            addMessage("Approve", "You have to select a Purchase Order and Approve by.", 2);
        }

        macc.Desconectar();

    }

    public void rechazar() {
        String mQuery;
        //Accesos mAccesos = new Accesos();
        macc.Conectar();

        if ("".equals(cod_mae) == false) {

            try {
//                mQuery = "update tbl_rel_man_sol_req set det_sta_sol_req=3 "
//                        + "where cod_sol_req= " + apr_cod_mae + " and flg_sol_req='SOL';";
//                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "update sol_mae set det_sta=3 where cod_mae=" + apr_cod_mae + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Deny", "Purchase order has been Denied.", 1);
            } catch (Exception e) {
                addMessage("Deny", "Error while Refusing. " + e.getMessage(), 2);
                System.out.println("Error al Cancelar Solicitud. " + e.getMessage());
            }
            maestrohistoria.clear();
            detalles.clear();
            detallesaprobar.clear();
            detalleshistoria.clear();
            llenarMaestro();
            llenarMaestroAprobar();

        } else {
            addMessage("Deny", "You have to select a Purchase Order.", 2);
        }

        macc.Desconectar();

    }

    //********************** Guardar ********************************
    public boolean validarseguimiento() {
        boolean mvalidar = true;

        if (des_ubi == null) {
            des_ubi = "";
        }

        if ("".equals(det_can_ent)) {
            det_can_ent = "0";
        }

        if ("".equals(cod_mae) || "0".equals(cod_mae)) {
            mvalidar = false;
            addMessage("Accept", "You have to select a Purchase Order.", 2);
        }

        if ("".equals(mdt_cod_det) || "0".equals(mdt_cod_det)) {
            mvalidar = false;
            addMessage("Accept", "You have to select a Purchase Order Detail.", 2);
        }

        try {
            if (Integer.valueOf(det_can_pen) < Integer.valueOf(det_can_ent)) {
                mvalidar = false;
                addMessage("Accept", "The quantity delivered may not exceed the Pending.", 2);
            }
        } catch (Exception e) {
            mvalidar = false;
            addMessage("Accept", "Check the quantity delivered and the outstanding amount.", 2);
        }

        if ("0".equals(destino)) {
            if ("0".equals(recibidapor)) {
                mvalidar = false;
                addMessage("Accept", "You have to select an Option from Received by.", 2);
            }
        } else {
            if ("0".equals(cod_pai)) {
                mvalidar = false;
                addMessage("Accept", "You have to select a Country.", 2);
            }
            if ("0".equals(cod_bod)) {
                mvalidar = false;
                addMessage("Accept", "You have to select a Warehouse.", 2);
            }
            if ("0".equals(mdt_cod_ite) || "".equals(mdt_cod_ite)) {
                mvalidar = false;
                addMessage("Accept", "This Item does not have Part Number.", 2);
            }
        }
        if ("0".equals(det_can_ent)) {
            mvalidar = false;
            addMessage("Accept", "You have to enter a Delivered Quantity above zero.", 2);
        }

        //Accesos macc = new Accesos();
        macc.Conectar();
        String mQuery = "select str_to_date(fec_sol,'%d/%b/%Y') from req_mae where cod_mae=" + cod_mae + ";";
        String mQuery2 = "select DATEDIFF(str_to_date('" + det_fec_cie + "','%d/%b/%Y'), str_to_date('" + macc.strQuerySQLvariable(mQuery) + "','%d/%b/%Y')) as datedif;";
        if (macc.doubleQuerySQLvariable(mQuery2) < 0.0) {
            mvalidar = false;
            addMessage("Accept", "The Receipt date can not be earlier than the Creation date.", 2);
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void guardarItem() {
        String mQuery = "", mdetsta;
        try {
            if (validarseguimiento()) {

                // ************************ INICIA TRATAMIENTO SOLICITUD **********************************
                //Accesos acc = new Accesos();
                macc.Conectar();
                mQuery = "update sol_mae set "
                        + "det_sta = 4 "
                        + "where cod_mae = " + cod_mae + ";";
                macc.dmlSQLvariable(mQuery);

                String strvar = macc.strQuerySQLvariable("select (det_can_pen - " + det_can_ent + " ) as resta from sol_det where cod_mae= " + cod_mae
                        + " and cod_det=" + mdt_cod_det + ";");

                if (Double.valueOf(strvar) < 0) {
                    mQuery = "update sol_det set "
                            + "det_can_ent = (det_can_ent + " + det_can_ent + "), "
                            + "det_can_pen = 0, "
                            + "cos_uni = " + mdt_cos_uni + " "
                            + "where cod_mae= " + cod_mae
                            + " and cod_det=" + mdt_cod_det + ";";
                } else {
                    mQuery = "update sol_det set "
                            + "det_can_ent = (det_can_ent + " + det_can_ent + "), "
                            + "det_can_pen = (det_can_pen-" + det_can_ent + "), "
                            + "cos_uni = " + mdt_cos_uni + " "
                            + "where cod_mae= " + cod_mae
                            + " and cod_det=" + mdt_cod_det + ";";
                }

                macc.dmlSQLvariable(mQuery);

                if ("0".equals(strvar)) {
                    mQuery = "update sol_det set "
                            + "det_sta=3, "
                            + "fec_cie = str_to_date('" + det_fec_cie + "','%d/%b/%Y') "
                            + "where cod_mae= " + cod_mae
                            + " and cod_det=" + mdt_cod_det + ";";
                    macc.dmlSQLvariable(mQuery);
                    mdetsta = "3";
                } else {
                    mQuery = "update sol_det set "
                            + "det_sta=1, "
                            + "fec_cie = str_to_date('" + det_fec_cie + "','%d/%b/%Y') "
                            + "where cod_mae= " + cod_mae
                            + " and cod_det=" + mdt_cod_det + ";";
                    macc.dmlSQLvariable(mQuery);
                    mdetsta = "1";
                }

                strvar = macc.strQuerySQLvariable("select count(cod_mae) from sol_det where cod_mae= " + cod_mae
                        + " and det_sta not in (2,3);");

                if ("0".equals(strvar)) {
                    mQuery = "update sol_mae set "
                            + "det_sta = 5, "
                            + "fec_cie = str_to_date('" + fec_cie + "','%d/%b/%Y') "
                            + "where cod_mae = " + cod_mae + ";";
                    macc.dmlSQLvariable(mQuery);
                    macc.Desconectar();
                    catdetalles = null;
                    detalles.clear();
                    llenarMaestro();
                } else {
                    catdetalles = null;
                    macc.Desconectar();
                    llenarDetalles();
                }

                // ************************ FINALIZA TRATAMIENTO SOLICITUD **********************************
                // ************************ INICIA TRATAMIENTO SUMINISTRO  **********************************
                macc.Conectar();
                String cod_rec = macc.strQuerySQLvariable("select ifnull(max(cod_rec),0)+1 as cod from sol_det_rec;");
                if ("0".equals(destino)) {
                    mQuery = "insert into sol_det_rec (cod_rec,cod_mae,det_mae,fec_rec,det_can,flg_usu_alm,cod_usu_rec,cod_his,det_sta,doc_rec) "
                            + "VALUES (" + cod_rec + "," + cod_mae + "," + mdt_cod_det
                            + ",str_to_date('" + det_fec_cie + "','%d/%b/%Y')," + det_can_ent + ",0," + recibidapor + ",0," + mdetsta + ",'" + vdocumento + "');";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    //código correlativo existencia histórica de artículo
                    String cod_cor_exi_art = macc.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                            + "as codigo from tbl_pie_his;");
                    //Código correlativo diario existencia histórica de artículo
                    String cor_dia = macc.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                            + "as cordia from tbl_pie_his "
                            + "where "
                            + "cod_pie=" + mdt_cod_ite + " "
                            + "and fec_his=STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + des_ubi + "' "
                            + ";");
                    //Costo promedio
                    Double cPromedio, exisAnt, mNuevaExistencia;
                    if ("1".equals(cod_cor_exi_art)) {
                        cPromedio = 0.0;
                        exisAnt = 0.0;
                    } else {
                        cPromedio = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + mdt_cod_ite + " "
                                + "and fec_his <= STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + des_ubi + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //Existencia Anterior
                        exisAnt = macc.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + mdt_cod_ite + " "
                                + "and fec_his <= STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + des_ubi + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    }
                    //Inserta Registros

                    mNuevaExistencia = (exisAnt + Double.valueOf(det_can_ent));

                    mQuery = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                            + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu,cod_pai,cod_bod,des_ubi) "
                            + "VALUES (" + cod_cor_exi_art + "," + mdt_cod_ite + ","
                            + "STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y')" + "," + cor_dia + ",0,0,0," + det_can_ent + "," + cPromedio + ","
                            + mNuevaExistencia + ","
                            + cPromedio + "," + "0" + "," + "now()" + "," + cbean.getCod_usu() + ","
                            + cod_pai + "," + cod_bod + ",'" + des_ubi + "');";
                    macc.dmlSQLvariable(mQuery);
                    mQuery = "insert into sol_det_rec (cod_rec,cod_mae,det_mae,fec_rec,det_can,flg_usu_alm,cod_usu_rec,cod_his,det_sta,doc_rec) "
                            + "VALUES (" + cod_rec + "," + cod_mae + "," + mdt_cod_det
                            + ",str_to_date('" + det_fec_cie + "','%d/%b/%Y')," + det_can_ent + ",1,0," + cod_cor_exi_art + "," + mdetsta + ",'" + vdocumento + "');";
                    macc.dmlSQLvariable(mQuery);

                    // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                    String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                            + "from tbl_pie_his where fec_his=STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                            + "and ord_dia >" + cor_dia + " "
                            + "and cod_pie=" + mdt_cod_ite + " "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + des_ubi + "' "
                            + ";");
                    contasiguientes = String.valueOf(
                            Integer.valueOf(contasiguientes)
                            + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                    + "from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                                    + "and cod_pie=" + mdt_cod_ite + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + des_ubi + "' "
                                    + ";")));

                    Double nuevacantidad = mNuevaExistencia;
                    if ("0".equals(contasiguientes) == false) {
                        try {
                            historico.clear();

                            //Double cos_uni_sal = 0.0;
                            ResultSet resvariable;
                            resvariable = macc.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                    + "cod_usu from tbl_pie_his "
                                    + "where fec_his = STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                                    + "and ord_dia >" + cor_dia + " "
                                    + "and cod_pie=" + mdt_cod_ite + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + des_ubi + "' "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc"
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

                            resvariable = macc.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                    + "cod_usu from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + det_fec_cie + "','%d/%b/%Y') "
                                    + "and cod_pie=" + mdt_cod_ite + " "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + cod_pai + " "
                                    + "and cod_bod = " + cod_bod + " "
                                    + "and des_ubi = '" + des_ubi + "' "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc"
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
                                    //cos_uni_sal = 0.0;
                                    //cunitario = Double.valueOf(seriehistorica1.getDet_cos());
                                } else {
                                    nuevacantidad = nuevacantidad - Double.valueOf(seriehistorica1.getDet_can());
                                    //cos_uni_sal = cPromedio;
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
                            System.out.println("Error en actualización de costos posteriores Agregar ManSeguimiento. " + e.getMessage());
                        }

                    }

                    // Tratamiento tabla bol_exi_pai
                    String mContador = macc.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and ing_sal=0 "
                            + "and cod_pie=" + mdt_cod_ite
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                                + "VALUES ("
                                + cod_pai + ","
                                + mdt_cod_ite + ",0,"
                                + det_can_ent
                                + ");";

                    } else {
                        mQuery = "update bol_exi_pai set "
                                + "det_exi= (det_exi + " + det_can_ent + ") "
                                + "where "
                                + "cod_pai=" + cod_pai + " "
                                + "and ing_sal=0 "
                                + "and cod_pie=" + mdt_cod_ite + ";";

                    }

                    macc.dmlSQLvariable(mQuery);

                    // Tratamiento tabla tbl_existencias
                    mContador = macc.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and cod_bod=" + cod_bod + " "
                            + "and cod_ubi = '" + des_ubi + "' "
                            + "and cod_pie=" + mdt_cod_ite
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                                + "VALUES ("
                                + macc.strQuerySQLvariable("select (ifnull(max(cod_exi),0) + 1) as codigo from tbl_existencias;") + ","
                                + mdt_cod_ite + ","
                                + cod_pai + ","
                                + cod_bod + ",'"
                                + des_ubi + "',"
                                + det_can_ent
                                + ",0);";
                    } else {
                        mQuery = " update tbl_existencias set det_can=(det_can+" + det_can_ent + ") "
                                + " where cod_pai=" + cod_pai + " and cod_bod = " + cod_bod + " and cod_ubi = '" + des_ubi + "' "
                                + " and cod_pie=" + mdt_cod_ite + " ;";
                    }
                    macc.dmlSQLvariable(mQuery);

                }

                macc.Desconectar();
                det_can_ent = "";
                vdocumento = "";
                addMessage("Accept", "successful.", 1);
            }

        } catch (Exception e) {
            System.out.println("Error al Guardar Cambios en Seguimiento." + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validarCancelado() {
        boolean mvalidar = true;

        if ("".equals(det_can_ent)) {
            det_can_ent = "0";
        }
        if ("".equals(cod_mae) || "0".equals(cod_mae)) {
            mvalidar = false;
            addMessage("Cancel", "You have to select a Purchase Order.", 2);
        }

        if ("".equals(mdt_cod_det) || "0".equals(mdt_cod_det)) {
            mvalidar = false;
            addMessage("Cancel", "You have to select a Purchase Order Detail.", 2);
        }

        return mvalidar;
    }

    public void cancelarItem() {
        String mQuery = "";
        try {
            if (validarCancelado()) {
                //Accesos acc = new Accesos();

                macc.Conectar();
                mQuery = "update sol_mae set "
                        + "det_sta = 4 "
                        + "where cod_mae = " + cod_mae + ";";
                macc.dmlSQLvariable(mQuery);

                mQuery = "update sol_det set "
                        + "det_sta=2, "
                        + "fec_cie = str_to_date('" + det_fec_cie + "','%d/%b/%Y') "
                        + "where cod_mae= " + cod_mae
                        + " and cod_det=" + mdt_cod_det + ";";
                macc.dmlSQLvariable(mQuery);
                String cod_rec = macc.strQuerySQLvariable("select ifnull(max(cod_rec),0)+1 as cod from sol_det_rec;");
                mQuery = "insert into sol_det_rec (cod_rec,cod_mae,det_mae,fec_rec,det_can,flg_usu_alm,cod_usu_rec,cod_his,det_sta,doc_rec) "
                        + "VALUES (" + cod_rec + "," + cod_mae + "," + mdt_cod_det
                        + ",str_to_date('" + det_fec_cie + "','%d/%b/%Y'),0,0,0,0,2,'');";
                macc.dmlSQLvariable(mQuery);

                String strvar = macc.strQuerySQLvariable("select count(cod_mae) from sol_det where cod_mae= " + cod_mae + ";");

                if (strvar.equals(macc.strQuerySQLvariable("select count(cod_mae) from sol_det where cod_mae= " + cod_mae + " and det_sta =2;"))) {
                    mQuery = "update sol_mae set "
                            + "det_sta = 1, "
                            + "fec_cie = str_to_date('" + fec_cie + "','%d/%b/%Y') "
                            + "where cod_mae = " + cod_mae + ";";
                    macc.dmlSQLvariable(mQuery);
                    macc.Desconectar();
                    catdetalles = null;
                    detalles.clear();
                    llenarMaestro();
                } else {
                    mQuery = "select (" + strvar + "-"
                            + macc.strQuerySQLvariable("select count(cod_mae) from sol_det where cod_mae= " + cod_mae + " and det_sta =2;") + "-"
                            + " count(cod_mae)) as total from sol_det where cod_mae=" + cod_mae + " and det_sta = 3;";
                    if ("0".equals(macc.strQuerySQLvariable(mQuery))) {
                        mQuery = "update sol_mae set "
                                + "det_sta = 5, "
                                + "fec_cie = str_to_date('" + fec_cie + "','%d/%b/%Y') "
                                + "where cod_mae = " + cod_mae + ";";
                        macc.dmlSQLvariable(mQuery);
                        macc.Desconectar();
                        catdetalles = null;
                        detalles.clear();
                        llenarMaestro();
                    } else {
                        catdetalles = null;
                        llenarDetalles();
                    }
                }

                det_can_ent = "";
                vdocumento = "";
                addMessage("Cancel", "This Purchase Order Detail has been canceled.", 1);
            }

        } catch (Exception e) {
            System.out.println("Error al Guardar Cambios en Seguimiento." + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardeshacer() {
        boolean mvalidar = true;

        if ("".equals(his_cod_mae) || "0".equals(his_cod_mae)) {
            mvalidar = false;
            addMessage("Undo", "You have to select a Purchase Order.", 2);
        }

        if ("".equals(his_cod_det) || "0".equals(his_cod_det)) {
            mvalidar = false;
            addMessage("Undo", "You have to select a Purchase Order Detail.", 2);
        }

        return mvalidar;
    }

    public void deshacer() {
        String mQuery = "", mdetsta;
        try {
            if (validardeshacer()) {

                // ***************************** INICIA TRATAMIENTO DE LA SOLICITUD *****************************
                //Accesos acc = new Accesos();
                macc.Conectar();
                mQuery = "update sol_mae set "
                        + "det_sta = 4 "
                        + "where cod_mae = " + catdetalleshistoria.getCod_mae() + ";";
                macc.dmlSQLvariable(mQuery);

                mQuery = "update sol_det set "
                        + "det_can_ent = (det_can_ent - " + catdetalleshistoria.getDet_can_ent() + "), "
                        + "det_can_pen = (det_can_pen + " + catdetalleshistoria.getDet_can_ent() + ") "
                        + "where cod_mae= " + catdetalleshistoria.getCod_mae()
                        + " and cod_det=" + catdetalleshistoria.getCod_det() + ";";
                macc.dmlSQLvariable(mQuery);

                mdetsta = macc.strQuerySQLvariable("select (det_can_sol - det_can_pen) as resta from sol_det where cod_mae= "
                        + catdetalleshistoria.getCod_mae()
                        + " and cod_det=" + catdetalleshistoria.getCod_det() + ";");

                if ("0".equals(mdetsta)) {
                    mQuery = "update sol_det set "
                            + "det_sta = 0 "
                            + "where cod_mae= " + catdetalleshistoria.getCod_mae() + " "
                            + "and cod_det=" + catdetalleshistoria.getCod_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update sol_det set "
                            + "det_sta = 1 "
                            + "where cod_mae= " + catdetalleshistoria.getCod_mae() + " "
                            + "and cod_det=" + catdetalleshistoria.getCod_det() + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                // ***************************** FINALIZA TRATAMIENTO DE LA SOLICITUD *****************************
                // ************************* INICIA REVERSIÓN DE LA ACCIÓN SELECCIONADA ***************************
                if ("1".equals(macc.strQuerySQLvariable("select flg_usu_alm from sol_det_rec where cod_rec= " + catdetalleshistoria.getCod_ubi() + ";"))) {
                    //Obtener el correlativo diario del registro actual
                    mQuery = "select ifnull(ord_dia,0) cordia "
                            + "from tbl_pie_his "
                            + "where "
                            + "cod_his = " + catdetalleshistoria.getNomubi() + ";";
                    String cor_dia = macc.strQuerySQLvariable(mQuery);

                    //Borrado Lógico el registro del Histórico
                    mQuery = " update tbl_pie_his set det_sta=1, fec_mod=now(), cod_usu=" + cbean.getCod_usu()
                            + " where cod_his=" + catdetalleshistoria.getNomubi() + ";";
                    macc.dmlSQLvariable(mQuery);

                    // Verifica si hay registros anteriores y toma sus valores
                    mQuery = "select count(ord_dia) as contador "
                            + "from tbl_pie_his "
                            + "where fec_his = STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                            + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                            + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                            + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                            + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                            + "and det_sta=0;";
                    String contador = macc.strQuerySQLvariable(mQuery);

                    Double cPromedio = 0.0, nuevacantidad = 0.0;

                    if ("0".equals(contador)) {
                        mQuery = "select count(ord_dia) as contador "
                                + "from tbl_pie_his "
                                + "where fec_his < STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                + "and det_sta=0;";
                        contador = macc.strQuerySQLvariable(mQuery);
                        if ("0".equals(contador) == false) {
                            mQuery = "select ifnull(cos_pro,0) as cpromedio "
                                    + "from tbl_pie_his where fec_his < STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                    + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                    + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                    + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                    + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                    + "and det_sta=0 "
                                    + "order by fec_his desc,"
                                    + "ord_dia desc "
                                    + "limit 1;";
                            cPromedio = macc.doubleQuerySQLvariable(mQuery);
                            mQuery = "select ifnull(can_exi,0) as nuevacantidad "
                                    + "from tbl_pie_his where fec_his < STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                    + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                    + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                    + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                    + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                    + "and det_sta=0 "
                                    + "order by fec_his desc,"
                                    + "ord_dia desc "
                                    + "limit 1;";
                            nuevacantidad = macc.doubleQuerySQLvariable(mQuery);

                        }
                    } else {
                        mQuery = "select ifnull(cos_pro,0) as cpromedio "
                                + "from tbl_pie_his where fec_his = STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                + "and ord_dia < " + cor_dia + " "
                                + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                + "and det_sta=0 "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;";
                        cPromedio = macc.doubleQuerySQLvariable(mQuery);
                        mQuery = "select ifnull(can_exi,0) as nuevacantidad "
                                + "from tbl_pie_his where fec_his=STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                + "and ord_dia < " + cor_dia + " "
                                + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                + "and det_sta=0 "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;";
                        nuevacantidad = macc.doubleQuerySQLvariable(mQuery);

                    }

                    // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                    String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                            + "from tbl_pie_his "
                            + "where fec_his=STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                            + "and ord_dia >" + cor_dia + " "
                            + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                            + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                            + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                            + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                            + "and det_sta = 0 "
                            + ";");
                    contasiguientes = String.valueOf(
                            Integer.valueOf(contasiguientes)
                            + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                    + "from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                    + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                    + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                    + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                    + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                    + "and det_sta = 0 "
                                    + ";")));

                    //Double nuevacantidad = mNuevaExistencia;
                    if ("0".equals(contasiguientes) == false) {
                        try {
                            historico.clear();

                            //Double cos_uni_sal = 0.0;
                            ResultSet resvariable;
                            resvariable = macc.querySQLvariable("select "
                                    + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                    + "cod_usu from tbl_pie_his "
                                    + "where fec_his = STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                    + "and ord_dia >" + cor_dia + " "
                                    + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                    + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                    + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                    + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                    + "and det_sta = 0 "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc"
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

                            resvariable = macc.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                    + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                    + "cod_usu from tbl_pie_his "
                                    + "where fec_his > STR_TO_DATE('" + catdetalleshistoria.getFec_cie() + "','%d/%b/%Y') "
                                    + "and cod_pai =" + catdetalleshistoria.getCod_pai() + " "
                                    + "and cod_bod =" + catdetalleshistoria.getCod_bod() + " "
                                    + "and des_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                    + "and cod_pie=" + catdetalleshistoria.getCod_ite() + " "
                                    + "and det_sta = 0 "
                                    + "order by fec_his asc,"
                                    + "ord_dia asc"
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
                                    //cos_uni_sal = 0.0;
                                    //cunitario = Double.valueOf(seriehistorica1.getDet_cos());
                                } else {
                                    nuevacantidad = nuevacantidad - Double.valueOf(seriehistorica1.getDet_can());
                                    //cos_uni_sal = cPromedio;
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
                            System.out.println("Error en actualización de costos posteriores. " + e.getMessage());
                        }

                    }

                    // Tratamiento tabla bol_exi_pai
                    String mContador = macc.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                            + "where "
                            + "cod_pai=" + catdetalleshistoria.getCod_pai() + " "
                            + "and ing_sal=0 "
                            + "and cod_pie=" + catdetalleshistoria.getCod_ite()
                            + ";");

                    if (!"0".equals(mContador)) {

                        mQuery = "update bol_exi_pai set "
                                + "det_exi= (det_exi - " + catdetalleshistoria.getDet_can_ent() + ") "
                                + "where "
                                + "cod_pai=" + catdetalleshistoria.getCod_pai() + " "
                                + "and ing_sal=0 "
                                + "and cod_pie=" + catdetalleshistoria.getCod_ite() + ";";

                    }

                    macc.dmlSQLvariable(mQuery);

                    // Tratamiento tabla tbl_existencias
                    mContador = macc.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                            + "where "
                            + "cod_pai=" + catdetalleshistoria.getCod_pai() + " "
                            + "and cod_bod=" + catdetalleshistoria.getCod_bod() + " "
                            + "and cod_ubi ='" + catdetalleshistoria.getNombod() + "' "
                            + "and cod_pie=" + catdetalleshistoria.getCod_ite()
                            + ";");

                    if (!"0".equals(mContador)) {

                        mQuery = " update tbl_existencias set det_can=(det_can-" + catdetalleshistoria.getDet_can_ent() + ") "
                                + " where cod_pai=" + catdetalleshistoria.getCod_pai() + " and cod_bod = " + catdetalleshistoria.getCod_bod()
                                + " and cod_ubi ='" + catdetalleshistoria.getNombod() + "' "
                                + " and cod_pie=" + catdetalleshistoria.getCod_ite() + " ;";

                    }
                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from tbl_pie_his where cod_his= " + catdetalleshistoria.getNomubi() + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                mQuery = "delete from sol_det_rec where cod_rec= " + catdetalleshistoria.getCod_ubi() + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                cod_mae = "";
                apr_cod_mae = "";
                his_cod_mae = "";
                his_cod_det = "";
                det_sta = "";
                det_can_sol = "";
                det_can_ent = "";
                det_can_pen = "";
                mdt_cod_det = "";
                mdt_cod_ite = "";
                mdt_des_ite = "";
                mdt_non_sto = "1";
                mdt_det_sta = "";
                mdt_cos_uni = "0.0";
                cod_pai = "";
                cod_bod = "0";
                des_ubi = "";
                cod_alt = "";
                idbuscar = "";

                detalles.clear();
                detallesaprobar.clear();
                detalleshistoria.clear();

                llenarMaestro();
                llenarMaestroAprobar();
                maestrohistoria.clear();

                addMessage("Undo", "successful.", 1);

            }
        } catch (Exception e) {
            System.out.println("Error en Deshacer Cambios Solicitud. " + e.getMessage());
            addMessage("Undo", "Error when saving data.", 2);
        }
    }

    //********************* Funciones Controles ***********************************
    public void onRowSelect(SelectEvent event) {
        cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        det_sta = ((CatSolicitudes) event.getObject()).getDet_sta();
        cod_pai = ((CatSolicitudes) event.getObject()).getCod_pai();
        cod_alt = ((CatSolicitudes) event.getObject()).getCod_alt();

        mfecha = Date.from(Instant.now());
        mfecha2 = Date.from(Instant.now());

        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        det_fec_cie = format.format(mfecha);
        fec_cie = format.format(mfecha2);
        recibidapor = "0";

        llenarDetalles();
        //llenarBodegas();
        limpiarControlesEntregar();
    }

    public void onRowUnselect(UnselectEvent event) {
        detalles.clear();
        limpiarControlesEntregar();
    }

    public void onRowSelectDetalle(SelectEvent event) {
        limpiarControlesEntregar();

        det_can_sol = ((CatSolicitudesDetalle) event.getObject()).getDet_can_sol();
        //det_can_ent = ((CatSolicitudesDetalle) event.getObject()).getDet_can_ent();
        det_can_pen = ((CatSolicitudesDetalle) event.getObject()).getDet_can_pen();
        mdt_cod_det = ((CatSolicitudesDetalle) event.getObject()).getCod_det();
        mdt_cod_ite = ((CatSolicitudesDetalle) event.getObject()).getCod_ite();
        mdt_des_ite = ((CatSolicitudesDetalle) event.getObject()).getDes_ite();
        mdt_non_sto = ((CatSolicitudesDetalle) event.getObject()).getNon_sto();
        mdt_det_sta = ((CatSolicitudesDetalle) event.getObject()).getDet_sta();
        mdt_cos_uni = ((CatSolicitudesDetalle) event.getObject()).getCos_uni();

        det_can_ent = det_can_pen;

        macc.Conectar();
        cod_bod = macc.strQuerySQLvariable("select cod_bod from tbl_pie_his where cod_pai = " + cod_pai + " and cod_pie = " + mdt_cod_ite + " order by fec_his desc, ord_dia desc limit 1;");
        des_ubi = macc.strQuerySQLvariable("select des_ubi from tbl_pie_his where cod_pai = " + cod_pai + " and cod_pie = " + mdt_cod_ite + " and cod_bod = " + cod_bod + " order by fec_his desc, ord_dia desc limit 1;");
        macc.Desconectar();

    }

    public void onRowUnselectDetalle(UnselectEvent event) {
        limpiarControlesEntregar();
    }

    public void onRowAprobarSelect(SelectEvent event) {
        apr_cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        cod_alt = ((CatSolicitudes) event.getObject()).getCod_alt();
        llenarDetallesAprobar();
    }

    public void onRowAprobarUnselect(UnselectEvent event) {
        detallesaprobar.clear();
    }

    public void onRowHistoriaSelect(SelectEvent event) {
        his_cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        llenarDetallesHistoria();
    }

    public void onRowHistoriaUnselect(UnselectEvent event) {
        detalleshistoria.clear();
    }

    public void onRowHistoriaDetalleSelect(SelectEvent event) {
        his_cod_det = ((CatSolicitudesDetalle) event.getObject()).getCod_det();
    }

    public void onRowHistoriaDetalleUnselect(UnselectEvent event) {
        his_cod_det = "";
    }

    public void dateCieSelected(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_cie = format.format(date);
        det_fec_cie = format.format(date);
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabSegSol":
                tabindex = "0";
                break;
            case "tabAproSol":
                tabindex = "1";
                break;
            case "tabHistoSol":
                tabindex = "2";
                break;
        }
        //System.out.println(tabindex);
        //RequestContext.getCurrentInstance().update(":frmListaEquipos:tvLE");
    }

    public void onSelectPais() {
        llenarBodegas();
        ubicaciones.clear();
        cod_bod = "0";
        des_ubi = "";
    }

    public void onSelectBodega() {
        llenarUbicaciones();
        des_ubi = "";
    }

    public void onSelectDestino() {
        if ("false".equals(booledit)) {
            booledit = "true";
        } else {
            booledit = "false";
        }
    }

    public void limpiarControlesEntregar() {

        det_can_ent = "";
        mfecha = Date.from(Instant.now());
        destino = "1";
        recibidapor = "0";
        cod_pai = "0";
        cod_bod = "0";
        des_ubi = "";
        booledit = "false";
        bodegas.clear();
        ubicaciones.clear();

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

    //******************* Reporte ***************************************
    public void limpiar() {
        if ("".equals(apr_cod_mae) || "0".equals(apr_cod_mae)) {
            addMessage("Print", "You have to select a Record", 2);
            RequestContext.getCurrentInstance().update("frmSeguimiento");
            RequestContext.getCurrentInstance().execute("PF('wCodImp').hide()");
        } else {
            RequestContext.getCurrentInstance().execute("PF('wCodImp').show()");
        }
    }

    public void recodificarmaestro() {
        if ("".equals(cod_mae) || "0".equals(cod_mae)) {
            addMessage("Print", "You have to select a Record", 2);
            RequestContext.getCurrentInstance().update("frmSeguimiento");
            RequestContext.getCurrentInstance().execute("PF('wCodImp2').hide()");
        } else {
            RequestContext.getCurrentInstance().execute("PF('wCodImp2').show()");
        }
    }

    public void cerrarcodimp() {
        apr_cod_mae = "";
        cod_alt = "";
        llenarMaestroAprobar();
        catmaestroaprobar = null;
        detallesaprobar.clear();
    }

    public void cerrarcodimpmaestro() {
        cod_mae = "";
        cod_alt = "";
        llenarMaestro();
        catmaestro = null;
        detalles.clear();
    }

    public void validarcodimp() {
        String mQuery = "";
        //Accesos mAccesos = new Accesos();
        macc.Conectar();
        try {
            mQuery = "select count(cod_alt) from sol_mae where UPPER(cod_alt)='" + cod_alt.replace("'", "").toUpperCase() + "' and cod_mae <> " + apr_cod_mae + ";";
            if ("0".equals(macc.strQuerySQLvariable(mQuery))) {
                if ("".equals(cod_alt)) {
                    addMessage("Modificar Solicitud", "Código de Impresión Vacío.", 2);
                } else {
                    addMessage("Modificar Solicitud", "Código de Impresión Válido.", 1);
                }
            } else {
                addMessage("Modificar Solicitud", "Código de Impresión ya Existe.", 2);
            }
        } catch (Exception e) {
            addMessage("Modificar Solicitud", "Error al Validar Código de Impresión. " + e.getMessage(), 2);
            System.out.println("Error al Validar Código de Impresión en Seguimiento Solicitudes. " + e.getMessage() + " Query: " + mQuery);
        }

        macc.Desconectar();

    }

    public void editarcodimp() {
        String mQuery = "";
        //Accesos mAccesos = new Accesos();
        macc.Conectar();
        try {
            mQuery = "select count(cod_alt) from sol_mae where UPPER(cod_alt)='" + cod_alt.trim().replace("'", "").toUpperCase() + "' and cod_mae <> " + apr_cod_mae + ";";
            if ("0".equals(macc.strQuerySQLvariable(mQuery))) {
                mQuery = "update sol_mae set cod_alt='" + cod_alt + "' where cod_mae=" + apr_cod_mae + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Edit", "Successfull", 1);
            } else if ("".equals(cod_alt.trim())) {
                mQuery = "update sol_mae set cod_alt='' where cod_mae=" + apr_cod_mae + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Edit", "Successfull", 1);
            } else {
                addMessage("Edit", "Code already exists", 2);
            }
        } catch (Exception e) {
            addMessage("Edit", "Error while saving. " + e.getMessage(), 2);
            System.out.println("Error al Actualizar Código de Impresión en Seguimiento Solicitudes. " + e.getMessage() + " Query: " + mQuery);
        }

        macc.Desconectar();

    }

    public void editarcodimpmaestro() {
        String mQuery = "";
        //Accesos mAccesos = new Accesos();
        macc.Conectar();
        try {
            mQuery = "select count(cod_alt) from sol_mae where UPPER(cod_alt)='" + cod_alt.trim().replace("'", "").toUpperCase() + "' and cod_mae <> " + cod_mae + ";";
            if ("0".equals(macc.strQuerySQLvariable(mQuery))) {
                mQuery = "update sol_mae set cod_alt='" + cod_alt + "' where cod_mae=" + cod_mae + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Edit", "Successfull", 1);
            } else if ("".equals(cod_alt.trim())) {
                mQuery = "update sol_mae set cod_alt='' where cod_mae=" + cod_mae + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Edit", "Successfull", 1);
            } else {
                addMessage("Edit", "Code already exists", 2);
            }
        } catch (Exception e) {
            addMessage("Edit", "Error while saving. " + e.getMessage(), 2);
            System.out.println("Error al Actualizar Código de Impresión2 en Seguimiento Solicitudes. " + e.getMessage() + " Query: " + mQuery);
        }

        macc.Desconectar();

    }

    //******************** Impresión Orden de Compra ****************************************
    public void ejecutarreporte() {
        try {
            if (!"".equals(apr_cod_mae) && !"0".equals(apr_cod_mae)) {
                editarcodimp();
                paramRepVarios();
                verPDF();
            } else {
                addMessage("Print", "You have to select a Record", 2);
            }
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Seguimiento Solicitudes" + e.getMessage());
        }

    }

    public void ejecutarreporte2() {
        try {
            if (!"".equals(cod_mae) && !"0".equals(cod_mae)) {
                editarcodimpmaestro();
                parametros = new HashMap<>();
                parametros.put("codigo", cod_mae);
                nombrereporte = "/reportes/ocompra.jasper";
                nombreexportar = "po_" + cod_mae;
                verPDF();
                parametros = null;
            } else {
                addMessage("Print", "You have to select a Record", 2);
            }
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte2 Seguimiento Solicitudes" + e.getMessage());
        }

    }

    public void paramRepVarios() {
        parametros = new HashMap<>();
        parametros.put("codigo", apr_cod_mae);
        nombrereporte = "/reportes/ocompra.jasper";
        nombreexportar = "po_" + apr_cod_mae;

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
            System.out.println("Error en verPDF en Seguimiento Solicitudes." + e.getMessage());
        }
    }
   
    //**********************Ejecuciòn Foranea *****************************
    public void llenarMaestroForaneo(String pai, String pie) {
        try {
            maestro = new ArrayList<>();

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%b/%Y'), "
                    + "mae.cod_usu_sol, "
                    + "mae.cod_usu_apr, mae.cod_usu_rec, mae.cod_dep, mae.det_uso, mae.cod_maq, "
                    + "case mae.det_sta "
                    + "when 0 then 'ESPERA APROBACIÓN' "
                    + "when 1 then 'CANCELADA' "
                    + "when 2 then 'APROBADA' "
                    + "when 3 then 'DENEGADA' "
                    + "when 4 then 'PENDIENTE' "
                    + "when 5 then 'CERRADA' end as sta, "
                    + "mae.det_obs, "
                    + "date_format(mae.fec_cie,'%d/%b/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM sol_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.det_sta in (2,4) "
                    + "and (select count(det.cod_mae) from sol_det as det where det.cod_pai=" + pai + " and det.cod_ite=" + pie + " and det.cod_mae = mae.cod_mae) > 0 "
                    + "order by mae.fec_sol desc;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                maestro.add(new CatSolicitudes(
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
                        resVariable.getString(18)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();
            RequestContext.getCurrentInstance().update("frmSeguimiento");
            RequestContext.getCurrentInstance().execute("PF('wSeguimiento').show()");

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Seguimiento Solicitud Foranea. " + e.getMessage());
        }
    }

    //**********************************************************************
    public List<CatHistorico> getHistorico() {
        return historico;
    }

    public void setHistorico(List<CatHistorico> historico) {
        this.historico = historico;
    }

    public List<CatUsuarios> getRecibidores() {
        return recibidores;
    }

    public void setRecibidores(List<CatUsuarios> recibidores) {
        this.recibidores = recibidores;
    }

    public List<CatUsuarios> getAprobadores() {
        return aprobadores;
    }

    public void setAprobadores(List<CatUsuarios> aprobadores) {
        this.aprobadores = aprobadores;
    }

    public CatSolicitudes getCatmaestro() {
        return catmaestro;
    }

    public void setCatmaestro(CatSolicitudes catmaestro) {
        this.catmaestro = catmaestro;
    }

    public List<CatSolicitudes> getMaestro() {
        return maestro;
    }

    public void setMaestro(List<CatSolicitudes> maestro) {
        this.maestro = maestro;
    }

    public CatSolicitudesDetalle getCatdetalles() {
        return catdetalles;
    }

    public void setCatdetalles(CatSolicitudesDetalle catdetalles) {
        this.catdetalles = catdetalles;
    }

    public List<CatSolicitudesDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CatSolicitudesDetalle> detalles) {
        this.detalles = detalles;
    }

    public CatSolicitudes getCatmaestroaprobar() {
        return catmaestroaprobar;
    }

    public void setCatmaestroaprobar(CatSolicitudes catmaestroaprobar) {
        this.catmaestroaprobar = catmaestroaprobar;
    }

    public List<CatSolicitudes> getMaestroaprobar() {
        return maestroaprobar;
    }

    public void setMaestroaprobar(List<CatSolicitudes> maestroaprobar) {
        this.maestroaprobar = maestroaprobar;
    }

    public CatSolicitudesDetalle getCatdetallesaprobar() {
        return catdetallesaprobar;
    }

    public void setCatdetallesaprobar(CatSolicitudesDetalle catdetallesaprobar) {
        this.catdetallesaprobar = catdetallesaprobar;
    }

    public List<CatSolicitudesDetalle> getDetallesaprobar() {
        return detallesaprobar;
    }

    public void setDetallesaprobar(List<CatSolicitudesDetalle> detallesaprobar) {
        this.detallesaprobar = detallesaprobar;
    }

    public CatSolicitudes getCatmaestrohistoria() {
        return catmaestrohistoria;
    }

    public void setCatmaestrohistoria(CatSolicitudes catmaestrohistoria) {
        this.catmaestrohistoria = catmaestrohistoria;
    }

    public List<CatSolicitudes> getMaestrohistoria() {
        return maestrohistoria;
    }

    public void setMaestrohistoria(List<CatSolicitudes> maestrohistoria) {
        this.maestrohistoria = maestrohistoria;
    }

    public CatSolicitudesDetalle getCatdetalleshistoria() {
        return catdetalleshistoria;
    }

    public void setCatdetalleshistoria(CatSolicitudesDetalle catdetalleshistoria) {
        this.catdetalleshistoria = catdetalleshistoria;
    }

    public List<CatSolicitudesDetalle> getDetalleshistoria() {
        return detalleshistoria;
    }

    public void setDetalleshistoria(List<CatSolicitudesDetalle> detalleshistoria) {
        this.detalleshistoria = detalleshistoria;
    }

    public String getCod_mae() {
        return cod_mae;
    }

    public void setCod_mae(String cod_mae) {
        this.cod_mae = cod_mae;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_can_sol() {
        return det_can_sol;
    }

    public void setDet_can_sol(String det_can_sol) {
        this.det_can_sol = det_can_sol;
    }

    public String getDet_can_ent() {
        return det_can_ent;
    }

    public void setDet_can_ent(String det_can_ent) {
        this.det_can_ent = det_can_ent;
    }

    public String getDet_can_pen() {
        return det_can_pen;
    }

    public void setDet_can_pen(String det_can_pen) {
        this.det_can_pen = det_can_pen;
    }

    public String getMdt_cod_det() {
        return mdt_cod_det;
    }

    public void setMdt_cod_det(String mdt_cod_det) {
        this.mdt_cod_det = mdt_cod_det;
    }

    public String getMdt_cod_ite() {
        return mdt_cod_ite;
    }

    public void setMdt_cod_ite(String mdt_cod_ite) {
        this.mdt_cod_ite = mdt_cod_ite;
    }

    public String getMdt_des_ite() {
        return mdt_des_ite;
    }

    public void setMdt_des_ite(String mdt_des_ite) {
        this.mdt_des_ite = mdt_des_ite;
    }

    public String getMdt_non_sto() {
        return mdt_non_sto;
    }

    public void setMdt_non_sto(String mdt_non_sto) {
        this.mdt_non_sto = mdt_non_sto;
    }

    public String getMdt_det_sta() {
        return mdt_det_sta;
    }

    public void setMdt_det_sta(String mdt_det_sta) {
        this.mdt_det_sta = mdt_det_sta;
    }

    public String getMdt_cos_uni() {
        return mdt_cos_uni;
    }

    public void setMdt_cos_uni(String mdt_cos_uni) {
        this.mdt_cos_uni = mdt_cos_uni;
    }

    public String getDet_fec_cie() {
        return det_fec_cie;
    }

    public void setDet_fec_cie(String det_fec_cie) {
        this.det_fec_cie = det_fec_cie;
    }

    public String getFec_cie() {
        return fec_cie;
    }

    public void setFec_cie(String fec_cie) {
        this.fec_cie = fec_cie;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getApr_cod_mae() {
        return apr_cod_mae;
    }

    public void setApr_cod_mae(String apr_cod_mae) {
        this.apr_cod_mae = apr_cod_mae;
    }

    public String getHis_cod_mae() {
        return his_cod_mae;
    }

    public void setHis_cod_mae(String his_cod_mae) {
        this.his_cod_mae = his_cod_mae;
    }

    public String getHis_cod_det() {
        return his_cod_det;
    }

    public void setHis_cod_det(String his_cod_det) {
        this.his_cod_det = his_cod_det;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

    public Date getMfecha2() {
        return mfecha2;
    }

    public void setMfecha2(Date mfecha2) {
        this.mfecha2 = mfecha2;
    }

    public String getAprobador() {
        return aprobador;
    }

    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }

    public String getRecibidapor() {
        return recibidapor;
    }

    public void setRecibidapor(String recibidapor) {
        this.recibidapor = recibidapor;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

    public String getIdbuscar() {
        return idbuscar;
    }

    public void setIdbuscar(String idbuscar) {
        this.idbuscar = idbuscar;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNombrereporte() {
        return nombrereporte;
    }

    public void setNombrereporte(String nombrereporte) {
        this.nombrereporte = nombrereporte;
    }

    public String getNombreexportar() {
        return nombreexportar;
    }

    public void setNombreexportar(String nombreexportar) {
        this.nombreexportar = nombreexportar;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
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

    public List<CatListados> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<CatListados> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getDes_ubi() {
        return des_ubi;
    }

    public void setDes_ubi(String des_ubi) {
        this.des_ubi = des_ubi;
    }

    public String getBooledit() {
        return booledit;
    }

    public void setBooledit(String booledit) {
        this.booledit = booledit;
    }

    public String getVdocumento() {
        return vdocumento;
    }

    public void setVdocumento(String vdocumento) {
        this.vdocumento = vdocumento;
    }

}
