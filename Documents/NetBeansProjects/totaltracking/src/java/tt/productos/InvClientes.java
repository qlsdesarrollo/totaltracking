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
import tt.general.CatClientes;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped

public class InvClientes implements Serializable {

    private static final long serialVersionUID = 8719478679471638L;
    @Inject
    Login cbean;
    private Accesos macc;
    private CatClientes catclientes;
    private List<CatClientes> clientes;
    private List<CatPaises> paises;
    private String cod_cli, cod_pai, nom_cli, per_con, tel_con, det_mai, det_dir;
    private int flagpropie;

    public InvClientes() {
    }

    public void iniciarventana(int flgpropie) {
        macc = new Accesos();
        cod_cli = "";
        cod_pai = cbean.getCod_pai();
        nom_cli = "";
        per_con = "";
        tel_con = "";
        det_mai = "";
        det_dir = "";
        flagpropie = flgpropie;

        paises = new ArrayList<>();
        catclientes = new CatClientes();
        clientes = new ArrayList<>();

        llenarClientes();
        llenarPaises();
    }

    public void nuevo() {
        cod_cli = "";
        cod_pai = cbean.getCod_pai();
        nom_cli = "";
        per_con = "";
        tel_con = "";
        det_mai = "";
        det_dir = "";
        catclientes = null;
    }

    public void cerrarventana() {
        cod_cli = "";
        cod_pai = "";
        nom_cli = "";
        per_con = "";
        tel_con = "";
        det_mai = "";
        det_dir = "";
        paises = null;
        catclientes = null;
        clientes = null;
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

    public void llenarClientes() {
        String mQuery = "";
        try {
            catclientes = null;
            clientes.clear();

            mQuery = "select cod_cli,cod_pai, nom_cli,per_con,tel_con,det_mai,flg_pro_pie,det_dir  "
                    + "from inv_cat_cli where cod_pai = " + cod_pai + " order by cod_cli;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new CatClientes(
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
            System.out.println("Error en el llenado de Catálogo Clientes. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_cli) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Cliente.", 2);
        }
        //Accesos maccesos = new Accesos();
        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_cli) from inv_cat_cli "
                + "where upper(nom_cli)='" + nom_cli.toUpperCase() + "' and cod_pai=" + cod_pai
                + " and flg_pro_pie =" + flagpropie + ";")) == false
                && "".equals(cod_cli)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del Cliente ya existe.", 2);
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
                if ("".equals(cod_cli)) {
                    mQuery = "select ifnull(max(cod_cli),0)+1 as codigo from inv_cat_cli;";
                    cod_cli = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_cli (cod_cli,cod_pai,nom_cli,per_con,tel_con,det_mai,flg_pro_pie,det_dir) "
                            + "values (" + cod_cli + "," + cod_pai + ",'" + nom_cli + "', '"
                            + per_con + "','" + tel_con + "','" + det_mai + "'," + flagpropie + ",'" + det_dir + "');";
                } else {
                    mQuery = "update inv_cat_cli SET "
                            + "nom_cli = '" + nom_cli + "' "
                            + ",per_con = '" + per_con + "' "
                            + ",tel_con = '" + tel_con + "' "
                            + ",det_mai = '" + det_mai + "' "
                            + ",det_dir = '" + det_dir + "' "
                            + "WHERE cod_cli = " + cod_cli + " "
                            + "and cod_pai = " + cod_pai + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Guardar Clientes", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Clientes", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Clientes. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarClientes();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        macc.Conectar();
        if ("".equals(cod_cli)) {
            addMessage("Eliminar Clientes", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else {
            if (!"0".equals(macc.strQuerySQLvariable("select count(cod_cli_pro) as contador from inv_tbl_transacciones where cod_cli_pro =" + cod_cli + " and flg_ing_sal = 'true';"))) {
                addMessage("Eliminar Clientes", "Esta Cliente tiene registros relacionados en Control de Inventario y no puede ser eliminado", 2);
                mvalidar = false;
            }
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_cli where cod_cli=" + cod_cli + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar Clientes", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Clientes", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Clientes. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarClientes();
            nuevo();
        } 
        macc.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        cod_cli = ((CatClientes) event.getObject()).getCod_cli();
        cod_pai = ((CatClientes) event.getObject()).getCod_pai();
        nom_cli = ((CatClientes) event.getObject()).getNom_cli();
        per_con = ((CatClientes) event.getObject()).getPer_con();
        tel_con = ((CatClientes) event.getObject()).getTel_con();
        det_mai = ((CatClientes) event.getObject()).getDet_mai();
        det_dir = ((CatClientes) event.getObject()).getDet_dir();
    }

    public void onselectpais() {
        llenarClientes();
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

    public CatClientes getCatclientes() {
        return catclientes;
    }

    public void setCatclientes(CatClientes catclientes) {
        this.catclientes = catclientes;
    }

    public List<CatClientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<CatClientes> clientes) {
        this.clientes = clientes;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
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
