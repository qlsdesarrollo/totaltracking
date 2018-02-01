package paquetes;

import tt.mantenimiento.CatListaEquipos;
import tt.seguridad.CatUsuarios;
import tt.general.Accesos;
import tt.general.CatDepartamentos;
import tt.mantenimiento.CatSolicitudesDetalle;
import tt.general.CatBodegas;
import tt.general.CatUbicaciones;
import tt.general.CatPaises;
import tt.mantenimiento.CatSolicitudes;
import tt.general.Login;
import tt.mantenimiento.CatPiezas;
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
import org.primefaces.event.TabChangeEvent;

@Named
@ConversationScoped
public class ManRequisicionNueva implements Serializable {

    private static final long serialVersionUID = 8791118726471918L;
    @Inject
    Login cbean;
    private CatSolicitudes catmaestro;
    private List<CatSolicitudes> maestro;
    private CatSolicitudesDetalle catdetalles;
    private List<CatSolicitudesDetalle> detalles;

    private List<CatUsuarios> usuarios;

    private List<CatPiezas> piezas;

    private List<CatListaEquipos> equipos;

    private List<CatDepartamentos> departamentos;

    private List<CatPaises> paises;

    private List<CatBodegas> bodegas;

    private List<CatUbicaciones> ubicaciones;

    private String cod_mae, cod_alt, fec_sol, cod_usu_sol, cod_usu_apr, cod_usu_rec, cod_dep, det_uso, cod_maq, det_sta, det_obs, fec_cie, cod_pai, flg_loc;
    private String det_cod_det, det_cod_pai, det_cod_bod, det_cod_ubi, det_cod_ite, det_des_ite, det_det_can_sol, det_det_can_ent, det_det_can_pen, det_non_sto, det_det_sta, det_fec_cie;
    private Date mfecha;
    private String tabindex, boolNS;

    public ManRequisicionNueva() {
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

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public List<CatPiezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatPiezas> piezas) {
        this.piezas = piezas;
    }

