
package tt.seguridad;

import java.io.Serializable;

public class CatControles implements Serializable{
    
    private String cod_ctrl, sid_ctrl, ctrl_desc, id_mod, modulo;

    public CatControles() {
    }

    public CatControles(String cod_ctrl, String sid_ctrl, String ctrl_desc, String id_mod, String modulo) {
        this.cod_ctrl = cod_ctrl;
        this.sid_ctrl = sid_ctrl;
        this.ctrl_desc = ctrl_desc;
        this.id_mod = id_mod;
        this.modulo = modulo;
    }

    public String getCod_ctrl() {
        return cod_ctrl;
    }

    public void setCod_ctrl(String cod_ctrl) {
        this.cod_ctrl = cod_ctrl;
    }

    public String getSid_ctrl() {
        return sid_ctrl;
    }

    public void setSid_ctrl(String sid_ctrl) {
        this.sid_ctrl = sid_ctrl;
    }

    public String getCtrl_desc() {
        return ctrl_desc;
    }

    public void setCtrl_desc(String ctrl_desc) {
        this.ctrl_desc = ctrl_desc;
    }

    public String getId_mod() {
        return id_mod;
    }

    public void setId_mod(String id_mod) {
        this.id_mod = id_mod;
    }   

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

   
}
