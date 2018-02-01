package paquetes;

import java.io.Serializable;

public class CatIngresos implements Serializable {

    private String id_ing, fec_ing, tip_mov, ord_com, doc_ent, cod_pro, nommov,det_obs;

    public CatIngresos() {
    }

    public CatIngresos(String id_ing, String fec_ing, String tip_mov, String ord_com, String doc_ent, String cod_pro, String nommov, String det_obs) {
        this.id_ing = id_ing;
        this.fec_ing = fec_ing;
        this.tip_mov = tip_mov;
        this.ord_com = ord_com;
        this.doc_ent = doc_ent;
        this.cod_pro = cod_pro;
        this.nommov = nommov;
        this.det_obs = det_obs;
    }

    public String getId_ing() {
        return id_ing;
    }

    public void setId_ing(String id_ing) {
        this.id_ing = id_ing;
    }

    public String getFec_ing() {
        return fec_ing;
    }

    public void setFec_ing(String fec_ing) {
        this.fec_ing = fec_ing;
    }

    public String getTip_mov() {
        return tip_mov;
    }

    public void setTip_mov(String tip_mov) {
        this.tip_mov = tip_mov;
    }

    public String getOrd_com() {
        return ord_com;
    }

    public void setOrd_com(String ord_com) {
        this.ord_com = ord_com;
    }

    public String getDoc_ent() {
        return doc_ent;
    }

    public void setDoc_ent(String doc_ent) {
        this.doc_ent = doc_ent;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getNommov() {
        return nommov;
    }

    public void setNommov(String nommov) {
        this.nommov = nommov;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

}
