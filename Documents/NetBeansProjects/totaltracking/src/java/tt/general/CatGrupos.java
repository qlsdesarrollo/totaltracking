
package tt.general;

import java.io.Serializable;

public class CatGrupos implements Serializable{
    
    private String id_grp, des_grp;

    public CatGrupos() {
    }

    public CatGrupos(String id_grp, String des_grp) {
        this.id_grp = id_grp;
        this.des_grp = des_grp;
    }

    public String getId_grp() {
        return id_grp;
    }

    public void setId_grp(String id_grp) {
        this.id_grp = id_grp;
    }

    public String getDes_grp() {
        return des_grp;
    }

    public void setDes_grp(String des_grp) {
        this.des_grp = des_grp;
    }
    
    
}
