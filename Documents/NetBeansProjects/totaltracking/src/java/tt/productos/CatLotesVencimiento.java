package tt.productos;

import java.io.Serializable;

public class CatLotesVencimiento implements Serializable {

    private String cod_pai, cod_bod, cod_ubi, cod_art, cod_emb, num_lot, fec_ven, exi_act, nompai, mombod, nomubi, nomart, nomemb;

    public CatLotesVencimiento() {
    }

    public CatLotesVencimiento(String cod_pai, String cod_bod, String cod_ubi, String cod_art, String cod_emb, String num_lot, String fec_ven, String exi_act, String nompai, String mombod, String nomubi, String nomart, String nomemb) {
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.cod_art = cod_art;
        this.cod_emb = cod_emb;
        this.num_lot = num_lot;
        this.fec_ven = fec_ven;
        this.exi_act = exi_act;
        this.nompai = nompai;
        this.mombod = mombod;
        this.nomubi = nomubi;
        this.nomart = nomart;
        this.nomemb = nomemb;
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

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getCod_emb() {
        return cod_emb;
    }

    public void setCod_emb(String cod_emb) {
        this.cod_emb = cod_emb;
    }

    public String getNum_lot() {
        return num_lot;
    }

    public void setNum_lot(String num_lot) {
        this.num_lot = num_lot;
    }

    public String getFec_ven() {
        return fec_ven;
    }

    public void setFec_ven(String fec_ven) {
        this.fec_ven = fec_ven;
    }

    public String getExi_act() {
        return exi_act;
    }

    public void setExi_act(String exi_act) {
        this.exi_act = exi_act;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getMombod() {
        return mombod;
    }

    public void setMombod(String mombod) {
        this.mombod = mombod;
    }

    public String getNomubi() {
        return nomubi;
    }

    public void setNomubi(String nomubi) {
        this.nomubi = nomubi;
    }

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
    }

    public String getNomemb() {
        return nomemb;
    }

    public void setNomemb(String nomemb) {
        this.nomemb = nomemb;
    }

}
