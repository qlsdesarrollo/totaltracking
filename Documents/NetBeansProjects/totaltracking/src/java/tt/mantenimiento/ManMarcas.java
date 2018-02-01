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
import tt.general.CatMarcas;
import tt.general.Login;

@Named
@ConversationScoped
public class ManMarcas implements Serializable {

    private static final long serialVersionUID = 8797678674716638L;
    @Inject
    Login cbean;
    private CatMarcas catmarcas;
    private List<CatMarcas> marcas;
    private String id_mar, nom_mar;

    private Accesos macc;

    public ManMarcas() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        id_mar = "";
        nom_mar = "";

        catmarcas = new CatMarcas();
        marcas = new ArrayList<>();

        llenarMarcas();
    }

    public void nuevo() {
        id_mar = "";
        nom_mar = "";
        catmarcas = null;
    }

    public void cerrarventana() {
        id_mar = "";
        nom_mar = "";
        catmarcas = null;
        marcas = null;
        macc = null;
    }

    public void llenarMarcas() {
        String mQuery = "";
        try {
            catmarcas = null;
            marcas.clear();

            mQuery = "select id_mar, nom_mar from cat_mar order by id_mar;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                marcas.add(new CatMarcas(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Marcas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_mar) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name.", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(id_mar) from cat_mar "
                + "where upper(nom_mar)='" + nom_mar.toUpperCase() + "';")) == false && "".equals(id_mar)) {
            mValidar = false;
            addMessage("Save", "This Name already exists.", 2);
        }
        macc.Desconectar();
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {

                macc.Conectar();
                if ("".equals(id_mar)) {
                    mQuery = "select ifnull(max(id_mar),0)+1 as codigo from cat_mar;";
                    id_mar = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_mar (id_mar,nom_mar) "
                            + "values (" + id_mar + ",'" + nom_mar + "');";
                } else {
                    mQuery = "update cat_mar SET "
                            + " nom_mar = '" + nom_mar + "' "
                            + "WHERE id_mar = " + id_mar + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Marca. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMarcas();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(id_mar) || "0".equals(id_mar)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_mar) from cat_equ where cod_mar = " + id_mar + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Brand has associated equipments", 2);
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_mar where id_mar=" + id_mar + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Marca. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMarcas();
            nuevo();
        }
    }

    public void onRowSelect(SelectEvent event) {
        id_mar = ((CatMarcas) event.getObject()).getId_mar();
        nom_mar = ((CatMarcas) event.getObject()).getNom_mar();
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

    public CatMarcas getCatmarcas() {
        return catmarcas;
    }

    public void setCatmarcas(CatMarcas catmarcas) {
        this.catmarcas = catmarcas;
    }

    public List<CatMarcas> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<CatMarcas> marcas) {
        this.marcas = marcas;
    }

    public String getId_mar() {
        return id_mar;
    }

    public void setId_mar(String id_mar) {
        this.id_mar = id_mar;
    }

    public String getNom_mar() {
        return nom_mar;
    }

    public void setNom_mar(String nom_mar) {
        this.nom_mar = nom_mar;
    }
}
