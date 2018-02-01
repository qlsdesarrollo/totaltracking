package tt.general;

import java.io.Serializable;

public class CatBodegas implements Serializable {

    private String id_bod, nom_bod, cod_pai, nompai;

    public CatBodegas() {
    }

    public CatBodegas(String id_bod, String nom_bod, String cod_pai, String nompai) {
        this.id_bod = id_bod;
        this.nom_bod = nom_bod;
        this.cod_pai = cod_pai;
        this.nompai = nompai;
    }

    public String getId_bod() {
        return id_bod;
    }

    public void setId_bod(String id_bod) {
        this.id_bod = id_bod;
    }

    public String getNom_bod() {
        return nom_bod;
    }

    public void setNom_bod(String nom_bod) {
        this.nom_bod = nom_bod;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

}
