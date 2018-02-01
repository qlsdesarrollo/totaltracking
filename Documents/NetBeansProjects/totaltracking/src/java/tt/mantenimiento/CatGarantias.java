package tt.mantenimiento;

import java.io.Serializable;

public class CatGarantias implements Serializable {

    private String cod_lis_equ, cod_gar, det_obs, fec_ini, fec_exp;

    public CatGarantias() {
    }

    public CatGarantias(String cod_lis_equ, String cod_gar, String det_obs, String fec_ini, String fec_exp) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_gar = cod_gar;
        this.det_obs = det_obs;
        this.fec_ini = fec_ini;
        this.fec_exp = fec_exp;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_gar() {
        return cod_gar;
    }

    public void setCod_gar(String cod_gar) {
        this.cod_gar = cod_gar;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_exp() {
        return fec_exp;
    }

    public void setFec_exp(String fec_exp) {
        this.fec_exp = fec_exp;
    }

}
