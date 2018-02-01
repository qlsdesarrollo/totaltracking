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
public class InvEmbalajes implements Serializable {

    private static final long serialVersionUID = 8799478674646638L;
    @Inject
    Login cbean;
    private CatEmbalaje catembalaje;
    private List<CatEmbalaje> embalaje;
    private String id_emb, nom_emb;

    public InvEmbalajes() {
    }

    public CatEmbalaje getCatembalaje() {
        return catembalaje;
    }

    public void setCatembalaje(CatEmbalaje catembalaje) {
        this.catembalaje = catembalaje;
    }

    public List<CatEmbalaje> getEmbalaje() {
        return embalaje;
    }

    public void setEmbalaje(List<CatEmbalaje> embalaje) {
        this.embalaje = embalaje;
    }

    public String getId_emb() {
        return id_emb;
    }

    public void setId_emb(String id_emb) {
        this.id_emb = id_emb;
    }

    public String getNom_emb() {
        return nom_emb;
    }

    public void setNom_emb(String nom_emb) {
        this.nom_emb = nom_emb;
    }

    public void iniciarventana() {
        id_emb = "";
        nom_emb = "";
        llenarEmbalaje();
    }

    public void cerrarventana() {
        id_emb = "";
        nom_emb = "";
        embalaje = new ArrayList<>();
    }

    public void llenarEmbalaje() {
        String mQuery = "";
        try {
            catembalaje = new CatEmbalaje();
            embalaje = new ArrayList<>();

            mQuery = "select id_emb, nom_emb from inv_cat_embalaje order by id_emb;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                embalaje.add(new CatEmbalaje(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Embalaje. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_emb = "";
        nom_emb = "";
        catembalaje = new CatEmbalaje();
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_emb) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Embalaje.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_emb) from inv_cat_embalaje "
                + "where upper(nom_emb)='" + nom_emb.toUpperCase() + "';")) == false && "".equals(id_emb)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del Embalaje ya existe.", 2);
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
                if ("".equals(id_emb)) {
                    mQuery = "select ifnull(max(id_emb),0)+1 as codigo from inv_cat_embalaje;";
                    id_emb = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_embalaje (id_emb,nom_emb) "
                            + "values (" + id_emb + ",'" + nom_emb + "');";
                } else {
                    mQuery = "update inv_cat_embalaje SET "
                            + " nom_emb= '" + nom_emb + "' "
                            + "WHERE id_emb= " + id_emb + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Embalaje", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Embalaje", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Embalaje. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEmbalaje();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_emb)) {
            addMessage("Eliminar Embalaje", "Debe elegir un Registro.", 2);
            mvalidar = false;
        } else if (!"0".equals(mAccesos.strQuerySQLvariable("select count(det_emb) as contador from inv_cat_articulo where det_emb =" + id_emb + ";"))) {
            addMessage("Eliminar Embalaje", "Esta Unidad de medida tiene registros relacionados en catálogo de artículos y no puede ser eliminada", 2);
            mvalidar = false;
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_embalaje where id_emb=" + id_emb + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Familia", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Familia", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Familia. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEmbalaje();
            nuevo();
        }
        mAccesos.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        id_emb = ((CatEmbalaje) event.getObject()).getId_emb();
        nom_emb = ((CatEmbalaje) event.getObject()).getNom_emb();
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
