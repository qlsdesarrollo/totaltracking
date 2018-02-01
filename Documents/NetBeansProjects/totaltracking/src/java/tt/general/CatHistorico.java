package tt.general;

import java.io.Serializable;

public class CatHistorico implements Serializable {

    private String cod_his, cod_pie, fec_his, ord_dia, flg_ing, cod_enc,
            cod_det, det_can, det_cos, can_exi, cos_pro, det_sta, fec_mod, cod_usu;

    public CatHistorico() {
    }

    public CatHistorico(String cod_his, String cod_pie, String fec_his, String ord_dia, String flg_ing, String cod_enc, String cod_det, String det_can, String det_cos, String can_exi, String cos_pro, String det_sta, String fec_mod, String cod_usu) {
        this.cod_his = cod_his;
        this.cod_pie = cod_pie;
        this.fec_his = fec_his;
        this.ord_dia = ord_dia;
        this.flg_ing = flg_ing;
        this.cod_enc = cod_enc;
        this.cod_det = cod_det;
        this.det_can = det_can;
        this.det_cos = det_cos;
        this.can_exi = can_exi;
        this.cos_pro = cos_pro;
        this.det_sta = det_sta;
        this.fec_mod = fec_mod;
        this.cod_usu = cod_usu;
    }

    public String getCod_his() {
        return cod_his;
    }

    public void setCod_his(String cod_his) {
        this.cod_his = cod_his;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getFec_his() {
        return fec_his;
    }

    public void setFec_his(String fec_his) {
        this.fec_his = fec_his;
    }

    public String getOrd_dia() {
        return ord_dia;
    }

    public void setOrd_dia(String ord_dia) {
        this.ord_dia = ord_dia;
    }

    public String getFlg_ing() {
        return flg_ing;
    }

    public void setFlg_ing(String flg_ing) {
        this.flg_ing = flg_ing;
    }

    public String getCod_enc() {
        return cod_enc;
    }

    public void setCod_enc(String cod_enc) {
        this.cod_enc = cod_enc;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(String det_cos) {
        this.det_cos = det_cos;
    }

    public String getCan_exi() {
        return can_exi;
    }

    public void setCan_exi(String can_exi) {
        this.can_exi = can_exi;
    }

    public String getCos_pro() {
        return cos_pro;
    }

    public void setCos_pro(String cos_pro) {
        this.cos_pro = cos_pro;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getFec_mod() {
        return fec_mod;
    }

    public void setFec_mod(String fec_mod) {
        this.fec_mod = fec_mod;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

}
