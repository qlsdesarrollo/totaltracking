package tt.mantenimiento;

import java.io.Serializable;

public class CatCategorias implements Serializable {

    private String cod_cat,nom_cat;

    public CatCategorias() {
    }

    public CatCategorias(String cod_cat, String nom_cat) {
        this.cod_cat = cod_cat;
        this.nom_cat = nom_cat;
    }

    public String getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(String cod_cat) {
        this.cod_cat = cod_cat;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }
    
    
    
}
