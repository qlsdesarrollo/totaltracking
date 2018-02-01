package paquetes;

import java.io.Serializable;

public class CatSalidas implements Serializable {

    private String id_sal, fec_sal, tip_mov, doc_sal, cod_cli, flg_anu, det_obs;

    public CatSalidas(String id_sal, String fec_sal, String tip_mov, String doc_sal, String cod_cli, String flg_anu, String det_obs) {
        this.id_sal = id_sal;
        this.fec_sal = fec_sal;
        this.tip_mov = tip_mov;
        this.doc_sal = doc_sal;
        this.cod_cli = cod_cli;
        this.flg_anu = flg_anu;
        this.det_obs = det_obs;
    }

    public CatSalidas() {
    }

    public String getId_sal() {
        return id_sal;
    }

    public void setId_sal(String id_sal) {
        this.id_sal = id_sal;
    }

    public String getFec_sal() {
        return fec_sal;
    }

    public void setFec_sal(String fec_sal) {
        this.fec_sal = fec_sal;
    }

    public String getTip_mov() {
        return tip_mov;
    }

    public void setTip_mov(String tip_mov) {
        this.tip_mov = tip_mov;
    }

    public String getDoc_sal() {
        return doc_sal;
    }

    public void setDoc_sal(String doc_sal) {
        this.doc_sal = doc_sal;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getFlg_anu() {
        return flg_anu;
    }

    public void setFlg_anu(String flg_anu) {
        this.flg_anu = flg_anu;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

}
