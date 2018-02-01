
package tt.productos;

import java.io.Serializable;


public class CatEmbalaje implements Serializable{
    
    private String id_emb,nom_emb;

    public CatEmbalaje() {
    }

    public CatEmbalaje(String id_emb, String nom_emb) {
        this.id_emb = id_emb;
        this.nom_emb = nom_emb;
    }

    public String getId_emb() {
        return id_emb;
    }

    public void setId_emb(String id_emb) {
        this.id_emb = id_emb;
    }

    public String getNom_emb() {
        return nom_emb;
    }

    public void setNom_emb(String nom_emb) {
        this.nom_emb = nom_emb;
    }
    
    
    
}
