package paquetes;

import tt.general.Accesos;
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

@Named
@ConversationScoped
public class ManEvaluaciones implements Serializable {

    private static final long serialVersionUID = 8797678674716638L;
    @Inject
    Login cbean;
    private CatEvaluaciones catevaluaciones;
    private List<CatEvaluaciones> evaluaciones;
    private String id_eva, nom_eva;

    public ManEvaluaciones() {
    }

    public CatEvaluaciones getCatevaluaciones() {
        return catevaluaciones;
    }

    public void setCatevaluaciones(CatEvaluaciones catevaluaciones) {
        this.catevaluaciones = catevaluaciones;
    }

    public List<CatEvaluaciones> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<CatEvaluaciones> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public String getId_eva() {
        return id_eva;
    }

    public void setId_eva(String id_eva) {
        this.id_eva = id_eva;
    }

    public String getNom_eva() {
        return nom_eva;
    }

    public void setNom_eva(String nom_eva) {
        this.nom_eva = nom_eva;
    }

    public void iniciarventana() {
        id_eva = "";
        nom_eva = "";
        llenarEvaluaciones();
    }

    public void cerrarventana() {
        id_eva = "";
        nom_eva = "";
        evaluaciones = new ArrayList<>();
    }

    public void llenarEvaluaciones() {
        String mQuery = "";
        try {
            catevaluaciones = new CatEvaluaciones();
            evaluaciones = new ArrayList<>();

            mQuery = "select id_eva, nom_eva from cat_eva order by id_eva;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                evaluaciones.add(new CatEvaluaciones(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Evaluaciones. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_eva = "";
        nom_eva = "";
        catevaluaciones = new CatEvaluaciones();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_eva)) {
                    mQuery = "select ifnull(max(id_eva),0)+1 as codigo from cat_eva;";
                    id_eva = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_eva (id_eva, nom_eva) "
                            + "values (" + id_eva + ",'" + nom_eva + "');";
                } else {
                    mQuery = "update cat_eva SET "
                            + " nom_eva = '" + nom_eva + "' "
                            + "WHERE id_eva = " + id_eva + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Evaluacion", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Evaluacion", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Evaluacion. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEvaluaciones();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_eva) == false) {
            try {
                mQuery = "delete from cat_eva where id_eva=" + id_eva + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Evaluacion", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Evaluacion", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Evaluacion. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEvaluaciones();
            nuevo();
        } else {
            addMessage("Eliminar Evaluaciones", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_eva) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para la Evaluacion.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_eva) from cat_eva "
                + "where upper(nom_eva)='" + nom_eva.toUpperCase() + "';")) == false && "".equals(id_eva)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre de la Evaluacion ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_eva = ((CatEvaluaciones) event.getObject()).getId_eva();
        nom_eva = ((CatEvaluaciones) event.getObject()).getNom_eva();
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
