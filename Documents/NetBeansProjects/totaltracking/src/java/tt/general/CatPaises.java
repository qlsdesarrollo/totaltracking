
package tt.general;

import java.io.Serializable;


public class CatPaises implements Serializable{
    
    private String cod_pai, nom_pai;

    public CatPaises() {
    }

    public CatPaises(String cod_pai, String nom_pai) {
        this.cod_pai = cod_pai;
        this.nom_pai = nom_pai;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_pai() {
        return nom_pai;
    }

    public void setNom_pai(String nom_pai) {
        this.nom_pai = nom_pai;
    }
    
    
    
}
