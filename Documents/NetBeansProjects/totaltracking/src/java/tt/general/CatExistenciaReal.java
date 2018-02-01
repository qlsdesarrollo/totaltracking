package tt.general;

import java.io.Serializable;

public class CatExistenciaReal implements Serializable {

    private String cod_pie, cod_ref, nom_pie, exireal;

    public CatExistenciaReal() {
    }

    public CatExistenciaReal(String cod_pie, String cod_ref, String nom_pie, String exireal) {
        this.cod_pie = cod_pie;
        this.cod_ref = cod_ref;
        this.nom_pie = nom_pie;
        this.exireal = exireal;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getNom_pie() {
        return nom_pie;
    }

    public void setNom_pie(String nom_pie) {
        this.nom_pie = nom_pie;
    }

    public String getExireal() {
        return exireal;
    }

    public void setExireal(String exireal) {
        this.exireal = exireal;
    }

}
