package tt.mantenimiento;

import java.io.Serializable;

public class CatEquipos implements Serializable {

    private String cod_equ, cod_mar, cod_ref, nom_equ, des_equ, marca, det_ima;

    public CatEquipos() {
    }

    public CatEquipos(String cod_equ, String cod_mar, String cod_ref, String nom_equ, String des_equ, String marca, String det_ima) {
        this.cod_equ = cod_equ;
        this.cod_mar = cod_mar;
        this.cod_ref = cod_ref;
        this.nom_equ = nom_equ;
        this.des_equ = des_equ;
        this.marca = marca;
        this.det_ima = det_ima;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getCod_mar() {
        return cod_mar;
    }

    public void setCod_mar(String cod_mar) {
        this.cod_mar = cod_mar;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getNom_equ() {
        return nom_equ;
    }

    public void setNom_equ(String nom_equ) {
        this.nom_equ = nom_equ;
    }

    public String getDes_equ() {
        return des_equ;
    }

    public void setDes_equ(String des_equ) {
        this.des_equ = des_equ;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDet_ima() {
        return det_ima;
    }

    public void setDet_ima(String det_ima) {
        this.det_ima = det_ima;
    }

}
