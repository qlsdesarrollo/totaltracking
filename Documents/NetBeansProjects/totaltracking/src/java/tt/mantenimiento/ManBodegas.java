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
import tt.general.CatBodegas;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped

public class ManBodegas implements Serializable {

    private static final long serialVersionUID = 8799345674716638L;
    @Inject
    Login cbean;
    private CatPaises catpaises;
    private List<CatPaises> paises;
    private CatBodegas catbodegas;
    private List<CatBodegas> bodegas;
    private String id_bod, nom_bod, cod_pai;

    private Accesos macc;

    public ManBodegas() {
    }

    public void iniciarventana() {
        macc = new Accesos();

        id_bod = "";
        nom_bod = "";
        cod_pai = cbean.getCod_pai();

        catbodegas = new CatBodegas();
        bodegas = new ArrayList<>();
        paises = new ArrayList<>();

        llenarPaises();
        llenarBodegas();
    }

    public void nuevo() {
        id_bod = "";
        nom_bod = "";
        cod_pai = cbean.getCod_pai();
        catbodegas = null;
    }

    public void cerrarventana() {
        id_bod = "";
        nom_bod = "";
        cod_pai = "";

        catbodegas = null;
        bodegas = null;
        paises = null;

        macc = null;

    }

    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en Bodegas. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {
            catbodegas = null;
            bodegas.clear();
            if ("".equals(cod_pai) || "0".equals(cod_pai)) {
                mQuery = "select bod.id_bod, bod.nom_bod,bod.cod_pai,pai.nom_pai "
                        + "from cat_bodegas as bod "
                        + "inner join cat_pai as pai on bod.cod_pai = pai.cod_pai "
                        + "order by id_bod;";
            } else {
                mQuery = "select bod.id_bod, bod.nom_bod,bod.cod_pai,pai.nom_pai "
                        + "from cat_bodegas as bod "
                        + "inner join cat_pai as pai on bod.cod_pai = pai.cod_pai "
                        + "where bod.cod_pai=" + cod_pai + " "
                        + "order by id_bod;";
            }
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Bodegas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_bod) == true) {
            mValidar = false;
            addMessage("Save", "you have to enter a Warehouse Name.", 2);
        }
        if ("0".equals(cod_pai) == true) {
            mValidar = false;
            addMessage("Save", "You have to select a Country.", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(id_bod) from cat_bodegas "
                + "where upper(nom_bod)='" + nom_bod.toUpperCase() + "' and cod_pai ="
                + cbean.getCod_pai() + ";")) == false
                && "".equals(id_bod)) {
            mValidar = false;
            addMessage("Save", "Warehouse Name already exists.", 2);
        }
        macc.Desconectar();
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {

                macc.Conectar();
                if ("".equals(id_bod)) {
                    mQuery = "select ifnull(max(id_bod),0)+1 as codigo from cat_bodegas;";
                    id_bod = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_bodegas (id_bod,nom_bod,cod_pai) "
                            + "values (" + id_bod + ",'" + nom_bod + "'," + cod_pai + ");";
                } else {
                    mQuery = "update cat_bodegas SET "
                            + " nom_bod = '" + nom_bod + "', "
                            + " cod_pai = " + cod_pai + " "
                            + "WHERE id_bod = " + id_bod + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Bodegas. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarBodegas();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;
        String mQuery = "";

        if ("".equals(id_bod) || "0".equals(id_bod)) {
            addMessage("Delete", "You have to select a record.", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_bod) from cat_pie_reo where cod_pai = " + cod_pai + " and cod_bod =" + id_bod + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Warehouse has associated reorder points", 2);
        }
        
        if (!macc.strQuerySQLvariable("select count(cod_bod) from req_det where cod_pai = " + cod_pai + " and cod_bod =" + id_bod + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Warehouse has associated inventory request", 2);
        }
        
        if (!macc.strQuerySQLvariable("select count(cod_bod) from sol_det where cod_pai = " + cod_pai + " and cod_bod =" + id_bod + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Warehouse has associated purchase order", 2);
        }
        
        if (!macc.strQuerySQLvariable("select count(cod_bod) from tbl_det_man_pie where cod_pai = " + cod_pai + " and cod_bod =" + id_bod + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Warehouse has associated maintenance parts", 2);
        }
        
        if (!macc.strQuerySQLvariable("select count(cod_bod) from tbl_pie_det where cod_bod =" + id_bod + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Warehouse has associated inventory parts", 2);
        }
        
        if (!macc.strQuerySQLvariable("select count(cod_bod) from tbl_pie_his where cod_pai = " + cod_pai + " and cod_bod =" + id_bod + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Warehouse has associated historical movements", 2);
        }
        
        macc.Desconectar();
        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_bodegas where id_bod=" + id_bod + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Bodegas. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarBodegas();
            nuevo();
        }

    }

    public void onRowSelect(SelectEvent event) {
        id_bod = ((CatBodegas) event.getObject()).getId_bod();
        nom_bod = ((CatBodegas) event.getObject()).getNom_bod();
        cod_pai = ((CatBodegas) event.getObject()).getCod_pai();
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

    public CatPaises getCatpaises() {
        return catpaises;
    }

    public void setCatpaises(CatPaises catpaises) {
        this.catpaises = catpaises;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public CatBodegas getCatbodegas() {
        return catbodegas;
    }

    public void setCatbodegas(CatBodegas catbodegas) {
        this.catbodegas = catbodegas;
    }

    public List<CatBodegas> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<CatBodegas> bodegas) {
        this.bodegas = bodegas;
    }

    public String getId_bod() {
        return id_bod;
    }

    public void setId_bod(String id_bod) {
        this.id_bod = id_bod;
    }

    public String getNom_bod() {
        return nom_bod;
    }

    public void setNom_bod(String nom_bod) {
        this.nom_bod = nom_bod;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

}
