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
import tt.general.CatUbicaciones;
import tt.general.Login;

@Named
@ConversationScoped
public class InvUbicaciones implements Serializable {

    private static final long serialVersionUID = 8947318674716638L;
    @Inject
    Login cbean;
    private CatPaises catpaises;
    private List<CatPaises> paises;
    private CatUbicaciones catubicaciones;
    private List<CatUbicaciones> ubicaciones;
    private CatBodegas catbodegas;
    private List<CatBodegas> bodegas;
    private String id_ubi, cod_bod, nom_ubi;
    private String cod_pai;

    public InvUbicaciones() {
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

    public CatUbicaciones getCatubicaciones() {
        return catubicaciones;
    }

    public void setCatubicaciones(CatUbicaciones catubicaciones) {
        this.catubicaciones = catubicaciones;
    }

    public List<CatUbicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<CatUbicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
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

    public String getId_ubi() {
        return id_ubi;
    }

    public void setId_ubi(String id_ubi) {
        this.id_ubi = id_ubi;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getNom_ubi() {
        return nom_ubi;
    }

    public void setNom_ubi(String nom_ubi) {
        this.nom_ubi = nom_ubi;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public void iniciarventana() {
        id_ubi = "";
        cod_bod = "0";
        nom_ubi = "";
        cod_pai = cbean.getCod_pai();
        llenarPaises();
        llenarBodegas();
        //llenarUbicaciones();
    }

    public void cerrarventana() {
        id_ubi = "";
        cod_bod = "0";
        nom_ubi = "";
        cod_pai = "";
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();
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
            System.out.println("Error en el llenado de Paises en Ubicaciones. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {
            cod_bod = "0";
            nom_ubi = "";
            ubicaciones = new ArrayList<>();
            bodegas = new ArrayList<>();

            mQuery = "select bod.id_bod, bod.nom_bod,bod.cod_pai,pai.nom_pai "
                    + "from inv_cat_bodegas as bod "
                    + "inner join inv_cat_pai as pai on bod.cod_pai = pai.cod_pai "
                    + "where bod.cod_pai=" + cod_pai + " "
                    + "order by id_bod;";

            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3), ""
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en Catálogo Ubicaciones. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones = new ArrayList<>();

            if ("".equals(cod_pai) || "0".equals(cod_pai)) {
                mQuery = "select ubi.id_ubi,ubi.cod_bod,ubi.nom_ubi,bod.nom_bod, bod.cod_pai, pai.nom_pai "
                        + "from inv_cat_ubicaciones as ubi "
                        + "left join inv_cat_bodegas as bod on bod.id_bod = ubi.cod_bod "
                        + "left join inv_cat_pai as pai on bod.cod_pai = pai.cod_pai "
                        + "order by ubi.cod_bod,ubi.id_ubi;";
            } else {
                mQuery = "select ubi.id_ubi,ubi.cod_bod,ubi.nom_ubi,bod.nom_bod, bod.cod_pai, pai.nom_pai "
                        + "from inv_cat_ubicaciones as ubi "
                        + "left join inv_cat_bodegas as bod on bod.id_bod = ubi.cod_bod "
                        + "left join inv_cat_pai as pai on bod.cod_pai = pai.cod_pai "
                        + "where ubi.cod_bod = " + cod_bod + " "
                        + "order by ubi.cod_bod,ubi.id_ubi;";
            }
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new CatUbicaciones(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Ubicaciones. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_ubi = "";
        cod_bod = "0";
        nom_ubi = "";
        cod_pai = cbean.getCod_pai();
        catubicaciones = new CatUbicaciones();
    }
    
    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_ubi) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para la Ubicación.", 2);
        }
        if ("0".equals(cod_bod) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Escoger una Bodega.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_ubi) from inv_cat_ubicaciones "
                + "where upper(nom_ubi)='" + nom_ubi.toUpperCase() + "' and cod_bod=" + cod_bod + ";")) == false
                && "".equals(id_ubi)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre de la Ubicación ya existe.", 2);
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
                if ("".equals(id_ubi)) {
                    mQuery = "select ifnull(max(id_ubi),0)+1 as codigo from inv_cat_ubicaciones;";
                    id_ubi = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_ubicaciones (id_ubi,cod_bod,nom_ubi) "
                            + "values (" + id_ubi + "," + cod_bod + ",'" + nom_ubi + "');";
                } else {
                    mQuery = "update inv_cat_ubicaciones SET "
                            + " cod_bod = " + cod_bod + " "
                            + ",nom_ubi = '" + nom_ubi + "' "
                            + "WHERE id_ubi = " + id_ubi + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Ubicaciones", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Ubicaciones", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Ubicaciones. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarUbicaciones();
        }
        id_ubi = "";
        nom_ubi = "";
        //nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_ubi)) {
            addMessage("Eliminar Ubicaciones", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else {
            if (!"0".equals(mAccesos.strQuerySQLvariable("select count(cod_ubi) as contador from inv_tbl_historico where cod_ubi =" + id_ubi + ";"))) {
                addMessage("Eliminar Ubicaciones", "Esta Ubicación tiene registros relacionados en Control de Inventario y no puede ser eliminada", 2);
                mvalidar = false;
            }
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_ubicaciones where id_ubi=" + id_ubi + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Ubicaciones", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Ubicaciones", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Ubicaciones. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarUbicaciones();
            //nuevo();
            id_ubi = "";
            nom_ubi = "";
        } 
        mAccesos.Desconectar();

    }

    

    public void onRowSelect(SelectEvent event) {
        id_ubi = ((CatUbicaciones) event.getObject()).getId_ubi();
        nom_ubi = ((CatUbicaciones) event.getObject()).getNom_ubi();
        cod_pai = ((CatUbicaciones) event.getObject()).getCod_pai();
        cod_bod = ((CatUbicaciones) event.getObject()).getCod_bod();
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

}
