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
public class ManGruposFallas implements Serializable {

    private static final long serialVersionUID = 8797678674716638L;
    @Inject
    Login cbean;
    private CatGrupoFallas catgrupofallas;
    private List<CatGrupoFallas> grupofallas;
    private String cod_gru_fal, nom_gru_fal;

    private Accesos macc;

    public ManGruposFallas() {
    }

    public void iniciarventana() {
        macc = new Accesos();

        cod_gru_fal = "";
        nom_gru_fal = "";

        catgrupofallas = new CatGrupoFallas();
        grupofallas = new ArrayList<>();

        llenarGrupoFallas();
    }

    public void nuevo() {
        cod_gru_fal = "";
        nom_gru_fal = "";
        catgrupofallas = null;
    }

    public void cerrarventana() {
        cod_gru_fal = "";
        nom_gru_fal = "";

        catgrupofallas = null;
        grupofallas = null;

        macc = null;
    }

    public void llenarGrupoFallas() {
        String mQuery = "";
        try {
            catgrupofallas = null;
            grupofallas.clear();

            mQuery = "select cod_gru_fal, nom_gru_fal from cat_gru_fal order by cod_gru_fal;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                grupofallas.add(new CatGrupoFallas(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Grupos de Falla. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_gru_fal) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_gru_fal) from cat_gru_fal "
                + "where upper(nom_gru_fal)='" + nom_gru_fal.toUpperCase() + "';")) == false && "".equals(cod_gru_fal)) {
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
                if ("".equals(cod_gru_fal)) {
                    mQuery = "select ifnull(max(cod_gru_fal),0)+1 as codigo from cat_gru_fal;";
                    cod_gru_fal = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_gru_fal (cod_gru_fal,nom_gru_fal) "
                            + "values (" + cod_gru_fal + ",'" + nom_gru_fal + "');";
                } else {
                    mQuery = "update cat_gru_fal SET "
                            + " nom_gru_fal = '" + nom_gru_fal + "' "
                            + "WHERE cod_gru_fal = " + cod_gru_fal + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Grupo de Falla. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarGrupoFallas();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_gru_fal) || "0".equals(cod_gru_fal)) {
            addMessage("Delete", "You have to select a record.", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_gru_fal) from tbl_det_man_fal where cod_gru_fal = " + cod_gru_fal + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Fail has associated maintenance", 2);
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_gru_fal where cod_gru_fal=" + cod_gru_fal + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Grupo de Fallas. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarGrupoFallas();
            nuevo();
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_gru_fal = ((CatGrupoFallas) event.getObject()).getCod_gru_fal();
        nom_gru_fal = ((CatGrupoFallas) event.getObject()).getNom_gru_fal();
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

    public CatGrupoFallas getCatgrupofallas() {
        return catgrupofallas;
    }

    public void setCatgrupofallas(CatGrupoFallas catgrupofallas) {
        this.catgrupofallas = catgrupofallas;
    }

    public List<CatGrupoFallas> getGrupofallas() {
        return grupofallas;
    }

    public void setGrupofallas(List<CatGrupoFallas> grupofallas) {
        this.grupofallas = grupofallas;
    }

    public String getCod_gru_fal() {
        return cod_gru_fal;
    }

    public void setCod_gru_fal(String cod_gru_fal) {
        this.cod_gru_fal = cod_gru_fal;
    }

    public String getNom_gru_fal() {
        return nom_gru_fal;
    }

    public void setNom_gru_fal(String nom_gru_fal) {
        this.nom_gru_fal = nom_gru_fal;
    }
}
