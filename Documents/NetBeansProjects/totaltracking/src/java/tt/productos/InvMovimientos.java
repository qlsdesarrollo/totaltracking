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
import tt.general.CatMovimientosInv;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped
public class InvMovimientos implements Serializable {

    private static final long serialVersionUID = 8719478674716638L;
    @Inject
    Login cbean;
    private CatMovimientosInv catmovimientos;
    private List<CatMovimientosInv> movimientos;
    private List<CatPaises> paises;
    private String id_mov, nom_mov, flg_tip, cod_pai, det_abr;

    public InvMovimientos() {
    }

    public CatMovimientosInv getCatmovimientos() {
        return catmovimientos;
    }

    public void setCatmovimientos(CatMovimientosInv catmovimientos) {
        this.catmovimientos = catmovimientos;
    }

    public List<CatMovimientosInv> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<CatMovimientosInv> movimientos) {
        this.movimientos = movimientos;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
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

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getDet_abr() {
        return det_abr;
    }

    public void setDet_abr(String det_abr) {
        this.det_abr = det_abr;
    }

    public void iniciarventana() {
        id_mov = "";
        nom_mov = "";
        flg_tip = "0";
        det_abr = "";
        cod_pai = cbean.getCod_pai();

        paises = new ArrayList<>();

        catmovimientos = new CatMovimientosInv();
        movimientos = new ArrayList<>();

        llenarPaises();
        llenarMovimientos();
    }

    public void cerrarventana() {
        id_mov = "";
        nom_mov = "";
        flg_tip = "0";
        cod_pai = "0";
        det_abr = "";

        paises = null;
        catmovimientos = null;
        movimientos = null;
    }

    public void nuevo() {
        id_mov = "";
        nom_mov = "";
        flg_tip = "0";
        det_abr = "";
        cod_pai = cbean.getCod_pai();

        catmovimientos = null;

    }

    public void llenarPaises() {
        try {
            paises.clear();

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
            System.out.println("Error en el llenado del Catálogo de Paises en InvMovimientos. " + e.getMessage());
        }
    }

    public void llenarMovimientos() {
        String mQuery = "";
        try {
            catmovimientos = null;
            movimientos.clear();

            mQuery = "select id_mov, nom_mov, "
                    + "case flg_tip when '0' then 'Entrada' when '1' then 'Salida' end as flg_tip,"
                    + "cod_pai,det_abr "
                    + "from inv_cat_mov "
                    + "where cod_pai = " + cod_pai + " "
                    + "order by flg_tip, id_mov;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                movimientos.add(new CatMovimientosInv(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo InvMovimientos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("false".equals(flg_tip)) {
            flg_tip = "0";
        } else {
            flg_tip = "1";
        }
        if ("".equals(nom_mov)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Tipo de Movimiento.", 2);
        }
        if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_mov) from inv_cat_mov "
                + "where upper(nom_mov)='" + nom_mov.toUpperCase() + "' and cod_pai = " + cod_pai + " ;")) == false
                && "".equals(id_mov)) {
            mValidar = false;
            addMessage("Validar Datos", "El Nombre del tipo de Movimiento ya existe.", 2);
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
                if ("".equals(id_mov)) {
                    mQuery = "select ifnull(max(id_mov),0)+1 as codigo from inv_cat_mov;";
                    id_mov = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_mov (id_mov,nom_mov,flg_tip,cod_pai,det_abr) "
                            + "values (" + id_mov + ",'" + nom_mov + "', " + flg_tip + "," + cod_pai + ",'" + det_abr + "');";
                } else {
                    mQuery = "update inv_cat_mov SET "
                            + "nom_mov = '" + nom_mov + "', "
                            + "flg_tip =  " + flg_tip + ", "
                            + "det_abr = '" + det_abr + "' "
                            + "WHERE id_mov = " + id_mov + " "
                            + "and cod_pai = " + cod_pai + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Movimientos", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Movimientos", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar InvMovimientos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMovimientos();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        boolean mvalidar = true;
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_mov)) {
            addMessage("Eliminar Movimiento", "Debe elegir un Registro", 2);
            mvalidar = false;
        } else if (!"0".equals(mAccesos.strQuerySQLvariable("select count(tip_mov) as contador from inv_tbl_transacciones where tip_mov =" + id_mov + ";"))) {
            addMessage("Eliminar Movimiento", "Este Movimiento tiene registros relacionados en Control de Inventario y no puede ser eliminado", 2);
            mvalidar = false;
        }

        if (mvalidar) {
            try {
                mQuery = "delete from inv_cat_mov where id_mov=" + id_mov + " and cod_pai = " + cod_pai + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Movimiento", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Movimiento", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar InvMovimientos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMovimientos();
            nuevo();
        }
        mAccesos.Desconectar();

    }

    public void onRowSelect(SelectEvent event) {
        id_mov = ((CatMovimientosInv) event.getObject()).getId_mov();
        nom_mov = ((CatMovimientosInv) event.getObject()).getNom_mov();
        flg_tip = ((CatMovimientosInv) event.getObject()).getFlg_tip();
        cod_pai = ((CatMovimientosInv) event.getObject()).getCod_pai();
        det_abr = ((CatMovimientosInv) event.getObject()).getDet_abr();
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
