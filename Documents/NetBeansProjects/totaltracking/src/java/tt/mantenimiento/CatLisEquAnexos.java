package tt.mantenimiento;

import java.io.Serializable;

public class CatLisEquAnexos implements Serializable {

    private String cod_lis_equ, cor_det, tip_ane, det_nom, det_obs, nomtipane;

    public CatLisEquAnexos() {
    }

    public CatLisEquAnexos(String cod_lis_equ, String cor_det, String tip_ane, String det_nom, String det_obs, String nomtipane) {
        this.cod_lis_equ = cod_lis_equ;
        this.cor_det = cor_det;
        this.tip_ane = tip_ane;
        this.det_nom = det_nom;
        this.det_obs = det_obs;
        this.nomtipane = nomtipane;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCor_det() {
        return cor_det;
    }

    public void setCor_det(String cor_det) {
        this.cor_det = cor_det;
    }

    public String getTip_ane() {
        return tip_ane;
    }

    public void setTip_ane(String tip_ane) {
        this.tip_ane = tip_ane;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getNomtipane() {
        return nomtipane;
    }

    public void setNomtipane(String nomtipane) {
        this.nomtipane = nomtipane;
    }

    
}
