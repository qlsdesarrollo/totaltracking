
package tt.mantenimiento;

import java.io.Serializable;


public class CatGrupoFallas implements Serializable {
    
    private String cod_gru_fal,nom_gru_fal;

    public CatGrupoFallas() {
    }

    public CatGrupoFallas(String cod_gru_fal, String nom_gru_fal) {
        this.cod_gru_fal = cod_gru_fal;
        this.nom_gru_fal = nom_gru_fal;
    }

    public String getCod_gru_fal() {
        return cod_gru_fal;
    }

    public void setCod_gru_fal(String cod_gru_fal) {
        this.cod_gru_fal = cod_gru_fal;
    }

    public String getNom_gru_fal() {
        return nom_gru_fal;
    }

    public void setNom_gru_fal(String nom_gru_fal) {
        this.nom_gru_fal = nom_gru_fal;
    }
    
    
    
}
