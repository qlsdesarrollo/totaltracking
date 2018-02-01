package paquetes;

import tt.general.Accesos;
import tt.general.Login;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@ConversationScoped
public class ManUniMed implements Serializable {

    private static final long serialVersionUID = 8771654786747168L;
    @Inject
    Login cbean;
    private CatUnidades catunidades;
    private List<CatUnidades> unidades;
    private String cod_uni, abr_uni, nom_uni;

    public ManUniMed() {
    }

    public CatUnidades getCatunidades() {
        return catunidades;
    }

    public void setCatunidades(CatUnidades catunidades) {
        this.catunidades = catunidades;
    }

    public List<CatUnidades> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<CatUnidades> unidades) {
        this.unidades = unidades;
    }

    public String getCod_uni() {
        return cod_uni;
    }

    public void setCod_uni(String cod_uni) {
        this.cod_uni = cod_uni;
    }

    public String getAbr_uni() {
        return abr_uni;
    }

    public void setAbr_uni(String abr_uni) {
        this.abr_uni = abr_uni;
    }

    public String getNom_uni() {
        return nom_uni;
    }

    public void setNom_uni(String nom_uni) {
        this.nom_uni = nom_uni;
    }

    public void iniciarventana() {
        cod_uni = "";
        abr_uni = "";
        nom_uni = "";
        llenarUnidades();
    }

    public void cerrarventana() {
        cod_uni = "";
        abr_uni = "";
        nom_uni = "";
        unidades = new ArrayList<>();

    }

    public void llenarUnidades() {
        try {
            catunidades = new CatUnidades();
            unidades = new ArrayList<>();
            String mQuery = "select cod_uni,abr_uni,nom_uni from cat_uni order by cod_uni;";
            ResultSet resUnidades;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resUnidades = mAccesos.querySQLvariable(mQuery);
            while (resUnidades.next()) {
                unidades.add(new CatUnidades(
                        resUnidades.getString(1),
                        resUnidades.getString(2),
                        resUnidades.getString(3)
                ));
            }
            mAccesos.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Unidades Medida. " + e.getMessage());
        }
    }

    public void nuevo() {
        cod_uni = "";
        abr_uni = "";
        nom_uni = "";
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(cod_uni)) {
                    mQuery = "select ifnull(max(cod_uni),0)+1 as codigo from cat_uni;";
                    cod_uni = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_uni(cod_uni,abr_uni,nom_uni) "
                            + "values (" + cod_uni + ",'" + abr_uni + "','" + nom_uni + "');";
                } else {
                    mQuery = "update cat_uni set abr_uni='" + abr_uni + "',nom_uni='" + nom_uni + "' "
                            + "where cod_uni=" + cod_uni + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Unidades", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Unidades", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Unidades Medida. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarUnidades();
        }
    }

    public void eliminar() {
        if ("".equals(cod_uni) == false) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                String mQuery = "delete from cat_uni where cod_uni=" + cod_uni + ";";
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Eliminar Unidades", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Unidades", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Unidades Medida. " + e.getMessage());
            }
            llenarUnidades();
        } else {
            addMessage("Eliminar Unidades", "Debe elegir un Registro.", 2);
        }
        
    }

    public boolean validardatos() {
        boolean mValidar = true;
        Accesos macc = new Accesos();
        if ("".equals(abr_uni) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar una Abreviatura para la Unidad de Medida.", 2);
        }
        if ("".equals(nom_uni) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre de Unidad de Medida.", 2);
        }
        macc.Conectar();
        if ("0".equals(macc.strQuerySQLvariable("select count(nom_uni) "
                + "from cat_uni where upper(nom_uni)='" + nom_uni.toUpperCase() + "';")) == false) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre asignado para la Unidad de Medida ya existe.", 2);
        }
        if ("0".equals(macc.strQuerySQLvariable("select count(abr_uni) "
                + "from cat_uni where upper(abr_uni)='" + abr_uni.toUpperCase() + "';")) == false) {
            mValidar = false;
            addMessage("Validar Datos", "La Abreviatura asignada para la Unidad de Medida ya existe.", 2);
        }
        macc.Desconectar();
        return mValidar;
    }

    public void onRowSelect(SelectEvent event) {
        cod_uni = ((CatUnidades) event.getObject()).getCod_uni();
        abr_uni = ((CatUnidades) event.getObject()).getAbr_uni();
        nom_uni = ((CatUnidades) event.getObject()).getNom_uni();
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
