package tt.mantenimiento;

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
import tt.general.CatDepartamentos;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped
public class ManDepartamentos implements Serializable {

    private static final long serialVersionUID = 87979476741638L;
    @Inject
    Login cbean;
    private CatDepartamentos catdepartamentos;
    private List<CatDepartamentos> departamentos;
    private List<CatPaises> paises;

    private String cod_dep, cod_pai, nom_dep;

    private Accesos macc;

    public ManDepartamentos() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_dep = "";
        cod_pai = cbean.getCod_pai();
        nom_dep = "";

        paises = new ArrayList<>();

        catdepartamentos = new CatDepartamentos();
        departamentos = new ArrayList<>();

        llenarPaises();
        llenarDepartamentos();

    }

    public void nuevo() {
        cod_dep = "";
        cod_pai = cbean.getCod_pai();
        nom_dep = "";

        catdepartamentos = null;
    }

    public void cerrarventana() {
        cod_dep = "";
        cod_pai = "";
        nom_dep = "";

        paises = null;

        catdepartamentos = null;
        departamentos = null;

        macc = null;
    }

    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Paises en Catálogo de Departamentos. " + e.getMessage());
        }
    }

    public void llenarDepartamentos() {
        try {
            catdepartamentos = null;
            departamentos.clear();

            String mQuery = "select cod_dep, cod_pai, nom_dep "
                    + "from cat_dep where cod_pai = " + cod_pai + " order by cod_dep;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                departamentos.add(new CatDepartamentos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Departamentos. " + e.getMessage());
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_dep)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre de Departamento.", 2);
        }

        return mValidar;
    }

    public void guardar() {
        if (validardatos()) {
            try {

                macc.Conectar();
                String mQuery;
                if ("".equals(cod_dep)) {
                    mQuery = "select ifnull(max(cod_dep),0)+1 as codigo from cat_dep;";
                    cod_dep = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_dep (cod_dep, cod_pai, nom_dep ) "
                            + "values (" + cod_dep + "," + cod_pai + ",'" + nom_dep + "');";
                } else {
                    mQuery = "update cat_dep SET "
                            + " nom_dep = '" + nom_dep + "' "
                            + "WHERE cod_dep = " + cod_dep + " "
                            + "and cod_pai = " + cod_pai + ";";
                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Guardar Departamentos", "Información Almacenada con Éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Departamentos", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Departamento. " + e.getMessage());
            }
            llenarDepartamentos();
            nuevo();
        }

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_dep) || "0".equals(cod_dep)) {
            addMessage("Eliminar Departamentos", "Debe seleccionar un registro", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_dep) from cat_usu where cod_dep = " + cod_dep + ";").equals("0")) {
            mvalidar = false;
            addMessage("Eliminar Departamentos", "Este Departamento tiene usuarios asociados", 2);
        }

        macc.Desconectar();
        return mvalidar;
    }

    public void eliminar() {

        if (validareliminar()) {
            try {
                macc.Conectar();
                String mQuery = "delete from cat_dep where cod_dep=" + cod_dep + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Eliminar Departamentos", "Información Eliminada con Éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Departamentos", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Departamentos. " + e.getMessage());
            }
            llenarDepartamentos();
            nuevo();
        }

    }

    public void onRowSelect(SelectEvent event) {
        cod_dep = ((CatDepartamentos) event.getObject()).getCod_dep();
        cod_pai = ((CatDepartamentos) event.getObject()).getCod_pai();
        nom_dep = ((CatDepartamentos) event.getObject()).getNom_dep();

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

    public CatDepartamentos getCatdepartamentos() {
        return catdepartamentos;
    }

    public void setCatdepartamentos(CatDepartamentos catdepartamentos) {
        this.catdepartamentos = catdepartamentos;
    }

    public List<CatDepartamentos> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<CatDepartamentos> departamentos) {
        this.departamentos = departamentos;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_dep() {
        return nom_dep;
    }

    public void setNom_dep(String nom_dep) {
        this.nom_dep = nom_dep;
    }

}
