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
import tt.general.CatProveedores;
import tt.general.Login;

@Named
@ConversationScoped
public class ManSuppliers implements Serializable {

    private static final long serialVersionUID = 8719719679471638L;
    @Inject
    Login cbean;
    private CatProveedores catproveedores;
    private List<CatProveedores> proveedores;
    private List<CatPaises> paises;
    private String cod_pro, cod_pai, nom_pro, per_con, tel_con, det_mai;
    private int flagpropie;

    private Accesos macc;

    public ManSuppliers() {
    }

    public void iniciarventana(int flgpropie) {
        macc = new Accesos();

        cod_pro = "";
        cod_pai = cbean.getCod_pai();
        nom_pro = "";
        per_con = "";
        tel_con = "";
        det_mai = "";
        flagpropie = flgpropie;

        paises = new ArrayList<>();
        catproveedores = new CatProveedores();
        proveedores = new ArrayList<>();

        llenarProveedores();
        llenarPaises();
    }

    public void nuevo() {
        cod_pro = "";
        cod_pai = cbean.getCod_pai();
        nom_pro = "";
        per_con = "";
        tel_con = "";
        det_mai = "";

        catproveedores = null;
    }

    public void cerrarventana() {
        cod_pro = "";
        cod_pai = "";
        nom_pro = "";
        per_con = "";
        tel_con = "";
        det_mai = "";

        paises = null;
        catproveedores = null;
        proveedores = null;

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
            System.out.println("Error en el llenado de Paises en Catálogo de Proveedores. " + e.getMessage());
        }
    }

    public void llenarProveedores() {
        String mQuery = "";
        try {
            catproveedores = null;
            proveedores.clear();

            mQuery = "select cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie  "
                    + "from cat_sup where flg_pro_pie =" + flagpropie + " order by cod_pro;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                proveedores.add(new CatProveedores(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7), ""
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Proveedores. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_pro) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name.", 2);
        }

        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_pro) from cat_sup "
                + "where upper(nom_pro)='" + nom_pro.toUpperCase()
                + "' and cod_pai =0 and flg_pro_pie = " + flagpropie + ";")) == false
                && "".equals(cod_pro)) {
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
                if ("".equals(cod_pro)) {
                    mQuery = "select ifnull(max(cod_pro),0)+1 as codigo from cat_sup;";
                    cod_pro = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_sup (cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie) "
                            + "values (" + cod_pro + ",0,'" + nom_pro + "', '"
                            + per_con + "','" + tel_con + "','" + det_mai + "'," + flagpropie + ");";
                } else {
                    mQuery = "update cat_sup SET "
                            + " nom_pro = '" + nom_pro + "' "
                            + " ,per_con = '" + per_con + "' "
                            + " ,tel_con = '" + tel_con + "' "
                            + " ,det_mai = '" + det_mai + "' "
                            + "WHERE cod_pro = " + cod_pro + " "
                            + "and cod_pai = " + cod_pai + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Proveedores. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarProveedores();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_pro) || "0".equals(cod_pro)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_cat) from cat_pie where cod_cat = " + cod_pro + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Supplier has associated Parts", 2);
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from cat_sup where cod_pro=" + cod_pro + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Proveedores. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarProveedores();
            nuevo();
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_pro = ((CatProveedores) event.getObject()).getCod_pro();
        cod_pai = ((CatProveedores) event.getObject()).getCod_pai();
        nom_pro = ((CatProveedores) event.getObject()).getNom_pro();
        per_con = ((CatProveedores) event.getObject()).getPer_con();
        tel_con = ((CatProveedores) event.getObject()).getTel_con();
        det_mai = ((CatProveedores) event.getObject()).getDet_mai();
    }

    public void onselectpais() {
        llenarProveedores();
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

    public CatProveedores getCatproveedores() {
        return catproveedores;
    }

    public void setCatproveedores(CatProveedores catproveedores) {
        this.catproveedores = catproveedores;
    }

    public List<CatProveedores> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<CatProveedores> proveedores) {
        this.proveedores = proveedores;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public String getPer_con() {
        return per_con;
    }

    public void setPer_con(String per_con) {
        this.per_con = per_con;
    }

    public String getTel_con() {
        return tel_con;
    }

    public void setTel_con(String tel_con) {
        this.tel_con = tel_con;
    }

    public String getDet_mai() {
        return det_mai;
    }

    public void setDet_mai(String det_mai) {
        this.det_mai = det_mai;
    }

    public int getFlagpropie() {
        return flagpropie;
    }

    public void setFlagpropie(int flagpropie) {
        this.flagpropie = flagpropie;
    }

}
