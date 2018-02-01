
package tt.seguridad;

import java.io.Serializable;

public class CatSeguridad implements Serializable{
    
    private String cod_sec, cod_ctrl, id_grp, cod_usu, control, activo, grupo, usuario, modulo;
    
    public CatSeguridad() {
    }

    public CatSeguridad(String cod_sec, String modulo, String cod_ctrl, String control, String id_grp, String grupo, String cod_usu,  String usuario, String activo) {
        this.cod_sec = cod_sec;
        this.cod_ctrl = cod_ctrl;
        this.id_grp = id_grp;
        this.cod_usu = cod_usu;
        this.activo = activo;
        this.control = control;
        this.grupo = grupo;
        this.usuario = usuario;
        this.modulo = modulo;
    }

    public String getCod_sec() {
        return cod_sec;
    }

    public void setCod_sec(String cod_sec) {
        this.cod_sec = cod_sec;
    }

    public String getCod_ctrl() {
        return cod_ctrl;
    }

    public void setCod_ctrl(String cod_ctrl) {
        this.cod_ctrl = cod_ctrl;
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    
    
    
}
