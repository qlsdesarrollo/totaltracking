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
import tt.general.CatBodegas;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped

public class InvBodegas implements Serializable {

    private static final long serialVersionUID = 8799345674716638L;
    @Inject
    Login cbean;
    private CatPaises catpaises;
    private List<CatPaises> paises;
    private CatBodegas catbodegas;
    private List<CatBodegas> bodegas;
    private String id_bod, nom_bod, cod_pai;

    public InvBodegas() {
    }

    public void iniciarventana() {
        id_bod = "";
        nom_bod = "";
        cod_pai = cbean.getCod_pai();
        llenarPaises();
        llenarBodegas();
    }

    public void cerrarventana() {
        id_bod = "";
        nom_bod = "";
        cod_pai = "";
        bodegas = new ArrayList<>();
    }

    public void llenarPaises() {
        try {
            paises = new ArrayList<>();

            String mQuery = "select cod_pai, nom_pai "
                    + "from inv_cat_pai order by cod_pai;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en Bodegas. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {
            catbodegas = new CatBodegas();
            bodegas = new ArrayList<>();
            if ("".equals(cod_pai) || "0".equals(cod_pai)) {
                mQuery = "select bod.id_bod, bod.nom_bod,bod.cod_pai,pai.nom_pai "
                        + "from inv_cat_bodegas as bod "
                        + "inner join inv_cat_pai as pai on bod.cod_pai = pai.cod_pai "
                        + "order by id_bod;";
            } else {
                mQuery = "select bod.id_bod, bod.nom_bod,bod.cod_pai,pai.nom_pai "
                        + "from inv_cat_bodegas as bod "
                        + "inner join inv_cat_pai as pai on bod.cod_pai = pai.cod_pai "
                        + "where bod.cod_pai=" + cod_pai + " "
                        + "order by id_bod;";
            }
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Bodegas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_bod = "";
        nom_bod = "";
        cod_pai = cbean.getCod_pai();
        catbodegas = new CatBodegas();
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_bod) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para la Bodega.", 2);
        }
        if ("0".equals(cod_pai) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_bod) from inv_cat_bodegas "
                + "where upper(nom_bod)='" + nom_bod.toUpperCase() + "' and cod_pai ="
                + cbean.getCod_pai() + ";")) == false
                && "".equals(id_bod)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre de la Bodega ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_bod)) {
                    mQuery = "select ifnull(max(id_bod),0)+1 as codigo from inv_cat_bodegas;";
                    id_bod = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_bodegas (id_bod,nom_bod,cod_pai) "
                            + "values (" + id_bod + ",'" + nom_bod + "'," + cod_pai + ");";
                } else {
                    mQuery = "update inv_cat_bodegas SET "
                            + " nom_bod = '" + nom_bod + "', "
                            + " cod_pai = " + cod_pai + " "
                            + "WHERE id_bod = " + id_bod + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Bodegas", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Bodegas", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Bodegas. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarBodegas();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_bod)) {
            addMessage("Eliminar Bodega", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else {
            if (!"0".equals(mAccesos.strQuerySQLvariable("select count(cod_bod) as contador from inv_cat_ubicaciones where cod_bod =" + id_bod + ";"))) {
                addMessage("Eliminar Bodega", "Esta Bodega tiene registros relacionados en catálogo de ubicaciones y no puede ser eliminada", 2);
                mvalidar = false;
            }
            if (!"0".equals(mAccesos.strQuerySQLvariable("select count(cod_bod) as contador from inv_tbl_historico where cod_bod =" + id_bod + ";"))) {
                addMessage("Eliminar Bodega", "Esta Bodega tiene registros relacionados en Control de Inventario y no puede ser eliminada", 2);
                mvalidar = false;
            }
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_bodegas where id_bod=" + id_bod + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Bodega", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Bodega", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Bodegas. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarBodegas();
            nuevo();
        } 
        mAccesos.Desconectar();

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
