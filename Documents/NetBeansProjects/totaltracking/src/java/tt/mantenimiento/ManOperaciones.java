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
import tt.general.Accesos;
import tt.general.Login;

@Named
@ConversationScoped
public class ManOperaciones implements Serializable {

    private static final long serialVersionUID = 8799330296716638L;
    @Inject
    Login cbean;
    private CatOperaciones catoperaciones;
    private List<CatOperaciones> operaciones;
    private String cod_ope, nom_ope;

    private Accesos macc;

    public ManOperaciones() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_ope = "";
        nom_ope = "";
        catoperaciones = new CatOperaciones();
        operaciones = new ArrayList<>();

        llenarOperaciones();
    }

    public void nuevo() {
        cod_ope = "";
        nom_ope = "";
        catoperaciones = null;
    }

    public void cerrarventana() {
        cod_ope = "";
        nom_ope = "";
        catoperaciones = null;
        operaciones = null;

        macc = null;
    }

    public void llenarOperaciones() {
        String mQuery = "";
        try {
            catoperaciones = null;
            operaciones.clear();

            mQuery = "select cod_ope, nom_ope "
                    + "from cat_ope "
                    + "order by cod_ope;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                operaciones.add(new CatOperaciones(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Operaciones. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_ope) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_ope) from cat_ope "
                + "where upper(nom_ope)='" + nom_ope.toUpperCase() + "';")) == false
                && "".equals(cod_ope)) {
            mValidar = false;
            addMessage("Save", "This Name already exists", 2);
        }
        macc.Desconectar();
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {

                macc.Conectar();
                if ("".equals(cod_ope)) {
                    mQuery = "select ifnull(max(cod_ope),0)+1 as codigo from cat_ope;";
                    cod_ope = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_ope (cod_ope,nom_ope) "
                            + "values (" + cod_ope + ",'" + nom_ope + "');";
                } else {
                    mQuery = "update cat_ope SET "
                            + " nom_ope = '" + nom_ope + "' "
                            + "WHERE cod_ope = " + cod_ope + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Operación. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarOperaciones();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_ope) || "0".equals(cod_ope)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_ope) from tbl_det_man_gen where cod_ope = " + cod_ope + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Action has associated maintenace", 2);
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if ("".equals(cod_ope) == false) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_ope where cod_ope=" + cod_ope + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Operación. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarOperaciones();
            nuevo();
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_ope = ((CatOperaciones) event.getObject()).getCod_ope();
        nom_ope = ((CatOperaciones) event.getObject()).getNom_ope();
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

    public CatOperaciones getCatoperaciones() {
        return catoperaciones;
    }

    public void setCatoperaciones(CatOperaciones catoperaciones) {
        this.catoperaciones = catoperaciones;
    }

    public List<CatOperaciones> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<CatOperaciones> operaciones) {
        this.operaciones = operaciones;
    }

    public String getCod_ope() {
        return cod_ope;
    }

    public void setCod_ope(String cod_ope) {
        this.cod_ope = cod_ope;
    }

    public String getNom_ope() {
        return nom_ope;
    }

    public void setNom_ope(String nom_ope) {
        this.nom_ope = nom_ope;
    }

}
