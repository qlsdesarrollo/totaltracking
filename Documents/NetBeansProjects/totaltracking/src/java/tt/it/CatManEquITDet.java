package tt.it;

public class CatManEquITDet {

    private String cod_lis_equ, cod_man, cod_det, cod_pie, det_obs, fec_acc;
    private Double det_cos;
    private int det_tie;
    private String usu_res, nomusu, anexo, nompieza;

    public CatManEquITDet() {
    }

    public CatManEquITDet(String cod_lis_equ, String cod_man, String cod_det, String cod_pie, String det_obs, String fec_acc, Double det_cos, int det_tie, String usu_res, String nomusu, String anexo, String nompieza) {
        this.cod_lis_equ = cod_lis_equ;
        this.cod_man = cod_man;
        this.cod_det = cod_det;
        this.cod_pie = cod_pie;
        this.det_obs = det_obs;
        this.fec_acc = fec_acc;
        this.det_cos = det_cos;
        this.det_tie = det_tie;
        this.usu_res = usu_res;
        this.nomusu = nomusu;
        this.anexo = anexo;
        this.nompieza = nompieza;
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

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFec_acc() {
        return fec_acc;
    }

    public void setFec_acc(String fec_acc) {
        this.fec_acc = fec_acc;
    }

    public Double getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(Double det_cos) {
        this.det_cos = det_cos;
    }

    public int getDet_tie() {
        return det_tie;
    }

    public void setDet_tie(int det_tie) {
        this.det_tie = det_tie;
    }

    public String getUsu_res() {
        return usu_res;
    }

    public void setUsu_res(String usu_res) {
        this.usu_res = usu_res;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getNompieza() {
        return nompieza;
    }

    public void setNompieza(String nompieza) {
        this.nompieza = nompieza;
    }
    
    

}
