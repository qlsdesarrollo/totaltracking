
package tt.mantenimiento;

import java.io.Serializable;


public class CatLineas implements Serializable{
    
    private String cod_gru, cod_lin,nom_lin,nomgru;

    public CatLineas() {
    }

    public CatLineas(String cod_gru, String cod_lin, String nom_lin, String nomgru) {
        this.cod_gru = cod_gru;
        this.cod_lin = cod_lin;
        this.nom_lin = nom_lin;
        this.nomgru = nomgru;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getCod_lin() {
        return cod_lin;
    }

    public void setCod_lin(String cod_lin) {
        this.cod_lin = cod_lin;
    }

    public String getNom_lin() {
        return nom_lin;
    }

    public void setNom_lin(String nom_lin) {
        this.nom_lin = nom_lin;
    }

    public String getNomgru() {
        return nomgru;
    }

    public void setNomgru(String nomgru) {
        this.nomgru = nomgru;
    }
    
    
    
}
