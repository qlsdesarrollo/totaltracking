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

public class InvFamilias implements Serializable {

    private static final long serialVersionUID = 8799478674716638L;
    @Inject
    Login cbean;
    private CatFamilia catfamilia;
    private List<CatFamilia> familia;
    private String id_fam, nom_fam;

    public InvFamilias() {
    }

    public CatFamilia getCatfamilia() {
        return catfamilia;
    }

    public void setCatfamilia(CatFamilia catfamilia) {
        this.catfamilia = catfamilia;
    }

    public List<CatFamilia> getFamilia() {
        return familia;
    }

    public void setFamilia(List<CatFamilia> familia) {
        this.familia = familia;
    }

    public String getId_fam() {
        return id_fam;
    }

    public void setId_fam(String id_fam) {
        this.id_fam = id_fam;
    }

    public String getNom_fam() {
        return nom_fam;
    }

    public void setNom_fam(String nom_fam) {
        this.nom_fam = nom_fam;
    }

    public void iniciarventana() {
        id_fam = "";
        nom_fam = "";
        llenarFamilias();
    }

    public void cerrarventana() {
        id_fam = "";
        nom_fam = "";
        familia = new ArrayList<>();
    }

    public void llenarFamilias() {
        String mQuery = "";
        try {
            catfamilia = new CatFamilia();
            familia = new ArrayList<>();

            mQuery = "select id_fam, nom_fam from inv_cat_familia order by id_fam;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                familia.add(new CatFamilia(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Familias. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_fam = "";
        nom_fam = "";
        catfamilia = new CatFamilia();
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_fam) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para la Plataforma.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_fam) from inv_cat_familia "
                + "where upper(nom_fam)='" + nom_fam.toUpperCase() + "';")) == false && "".equals(id_fam)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre de la Plataforma ya existe.", 2);
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
                if ("".equals(id_fam)) {
                    mQuery = "select ifnull(max(id_fam),0)+1 as codigo from inv_cat_familia;";
                    id_fam = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_familia (id_fam,nom_fam) "
                            + "values (" + id_fam + ",'" + nom_fam + "');";
                } else {
                    mQuery = "update inv_cat_familia SET "
                            + " nom_fam = '" + nom_fam + "' "
                            + "WHERE id_fam = " + id_fam + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Plataforma", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Plataforma", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Familia. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarFamilias();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_fam)) {
            addMessage("Eliminar Plataforma", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else if (!"0".equals(mAccesos.strQuerySQLvariable("select count(det_fam) as contador from inv_cat_articulo where det_fam =" + id_fam + ";"))) {
            addMessage("Eliminar Plataforma", "Esta Plataforma tiene registros relacionados en catálogo de artículos y no puede ser eliminada", 2);
            mvalidar = false;
        }
        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_familia where id_fam=" + id_fam + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Plataforma", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Plataforma", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Familia. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarFamilias();
            nuevo();
        }
        mAccesos.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        id_fam = ((CatFamilia) event.getObject()).getId_fam();
        nom_fam = ((CatFamilia) event.getObject()).getNom_fam();
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
