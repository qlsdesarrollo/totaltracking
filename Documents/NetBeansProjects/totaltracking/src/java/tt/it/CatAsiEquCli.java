package tt.it;

import java.io.Serializable;

public class CatAsiEquCli implements Serializable {

    private String cod_lis_equ, nom_cli, cod_det, cod_pai, nom_dep, nom_usu, fec_asi, fec_cam, nompai;

    public CatAsiEquCli() {
    }

    public CatAsiEquCli(String cod_lis_equ, String nom_cli, String cod_det, String cod_pai, String nom_dep, String nom_usu, String fec_asi, String fec_cam, String nompai) {
        this.cod_lis_equ = cod_lis_equ;
        this.nom_cli = nom_cli;
        this.cod_det = cod_det;
        this.cod_pai = cod_pai;
        this.nom_dep = nom_dep;
        this.nom_usu = nom_usu;
        this.fec_asi = fec_asi;
        this.fec_cam = fec_cam;
        this.nompai = nompai;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
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

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

    public String getFec_asi() {
        return fec_asi;
    }

    public void setFec_asi(String fec_asi) {
        this.fec_asi = fec_asi;
    }

    public String getFec_cam() {
        return fec_cam;
    }

    public void setFec_cam(String fec_cam) {
        this.fec_cam = fec_cam;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

}
