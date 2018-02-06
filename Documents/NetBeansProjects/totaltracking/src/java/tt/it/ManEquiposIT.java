package tt.it;

import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped

public class ManEquiposIT implements Serializable {

    private static final long serialVersionUID = 8797968735428638L;
    @Inject
    Login cbean;
    private Accesos macc;

    private String tabindex;

    private List<CatPaises> paises;

    private CatLisEqu catlisequ;
    private List<CatLisEqu> equipos;

    private String cod_lis_equ, cod_pai, tip_equ, cod_equ, rut_ima_enc;

    private String mancho_enc;

    private static DefaultStreamedContent mimagen_enc;

    private List<CatListados> marcas;
    private List<CatListados> modelos;
    private List<CatListados> sistemasop;

    private CatLisEquDet catlisequdet;
    private List<CatLisEquDet> equiposdet;

    private String cod_det, cod_pie, nom_ite, det_mar, det_mod, det_ser, det_sop, det_bit, fec_adq, fec_cam, flg_act, flg_sta, det_obs;

    private Date dfec_adq, dfec_cam;

    private String mancho_det, rut_ima_det;

    private static DefaultStreamedContent mimagen_det;

    private List<CatListados> clientes;
    private List<CatListados> departamentos;
    private List<CatListados> usuarios;

    private CatAsiEquCli catasiequcli;
    private List<CatAsiEquCli> asiequcli;

    private String asi_cod_lis_equ, asi_nom_cli, asi_cod_det, asi_cod_pai, asi_nom_dep, asi_nom_usu, asi_fec_asi, asi_fec_cam, asieditable;

    private Date dasi_fec_asi, dasi_fec_cam;

    private List<CatListados> responsables;

    private CatManEquIT catmanequit;
    private List<CatManEquIT> manequit;

    private String man_enc_cod_man, man_enc_cod_pai, man_enc_nom_cli, man_enc_tip_man, man_enc_fec_ini, man_enc_fec_fin,
            man_enc_usu_res, man_enc_det_obs, man_enc_det_sta;

    private Date dman_enc_fec_ini, dman_enc_fec_fin;

    private List<CatListados> piezas;

    private CatManEquITDet catmanequitdet;
    private List<CatManEquITDet> manequitdet;

    private String man_det_cod_det, man_det_cod_pie, man_det_det_obs, man_det_fec_acc;
    private Double man_det_det_cos;
    private int man_det_det_tie;
    private String man_det_usu_res, rut_ima_ane, man_det_rut_ane;

    private Date dman_det_fec_ini;

    public ManEquiposIT() {
    }

    public void iniciarventana() {
        macc = new Accesos();

        tabindex = "0";

        cod_lis_equ = "";
        cod_pai = cbean.getCod_pai();
        tip_equ = "";
        cod_equ = "";
        rut_ima_enc = "";

        mimagen_enc = null;
        mancho_enc = "180";

        paises = new ArrayList<>();
        catlisequ = new CatLisEqu();
        equipos = new ArrayList<>();

        llenarPaises();

        dfec_adq = null;
        dfec_cam = null;

        cod_det = "";
        cod_pie = "";
        nom_ite = "";
        det_mar = "";
        det_mod = "";
        det_ser = "";
        det_sop = "";
        det_bit = "0";
        fec_adq = "";
        flg_act = "false";
        fec_cam = "";
        flg_sta = "Operativo";
        det_obs = "";

        mimagen_det = null;
        mancho_det = "200";

        marcas = new ArrayList<>();
        modelos = new ArrayList<>();
        sistemasop = new ArrayList<>();

        catlisequdet = new CatLisEquDet();
        equiposdet = new ArrayList<>();

        dasi_fec_asi = null;
        dasi_fec_cam = null;

        asi_cod_lis_equ = "";
        asi_nom_cli = "";
        asi_cod_det = "";
        asi_cod_pai = cbean.getCod_pai();
        asi_nom_dep = "";
        asi_nom_usu = "";
        asi_fec_asi = "";
        asi_fec_cam = "";
        asieditable = "false";

        clientes = new ArrayList<>();
        departamentos = new ArrayList<>();
        usuarios = new ArrayList<>();

        catasiequcli = new CatAsiEquCli();
        asiequcli = new ArrayList<>();

        dman_enc_fec_ini = null;
        dman_enc_fec_fin = null;

        man_enc_cod_man = "";
        man_enc_cod_pai = cbean.getCod_pai();
        man_enc_nom_cli = "";
        man_enc_tip_man = "Preventivo";
        man_enc_fec_ini = "";
        man_enc_fec_fin = "";
        man_enc_usu_res = cbean.getCod_usu();
        man_enc_det_obs = "";
        man_enc_det_sta = "PROGRAMADO";

        responsables = new ArrayList<>();

        catmanequit = new CatManEquIT();
        manequit = new ArrayList<>();

        llenarResponsables();

        dman_det_fec_ini = null;

        man_det_cod_det = "";
        man_det_cod_pie = "0";
        man_det_det_obs = "";
        man_det_fec_acc = "";
        man_det_det_cos = 0.0;
        man_det_det_tie = 0;
        man_det_usu_res = cbean.getCod_usu();
        man_det_rut_ane = "";

        piezas = new ArrayList<>();

        catmanequitdet = new CatManEquITDet();
        manequitdet = new ArrayList<>();

        llenarEquipos();

    }

    public void nuevo() {
        tabindex = "0";

        cod_lis_equ = "";
        cod_pai = cbean.getCod_pai();
        tip_equ = "";
        cod_equ = "";
        rut_ima_enc = "";

        mimagen_enc = null;
        mancho_enc = "180";

        catlisequ = null;

        dfec_adq = null;
        dfec_cam = null;

        cod_det = "";
        cod_pie = "";
        nom_ite = "";
        det_mar = "";
        det_mod = "";
        det_ser = "";
        det_sop = "";
        det_bit = "0";
        fec_adq = "";
        flg_act = "false";
        fec_cam = "";
        flg_sta = "Operativo";
        det_obs = "";

        mimagen_det = null;
        mancho_det = "200";

        catlisequdet = null;
        equiposdet.clear();

        dasi_fec_asi = null;
        dasi_fec_cam = null;

        asi_cod_lis_equ = "";
        asi_nom_cli = "";
        asi_cod_det = "";
        asi_cod_pai = cbean.getCod_pai();
        asi_nom_dep = "";
        asi_nom_usu = "";
        asi_fec_asi = "";
        asi_fec_cam = "";

        asieditable = "false";

        catasiequcli = null;
        asiequcli.clear();

        dman_enc_fec_ini = null;
        dman_enc_fec_fin = null;

        man_enc_cod_man = "";
        man_enc_cod_pai = cbean.getCod_pai();
        man_enc_nom_cli = "";
        man_enc_tip_man = "Preventivo";
        man_enc_fec_ini = "";
        man_enc_fec_fin = "";
        man_enc_usu_res = cbean.getCod_usu();
        man_enc_det_obs = "";
        man_enc_det_sta = "PROGRAMADO";

        catmanequit = null;
        manequit.clear();

        dman_det_fec_ini = null;

        man_det_cod_det = "";
        man_det_cod_pie = "0";
        man_det_det_obs = "";
        man_det_fec_acc = "";
        man_det_det_cos = 0.0;
        man_det_det_tie = 0;
        man_det_usu_res = cbean.getCod_usu();
        man_det_rut_ane = "";

        catmanequitdet = null;
        manequitdet.clear();

    }

    public void nuevoDet() {
        dfec_adq = null;
        dfec_cam = null;

        cod_det = "";
        cod_pie = "";
        nom_ite = "";
        det_mar = "";
        det_mod = "";
        det_ser = "";
        det_sop = "";
        det_bit = "0";
        fec_adq = "";
        flg_act = "false";
        fec_cam = "";
        flg_sta = "Operativo";
        det_obs = "";

        mimagen_det = null;
        mancho_det = "200";

        catlisequdet = null;

        recuperarimagen();

    }

    public void nuevoAsi() {
        dasi_fec_asi = null;
        dasi_fec_cam = null;

        asi_cod_lis_equ = "";
        asi_nom_cli = "";
        asi_cod_det = "";
        asi_cod_pai = cbean.getCod_pai();
        asi_nom_dep = "";
        asi_nom_usu = "";
        asi_fec_asi = "";
        asi_fec_cam = "";

        asieditable = "false";

        catasiequcli = null;

        recuperarimagen();

    }

    public void nuevoMan() {
        dman_enc_fec_ini = null;
        dman_enc_fec_fin = null;

        man_enc_cod_man = "";
        //man_enc_cod_pai = cod_pai;
        //man_enc_nom_cli = "";
        man_enc_tip_man = "Preventivo";
        man_enc_fec_ini = "";
        man_enc_fec_fin = "";
        man_enc_usu_res = cbean.getCod_usu();
        man_enc_det_obs = "";
        man_enc_det_sta = "PROGRAMADO";

        catmanequit = null;

        dman_det_fec_ini = null;

        man_det_cod_det = "";
        man_det_cod_pie = "0";
        man_det_det_obs = "";
        man_det_fec_acc = "";
        man_det_det_cos = 0.0;
        man_det_det_tie = 0;
        man_det_usu_res = cbean.getCod_usu();

        catmanequitdet = null;
        manequitdet.clear();

        recuperarimagen();
    }

