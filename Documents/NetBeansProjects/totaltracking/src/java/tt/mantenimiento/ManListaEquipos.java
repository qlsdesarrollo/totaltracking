package tt.mantenimiento;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatClientes;
import tt.general.CatPaises;
import tt.general.CatProveedores;
import tt.general.CatRemovidos;
import tt.seguridad.CatUsuarios;
import tt.general.Login;

@Named
@ConversationScoped
public class ManListaEquipos implements Serializable {

    private static final long serialVersionUID = 8774836761534938L;
    @Inject
    Login cbean;
    private Accesos maccess;

    private List<CatTipos> tipos;
    private CatMantenimientos catmantenimientos;
    private List<CatMantenimientos> mantenimientos;
    private List<CatProveedores> proveedores;
    private List<CatClientes> clientes;
    private List<CatClientes> clientesb;
    private List<CatPaises> paises;
    private List<CatUsuarios> usuarios;
    private List<CatGarantias> garantias;
    private CatRemovidos catremovidos;
    private List<CatRemovidos> removidos;
    private CatSistemas catsistemas;
    private List<CatSistemas> sistemas;
    private CatContratos catcontratos;
    private List<CatContratos> contratos;
    private List<CatEquipos> equipos;
    private CatListaEquipos catlistaequipos;
    private List<CatListaEquipos> lequipos;

    private CatLisEquAnexos catlisequanexos;
    private List<CatLisEquAnexos> anexos;

    private String cod_lis_equ, cod_pai, cod_equ, cod_pro, cod_cli, num_mod, num_ser, des_equ,
            des_ubi, fec_fab, fec_com, fec_adq, fec_pue_ser, fec_ult_man, cod_bar;

    private String bfiltro;

    private String gar_fec_ini, gar_fec_fin, gar_obs;

    private String cod_equ_b, cod_cli_b, cod_pai_b, num_ser_b;

    private String fec_ret, rem_cod_rem, rem_det_obs;

    private String s_cod_sys, s_det_obs, s_ver_ant, s_ver_act, s_fec_act;

    private String c_cod_con, c_cod_ref, c_des_inf, c_fec_con, c_fec_exp;

    private String m_cod_man, m_cod_tip, m_det_obs, m_fec_ini, m_fec_fin, m_det_sta, m_nomtip, m_estado;

    private Date dfecfab, dfeccom, dfecadq, dfecpueser, dfecultman, dfecret, dfecini, dfecfin, dfecact, dfeccon, dfecexp;

    private boolean edfecfab, edfeccom, edfecadq, edfecpueser, edfecultman, edfecret, edfecini, edfecfin, edfecact, edfeccon, edfecexp;

    private String tabindex, bpais;

    private String rut_img;

    private String mWidth;

    private static DefaultStreamedContent mimagen;

    private String ane_cor_det, ane_tip_ane, ane_rut_nom, ane_det_obs, nombrearchivo;

    private String nombrereporte, nombreexportar;
    private Map<String, Object> parametros;

    public ManListaEquipos() {
    }

    //************************* Funciones Cargar Datos iniciales ***************
    public void iniciarventana() {
        maccess = new Accesos();
        dfecfab = null;
        dfeccom = null;
        dfecadq = null;
        dfecpueser = null;
        dfecultman = null;
        dfecini = null;
        dfecfin = null;
        dfecret = null;
        dfecact = null;
        dfeccon = null;
        dfecexp = null;

        edfecfab = false;
        edfeccom = false;
        edfecadq = false;
        edfecpueser = false;
        edfecultman = false;
        edfecini = false;
        edfecfin = false;
        edfecret = false;
        edfecact = false;
        edfeccon = false;
        edfecexp = false;

        tabindex = "0";

        cod_lis_equ = "";
        cod_pai = cbean.getCod_pai();
        cod_equ = "";
        cod_pro = "";
        cod_cli = "";
        num_mod = "";
        num_ser = "";
        des_equ = "";
        des_ubi = "";
        fec_fab = "";
        fec_com = "";
        fec_adq = "";
        fec_pue_ser = "";
        fec_ult_man = "";
        fec_ret = "";
        cod_bar = "";
        gar_fec_ini = "";
        gar_fec_fin = "";
        gar_obs = "";

        rem_cod_rem = "";
        rem_det_obs = "";

        s_cod_sys = "";
        s_det_obs = "";
        s_ver_ant = "";
        s_ver_act = "";
        s_fec_act = "";
        c_cod_con = "";
        c_cod_ref = "";
        c_des_inf = "";
        c_fec_con = "";
        c_fec_exp = "";
        m_cod_man = "";
        m_cod_tip = "";
        m_det_obs = "";
        m_fec_ini = "";
        m_fec_fin = "";
        m_det_sta = "";
        m_nomtip = "";
        m_estado = "";

        mWidth = "400";

        bpais = cbean.getCod_pai();
        rut_img = "/resources/images/lequ/noimage.png";
        mimagen = null;

        ane_cor_det = "";
        ane_tip_ane = "";
        ane_rut_nom = "";
        ane_det_obs = "";
        nombrearchivo = "Select One File";

        paises = new ArrayList<>();
        equipos = new ArrayList<>();
        proveedores = new ArrayList<>();
        clientes = new ArrayList<>();

        catremovidos = new CatRemovidos();
        catsistemas = new CatSistemas();
        catcontratos = new CatContratos();
        catmantenimientos = new CatMantenimientos();

        removidos = new ArrayList<>();
        mantenimientos = new ArrayList<>();
        sistemas = new ArrayList<>();
        contratos = new ArrayList<>();

        catlistaequipos = new CatListaEquipos();
        lequipos = new ArrayList<>();

        catlisequanexos = new CatLisEquAnexos();
        anexos = new ArrayList<>();

        bfiltro = "";

        parametros = new HashMap<>();

        llenarPaises();
        llenarEquipos();
        llenarProveedores();
        llenarClientes();
        llenarLequipos();

    }

    public void nuevo() {

        dfecfab = null;
        dfeccom = null;
        dfecadq = null;
        dfecpueser = null;
        dfecultman = null;
        dfecini = null;
        dfecfin = null;
        dfecret = null;
        dfecact = null;
        dfeccon = null;
        dfecexp = null;

        edfecfab = false;
        edfeccom = false;
        edfecadq = false;
        edfecpueser = false;
        edfecultman = false;
        edfecini = false;
        edfecfin = false;
        edfecret = false;
        edfecact = false;
        edfeccon = false;
        edfecexp = false;

        tabindex = "1";

        cod_lis_equ = "";
        cod_pai = cbean.getCod_pai();
        m_fec_ini = "";
        m_fec_fin = "";
        m_det_sta = "";
        m_nomtip = "";
        m_estado = "";

        cod_equ = "";
        cod_pro = "";
        cod_cli = "";
        num_mod = "";
        num_ser = "";
        des_equ = "";
        des_ubi = "";
        fec_fab = "";
        fec_com = "";
        fec_adq = "";
        fec_pue_ser = "";
        fec_ult_man = "";
        fec_ret = "";
        cod_bar = "";
        gar_fec_ini = "";
        gar_fec_fin = "";
        gar_obs = "";

        rem_cod_rem = "";
        rem_det_obs = "";

        s_cod_sys = "";
        s_det_obs = "";
        s_ver_ant = "";
        s_ver_act = "";
        s_fec_act = "";
        c_cod_con = "";
        c_cod_ref = "";
        c_des_inf = "";
        c_fec_con = "";
        c_fec_exp = "";
        m_cod_man = "";
        m_cod_tip = "";
        m_det_obs = "";
        m_fec_ini = "";
        m_fec_fin = "";
        m_det_sta = "";
        m_nomtip = "";
        m_estado = "";
        rut_img = "/resources/images/lequ/noimage.png";
        mimagen = null;
        mWidth = "400";

        ane_cor_det = "";
        ane_tip_ane = "";
        ane_rut_nom = "";
        ane_det_obs = "";
        nombrearchivo = "Select One File";

        catlistaequipos = null;

        catremovidos = null;
        catsistemas = null;
        catcontratos = null;
        catmantenimientos = null;

        catlisequanexos = null;
        anexos.clear();

        removidos.clear();
        mantenimientos.clear();
        sistemas.clear();
        contratos.clear();

        llenarPaises();
        llenarEquipos();
        llenarProveedores();
        llenarClientes();

    }

    public void cerrarventana() {
        tabindex = null;

        cod_lis_equ = null;
        cod_pai = null;
        cod_equ = null;
        cod_pro = null;
        cod_cli = null;
        num_mod = null;
        num_ser = null;
        des_equ = null;
        des_ubi = null;
        fec_fab = null;
        fec_com = null;
        fec_adq = null;
        fec_pue_ser = null;
        fec_ult_man = null;
        fec_ret = null;
        cod_bar = null;
        gar_fec_ini = null;
        gar_fec_fin = null;
        gar_obs = null;

        rem_cod_rem = null;
        rem_det_obs = null;

        s_cod_sys = null;
        s_det_obs = null;
        s_ver_ant = null;
        s_ver_act = null;
        s_fec_act = null;
        c_cod_con = null;
        c_cod_ref = null;
        c_des_inf = null;
        c_fec_con = null;
        c_fec_exp = null;
        m_cod_man = null;
        m_cod_tip = null;
        m_det_obs = null;

        mimagen = null;

        ane_cor_det = null;
        ane_tip_ane = null;
        ane_rut_nom = null;
        ane_det_obs = null;
        nombrearchivo = null;

        bfiltro = "";

        paises = null;
        equipos = null;
        proveedores = null;
        clientes = null;

        catremovidos = null;
        catsistemas = null;
        catcontratos = null;
        catmantenimientos = null;

        removidos = null;
        mantenimientos = null;
        sistemas = null;
        contratos = null;

        catlistaequipos = null;
        lequipos = null;

        catlisequanexos = null;
        anexos = null;

        parametros = null;

        maccess = null;

    }

