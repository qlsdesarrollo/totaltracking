package tt.productos;

import java.io.Serializable;

public class CatDetLotComp implements Serializable {

    private String cod_mae, cod_det, det_lot, fec_ven;
    private Double det_can_pen;

    public CatDetLotComp() {
    }

    public CatDetLotComp(String cod_mae, String cod_det, String det_lot, String fec_ven, Double det_can_pen) {
        this.cod_mae = cod_mae;
        this.cod_det = cod_det;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.det_can_pen = det_can_pen;
    }

    public String getCod_mae() {
        return cod_mae;
    }

    public void setCod_mae(String cod_mae) {
        this.cod_mae = cod_mae;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getDet_lot() {
        return det_lot;
    }

    public void setDet_lot(String det_lot) {
        this.det_lot = det_lot;
    }

    public String getFec_ven() {
        return fec_ven;
    }

    public void setFec_ven(String fec_ven) {
        this.fec_ven = fec_ven;
    }

    public Double getDet_can_pen() {
        return det_can_pen;
    }

    public void setDet_can_pen(Double det_can_pen) {
        this.det_can_pen = det_can_pen;
    }

}
