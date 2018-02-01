
package tt.seguridad;

import java.io.Serializable;

public class CatModulos implements Serializable{
    
    private String id_mod,des_mod;

    public CatModulos() {
    }

    public CatModulos(String id_mod, String des_mod) {
        this.id_mod = id_mod;
        this.des_mod = des_mod;
    }

    public String getId_mod() {
        return id_mod;
    }

    public void setId_mod(String id_mod) {
        this.id_mod = id_mod;
    }

    public String getDes_mod() {
        return des_mod;
    }

    public void setDes_mod(String des_mod) {
        this.des_mod = des_mod;
    }
    
    
}
