package tt.it;

import java.io.Serializable;

public class CatLisEqu implements Serializable {

    private String cod_lis_equ, cod_pai, tip_equ, cod_equ, nompai, nomcli;

    public CatLisEqu() {
    }

    public CatLisEqu(String cod_lis_equ, String cod_pai, String tip_equ, String cod_equ, String nompai, String nomcli) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_pai = cod_pai;
        this.tip_equ = tip_equ;
        this.cod_equ = cod_equ;
        this.nompai = nompai;
        this.nomcli = nomcli;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getTip_equ() {
        return tip_equ;
    }

    public void setTip_equ(String tip_equ) {
        this.tip_equ = tip_equ;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNomcli() {
        return nomcli;
    }

    public void setNomcli(String nomcli) {
        this.nomcli = nomcli;
    }

}
