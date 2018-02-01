package tt.productos;

import java.io.Serializable;

public class CatPedidos implements Serializable {

    private String cod_ped, cod_pai, fec_ped, flg_ing_sal, tip_mov, doc_tra, ord_com,
            cod_cli_pro, det_obs, cod_usu, flg_anu, cod_esp, cor_mov, cod_are, obs_anu, det_sta, nomusu, nomcli, flg_val, valorflg;

    public CatPedidos() {
    }

    public CatPedidos(String cod_ped, String cod_pai, String fec_ped, String flg_ing_sal, String tip_mov, String doc_tra, String ord_com, String cod_cli_pro, String det_obs, String cod_usu, String flg_anu, String cod_esp, String cor_mov, String cod_are, String obs_anu, String det_sta, String nomusu, String nomcli, String flg_val, String valorflg) {
        this.cod_ped = cod_ped;
        this.cod_pai = cod_pai;
        this.fec_ped = fec_ped;
        this.flg_ing_sal = flg_ing_sal;
        this.tip_mov = tip_mov;
        this.doc_tra = doc_tra;
        this.ord_com = ord_com;
        this.cod_cli_pro = cod_cli_pro;
        this.det_obs = det_obs;
        this.cod_usu = cod_usu;
        this.flg_anu = flg_anu;
        this.cod_esp = cod_esp;
        this.cor_mov = cor_mov;
        this.cod_are = cod_are;
        this.obs_anu = obs_anu;
        this.det_sta = det_sta;
        this.nomusu = nomusu;
        this.nomcli = nomcli;
        this.flg_val = flg_val;
        this.valorflg = valorflg;
    }

    public String getCod_ped() {
        return cod_ped;
    }

    public void setCod_ped(String cod_ped) {
        this.cod_ped = cod_ped;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getFec_ped() {
        return fec_ped;
    }

    public void setFec_ped(String fec_ped) {
        this.fec_ped = fec_ped;
    }

    public String getFlg_ing_sal() {
        return flg_ing_sal;
    }

    public void setFlg_ing_sal(String flg_ing_sal) {
        this.flg_ing_sal = flg_ing_sal;
    }

    public String getTip_mov() {
        return tip_mov;
    }

    public void setTip_mov(String tip_mov) {
        this.tip_mov = tip_mov;
    }

    public String getDoc_tra() {
        return doc_tra;
    }

    public void setDoc_tra(String doc_tra) {
        this.doc_tra = doc_tra;
    }

    public String getOrd_com() {
        return ord_com;
    }

    public void setOrd_com(String ord_com) {
        this.ord_com = ord_com;
    }

    public String getCod_cli_pro() {
        return cod_cli_pro;
    }

    public void setCod_cli_pro(String cod_cli_pro) {
        this.cod_cli_pro = cod_cli_pro;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getFlg_anu() {
        return flg_anu;
    }

    public void setFlg_anu(String flg_anu) {
        this.flg_anu = flg_anu;
    }

    public String getCod_esp() {
        return cod_esp;
    }

    public void setCod_esp(String cod_esp) {
        this.cod_esp = cod_esp;
    }

    public String getCor_mov() {
        return cor_mov;
    }

    public void setCor_mov(String cor_mov) {
        this.cor_mov = cor_mov;
    }

    public String getCod_are() {
        return cod_are;
    }

    public void setCod_are(String cod_are) {
        this.cod_are = cod_are;
    }

    public String getObs_anu() {
        return obs_anu;
    }

    public void setObs_anu(String obs_anu) {
        this.obs_anu = obs_anu;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

    public String getNomcli() {
        return nomcli;
    }

    public void setNomcli(String nomcli) {
        this.nomcli = nomcli;
    }

    public String getFlg_val() {
        return flg_val;
    }

    public void setFlg_val(String flg_val) {
        this.flg_val = flg_val;
    }

    public String getValorflg() {
        return valorflg;
    }

    public void setValorflg(String valorflg) {
        this.valorflg = valorflg;
    }

}
