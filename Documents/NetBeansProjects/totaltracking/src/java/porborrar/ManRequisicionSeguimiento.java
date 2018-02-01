package paquetes;

import tt.seguridad.CatUsuarios;
import tt.general.Accesos;
import tt.general.Conexion;
import tt.mantenimiento.CatSolicitudesDetalle;
import tt.general.CatHistorico;
import tt.mantenimiento.CatSolicitudes;
import tt.general.Login;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

@Named
@ConversationScoped

public class ManRequisicionSeguimiento extends Conexion implements Serializable {

    private static final long serialVersionUID = 8791117452189288L;
    @Inject
    Login cbean;
    private List<CatHistorico> historico;
    private List<CatUsuarios> recibidores;
    private List<CatUsuarios> aprobadores;
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

    private String cod_mae, det_sta, det_can_sol, det_can_ent, det_can_pen, det_fec_cie, fec_cie, cod_equ;
    private String apr_cod_mae, his_cod_mae;
    private String tabindex;
    private Date mfecha, mfecha2;
    private String aprobador, recibidapor, cod_pai, cod_alt, idbuscar;

    private String nombrereporte, nombreexportar;
    private Map<String, Object> parametros;

    public ManRequisicionSeguimiento() {
    }

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

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
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

    public void iniciarventana() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        tabindex = "0";
        mfecha = Date.from(Instant.now());
        mfecha2 = Date.from(Instant.now());

