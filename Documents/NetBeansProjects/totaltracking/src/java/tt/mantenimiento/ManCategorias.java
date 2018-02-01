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
public class ManCategorias implements Serializable {

    private static final long serialVersionUID = 8799543897676638L;
    @Inject
    Login cbean;
    private CatCategorias catcategorias;
    private List<CatCategorias> categorias;
    private String cod_cat, nom_cat;
    private Accesos macc;

    public ManCategorias() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_cat = "";
        nom_cat = "";

        catcategorias = new CatCategorias();
        categorias = new ArrayList<>();
        llenarCategorias();
    }

    public void nuevo() {
        cod_cat = "";
        nom_cat = "";
        catcategorias = null;
    }

    public void cerrarventana() {
        cod_cat = "";
        nom_cat = "";
        catcategorias = null;
        categorias = null;
        macc = null;
    }

    public void llenarCategorias() {
        String mQuery = "";
        try {
            catcategorias = null;
            categorias.clear();

            mQuery = "select cod_cat, nom_cat from cat_cat order by cod_cat;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                categorias.add(new CatCategorias(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Categorías. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_cat) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name.", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_cat) from cat_cat "
                + "where upper(nom_cat)='" + nom_cat.toUpperCase() + "';")) == false && "".equals(cod_cat)) {
            mValidar = false;
            addMessage("Save", "This Name Already Exists.", 2);
        }
        macc.Desconectar();
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {

                macc.Conectar();
                if ("".equals(cod_cat)) {
                    mQuery = "select ifnull(max(cod_cat),0)+1 as codigo from cat_cat;";
                    cod_cat = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_cat (cod_cat,nom_cat) "
                            + "values (" + cod_cat + ",'" + nom_cat + "');";
                } else {
                    mQuery = "update cat_cat SET "
                            + "nom_cat= '" + nom_cat + "' "
                            + "WHERE cod_cat= " + cod_cat + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while Saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Categorías. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarCategorias();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;
        String mQuery = "";

        if ("".equals(cod_cat) || "0".equals(cod_cat)) {
            addMessage("Delete", "You have to select a record.", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_cat) from cat_pie where cod_cat = " + cod_cat + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This category has associated parts", 2);
        }

        macc.Desconectar();
        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_cat where cod_cat=" + cod_cat + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Categorías. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarCategorias();
            nuevo();
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_cat = ((CatCategorias) event.getObject()).getCod_cat();
        nom_cat = ((CatCategorias) event.getObject()).getNom_cat();
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

    public CatCategorias getCatcategorias() {
        return catcategorias;
    }

    public void setCatcategorias(CatCategorias catcategorias) {
        this.catcategorias = catcategorias;
    }

    public List<CatCategorias> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CatCategorias> categorias) {
        this.categorias = categorias;
    }

    public String getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(String cod_cat) {
        this.cod_cat = cod_cat;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }
}
