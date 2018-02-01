package tt.mantenimiento;

import java.io.Serializable;

public class CatMantenimientosGen implements Serializable {

    private String cod_lis_equ, cod_man, det_man, fec_man, cod_ope, det_obs, cod_usu, nomope, nomusu, det_min;

    public CatMantenimientosGen() {
    }

    public CatMantenimientosGen(String cod_lis_equ, String cod_man, String det_man, String fec_man, 
            String cod_ope, String det_obs, String cod_usu, String nomope, String nomusu, String det_min) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.det_man = det_man;
        this.fec_man = fec_man;
        this.cod_ope = cod_ope;
        this.det_obs = det_obs;
        this.cod_usu = cod_usu;
        this.nomope = nomope;
        this.nomusu = nomusu;
        this.det_min = det_min;
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

    public String getFec_man() {
        return fec_man;
    }

    public void setFec_man(String fec_man) {
        this.fec_man = fec_man;
    }

    public String getCod_ope() {
        return cod_ope;
    }

    public void setCod_ope(String cod_ope) {
        this.cod_ope = cod_ope;
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

    public String getNomope() {
        return nomope;
    }

    public void setNomope(String nomope) {
        this.nomope = nomope;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

    public String getDet_min() {
        return det_min;
    }

    public void setDet_min(String det_min) {
        this.det_min = det_min;
    }
    
    

}
