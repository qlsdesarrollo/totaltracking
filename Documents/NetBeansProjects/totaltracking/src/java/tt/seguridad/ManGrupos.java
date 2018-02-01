package tt.seguridad;

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
import tt.general.CatGrupos;
import tt.general.Login;

@Named
@ConversationScoped
public class ManGrupos implements Serializable {

    private static final long serialVersionUID = 8797678674716638L;
    @Inject
    Login cbean;
    private CatGrupos catgrupos;
    private List<CatGrupos> grupos;
    private String id_grp, des_grp;

    public ManGrupos() {
    }

    public CatGrupos getCatgrupos() {
        return catgrupos;
    }

    public void setCatgrupos(CatGrupos catgrupos) {
        this.catgrupos = catgrupos;
    }

    public List<CatGrupos> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<CatGrupos> grupos) {
        this.grupos = grupos;
    }

    public String getId_grp() {
        return id_grp;
    }

    public void setId_grp(String id_grp) {
        this.id_grp = id_grp;
    }

    public String getDes_grp() {
        return des_grp;
    }

    public void setDes_grp(String des_grp) {
        this.des_grp = des_grp;
    }

    public void iniciarventana() {
        id_grp = "";
        des_grp = "";
        llenarGrupos();
    }

    public void cerrarventana() {
        id_grp = "";
        des_grp = "";
        grupos = new ArrayList<>();
    }

    public void llenarGrupos() {
        String mQuery = "";
        try {
            catgrupos = new CatGrupos();
            grupos = new ArrayList<>();

            mQuery = "select id_grp, des_grp from cat_grp order by id_grp;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                grupos.add(new CatGrupos(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Grupos de Seguridad. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_grp = "";
        des_grp = "";
        catgrupos = new CatGrupos();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_grp)) {
                    mQuery = "select ifnull(max(id_grp),0)+1 as codigo from cat_grp;";
                    id_grp = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_grp (id_grp,des_grp) "
                            + "values (" + id_grp + ",'" + des_grp + "');";
                } else {
                    mQuery = "update cat_grp SET "
                            + " des_grp = '" + des_grp + "' "
                            + "WHERE id_grp = " + id_grp + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Grupo", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Grupo", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Grupo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarGrupos();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_grp) == false) {
            try {
                mQuery = "delete from cat_grp where id_grp=" + id_grp + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Grupo", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Grupo", "Error al momento de Eliminar Grupo, tiene usuarios dependientes. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Grupo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarGrupos();
            nuevo();
        } else {
            addMessage("Eliminar Grupos", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(des_grp) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Grupo.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_grp) from cat_grp "
                + "where upper(des_grp)='" + des_grp.toUpperCase() + "';")) == false && "".equals(id_grp)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del Grupo ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_grp = ((CatGrupos) event.getObject()).getId_grp();
        des_grp = ((CatGrupos) event.getObject()).getDes_grp();
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
