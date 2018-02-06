package tt.it;

import java.io.Serializable;

public class CatManEquIT implements Serializable {

    private String cod_lis_equ, cod_man, cod_pai, nom_cli, tip_man, fec_ini, fec_fin, usu_res, det_obs, det_sta, nompai, nomusu;

    public CatManEquIT() {
    }

    public CatManEquIT(String cod_lis_equ, String cod_man, String cod_pai, String nom_cli, String tip_man, String fec_ini, String fec_fin, String usu_res, String det_obs, String det_sta, String nompai, String nomusu) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.cod_pai = cod_pai;
        this.nom_cli = nom_cli;
        this.tip_man = tip_man;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
        this.usu_res = usu_res;
        this.det_obs = det_obs;
        this.det_sta = det_sta;
        this.nompai = nompai;
        this.nomusu = nomusu;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_man() {
        return cod_man;
    }

    public void setCod_man(String cod_man) {
        this.cod_man = cod_man;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getTip_man() {
        return tip_man;
    }

    public void setTip_man(String tip_man) {
        this.tip_man = tip_man;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public String getUsu_res() {
        return usu_res;
    }

    public void setUsu_res(String usu_res) {
        this.usu_res = usu_res;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
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

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

}
