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
public class ManFactores implements Serializable {

    private static final long serialVersionUID = 8797674674716638L;
    @Inject
    Login cbean;
    private CatFactores catfactores;
    private List<CatFactores> factores;
    private String id_fac, nom_fac;

    public ManFactores() {
    }

    public CatFactores getCatfactores() {
        return catfactores;
    }

    public void setCatfactores(CatFactores catfactores) {
        this.catfactores = catfactores;
    }

    public List<CatFactores> getFactores() {
        return factores;
    }

    public void setFactores(List<CatFactores> factores) {
        this.factores = factores;
    }

    public String getId_fac() {
        return id_fac;
    }

    public void setId_fac(String id_fac) {
        this.id_fac = id_fac;
    }

    public String getNom_fac() {
        return nom_fac;
    }

    public void setNom_fac(String nom_fac) {
        this.nom_fac = nom_fac;
    }

    

    public void iniciarventana() {
        id_fac = "";
        nom_fac = "";
        llenarFactores();
    }

    public void cerrarventana() {
        id_fac = "";
        nom_fac = "";
        factores = new ArrayList<>();
    }

    public void llenarFactores() {
        String mQuery = "";
        try {
            catfactores = new CatFactores();
            factores = new ArrayList<>();

            mQuery = "select id_fac, nom_fac from cat_fac order by id_fac;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                factores.add(new CatFactores(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Factor de Evaluacion. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_fac = "";
        nom_fac = "";
        catfactores = new CatFactores();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_fac)) {
                    mQuery = "select ifnull(max(id_fac),0)+1 as codigo from cat_fac;";
                    id_fac = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_fac (id_fac,nom_fac) "
                            + "values (" + id_fac + ",'" + nom_fac + "');";
                } else {
                    mQuery = "update cat_fac SET "
                            + " nom_fac = '" + nom_fac + "' "
                            + "WHERE id_fac = " + id_fac + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Factor de Evaluacion", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Factor de Evaluacion", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Factor de Evaluacion. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarFactores();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_fac) == false) {
            try {
                mQuery = "delete from cat_fac where id_fac=" + id_fac + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Factor de Evaluacion", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Factor de Evaluacion", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Factor de Evaluacion. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarFactores();
            nuevo();
        } else {
            addMessage("Eliminar Factor de Evaluacion", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_fac) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para la Factor de Evaluacion.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_fac) from cat_fac "
                + "where upper(nom_fac)='" + nom_fac.toUpperCase() + "';")) == false && "".equals(id_fac)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre de la Factor de Evaluacion ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_fac = ((CatFactores) event.getObject()).getId_fac();
        nom_fac = ((CatFactores) event.getObject()).getNom_fac();
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
