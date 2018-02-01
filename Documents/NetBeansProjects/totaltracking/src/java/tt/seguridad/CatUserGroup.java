
package tt.seguridad;

import java.io.Serializable;

public class CatUserGroup implements Serializable{
    
    private String id_usr_grp, id_grp, cod_usu, grupo, usuario;

    public CatUserGroup() {
    }

    public CatUserGroup(String id_usr_grp, String id_grp, String cod_usu, String grupo, String usuario) {
        this.id_usr_grp = id_usr_grp;
        this.id_grp = id_grp;
        this.cod_usu = cod_usu;
        this.grupo = grupo;
        this.usuario = usuario;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
