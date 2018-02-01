package tt.licencias;

import java.io.Serializable;

public class CatControlLicenciasInterfaces implements Serializable {

    private String cor_enc, cor_det, det_nom_int, det_sis_ope, det_ver_int, fec_cad_int, fec_ult_env, det_sta, det_obs;

    public CatControlLicenciasInterfaces() {
    }

    public CatControlLicenciasInterfaces(String cor_enc, String cor_det, String det_nom_int, String det_sis_ope, String det_ver_int, String fec_cad_int, String fec_ult_env, String det_sta, String det_obs) {
        this.cor_enc = cor_enc;
        this.cor_det = cor_det;
        this.det_nom_int = det_nom_int;
        this.det_sis_ope = det_sis_ope;
        this.det_ver_int = det_ver_int;
        this.fec_cad_int = fec_cad_int;
        this.fec_ult_env = fec_ult_env;
        this.det_sta = det_sta;
        this.det_obs = det_obs;
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

    public String getDet_nom_int() {
        return det_nom_int;
    }

    public void setDet_nom_int(String det_nom_int) {
        this.det_nom_int = det_nom_int;
    }

    public String getDet_sis_ope() {
        return det_sis_ope;
    }

    public void setDet_sis_ope(String det_sis_ope) {
        this.det_sis_ope = det_sis_ope;
    }

    public String getDet_ver_int() {
        return det_ver_int;
    }

    public void setDet_ver_int(String det_ver_int) {
        this.det_ver_int = det_ver_int;
    }

    public String getFec_cad_int() {
        return fec_cad_int;
    }

    public void setFec_cad_int(String fec_cad_int) {
        this.fec_cad_int = fec_cad_int;
    }

    public String getFec_ult_env() {
        return fec_ult_env;
    }

    public void setFec_ult_env(String fec_ult_env) {
        this.fec_ult_env = fec_ult_env;
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

}
