
package tt.productos;

import java.io.Serializable;

public class CatFamilia implements Serializable{
    
    private String id_fam,nom_fam;

    public CatFamilia() {
    }

    public CatFamilia(String id_fam, String nom_fam) {
        this.id_fam = id_fam;
        this.nom_fam = nom_fam;
    }

    public String getId_fam() {
        return id_fam;
    }

    public void setId_fam(String id_fam) {
        this.id_fam = id_fam;
    }

    public String getNom_fam() {
        return nom_fam;
    }

    public void setNom_fam(String nom_fam) {
        this.nom_fam = nom_fam;
    }
    
    
    
}
