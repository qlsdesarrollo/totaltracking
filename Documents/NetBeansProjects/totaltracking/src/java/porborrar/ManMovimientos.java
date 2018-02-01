package porborrar;

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
import tt.general.CatMovimientos;
import tt.general.Login;

@Named
@ConversationScoped
public class ManMovimientos implements Serializable {

    private static final long serialVersionUID = 8719478674716638L;
    @Inject
    Login cbean;
    private CatMovimientos catmovimientos;
    private List<CatMovimientos> movimientos;
    private String id_mov, nom_mov, flg_tip;

    public ManMovimientos() {
    }

    public CatMovimientos getCatmovimientos() {
        return catmovimientos;
    }

    public void setCatmovimientos(CatMovimientos catmovimientos) {
        this.catmovimientos = catmovimientos;
    }

    public List<CatMovimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<CatMovimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public String getId_mov() {
        return id_mov;
    }

    public void setId_mov(String id_mov) {
        this.id_mov = id_mov;
    }

    public String getNom_mov() {
        return nom_mov;
    }

    public void setNom_mov(String nom_mov) {
        this.nom_mov = nom_mov;
    }

    public String getFlg_tip() {
        return flg_tip;
    }

    public void setFlg_tip(String flg_tip) {
        this.flg_tip = flg_tip;
    }

    public void iniciarventana() {
        id_mov = "";
        nom_mov = "";
        flg_tip = "0";
        llenarMovimientos();
    }

    public void cerrarventana() {
        id_mov = "";
        nom_mov = "";
        flg_tip = "0";
        movimientos = new ArrayList<>();
    }

    public void llenarMovimientos() {
        String mQuery = "";
        try {
            catmovimientos = new CatMovimientos();
            movimientos = new ArrayList<>();

            mQuery = "select id_mov, nom_mov, "
                    + "case flg_tip when '0' then 'Entrada' when '1' then 'Salida' end as flg_tip "
                    + "from cat_mov order by flg_tip;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                movimientos.add(new CatMovimientos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),""
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Movimientos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_mov = "";
        nom_mov = "";
        flg_tip = "0";
        catmovimientos = new CatMovimientos();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_mov)) {
                    mQuery = "select ifnull(max(id_mov),0)+1 as codigo from cat_mov;";
                    id_mov = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_mov (id_mov,nom_mov,flg_tip) "
                            + "values (" + id_mov + ",'" + nom_mov + "', " + flg_tip + ");";
                } else {
                    mQuery = "update cat_mov SET "
                            + " nom_mov = '" + nom_mov + "', "
                            + " flg_tip = " + flg_tip + " "
                            + "WHERE id_mov = " + id_mov + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Movimientos", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Movimientos", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Movimientos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMovimientos();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_mov) == false) {
            try {
                mQuery = "delete from cat_mov where id_mov=" + id_mov + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Movimiento", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Movimiento", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Movimiento. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMovimientos();
            nuevo();
        } else {
            addMessage("Eliminar Familia", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("false".equals(flg_tip)) {
            flg_tip = "0";
        } else {
            flg_tip = "1";
        }
        if ("".equals(nom_mov) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Tipo de Movimiento.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_mov) from cat_mov "
                + "where upper(nom_mov)='" + nom_mov.toUpperCase() + "';")) == false && "".equals(id_mov)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del tipo de Movimiento ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_mov = ((CatMovimientos) event.getObject()).getId_mov();
        nom_mov = ((CatMovimientos) event.getObject()).getNom_mov();
        flg_tip = ((CatMovimientos) event.getObject()).getFlg_tip();
        if ("Entrada".equals(flg_tip)) {
            flg_tip = "false";
        } else {
            flg_tip = "true";
        }
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
