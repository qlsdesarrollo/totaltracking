package paquetes;

import java.io.Serializable;

public class CatCriterios implements Serializable {

    private String id_cri, nom_cri, id_fac, nomfac;

    public CatCriterios() {
    }

    public CatCriterios(String id_cri, String nom_cri, String id_fac, String nomfac) {
        this.id_cri = id_cri;
        this.nom_cri = nom_cri;
        this.id_fac = id_fac;
        this.nomfac = nomfac;
    }

    public String getId_cri() {
        return id_cri;
    }

    public void setId_cri(String id_cri) {
        this.id_cri = id_cri;
    }

    public String getNom_cri() {
        return nom_cri;
    }

    public void setNom_cri(String nom_cri) {
        this.nom_cri = nom_cri;
    }

    public String getId_fac() {
        return id_fac;
    }

    public void setId_fac(String id_fac) {
        this.id_fac = id_fac;
    }

    public String getNomfac() {
        return nomfac;
    }

    public void setNom_fac(String nomfac) {
        this.nomfac = nomfac;
    }

    

}
