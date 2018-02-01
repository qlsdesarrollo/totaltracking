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
import tt.seguridad.CatUsuarios;
import tt.general.Login;
import tt.mantenimiento.CatListaEspecifica;

@Named
@ConversationScoped
public class InvListaEspecifica implements Serializable {

    private static final long serialVersionUID = 74697849863578L;
    @Inject
    Login mbMain;
    private CatListaEspecifica catlistaespecifica;
    private List<CatListaEspecifica> lista;
    private List<CatUsuarios> usuarios;

    private String cod_lis, cod_usu;

    private Accesos macc;

    public InvListaEspecifica() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_lis = "0";
        cod_usu = "0";

        usuarios = new ArrayList<>();

        catlistaespecifica = new CatListaEspecifica();
        lista = new ArrayList<>();

        llenarUsuarios();
    }

    public void nuevo() {
        cod_lis = "0";
        cod_usu = "0";
        lista = null;
    }

    public void cerrarventana() {
        cod_lis = null;
        cod_usu = null;

        usuarios = null;

        catlistaespecifica = null;
        lista = null;

        macc = null;
    }

    public void llenarUsuarios() {
        try {
            usuarios.clear();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai "
                    + "where usu.cod_pai = " + mbMain.getCod_pai() + " "
                    + "order by cod_usu;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Usuarios InvListaEspecifica. " + e.getMessage());
        }
    }

    public void llenarLista() {
        try {
            catlistaespecifica = null;
            lista.clear();

            String mQuery = "select lis.flg_act_des, lis.cod_usu, usu.det_nom "
                    + "from inv_cat_usu_esp as lis "
                    + "left join cat_usu as usu on lis.cod_usu = usu.cod_usu "
                    + "where lis.flg_act_des = " + cod_lis + " "
                    + "and usu.cod_pai = " + mbMain.getCod_pai() + " "
                    + ";";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lista.add(new CatListaEspecifica(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Lista Específica InvListaEspecifica. " + e.getMessage());
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("0".equals(cod_lis)) {
            mValidar = false;
            addMessage("Guardar", "Debe seleccionar una Lista", 2);
        }
        if ("0".equals(cod_usu)) {
            mValidar = false;
            addMessage("Guardar", "Debe Seleccionar un Usuario", 2);
        }

        macc.Conectar();
        if (!"0".equals(macc.strQuerySQLvariable("select count(cod_usu) from inv_cat_usu_esp where flg_act_des=" + cod_lis + " and cod_usu=" + cod_usu + ";"))) {
            mValidar = false;
            addMessage("Guardar", "Este usuario ya existe", 2);
        }
        macc.Desconectar();
        return mValidar;
    }

    public void guardar() {
        if (validardatos()) {
            try {

                macc.Conectar();
                String mQuery;

                mQuery = "insert into inv_cat_usu_esp (cod_usu, flg_act_des) "
                        + "values (" + cod_usu + "," + cod_lis + ");";

                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error Mientras guardaba la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Lista específica InvListaEspecifica. " + e.getMessage());
            }
            //nuevo();
            llenarLista();
            cod_usu = "0";
        }

    }

    public void eliminar() {

        macc.Conectar();

        if (!"0".equals(cod_lis) && !"0".equals(cod_usu)) {
            try {
                String mQuery = "delete from inv_cat_usu_esp where flg_act_des=" + cod_lis + " and cod_usu=" + cod_usu + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con éxito", 1);
            } catch (Exception e) {
                addMessage("Eliminar", "Error mientras eliminaba la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Lista InvListaEspecifica. " + e.getMessage());
            }
        } else {
            addMessage("Eliminar", "Debe seleccionar un registro", 2);
        }

        macc.Desconectar();
        //nuevo();
        llenarLista();
        cod_usu = "0";

    }

    public void onRowSelect(SelectEvent event) {
        cod_lis = ((CatListaEspecifica) event.getObject()).getCod_lis();
        cod_usu = ((CatListaEspecifica) event.getObject()).getCod_usu();
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

    public CatListaEspecifica getCatlistaespecifica() {
        return catlistaespecifica;
    }

    public void setCatlistaespecifica(CatListaEspecifica catlistaespecifica) {
        this.catlistaespecifica = catlistaespecifica;
    }

    public List<CatListaEspecifica> getLista() {
        return lista;
    }

    public void setLista(List<CatListaEspecifica> lista) {
        this.lista = lista;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public String getCod_lis() {
        return cod_lis;
    }

    public void setCod_lis(String cod_lis) {
        this.cod_lis = cod_lis;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

}
