package tt.licencias;

import java.io.Serializable;

public class CatControlLicenciasEdelphynDet implements Serializable {

    private String cor_enc, cor_det, det_lic, fec_cad_ede, det_ubi;

    public CatControlLicenciasEdelphynDet() {
    }

    public CatControlLicenciasEdelphynDet(String cor_enc, String cor_det, String det_lic, String fec_cad_ede, String det_ubi) {
        this.cor_enc = cor_enc;
        this.cor_det = cor_det;
        this.det_lic = det_lic;
        this.fec_cad_ede = fec_cad_ede;
        this.det_ubi = det_ubi;
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

    public String getDet_lic() {
        return det_lic;
    }

    public void setDet_lic(String det_lic) {
        this.det_lic = det_lic;
    }

    public String getFec_cad_ede() {
        return fec_cad_ede;
    }

    public void setFec_cad_ede(String fec_cad_ede) {
        this.fec_cad_ede = fec_cad_ede;
    }

    public String getDet_ubi() {
        return det_ubi;
    }

    public void setDet_ubi(String det_ubi) {
        this.det_ubi = det_ubi;
    }

}
