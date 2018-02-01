package tt.mantenimiento;

import java.io.Serializable;

public class CatListaPiezas implements Serializable {

    private String cod_exi, cod_pie, cod_pai, cod_bod, cod_ubi, det_can, cos_pro, nompie, nompai, nombod, nomubi;

    public CatListaPiezas() {
    }

    public CatListaPiezas(String cod_exi, String cod_pie, String cod_pai, String cod_bod, String cod_ubi, String det_can, String cos_pro, String nompie, String nompai, String nombod, String nomubi) {
        this.cod_exi = cod_exi;
        this.cod_pie = cod_pie;
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.det_can = det_can;
        this.cos_pro = cos_pro;
        this.nompie = nompie;
        this.nompai = nompai;
        this.nombod = nombod;
        this.nomubi = nomubi;
    }

    public String getCod_exi() {
        return cod_exi;
    }

    public void setCod_exi(String cod_exi) {
        this.cod_exi = cod_exi;
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

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getCos_pro() {
        return cos_pro;
    }

    public void setCos_pro(String cos_pro) {
        this.cos_pro = cos_pro;
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
