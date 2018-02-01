package tt.licencias;

import java.io.Serializable;

public class CatControlLicenciasEncabezado implements Serializable {

    private String cor_ctr, cod_pai, nom_hos, nom_ciu, nom_ent, det_sta, det_obs, nompai;

    public CatControlLicenciasEncabezado() {
    }

    public CatControlLicenciasEncabezado(String cor_ctr, String cod_pai, String nom_hos, String nom_ciu, String nom_ent, String det_sta, String det_obs, String nompai) {
        this.cor_ctr = cor_ctr;
        this.cod_pai = cod_pai;
        this.nom_hos = nom_hos;
        this.nom_ciu = nom_ciu;
        this.nom_ent = nom_ent;
        this.det_sta = det_sta;
        this.det_obs = det_obs;
        this.nompai = nompai;
    }

    public String getCor_ctr() {
        return cor_ctr;
    }

    public void setCor_ctr(String cor_ctr) {
        this.cor_ctr = cor_ctr;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_hos() {
        return nom_hos;
    }

    public void setNom_hos(String nom_hos) {
        this.nom_hos = nom_hos;
    }

    public String getNom_ciu() {
        return nom_ciu;
    }

    public void setNom_ciu(String nom_ciu) {
        this.nom_ciu = nom_ciu;
    }

    public String getNom_ent() {
        return nom_ent;
    }

    public void setNom_ent(String nom_ent) {
        this.nom_ent = nom_ent;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

}
