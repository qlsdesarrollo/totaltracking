package tt.general;

import java.io.Serializable;

public class CatRemovidos implements Serializable {

    private String cod_lis_equ, cod_rem, fec_rem, cod_pai, cod_cli, det_obs, nompai, nomcli;

    public CatRemovidos() {
    }

    public CatRemovidos(String cod_lis_equ, String cod_rem, String fec_rem, String cod_pai, String cod_cli, String det_obs, String nompai, String nomcli) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_rem = cod_rem;
        this.fec_rem = fec_rem;
        this.cod_pai = cod_pai;
        this.cod_cli = cod_cli;
        this.det_obs = det_obs;
        this.nompai = nompai;
        this.nomcli = nomcli;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_rem() {
        return cod_rem;
    }

    public void setCod_rem(String cod_rem) {
        this.cod_rem = cod_rem;
    }

    public String getFec_rem() {
        return fec_rem;
    }

    public void setFec_rem(String fec_rem) {
        this.fec_rem = fec_rem;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
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
