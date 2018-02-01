package tt.licencias;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import tt.general.Accesos;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped
public class LupControlLicencias implements Serializable {

    private static final long serialVersionUID = 8719471112716638L;
    @Inject
    Login cbean;
    private List<CatPaises> paises;
    private CatControlLicenciasEncabezado catcontrollicenciasencabezado;
    private CatControlLicenciasEdelphyn catcontrollicenciasedelphyn;
    private CatControlLicenciasEdelphynDet catcontrollicenciasedelphyndet;
    private CatControlLicenciasInterfaces catcontrollicenciasinterfaces;
    private List<CatControlLicenciasEncabezado> encabezado;
    private List<CatControlLicenciasEdelphyn> edelphyn;
    private List<CatControlLicenciasEdelphynDet> edelphyndet;
    private List<CatControlLicenciasInterfaces> interfaces;
    private String cor_ctr, cod_pai, nom_hos, nom_ciu, nom_ent, det_sta, det_obs, nompai;
    private String ede_cor_det, ede_det_ver_war, ede_det_ver_scr,
            ede_det_sis_ope, ede_det_cad_ede, ede_det_cad_jav, ede_det_obs, ede_det_sta;
    private int ede_det_lic_sol, ede_det_lic_def;
    private String int_cor_det, int_det_nom_int, int_det_sis_ope, int_det_ver_int, int_fec_cad_int, int_fec_ult_env, int_det_sta, int_det_obs;
    private String det_cor_det, det_det_lic, det_fec_cad_ede, det_det_ubi;
    private Date dcadede, dcadjav, dfeccadint, dfecultenv, dfeccadede;
    private String tabindex, tabenable;

    public LupControlLicencias() {
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public CatControlLicenciasEncabezado getCatcontrollicenciasencabezado() {
        return catcontrollicenciasencabezado;
    }

    public void setCatcontrollicenciasencabezado(CatControlLicenciasEncabezado catcontrollicenciasencabezado) {
        this.catcontrollicenciasencabezado = catcontrollicenciasencabezado;
    }

    public CatControlLicenciasEdelphyn getCatcontrollicenciasedelphyn() {
        return catcontrollicenciasedelphyn;
    }

    public void setCatcontrollicenciasedelphyn(CatControlLicenciasEdelphyn catcontrollicenciasedelphyn) {
        this.catcontrollicenciasedelphyn = catcontrollicenciasedelphyn;
    }

    public CatControlLicenciasEdelphynDet getCatcontrollicenciasedelphyndet() {
        return catcontrollicenciasedelphyndet;
    }

    public void setCatcontrollicenciasedelphyndet(CatControlLicenciasEdelphynDet catcontrollicenciasedelphyndet) {
        this.catcontrollicenciasedelphyndet = catcontrollicenciasedelphyndet;
    }

    public CatControlLicenciasInterfaces getCatcontrollicenciasinterfaces() {
        return catcontrollicenciasinterfaces;
    }

    public void setCatcontrollicenciasinterfaces(CatControlLicenciasInterfaces catcontrollicenciasinterfaces) {
        this.catcontrollicenciasinterfaces = catcontrollicenciasinterfaces;
    }

    public List<CatControlLicenciasEncabezado> getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(List<CatControlLicenciasEncabezado> encabezado) {
        this.encabezado = encabezado;
    }

    public List<CatControlLicenciasEdelphyn> getEdelphyn() {
        return edelphyn;
    }

    public void setEdelphyn(List<CatControlLicenciasEdelphyn> edelphyn) {
        this.edelphyn = edelphyn;
    }

    public List<CatControlLicenciasEdelphynDet> getEdelphyndet() {
        return edelphyndet;
    }

    public void setEdelphyndet(List<CatControlLicenciasEdelphynDet> edelphyndet) {
        this.edelphyndet = edelphyndet;
    }

    public List<CatControlLicenciasInterfaces> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<CatControlLicenciasInterfaces> interfaces) {
        this.interfaces = interfaces;
    }

    public String getCor_ctr() {
        return cor_ctr;
    }