    public List<CatListaEquipos> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatListaEquipos> equipos) {
        this.equipos = equipos;
    }

    public List<CatDepartamentos> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<CatDepartamentos> departamentos) {
        this.departamentos = departamentos;
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

    public String getFec_sol() {
        return fec_sol;
    }

    public void setFec_sol(String fec_sol) {
        this.fec_sol = fec_sol;
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

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getDet_uso() {
        return det_uso;
    }

    public void setDet_uso(String det_uso) {
        this.det_uso = det_uso;
    }

    public String getCod_maq() {
        return cod_maq;
    }

    public void setCod_maq(String cod_maq) {
        this.cod_maq = cod_maq;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFec_cie() {
        return fec_cie;
    }

    public void setFec_cie(String fec_cie) {
        this.fec_cie = fec_cie;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getFlg_loc() {
        return flg_loc;
    }

    public void setFlg_loc(String flg_loc) {
        this.flg_loc = flg_loc;
    }

    public String getDet_cod_det() {
        return det_cod_det;
    }

    public void setDet_cod_det(String det_cod_det) {
        this.det_cod_det = det_cod_det;
    }

    public String getDet_cod_pai() {
        return det_cod_pai;
    }

    public void setDet_cod_pai(String det_cod_pai) {
        this.det_cod_pai = det_cod_pai;
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

    public String getDet_det_can_sol() {
        return det_det_can_sol;
    }

    public void setDet_det_can_sol(String det_det_can_sol) {
        this.det_det_can_sol = det_det_can_sol;
    }

    public String getDet_det_can_ent() {
        return det_det_can_ent;
    }

    public void setDet_det_can_ent(String det_det_can_ent) {
        this.det_det_can_ent = det_det_can_ent;
    }

    public String getDet_det_can_pen() {
        return det_det_can_pen;
    }

    public void setDet_det_can_pen(String det_det_can_pen) {
        this.det_det_can_pen = det_det_can_pen;
    }

    public String getDet_non_sto() {
        return det_non_sto;
    }

    public void setDet_non_sto(String det_non_sto) {
        this.det_non_sto = det_non_sto;
    }

    public String getDet_det_sta() {
        return det_det_sta;
    }

    public void setDet_det_sta(String det_det_sta) {
        this.det_det_sta = det_det_sta;
    }

    public String getDet_fec_cie() {
        return det_fec_cie;
    }

    public void setDet_fec_cie(String det_fec_cie) {
        this.det_fec_cie = det_fec_cie;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getBoolNS() {
        return boolNS;
    }

    public void setBoolNS(String boolNS) {
        this.boolNS = boolNS;
    }

    public void iniciarventana() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        mfecha = Date.from(Instant.now());
        cod_mae = "";
        cod_alt = "";
        fec_sol = format.format(mfecha);
        cod_usu_sol = cbean.getCod_usu();
        cod_usu_apr = "0";
        cod_usu_rec = "0";
        cod_dep = cbean.getCod_dep();
        det_uso = "";
        cod_maq = "0";
        det_sta = "0";
        det_obs = "";
        fec_cie = "";
        cod_pai = cbean.getCod_pai();
        flg_loc = "0";
        det_cod_det = "";
        det_cod_pai = "";
        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_cod_ite = "0";
        det_des_ite = "";
        det_det_can_sol = "";
        det_det_can_ent = "0";
        det_det_can_pen = "0";
        det_non_sto = "0";
        det_det_sta = "0";
        det_fec_cie = "null";
        tabindex = "0";
        boolNS = "true";
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();
        llenarPaises();
        llenarPiezas();
        llenarUsuarios();
        llenarEquipos();
        llenarDepartamentos();
    }

    public void cerrarventana() {
        mfecha = Date.from(Instant.now());
        cod_mae = "";
        cod_alt = "";
        fec_sol = "";
        cod_usu_sol = cbean.getCod_usu();
        cod_usu_apr = "0";
        cod_usu_rec = "0";
        cod_dep = cbean.getCod_dep();
        det_uso = "";
        cod_maq = "0";
        det_sta = "0";
        det_obs = "";
        fec_cie = "";
        cod_pai = cbean.getCod_pai();
        flg_loc = "0";
        det_cod_det = "";
        det_cod_pai = "";
        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_cod_ite = "0";
        det_des_ite = "";
        det_det_can_sol = "";
        det_det_can_ent = "0";
        det_det_can_pen = "0";
        det_non_sto = "0";
        det_det_sta = "0";
        det_fec_cie = "";
        tabindex = "0";
        usuarios = new ArrayList<>();
        equipos = new ArrayList<>();
        departamentos = new ArrayList<>();
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();
    }

    public void nuevo() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        mfecha = Date.from(Instant.now());
        cod_mae = "";
        cod_alt = "";
        fec_sol = format.format(mfecha);
        cod_usu_sol = cbean.getCod_usu();
        cod_usu_apr = "0";
        cod_usu_rec = "0";
        cod_dep = cbean.getCod_dep();
        det_uso = "";
        cod_maq = "0";
        det_sta = "0";
        det_obs = "";
        fec_cie = "";
        cod_pai = cbean.getCod_pai();
        flg_loc = "0";
        det_cod_det = "";
        det_cod_pai = "";
        det_cod_bod = "0";
        det_cod_ubi = "0";
        det_cod_ite = "0";
        det_des_ite = "";
        det_det_can_sol = "";
        det_det_can_ent = "0";
        det_det_can_pen = "0";
        det_non_sto = "0";
        det_det_sta = "0";
        det_fec_cie = "null";
        tabindex = "0";
        boolNS = "true";
        catmaestro = new CatSolicitudes();
        catdetalles = new CatSolicitudesDetalle();
        detalles = new ArrayList<>();
    }

    public void iniciarventanasearch() {
        llenarMaestro();
    }

    public void cerrarventanasearch() {
        catmaestro = new CatSolicitudes();
        maestro = new ArrayList<>();
    }

    public void llenarUsuarios() {
        try {

            usuarios = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, "
                    + "usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, "
                    + "ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai order by cod_usu;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                usuarios.add(new CatUsuarios(
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
            System.out.println("Error en el llenado de Usuarios en Nueva Requisición. " + e.getMessage());
        }
    }

    public void llenarEquipos() {
        String mQuery = "";
        try {
            equipos = new ArrayList<>();

            mQuery = "select lequ.cod_lis_equ, lequ.cod_pai, lequ.cod_equ, lequ.cod_pro, "
                    + "lequ.cod_cli, lequ.num_mod, lequ.num_ser, lequ.des_equ, lequ.des_ubi, "
                    + "DATE_FORMAT(lequ.fec_fab,'%d/%m/%Y') as ffab, "
                    + "DATE_FORMAT(lequ.fec_com,'%d/%m/%Y') as fcom, "
                    + "DATE_FORMAT(lequ.fec_adq,'%d/%m/%Y') as fadq,"
                    + "DATE_FORMAT(lequ.fec_pue_ser,'%d/%m/%Y') as fps,"
                    + "DATE_FORMAT(lequ.fec_ult_man,'%d/%m/%Y') as fum, "
                    + "DATE_FORMAT(lequ.fec_ret,'%d/%m/%Y') as fret,"
                    + "lequ.cod_bar,"
                    + "cpai.nom_pai as nompai,"
                    + "cequ.nom_equ as nomequ, "
                    + "cpro.nom_pro as nompro, "
                    + "ccli.nom_cli as nomcli "
                    + "from lis_equ as lequ "
                    + "left join cat_pai as cpai on lequ.cod_pai = cpai.cod_pai "
                    + "left join cat_equ as cequ on lequ.cod_equ = cequ.cod_equ "
                    + "left join cat_pro as cpro on lequ.cod_pro = cpro.cod_pro and lequ.cod_pai = cpro.cod_pai "
                    + "left join cat_cli as ccli on lequ.cod_cli = ccli.cod_cli and lequ.cod_pai = ccli.cod_pai "
                    + "where "
                    + "lequ.cod_pai = " + cod_pai + " "
                    + "order by lequ.cod_pai, lequ.cod_equ, lequ.num_ser ,lequ.cod_lis_equ;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatListaEquipos(
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Equipos en Nueva Requisición. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarDepartamentos() {
        try {

            departamentos = new ArrayList<>();

            String mQuery = "select cod_dep, cod_pai, nom_dep "
                    + "from cat_dep where cod_pai = " + cod_pai + " order by cod_dep;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                departamentos.add(new CatDepartamentos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Departamentos en Nueva Requisición. " + e.getMessage());
        }
    }

    public void llenarPaises() {
        try {
            paises = new ArrayList<>();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en Nueva Requisición. " + e.getMessage());
        }
    }

    
    public void llenarPiezas() {
        String mQuery = "";
        try {

            piezas = new ArrayList<>();

            mQuery = "select pie.cod_pie,pie.cod_ref,pie.cod_equ,"
                    + "pie.nom_pie,pie.des_pie,equ.nom_equ,pie.cod_cat,"
                    + "pie.det_ima,pie.vid_uti,pie.cod_gru,pie.cod_lin, "
                    + "gru.nom_gru,"
                    + "lin.nom_lin "
                    + "from cat_pie as pie "
                    + "left join cat_equ as equ on equ.cod_equ=pie.cod_equ "
                    + "left join cat_gru as gru on pie.cod_gru = gru.cod_gru "
                    + "left join cat_lin as lin on pie.cod_gru = lin.cod_gru and lin.cod_lin = pie.cod_lin "
                    + "order by pie.cod_pie;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezas.add(new CatPiezas(
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Piezas en Nueva Requisición. " + e.getMessage() + " Query: " + mQuery);
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
                    + "mae.det_sta, mae.det_obs, "
                    + "date_format(mae.fec_cie,'%d/%m/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(maq.nom_equ,'-',lis.num_ser) as nomequ, "
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM req_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.cod_usu_sol = " + cod_usu_sol + " "
                    + "and mae.det_sta = 0 "
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
            System.out.println("Error en el llenado Maestro en Crear Nueva Requisición. " + e.getMessage());
        }
    }

    public void llenarDetalles() {
        try {
            catdetalles = new CatSolicitudesDetalle();
            detalles = new ArrayList<>();

            String mQuery = "select  "
                    + "det.cod_mae, det.cod_det, det.cod_pai, det.cod_bod, "
                    + "det.cod_ubi, det.cod_ite, det.des_ite, det.det_can_sol, det.det_can_ent, "
                    + "det.det_can_pen, det.non_sto, det.det_sta, det.fec_cie,det.cos_uni, "
                    + "pai.nom_pai, bod.nom_bod, ubi.nom_ubi "
                    + "from req_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on det.cod_pai = bod.cod_pai and det.cod_bod = bod.id_bod "
                    + "left join cat_ubicaciones as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.id_ubi "
                    + "where det.cod_mae = " + cod_mae + " order by det.cod_det asc;";
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
            System.out.println("Error en el llenado Detalles en Crear Nueva Requisición. " + e.getMessage());
        }
    }

    public void agregardetalle() {
        if (validardetalle()) {
            int correlativo = 0, insert = 0;
            try {
                for (int i = 0; i < detalles.size(); i++) {
                    if (Integer.valueOf(detalles.get(i).getCod_det()) > correlativo) {
                        correlativo = Integer.valueOf(detalles.get(i).getCod_det());
                    }
                }

                if (insert == 0 && detalles.size() < 17) {
                    Accesos macc = new Accesos();
                    macc.Conectar();

                    det_des_ite = macc.strQuerySQLvariable("select nom_pie from cat_pie where cod_pie=" + det_cod_ite + ";");

                    String nompai, nombod, nomubi;
                    nompai = macc.strQuerySQLvariable("select nom_pai from cat_pai where cod_pai=" + cod_pai + ";");
                    nombod = "";
                    nomubi = "";
                    macc.Desconectar();

                    detalles.add(new CatSolicitudesDetalle(
                            "",
                            String.valueOf(correlativo + 1),
                            cod_pai,
                            det_cod_bod,
                            det_cod_ubi,
                            det_cod_ite,
                            det_des_ite.replace("'", ""),
                            det_det_can_sol,
                            det_det_can_ent,
                            det_det_can_sol,
                            det_non_sto,
                            det_det_sta,
                            det_fec_cie,
                            "0",
                            nompai,
                            nombod,
                            nomubi
                    ));

                    det_des_ite = "";
                    det_det_can_sol = "";
                    det_det_can_ent = "0";
                    det_det_can_pen = "0";
                    det_non_sto = "0";
                    det_det_sta = "0";
                    det_fec_cie = "null";

                } else {
                    addMessage("Validar Datos", "El número máximo de Items permitidos por formulario ha sido alcanzado.", 2);
                }

            } catch (Exception e) {
                System.out.println("Error en agregardetalle de Nueva Requisición." + e.getMessage());
            }
        }

    }

    public boolean validardetalle() {
        boolean mvalidar = true;

        if ("0".equals(det_cod_ite)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Escoger un Item.", 2);
        }
        if ("0".equals(cod_pai)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País.", 2);
        }

        if ("".equals(det_det_can_sol)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar una Cantidad mayor que Cero.", 2);
        } else if (Double.valueOf(det_det_can_sol) <= 0) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar una Cantidad mayor que Cero.", 2);
        }
        return mvalidar;

    }

    public void guardar() {
        String mQuery = "";
        try {
            if (validarg()) {
                Accesos acc = new Accesos();
                acc.Conectar();
                if ("".equals(cod_mae)) {
                    mQuery = "select ifnull(max(cod_mae),0)+1 as codigo from req_mae;";
                    cod_mae = acc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into req_mae (cod_mae, cod_alt, fec_sol, cod_usu_sol, cod_usu_apr, "
                            + "cod_usu_rec, cod_dep, det_uso, cod_maq, det_sta, det_obs, fec_cie, flg_loc,cod_pai) "
                            + "values (" + cod_mae + ",'" + cod_alt + "',str_to_date('" + fec_sol + "','%d/%m/%Y'),"
                            + cod_usu_sol + "," + cod_usu_apr + "," + cod_usu_rec + "," + cod_dep + ",'" + det_uso.replace("'", " ") + "',"
                            + cod_maq + "," + det_sta + ",'" + det_obs.replace("'", " ") + "',null," + flg_loc + "," + cod_pai + ");";
                } else {
                    mQuery = "update req_mae set "
                            + "fec_sol=str_to_date('" + fec_sol + "','%d/%m/%Y %h:%i'),"
                            + "cod_usu_sol=" + cod_usu_sol + ","
                            + "cod_pai=" + cod_pai + ","
                            + "cod_dep=" + cod_dep + ","
                            + "det_uso='" + det_uso.replace("'", "") + "',"
                            + "cod_maq= " + cod_maq + ","
                            + "flg_loc= " + flg_loc + ","
                            + "det_obs='" + det_obs.replace("'", "") + "' "
                            + "where cod_mae=" + cod_mae + ";";
                }
                acc.dmlSQLvariable(mQuery);
                mQuery = "delete from req_det where cod_mae=" + cod_mae + ";";
                acc.dmlSQLvariable(mQuery);
                String mValores = "";
                int mCorrela = 1;
                for (int i = 0; i < detalles.size(); i++) {
                    mValores = mValores + ",("
                            + cod_mae + ","
                            + mCorrela + ","
                            + detalles.get(i).getCod_pai() + ","
                            + detalles.get(i).getCod_bod() + ","
                            + detalles.get(i).getCod_ubi() + ","
                            + detalles.get(i).getCod_ite() + ",'"
                            + acc.strQuerySQLvariable("select ifnull(concat(cod_ref,' - '),'') as codref from cat_pie where cod_pie = " + detalles.get(i).getCod_ite() + ";")
                            + detalles.get(i).getDes_ite() + "',"
                            + detalles.get(i).getDet_can_sol() + ","
                            + detalles.get(i).getDet_can_ent() + ","
                            + detalles.get(i).getDet_can_pen() + ","
                            + detalles.get(i).getNon_sto() + ","
                            + detalles.get(i).getDet_sta() + ","
                            + detalles.get(i).getFec_cie()
                            + ",0)";
                    mCorrela = mCorrela + 1;
                }
                mValores = mValores.substring(1);
                mQuery = "insert into req_det(cod_mae,cod_det,cod_pai,cod_bod,cod_ubi,cod_ite,des_ite,"
                        + "det_can_sol,det_can_ent,det_can_pen,non_sto,det_sta,fec_cie,cos_uni) values " + mValores + ";";
                acc.dmlSQLvariable(mQuery);
                acc.Desconectar();
                nuevo();
                addMessage("Guardar Requisición", "Información almacenada con Éxito.", 1);
            }
        } catch (Exception e) {
            System.out.println("Error en guardar Nueva Requisición." + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validarg() {
        boolean valida = true;
        /*try {
         SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm");
         ft.format(fec_sol);
         } catch (Exception e) {
         valida = false;
         addMessage("Validar Datos", "Debe Proporcionar una Fecha de Solicitud en formato dd/mm/yyyy", 2);
         }*/
        if (detalles.isEmpty()) {
            valida = false;
            addMessage("Validar Datos", "Debe Ingresar por lo menos un Item al Detalle.", 2);
        }

        return valida;
    }

    public void eliminardetalle() {
        if ("".equals(det_cod_det)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover.", 2);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (det_cod_det.equals(detalles.get(i).getCod_det())) {
                    detalles.remove(i);
                }
            }
            det_des_ite = "";
            det_det_can_sol = "";
            det_det_can_ent = "0";
            det_det_can_pen = "0";
            det_non_sto = "0";
            det_det_sta = "0";
            det_fec_cie = "null";
        }
    }

    public void onRowSelect(SelectEvent event) {

        det_cod_det = ((CatSolicitudesDetalle) event.getObject()).getCod_det();
        det_cod_pai = ((CatSolicitudesDetalle) event.getObject()).getCod_pai();
        det_cod_bod = ((CatSolicitudesDetalle) event.getObject()).getCod_bod();
        det_cod_ubi = ((CatSolicitudesDetalle) event.getObject()).getCod_ubi();
        det_cod_ite = ((CatSolicitudesDetalle) event.getObject()).getCod_ite();
        det_des_ite = ((CatSolicitudesDetalle) event.getObject()).getDes_ite();
        det_det_can_sol = ((CatSolicitudesDetalle) event.getObject()).getDet_can_sol();
        det_det_can_ent = ((CatSolicitudesDetalle) event.getObject()).getDet_can_ent();
        det_det_can_pen = ((CatSolicitudesDetalle) event.getObject()).getDet_can_pen();
        det_non_sto = ((CatSolicitudesDetalle) event.getObject()).getNon_sto();
        det_det_sta = ((CatSolicitudesDetalle) event.getObject()).getDet_sta();

        if ("0".equals(det_non_sto)) {
            boolNS = "true";
        } else {
            boolNS = "false";
        }

        //System.out.println(tabindex);
    }

    public void onRowSelectsearch(SelectEvent event) {
        cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        cod_alt = ((CatSolicitudes) event.getObject()).getCod_alt();
        fec_sol = ((CatSolicitudes) event.getObject()).getFec_sol();
        cod_usu_sol = ((CatSolicitudes) event.getObject()).getCod_usu_sol();
        cod_usu_apr = ((CatSolicitudes) event.getObject()).getCod_usu_apr();
        cod_usu_rec = ((CatSolicitudes) event.getObject()).getCod_usu_rec();
        cod_dep = ((CatSolicitudes) event.getObject()).getCod_dep();
        det_uso = ((CatSolicitudes) event.getObject()).getDet_uso();
        cod_maq = ((CatSolicitudes) event.getObject()).getCod_maq();
        det_sta = ((CatSolicitudes) event.getObject()).getDet_sta();
        det_obs = ((CatSolicitudes) event.getObject()).getDet_obs();
        fec_cie = ((CatSolicitudes) event.getObject()).getFec_cie();
        cod_pai = ((CatSolicitudes) event.getObject()).getCod_pai();
        flg_loc = ((CatSolicitudes) event.getObject()).getFlg_loc();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            mfecha = format.parse(fec_sol);
        } catch (Exception ex) {

        }
        if ("00/00/0000".equals(fec_sol)) {
            fec_sol = "";
        }

        llenarDetalles();
        catmaestro = new CatSolicitudes();
        maestro = new ArrayList<>();
        RequestContext.getCurrentInstance().update("frmSearchReq");
        RequestContext.getCurrentInstance().update("frmReq");
        RequestContext.getCurrentInstance().execute("PF('wSearchReq').hide()");

    }

    public void dateSelected(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        fec_sol = format.format(date);
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabgenreq":
                tabindex = "0";
                break;
            case "tabdetreq":
                tabindex = "1";
                break;
        }
        //System.out.println(tabindex);
        //RequestContext.getCurrentInstance().update(":frmListaEquipos:tvLE");
    }

    public void onChangePais() {
        llenarDepartamentos();
        llenarEquipos();
        

    }


    public void onChangeNonStock() {
        if ("0".equals(det_non_sto)) {
            boolNS = "true";
            det_cod_bod = "0";
            det_cod_ubi = "0";
            det_cod_ite = "0";
            det_des_ite = "";
        } else {
            boolNS = "false";
            det_des_ite = "";
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

}
