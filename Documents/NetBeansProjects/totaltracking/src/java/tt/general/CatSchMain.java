package tt.general;

import java.io.Serializable;

public class CatSchMain implements Serializable {

    private String correla, sch_cor, fec_sch, cod_usu, usu_sol, cod_man, det_obs, det_sta, det_col, flg_tar_man, solicita, equipo, estado, fec_fin;

    public CatSchMain() {
    }

    public CatSchMain(String correla, String sch_cor, String fec_sch, String cod_usu, String usu_sol, String cod_man, String det_obs, String det_sta, String det_col, String flg_tar_man, String solicita, String equipo, String estado, String fec_fin) {
        this.correla = correla;
        this.sch_cor = sch_cor;
        this.fec_sch = fec_sch;
        this.cod_usu = cod_usu;
        this.usu_sol = usu_sol;
        this.cod_man = cod_man;
        this.det_obs = det_obs;
        this.det_sta = det_sta;
        this.det_col = det_col;
        this.flg_tar_man = flg_tar_man;
        this.solicita = solicita;
        this.equipo = equipo;
        this.estado = estado;
        this.fec_fin = fec_fin;
    }

    public String getCorrela() {
        return correla;
    }

    public void setCorrela(String correla) {
        this.correla = correla;
    }

    public String getSch_cor() {
        return sch_cor;
    }

    public void setSch_cor(String sch_cor) {
        this.sch_cor = sch_cor;
    }

    public String getFec_sch() {
        return fec_sch;
    }

    public void setFec_sch(String fec_sch) {
        this.fec_sch = fec_sch;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getUsu_sol() {
        return usu_sol;
    }

    public void setUsu_sol(String usu_sol) {
        this.usu_sol = usu_sol;
    }

    public String getCod_man() {
        return cod_man;
    }

    public void setCod_man(String cod_man) {
        this.cod_man = cod_man;
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

    public String getDet_col() {
        return det_col;
    }

    public void setDet_col(String det_col) {
        this.det_col = det_col;
    }

    public String getFlg_tar_man() {
        return flg_tar_man;
    }

    public void setFlg_tar_man(String flg_tar_man) {
        this.flg_tar_man = flg_tar_man;
    }

    public String getSolicita() {
        return solicita;
    }

    public void setSolicita(String solicita) {
        this.solicita = solicita;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

}
