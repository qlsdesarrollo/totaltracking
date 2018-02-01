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
public class ManUserGroup implements Serializable {

    private static final long serialVersionUID = 87978674716638L;
    @Inject
    Login cbean;
    private CatUserGroup catusergroup;
    private List<CatUserGroup> usergroup;
    private CatGrupos catgrupos;
    private List<CatGrupos> grupos;
    private CatUsuarios catusuarios;
    private List<CatUsuarios> usuarios;
    private String id_usr_grp, id_grp, cod_usu;

    public ManUserGroup() {
    }

    public CatUserGroup getCatusergroup() {
        return catusergroup;
    }

    public void setCatusergroup(CatUserGroup catusergroup) {
        this.catusergroup = catusergroup;
    }

    public List<CatUserGroup> getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(List<CatUserGroup> usergroup) {
        this.usergroup = usergroup;
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

    public CatUsuarios getCatusuarios() {
        return catusuarios;
    }

    public void setCatusuarios(CatUsuarios catusuarios) {
        this.catusuarios = catusuarios;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public String getId_usr_grp() {
        return id_usr_grp;
    }

    public void setId_usr_grp(String id_usr_grp) {
        this.id_usr_grp = id_usr_grp;
    }

    public String getId_grp() {
        return id_grp;
    }

    public void setId_grp(String id_grp) {
        this.id_grp = id_grp;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public void iniciarventana() {
        id_usr_grp = "";
        id_grp = "";
        cod_usu = "";
        llenarGrupos();
        llenarUsuarios();
        //llenarUserGroup();
    }

    public void cerrarventana() {
        id_usr_grp = "";
        id_grp = "";
        cod_usu = "";
        usergroup = new ArrayList<>();
        usuarios = new ArrayList<>();
        grupos = new ArrayList<>();
    }

    public void llenarGrupos() {
        String mQuery = "";
        try {
            catgrupos = new CatGrupos();
            grupos = new ArrayList<>();

            mQuery = "select id_grp, des_grp from cat_grp order by des_grp;";
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

    public void llenarUsuarios() {
        try {
            catusuarios = new CatUsuarios();
            usuarios = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai order by cod_usu;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                usuarios.add(new CatUsuarios(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Usuarios. " + e.getMessage());
        }
    }

    public void llenarUserGroup() {
        String mQuery = "";
        try {
            catusergroup = new CatUserGroup();
            usergroup = new ArrayList<>();

            mQuery = "SELECT "
                    + "id_usr_grp, "
                    + "cat_usr_grp.id_grp, "
                    + "cat_usr_grp.cod_usu, "
                    + "cat_grp.des_grp, "
                    + "cat_usu.nom_usu "
                    + "FROM cat_usr_grp "
                    + "inner join cat_grp ON cat_grp.id_grp = cat_usr_grp.id_grp "
                    + "inner join cat_usu ON cat_usu.cod_usu = cat_usr_grp.cod_usu "
                    + "where "
                    + "cat_usr_grp.id_grp =" + id_grp + ";";

            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                usergroup.add(new CatUserGroup(
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
            System.out.println("Error en el llenado de Registros de Usuarios - Grupos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_usr_grp = "";
        id_grp = "";
        cod_usu = "";
        catusergroup = new CatUserGroup();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_usr_grp)) {
                    mQuery = "select ifnull(max(id_usr_grp),0)+1 as codigo from cat_usr_grp;";
                    id_usr_grp = mAccesos.strQuerySQLvariable(mQuery);

                    mQuery = "insert into cat_usr_grp (id_usr_grp, id_grp, cod_usu) "
                            + "values (" + id_usr_grp + ",'" + id_grp + "','" + cod_usu + "');";
                } else {
                    mQuery = "update cat_usr_grp SET "
                            + " id_grp = '" + id_grp + "', "
                            + " cod_usu = '" + cod_usu + "' "
                            + "WHERE id_usr_grp = " + id_usr_grp + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Asignacion Usuario-Grupo", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Asignacion Usuario-Grupo", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Asignacion Usuario - Grupo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarUserGroup();
        }
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_usr_grp) == false) {
            try {
                mQuery = "delete from cat_usr_grp where id_usr_grp=" + id_usr_grp + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Asignacion Usuario-Grupo", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Asignacion Usuario-Grupo", "Error al momento de Eliminar Asignación. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Asignacion Usuario - Grupo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarUserGroup();
            nuevo();
        } else {
            addMessage("Eliminar Asignacion Usuario-Grupo", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("0".equals(id_grp) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Grupo.", 2);
        }

        if ("0".equals(cod_usu) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Usuario.", 2);
        }

        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_usr_grp) from cat_usr_grp "
                + "where id_grp ='" + id_grp + "' AND cod_usu ='" + cod_usu + "';")) == false && "".equals(id_usr_grp)) {
            mValidar = false;
            addMessage("Validar Datos", "La asignacion ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_usr_grp = ((CatUserGroup) event.getObject()).getId_usr_grp();
        id_grp = ((CatUserGroup) event.getObject()).getId_grp();
        cod_usu = ((CatUserGroup) event.getObject()).getCod_usu();
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
