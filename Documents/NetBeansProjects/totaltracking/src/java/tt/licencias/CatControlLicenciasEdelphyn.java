package tt.licencias;

import java.io.Serializable;

public class CatControlLicenciasEdelphyn implements Serializable {

    private String cor_enc, cor_det, det_lic_sol, det_lic_def, det_ver_war, det_ver_scr, det_sis_ope, det_cad_ede, det_cad_jav, det_obs, det_sta;

    public CatControlLicenciasEdelphyn() {
    }

    public CatControlLicenciasEdelphyn(String cor_enc, String cor_det, String det_lic_sol, String det_lic_def, String det_ver_war, String det_ver_scr, String det_sis_ope, String det_cad_ede, String det_cad_jav, String det_obs, String det_sta) {
        this.cor_enc = cor_enc;
        this.cor_det = cor_det;
        this.det_lic_sol = det_lic_sol;
        this.det_lic_def = det_lic_def;
        this.det_ver_war = det_ver_war;
        this.det_ver_scr = det_ver_scr;
        this.det_sis_ope = det_sis_ope;
        this.det_cad_ede = det_cad_ede;
        this.det_cad_jav = det_cad_jav;
        this.det_obs = det_obs;
        this.det_sta = det_sta;
    }

    public String getCor_enc() {
        return cor_enc;
    }

    public void setCor_enc(String cor_enc) {
        this.cor_enc = cor_enc;
    }

    public String getCor_det() {
        return cor_det;
    }

    public void setCor_det(String cor_det) {
        this.cor_det = cor_det;
    }

    public String getDet_lic_sol() {
        return det_lic_sol;
    }

    public void setDet_lic_sol(String det_lic_sol) {
        this.det_lic_sol = det_lic_sol;
    }

    public String getDet_lic_def() {
        return det_lic_def;
    }

    public void setDet_lic_def(String det_lic_def) {
        this.det_lic_def = det_lic_def;
    }

    public String getDet_ver_war() {
        return det_ver_war;
    }

    public void setDet_ver_war(String det_ver_war) {
        this.det_ver_war = det_ver_war;
    }

    public String getDet_ver_scr() {
        return det_ver_scr;
    }

    public void setDet_ver_scr(String det_ver_scr) {
        this.det_ver_scr = det_ver_scr;
    }

    public String getDet_sis_ope() {
        return det_sis_ope;
    }

    public void setDet_sis_ope(String det_sis_ope) {
        this.det_sis_ope = det_sis_ope;
    }

    public String getDet_cad_ede() {
        return det_cad_ede;
    }

    public void setDet_cad_ede(String det_cad_ede) {
        this.det_cad_ede = det_cad_ede;
    }

    public String getDet_cad_jav() {
        return det_cad_jav;
    }

    public void setDet_cad_jav(String det_cad_jav) {
        this.det_cad_jav = det_cad_jav;
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
    
    

}
