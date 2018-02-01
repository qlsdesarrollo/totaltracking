package tt.mantenimiento;

import java.io.Serializable;

public class CatMantenimientos implements Serializable {

    private String cod_lis_equ, cod_man, cod_tip, det_obs, fec_ini, fec_fin, det_sta,
            cod_usu, nomtip, status, datraso, color, cod_per, periodo, flg_ext, cod_alt, tequipo, nserie, npais, ord_por, nordpor, nusu;
    private int codigoalt;
    private String ordenafecha;

    public CatMantenimientos() {
    }

    public CatMantenimientos(String cod_lis_equ, String cod_man, String cod_tip, String det_obs, String fec_ini, String fec_fin, String det_sta, String cod_usu, String nomtip, String status, String datraso, String color, String cod_per, String periodo, String flg_ext, String cod_alt, String tequipo, String nserie, String npais, String ord_por, String nordpor, String nusu, int codigoalt, String ordenafecha) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.cod_tip = cod_tip;
        this.det_obs = det_obs;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
        this.det_sta = det_sta;
        this.cod_usu = cod_usu;
        this.nomtip = nomtip;
        this.status = status;
        this.datraso = datraso;
        this.color = color;
        this.cod_per = cod_per;
        this.periodo = periodo;
        this.flg_ext = flg_ext;
        this.cod_alt = cod_alt;
        this.tequipo = tequipo;
        this.nserie = nserie;
        this.npais = npais;
        this.ord_por = ord_por;
        this.nordpor = nordpor;
        this.nusu = nusu;
        this.codigoalt = codigoalt;
        this.ordenafecha = ordenafecha;
    }

    

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_man() {
        return cod_man;
    }

    public void setCod_man(String cod_man) {
        this.cod_man = cod_man;
    }

    public String getCod_tip() {
        return cod_tip;
    }

    public void setCod_tip(String cod_tip) {
        this.cod_tip = cod_tip;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNomtip() {
        return nomtip;
    }

    public void setNomtip(String nomtip) {
        this.nomtip = nomtip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatraso() {
        return datraso;
    }

    public void setDatraso(String datraso) {
        this.datraso = datraso;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCod_per() {
        return cod_per;
    }

    public void setCod_per(String cod_per) {
        this.cod_per = cod_per;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getFlg_ext() {
        return flg_ext;
    }

    public void setFlg_ext(String flg_ext) {
        this.flg_ext = flg_ext;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

    public String getTequipo() {
        return tequipo;
    }

    public void setTequipo(String tequipo) {
        this.tequipo = tequipo;
    }

    public String getNserie() {
        return nserie;
    }

    public void setNserie(String nserie) {
        this.nserie = nserie;
    }

    public String getNpais() {
        return npais;
    }

    public void setNpais(String npais) {
        this.npais = npais;
    }

    public String getOrd_por() {
        return ord_por;
    }

    public void setOrd_por(String ord_por) {
        this.ord_por = ord_por;
    }

    public String getNordpor() {
        return nordpor;
    }

    public void setNordpor(String nordpor) {
        this.nordpor = nordpor;
    }

    public String getNusu() {
        return nusu;
    }

    public void setNusu(String nusu) {
        this.nusu = nusu;
    }

    public int getCodigoalt() {
        return codigoalt;
    }

    public void setCodigoalt(int codigoalt) {
        this.codigoalt = codigoalt;
    }

    public String getOrdenafecha() {
        return ordenafecha;
    }

    public void setOrdenafecha(String ordenafecha) {
        this.ordenafecha = ordenafecha;
    }
    
    

}
