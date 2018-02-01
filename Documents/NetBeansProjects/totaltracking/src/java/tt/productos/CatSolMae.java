package tt.productos;

import java.io.Serializable;

public class CatSolMae implements Serializable {

    private String cod_mae, cod_alt, cod_pai, cod_pro, fec_sol, cod_usu_sol, cod_usu_apr,
            cod_usu_rec, det_obs, det_sta, fec_cie, nompais, nomproveedor, nomusu, nomrecibe, estado;

    public CatSolMae() {
    }

    public CatSolMae(String cod_mae, String cod_alt, String cod_pai, String cod_pro, String fec_sol, String cod_usu_sol, String cod_usu_apr, String cod_usu_rec, String det_obs, String det_sta, String fec_cie, String nompais, String nomproveedor, String nomusu, String nomrecibe, String estado) {
        this.cod_mae = cod_mae;
        this.cod_alt = cod_alt;
        this.cod_pai = cod_pai;
        this.cod_pro = cod_pro;
        this.fec_sol = fec_sol;
        this.cod_usu_sol = cod_usu_sol;
        this.cod_usu_apr = cod_usu_apr;
        this.cod_usu_rec = cod_usu_rec;
        this.det_obs = det_obs;
        this.det_sta = det_sta;
        this.fec_cie = fec_cie;
        this.nompais = nompais;
        this.nomproveedor = nomproveedor;
        this.nomusu = nomusu;
        this.nomrecibe = nomrecibe;
        this.estado = estado;
    }

    public String getCod_mae() {
        return cod_mae;
    }

    public void setCod_mae(String cod_mae) {
        this.cod_mae = cod_mae;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getFec_sol() {
        return fec_sol;
    }

    public void setFec_sol(String fec_sol) {
        this.fec_sol = fec_sol;
    }

    public String getCod_usu_sol() {
        return cod_usu_sol;
    }

    public void setCod_usu_sol(String cod_usu_sol) {
        this.cod_usu_sol = cod_usu_sol;
    }

    public String getCod_usu_apr() {
        return cod_usu_apr;
    }

    public void setCod_usu_apr(String cod_usu_apr) {
        this.cod_usu_apr = cod_usu_apr;
    }

    public String getCod_usu_rec() {
        return cod_usu_rec;
    }

    public void setCod_usu_rec(String cod_usu_rec) {
        this.cod_usu_rec = cod_usu_rec;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
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

    public String getNompais() {
        return nompais;
    }

    public void setNompais(String nompais) {
        this.nompais = nompais;
    }

    public String getNomproveedor() {
        return nomproveedor;
    }

    public void setNomproveedor(String nomproveedor) {
        this.nomproveedor = nomproveedor;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

    public String getNomrecibe() {
        return nomrecibe;
    }

    public void setNomrecibe(String nomrecibe) {
        this.nomrecibe = nomrecibe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