    //******************** Llenado de Catálogos ********************************
    public void llenarPaises() {
        try {
            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by nom_pai;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Países en Lista Equipos. " + e.getMessage());
        }
    }

    public void llenarEquipos() {
        String mQuery = "";
        try {
            equipos.clear();

            mQuery = "select equ.cod_equ, equ.cod_mar,equ.cod_ref, "
                    + "equ.nom_equ, equ.des_equ,mar.nom_mar,equ.det_ima "
                    + "from cat_equ as equ "
                    + "left join cat_mar as mar on mar.id_mar = equ.cod_mar "
                    + "order by equ.cod_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatEquipos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Equipos en Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarLequipos() {
        String mQuery = "";
        try {
            //RequestContext.getCurrentInstance().execute("PF('wvSLE').clearFilters()");
            catlistaequipos = null;
            lequipos.clear();
            String mAnexo = "";
            if (!"0".equals(bpais)) {
                mAnexo = "and lequ.cod_pai = " + bpais + " ";
            }
            /*
            if (!"0".equals(cod_equ_b)) {
                mAnexo = mAnexo + " and lequ.cod_equ =" + cod_equ_b;
            }
            if (!"0".equals(cod_pai_b)) {
                mAnexo = mAnexo + " and lequ.cod_pai =" + cod_pai_b;
            }
            if (!"".equals(num_ser_b)) {
                mAnexo = mAnexo + " and lequ.num_ser ='" + num_ser_b + "'";
            }*/

            mQuery = "select "
                    + "lequ.cod_lis_equ, "
                    + "lequ.cod_pai, "
                    + "lequ.cod_equ, "
                    + "lequ.cod_pro, "
                    + "lequ.cod_cli, "
                    + "lequ.num_mod, "
                    + "lequ.num_ser, "
                    + "lequ.des_equ, "
                    + "lequ.des_ubi, "
                    + "DATE_FORMAT(lequ.fec_fab,'%d/%b/%Y') as ffab, "
                    + "DATE_FORMAT(lequ.fec_com,'%d/%b/%Y') as fcom, "
                    + "DATE_FORMAT(lequ.fec_adq,'%d/%b/%Y') as fadq,"
                    + "DATE_FORMAT(lequ.fec_pue_ser,'%d/%b/%Y') as fps,"
                    + "DATE_FORMAT(lequ.fec_ult_man,'%d/%b/%Y') as fum, "
                    + "DATE_FORMAT(lequ.fec_ret,'%d/%b/%Y') as fret,"
                    + "lequ.cod_bar,"
                    + "cpai.nom_pai as nompai,"
                    + "cequ.nom_equ as nomequ, "
                    + "cpro.nom_pro as nompro, "
                    + "ccli.nom_cli as nomcli "
                    + "from lis_equ as lequ "
                    + "left join cat_pai as cpai on lequ.cod_pai = cpai.cod_pai "
                    + "left join cat_equ as cequ on lequ.cod_equ = cequ.cod_equ "
                    + "left join cat_pro as cpro on lequ.cod_pro = cpro.cod_pro and lequ.cod_pai = cpro.cod_pai "
                    + "left join cat_cli as ccli on lequ.cod_cli = ccli.cod_cli and lequ.cod_pai = ccli.cod_pai "
                    + "where "
                    + "lequ.cod_lis_equ <> 0 "
                    + mAnexo
                    + " order by lequ.cod_pai, lequ.cod_equ, lequ.num_ser,lequ.des_equ,lequ.cod_lis_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lequipos.add(new CatListaEquipos(
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
                        resVariable.getString(11),
                        resVariable.getString(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Lista Equipos ManListaEquipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarLequiposFiltro() {
        String mQuery = "";
        try {
            catlistaequipos = null;
            lequipos.clear();
            String mAnexo = "";
            if (!"".equals(bfiltro)) {
                if ("".equals(bpais) || "0".equals(bpais)) {
                    mAnexo = "and ( "
                            + "upper(cpai.nom_pai) like '%" + bfiltro.toUpperCase() + "%' "
                            + "or upper(cequ.nom_equ) like '%" + bfiltro.toUpperCase() + "%' "
                            + "or upper(lequ.num_ser) like '%" + bfiltro.toUpperCase() + "%' "
                            + "or upper(ccli.nom_cli) like '%" + bfiltro.toUpperCase() + "%' "
                            + ") ";

                } else {
                    mAnexo = "and ( "
                            + "upper(cpai.nom_pai) like '%" + bfiltro.toUpperCase() + "%' "
                            + "or upper(cequ.nom_equ) like '%" + bfiltro.toUpperCase() + "%' "
                            + "or upper(lequ.num_ser) like '%" + bfiltro.toUpperCase() + "%' "
                            + "or upper(ccli.nom_cli) like '%" + bfiltro.toUpperCase() + "%' "
                            + ") "
                            + "and lequ.cod_pai = " + bpais + " ";
                }

            }

            mQuery = "select "
                    + "lequ.cod_lis_equ, "
                    + "lequ.cod_pai, "
                    + "lequ.cod_equ, "
                    + "lequ.cod_pro, "
                    + "lequ.cod_cli, "
                    + "lequ.num_mod, "
                    + "lequ.num_ser, "
                    + "lequ.des_equ, "
                    + "lequ.des_ubi, "
                    + "DATE_FORMAT(lequ.fec_fab,'%d/%b/%Y') as ffab, "
                    + "DATE_FORMAT(lequ.fec_com,'%d/%b/%Y') as fcom, "
                    + "DATE_FORMAT(lequ.fec_adq,'%d/%b/%Y') as fadq,"
                    + "DATE_FORMAT(lequ.fec_pue_ser,'%d/%b/%Y') as fps,"
                    + "DATE_FORMAT(lequ.fec_ult_man,'%d/%b/%Y') as fum, "
                    + "DATE_FORMAT(lequ.fec_ret,'%d/%b/%Y') as fret,"
                    + "lequ.cod_bar,"
                    + "cpai.nom_pai as nompai,"
                    + "cequ.nom_equ as nomequ, "
                    + "cpro.nom_pro as nompro, "
                    + "ccli.nom_cli as nomcli "
                    + "from lis_equ as lequ "
                    + "left join cat_pai as cpai on lequ.cod_pai = cpai.cod_pai "
                    + "left join cat_equ as cequ on lequ.cod_equ = cequ.cod_equ "
                    + "left join cat_pro as cpro on lequ.cod_pro = cpro.cod_pro and lequ.cod_pai = cpro.cod_pai "
                    + "left join cat_cli as ccli on lequ.cod_cli = ccli.cod_cli and lequ.cod_pai = ccli.cod_pai "
                    + "where "
                    + "lequ.cod_lis_equ <> 0 "
                    + mAnexo
                    + " order by lequ.cod_pai, lequ.cod_equ, lequ.num_ser,lequ.des_equ,lequ.cod_lis_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lequipos.add(new CatListaEquipos(
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
                        resVariable.getString(11),
                        resVariable.getString(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Lista Equipos ManListaEquipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarProveedores() {
        String mQuery = "";
        try {
            proveedores.clear();

            mQuery = "select cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie  "
                    + "from cat_pro where flg_pro_pie = 1 order by cod_pro;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                proveedores.add(new CatProveedores(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7), ""
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Proveedores en Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarClientes() {
        String mQuery = "";
        try {
            clientes.clear();

            mQuery = "select cod_cli,cod_pai, nom_cli,per_con,tel_con,det_mai,flg_pro_pie  "
                    + "from cat_cli where cod_pai = " + cod_pai + " and flg_pro_pie = 1 order by cod_cli;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new CatClientes(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7), ""
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Clientes en Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarGarantias() {
        String mQuery = "";
        try {
            gar_obs = "";
            gar_fec_ini = "";
            gar_fec_fin = "";

            mQuery = "select cod_lis_equ, cod_gar, det_obs, "
                    + "date_format(fec_ini,'%d/%b/%Y'), "
                    + "date_format(fec_exp,'%d/%b/%Y') "
                    + "from tbl_gar "
                    + "where cod_lis_equ=" + cod_lis_equ + " "
                    + "order by cod_lis_equ;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                gar_obs = resVariable.getString(3);
                gar_fec_ini = resVariable.getString(4);
                gar_fec_fin = resVariable.getString(5);
            }
            resVariable.close();
            maccess.Desconectar();

            if ("00/00/0000".equals(gar_fec_ini) || "".equals(gar_fec_ini) || gar_fec_ini == null) {
                gar_fec_ini = "";
            } else {
                edfecini = true;
            }
            if ("00/00/0000".equals(gar_fec_fin) || "".equals(gar_fec_fin) || gar_fec_fin == null) {
                gar_fec_fin = "";
            } else {
                edfecfin = true;
            }

            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
            try {
                dfecini = format.parse(gar_fec_ini);
                dfecfin = format.parse(gar_fec_fin);
            } catch (Exception ex) {

            }
        } catch (Exception e) {
            System.out.println("Error en el llenado de Garantías en Lista Equipos. "
                    + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarRemovidos() {
        String mQuery = "";
        try {
            catremovidos = null;
            removidos.clear();

            mQuery = "select "
                    + "rem.cod_lis_equ, "
                    + "rem.cod_rem, "
                    + "case ifnull(rem.fec_rem,0) "
                    + "when 0 then '' "
                    + "else date_format(rem.fec_rem,'%d/%b/%Y') end as fecrem, "
                    + "rem.cod_pai, "
                    + "rem.cod_cli, "
                    + "rem.det_obs, "
                    + "pai.nom_pai, "
                    + "cli.nom_cli "
                    + "from lis_equ_rem as rem "
                    + "left join cat_pai as pai on rem.cod_pai = pai.cod_pai "
                    + "left join cat_cli as cli on rem.cod_pai = cli.cod_pai and rem.cod_cli = cli.cod_cli "
                    + "where rem.cod_lis_equ = " + cod_lis_equ + " "
                    + "order by rem.fec_rem;";
            ResultSet resVariable;
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                removidos.add(new CatRemovidos(
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
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Removidos en Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarSistemas() {
        String mQuery = "";
        try {
            catsistemas = null;
            sistemas.clear();

            mQuery = "select cod_lis_equ,cod_sys,det_obs,ver_ant,ver_act,date_format(fec_act,'%d/%b/%Y') "
                    + "from tbl_sys "
                    + "where cod_lis_equ=" + cod_lis_equ + " "
                    + "order by cod_lis_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                sistemas.add(new CatSistemas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Sistemas en Lista Equipos. "
                    + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarContratos() {
        String mQuery = "";
        try {
            catcontratos = null;
            contratos.clear();

            mQuery = "select cod_lis_equ, cod_con, cod_ref, des_inf, date_format(fec_con,'%d/%b/%Y'), date_format(fec_exp,'%d/%b/%Y') "
                    + "from tbl_con "
                    + "where cod_lis_equ=" + cod_lis_equ + " "
                    + "order by cod_lis_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                contratos.add(new CatContratos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Contratos en Lista Equipos. "
                    + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAnexos() {
        String mQuery = "";
        try {
            catlisequanexos = null;
            anexos.clear();

            mQuery = "select  "
                    + "cod_lis_equ,"
                    + "cor_det,"
                    + "tip_ane,"
                    + "det_nom,"
                    + "det_obs, "
                    + "case tip_ane "
                    + "when 1 then 'PDF' "
                    + "when 2 then 'IMAGE' "
                    + "when 3 then 'OTHER' "
                    + "end as nomtip "
                    + "from lis_equ_ane "
                    + "where cod_lis_equ =" + cod_lis_equ
                    + ";";

            ResultSet resVariable;
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                anexos.add(new CatLisEquAnexos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Anexos en ManListaEquipos" + e.getMessage() + " Query: " + mQuery);
        }
    }

    //********* Funciones Mantenimiento Tablas de Registro equipos *************
    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(cod_equ) || "0".equals(cod_equ)) {
            mValidar = false;
            addMessage("Save", "You have to select an Equipment.", 2);
        }
        if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            mValidar = false;
            addMessage("Save", "You have to select a Country.", 2);
        }
        if ("".equals(num_ser)) {
            mValidar = false;
            addMessage("Save", "You have to enter a Serial Number.", 2);
        }

        String contador = "";
        maccess.Conectar();
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            contador = maccess.strQuerySQLvariable("select count(num_ser) from lis_equ where num_ser='" + num_ser + "';");
        } else {
            contador = maccess.strQuerySQLvariable("select count(num_ser) from lis_equ where num_ser='" + num_ser + "' and cod_lis_equ <> " + cod_lis_equ + ";");
        }
        if (!"0".equals(contador)) {
            mValidar = false;
            addMessage("Save", "Serial Number already exists.", 2);
        }
        maccess.Desconectar();

        return mValidar;

    }

    public void guardar() {
        String mQuery = "", mvalores = "";
        if (validardatos()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");

                if (edfecfab == false || dfecfab == null) {
                    dfecfab = null;
                    fec_fab = "null";
                } else {
                    try {
                        fec_fab = "str_to_date('" + format.format(dfecfab) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        fec_fab = "null";
                    }
                }
                if (edfeccom == false || dfeccom == null) {
                    dfeccom = null;
                    fec_com = "null";
                } else {
                    try {
                        fec_com = "str_to_date('" + format.format(dfeccom) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        fec_com = "null";
                    }
                }
                if (edfecadq == false || dfecadq == null) {
                    dfecadq = null;
                    fec_adq = "null";
                } else {
                    try {
                        fec_adq = "str_to_date('" + format.format(dfecadq) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        fec_adq = "null";
                    }
                }
                if (edfecpueser == false || dfecpueser == null) {
                    dfecpueser = null;
                    fec_pue_ser = "null";
                } else {
                    try {
                        fec_pue_ser = "str_to_date('" + format.format(dfecpueser) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        fec_pue_ser = "null";
                    }
                }

                fec_ult_man = "null";

                if (edfecret == false || dfecret == null) {
                    dfecret = null;
                    fec_ret = "null";
                } else {
                    try {
                        fec_ret = "str_to_date('" + format.format(dfecret) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        fec_ret = "null";
                    }
                }
                if (edfecini == false || dfecini == null) {
                    dfecini = null;
                    gar_fec_ini = "null";
                } else {
                    try {
                        gar_fec_ini = "str_to_date('" + format.format(dfecini) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        gar_fec_ini = "null";
                    }
                }
                if (edfecfin == false || dfecfin == null) {
                    dfecfin = null;
                    gar_fec_fin = "null";
                } else {
                    try {
                        gar_fec_fin = "str_to_date('" + format.format(dfecfin) + "','%d/%b/%Y')";
                    } catch (Exception e) {
                        gar_fec_fin = "null";
                    }
                }

                maccess.Conectar();
                if ("".equals(cod_lis_equ)) {
                    mQuery = "select ifnull(max(cod_lis_equ),0)+1 as codigo from lis_equ;";
                    cod_lis_equ = maccess.strQuerySQLvariable(mQuery);
                    mQuery = "insert into lis_equ ("
                            + "cod_lis_equ,"
                            + "cod_pai,cod_equ,cod_pro,cod_cli,num_mod,num_ser,des_equ,"
                            + "des_ubi,fec_fab,fec_com,fec_adq,fec_pue_ser,fec_ult_man,"
                            + "fec_ret,cod_bar"
                            + ") VALUES ("
                            + cod_lis_equ + ","
                            + cod_pai + ","
                            + cod_equ + ","
                            + cod_pro + ","
                            + cod_cli + ",'"
                            + num_mod + "','"
                            + num_ser + "','"
                            + des_equ + "','"
                            + des_ubi + "',"
                            + fec_fab + ","
                            + fec_com + ","
                            + fec_adq + ","
                            + fec_pue_ser + ","
                            + fec_ult_man + ","
                            + "null,'" + cod_bar + "')";

                    maccess.dmlSQLvariable("insert into tbl_gar (cod_lis_equ,cod_gar,det_obs,fec_ini,fec_exp) values "
                            + "(" + cod_lis_equ + "," + cod_lis_equ + ",'" + gar_obs + "'," + gar_fec_ini + ","
                            + gar_fec_fin + ");");

                } else {
                    mQuery = "update lis_equ SET "
                            + "cod_pai=" + cod_pai
                            + ",cod_equ=" + cod_equ
                            + ",cod_pro=" + cod_pro
                            + ",cod_cli=" + cod_cli
                            + ",num_mod='" + num_mod + "' "
                            + ",num_ser='" + num_ser + "' "
                            + ",des_equ='" + des_equ + "' "
                            + ",des_ubi='" + des_ubi + "' "
                            + ",fec_fab= " + fec_fab + " "
                            + ",fec_com= " + fec_com + " "
                            + ",fec_adq= " + fec_adq + " "
                            + ",fec_pue_ser= " + fec_pue_ser + " "
                            + ",fec_ult_man= " + fec_ult_man + " "
                            + ",cod_bar='" + cod_bar + "' "
                            + " WHERE cod_lis_equ = " + cod_lis_equ + ";";

                    maccess.dmlSQLvariable("update tbl_gar set "
                            + "det_obs = '" + gar_obs + "' "
                            + ",fec_ini = " + gar_fec_ini + " "
                            + ",fec_exp = " + gar_fec_fin + " "
                            + "where cod_lis_equ =" + cod_lis_equ + ";");

                }
                maccess.dmlSQLvariable(mQuery);

                // ********************* Removidos ******************************************
                mQuery = "delete from lis_equ_rem where cod_lis_equ=" + cod_lis_equ + ";";
                maccess.dmlSQLvariable(mQuery);
                for (int i = 0; i < removidos.size(); i++) {
                    mvalores = mvalores + "," + "("
                            + cod_lis_equ + ","
                            + (i + 1) + ","
                            + "str_to_date('" + removidos.get(i).getFec_rem() + "','%d/%b/%Y'),"
                            + removidos.get(i).getCod_pai() + ","
                            + removidos.get(i).getCod_cli() + ",'"
                            + removidos.get(i).getDet_obs() + "',"
                            + cbean.getCod_usu() + ",now()"
                            + ")";
                }

                if (!"".equals(mvalores)) {
                    mvalores = mvalores.substring(1);
                    mQuery = " insert into lis_equ_rem (cod_lis_equ,cod_rem,fec_rem,cod_pai,cod_cli,det_obs,usu_edi,fec_edi) values " + mvalores + ";";
                    maccess.dmlSQLvariable(mQuery);
                }

                // ********************* Sistemas ******************************************
                mQuery = "delete from tbl_sys where cod_lis_equ=" + cod_lis_equ + ";";
                maccess.dmlSQLvariable(mQuery);
                mvalores = "";
                for (int i = 0; i < sistemas.size(); i++) {
                    mvalores = mvalores + "," + "("
                            + cod_lis_equ + ","
                            + (i + 1) + ",'"
                            + sistemas.get(i).getDet_obs() + "','"
                            + sistemas.get(i).getVer_ant() + "','"
                            + sistemas.get(i).getVer_act() + "', str_to_date('"
                            + sistemas.get(i).getFec_act() + "','%d/%b/%Y')"
                            + ")";
                }

                if (!"".equals(mvalores)) {
                    mvalores = mvalores.substring(1);
                    mQuery = " insert into tbl_sys (cod_lis_equ,cod_sys,det_obs,ver_ant,ver_act,fec_act) values " + mvalores + ";";
                    maccess.dmlSQLvariable(mQuery);
                }

                // ********************** Contratos ****************************************
                mQuery = "delete from tbl_con where cod_lis_equ=" + cod_lis_equ + ";";
                maccess.dmlSQLvariable(mQuery);

                mvalores = "";
                for (int i = 0; i < contratos.size(); i++) {
                    mvalores = mvalores + "," + "("
                            + cod_lis_equ + ","
                            + (i + 1) + ",'"
                            + contratos.get(i).getCod_ref() + "','"
                            + contratos.get(i).getDes_inf() + "', str_to_date('"
                            + contratos.get(i).getFec_con() + "','%d/%b/%Y'), str_to_date('"
                            + contratos.get(i).getFec_exp() + "','%d/%b/%Y')"
                            + ")";
                }
                if (!"".equals(mvalores)) {
                    mvalores = mvalores.substring(1);
                    mQuery = " insert into tbl_con (cod_lis_equ,cod_con,cod_ref,des_inf,fec_con,fec_exp) values " + mvalores + ";";
                    maccess.dmlSQLvariable(mQuery);
                }

                maccess.Desconectar();
                addMessage("Save", "Information stored successfully.", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while Saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarLequipos();
            catlistaequipos = null;
        }

    }

    public void eliminar() {
        String mQuery = "";
        maccess.Conectar();

        if ("".equals(cod_lis_equ) == false && "0".equals(cod_lis_equ) == false) {
            String nman = maccess.strQuerySQLvariable("select count(cod_lis_equ) from tbl_mae_man where cod_lis_equ =" + cod_lis_equ + ";");
            if ("0".equals(nman)) {
                try {
                    mQuery = "delete from lis_equ_ane where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = "delete from lis_equ_rem where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = "delete from lis_equ_img where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = mQuery + " delete from tbl_con where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = mQuery + " delete from tbl_sys where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = mQuery + " delete from tbl_gar where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = mQuery + " delete from lis_equ where cod_lis_equ = " + cod_lis_equ + ";";
                    maccess.dmlSQLvariable(mQuery);
                    addMessage("Delete", "Information deleted successfully.", 1);
                } catch (Exception e) {
                    addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                    System.out.println("Error al Eliminar Equipo. " + e.getMessage() + " Query: " + mQuery);
                }
                llenarLequipos();
                nuevo();
            } else {
                addMessage("Delete", "This Equipment can not be deleted because there are " + nman + " Maintenance associated", 2);
            }
        } else {
            addMessage("Delete", "You have to select an Equipment", 2);
        }
        maccess.Desconectar();

    }

    //**************** Historia Removidos **************************************
    public boolean validarremovidos() {
        boolean mvalidar = true;
        if (edfecret == false) {
            dfecret = null;
            fec_ret = "";
        }
        if ("".equals(rem_det_obs)) {
            mvalidar = false;
            addMessage("Save", "You have to enter a Comment.", 2);
        }
        if ("0".equals(cod_cli) || "".equals(cod_cli)) {
            mvalidar = false;
            addMessage("Save", "You have to select a Client.", 2);
        }
        if ("0".equals(cod_pai) || "".equals(cod_pai)) {
            mvalidar = false;
            addMessage("Save", "You have to select a Country.", 2);
        }
        if ("".equals(fec_ret) || dfecret == null) {
            mvalidar = false;
            addMessage("Save", "You have to select a Remotion Date.", 2);
        }

        return mvalidar;

    }

    public void agregarremovidos() {
        if (validarremovidos()) {
            int correlativo = 0, insert = 0;
            try {
                for (int i = 0; i < removidos.size(); i++) {
                    if (Integer.valueOf(removidos.get(i).getCod_rem()) > correlativo) {
                        correlativo = Integer.valueOf(removidos.get(i).getCod_rem());
                    }
                }
                maccess.Conectar();
                if (insert == 0) {
                    removidos.add(new CatRemovidos(
                            cod_lis_equ,
                            String.valueOf(correlativo),
                            fec_ret,
                            cod_pai,
                            cod_cli,
                            rem_det_obs,
                            maccess.strQuerySQLvariable("select nom_pai from cat_pai where cod_pai =" + cod_pai + ";"),
                            maccess.strQuerySQLvariable("select nom_cli from cat_cli where cod_pai =" + cod_pai + " and cod_cli = " + cod_cli + ";")
                    ));

                    rem_det_obs = "";
                    dfecret = null;
                    edfecret = false;

                }
                maccess.Desconectar();
            } catch (Exception e) {
                System.out.println("Error en Agregar Removidos ManListaEquipos." + e.getMessage());
            }
        }

    }

    public void eliminarremovidos() {
        rem_cod_rem = catremovidos.getCod_rem();
        catsistemas = null;
        if ("".equals(rem_cod_rem) || "0".equals(rem_cod_rem)) {
            addMessage("Delete", "You have to select a Record.", 2);
        } else {
            for (int i = 0; i < removidos.size(); i++) {
                if (rem_cod_rem.equals(removidos.get(i).getCod_rem())) {
                    removidos.remove(i);
                }
            }
            rem_cod_rem = "";
            rem_det_obs = "";
            dfecret = null;
            edfecret = false;
        }
    }

    public void onRowSelectRemovidos(SelectEvent event) {
        rem_cod_rem = ((CatRemovidos) event.getObject()).getCod_rem();
        rem_det_obs = ((CatRemovidos) event.getObject()).getDet_obs();
    }

    //***************** Historia Mantenimientos ********************************
    public void llenarMantenimientos() {
        String mQuery = "";
        try {
            catmantenimientos = null;
            mantenimientos.clear();

            mQuery = "select "
                    + "mm.cod_lis_equ, "
                    + "mm.cod_man, "
                    + "mm.cod_tip, "
                    + "mm.det_obs, "
                    + "date_format(mm.fec_ini,'%d/%b/%Y %H:%i'), "
                    + "date_format(mm.fec_fin,'%d/%b/%Y %H:%i'), "
                    + "mm.det_sta, "
                    + "mm.cod_usu,"
                    + "tip.nom_tip,"
                    + "case mm.det_sta "
                    + "when 1 then 'PENDING' "
                    + "when 2 then 'CANCELED' "
                    + "when 3 then 'IN PROGRESS' "
                    + "when 4 then 'COMPLETED' "
                    + "end as status, "
                    + "case mm.det_sta "
                    + "when 1 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<2,0,(TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))) "
                    + "when 2 then 0 "
                    + "when 3 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<2,0,(TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))) "
                    + "when 4 then 0 "
                    + "end as dr, "
                    + "case mm.det_sta "
                    + "when 1 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 2 then 'lime' "
                    + "when 3 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 4 then 'lime' "
                    + "end as color,"
                    + "mm.cod_per, "
                    + "per.nom_per, "
                    + "mm.flg_ext,"
                    + "case tip.flg_urg "
                    + "when 'false' then concat('WO',right(year(mm.fec_ini),2),'-',mm.cod_alt) "
                    + "when 'true' then concat('PM','-',mm.cod_alt) "
                    + "end as cod_alt,"
                    + "equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%b/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "mm.det_sta IN (2,4) "
                    + "and mm.cod_lis_equ=" + cod_lis_equ + " "
                    + "order by mm.cod_man;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientos.add(new CatMantenimientos(
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
                        resVariable.getString(11),
                        resVariable.getString(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20),
                        resVariable.getString(21),
                        resVariable.getString(22),
                        resVariable.getInt(23),
                        resVariable.getString(24)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Mantenimientos en Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void reabrirMantenimiento() {
        String mQuery = "";
        maccess.Conectar();
        if ("".equals(cod_lis_equ) == false && "0".equals(cod_lis_equ) == false && "".equals(m_cod_man) == false && "0".equals(m_cod_man) == false) {
            try {
                mQuery = "update tbl_mae_man set det_sta = 3 where cod_man=" + m_cod_man + " and cod_lis_equ = " + cod_lis_equ + ";";
                maccess.dmlSQLvariable(mQuery);
                llenarMantenimientos();
                addMessage("Reopen", "Reopened successfully.", 1);
            } catch (Exception e) {
                addMessage("Reopen", "Error While Reopening. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Encabezado ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
            }

        } else {
            addMessage("Reopen", "You have to select a WO.", 2);
        }
        maccess.Desconectar();
    }

    public void onRowSelectMantenimiento(SelectEvent event) {
        m_cod_man = ((CatMantenimientos) event.getObject()).getCod_man();
        m_cod_tip = ((CatMantenimientos) event.getObject()).getCod_tip();
        m_det_obs = ((CatMantenimientos) event.getObject()).getDet_obs();
        m_fec_ini = ((CatMantenimientos) event.getObject()).getFec_ini();
        m_fec_fin = ((CatMantenimientos) event.getObject()).getFec_fin();
        m_det_sta = ((CatMantenimientos) event.getObject()).getDet_sta();
        m_nomtip = ((CatMantenimientos) event.getObject()).getNomtip();
        m_estado = ((CatMantenimientos) event.getObject()).getStatus();
    }

    //************************** SISTEMAS **************************************
    public boolean validarsistema() {
        boolean mvalidar = true;
        if (edfecact == false) {
            dfecact = null;
            s_fec_act = "";
        }
        if ("".equals(s_det_obs)) {
            mvalidar = false;
            addMessage("Save", "You have to enter a System Name.", 2);
        }
        if ("".equals(s_ver_act)) {
            mvalidar = false;
            addMessage("Save", "You have to enter an Actual Version.", 2);
        }
        if ("".equals(s_fec_act) || dfecact == null) {
            mvalidar = false;
            addMessage("Save", "You have to select a Date.", 2);
        }
        if (s_ver_ant.equals(s_ver_act)) {
            mvalidar = false;
            addMessage("Save", "The name of the current version has the same name as the previous.", 2);
        }

        return mvalidar;

    }

    public void agregarsistema() {
        if (validarsistema()) {
            int correlativo = 0, insert = 0;
            try {
                for (int i = 0; i < sistemas.size(); i++) {
                    if (Integer.valueOf(sistemas.get(i).getCod_sys()) > correlativo) {
                        correlativo = Integer.valueOf(sistemas.get(i).getCod_sys());
                    }
                }

                if (insert == 0) {
                    sistemas.add(new CatSistemas(
                            cod_lis_equ,
                            String.valueOf(correlativo),
                            s_det_obs,
                            s_ver_ant,
                            s_ver_act,
                            s_fec_act
                    ));

                    s_det_obs = "";
                    s_ver_ant = "";
                    s_ver_act = "";
                    s_fec_act = "";
                    dfecact = null;
                    edfecact = false;

                }

            } catch (Exception e) {
                System.out.println("Error en Agregar Sistemas ManListaEquipos." + e.getMessage());
            }
        }

    }

    public void eliminarsistema() {

        s_cod_sys = catsistemas.getCod_sys();
        catsistemas = null;
        if ("".equals(s_cod_sys)) {
            addMessage("Delete", "You have to select a Record.", 2);
        } else {
            for (int i = 0; i < sistemas.size(); i++) {
                if (s_cod_sys.equals(sistemas.get(i).getCod_sys())) {
                    sistemas.remove(i);
                }
            }
            s_cod_sys = "";
            s_det_obs = "";
            s_ver_ant = "";
            s_ver_act = "";
            s_fec_act = "";
            //catlistaequipos = new CatListaEquipos();
        }
    }

    //************************* CONTRATOS **************************************
    public boolean validarcontrato() {
        boolean mvalidar = true;
        if (edfeccon == false) {
            dfeccon = null;
            c_fec_con = "";
        }
        if (edfecexp == false) {
            dfecexp = null;
            c_fec_exp = "";
        }
        if ("".equals(c_cod_ref)) {
            mvalidar = false;
            addMessage("Save", "You have to enter a Contract Number.", 2);
        }
        if ("".equals(c_fec_con) || dfeccon == null) {
            mvalidar = false;
            addMessage("Save", "You have to select a Start Date.", 2);
        }
        if ("".equals(c_fec_exp) || dfecexp == null) {
            mvalidar = false;
            addMessage("Save", "You have to select an Expiration Date.", 2);
        }

        return mvalidar;
    }

    public void agregarcontrato() {
        if (validarcontrato()) {
            int correlativo = 0, insert = 0;
            try {
                for (int i = 0; i < contratos.size(); i++) {
                    if (Integer.valueOf(contratos.get(i).getCod_con()) > correlativo) {
                        correlativo = Integer.valueOf(contratos.get(i).getCod_con());
                    }
                }

                if (insert == 0) {
                    contratos.add(new CatContratos(
                            cod_lis_equ,
                            String.valueOf(correlativo),
                            c_cod_ref,
                            c_des_inf,
                            c_fec_con,
                            c_fec_exp
                    ));

                    c_cod_con = "";
                    c_cod_ref = "";
                    c_des_inf = "";
                    c_fec_con = "";
                    c_fec_exp = "";
                    dfeccon = null;
                    edfeccon = false;
                    dfecexp = null;
                    edfecexp = false;

                }

            } catch (Exception e) {
                System.out.println("Error en Agregar Contratos ManListaEquipos." + e.getMessage());
            }
        }

    }

    public void eliminarcontrato() {
        c_cod_con = catcontratos.getCod_con();
        catcontratos = null;
        if ("".equals(c_cod_con)) {
            addMessage("Delete", "You have to select a Record.", 2);
        } else {
            for (int i = 0; i < contratos.size(); i++) {
                if (c_cod_con.equals(contratos.get(i).getCod_con())) {
                    contratos.remove(i);
                }
            }
            c_cod_con = "";
            c_cod_ref = "";
            c_des_inf = "";
            c_fec_con = "";
            c_fec_exp = "";
        }
    }

    //********************************* ANEXOS *********************************
    public boolean validaranexos() {
        boolean mvalidar = true;

        if ("0".equals(ane_tip_ane)) {
            mvalidar = false;
            addMessage("Files", "You have to select a type of file", 2);
        }

        if ("".equals(ane_rut_nom)) {
            mvalidar = false;
            addMessage("Files", "You have to select a File", 2);
        }
        if ("".equals(cod_lis_equ)) {
            mvalidar = false;
            addMessage("Files", "You have to select an Equipment", 2);
        }

        return mvalidar;

    }

    public void agregaranexo() {
        if (validaranexos()) {
            int correlativo = 0;
            String mQuery = "";
            try {
                for (int i = 0; i < anexos.size(); i++) {
                    if (Integer.valueOf(anexos.get(i).getCor_det()) > correlativo) {
                        correlativo = Integer.valueOf(anexos.get(i).getCor_det());
                    }
                }

                String tipoanexo = "";
                switch (ane_tip_ane) {
                    case "1":
                        tipoanexo = "PDF";
                        break;
                    case "2":
                        tipoanexo = "IMAGE";
                        break;
                    case "3":
                        tipoanexo = "OTHER";
                        break;

                }

                anexos.add(new CatLisEquAnexos(
                        cod_lis_equ,
                        String.valueOf(correlativo + 1),
                        ane_tip_ane,
                        ane_rut_nom,
                        ((ane_det_obs.replace("'", " ")).replace("/", " ")).replace("\\", " "),
                        tipoanexo
                ));

                maccess.Conectar();
                if ("0".equals(maccess.strQuerySQLvariable("select count(cor_det) from lis_equ_ane where cod_lis_equ =" + cod_lis_equ
                        + " and tip_ane = " + ane_tip_ane
                        + " and det_nom = '" + ane_rut_nom + "';"))) {

                    String micorr = maccess.strQuerySQLvariable("select ifnull(max(cor_det),0) + 1 from lis_equ_ane where cod_lis_equ =" + cod_lis_equ + ";");
                    // ****************************  Inserta Archivo en tabla ************************************************************
                    try {
                        FileInputStream fis = null;
                        PreparedStatement ps = null;
                        try {
                            maccess.Conectar().setAutoCommit(false);
                            File mfile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(ane_rut_nom));
                            fis = new FileInputStream(mfile);

                            mQuery = "insert into lis_equ_ane(cod_lis_equ,cor_det,tip_ane,det_nom,det_obs,det_ima) values(?,?,?,?,?,?)";
                            ps = maccess.Conectar().prepareStatement(mQuery);
                            ps.setString(1, cod_lis_equ);
                            ps.setString(2, micorr);
                            ps.setString(3, ane_tip_ane);
                            ps.setString(4, ane_rut_nom);
                            ps.setString(5, ane_det_obs);
                            ps.setBinaryStream(6, fis, (int) mfile.length());

                            ps.executeUpdate();
                            maccess.Conectar().commit();

                        } catch (Exception ex) {

                        } finally {
                            try {
                                ps.close();
                                fis.close();
                            } catch (Exception ex) {

                            }
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                maccess.Desconectar();

                llenarAnexos();

                ane_det_obs = "";
                ane_tip_ane = "0";
                ane_rut_nom = "";
                ane_det_obs = "";
                nombrearchivo = "Select One File";

                addMessage("Upload", "File has been successfully saved", 1);

            } catch (Exception e) {
                addMessage("Upload", "Error while saved. " + e.getMessage(), 2);
                System.out.println("Error en Agregar Anexo ManMaestroMan." + e.getMessage());
            }
        }

    }

    public void cargarArchivoAnexo(FileUploadEvent event) {
        try {
            ane_cor_det = "";
            catlisequanexos = null;
            nombrearchivo = event.getFile().getFileName();

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                copyFile("ane_lis_equ_" + cod_lis_equ + "_" + event.getFile().getFileName().toLowerCase(), event.getFile().getInputstream());
            } catch (Exception e) {
                nombrearchivo = "Select One File";
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            addMessage("Upload", "File " + event.getFile().getFileName() + " can't be uploaded. " + e.getMessage(), 2);
            System.out.println("Error en subir archivo Mantenimiento." + e.getMessage());
        }

    }

    public void eliminarAnexos() {
        String mQuery = "";
        if ("".equals(ane_cor_det)) {
            addMessage("Files", "You have to select a Record from the list.", 2);
        } else {
            try {
                maccess.Conectar();
                mQuery = "delete from lis_equ_ane where cod_lis_equ = " + cod_lis_equ + " and cor_det= " + ane_cor_det + ";";
                maccess.dmlSQLvariable(mQuery);
                maccess.Desconectar();
            } catch (Exception ex) {
                addMessage("Save", "Error while deleted. " + ex.getMessage(), 2);
                System.out.println("Error en Eliminar Anexo ManListaEquipos." + ex.getMessage() + " mQuery: " + mQuery);
            }

            llenarAnexos();

            ane_cor_det = "";
            ane_tip_ane = "";
            ane_rut_nom = "";
            ane_det_obs = "";
            nombrearchivo = "Select One File";
        }
    }

    public void descargaranexos() {
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ) || "".equals(ane_cor_det) || "0".equals(ane_cor_det)) {
            addMessage("Download", "You have to select a record", 2);
        } else {
            byte[] fileBytes;
            String mQuery;
            maccess.Conectar();
            try {
                mQuery = "select det_ima from lis_equ_ane where cod_lis_equ=" + cod_lis_equ + " and cor_det=" + ane_cor_det + ";";

                ResultSet rs = maccess.querySQLvariable(mQuery);
                if (rs.next()) {
                    File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                    String destinationO = mIMGFile.getPath().replace("config.xml", "");

                    fileBytes = rs.getBytes(1);
                    OutputStream targetFile = new FileOutputStream(destinationO + ane_rut_nom.replace("/resources/images/temp/", ""));

                    targetFile.write(fileBytes);
                    targetFile.flush();
                    targetFile.close();
                    mIMGFile = null;

                    try {
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("window.open('" + "/totaltracking/faces" + ane_rut_nom + "', '_blank')");
                    } catch (Exception e) {
                        System.out.println("Error en redireccionar a descarga en ManListaEquipos. " + e.getMessage());
                    }

                }
                rs.close();

            } catch (Exception e) {
                System.out.println("Error en descargar archivo ManListaEquipos. " + e.getMessage());
            }
            maccess.Desconectar();
        }

    }

    //*************************** GENERALES ************************************
    public void recuperarimagen() {
        String mQuery = "";
        maccess.Conectar();
        mimagen = null;
        try {
            mQuery = "select det_ima from lis_equ_img where cod_lis_equ=" + cod_lis_equ + ";";
            Blob miBlob = maccess.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mWidth = String.valueOf(Double.valueOf(img.getWidth()) / Double.valueOf(img.getHeight()) * 330.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from lis_equ_img where cod_lis_equ=0;";
                miBlob = maccess.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mWidth = String.valueOf(Double.valueOf(img.getWidth()) / Double.valueOf(img.getHeight()) * 330.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            System.out.println("Error en recuperarimagen ManListaEquipos. " + e.getMessage() + " Query: " + mQuery);

        }

        maccess.Desconectar();
    }

    public void onRowSelectListaEquipo(SelectEvent event) {
        cod_lis_equ = ((CatListaEquipos) event.getObject()).getCod_lis_equ();
        cod_pai = ((CatListaEquipos) event.getObject()).getCod_pai();
        cod_equ = ((CatListaEquipos) event.getObject()).getCod_equ();
        cod_pro = ((CatListaEquipos) event.getObject()).getCod_pro();
        cod_cli = ((CatListaEquipos) event.getObject()).getCod_cli();
        num_mod = ((CatListaEquipos) event.getObject()).getNum_mod();
        num_ser = ((CatListaEquipos) event.getObject()).getNum_ser();
        des_equ = ((CatListaEquipos) event.getObject()).getDes_equ();
        des_ubi = ((CatListaEquipos) event.getObject()).getDes_ubi();
        fec_fab = ((CatListaEquipos) event.getObject()).getFec_fab();
        fec_com = ((CatListaEquipos) event.getObject()).getFec_com();
        fec_adq = ((CatListaEquipos) event.getObject()).getFec_adq();
        fec_pue_ser = ((CatListaEquipos) event.getObject()).getFec_pue_ser();
        fec_ult_man = ((CatListaEquipos) event.getObject()).getFec_ult_man();
        fec_ret = ((CatListaEquipos) event.getObject()).getFec_ret();
        cod_bar = ((CatListaEquipos) event.getObject()).getCod_bar();

        dfecfab = null;
        dfeccom = null;
        dfecadq = null;
        dfecpueser = null;
        dfecultman = null;
        dfecini = null;
        dfecfin = null;
        dfecret = null;
        dfecact = null;
        dfeccon = null;
        dfecexp = null;

        edfecfab = false;
        edfeccom = false;
        edfecadq = false;
        edfecpueser = false;
        edfecultman = false;
        edfecini = false;
        edfecfin = false;
        edfecret = false;
        edfecact = false;
        edfeccon = false;
        edfecexp = false;

        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");

        if ("00/00/0000".equals(fec_fab) || "".equals(fec_fab) || fec_fab == null) {
            fec_fab = "";
        } else {
            edfecfab = true;
            try {
                dfecfab = format.parse(fec_fab);
                fec_fab = format2.format(dfecfab);
            } catch (Exception ex) {

            }
        }
        if ("00/00/0000".equals(fec_com) || "".equals(fec_com) || fec_com == null) {
            fec_com = "";
        } else {
            edfeccom = true;
            try {
                dfeccom = format.parse(fec_com);
                fec_com = format2.format(dfeccom);
            } catch (Exception ex) {

            }
        }
        if ("00/00/0000".equals(fec_adq) || "".equals(fec_adq) || fec_adq == null) {
            fec_adq = "";
        } else {
            edfecadq = true;
            try {
                dfecadq = format.parse(fec_adq);
                fec_adq = format2.format(dfecadq);
            } catch (Exception ex) {

            }
        }
        if ("00/00/0000".equals(fec_pue_ser) || "".equals(fec_pue_ser) || fec_pue_ser == null) {
            fec_pue_ser = "";
        } else {
            edfecpueser = true;
            try {
                dfecpueser = format.parse(fec_pue_ser);
                fec_pue_ser = format2.format(dfecpueser);
            } catch (Exception ex) {

            }
        }
        if ("00/00/0000".equals(fec_ult_man) || "".equals(fec_ult_man) || fec_ult_man == null) {
            fec_ult_man = "";
        } else {
            edfecultman = true;
            try {
                dfecultman = format.parse(fec_ult_man);
                fec_ult_man = format2.format(dfecultman);
            } catch (Exception ex) {

            }
        }
        /*
        if ("00/00/0000".equals(fec_ret) || "".equals(fec_ret) || fec_ret == null) {
            fec_ret = "";

        } else {
            edfecret = true;
            try {
                dfecret = format.parse(fec_ret);
            } catch (Exception ex) {

            }
        }*/

        llenarGarantias();
        llenarRemovidos();
        llenarSistemas();
        llenarContratos();
        llenarMantenimientos();

        recuperarimagen();

        llenarAnexos();

        ane_cor_det = "";
        ane_tip_ane = "";
        ane_rut_nom = "";
        ane_det_obs = "";
        nombrearchivo = "Select One File";

    }

    public void onRowSelectAnexos(SelectEvent event) {
        ane_cor_det = ((CatLisEquAnexos) event.getObject()).getCor_det();
        ane_rut_nom = ((CatLisEquAnexos) event.getObject()).getDet_nom();
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabLista":
                tabindex = "0";
                break;
            case "tabGeneral":
                tabindex = "1";
                break;
            case "tabHistoria":
                tabindex = "2";
                break;
            case "tabSoftware":
                tabindex = "3";
                break;
            case "tabContratos":
                tabindex = "4";
                break;
            case "tabImagen":
                tabindex = "5";
                break;
            case "tabFiles":
                tabindex = "6";
                break;
        }

    }

    public void onPaisChange() {
        llenarProveedores();
        llenarClientes();

    }

    public void onPaisBusChange() {
        //llenarClientesB();
    }

    public void dateSelectedFecfab(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_fab = format.format(date);
    }

    public void dateSelectedFeccom(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_com = format.format(date);
    }

    public void dateSelectedFecadq(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_adq = format.format(date);
    }

    public void dateSelectedFecpueser(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_pue_ser = format.format(date);
    }

    public void dateSelectedFecultman(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_ult_man = format.format(date);
    }

    public void dateSelectedFecgarini(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        gar_fec_ini = format.format(date);
    }

    public void dateSelectedFecgarfin(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        gar_fec_fin = format.format(date);
    }

    public void dateSelectedFecret(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_ret = format.format(date);
    }

    public void dateSelectedFecact(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        s_fec_act = format.format(date);
    }

    public void dateSelectedFeccon(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        c_fec_con = format.format(date);
    }

    public void dateSelectedFecexp(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        c_fec_exp = format.format(date);
    }

    public void upload(FileUploadEvent event) {
        String mQuery = "";
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Procesar Imagen", "Debe Seleccionar un Equipo.", 2);
            rut_img = "/resources/images/lequ/noimage.png";
        } else {
            try {

                copyFile("lequ_" + cod_lis_equ + ".jpg", event.getFile().getInputstream());

                // ****************************  Inserta Imagen en tabla ************************************************************
                try {
                    maccess.Conectar();

                    FileInputStream fis = null;
                    PreparedStatement ps = null;
                    try {
                        maccess.Conectar().setAutoCommit(false);
                        File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(rut_img));
                        fis = new FileInputStream(file);
                        if ("0".equals(maccess.strQuerySQLvariable("select count(cod_lis_equ) conta from lis_equ_img where cod_lis_equ=" + cod_lis_equ + ";"))) {
                            mQuery = "insert into lis_equ_img(cod_lis_equ,det_ima) values(?,?)";
                            ps = maccess.Conectar().prepareStatement(mQuery);
                            ps.setString(1, cod_lis_equ);
                            ps.setBinaryStream(2, fis, (int) file.length());
                        } else {
                            mQuery = "update lis_equ_img set det_ima = ? where cod_lis_equ=?;";
                            ps = maccess.Conectar().prepareStatement(mQuery);
                            ps.setBinaryStream(1, fis, (int) file.length());
                            ps.setString(2, cod_lis_equ);
                        }

                        ps.executeUpdate();
                        maccess.Conectar().commit();

                    } catch (Exception ex) {

                    } finally {
                        try {
                            ps.close();
                            fis.close();
                        } catch (Exception ex) {

                        }
                    }
                    recuperarimagen();

                    maccess.Desconectar();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                addMessage("Procesar Archivo", "El Archivo " + event.getFile().getFileName() + " No se ha podido Cargar. " + e.getMessage(), 2);
                System.out.println("Error en subir archivo Mantenimiento." + e.getMessage());
            }
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            rut_img = "/resources/images/temp/" + fileName;
            ane_rut_nom = "/resources/images/temp/" + fileName;

            destination = mIMGFile.getPath().replace("config.xml", "");

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                File mfile = new File(destination + fileName);
                if (mfile.exists()) {
                    mfile.delete();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName.toLowerCase()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            addMessage("Copiar Archivo Imagen Equipo", "El Archivo en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

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

    //*************************** REPORTES *************************************
    
    public void ejecutarreporte() {
        try {
            parametros.clear();
            if (!"".equals(cod_lis_equ) && !"0".equals(cod_lis_equ)) {
                parametros.put("codequipo", cod_lis_equ);

            } else {
                parametros.put("codequipo", "0");
                addMessage("Print", "You have to select a Equipment.", 2);
            }
            nombrereporte = "/reportes/fichaequipo.jasper";
            nombreexportar = "File_" + cod_lis_equ;
            verPDF();
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Lista Equipos." + e.getMessage());
        }

    }

    public void ejecutarreporte2() {
        try {
            parametros.clear();
            if (!"".equals(cod_lis_equ) && !"0".equals(cod_lis_equ) && !"".equals(m_cod_man) && !"0".equals(m_cod_man)) {
                parametros.put("codequipo", cod_lis_equ);
                parametros.put("codman", m_cod_man);
            } else {
                addMessage("Print", "You have to select a Record.", 2);
                parametros.put("codequipo", "0");
                parametros.put("codman", "0");
            }
            nombrereporte = "/reportes/manequipodetalle.jasper";
            nombreexportar = "Maintenance_" + cod_lis_equ + "_" + m_cod_man;
            verPDF();
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Lista Equipos." + e.getMessage());
        }

    }

    public void ejecutarHistoriaMan() {
        try {
            parametros.clear();
            if (!"".equals(cod_lis_equ) && !"0".equals(cod_lis_equ)) {
                parametros.put("codequipo", cod_lis_equ);
            } else {
                addMessage("Print", "You have to select a Equipment.", 2);
                parametros.put("codequipo", "0");
            }
            nombrereporte = "/reportes/fichamanequipo.jasper";
            nombreexportar = "Maintenance_History_" + cod_lis_equ;
            verPDF();
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Lista Equipos." + e.getMessage());
        }

    }

    public void verPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, maccess.Conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
            maccess.Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en verPDF en Lista Equipo." + e.getMessage());
        }
    }

    //******************** GETTER Y SETTER *************************************
    public List<CatTipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<CatTipos> tipos) {
        this.tipos = tipos;
    }

    public CatMantenimientos getCatmantenimientos() {
        return catmantenimientos;
    }

    public void setCatmantenimientos(CatMantenimientos catmantenimientos) {
        this.catmantenimientos = catmantenimientos;
    }

    public List<CatMantenimientos> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<CatMantenimientos> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public List<CatProveedores> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<CatProveedores> proveedores) {
        this.proveedores = proveedores;
    }

    public List<CatClientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<CatClientes> clientes) {
        this.clientes = clientes;
    }

    public List<CatClientes> getClientesb() {
        return clientesb;
    }

    public void setClientesb(List<CatClientes> clientesb) {
        this.clientesb = clientesb;
    }

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public List<CatContratos> getContratos() {
        return contratos;
    }

    public void setContratos(List<CatContratos> contratos) {
        this.contratos = contratos;
    }

    public List<CatGarantias> getGarantias() {
        return garantias;
    }

    public void setGarantias(List<CatGarantias> garantias) {
        this.garantias = garantias;
    }

    public CatSistemas getCatsistemas() {
        return catsistemas;
    }

    public void setCatsistemas(CatSistemas catsistemas) {
        this.catsistemas = catsistemas;
    }

    public List<CatSistemas> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<CatSistemas> sistemas) {
        this.sistemas = sistemas;
    }

    public CatContratos getCatcontratos() {
        return catcontratos;
    }

    public void setCatcontratos(CatContratos catcontratos) {
        this.catcontratos = catcontratos;
    }

    public List<CatEquipos> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatEquipos> equipos) {
        this.equipos = equipos;
    }

    public CatListaEquipos getCatlistaequipos() {
        return catlistaequipos;
    }

    public void setCatlistaequipos(CatListaEquipos catlistaequipos) {
        this.catlistaequipos = catlistaequipos;
    }

    public List<CatListaEquipos> getLequipos() {
        return lequipos;
    }

    public void setLequipos(List<CatListaEquipos> lequipos) {
        this.lequipos = lequipos;
    }

    public String getCod_lis_equ() {
        return cod_lis_equ;
    }

    public void setCod_lis_equ(String cod_lis_equ) {
        this.cod_lis_equ = cod_lis_equ;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getNum_mod() {
        return num_mod;
    }

    public void setNum_mod(String num_mod) {
        this.num_mod = num_mod;
    }

    public String getNum_ser() {
        return num_ser;
    }

    public void setNum_ser(String num_ser) {
        this.num_ser = num_ser;
    }

    public String getDes_equ() {
        return des_equ;
    }

    public void setDes_equ(String des_equ) {
        this.des_equ = des_equ;
    }

    public String getDes_ubi() {
        return des_ubi;
    }

    public void setDes_ubi(String des_ubi) {
        this.des_ubi = des_ubi;
    }

    public String getFec_fab() {
        return fec_fab;
    }

    public void setFec_fab(String fec_fab) {
        this.fec_fab = fec_fab;
    }

    public String getFec_com() {
        return fec_com;
    }

    public void setFec_com(String fec_com) {
        this.fec_com = fec_com;
    }

    public String getFec_adq() {
        return fec_adq;
    }

    public void setFec_adq(String fec_adq) {
        this.fec_adq = fec_adq;
    }

    public String getFec_pue_ser() {
        return fec_pue_ser;
    }

    public void setFec_pue_ser(String fec_pue_ser) {
        this.fec_pue_ser = fec_pue_ser;
    }

    public String getFec_ult_man() {
        return fec_ult_man;
    }

    public void setFec_ult_man(String fec_ult_man) {
        this.fec_ult_man = fec_ult_man;
    }

    public String getFec_ret() {
        return fec_ret;
    }

    public void setFec_ret(String fec_ret) {
        this.fec_ret = fec_ret;
    }

    public String getCod_bar() {
        return cod_bar;
    }

    public void setCod_bar(String cod_bar) {
        this.cod_bar = cod_bar;
    }

    public String getGar_fec_ini() {
        return gar_fec_ini;
    }

    public void setGar_fec_ini(String gar_fec_ini) {
        this.gar_fec_ini = gar_fec_ini;
    }

    public String getGar_fec_fin() {
        return gar_fec_fin;
    }

    public void setGar_fec_fin(String gar_fec_fin) {
        this.gar_fec_fin = gar_fec_fin;
    }

    public String getGar_obs() {
        return gar_obs;
    }

    public void setGar_obs(String gar_obs) {
        this.gar_obs = gar_obs;
    }

    public String getCod_equ_b() {
        return cod_equ_b;
    }

    public void setCod_equ_b(String cod_equ_b) {
        this.cod_equ_b = cod_equ_b;
    }

    public String getCod_cli_b() {
        return cod_cli_b;
    }

    public void setCod_cli_b(String cod_cli_b) {
        this.cod_cli_b = cod_cli_b;
    }

    public String getCod_pai_b() {
        return cod_pai_b;
    }

    public void setCod_pai_b(String cod_pai_b) {
        this.cod_pai_b = cod_pai_b;
    }

    public String getNum_ser_b() {
        return num_ser_b;
    }

    public void setNum_ser_b(String num_ser_b) {
        this.num_ser_b = num_ser_b;
    }

    public String getS_cod_sys() {
        return s_cod_sys;
    }

    public void setS_cod_sys(String s_cod_sys) {
        this.s_cod_sys = s_cod_sys;
    }

    public String getS_det_obs() {
        return s_det_obs;
    }

    public void setS_det_obs(String s_det_obs) {
        this.s_det_obs = s_det_obs;
    }

    public String getS_ver_ant() {
        return s_ver_ant;
    }

    public void setS_ver_ant(String s_ver_ant) {
        this.s_ver_ant = s_ver_ant;
    }

    public String getS_ver_act() {
        return s_ver_act;
    }

    public void setS_ver_act(String s_ver_act) {
        this.s_ver_act = s_ver_act;
    }

    public String getS_fec_act() {
        return s_fec_act;
    }

    public void setS_fec_act(String s_fec_act) {
        this.s_fec_act = s_fec_act;
    }

    public String getC_cod_con() {
        return c_cod_con;
    }

    public void setC_cod_con(String c_cod_con) {
        this.c_cod_con = c_cod_con;
    }

    public String getC_cod_ref() {
        return c_cod_ref;
    }

    public void setC_cod_ref(String c_cod_ref) {
        this.c_cod_ref = c_cod_ref;
    }

    public String getC_des_inf() {
        return c_des_inf;
    }

    public void setC_des_inf(String c_des_inf) {
        this.c_des_inf = c_des_inf;
    }

    public String getC_fec_con() {
        return c_fec_con;
    }

    public void setC_fec_con(String c_fec_con) {
        this.c_fec_con = c_fec_con;
    }

    public String getC_fec_exp() {
        return c_fec_exp;
    }

    public void setC_fec_exp(String c_fec_exp) {
        this.c_fec_exp = c_fec_exp;
    }

    public String getM_cod_man() {
        return m_cod_man;
    }

    public void setM_cod_man(String m_cod_man) {
        this.m_cod_man = m_cod_man;
    }

    public String getM_cod_tip() {
        return m_cod_tip;
    }

    public void setM_cod_tip(String m_cod_tip) {
        this.m_cod_tip = m_cod_tip;
    }

    public String getM_det_obs() {
        return m_det_obs;
    }

    public void setM_det_obs(String m_det_obs) {
        this.m_det_obs = m_det_obs;
    }

    public String getM_fec_ini() {
        return m_fec_ini;
    }

    public void setM_fec_ini(String m_fec_ini) {
        this.m_fec_ini = m_fec_ini;
    }

    public String getM_fec_fin() {
        return m_fec_fin;
    }

    public void setM_fec_fin(String m_fec_fin) {
        this.m_fec_fin = m_fec_fin;
    }

    public String getM_det_sta() {
        return m_det_sta;
    }

    public void setM_det_sta(String m_det_sta) {
        this.m_det_sta = m_det_sta;
    }

    public String getM_nomtip() {
        return m_nomtip;
    }

    public void setM_nomtip(String m_nomtip) {
        this.m_nomtip = m_nomtip;
    }

    public String getM_estado() {
        return m_estado;
    }

    public void setM_estado(String m_estado) {
        this.m_estado = m_estado;
    }

    public Date getDfecfab() {
        return dfecfab;
    }

    public void setDfecfab(Date dfecfab) {
        this.dfecfab = dfecfab;
    }

    public Date getDfeccom() {
        return dfeccom;
    }

    public void setDfeccom(Date dfeccom) {
        this.dfeccom = dfeccom;
    }

    public Date getDfecadq() {
        return dfecadq;
    }

    public void setDfecadq(Date dfecadq) {
        this.dfecadq = dfecadq;
    }

    public Date getDfecpueser() {
        return dfecpueser;
    }

    public void setDfecpueser(Date dfecpueser) {
        this.dfecpueser = dfecpueser;
    }

    public Date getDfecultman() {
        return dfecultman;
    }

    public void setDfecultman(Date dfecultman) {
        this.dfecultman = dfecultman;
    }

    public Date getDfecret() {
        return dfecret;
    }

    public void setDfecret(Date dfecret) {
        this.dfecret = dfecret;
    }

    public Date getDfecini() {
        return dfecini;
    }

    public void setDfecini(Date dfecini) {
        this.dfecini = dfecini;
    }

    public Date getDfecfin() {
        return dfecfin;
    }

    public void setDfecfin(Date dfecfin) {
        this.dfecfin = dfecfin;
    }

    public Date getDfecact() {
        return dfecact;
    }

    public void setDfecact(Date dfecact) {
        this.dfecact = dfecact;
    }

    public Date getDfeccon() {
        return dfeccon;
    }

    public void setDfeccon(Date dfeccon) {
        this.dfeccon = dfeccon;
    }

    public Date getDfecexp() {
        return dfecexp;
    }

    public void setDfecexp(Date dfecexp) {
        this.dfecexp = dfecexp;
    }

    public boolean isEdfecfab() {
        return edfecfab;
    }

    public void setEdfecfab(boolean edfecfab) {
        this.edfecfab = edfecfab;
    }

    public boolean isEdfeccom() {
        return edfeccom;
    }

    public void setEdfeccom(boolean edfeccom) {
        this.edfeccom = edfeccom;
    }

    public boolean isEdfecadq() {
        return edfecadq;
    }

    public void setEdfecadq(boolean edfecadq) {
        this.edfecadq = edfecadq;
    }

    public boolean isEdfecpueser() {
        return edfecpueser;
    }

    public void setEdfecpueser(boolean edfecpueser) {
        this.edfecpueser = edfecpueser;
    }

    public boolean isEdfecultman() {
        return edfecultman;
    }

    public void setEdfecultman(boolean edfecultman) {
        this.edfecultman = edfecultman;
    }

    public boolean isEdfecret() {
        return edfecret;
    }

    public void setEdfecret(boolean edfecret) {
        this.edfecret = edfecret;
    }

    public boolean isEdfecini() {
        return edfecini;
    }

    public void setEdfecini(boolean edfecini) {
        this.edfecini = edfecini;
    }

    public boolean isEdfecfin() {
        return edfecfin;
    }

    public void setEdfecfin(boolean edfecfin) {
        this.edfecfin = edfecfin;
    }

    public boolean isEdfecact() {
        return edfecact;
    }

    public void setEdfecact(boolean edfecact) {
        this.edfecact = edfecact;
    }

    public boolean isEdfeccon() {
        return edfeccon;
    }

    public void setEdfeccon(boolean edfeccon) {
        this.edfeccon = edfeccon;
    }

    public boolean isEdfecexp() {
        return edfecexp;
    }

    public void setEdfecexp(boolean edfecexp) {
        this.edfecexp = edfecexp;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getBpais() {
        return bpais;
    }

    public void setBpais(String bpais) {
        this.bpais = bpais;
    }

    public String getRut_img() {
        return rut_img;
    }

    public void setRut_img(String rut_img) {
        this.rut_img = rut_img;
    }

    public String getBfiltro() {
        return bfiltro;
    }

    public void setBfiltro(String bfiltro) {
        this.bfiltro = bfiltro;
    }

    public DefaultStreamedContent getMimagen() {
        return mimagen;
    }

    public void setMimagen(DefaultStreamedContent mimagen) {
        ManListaEquipos.mimagen = mimagen;
    }

    public String getmWidth() {
        return mWidth;
    }

    public void setmWidth(String mWidth) {
        this.mWidth = mWidth;
    }

    public CatRemovidos getCatremovidos() {
        return catremovidos;
    }

    public void setCatremovidos(CatRemovidos catremovidos) {
        this.catremovidos = catremovidos;
    }

    public List<CatRemovidos> getRemovidos() {
        return removidos;
    }

    public void setRemovidos(List<CatRemovidos> removidos) {
        this.removidos = removidos;
    }

    public String getRem_cod_rem() {
        return rem_cod_rem;
    }

    public void setRem_cod_rem(String rem_cod_rem) {
        this.rem_cod_rem = rem_cod_rem;
    }

    public String getRem_det_obs() {
        return rem_det_obs;
    }

    public void setRem_det_obs(String rem_det_obs) {
        this.rem_det_obs = rem_det_obs;
    }

    public CatLisEquAnexos getCatlisequanexos() {
        return catlisequanexos;
    }

    public void setCatlisequanexos(CatLisEquAnexos catlisequanexos) {
        this.catlisequanexos = catlisequanexos;
    }

    public List<CatLisEquAnexos> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<CatLisEquAnexos> anexos) {
        this.anexos = anexos;
    }

    public String getAne_tip_ane() {
        return ane_tip_ane;
    }

    public void setAne_tip_ane(String ane_tip_ane) {
        this.ane_tip_ane = ane_tip_ane;
    }

    public String getAne_det_obs() {
        return ane_det_obs;
    }

    public void setAne_det_obs(String ane_det_obs) {
        this.ane_det_obs = ane_det_obs;
    }

    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    public String getNombrereporte() {
        return nombrereporte;
    }

    public void setNombrereporte(String nombrereporte) {
        this.nombrereporte = nombrereporte;
    }

    public String getNombreexportar() {
        return nombreexportar;
    }

    public void setNombreexportar(String nombreexportar) {
        this.nombreexportar = nombreexportar;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }

}
