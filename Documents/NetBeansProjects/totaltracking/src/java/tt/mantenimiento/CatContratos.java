package tt.mantenimiento;

import java.io.Serializable;

public class CatContratos implements Serializable {

    private String cod_lis_equ, cod_con, cod_ref, des_inf, fec_con, fec_exp;

    public CatContratos() {
    }

    public CatContratos(String cod_lis_equ, String cod_con, String cod_ref, String des_inf, String fec_con, String fec_exp) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_con = cod_con;
        this.cod_ref = cod_ref;
        this.des_inf = des_inf;
        this.fec_con = fec_con;
        this.fec_exp = fec_exp;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_con() {
        return cod_con;
    }

    public void setCod_con(String cod_con) {
        this.cod_con = cod_con;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getDes_inf() {
        return des_inf;
    }

    public void setDes_inf(String des_inf) {
        this.des_inf = des_inf;
    }

    public String getFec_con() {
        return fec_con;
    }

    public void setFec_con(String fec_con) {
        this.fec_con = fec_con;
    }

    public String getFec_exp() {
        return fec_exp;
    }

    public void setFec_exp(String fec_exp) {
        this.fec_exp = fec_exp;
    }

}
