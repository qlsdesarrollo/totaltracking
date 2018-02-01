package tt.productos;

import java.io.Serializable;

public class CatSolDet implements Serializable {

    private String cod_mae, cod_det, cod_art, cod_ite, des_ite;
    private Double det_can_sol, det_can_ent, det_can_pen, det_can_con;
    private String cod_uni_med, fec_cie;
    private Double cos_uni;
    private String det_sta, estado, codigo, unidad;

    public CatSolDet() {
    }

    public CatSolDet(String cod_mae, String cod_det, String cod_art, String cod_ite, String des_ite, Double det_can_sol, Double det_can_ent, Double det_can_pen, Double det_can_con, String cod_uni_med, String fec_cie, Double cos_uni, String det_sta, String estado, String codigo, String unidad) {
        this.cod_mae = cod_mae;
        this.cod_det = cod_det;
        this.cod_art = cod_art;
        this.cod_ite = cod_ite;
        this.des_ite = des_ite;
        this.det_can_sol = det_can_sol;
        this.det_can_ent = det_can_ent;
        this.det_can_pen = det_can_pen;
        this.det_can_con = det_can_con;
        this.cod_uni_med = cod_uni_med;
        this.fec_cie = fec_cie;
        this.cos_uni = cos_uni;
        this.det_sta = det_sta;
        this.estado = estado;
        this.codigo = codigo;
        this.unidad = unidad;
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

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
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

    public Double getDet_can_sol() {
        return det_can_sol;
    }

    public void setDet_can_sol(Double det_can_sol) {
        this.det_can_sol = det_can_sol;
    }

    public Double getDet_can_ent() {
        return det_can_ent;
    }

    public void setDet_can_ent(Double det_can_ent) {
        this.det_can_ent = det_can_ent;
    }

    public Double getDet_can_pen() {
        return det_can_pen;
    }

    public void setDet_can_pen(Double det_can_pen) {
        this.det_can_pen = det_can_pen;
    }

    public Double getDet_can_con() {
        return det_can_con;
    }

    public void setDet_can_con(Double det_can_con) {
        this.det_can_con = det_can_con;
    }

    public String getCod_uni_med() {
        return cod_uni_med;
    }

    public void setCod_uni_med(String cod_uni_med) {
        this.cod_uni_med = cod_uni_med;
    }

    public String getFec_cie() {
        return fec_cie;
    }

    public void setFec_cie(String fec_cie) {
        this.fec_cie = fec_cie;
    }

    public Double getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(Double cos_uni) {
        this.cos_uni = cos_uni;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
