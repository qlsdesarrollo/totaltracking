package paquetes;

import tt.seguridad.CatUsuarios;
import tt.general.Accesos;
import tt.mantenimiento.CatEquipos;
import tt.mantenimiento.CatOperaciones;
import tt.mantenimiento.CatTipos;
import tt.general.Login;
import tt.mantenimiento.CatPiezas;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ConversationScoped

public class ManPlantillas implements Serializable {

    private static final long serialVersionUID = 761578659667418L;
    @Inject
    Login mbMain;
    private CatPlantillas catplantillas;
    private List<CatPlantillas> plantillas;
    private CatPlantillasDetalle catplantilladetallesgen;
    private List<CatPlantillasDetalle> detallesgen;
    private CatPlantillasDetalle catplantilladetallespie;
    private List<CatPlantillasDetalle> detallespie;
    private CatPlantillasDetalle catplantilladetallesacc;
    private List<CatPlantillasDetalle> detallesacc;

    private List<CatEquipos> equipos;
    private List<CatTipos> tipos;
    private List<CatUsuarios> usuarios;
    private List<CatOperaciones> operaciones;
    private List<CatPiezas> piezas;

    private String cod_pla, cod_equ, tip_man, det_hor, usu_sug;
    private String flg_tip_det, det_pla, cod_ope, det_obs, det_can, cod_pie, des_ite;

    private String tabindex;

    public ManPlantillas() {
    }

    public CatPlantillas getCatplantillas() {
        return catplantillas;
    }

    public void setCatplantillas(CatPlantillas catplantillas) {
        this.catplantillas = catplantillas;
    }

    public List<CatPlantillas> getPlantillas() {
        return plantillas;
    }

    public void setPlantillas(List<CatPlantillas> plantillas) {
        this.plantillas = plantillas;
    }

    public CatPlantillasDetalle getCatplantilladetallesgen() {
        return catplantilladetallesgen;
    }

    public void setCatplantilladetallesgen(CatPlantillasDetalle catplantilladetallesgen) {
        this.catplantilladetallesgen = catplantilladetallesgen;
    }

    public List<CatPlantillasDetalle> getDetallesgen() {
        return detallesgen;
    }

    public void setDetallesgen(List<CatPlantillasDetalle> detallesgen) {
        this.detallesgen = detallesgen;
    }

    public CatPlantillasDetalle getCatplantilladetallespie() {
        return catplantilladetallespie;
    }

    public void setCatplantilladetallespie(CatPlantillasDetalle catplantilladetallespie) {
        this.catplantilladetallespie = catplantilladetallespie;
    }

    public List<CatPlantillasDetalle> getDetallespie() {
        return detallespie;
    }

    public void setDetallespie(List<CatPlantillasDetalle> detallespie) {
        this.detallespie = detallespie;
    }

    public CatPlantillasDetalle getCatplantilladetallesacc() {
        return catplantilladetallesacc;
    }

    public void setCatplantilladetallesacc(CatPlantillasDetalle catplantilladetallesacc) {
        this.catplantilladetallesacc = catplantilladetallesacc;
    }

    public List<CatPlantillasDetalle> getDetallesacc() {
        return detallesacc;
    }

    public void setDetallesacc(List<CatPlantillasDetalle> detallesacc) {
        this.detallesacc = detallesacc;
    }

