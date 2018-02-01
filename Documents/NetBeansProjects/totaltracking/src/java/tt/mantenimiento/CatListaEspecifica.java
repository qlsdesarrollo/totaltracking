package tt.mantenimiento;

import java.io.Serializable;

public class CatListaEspecifica implements Serializable {

    private String cod_lis, cod_usu, nom_usu;

    public CatListaEspecifica() {
    }

    public CatListaEspecifica(String cod_lis, String cod_usu, String nom_usu) {
        this.cod_lis = cod_lis;
        this.cod_usu = cod_usu;
        this.nom_usu = nom_usu;
    }

    public String getCod_lis() {
        return cod_lis;
    }

    public void setCod_lis(String cod_lis) {
        this.cod_lis = cod_lis;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

}
