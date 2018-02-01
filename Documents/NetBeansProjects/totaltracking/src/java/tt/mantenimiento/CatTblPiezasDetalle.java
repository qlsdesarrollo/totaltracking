package tt.mantenimiento;

import java.io.Serializable;

public class CatTblPiezasDetalle implements Serializable {

    private String cod_enc, cod_det, cod_pie, cod_bod, cod_ubi, det_can, det_cos, det_sta, nompie, nombod, nomubi;

    public CatTblPiezasDetalle() {
    }

    public CatTblPiezasDetalle(String cod_enc, String cod_det, String cod_pie, String cod_bod, String cod_ubi, String det_can, String det_cos, String det_sta, String nompie, String nombod, String nomubi) {
        this.cod_enc = cod_enc;
        this.cod_det = cod_det;
        this.cod_pie = cod_pie;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.det_can = det_can;
        this.det_cos = det_cos;
        this.det_sta = det_sta;
        this.nompie = nompie;
        this.nombod = nombod;
        this.nomubi = nomubi;
    }

    public String getCod_enc() {
        return cod_enc;
    }

    public void setCod_enc(String cod_enc) {
        this.cod_enc = cod_enc;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getCod_ubi() {
        return cod_ubi;
    }

    public void setCod_ubi(String cod_ubi) {
        this.cod_ubi = cod_ubi;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(String det_cos) {
        this.det_cos = det_cos;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getNompie() {
        return nompie;
    }

    public void setNompie(String nompie) {
        this.nompie = nompie;
    }

    public String getNombod() {
        return nombod;
    }

    public void setNombod(String nombod) {
        this.nombod = nombod;
    }

    public String getNomubi() {
        return nomubi;
    }

    public void setNomubi(String nomubi) {
        this.nomubi = nomubi;
    }


}
