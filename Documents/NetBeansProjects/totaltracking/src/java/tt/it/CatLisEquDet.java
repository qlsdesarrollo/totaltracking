package tt.it;

import java.io.Serializable;

public class CatLisEquDet implements Serializable {

    private String cod_lis_equ, cod_det, cod_pie, nom_ite, det_mar, det_mod, det_ser,
            det_sop, det_bit, fec_adq, fec_cam, flg_act, flg_sta, det_obs;

    public CatLisEquDet() {
    }

    public CatLisEquDet(String cod_lis_equ, String cod_det, String cod_pie, String nom_ite, String det_mar, String det_mod, String det_ser, String det_sop, String det_bit, String fec_adq, String fec_cam, String flg_act, String flg_sta, String det_obs) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_det = cod_det;
        this.cod_pie = cod_pie;
        this.nom_ite = nom_ite;
        this.det_mar = det_mar;
        this.det_mod = det_mod;
        this.det_ser = det_ser;
        this.det_sop = det_sop;
        this.det_bit = det_bit;
        this.fec_adq = fec_adq;
        this.fec_cam = fec_cam;
        this.flg_act = flg_act;
        this.flg_sta = flg_sta;
        this.det_obs = det_obs;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getNom_ite() {
        return nom_ite;
    }

    public void setNom_ite(String nom_ite) {
        this.nom_ite = nom_ite;
    }

    public String getDet_mar() {
        return det_mar;
    }

    public void setDet_mar(String det_mar) {
        this.det_mar = det_mar;
    }

    public String getDet_mod() {
        return det_mod;
    }

    public void setDet_mod(String det_mod) {
        this.det_mod = det_mod;
    }

    public String getDet_ser() {
        return det_ser;
    }

    public void setDet_ser(String det_ser) {
        this.det_ser = det_ser;
    }

    public String getDet_sop() {
        return det_sop;
    }

    public void setDet_sop(String det_sop) {
        this.det_sop = det_sop;
    }

    public String getDet_bit() {
        return det_bit;
    }

    public void setDet_bit(String det_bit) {
        this.det_bit = det_bit;
    }

    public String getFec_adq() {
        return fec_adq;
    }

    public void setFec_adq(String fec_adq) {
        this.fec_adq = fec_adq;
    }

    public String getFec_cam() {
        return fec_cam;
    }

    public void setFec_cam(String fec_cam) {
        this.fec_cam = fec_cam;
    }

    public String getFlg_act() {
        return flg_act;
    }

    public void setFlg_act(String flg_act) {
        this.flg_act = flg_act;
    }

    public String getFlg_sta() {
        return flg_sta;
    }

    public void setFlg_sta(String flg_sta) {
        this.flg_sta = flg_sta;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }
    
    

}