    public List<CatEquipos> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatEquipos> equipos) {
        this.equipos = equipos;
    }

    public List<CatTipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<CatTipos> tipos) {
        this.tipos = tipos;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public List<CatOperaciones> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<CatOperaciones> operaciones) {
        this.operaciones = operaciones;
    }

    public List<CatPiezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatPiezas> piezas) {
        this.piezas = piezas;
    }

    public String getCod_pla() {
        return cod_pla;
    }

    public void setCod_pla(String cod_pla) {
        this.cod_pla = cod_pla;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getTip_man() {
        return tip_man;
    }

    public void setTip_man(String tip_man) {
        this.tip_man = tip_man;
    }

    public String getDet_hor() {
        return det_hor;
    }

    public void setDet_hor(String det_hor) {
        this.det_hor = det_hor;
    }

    public String getUsu_sug() {
        return usu_sug;
    }

    public void setUsu_sug(String usu_sug) {
        this.usu_sug = usu_sug;
    }

    public String getFlg_tip_det() {
        return flg_tip_det;
    }

    public void setFlg_tip_det(String flg_tip_det) {
        this.flg_tip_det = flg_tip_det;
    }

    public String getDet_pla() {
        return det_pla;
    }

    public void setDet_pla(String det_pla) {
        this.det_pla = det_pla;
    }

    public String getCod_ope() {
        return cod_ope;
    }

    public void setCod_ope(String cod_ope) {
        this.cod_ope = cod_ope;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getDes_ite() {
        return des_ite;
    }

    public void setDes_ite(String des_ite) {
        this.des_ite = des_ite;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public void iniciarventana() {
        cod_pla = "";
        cod_equ = "0";
        tip_man = "0";
        det_hor = "";
        usu_sug = "0";
        flg_tip_det = "";
        det_pla = "";
        cod_ope = "0";
        det_obs = "";
        det_can = "";
        cod_pie = "0";
        des_ite = "";
        tabindex = "0";
    }

    public void cerrarventana() {
        cod_pla = "";
        cod_equ = "0";
        tip_man = "0";
        det_hor = "";
        usu_sug = "0";
        flg_tip_det = "";
        det_pla = "";
        cod_ope = "0";
        det_obs = "";
        det_can = "";
        cod_pie = "0";
        des_ite = "";
        tabindex = "0";
        plantillas = new ArrayList<>();
        detallesgen = new ArrayList<>();
        detallespie = new ArrayList<>();
        detallesacc = new ArrayList<>();
        equipos = new ArrayList<>();
        tipos = new ArrayList<>();
        usuarios = new ArrayList<>();
        operaciones = new ArrayList<>();
        piezas = new ArrayList<>();
        llenarEquipos();
        llenarTipos();
        llenarUsuarios();
        llenarOperaciones();
        llenarPiezas();
    }

    public void nuevo() {
        cod_pla = "";
        cod_equ = "0";
        tip_man = "0";
        det_hor = "";
        usu_sug = "0";
        flg_tip_det = "";
        det_pla = "";
        cod_ope = "0";
        det_obs = "";
        det_can = "";
        cod_pie = "0";
        des_ite = "";

    }

    public void llenarEquipos() {
        String mQuery = "";
        try {

            equipos = new ArrayList<>();

            mQuery = "select equ.cod_equ, equ.cod_mar,equ.cod_ref, "
                    + "equ.nom_equ, equ.des_equ,mar.nom_mar,equ.det_ima "
                    + "from cat_equ as equ "
                    + "left join cat_mar as mar on mar.id_mar = equ.cod_mar "
                    + "order by equ.cod_equ;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatEquipos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Equipos en ManPlantillas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarTipos() {
        String mQuery = "";
        try {

            tipos = new ArrayList<>();

            mQuery = "select cod_tip, nom_tip,"
                    + "case flg_urg "
                    + "when 0 then 'false' "
                    + "when 1 then 'true' end as urg from cat_tip order by cod_tip;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tipos.add(new CatTipos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Tipos en ManPlantillas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUsuarios() {
        try {

            usuarios = new ArrayList<>();

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
            System.out.println("Error en el llenado de Usuarios en ManPlantillas. " + e.getMessage());
        }
    }

    public void llenarOperaciones() {
        String mQuery = "";
        try {
            operaciones = new ArrayList<>();

            mQuery = "select cod_ope, nom_ope "
                    + "from cat_ope "
                    + "order by cod_ope;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                operaciones.add(new CatOperaciones(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Operaciones en ManPlantillas. " + e.getMessage() + " Query: " + mQuery);
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
            System.out.println("Error en el llenado de Piezas en ManPlantillas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPlantillas() {
        String mQuery = "";
        try {
            catplantillas = new CatPlantillas();
            plantillas = new ArrayList<>();

            mQuery = "select  mae.cod_pla, mae.cod_equ, mae.tip_man, mae.det_hor, "
                    + "mae.usu_sug, equ.nom_equ, tip.nom_tip, usu.det_nom "
                    + "FROM tbl_pla_man as mae "
                    + "left join cat_equ as equ on mae.cod_equ = equ.cod_eq n "
                    + "left join cat_tip as tip on mae.tip_man = tip.cod_tip "
                    + "left join cat_usu as usu on mae.usu_sug = usu.cod_usu "
                    + "order by mae.cod_equ, mae.tip_man,mae.cod_pla;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                plantillas.add(new CatPlantillas(
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Plantillas en ManPlantillas. " + e.getMessage() + " Query: " + mQuery);
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
