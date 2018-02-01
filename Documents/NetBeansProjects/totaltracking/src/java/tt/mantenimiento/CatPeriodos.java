package tt.mantenimiento;

import java.io.Serializable;

public class CatPeriodos implements Serializable {

    private String cod_per, nom_per, det_dia;

    public CatPeriodos() {
    }

    public CatPeriodos(String cod_per, String nom_per, String det_dia) {
        this.cod_per = cod_per;
        this.nom_per = nom_per;
        this.det_dia = det_dia;
    }

    public String getCod_per() {
        return cod_per;
    }

    public void setCod_per(String cod_per) {
        this.cod_per = cod_per;
    }

    public String getNom_per() {
        return nom_per;
    }

    public void setNom_per(String nom_per) {
        this.nom_per = nom_per;
    }

    public String getDet_dia() {
        return det_dia;
    }

    public void setDet_dia(String det_dia) {
        this.det_dia = det_dia;
    }

}
