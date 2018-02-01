
package tt.productos;

import java.io.Serializable;


public class CatAreasInv implements Serializable {

    private String cod_are, nom_are;

    public CatAreasInv() {
    }

    public CatAreasInv(String cod_are, String nom_are) {
        this.cod_are = cod_are;
        this.nom_are = nom_are;
    }

    public String getCod_are() {
        return cod_are;
    }

    public void setCod_are(String cod_are) {
        this.cod_are = cod_are;
    }

    public String getNom_are() {
        return nom_are;
    }

    public void setNom_are(String nom_are) {
        this.nom_are = nom_are;
    }

   
    
    
}
