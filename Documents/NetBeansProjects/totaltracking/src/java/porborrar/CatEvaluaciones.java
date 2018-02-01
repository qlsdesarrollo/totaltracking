
package paquetes;

import java.io.Serializable;

public class CatEvaluaciones implements Serializable{
    
    private String id_eva,nom_eva;

    public CatEvaluaciones() {
    }

    public CatEvaluaciones(String id_eva, String nom_eva) {
        this.id_eva = id_eva;
        this.nom_eva = nom_eva;
    }

    public String getId_eva() {
        return id_eva;
    }

    public void setId_eva(String id_eva) {
        this.id_eva = id_eva;
    }

    public String getNom_eva() {
        return nom_eva;
    }

    public void setNom_eva(String nom_eva) {
        this.nom_eva = nom_eva;
    }

    
}
