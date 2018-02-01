package tt.general;

import java.io.Serializable;

public class CatDepartamentos implements Serializable {

    private String cod_dep,cod_pai,nom_dep;

    public CatDepartamentos() {
    }

    public CatDepartamentos(String cod_dep, String cod_pai, String nom_dep) {
        this.cod_dep = cod_dep;
        this.cod_pai = cod_pai;
        this.nom_dep = nom_dep;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_dep() {
        return nom_dep;
    }

    public void setNom_dep(String nom_dep) {
        this.nom_dep = nom_dep;
    }

   

}
