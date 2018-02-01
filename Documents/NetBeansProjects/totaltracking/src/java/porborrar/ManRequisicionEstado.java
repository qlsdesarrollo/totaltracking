
package paquetes;

import tt.general.Accesos;
import tt.mantenimiento.CatSolicitudesDetalle;
import tt.mantenimiento.CatSolicitudes;
import tt.general.Login;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@Named
@ConversationScoped
public class ManRequisicionEstado implements Serializable {
    private static final long serialVersionUID = 8791111756981668L;
    @Inject
    Login cbean;
    private CatSolicitudes catmaestro;
    private List<CatSolicitudes> maestro;
    private CatSolicitudesDetalle catdetalles;
    private List<CatSolicitudesDetalle> detalles;

    private String cod_mae, det_sta, cod_usu_sol;

    public ManRequisicionEstado() {
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

    public String getCod_usu_sol() {
        return cod_usu_sol;
    }

    public void setCod_usu_sol(String cod_usu_sol) {
        this.cod_usu_sol = cod_usu_sol;
    }

    public void iniciarventana() {
        cod_mae = "";
        det_sta = "";
        cod_usu_sol = cbean.getCod_usu();
        detalles = new ArrayList<>();
        llenarMaestro();

    }

    public void cerrarventana() {
        cod_mae = "";
        det_sta = "";
        cod_usu_sol = "";
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();
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
                    + "dep.nom_dep, concat(maq.nom_equ,'-',lis.num_ser) as nomequ,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM req_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.cod_usu_sol = " + cod_usu_sol + " "
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
            System.out.println("Error en el llenado Maestro en Estado Requsiciones. " + e.getMessage());
        }
    }

    public void llenarDetalles() {
        try {
            catdetalles = new CatSolicitudesDetalle();
            detalles = new ArrayList<>();

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
                    + "det.cos_uni,"
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
            System.out.println("Error en el llenado Detalles en Estado Requsiciones. " + e.getMessage());
        }
    }

    public void eliminar() {

        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();

        if ("".equals(cod_mae) == false) {
            if ("ESPERA APROBACIÓN".equals(det_sta)) {
                try {
                    String mQuery = "delete from req_det where cod_mae=" + cod_mae + ";";
                    mAccesos.dmlSQLvariable(mQuery);
                    mQuery = "delete from req_mae where cod_mae=" + cod_mae + " and det_sta=0;";
                    mAccesos.dmlSQLvariable(mQuery);
                    addMessage("Eliminar Requisición", "Información Eliminada con Éxito.", 1);
                } catch (Exception e) {
                    addMessage("Eliminar Requisición", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                    System.out.println("Error al Eliminar Requisición. " + e.getMessage());
                }
                iniciarventana();
            } else {
                addMessage("Eliminar Requisición", "Esta Requisición ya pasó a otra instancia y no puede eliminarse, consulte al Administrador del Sistema.", 2);
            }
        } else {
            addMessage("Eliminar Requisición", "Debe elegir un Registro.", 2);
        }

        mAccesos.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        cod_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
        det_sta = ((CatSolicitudes) event.getObject()).getDet_sta();
        llenarDetalles();
    }

    public void onRowUnselect(UnselectEvent event) {
        detalles = new ArrayList<>();
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
