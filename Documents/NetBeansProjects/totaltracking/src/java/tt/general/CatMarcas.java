
package tt.general;

import java.io.Serializable;

public class CatMarcas implements Serializable{
    
    private String id_mar,nom_mar;

    public CatMarcas() {
    }

    public CatMarcas(String id_mar, String nom_mar) {
        this.id_mar = id_mar;
        this.nom_mar = nom_mar;
    }

    public String getId_mar() {
        return id_mar;
    }

    public void setId_mar(String id_mar) {
        this.id_mar = id_mar;
    }

    public String getNom_mar() {
        return nom_mar;
    }

    public void setNom_mar(String nom_mar) {
        this.nom_mar = nom_mar;
    }
    
    
}
