package tt.mantenimiento;

import java.io.Serializable;

public class CatSolicitudes implements Serializable {

    private String cod_mae, cod_alt, fec_sol, cod_usu_sol, cod_usu_apr, 
            cod_usu_rec, cod_dep, det_uso, cod_maq, det_sta, det_obs, 
            fec_cie, flg_loc, cod_pai, nomdep, nommaq,nompai,nomusu;

    public CatSolicitudes() {
    }

    public CatSolicitudes(String cod_mae, String cod_alt, String fec_sol, String cod_usu_sol, String cod_usu_apr, String cod_usu_rec, String cod_dep, String det_uso, String cod_maq, String det_sta, String det_obs, String fec_cie, String flg_loc, String cod_pai, String nomdep, String nommaq, String nompai, String nomusu) {
        this.cod_mae = cod_mae;
        this.cod_alt = cod_alt;
        this.fec_sol = fec_sol;
        this.cod_usu_sol = cod_usu_sol;
        this.cod_usu_apr = cod_usu_apr;
        this.cod_usu_rec = cod_usu_rec;
        this.cod_dep = cod_dep;
        this.det_uso = det_uso;
        this.cod_maq = cod_maq;
        this.det_sta = det_sta;
        this.det_obs = det_obs;
        this.fec_cie = fec_cie;
        this.flg_loc = flg_loc;
        this.cod_pai = cod_pai;
        this.nomdep = nomdep;
        this.nommaq = nommaq;
        this.nompai = nompai;
        this.nomusu = nomusu;
    }

   

    public String getCod_mae() {
        return cod_mae;
    }

    public void setCod_mae(String cod_mae) {
        this.cod_mae = cod_mae;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

    public String getFec_sol() {
        return fec_sol;
    }

    public void setFec_sol(String fec_sol) {
        this.fec_sol = fec_sol;
    }

    public String getCod_usu_sol() {
        return cod_usu_sol;
    }

    public void setCod_usu_sol(String cod_usu_sol) {
        this.cod_usu_sol = cod_usu_sol;
    }

    public String getCod_usu_apr() {
        return cod_usu_apr;
    }

    public void setCod_usu_apr(String cod_usu_apr) {
        this.cod_usu_apr = cod_usu_apr;
    }

    public String getCod_usu_rec() {
        return cod_usu_rec;
    }

    public void setCod_usu_rec(String cod_usu_rec) {
        this.cod_usu_rec = cod_usu_rec;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getDet_uso() {
        return det_uso;
    }

    public void setDet_uso(String det_uso) {
        this.det_uso = det_uso;
    }

    public String getCod_maq() {
        return cod_maq;
    }

    public void setCod_maq(String cod_maq) {
        this.cod_maq = cod_maq;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFec_cie() {
        return fec_cie;
    }

    public void setFec_cie(String fec_cie) {
        this.fec_cie = fec_cie;
    }

    public String getFlg_loc() {
        return flg_loc;
    }

    public void setFlg_loc(String flg_loc) {
        this.flg_loc = flg_loc;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNomdep() {
        return nomdep;
    }

    public void setNomdep(String nomdep) {
        this.nomdep = nomdep;
    }

    public String getNommaq() {
        return nommaq;
    }

    public void setNommaq(String nommaq) {
        this.nommaq = nommaq;
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
