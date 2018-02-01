package tt.productos;

import java.io.Serializable;

public class CatPedidosDetalles implements Serializable {

    private String cod_ped, ped_det, cod_bod, cod_ubi, cod_art, det_lot, fec_ven;
    private Double can_sol, can_ent, can_pen, det_cos, can_sol_con;
    private String uni_med_con, det_sta, nombod, nomubi, nomart, refart, altart, tra_mae, tra_det, fec_tra, nomunimed, lot_sug;

    public CatPedidosDetalles() {
    }

    public CatPedidosDetalles(String cod_ped, String ped_det, String cod_bod, String cod_ubi, String cod_art, String det_lot, String fec_ven, Double can_sol, Double can_ent, Double can_pen, Double det_cos, Double can_sol_con, String uni_med_con, String det_sta, String nombod, String nomubi, String nomart, String refart, String altart, String tra_mae, String tra_det, String fec_tra, String nomunimed, String lot_sug) {
        this.cod_ped = cod_ped;
        this.ped_det = ped_det;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.cod_art = cod_art;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.can_sol = can_sol;
        this.can_ent = can_ent;
        this.can_pen = can_pen;
        this.det_cos = det_cos;
        this.can_sol_con = can_sol_con;
        this.uni_med_con = uni_med_con;
        this.det_sta = det_sta;
        this.nombod = nombod;
        this.nomubi = nomubi;
        this.nomart = nomart;
        this.refart = refart;
        this.altart = altart;
        this.tra_mae = tra_mae;
        this.tra_det = tra_det;
        this.fec_tra = fec_tra;
        this.nomunimed = nomunimed;
        this.lot_sug = lot_sug;
    }

    public String getCod_ped() {
        return cod_ped;
    }

    public void setCod_ped(String cod_ped) {
        this.cod_ped = cod_ped;
    }

    public String getPed_det() {
        return ped_det;
    }

    public void setPed_det(String ped_det) {
        this.ped_det = ped_det;
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

    public Double getCan_sol() {
        return can_sol;
    }

    public void setCan_sol(Double can_sol) {
        this.can_sol = can_sol;
    }

    public Double getCan_ent() {
        return can_ent;
    }

    public void setCan_ent(Double can_ent) {
        this.can_ent = can_ent;
    }

    public Double getCan_pen() {
        return can_pen;
    }

    public void setCan_pen(Double can_pen) {
        this.can_pen = can_pen;
    }

    public Double getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(Double det_cos) {
        this.det_cos = det_cos;
    }

    public Double getCan_sol_con() {
        return can_sol_con;
    }

    public void setCan_sol_con(Double can_sol_con) {
        this.can_sol_con = can_sol_con;
    }

    public String getUni_med_con() {
        return uni_med_con;
    }

    public void setUni_med_con(String uni_med_con) {
        this.uni_med_con = uni_med_con;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
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

    public String getRefart() {
        return refart;
    }

    public void setRefart(String refart) {
        this.refart = refart;
    }

    public String getAltart() {
        return altart;
    }

    public void setAltart(String altart) {
        this.altart = altart;
    }

    public String getTra_mae() {
        return tra_mae;
    }

    public void setTra_mae(String tra_mae) {
        this.tra_mae = tra_mae;
    }

    public String getTra_det() {
        return tra_det;
    }

    public void setTra_det(String tra_det) {
        this.tra_det = tra_det;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getNomunimed() {
        return nomunimed;
    }

    public void setNomunimed(String nomunimed) {
        this.nomunimed = nomunimed;
    }

    public String getLot_sug() {
        return lot_sug;
    }

    public void setLot_sug(String lot_sug) {
        this.lot_sug = lot_sug;
    }

}
