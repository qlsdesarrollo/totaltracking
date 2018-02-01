package tt.mantenimiento;

import java.io.Serializable;

public class CatTblPiezas implements Serializable {

    private String cod_lis_pie, por_qbo, cod_pai, cod_pro, cod_mov, doc_tra, cod_sol,
            fec_tra, det_obs, flg_ing, det_sta, nompai, nompro, nommov, detsta;

    public CatTblPiezas() {
    }

    public CatTblPiezas(String cod_lis_pie, String por_qbo, String cod_pai, String cod_pro, String cod_mov, String doc_tra, String cod_sol, String fec_tra, String det_obs, String flg_ing, String det_sta, String nompai, String nompro, String nommov, String detsta) {
        this.cod_lis_pie = cod_lis_pie;
        this.por_qbo = por_qbo;
        this.cod_pai = cod_pai;
        this.cod_pro = cod_pro;
        this.cod_mov = cod_mov;
        this.doc_tra = doc_tra;
        this.cod_sol = cod_sol;
        this.fec_tra = fec_tra;
        this.det_obs = det_obs;
        this.flg_ing = flg_ing;
        this.det_sta = det_sta;
        this.nompai = nompai;
        this.nompro = nompro;
        this.nommov = nommov;
        this.detsta = detsta;
    }

    public String getCod_lis_pie() {
        return cod_lis_pie;
    }

    public void setCod_lis_pie(String cod_lis_pie) {
        this.cod_lis_pie = cod_lis_pie;
    }

    public String getPor_qbo() {
        return por_qbo;
    }

    public void setPor_qbo(String por_qbo) {
        this.por_qbo = por_qbo;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_mov() {
        return cod_mov;
    }

    public void setCod_mov(String cod_mov) {
        this.cod_mov = cod_mov;
    }

    public String getDoc_tra() {
        return doc_tra;
    }

    public void setDoc_tra(String doc_tra) {
        this.doc_tra = doc_tra;
    }

    public String getCod_sol() {
        return cod_sol;
    }

    public void setCod_sol(String cod_sol) {
        this.cod_sol = cod_sol;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFlg_ing() {
        return flg_ing;
    }

    public void setFlg_ing(String flg_ing) {
        this.flg_ing = flg_ing;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNompro() {
        return nompro;
    }

    public void setNompro(String nompro) {
        this.nompro = nompro;
    }

    public String getNommov() {
        return nommov;
    }

    public void setNommov(String nommov) {
        this.nommov = nommov;
    }

    public String getDetsta() {
        return detsta;
    }

    public void setDetsta(String detsta) {
        this.detsta = detsta;
    }

}
