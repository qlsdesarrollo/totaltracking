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
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped

public class ManPaises implements Serializable {

    private static final long serialVersionUID = 74696476741638L;
    @Inject
    Login mbMain;
    private CatPaises catpaises;
    private List<CatPaises> paises;

    private String cod_pai, nom_pai;

    private Accesos macc;

    public ManPaises() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_pai = "";
        nom_pai = "";

        catpaises = new CatPaises();
        paises = new ArrayList<>();

        llenarPaises();
    }

    public void nuevo() {
        cod_pai = "";
        nom_pai = "";

        catpaises = null;
    }

    public void cerrarventana() {
        cod_pai = "";
        nom_pai = "";

        catpaises = null;
        paises = null;

        macc = null;
    }

    public void llenarPaises() {
        try {
            catpaises = null;
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
            System.out.println("Error en el llenado del Catálogo de Paises. " + e.getMessage());
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_pai)) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name", 2);
        }

        return mValidar;
    }

    public void guardar() {
        if (validardatos()) {
            try {

                macc.Conectar();
                String mQuery;
                if ("".equals(cod_pai)) {
                    mQuery = "select ifnull(max(cod_pai),0)+1 as codigo from cat_pai;";
                    cod_pai = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_pai (cod_pai, nom_pai) "
                            + "values (" + cod_pai + ",'" + nom_pai + "');";
                } else {
                    mQuery = "update cat_pai SET "
                            + " nom_pai = '" + nom_pai + "' "
                            + "WHERE cod_pai = " + cod_pai + ";";
                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar País. " + e.getMessage());
            }
            llenarPaises();
            nuevo();
        }

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_bodegas where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated warehouses", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_cli where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated clients", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_dep where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Departments", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_pie_reo where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Reorder Levels", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_pro where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Manufacturers", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_sup where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Suppliers", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from cat_usu where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Users", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from lis_equ where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Equipments", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from lis_equ_rem where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated remove history", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from req_mae where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Warehouse requisitions", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from req_det where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Warehouse requisitions", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from sol_mae where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Purchase orders", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from sol_det where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Purchase orders", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from tbl_det_man_acc where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Maintenances miscellaneous", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from tbl_pie where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Inventory Parts", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from tbl_pie_his where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Inventory History", 2);
        }

        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_cat_articulo where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_cat_bodegas where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_cat_cli where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_cat_mov where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_cat_pro where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_cat_pun_ale where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_tbl_historico where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_tbl_lot_ven where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_tbl_pedidos where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        if (!macc.strQuerySQLvariable("select count(cod_pai) from inv_tbl_transacciones where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated Product inventory Module Information", 2);
        }
        
        if (!macc.strQuerySQLvariable("select count(cod_pai) from lup_ctr_lic_enc where cod_pai = " + cod_pai + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Country has associated License Control Module Information", 2);
        }

        macc.Desconectar();

        return mvalidar;
    }

    public void eliminar() {

        if (validareliminar()) {
            try {
                macc.Conectar();
                String mQuery = "delete from cat_pai where cod_pai=" + cod_pai + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar País. " + e.getMessage());
            }
            llenarPaises();
        }

        nuevo();

    }

    public void onRowSelect(SelectEvent event) {
        cod_pai = ((CatPaises) event.getObject()).getCod_pai();
        nom_pai = ((CatPaises) event.getObject()).getNom_pai();

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

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_pai() {
        return nom_pai;
    }

    public void setNom_pai(String nom_pai) {
        this.nom_pai = nom_pai;
    }

}
