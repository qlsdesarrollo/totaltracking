package porborrar;

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
import org.primefaces.event.UnselectEvent;
import tt.general.Accesos;
import tt.mantenimiento.CatSolicitudes;
import tt.mantenimiento.CatSolicitudesDetalle;
import tt.general.Login;

@Named
@ConversationScoped

public class ManAprobacion implements Serializable {

    private static final long serialVersionUID = 8791111234571668L;
    @Inject
    Login cbean;
    private CatSolicitudes catmaestro;
    private List<CatSolicitudes> maestro;
    private CatSolicitudesDetalle catdetalles;
    private List<CatSolicitudesDetalle> detalles;

    private String id_mae, det_sta;

    public ManAprobacion() {
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

    public String getId_mae() {
        return id_mae;
    }

    public void setId_mae(String id_mae) {
        this.id_mae = id_mae;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public void iniciarventana() {

        id_mae = "";
        det_sta = "";
        detalles = new ArrayList<>();
        llenarMaestro();
        if (Integer.valueOf(cbean.getPerfil()) < 5) {
            addMessage("Acceso Denegado", "Este Usuario no posee permisos de Aprobación", 2);
            RequestContext.getCurrentInstance().update("frmAprobado");
            RequestContext.getCurrentInstance().execute("PF('wAprobado').hide()");
        }

    }

    public void cerrarventana() {
        id_mae = "";
        det_sta = "";
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();
    }

    public void llenarMaestro() {
        try {
            catmaestro = new CatSolicitudes();
            maestro = new ArrayList<>();

            String mQuery = "select mae.id_mae, date_format(mae.fec_sol,'%d/%m/%Y') as fec_sol, usu.det_nom, "
                    + "mae.cod_dep, mae.det_uso, mae.cod_maq, mae.nom_apr, mae.det_sta,"
                    + "dep.nom_dep,maq.nom_equ,mae.det_obs "
                    + "FROM mae_sol as mae "
                    + "left join cat_dep as dep on mae.cod_dep = dep.cod_dep "
                    + "left join cat_equ as maq on mae.cod_maq = maq.cod_equ "
                    + "left join cat_usu as usu on mae.nom_usu = usu.cod_usu "
                    + "where "
                    + "mae.det_sta = 'ESPERA APROBACIÓN' "
                    + "order by mae.fec_sol asc;";
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
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(11),
                        resVariable.getString(11)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Maestro en Estado Solicitud. " + e.getMessage());
        }
    }

    public void llenarDetalles() {
        try {
            catdetalles = new CatSolicitudesDetalle();
            detalles = new ArrayList<>();

            String mQuery = "select id_mae, id_det, cod_ite,des_ite, det_can, det_sta FROM det_sol "
                    + "where id_mae = " + id_mae + " order by id_det asc;";
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
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalles en Aprobación. " + e.getMessage());
        }
    }

    public void aprobar() {

        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();

        if ("".equals(id_mae) == false) {

            try {
                String mQuery = "update mae_sol set det_sta='APROBADA', nom_apr=" + cbean.getCod_usu()
                        + " where id_mae=" + id_mae + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Aprobar Solicitud", "La Solicitud ha sido Aprobada.", 1);
            } catch (Exception e) {
                addMessage("Aprobar Solicitud", "Error al Aprobar Solicitud. " + e.getMessage(), 2);
                System.out.println("Error al Aprobar Solicitud. " + e.getMessage());
            }
            iniciarventana();

        } else {
            addMessage("Aprobar Solicitud", "Debe elegir un Registro.", 2);
        }

        mAccesos.Desconectar();

    }

    public void rechazar() {

        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();

        if ("".equals(id_mae) == false) {

            try {
                String mQuery = "update mae_sol set det_sta='NO APROBADA' where id_mae=" + id_mae + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Cancelar Solicitud", "La Solicitud ha sido denegada.", 1);
            } catch (Exception e) {
                addMessage("Cancelar Solicitud", "Error al Denegar Solicitud. " + e.getMessage(), 2);
                System.out.println("Error al Cancelar Solicitud. " + e.getMessage());
            }
            iniciarventana();

        } else {
            addMessage("Cancelar Solicitud", "Debe elegir un Registro.", 2);
        }

        mAccesos.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        id_mae = ((CatSolicitudes) event.getObject()).getCod_mae();
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