    public void nuevoManDet() {
        dman_det_fec_ini = null;

        man_det_cod_det = "";
        man_det_cod_pie = "0";
        man_det_det_obs = "";
        man_det_fec_acc = "";
        man_det_det_cos = 0.0;
        man_det_det_tie = 0;
        man_det_usu_res = cbean.getCod_usu();
        man_det_rut_ane = "";

        catmanequitdet = null;

        recuperarimagen();

    }

    public void cerrarventana() {
        tabindex = null;

        cod_lis_equ = null;
        cod_pai = null;
        tip_equ = null;
        cod_equ = null;
        rut_ima_enc = null;

        mimagen_enc = null;
        mancho_enc = null;

        paises = null;

        catlisequ = null;
        equipos = null;

        dfec_adq = null;
        dfec_cam = null;

        cod_det = null;
        cod_pie = null;
        nom_ite = null;
        det_mar = null;
        det_mod = null;
        det_ser = null;
        det_sop = null;
        det_bit = null;
        fec_adq = null;
        flg_act = null;
        fec_cam = null;
        flg_sta = null;
        det_obs = null;

        mimagen_det = null;
        mancho_det = null;

        marcas = null;
        modelos = null;
        sistemasop = null;

        catlisequdet = null;
        equiposdet = null;

        dasi_fec_asi = null;
        dasi_fec_cam = null;

        asi_cod_lis_equ = null;
        asi_nom_cli = null;
        asi_cod_det = null;
        asi_cod_pai = null;
        asi_nom_dep = null;
        asi_nom_usu = null;
        asi_fec_asi = null;
        asi_fec_cam = null;

        asieditable = null;

        clientes = null;
        departamentos = null;
        usuarios = null;

        catasiequcli = null;
        asiequcli = null;

        dman_enc_fec_ini = null;
        dman_enc_fec_fin = null;

        man_enc_cod_man = null;
        man_enc_cod_pai = null;
        man_enc_nom_cli = null;
        man_enc_tip_man = null;
        man_enc_fec_ini = null;
        man_enc_fec_fin = null;
        man_enc_usu_res = null;
        man_enc_det_obs = null;
        man_enc_det_sta = null;

        responsables = null;

        catmanequit = null;
        manequit = null;

        dman_det_fec_ini = null;

        man_det_cod_det = null;
        man_det_cod_pie = null;
        man_det_det_obs = null;
        man_det_fec_acc = null;
        man_det_det_cos = null;
        man_det_det_tie = 0;
        man_det_usu_res = null;
        man_det_rut_ane = null;

        piezas = null;

        catmanequitdet = null;
        manequitdet = null;

        macc = null;
    }

