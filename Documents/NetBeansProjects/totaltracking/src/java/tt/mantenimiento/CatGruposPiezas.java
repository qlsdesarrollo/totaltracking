
package tt.mantenimiento;

import java.io.Serializable;


public class CatGruposPiezas implements Serializable{
    
    private String cod_gru, nom_gru;

    public CatGruposPiezas() {
    }

    public CatGruposPiezas(String cod_gru, String nom_gru) {
        this.cod_gru = cod_gru;
        this.nom_gru = nom_gru;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getNom_gru() {
        return nom_gru;
    }

    public void setNom_gru(String nom_gru) {
        this.nom_gru = nom_gru;
    }  
}
