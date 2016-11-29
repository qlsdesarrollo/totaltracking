package p_03_autenticar;
import p_02_transactsql.Querys;

import java.io.Serializable;
import java.security.MessageDigest;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ConversationScoped

public class Login implements Serializable {

    private static final long serialVersionUID = -6541718762358561835L;
    @Inject
    private Conversation conversation;
    private String cod_usu, nom_usu, des_pas, tip_usu, cod_pai, cod_dep, det_nom, nompai, nomdep, perfil;

    public Login() {
    }

    @PostConstruct
    public void initialize() {
        conversation.setTimeout(3600000);
        addMessage("Bienvenido", "Introduzca su Usuario y Contraseña", 1);
    }

    public String validausuario() {
        try {
            Querys consulta = new Querys();
            consulta.Conectar();

            System.out.println(md5(des_pas));

            String validar = consulta.strQuerySQLvariable("select count(cod_usu) as usu "
                    + "from cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + des_pas + "';");

            if ("1".equals(validar)) {
                cod_usu = consulta.strQuerySQLvariable("select cod_usu "
                        + "from cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + des_pas + "';");
                det_nom = consulta.strQuerySQLvariable("select det_nom "
                        + "from cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + des_pas + "';");
                cod_pai = consulta.strQuerySQLvariable("select cod_pai "
                        + "from cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + des_pas + "';");
                nompai = consulta.strQuerySQLvariable("select nom_pai "
                        + "from cat_pai where cod_pai=" + cod_pai + ";");
                cod_dep = consulta.strQuerySQLvariable("select cod_dep "
                        + "from cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + des_pas + "';");
                nomdep = consulta.strQuerySQLvariable("select nom_dep "
                        + "from cat_dep where cod_dep=" + cod_dep + ";");
                perfil = consulta.strQuerySQLvariable("select tip_usu "
                        + "from cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + des_pas + "';");

                addMessage("Bienvenido", "Validación Satisfactoria", 1);
                return "antsolution?faces-redirect=true";

            } else {
                addMessage("Fallo en Login", "Nombre de Usuario o Clave Incorrecta", 3);
                RequestContext.getCurrentInstance().update("frmLogin");
            }

            consulta.Desconectar();

        } catch (Exception e) {
            return "index?faces-redirect=true";
        }
        return null;
    }

    private static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());

        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }

    public void redireccionarlogin() {
        try {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/antsolution/faces/login.xhtml");

        } catch (Exception e) {
            System.out.println("Error en redireccionar a login " + e.getMessage());
        }
    }

    public void redireccionarlogout() {
        try {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/antsolution/faces/timeout.xhtml");

        } catch (Exception e) {
            System.out.println("Error en redireccionar a login " + e.getMessage());
        }
    }

    public void initConversation() {
        if (!FacesContext.getCurrentInstance().isPostback()
                && conversation.isTransient()) {
            conversation.begin();
        }

    }

    public String endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "index?faces-redirect=true";

    }

    public void unloadbody() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public void addMessage(String summary, String detail, int tipo) {
        FacesMessage message = new FacesMessage();
        switch (tipo) {
            case 1:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
                break;
            case 2:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
                break;
            case 3:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
                break;
            case 4:
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
                break;
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNomdep() {
        return nomdep;
    }

    public void setNomdep(String nomdep) {
        this.nomdep = nomdep;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

}
