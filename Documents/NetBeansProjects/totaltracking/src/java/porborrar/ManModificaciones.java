package porborrar;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import tt.general.Accesos;
import tt.mantenimiento.CatSolicitudes;
import tt.mantenimiento.CatSolicitudesDetalle;
import tt.seguridad.CatUsuarios;
import tt.general.Conexion;
import tt.general.Login;

@Named
@ConversationScoped

public class ManModificaciones extends Conexion implements Serializable {

    private static final long serialVersionUID = 8791111543571668L;
    @Inject
    Login cbean;
    private CatSolicitudes catmaestro;
    private List<CatSolicitudes> maestro;
    private CatSolicitudesDetalle catdetalles;
    private List<CatSolicitudesDetalle> detalles;
    private CatUsuarios catusuarios;
    private List<CatUsuarios> usuarios;

    private String id_mae, det_sta, cod_apr;

    private String nombrereporte, nombreexportar;
    private Map<String, Object> parametros;

    public ManModificaciones() {
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

    public CatUsuarios getCatusuarios() {
        return catusuarios;
    }

    public void setCatusuarios(CatUsuarios catusuarios) {
        this.catusuarios = catusuarios;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
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

    public String getCod_apr() {
        return cod_apr;
    }

    public void setCod_apr(String cod_apr) {
        this.cod_apr = cod_apr;
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

        id_mae = "";
        det_sta = "";
        cod_apr = "0";
        detalles = new ArrayList<>();
        llenarAprobadores();
        llenarMaestro();
        if (Integer.valueOf(cbean.getPerfil()) < 5) {
            addMessage("Acceso Denegado", "Este Usuario no posee permisos para hacer Modificaciones", 2);
            RequestContext.getCurrentInstance().update("frmModificaciones");
            RequestContext.getCurrentInstance().execute("PF('wModificaciones').hide()");
        }

    }

    public void cerrarventana() {
        id_mae = "";
        det_sta = "";
        cod_apr = "";
        maestro = new ArrayList<>();
        detalles = new ArrayList<>();
    }

    public void llenarAprobadores() {
        try {
            catusuarios = new CatUsuarios();
            usuarios = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai "
                    + "where usu.tip_usu=5 order by cod_usu;";
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
            System.out.println("Error en el llenado de Aprobadores en Modificaciones. " + e.getMessage());
        }
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
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
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
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
                        resVariable.getString(6),
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
            if ("0".equals(cod_apr) == false) {
                try {
                    String mQuery = "update mae_sol set det_sta='APROBADA', nom_apr=" + cod_apr
                            + " where id_mae=" + id_mae + ";";
                    mAccesos.dmlSQLvariable(mQuery);
                    addMessage("Aprobar Solicitud", "La Solicitud ha sido Aprobada.", 1);
                } catch (Exception e) {
                    addMessage("Aprobar Solicitud", "Error al Aprobar Solicitud. " + e.getMessage(), 2);
                    System.out.println("Error al Aprobar Solicitud. " + e.getMessage());
                }
                iniciarventana();
            } else {
                addMessage("Aprobar Solicitud", "Debe elegir un Encargado de Aprobación.", 2);
            }
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

    /**
     * ******************* Reporte ***************************************
     */
    public void ejecutarreporte() {
        try {
            if (!"".equals(id_mae) && !"0".equals(id_mae)) {
                paramRepVarios();
                verPDF();
            } else {
                addMessage("Imprimir Solicitud", "Debe elegir un Registro.", 2);
            }

        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte manModificaciones" + e.getMessage());
        }
    }

    public void paramRepVarios() {
        parametros = new HashMap<>();
        parametros.put("solicitud", id_mae);
        nombrereporte = "/reportes/solicitud.jasper";
        nombreexportar = "Solicitud_" + id_mae;

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
            System.out.println("Error en verPDF en VisReportes." + e.getMessage());
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
