
package tt.mantenimiento;

import java.io.Serializable;


public class CatMantenimientosAcc implements Serializable{
    
    private String cod_lis_equ,cod_man,det_man,fec_man,cod_pai,det_can,des_ite,cod_usu,flg_sol,nompai;

    public CatMantenimientosAcc() {
    }

    public CatMantenimientosAcc(String cod_lis_equ, String cod_man, String det_man, String fec_man, String cod_pai, String det_can, String des_ite, String cod_usu, String flg_sol, String nompai) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.det_man = det_man;
        this.fec_man = fec_man;
        this.cod_pai = cod_pai;
        this.det_can = det_can;
        this.des_ite = des_ite;
        this.cod_usu = cod_usu;
        this.flg_sol = flg_sol;
        this.nompai = nompai;
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

    public String getDet_man() {
        return det_man;
    }

    public void setDet_man(String det_man) {
        this.det_man = det_man;
    }

    public String getFec_man() {
        return fec_man;
    }

    public void setFec_man(String fec_man) {
        this.fec_man = fec_man;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getDes_ite() {
        return des_ite;
    }

    public void setDes_ite(String des_ite) {
        this.des_ite = des_ite;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getFlg_sol() {
        return flg_sol;
    }

    public void setFlg_sol(String flg_sol) {
        this.flg_sol = flg_sol;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }
    
    
    
    
}
