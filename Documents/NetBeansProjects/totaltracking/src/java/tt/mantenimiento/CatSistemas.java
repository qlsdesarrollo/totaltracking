package tt.mantenimiento;

import java.io.Serializable;


public class CatSistemas implements Serializable{
    
    private String cod_lis_equ,cod_sys,det_obs,ver_ant,ver_act,fec_act;

    public CatSistemas() {
    }

    public CatSistemas(String cod_lis_equ, String cod_sys, String det_obs, String ver_ant, String ver_act, String fec_act) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_sys = cod_sys;
        this.det_obs = det_obs;
        this.ver_ant = ver_ant;
        this.ver_act = ver_act;
        this.fec_act = fec_act;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_sys() {
        return cod_sys;
    }

    public void setCod_sys(String cod_sys) {
        this.cod_sys = cod_sys;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getVer_ant() {
        return ver_ant;
    }

    public void setVer_ant(String ver_ant) {
        this.ver_ant = ver_ant;
    }

    public String getVer_act() {
        return ver_act;
    }

    public void setVer_act(String ver_act) {
        this.ver_act = ver_act;
    }

    public String getFec_act() {
        return fec_act;
    }

    public void setFec_act(String fec_act) {
        this.fec_act = fec_act;
    }
    
    
}
