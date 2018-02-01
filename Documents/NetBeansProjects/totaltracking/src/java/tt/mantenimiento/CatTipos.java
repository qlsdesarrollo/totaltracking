package tt.mantenimiento;

import java.io.Serializable;

public class CatTipos implements Serializable {

    private String cod_tip, nom_tip, flg_urg;

    public CatTipos() {
    }

    public CatTipos(String cod_tip, String nom_tip, String flg_urg) {
        this.cod_tip = cod_tip;
        this.nom_tip = nom_tip;
        this.flg_urg = flg_urg;
    }

    public String getCod_tip() {
        return cod_tip;
    }

    public void setCod_tip(String cod_tip) {
        this.cod_tip = cod_tip;
    }

    public String getNom_tip() {
        return nom_tip;
    }

    public void setNom_tip(String nom_tip) {
        this.nom_tip = nom_tip;
    }

    public String getFlg_urg() {
        return flg_urg;
    }

    public void setFlg_urg(String flg_urg) {
        this.flg_urg = flg_urg;
    }

}