        cod_mae = "";
        apr_cod_mae = "";
        his_cod_mae = "";
        det_sta = "";
        det_can_sol = "";
        det_can_ent = "";
        det_can_pen = "";
        det_fec_cie = format.format(mfecha);
        fec_cie = format.format(mfecha2);
        aprobador = "0";
        recibidapor = "0";
        cod_pai = "";
        cod_alt = "";
        idbuscar = "";
        cod_equ = "0";
        maestrohistoria = new ArrayList<>();
        detalles = new ArrayList<>();
        detallesaprobar = new ArrayList<>();
        detalleshistoria = new ArrayList<>();
        llenarAprobadores();
        llenarRecibidores();
        llenarMaestro();
        llenarMaestroAprobar();

    }

    public void cerrarventana() {
        cod_mae = "";
        apr_cod_mae = "";
        his_cod_mae = "";
        det_sta = "";
        det_can_sol = "";
        det_can_ent = "";
        det_can_pen = "";
        det_fec_cie = "";
        fec_cie = "";
        cod_equ = "0";
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();
        maestroaprobar = new ArrayList<>();
        detallesaprobar = new ArrayList<>();
        maestrohistoria = new ArrayList<>();
        detalleshistoria = new ArrayList<>();
        catmaestrohistoria = new CatSolicitudes();
    }

    public void refrescarventana() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        mfecha = Date.from(Instant.now());
        mfecha2 = Date.from(Instant.now());

        cod_mae = "";
        apr_cod_mae = "";
        his_cod_mae = "";
        det_sta = "";
        det_can_sol = "";
        det_can_ent = "";
        det_can_pen = "";
        det_fec_cie = format.format(mfecha);
        fec_cie = format.format(mfecha2);
        cod_pai = "";
        cod_alt = "";
        idbuscar = "";
        cod_equ = "0";
        maestrohistoria = new ArrayList<>();
        detalles = new ArrayList<>();
        detallesaprobar = new ArrayList<>();
        detalleshistoria = new ArrayList<>();
        catmaestrohistoria = new CatSolicitudes();
        llenarMaestro();
        llenarMaestroAprobar();

    }

    public void llenarAprobadores() {
        try {
            aprobadores = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai order by cod_usu;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Usuarios. " + e.getMessage());
        }
    }

    public void llenarRecibidores() {
        try {
            recibidores = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai order by cod_usu;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Usuarios. " + e.getMessage());
        }
    }

    public void llenarMaestro() {
        try {
            catmaestro = new CatSolicitudes();
            maestro = new ArrayList<>();

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%m/%Y'), "
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
                    + "date_format(mae.fec_cie,'%d/%m/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM req_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.det_sta in (2,4) "
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Seguimiento Requisición. " + e.getMessage());
        }
    }

    public void llenarDetalles() {
        try {
            catdetalles = new CatSolicitudesDetalle();
            detalles = new ArrayList<>();

            String mQuery = "select  "
                    + "det.cod_mae, det.cod_det, det.cod_pai, det.cod_bod, "
                    + "det.cod_ubi, det.cod_ite, det.des_ite, det.det_can_sol, 0, "
                    + "det.det_can_pen, det.non_sto, "
                    + "case det.det_sta "
                    + "when 0 then 'PENDIENTE' "
                    + "when 1 then 'COTIZADO' "
                    + "when 2 then 'COMPRADO' "
                    + "when 3 then 'ENTREGA PARCIAL' "
                    + "when 4 then 'ENTREGADO' "
                    + "when 5 then 'CANCELADO' "
                    + "end as sta , "
                    + "det.fec_cie, "
                    + "det.cos_uni, "
                    + "pai.nom_pai, bod.nom_bod, ubi.nom_ubi "
                    + "from req_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on det.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                    + "left join cat_ubicaciones as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                    + "where det.cod_mae = " + cod_mae + " "
                    + "and det.det_sta in (0,1,2,3) "
                    + "order by det.cod_det asc;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalles en Seguimiento Requisición. " + e.getMessage());
        }
    }

    public void llenarMaestroAprobar() {
        try {
            catmaestroaprobar = new CatSolicitudes();
            maestroaprobar = new ArrayList<>();

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%m/%Y'), "
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
                    + "date_format(mae.fec_cie,'%d/%m/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM req_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.det_sta = 0 "
                    + "order by mae.fec_sol desc, mae.cod_mae desc;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Seguimiento Requisición. " + e.getMessage());
        }
    }

    public void llenarDetallesAprobar() {
        try {
            catdetallesaprobar = new CatSolicitudesDetalle();
            detallesaprobar = new ArrayList<>();

            String mQuery = "select  "
                    + "det.cod_mae, det.cod_det, det.cod_pai, det.cod_bod, "
                    + "det.cod_ubi, det.cod_ite, det.des_ite, det.det_can_sol, det.det_can_ent, "
                    + "det.det_can_pen, det.non_sto, "
                    + "case det.det_sta "
                    + "when 0 then 'PENDIENTE' "
                    + "when 1 then 'COTIZADO' "
                    + "when 2 then 'COMPRADO' "
                    + "when 3 then 'ENTREGA PARCIAL' "
                    + "when 4 then 'ENTREGADO' "
                    + "when 5 then 'CANCELADO' "
                    + "end as sta , "
                    + "det.fec_cie, "
                    + "det.cos_uni, "
                    + "pai.nom_pai, bod.nom_bod, ubi.nom_ubi "
                    + "from req_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on det.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                    + "left join cat_ubicaciones as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                    + "where det.cod_mae = " + apr_cod_mae + " order by det.cod_det asc;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalles en Seguimiento Requisición. " + e.getMessage());
        }
    }

    public void llenarMaestroHistoria() {
        try {
            catmaestrohistoria = new CatSolicitudes();
            maestrohistoria = new ArrayList<>();

            String mQuery = "select "
                    + "mae.cod_mae, mae.cod_alt, "
                    + "date_format(mae.fec_sol,'%d/%m/%Y'), "
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
                    + "date_format(mae.fec_cie,'%d/%m/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(lis.num_ser,'-',maq.nom_equ) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM req_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.cod_mae in (" + idbuscar + ") "
                    + "order by mae.fec_sol desc;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();
            idbuscar = "";
        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro Historia en Seguimiento Requisición. " + e.getMessage());
        }
    }

    public void llenarDetallesHistoria() {
        try {
            catdetalleshistoria = new CatSolicitudesDetalle();
            detalleshistoria = new ArrayList<>();

            String mQuery = "select  "
                    + "det.cod_mae, det.cod_det, det.cod_pai, det.cod_bod, "
                    + "det.cod_ubi, det.cod_ite, det.des_ite, det.det_can_sol, det.det_can_ent, "
                    + "det.det_can_pen, det.non_sto, "
                    + "case det.det_sta "
                    + "when 0 then 'PENDIENTE' "
                    + "when 1 then 'COTIZADO' "
                    + "when 2 then 'COMPRADO' "
                    + "when 3 then 'ENTREGADO PARCIAL' "
                    + "when 4 then 'ENTREGADO' "
                    + "when 5 then 'CANCELADO' "
                    + "end as sta , "
                    + "det.fec_cie, "
                    + "det.cos_uni, "
                    + "pai.nom_pai, bod.nom_bod, ubi.nom_ubi "
                    + "from req_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on det.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                    + "left join cat_ubicaciones as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                    + "where det.cod_mae = " + his_cod_mae + " order by det.cod_det asc;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalles en Seguimiento Requisición. " + e.getMessage());
        }
    }

    public void aprobar() {
        String mQuery;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();

        if (!"".equals(apr_cod_mae) && !"0".equals(aprobador)) {

            try {
                mQuery = "update tbl_rel_man_sol_req set det_sta_sol_req=2 "
                        + "where cod_sol_req= " + apr_cod_mae + " and flg_sol_req='REQ'";
                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "update req_mae set det_sta=2, cod_usu_apr=" + aprobador
                        + " where cod_mae=" + apr_cod_mae + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Aprobar Requisición", "La Requisición ha sido Aprobada.", 1);
            } catch (Exception e) {
                addMessage("Aprobar Requisición", "Error al Aprobar Requisición. " + e.getMessage(), 2);
                System.out.println("Error al Aprobar Requisición. " + e.getMessage());
            }
            maestrohistoria = new ArrayList<>();
            detalles = new ArrayList<>();
            detallesaprobar = new ArrayList<>();
            detalleshistoria = new ArrayList<>();
            llenarMaestro();
            llenarMaestroAprobar();

        } else {
            addMessage("Aprobar Requisición", "Debe elegir una Requisición y un Aprobador.", 2);
        }

        mAccesos.Desconectar();

    }

    public void rechazar() {
        String mQuery;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();

        if ("".equals(cod_mae) == false) {

            try {
                mQuery = "update tbl_rel_man_sol_req set det_sta_sol_req=3 "
                        + "where cod_sol_req= " + apr_cod_mae + " and flg_sol_req='REQ'";
                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "update req_mae set det_sta=3 where cod_mae=" + apr_cod_mae + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Cancelar Requisición", "La Requisición ha sido denegada.", 1);
            } catch (Exception e) {
                addMessage("Cancelar Requisición", "Error al Denegar Requisición. " + e.getMessage(), 2);
                System.out.println("Error al Cancelar Requisición. " + e.getMessage());
            }
            maestrohistoria = new ArrayList<>();
            detalles = new ArrayList<>();
            detallesaprobar = new ArrayList<>();
            detalleshistoria = new ArrayList<>();
            llenarMaestro();
            llenarMaestroAprobar();

        } else {
            addMessage("Cancelar Requisición", "Debe elegir un Registro.", 2);
        }

        mAccesos.Desconectar();

    }

    public void guardar() {
        String mQuery = "";
        try {
            if (validarseguimiento()) {
                Accesos acc = new Accesos();

                int contador = 0, contador2 = 0;
                String mEstado = "", mCostoUni = "", mCanEnt = "", mNonStock = "", cod_lis_pie = "";

                // ********************** Fin Creación Solicitud de Compra *********************
                for (int i = 0; i < detalles.size(); i++) {

                    switch (detalles.get(i).getDet_sta()) {
                        case "PENDIENTE":
                            mEstado = "0";
                            break;
                        case "COTIZADO":
                            mEstado = "1";
                            break;
                        case "COMPRADO":
                            mEstado = "2";
                            break;
                        case "ENTREGA PARCIAL":
                            mEstado = "0";
                            break;
                        case "ENTREGADO":
                            mEstado = "4";
                            break;
                        case "CANCELADO":
                            mEstado = "5";
                            break;
                    }

                    mCostoUni = "0";

                    if ("".equals(detalles.get(i).getDet_can_ent().replace(",", ""))) {
                        mCanEnt = "0";
                    } else {
                        mCanEnt = detalles.get(i).getDet_can_ent().replace(",", "");
                    }

                    acc.Conectar();
                    mQuery = "update req_det set "
                            + "det_sta = " + mEstado + ", "
                            + "det_can_ent = (det_can_ent + " + mCanEnt + "), "
                            + "det_can_pen = (det_can_pen-" + mCanEnt + "), "
                            + "cos_uni = " + mCostoUni + ", "
                            + "fec_cie = str_to_date('" + det_fec_cie + "','%d/%m/%Y') "
                            + "where cod_mae= " + detalles.get(i).getCod_mae()
                            + " and cod_det=" + detalles.get(i).getCod_det() + ";";
                    acc.dmlSQLvariable(mQuery);
                    acc.Desconectar();

                    //********************* Registro de Recibido ***********************************
                    if ("ENTREGADO".equals(detalles.get(i).getDet_sta()) || "ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta())) {
                        acc.Conectar();
                        String mnewCod = acc.strQuerySQLvariable("select ifnull(max(cod_rec),0)+1 as newcod from sol_req_det_rec;");
                        mQuery = "insert into sol_req_det_rec ( cod_rec,cod_mae,det_mae,cod_usu_rec,fec_rec,flg_sol_req,det_can) VALUES "
                                + "( " + mnewCod + "," + cod_mae + "," + detalles.get(i).getCod_det() + "," + recibidapor
                                + ",str_to_date('" + det_fec_cie + "','%d/%m/%Y'),'REQ'," + detalles.get(i).getDet_can_ent() + ");";
                        acc.dmlSQLvariable(mQuery);
                        acc.Desconectar();
                    }

                    //******************* Fin Registro de Recibido *********************************
                    acc.Conectar();
                    //******************* Insertar Existencias globales *********************************
                    if ("0".equals(acc.strQuerySQLvariable("select count(cod_pai) as count from bol_exi_pai where cod_pai = " + cod_pai
                            + " and cod_pie=" + detalles.get(i).getCod_ite() + " and ing_sal = 1;"))) {
                        mQuery = "insert into bol_exi_pai (cod_pai,cod_pie,ing_sal,det_exi) "
                                + "values (" + cod_pai + "," + detalles.get(i).getCod_ite() + ",1,"
                                + detalles.get(i).getDet_can_ent() + ");";

                    } else {
                        mQuery = "update into bol_exi_pai set det_exi = (det_exi + " + detalles.get(i).getDet_can_ent() + ") where "
                                + "cod_pai = " + cod_pai + " "
                                + "and cod_pie = " + detalles.get(i).getCod_ite() + " "
                                + "and ing_sal = 1;";
                    }
                    acc.dmlSQLvariable(mQuery);
                    //******************* Insertar Pendientes por desalojar *********************************
                    String mnewpen = acc.strQuerySQLvariable("select ifnull(max(cod_pen),0)+1 as newcod from tbl_pen_ubi;");
                    mQuery = "insert into tbl_pen_ubi(cod_pen, cod_sol_req, cod_ite, det_can, flg_ing_sal) VALUES( "
                            + mnewpen + "," + cod_mae + "," + detalles.get(i).getCod_ite() + "," + detalles.get(i).getDet_can_ent() + ",1);";
                    acc.dmlSQLvariable(mQuery);
                    // ******************* Borrar Reservas*****************
                    mQuery = "delete from tbl_res where cod_req =" + cod_mae + "and det_req=" + (i + 1) + ";";
                    acc.dmlSQLvariable(mQuery);
                    acc.Desconectar();

                    if ("ENTREGADO".equals(detalles.get(i).getDet_sta()) || "CANCELADO".equals(detalles.get(i).getDet_sta())) {
                        contador = contador + 1;
                    }
                    if ("CANCELADO".equals(detalles.get(i).getDet_sta())) {
                        contador2 = contador2 + 1;
                    }
                    if ("ENTREGADO".equals(detalles.get(i).getDet_sta()) || "ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta())) {
                        if (!"PENDIENTE".equals(det_sta)) {
                            acc.Conectar();
                            mQuery = "update req_mae set det_sta = 4 where cod_mae = " + cod_mae + ";";
                            acc.dmlSQLvariable(mQuery);
                            acc.Desconectar();
                        }

                    }

                }

                if (detalles.size() == contador2) {
                    acc.Conectar();
                    mQuery = "update tbl_rel_man_sol_req set det_sta_sol_req=1 "
                            + "where cod_sol_req= " + cod_mae + " and flg_sol_req='REQ';";
                    acc.dmlSQLvariable(mQuery);
                    mQuery = "update req_mae set det_sta = 1 where cod_ma = " + cod_mae + ";";
                    acc.dmlSQLvariable(mQuery);
                    acc.Desconectar();
                } else if (detalles.size() == contador) {
                    acc.Conectar();
                    mQuery = "update tbl_rel_man_sol_req set det_sta_sol_req=5 "
                            + "where cod_sol_req= " + cod_mae + " and flg_sol_req='REQ';";
                    acc.dmlSQLvariable(mQuery);
                    mQuery = "update req_mae set "
                            + "det_sta = 5, "
                            + "fec_cie = str_to_date('" + fec_cie + "','%d/%m/%Y') "
                            + "where cod_mae = " + cod_mae + ";";
                    acc.dmlSQLvariable(mQuery);
                    acc.Desconectar();
                }
                catdetalles = new CatSolicitudesDetalle();
                detalles = new ArrayList<>();
                llenarMaestro();
                addMessage("Guardar Requisición", "Cambios Almacenados con Éxito.", 1);
            }

        } catch (Exception e) {
            System.out.println("Error al Guardar Cambios en Requisición." + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validarseguimiento() {
        boolean mvalidar = true;
        for (int i = 0; i < detalles.size(); i++) {

            if ("ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta()) && "0".equals(detalles.get(i).getDet_can_ent())) {
                mvalidar = false;
                addMessage("Validar Datos", "Al Detalle con ENTREGA PARCIAL " + detalles.get(i).getDet_can_sol()
                        + " " + detalles.get(i).getDes_ite() + " debe definirse una Cantidad Parcial Entregada.", 2);
            }
            if ("ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta()) && "".equals(detalles.get(i).getDet_can_ent())) {
                mvalidar = false;
                addMessage("Validar Datos", "Al Detalle con ENTREGA PARCIAL " + detalles.get(i).getDet_can_sol()
                        + " " + detalles.get(i).getDes_ite() + " debe definirse una Cantidad Parcial Entregada.", 2);
            }
            if ("ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta()) && detalles.get(i).getDet_can_pen().equals(detalles.get(i).getDet_can_ent())) {
                mvalidar = false;
                addMessage("Validar Datos", "La ENTREGA PARCIAL de " + detalles.get(i).getDet_can_sol()
                        + " " + detalles.get(i).getDes_ite() + " debe ser MENOR que la Cantidad Solicitada Pendiente.", 2);
            }
            if ("ENTREGADO".equals(detalles.get(i).getDet_sta()) && !detalles.get(i).getDet_can_pen().equals(detalles.get(i).getDet_can_ent())) {
                mvalidar = false;
                addMessage("Validar Datos", "Al Detalle ENTREGADO " + detalles.get(i).getDet_can_sol()
                        + " " + detalles.get(i).getDes_ite() + " debe Asignarsele una Cantidad Entregada igual a la Solicitada Pendiente.", 2);
            }
            if ("PENDIENTE".equals(detalles.get(i).getDet_sta()) || "COTIZADO".equals(detalles.get(i).getDet_sta())
                    || "COMPRADO".equals(detalles.get(i).getDet_sta()) || "CANCELADO".equals(detalles.get(i).getDet_sta())) {
                if (!"0".equals(detalles.get(i).getDet_can_ent()) && !"".equals(detalles.get(i).getDet_can_ent())) {
                    mvalidar = false;
                    addMessage("Validar Datos", "El Detalle " + detalles.get(i).getDet_can_sol()
                            + " " + detalles.get(i).getDes_ite()
                            + " No ha sido Entregado Parcial o totalmente y NO PUEDE tener Cantidad Entregada Mayor que Cero.", 2);
                }
            }
            if ("ENTREGADO".equals(detalles.get(i).getDet_sta()) || "ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta())) {
                if ("0".equals(recibidapor)) {
                    mvalidar = false;
                    addMessage("Validar Datos", "El Detalle " + detalles.get(i).getDet_can_sol()
                            + " " + detalles.get(i).getDes_ite() + " Debe ser Recibido por un Responsable.", 2);
                }
            }

            if ("ENTREGADO".equals(detalles.get(i).getDet_sta()) || "ENTREGA PARCIAL".equals(detalles.get(i).getDet_sta())) {
                Accesos macc = new Accesos();
                macc.Conectar();
                String mQuery = "select str_to_date(fec_sol,'%d/%m/%Y') from req_mae where cod_mae=" + cod_mae + ";";
                String mQuery2 = "select DATEDIFF(str_to_date('" + det_fec_cie + "','%d/%m/%Y'), str_to_date('" + macc.strQuerySQLvariable(mQuery) + "','%d/%m/%Y')) as datedif;";
                if (macc.doubleQuerySQLvariable(mQuery2) < 0.0) {
                    mvalidar = false;
                    addMessage("Validar Datos", "La Fecha de Recepción no puede ser anterior a la Creación de Requisición.", 2);
                }
                macc.Desconectar();
            }

        }

        return mvalidar;
    }

    public void salidaalmacen(String cod_lis_pie, String fec_tra, String cod_bod, String cod_ubi, String det_can,
            String cod_pie, String det_cos, int i, String cod_usu) {
        try {
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            String mQuery = "", mQuery2 = "", cuentaregs = "";

            String mValues = "";

//              ********************************** Existencias ****************************************
            Double mCantidad;
            mCantidad = Double.valueOf(det_can.replace(",", ""));
            //código correlativo existencia histórica de artículo
            String cod_cor_exi_art = mAccesos.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                    + "as codigo from tbl_pie_his;");
            //Código correlativo diario existencia histórica de artículo
            String cor_dia = mAccesos.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                    + "as cordia from tbl_pie_his "
                    + "where "
                    + "cod_pie=" + cod_pie + " "
                    + "and fec_his=STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                    + "and flg_ing = 0"
                    + ";");
            //Contador de Registros históricos con esa pieza.
            cuentaregs = mAccesos.strQuerySQLvariable("select ifnull(count(cod_his),0) "
                    + "as codigo from tbl_pie_his where cod_pie=" + cod_pie + ";");
            //Costo promedio
            Double cPromedio, exisAnt, cunitario, mNuevaExistencia;

            if ("0".equals(cuentaregs)) {
                cPromedio = Double.valueOf(det_cos.replace(",", ""));
                exisAnt = 0.0;
            } else {

                cPromedio = mAccesos.doubleQuerySQLvariable("select (ifnull((can_exi*cos_pro),0)+"
                        + (Double.valueOf(det_can) * Double.valueOf(det_cos)) + ")"
                        + "/(IFNULL(can_exi,0)+" + mCantidad + ") as Cpromedio "
                        + "from tbl_pie_his "
                        + "where "
                        + "cod_pie=" + cod_pie + " "
                        + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and det_sta = 0 "
                        + "order by fec_his desc,"
                        + "ord_dia desc "
                        + "limit 1;");

                //Existencia Anterior
                exisAnt = mAccesos.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                        + "from tbl_pie_his "
                        + "where "
                        + "cod_pie=" + cod_pie + " "
                        + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and det_sta = 0 "
                        + "order by fec_his desc,"
                        + "ord_dia desc "
                        + "limit 1;");

            }
            //Inserta Registro

            mNuevaExistencia = (exisAnt - mCantidad);

            cunitario = Double.valueOf(det_cos.replace(",", ""));
            mQuery2 = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                    + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu) "
                    + "VALUES (" + cod_cor_exi_art + "," + cod_pie + ","
                    + "STR_TO_DATE('" + fec_tra + "','%d/%m/%Y')" + "," + cor_dia + ",1,"
                    + cod_lis_pie + "," + (i + 1) + "," + mCantidad + "," + cunitario + ","
                    + mNuevaExistencia + ","
                    + cPromedio + "," + "0" + "," + "now()" + "," + cod_usu + ");";

            mAccesos.dmlSQLvariable(mQuery2);

            // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
            String contasiguientes = mAccesos.strQuerySQLvariable("select count(cod_his) "
                    + "from tbl_pie_his where fec_his=STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                    + "and ord_dia >" + cor_dia + " "
                    + "and cod_pie=" + cod_pie + " "
                    + "and det_sta = 0 "
                    + ";");
            contasiguientes = String.valueOf(
                    Integer.valueOf(contasiguientes)
                    + Integer.valueOf(mAccesos.strQuerySQLvariable("select count(cod_his) "
                            + "from tbl_pie_his "
                            + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "and cod_pie=" + cod_pie + " "
                            + "and det_sta = 0 "
                            + ";")));

            Double nuevacantidad = mNuevaExistencia;
            if ("0".equals(contasiguientes) == false) {
                try {
                    historico = new ArrayList<>();

                    //Double cos_uni_sal = 0.0;
                    ResultSet resvariable;
                    resvariable = mAccesos.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                            + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                            + "cod_usu "
                            + "from tbl_pie_his "
                            + "where fec_his >= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "and cod_pie=" + cod_pie + " "
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
                        mQuery2 = "update tbl_pie_his set "
                                + "cos_pro= " + cPromedio + " ,"
                                + "can_exi= " + nuevacantidad + " "
                                + "where "
                                + "cod_his = " + seriehistorica1.getCod_his()
                                + ";";
                        mAccesos.dmlSQLvariable(mQuery2);
                    }

                } catch (Exception e) {
                    System.out.println("Error en actualización de costos posteriores Agregar. " + e.getMessage());
                }

            }

            // Tratamiento tabla tbl_existencias
            String mContador = mAccesos.strQuerySQLvariable("select count(cod_pie) as contador from tbl_existencias "
                    + "where "
                    + "cod_pai=" + cod_pai + " "
                    + "and cod_bod=" + cod_bod + " "
                    + "and cod_ubi=" + cod_ubi + " "
                    + "and cod_pie=" + cod_pie
                    + ";");

            if ("0".equals(mContador)) {
                String codexi = mAccesos.strQuerySQLvariable("select ifnull(max(cod_exi),0)+1 "
                        + "as cod from tbl_existencias;");

                mQuery2 = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                        + "VALUES ("
                        + codexi + ","
                        + cod_pie + ","
                        + cod_pai + ","
                        + cod_bod + ","
                        + cod_ubi + ","
                        + nuevacantidad + ","
                        + cPromedio
                        + ");";
            } else {

                mQuery2 = "update tbl_existencias set "
                        + "det_can=" + nuevacantidad + ","
                        + "cos_pro=" + cPromedio + " "
                        + "where "
                        + "cod_pai=" + cod_pai + " "
                        + "and cod_bod=" + cod_bod + " "
                        + "and cod_ubi=" + cod_ubi + " "
                        + "and cod_pie=" + cod_pie + ";";
            }

            mAccesos.dmlSQLvariable(mQuery2);

