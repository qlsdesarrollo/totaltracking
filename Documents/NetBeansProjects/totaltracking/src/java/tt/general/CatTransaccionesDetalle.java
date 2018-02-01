package tt.general;

import java.io.Serializable;

public class CatTransaccionesDetalle implements Serializable {

    private String cod_tra, cod_det, cod_bod, cod_ubi, cod_art, det_lot, fec_ven, det_can, 
            nombod, nomubi, nomart, det_can_con, uni_med_con, det_cos, codrefart, nomunimed, ord_dia_ant;

    public CatTransaccionesDetalle() {
    }

    public CatTransaccionesDetalle(String cod_tra, String cod_det, String cod_bod, String cod_ubi, String cod_art, String det_lot, String fec_ven, String det_can, String nombod, String nomubi, String nomart, String det_can_con, String uni_med_con, String det_cos, String codrefart, String nomunimed, String ord_dia_ant) {
        this.cod_tra = cod_tra;
        this.cod_det = cod_det;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.cod_art = cod_art;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.det_can = det_can;
        this.nombod = nombod;
        this.nomubi = nomubi;
        this.nomart = nomart;
        this.det_can_con = det_can_con;
        this.uni_med_con = uni_med_con;
        this.det_cos = det_cos;
        this.codrefart = codrefart;
        this.nomunimed = nomunimed;
        this.ord_dia_ant = ord_dia_ant;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
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

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
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

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
    }

    public String getDet_can_con() {
        return det_can_con;
    }

    public void setDet_can_con(String det_can_con) {
        this.det_can_con = det_can_con;
    }

    public String getUni_med_con() {
        return uni_med_con;
    }

    public void setUni_med_con(String uni_med_con) {
        this.uni_med_con = uni_med_con;
    }

    public String getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(String det_cos) {
        this.det_cos = det_cos;
    }

    public String getCodrefart() {
        return codrefart;
    }

    public void setCodrefart(String codrefart) {
        this.codrefart = codrefart;
    }

    public String getNomunimed() {
        return nomunimed;
    }

    public void setNomunimed(String nomunimed) {
        this.nomunimed = nomunimed;
    }

    public String getOrd_dia_ant() {
        return ord_dia_ant;
    }

    public void setOrd_dia_ant(String ord_dia_ant) {
        this.ord_dia_ant = ord_dia_ant;
    }

}
