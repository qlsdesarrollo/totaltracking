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
public class ManTipos implements Serializable {

    private static final long serialVersionUID = 8799478674646638L;
    @Inject
    Login cbean;
    private CatTipos cattipos;
    private List<CatTipos> tipos;
    private String cod_tip, nom_tip, flg_urg;

    private Accesos macc;

    public ManTipos() {
    }

    public void iniciarventana() {
        macc = new Accesos();

        cod_tip = "";
        nom_tip = "";
        flg_urg = "false";

        cattipos = new CatTipos();
        tipos = new ArrayList<>();

        llenarTipos();
    }

    public void nuevo() {
        cod_tip = "";
        nom_tip = "";
        flg_urg = "false";
        cattipos = null;
    }

    public void cerrarventana() {
        cod_tip = "";
        nom_tip = "";
        flg_urg = "false";

        cattipos = null;
        tipos = null;

        macc = null;
    }

    public void llenarTipos() {
        String mQuery = "";
        try {
            cattipos = null;
            tipos.clear();

            mQuery = "select cod_tip, nom_tip,"
                    + "flg_urg "
                    + "from cat_tip order by cod_tip;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tipos.add(new CatTipos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Tipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_tip) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name.", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_tip) from cat_tip "
                + "where upper(nom_tip)='" + nom_tip.toUpperCase() + "';")) == false && "".equals(cod_tip)) {
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
                if ("".equals(cod_tip)) {
                    mQuery = "select ifnull(max(cod_tip),0)+1 as codigo from cat_tip;";
                    cod_tip = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_tip (cod_tip,nom_tip,flg_urg) "
                            + "values (" + cod_tip + ",'" + nom_tip + "','" + flg_urg + "');";
                } else {
                    mQuery = "update cat_tip SET "
                            + " nom_tip= '" + nom_tip + "', "
                            + " flg_urg= '" + flg_urg + "' "
                            + "WHERE cod_tip= " + cod_tip + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Tipos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarTipos();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_tip) || "0".equals(cod_tip)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_tip) from tbl_mae_man where cod_tip = " + cod_tip + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Maintenance Type has associated Maintenances", 2);
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_tip where cod_tip=" + cod_tip + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Tipos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarTipos();
            nuevo();
        } else {
            addMessage("Delete", "You have to select a Record.", 2);
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_tip = ((CatTipos) event.getObject()).getCod_tip();
        nom_tip = ((CatTipos) event.getObject()).getNom_tip();
        flg_urg = ((CatTipos) event.getObject()).getFlg_urg();

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

    public CatTipos getCattipos() {
        return cattipos;
    }

    public void setCattipos(CatTipos cattipos) {
        this.cattipos = cattipos;
    }

    public List<CatTipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<CatTipos> tipos) {
        this.tipos = tipos;
    }

    public String getCod_tip() {
        return cod_tip;
    }

    public void setCod_tip(String cod_tip) {
        this.cod_tip = cod_tip;
    }

    public String getNom_tip() {
        return nom_tip;
    }

    public void setNom_tip(String nom_tip) {
        this.nom_tip = nom_tip;
    }

    public String getFlg_urg() {
        return flg_urg;
    }

    public void setFlg_urg(String flg_urg) {
        this.flg_urg = flg_urg;
    }

}
