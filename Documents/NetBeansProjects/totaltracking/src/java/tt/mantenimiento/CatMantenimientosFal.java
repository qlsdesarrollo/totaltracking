
package tt.mantenimiento;

import java.io.Serializable;


public class CatMantenimientosFal implements Serializable{
    
    private String cod_lis_equ,cod_man,det_man,cod_gru_fal,cod_fal,det_obs, nomgrup,nomfal;

    public CatMantenimientosFal() {
    }

    public CatMantenimientosFal(String cod_lis_equ, String cod_man, String det_man, String cod_gru_fal, String cod_fal, String det_obs, String nomgrup, String nomfal) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.det_man = det_man;
        this.cod_gru_fal = cod_gru_fal;
        this.cod_fal = cod_fal;
        this.det_obs = det_obs;
        this.nomgrup = nomgrup;
        this.nomfal = nomfal;
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

    public String getCod_gru_fal() {
        return cod_gru_fal;
    }

    public void setCod_gru_fal(String cod_gru_fal) {
        this.cod_gru_fal = cod_gru_fal;
    }

    public String getCod_fal() {
        return cod_fal;
    }

    public void setCod_fal(String cod_fal) {
        this.cod_fal = cod_fal;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getNomgrup() {
        return nomgrup;
    }

    public void setNomgrup(String nomgrup) {
        this.nomgrup = nomgrup;
    }

    public String getNomfal() {
        return nomfal;
    }

    public void setNomfal(String nomfal) {
        this.nomfal = nomfal;
    }
    
    
    
}
