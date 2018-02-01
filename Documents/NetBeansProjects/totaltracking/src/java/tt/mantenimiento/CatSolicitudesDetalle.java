package tt.mantenimiento;

import java.io.Serializable;

public class CatSolicitudesDetalle implements Serializable {

    private String cod_mae, cod_det, cod_pai, cod_bod, cod_ubi, cod_ite, des_ite, det_can_sol, det_can_ent, det_can_pen, non_sto, det_sta, fec_cie, cos_uni, nompai, nombod, nomubi;

    public CatSolicitudesDetalle() {
    }

    public CatSolicitudesDetalle(String cod_mae, String cod_det, String cod_pai, String cod_bod, String cod_ubi, String cod_ite, String des_ite, String det_can_sol, String det_can_ent, String det_can_pen, String non_sto, String det_sta, String fec_cie, String cos_uni, String nompai, String nombod, String nomubi) {
        this.cod_mae = cod_mae;
        this.cod_det = cod_det;
        this.cod_pai = cod_pai;
        this.cod_bod = cod_bod;
        this.cod_ubi = cod_ubi;
        this.cod_ite = cod_ite;
        this.des_ite = des_ite;
        this.det_can_sol = det_can_sol;
        this.det_can_ent = det_can_ent;
        this.det_can_pen = det_can_pen;
        this.non_sto = non_sto;
        this.det_sta = det_sta;
        this.fec_cie = fec_cie;
        this.cos_uni = cos_uni;
        this.nompai = nompai;
        this.nombod = nombod;
        this.nomubi = nomubi;
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

    public String getCod_ite() {
        return cod_ite;
    }

    public void setCod_ite(String cod_ite) {
        this.cod_ite = cod_ite;
    }

    public String getDes_ite() {
        return des_ite;
    }

    public void setDes_ite(String des_ite) {
        this.des_ite = des_ite;
    }

    public String getDet_can_sol() {
        return det_can_sol;
    }

    public void setDet_can_sol(String det_can_sol) {
        this.det_can_sol = det_can_sol;
    }

    public String getDet_can_ent() {
        return det_can_ent;
    }

    public void setDet_can_ent(String det_can_ent) {
        this.det_can_ent = det_can_ent;
    }

    public String getDet_can_pen() {
        return det_can_pen;
    }

    public void setDet_can_pen(String det_can_pen) {
        this.det_can_pen = det_can_pen;
    }

    public String getNon_sto() {
        return non_sto;
    }

    public void setNon_sto(String non_sto) {
        this.non_sto = non_sto;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getFec_cie() {
        return fec_cie;
    }

    public void setFec_cie(String fec_cie) {
        this.fec_cie = fec_cie;
    }

    public String getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(String cos_uni) {
        this.cos_uni = cos_uni;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
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

}
