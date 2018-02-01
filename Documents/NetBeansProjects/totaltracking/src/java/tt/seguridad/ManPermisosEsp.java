package tt.seguridad;

import tt.mantenimiento.CatPermisosEsp;
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
public class ManPermisosEsp implements Serializable {

    private static final long serialVersionUID = 8796874198435638L;
    @Inject
    Login cbean;
    private CatPermisosEsp catpermisosesp;
    private List<CatPermisosEsp> permisos;
    private List<CatGrupos> grupos;
    private String cod_rel, det_per, cod_gru;

    public ManPermisosEsp() {
    }

    public CatPermisosEsp getCatpermisosesp() {
        return catpermisosesp;
    }

    public void setCatpermisosesp(CatPermisosEsp catpermisosesp) {
        this.catpermisosesp = catpermisosesp;
    }

    public List<CatPermisosEsp> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<CatPermisosEsp> permisos) {
        this.permisos = permisos;
    }

    public List<CatGrupos> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<CatGrupos> grupos) {
        this.grupos = grupos;
    }

    public String getCod_rel() {
        return cod_rel;
    }

    public void setCod_rel(String cod_rel) {
        this.cod_rel = cod_rel;
    }

    public String getDet_per() {
        return det_per;
    }

    public void setDet_per(String det_per) {
        this.det_per = det_per;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public void iniciarventana() {
        cod_rel = "";
        det_per = "0";
        cod_gru = "0";
        llenarGrupos();
    }

    public void cerrarventana() {
        cod_rel = "";
        det_per = "0";
        cod_gru = "0";
        grupos = new ArrayList<>();
        permisos = new ArrayList<>();
    }

    public void llenarGrupos() {
        String mQuery = "";
        try {
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
            System.out.println("Error en el llenado de Grupos de ManPermisosEsp. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPermisos() {
        String mQuery = "";
        try {
            permisos = new ArrayList<>();

            mQuery = "select "
                    + "per.cod_rel, "
                    + "per.det_per, "
                    + "per.cod_gru, "
                    + "gru.des_grp "
                    + "from tbl_permisos_esp as per "
                    + "left join cat_grp as gru on per.cod_gru = gru.id_grp "
                    + "where per.cod_gru =" + cod_gru + " "
                    + "order by per.cod_rel;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                permisos.add(new CatPermisosEsp(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Permisos de ManPermisosEsp. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        cod_rel = "";
        det_per = "0";
        cod_gru = "0";
        permisos = new ArrayList<>();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(cod_rel)) {
                    mQuery = "select ifnull(max(cod_rel),0)+1 as codigo from tbl_permisos_esp;";
                    cod_rel = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into tbl_permisos_esp (cod_rel,det_per,cod_gru) "
                            + "values (" + cod_rel + ",'" + det_per + "'," + cod_gru + ");";
                } else {
                    mQuery = "update tbl_permisos_esp SET "
                            + "det_per = '" + det_per + "', "
                            + "cod_gru = " + cod_gru + " "
                            + "WHERE cod_rel = " + cod_rel + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Permiso Específico", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Permiso Específico", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Permiso Específico. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarGrupos();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(cod_rel) == false) {
            try {
                mQuery = "delete from tbl_permisos_esp where cod_rel=" + cod_rel + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Permiso Específico", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Permiso Específico", "Error al momento de Eliminar Permiso Específico, tiene usuarios dependientes. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Permiso Específico. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarGrupos();
            nuevo();
        } else {
            addMessage("Eliminar Permiso Específico", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("0".equals(det_per)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Permiso Específico.", 2);
        }
        if ("0".equals(cod_gru) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Grupo.", 2);
        }
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if (!"0".equals(mAccesos.strQuerySQLvariable("select count(cod_rel) from tbl_permisos_esp where cod_gru=" + cod_gru + " and det_per ='" + det_per + "';"))) {
            mValidar = false;
            addMessage("Validar Datos", "El permiso Específico Seleccionado ya ha sido asignado al Grupo.", 2);
        }
        mAccesos.Desconectar();

        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        cod_rel = ((CatPermisosEsp) event.getObject()).getCod_rel();
        det_per = ((CatPermisosEsp) event.getObject()).getDet_per();
        cod_gru = ((CatPermisosEsp) event.getObject()).getCod_gru();
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
