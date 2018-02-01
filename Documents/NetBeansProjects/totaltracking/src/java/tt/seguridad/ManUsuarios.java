package tt.seguridad;

import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import tt.general.Accesos;
import tt.general.CatDepartamentos;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped
public class ManUsuarios implements Serializable {

    private static final long serialVersionUID = 87996476741638L;
    @Inject
    Login mbMain;
    private CatUsuarios catusuarios;
    private List<CatUsuarios> usuarios;
    private CatDepartamentos catdepartamentos;
    private List<CatDepartamentos> departamentos;
    private CatPaises catpaises;
    private List<CatPaises> paises;

    private String cod_usu, nom_usu, des_pas, tip_usu, cod_pai, cod_dep, det_nom, det_mai;

    public ManUsuarios() {
    }

    public void iniciarventana() {
        cod_usu = "";
        nom_usu = "";
        des_pas = "";
        tip_usu = "";
        cod_pai = "";
        cod_dep = "";
        det_nom = "";
        det_mai = "";
        llenarUsuarios();
        llenarDepartamentos();
        llenarPaises();
        if (Integer.valueOf(mbMain.getPerfil()) < 5) {
            RequestContext.getCurrentInstance().execute("PF('wUsuarios').hide()");
            addMessage("Error de Validación", "Usuario sin Privilegios de Administración.", 2);
        }
    }

    public void cerrarventana() {
        cod_usu = "";
        nom_usu = "";
        des_pas = "";
        tip_usu = "";
        cod_pai = "";
        cod_dep = "";
        det_nom = "";
        det_mai = "";
        usuarios = new ArrayList<>();
    }

    public void llenarDepartamentos() {
        try {
            departamentos = new ArrayList<>();

            String mQuery = "select cod_dep,cod_pai,nom_dep "
                    + "from cat_dep order by cod_dep;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                departamentos.add(new CatDepartamentos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Departamentos en Catálogo de Usuarios. " + e.getMessage());
        }
    }

    public void llenarPaises() {
        try {
            paises = new ArrayList<>();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
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
            System.out.println("Error en el llenado Paises en Catálogo de Usuarios. " + e.getMessage());
        }
    }

    public void llenarUsuarios() {
        try {
            catusuarios = new CatUsuarios();
            usuarios = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep "
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

    public void nuevo() {
        cod_usu = "";
        nom_usu = "";
        des_pas = "";
        tip_usu = "";
        cod_pai = "";
        cod_dep = "";
        det_nom = "";
        det_mai = "";
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_usu)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre de Usuario.", 2);
        }
        if ("".equals(des_pas)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar una Clave.", 2);
        }
        if ("1".equals(cod_usu)) {
            nom_usu = "ADMIN";
        } else if ("ADMIN".equals(nom_usu.toUpperCase())) {
            mValidar = false;
            addMessage("Validar Datos", "Este nombre de Usuario está reservado para el Administrador.", 2);
        }

        return mValidar;
    }

    public void guardar() {
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                String mQuery;
                if ("".equals(cod_usu)) {
                    mQuery = "select ifnull(max(cod_usu),0)+1 as codigo from cat_usu;";
                    cod_usu = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_usu (cod_usu, nom_usu, des_pas, tip_usu, cod_pai, cod_dep, det_nom,det_mai) "
                            + "values (" + cod_usu + ",'" + nom_usu + "','" + md5(des_pas) + "'," + tip_usu
                            + "," + cod_pai + "," + cod_dep + ",'" + det_nom + "','" + det_mai + "');";
                } else {
                    mQuery = "update cat_usu SET "
                            + " nom_usu = '" + nom_usu + "' "
                            + ",des_pas = '" + md5(des_pas) + "' "
                            + ",tip_usu = '" + tip_usu + "' "
                            + ",cod_pai = " + cod_pai + " "
                            + ",cod_dep = " + cod_dep + " "
                            + ",det_nom = '" + det_nom + "' "
                            + ",det_mai = '" + det_mai + "' "
                            + "WHERE cod_usu = " + cod_usu + ";";
                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Usuarios", "Información Almacenada con Éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Usuarios", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Usuarios. " + e.getMessage());
            }
            llenarUsuarios();
            nuevo();
        }

    }

    public void eliminar() {

        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();

        if ("1".equals(cod_usu) == false) {
            if ("".equals(cod_usu) == false) {
                try {
                    String mQuery = "delete from cat_usu where cod_usu=" + cod_usu + ";";
                    mAccesos.dmlSQLvariable(mQuery);
                    addMessage("Eliminar Usuarios", "Información Eliminada con Éxito.", 1);
                } catch (Exception e) {
                    addMessage("Eliminar Usuarios", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                    System.out.println("Error al Eliminar Usuarios. " + e.getMessage());
                }
                llenarUsuarios();
            } else {
                addMessage("Eliminar Usuarios", "Debe elegir un Registro.", 2);
            }
        } else {
            addMessage("Eliminar Usuarios", "El Usuario ADMIN no puede ser Eliminado.", 2);
        }

        mAccesos.Desconectar();
        nuevo();

    }

    public void onRowSelect(SelectEvent event) {
        cod_usu = ((CatUsuarios) event.getObject()).getCod_usu();
        nom_usu = ((CatUsuarios) event.getObject()).getNom_usu();
        //des_pas = ((CatUsuarios) event.getObject()).getDes_pas();
        des_pas = "";
        tip_usu = ((CatUsuarios) event.getObject()).getTip_usu();
        cod_pai = ((CatUsuarios) event.getObject()).getCod_pai();
        cod_dep = ((CatUsuarios) event.getObject()).getCod_dep();
        det_nom = ((CatUsuarios) event.getObject()).getDet_nom();
        det_mai = ((CatUsuarios) event.getObject()).getDet_mai();
    }

    private static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());

        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0").append(Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
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

    public CatPaises getCatpaises() {
        return catpaises;
    }

    public void setCatpaises(CatPaises catpaises) {
        this.catpaises = catpaises;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

    public String getDes_pas() {
        return des_pas;
    }

    public void setDes_pas(String des_pas) {
        this.des_pas = des_pas;
    }

    public String getTip_usu() {
        return tip_usu;
    }

    public void setTip_usu(String tip_usu) {
        this.tip_usu = tip_usu;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getDet_mai() {
        return det_mai;
    }

    public void setDet_mai(String det_mai) {
        this.det_mai = det_mai;
    }
}
