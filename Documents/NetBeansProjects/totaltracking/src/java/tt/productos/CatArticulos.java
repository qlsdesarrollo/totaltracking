package tt.productos;

import java.io.Serializable;

public class CatArticulos implements Serializable {

    private String id_art, cod_art, det_mar, det_fam, det_nom, det_des, det_lot, fec_ven, flg_ref, tem_ref, img_pro, det_emb, reg_san, nomemb, pai_ori, cod_pai, cod_alt;

    public CatArticulos() {
    }

    public CatArticulos(String id_art, String cod_art, String det_mar, String det_fam, String det_nom, String det_des, String det_lot, String fec_ven, String flg_ref, String tem_ref, String img_pro, String det_emb, String reg_san, String nomemb, String pai_ori, String cod_pai, String cod_alt) {
        this.id_art = id_art;
        this.cod_art = cod_art;
        this.det_mar = det_mar;
        this.det_fam = det_fam;
        this.det_nom = det_nom;
        this.det_des = det_des;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.flg_ref = flg_ref;
        this.tem_ref = tem_ref;
        this.img_pro = img_pro;
        this.det_emb = det_emb;
        this.reg_san = reg_san;
        this.nomemb = nomemb;
        this.pai_ori = pai_ori;
        this.cod_pai = cod_pai;
        this.cod_alt = cod_alt;
    }

    public String getId_art() {
        return id_art;
    }

    public void setId_art(String id_art) {
        this.id_art = id_art;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getDet_mar() {
        return det_mar;
    }

    public void setDet_mar(String det_mar) {
        this.det_mar = det_mar;
    }

    public String getDet_fam() {
        return det_fam;
    }

    public void setDet_fam(String det_fam) {
        this.det_fam = det_fam;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getDet_des() {
        return det_des;
    }

    public void setDet_des(String det_des) {
        this.det_des = det_des;
    }

    public String getDet_lot() {
        return det_lot;
    }

    public void setDet_lot(String det_lot) {
        this.det_lot = det_lot;
    }

    public String getFec_ven() {
        return fec_ven;
    }

    public void setFec_ven(String fec_ven) {
        this.fec_ven = fec_ven;
    }

    public String getFlg_ref() {
        return flg_ref;
    }

    public void setFlg_ref(String flg_ref) {
        this.flg_ref = flg_ref;
    }

    public String getTem_ref() {
        return tem_ref;
    }

    public void setTem_ref(String tem_ref) {
        this.tem_ref = tem_ref;
    }

    public String getImg_pro() {
        return img_pro;
    }

    public void setImg_pro(String img_pro) {
        this.img_pro = img_pro;
    }

    public String getDet_emb() {
        return det_emb;
    }

    public void setDet_emb(String det_emb) {
        this.det_emb = det_emb;
    }

    public String getReg_san() {
        return reg_san;
    }

    public void setReg_san(String reg_san) {
        this.reg_san = reg_san;
    }

    public String getNomemb() {
        return nomemb;
    }

    public void setNomemb(String nomemb) {
        this.nomemb = nomemb;
    }

    public String getPai_ori() {
        return pai_ori;
    }

    public void setPai_ori(String pai_ori) {
        this.pai_ori = pai_ori;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

}