//              ********************************* Fin Existencias ************************************
            mValues = mValues + "," + "("
                    + cod_lis_pie + ","
                    + (i + 1) + ","
                    + cod_pie + ","
                    + cod_bod + ","
                    + cod_ubi + ","
                    + det_can + ","
                    + det_cos + ","
                    + "0"
                    + ")";

            // ******************* Inserta Detalles*****************
            mValues = mValues.substring(1);
            mQuery = " insert into tbl_pie_det(cod_enc,cod_det,"
                    + "cod_pie,cod_bod,cod_ubi,det_can,det_cos,det_sta) "
                    + "values " + mValues + ";";

            mAccesos.dmlSQLvariable(mQuery);

            mAccesos.Desconectar();

        } catch (Exception e) {
            addMessage("Guardar Almacén", "Error al momento de guardar la información. " + e.getMessage(), 2);
            System.out.println("Error al Sacar Detalle Almacén en Seguimiento de Requisición. " + e.getMessage());
        }

    }

    //******************* Reporte ***************************************
    public void limpiar() {
        if ("".equals(apr_cod_mae) || "0".equals(apr_cod_mae)) {
            addMessage("Imprimir Requisición", "Debe elegir un Registro.", 2);
            RequestContext.getCurrentInstance().update("frmSeguimientoReq");
            RequestContext.getCurrentInstance().execute("PF('wCodImpReq').hide()");
        } else {
            RequestContext.getCurrentInstance().execute("PF('wCodImpReq').show()");
        }
    }

    public void cerrarcodimp() {
        apr_cod_mae = "";
        cod_alt = "";
        llenarMaestroAprobar();
        catmaestroaprobar = new CatSolicitudes();
        detallesaprobar = new ArrayList<>();
    }

    public void validarcodimp() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        try {
            mQuery = "select count(cod_alt) from req_mae where UPPER(cod_alt)='" + cod_alt.replace("'", "").toUpperCase() + "' and cod_mae <> " + apr_cod_mae + ";";
            if ("0".equals(mAccesos.strQuerySQLvariable(mQuery))) {
                if ("".equals(cod_alt)) {
                    addMessage("Modificar Requisición", "Código de Impresión Vacío.", 2);
                } else {
                    addMessage("Modificar Requisición", "Código de Impresión Válido.", 1);
                }
            } else {
                addMessage("Modificar Requisición", "Código de Impresión ya Existe.", 2);
            }
        } catch (Exception e) {
            addMessage("Modificar Requisición", "Error al Validar Código de Impresión. " + e.getMessage(), 2);
            System.out.println("Error al Validar Código de Impresión en Seguimiento Requisición. " + e.getMessage() + " Query: " + mQuery);
        }

        mAccesos.Desconectar();

    }

    public void editarcodimp() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        try {
            mQuery = "select count(cod_alt) from req_mae where UPPER(cod_alt)='" + cod_alt.trim().replace("'", "").toUpperCase() + "' and cod_mae <> " + apr_cod_mae + ";";
            if ("0".equals(mAccesos.strQuerySQLvariable(mQuery))) {
                mQuery = "update req_mae set cod_alt='" + cod_alt + "' where cod_mae=" + apr_cod_mae + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Modificar Requisición", "Código de Impresión Actualizado.", 1);
            } else if ("".equals(cod_alt.trim())) {
                mQuery = "update sol_mae set cod_alt='' where cod_mae=" + apr_cod_mae + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Modificar Requisición", "Código de Impresión Actualizado.", 1);
            } else {
                addMessage("Modificar Requisición", "Código de Impresión ya Existe.", 2);
            }
        } catch (Exception e) {
            addMessage("Modificar Requisición", "Error al Actualizar Código de Impresión. " + e.getMessage(), 2);
            System.out.println("Error al Actualizar Código de Impresión. " + e.getMessage() + " Query: " + mQuery);
        }

        mAccesos.Desconectar();

    }

    public void ejecutarreporte() {
        try {
            if (!"".equals(apr_cod_mae) && !"0".equals(apr_cod_mae)) {
                editarcodimp();
                paramRepVarios();
                verPDF();
            } else {
                addMessage("Imprimir Requisición", "Debe elegir un Registro.", 2);
            }
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Seguimiento Requisición" + e.getMessage());
        }

    }

    public void paramRepVarios() {
        parametros = new HashMap<>();
        parametros.put("solicitud", apr_cod_mae);
        nombrereporte = "/reportes/solicitud.jasper";
        nombreexportar = "Requisición" + apr_cod_mae;

    }

    public void verPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, Conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
            Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en verPDF en Seguimiento Requisición." + e.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        det_sta = ((CatSolicitudes) event.getObject()).getDet_sta();
        cod_pai = ((CatSolicitudes) event.getObject()).getCod_pai();
        cod_alt = ((CatSolicitudes) event.getObject()).getCod_alt();
        cod_equ = ((CatSolicitudes) event.getObject()).getCod_maq();

        mfecha = Date.from(Instant.now());
        mfecha2 = Date.from(Instant.now());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        det_fec_cie = format.format(mfecha);
        fec_cie = format.format(mfecha2);
        recibidapor = "0";

        llenarDetalles();
    }

    public void onRowUnselect(UnselectEvent event) {
        detalles = new ArrayList<>();
    }

    public void onRowAprobarSelect(SelectEvent event) {
        apr_cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        cod_alt = ((CatSolicitudes) event.getObject()).getCod_alt();
        llenarDetallesAprobar();
    }

    public void onRowAprobarUnselect(UnselectEvent event) {
        detallesaprobar = new ArrayList<>();
    }

    public void onRowHistoriaSelect(SelectEvent event) {
        his_cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        llenarDetallesHistoria();
    }

    public void onRowHistoriaUnselect(UnselectEvent event) {
        detalleshistoria = new ArrayList<>();
    }

    public void onRowEdit(RowEditEvent event) {

        addMessage("Modificar Item", "Estado Modificado Satisfactoriamente.", 1);
    }

    public void onRowCancel(RowEditEvent event) {
        addMessage("Modificar Item", "Modificación Cancelada.", 2);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        String mQuery = "", mValor = "";
        int alteredRow = event.getRowIndex();
        String column_name = event.getColumn().getHeaderText();

        switch (column_name) {
            case "C.Ent.":

                if (Double.valueOf(newValue.toString()) > Double.valueOf(detalles.get(alteredRow).getDet_can_pen())) {
                    detalles.set(alteredRow, new CatSolicitudesDetalle(
                            detalles.get(alteredRow).getCod_mae(),
                            detalles.get(alteredRow).getCod_det(),
                            detalles.get(alteredRow).getCod_pai(),
                            detalles.get(alteredRow).getCod_bod(),
                            detalles.get(alteredRow).getCod_ubi(),
                            detalles.get(alteredRow).getCod_ite(),
                            detalles.get(alteredRow).getDes_ite(),
                            detalles.get(alteredRow).getDet_can_sol(),
                            detalles.get(alteredRow).getDet_can_sol(),
                            detalles.get(alteredRow).getDet_can_pen(),
                            detalles.get(alteredRow).getNon_sto(),
                            detalles.get(alteredRow).getDet_sta(),
                            detalles.get(alteredRow).getFec_cie(),
                            detalles.get(alteredRow).getCos_uni(),
                            detalles.get(alteredRow).getNompai(),
                            detalles.get(alteredRow).getNombod(),
                            detalles.get(alteredRow).getNomubi()
                    ));
                    mValor = detalles.get(alteredRow).getDet_can_sol();
                    addMessage("Modificar Cantidad Entregada", "La Cantidad Entregada no puede superar la Cantidad Pendiente Solicitada.", 2);

                } else {
                    mValor = newValue.toString();
                }

                //*********************Validar Existencias ***********************************
                try {
                    Accesos macc = new Accesos();
                    macc.Conectar();
                    mQuery = "select "
                            + "ifnull((arreglo.existencia - arreglo.reserva), 0 )- " + mValor.replace(",", "") + " as exireal "
                            + "from ("
                            + "select "
                            + "exi.cod_pie, pie.cod_ref, pie.nom_pie,"
                            + "exi.det_can as existencia, "
                            + "ifnull(sum(res.det_can),0) as reserva "
                            + "from tbl_existencias as exi "
                            + "left join tbl_res as res on exi.cod_pai = res.cod_pai and exi.cod_bod = res.cod_bod and exi.cod_ubi = res.cod_ubi and exi.cod_pie = res.cod_pie "
                            + "left join cat_pie as pie on exi.cod_pie = pie.cod_pie "
                            + "where "
                            + "exi.cod_pai = " + detalles.get(alteredRow).getCod_pai() + " "
                            + "and exi.cod_bod = " + detalles.get(alteredRow).getCod_bod() + " "
                            + "and exi.cod_ubi = " + detalles.get(alteredRow).getCod_ubi() + " "
                            + "and exi.cod_pie = " + detalles.get(alteredRow).getCod_ite() + " "
                            + "group by exi.cod_pie, pie.cod_ref, pie.nom_pie, exi.det_can "
                            + ") as arreglo;";

                    if (macc.doubleQuerySQLvariable(mQuery) < 0.0) {
                        addMessage("Validar Datos", "La Cantidad de Piezas solicitada sobrepasa la Cantidad de Existencias en el Almacén. Verificar Existencias de la Pieza Solicitada.", 2);
                        detalles.set(alteredRow, new CatSolicitudesDetalle(
                                detalles.get(alteredRow).getCod_mae(),
                                detalles.get(alteredRow).getCod_det(),
                                detalles.get(alteredRow).getCod_pai(),
                                detalles.get(alteredRow).getCod_bod(),
                                detalles.get(alteredRow).getCod_ubi(),
                                detalles.get(alteredRow).getCod_ite(),
                                detalles.get(alteredRow).getDes_ite(),
                                detalles.get(alteredRow).getDet_can_sol(),
                                "0",
                                detalles.get(alteredRow).getDet_can_pen(),
                                detalles.get(alteredRow).getNon_sto(),
                                detalles.get(alteredRow).getDet_sta(),
                                detalles.get(alteredRow).getFec_cie(),
                                detalles.get(alteredRow).getCos_uni(),
                                detalles.get(alteredRow).getNompai(),
                                detalles.get(alteredRow).getNombod(),
                                detalles.get(alteredRow).getNomubi()
                        ));
                    } else {
                        String codexistente = macc.strQuerySQLvariable("select count(cod_res) from tbl_res where cod_req=" + cod_mae
                                + " and det_req=" + detalles.get(alteredRow).getCod_det() + ";");
                        if ("0".equals(codexistente)) {
                            String newcod = macc.strQuerySQLvariable("select ifnull(max(cod_res),0) + 1 from tbl_res;");
                            mQuery = "insert into tbl_res (cod_res,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,fec_res,"
                                    + "cod_lis_pie,cod_det,cod_lis_equ,cod_man,det_man,cod_usu,cod_req,det_req) VALUES "
                                    + "(" + newcod + "," + detalles.get(alteredRow).getCod_ite() + "," + detalles.get(alteredRow).getCod_pai()
                                    + "," + detalles.get(alteredRow).getCod_bod() + ","
                                    + detalles.get(alteredRow).getCod_ubi() + "," + mValor + ",str_to_date('" + det_fec_cie + "','%d/%m/%Y'),"
                                    + "0" + "," + "0" + "," + cod_equ + ","
                                    + "0" + "," + "0" + "," + recibidapor + "," + cod_mae
                                    + "," + detalles.get(alteredRow).getCod_det() + ")";
                        } else {
                            mQuery = "update tbl_res set "
                                    + "det_can=" + mValor + " "
                                    + ",fec_res=str_to_date('" + det_fec_cie + "','%d/%m/%Y') "
                                    + "where cod_req = " + cod_mae + " "
                                    + "and det_req =" + detalles.get(alteredRow).getCod_det() + ";";
                        }

                        macc.dmlSQLvariable(mQuery);

                        addMessage("Modificar Cantidad Entregada", "Cantidad Entregada Modificada de " + oldValue + " a " + newValue + ".", 1);

                    }

                    macc.Desconectar();
                } catch (Exception e) {
                    System.out.println("Error en validación Reservas y Existencias Edición Celda Requisición. " + e.getMessage() + " Query: " + mQuery);
                }

                break;
            case "Cost.Uni.":
                addMessage("Modificar Costo Unitario Detalle", "Costo Unitario del Detalle Modificado de " + oldValue + " a " + newValue + ".", 1);
                break;
            case "Estado":
                addMessage("Modificar Estado Detalle", "Estado del Detalle Modificado de " + oldValue + " a " + newValue + ".", 1);
                break;
        }

        /*
        if (newValue != null && !newValue.equals(oldValue)) {
            addMessage("Modificar Item", "Estado Modificado de " + oldValue + " a " + newValue + ".", 1);
        }
         */
    }

    public void dateCieSelected(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_cie = format.format(date);
        det_fec_cie = format.format(date);
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabSegReq":
                tabindex = "0";
                break;
            case "tabAproReq":
                tabindex = "1";
                break;
            case "tabHistoReq":
                tabindex = "2";
                break;
        }
        //System.out.println(tabindex);
        //RequestContext.getCurrentInstance().update(":frmListaEquipos:tvLE");
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

}
