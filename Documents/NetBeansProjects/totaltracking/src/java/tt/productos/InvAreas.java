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
import tt.general.Login;

@Named
@ConversationScoped
public class InvAreas implements Serializable {

    private static final long serialVersionUID = 8719478679471638L;
    @Inject
    Login cbean;
    private Accesos macc;
    private CatAreasInv catareasinv;
    private List<CatAreasInv> areas;
    private String cod_are, nom_are;

    public InvAreas() {
    }

    public void iniciarventana() {
        cod_are = "";
        nom_are = "";

        catareasinv = new CatAreasInv();
        areas = new ArrayList<>();

        macc = new Accesos();

        llenarAreas();

    }

    public void nuevo() {
        cod_are = "";
        nom_are = "";
        catareasinv = null;

    }

    public void cerrarventana() {
        cod_are = "";
        nom_are = "";
        catareasinv = null;
        areas = null;

        macc = null;
    }

    public void llenarAreas() {
        try {
            catareasinv = null;
            areas.clear();

            String mQuery = "select cod_are, nom_are "
                    + "from inv_cat_are order by cod_are;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                areas.add(new CatAreasInv(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Áreas en Catálogo de Áreas. " + e.getMessage());
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_are) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Área.", 2);
        }
        //Accesos maccesos = new Accesos();
        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(cod_are) from inv_cat_are "
                + "where upper(nom_are)='" + nom_are.toUpperCase() + "' ;")) == false
                && "".equals(cod_are)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del Área ya existe.", 2);
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
                if ("".equals(cod_are)) {
                    mQuery = "select ifnull(max(cod_are),0)+1 as codigo from inv_cat_are;";
                    cod_are = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_are (cod_are,nom_are) "
                            + "values (" + cod_are + ",'" + nom_are + "');";
                } else {
                    mQuery = "update inv_cat_are SET "
                            + "nom_are = '" + nom_are + "' "
                            + "WHERE cod_are = " + cod_are
                            + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Guardar Área", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Área", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Área. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarAreas();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        macc.Conectar();
        if ("".equals(cod_are)) {
            addMessage("Eliminar Área", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else if (!"0".equals(macc.strQuerySQLvariable("select count(cod_are) as contador from inv_tbl_transacciones where cod_are =" + cod_are + ";"))) {
            addMessage("Eliminar Área", "Esta Área tiene registros relacionados en Control de Inventario y no puede ser eliminada", 2);
            mvalidar = false;
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_are where cod_are=" + cod_are + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar Área", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Área", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Área. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarAreas();
            nuevo();
        }
        macc.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        cod_are = ((CatAreasInv) event.getObject()).getCod_are();
        nom_are = ((CatAreasInv) event.getObject()).getNom_are();

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

    public Accesos getMacc() {
        return macc;
    }

    public void setMacc(Accesos macc) {
        this.macc = macc;
    }

    public CatAreasInv getCatareasinv() {
        return catareasinv;
    }

    public void setCatareasinv(CatAreasInv catareasinv) {
        this.catareasinv = catareasinv;
    }

    public List<CatAreasInv> getAreas() {
        return areas;
    }

    public void setAreas(List<CatAreasInv> areas) {
        this.areas = areas;
    }

    public String getCod_are() {
        return cod_are;
    }

    public void setCod_are(String cod_are) {
        this.cod_are = cod_are;
    }

    public String getNom_are() {
        return nom_are;
    }

    public void setNom_are(String nom_are) {
        this.nom_are = nom_are;
    }

}
