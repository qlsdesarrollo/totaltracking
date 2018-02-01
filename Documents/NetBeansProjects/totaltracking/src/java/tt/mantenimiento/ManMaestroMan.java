package tt.mantenimiento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.timeline.TimelineModificationEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import tt.general.Accesos;
import tt.general.CatBodegas;
import tt.general.CatCalendario;
import tt.general.CatHistorico;
import tt.general.CatListados;
import tt.general.CatPaises;
import tt.seguridad.CatUsuarios;
import tt.general.Login;

@Named
@ConversationScoped

public class ManMaestroMan implements Serializable {

    private static final long serialVersionUID = 8774297541534938L;
    @Inject
    Login cbean;

    private Accesos maccess;

    private List<CatListados> listadoequipo;
    private List<CatGrupoFallas> grupofallas;
    private List<CatOperaciones> operaciones;
    private List<CatTipos> tipos;
    private List<CatPeriodos> periodos;

    private List<CatPaises> paises;
    private List<CatBodegas> bodegas;
    private List<CatListados> ubicaciones;
    private List<CatPiezas> listapiezas;
    private List<CatUsuarios> usuarios;

    private CatMantenimientos catmantenimientos;
    private List<CatMantenimientos> mantenimientos;

    private CatMantenimientos catmantenimientospm;
    private List<CatMantenimientos> mantenimientospm;

    private CatMantenimientos catmantenimientoshis;
    private List<CatMantenimientos> mantenimientoshis;

    private CatMantenimientosGen catmantenimientosgen;
    private List<CatMantenimientosGen> general;
    private CatMantenimientosPie catmantenimientospie;
    private List<CatMantenimientosPie> piezas;
    private CatMantenimientosAne catmantenimientosane;
    private List<CatMantenimientosAne> anexos;
    private CatMantenimientosFal catmantenimientosfal;
    private List<CatMantenimientosFal> fallas;
    private CatMantenimientosAcc catmantenimientosacc;
    private List<CatMantenimientosAcc> accesorios;

    private List<CatTblPiezasDetalle> piezasdetalle;
    private List<CatTblPiezasDetalle> piezasdetallecopia;
    private List<CatHistorico> historico;

    private CatMantenimientosAne catmantenimientosanehis;
    private List<CatMantenimientosAne> anexoshis;

    private CatCalendario catcalendario;
    private List<CatCalendario> listaMttos;
    private ScheduleModel mttoModel;
    private ScheduleEvent mtto = new DefaultScheduleEvent();

    private String cod_lis_equ, cod_man, cod_tip, det_obs, fec_ini, fec_fin, det_sta, cod_usu, cod_per, flg_ext;
    private String ord_por;

    private String gen_det_man, gen_fec_man, gen_cod_ope, gen_det_obs, gen_cod_usu, gen_det_min;

    private String pie_det_man, pie_fec_man, pie_cod_pai, pie_cod_bod, pie_cod_ubi, pie_cod_pie, pie_num_ser, pie_cod_usu;
    private Double pie_det_can;

    private String ane_det_man, ane_det_obs, ane_tip_ane, ane_rut_ane, ane_cod_usu;

    private String acc_det_man, acc_fec_man, acc_cod_pai, acc_det_can, acc_des_ite, acc_cod_usu;

    private String tabindex, buscar_pais, buscar_pais2, cod_gru_fal, cod_fal, otr_fal, cod_alt, panindex,
            vlimit, tabindex2, detailsenabled, exiscan, editable, switchinicio, switchinicio2;
    private String bfiltro, bfiltropm;

    private Date dfecha1, dfecha2, dfecha3, dfecfinF, dfecini, dfechacreate;

    private String nombrearchivo, nombreequipoman;

    private String vlimit_his, buscar_pais_his, bfiltro_his, cod_lis_equ_his, cod_man_his, ane_det_man_his, ane_rut_ane_his;

    // Variables para timeline
    private TimelineModel modelTimeLine;
    private TimelineEvent eventTimeLine;
    private List<CatCalendario> listaMttosPre;

    private String nombrereporte, nombreexportar;
    private Map<String, Object> parametros;

    public ManMaestroMan() {
    }

    @PostConstruct
    public void init() {
        maccess = new Accesos();
        listaMttos = new ArrayList<>();
        listaMttosPre = new ArrayList<>();
        catcalendario = new CatCalendario();
        mttoModel = new DefaultScheduleModel();
        modelTimeLine = new TimelineModel();

        llenarMttosPreventivos();

        for (CatCalendario cm : listaMttosPre) {
            DefaultScheduleEvent cmt = new DefaultScheduleEvent();
            TimelineEvent tle = new TimelineEvent();
            tle.setData(cm.getDes_equ());
            tle.setStartDate(cm.getFec_ini());

            String status = "";
            String color = cm.getColor();

            if ("lime".equals(color)) {
                status = "entiempo";
            } else if ("yellow".equals(color)) {
                status = "atrasoleve";
            } else if ("red".equals(color)) {
                status = "atrazado";
            }

            tle.setStyleClass(status);
            cmt.setId(cm.getCod_man());
            cmt.setDescription(cm.getDet_obs());
            cmt.setTitle(cm.getDes_equ());
            cmt.setData(cm.getCod_man());
            cmt.setAllDay(true);
            cmt.setEditable(true);
            cmt.setStartDate(cm.getFec_ini());
            if (cm.getFec_fin() == null) {
                cm.setFec_fin(cm.getFec_ini());
            }

            cmt.setEndDate(cm.getFec_fin());

            if ("1".equals(cm.getDet_sta())) {
                cmt.setStyleClass("mtto1");
            } else if ("2".equals(cm.getDet_sta())) {
                cmt.setStyleClass("mtto2");
            } else if ("3".equals(cm.getDet_sta())) {
                cmt.setStyleClass("mtto3");
            } else {
                cmt.setStyleClass("mtto4");
            }

            mttoModel.addEvent(cmt);
            modelTimeLine.add(tle);
        }
        limpiarDirectorioTemporales();
        maccess = null;
    }

    //*********************** Pantalla Integral ********************************
    public void iniciarventanaintegral() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");

        maccess = new Accesos();
        parametros = new HashMap<>();

        dfechacreate = null;
        dfecini = Date.from(Instant.now());
        dfecfinF = null; //Date.from(Instant.now());
        dfecha1 = Date.from(Instant.now());
        dfecha2 = Date.from(Instant.now());
        dfecha3 = Date.from(Instant.now());

        switchinicio = "6";
        switchinicio2 = "1";

        tabindex = "0";

        cod_lis_equ = "";
        cod_man = "";
        cod_tip = "";
        det_obs = "";
        fec_ini = format.format(dfecha1);
        fec_fin = ""; //format.format(dfecha1);
        det_sta = "";
        cod_usu = "0";
        cod_per = "0";
        flg_ext = "0";
        ord_por = cbean.getCod_pai();

        gen_det_man = "";
        gen_fec_man = format.format(dfecha1);
        gen_cod_ope = "";
        gen_det_obs = "";
        gen_cod_usu = cbean.getCod_usu();
        gen_det_min = "";

        pie_det_man = "";
        pie_fec_man = format.format(dfecha1);
        pie_cod_pai = cbean.getCod_pai();
        pie_cod_bod = "0";
        pie_cod_ubi = "0";
        pie_det_can = 0.0;
        pie_cod_pie = "0";
        pie_num_ser = "";
        pie_cod_usu = cbean.getCod_usu();

        ane_det_man = "";
        ane_det_obs = "";
        ane_tip_ane = "";
        ane_rut_ane = "";
        ane_cod_usu = cbean.getCod_usu();

        acc_det_man = "";
        acc_fec_man = format.format(dfecha3);
        acc_cod_pai = cbean.getCod_pai();
        acc_det_can = "";
        acc_des_ite = "";
        acc_cod_usu = cbean.getCod_usu();

        cod_gru_fal = "0";
        cod_fal = "0";
        otr_fal = "";
        buscar_pais = cbean.getCod_pai(); //"0";
        buscar_pais2 = cbean.getCod_pai();
        panindex = "0";

        maccess.Conectar();
        vlimit = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
        cod_alt = ""; //acc.strQuerySQLvariable("select ifnull(max(cod_alt),0)+1 from tbl_mae_man where year(fec_ini)= " + vlimit + " ;");
        maccess.Desconectar();

        vlimit_his = vlimit;
        buscar_pais_his = cbean.getCod_pai();
        bfiltro_his = "";
        cod_lis_equ_his = "";
        cod_man_his = "";
        ane_det_man_his = "";
        ane_rut_ane_his = "";

        nombreequipoman = "";

        listadoequipo = new ArrayList<>();
        grupofallas = new ArrayList<>();
        operaciones = new ArrayList<>();
        tipos = new ArrayList<>();
        periodos = new ArrayList<>();
        paises = new ArrayList<>();
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        listapiezas = new ArrayList<>();
        usuarios = new ArrayList<>();

        catmantenimientos = new CatMantenimientos();
        mantenimientos = new ArrayList<>();

        catmantenimientospm = new CatMantenimientos();
        mantenimientospm = new ArrayList<>();

        catmantenimientoshis = new CatMantenimientos();
        mantenimientoshis = new ArrayList<>();

        catmantenimientosgen = new CatMantenimientosGen();
        general = new ArrayList<>();
        catmantenimientospie = new CatMantenimientosPie();
        piezas = new ArrayList<>();
        catmantenimientosane = new CatMantenimientosAne();
        anexos = new ArrayList<>();
        catmantenimientosfal = new CatMantenimientosFal();
        fallas = new ArrayList<>();
        catmantenimientosacc = new CatMantenimientosAcc();
        accesorios = new ArrayList<>();

        catmantenimientosanehis = new CatMantenimientosAne();
        anexoshis = new ArrayList<>();

        piezasdetalle = new ArrayList<>();
        piezasdetallecopia = new ArrayList<>();
        historico = new ArrayList<>();

        tabindex2 = "0";
        detailsenabled = "true";
        editable = "false";
        exiscan = "0";
        bfiltro = "";
        bfiltropm = "";

        nombrearchivo = "Select One File";

        llenarTipos();
        llenarPeriodos();
        llenarGrupoFallas();
        llenarUsuarios();
        llenarOperaciones();
        llenarPaises();
        llenarBodegas();
        llenarListaPiezas();
        llenarListaEquiposIntegral();

