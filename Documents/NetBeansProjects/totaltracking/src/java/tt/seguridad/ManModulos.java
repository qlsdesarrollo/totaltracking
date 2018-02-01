package tt.seguridad;

import tt.seguridad.CatModulos;
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
import tt.seguridad.CatModulos;
import tt.general.Login;

@Named
@ConversationScoped
public class ManModulos implements Serializable {

    private static final long serialVersionUID = 879767867471668L;
    @Inject
    Login cbean;
    private CatModulos catmodulos;
    private List<CatModulos> modulos;
    private String id_mod, des_mod;

    public ManModulos() {
    }

    public CatModulos getCatmodulos() {
        return catmodulos;
    }

    public void setCatmarcas(CatModulos catmodulos) {
        this.catmodulos = catmodulos;
    }

    public List<CatModulos> getModulos() {
        return modulos;
    }

    public void setModulos(List<CatModulos> modulos) {
        this.modulos = modulos;
    }

    public String getId_mod() {
        return id_mod;
    }

    public void setId_mod(String id_mod) {
        this.id_mod = id_mod;
    }

    public String getDes_mod() {
        return des_mod;
    }

    public void setDes_mod(String des_mod) {
        this.des_mod = des_mod;
    }

    public void iniciarventana() {
        id_mod = "";
        des_mod = "";
        llenarModulos();
    }

    public void cerrarventana() {
        id_mod = "";
        des_mod = "";
        modulos = new ArrayList<>();
    }

    public void llenarModulos() {
        String mQuery = "";
        try {
            catmodulos = new CatModulos();
            modulos = new ArrayList<>();

            mQuery = "select id_mod, des_mod from cat_mod order by id_mod;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                modulos.add(new CatModulos(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Modulos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_mod = "";
        des_mod = "";
        catmodulos = new CatModulos();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_mod)) {
                    mQuery = "select ifnull(max(id_mod),0)+1 as codigo from cat_mod;";
                    id_mod = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_mod (id_mod,nom_mod) "
                            + "values (" + id_mod + ",'" + des_mod + "');";
                } else {
                    mQuery = "update cat_mod SET "
                            + " des_mod = '" + des_mod + "' "
                            + "WHERE id_mod = " + id_mod + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Modulo", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Modulo", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Marca. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarModulos();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_mod) == false) {
            try {
                mQuery = "delete from cat_mod where id_mod=" + id_mod + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Modulo", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Modulo", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Modulo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarModulos();
            nuevo();
        } else {
            addMessage("Eliminar Modulos", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(des_mod) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Modulo.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_mod) from cat_mod "
                + "where upper(des_mod)='" + des_mod.toUpperCase() + "';")) == false && "".equals(id_mod)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del Modulo ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_mod = ((CatModulos) event.getObject()).getId_mod();
        des_mod = ((CatModulos) event.getObject()).getDes_mod();
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
