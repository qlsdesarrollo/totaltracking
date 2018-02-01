package tt.seguridad;

import java.io.Serializable;

public class CatUsuarios implements Serializable {

    private String cod_usu, nom_usu, des_pas, tip_usu, cod_pai, cod_dep, det_nom, det_mai, nompai, nomdep;

    public CatUsuarios() {
    }

    public CatUsuarios(String cod_usu, String nom_usu, String des_pas, String tip_usu, String cod_pai, String cod_dep, String det_nom, String det_mai, String nompai, String nomdep) {
        this.cod_usu = cod_usu;
        this.nom_usu = nom_usu;
        this.des_pas = des_pas;
        this.tip_usu = tip_usu;
        this.cod_pai = cod_pai;
        this.cod_dep = cod_dep;
        this.det_nom = det_nom;
        this.det_mai = det_mai;
        this.nompai = nompai;
        this.nomdep = nomdep;
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

    public String getDes_pas() {
        return des_pas;
    }

    public void setDes_pas(String des_pas) {
        this.des_pas = des_pas;
    }

    public String getTip_usu() {
        return tip_usu;
    }

    public void setTip_usu(String tip_usu) {
        this.tip_usu = tip_usu;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getDet_mai() {
        return det_mai;
    }

    public void setDet_mai(String det_mai) {
        this.det_mai = det_mai;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNomdep() {
        return nomdep;
    }

    public void setNomdep(String nomdep) {
        this.nomdep = nomdep;
    }

}