    public void llenarPaises() {
        String mQuery = "";
        try {
            paises.clear();

            mQuery = "select cod_pai,nom_pai from cat_pai order by nom_pai;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEquipos() {
        String mQuery = "", mWhere;

        if (!cod_pai.equals("0")) {
            mWhere = "where cod_pai = " + cod_pai + " ";
        } else {
            mWhere = "";
        }

        try {
            catlisequdet = null;
            equiposdet.clear();
            catasiequcli = null;
            asiequcli.clear();
            catmanequit = null;
            manequit.clear();
            catmanequitdet = null;
            manequitdet.clear();
            catlisequ = null;
            equipos.clear();

            mQuery = "select "
                    + "cod_lis_equ,cod_pai,tip_equ,cod_equ,nom_pai,nom_cli "
                    + "from( "
                    + " select    "
                    + " leq.cod_lis_equ,  "
                    + " leq.cod_pai,  "
                    + " leq.tip_equ,  "
                    + " leq.cod_equ,  "
                    + " pai.nom_pai, "
                    + " ifnull((select   "
                    + "  nom_cli "
                    + "  from it_asi_equ_cli as asi "
                    + "  where asi.cod_lis_equ = leq.cod_lis_equ "
                    + "  order by asi.fec_asi desc limit 1),'No Asignado') as nom_cli "
                    + " from it_lis_equ as leq "
                    + " left join cat_pai as pai on leq.cod_pai = pai.cod_pai "
                    + " ) as total "
                    + mWhere
                    + "order by nom_pai, nom_cli, tip_equ, cod_equ;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatLisEqu(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Encabezado Equipos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMarcas() {
        String mQuery = "";
        try {
            marcas.clear();

            mQuery = "select distinct det_mar from it_lis_equ_det order by det_mar;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                marcas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Marcas en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarModelos() {
        String mQuery = "";
        try {
            modelos.clear();

            mQuery = "select distinct det_mod from it_lis_equ_det order by det_mod;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                modelos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Modelos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarSistemasop() {
        String mQuery = "";
        try {
            sistemasop.clear();

            mQuery = "select distinct det_sop from it_lis_equ_det order by det_sop;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                sistemasop.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Sistemas operativos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEquiposDet() {
        String mQuery = "";
        try {
            catlisequdet = null;
            equiposdet.clear();

            mQuery = "select  "
                    + "cod_lis_equ,  "
                    + "cod_det,  "
                    + "cod_pie,  "
                    + "nom_ite,  "
                    + "ifnull(det_mar,'') as det_mar,  "
                    + "ifnull(det_mod,'') as det_mod,  "
                    + "det_ser,  "
                    + "ifnull(det_sop,'') as det_sop,  "
                    + "det_bit,  "
                    + "case date_format(fec_adq,'%d/%m/%Y') when '00/00/0000' then '' else date_format(fec_adq,'%d/%m/%Y') end as fec_adq,  "
                    + "case date_format(fec_cam,'%d/%m/%Y') when '00/00/0000' then '' else date_format(fec_cam,'%d/%m/%Y') end as fec_cam,  "
                    + "flg_act,  "
                    + "flg_sta,  "
                    + "ifnull(det_obs,'') as det_obs  "
                    + "from it_lis_equ_det "
                    + "where cod_lis_equ = " + cod_lis_equ + " order by cod_det;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equiposdet.add(new CatLisEquDet(
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
                        resVariable.getString(14)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Detalle Equipos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarClientes() {
        String mQuery = "";
        try {
            clientes.clear();

            mQuery = "select distinct nom_cli from it_asi_equ_cli where cod_pai = " + asi_cod_pai + " order by nom_cli;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Clientes en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarDepartamentos() {
        String mQuery = "";
        try {
            departamentos.clear();

            mQuery = "select distinct nom_dep from it_asi_equ_cli where cod_pai = " + asi_cod_pai + " order by nom_dep;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                departamentos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Departamentos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUsuarios() {
        String mQuery = "";
        try {
            usuarios.clear();

            if (asi_nom_cli == null) {
                asi_nom_cli = "";
            }

            mQuery = "select distinct nom_usu from it_asi_equ_cli where cod_pai = " + asi_cod_pai + " and upper(nom_cli) = '" + asi_nom_cli.toUpperCase() + "' order by nom_usu;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                usuarios.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Usuarios en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAsignaciones() {
        String mQuery = "";

        try {
            catasiequcli = null;
            asiequcli.clear();

            mQuery = "select "
                    + "asi.cod_lis_equ, "
                    + "asi.nom_cli, "
                    + "asi.cod_det, "
                    + "asi.cod_pai, "
                    + "ifnull(asi.nom_dep,''), "
                    + "ifnull(asi.nom_usu,''), "
                    + "case date_format(asi.fec_asi,'%d/%m/%Y') when '00/00/0000' then '' else date_format(asi.fec_asi,'%d/%m/%Y') end as fec_asi, "
                    + "case date_format(asi.fec_cam,'%d/%m/%Y') when '00/00/0000' then '' else date_format(asi.fec_cam,'%d/%m/%Y') end as fec_cam, "
                    + "pai.nom_pai "
                    + "from it_asi_equ_cli as asi "
                    + "left join cat_pai as pai on asi.cod_pai = pai.cod_pai "
                    + "where asi.cod_lis_equ = " + cod_lis_equ + " "
                    + "order by pai.nom_pai, asi.nom_cli, asi.fec_asi desc, asi.nom_dep, asi.nom_usu;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                asiequcli.add(new CatAsiEquCli(
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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Asignaciones Clientes Equipos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarResponsables() {
        String mQuery = "";
        try {
            responsables.clear();

            mQuery = "select usu.cod_usu, usu.det_nom "
                    + "from it_cat_usu_esp as ues "
                    + "left join cat_usu as usu on ues.cod_usu = usu.cod_usu "
                    + "where ues.flg_act_des = 1 order by usu.det_nom;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                responsables.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Responsables en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientos() {
        String mQuery = "";

        try {
            catmanequit = null;
            manequit.clear();

            mQuery = "select  "
                    + "man.cod_lis_equ, "
                    + "man.cod_man, "
                    + "man.cod_pai, "
                    + "ifnull(man.nom_cli,'') as nom_cli, "
                    + "man.tip_man, "
                    + "case date_format(man.fec_ini,'%d/%m/%Y') when '00/00/0000' then '' else date_format(man.fec_ini,'%d/%m/%Y') end as fec_ini, "
                    + "case date_format(man.fec_fin,'%d/%m/%Y') when '00/00/0000' then '' else date_format(man.fec_fin,'%d/%m/%Y') end as fec_fin, "
                    + "man.usu_res, "
                    + "man.det_obs, "
                    + "man.det_sta, "
                    + "ifnull(pai.nom_pai,'') as nom_pai, "
                    + "ifnull(usu.det_nom,'') as det_nom "
                    + "from it_man_equ as man "
                    + "left join cat_pai as pai on man.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on man.usu_res = usu.cod_usu "
                    + "where cod_lis_equ = " + cod_lis_equ + " "
                    + "order by fec_ini desc;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                manequit.add(new CatManEquIT(
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
                        resVariable.getString(12)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Encabezado Mantenimiento en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPiezas() {
        String mQuery = "";
        try {
            piezas.clear();

            mQuery = "select cod_pie,concat( nom_ite, ' - Marca: ' , ifnull(det_mar,'Sin Marca'), ' - SN°: ' , det_ser ) as item "
                    + "from it_lis_equ_det where cod_lis_equ =" + cod_lis_equ + ";";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Piezas en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosDet() {
        String mQuery = "";

        try {
            catmanequitdet = null;
            manequitdet.clear();

            mQuery = "select "
                    + "acc.cod_lis_equ, "
                    + "acc.cod_man, "
                    + "acc.cod_det, "
                    + "ifnull(ede.cod_pie,'') as cod_pie,  "
                    + "ifnull(acc.det_obs,'') as det_obs,  "
                    + "case date_format(acc.fec_acc,'%d/%m/%Y') when '00/00/0000' then '' else date_format(acc.fec_acc,'%d/%m/%Y') end as fec_acc, "
                    + "acc.det_cos, "
                    + "acc.det_tie, "
                    + "acc.usu_res, "
                    + "ifnull(usu.nom_usu,'') as nom_usu, "
                    + "ane.des_rut, "
                    + "concat(ifnull(ede.cod_pie,''),' ',ifnull(ede.nom_ite,'')) as nompie "
                    + "from it_man_equ_acc as acc "
                    + "left join it_man_equ_ane as ane on acc.cod_lis_equ = ane.cod_lis_equ and acc.cod_man = ane.cod_man and acc.cod_det = ane.cod_det "
                    + "left join cat_usu as usu on acc.usu_res = usu.cod_usu "
                    + "left join it_lis_equ_det as ede on acc.cod_pie = ede.cod_pie "
                    + "where acc.cod_lis_equ = " + cod_lis_equ + " "
                    + "and acc.cod_man = " + man_enc_cod_man + ";";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                manequitdet.add(new CatManEquITDet(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getDouble(7),
                        resVariable.getInt(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(12)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalle Mantenimiento en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatosEquipos() {
        boolean mValidar = true;
        if ("0".equals(cod_pai)) {
            addMessage("Guardar", "Debe Seleccionar un País", 2);
            return false;
        }

        if ("".equals(tip_equ)) {
            addMessage("Guardar", "Debe Seleccionar un Tipo de Equipo", 2);
            return false;
        }

        if ("".equals(cod_equ)) {
            addMessage("Guardar", "Debe Ingresar un Código para el Equipo", 2);
            return false;
        } else {
            macc.Conectar();
            if (cod_lis_equ.equals("")) {
                if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ where upper(cod_equ) = '" + cod_equ.toUpperCase() + "';").equals("0")) {
                    addMessage("Guardar", "El código Ingresado ya está asignado a otro Equipo", 2);
                    macc.Desconectar();
                    return false;
                }
            } else if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ where upper(cod_equ) = '" + cod_equ.toUpperCase() + "' and cod_lis_equ != " + cod_lis_equ + " ;").equals("0")) {
                addMessage("Guardar", "El código Ingresado ya está asignado a otro Equipo", 2);
                macc.Desconectar();
                return false;
            }
            macc.Desconectar();
        }

        return mValidar;

    }

    public void guardarEquipo() {
        String mQuery = "";
        if (validardatosEquipos()) {
            try {
                macc.Conectar();

                if ("".equals(cod_lis_equ)) {
                    mQuery = "select ifnull(max(cod_lis_equ),0)+1 as codigo from it_lis_equ;";
                    cod_lis_equ = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into it_lis_equ (cod_lis_equ ,cod_pai ,tip_equ ,cod_equ ,fec_edi ,usu_edi ) "
                            + "values (" + cod_lis_equ + "," + cod_pai + ",'" + tip_equ + "','"
                            + cod_equ + "', now()," + cbean.getCod_usu() + ");";
                } else {
                    mQuery = "update it_lis_equ SET "
                            + "cod_pai = " + cod_pai + " "
                            + ",tip_equ = '" + tip_equ + "' "
                            + ",cod_equ = '" + cod_equ + "' "
                            + ",fec_edi = now() "
                            + ",usu_edi = " + cbean.getCod_usu() + " "
                            + "WHERE cod_lis_equ = " + cod_lis_equ + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEquipos();
        }
        nuevo();

    }

    public void upload(FileUploadEvent event) {
        String mQuery = "";
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Procesar Imagen", "Debe Seleccionar un Equipo.", 2);
            rut_ima_enc = "/resources/images/temp/noimage.png";
        } else {
            try {

                copyFile("itlequ_" + cod_lis_equ + ".jpg", event.getFile().getInputstream());

                // ****************************  Inserta Imagen en tabla ************************************************************
                try {
                    macc.Conectar();

                    FileInputStream fis = null;
                    PreparedStatement ps = null;
                    try {
                        macc.Conectar().setAutoCommit(false);
                        File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(rut_ima_enc));
                        fis = new FileInputStream(file);
                        if ("0".equals(macc.strQuerySQLvariable("select count(cod_lis_equ) conta from it_lis_equ_ima where cod_lis_equ=" + cod_lis_equ + ";"))) {
                            mQuery = "insert into it_lis_equ_ima(cod_lis_equ,des_rut,det_ima) values(?,?,?)";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setString(1, cod_lis_equ);
                            ps.setString(2, rut_ima_enc);
                            ps.setBinaryStream(3, fis, (int) file.length());
                        } else {
                            mQuery = "update it_lis_equ_ima set des_rut = ?, det_ima = ? where cod_lis_equ=?;";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setString(1, rut_ima_enc);
                            ps.setBinaryStream(2, fis, (int) file.length());
                            ps.setString(3, cod_lis_equ);
                        }

                        ps.executeUpdate();
                        macc.Conectar().commit();

                    } catch (Exception ex) {

                    } finally {
                        try {
                            ps.close();
                            fis.close();
                        } catch (Exception ex) {

                        }
                    }
                    recuperarimagen();

                    macc.Desconectar();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                addMessage("Procesar Archivo", "El Archivo " + event.getFile().getFileName() + " No se ha podido Cargar. " + e.getMessage(), 2);
                System.out.println("Error en subir archivo Imagen EquipoIT." + e.getMessage());
            }
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            rut_ima_enc = "/resources/images/temp/" + fileName;
            //ane_rut_nom = "/resources/images/temp/" + fileName;

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
            addMessage("Copiar Archivo Imagen Equipo IT", "El Archivo en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    public void recuperarimagen() {
        String mQuery = "";
        macc.Conectar();
        mimagen_enc = null;
        try {
            mQuery = "select det_ima from it_lis_equ_ima where cod_lis_equ=" + cod_lis_equ + ";";
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen_enc = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mancho_enc = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 180.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from it_lis_equ_ima where cod_lis_equ = 0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen_enc = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mancho_enc = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 180.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            System.out.println("Error en recuperarimagen Equipo IT. " + e.getMessage() + " Query: " + mQuery);

        }

        macc.Desconectar();
    }

    public boolean validareliminarEquipo() {
        boolean mvalidar = true;

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Eliminar", "Debe seleccionar un registro", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_asi_equ_cli where cod_lis_equ = " + cod_lis_equ + ";").equals("0")) {
            macc.Desconectar();
            addMessage("Eliminar", "Este equipo tiene asignaciones de Cliente", 2);
            return false;
        }

        if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_man_equ where cod_lis_equ = " + cod_lis_equ + ";").equals("0")) {
            macc.Desconectar();
            addMessage("Eliminar", "Este equipo tiene mantenimientos relacionados", 2);
            return false;
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminarEquipo() {
        String mQuery = "";

        if (validareliminarEquipo()) {
            try {
                macc.Conectar();

                mQuery = "delete from it_lis_equ_ima where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ_det_ima where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ_det where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con Éxito", 1);
                macc.Desconectar();

            } catch (Exception e) {
                addMessage("Eliminar", "Error al intentar Eliminar. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEquipos();
            nuevo();
        }

    }

    public boolean validardatosEquiposDet() {
        boolean mValidar = true;
        if (det_mar == null) {
            det_mar = "";
        }

        if (det_mod == null) {
            det_mod = "";
        }

        if (det_sop == null) {
            det_sop = "";
        }

        if ("0".equals(cod_lis_equ) || "".equals(cod_lis_equ)) {
            addMessage("Guardar", "Debe Seleccionar un Equipo", 2);
            return false;
        }

        if ("".equals(cod_pie)) {
            addMessage("Guardar", "Debe ingresar un Código de pieza", 2);
            return false;
        }

        if ("".equals(nom_ite)) {
            addMessage("Guardar", "Debe Ingresar un Nombre de pieza", 2);
            return false;
        } else {
            macc.Conectar();
            if (cod_det.equals("")) {
                if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ_det where upper(nom_ite) = '" + nom_ite.toUpperCase() + "';").equals("0")) {
                    addMessage("Guardar", "El Nombre Ingresado ya está asignado a otra Pieza", 2);
                    macc.Desconectar();
                    return false;
                }
            } else if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ_det where upper(nom_ite) = '" + nom_ite.toUpperCase() + "' and cod_lis_equ != " + cod_lis_equ + " ;").equals("0")) {
                addMessage("Guardar", "El Nombre Ingresado ya está asignado a otra Pieza", 2);
                macc.Desconectar();
                return false;
            } else if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ_det where upper(nom_ite) = '" + nom_ite.toUpperCase() + "' and cod_lis_equ = " + cod_lis_equ + " and cod_det != " + cod_det + ";").equals("0")) {
                addMessage("Guardar", "El Nombre Ingresado ya está asignado a otra Pieza", 2);
                macc.Desconectar();
                return false;
            }
            macc.Desconectar();
        }

        if ("".equals(det_sop) && !"0".equals(det_bit)) {
            addMessage("Guardar", "No puede establecer Bits si no ha ingresado un Sistema Operativo", 2);
            return false;
        }

        return mValidar;

    }

    public void guardarEquipoDet() {
        String mQuery = "";
        if (validardatosEquiposDet()) {
            try {
                String mfecadq, mfeccam;

                if (fec_adq.equals("") || fec_adq.equals("00/00/0000")) {
                    mfecadq = " null ";
                } else {
                    mfecadq = " str_to_date('" + fec_adq + "','%d/%m/%Y') ";
                }

                if (flg_act.equals("false")) {
                    mfeccam = " null ";
                } else if (fec_cam.equals("") || fec_cam.equals("00/00/0000")) {
                    mfeccam = " null ";
                } else {
                    mfeccam = " str_to_date('" + fec_cam + "','%d/%m/%Y') ";
                }

                macc.Conectar();

                if ("".equals(cod_det)) {
                    mQuery = "select ifnull(max(cod_det),0)+1 as codigo from it_lis_equ_det where cod_lis_equ = " + cod_lis_equ + ";";
                    cod_det = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into it_lis_equ_det (cod_lis_equ,cod_det,cod_pie,nom_ite,"
                            + "det_mar,det_mod,det_ser,det_sop,det_bit,fec_adq,fec_cam,flg_act,"
                            + "flg_sta,det_obs,fec_edi,usu_edi) "
                            + "values (" + cod_lis_equ + "," + cod_det + ",'" + cod_pie + "','"
                            + nom_ite + "','" + det_mar + "','" + det_mod + "','" + det_ser + "','"
                            + det_sop + "','" + det_bit + "'," + mfecadq + "," + mfeccam + ",'"
                            + flg_act + "','" + flg_sta + "','" + det_obs
                            + "', now()," + cbean.getCod_usu() + ");";
                } else {
                    mQuery = "update it_lis_equ_det SET "
                            + "cod_pie = '" + cod_pie + "' "
                            + ",nom_ite = '" + nom_ite + "' "
                            + ",det_mar = '" + det_mar + "' "
                            + ",det_mod = '" + det_mod + "' "
                            + ",det_ser = '" + det_ser + "' "
                            + ",det_sop = '" + det_sop + "' "
                            + ",det_bit = '" + det_bit + "' "
                            + ",fec_adq = " + mfecadq + " "
                            + ",fec_cam = " + mfeccam + " "
                            + ",flg_act = '" + flg_act + "' "
                            + ",flg_sta = '" + flg_sta + "' "
                            + ",det_obs = '" + det_obs + "' "
                            + ",fec_edi = now() "
                            + ",usu_edi = " + cbean.getCod_usu() + " "
                            + "WHERE cod_lis_equ = " + cod_lis_equ + " "
                            + "and cod_det = " + cod_det + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Detalle Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMarcas();
            llenarModelos();
            llenarSistemasop();
            llenarEquiposDet();
            llenarPiezas();

        }
        nuevoDet();
    }

    public void uploadDet(FileUploadEvent event) {
        String mQuery = "";
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Procesar Imagen", "Debe Seleccionar un Equipo.", 2);
            rut_ima_det = "/resources/images/temp/noimage.png";
        } else if ("".equals(cod_det) || "0".equals(cod_det)) {
            addMessage("Procesar Imagen", "Debe Seleccionar una Pieza.", 2);
            rut_ima_det = "/resources/images/temp/noimage.png";
        } else {
            try {

                copyFileDet("itlequ_det_" + cod_lis_equ + "_" + cod_det + ".jpg", event.getFile().getInputstream());

                // ****************************  Inserta Imagen en tabla ************************************************************
                try {
                    macc.Conectar();

                    FileInputStream fis = null;
                    PreparedStatement ps = null;
                    try {
                        macc.Conectar().setAutoCommit(false);
                        File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(rut_ima_det));
                        fis = new FileInputStream(file);
                        if ("0".equals(macc.strQuerySQLvariable("select count(cod_lis_equ) conta from it_lis_equ_det_ima where cod_lis_equ=" + cod_lis_equ + " and cod_det = " + cod_det + ";"))) {
                            mQuery = "insert into it_lis_equ_det_ima (cod_lis_equ,cod_det,des_rut,det_ima) values(?,?,?,?)";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setString(1, cod_lis_equ);
                            ps.setString(2, cod_det);
                            ps.setString(3, rut_ima_det);
                            ps.setBinaryStream(4, fis, (int) file.length());
                        } else {
                            mQuery = "update it_lis_equ_det_ima set des_rut = ?, det_ima = ? where cod_lis_equ=? and cod_det =?;";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setString(1, rut_ima_det);
                            ps.setBinaryStream(2, fis, (int) file.length());
                            ps.setString(3, cod_lis_equ);
                            ps.setString(4, cod_det);
                        }

                        ps.executeUpdate();
                        macc.Conectar().commit();

                    } catch (Exception ex) {

                    } finally {
                        try {
                            ps.close();
                            fis.close();
                        } catch (Exception ex) {

                        }
                    }

                    recuperarimagen();
                    recuperarimagenDet();

                    macc.Desconectar();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                addMessage("Procesar Archivo", "El Archivo " + event.getFile().getFileName() + " No se ha podido Cargar. " + e.getMessage(), 2);
                System.out.println("Error en subir archivo Imagen EquipoIT Det." + e.getMessage());
            }
        }

    }

    public void copyFileDet(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            rut_ima_det = "/resources/images/temp/" + fileName;
            //ane_rut_nom = "/resources/images/temp/" + fileName;

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
            addMessage("Copiar Archivo Imagen Equipo IT Det", "El Archivo en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    public void recuperarimagenDet() {
        String mQuery = "";
        macc.Conectar();
        mimagen_det = null;
        try {
            mQuery = "select det_ima from it_lis_equ_det_ima where cod_lis_equ=" + cod_lis_equ + " and cod_det = " + cod_det + ";";
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen_det = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mancho_det = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 200.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from it_lis_equ_det_ima where cod_lis_equ = 0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen_det = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mancho_det = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 200.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            System.out.println("Error en recuperarimagen Equipo IT Det. " + e.getMessage() + " Query: " + mQuery);

        }

        macc.Desconectar();
    }

    public boolean validareliminarEquipoDet() {
        boolean mvalidar = true;

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Eliminar", "Debe seleccionar un Equipo", 2);
            return false;
        }

        if ("".equals(cod_det) || "0".equals(cod_det)) {
            addMessage("Eliminar", "Debe seleccionar un Registro", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_man_equ_acc where cod_lis_equ = " + cod_lis_equ + " and cod_pie = '" + cod_pie + "';").equals("0")) {
            macc.Desconectar();
            addMessage("Eliminar", "Esta pieza tiene mantenimientos relacionados", 2);
            return false;
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminarEquipoDet() {
        String mQuery = "";

        if (validareliminarEquipoDet()) {
            try {
                macc.Conectar();

                mQuery = "delete from it_lis_equ_det_ima where cod_lis_equ=" + cod_lis_equ + " and cod_det = " + cod_det + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ_det where cod_lis_equ=" + cod_lis_equ + " and cod_det = " + cod_det + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con Éxito", 1);
                macc.Desconectar();

            } catch (Exception e) {
                addMessage("Eliminar", "Error al intentar Eliminar. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Detalle Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMarcas();
            llenarModelos();
            llenarSistemasop();
            llenarEquiposDet();
            nuevoDet();
            llenarPiezas();
        }

    }

    public boolean validardatosAsignaciones() {
        boolean mValidar = true;
        if (asi_nom_cli == null) {
            asi_nom_cli = "";
        }

        if (asi_nom_dep == null) {
            asi_nom_dep = "";
        }

        if (asi_nom_usu == null) {
            asi_nom_usu = "";
        }

        if (asi_fec_asi == null) {
            asi_fec_asi = "";
        }

        if (asi_fec_cam == null) {
            asi_fec_cam = "";
        }

        if ("0".equals(cod_lis_equ) || "".equals(cod_lis_equ)) {
            addMessage("Guardar", "Debe Seleccionar un Equipo", 2);
            return false;
        }

        if ("0".equals(asi_cod_pai)) {
            addMessage("Guardar", "Debe seleccionar un País", 2);
            return false;
        }

        if ("".equals(asi_fec_asi)) {
            addMessage("Guardar", "Debe seleccionar una Fecha de Asignación", 2);
            return false;
        }

        if ("".equals(asi_nom_cli)) {
            addMessage("Guardar", "Debe Ingresar un Nombre de Cliente", 2);
            return false;
        } else {
            macc.Conectar();
            if (asi_cod_det.equals("")) {
                if (!macc.strQuerySQLvariable("select count(cod_lis_equ) "
                        + "from it_asi_equ_cli "
                        + "where cod_lis_equ = " + cod_lis_equ + " "
                        + "and upper(nom_cli) = '" + asi_nom_cli.toUpperCase() + "' "
                        + "and cod_pai = " + asi_cod_pai + " "
                        + "and upper(nom_dep) = '" + asi_nom_dep.toUpperCase() + "' "
                        + "and upper(nom_usu) = '" + asi_nom_usu.toUpperCase() + "' "
                        + ";").equals("0")) {
                    addMessage("Guardar", "Esta asignación ya existe para la misma fecha", 2);
                    macc.Desconectar();
                    return false;
                }
            }
            macc.Desconectar();
        }

        return mValidar;

    }

    public void guardarAsignaciones() {
        String mQuery = "";
        if (validardatosAsignaciones()) {
            try {
                String mfeccam;

                if (asi_fec_cam.equals("") || asi_fec_cam.equals("00/00/0000")) {
                    mfeccam = " null ";
                } else {
                    mfeccam = " str_to_date('" + asi_fec_cam + "','%d/%m/%Y') ";
                }

                macc.Conectar();

                if ("".equals(asi_cod_det)) {
                    mQuery = "select ifnull(max(cod_det),0)+1 as codigo from it_asi_equ_cli "
                            + "where cod_lis_equ = " + cod_lis_equ + ";";
                    asi_cod_det = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into it_asi_equ_cli (cod_lis_equ,cod_pai,nom_cli,cod_det,"
                            + "nom_dep,nom_usu,fec_asi,fec_cam,fec_edi,usu_edi) "
                            + "values (" + cod_lis_equ + "," + asi_cod_pai + ",'" + asi_nom_cli.toUpperCase() + "',"
                            + asi_cod_det + ",'" + asi_nom_dep.toUpperCase() + "','" + asi_nom_usu.toUpperCase() + "',str_to_date('" + asi_fec_asi + "','%d/%m/%Y'), " + mfeccam
                            + ", now()," + cbean.getCod_usu() + ");";
                } else {
                    mQuery = "update it_asi_equ_cli SET "
                            + "cod_pai = " + asi_cod_pai + " "
                            + ",nom_cli = '" + asi_nom_cli.toUpperCase() + "' "
                            + ",nom_dep = '" + asi_nom_dep.toUpperCase() + "' "
                            + ",nom_usu = '" + asi_nom_usu.toUpperCase() + "' "
                            + ",fec_asi = str_to_date('" + asi_fec_asi + "','%d/%m/%Y') "
                            + ",fec_cam = " + mfeccam + " "
                            + ",fec_edi = now() "
                            + ",usu_edi = " + cbean.getCod_usu() + " "
                            + "where cod_lis_equ = " + cod_lis_equ + " "
                            + "and cod_det = " + asi_cod_det + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Asignaciones Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarAsignaciones();

            llenarClientes();
            llenarDepartamentos();
            llenarUsuarios();
        }
        nuevoAsi();

    }

    public boolean validareliminarAsignaciones() {
        boolean mvalidar = true;

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Eliminar", "Debe seleccionar un Equipo", 2);
            return false;
        }

        if ("".equals(asi_cod_det) || "0".equals(asi_cod_det)) {
            addMessage("Eliminar", "Debe seleccionar un Registro", 2);
            return false;
        }

        return mvalidar;
    }

    public void eliminarAsignaciones() {
        String mQuery = "";

        if (validareliminarAsignaciones()) {
            try {
                macc.Conectar();
                mQuery = "delete from it_asi_equ_cli where cod_lis_equ=" + cod_lis_equ + " and cod_det = " + asi_cod_det + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con Éxito", 1);
                macc.Desconectar();

            } catch (Exception e) {
                addMessage("Eliminar", "Error al intentar Eliminar. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Detalle Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarAsignaciones();
            nuevoAsi();
            llenarClientes();
            llenarDepartamentos();
            llenarUsuarios();
        }

    }

    public boolean validardatosMantenimiento() {
        boolean mValidar = true;
        if (man_enc_fec_ini == null) {
            man_enc_fec_ini = "";
        }

        if (man_enc_fec_fin == null) {
            man_enc_fec_fin = "";
        }

        if (man_enc_nom_cli == null) {
            man_enc_nom_cli = "";
        }

        if (man_enc_det_obs == null) {
            man_enc_det_obs = "";
        }

        if ("0".equals(cod_lis_equ) || "".equals(cod_lis_equ)) {
            addMessage("Guardar", "Debe Seleccionar un Equipo", 2);
            return false;
        }

        if ("0".equals(man_enc_cod_pai) || "".equals(man_enc_cod_pai)) {
            addMessage("Guardar", "Debe seleccionar un País", 2);
            return false;
        }

        if ("".equals(man_enc_fec_ini)) {
            addMessage("Guardar", "Debe seleccionar una Fecha de Inicio", 2);
            return false;
        }

        if ("0".equals(man_enc_usu_res)) {
            addMessage("Guardar", "Debe seleccionar un Responsable", 2);
            return false;
        }

        if ("FINALIZADO".equals(man_enc_det_sta) && "".equals(man_enc_fec_fin)) {
            addMessage("Guardar", "Debe ingresar una fecha de Finalización", 2);
            return false;
        }

        if (!"FINALIZADO".equals(man_enc_det_sta) && !"".equals(man_enc_fec_fin)) {
            addMessage("Guardar", "Debe Seleccionar el Estado FINALIZADO al ingresar una Fecha de Finalización", 2);
            return false;
        }

        return mValidar;

    }

    public void guardarMantenimiento() {
        String mQuery = "";
        if (validardatosMantenimiento()) {
            try {
                String mfecini, mfecfin;

                if (man_enc_fec_ini.equals("") || man_enc_fec_ini.equals("00/00/0000")) {
                    mfecini = " null ";
                } else {
                    mfecini = " str_to_date('" + man_enc_fec_ini + "','%d/%m/%Y') ";
                }

                if (man_enc_fec_fin.equals("") || man_enc_fec_fin.equals("00/00/0000")) {
                    mfecfin = " null ";
                } else {
                    mfecfin = " str_to_date('" + man_enc_fec_fin + "','%d/%m/%Y') ";
                }
                macc.Conectar();

                if ("".equals(man_enc_cod_man)) {
                    mQuery = "select ifnull(max(cod_man),0)+1 as codigo from it_man_equ where cod_lis_equ = " + cod_lis_equ + ";";
                    man_enc_cod_man = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into it_man_equ (cod_lis_equ,cod_man,cod_pai,nom_cli,tip_man,fec_ini,fec_fin,usu_res,"
                            + "det_obs,det_sta,usu_edi,fec_edi,pai_ori,cli_ori) "
                            + "values (" + cod_lis_equ + "," + man_enc_cod_man + "," + man_enc_cod_pai + ",'"
                            + man_enc_nom_cli + "','" + man_enc_tip_man + "'," + mfecini + "," + mfecfin + ","
                            + man_enc_usu_res + ",'" + man_enc_det_obs + "','" + man_enc_det_sta + "'," + cbean.getCod_usu() + ",now(),"
                            + man_enc_cod_pai + ",'" + man_enc_nom_cli + "');";
                } else {
                    mQuery = "update it_man_equ SET "
                            + "cod_pai = " + man_enc_cod_pai + " "
                            + ",nom_cli = '" + man_enc_nom_cli + "' "
                            + ",tip_man = '" + man_enc_tip_man + "' "
                            + ",fec_ini = " + mfecini + " "
                            + ",fec_fin = " + mfecfin + " "
                            + ",usu_res = " + man_enc_usu_res + " "
                            + ",det_obs = '" + man_enc_det_obs + "' "
                            + ",det_sta = '" + man_enc_det_sta + "' "
                            + ",usu_edi = " + cbean.getCod_usu() + " "
                            + ",fec_edi = now() "
                            + "WHERE cod_lis_equ = " + cod_lis_equ + " "
                            + "AND cod_man = " + man_enc_cod_man + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Mantenimiento de Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMantenimientos();

        }
        nuevoMan();

    }

    public boolean validareliminarMantenimiento() {
        boolean mvalidar = true;

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Eliminar", "Debe seleccionar un Equipo", 2);
            return false;
        }

        if ("".equals(man_enc_cod_man) || "0".equals(man_enc_cod_man)) {
            addMessage("Eliminar", "Debe seleccionar un Registro", 2);
            return false;
        }

        return mvalidar;
    }

    public void eliminarMantenimiento() {
        String mQuery = "";

        if (validareliminarMantenimiento()) {
            try {
                macc.Conectar();
                mQuery = "delete from it_man_equ_ane where cod_lis_equ=" + cod_lis_equ + " and cod_man = " + man_enc_cod_man + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_man_equ_acc where cod_lis_equ=" + cod_lis_equ + " and cod_man = " + man_enc_cod_man + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_man_equ where cod_lis_equ=" + cod_lis_equ + " and cod_man = " + man_enc_cod_man + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con Éxito", 1);
                macc.Desconectar();

            } catch (Exception e) {
                addMessage("Eliminar", "Error al intentar Eliminar. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Mantenimiento Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMantenimientos();
            nuevoMan();
        }

    }

    public boolean validardatosMantenimientoDet() {
        boolean mValidar = true;
        if (man_det_fec_acc == null) {
            man_det_fec_acc = "";
        }

        if ("0".equals(cod_lis_equ) || "".equals(cod_lis_equ)) {
            addMessage("Guardar", "Debe Seleccionar un Equipo", 2);
            return false;
        }

        if ("0".equals(man_enc_cod_man) || "".equals(man_enc_cod_man)) {
            addMessage("Guardar", "Debe seleccionar un Mantenimiento", 2);
            return false;
        }

        if ("".equals(man_det_det_obs)) {
            addMessage("Guardar", "Debe Ingresar un Detalle o Comentario", 2);
            return false;
        }

        if ("0".equals(man_enc_usu_res)) {
            addMessage("Guardar", "Debe seleccionar un Responsable", 2);
            return false;
        }

        if ("FINALIZADO".equals(man_enc_det_sta) || !"".equals(man_enc_fec_fin)) {
            addMessage("Guardar", "Este Mantenimiento ya ha sido Finalizado", 2);
            return false;
        }

        return mValidar;

    }

    public void guardarMantenimientoDet() {
        String mQuery = "";
        if (validardatosMantenimientoDet()) {
            try {
                String mfecini;

                if (man_det_fec_acc.equals("") || man_det_fec_acc.equals("00/00/0000")) {
                    mfecini = " null ";
                } else {
                    mfecini = " str_to_date('" + man_det_fec_acc + "','%d/%m/%Y') ";
                }

                macc.Conectar();

                if ("".equals(man_det_cod_det)) {
                    mQuery = "select ifnull(max(cod_det),0)+1 as codigo from it_man_equ_acc where cod_lis_equ = " + cod_lis_equ + " and cod_man = " + man_enc_cod_man + ";";
                    man_det_cod_det = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into it_man_equ_acc (cod_lis_equ,cod_man,cod_det,cod_pie,det_obs,fec_acc,det_cos,det_tie,usu_res,usu_edi,fec_edi) "
                            + "values (" + cod_lis_equ + "," + man_enc_cod_man + "," + man_det_cod_det + ",'"
                            + man_det_cod_pie + "','" + man_det_det_obs + "'," + mfecini + "," + man_det_det_cos + ","
                            + man_det_det_tie + "," + man_det_usu_res + "," + cbean.getCod_usu() + ",now());";
                } else {
                    mQuery = "update it_man_equ_acc SET "
                            + "cod_pie = " + man_det_cod_pie + " "
                            + ",det_obs = '" + man_det_det_obs + "' "
                            + ",fec_acc = " + mfecini + " "
                            + ",det_cos = " + man_det_det_cos + " "
                            + ",det_tie = " + man_det_det_tie + " "
                            + ",usu_res = " + man_det_usu_res + " "
                            + ",usu_edi = " + cbean.getCod_usu() + " "
                            + ",fec_edi = now() "
                            + "WHERE cod_lis_equ = " + cod_lis_equ + " "
                            + "AND cod_man = " + man_enc_cod_man + " "
                            + "and cod_det = " + man_det_cod_det + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Detalle Mantenimiento de Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMantenimientosDet();

        }
        nuevoManDet();

    }

    public void uploadAnexo(FileUploadEvent event) {
        String mQuery = "";
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Procesar Imagen", "Debe Seleccionar un Equipo.", 2);
            rut_ima_ane = "/resources/images/temp/noimage.png";
        } else if ("".equals(man_det_cod_det) || "0".equals(man_det_cod_det)) {
            addMessage("Procesar Imagen", "Debe Seleccionar un detalle de mantenimiento.", 2);
            rut_ima_ane = "/resources/images/temp/noimage.png";
        } else {
            try {

                copyFileAnexo("itlequ_man_det_" + cod_lis_equ + "_" + man_enc_cod_man + "_" + man_det_cod_det + ".jpg", event.getFile().getInputstream());

                // ****************************  Inserta Imagen en tabla ************************************************************
                try {
                    macc.Conectar();

                    FileInputStream fis = null;
                    PreparedStatement ps = null;
                    try {
                        macc.Conectar().setAutoCommit(false);
                        File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(rut_ima_ane));
                        fis = new FileInputStream(file);
                        if ("0".equals(macc.strQuerySQLvariable("select count(cod_lis_equ) conta "
                                + "from it_man_equ_ane "
                                + "where cod_lis_equ=" + cod_lis_equ
                                + " and cod_man = " + man_enc_cod_man
                                + " and cod_det = " + man_det_cod_det + ";"))) {
                            mQuery = "insert into it_man_equ_ane (cod_lis_equ,cod_man,cod_det,nom_arc,des_rut,det_ima) values(?,?,?,?,?,?)";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setString(1, cod_lis_equ);
                            ps.setString(2, man_enc_cod_man);
                            ps.setString(3, man_det_cod_det);
                            ps.setString(4, event.getFile().getFileName());
                            ps.setString(5, rut_ima_ane);
                            ps.setBinaryStream(6, fis, (int) file.length());
                        } else {
                            mQuery = "update it_man_equ_ane set des_rut = ?, det_ima = ?, nom_arc = ? where cod_lis_equ=? and cod_man =? and cod_det = ?;";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setString(1, rut_ima_ane);
                            ps.setBinaryStream(2, fis, (int) file.length());
                            ps.setString(3, event.getFile().getFileName());
                            ps.setString(4, cod_lis_equ);
                            ps.setString(5, man_enc_cod_man);
                            ps.setString(6, man_det_cod_det);
                        }

                        ps.executeUpdate();
                        macc.Conectar().commit();

                    } catch (Exception ex) {

                    } finally {
                        try {
                            ps.close();
                            fis.close();
                        } catch (Exception ex) {

                        }
                    }

                    nuevoManDet();
                    llenarMantenimientosDet();
                    man_det_rut_ane = "";

                    macc.Desconectar();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                addMessage("Procesar Archivo", "El Archivo " + event.getFile().getFileName() + " No se ha podido Cargar. " + e.getMessage(), 2);
                System.out.println("Error en subir archivo EquipoIT Anexo." + e.getMessage());
            }
        }

    }

    public void copyFileAnexo(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            rut_ima_ane = "/resources/images/temp/" + fileName;
            //ane_rut_nom = "/resources/images/temp/" + fileName;

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
            addMessage("Copiar Archivo Imagen Equipo IT Anexo", "El Archivo en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    public void descargaranexo() {
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ) || "".equals(man_det_cod_det) || "0".equals(man_det_cod_det) || "".equals(man_det_rut_ane)) {
            addMessage("Descargar", "Debe seleccionar un Detalle de Mantenimiento con Anexo", 2);
        } else {
            byte[] fileBytes;
            String mQuery;
            macc.Conectar();
            try {
                mQuery = "select det_ima from it_man_equ_ane where cod_lis_equ=" + cod_lis_equ + " and cod_man = " + man_enc_cod_man + " and cod_det=" + man_det_cod_det + ";";

                ResultSet rs = macc.querySQLvariable(mQuery);
                if (rs.next()) {
                    File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                    String destinationO = mIMGFile.getPath().replace("config.xml", "");

                    fileBytes = rs.getBytes(1);
                    OutputStream targetFile = new FileOutputStream(destinationO + man_det_rut_ane.replace("/resources/images/temp/", ""));

                    targetFile.write(fileBytes);
                    targetFile.flush();
                    targetFile.close();
                    mIMGFile = null;

                    try {
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("window.open('" + "/totaltracking/faces" + man_det_rut_ane + "', '_blank')");
                    } catch (Exception e) {
                        System.out.println("Error en redireccionar a descarga en ManEquiposIT. " + e.getMessage());
                    }

                }
                rs.close();

            } catch (Exception e) {
                System.out.println("Error en descargar archivo ManEquiposIT. " + e.getMessage());
            }
            macc.Desconectar();
        }
        recuperarimagen();

    }

    public boolean validareliminarMantenimientoDet() {
        boolean mvalidar = true;

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Eliminar", "Debe seleccionar un Equipo", 2);
            return false;
        }

        if ("".equals(man_enc_cod_man) || "0".equals(man_enc_cod_man)) {
            addMessage("Eliminar", "Debe seleccionar un Mantenimiento", 2);
            return false;
        }

        if ("".equals(man_det_cod_det) || "0".equals(man_det_cod_det)) {
            addMessage("Eliminar", "Debe seleccionar un Registro", 2);
            return false;
        }

        return mvalidar;
    }

    public void eliminarMantenimientoDet() {
        String mQuery = "";

        if (validareliminarMantenimientoDet()) {
            try {
                macc.Conectar();
                mQuery = "delete from it_man_equ_ane where cod_lis_equ=" + cod_lis_equ + " and cod_man = " + man_enc_cod_man + " and cod_det = " + man_det_cod_det + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_man_equ_acc where cod_lis_equ=" + cod_lis_equ + " and cod_man = " + man_enc_cod_man + " and cod_det = " + man_det_cod_det + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con Éxito", 1);
                macc.Desconectar();

            } catch (Exception e) {
                addMessage("Eliminar", "Error al intentar Eliminar. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Detalle Mantenimiento Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMantenimientosDet();
            nuevoManDet();
        }

    }

    public void onRowSelectEnc(SelectEvent event) {
        cod_lis_equ = ((CatLisEqu) event.getObject()).getCod_lis_equ();
        cod_pai = ((CatLisEqu) event.getObject()).getCod_pai();
        tip_equ = ((CatLisEqu) event.getObject()).getTip_equ();
        cod_equ = ((CatLisEqu) event.getObject()).getCod_equ();

        man_enc_cod_pai = ((CatLisEqu) event.getObject()).getCod_pai();
        man_enc_nom_cli = ((CatLisEqu) event.getObject()).getNomcli();

        llenarMarcas();
        llenarModelos();
        llenarSistemasop();
        llenarEquiposDet();

        llenarClientes();
        llenarDepartamentos();
        llenarUsuarios();
        llenarAsignaciones();

        llenarMantenimientos();

        llenarPiezas();

        recuperarimagen();

        man_det_rut_ane = "";

    }

    public void onRowSelectEncDet(SelectEvent event) {
        cod_det = ((CatLisEquDet) event.getObject()).getCod_det();
        cod_pie = ((CatLisEquDet) event.getObject()).getCod_pie();
        nom_ite = ((CatLisEquDet) event.getObject()).getNom_ite();
        det_mar = ((CatLisEquDet) event.getObject()).getDet_mar();
        det_mod = ((CatLisEquDet) event.getObject()).getDet_mod();
        det_ser = ((CatLisEquDet) event.getObject()).getDet_ser();
        det_sop = ((CatLisEquDet) event.getObject()).getDet_sop();
        det_bit = ((CatLisEquDet) event.getObject()).getDet_bit();
        fec_adq = ((CatLisEquDet) event.getObject()).getFec_adq();
        flg_act = ((CatLisEquDet) event.getObject()).getFlg_act();
        fec_cam = ((CatLisEquDet) event.getObject()).getFec_cam();
        flg_sta = ((CatLisEquDet) event.getObject()).getFlg_sta();
        det_obs = ((CatLisEquDet) event.getObject()).getDet_obs();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if (fec_adq == null) {
            fec_adq = "";
        }

        if (fec_cam == null) {
            fec_cam = "";
        }

        if (fec_adq.equals("")) {
            dfec_adq = null;
        } else {
            try {
                dfec_adq = format.parse(fec_adq);
            } catch (Exception ex) {

            }
        }

        if (fec_cam.equals("")) {
            dfec_cam = null;
        } else {
            try {
                dfec_cam = format.parse(fec_cam);
            } catch (Exception ex) {

            }
        }

        recuperarimagen();
        recuperarimagenDet();

    }

    public void onRowSelectAsignaciones(SelectEvent event) {
        asi_cod_det = ((CatAsiEquCli) event.getObject()).getCod_det();
        asi_nom_cli = ((CatAsiEquCli) event.getObject()).getNom_cli();
        asi_cod_pai = ((CatAsiEquCli) event.getObject()).getCod_pai();
        asi_nom_dep = ((CatAsiEquCli) event.getObject()).getNom_dep();
        asi_nom_usu = ((CatAsiEquCli) event.getObject()).getNom_usu();
        asi_fec_asi = ((CatAsiEquCli) event.getObject()).getFec_asi();
        asi_fec_cam = ((CatAsiEquCli) event.getObject()).getFec_cam();

        asieditable = "true";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if (asi_fec_asi == null) {
            asi_fec_asi = "";
        }

        if (asi_fec_cam == null) {
            asi_fec_cam = "";
        }

        if (asi_fec_asi.equals("")) {
            dasi_fec_asi = null;
        } else {
            try {
                dasi_fec_asi = format.parse(asi_fec_asi);
            } catch (Exception ex) {

            }
        }

        if (asi_fec_cam.equals("")) {
            dasi_fec_cam = null;
        } else {
            try {
                dasi_fec_cam = format.parse(asi_fec_cam);
            } catch (Exception ex) {

            }
        }

        recuperarimagen();
    }

    public void onRowSelectMan(SelectEvent event) {
        man_enc_cod_man = ((CatManEquIT) event.getObject()).getCod_man();
        man_enc_cod_pai = ((CatManEquIT) event.getObject()).getCod_pai();
        man_enc_nom_cli = ((CatManEquIT) event.getObject()).getNom_cli();
        man_enc_tip_man = ((CatManEquIT) event.getObject()).getTip_man();
        man_enc_fec_ini = ((CatManEquIT) event.getObject()).getFec_ini();
        man_enc_fec_fin = ((CatManEquIT) event.getObject()).getFec_fin();
        man_enc_usu_res = ((CatManEquIT) event.getObject()).getUsu_res();
        man_enc_det_obs = ((CatManEquIT) event.getObject()).getDet_obs();
        man_enc_det_sta = ((CatManEquIT) event.getObject()).getDet_sta();

        asieditable = "true";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if (man_enc_fec_ini == null) {
            man_enc_fec_ini = "";
        }

        if (man_enc_fec_fin == null) {
            man_enc_fec_fin = "";
        }

        if (man_enc_fec_ini.equals("")) {
            dman_enc_fec_ini = null;
        } else {
            try {
                dman_enc_fec_ini = format.parse(man_enc_fec_ini);
            } catch (Exception ex) {

            }
        }

        if (man_enc_fec_fin.equals("")) {
            dman_enc_fec_fin = null;
        } else {
            try {
                dman_enc_fec_fin = format.parse(man_enc_fec_fin);
            } catch (Exception ex) {

            }
        }

        recuperarimagen();

        llenarMantenimientosDet();
        man_det_rut_ane = "";

    }

    public void onRowSelectManDet(SelectEvent event) {
        man_det_cod_det = ((CatManEquITDet) event.getObject()).getCod_det();
        man_det_cod_pie = ((CatManEquITDet) event.getObject()).getCod_pie();
        man_det_det_obs = ((CatManEquITDet) event.getObject()).getDet_obs();
        man_det_fec_acc = ((CatManEquITDet) event.getObject()).getFec_acc();
        man_det_det_cos = ((CatManEquITDet) event.getObject()).getDet_cos();
        man_det_det_tie = ((CatManEquITDet) event.getObject()).getDet_tie();
        man_det_usu_res = ((CatManEquITDet) event.getObject()).getUsu_res();
        man_det_rut_ane = ((CatManEquITDet) event.getObject()).getAnexo();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if (man_det_fec_acc == null) {
            man_det_fec_acc = "";
        }

        if (man_det_fec_acc.equals("")) {
            dman_det_fec_ini = null;
        } else {
            try {
                dman_det_fec_ini = format.parse(man_det_fec_acc);
            } catch (Exception ex) {

            }
        }

        recuperarimagen();

    }

    public void selectCheckBox() {
        dfec_cam = null;
        fec_cam = "";
    }

    public void selectPaisAsi() {
        llenarClientes();
        llenarDepartamentos();
        usuarios.clear();

        asi_nom_cli = "";
        asi_nom_dep = "";
        asi_nom_usu = "";
    }

    public void selectClientes() {
        llenarUsuarios();

        asi_nom_dep = "";
        asi_nom_usu = "";
    }

    public void dateSelectedAdq(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_adq = format.format(date);
    }

    public void dateSelectedCam(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_cam = format.format(date);
    }

    public void dateSelectedAsi(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        asi_fec_asi = format.format(date);
    }

    public void dateSelectedRet(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        asi_fec_cam = format.format(date);
    }

    public void dateSelectedManIni(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        man_enc_fec_ini = format.format(date);
    }

    public void dateSelectedManFin(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        man_enc_fec_fin = format.format(date);
    }

    public void dateSelectedManDet(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        man_det_fec_acc = format.format(date);
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tbItPiezas":
                tabindex = "0";
                break;
            case "tbItAsignaciones":
                tabindex = "1";
                break;
            case "tbItMantenimientos":
                tabindex = "2";
                break;

        }

    }

    //************************* Funciones para guardar archivos que ya no se utilizan ******************************
    public static void copiarImagen(String filePath, String copyPath, int wh) {
        BufferedImage nuevaImagen = cargarImagen(filePath);
        if (nuevaImagen.getHeight() > nuevaImagen.getWidth()) {
            int heigt = (nuevaImagen.getHeight() * wh) / nuevaImagen.getWidth();
            nuevaImagen = resize(nuevaImagen, wh, heigt);
            int width = (nuevaImagen.getWidth() * wh) / nuevaImagen.getHeight();
            nuevaImagen = resize(nuevaImagen, width, wh);
        } else {
            int width = (nuevaImagen.getWidth() * wh) / nuevaImagen.getHeight();
            nuevaImagen = resize(nuevaImagen, width, wh);
            int heigt = (nuevaImagen.getHeight() * wh) / nuevaImagen.getWidth();
            nuevaImagen = resize(nuevaImagen, wh, heigt);
        }
        GuardarImagen(nuevaImagen, copyPath);
    }

    /*
     Con este método, cargamos la imagen inicial, indicándole el path
     */
    public static BufferedImage cargarImagen(String pathName) {
        BufferedImage nuevaImagen = null;
        try {
            nuevaImagen = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuevaImagen;
    }

    /*
     Este método se utiliza para redimensionar la imagen
     */
    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage imagenRedimensionada = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = imagenRedimensionada.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return imagenRedimensionada;
    }

    /*
     Con este método almacenamos la imagen en el disco
     */
    public static void GuardarImagen(BufferedImage bufferedImage, String pathName) {
        try {
            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
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

    //********************************* GETTERS Y SETTERS ***********************************
    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public CatLisEqu getCatlisequ() {
        return catlisequ;
    }

    public void setCatlisequ(CatLisEqu catlisequ) {
        this.catlisequ = catlisequ;
    }

    public List<CatLisEqu> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatLisEqu> equipos) {
        this.equipos = equipos;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getTip_equ() {
        return tip_equ;
    }

    public void setTip_equ(String tip_equ) {
        this.tip_equ = tip_equ;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getMancho_enc() {
        return mancho_enc;
    }

    public void setMancho_enc(String mancho_enc) {
        this.mancho_enc = mancho_enc;
    }

    public DefaultStreamedContent getMimagen_enc() {
        return mimagen_enc;
    }

    public void setMimagen_enc(DefaultStreamedContent mimagen_enc) {
        this.mimagen_enc = mimagen_enc;
    }

    public List<CatListados> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<CatListados> marcas) {
        this.marcas = marcas;
    }

    public List<CatListados> getModelos() {
        return modelos;
    }

    public void setModelos(List<CatListados> modelos) {
        this.modelos = modelos;
    }

    public List<CatListados> getSistemasop() {
        return sistemasop;
    }

    public void setSistemasop(List<CatListados> sistemasop) {
        this.sistemasop = sistemasop;
    }

    public CatLisEquDet getCatlisequdet() {
        return catlisequdet;
    }

    public void setCatlisequdet(CatLisEquDet catlisequdet) {
        this.catlisequdet = catlisequdet;
    }

    public List<CatLisEquDet> getEquiposdet() {
        return equiposdet;
    }

    public void setEquiposdet(List<CatLisEquDet> equiposdet) {
        this.equiposdet = equiposdet;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getNom_ite() {
        return nom_ite;
    }

    public void setNom_ite(String nom_ite) {
        this.nom_ite = nom_ite;
    }

    public String getDet_mar() {
        return det_mar;
    }

    public void setDet_mar(String det_mar) {
        this.det_mar = det_mar;
    }

    public String getDet_mod() {
        return det_mod;
    }

    public void setDet_mod(String det_mod) {
        this.det_mod = det_mod;
    }

    public String getDet_ser() {
        return det_ser;
    }

    public void setDet_ser(String det_ser) {
        this.det_ser = det_ser;
    }

    public String getDet_sop() {
        return det_sop;
    }

    public void setDet_sop(String det_sop) {
        this.det_sop = det_sop;
    }

    public String getDet_bit() {
        return det_bit;
    }

    public void setDet_bit(String det_bit) {
        this.det_bit = det_bit;
    }

    public String getFlg_act() {
        return flg_act;
    }

    public void setFlg_act(String flg_act) {
        this.flg_act = flg_act;
    }

    public String getFlg_sta() {
        return flg_sta;
    }

    public void setFlg_sta(String flg_sta) {
        this.flg_sta = flg_sta;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public Date getDfec_adq() {
        return dfec_adq;
    }

    public void setDfec_adq(Date dfec_adq) {
        this.dfec_adq = dfec_adq;
    }

    public Date getDfec_cam() {
        return dfec_cam;
    }

    public void setDfec_cam(Date dfec_cam) {
        this.dfec_cam = dfec_cam;
    }

    public DefaultStreamedContent getMimagen_det() {
        return mimagen_det;
    }

    public void setMimagen_det(DefaultStreamedContent mimagen_det) {
        this.mimagen_det = mimagen_det;
    }

    public String getMancho_det() {
        return mancho_det;
    }

    public void setMancho_det(String mancho_det) {
        this.mancho_det = mancho_det;
    }

    public List<CatListados> getClientes() {
        return clientes;
    }

    public void setClientes(List<CatListados> clientes) {
        this.clientes = clientes;
    }

    public List<CatListados> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<CatListados> departamentos) {
        this.departamentos = departamentos;
    }

    public List<CatListados> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatListados> usuarios) {
        this.usuarios = usuarios;
    }

    public CatAsiEquCli getCatasiequcli() {
        return catasiequcli;
    }

    public void setCatasiequcli(CatAsiEquCli catasiequcli) {
        this.catasiequcli = catasiequcli;
    }

    public List<CatAsiEquCli> getAsiequcli() {
        return asiequcli;
    }

    public void setAsiequcli(List<CatAsiEquCli> asiequcli) {
        this.asiequcli = asiequcli;
    }

    public String getAsi_nom_cli() {
        return asi_nom_cli;
    }

    public void setAsi_nom_cli(String asi_nom_cli) {
        this.asi_nom_cli = asi_nom_cli;
    }

    public String getAsi_cod_pai() {
        return asi_cod_pai;
    }

    public void setAsi_cod_pai(String asi_cod_pai) {
        this.asi_cod_pai = asi_cod_pai;
    }

    public String getAsi_nom_dep() {
        return asi_nom_dep;
    }

    public void setAsi_nom_dep(String asi_nom_dep) {
        this.asi_nom_dep = asi_nom_dep;
    }

    public String getAsi_nom_usu() {
        return asi_nom_usu;
    }

    public void setAsi_nom_usu(String asi_nom_usu) {
        this.asi_nom_usu = asi_nom_usu;
    }

    public String getAsieditable() {
        return asieditable;
    }

    public void setAsieditable(String asieditable) {
        this.asieditable = asieditable;
    }

    public Date getDasi_fec_asi() {
        return dasi_fec_asi;
    }

    public void setDasi_fec_asi(Date dasi_fec_asi) {
        this.dasi_fec_asi = dasi_fec_asi;
    }

    public Date getDasi_fec_cam() {
        return dasi_fec_cam;
    }

    public void setDasi_fec_cam(Date dasi_fec_cam) {
        this.dasi_fec_cam = dasi_fec_cam;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public List<CatListados> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<CatListados> responsables) {
        this.responsables = responsables;
    }

    public CatManEquIT getCatmanequit() {
        return catmanequit;
    }

    public void setCatmanequit(CatManEquIT catmanequit) {
        this.catmanequit = catmanequit;
    }

    public List<CatManEquIT> getManequit() {
        return manequit;
    }

    public void setManequit(List<CatManEquIT> manequit) {
        this.manequit = manequit;
    }

    public String getMan_enc_tip_man() {
        return man_enc_tip_man;
    }

    public void setMan_enc_tip_man(String man_enc_tip_man) {
        this.man_enc_tip_man = man_enc_tip_man;
    }

    public String getMan_enc_usu_res() {
        return man_enc_usu_res;
    }

    public void setMan_enc_usu_res(String man_enc_usu_res) {
        this.man_enc_usu_res = man_enc_usu_res;
    }

    public String getMan_enc_det_obs() {
        return man_enc_det_obs;
    }

    public void setMan_enc_det_obs(String man_enc_det_obs) {
        this.man_enc_det_obs = man_enc_det_obs;
    }

    public String getMan_enc_det_sta() {
        return man_enc_det_sta;
    }

    public void setMan_enc_det_sta(String man_enc_det_sta) {
        this.man_enc_det_sta = man_enc_det_sta;
    }

    public Date getDman_enc_fec_ini() {
        return dman_enc_fec_ini;
    }

    public void setDman_enc_fec_ini(Date dman_enc_fec_ini) {
        this.dman_enc_fec_ini = dman_enc_fec_ini;
    }

    public Date getDman_enc_fec_fin() {
        return dman_enc_fec_fin;
    }

    public void setDman_enc_fec_fin(Date dman_enc_fec_fin) {
        this.dman_enc_fec_fin = dman_enc_fec_fin;
    }

    public List<CatListados> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatListados> piezas) {
        this.piezas = piezas;
    }

    public CatManEquITDet getCatmanequitdet() {
        return catmanequitdet;
    }

    public void setCatmanequitdet(CatManEquITDet catmanequitdet) {
        this.catmanequitdet = catmanequitdet;
    }

    public List<CatManEquITDet> getManequitdet() {
        return manequitdet;
    }

    public void setManequitdet(List<CatManEquITDet> manequitdet) {
        this.manequitdet = manequitdet;
    }

    public String getMan_det_cod_pie() {
        return man_det_cod_pie;
    }

    public void setMan_det_cod_pie(String man_det_cod_pie) {
        this.man_det_cod_pie = man_det_cod_pie;
    }

    public String getMan_det_det_obs() {
        return man_det_det_obs;
    }

    public void setMan_det_det_obs(String man_det_det_obs) {
        this.man_det_det_obs = man_det_det_obs;
    }

    public Double getMan_det_det_cos() {
        return man_det_det_cos;
    }

    public void setMan_det_det_cos(Double man_det_det_cos) {
        this.man_det_det_cos = man_det_det_cos;
    }

    public int getMan_det_det_tie() {
        return man_det_det_tie;
    }

    public void setMan_det_det_tie(int man_det_det_tie) {
        this.man_det_det_tie = man_det_det_tie;
    }

    public String getMan_det_usu_res() {
        return man_det_usu_res;
    }

    public void setMan_det_usu_res(String man_det_usu_res) {
        this.man_det_usu_res = man_det_usu_res;
    }

    public Date getDman_det_fec_ini() {
        return dman_det_fec_ini;
    }

    public void setDman_det_fec_ini(Date dman_det_fec_ini) {
        this.dman_det_fec_ini = dman_det_fec_ini;
    }

}
