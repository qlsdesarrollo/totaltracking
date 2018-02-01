package tt.general;

import java.io.Serializable;

public class CatProveedores implements Serializable {

    private String cod_pro, cod_pai, nom_pro, per_con, tel_con, det_mai, flg_pro_pie, det_dir;

    public CatProveedores() {
    }

    public CatProveedores(String cod_pro, String cod_pai, String nom_pro, String per_con, String tel_con, String det_mai, String flg_pro_pie, String det_dir) {
        this.cod_pro = cod_pro;
        this.cod_pai = cod_pai;
        this.nom_pro = nom_pro;
        this.per_con = per_con;
        this.tel_con = tel_con;
        this.det_mai = det_mai;
        this.flg_pro_pie = flg_pro_pie;
        this.det_dir = det_dir;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public String getPer_con() {
        return per_con;
    }

    public void setPer_con(String per_con) {
        this.per_con = per_con;
    }

    public String getTel_con() {
        return tel_con;
    }

    public void setTel_con(String tel_con) {
        this.tel_con = tel_con;
    }

    public String getDet_mai() {
        return det_mai;
    }

    public void setDet_mai(String det_mai) {
        this.det_mai = det_mai;
    }

    public String getFlg_pro_pie() {
        return flg_pro_pie;
    }

    public void setFlg_pro_pie(String flg_pro_pie) {
        this.flg_pro_pie = flg_pro_pie;
    }

    public String getDet_dir() {
        return det_dir;
    }

    public void setDet_dir(String det_dir) {
        this.det_dir = det_dir;
    }

}
