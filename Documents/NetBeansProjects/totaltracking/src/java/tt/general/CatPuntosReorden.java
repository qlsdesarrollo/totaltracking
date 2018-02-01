package tt.general;

import java.io.Serializable;

public class CatPuntosReorden implements Serializable {

    private String cod_pai, cod_bod, cod_ubi, cod_pie, pun_reo, nompai, nombod, nompie;

    public CatPuntosReorden() {
    }

    public CatPuntosReorden(String cod_pai, String cod_bod, String cod_ubi, String cod_pie, String pun_reo, String nompai, String nombod, String nompie) {
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.cod_pie = cod_pie;
        this.pun_reo = pun_reo;
        this.nompai = nompai;
        this.nombod = nombod;
        this.nompie = nompie;
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

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getPun_reo() {
        return pun_reo;
    }

    public void setPun_reo(String pun_reo) {
        this.pun_reo = pun_reo;
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

    public String getNompie() {
        return nompie;
    }

    public void setNompie(String nompie) {
        this.nompie = nompie;
    }

}
