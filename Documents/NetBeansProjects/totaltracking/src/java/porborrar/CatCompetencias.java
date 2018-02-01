
package paquetes;

import java.io.Serializable;

public class CatCompetencias implements Serializable{
    
    private String id_com, des_com;

    public CatCompetencias() {
    }

    public CatCompetencias(String id_com, String des_com) {
        this.id_com = id_com;
        this.des_com = des_com;
    }

    public String getId_com() {
        return id_com;
    }

    public void setId_com(String id_com) {
        this.id_com = id_com;
    }

    public String getDes_com() {
        return des_com;
    }

    public void setDes_com(String des_com) {
        this.des_com = des_com;
    }
   
}
