package tt.mantenimiento;

import java.io.Serializable;

public class CatMantenimientosPie implements Serializable {

    private String cod_lis_equ, cod_man, det_man, fec_man, cod_pai, cod_bod, cod_ubi, det_can, cod_pie, num_ser, cod_usu, nompai, nombod, nomubi, nomusu, nompie, flg_sol;

    public CatMantenimientosPie() {
    }

    public CatMantenimientosPie(String cod_lis_equ, String cod_man, String det_man, String fec_man, String cod_pai, String cod_bod, String cod_ubi, String det_can, String cod_pie, String num_ser, String cod_usu, String nompai, String nombod, String nomubi, String nomusu, String nompie, String flg_sol) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.det_man = det_man;
        this.fec_man = fec_man;
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.det_can = det_can;
        this.cod_pie = cod_pie;
        this.num_ser = num_ser;
        this.cod_usu = cod_usu;
        this.nompai = nompai;
        this.nombod = nombod;
        this.nomubi = nomubi;
        this.nomusu = nomusu;
        this.nompie = nompie;
        this.flg_sol = flg_sol;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_man() {
        return cod_man;
    }

    public void setCod_man(String cod_man) {
        this.cod_man = cod_man;
    }

    public String getDet_man() {
        return det_man;
    }

    public void setDet_man(String det_man) {
        this.det_man = det_man;
    }

    public String getFec_man() {
        return fec_man;
    }

    public void setFec_man(String fec_man) {
        this.fec_man = fec_man;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getCod_ubi() {
        return cod_ubi;
    }

    public void setCod_ubi(String cod_ubi) {
        this.cod_ubi = cod_ubi;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getNum_ser() {
        return num_ser;
    }

    public void setNum_ser(String num_ser) {
        this.num_ser = num_ser;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNombod() {
        return nombod;
    }

    public void setNombod(String nombod) {
        this.nombod = nombod;
    }

    public String getNomubi() {
        return nomubi;
    }

    public void setNomubi(String nomubi) {
        this.nomubi = nomubi;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

    public String getNompie() {
        return nompie;
    }

    public void setNompie(String nompie) {
        this.nompie = nompie;
    }

    public String getFlg_sol() {
        return flg_sol;
    }

    public void setFlg_sol(String flg_sol) {
        this.flg_sol = flg_sol;
    }

}
