package tt.general;

import java.io.Serializable;

public class CatExistencias implements Serializable {

    private String cod_bod, id_ubi, cod_art, exi_act, nomart, nombod, nomubi, codref, cod_emb;

    public CatExistencias() {
    }

    public CatExistencias(String cod_bod, String id_ubi, String cod_art, String exi_act, String nomart, String nombod, String nomubi, String codref, String cod_emb) {
        this.cod_bod = cod_bod;
        this.id_ubi = id_ubi;
        this.cod_art = cod_art;
        this.exi_act = exi_act;
        this.nomart = nomart;
        this.nombod = nombod;
        this.nomubi = nomubi;
        this.codref = codref;
        this.cod_emb = cod_emb;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getId_ubi() {
        return id_ubi;
    }

    public void setId_ubi(String id_ubi) {
        this.id_ubi = id_ubi;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getExi_act() {
        return exi_act;
    }

    public void setExi_act(String exi_act) {
        this.exi_act = exi_act;
    }

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
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

    public String getCodref() {
        return codref;
    }

    public void setCodref(String codref) {
        this.codref = codref;
    }

    public String getCod_emb() {
        return cod_emb;
    }

    public void setCod_emb(String cod_emb) {
        this.cod_emb = cod_emb;
    }

}