        llenarMantenimientosintegral();
        llenarMantenimientosintegralpm();
        llenarMantenimientosintegralHis();

    }

    public void reiniciarventana() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");

        dfechacreate = null;
        dfecini = Date.from(Instant.now());
        dfecfinF = null; //Date.from(Instant.now());
        dfecha1 = Date.from(Instant.now());
        dfecha2 = Date.from(Instant.now());
        dfecha3 = Date.from(Instant.now());

        switchinicio = "6";
        switchinicio2 = "1";

        tabindex = "0";

        cod_lis_equ = "";
        cod_man = "";
        cod_tip = "";
        det_obs = "";
        fec_ini = format.format(dfecha1);
        fec_fin = ""; //format.format(dfecha1);
        det_sta = "";
        cod_usu = "0";
        cod_per = "0";
        flg_ext = "0";
        ord_por = cbean.getCod_pai();

        gen_det_man = "";
        gen_fec_man = format.format(dfecha1);
        gen_cod_ope = "";
        gen_det_obs = "";
        gen_cod_usu = cbean.getCod_usu();
        gen_det_min = "";

        pie_det_man = "";
        pie_fec_man = format.format(dfecha1);
        pie_cod_pai = cbean.getCod_pai();
        pie_cod_bod = "0";
        pie_cod_ubi = "0";
        pie_det_can = 0.0;
        pie_cod_pie = "0";
        pie_num_ser = "";
        pie_cod_usu = cbean.getCod_usu();

        ane_det_man = "";
        ane_det_obs = "";
        ane_tip_ane = "";
        ane_rut_ane = "";
        ane_cod_usu = cbean.getCod_usu();

        acc_det_man = "";
        acc_fec_man = format.format(dfecha3);
        acc_cod_pai = cbean.getCod_pai();
        acc_det_can = "";
        acc_des_ite = "";
        acc_cod_usu = cbean.getCod_usu();

        cod_gru_fal = "0";
        cod_fal = "0";
        otr_fal = "";
        buscar_pais = cbean.getCod_pai(); //"0";
        buscar_pais2 = cbean.getCod_pai();
        panindex = "0";

        maccess.Conectar();
        vlimit = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
        cod_alt = ""; //acc.strQuerySQLvariable("select ifnull(max(cod_alt),0)+1 from tbl_mae_man where year(fec_ini)= " + vlimit + " ;");
        maccess.Desconectar();

        vlimit_his = vlimit;
        buscar_pais_his = cbean.getCod_pai();
        bfiltro_his = "";
        cod_lis_equ_his = "";
        cod_man_his = "";

        nombreequipoman = "";

        listadoequipo.clear();
        grupofallas.clear();
        operaciones.clear();
        tipos.clear();
        periodos.clear();
        paises.clear();
        bodegas.clear();
        ubicaciones.clear();
        listapiezas.clear();
        usuarios.clear();

        catmantenimientos = null;
        mantenimientos.clear();

        catmantenimientospm = null;
        mantenimientospm.clear();

        catmantenimientoshis = null;
        mantenimientoshis.clear();

        catmantenimientosgen = null;
        general.clear();
        catmantenimientospie = null;
        piezas.clear();
        catmantenimientosane = null;
        anexos.clear();
        catmantenimientosfal = null;
        fallas.clear();
        catmantenimientosacc = null;
        accesorios.clear();

        piezasdetalle.clear();
        piezasdetallecopia.clear();
        historico.clear();

        tabindex2 = "0";
        detailsenabled = "true";
        editable = "false";
        exiscan = "0";
        bfiltro = "";
        bfiltropm = "";

        nombrearchivo = "Select One File";

        llenarTipos();
        llenarPeriodos();
        llenarGrupoFallas();
        llenarUsuarios();
        llenarOperaciones();
        llenarPaises();
        llenarBodegas();
        llenarListaPiezas();
        llenarListaEquiposIntegral();

        llenarMantenimientosintegral();
        llenarMantenimientosintegralpm();
        llenarMantenimientosintegralHis();

    }

    public void nuevoIntegral() {
        reiniciarventana();
        tabindex = "2";
    }

    public void cerrarventanaintegral() {
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        dfechacreate = null;

        dfecha1 = null;
        dfecha2 = null;
        dfecha3 = null;

        tabindex = null;

        switchinicio = null;
        switchinicio2 = null;

        cod_lis_equ = null;
        cod_man = null;
        cod_tip = null;
        det_obs = null;
        fec_ini = null;
        fec_fin = null;
        det_sta = null;
        cod_usu = null;
        cod_per = null;
        flg_ext = null;
        ord_por = null;

        gen_det_man = null;
        gen_fec_man = null;
        gen_cod_ope = null;
        gen_det_obs = null;
        gen_cod_usu = null;
        gen_det_min = null;
        pie_det_man = null;
        pie_fec_man = null;
        pie_cod_pai = null;
        pie_cod_bod = null;
        pie_cod_ubi = null;
        pie_det_can = null;
        pie_cod_pie = null;
        pie_num_ser = null;
        pie_cod_usu = null;
        ane_det_man = null;
        ane_det_obs = null;
        ane_tip_ane = null;
        ane_rut_ane = null;
        ane_cod_usu = null;
        acc_det_man = null;
        acc_fec_man = null;
        acc_cod_pai = null;
        acc_det_can = null;
        acc_des_ite = null;
        acc_cod_usu = null;

        dfecini = null;
        dfecfinF = null;
        panindex = null;
        // cod_tip = "0";
        // det_obs = "";
        // fec_ini = format.format(dfecini);
        // fec_fin = format.format(dfecfinF);
        // det_sta = "0";
        // cod_usu = "0";
        // cod_per = "0";
        // flg_ext = "0";
        cod_alt = null;
        cod_gru_fal = null;
        cod_fal = null;
        otr_fal = null;
        vlimit = null;
        tabindex2 = null;

        editable = null;
        exiscan = null;
        bfiltro = null;
        bfiltropm = null;

        vlimit_his = null;
        buscar_pais_his = null;
        bfiltro_his = null;
        cod_lis_equ_his = null;
        cod_man_his = null;
        ane_det_man_his = null;
        ane_rut_ane_his = null;

        nombrearchivo = null;

        nombreequipoman = null;

        listadoequipo = null;
        grupofallas = null;
        operaciones = null;
        tipos = null;
        periodos = null;
        paises = null;
        bodegas = null;
        ubicaciones = null;
        listapiezas = null;
        usuarios = null;

        catmantenimientos = null;
        mantenimientos = null;

        catmantenimientospm = null;
        mantenimientospm = null;

        catmantenimientoshis = null;
        mantenimientoshis = null;

        catmantenimientosgen = null;
        general = null;
        catmantenimientospie = null;
        piezas = null;
        catmantenimientosane = null;
        anexos = null;
        catmantenimientosfal = null;
        fallas = null;
        catmantenimientosacc = null;
        accesorios = null;

        catmantenimientosanehis = null;
        anexoshis = null;

        piezasdetalle = null;
        historico = null;
        piezasdetallecopia = null;

        parametros = null;
        maccess = null;

    }

    public void limpiarventana() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        dfecha1 = Date.from(Instant.now());
        gen_fec_man = format.format(dfecha1);

        gen_cod_ope = "0";
        gen_det_obs = "";
        gen_cod_usu = cbean.getCod_usu();

        dfecha2 = Date.from(Instant.now());
        pie_fec_man = format.format(dfecha1);
        pie_cod_pai = cbean.getCod_pai();
        pie_cod_bod = "0";
        pie_cod_ubi = "0";
        pie_det_can = 0.0;
        pie_cod_pie = "";
        pie_num_ser = "";
        pie_cod_usu = cbean.getCod_usu();

        dfecha3 = Date.from(Instant.now());
        acc_fec_man = format.format(dfecha3);
        acc_cod_pai = cbean.getCod_pai();
        acc_det_can = "";
        acc_des_ite = "";
        acc_cod_usu = cbean.getCod_usu();

        ane_det_obs = "";
        ane_tip_ane = "0";
        ane_rut_ane = "";
        ane_cod_usu = cbean.getCod_usu();

        exiscan = "0";

        general.clear();
        piezas.clear();
        accesorios.clear();
        anexos.clear();
    }

    public void llenarMantenimientosintegral() {
        String mQuery = "", mWhere = "";
        //RequestContext.getCurrentInstance().execute("PF('wvEncManIni').clearFilters()");
        try {
            cod_man = "";
            catmantenimientosgen = null;
            general.clear();
            catmantenimientospie = null;
            piezas.clear();
            catmantenimientosacc = null;
            accesorios.clear();
            catmantenimientosane = null;
            anexos.clear();
            catmantenimientos = null;
            mantenimientos.clear();

            maccess.Conectar();
            if ("".equals(vlimit)) {
                vlimit = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
            }
            if (!"0".equals(buscar_pais) && !"".equals(buscar_pais)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais + " or mm.ord_por = " + buscar_pais + ") ";
            }
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
                    + "mm.flg_ext,concat('WO',right(year(mm.fec_ini),2),'-',mm.cod_alt) as codalt,equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "year(mm.fec_ini)=" + vlimit + " "
                    + "and mm.det_sta in (1,3) "
                    + "and tip.flg_urg = 'false' "
                    + mWhere
                    + "order by mm.cod_alt desc ;";

            ResultSet resVariable;

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
            System.out.println("Error en el llenado de Mantenimientos Integral en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosintegral2() {
        String mQuery = "", mWhere = "";
        //RequestContext.getCurrentInstance().execute("PF('wvEncManIni').clearFilters()");
        try {
            mantenimientos.clear();

            maccess.Conectar();
            if ("".equals(vlimit)) {
                vlimit = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
            }
            if (!"0".equals(buscar_pais)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais + " or mm.ord_por = " + buscar_pais + ") ";
            }
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
                    + "mm.flg_ext,concat('WO',right(year(mm.fec_ini),2),'-',mm.cod_alt) as codalt,equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "year(mm.fec_ini)=" + vlimit + " "
                    + "and mm.det_sta in (1,3) "
                    + "and tip.flg_urg = 'false' "
                    + mWhere
                    + "order by mm.cod_alt desc ;";

            ResultSet resVariable;

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
            System.out.println("Error en el llenado de Mantenimientos Integral 2 en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosFiltrado() {
        String mQuery = "", mWhere = "";
        try {
            catmantenimientos = null;
            mantenimientos.clear();

            maccess.Conectar();
            if ("".equals(vlimit)) {
                vlimit = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
            }
            if (!"0".equals(buscar_pais) && !"".equals(buscar_pais)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais + " or mm.ord_por = " + buscar_pais + ") ";
            }
            if (!"".equals(bfiltro) && bfiltro != null) {
                try {
                    int mInt = Integer.valueOf(bfiltro);
                    mWhere = mWhere + " and (mm.cod_alt =" + bfiltro + " or lis.num_ser like '%" + bfiltro + "%' or month(mm.fec_ini) = " + bfiltro + " ) ";
                } catch (Exception e) {
                    mWhere = mWhere + " and (upper(tip.nom_tip) like '%" + bfiltro.toUpperCase()
                            + "%' or upper(pai.nom_pai) like '%" + bfiltro.toUpperCase()
                            + "%' or upper(usu.det_nom) like '%" + bfiltro.toUpperCase()
                            + "%' or upper(pob.nom_pai) like '%" + bfiltro.toUpperCase()
                            + "%' or upper(equ.nom_equ) like '%" + bfiltro.toUpperCase()
                            + "%' or upper(lis.num_ser) like '%" + bfiltro.toUpperCase() + "%') ";
                }

            }
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
                    + "mm.flg_ext,concat('WO',right(year(mm.fec_ini),2),'-',mm.cod_alt) as codalt,equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "year(mm.fec_ini)=" + vlimit + " "
                    + "and mm.det_sta in (1,3) "
                    + "and tip.flg_urg = 'false' "
                    + mWhere
                    + "order by mm.cod_alt desc ;";

            ResultSet resVariable;

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
            System.out.println("Error en el llenado de Mantenimientos Integral 2 en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosintegralpm() {
        String mQuery = "", mWhere = "";
        try {
            catmantenimientospm = null;
            mantenimientospm.clear();

            maccess.Conectar();
            if (!"0".equals(buscar_pais2) && !"".equals(buscar_pais2)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais2 + " or mm.ord_por = " + buscar_pais2 + ") ";
            }
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
                    + "when 2 then 'magenta' "
                    + "when 3 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 4 then 'pink' "
                    + "end as color,"
                    + "mm.cod_per, "
                    + "per.nom_per, "
                    + "mm.flg_ext,concat('PM-',mm.cod_alt) as codalt,equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "mm.det_sta in (1,3) "
                    + "and tip.flg_urg = 'true' "
                    + mWhere
                    + "order by mm.cod_alt desc ;";

            ResultSet resVariable;

            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientospm.add(new CatMantenimientos(
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
            System.out.println("Error en el llenado de MantenimientosPM Integral en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosintegral2pm() {
        String mQuery = "", mWhere = "";
        try {
            mantenimientospm.clear();

            maccess.Conectar();
            if (!"0".equals(buscar_pais2)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais2 + " or mm.ord_por = " + buscar_pais2 + ") ";
            }
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
                    + "when 2 then 'magenta' "
                    + "when 3 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 4 then 'pink' "
                    + "end as color,"
                    + "mm.cod_per, "
                    + "per.nom_per, "
                    + "mm.flg_ext,concat('PM-',mm.cod_alt) as codalt,equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "mm.det_sta in (1,3) "
                    + "and tip.flg_urg = 'true' "
                    + mWhere
                    + "order by mm.cod_alt desc ;";

            ResultSet resVariable;

            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientospm.add(new CatMantenimientos(
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
            System.out.println("Error en el llenado de MantenimientosPM Integral 2 en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosFiltradopm() {
        String mQuery = "", mWhere = "";
        try {
            catmantenimientospm = null;
            mantenimientospm.clear();

            maccess.Conectar();
            if (!"0".equals(buscar_pais2) && !"".equals(buscar_pais2)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais2 + " or mm.ord_por = " + buscar_pais2 + ") ";
            }
            if (!"".equals(bfiltropm) && bfiltropm != null) {
                try {
                    int mInt = Integer.valueOf(bfiltropm);
                    mWhere = mWhere + " and (mm.cod_alt =" + bfiltropm + " or lis.num_ser like '%" + bfiltropm + "%' or month(mm.fec_ini) = " + bfiltropm + ") ";
                } catch (Exception e) {
                    mWhere = mWhere + " and (upper(tip.nom_tip) like '%" + bfiltropm.toUpperCase()
                            + "%' or upper(pai.nom_pai) like '%" + bfiltropm.toUpperCase()
                            + "%' or upper(usu.det_nom) like '%" + bfiltropm.toUpperCase()
                            + "%' or upper(pob.nom_pai) like '%" + bfiltropm.toUpperCase()
                            + "%' or upper(equ.nom_equ) like '%" + bfiltropm.toUpperCase()
                            + "%' or upper(lis.num_ser) like '%" + bfiltropm.toUpperCase() + "%') ";
                }

            }
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
                    + "when 2 then 'magenta' "
                    + "when 3 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 4 then 'pink' "
                    + "end as color,"
                    + "mm.cod_per, "
                    + "per.nom_per, "
                    + "mm.flg_ext,concat('PM','-',mm.cod_alt) as codalt,equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "mm.det_sta in (1,3) "
                    + "and tip.flg_urg = 'true' "
                    + mWhere
                    + "order by mm.cod_alt desc ;";

            ResultSet resVariable;

            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientospm.add(new CatMantenimientos(
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
            System.out.println("Error en el llenado de Mantenimientos Integral 2 en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosintegralHis() {
        String mQuery = "", mWhere = "";
        try {
            cod_lis_equ_his = "";
            cod_man_his = "";
            ane_det_man_his = "";
            ane_rut_ane_his = "";
            catmantenimientosanehis = null;
            anexoshis.clear();
            catmantenimientoshis = null;
            mantenimientoshis.clear();

            maccess.Conectar();
            if ("".equals(vlimit_his)) {
                vlimit_his = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
            }
            if (!"0".equals(buscar_pais_his) && !"".equals(buscar_pais_his)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais_his + " or mm.ord_por = " + buscar_pais_his + ") ";
            }
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
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "year(mm.fec_ini)=" + vlimit_his + " "
                    + "and mm.det_sta in (2,4) "
                    + mWhere
                    + "order by mm.fec_ini desc, mm.cod_alt desc ;";

            ResultSet resVariable;

            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientoshis.add(new CatMantenimientos(
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
            System.out.println("Error en el llenado de Mantenimientos Historia Integral en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosintegral2His() {
        String mQuery = "", mWhere = "";
        try {
            mantenimientoshis.clear();
            ane_det_man_his = "";
            ane_rut_ane_his = "";
            catmantenimientosanehis = null;
            anexoshis.clear();

            maccess.Conectar();
            if (!"0".equals(buscar_pais_his)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais_his + " or mm.ord_por = " + buscar_pais_his + ") ";
            }
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
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'white',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 2 then 'magenta' "
                    + "when 3 then "
                    + "if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,mm.fec_ini,now()))<=2,'yellow','red')) "
                    + "when 4 then 'pink' "
                    + "end as color,"
                    + "mm.cod_per, "
                    + "per.nom_per, "
                    + "mm.flg_ext,"
                    + "case tip.flg_urg "
                    + "when 'false' then concat('WO',right(year(mm.fec_ini),2),'-',mm.cod_alt) "
                    + "when 'true' then concat('PM','-',mm.cod_alt) "
                    + "end as cod_alt,"
                    + "equ.nom_equ,lis.num_ser,pai.nom_pai,mm.ord_por,pob.nom_pai, usu.det_nom, "
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "mm.det_sta in (2,4) "
                    + mWhere
                    + "order by  mm.fec_ini desc, mm.cod_alt desc ;";

            ResultSet resVariable;

            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientoshis.add(new CatMantenimientos(
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
            System.out.println("Error en el llenado de Mantenimientos Historia Integral 2 en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMantenimientosFiltradoHis() {
        String mQuery = "", mWhere = "";
        try {
            ane_det_man_his = "";
            ane_rut_ane_his = "";
            catmantenimientosanehis = null;
            anexoshis.clear();
            catmantenimientoshis = null;
            mantenimientoshis.clear();

            maccess.Conectar();
            if ("".equals(vlimit_his)) {
                vlimit_his = maccess.strQuerySQLvariable("select year(CURDATE()) as anio;");
            }
            if (!"0".equals(buscar_pais_his) && !"".equals(buscar_pais_his)) {
                mWhere = " and (lis.cod_pai = " + buscar_pais_his + " or mm.ord_por = " + buscar_pais_his + ") ";
            }
            if (!"".equals(bfiltro_his) && bfiltro_his != null) {
                try {
                    int mInt = Integer.valueOf(bfiltro_his);
                    mWhere = mWhere + " and (mm.cod_alt =" + bfiltro_his + " or lis.num_ser like '%" + bfiltro_his + "%' or month(mm.fec_ini) = " + bfiltro_his + " ) ";
                } catch (Exception e) {
                    mWhere = mWhere + " and (upper(tip.nom_tip) like '%" + bfiltro_his.toUpperCase()
                            + "%' or upper(pai.nom_pai) like '%" + bfiltro_his.toUpperCase()
                            + "%' or upper(usu.det_nom) like '%" + bfiltro_his.toUpperCase()
                            + "%' or upper(pob.nom_pai) like '%" + bfiltro_his.toUpperCase()
                            + "%' or upper(equ.nom_equ) like '%" + bfiltro_his.toUpperCase()
                            + "%' or upper(lis.num_ser) like '%" + bfiltro_his.toUpperCase() + "%') ";
                }

            }
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
                    + "mm.cod_alt, date_format(mm.fec_ini,'%Y/%m/%d') "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "left join cat_per as per on mm.cod_per = per.cod_per "
                    + "left join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lis.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on usu.cod_usu = mm.cod_usu "
                    + "left join cat_pai as pob on pob.cod_pai = mm.ord_por "
                    + "where "
                    + "year(mm.fec_ini)=" + vlimit_his + " "
                    + "and mm.det_sta in (2,4) "
                    + mWhere
                    + "order by  mm.fec_ini desc, mm.cod_alt desc ;";

            ResultSet resVariable;

            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientoshis.add(new CatMantenimientos(
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
            System.out.println("Error en el llenado de Mantenimientos Historia Integral 2 en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validarpermisoPM() {
        boolean disable = true;
        if (!"0".equals(cod_per)) {
            String grupo = "", codCtrl = "", ctrlActivo = "";

            maccess.Conectar();
            grupo = maccess.strQuerySQLvariable("select id_grp from cat_usr_grp where cod_usu = '" + cbean.getCod_usu() + "' limit 1;");
            codCtrl = maccess.strQuerySQLvariable("select cod_ctrl from cat_ctrl where sid_ctrl = 'botonPMCompleted' limit 1;");

            ctrlActivo = maccess.strQuerySQLvariable("select activo from sec_ctrl where cod_ctrl = '" + codCtrl + "' AND ( id_grp = '" + grupo + "' OR cod_usu = '" + cbean.getCod_usu() + "') limit 1;");

            maccess.Desconectar();

            if (ctrlActivo == null) {
                ctrlActivo = "";
            }

            if (ctrlActivo.equals("") || ctrlActivo.equals("false")) {
                disable = false;
            }
            if (ctrlActivo.equals("true")) {
                disable = true;
            }
        }

        //System.out.println("cod_per: " + cod_per + " cod_man: " + cod_man + " disable: " + disable);

        return disable;
    }

    public void guardarintegral() {
        String mQuery = "";
        boolean mvalidar = true;
        try {
            if (!validarpermisoPM() && cod_man.equals("")) {
                mvalidar = false;
                addMessage("Save", "This user does not have permission to create new PM ID", 2);
            }

            maccess.Conectar();
            if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
                mvalidar = false;
                addMessage("Save", "You have to select an Equipment", 2);
            }
            if ("".equals(cod_usu) || "0".equals(cod_usu)) {
                mvalidar = false;
                addMessage("Save", "You have to select an Engineer", 2);
            }
            if ("0".equals(cod_tip)) {
                mvalidar = false;
                addMessage("Save", "You have to select a type of maintenance", 2);
            }
            if (!"".equals(cod_man) && !"0".equals(cod_man)) {
                //mQuery = "select ifnull(max(cod_tip),0) as cod_tip from tbl_mae_man where cod_man = " + cod_man + " and cod_lis_equ =" + cod_lis_equ + ";";
                mQuery = "select ifnull(max(det_sta),0) as det_sta from tbl_mae_man where cod_man = " + cod_man + " and cod_lis_equ =" + cod_lis_equ + ";";
                if ("4".equals(maccess.strQuerySQLvariable(mQuery))) {
                    mvalidar = false;
                    addMessage("Save", "This WO has been Completed", 2);
                }
            }
            if ("2".equals(det_sta)) {
                mvalidar = false;
                addMessage("Save", "This WO has been Canceled", 2);
            }
            if ("0".equals(ord_por) || "".equals(ord_por)) {
                mvalidar = false;
                addMessage("Save", "You have to select an 'Ordered By' Value", 2);
            }
            if ("true".equals(maccess.strQuerySQLvariable("select flg_urg from cat_tip where cod_tip =" + cod_tip + ";")) && "".equals(cod_man) && !"0".equals(maccess.strQuerySQLvariable("select count(cod_alt) from tbl_mae_man where cod_lis_equ = " + cod_lis_equ + " and cod_tip =" + cod_tip + ";"))) {
                mvalidar = false;
                addMessage("Save", "This PMI already exists", 2);
            }
            if ("true".equals(maccess.strQuerySQLvariable("select flg_urg from cat_tip where cod_tip = " + cod_tip + ";")) && "0".equals(cod_per)) {
                mvalidar = false;
                addMessage("Save", "You have to select a Frequency", 2);
            }
            if ("false".equals(maccess.strQuerySQLvariable("select flg_urg from cat_tip where cod_tip = " + cod_tip + ";"))) {
                if (!"0".equals(cod_per)) {
                    mvalidar = false;
                    addMessage("Save", "You have selected Frequency for a non-periodic maintenance", 2);
                }
                if ("".equals(det_obs)) {
                    mvalidar = false;
                    addMessage("Save", "You have to enter a Event", 2);
                }
            }
            maccess.Desconectar();

        } catch (Exception ex) {
            mvalidar = false;
            System.out.println("Error al validar GuardarIntegral");
        }
        if (mvalidar) {
            try {
                maccess.Conectar();

                if ("".equals(cod_man)) {
                    mQuery = "select ifnull(max(cod_man),0)+1 as codigo from tbl_mae_man where cod_lis_equ = " + cod_lis_equ + ";";
                    cod_man = maccess.strQuerySQLvariable(mQuery);

                    if ("true".equals(maccess.strQuerySQLvariable("select flg_urg from cat_tip where cod_tip = " + cod_tip + ";"))) {
                        if ("0".equals(maccess.strQuerySQLvariable("select count(cod_lis_equ) from tbl_mae_man where cod_lis_equ =" + cod_lis_equ + " and cod_tip=" + cod_tip + ";"))) {
                            cod_alt = maccess.strQuerySQLvariable("select ifnull(max(cod_alt),0)+1 from tbl_mae_man where cod_tip in (select cod_tip from cat_tip where flg_urg = 'true');");
                        } else {
                            cod_alt = maccess.strQuerySQLvariable("select cod_alt from tbl_mae_man where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + " limit 1;");
                        }
                        if ("5".equals(cod_per)) {
                            mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                    + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ",1," + "str_to_date('" + fec_ini + "','%d/%b/%Y %H:%i')"
                                    + ",now()," + cbean.getCod_usu() + ",'New Preventive Maintenance'," + switchinicio + ");";
                            det_obs = switchinicio + " Month Maintenance";
                        } else if ("6".equals(cod_per)) {
                            String mswt = "";
                            switch (switchinicio2) {
                                case "1":
                                    mswt = "6";
                                    break;
                                case "2":
                                    mswt = "6";
                                    break;
                                case "3":
                                    mswt = "12";
                                    break;

                            }

                            mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                    + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ",1," + "str_to_date('" + fec_ini + "','%d/%b/%Y %H:%i')"
                                    + ",now()," + cbean.getCod_usu() + ",'New Preventive Maintenance'," + switchinicio2 + ");";
                            det_obs = mswt + " Month Maintenance";
                        } else {

                            mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                    + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ",1," + "str_to_date('" + fec_ini + "','%d/%b/%Y %H:%i')"
                                    + ",now()," + cbean.getCod_usu() + ",'New Preventive Maintenance',0);";
                            det_obs = maccess.strQuerySQLvariable("select ifnull(nom_per,'') from cat_per where cod_per =" + cod_per + ";") + " Maintenance";
                        }

                        maccess.dmlSQLvariable(mQuery);
                    } else {
                        cod_alt = maccess.strQuerySQLvariable("select ifnull(max(cod_alt),0)+1 from tbl_mae_man where year(fec_ini)= " + vlimit + " and cod_tip in (select cod_tip from cat_tip where flg_urg = 'false');");
                    }

                    mQuery = "insert into tbl_mae_man (cod_lis_equ,cod_man,cod_tip,det_obs,fec_ini,fec_fin,det_sta,cod_usu,cod_per,flg_ext,cod_alt,ord_por,usu_edi,fec_edi) "
                            + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ",'" + det_obs.replace("'", " ") + "',"
                            + "str_to_date('" + fec_ini + "','%d/%b/%Y %H:%i'),null,1,"
                            + cod_usu + "," + cod_per + "," + flg_ext + "," + cod_alt + "," + ord_por + "," + cbean.getCod_usu() + ",now());";

                    if ("false".equals(maccess.strQuerySQLvariable("select flg_urg from cat_tip where cod_tip = " + cod_tip + ";"))) {
                        cod_alt = "WO-" + cod_alt;
                    } else {
                        cod_alt = "PM-" + cod_alt;
                    }
                } else {
                    if ("true".equals(maccess.strQuerySQLvariable("select flg_urg from cat_tip where cod_tip = " + cod_tip + ";"))) {
                        if ("5".equals(cod_per)) {
                            mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                    + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                    + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                    + ",null,now()," + cbean.getCod_usu() + ",'Maintenance Modification',"
                                    + switchinicio
                                    + ");";
                        } else if ("6".equals(cod_per)) {

                            mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                    + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                    + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                    + ",null,now()," + cbean.getCod_usu() + ",'Maintenance Modification',"
                                    + switchinicio2
                                    + ");";
                        } else {
                            mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                    + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                    + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                    + ",null,now()," + cbean.getCod_usu() + ",'Maintenance Modification',0);";
                        }
                        maccess.dmlSQLvariable(mQuery);
                    }

                    mQuery = "delete from tbl_det_man_fal where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
                    maccess.dmlSQLvariable(mQuery);

                    if ("0".equals(maccess.strQuerySQLvariable("select count(det_man) as cuenta "
                            + "from tbl_det_man_pie "
                            + "where cod_lis_equ =" + cod_lis_equ + " and cod_man=" + cod_man + ";"))) {
                        mQuery = "update tbl_mae_man set "
                                + "fec_ini = str_to_date('" + fec_ini + "','%d/%b/%Y %H:%i'), "
                                + "cod_tip = " + cod_tip + ","
                                + "det_obs = '" + det_obs.replace("'", " ") + "',"
                                + "cod_usu = " + cod_usu + ","
                                + "cod_per = " + cod_per + ","
                                + "flg_ext = " + flg_ext + ","
                                + "ord_por = " + ord_por + " "
                                + "where cod_lis_equ = " + cod_lis_equ + " "
                                + "and cod_man = " + cod_man + ";";
                    } else {
                        mQuery = "update tbl_mae_man set "
                                + "cod_tip = " + cod_tip + ","
                                + "det_obs = '" + det_obs.replace("'", " ") + "',"
                                + "cod_usu = " + cod_usu + ","
                                + "cod_per = " + cod_per + ","
                                + "flg_ext = " + flg_ext + ","
                                + "ord_por = " + ord_por + " "
                                + "where cod_lis_equ = " + cod_lis_equ + " "
                                + "and cod_man = " + cod_man + ";";

                        addMessage("Save", "There are records of parts associated with this maintenance. The start date will not be updated", 2);
                    }
                }
                maccess.dmlSQLvariable(mQuery);
                String mValues = "";
                mQuery = "";

                for (int i = 0; i < fallas.size(); i++) {
                    mValues = mValues + ",(" + cod_lis_equ + "," + cod_man + ","
                            + (i + 1) + "," + fallas.get(i).getCod_gru_fal() + "," + fallas.get(i).getCod_fal() + ",'" + fallas.get(i).getDet_obs() + "')";
                }

                if (!"".equals(mValues)) {
                    mQuery = "insert into tbl_det_man_fal (cod_lis_equ,cod_man,det_man,cod_gru_fal,cod_fal,det_obs) VALUES" + mValues.substring(1) + ";";
                    maccess.dmlSQLvariable(mQuery);
                }

                maccess.Desconectar();

                eliminarTransaccionPiezas();

                int correlativo = 0;
                Double mItems = 0.0;
                String mValoresGeneral = "", mValoresPiezas = "", mValoresAccesorios = "", mValoresAnexos = "";
                try {
                    maccess.Conectar();
                    for (int i = 0; i < general.size(); i++) {
                        mValoresGeneral = mValoresGeneral + ",(" + cod_lis_equ + "," + cod_man + "," + (i + 1)
                                + ",str_to_date('" + general.get(i).getFec_man() + "', '%d/%b/%Y %H:%i'),"
                                + general.get(i).getCod_ope() + ",'" + general.get(i).getDet_obs() + "',"
                                + general.get(i).getCod_usu() + "," + general.get(i).getDet_min() + ")";
                    }

                    for (int i = 0; i < piezas.size(); i++) {
                        mValoresPiezas = mValoresPiezas + ",(" + cod_lis_equ + "," + cod_man + "," + (i + 1)
                                + ",str_to_date('" + piezas.get(i).getFec_man() + "', '%d/%b/%Y %H:%i'),"
                                + piezas.get(i).getCod_pai() + "," + piezas.get(i).getCod_bod()
                                + ",'" + piezas.get(i).getCod_ubi() + "'," + piezas.get(i).getDet_can() + ","
                                + piezas.get(i).getCod_pie() + ",'" + piezas.get(i).getNum_ser() + "',"
                                + piezas.get(i).getCod_usu() + "," + piezas.get(i).getFlg_sol() + ")";

                        if ("0".equals(piezas.get(i).getFlg_sol())) {
                            mItems = mItems + Double.valueOf(piezas.get(i).getDet_can());
                        }

                    }

                    for (int i = 0; i < accesorios.size(); i++) {
                        mValoresAccesorios = mValoresAccesorios + ",(" + cod_lis_equ + "," + cod_man + "," + (i + 1)
                                + ",str_to_date('" + accesorios.get(i).getFec_man() + "', '%d/%b/%Y %H:%i'),"
                                + accesorios.get(i).getCod_pai() + "," + accesorios.get(i).getDet_can() + ",'"
                                + accesorios.get(i).getDes_ite() + "'," + accesorios.get(i).getCod_usu() + ","
                                + accesorios.get(i).getFlg_sol() + ")";

                        if ("0".equals(accesorios.get(i).getFlg_sol())) {
                            mItems = mItems + Double.valueOf(accesorios.get(i).getDet_can());
                        }

                    }

                    /*
                    for (int i = 0; i < anexos.size(); i++) {
                        if ("0".equals(maccess.strQuerySQLvariable("select count(det_man) from tbl_det_man_ane where cod_lis_equ =" + cod_lis_equ
                                + " and cod_man = " + cod_man + " and tip_ane = " + anexos.get(i).getTip_ane()
                                + " and rut_ane = '" + anexos.get(i).getRut_ane() + "';"))) {

                            mValoresAnexos = mValoresAnexos + ",(" + cod_lis_equ + "," + cod_man + "," + (i + 1) + ",'"
                                    + anexos.get(i).getDet_obs() + "'," + anexos.get(i).getTip_ane() + ",'"
                                    + anexos.get(i).getRut_ane().replace("/resources/images/temp/", "/resources/images/anexos/") + "'," + anexos.get(i).getCod_usu() + ")";

                            
                            String nTemporal = anexos.get(i).getRut_ane().replace("/resources/images/temp/", "");

                            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                            String destinationO = mIMGFile.getPath().replace("config.xml", "");

                            File mIMGFile2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/anexos/config.xml"));
                            String destinationD = mIMGFile2.getPath().replace("config.xml", "");

                            //Mueve el nuevo archivo
                            try {
                                Path Origen = Paths.get(destinationO + nTemporal);
                                Path Destino = Paths.get(destinationD + nTemporal);
                                Files.move(Origen, Destino, StandardCopyOption.REPLACE_EXISTING);
                            } catch (Exception e) {
                                System.out.println("Error en mover Archivo: " + nTemporal + ". " + e.getMessage());
                            }
                             
                            correlativo = correlativo + 1;

                        } else {
                            maccess.dmlSQLvariable("update tbl_det_man_ane set det_man =" + (i + 1) + " where cod_lis_equ =" + cod_lis_equ
                                    + " and cod_man = " + cod_man + " and tip_ane = " + anexos.get(i).getTip_ane()
                                    + " and rut_ane = '" + anexos.get(i).getRut_ane() + "';");
                        }

                    }
                     */
                    mQuery = "delete from tbl_det_man_gen where cod_lis_equ =" + cod_lis_equ + " and cod_man= " + cod_man + "; ";
                    maccess.dmlSQLvariable(mQuery);
                    /*mQuery = "update bol_exi_pai as exi "
                            + "left join (select det.cod_pai,det.cod_pie,sum(det.det_can) as det_can "
                            + "from tbl_det_man_pie as det where det.cod_lis_equ=" + cod_lis_equ + " and det.cod_man=" + cod_man + " group by det.cod_pai,det.cod_pie) as pie "
                            + "on exi.cod_pai = pie.cod_pai and exi.cod_pie=pie.cod_pie and exi.ing_sal=1 "
                            + "set exi.det_exi= exi.det_exi - pie.det_can "
                            + "where exi.cod_pai = pie.cod_pai and exi.cod_pie=pie.cod_pie and exi.ing_sal=1";
                    
                    maccess.dmlSQLvariable(mQuery);*/
                    mQuery = "delete from tbl_det_man_pie where cod_lis_equ =" + cod_lis_equ + " and cod_man= " + cod_man + "; ";
                    maccess.dmlSQLvariable(mQuery);
                    mQuery = "delete from tbl_det_man_acc where cod_lis_equ =" + cod_lis_equ + " and cod_man= " + cod_man + "; ";
                    maccess.dmlSQLvariable(mQuery);

                    if (general.size() > 0) {
                        mValoresGeneral = "insert into tbl_det_man_gen (cod_lis_equ,cod_man,det_man,fec_man,cod_ope,det_obs,cod_usu,det_min) VALUES "
                                + mValoresGeneral.substring(1) + ";";
                        maccess.dmlSQLvariable(mValoresGeneral);
                    }
                    if (piezas.size() > 0) {
                        mValoresPiezas = "insert into tbl_det_man_pie (cod_lis_equ,cod_man,det_man,fec_man,cod_pai,"
                                + "cod_bod,cod_ubi,det_can,cod_pie,num_ser,cod_usu,flg_sol) VALUES " + mValoresPiezas.substring(1) + ";";
                        maccess.dmlSQLvariable(mValoresPiezas);
                    }
                    if (accesorios.size() > 0) {
                        mValoresAccesorios = "insert into tbl_det_man_acc (cod_lis_equ,cod_man,det_man,fec_man,cod_pai,"
                                + "det_can,des_ite,cod_usu,flg_sol) VALUES " + mValoresAccesorios.substring(1) + ";";
                        maccess.dmlSQLvariable(mValoresAccesorios);
                    }
                    /*
                    if (correlativo > 0) {
                        mValoresAnexos = "insert into tbl_det_man_ane (cod_lis_equ,cod_man,det_man,det_obs,tip_ane,"
                                + "rut_ane,cod_usu) VALUES " + mValoresAnexos.substring(1) + ";";
                        maccess.dmlSQLvariable(mValoresAnexos);
                    }*/

                    maccess.dmlSQLvariable("update tbl_mae_man set det_sta = 3 where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";");

                    maccess.Desconectar();

                    try {
                        guardarTransaccionPiezas();
                    } catch (Exception e) {
                        System.out.println("Error en TransaccionesPiezas de Guardar Detalles Mantenimiento en ManMaestroMan." + e.getMessage());
                    }

                    SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                    try {
                        maccess.Conectar();
                        dfechacreate = format.parse(maccess.strQuerySQLvariable("select date_format(fec_edi,'%d/%b/%Y %H:%i') as fec "
                                + "from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";"));
                        maccess.Desconectar();
                    } catch (Exception ex) {
                        dfechacreate = null;
                    }
                    detailsenabled = "false";
                    RequestContext.getCurrentInstance().update("frmIntegralIni");
                    addMessage("Save", "WO has been successfully saved.", 1);

                    /*if (mItems > 0) {
                        mmensaje = "Existen " + mItems + " Items sin solicitar. ¿Desea que el Sistema Genere automáticamente las respectivas solicitudes?";
                        RequestContext.getCurrentInstance().update("frmAutoSolReq");
                        RequestContext.getCurrentInstance().execute("PF('wAutoSolReq').show()");
                    }*/
                } catch (Exception e) {
                    addMessage("Save", "Error while saved.", 2);
                    System.out.println("Error en guardar Detalles Mantenimiento en ManMaestroMan." + e.getMessage() + " Query: " + mQuery);
                    System.out.println(" General: " + mValoresGeneral);
                    System.out.println(" Piezas: " + mValoresPiezas);
                    System.out.println(" Accesorios: " + mValoresAccesorios);
                    System.out.println(" Anexos: " + mValoresAnexos);

                }

                //addMessage("Guardar Mantenimiento", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saved. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Mantenimiento Integral. " + e.getMessage() + " Query: " + mQuery);
            }

            //iniciarventanaintegral();
            llenarMantenimientosintegral2();
            llenarMantenimientosintegral2pm();

            pie_cod_bod = "0";
        }

    }

    public void eliminarencabezadoInt() {
        String mQuery = "";

        maccess.Conectar();
        if ("".equals(cod_lis_equ) == false && "0".equals(cod_lis_equ) == false && "".equals(cod_man) == false) {
//            try {
//                mQuery = "select ifnull(sum(det_can),0) as suma from tbl_det_man_pie where cod_man=" + cod_man + " and cod_lis_equ = " + cod_lis_equ + ";";
//                if ("0".equals(maccess.strQuerySQLvariable(mQuery))) {
//                    eliminarTransaccionPiezas();
//                    mQuery = "delete from tbl_det_man_ane where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    mQuery = "delete from tbl_det_man_acc where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    mQuery = "delete from tbl_det_man_pie where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    mQuery = "delete from tbl_det_man_gen where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    mQuery = "delete from tbl_det_man_fal where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    mQuery = "delete from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    addMessage("Delete", "Deleted successful.", 1);
//                } else {
//                    addMessage("Delete", "WO can not be deleted becouse it has associated parts.", 2);
//                }
//            } catch (Exception e) {
//                addMessage("Delete", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
//                System.out.println("Error al Eliminar Equipo. " + e.getMessage() + " Query: " + mQuery);
//            }
            try {
                mQuery = "update tbl_mae_man set det_sta = 2 where cod_man=" + cod_man + " and cod_lis_equ = " + cod_lis_equ + ";";
                maccess.dmlSQLvariable(mQuery);
                addMessage("Cancel", "Canceled successfully.", 1);
            } catch (Exception e) {
                addMessage("Cancel", "Error While Canceling. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Encabezado ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
            }

            iniciarventanaintegral();

        } else {
            addMessage("Cancel", "You have to select a WO.", 2);
        }
        maccess.Desconectar();
    }

    public void guardarTransaccionPiezas() {
        String mQuery = "";
        maccess.Conectar();

        if (!piezas.isEmpty()) {
            //List<CatTblPiezasDetalle> piezasdetalle;
            //List<CatHistorico> historico;
            ResultSet resVariable;
            String cod_lis_pie = "", cod_rel = "", por_qbo = "", cod_pro = "0", cod_mov = "0",
                    doc_tra = "man-automatic0000", cod_sol = "0", flg_ing = "1",
                    mdet_obs = "Automatic stock edition by Maintenance Eq.Code:" + cod_lis_equ + " TT ID:" + cod_man + " WO/PMI:" + cod_alt + ".", mdet_sta = "0";

            try {

                piezasdetalle.clear();

                mQuery = "select cod_man, det_man, cod_pie, cod_bod, cod_ubi, det_can,0,0,'',"
                        + "cod_pai, "
                        + "date_format(fec_man,'%d/%b/%Y') as fecman "
                        + "FROM tbl_det_man_pie where cod_man = "
                        + cod_man + " and cod_lis_equ = " + cod_lis_equ + " order by cod_pai, cod_bod";

                resVariable = maccess.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    piezasdetalle.add(new CatTblPiezasDetalle(
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

                //**************************** Aplicar proceso a Piezas ***********************************
//                cod_lis_pie = maccess.strQuerySQLvariable("select count(cod_lis_pie) from tbl_rel_man_inv where cod_man ="
//                        + cod_man + " and cod_lis_equ=" + cod_lis_equ + ";");
//                if ("0".equals(cod_lis_pie)) {
                mQuery = "select ifnull(max(cod_lis_pie),0)+1 as codigo from tbl_pie;";
                cod_lis_pie = maccess.strQuerySQLvariable(mQuery);
                mQuery = "insert into tbl_pie (cod_lis_pie,por_qbo,cod_pai,cod_pro,cod_mov,"
                        + "doc_tra,cod_sol,fec_tra,det_obs,flg_ing,det_sta) "
                        + "values (" + cod_lis_pie + ",'" + por_qbo + "',0,"
                        + cod_pro + "," + cod_mov + ",'" + doc_tra + "'," + cod_sol + ","
                        + "STR_TO_DATE('" + fec_ini.substring(0, 11).trim() + "','%d/%b/%Y')" + ",'"
                        + mdet_obs + "'," + flg_ing + "," + mdet_sta + ");";
                maccess.dmlSQLvariable(mQuery);
                cod_rel = maccess.strQuerySQLvariable("select ifnull(max(cod_rel),0)+1 as codigo from tbl_rel_man_inv;");
                mQuery = "insert into tbl_rel_man_inv (cod_rel,cod_lis_equ,cod_man,cod_lis_pie,cod_his) "
                        + "VALUES (" + cod_rel + "," + cod_lis_equ + "," + cod_man + "," + cod_lis_pie + ",0);";
                maccess.dmlSQLvariable(mQuery);
//                } else {
//                    cod_lis_pie = maccess.strQuerySQLvariable("select cod_lis_pie from tbl_rel_man_inv where cod_man ="
//                            + cod_man + " and cod_lis_equ=" + cod_lis_equ + ";");
//                    mQuery = "update tbl_pie SET "
//                            + "fec_tra = " + "STR_TO_DATE('" + fec_ini.substring(0, 9) + "','%d/%b/%Y')" + " "
//                            + "WHERE cod_lis_pie =" + cod_lis_pie + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                    mQuery = " delete from tbl_pie_det where cod_enc=" + cod_lis_pie + ";";
//                    maccess.dmlSQLvariable(mQuery);

                // ***************** Borrado Lógico del Histórico *****************************************
                /*mQuery = " update tbl_pie_his set det_sta=1, fec_mod=now(), cod_usu=" + cbean.getCod_usu()
                            + " where flg_ing=" + flg_ing + " and cod_enc=" + cod_lis_pie + " and det_sta=0;";
                    maccess.dmlSQLvariable(mQuery);
                 */
                //***************** Borrado Físico del Histórico ******************************************
//                    mQuery = " delete from tbl_pie_his "
//                            + " where cod_enc=" + cod_lis_pie + ";";
//                    maccess.dmlSQLvariable(mQuery);
//                }
                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                maccess.dmlSQLvariable(mQuery);

                //-----------               maccess.dmlSQLvariable(mQuery);
                String mValues = "";

                if (!piezasdetalle.isEmpty()) {
                    for (int i = 0; i < piezasdetalle.size(); i++) {
//              ********************************** Existencias ****************************************

                        Double mCantidad;
                        mCantidad = Double.valueOf(piezasdetalle.get(i).getDet_can().replace(",", ""));
                        //código correlativo existencia histórica de artículo
                        String cod_cor_exi_art = maccess.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                                + "as codigo from tbl_pie_his;");
                        //Código correlativo diario existencia histórica de artículo
                        String cor_dia = maccess.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                                + "as cordia from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                + "and fec_his=STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                + ";");

                        //Costo promedio
                        Double cPromedio, exisAnt, cunitario, mNuevaExistencia;
                        if ("1".equals(cod_cor_exi_art)) {
                            cPromedio = Double.valueOf(piezasdetalle.get(i).getDet_cos().replace(",", ""));
                            exisAnt = 0.0;
                        } else {
                            cPromedio = maccess.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                    + "from tbl_pie_his "
                                    + "where "
                                    + "cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                    + "and fec_his <= STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                    + "and flg_ing = 0 "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                    + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                    + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                    + "order by fec_his desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                            //Existencia Anterior
                            exisAnt = maccess.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                                    + "from tbl_pie_his "
                                    + "where "
                                    + "cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                    + "and fec_his <= STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                    + "and det_sta = 0 "
                                    + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                    + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                    + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                    + "order by fec_his desc,"
                                    + "ord_dia desc "
                                    + "limit 1;");

                        }
                        //Inserta Registro
                        mNuevaExistencia = (exisAnt - mCantidad);

                        cunitario = Double.valueOf(piezasdetalle.get(i).getDet_cos().replace(",", ""));
                        mQuery = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                                + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu,cod_pai,cod_bod,des_ubi) "
                                + "VALUES (" + cod_cor_exi_art + "," + piezasdetalle.get(i).getCod_pie() + ","
                                + "STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y')" + "," + cor_dia + "," + flg_ing + ","
                                + cod_lis_pie + "," + (i + 1) + "," + mCantidad + "," + cunitario + ","
                                + mNuevaExistencia + ","
                                + cPromedio + "," + "0" + "," + "now()" + "," + cbean.getCod_usu() + ","
                                + piezasdetalle.get(i).getNombod() + "," + piezasdetalle.get(i).getCod_bod() + ",'" + piezasdetalle.get(i).getCod_ubi() + "');";

                        maccess.dmlSQLvariable(mQuery);

                        // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                        String contasiguientes = maccess.strQuerySQLvariable("select count(cod_his) "
                                + "from tbl_pie_his where fec_his=STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                + "and ord_dia >" + cor_dia + " "
                                + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                + ";");
                        contasiguientes = String.valueOf(
                                Integer.valueOf(contasiguientes)
                                + Integer.valueOf(maccess.strQuerySQLvariable("select count(cod_his) "
                                        + "from tbl_pie_his "
                                        + "where fec_his > STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                        + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                        + "and det_sta = 0 "
                                        + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                        + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                        + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                        + ";")));

                        Double nuevacantidad = mNuevaExistencia;
                        if ("0".equals(contasiguientes) == false) {
                            try {
                                historico.clear();

                                //Double cos_uni_sal = 0.0;
                                ResultSet resvariable;
                                resvariable = maccess.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                        + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                        + "cod_usu from tbl_pie_his "
                                        + "where fec_his = STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                        + "and ord_dia >" + cor_dia + " "
                                        + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                        + "and det_sta = 0 "
                                        + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                        + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                        + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                        + "order by fec_his asc,"
                                        + "ord_dia asc"
                                        + ";");
                                while (resvariable.next()) {
                                    historico.add(new CatHistorico(
                                            resvariable.getString(1),
                                            resvariable.getString(2),
                                            resvariable.getString(3),
                                            resvariable.getString(4),
                                            resvariable.getString(5),
                                            resvariable.getString(6),
                                            resvariable.getString(7),
                                            resvariable.getString(8),
                                            resvariable.getString(9),
                                            resvariable.getString(10),
                                            resvariable.getString(11),
                                            resvariable.getString(12),
                                            resvariable.getString(13),
                                            resvariable.getString(14)
                                    ));
                                }
                                resvariable.close();

                                resvariable = maccess.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                        + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                        + "cod_usu from tbl_pie_his "
                                        + "where fec_his > STR_TO_DATE('" + piezasdetalle.get(i).getNomubi().substring(0, 11) + "','%d/%b/%Y') "
                                        + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " "
                                        + "and det_sta = 0 "
                                        + "and cod_pai = " + piezasdetalle.get(i).getNombod() + " "
                                        + "and cod_bod = " + piezasdetalle.get(i).getCod_bod() + " "
                                        + "and des_ubi = '" + piezasdetalle.get(i).getCod_ubi() + "' "
                                        + "order by fec_his asc,"
                                        + "ord_dia asc"
                                        + ";");
                                while (resvariable.next()) {
                                    historico.add(new CatHistorico(
                                            resvariable.getString(1),
                                            resvariable.getString(2),
                                            resvariable.getString(3),
                                            resvariable.getString(4),
                                            resvariable.getString(5),
                                            resvariable.getString(6),
                                            resvariable.getString(7),
                                            resvariable.getString(8),
                                            resvariable.getString(9),
                                            resvariable.getString(10),
                                            resvariable.getString(11),
                                            resvariable.getString(12),
                                            resvariable.getString(13),
                                            resvariable.getString(14)
                                    ));
                                }
                                resvariable.close();

                                for (CatHistorico seriehistorica1 : historico) {
                                    if ("0".equals(seriehistorica1.getFlg_ing())) {
                                        cPromedio = (cPromedio * nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()) * Double.valueOf(seriehistorica1.getDet_cos())) / (nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()));
                                        nuevacantidad = nuevacantidad + Double.valueOf(seriehistorica1.getDet_can());
                                        //cos_uni_sal = 0.0;
                                        //cunitario = Double.valueOf(seriehistorica1.getDet_cos());
                                    } else {
                                        nuevacantidad = nuevacantidad - Double.valueOf(seriehistorica1.getDet_can());
                                        //cos_uni_sal = cPromedio;
                                    }
                                    mQuery = "update tbl_pie_his set "
                                            + "cos_pro= " + cPromedio + " ,"
                                            + "can_exi= " + nuevacantidad + " "
                                            + "where "
                                            + "cod_his = " + seriehistorica1.getCod_his()
                                            + ";";
                                    maccess.dmlSQLvariable(mQuery);
                                }

                            } catch (SQLException | NumberFormatException e) {
                                System.out.println("Error en actualización de costos posteriores Agregar. " + e.getMessage());
                            }

                        }

                        // Tratamiento tabla bol_exi_pai
                        String mContador = maccess.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                                + "where "
                                + "cod_pai=" + piezasdetalle.get(i).getNombod() + " "
                                + "and ing_sal=" + flg_ing + " "
                                + "and cod_pie=" + piezasdetalle.get(i).getCod_pie()
                                + ";");

                        if ("0".equals(mContador)) {

                            mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                                    + "VALUES ("
                                    + piezasdetalle.get(i).getNombod() + ","
                                    + piezasdetalle.get(i).getCod_pie() + ","
                                    + flg_ing + ","
                                    + piezasdetalle.get(i).getDet_can()
                                    + ");";

                        } else {
                            mQuery = "update bol_exi_pai set "
                                    + "det_exi= (det_exi + " + piezasdetalle.get(i).getDet_can() + ") "
                                    + "where "
                                    + "cod_pai=" + piezasdetalle.get(i).getNombod() + " "
                                    + "and ing_sal=" + flg_ing + " "
                                    + "and cod_pie=" + piezasdetalle.get(i).getCod_pie() + ";";

                        }

                        maccess.dmlSQLvariable(mQuery);

                        // Tratamiento tabla tbl_existencias
                        mContador = maccess.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                                + "where "
                                + "cod_pai=" + piezasdetalle.get(i).getNombod() + " "
                                + "and cod_bod=" + piezasdetalle.get(i).getCod_bod() + " "
                                + "and cod_ubi='" + piezasdetalle.get(i).getCod_ubi() + "' "
                                + "and cod_pie=" + piezasdetalle.get(i).getCod_pie()
                                + ";");

                        if ("0".equals(mContador)) {

                            mQuery = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                                    + "VALUES ("
                                    + maccess.strQuerySQLvariable("select (ifnull(max(cod_exi),0) + 1) as codigo from tbl_existencias;") + ","
                                    + piezasdetalle.get(i).getCod_pie() + ","
                                    + piezasdetalle.get(i).getNombod() + ","
                                    + piezasdetalle.get(i).getCod_bod() + ",'" + piezasdetalle.get(i).getCod_ubi() + "',"
                                    + piezasdetalle.get(i).getDet_can()
                                    + ",0);";

                        } else {
                            mQuery = " update tbl_existencias set det_can=(det_can-" + piezasdetalle.get(i).getDet_can() + ") "
                                    + " where cod_pai=" + piezasdetalle.get(i).getNombod() + " and cod_bod = " + piezasdetalle.get(i).getCod_bod()
                                    + " and cod_ubi='" + piezasdetalle.get(i).getCod_ubi() + "' "
                                    + " and cod_pie=" + piezasdetalle.get(i).getCod_pie() + " ;";
                        }

                        maccess.dmlSQLvariable(mQuery);

//              ********************************* Fin Existencias ************************************
                        mValues = mValues + "," + "("
                                + cod_lis_pie + ","
                                + (i + 1) + ","
                                + piezasdetalle.get(i).getCod_pie() + ","
                                + piezasdetalle.get(i).getCod_bod() + ",'"
                                + piezasdetalle.get(i).getCod_ubi() + "',"
                                + piezasdetalle.get(i).getDet_can() + ","
                                + piezasdetalle.get(i).getDet_cos() + ","
                                + piezasdetalle.get(i).getDet_sta()
                                + ")";

                    }
                    // ******************* Inserta Detalles*****************
                    mValues = mValues.substring(1);
                    mQuery = " insert into tbl_pie_det(cod_enc,cod_det,"
                            + "cod_pie,cod_bod,cod_ubi,det_can,det_cos,det_sta) "
                            + "values " + mValues + ";";

                    maccess.dmlSQLvariable(mQuery);

                }
                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                maccess.dmlSQLvariable(mQuery);
                //*************** Devolver a estatus normal el registro editado en histórico ******************
                /*mQuery = " update tbl_pie_his set det_sta = 0 "
                        + " where cod_enc=" + cod_lis_pie + ";";
                        maccess.dmlSQLvariable(mQuery);*/

                //addMessage("Guardar Almacén", "Información Almacenada con éxito.", 1);
                //******************** Actualiza tabla relaciones *********************************************
//                mQuery = "update tbl_rel_man_sol_req set cod_sol_req=" + cod_lis_pie + " ,det_sta_sol_req = " + piezasdetalle.get(i).getNombod() + " where cod_rel=" + cod_rel + ";";
//                maccess.dmlSQLvariable(mQuery);
                //**************************** Fin proceso a Piezas ***********************************
//            }
                maccess.Desconectar();
            } catch (SQLException | NumberFormatException e) {
                //addMessage("Guardar Almacén", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Procesar Descargo pieza Almacén en ManMaestroMan. " + e.getMessage());
            }
        }

    }

    public void eliminarTransaccionPiezas() {
        try {
            String mQuery = "";
            String micontador;

            maccess.Conectar();

            micontador = maccess.strQuerySQLvariable("select count(cod_rel) from tbl_rel_man_inv where cod_man = "
                    + cod_man + " and cod_lis_equ=" + cod_lis_equ + ";");

            if (!"0".equals(micontador)) {
                //List<CatTblPiezasDetalle> piezasdetallecopia;
                //List<CatHistorico> historico;
                ResultSet resVariable;
                String cod_lis_pie;
                piezasdetallecopia.clear();

                cod_lis_pie = maccess.strQuerySQLvariable("select cod_lis_pie from tbl_rel_man_inv where cod_man ="
                        + cod_man + " and cod_lis_equ=" + cod_lis_equ + ";");

                try {

                    mQuery = "select "
                            + "rel.cod_lis_pie, "
                            + "det.det_man, "
                            + "det.cod_pie, "
                            + "det.cod_bod, "
                            + "det.cod_ubi, "
                            + "det.det_can,"
                            + "0,0,'',"
                            + "det.cod_pai, "
                            + "date_format(det.fec_man,'%d/%b/%Y') as fecman "
                            + "FROM tbl_det_man_pie as det "
                            + "left join tbl_rel_man_inv as rel on det.cod_lis_equ = rel.cod_lis_equ and det.cod_man = rel.cod_man "
                            + "where det.cod_man = " + cod_man + " "
                            + "and det.cod_lis_equ=" + cod_lis_equ + " ;";

                    resVariable = maccess.querySQLvariable(mQuery);
                    while (resVariable.next()) {
                        piezasdetallecopia.add(new CatTblPiezasDetalle(
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

                } catch (Exception e) {
                    System.out.println("Error en el llenado de DetallePiezas en eliminarTransaccionPiezas de ManTblPiezas. " + e.getMessage());
                }

                //Borrado Lógico el registro del Histórico
                mQuery = " update tbl_pie_his set det_sta=1, fec_mod=now(), cod_usu=" + cbean.getCod_usu()
                        + " where cod_enc=" + cod_lis_pie + " and det_sta=0;";
                maccess.dmlSQLvariable(mQuery);

                for (int i = 0; i < piezasdetallecopia.size(); i++) {

                    try {
                        Double cPromedio = maccess.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " "
                                + "and fec_his <= STR_TO_DATE('" + piezasdetallecopia.get(i).getNomubi() + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetallecopia.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetallecopia.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        //Existencia Anterior
                        Double nuevacantidad = maccess.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " "
                                + "and fec_his <= STR_TO_DATE('" + piezasdetallecopia.get(i).getNomubi() + "','%d/%b/%Y') "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetallecopia.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetallecopia.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        String cod_his = maccess.strQuerySQLvariable("select cod_his "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " "
                                + "and fec_his <= STR_TO_DATE('" + piezasdetallecopia.get(i).getNomubi() + "','%d/%b/%Y') "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetallecopia.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetallecopia.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        String cor_dia = maccess.strQuerySQLvariable("select ord_dia "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " "
                                + "and fec_his <= STR_TO_DATE('" + piezasdetallecopia.get(i).getNomubi() + "','%d/%b/%Y') "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetallecopia.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetallecopia.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                        historico.clear();

                        //Double cos_uni_sal = 0.0;
                        ResultSet resvariable;
                        resvariable = maccess.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                + "cod_usu from tbl_pie_his "
                                + "where fec_his = STR_TO_DATE('" + piezasdetallecopia.get(i).getNomubi() + "','%d/%b/%Y') "
                                + "and ord_dia > " + cor_dia + " "
                                + "and cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetallecopia.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetallecopia.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + "and cod_his <> " + cod_his + " "
                                + "order by fec_his asc,"
                                + "ord_dia asc"
                                + ";");
                        while (resvariable.next()) {
                            historico.add(new CatHistorico(
                                    resvariable.getString(1),
                                    resvariable.getString(2),
                                    resvariable.getString(3),
                                    resvariable.getString(4),
                                    resvariable.getString(5),
                                    resvariable.getString(6),
                                    resvariable.getString(7),
                                    resvariable.getString(8),
                                    resvariable.getString(9),
                                    resvariable.getString(10),
                                    resvariable.getString(11),
                                    resvariable.getString(12),
                                    resvariable.getString(13),
                                    resvariable.getString(14)
                            ));
                        }
                        resvariable.close();

                        resvariable = maccess.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                + "cod_usu from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + piezasdetallecopia.get(i).getNomubi() + "','%d/%b/%Y') "
                                + "and cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + piezasdetallecopia.get(i).getNombod() + " "
                                + "and cod_bod = " + piezasdetallecopia.get(i).getCod_bod() + " "
                                + "and des_ubi = '" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + "and cod_his <> " + cod_his + " "
                                + "order by fec_his asc,"
                                + "ord_dia asc"
                                + ";");
                        while (resvariable.next()) {
                            historico.add(new CatHistorico(
                                    resvariable.getString(1),
                                    resvariable.getString(2),
                                    resvariable.getString(3),
                                    resvariable.getString(4),
                                    resvariable.getString(5),
                                    resvariable.getString(6),
                                    resvariable.getString(7),
                                    resvariable.getString(8),
                                    resvariable.getString(9),
                                    resvariable.getString(10),
                                    resvariable.getString(11),
                                    resvariable.getString(12),
                                    resvariable.getString(13),
                                    resvariable.getString(14)
                            ));
                        }
                        resvariable.close();

                        for (CatHistorico seriehistorica1 : historico) {
                            if ("0".equals(seriehistorica1.getFlg_ing())) {
                                cPromedio = (cPromedio * nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()) * Double.valueOf(seriehistorica1.getDet_cos())) / (nuevacantidad + Double.valueOf(seriehistorica1.getDet_can()));
                                nuevacantidad = nuevacantidad + Double.valueOf(seriehistorica1.getDet_can());

                            } else {
                                nuevacantidad = nuevacantidad - Double.valueOf(seriehistorica1.getDet_can());

                            }
                            mQuery = "update tbl_pie_his set "
                                    + "cos_pro= " + cPromedio + " ,"
                                    + "can_exi= " + nuevacantidad + " "
                                    + "where "
                                    + "cod_his = " + seriehistorica1.getCod_his()
                                    + ";";
                            maccess.dmlSQLvariable(mQuery);
                        }

                    } catch (SQLException | NumberFormatException e) {
                        System.out.println("Error en actualización de costos posteriores con Detalle Vacío. " + e.getMessage());
                    }

                    // Tratamiento tabla bol_exi_pai
                    String mContador = maccess.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                            + "where "
                            + "cod_pai=" + piezasdetallecopia.get(i).getNombod() + " "
                            + "and ing_sal= 1 "
                            + "and cod_pie=" + piezasdetallecopia.get(i).getCod_pie()
                            + ";");

                    if ("0".equals(mContador)) {

                        mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                                + "VALUES ("
                                + piezasdetallecopia.get(i).getNombod() + ","
                                + piezasdetallecopia.get(i).getCod_pie() + ",1,"
                                + piezasdetallecopia.get(i).getDet_can()
                                + ");";

                    } else {
                        mQuery = "update bol_exi_pai set "
                                + "det_exi= (det_exi + " + piezasdetallecopia.get(i).getDet_can() + ") "
                                + "where "
                                + "cod_pai=" + piezasdetallecopia.get(i).getNombod() + " "
                                + "and ing_sal= 1 "
                                + "and cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + ";";

                    }

                    maccess.dmlSQLvariable(mQuery);

                    // Tratamiento tabla tbl_existencias
                    mContador = maccess.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                            + "where "
                            + "cod_pai=" + piezasdetallecopia.get(i).getNombod() + " "
                            + "and cod_bod=" + piezasdetallecopia.get(i).getCod_bod() + " "
                            + "and cod_ubi='" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                            + "and cod_pie=" + piezasdetallecopia.get(i).getCod_pie()
                            + ";");

                    if (!"0".equals(mContador)) {

                        mQuery = " update tbl_existencias set det_can=(det_can + " + piezasdetallecopia.get(i).getDet_can() + ") "
                                + " where cod_pai=" + piezasdetallecopia.get(i).getNombod() + " and cod_bod = " + piezasdetallecopia.get(i).getCod_bod()
                                + " and cod_ubi='" + piezasdetallecopia.get(i).getCod_ubi() + "' "
                                + " and cod_pie=" + piezasdetallecopia.get(i).getCod_pie() + " ;";

                        maccess.dmlSQLvariable(mQuery);
                    }

                }

                //************** Borrado Físico el registro del Histórico
                mQuery = " delete from tbl_pie_his where cod_enc=" + cod_lis_pie + ";";
                maccess.dmlSQLvariable(mQuery);
                // ************* Borrado Físico Detalle piezas *************************************
                mQuery = "delete from tbl_pie_det where cod_enc=" + cod_lis_pie + ";";
                maccess.dmlSQLvariable(mQuery);
                // ************* Borrado Físico Encabezado *************************************
                mQuery = "delete from tbl_pie where cod_lis_pie=" + cod_lis_pie + ";";
                maccess.dmlSQLvariable(mQuery);
                // ************* Borrado Físico Tabla Relaciones *************************************
                mQuery = "delete from tbl_rel_man_inv where cod_man=" + cod_man
                        + " and cod_lis_equ=" + cod_lis_equ + ";";
                maccess.dmlSQLvariable(mQuery);
            }
            maccess.Desconectar();
        } catch (Exception e) {
            System.out.println("Error al Eliminar transacción de almacén en ManMaestroMan. " + e.getMessage());
        }

    }

    public void unlock() {
        detailsenabled = "true";
    }

    // ********************** Llenado Lista de catálogos ***********************
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
            System.out.println("Error en el llenado de Paises en ManMaestroMan. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        try {
            bodegas.clear();

            String mQuery = "select id_bod, nom_bod, cod_pai, 'nompai' as nompai "
                    + "from cat_bodegas "
                    + "where cod_pai=" + pie_cod_pai + " "
                    + "order by id_bod;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en ManMaestroMan. " + e.getMessage());
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select case ubix.ubi when null then '' else ubix.ubi end as location "
                    + "from (select distinct(des_ubi) as ubi "
                    + "from tbl_pie_his "
                    + "where cod_pie=" + pie_cod_pie
                    + " and cod_pai=" + pie_cod_pai
                    + " and cod_bod=" + pie_cod_bod
                    + " and det_sta=0 "
                    + " order by des_ubi) as ubix;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Ubicaciones en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarListaPiezas() {
        String mQuery = "";
        try {

            listapiezas.clear();

            mQuery = "select pie.cod_pie,pie.cod_ref,pie.cod_equ,"
                    + "pie.nom_pie,pie.des_pie,equ.nom_equ,pie.cod_cat,"
                    + "pie.det_ima,pie.vid_uti,pie.cod_gru,pie.cod_lin, "
                    + "gru.nom_gru,"
                    + "lin.nom_lin "
                    + "from cat_pie as pie "
                    + "left join cat_equ as equ on equ.cod_equ=pie.cod_equ "
                    + "left join cat_gru as gru on pie.cod_gru = gru.cod_gru "
                    + "left join cat_lin as lin on pie.cod_gru = lin.cod_gru and lin.cod_lin = pie.cod_lin "
                    + "where pie.flg_anu = 0 "
                    + "order by pie.cod_pie;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                listapiezas.add(new CatPiezas(
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
                        resVariable.getString(13)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Piezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarListaEquiposIntegral() {
        try {
            listadoequipo.clear();

            String mQuery = "select lequ.cod_lis_equ, "
                    + "concat(ifnull(equ.nom_equ,''), ' - SN: ',ifnull(lequ.num_ser,''), ' (' , ifnull(pai.nom_pai,''),')' ) as equipo "
                    + "FROM lis_equ as lequ "
                    + "left join cat_equ as equ on lequ.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai on lequ.cod_pai = pai.cod_pai "
                    + "order by lequ.cod_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                listadoequipo.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            maccess.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Listado Equipo en ManMaestroMan. " + e.getMessage());
        }
    }

    public void llenarGrupoFallas() {
        try {
            grupofallas.clear();

            String mQuery = "select cod_gru_fal,nom_gru_fal "
                    + "from cat_gru_fal order by cod_gru_fal;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                grupofallas.add(new CatGrupoFallas(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de GrupoFallas en ManMaestroMan. " + e.getMessage());
        }
    }

    public void llenarUsuarios() {
        try {
            usuarios.clear();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai "
                    + "left join lis_esp as lis on usu.cod_usu = lis.cod_usu "
                    + "where lis.cod_lis = 1 "
                    + "order by cod_usu;";
            ResultSet resVariable;

            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                usuarios.add(new CatUsuarios(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Usuarios ManMaestroMan. " + e.getMessage());
        }
    }

    public void llenarTipos() {
        String mQuery = "";
        try {
            tipos.clear();

            mQuery = "select cod_tip, nom_tip, flg_urg from cat_tip order by cod_tip;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tipos.add(new CatTipos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Tipos en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPeriodos() {
        String mQuery = "";
        try {
            periodos.clear();

            mQuery = "select cod_per, nom_per, det_dia from cat_per order by cod_per;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                periodos.add(new CatPeriodos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Períodos en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarCatalogoPiezas() {
        String mQuery = "";
        try {
            pie_cod_pie = "0";
            llenarListaPiezas();
            pie_cod_ubi = "0";
            onSelectUbicacion();
        } catch (Exception e) {
            System.out.println("Error en llenarCatalogoPiezas en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarOperaciones() {
        String mQuery = "";
        try {
            operaciones.clear();

            mQuery = "select cod_ope, nom_ope from cat_ope order by cod_ope;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                operaciones.add(new CatOperaciones(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Operaciones en ManMaestroMan" + e.getMessage() + " Query: " + mQuery);
        }
    }

    //***************** Llenado listas Tablas Detalle Mantenimiento*************
    //************************* Llenado Detalle Técnico ************************
    public void llenarFallas() {
        String mQuery = "";
        try {
            catmantenimientosfal = null;
            fallas.clear();

            mQuery = "select fal.cod_lis_equ,fal.cod_man,fal.det_man,fal.cod_gru_fal,fal.cod_fal,fal.det_obs, "
                    + "gru.nom_gru_fal,"
                    + "lfal.nom_fal "
                    + "from tbl_det_man_fal as fal "
                    + "left join cat_gru_fal as gru on fal.cod_gru_fal = gru.cod_gru_fal "
                    + "left join cat_fal as lfal on fal.cod_gru_fal = lfal.cod_gru_fal and fal.cod_fal = lfal.cod_fal "
                    + "where fal.cod_lis_equ=" + cod_lis_equ + " "
                    + "and fal.cod_man=" + cod_man + " "
                    + "order by fal.det_man;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                fallas.add(new CatMantenimientosFal(
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
            System.out.println("Error en el llenado Detalle Fallas en ManMaestroMan." + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarGeneral() {
        String mQuery = "";
        try {
            catmantenimientosgen = null;
            general.clear();

            mQuery = "select gen.cod_lis_equ,gen.cod_man,gen.det_man,"
                    + "date_format(gen.fec_man,'%d/%b/%Y %H:%i'),"
                    + "gen.cod_ope,gen.det_obs,"
                    + "gen.cod_usu,ope.nom_ope, "
                    + "usu.det_nom,gen.det_min "
                    + "from tbl_det_man_gen as gen "
                    + "left join cat_ope as ope on gen.cod_ope = ope.cod_ope "
                    + "left join cat_usu as usu on gen.cod_usu = usu.cod_usu "
                    + "where gen.cod_lis_equ=" + cod_lis_equ + " "
                    + "and gen.cod_man=" + cod_man + " "
                    + "order by gen.det_man;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                general.add(new CatMantenimientosGen(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalle General en ManMaestroMan" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPiezas() {
        String mQuery = "";
        try {
            catmantenimientospie = null;
            piezas.clear();

            mQuery = "select "
                    + "gen.cod_lis_equ,"
                    + "gen.cod_man,"
                    + "gen.det_man,"
                    + "date_format(gen.fec_man,'%d/%b/%Y %H:%i'), "
                    + "gen.cod_pai, "
                    + "gen.cod_bod, "
                    + "gen.cod_ubi, "
                    + "gen.det_can, "
                    + "gen.cod_pie, "
                    + "gen.num_ser, "
                    + "gen.cod_usu,"
                    + "pai.nom_pai as nompai, "
                    + "bod.nom_bod as nombod, "
                    + "gen.cod_ubi as nomubi, "
                    + "usu.det_nom as nomusu, "
                    + "pie.nom_pie as nompie, "
                    + "gen.flg_sol "
                    + "from tbl_det_man_pie as gen "
                    + "left join cat_pai as pai on gen.cod_pai = pai.cod_pai "
                    + "left join cat_bodegas as bod on gen.cod_pai = bod.cod_pai and gen.cod_bod = bod.id_bod "
                    + "left join cat_usu as usu on gen.cod_usu = usu.cod_usu "
                    + "left join cat_pie as pie on gen.cod_pie = pie.cod_pie "
                    + "where gen.cod_lis_equ= " + cod_lis_equ + " "
                    + "and gen.cod_man=" + cod_man + " "
                    + "order by gen.det_man;";
            ResultSet resVariable;
            //       Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezas.add(new CatMantenimientosPie(
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
                        resVariable.getString(17)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalle Piezas en ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAccesorios() {
        String mQuery = "";
        try {
            catmantenimientosacc = null;
            accesorios.clear();

            mQuery = "select "
                    + "acc.cod_lis_equ, acc.cod_man, acc.det_man, "
                    + "date_format(acc.fec_man,'%d/%b/%Y %H:%i'), "
                    + "acc.cod_pai, acc.det_can, acc.des_ite, acc.cod_usu, acc.flg_sol,"
                    + "pai.nom_pai, usu.det_nom "
                    + "FROM tbl_det_man_acc as acc "
                    + "left join cat_pai as pai on acc.cod_pai = pai.cod_pai "
                    + "left join cat_usu as usu on acc.cod_usu = usu.cod_usu "
                    + "where acc.cod_lis_equ= " + cod_lis_equ + " "
                    + "and acc.cod_man=" + cod_man + " "
                    + "order by acc.det_man;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                accesorios.add(new CatMantenimientosAcc(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Accesorios en ManMaestroMan" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAnexos() {
        String mQuery = "";
        try {
            catmantenimientosane = null;
            anexos.clear();

            mQuery = "select "
                    + "man.cod_lis_equ, man.cod_man, man.det_man, "
                    + "man.det_obs, man.tip_ane, man.rut_ane, man.cod_usu, "
                    + "case man.tip_ane "
                    + "when 1 then 'PDF' "
                    + "when 2 then 'IMAGE' "
                    + "when 3 then 'OTHER' "
                    + "end as nomtip, "
                    + "usu.det_nom "
                    + "from tbl_det_man_ane as man "
                    + "left join cat_usu as usu on man.cod_usu = usu.cod_usu "
                    + "where cod_lis_equ=" + cod_lis_equ + " "
                    + "and cod_man=" + cod_man + " "
                    + "order by det_man;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                anexos.add(new CatMantenimientosAne(
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
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Anexos en ManMaestroMan" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAnexosHis() {
        String mQuery = "";
        try {
            catmantenimientosanehis = null;
            anexoshis.clear();

            mQuery = "select "
                    + "man.cod_lis_equ, man.cod_man, man.det_man, "
                    + "man.det_obs, man.tip_ane, man.rut_ane, man.cod_usu, "
                    + "case man.tip_ane "
                    + "when 1 then 'PDF' "
                    + "when 2 then 'IMAGE' "
                    + "when 3 then 'OTHER' "
                    + "end as nomtip, "
                    + "usu.det_nom "
                    + "from tbl_det_man_ane as man "
                    + "left join cat_usu as usu on man.cod_usu = usu.cod_usu "
                    + "where cod_lis_equ=" + cod_lis_equ_his + " "
                    + "and cod_man=" + cod_man_his + " "
                    + "order by det_man;";

            ResultSet resVariable;

            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                anexoshis.add(new CatMantenimientosAne(
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
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado AnexosHis en ManMaestroMan" + e.getMessage() + " Query: " + mQuery);
        }
    }

    //***************** Mantenimiento Tablas Detalle Mantenimiento**************
    //*********************Fallas***********************************************
    public boolean validarfalla() {
        boolean mvalidar = true;

        if ("0".equals(cod_gru_fal)) {
            mvalidar = false;
            addMessage("Failures", "You have to select a Failure.", 2);
        }
        /*if ("0".equals(cod_fal)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Falla Específica.", 2);
        }*/

        return mvalidar;

    }

    public void agregarfalla() {
        if (validarfalla()) {
            int correlativo = 0, existe = 0;
            try {
                for (int i = 0; i < fallas.size(); i++) {
                    if (Integer.valueOf(fallas.get(i).getDet_man()) > correlativo) {
                        correlativo = Integer.valueOf(fallas.get(i).getDet_man());
                    }
                    if (cod_gru_fal.equals(fallas.get(i).getCod_gru_fal()) && cod_fal.equals(fallas.get(i).getCod_fal())) {
                        existe = 1;
                    }

                }

                if (existe == 0) {
                    //Accesos macc = new Accesos();
                    maccess.Conectar();
                    String nomgrup = maccess.strQuerySQLvariable("select ifnull(nom_gru_fal,'') from cat_gru_fal where cod_gru_fal =" + cod_gru_fal + ";");
                    String nomfal = maccess.strQuerySQLvariable("select ifnull(nom_fal,'') from cat_fal where cod_gru_fal =" + cod_gru_fal + " and cod_fal=" + cod_fal + ";");
                    maccess.Desconectar();

                    fallas.add(new CatMantenimientosFal(
                            cod_lis_equ,
                            cod_man,
                            String.valueOf(correlativo + 1),
                            cod_gru_fal,
                            cod_fal,
                            "",
                            nomgrup,
                            nomfal
                    ));
                }

                cod_gru_fal = "0";
                cod_fal = "0";
                catmantenimientosfal = new CatMantenimientosFal();
                cod_gru_fal = "0";
                cod_fal = "0";
                otr_fal = "";

            } catch (Exception e) {
                System.out.println("Error en Agregar Falla ManMaestroMan." + e.getMessage());
            }
        }

    }

    public void eliminarfalla() {
        if ("0".equals(cod_fal)) {
            addMessage("Failures", "You have to select a Failure.", 2);
        } else {
            for (int i = 0; i < fallas.size(); i++) {
                if (cod_gru_fal.equals(fallas.get(i).getCod_gru_fal()) && cod_fal.equals(fallas.get(i).getCod_fal())) {
                    fallas.remove(i);
                }
            }

            cod_gru_fal = "0";
            cod_fal = "0";
            catmantenimientosfal = new CatMantenimientosFal();
        }
    }

    //*************************** General **************************************
    public boolean validargeneral() {
        boolean mvalidar = true;
        String mQuery = "";
        if ("".equals(gen_det_min)) {
            gen_det_min = "0.0";
        }
        if ("0".equals(gen_cod_ope) || "".equals(gen_cod_ope)) {
            if ("".equals(gen_det_obs.trim())) {
                mvalidar = false;
                addMessage("General", "You have to select an Action or write a comment.", 2);
            }
        }

        if ("0".equals(gen_cod_usu)) {
            mvalidar = false;
            addMessage("General", "You have to select an Engineer.", 2);
        }

        if ("".equals(cod_lis_equ)) {
            mvalidar = false;
            addMessage("General", "You have to select an Equipment.", 2);
        } else if ("".equals(cod_man)) {
            mvalidar = false;
            addMessage("General", "You have to select a WO.", 2);
        }
        /*try {
            //Accesos macc = new Accesos();
            maccess.Conectar();
            mQuery = "select DATEDIFF(str_to_date('" + pie_fec_man + "','%d/%m/%Y'), str_to_date('" + fec_ini + "','%d/%m/%Y')) as datedif;";
            if (maccess.doubleQuerySQLvariable(mQuery) < 0.0) {
                mvalidar = false;
                addMessage("Validar Datos", "La Fecha del Detalle Técnico no puede ser anterior al Inicio del Mantenimiento.", 2);
            }
            maccess.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en validación General ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }*/

        return mvalidar;

    }

    public void agregargeneral() {
        if (validargeneral()) {
            int correlativo = 0;
            try {
                for (int i = 0; i < general.size(); i++) {
                    if (Integer.valueOf(general.get(i).getDet_man()) > correlativo) {
                        correlativo = Integer.valueOf(general.get(i).getDet_man());
                    }
                }

                //Accesos macc = new Accesos();
                maccess.Conectar();
                String nomope = maccess.strQuerySQLvariable("select ifnull(nom_ope,'') from cat_ope where cod_ope =" + gen_cod_ope + ";");
                String nomusu = maccess.strQuerySQLvariable("select ifnull(det_nom,'') from cat_usu where cod_usu =" + gen_cod_usu + ";");
                maccess.Desconectar();

                general.add(new CatMantenimientosGen(
                        cod_lis_equ,
                        cod_man,
                        String.valueOf(correlativo + 1),
                        gen_fec_man,
                        gen_cod_ope,
                        gen_det_obs.replace("'", " "),
                        gen_cod_usu,
                        nomope,
                        nomusu,
                        gen_det_min
                ));

                guardarintegral();

                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                dfecha1 = Date.from(Instant.now());
                gen_fec_man = format.format(dfecha1);
                gen_cod_ope = "0";
                gen_det_obs = "";
                gen_cod_usu = cbean.getCod_usu();
                gen_det_min = "";

            } catch (Exception e) {
                System.out.println("Error en Agregar General ManMaestroMan." + e.getMessage());
            }
        }

    }

    public void eliminargeneral() {

        if ("".equals(gen_det_man)) {
            addMessage("General", "You have to select a technical detail from the List.", 2);
        } else {
            for (int i = 0; i < general.size(); i++) {
                if (cod_lis_equ.equals(general.get(i).getCod_lis_equ())
                        && cod_man.equals(general.get(i).getCod_man())
                        && gen_det_man.equals(general.get(i).getDet_man())) {
                    general.remove(i);
                }
            }

            guardarintegral();

            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            dfecha1 = Date.from(Instant.now());
            gen_fec_man = format.format(dfecha1);
            gen_cod_ope = "0";
            gen_det_obs = "";
            gen_cod_usu = cbean.getCod_usu();
        }
    }

    //************************* Piezas *****************************************
    public boolean validarpiezas() {
        boolean mvalidar = true;
        String mQuery = "";

        if (pie_cod_ubi == null) {
            pie_cod_ubi = "";
        }

        if (pie_det_can == 0.0) {
            mvalidar = false;
            addMessage("Parts", "Required Quantity must be greater than zero", 2);
        }
        if ("0".equals(pie_cod_pie)) {
            mvalidar = false;
            addMessage("Parts", "You have to select a Part", 2);
        }
        if ("0".equals(pie_cod_bod)) {
            mvalidar = false;
            addMessage("Parts", "You have to select a Warehouse", 2);
        }
        if ("0".equals(pie_cod_ubi)) {
            mvalidar = false;
            addMessage("Parts", "You have to select a Location", 2);
        }
        if ("0".equals(pie_cod_usu)) {
            mvalidar = false;
            addMessage("Parts", "You have to select an Engineer", 2);
        }
        if ("".equals(cod_lis_equ)) {
            mvalidar = false;
            addMessage("Parts", "You have to select an Equipment", 2);
        } else if ("".equals(cod_man)) {
            mvalidar = false;
            addMessage("Parts", "You have to select a WO", 2);
        }
        /*try {
            //Accesos macc = new Accesos();
            maccess.Conectar();
            mQuery = "select DATEDIFF(str_to_date('" + pie_fec_man + "','%d/%m/%Y'), str_to_date('" + fec_ini + "','%d/%m/%Y')) as datedif;";
            if (maccess.doubleQuerySQLvariable(mQuery) < 0.0) {
                mvalidar = false;
                addMessage("Validar Datos", "La Fecha del Detalle de Piezas Utilizadas no puede ser anterior al Inicio del Mantenimiento.", 2);
            }
            maccess.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en validación pieza ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }*/
        if ("0".equals(pie_cod_pai)) {
            mvalidar = false;
            addMessage("Parts", "You have to select a Country.", 2);
        } else {
            try {
                Double existencia = 0.0, solicita = 0.0;
                //Accesos acc = new Accesos();
                mQuery = "select ifnull( (select exi.can_exi "
                        + " from tbl_pie_his as exi "
                        + " where "
                        + " exi.fec_his <= STR_TO_DATE('" + pie_fec_man + "','%d/%b/%Y') "
                        + " and exi.det_sta = 0 "
                        + " and exi.cod_pai = " + pie_cod_pai + " "
                        + " and exi.cod_bod = " + pie_cod_bod + " "
                        + " and exi.des_ubi = '" + pie_cod_ubi + "' "
                        + " and exi.cod_pie = " + pie_cod_pie + " "
                        + " order by exi.fec_his desc, exi.ord_dia desc limit 1),0) as existencia;";

                if (!piezas.isEmpty()) {
                    for (int i = 0; i < piezas.size(); i++) {
                        if (pie_cod_pie.equals(piezas.get(i).getCod_pie())
                                && "0".equals(piezas.get(i).getFlg_sol())
                                && pie_cod_bod.equals(piezas.get(i).getCod_bod())
                                && pie_cod_pai.equals(piezas.get(i).getCod_pai())) {
                            solicita = solicita + Double.valueOf(piezas.get(i).getDet_can());
                        }
                    }
                }

                maccess.Conectar();
                //System.out.println("cantidad grid: " + existencia + " Existencia:" + acc.doubleQuerySQLvariable(mQuery) + " Cantidad sol: " + pie_det_can);
                existencia = maccess.doubleQuerySQLvariable(mQuery); //- pie_det_can;
                maccess.Desconectar();
                //System.out.println(" Existencia: " + existencia);
                if ((solicita + pie_det_can) > existencia) {
                    mvalidar = false;
                    addMessage("Parts", "Required Quantity is greater than stock.", 2);

                }

            } catch (Exception e) {
                mvalidar = false;
                System.out.println("Error en Validación Existencia agregardetalle de ManInventarioPiezas. " + e.getMessage() + " Query: " + mQuery);
            }
        }
        return mvalidar;

    }

    public void agregarpiezas() {
        if (validarpiezas()) {
            int correlativo = 0;
            try {
                for (int i = 0; i < piezas.size(); i++) {
                    if (Integer.valueOf(piezas.get(i).getDet_man()) > correlativo) {
                        correlativo = Integer.valueOf(piezas.get(i).getDet_man());
                    }
                }

                //Accesos macc = new Accesos();
                maccess.Conectar();
                String nompaipie = maccess.strQuerySQLvariable("select ifnull(nom_pai,'') from cat_pai where cod_pai =" + pie_cod_pai + ";");
                String nombodpie = maccess.strQuerySQLvariable("select ifnull(nom_bod,'') from cat_bodegas where cod_pai=" + pie_cod_pai + " and id_bod =" + pie_cod_bod + ";");
                //String nomubipie = macc.strQuerySQLvariable("select ifnull(nom_ubi,'') from cat_ubicaciones where cod_bod=" + pie_cod_bod + " and id_ubi =" + pie_cod_ubi + ";");
                String nomusupie = maccess.strQuerySQLvariable("select ifnull(det_nom,'') from cat_usu where cod_usu =" + pie_cod_usu + ";");
                String nompiepie = maccess.strQuerySQLvariable("select ifnull(nom_pie,'') from cat_pie where cod_pie =" + pie_cod_pie + ";");

                maccess.Desconectar();

                piezas.add(new CatMantenimientosPie(
                        cod_lis_equ,
                        cod_man,
                        String.valueOf(correlativo + 1),
                        pie_fec_man,
                        pie_cod_pai,
                        pie_cod_bod,
                        ((pie_cod_ubi.replace("'", " ")).replace("/", " ")).replace("\\", " "),
                        String.valueOf(pie_det_can),
                        pie_cod_pie,
                        ((pie_num_ser.replace("'", " ")).replace("/", " ")).replace("\\", " "),
                        pie_cod_usu,
                        nompaipie,
                        nombodpie,
                        pie_cod_ubi,
                        nomusupie,
                        nompiepie,
                        "0"
                ));

                guardarintegral();

                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                dfecha2 = Date.from(Instant.now());
                pie_fec_man = format.format(dfecha1);
                pie_cod_bod = "0";
                pie_cod_ubi = "0";
                pie_det_can = 0.0;
                pie_cod_pie = "0";
                pie_num_ser = "";
                exiscan = "0";

            } catch (Exception e) {
                System.out.println("Error en Agregar Pieza ManMaestroMan." + e.getMessage());
            }
        }

    }

    public void eliminarpiezas() {
        if ("".equals(pie_det_man)) {
            addMessage("Parts", "You have to select a record from the list.", 2);
        } else {
            for (int i = 0; i < piezas.size(); i++) {
                if (cod_lis_equ.equals(piezas.get(i).getCod_lis_equ())
                        && cod_man.equals(piezas.get(i).getCod_man())
                        && pie_det_man.equals(piezas.get(i).getDet_man())) {
                    piezas.remove(i);
                    /*if ("1".equals(piezas.get(i).getFlg_sol())) {
                        //Accesos acc = new Accesos();
                        maccess.Conectar();
                        String mQuery = "update bol_exi_pai set det_exi = det_exi - " +piezas.get(i).getDet_can() + " "
                                + "where cod_pai =" + pie_cod_pai + " "
                                + "and cod_pie =" + pie_cod_pie + " "
                                + "and ing_sal = 1;";
                        maccess.dmlSQLvariable(mQuery);
                        maccess.Desconectar();
                    }*/
                }
            }

            guardarintegral();

            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            dfecha2 = Date.from(Instant.now());
            pie_fec_man = format.format(dfecha2);
            pie_cod_bod = "0";
            pie_cod_ubi = "0";
            pie_det_can = 0.0;
            pie_cod_pie = "0";
            pie_num_ser = "";
            exiscan = "0";

        }
    }

    //************************* Accesorios *************************************
    public boolean validaraccesorios() {
        boolean mvalidar = true;
        String mQuery = "";
        if ("".equals(acc_det_can)) {
            acc_det_can = "0.0";
        }
        if ("".equals(acc_des_ite)) {
            mvalidar = false;
            addMessage("Miscellaneus", "You have to write an Item Description.", 2);
        }
        if (Double.valueOf(acc_det_can) == 0.0) {
            mvalidar = false;
            addMessage("Miscellaneus", "Required Quantity must be greater than zero.", 2);
        }
        if ("0".equals(acc_cod_usu)) {
            mvalidar = false;
            addMessage("Miscellaneus", "You have to select an Engineer.", 2);
        }
        if ("0".equals(acc_cod_pai)) {
            mvalidar = false;
            addMessage("Miscellaneus", "You have to select a Country.", 2);
        }
        if ("".equals(cod_lis_equ)) {
            mvalidar = false;
            addMessage("Miscellaneus", "You have to select an Equipment.", 2);
        } else if ("".equals(cod_man)) {
            mvalidar = false;
            addMessage("Miscellaneus", "You have to select a WO.", 2);
        }
        /*try {
            //Accesos macc = new Accesos();
            maccess.Conectar();
            mQuery = "select DATEDIFF(str_to_date('" + acc_fec_man + "','%d/%m/%Y'), str_to_date('" + fec_ini + "','%d/%m/%Y')) as datedif;";
            if (maccess.doubleQuerySQLvariable(mQuery) < 0.0) {
                mvalidar = false;
                addMessage("Validar Datos", "La Fecha del Detalle de Accesorios Utilizados no puede ser anterior al Inicio del Mantenimiento.", 2);
            }
            maccess.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en validación Accesorios ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }*/
        return mvalidar;

    }

    public void agregaraccesorios() {
        if (validaraccesorios()) {
            int correlativo = 0;
            try {
                for (int i = 0; i < accesorios.size(); i++) {
                    if (Integer.valueOf(accesorios.get(i).getDet_man()) > correlativo) {
                        correlativo = Integer.valueOf(accesorios.get(i).getDet_man());
                    }
                }

                //Accesos macc = new Accesos();
                maccess.Conectar();
                String nompaipie = maccess.strQuerySQLvariable("select ifnull(nom_pai,'') from cat_pai where cod_pai =" + acc_cod_pai + ";");

                maccess.Desconectar();

                accesorios.add(new CatMantenimientosAcc(
                        cod_lis_equ,
                        cod_man,
                        String.valueOf(correlativo + 1),
                        acc_fec_man,
                        acc_cod_pai,
                        acc_det_can.replace(",", " "),
                        ((acc_des_ite.replace("'", " ")).replace("/", " ")).replace("\\", " "),
                        acc_cod_usu,
                        "0",
                        nompaipie
                ));

                guardarintegral();

                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                dfecha3 = Date.from(Instant.now());
                acc_fec_man = format.format(dfecha3);
                //acc_cod_pai = "0";
                acc_det_can = "";
                acc_des_ite = "";
                acc_cod_usu = cbean.getCod_usu();

            } catch (Exception e) {
                System.out.println("Error en Agregar Accesorio ManMaestroMan." + e.getMessage());
            }
        }

    }

    public void eliminarAccesorio() {
        if ("".equals(acc_det_man)) {
            addMessage("Miscellaneus", "You have to select a Record from the list.", 2);
        } else {
            for (int i = 0; i < accesorios.size(); i++) {
                if (cod_lis_equ.equals(accesorios.get(i).getCod_lis_equ())
                        && cod_man.equals(accesorios.get(i).getCod_man())
                        && acc_det_man.equals(accesorios.get(i).getDet_man())) {
                    accesorios.remove(i);
                }
            }

            guardarintegral();

            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            dfecha3 = Date.from(Instant.now());
            acc_fec_man = format.format(dfecha3);
            //acc_cod_pai = "0";
            acc_det_can = "";
            acc_des_ite = "";
            acc_cod_usu = cbean.getCod_usu();
        }
    }

    //**************************** Anexos **************************************
    public boolean validaranexos() {
        boolean mvalidar = true;

        if ("".equals(ane_det_obs)) {
            mvalidar = false;
            addMessage("Files", "You have to write a Comment.", 2);
        }
        if ("0".equals(ane_tip_ane)) {
            mvalidar = false;
            addMessage("Files", "You have to select a type of file.", 2);
        }
        if ("0".equals(ane_cod_usu)) {
            mvalidar = false;
            addMessage("Files", "You have to select an Engineer.", 2);
        }
        if ("".equals(ane_rut_ane)) {
            mvalidar = false;
            addMessage("Files", "You have to select a File.", 2);
        }
        if ("".equals(cod_lis_equ)) {
            mvalidar = false;
            addMessage("Files", "You have to select an Equipment.", 2);
        } else if ("".equals(cod_man)) {
            mvalidar = false;
            addMessage("Files", "You have to select a WO.", 2);
        }

        return mvalidar;

    }

    public void agregaranexo() {
        if (validaranexos()) {
            int correlativo = 0;
            String mQuery = "";
            try {
                for (int i = 0; i < anexos.size(); i++) {
                    if (Integer.valueOf(anexos.get(i).getDet_man()) > correlativo) {
                        correlativo = Integer.valueOf(anexos.get(i).getDet_man());
                    }
                }

                //Accesos macc = new Accesos();
                maccess.Conectar();
                String nomusupie = maccess.strQuerySQLvariable("select ifnull(det_nom,'') from cat_usu where cod_usu =" + ane_cod_usu + ";");
                String tipoanexo = "";
                switch (ane_tip_ane) {
                    case "1":
                        tipoanexo = "PDF";
                        break;
                    case "2":
                        tipoanexo = "IMAGEN";
                        break;
                    case "3":
                        tipoanexo = "OTRO";
                        break;

                }

                maccess.Desconectar();

                anexos.add(new CatMantenimientosAne(
                        cod_lis_equ,
                        cod_man,
                        String.valueOf(correlativo + 1),
                        ((ane_det_obs.replace("'", " ")).replace("/", " ")).replace("\\", " "),
                        ane_tip_ane,
                        ane_rut_ane,
                        ane_cod_usu,
                        tipoanexo,
                        nomusupie
                ));

                //guardarintegral();
                maccess.Conectar();
                if ("0".equals(maccess.strQuerySQLvariable("select count(det_man) from tbl_det_man_ane where cod_lis_equ =" + cod_lis_equ
                        + " and cod_man = " + cod_man + " and tip_ane = " + ane_tip_ane
                        + " and rut_ane = '" + ane_rut_ane + "';"))) {

                    String micorr = maccess.strQuerySQLvariable("select ifnull(max(det_man),0) + 1 from tbl_det_man_ane where cod_lis_equ =" + cod_lis_equ
                            + " and cod_man = " + cod_man);
                    maccess.dmlSQLvariable("insert into tbl_det_man_ane (cod_lis_equ,cod_man,det_man,det_obs,tip_ane,"
                            + "rut_ane,cod_usu) VALUES" + "(" + cod_lis_equ + "," + cod_man + "," + micorr + ",'"
                            + ((ane_det_obs.replace("'", " ")).replace("/", " ")).replace("\\", " ") + "'," + ane_tip_ane + ",'"
                            + ane_rut_ane + "'," + ane_cod_usu + ")");

                    // ****************************  Inserta Imagen en tabla ************************************************************
                    try {
                        FileInputStream fis = null;
                        PreparedStatement ps = null;
                        try {
                            maccess.Conectar().setAutoCommit(false);
                            File mfile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(ane_rut_ane));
                            fis = new FileInputStream(mfile);

                            mQuery = "insert into tbl_det_man_ane_img(cod_lis_equ,cod_man,det_man,det_nom,det_ima) values(?,?,?,?,?)";
                            ps = maccess.Conectar().prepareStatement(mQuery);
                            ps.setString(1, cod_lis_equ);
                            ps.setString(2, cod_man);
                            ps.setString(3, micorr);
                            ps.setString(4, ane_rut_ane);
                            ps.setBinaryStream(5, fis, (int) mfile.length());

                            ps.executeUpdate();
                            maccess.Conectar().commit();

                        } catch (Exception ex) {
                            //System.out.println("Error 1 subir anexos en ManMaestroMan " + ex.getMessage());

                        } finally {
                            try {
                                ps.close();
                                fis.close();
                            } catch (Exception ex) {
                                //System.out.println("Error 2 subir anexos en ManMaestroMan " + ex.getMessage());
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
                ane_rut_ane = "";
                ane_cod_usu = cbean.getCod_usu();
                nombrearchivo = "Select One File";

                addMessage("Save", "WO has been successfully saved.", 1);

            } catch (Exception e) {
                addMessage("Save", "Error while saved. " + e.getMessage(), 2);
                System.out.println("Error en Agregar Anexo ManMaestroMan." + e.getMessage());
            }
        }

    }

    public void eliminarAnexos() {
        String mQuery = "";
        if ("".equals(ane_det_man)) {
            addMessage("Files", "You have to select a Record from the list.", 2);
        } else {
            /*
            for (int i = 0; i < anexos.size(); i++) {
                if (cod_lis_equ.equals(anexos.get(i).getCod_lis_equ())
                        && cod_man.equals(anexos.get(i).getCod_man())
                        && ane_det_man.equals(anexos.get(i).getDet_man())) {

                    try {
                        File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/anexos/"
                                + (anexos.get(i).getRut_ane().replace("/resources/images/anexos/", "")).replace("/resources/images/temp/", "")));
                        if (mIMGFile.exists()) {
                            mIMGFile.delete();
                        }
                    } catch (Exception e) {
                        System.out.println("Error al Eliminar Archivos Anexos 1: "
                                + FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/anexos/"
                                        + (ane_rut_ane.replace("/resources/images/anexos/", "")).replace("/resources/images/temp/", "")));
                    }
                    try {
                        File mIMGFile2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/"
                                + (ane_rut_ane.replace("/resources/images/anexos/", "")).replace("/resources/images/temp/", "")));
                        if (mIMGFile2.exists()) {
                            mIMGFile2.delete();
                        }
                    } catch (Exception e) {
                        System.out.println("Error al Eliminar Archivos Anexos 2: "
                                + FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/"
                                        + (anexos.get(i).getRut_ane().replace("/resources/images/anexos/", "")).replace("/resources/images/temp/", "")));
                    }
                    try {

                        //Accesos macc = new Accesos();
                        maccess.Conectar();
                        maccess.dmlSQLvariable("delete from tbl_det_man_ane where cod_lis_equ = " + cod_lis_equ + " and cod_man = " + cod_man
                                + " and tip_ane = " + ane_tip_ane + " and rut_ane = '" + ane_rut_ane.replace("\\", "\\\\") + "';");
                        maccess.Desconectar();
                        anexos.remove(i);

                    } catch (Exception e) {
                        System.out.println("Error al Eliminar Archivos Anexos 3: "
                                + "delete from tbl_det_man_ane where cod_lis_equ = "
                                + cod_lis_equ + " and cod_man = " + cod_man
                                + " and tip_ane = " + ane_tip_ane
                                + " and rut_ane = '" + ane_rut_ane + "';" + ". "
                                + e.getMessage());
                    }

                }
            }

            guardarintegral();
             */

            try {
                maccess.Conectar();
                mQuery = "delete from tbl_det_man_ane_img where cod_lis_equ = " + cod_lis_equ + " and cod_man= " + cod_man + " and det_man=" + ane_det_man + ";";
                maccess.dmlSQLvariable(mQuery);
                mQuery = "delete from tbl_det_man_ane where cod_lis_equ = " + cod_lis_equ + " and cod_man= " + cod_man + " and det_man=" + ane_det_man + ";";
                maccess.dmlSQLvariable(mQuery);
                maccess.Desconectar();
            } catch (Exception ex) {
                addMessage("Save", "Error while deleted. " + ex.getMessage(), 2);
                System.out.println("Error en Eliminar Anexo ManMaestroMan." + ex.getMessage() + " mQuery: " + mQuery);
            }

            llenarAnexos();

            ane_det_obs = "";
            ane_tip_ane = "0";
            ane_rut_ane = "";
            ane_cod_usu = cbean.getCod_usu();
            nombrearchivo = "Select One File";
        }
    }

    public void descargaranexos() {
        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ) || "".equals(cod_man) || "0".equals(cod_man) || "".equals(ane_det_man)) {
            addMessage("Download", "You have to select a record", 2);
        } else {
            byte[] fileBytes;
            String mQuery;
            maccess.Conectar();
            try {
                mQuery = "select det_ima from tbl_det_man_ane_img where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + " and det_man=" + ane_det_man + ";";

                ResultSet rs = maccess.querySQLvariable(mQuery);
                if (rs.next()) {
                    File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                    String destinationO = mIMGFile.getPath().replace("config.xml", "");

                    //FacesContext ctx;
                    //ctx = FacesContext.getCurrentInstance();
                    //HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                    //response.setHeader("Content-Disposition", "attachment;filename=\"" + ane_rut_ane.replace("/resources/images/temp/", "") + "\"");
                    fileBytes = rs.getBytes(1);
                    OutputStream targetFile = new FileOutputStream(destinationO + ane_rut_ane.replace("/resources/images/temp/", ""));
                    //ServletOutputStream targetFile = response.getOutputStream();
                    targetFile.write(fileBytes);
                    targetFile.flush();
                    targetFile.close();
                    mIMGFile = null;

                    try {
                        //FacesContext contex = FacesContext.getCurrentInstance();
                        //contex.getExternalContext().redirect("/totaltracking/faces/" + ane_rut_ane);
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("window.open('" + "/totaltracking/faces" + ane_rut_ane + "', '_blank')");
                    } catch (Exception e) {
                        System.out.println("Error en redireccionar a descarga en ManMaestroMan. " + e.getMessage());
                    }

                }
                rs.close();

            } catch (Exception e) {
                System.out.println("Error en descargar archivo ManMaestroMan. " + e.getMessage());
            }
            maccess.Desconectar();
        }

    }

    public void descargaranexosHis() {
        if ("".equals(cod_lis_equ_his) || "0".equals(cod_lis_equ_his) || "".equals(cod_man_his) || "0".equals(cod_man_his) || "".equals(ane_det_man_his)) {
            addMessage("Download", "You have to select a record", 2);
        } else {
            byte[] fileBytes;
            String mQuery;
            maccess.Conectar();
            try {
                mQuery = "select det_ima from tbl_det_man_ane_img where cod_lis_equ=" + cod_lis_equ_his + " and cod_man=" + cod_man_his + " and det_man=" + ane_det_man_his + ";";

                ResultSet rs = maccess.querySQLvariable(mQuery);
                if (rs.next()) {
                    File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                    String destinationO = mIMGFile.getPath().replace("config.xml", "");

                    fileBytes = rs.getBytes(1);
                    OutputStream targetFile = new FileOutputStream(destinationO + ane_rut_ane_his.replace("/resources/images/temp/", ""));

                    targetFile.write(fileBytes);
                    targetFile.flush();
                    targetFile.close();
                    mIMGFile = null;

                    try {

                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("window.open('" + "/totaltracking/faces" + ane_rut_ane_his + "', '_blank')");
                    } catch (Exception e) {
                        System.out.println("Error en redireccionar a descarga en ManMaestroMan. " + e.getMessage());
                    }

                }
                rs.close();

            } catch (Exception e) {
                System.out.println("Error en descargar archivo anexo His ManMaestroMan. " + e.getMessage());
            }
            maccess.Desconectar();
        }

    }

    //********************** Finalizar Mantenimiento ***************************
    public void iniciarFinalizarWO() {
        boolean mvalidar = true;

        if (!"0".equals(cod_per) || cod_per == null) {
            mvalidar = false;
            addMessage("Complete", "This maintenance has periodic frequency", 2);
            RequestContext.getCurrentInstance().update("frmIntegralIni");
        }

        if (mvalidar) {
            if ("0".equals(cod_man) || "".equals(cod_man) || cod_man == null) {
                mvalidar = false;
                addMessage("Complete", "You have to select a maintenance", 2);
                RequestContext.getCurrentInstance().update("frmIntegralIni");
            }
        }

        //System.out.println("Cod_per: " + cod_per + " cod_man : " + cod_man + " mValidar: " + mvalidar);
        if (mvalidar) {
            dfecfinF = null;
            fec_fin = "";
            RequestContext.getCurrentInstance().update("frmMaestraFin");
            RequestContext.getCurrentInstance().execute("PF('wMaestraFinWO').show()");
        }

    }

    public boolean validarfinalizarWO() {
        boolean mvalidar = true;

        if (fec_fin == null) {
            fec_fin = "";
        }

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Complete", "You have to select an Equipment.", 2);
            return false;
        }
        if ("".equals(cod_man) || "0".equals(cod_man)) {
            addMessage("Complete", "You have to select a WO.", 2);
            return false;
        }

        if (general.isEmpty() && piezas.isEmpty() && accesorios.isEmpty() && anexos.isEmpty()) {
            addMessage("Complete", "This Maintenance does not have details.", 2);
            return false;
        }

        if ("".equals(fec_fin) || dfecfinF == null) {
            addMessage("Complete", "You have to select an End Date.", 2);
            return false;
        }

        if (!"0".equals(cod_per) || cod_per == null) {
            mvalidar = false;
            addMessage("Complete", "This maintenance has periodic frequency", 2);
        }

        return mvalidar;

    }

    public void finalizarmantenimientoWO() {
        String mQuery = "";
        if (validarfinalizarWO()) {
            try {
                maccess.Conectar();

                mQuery = "update tbl_mae_man set "
                        + "fec_fin = str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i'), "
                        + "det_sta = 4 "
                        + "where "
                        + "cod_lis_equ= " + cod_lis_equ + " "
                        + "and cod_man = " + cod_man + ";";
                maccess.dmlSQLvariable(mQuery);

                maccess.Desconectar();
                reiniciarventana();
                addMessage("Complete", "Maintenance Completed Successfully", 1);
            } catch (Exception e) {
                addMessage("Complete", "The completion of maintenance failed", 2);
                System.out.println("Error en Finalizar Mantenimieinto. " + e.getMessage() + " Query: " + mQuery);
            }
        }
    }

    public void iniciarFinalizarPM() {
        boolean mvalidar = true;

        if ("0".equals(cod_per)) {
            mvalidar = false;
            addMessage("Complete", "This maintenance doesn´t has periodic frequency", 2);
            RequestContext.getCurrentInstance().update("frmIntegralIni");
        }

        if (mvalidar) {
            if ("0".equals(cod_man) || "".equals(cod_man) || cod_man == null) {
                mvalidar = false;
                addMessage("Complete", "You have to select a maintenance", 2);
                RequestContext.getCurrentInstance().update("frmIntegralIni");
            }
        }

        //System.out.println("Cod_per: " + cod_per + " cod_man : " + cod_man + " mValidar: " + mvalidar);
        if (mvalidar) {
            dfecfinF = null;
            fec_fin = "";
            RequestContext.getCurrentInstance().update("frmMaestraFinPM");
            RequestContext.getCurrentInstance().execute("PF('wMaestraFinPM').show()");
        }

    }

    public boolean validarfinalizarPM() {
        boolean mvalidar = true;

        if (fec_fin == null) {
            fec_fin = "";
        }

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Complete", "You have to select an Equipment", 2);
            return false;
        }
        if ("".equals(cod_man) || "0".equals(cod_man)) {
            addMessage("Complete", "You have to select a PM", 2);
            return false;
        }

        if (general.isEmpty() && piezas.isEmpty() && accesorios.isEmpty() && anexos.isEmpty()) {
            addMessage("Complete", "This Maintenance does not have details", 2);
            return false;
        }

        if ("".equals(fec_fin) || dfecfinF == null) {
            addMessage("Complete", "You have to select an End Date", 2);
            return false;
        }

        if ("0".equals(cod_per)) {
            mvalidar = false;
            addMessage("Complete", "This maintenance doesn´t has periodic frequency", 2);
        }

        return mvalidar;

    }

    public void finalizarmantenimientoPM() {
        String mQuery = "";
        if (validarfinalizarPM()) {
            try {
                maccess.Conectar();

                mQuery = "update tbl_mae_man set "
                        + "fec_fin = str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i'), "
                        + "det_sta = 4 "
                        + "where "
                        + "cod_lis_equ= " + cod_lis_equ + " "
                        + "and cod_man = " + cod_man + ";";
                maccess.dmlSQLvariable(mQuery);
                if (!"0".equals(cod_per) && !"5".equals(cod_per) && !"6".equals(cod_per)) {
                    mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                            + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                            + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                            + "," + "str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i')"
                            + ",now()," + cbean.getCod_usu() + ",'Maintenance Completed',0);";
                    maccess.dmlSQLvariable(mQuery);

                    String newcodman = maccess.strQuerySQLvariable("select ifnull(max(cod_man),0)+1 as codigo from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + ";");
                    String diasper = maccess.strQuerySQLvariable("Select det_dia from cat_per where cod_per=" + cod_per + ";");
                    String newfechaini = maccess.strQuerySQLvariable("select date_format(DATE_ADD(str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i'), INTERVAL " + diasper + " DAY),'%d/%b/%Y %H:%i');");
                    cod_alt = maccess.strQuerySQLvariable("select cod_alt from tbl_mae_man where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + " limit 1;");
                    det_obs = maccess.strQuerySQLvariable("select ifnull(nom_per,'') from cat_per where cod_per =" + cod_per + ";") + " Maintenance";
                    mQuery = "insert into tbl_mae_man (cod_lis_equ,cod_man,cod_tip,det_obs,fec_ini,fec_fin,det_sta,cod_usu,cod_per,flg_ext,cod_alt,ord_por,usu_edi,fec_edi) "
                            + "VALUES (" + cod_lis_equ + "," + newcodman + "," + cod_tip + ",'" + det_obs + "',"
                            + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i'),null,1,"
                            + cod_usu + "," + cod_per + "," + flg_ext + "," + cod_alt + "," + cbean.getCod_pai() + "," + cbean.getCod_usu() + ",now());";
                    maccess.dmlSQLvariable(mQuery);

                    mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                            + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                            + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                            + "," + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i')"
                            + ",now()," + cbean.getCod_usu() + ",'Automatic PM Update',0);";
                    maccess.dmlSQLvariable(mQuery);

                }
                if ("5".equals(cod_per)) {
                    mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                            + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                            + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                            + "," + "str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i')"
                            + ",now()," + cbean.getCod_usu() + ",'Maintenance Completed'," + maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ =" + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;") + ");";
                    maccess.dmlSQLvariable(mQuery);

                    String newcodman = maccess.strQuerySQLvariable("select ifnull(max(cod_man),0)+1 as codigo from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + ";");
                    String diasper = maccess.strQuerySQLvariable("Select det_dia from cat_per where cod_per=" + cod_per + ";");
                    String newfechaini = maccess.strQuerySQLvariable("select date_format(DATE_ADD(str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i'), INTERVAL " + diasper + " DAY),'%d/%b/%Y %H:%i');");
                    cod_alt = maccess.strQuerySQLvariable("select cod_alt from tbl_mae_man where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + " limit 1;");
                    if ("6".equals(maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ =" + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;"))) {
                        det_obs = "12 Month Maintenance";
                        mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                + "," + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i')"
                                + ",now()," + cbean.getCod_usu() + ",'Automatic PM Update',12);";
                    } else {
                        det_obs = "6 Month Maintenance";
                        mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                + "," + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i')"
                                + ",now()," + cbean.getCod_usu() + ",'Automatic PM Update',6);";
                    }
                    maccess.dmlSQLvariable(mQuery);

                    mQuery = "insert into tbl_mae_man (cod_lis_equ,cod_man,cod_tip,det_obs,fec_ini,fec_fin,det_sta,cod_usu,cod_per,flg_ext,cod_alt,ord_por,usu_edi,fec_edi) "
                            + "VALUES (" + cod_lis_equ + "," + newcodman + "," + cod_tip + ",'" + det_obs + "',"
                            + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i'),null,1,"
                            + cod_usu + "," + cod_per + "," + flg_ext + "," + cod_alt + "," + cbean.getCod_pai() + "," + cbean.getCod_usu() + ",now());";
                    maccess.dmlSQLvariable(mQuery);

                }

                if ("6".equals(cod_per)) {
                    mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                            + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                            + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                            + "," + "str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i')"
                            + ",now()," + cbean.getCod_usu() + ",'Maintenance Completed'," + maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ =" + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;") + ");";
                    maccess.dmlSQLvariable(mQuery);

                    String newcodman = maccess.strQuerySQLvariable("select ifnull(max(cod_man),0)+1 as codigo from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + ";");
                    String diasper = maccess.strQuerySQLvariable("Select det_dia from cat_per where cod_per=" + cod_per + ";");
                    String newfechaini = maccess.strQuerySQLvariable("select date_format(DATE_ADD(str_to_date('" + fec_fin + "','%d/%b/%Y %H:%i'), INTERVAL " + diasper + " DAY),'%d/%b/%Y %H:%i');");
                    cod_alt = maccess.strQuerySQLvariable("select cod_alt from tbl_mae_man where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + " limit 1;");
                    if ("1".equals(maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ =" + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;"))) {
                        det_obs = "6 Month Maintenance";
                        mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                + "," + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i')"
                                + ",now()," + cbean.getCod_usu() + ",'Automatic PM Update',2);";
                    }
                    if ("2".equals(maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ =" + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;"))) {
                        det_obs = "12 Month Maintenance";
                        mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                + "," + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i')"
                                + ",now()," + cbean.getCod_usu() + ",'Automatic PM Update',3);";
                    }
                    if ("3".equals(maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ =" + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;"))) {
                        det_obs = "6 Month Maintenance";
                        mQuery = "insert into tbl_det_man_pre (cod_lis_equ,cod_man,cod_tip,det_man,fec_rep,fec_mod,usu_mod,obs_mod,flg_swi) "
                                + "VALUES (" + cod_lis_equ + "," + cod_man + "," + cod_tip + ","
                                + maccess.strQuerySQLvariable("select ifnull(max(det_man),0)+1 as cod from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip=" + cod_tip + ";")
                                + "," + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i')"
                                + ",now()," + cbean.getCod_usu() + ",'Automatic PM Update',1);";
                    }
                    maccess.dmlSQLvariable(mQuery);

                    mQuery = "insert into tbl_mae_man (cod_lis_equ,cod_man,cod_tip,det_obs,fec_ini,fec_fin,det_sta,cod_usu,cod_per,flg_ext,cod_alt,ord_por,usu_edi,fec_edi) "
                            + "VALUES (" + cod_lis_equ + "," + newcodman + "," + cod_tip + ",'" + det_obs + "',"
                            + "str_to_date('" + newfechaini + "','%d/%b/%Y %H:%i'),null,1,"
                            + cod_usu + "," + cod_per + "," + flg_ext + "," + cod_alt + "," + cbean.getCod_pai() + "," + cbean.getCod_usu() + ",now());";
                    maccess.dmlSQLvariable(mQuery);

                }
                maccess.Desconectar();
                nuevoIntegral();
                addMessage("Complete", "Maintenance Completed Successfully.", 1);
            } catch (Exception e) {
                addMessage("Complete", "The completion of maintenance failed.", 2);
                System.out.println("Error en Finalizar Mantenimieinto. " + e.getMessage() + " Query: " + mQuery);
            }
        }
    }

    //************************** Funciones varias ******************************
    public void onSelectPiePais() {
        llenarBodegas();
        ubicaciones.clear();
        pie_cod_ubi = "0";
        pie_cod_pie = "0";
        pie_det_can = 0.0;
        exiscan = "0";
    }

    public void onSelectFrecuencia() {
        String mQuery = "";
        try {
            if ("5".equals(cod_per)) {
                RequestContext.getCurrentInstance().update("frmSwitch");
                RequestContext.getCurrentInstance().execute("PF('wSwitch').show()");
                det_obs = switchinicio + " Month Maintenance";
            } else if ("0".equals(cod_per)) {
                det_obs = "";
            } else if ("6".equals(cod_per)) {
                String mswt = "";
                switch (switchinicio2) {
                    case "1":
                        mswt = "6";
                        break;
                    case "2":
                        mswt = "6";
                        break;
                    case "3":
                        mswt = "12";
                        break;

                }
                RequestContext.getCurrentInstance().update("frmSwitch2");
                RequestContext.getCurrentInstance().execute("PF('wSwitch2').show()");
                det_obs = mswt + " Month Maintenance";
            } else {
                maccess.Conectar();
                det_obs = maccess.strQuerySQLvariable("select ifnull(nom_per,'') from cat_per where cod_per =" + cod_per + ";") + " Maintenance";
                maccess.Desconectar();
            }
        } catch (Exception e) {
            det_obs = "";
        }
    }

    public void onPressOkSwitch() {
        det_obs = switchinicio + " Month Maintenance";
    }

    public void onPressOkSwitch2() {
        String mswt = "";
        switch (switchinicio2) {
            case "1":
                mswt = "6";
                break;
            case "2":
                mswt = "6";
                break;
            case "3":
                mswt = "12";
                break;

        }
        det_obs = mswt + " Month Maintenance";
    }

    public void onSelectPieBodega() {
        ubicaciones.clear();
        pie_cod_ubi = "0";
        pie_cod_pie = "0";
        pie_det_can = 0.0;
        exiscan = "0";
    }

    public void onSelectPiePieza() {
        llenarUbicaciones();
        pie_cod_ubi = "0";
        pie_det_can = 0.0;
        exiscan = "0";
    }

    public void onSelectUbicacion() {

        String mQuery = "";

        try {
            boolean mValidar = true;

            if (pie_cod_ubi == null) {
                pie_cod_ubi = "";
            }

            if ("0".equals(pie_cod_pai) || "".equals(pie_cod_pai)) {
                mValidar = false;
                addMessage("Validar Datos", "You have to select a Country.", 2);
            }

            if ("0".equals(pie_cod_bod) || "".equals(pie_cod_bod)) {
                mValidar = false;
                addMessage("Validar Datos", "You have to select a Warehouse.", 2);
            }

            if (mValidar) {
                mQuery = "select exi.can_exi "
                        + "from tbl_pie_his as exi  "
                        + "where exi.det_sta = 0  "
                        + "and exi.cod_pie =  " + pie_cod_pie + " "
                        + "and exi.cod_pai =  " + pie_cod_pai + " "
                        + "and exi.cod_bod =  " + pie_cod_bod + " "
                        + "and exi.des_ubi = '" + pie_cod_ubi + "' "
                        + "and exi.fec_his <= STR_TO_DATE('" + pie_fec_man + "','%d/%b/%Y')  "
                        + ";";
                //System.out.println(mQuery);
                maccess.Conectar();
                exiscan = maccess.strQuerySQLvariable(mQuery);
                if (exiscan == null) {
                    exiscan = "";
                }
                if (exiscan.equals("")) {
                    exiscan = "0";
                }
                maccess.Desconectar();
            }
        } catch (Exception e) {
            System.out.println("Error en onSelectUbicacion de ManMaestroMan. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onSelectEquipo() {
        catmantenimientos = null;
        mantenimientos.clear();
        cod_man = "";
        general.clear();
        piezas.clear();
        accesorios.clear();
        anexos.clear();

    }

    public void onRowSelectEncInt(SelectEvent event) {
        catmantenimientospm = null;

        cod_lis_equ = ((CatMantenimientos) event.getObject()).getCod_lis_equ();
        cod_man = ((CatMantenimientos) event.getObject()).getCod_man();
        cod_tip = ((CatMantenimientos) event.getObject()).getCod_tip();
        det_obs = ((CatMantenimientos) event.getObject()).getDet_obs();
        fec_ini = ((CatMantenimientos) event.getObject()).getFec_ini();
        //fec_fin = ((CatMantenimientos) event.getObject()).getFec_fin();
        det_sta = ((CatMantenimientos) event.getObject()).getDet_sta();
        cod_usu = ((CatMantenimientos) event.getObject()).getCod_usu();
        cod_per = ((CatMantenimientos) event.getObject()).getCod_per();
        flg_ext = ((CatMantenimientos) event.getObject()).getFlg_ext();
        cod_alt = ((CatMantenimientos) event.getObject()).getCod_alt();
        ord_por = ((CatMantenimientos) event.getObject()).getOrd_por();

        nombreequipoman = ((CatMantenimientos) event.getObject()).getCod_alt() + " : "
                + ((CatMantenimientos) event.getObject()).getTequipo() + " "
                + ((CatMantenimientos) event.getObject()).getNserie() + " ("
                + ((CatMantenimientos) event.getObject()).getNpais() + ")";

        if ("00/00/0000".equals(fec_ini)) {
            fec_ini = "";
        }
        /*if ("00/00/0000".equals(fec_fin)) {
            fec_fin = "";
        }*/

        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        try {
            maccess.Conectar();
            dfechacreate = format.parse(maccess.strQuerySQLvariable("select date_format(fec_edi,'%d/%b/%Y %H:%i') as fec from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";"));
            maccess.Desconectar();
        } catch (Exception ex) {
            dfechacreate = null;
        }
        dfecha1 = Date.from(Instant.now());
        gen_fec_man = format.format(dfecha1);

        gen_cod_ope = "0";
        gen_det_obs = "";
        gen_cod_usu = cbean.getCod_usu();

        dfecha2 = Date.from(Instant.now());
        pie_fec_man = format.format(dfecha1);
        pie_cod_pai = cbean.getCod_pai();
        pie_cod_bod = "0";
        pie_cod_ubi = "0";
        pie_det_can = 0.0;
        pie_cod_pie = "";
        pie_num_ser = "";
        pie_cod_usu = cbean.getCod_usu();

        dfecha3 = Date.from(Instant.now());
        acc_fec_man = format.format(dfecha3);
        acc_cod_pai = cbean.getCod_pai();
        acc_det_can = "";
        acc_des_ite = "";
        acc_cod_usu = cbean.getCod_usu();

        ane_det_obs = "";
        ane_tip_ane = "0";
        ane_rut_ane = "";
        ane_cod_usu = cbean.getCod_usu();

        try {
            dfecini = format.parse(fec_ini);
            //dfecfinF = format.parse(fec_fin);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");
            fec_ini = format2.format(dfecini);
        } catch (Exception ex) {
            System.out.println("Error en convertir fechas encabezado." + ex.getMessage() + " fec_ini: " + fec_ini + " fec_fin: " + fec_fin);
        }

        llenarFallas();
        llenarGeneral();
        llenarPiezas();
        llenarAccesorios();
        llenarAnexos();

        detailsenabled = "false";

    }

    public void onRowSelectEncIntPM(SelectEvent event) {
        catmantenimientos = null;

        cod_lis_equ = ((CatMantenimientos) event.getObject()).getCod_lis_equ();
        cod_man = ((CatMantenimientos) event.getObject()).getCod_man();
        cod_tip = ((CatMantenimientos) event.getObject()).getCod_tip();
        det_obs = ((CatMantenimientos) event.getObject()).getDet_obs();
        fec_ini = ((CatMantenimientos) event.getObject()).getFec_ini();
        //fec_fin = ((CatMantenimientos) event.getObject()).getFec_fin();
        det_sta = ((CatMantenimientos) event.getObject()).getDet_sta();
        cod_usu = ((CatMantenimientos) event.getObject()).getCod_usu();
        cod_per = ((CatMantenimientos) event.getObject()).getCod_per();
        flg_ext = ((CatMantenimientos) event.getObject()).getFlg_ext();
        cod_alt = ((CatMantenimientos) event.getObject()).getCod_alt();
        ord_por = ((CatMantenimientos) event.getObject()).getOrd_por();

        nombreequipoman = ((CatMantenimientos) event.getObject()).getCod_alt() + " : "
                + ((CatMantenimientos) event.getObject()).getTequipo() + " "
                + ((CatMantenimientos) event.getObject()).getNserie() + " ("
                + ((CatMantenimientos) event.getObject()).getNpais() + ")";

        if ("5".equals(cod_per)) {
            maccess.Conectar();
            switchinicio = maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;");
            maccess.Desconectar();
        }

        if ("6".equals(cod_per)) {
            maccess.Conectar();
            switchinicio2 = maccess.strQuerySQLvariable("select flg_swi from tbl_det_man_pre where cod_lis_equ = " + cod_lis_equ + " and cod_tip = " + cod_tip + " order by det_man desc limit 1;");
            maccess.Desconectar();
        }

        if ("00/00/0000".equals(fec_ini)) {
            fec_ini = "";
        }
        /*if ("00/00/0000".equals(fec_fin)) {
            fec_fin = "";
        }*/

        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        try {
            maccess.Conectar();
            dfechacreate = format.parse(maccess.strQuerySQLvariable("select date_format(fec_edi,'%d/%b/%Y %H:%i') as fec from tbl_mae_man where cod_lis_equ=" + cod_lis_equ + " and cod_man=" + cod_man + ";"));
            maccess.Desconectar();
        } catch (Exception ex) {
            dfechacreate = null;
        }
        dfecha1 = Date.from(Instant.now());
        gen_fec_man = format.format(dfecha1);

        gen_cod_ope = "0";
        gen_det_obs = "";
        gen_cod_usu = cbean.getCod_usu();

        dfecha2 = Date.from(Instant.now());
        pie_fec_man = format.format(dfecha1);
        pie_cod_pai = cbean.getCod_pai();
        pie_cod_bod = "0";
        pie_cod_ubi = "0";
        pie_det_can = 0.0;
        pie_cod_pie = "";
        pie_num_ser = "";
        pie_cod_usu = cbean.getCod_usu();

        dfecha3 = Date.from(Instant.now());
        acc_fec_man = format.format(dfecha3);
        acc_cod_pai = cbean.getCod_pai();
        acc_det_can = "";
        acc_des_ite = "";
        acc_cod_usu = cbean.getCod_usu();

        ane_det_obs = "";
        ane_tip_ane = "0";
        ane_rut_ane = "";
        ane_cod_usu = cbean.getCod_usu();

        try {
            dfecini = format.parse(fec_ini);
            //dfecfinF = format.parse(fec_fin);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            fec_ini = format2.format(dfecini);
        } catch (Exception ex) {
            System.out.println("Error en convertir fechas encabezado." + ex.getMessage() + " fec_ini: " + fec_ini + " fec_fin: " + fec_fin);
        }

        llenarFallas();
        llenarGeneral();
        llenarPiezas();
        llenarAccesorios();
        llenarAnexos();

        detailsenabled = "false";

    }

    public void onRowSelectEncIntHis(SelectEvent event) {
        cod_lis_equ_his = ((CatMantenimientos) event.getObject()).getCod_lis_equ();
        cod_man_his = ((CatMantenimientos) event.getObject()).getCod_man();
        ane_det_man_his = "";
        ane_rut_ane_his = "";
        llenarAnexosHis();
    }

    public void onRowSelectGen(SelectEvent event) {
        gen_det_man = ((CatMantenimientosGen) event.getObject()).getDet_man();
        gen_fec_man = ((CatMantenimientosGen) event.getObject()).getFec_man();
        gen_cod_ope = ((CatMantenimientosGen) event.getObject()).getCod_ope();
        gen_det_obs = ((CatMantenimientosGen) event.getObject()).getDet_obs();
        gen_cod_usu = ((CatMantenimientosGen) event.getObject()).getCod_usu();
        gen_det_min = ((CatMantenimientosGen) event.getObject()).getDet_min();
        if ("00/00/0000".equals(gen_fec_man)) {
            gen_fec_man = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        try {
            dfecha1 = format.parse(gen_fec_man);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            gen_fec_man = format2.format(dfecha1);
        } catch (Exception ex) {

        }

        RequestContext.getCurrentInstance().update("frmObservaciones");
        RequestContext.getCurrentInstance().execute("PF('wObservaciones').show()");

    }

    public void onRowSelectPie(SelectEvent event) {

        pie_det_man = ((CatMantenimientosPie) event.getObject()).getDet_man();
        pie_fec_man = ((CatMantenimientosPie) event.getObject()).getFec_man();
        pie_cod_pai = ((CatMantenimientosPie) event.getObject()).getCod_pai();
        pie_cod_bod = ((CatMantenimientosPie) event.getObject()).getCod_bod();
        pie_cod_ubi = ((CatMantenimientosPie) event.getObject()).getCod_ubi();
        pie_det_can = Double.valueOf(((CatMantenimientosPie) event.getObject()).getDet_can());
        pie_cod_pie = ((CatMantenimientosPie) event.getObject()).getCod_pie();
        pie_num_ser = ((CatMantenimientosPie) event.getObject()).getNum_ser();
        pie_cod_usu = ((CatMantenimientosPie) event.getObject()).getCod_usu();
        if ("00/00/0000".equals(pie_fec_man)) {
            pie_fec_man = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        try {
            dfecha2 = format.parse(pie_fec_man);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            pie_fec_man = format2.format(dfecha2);
        } catch (Exception ex) {

        }
    }

    public void onRowSelectAcc(SelectEvent event) {

        acc_det_man = ((CatMantenimientosAcc) event.getObject()).getDet_man();
        acc_fec_man = ((CatMantenimientosAcc) event.getObject()).getFec_man();
        acc_cod_pai = ((CatMantenimientosAcc) event.getObject()).getCod_pai();
        acc_det_can = ((CatMantenimientosAcc) event.getObject()).getDet_can();
        acc_des_ite = ((CatMantenimientosAcc) event.getObject()).getDes_ite();
        acc_cod_usu = ((CatMantenimientosAcc) event.getObject()).getCod_usu();

        if ("00/00/0000".equals(acc_fec_man)) {
            acc_fec_man = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        try {
            dfecha3 = format.parse(acc_fec_man);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
            acc_fec_man = format2.format(dfecha3);
        } catch (Exception ex) {

        }
    }

    public void onRowSelectAne(SelectEvent event) {
        nombrearchivo = "Select One File";

        ane_det_man = ((CatMantenimientosAne) event.getObject()).getDet_man();
        //ane_det_obs = ((CatMantenimientosAne) event.getObject()).getDet_obs();
        //ane_tip_ane = ((CatMantenimientosAne) event.getObject()).getTip_ane();
        ane_rut_ane = ((CatMantenimientosAne) event.getObject()).getRut_ane();
        // ane_cod_usu = ((CatMantenimientosAne) event.getObject()).getCod_usu();

    }

    public void onRowSelectAneHis(SelectEvent event) {
        ane_det_man_his = ((CatMantenimientosAne) event.getObject()).getDet_man();
        ane_rut_ane_his = ((CatMantenimientosAne) event.getObject()).getRut_ane();
    }

    public void onChangeGrupoFalla() {
        //llenarListaFallas();
    }

    public void onTabChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabPIE":
                tabindex = "0";
                break;
            case "tabACC":
                tabindex = "1";
                break;
            case "tabANE":
                tabindex = "2";
                break;

        }
        //System.out.println(tabindex);
        //RequestContext.getCurrentInstance().update(":frmListaEquipos:tvLE");
    }

    public void onTabPanChange(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "tabOBSTECini":
                panindex = "0";
                break;
            case "tabGENini":
                panindex = "1";
                break;
            /*case "tabFALL":
                panindex = "1";
                break;*/

        }
        //System.out.println(panindex);
        //RequestContext.getCurrentInstance().update(":frmListaEquipos:tvLE");
    }

    public void onTabChangeIni(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            case "listagenini":
                tabindex = "0";
                break;
            case "listaPMini":
                tabindex = "1";
                break;
            case "genini":
                tabindex = "2";
                break;
            case "detintini":
                tabindex = "3";
                break;
            case "detinthis":
                tabindex = "4";
                break;

        }

    }

    public void onTab2Change(TabChangeEvent event) {
        switch (event.getTab().getId()) {
            /*case "tabGENini":
                tabindex2 = "0";
                break;*/
            case "tabPIEini":
                tabindex2 = "0";
                break;
            case "tabACCini":
                tabindex2 = "1";
                break;
            case "tabANEini":
                tabindex2 = "2";
                break;
            case "tab001rec":
                tabindex2 = "3";
                break;
            case "tab001ent":
                tabindex2 = "4";
                break;

        }
        //System.out.println(tabindex);
        //RequestContext.getCurrentInstance().update(":frmListaEquipos:tvLE");
    }

    public void dateSelectedFencabezado(SelectEvent f) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        Date date = (Date) f.getObject();
        fec_ini = format.format(date);
        pie_cod_bod = "0";
    }

    public void dateSelectedFencabezadoFin(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        fec_fin = format.format(date);
    }

    public void dateSelectedGeneral(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        gen_fec_man = format.format(date);
    }

    public void dateSelectedPiezas(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        pie_fec_man = format.format(date);
    }

    public void dateSelectedAccesorios(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        acc_fec_man = format.format(date);
    }

    //******************** Funciones Copiar Archivos ***************************
    public void upload(FileUploadEvent event) {
        try {
            ane_det_man = "";
            catmantenimientosane = null;
            //String destination = "";
            //File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/anexos/config.xml"));
            //destination = mIMGFile.getPath().replace("config.xml", "");
            nombrearchivo = event.getFile().getFileName();

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                //File mfile = new File(destination + "ane_equ_" + cod_lis_equ + "_man_" + cod_man + "_" + event.getFile().getFileName().toLowerCase());
                //if (mfile.exists()) {
                //addMessage("Procesar Archivo", "El Archivo " + event.getFile().getFileName() + " ya Existe en este Mantenimiento. ", 2);
                //} else {
                //Random rnd = new Random();
                //String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));
                copyFile("ane_equ_" + cod_lis_equ + "_man_" + cod_man + "_" + event.getFile().getFileName().toLowerCase(), event.getFile().getInputstream());
                //}
            } catch (Exception e) {
                nombrearchivo = "Select One File";
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            addMessage("Upload", "File " + event.getFile().getFileName() + " can't be uploaded. " + e.getMessage(), 2);
            System.out.println("Error en subir archivo Mantenimiento." + e.getMessage());
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            ane_rut_ane = "/resources/images/temp/" + fileName;

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
            nombrearchivo = "Select One File";
            addMessage("Copiar Archivo Mantenimiento", "El Archivo en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    private static void limpiarDirectorioTemporales() {
        File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
        String direccion = mIMGFile.getPath().replace("config.xml", "");

        File directorio = new File(direccion);
        File f;
        if (directorio.isDirectory()) {
            String[] files = directorio.list();
            if (files.length > 0) {
                //System.out.println(" Directorio vacio: " + direccion);
                for (String archivo : files) {
                    //System.out.println(archivo);
                    f = new File(direccion + File.separator + archivo);

                    //System.out.println("Ultima modificación: " + new Date(f.lastModified()));
                    long Time;
                    Time = (System.currentTimeMillis() - f.lastModified());
                    //long cantidadDia = (Time / 86400000);
                    //System.out.println("Age of the file is: " + cantidadDia + " days");
                    // Attempt to delete it
                    //86400000 ms is equivalent to one day
                    if (Time > (86400000 * 1) && !archivo.contains("config.xml")) {
                        //System.out.println("Borrado:" + archivo);
                        f.delete();
                        f.deleteOnExit();
                    }

                }
            }
        }

    }

    //****************************  Reportes ***********************************
    //***************************** REPORTES ***********************************
    public void imprimir_f_man_004() {
        try {
            byte[] content;
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            content = imprimirFicha();
            response.setContentType("application/pdf");
            response.setContentLength(content == null ? 0 : content.length);
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (SQLException ex) {
            Logger.getLogger(ManMaestroMan.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (JRException ex) {
            Logger.getLogger(ManMaestroMan.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(ManMaestroMan.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] imprimirFicha() throws SQLException, JRException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String reportPath = ctx.getRealPath(File.separator + "reportes" + File.separator);
        HashMap param = new HashMap();
        param.put("cod_lis_equ", cod_lis_equ);
        param.put("cod_man", cod_man);

        //Accesos racc = new Accesos();
        return JasperRunManager.runReportToPdf(reportPath + File.separator + "FMAN004.jasper", param, maccess.Conectar());
    }

    public void ejecutarreporte() {
        try {
            if (!"".equals(cod_lis_equ) && !"0".equals(cod_lis_equ)) {
                parametros.clear();
                parametros.put("codequipo", cod_lis_equ);
                nombrereporte = "/reportes/fichaequipo.jasper";
                nombreexportar = "Ficha_Equipo_" + cod_lis_equ;
                verPDF();
            } else {
                addMessage("Imprimir Detalle", "Debe elegir un Equipo.", 2);
            }
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Lista Equipos." + e.getMessage());
        }

    }

    public void ejecutarreporte2() {
        try {
            parametros.clear();
            if (!"".equals(cod_lis_equ) && !"0".equals(cod_lis_equ) && !"".equals(cod_man) && !"0".equals(cod_man)) {
                parametros.put("codequipo", cod_lis_equ);
                parametros.put("codman", cod_man);

            } else {
                parametros.put("codequipo", "0");
                parametros.put("codman", "0");
                addMessage("Detalle Mantenimiento", "Debe elegir un Equipo y un Mantenimiento.", 2);
            }
            nombrereporte = "/reportes/manequipodetalle.jasper";
            nombreexportar = "Detalle_Mantenimiento_" + cod_lis_equ + "_" + cod_man;
            verPDF();
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Lista Equipos." + e.getMessage());
        }

    }

    public void ejecutarHistoriaMan() {
        try {
            parametros.clear();
            if (!"".equals(cod_lis_equ_his) && !"0".equals(cod_lis_equ_his) && !"".equals(cod_man_his)) {
                parametros.put("codequipo", cod_lis_equ_his);
                parametros.put("codman", cod_man_his);
            } else {
                addMessage("Print", "You have to select a Record", 2);
                parametros.put("codequipo", "0");
                parametros.put("codman", "0");
            }
            nombrereporte = "/reportes/manequipodetalle.jasper";
            nombreexportar = "Maintenance_" + cod_lis_equ_his + "_" + cod_man_his;
            verPDF();

        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte Historia Mantenimiento en ManMaestroMan." + e.getMessage());
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
        } catch (Exception e) {
            System.out.println("Error en verPDF en MaestraMan." + e.getMessage());
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

    //*********************** Calendario y TimeLine ****************************
    public void llenarMttosPreventivos() {
        String mQuery = "";
        try {

            listaMttosPre.clear();

            mQuery = " select mm.cod_lis_equ, mm.cod_man, mm.cod_tip, mm.det_obs, mm.fec_ini, mm.fec_fin, mm.det_sta, mm.cod_usu,concat(equ.nom_equ,' - ', lis.num_ser) as equipo, "
                    + "if((TIMESTAMPDIFF(MONTH,fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,fec_ini,now()))<=2,'yellow','red')) as color,"
                    + "week(fec_ini,1) as semana, mm.cod_alt "
                    + "from tbl_mae_man as mm "
                    + "inner join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "where mm.cod_tip = 1 and mm.det_sta IN (1,3) "
                    + "and (lis.cod_pai = " + cbean.getCod_pai() + " or mm.ord_por =" + cbean.getCod_pai() + " or mm.cod_usu = " + cbean.getCod_usu() + ") "
                    + "order by mm.cod_man;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                listaMttosPre.add(new CatCalendario(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getDate(5),
                        resVariable.getDate(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(12)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Calendarización. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {

        ScheduleEvent smtto = (ScheduleEvent) selectEvent.getObject();

        for (CatCalendario cm : listaMttosPre) {
            if (cm.getCod_man() == smtto.getData()) {
                catcalendario = cm;
                cod_alt = catcalendario.getCod_alt();
                System.out.println(cod_alt);

                break;
            }
        }
    }

    public void llenarMttosCalendario() {
        String mQuery = "";
        try {
            catcalendario = null;
            listaMttos.clear();

            mQuery = " select mm.cod_lis_equ, mm.cod_man, mm.cod_tip, mm.det_obs, mm.fec_ini, mm.fec_fin, mm.det_sta, mm.cod_usu,concat(equ.nom_equ,' - ', lis.num_ser) as equipo, "
                    + "if((TIMESTAMPDIFF(MONTH,fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,fec_ini,now()))<=2,'yellow','red')) as color,"
                    + "week(fec_ini,1) as semana "
                    + "from tbl_mae_man as mm "
                    + "inner join lis_equ as lis on mm.cod_lis_equ = lis.cod_lis_equ "
                    + "left join cat_equ as equ on lis.cod_equ = equ.cod_equ "
                    + "where mm.cod_tip = 1 and mm.det_sta IN (1,3) order by mm.cod_man;";

            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                listaMttos.add(new CatCalendario(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getDate(5),
                        resVariable.getDate(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(12)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Calendarización. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void actualizar() {
        String mQuery = "";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        //Accesos mAccesos = new Accesos();
        maccess.Conectar();

        mQuery = "update tbl_mae_man SET "
                + " fec_ini = '" + fmt.format(catcalendario.getFec_ini()) + "', "
                + " fec_fin = '" + fmt.format(catcalendario.getFec_fin()) + "' "
                + "WHERE cod_man = " + catcalendario.getCod_man() + " AND cod_lis_equ = '" + catcalendario.getCod_lis_equ() + "';";

        maccess.dmlSQLvariable(mQuery);
        maccess.Desconectar();
        addMessage("Guardar Mantenimiento", "Información Almacenada con éxito.", 1);
    }

    public void onEventMove(ScheduleEntryMoveEvent mttoMove) {

        for (CatCalendario cm : listaMttos) {
            if (cm.getCod_man() == mttoMove.getScheduleEvent().getData()) {
                catcalendario = cm;
                actualizar();
                break;
            }
        }

    }

    public void onEventResize(ScheduleEntryResizeEvent mttoResize) {
        for (CatCalendario cm : listaMttos) {
            if (cm.getCod_man() == mttoResize.getScheduleEvent().getData()) {
                catcalendario = cm;
                actualizar();
                break;
            }
        }
    }

    public void onEdit(TimelineModificationEvent e) {
        TimelineEvent tlmtto = e.getTimelineEvent();

        for (CatCalendario cm : listaMttosPre) {
            if (cm.getDes_equ() == tlmtto.getData()) {
                catcalendario = cm;
                cod_alt = catcalendario.getCod_alt();

                break;
            }
        }
    }

    public void onChange(TimelineModificationEvent e) {

        for (CatCalendario cm : listaMttosPre) {
            if (cm.getDes_equ() == e.getTimelineEvent().getData()) {
                Calendar calendar = Calendar.getInstance();

                long dif = cm.getFec_fin().getTime() - cm.getFec_ini().getTime();
                long difDias = dif / (1000 * 60 * 60 * 24);

                calendar.setTime(e.getTimelineEvent().getStartDate());
                calendar.add(Calendar.DAY_OF_YEAR, (int) difDias);

                catcalendario = cm;
                cm.setFec_ini(e.getTimelineEvent().getStartDate());
                cm.setFec_fin(calendar.getTime());

                actualizar();
                break;
            }
        }
    }

    //**************************** Getter y Setters ****************************
    public List<CatListados> getListadoequipo() {
        return listadoequipo;
    }

    public void setListadoequipo(List<CatListados> listadoequipo) {
        this.listadoequipo = listadoequipo;
    }

    public List<CatGrupoFallas> getGrupofallas() {
        return grupofallas;
    }

    public void setGrupofallas(List<CatGrupoFallas> grupofallas) {
        this.grupofallas = grupofallas;
    }

    public List<CatOperaciones> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<CatOperaciones> operaciones) {
        this.operaciones = operaciones;
    }

    public List<CatTipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<CatTipos> tipos) {
        this.tipos = tipos;
    }

    public List<CatPeriodos> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<CatPeriodos> periodos) {
        this.periodos = periodos;
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

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public List<CatBodegas> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<CatBodegas> bodegas) {
        this.bodegas = bodegas;
    }

    public List<CatListados> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<CatListados> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<CatPiezas> getListapiezas() {
        return listapiezas;
    }

    public void setListapiezas(List<CatPiezas> listapiezas) {
        this.listapiezas = listapiezas;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public CatMantenimientosGen getCatmantenimientosgen() {
        return catmantenimientosgen;
    }

    public void setCatmantenimientosgen(CatMantenimientosGen catmantenimientosgen) {
        this.catmantenimientosgen = catmantenimientosgen;
    }

    public List<CatMantenimientosGen> getGeneral() {
        return general;
    }

    public void setGeneral(List<CatMantenimientosGen> general) {
        this.general = general;
    }

    public CatMantenimientosPie getCatmantenimientospie() {
        return catmantenimientospie;
    }

    public void setCatmantenimientospie(CatMantenimientosPie catmantenimientospie) {
        this.catmantenimientospie = catmantenimientospie;
    }

    public List<CatMantenimientosPie> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatMantenimientosPie> piezas) {
        this.piezas = piezas;
    }

    public CatMantenimientosAne getCatmantenimientosane() {
        return catmantenimientosane;
    }

    public void setCatmantenimientosane(CatMantenimientosAne catmantenimientosane) {
        this.catmantenimientosane = catmantenimientosane;
    }

    public List<CatMantenimientosAne> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<CatMantenimientosAne> anexos) {
        this.anexos = anexos;
    }

    public CatMantenimientosFal getCatmantenimientosfal() {
        return catmantenimientosfal;
    }

    public void setCatmantenimientosfal(CatMantenimientosFal catmantenimientosfal) {
        this.catmantenimientosfal = catmantenimientosfal;
    }

    public List<CatMantenimientosFal> getFallas() {
        return fallas;
    }

    public void setFallas(List<CatMantenimientosFal> fallas) {
        this.fallas = fallas;
    }

    public CatMantenimientosAcc getCatmantenimientosacc() {
        return catmantenimientosacc;
    }

    public void setCatmantenimientosacc(CatMantenimientosAcc catmantenimientosacc) {
        this.catmantenimientosacc = catmantenimientosacc;
    }

    public List<CatMantenimientosAcc> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<CatMantenimientosAcc> accesorios) {
        this.accesorios = accesorios;
    }

    public CatCalendario getCatcalendario() {
        return catcalendario;
    }

    public void setCatcalendario(CatCalendario catcalendario) {
        this.catcalendario = catcalendario;
    }

    public List<CatCalendario> getListaMttos() {
        return listaMttos;
    }

    public void setListaMttos(List<CatCalendario> listaMttos) {
        this.listaMttos = listaMttos;
    }

    public ScheduleModel getMttoModel() {
        return mttoModel;
    }

    public void setMttoModel(ScheduleModel mttoModel) {
        this.mttoModel = mttoModel;
    }

    public ScheduleEvent getMtto() {
        return mtto;
    }

    public void setMtto(ScheduleEvent mtto) {
        this.mtto = mtto;
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

    public String getCod_per() {
        return cod_per;
    }

    public void setCod_per(String cod_per) {
        this.cod_per = cod_per;
    }

    public String getFlg_ext() {
        return flg_ext;
    }

    public void setFlg_ext(String flg_ext) {
        this.flg_ext = flg_ext;
    }

    public String getGen_det_man() {
        return gen_det_man;
    }

    public void setGen_det_man(String gen_det_man) {
        this.gen_det_man = gen_det_man;
    }

    public String getGen_fec_man() {
        return gen_fec_man;
    }

    public void setGen_fec_man(String gen_fec_man) {
        this.gen_fec_man = gen_fec_man;
    }

    public String getGen_cod_ope() {
        return gen_cod_ope;
    }

    public void setGen_cod_ope(String gen_cod_ope) {
        this.gen_cod_ope = gen_cod_ope;
    }

    public String getGen_det_obs() {
        return gen_det_obs;
    }

    public void setGen_det_obs(String gen_det_obs) {
        this.gen_det_obs = gen_det_obs;
    }

    public String getGen_cod_usu() {
        return gen_cod_usu;
    }

    public void setGen_cod_usu(String gen_cod_usu) {
        this.gen_cod_usu = gen_cod_usu;
    }

    public String getGen_det_min() {
        return gen_det_min;
    }

    public void setGen_det_min(String gen_det_min) {
        this.gen_det_min = gen_det_min;
    }

    public String getPie_det_man() {
        return pie_det_man;
    }

    public void setPie_det_man(String pie_det_man) {
        this.pie_det_man = pie_det_man;
    }

    public String getPie_fec_man() {
        return pie_fec_man;
    }

    public void setPie_fec_man(String pie_fec_man) {
        this.pie_fec_man = pie_fec_man;
    }

    public String getPie_cod_pai() {
        return pie_cod_pai;
    }

    public void setPie_cod_pai(String pie_cod_pai) {
        this.pie_cod_pai = pie_cod_pai;
    }

    public String getPie_cod_bod() {
        return pie_cod_bod;
    }

    public void setPie_cod_bod(String pie_cod_bod) {
        this.pie_cod_bod = pie_cod_bod;
    }

    public String getPie_cod_ubi() {
        return pie_cod_ubi;
    }

    public void setPie_cod_ubi(String pie_cod_ubi) {
        this.pie_cod_ubi = pie_cod_ubi;
    }

    public Double getPie_det_can() {
        return pie_det_can;
    }

    public void setPie_det_can(Double pie_det_can) {
        this.pie_det_can = pie_det_can;
    }

    public String getPie_cod_pie() {
        return pie_cod_pie;
    }

    public void setPie_cod_pie(String pie_cod_pie) {
        this.pie_cod_pie = pie_cod_pie;
    }

    public String getPie_num_ser() {
        return pie_num_ser;
    }

    public void setPie_num_ser(String pie_num_ser) {
        this.pie_num_ser = pie_num_ser;
    }

    public String getPie_cod_usu() {
        return pie_cod_usu;
    }

    public void setPie_cod_usu(String pie_cod_usu) {
        this.pie_cod_usu = pie_cod_usu;
    }

    public String getAne_det_man() {
        return ane_det_man;
    }

    public void setAne_det_man(String ane_det_man) {
        this.ane_det_man = ane_det_man;
    }

    public String getAne_det_obs() {
        return ane_det_obs;
    }

    public void setAne_det_obs(String ane_det_obs) {
        this.ane_det_obs = ane_det_obs;
    }

    public String getAne_tip_ane() {
        return ane_tip_ane;
    }

    public void setAne_tip_ane(String ane_tip_ane) {
        this.ane_tip_ane = ane_tip_ane;
    }

    public String getAne_rut_ane() {
        return ane_rut_ane;
    }

    public void setAne_rut_ane(String ane_rut_ane) {
        this.ane_rut_ane = ane_rut_ane;
    }

    public String getAne_cod_usu() {
        return ane_cod_usu;
    }

    public void setAne_cod_usu(String ane_cod_usu) {
        this.ane_cod_usu = ane_cod_usu;
    }

    public String getAcc_det_man() {
        return acc_det_man;
    }

    public void setAcc_det_man(String acc_det_man) {
        this.acc_det_man = acc_det_man;
    }

    public String getAcc_fec_man() {
        return acc_fec_man;
    }

    public void setAcc_fec_man(String acc_fec_man) {
        this.acc_fec_man = acc_fec_man;
    }

    public String getAcc_cod_pai() {
        return acc_cod_pai;
    }

    public void setAcc_cod_pai(String acc_cod_pai) {
        this.acc_cod_pai = acc_cod_pai;
    }

    public String getAcc_det_can() {
        return acc_det_can;
    }

    public void setAcc_det_can(String acc_det_can) {
        this.acc_det_can = acc_det_can;
    }

    public String getAcc_des_ite() {
        return acc_des_ite;
    }

    public void setAcc_des_ite(String acc_des_ite) {
        this.acc_des_ite = acc_des_ite;
    }

    public String getAcc_cod_usu() {
        return acc_cod_usu;
    }

    public void setAcc_cod_usu(String acc_cod_usu) {
        this.acc_cod_usu = acc_cod_usu;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getBuscar_pais() {
        return buscar_pais;
    }

    public void setBuscar_pais(String buscar_pais) {
        this.buscar_pais = buscar_pais;
    }

    public String getCod_gru_fal() {
        return cod_gru_fal;
    }

    public void setCod_gru_fal(String cod_gru_fal) {
        this.cod_gru_fal = cod_gru_fal;
    }

    public String getCod_fal() {
        return cod_fal;
    }

    public void setCod_fal(String cod_fal) {
        this.cod_fal = cod_fal;
    }

    public String getOtr_fal() {
        return otr_fal;
    }

    public void setOtr_fal(String otr_fal) {
        this.otr_fal = otr_fal;
    }

    public Date getDfecha1() {
        return dfecha1;
    }

    public void setDfecha1(Date dfecha1) {
        this.dfecha1 = dfecha1;
    }

    public Date getDfecha2() {
        return dfecha2;
    }

    public void setDfecha2(Date dfecha2) {
        this.dfecha2 = dfecha2;
    }

    public Date getDfecha3() {
        return dfecha3;
    }

    public void setDfecha3(Date dfecha3) {
        this.dfecha3 = dfecha3;
    }

    public Date getDfecfinF() {
        return dfecfinF;
    }

    public void setDfecfinF(Date dfecfinF) {
        this.dfecfinF = dfecfinF;
    }

    public Date getDfecini() {
        return dfecini;
    }

    public void setDfecini(Date dfecini) {
        this.dfecini = dfecini;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

    public String getPanindex() {
        return panindex;
    }

    public void setPanindex(String panindex) {
        this.panindex = panindex;
    }

    public String getVlimit() {
        return vlimit;
    }

    public void setVlimit(String vlimit) {
        this.vlimit = vlimit;
    }

    public String getTabindex2() {
        return tabindex2;
    }

    public void setTabindex2(String tabindex2) {
        this.tabindex2 = tabindex2;
    }

    public String getDetailsenabled() {
        return detailsenabled;
    }

    public void setDetailsenabled(String detailsenabled) {
        this.detailsenabled = detailsenabled;
    }

    public String getExiscan() {
        return exiscan;
    }

    public void setExiscan(String exiscan) {
        this.exiscan = exiscan;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public TimelineModel getModelTimeLine() {
        return modelTimeLine;
    }

    public void setModelTimeLine(TimelineModel modelTimeLine) {
        this.modelTimeLine = modelTimeLine;
    }

    public TimelineEvent getEventTimeLine() {
        return eventTimeLine;
    }

    public void setEventTimeLine(TimelineEvent eventTimeLine) {
        this.eventTimeLine = eventTimeLine;
    }

    public List<CatCalendario> getListaMttosPre() {
        return listaMttosPre;
    }

    public void setListaMttosPre(List<CatCalendario> listaMttosPre) {
        this.listaMttosPre = listaMttosPre;
    }

    public String getOrd_por() {
        return ord_por;
    }

    public void setOrd_por(String ord_por) {
        this.ord_por = ord_por;
    }

    public String getBfiltro() {
        return bfiltro;
    }

    public void setBfiltro(String bfiltro) {
        this.bfiltro = bfiltro;
    }

    public Date getDfechacreate() {
        return dfechacreate;
    }

    public void setDfechacreate(Date dfechacreate) {
        this.dfechacreate = dfechacreate;
    }

    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    public CatMantenimientos getCatmantenimientospm() {
        return catmantenimientospm;
    }

    public void setCatmantenimientospm(CatMantenimientos catmantenimientospm) {
        this.catmantenimientospm = catmantenimientospm;
    }

    public List<CatMantenimientos> getMantenimientospm() {
        return mantenimientospm;
    }

    public void setMantenimientospm(List<CatMantenimientos> mantenimientospm) {
        this.mantenimientospm = mantenimientospm;
    }

    public String getBfiltropm() {
        return bfiltropm;
    }

    public void setBfiltropm(String bfiltropm) {
        this.bfiltropm = bfiltropm;
    }

    public String getBuscar_pais2() {
        return buscar_pais2;
    }

    public void setBuscar_pais2(String buscar_pais2) {
        this.buscar_pais2 = buscar_pais2;
    }

    public String getSwitchinicio() {
        return switchinicio;
    }

    public void setSwitchinicio(String switchinicio) {
        this.switchinicio = switchinicio;
    }

    public String getSwitchinicio2() {
        return switchinicio2;
    }

    public void setSwitchinicio2(String switchinicio2) {
        this.switchinicio2 = switchinicio2;
    }

    public CatMantenimientos getCatmantenimientoshis() {
        return catmantenimientoshis;
    }

    public void setCatmantenimientoshis(CatMantenimientos catmantenimientoshis) {
        this.catmantenimientoshis = catmantenimientoshis;
    }

    public List<CatMantenimientos> getMantenimientoshis() {
        return mantenimientoshis;
    }

    public void setMantenimientoshis(List<CatMantenimientos> mantenimientoshis) {
        this.mantenimientoshis = mantenimientoshis;
    }

    public String getVlimit_his() {
        return vlimit_his;
    }

    public void setVlimit_his(String vlimit_his) {
        this.vlimit_his = vlimit_his;
    }

    public String getBuscar_pais_his() {
        return buscar_pais_his;
    }

    public void setBuscar_pais_his(String buscar_pais_his) {
        this.buscar_pais_his = buscar_pais_his;
    }

    public String getBfiltro_his() {
        return bfiltro_his;
    }

    public void setBfiltro_his(String bfiltro_his) {
        this.bfiltro_his = bfiltro_his;
    }

    public CatMantenimientosAne getCatmantenimientosanehis() {
        return catmantenimientosanehis;
    }

    public void setCatmantenimientosanehis(CatMantenimientosAne catmantenimientosanehis) {
        this.catmantenimientosanehis = catmantenimientosanehis;
    }

    public List<CatMantenimientosAne> getAnexoshis() {
        return anexoshis;
    }

    public void setAnexoshis(List<CatMantenimientosAne> anexoshis) {
        this.anexoshis = anexoshis;
    }

    public String getNombreequipoman() {
        return nombreequipoman;
    }

    public void setNombreequipoman(String nombreequipoman) {
        this.nombreequipoman = nombreequipoman;
    }

}
