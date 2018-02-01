package paquetes;

import java.io.Serializable;

public class CatPlantillasDetalle implements Serializable {

    private String cod_pla, flg_tip_det, det_pla, cod_ope, det_obs, det_can, cod_pie, des_ite, nomope, nompie;

    public CatPlantillasDetalle() {
    }

    public CatPlantillasDetalle(String cod_pla, String flg_tip_det, String det_pla, String cod_ope, String det_obs, String det_can, String cod_pie, String des_ite, String nomope, String nompie) {
        this.cod_pla = cod_pla;
        this.flg_tip_det = flg_tip_det;
        this.det_pla = det_pla;
        this.cod_ope = cod_ope;
        this.det_obs = det_obs;
        this.det_can = det_can;
        this.cod_pie = cod_pie;
        this.des_ite = des_ite;
        this.nomope = nomope;
        this.nompie = nompie;
    }

    public String getCod_pla() {
        return cod_pla;
    }

    public void setCod_pla(String cod_pla) {
        this.cod_pla = cod_pla;
    }

    public String getFlg_tip_det() {
        return flg_tip_det;
    }

    public void setFlg_tip_det(String flg_tip_det) {
        this.flg_tip_det = flg_tip_det;
    }

    public String getDet_pla() {
        return det_pla;
    }

    public void setDet_pla(String det_pla) {
        this.det_pla = det_pla;
    }

    public String getCod_ope() {
        return cod_ope;
    }

    public void setCod_ope(String cod_ope) {
        this.cod_ope = cod_ope;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getDes_ite() {
        return des_ite;
    }

    public void setDes_ite(String des_ite) {
        this.des_ite = des_ite;
    }

    public String getNomope() {
        return nomope;
    }

    public void setNomope(String nomope) {
        this.nomope = nomope;
    }

    public String getNompie() {
        return nompie;
    }

    public void setNompie(String nompie) {
        this.nompie = nompie;
    }

}
