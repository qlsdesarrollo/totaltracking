package paquetes;

import java.io.Serializable;

public class CatFactores implements Serializable {

    private String id_fac, nom_fac;

    public CatFactores() {
    }

    public CatFactores(String id_fac, String nom_fac) {
        this.id_fac = id_fac;
        this.nom_fac = nom_fac;
    }

    public String getId_fac() {
        return id_fac;
    }

    public void setId_fac(String id_fac) {
        this.id_fac = id_fac;
    }

    public String getNom_fac() {
        return nom_fac;
    }

    public void setNom_fac(String nom_fac) {
        this.nom_fac = nom_fac;
    }

}
