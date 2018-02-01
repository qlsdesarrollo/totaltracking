package tt.productos;

import java.io.Serializable;

public class CatSolRec implements Serializable {

    private String cod_rec, cod_mae, det_mae, fec_rec;
    private double det_can, det_can_con;
    private String cod_uni_med, flg_usu_alm, cod_usu_rec, cod_his, det_sta;

    public CatSolRec() {
    }

    public CatSolRec(String cod_rec, String cod_mae, String det_mae, String fec_rec, double det_can, double det_can_con, String cod_uni_med, String flg_usu_alm, String cod_usu_rec, String cod_his, String det_sta) {
        this.cod_rec = cod_rec;
        this.cod_mae = cod_mae;
        this.det_mae = det_mae;
        this.fec_rec = fec_rec;
        this.det_can = det_can;
        this.det_can_con = det_can_con;
        this.cod_uni_med = cod_uni_med;
        this.flg_usu_alm = flg_usu_alm;
        this.cod_usu_rec = cod_usu_rec;
        this.cod_his = cod_his;
        this.det_sta = det_sta;
    }

    public String getCod_rec() {
        return cod_rec;
    }

    public void setCod_rec(String cod_rec) {
        this.cod_rec = cod_rec;
    }

    public String getCod_mae() {
        return cod_mae;
    }

    public void setCod_mae(String cod_mae) {
        this.cod_mae = cod_mae;
    }

    public String getDet_mae() {
        return det_mae;
    }

    public void setDet_mae(String det_mae) {
        this.det_mae = det_mae;
    }

    public String getFec_rec() {
        return fec_rec;
    }

    public void setFec_rec(String fec_rec) {
        this.fec_rec = fec_rec;
    }

    public double getDet_can() {
        return det_can;
    }

    public void setDet_can(double det_can) {
        this.det_can = det_can;
    }

    public double getDet_can_con() {
        return det_can_con;
    }

    public void setDet_can_con(double det_can_con) {
        this.det_can_con = det_can_con;
    }

    public String getCod_uni_med() {
        return cod_uni_med;
    }

    public void setCod_uni_med(String cod_uni_med) {
        this.cod_uni_med = cod_uni_med;
    }

    public String getFlg_usu_alm() {
        return flg_usu_alm;
    }

    public void setFlg_usu_alm(String flg_usu_alm) {
        this.flg_usu_alm = flg_usu_alm;
    }

    public String getCod_usu_rec() {
        return cod_usu_rec;
    }

    public void setCod_usu_rec(String cod_usu_rec) {
        this.cod_usu_rec = cod_usu_rec;
    }

    public String getCod_his() {
        return cod_his;
    }

    public void setCod_his(String cod_his) {
        this.cod_his = cod_his;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

}
