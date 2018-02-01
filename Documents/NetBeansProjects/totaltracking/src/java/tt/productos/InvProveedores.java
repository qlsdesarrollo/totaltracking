package tt.productos;

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
public class InvProveedores implements Serializable {

    private static final long serialVersionUID = 8719719679471638L;
    @Inject
    Login cbean;
    private Accesos macc;
    private CatProveedores catproveedores;
    private List<CatProveedores> proveedores;
    private List<CatPaises> paises;
    private String cod_pro, cod_pai, nom_pro, per_con, tel_con, det_mai, det_dir;
    private int flagpropie;

    public InvProveedores() {
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
        det_dir = "";

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
        det_dir = "";
        catproveedores = null;
    }

    public void cerrarventana() {
        cod_pro = "";
        cod_pai = "";
        nom_pro = "";
        per_con = "";
        tel_con = "";
        det_mai = "";
        det_dir = "";

        paises = null;
        catproveedores = null;
        proveedores = null;

        macc = null;
    }

    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from inv_cat_pai order by cod_pai;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
            System.out.println("Error en el llenado de Paises en Catálogo de Clientes. " + e.getMessage());
        }
    }

    public void llenarProveedores() {
        String mQuery = "";
        try {
            catproveedores = null;
            proveedores.clear();

            mQuery = "select cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie,det_dir  "
                    + "from inv_cat_pro where cod_pai = " + cod_pai + " order by cod_pro;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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
                        resVariable.getString(7),
                        resVariable.getString(8)
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
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Proveedor.", 2);
        }
        //Accesos maccesos = new Accesos();
        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_pro) from inv_cat_pro "
                + "where upper(nom_pro)='" + nom_pro.toUpperCase()
                + "' and cod_pai =" + cod_pai + " and flg_pro_pie = " + flagpropie + ";")) == false
                && "".equals(cod_pro)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del Proveedor ya existe.", 2);
        }
        macc.Desconectar();
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                //Accesos mAccesos = new Accesos();
                macc.Conectar();
                if ("".equals(cod_pro)) {
                    mQuery = "select ifnull(max(cod_pro),0)+1 as codigo from inv_cat_pro;";
                    cod_pro = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_pro (cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie,det_dir) "
                            + "values (" + cod_pro + "," + cod_pai + ",'" + nom_pro + "', '"
                            + per_con + "','" + tel_con + "','" + det_mai + "'," + flagpropie + ",'" + det_dir + "');";
                } else {
                    mQuery = "update inv_cat_pro SET "
                            + " nom_pro = '" + nom_pro + "' "
                            + " ,per_con = '" + per_con + "' "
                            + " ,tel_con = '" + tel_con + "' "
                            + " ,det_mai = '" + det_mai + "' "
                            + " ,det_dir = '" + det_dir + "' "
                            + "WHERE cod_pro = " + cod_pro + " "
                            + "and cod_pai = " + cod_pai + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Guardar Proveedores", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Proveedores", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Proveedores. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarProveedores();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        macc.Conectar();
        if ("".equals(cod_pro)) {
            addMessage("Eliminar Proveedores", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else if (!"0".equals(macc.strQuerySQLvariable("select count(cod_cli_pro) as contador from inv_tbl_transacciones where cod_cli_pro =" + cod_pro + " and flg_ing_sal = 'false';"))) {
            addMessage("Eliminar Proveedores", "Esta Proveedor tiene registros relacionados en Control de Inventario y no puede ser eliminado", 2);
            mvalidar = false;
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_pro where cod_pro=" + cod_pro + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar Proveedores", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Proveedores", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Proveedores. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarProveedores();
            nuevo();
        }
        macc.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        cod_pro = ((CatProveedores) event.getObject()).getCod_pro();
        cod_pai = ((CatProveedores) event.getObject()).getCod_pai();
        nom_pro = ((CatProveedores) event.getObject()).getNom_pro();
        per_con = ((CatProveedores) event.getObject()).getPer_con();
        tel_con = ((CatProveedores) event.getObject()).getTel_con();
        det_mai = ((CatProveedores) event.getObject()).getDet_mai();
        det_dir = ((CatProveedores) event.getObject()).getDet_dir();
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

    public String getDet_dir() {
        return det_dir;
    }

    public void setDet_dir(String det_dir) {
        this.det_dir = det_dir;
    }

}
