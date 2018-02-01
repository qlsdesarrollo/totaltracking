package tt.general;

import java.io.Serializable;

public class ListaCortaTransaccionExistencias implements Serializable {

    private String cod_tra, flg_ent_sal;
    private Double det_can, cos_uni;

    public ListaCortaTransaccionExistencias() {
    }

    public ListaCortaTransaccionExistencias(String cod_tra, String flg_ent_sal, Double det_can, Double cos_uni) {
        this.cod_tra = cod_tra;
        this.flg_ent_sal = flg_ent_sal;
        this.det_can = det_can;
        this.cos_uni = cos_uni;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getFlg_ent_sal() {
        return flg_ent_sal;
    }

    public void setFlg_ent_sal(String flg_ent_sal) {
        this.flg_ent_sal = flg_ent_sal;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public Double getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(Double cos_uni) {
        this.cos_uni = cos_uni;
    }

    

    

}
