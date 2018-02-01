package tt.mantenimiento;

import java.io.Serializable;

public class CatPiezasConExistencia implements Serializable{
    
    private String cod_pie,cod_pai,cod_bod,cod_ubi,can_res,can_sto,nompie,nompai,nombod,nomubi;

    public CatPiezasConExistencia() {
    }

    public CatPiezasConExistencia(String cod_pie, String cod_pai, String cod_bod, String cod_ubi, String can_res, String can_sto, String nompie, String nompai, String nombod, String nomubi) {
        this.cod_pie = cod_pie;
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.can_res = can_res;
        this.can_sto = can_sto;
        this.nompie = nompie;
        this.nompai = nompai;
        this.nombod = nombod;
        this.nomubi = nomubi;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
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

    public String getCan_res() {
        return can_res;
    }

    public void setCan_res(String can_res) {
        this.can_res = can_res;
    }

    public String getCan_sto() {
        return can_sto;
    }

    public void setCan_sto(String can_sto) {
        this.can_sto = can_sto;
    }

    public String getNompie() {
        return nompie;
    }

    public void setNompie(String nompie) {
        this.nompie = nompie;
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
    
    
}
