package tt.mantenimiento;

import java.io.Serializable;

public class CatOperaciones implements Serializable{
    
    private String cod_ope, nom_ope;

    public CatOperaciones() {
    }

    public CatOperaciones(String cod_ope, String nom_ope) {
        this.cod_ope = cod_ope;
        this.nom_ope = nom_ope;
    }

    public String getCod_ope() {
        return cod_ope;
    }

    public void setCod_ope(String cod_ope) {
        this.cod_ope = cod_ope;
    }

    public String getNom_ope() {
        return nom_ope;
    }

    public void setNom_ope(String nom_ope) {
        this.nom_ope = nom_ope;
    }
    
    
    
}
