package tt.mantenimiento;

import java.io.Serializable;

public class CatPermisosEsp implements Serializable {

    private String cod_rel, det_per, cod_gru, nomgru;

    public CatPermisosEsp() {
    }

    public CatPermisosEsp(String cod_rel, String det_per, String cod_gru, String nomgru) {
        this.cod_rel = cod_rel;
        this.det_per = det_per;
        this.cod_gru = cod_gru;
        this.nomgru = nomgru;
    }

    public String getCod_rel() {
        return cod_rel;
    }

    public void setCod_rel(String cod_rel) {
        this.cod_rel = cod_rel;
    }

    public String getDet_per() {
        return det_per;
    }

    public void setDet_per(String det_per) {
        this.det_per = det_per;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getNomgru() {
        return nomgru;
    }

    public void setNomgru(String nomgru) {
        this.nomgru = nomgru;
    }

}
