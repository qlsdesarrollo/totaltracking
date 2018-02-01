package paquetes;

import java.io.Serializable;

public class CatUnidades implements Serializable {

    private String cod_uni, abr_uni, nom_uni;

    public CatUnidades() {
    }

    public CatUnidades(String cod_uni, String abr_uni, String nom_uni) {
        this.cod_uni = cod_uni;
        this.abr_uni = abr_uni;
        this.nom_uni = nom_uni;
    }

    public String getCod_uni() {
        return cod_uni;
    }

    public void setCod_uni(String cod_uni) {
        this.cod_uni = cod_uni;
    }

    public String getAbr_uni() {
        return abr_uni;
    }

    public void setAbr_uni(String abr_uni) {
        this.abr_uni = abr_uni;
    }

    public String getNom_uni() {
        return nom_uni;
    }

    public void setNom_uni(String nom_uni) {
        this.nom_uni = nom_uni;
    }

}
