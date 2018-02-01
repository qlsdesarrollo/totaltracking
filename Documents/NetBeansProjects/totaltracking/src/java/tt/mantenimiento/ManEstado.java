package tt.mantenimiento;

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
import tt.general.Accesos;
import tt.general.Login;

@Named
@ConversationScoped

public class ManEstado implements Serializable {

    private static final long serialVersionUID = 8791111234571668L;
    @Inject
    Login cbean;
    private CatSolicitudes catmaestro;
    private List<CatSolicitudes> maestro;
    private List<CatSolicitudesDetalle> detalles;

    private String cod_mae, det_sta, cod_usu_sol;

    private Accesos macc;

    public ManEstado() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        
        cod_mae = "";
        det_sta = "";
        cod_usu_sol = cbean.getCod_usu();

        catmaestro = new CatSolicitudes();
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();

        llenarMaestro();

    }

    public void cerrarventana() {
        cod_mae = "";
        det_sta = "";
        cod_usu_sol = "";

        catmaestro = null;
        maestro = null;
        detalles = null;
        macc =  null;
    }

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
                    + "date_format(mae.fec_cie,'%d/%m/%Y'), mae.flg_loc,mae.cod_pai, "
                    + "dep.nom_dep, concat(maq.nom_equ,'-',lis.num_ser) as nomequ, "
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM sol_mae as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join lis_equ as lis on mae.cod_maq = lis.cod_lis_equ "
                    + "left join cat_equ as maq on lis.cod_equ = maq.cod_equ "
                    + "left join cat_pai as pai on mae.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on mae.cod_usu_sol = usu.cod_usu "
                    + "where "
                    + "mae.cod_usu_sol = " + cod_usu_sol + " "
                    + "order by mae.fec_sol desc, cod_mae desc;";
            ResultSet resVariable;

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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Estado Solicitud. " + e.getMessage());
        }
    }

    public void llenarDetalles() {
        try {
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
                    + "det.cos_uni,"
                    + "pai.nom_pai, '', '' "
                    + "from sol_det as det "
                    + "left join cat_pai as pai on det.cod_pai = pai.cod_pai "
                    + "where det.cod_mae = " + cod_mae + " order by det.cod_det asc;";
            ResultSet resVariable;

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
            System.out.println("Error en el llenado Detalles en Estado Solicitud. " + e.getMessage());
        }
    }

    public void eliminar() {

        macc.Conectar();

        if ("".equals(cod_mae) == false) {
            if ("AWAITING APPROVAL".equals(det_sta)) {
                try {
                    String mQuery = "delete from sol_det where cod_mae=" + cod_mae + ";";
                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from sol_mae where cod_mae=" + cod_mae + " and det_sta=0;";
                    macc.dmlSQLvariable(mQuery);
                    addMessage("Delete", "Deleted successful.", 1);
                } catch (Exception e) {
                    addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                    System.out.println("Error al Eliminar Solicitud. " + e.getMessage());
                }
                iniciarventana();
            } else {
                addMessage("Delete", "This Purchase order has been approved and can not be deleted.", 2);
            }
        } else {
            addMessage("Delete", "You have to select a Purchase Order.", 2);
        }

        macc.Desconectar();

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

}
