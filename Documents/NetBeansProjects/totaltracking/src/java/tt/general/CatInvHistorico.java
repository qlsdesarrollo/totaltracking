package tt.general;

import java.io.Serializable;

public class CatInvHistorico implements Serializable {

    private String cod_tra, flg_ing_sal, fec_tra, ord_dia, cod_mov, det_mov, cod_pai, cod_bod, cod_ubi, cod_art, det_can, exi_ant_bod, exi_act_bod, cos_uni, cos_pro;

    public CatInvHistorico() {
    }

    public CatInvHistorico(String cod_tra, String flg_ing_sal, String fec_tra, String ord_dia, String cod_mov, String det_mov, String cod_pai, String cod_bod, String cod_ubi, String cod_art, String det_can, String exi_ant_bod, String exi_act_bod, String cos_uni, String cos_pro) {
        this.cod_tra = cod_tra;
        this.flg_ing_sal = flg_ing_sal;
        this.fec_tra = fec_tra;
        this.ord_dia = ord_dia;
        this.cod_mov = cod_mov;
        this.det_mov = det_mov;
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.cod_art = cod_art;
        this.det_can = det_can;
        this.exi_ant_bod = exi_ant_bod;
        this.exi_act_bod = exi_act_bod;
        this.cos_uni = cos_uni;
        this.cos_pro = cos_pro;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getFlg_ing_sal() {
        return flg_ing_sal;
    }

    public void setFlg_ing_sal(String flg_ing_sal) {
        this.flg_ing_sal = flg_ing_sal;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getOrd_dia() {
        return ord_dia;
    }

    public void setOrd_dia(String ord_dia) {
        this.ord_dia = ord_dia;
    }

    public String getCod_mov() {
        return cod_mov;
    }

    public void setCod_mov(String cod_mov) {
        this.cod_mov = cod_mov;
    }

    public String getDet_mov() {
        return det_mov;
    }

    public void setDet_mov(String det_mov) {
        this.det_mov = det_mov;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getCod_ubi() {
        return cod_ubi;
    }

    public void setCod_ubi(String cod_ubi) {
        this.cod_ubi = cod_ubi;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getExi_ant_bod() {
        return exi_ant_bod;
    }

    public void setExi_ant_bod(String exi_ant_bod) {
        this.exi_ant_bod = exi_ant_bod;
    }

    public String getExi_act_bod() {
        return exi_act_bod;
    }

    public void setExi_act_bod(String exi_act_bod) {
        this.exi_act_bod = exi_act_bod;
    }

    public String getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(String cos_uni) {
        this.cos_uni = cos_uni;
    }

    public String getCos_pro() {
        return cos_pro;
    }

    public void setCos_pro(String cos_pro) {
        this.cos_pro = cos_pro;
    }

}