    public void setCor_ctr(String cor_ctr) {
        this.cor_ctr = cor_ctr;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_hos() {
        return nom_hos;
    }

    public void setNom_hos(String nom_hos) {
        this.nom_hos = nom_hos;
    }

    public String getNom_ciu() {
        return nom_ciu;
    }

    public void setNom_ciu(String nom_ciu) {
        this.nom_ciu = nom_ciu;
    }

    public String getNom_ent() {
        return nom_ent;
    }

    public void setNom_ent(String nom_ent) {
        this.nom_ent = nom_ent;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getEde_cor_det() {
        return ede_cor_det;
    }

    public void setEde_cor_det(String ede_cor_det) {
        this.ede_cor_det = ede_cor_det;
    }

    public int getEde_det_lic_sol() {
        return ede_det_lic_sol;
    }

    public void setEde_det_lic_sol(int ede_det_lic_sol) {
        this.ede_det_lic_sol = ede_det_lic_sol;
    }

    public int getEde_det_lic_def() {
        return ede_det_lic_def;
    }

    public void setEde_det_lic_def(int ede_det_lic_def) {
        this.ede_det_lic_def = ede_det_lic_def;
    }

    public String getEde_det_ver_war() {
        return ede_det_ver_war;
    }

    public void setEde_det_ver_war(String ede_det_ver_war) {
        this.ede_det_ver_war = ede_det_ver_war;
    }

    public String getEde_det_ver_scr() {
        return ede_det_ver_scr;
    }

    public void setEde_det_ver_scr(String ede_det_ver_scr) {
        this.ede_det_ver_scr = ede_det_ver_scr;
    }

    public String getEde_det_sis_ope() {
        return ede_det_sis_ope;
    }

    public void setEde_det_sis_ope(String ede_det_sis_ope) {
        this.ede_det_sis_ope = ede_det_sis_ope;
    }

    public String getEde_det_cad_ede() {
        return ede_det_cad_ede;
    }

    public void setEde_det_cad_ede(String ede_det_cad_ede) {
        this.ede_det_cad_ede = ede_det_cad_ede;
    }

    public String getEde_det_cad_jav() {
        return ede_det_cad_jav;
    }

    public void setEde_det_cad_jav(String ede_det_cad_jav) {
        this.ede_det_cad_jav = ede_det_cad_jav;
    }

    public String getEde_det_obs() {
        return ede_det_obs;
    }

    public void setEde_det_obs(String ede_det_obs) {
        this.ede_det_obs = ede_det_obs;
    }

    public String getEde_det_sta() {
        return ede_det_sta;
    }

    public void setEde_det_sta(String ede_det_sta) {
        this.ede_det_sta = ede_det_sta;
    }

    public String getInt_cor_det() {
        return int_cor_det;
    }

    public void setInt_cor_det(String int_cor_det) {
        this.int_cor_det = int_cor_det;
    }

    public String getInt_det_nom_int() {
        return int_det_nom_int;
    }

    public void setInt_det_nom_int(String int_det_nom_int) {
        this.int_det_nom_int = int_det_nom_int;
    }

    public String getInt_det_sis_ope() {
        return int_det_sis_ope;
    }

    public void setInt_det_sis_ope(String int_det_sis_ope) {
        this.int_det_sis_ope = int_det_sis_ope;
    }

    public String getInt_det_ver_int() {
        return int_det_ver_int;
    }

    public void setInt_det_ver_int(String int_det_ver_int) {
        this.int_det_ver_int = int_det_ver_int;
    }

    public String getInt_fec_cad_int() {
        return int_fec_cad_int;
    }

    public void setInt_fec_cad_int(String int_fec_cad_int) {
        this.int_fec_cad_int = int_fec_cad_int;
    }

    public String getInt_fec_ult_env() {
        return int_fec_ult_env;
    }

    public void setInt_fec_ult_env(String int_fec_ult_env) {
        this.int_fec_ult_env = int_fec_ult_env;
    }

    public String getInt_det_sta() {
        return int_det_sta;
    }

    public void setInt_det_sta(String int_det_sta) {
        this.int_det_sta = int_det_sta;
    }

    public String getInt_det_obs() {
        return int_det_obs;
    }

    public void setInt_det_obs(String int_det_obs) {
        this.int_det_obs = int_det_obs;
    }

    public String getDet_cor_det() {
        return det_cor_det;
    }

    public void setDet_cor_det(String det_cor_det) {
        this.det_cor_det = det_cor_det;
    }

    public String getDet_det_lic() {
        return det_det_lic;
    }

    public void setDet_det_lic(String det_det_lic) {
        this.det_det_lic = det_det_lic;
    }

    public String getDet_fec_cad_ede() {
        return det_fec_cad_ede;
    }

    public void setDet_fec_cad_ede(String det_fec_cad_ede) {
        this.det_fec_cad_ede = det_fec_cad_ede;
    }

    public String getDet_det_ubi() {
        return det_det_ubi;
    }

    public void setDet_det_ubi(String det_det_ubi) {
        this.det_det_ubi = det_det_ubi;
    }

    public Date getDcadede() {
        return dcadede;
    }

    public void setDcadede(Date dcadede) {
        this.dcadede = dcadede;
    }

    public Date getDcadjav() {
        return dcadjav;
    }

    public void setDcadjav(Date dcadjav) {
        this.dcadjav = dcadjav;
    }

    public Date getDfeccadint() {
        return dfeccadint;
    }

    public void setDfeccadint(Date dfeccadint) {
        this.dfeccadint = dfeccadint;
    }

    public Date getDfecultenv() {
        return dfecultenv;
    }

    public void setDfecultenv(Date dfecultenv) {
        this.dfecultenv = dfecultenv;
    }

    public Date getDfeccadede() {
        return dfeccadede;
    }

    public void setDfeccadede(Date dfeccadede) {
        this.dfeccadede = dfeccadede;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTabenable() {
        return tabenable;
    }

    public void setTabenable(String tabenable) {
        this.tabenable = tabenable;
    }

    public void iniciarventana() {
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        dcadede = null;
        dcadjav = null;
        dfeccadint = null;
        dfecultenv = null;
        dfeccadede = null;

        cor_ctr = "";
        cod_pai = "";
        nom_hos = "";
        nom_ciu = "";
        nom_ent = "";
        det_sta = "";
        det_obs = "";
        nompai = "";
        ede_cor_det = "";
        ede_det_lic_sol = 0;
        ede_det_lic_def = 0;
        ede_det_ver_war = "";
        ede_det_ver_scr = "";
        ede_det_sis_ope = "";
        ede_det_cad_ede = "";
        ede_det_cad_jav = "";
        ede_det_obs = "";
        ede_det_sta = "";
        int_cor_det = "";
        int_det_nom_int = "";
        int_det_sis_ope = "";
        int_det_ver_int = "";
        int_fec_cad_int = "";
        int_fec_ult_env = "";
        int_det_sta = "";
        int_det_obs = "";
        det_cor_det = "";
        det_det_lic = "";
        det_fec_cad_ede = "";
        det_det_ubi = "";

        tabindex = "0";
        tabenable = "true";

        catcontrollicenciasencabezado = new CatControlLicenciasEncabezado();
        catcontrollicenciasedelphyn = new CatControlLicenciasEdelphyn();
        catcontrollicenciasedelphyndet = new CatControlLicenciasEdelphynDet();
        catcontrollicenciasinterfaces = new CatControlLicenciasInterfaces();
        paises = new ArrayList<>();
        encabezado = new ArrayList<>();
        edelphyn = new ArrayList<>();
        edelphyndet = new ArrayList<>();
        interfaces = new ArrayList<>();

        llenarPaises();
        llenarEncabezado();
    }

    public void cerrarventana() {
        cor_ctr = "";
        cod_pai = "";
        nom_hos = "";
        nom_ciu = "";
        nom_ent = "";
        det_sta = "";
        det_obs = "";
        nompai = "";
        ede_cor_det = "";
        ede_det_lic_sol = 0;
        ede_det_lic_def = 0;
        ede_det_ver_war = "";
        ede_det_ver_scr = "";
        ede_det_sis_ope = "";
        ede_det_cad_ede = "";
        ede_det_cad_jav = "";
        ede_det_obs = "";
        ede_det_sta = "";
        int_cor_det = "";
        int_det_nom_int = "";
        int_det_sis_ope = "";
        int_det_ver_int = "";
        int_fec_cad_int = "";
        int_fec_ult_env = "";
        int_det_sta = "";
        int_det_obs = "";
        det_cor_det = "";
        det_det_lic = "";
        det_fec_cad_ede = "";
        det_det_ubi = "";

        tabindex = "0";
        tabenable = "true";

        catcontrollicenciasencabezado = null;
        catcontrollicenciasedelphyn = null;
        catcontrollicenciasedelphyndet = null;
        catcontrollicenciasinterfaces = null;

        paises.clear();
        encabezado.clear();
        edelphyn.clear();
        edelphyndet.clear();
        interfaces.clear();
    }

    public void nuevo() {
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        RequestContext.getCurrentInstance().execute("PF('wvListaLupLic').clearFilters()");
        dcadede = null;
        dcadjav = null;
        dfeccadint = null;
        dfecultenv = null;
        dfeccadede = null;

        cor_ctr = "";
        cod_pai = "";
        nom_hos = "";
        nom_ciu = "";
        nom_ent = "";
        det_sta = "";
        det_obs = "";
        nompai = "";
        ede_cor_det = "";
        ede_det_lic_sol = 0;
        ede_det_lic_def = 0;
        ede_det_ver_war = "";
        ede_det_ver_scr = "";
        ede_det_sis_ope = "";
        ede_det_cad_ede = "";
        ede_det_cad_jav = "";
        ede_det_obs = "";
        ede_det_sta = "";
        int_cor_det = "";
        int_det_nom_int = "";
        int_det_sis_ope = "";
        int_det_ver_int = "";
        int_fec_cad_int = "";
        int_fec_ult_env = "";
        int_det_sta = "";
        int_det_obs = "";
        det_cor_det = "";
        det_det_lic = "";
        det_fec_cad_ede = "";
        det_det_ubi = "";

        tabindex = "1";
        tabenable = "true";

        catcontrollicenciasencabezado = null;
        catcontrollicenciasedelphyn = null;
        catcontrollicenciasedelphyndet = null;
        catcontrollicenciasinterfaces = null;
        edelphyn.clear();
        edelphyndet.clear();
        interfaces.clear();
    }

    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado del Catálogo de Paises LupControlLicencias. " + e.getMessage());
        }
    }

    public void llenarEncabezado() {
        try {
            RequestContext.getCurrentInstance().execute("PF('wvListaLupLic').clearFilters()");
            catcontrollicenciasencabezado = null;
            catcontrollicenciasedelphyn = null;
            catcontrollicenciasinterfaces = null;
            encabezado.clear();

            String mQuery = "select enc.cor_ctr, enc.cod_pai, enc.nom_hos, enc.nom_ciu, enc.nom_ent, enc.det_sta, enc.det_obs, pai.nom_pai "
                    + "from lup_ctr_lic_enc as enc "
                    + "inner join cat_pai as pai on enc.cod_pai = pai.cod_pai "
                    + "order by enc.cor_ctr;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezado.add(new CatControlLicenciasEncabezado(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Encabezado LupControlLicencias. " + e.getMessage());
        }
    }

