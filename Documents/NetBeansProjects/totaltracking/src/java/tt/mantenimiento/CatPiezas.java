package tt.mantenimiento;

import java.io.Serializable;

public class CatPiezas implements Serializable {

    private String cod_pie, cod_ref, cod_equ, nom_pie, des_pie, nomequ, cod_cat, det_ima, vid_uti, cod_gru, cod_lin, nomgru, nomlin;

    public CatPiezas() {
    }

    public CatPiezas(String cod_pie, String cod_ref, String cod_equ, String nom_pie, String des_pie, String nomequ, String cod_cat, String det_ima, String vid_uti, String cod_gru, String cod_lin, String nomgru, String nomlin) {
        this.cod_pie = cod_pie;
        this.cod_ref = cod_ref;
        this.cod_equ = cod_equ;
        this.nom_pie = nom_pie;
        this.des_pie = des_pie;
        this.nomequ = nomequ;
        this.cod_cat = cod_cat;
        this.det_ima = det_ima;
        this.vid_uti = vid_uti;
        this.cod_gru = cod_gru;
        this.cod_lin = cod_lin;
        this.nomgru = nomgru;
        this.nomlin = nomlin;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getNom_pie() {
        return nom_pie;
    }

    public void setNom_pie(String nom_pie) {
        this.nom_pie = nom_pie;
    }

    public String getDes_pie() {
        return des_pie;
    }

    public void setDes_pie(String des_pie) {
        this.des_pie = des_pie;
    }

    public String getNomequ() {
        return nomequ;
    }

    public void setNomequ(String nomequ) {
        this.nomequ = nomequ;
    }

    public String getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(String cod_cat) {
        this.cod_cat = cod_cat;
    }

    public String getDet_ima() {
        return det_ima;
    }

    public void setDet_ima(String det_ima) {
        this.det_ima = det_ima;
    }

    public String getVid_uti() {
        return vid_uti;
    }

    public void setVid_uti(String vid_uti) {
        this.vid_uti = vid_uti;
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

    public String getNomgru() {
        return nomgru;
    }

    public void setNomgru(String nomgru) {
        this.nomgru = nomgru;
    }

    public String getNomlin() {
        return nomlin;
    }

    public void setNomlin(String nomlin) {
        this.nomlin = nomlin;
    }

}
