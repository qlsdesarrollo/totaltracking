package tt.mantenimiento;

import java.io.Serializable;

public class CatMantenimientosAne implements Serializable {

    private String cod_lis_equ, cod_man, det_man, det_obs, tip_ane, rut_ane, cod_usu, nomtip, nomusu;

    public CatMantenimientosAne() {
    }

    public CatMantenimientosAne(String cod_lis_equ, String cod_man, String det_man, String det_obs, String tip_ane, String rut_ane, String cod_usu, String nomtip, String nomusu) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.det_man = det_man;
        this.det_obs = det_obs;
        this.tip_ane = tip_ane;
        this.rut_ane = rut_ane;
        this.cod_usu = cod_usu;
        this.nomtip = nomtip;
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

    public String getDet_man() {
        return det_man;
    }

    public void setDet_man(String det_man) {
        this.det_man = det_man;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getTip_ane() {
        return tip_ane;
    }

    public void setTip_ane(String tip_ane) {
        this.tip_ane = tip_ane;
    }

    public String getRut_ane() {
        return rut_ane;
    }

    public void setRut_ane(String rut_ane) {
        this.rut_ane = rut_ane;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNomtip() {
        return nomtip;
    }

    public void setNomtip(String nomtip) {
        this.nomtip = nomtip;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

}
