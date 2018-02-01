package tt.general;

import java.io.Serializable;

public class CatMovimientos implements Serializable {

    private String id_mov, nom_mov, flg_tip, cod_pai;

    public CatMovimientos() {
    }

    public CatMovimientos(String id_mov, String nom_mov, String flg_tip, String cod_pai) {
        this.id_mov = id_mov;
        this.nom_mov = nom_mov;
        this.flg_tip = flg_tip;
        this.cod_pai = cod_pai;
    }

    public String getId_mov() {
        return id_mov;
    }

    public void setId_mov(String id_mov) {
        this.id_mov = id_mov;
    }

    public String getNom_mov() {
        return nom_mov;
    }

    public void setNom_mov(String nom_mov) {
        this.nom_mov = nom_mov;
    }

    public String getFlg_tip() {
        return flg_tip;
    }

    public void setFlg_tip(String flg_tip) {
        this.flg_tip = flg_tip;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

}
