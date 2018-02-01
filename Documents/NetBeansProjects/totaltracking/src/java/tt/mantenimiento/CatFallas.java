
package tt.mantenimiento;

import java.io.Serializable;


public class CatFallas implements Serializable{
    
    private String cod_fal,cod_gru_fal,nom_fal,nomgrupo;

    public CatFallas() {
    }

    public CatFallas(String cod_fal, String cod_gru_fal, String nom_fal, String nomgrupo) {
        this.cod_fal = cod_fal;
        this.cod_gru_fal = cod_gru_fal;
        this.nom_fal = nom_fal;
        this.nomgrupo = nomgrupo;
    }

    public String getCod_fal() {
        return cod_fal;
    }

    public void setCod_fal(String cod_fal) {
        this.cod_fal = cod_fal;
    }

    public String getCod_gru_fal() {
        return cod_gru_fal;
    }

    public void setCod_gru_fal(String cod_gru_fal) {
        this.cod_gru_fal = cod_gru_fal;
    }

    public String getNom_fal() {
        return nom_fal;
    }

    public void setNom_fal(String nom_fal) {
        this.nom_fal = nom_fal;
    }

    public String getNomgrupo() {
        return nomgrupo;
    }

    public void setNomgrupo(String nomgrupo) {
        this.nomgrupo = nomgrupo;
    }
    
    
    
    
}
