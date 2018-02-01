
package paquetes;

import java.io.Serializable;


public class CatPlantillas implements Serializable{
    
    private String cod_pla,cod_equ,tip_man,det_hor,usu_sug,nomequ,nomtipo,nomusu;

    public CatPlantillas() {
    }

    public CatPlantillas(String cod_pla, String cod_equ, String tip_man, String det_hor, String usu_sug, String nomequ, String nomtipo, String nomusu) {
        this.cod_pla = cod_pla;
        this.cod_equ = cod_equ;
        this.tip_man = tip_man;
        this.det_hor = det_hor;
        this.usu_sug = usu_sug;
        this.nomequ = nomequ;
        this.nomtipo = nomtipo;
        this.nomusu = nomusu;
    }

    public String getCod_pla() {
        return cod_pla;
    }

    public void setCod_pla(String cod_pla) {
        this.cod_pla = cod_pla;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getTip_man() {
        return tip_man;
    }

    public void setTip_man(String tip_man) {
        this.tip_man = tip_man;
    }

    public String getDet_hor() {
        return det_hor;
    }

    public void setDet_hor(String det_hor) {
        this.det_hor = det_hor;
    }

    public String getUsu_sug() {
        return usu_sug;
    }

    public void setUsu_sug(String usu_sug) {
        this.usu_sug = usu_sug;
    }

    public String getNomequ() {
        return nomequ;
    }

    public void setNomequ(String nomequ) {
        this.nomequ = nomequ;
    }

    public String getNomtipo() {
        return nomtipo;
    }

    public void setNomtipo(String nomtipo) {
        this.nomtipo = nomtipo;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }
    
    
    
}