    public void llenarEncabezado2() {
        try {
            RequestContext.getCurrentInstance().execute("PF('wvListaLupLic').clearFilters()");
            catcontrollicenciasedelphyn = null;
            catcontrollicenciasinterfaces = null;
            encabezado.clear();

            String mQuery = "select enc.cor_ctr, enc.cod_pai, enc.nom_hos, enc.nom_ciu, enc.nom_ent, enc.det_sta, enc.det_obs, pai.nom_pai "
                    + "from lup_ctr_lic_enc as enc "
                    + "inner join cat_pai as pai on enc.cod_pai = pai.cod_pai "
                    + "order by enc.cor_ctr;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezado.add(new CatControlLicenciasEncabezado(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Encabezado LupControlLicencias 2. " + e.getMessage());
        }
    }

    public void llenarEdelphyn() {
        try {
            catcontrollicenciasedelphyn = null;
            edelphyn.clear();

            String mQuery = "select cor_enc, cor_det, det_lic_sol, det_lic_def, det_ver_war, det_ver_scr, det_sis_ope, "
                    + "case det_cad_ede when null then '' else date_format(det_cad_ede,'%d/%m/%Y') end, "
                    + "case det_cad_jav when null then '' else date_format(det_cad_jav,'%d/%m/%Y') end, "
                    + "det_obs, det_sta "
                    + "from lup_ctr_lic_ede "
                    + "where cor_enc = " + cor_ctr + " "
                    + "order by cor_det;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                edelphyn.add(new CatControlLicenciasEdelphyn(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11)
                ));

                ede_cor_det = resVariable.getString(2);
                ede_det_lic_sol = Integer.valueOf(resVariable.getString(3));
                ede_det_lic_def = Integer.valueOf(resVariable.getString(4));
                ede_det_ver_war = resVariable.getString(5);
                ede_det_ver_scr = resVariable.getString(6);
                ede_det_sis_ope = resVariable.getString(7);
                ede_det_cad_ede = resVariable.getString(8);
                ede_det_cad_jav = resVariable.getString(9);
                ede_det_obs = resVariable.getString(10);
                ede_det_sta = resVariable.getString(11);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                if (!"".equals(ede_det_cad_ede) && ede_det_cad_ede != null) {
                    try {
                        dcadede = format.parse(ede_det_cad_ede);
                    } catch (Exception ex) {

                    }
                } else {
                    dcadede = null;
                }
                if (!"".equals(ede_det_cad_jav) && ede_det_cad_jav != null) {
                    try {
                        dcadjav = format.parse(ede_det_cad_jav);
                    } catch (Exception ex) {

                    }
                } else {
                    dcadjav = null;
                }

            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de eDelphyn LupControlLicencias. " + e.getMessage());
        }
    }

    public void llenarEdelphyn2() {
        try {
            catcontrollicenciasedelphyn = null;
            edelphyn.clear();

            String mQuery = "select cor_enc, cor_det, det_lic_sol, det_lic_def, det_ver_war, det_ver_scr, det_sis_ope, "
                    + "case det_cad_ede when null then '' else date_format(det_cad_ede,'%d/%m/%Y') end, "
                    + "case det_cad_jav when null then '' else date_format(det_cad_jav,'%d/%m/%Y') end, "
                    + "det_obs, det_sta "
                    + "from lup_ctr_lic_ede "
                    + "where cor_enc = " + cor_ctr + " "
                    + "order by cor_det;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                edelphyn.add(new CatControlLicenciasEdelphyn(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11)
                ));

            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de eDelphyn LupControlLicencias 2. " + e.getMessage());
        }
    }

    public void llenarEdelphynDet() {
        try {
            catcontrollicenciasedelphyndet = null;
            edelphyndet.clear();

            String mQuery = "select cor_enc, cor_det, det_lic, "
                    + "case fec_cad_ede when null then '' else date_format(fec_cad_ede,'%d/%m/%Y') end, "
                    + "det_ubi from lup_ctr_lic_ede_det "
                    + "where cor_enc = " + cor_ctr + " "
                    + "order by cor_det;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                edelphyndet.add(new CatControlLicenciasEdelphynDet(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de eDelphyn Detalle LupControlLicencias. " + e.getMessage());
        }
    }

    public void llenarInterfaces() {
        try {
            catcontrollicenciasinterfaces = null;
            interfaces.clear();

            String mQuery = "select cor_enc, cor_det, det_nom_int, det_sis_ope, det_ver_int, "
                    + "case fec_cad_int when null then '' else date_format(fec_cad_int,'%d/%m/%Y') end, "
                    + "case fec_ult_env when null then '' else date_format(fec_ult_env,'%d/%m/%Y') end, "
                    + "det_sta, det_obs "
                    + "from lup_ctr_lic_int "
                    + "where cor_enc = " + cor_ctr + " "
                    + "order by cor_det;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                interfaces.add(new CatControlLicenciasInterfaces(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Interfaces LupControlLicencias. " + e.getMessage());
        }
    }

    public boolean validarControl() {
        boolean mValidar = true;

        if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un País.", 2);
        }
        if ("".equals(nom_hos)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Cliente.", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if (!"0".equals(maccesos.strQuerySQLvariable("select count(cor_ctr) from lup_ctr_lic_enc "
                + "where upper(nom_hos)='" + nom_hos.toUpperCase() + "' and cod_pai = " + cod_pai + ";"))
                && "".equals(cor_ctr)) {
            mValidar = false;
            addMessage("Validar Datos", "El Cliente ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void guardarControl() {
        String mQuery = "";
        if (validarControl()) {
            try {
                if (ede_det_cad_ede.equals("")) {
                    ede_det_cad_ede = "null";
                } else {
                    ede_det_cad_ede = "str_to_date('" + ede_det_cad_ede + "','%d/%m/%Y')";
                }
                if (ede_det_cad_jav.equals("")) {
                    ede_det_cad_jav = "null";
                } else {
                    ede_det_cad_jav = "str_to_date('" + ede_det_cad_jav + "','%d/%m/%Y')";
                }

                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(cor_ctr)) {
                    mQuery = "select ifnull(max(cor_ctr),0)+1 as codigo from lup_ctr_lic_enc;";
                    cor_ctr = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into lup_ctr_lic_enc (cor_ctr, cod_pai, nom_hos, nom_ciu, nom_ent, det_sta, det_obs) "
                            + "values (" + cor_ctr + "," + cod_pai + ",'" + nom_hos + "','" + nom_ciu + "','" + nom_ent + "','" + det_sta + "','" + det_obs + "');";
                    mAccesos.dmlSQLvariable(mQuery);
                    mQuery = "insert into lup_ctr_lic_ede (cor_enc,cor_det,det_lic_sol,det_lic_def,det_ver_war,det_ver_scr,det_sis_ope,det_cad_ede,det_cad_jav,det_obs,det_sta) "
                            + "values (" + cor_ctr + ",1," + ede_det_lic_sol + "," + ede_det_lic_def + ",'" + ede_det_ver_war + "','" + ede_det_ver_scr + "','" + ede_det_sis_ope
                            + "'," + ede_det_cad_ede + "," + ede_det_cad_jav + ",'" + ede_det_obs + "','" + ede_det_sta + "');";
                    mAccesos.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update lup_ctr_lic_enc SET "
                            + " cod_pai = " + cod_pai + ", "
                            + " nom_hos = '" + nom_hos + "', "
                            + " nom_ciu = '" + nom_ciu + "', "
                            + " nom_ent = '" + nom_ent + "', "
                            + " det_sta = '" + det_sta + "', "
                            + " det_obs = '" + det_obs + "' "
                            + "WHERE cor_ctr = " + cor_ctr + ";";
                    mAccesos.dmlSQLvariable(mQuery);
                    mQuery = "update lup_ctr_lic_ede SET "
                            + " det_lic_sol = " + ede_det_lic_sol + ", "
                            + " det_lic_def = " + ede_det_lic_def + ", "
                            + " det_ver_war = '" + ede_det_ver_war + "', "
                            + " det_ver_scr = '" + ede_det_ver_scr + "', "
                            + " det_sis_ope = '" + ede_det_sis_ope + "', "
                            + " det_cad_ede = " + ede_det_cad_ede + ", "
                            + " det_cad_jav = " + ede_det_cad_jav + ", "
                            + " det_obs = '" + ede_det_obs + "', "
                            + " det_sta = '" + ede_det_sta + "' "
                            + "WHERE cor_enc = " + cor_ctr + " and cor_det = 1;";
                    mAccesos.dmlSQLvariable(mQuery);
                    llenarEdelphyn2();
                }

                mAccesos.Desconectar();
                addMessage("Guardar Control", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Control", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Control. " + e.getMessage() + " Query: " + mQuery);
            }
            //llenarMovimientos();
            //nuevo();
            llenarEncabezado2();
            tabenable = "false";
        }

    }

    public void eliminarControl() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if (!"".equals(cor_ctr)) {
            try {
                mQuery = "delete from lup_ctr_lic_ede_det where cor_enc=" + cor_ctr + ";";
                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "delete from lup_ctr_lic_ede where cor_enc=" + cor_ctr + ";";
                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "delete from lup_ctr_lic_int where cor_enc=" + cor_ctr + ";";
                mAccesos.dmlSQLvariable(mQuery);
                mQuery = "delete from lup_ctr_lic_enc where cor_ctr=" + cor_ctr + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Control", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Control", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Control. " + e.getMessage() + " Query: " + mQuery);
            }
            //llenarMovimientos();
            llenarEncabezado();
            nuevo();
            tabindex = "0";
        } else {
            addMessage("Eliminar Control", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validarEdelphynDet() {
        boolean mValidar = true;

        if ("".equals(cor_ctr) || "0".equals(cor_ctr)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Control", 2);
        }
        if ("".equals(det_det_lic)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar una Licencia", 2);
        }
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if (!"0".equals(maccesos.strQuerySQLvariable("select count(cor_enc) from lup_ctr_lic_ede_det "
                + "where upper(det_lic)='" + det_det_lic.toUpperCase() + "' and cor_enc = " + cor_ctr + ";"))) {
            mValidar = false;
            addMessage("Validar Datos", "La Licencia ya existe", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void guardarEdelphynDet() {
        String mQuery = "";
        if (validarEdelphynDet()) {
            try {
                if (det_fec_cad_ede.equals("")) {
                    det_fec_cad_ede = "null";
                } else {
                    det_fec_cad_ede = "str_to_date('" + det_fec_cad_ede + "','%d/%m/%Y')";
                }
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();

                mQuery = "select ifnull(max(cor_det),0)+1 as codigo from lup_ctr_lic_ede_det where cor_enc=" + cor_ctr + ";";
                det_cor_det = mAccesos.strQuerySQLvariable(mQuery);
                mQuery = "insert into lup_ctr_lic_ede_det (cor_enc,cor_det,det_lic,fec_cad_ede,det_ubi) "
                        + "values (" + cor_ctr + "," + det_cor_det + ",'" + det_det_lic + "'," + det_fec_cad_ede + ",'" + det_det_ubi + "');";

                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Licencia", "Información Almacenada con éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar Licencia", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Detalle eDelphyn. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEdelphynDet();
            det_cor_det = "";
            det_det_lic = "";
            det_fec_cad_ede = "";
            det_det_ubi = "";
            dfeccadede = null;
            catcontrollicenciasedelphyndet = null;
        }

    }

    public void eliminarEdelphynDet() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if (!"".equals(cor_ctr)) {
            try {
                mQuery = "delete from lup_ctr_lic_ede_det where cor_enc=" + cor_ctr + " and cor_det = " + det_cor_det + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Control", "Información Eliminada con éxito", 1);
            } catch (Exception e) {
                addMessage("Eliminar Licencia", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Detalle eDelphyn. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEdelphynDet();
            det_cor_det = "";
            det_det_lic = "";
            det_fec_cad_ede = "";
            det_det_ubi = "";
            dfeccadede = null;
            catcontrollicenciasedelphyndet = null;
        } else {
            addMessage("Eliminar Licencia", "Debe elegir un Registro", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validarInterface() {
        boolean mValidar = true;

        if ("".equals(cor_ctr) || "0".equals(cor_ctr)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Control", 2);
        }
        if ("".equals(int_det_nom_int)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Interface", 2);
        }

        return mValidar;

    }

    public void guardarInterface() {
        String mQuery = "";
        if (validarInterface()) {
            try {
                if (int_fec_cad_int.equals("")) {
                    int_fec_cad_int = "null";
                } else {
                    int_fec_cad_int = "str_to_date('" + int_fec_cad_int + "','%d/%m/%Y')";
                }
                if (int_fec_ult_env.equals("")) {
                    int_fec_ult_env = "null";
                } else {
                    int_fec_ult_env = "str_to_date('" + int_fec_ult_env + "','%d/%m/%Y')";
                }
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();

                mQuery = "select ifnull(max(cor_det),0)+1 as codigo from lup_ctr_lic_int where cor_enc=" + cor_ctr + ";";
                int_cor_det = mAccesos.strQuerySQLvariable(mQuery);
                mQuery = "insert into lup_ctr_lic_int (cor_enc,cor_det,det_nom_int,det_sis_ope,det_ver_int,fec_cad_int,fec_ult_env,det_sta,det_obs) "
                        + "values (" + cor_ctr + "," + int_cor_det + ",'" + int_det_nom_int + "','" + int_det_sis_ope + "','" + int_det_ver_int + "'," + int_fec_cad_int
                        + "," + int_fec_ult_env + ",'" + int_det_sta + "','" + int_det_obs + "');";
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Interface", "Información Almacenada con éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar Interface", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Interface. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarInterfaces();
            dfeccadint = null;
            dfecultenv = null;
            int_cor_det = "";
            int_det_nom_int = "";
            int_det_sis_ope = "";
            int_det_ver_int = "";
            int_fec_cad_int = "";
            int_fec_ult_env = "";
            int_det_sta = "";
            int_det_obs = "";

            catcontrollicenciasinterfaces = null;
        }

    }

    public void eliminarInterface() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if (!"".equals(cor_ctr)) {
            try {
                mQuery = "delete from lup_ctr_lic_int where cor_enc=" + cor_ctr + " and cor_det = " + int_cor_det + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Interface", "Información Eliminada con éxito", 1);
            } catch (Exception e) {
                addMessage("Eliminar Interface", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Interface. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarInterfaces();
            dfeccadint = null;
            dfecultenv = null;
            int_cor_det = "";
            int_det_nom_int = "";
            int_det_sis_ope = "";
            int_det_ver_int = "";
            int_fec_cad_int = "";
            int_fec_ult_env = "";
            int_det_sta = "";
            int_det_obs = "";

            catcontrollicenciasinterfaces = null;
        } else {
            addMessage("Eliminar Interface", "Debe elegir un Registro", 2);
        }
        mAccesos.Desconectar();

    }

    public void onRowSelectEncabezado(SelectEvent event) {
        cor_ctr = ((CatControlLicenciasEncabezado) event.getObject()).getCor_ctr();
        cod_pai = ((CatControlLicenciasEncabezado) event.getObject()).getCod_pai();
        nom_hos = ((CatControlLicenciasEncabezado) event.getObject()).getNom_hos();
        nom_ciu = ((CatControlLicenciasEncabezado) event.getObject()).getNom_ciu();
        nom_ent = ((CatControlLicenciasEncabezado) event.getObject()).getNom_ent();
        det_sta = ((CatControlLicenciasEncabezado) event.getObject()).getDet_sta();
        det_obs = ((CatControlLicenciasEncabezado) event.getObject()).getDet_obs();
        nompai = ((CatControlLicenciasEncabezado) event.getObject()).getNompai();

        llenarEdelphyn();
        llenarEdelphynDet();
        llenarInterfaces();

        tabenable = "false";

        det_cor_det = "";
        det_det_lic = "";
        det_fec_cad_ede = "";
        det_det_ubi = "";
        dfeccadede = null;

        catcontrollicenciasedelphyndet = null;

        dfeccadint = null;
        dfecultenv = null;
        int_cor_det = "";
        int_det_nom_int = "";
        int_det_sis_ope = "";
        int_det_ver_int = "";
        int_fec_cad_int = "";
        int_fec_ult_env = "";
        int_det_sta = "";
        int_det_obs = "";

        catcontrollicenciasinterfaces = null;

    }

    public void onRowUnselectEncabezado() {

        dcadede = null;
        dcadjav = null;
        dfeccadint = null;
        dfecultenv = null;
        dfeccadede = null;

        cor_ctr = "";
        cod_pai = "";
        nom_hos = "";
        nom_ciu = "";
        nom_ent = "";
        det_sta = "";
        det_obs = "";
        nompai = "";
        ede_cor_det = "";
        ede_det_lic_sol = 0;
        ede_det_lic_def = 0;
        ede_det_ver_war = "";
        ede_det_ver_scr = "";
        ede_det_sis_ope = "";
        ede_det_cad_ede = "";
        ede_det_cad_jav = "";
        ede_det_obs = "";
        ede_det_sta = "";
        int_cor_det = "";
        int_det_nom_int = "";
        int_det_sis_ope = "";
        int_det_ver_int = "";
        int_fec_cad_int = "";
        int_fec_ult_env = "";
        int_det_sta = "";
        int_det_obs = "";
        det_cor_det = "";
        det_det_lic = "";
        det_fec_cad_ede = "";
        det_det_ubi = "";

        catcontrollicenciasencabezado = null;
        catcontrollicenciasedelphyn = null;
        catcontrollicenciasedelphyndet = null;
        catcontrollicenciasinterfaces = null;
        edelphyn.clear();
        edelphyndet.clear();
        interfaces.clear();

        tabenable = "true";
    }

    public void onRowSelectLicencia(SelectEvent event) {
        det_cor_det = ((CatControlLicenciasEdelphynDet) event.getObject()).getCor_det();
        det_det_lic = ((CatControlLicenciasEdelphynDet) event.getObject()).getDet_lic();
        det_fec_cad_ede = ((CatControlLicenciasEdelphynDet) event.getObject()).getFec_cad_ede();
        det_det_ubi = ((CatControlLicenciasEdelphynDet) event.getObject()).getDet_ubi();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (!"".equals(det_fec_cad_ede) && det_fec_cad_ede != null) {
            try {
                dfeccadede = format.parse(det_fec_cad_ede);
            } catch (Exception ex) {

            }
        } else {
            dfeccadede = null;
        }
    }

    public void onRowUnSelectLicencia() {
        det_cor_det = "";
        det_det_lic = "";
        det_fec_cad_ede = "";
        det_det_ubi = "";
        dfeccadede = null;
        catcontrollicenciasedelphyndet = null;
    }

    public void onRowSelectInterface(SelectEvent event) {
        int_cor_det = ((CatControlLicenciasInterfaces) event.getObject()).getCor_det();
        int_det_nom_int = ((CatControlLicenciasInterfaces) event.getObject()).getDet_nom_int();
        int_det_sis_ope = ((CatControlLicenciasInterfaces) event.getObject()).getDet_sis_ope();
        int_det_ver_int = ((CatControlLicenciasInterfaces) event.getObject()).getDet_ver_int();
        int_fec_cad_int = ((CatControlLicenciasInterfaces) event.getObject()).getFec_cad_int();
        int_fec_ult_env = ((CatControlLicenciasInterfaces) event.getObject()).getFec_ult_env();
        int_det_sta = ((CatControlLicenciasInterfaces) event.getObject()).getDet_sta();
        int_det_obs = ((CatControlLicenciasInterfaces) event.getObject()).getDet_obs();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (!"".equals(int_fec_cad_int) && int_fec_cad_int != null) {
            try {
                dfeccadint = format.parse(int_fec_cad_int);
            } catch (Exception ex) {

            }
        } else {
            dfeccadint = null;
        }
        if (!"".equals(int_fec_ult_env) && int_fec_ult_env != null) {
            try {
                dfecultenv = format.parse(int_fec_ult_env);
            } catch (Exception ex) {

            }
        } else {
            dfecultenv = null;
        }
    }

    public void onRowUnSelectInterface() {
        dfeccadint = null;
        dfecultenv = null;
        int_cor_det = "";
        int_det_nom_int = "";
        int_det_sis_ope = "";
        int_det_ver_int = "";
        int_fec_cad_int = "";
        int_fec_ult_env = "";
        int_det_sta = "";
        int_det_obs = "";

        catcontrollicenciasinterfaces = null;
    }

    public void dateSelectedcadede(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        ede_det_cad_ede = format.format(date);
    }

    public void dateSelectedcadjav(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        ede_det_cad_jav = format.format(date);
    }

    public void dateSelectedcadededet(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        det_fec_cad_ede = format.format(date);
    }

    public void dateSelectedcadint(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        int_fec_cad_int = format.format(date);
    }

    public void dateSelectedultenv(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        int_fec_ult_env = format.format(date);
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "listaluplic":
                tabindex = "0";
                break;
            case "clienteluplic":
                tabindex = "1";
                break;
            case "edelphynluplic":
                tabindex = "2";
                break;
            case "interfacesluplic":
                tabindex = "3";
                break;
        }

    }

    public void addMessage(String summary, String detail, int tipo) {
        FacesMessage message = new FacesMessage();
        switch (tipo) {
            case 1:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
                break;
            case 2:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
                break;
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
