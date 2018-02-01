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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatBodegas;
import tt.general.CatHistorico;
import tt.general.CatInventarioView;
import tt.general.CatListados;
import tt.general.CatPaises;
import tt.general.CatProveedores;
import tt.general.CatPuntosReorden;
import tt.general.Login;

@Named
@ConversationScoped
public class ManInventarioNew implements Serializable {

    private static final long serialVersionUID = 8799867942566638L;
    @Inject
    Login cbean;
    @Inject
    ManSeguimiento manseg;

    private Accesos macc;

    private List<CatPaises> paises;
    private List<CatBodegas> bodegas;
    private List<CatProveedores> proveedores;

    private List<CatListados> bodegasreo;
    private List<CatListados> ubicacionesreo;

    private List<CatListados> ubicacionesdestino;

    private CatPuntosReorden catpuntosreorden;
    private List<CatPuntosReorden> puntos;

    private List<CatListados> equipos;
    private CatPiezas catpiezas;
    private List<CatPiezas> piezas;

    private List<CatHistorico> historico;

    private CatInventarioView catinventarioview;
    private List<CatInventarioView> vista;

    private String pie_cod_pie, pie_cod_ref, pie_cod_equ, pie_nom_pie, pie_des_pie, pie_cod_cat, pie_det_ima, pie_cod_lin;
    private Double pie_vid_uti, pie_det_pre;

    private String reo_cod_pai, reo_cod_bod, reo_cod_ubi, reo_cod_pie;
    private Double reo_pun_reo;

    private String cod_lis_pie, por_qbo, cod_pai, cod_pro, cod_mov, doc_tra, cod_sol, fec_tra, det_obs, flg_ing, det_sta;
    private String cod_enc, cod_det, cod_pie, cod_bod, cod_ubi, det_cos, det_sta2;
    private Double det_can;

    private String nompie, canexi, canpen, det_ima;
    private Double inputnumber;
    private String boolinout, booleditable, tabacordion, deletedpart;

    private String boddestino, ubidestino;

    private Date mfecha;

    private static DefaultStreamedContent mimagen, mimagen2;

    private String mwidth, mHeight;

    public ManInventarioNew() {
    }

    //*********************** Configuraciones Ventana Principal ********************************
    public void iniciarventana() {

        macc = new Accesos();

        pie_cod_pie = "";
        pie_cod_ref = "";
        pie_cod_equ = "0";
        pie_nom_pie = "";
        pie_des_pie = "";
        pie_cod_cat = "";
        pie_det_ima = "";
        pie_vid_uti = 0.0;
        pie_det_pre = 0.0;
        pie_cod_lin = "0";

        deletedpart = "0";

        mwidth = "140";
        mimagen = null;

        //******************
        reo_cod_pai = "";
        reo_cod_bod = "";
        reo_cod_ubi = "0.";
        reo_cod_pie = "";
        reo_pun_reo = 0.0;

        //******************
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        mfecha = Date.from(Instant.now());
        cod_lis_pie = "";
        por_qbo = "";
        cod_pai = cbean.getCod_pai();
        cod_pro = "";
        cod_mov = "0";
        doc_tra = "";
        cod_sol = "0";
        fec_tra = format.format(mfecha);
        det_obs = "";
        flg_ing = "0";
        det_sta = "0";

        //*********************
        cod_enc = "";
        cod_det = "";
        cod_pie = "0";
        cod_bod = "0";
        cod_ubi = "";
        det_can = 0.0;
        det_cos = "0";
        det_sta2 = "0";

        //*******************
        nompie = "";
        canexi = "0";
        canpen = "0";
        det_ima = "";
        inputnumber = 0.0;

        //********************
        boolinout = "false";
        booleditable = "true";

        tabacordion = "1";

        //********************
        paises = new ArrayList<>();
        proveedores = new ArrayList<>();
        bodegas = new ArrayList<>();
        piezas = new ArrayList<>();
        equipos = new ArrayList<>();

        bodegasreo = new ArrayList<>();
        ubicacionesreo = new ArrayList<>();

        ubicacionesdestino = new ArrayList<>();

        catpuntosreorden = new CatPuntosReorden();
        puntos = new ArrayList<>();

        catinventarioview = new CatInventarioView();
        vista = new ArrayList<>();

        historico = new ArrayList<>();

        llenarPaises();
        llenarBodegas();

        llenarProveedores();
        llenarEquipos();

        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");

    }

    public void cerrarventana() {
        pie_cod_pie = null;
        pie_cod_ref = null;
        pie_cod_equ = null;
        pie_nom_pie = null;
        pie_des_pie = null;
        pie_cod_cat = null;
        pie_det_ima = null;
        pie_vid_uti = null;
        pie_det_pre = null;
        pie_cod_lin = null;
        deletedpart = null;

        mwidth = null;
        mimagen = null;

        reo_cod_pai = null;
        reo_cod_bod = null;
        reo_cod_ubi = null;
        reo_cod_pie = null;
        reo_pun_reo = null;

        cod_lis_pie = null;
        por_qbo = null;
        cod_pai = null;
        cod_pro = null;
        cod_mov = null;
        doc_tra = null;
        cod_sol = null;
        fec_tra = null;
        det_obs = null;
        flg_ing = null;
        det_sta = null;
        cod_enc = null;
        cod_det = null;
        cod_pie = null;
        cod_bod = null;
        cod_ubi = null;
        det_can = null;
        det_cos = null;
        det_sta2 = null;
        nompie = null;
        canexi = null;
        canpen = null;
        det_ima = null;
        inputnumber = null;

        boolinout = null;
        booleditable = null;
        tabacordion = null;

        paises = null;
        proveedores = null;
        bodegas = null;
        piezas = null;
        equipos = null;

        bodegasreo = null;
        ubicacionesreo = null;

        ubicacionesdestino = null;

        catpuntosreorden = null;
        puntos = null;

        catinventarioview = null;
        vista = null;

        historico = null;

        macc = null;
    }

    //*********************** Operaciones Ventana Principal ********************************
    public void nuevapieza() {
        pie_cod_pie = "";
        pie_cod_ref = "";
        pie_cod_equ = "0";
        pie_nom_pie = "";
        pie_des_pie = "";
        pie_cod_cat = "";
        pie_det_ima = "";
        pie_vid_uti = 0.0;
        pie_det_pre = 0.0;
        pie_cod_lin = "0";

        mwidth = "140";
        mimagen = null;
    }

    public boolean validardatospieza() {
        boolean mValidar = true;
        if ("".equals(pie_nom_pie)) {

            addMessage("Save", "You have to enter a Name.", 2);
            return false;
        }

        String contador = "";
        macc.Conectar();
        if ("".equals(pie_cod_pie) || "0".equals(pie_cod_pie)) {
            contador = macc.strQuerySQLvariable("select count(cod_pie) from cat_pie where cod_ref='" + pie_cod_ref + "';");
        } else {
            contador = macc.strQuerySQLvariable("select count(cod_pie) from cat_pie where cod_ref='" + pie_cod_ref + "' and cod_pie <> " + pie_cod_pie + ";");
        }
        if (!"0".equals(contador)) {

            addMessage("Save", "Part Number already exists", 2);
            return false;
        }
        macc.Desconectar();

        return mValidar;

    }

    public void guardarpieza() {
        String mQuery = "";
        if (validardatospieza()) {
            try {
                macc.Conectar();
                if ("".equals(pie_cod_pie)) {
                    mQuery = "select ifnull(max(cod_pie),0)+1 as codigo from cat_pie;";
                    pie_cod_pie = macc.strQuerySQLvariable(mQuery);
                    //det_ima = "/resources/images/piezas/" + "pie_" + cod_pie + ".jpg";
                    mQuery = "insert into cat_pie (cod_pie,cod_ref,cod_equ,nom_pie,des_pie,cod_cat,det_ima,vid_uti,cod_gru,cod_lin,flg_anu) "
                            + "values (" + pie_cod_pie + ",'" + pie_cod_ref + "'," + pie_cod_equ + ",'" + pie_nom_pie
                            + "','" + pie_des_pie + "'," + pie_cod_cat + ",'" + pie_det_ima + "'," + pie_vid_uti + "," + pie_det_pre + "," + pie_cod_lin + ",0);";
                } else {
                    //det_ima = "/resources/images/piezas/" + "pie_" + pie_cod_pie + ".jpg";
                    mQuery = "update cat_pie SET "
                            + " cod_ref = '" + pie_cod_ref + "' "
                            + ",cod_equ = " + pie_cod_equ + " "
                            + ",nom_pie = '" + pie_nom_pie + "' "
                            + ",des_pie = '" + pie_des_pie + "' "
                            + ",cod_cat = " + pie_cod_cat + " "
                            + ",det_ima = '" + pie_det_ima + "' "
                            + ",vid_uti = " + pie_vid_uti + " "
                            + ",cod_gru = " + pie_det_pre + " "
                            + ",cod_lin = " + pie_cod_lin + " "
                            + "WHERE cod_pie = " + pie_cod_pie + " "
                            + "and flg_anu = 0";

                }
                macc.dmlSQLvariable(mQuery);

                if (!"".equals(pie_det_ima)) {
                    try {
                        // ****************************  Inserta Imagen en tabla ************************************************************
                        try {

                            FileInputStream fis = null;
                            PreparedStatement ps = null;
                            File file = null;
                            try {
                                macc.Conectar().setAutoCommit(false);
                                file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pie_det_ima));
                                fis = new FileInputStream(file);
                                if ("0".equals(macc.strQuerySQLvariable("select count(cod_pie) conta from cat_pie_img where cod_pie=" + pie_cod_pie + ";"))) {
                                    mQuery = "insert into cat_pie_img(cod_pie,det_ima) values(?,?)";
                                    ps = macc.Conectar().prepareStatement(mQuery);
                                    ps.setString(1, pie_cod_pie);
                                    ps.setBinaryStream(2, fis, (int) file.length());
                                } else {
                                    mQuery = "update cat_pie_img set det_ima = ? where cod_pie=?;";
                                    ps = macc.Conectar().prepareStatement(mQuery);
                                    ps.setBinaryStream(1, fis, (int) file.length());
                                    ps.setString(2, pie_cod_pie);
                                }

                                ps.executeUpdate();
                                macc.Conectar().commit();

                            } catch (Exception ex) {

                            } finally {
                                try {
                                    ps.close();
                                    fis.close();
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    file = null;
                                } catch (Exception ex) {

                                }
                            }
                            //recuperarimagen();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Guardar archivo Imagen de Piezas." + e.getMessage());
                    }
                }

                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Pieza. " + e.getMessage() + " Query: " + mQuery);
            }
        }
        llenarPiezas();
        nuevapieza();

    }

    public void editarpieza() {
        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinalEditPart').clearFilters()");
        llenarPiezas();
    }

    public void puntoreorden() {
        try {
            boolean mValidar = true;
            if (pie_cod_pie.equals("") || pie_cod_pie.equals("0")) {
                mValidar = false;
                addMessage("Reorder Level", "You have to select a Part from Edit Part", 2);
            }
            if (mValidar) {
                reo_cod_pai = cbean.getCod_pai();
                reo_cod_bod = "0";
                reo_cod_ubi = "0.";
                reo_cod_pie = pie_cod_pie;
                reo_pun_reo = 0.0;

                catpuntosreorden = null;

                llenarBodegasReo();
                llenarReorden();

                RequestContext.getCurrentInstance().update("frmInventarioFinalReo");
                RequestContext.getCurrentInstance().execute("PF('wInventarioFinalReo').show()");

            }
        } catch (Exception ex) {

        }
    }

    public void iniciareliminarpieza() {
        deletedpart = "0";
        llenarPiezas();
        RequestContext.getCurrentInstance().update("frmDelPart");
        RequestContext.getCurrentInstance().execute("PF('wDelPart').show()");

    }

    public void cerrareliminarpieza() {
        deletedpart = "0";
        llenarPiezas();
        ejecutarVista();
        RequestContext.getCurrentInstance().update("frmInventarioFinal");
    }

    public boolean validareliminar() {
        boolean mvalidar = true;
        String mQuery = "";

        if ("".equals(deletedpart) || "0".equals(deletedpart)) {
            addMessage("Delete", "You have to select a Part", 2);
            return false;
        }
        /*
        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_pie) from tbl_det_man_pie where cod_pie = " + pie_cod_pie + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This Part has associated maintenance", 2);
        }
        macc.Desconectar();
         */
 /*macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_pie) from tbl_pie_det where cod_pie = " + pie_cod_pie + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This part has records related to inventory", 2);
        }
        macc.Desconectar();
         */
        return mvalidar;
    }

    public void eliminarpieza() {
        String mQuery = "";

        macc.Conectar();
        if (validareliminar()) {
            try {

                Double contador = 0.0;

                contador = contador + macc.doubleQuerySQLvariable("select count(cod_pie) from tbl_pie_det where cod_pie = " + deletedpart + ";");
                contador = contador + macc.doubleQuerySQLvariable("select count(cod_ite) from sol_det where cod_ite = " + deletedpart + ";");
                contador = contador + macc.doubleQuerySQLvariable("select count(cod_ite) from req_det where cod_ite = " + deletedpart + ";");
                contador = contador + macc.doubleQuerySQLvariable("select count(cod_pie) from tbl_det_man_pie where cod_pie = " + deletedpart + ";");
                contador = contador + macc.doubleQuerySQLvariable("select count(cod_pie) from tbl_pie_his where cod_pie = " + deletedpart + ";");

                if (contador == 0.0) {
                    mQuery = "delete from cat_pie_reo where cod_pie=" + deletedpart + ";";
                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from cat_pie_img where cod_pie=" + deletedpart + ";";
                    macc.dmlSQLvariable(mQuery);
                    mQuery = "delete from cat_pie where cod_pie=" + deletedpart + ";";
                    macc.dmlSQLvariable(mQuery);
                } else {
                    mQuery = "update cat_pie set flg_anu = 1 where cod_pie=" + deletedpart + ";";
                    macc.dmlSQLvariable(mQuery);
                }

                addMessage("Delete", "Information deleted successfully", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Pieza ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarPiezas();
            refreshlist();
            //nuevapieza();
        }
        macc.Desconectar();

    }

    public void nuevaLocation() {
        catinventarioview = null;
        cod_pie = "0";
        try {
            boolean mValidar = true;
            if (fec_tra.equals("") || fec_tra == null) {
                mValidar = false;
                addMessage("Edit", "You have to select a valid date.", 2);
            }
            if (cod_pai.equals("") || cod_pai.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Country.", 2);
            }
            if (cod_bod.equals("") || cod_bod.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Warehouse.", 2);
            }

            if (mValidar) {
                cod_lis_pie = "";
                por_qbo = "";
                cod_pro = "0";
                cod_mov = "0";
                doc_tra = "inv-movement0000";
                cod_sol = "0";
                det_obs = "Create new Part-Location";
                flg_ing = "0";
                det_sta = "0";
                cod_enc = "";
                cod_det = "";
                cod_ubi = "";
                det_can = 0.0;
                det_cos = "0";
                det_sta2 = "0";

                llenarPiezas();

                RequestContext.getCurrentInstance().update("frmInventarioFinalNew");
                RequestContext.getCurrentInstance().execute("PF('wInventarioFinalNew').show()");
            }
        } catch (Exception ex) {
            System.out.println("Error en NuevaLocation ManInventarioNew. " + ex.getMessage());
        }

        nuevapieza();
    }

    public void eliminarLocation() {
        String mQuery = "";

        boolean mValidar = true;
        if (fec_tra.equals("") || fec_tra == null) {
            mValidar = false;
            addMessage("Delete", "You have to select a valid date", 2);
        }
        if (cod_pai.equals("") || cod_pai.equals("0")) {
            mValidar = false;
            addMessage("Delete", "You have to select a Country", 2);
        }
        if (cod_bod.equals("") || cod_bod.equals("0")) {
            mValidar = false;
            addMessage("Delete", "You have to select a Warehouse", 2);
        }
        if (cod_pie.equals("") || cod_pie.equals("0")) {
            mValidar = false;
            addMessage("Delete", "You have to select a Part-Location from List", 2);
        }
        /*
        else if (!canexi.equals("") && !canexi.equals("0")) {
            mValidar = false;
            addMessage("Delete", "This Record have items in Stock. You have to close the pair Part-Location to zero", 2);
        }*/

        if (mValidar) {
            try {

                flg_ing = "1";

                macc.Conectar();

                mQuery = "select ifnull(max(cod_lis_pie),0)+1 as codigo from tbl_pie;";
                cod_lis_pie = macc.strQuerySQLvariable(mQuery);
                mQuery = "insert into tbl_pie (cod_lis_pie,por_qbo,cod_pai,cod_pro,cod_mov,"
                        + "doc_tra,cod_sol,fec_tra,det_obs,flg_ing,det_sta) "
                        + "values (" + cod_lis_pie + ",''," + cod_pai + ",0,0,'inv-movement0001',0,"
                        + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + ",'Delete Location'," + flg_ing + ",0);";
                macc.dmlSQLvariable(mQuery);

                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);

                // -----------               mAccesos.dmlSQLvariable(mQuery);
                String mValues = "";

                //*************************************************************************************************************************
                //*********************************************** Manejo del Detalle ******************************************************
                //*************************************************************************************************************************
                //********************************** Existencias ****************************************
                if (canexi.equals("") || canexi == null) {
                    canexi = "0";
                }
                Double mCantidad;
                mCantidad = Double.valueOf(canexi);
                //código correlativo existencia histórica de artículo
                String cod_cor_exi_art = macc.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                        + "as codigo from tbl_pie_his;");

                //Código correlativo diario existencia histórica de artículo
                String cor_dia = macc.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                        + "as cordia from tbl_pie_his "
                        + "where "
                        + "cod_pie=" + cod_pie + " "
                        + "and fec_his=STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + cod_bod + " "
                        + "and des_ubi = '" + cod_ubi + "' "
                        + ";");

                //Costo promedio
                Double cPromedio, exisAnt, cunitario, mNuevaExistencia;
                if ("1".equals(cod_cor_exi_art)) {
                    cPromedio = Double.valueOf(det_cos.replace(",", ""));
                    exisAnt = 0.0;
                } else {

                    cPromedio = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                            + "from tbl_pie_his "
                            + "where "
                            + "cod_pie=" + cod_pie + " "
                            + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and flg_ing = 0 "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + cod_ubi + "' "
                            + "order by fec_his desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //Existencia Anterior
                    exisAnt = macc.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                            + "from tbl_pie_his "
                            + "where "
                            + "cod_pie=" + cod_pie + " "
                            + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + cod_ubi + "' "
                            + "order by fec_his desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //Inserta Registro
                mNuevaExistencia = (exisAnt - mCantidad);

                cunitario = 0.0;
                mQuery = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                        + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu,cod_pai,cod_bod,des_ubi) "
                        + "VALUES (" + cod_cor_exi_art + "," + cod_pie + ","
                        + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + "," + cor_dia + "," + flg_ing + ","
                        + cod_lis_pie + ",1," + mCantidad + "," + cunitario + ","
                        + mNuevaExistencia + ","
                        + cPromedio + "," + "0" + "," + "now()" + "," + cbean.getCod_usu() + ","
                        + cod_pai + "," + cod_bod + ",'" + cod_ubi + "');";

                macc.dmlSQLvariable(mQuery);

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                        + "from tbl_pie_his where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + "and ord_dia > " + cor_dia + " "
                        + "and cod_pie = " + cod_pie + " "
                        + "and det_sta = 0 "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + cod_bod + " "
                        + "and des_ubi = '" + cod_ubi + "' "
                        + ";");
                contasiguientes = String.valueOf(
                        Integer.valueOf(contasiguientes)
                        + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                + "from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + ";")));

                Double nuevacantidad = mNuevaExistencia;
                if ("0".equals(contasiguientes) == false) {
                    try {
                        historico.clear();

                        ResultSet resvariable;
                        resvariable = macc.querySQLvariable(
                                "select "
                                + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                + "fec_mod,cod_usu "
                                + "from tbl_pie_his "
                                + "where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and ord_dia > " + cor_dia + " "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
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
                        
                        resvariable = macc.querySQLvariable(
                                "select "
                                + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                + "fec_mod,cod_usu "
                                + "from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
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
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en actualización de costos posteriores Agregar Delete Location. " + e.getMessage() + " mQuery: " + mQuery);
                    }

                }

                // Tratamiento tabla bol_exi_pai
                String mContador = macc.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                        + "where "
                        + "cod_pai=" + cod_pai + " "
                        + "and ing_sal=" + flg_ing + " "
                        + "and cod_pie=" + cod_pie
                        + ";");

                if ("0".equals(mContador)) {

                    mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                            + "VALUES ("
                            + cod_pai + ","
                            + cod_pie + ","
                            + flg_ing + ","
                            + mCantidad
                            + ");";

                } else {
                    mQuery = "update bol_exi_pai set "
                            + "det_exi= (det_exi + " + mCantidad + ") "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and ing_sal=" + flg_ing + " "
                            + "and cod_pie=" + cod_pie + ";";

                }

                macc.dmlSQLvariable(mQuery);

                // Tratamiento tabla tbl_existencias
                mContador = macc.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                        + "where "
                        + "cod_pai=" + cod_pai + " "
                        + "and cod_bod=" + cod_bod + " "
                        + "and cod_ubi = '" + cod_ubi + "' "
                        + "and cod_pie=" + cod_pie
                        + ";");

                if ("0".equals(mContador)) {

                    mQuery = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                            + "VALUES ("
                            + macc.strQuerySQLvariable("select (ifnull(max(cod_exi),0) + 1) as codigo from tbl_existencias;") + ","
                            + cod_pie + ","
                            + cod_pai + ","
                            + cod_bod + ",'"
                            + cod_ubi + "',"
                            + mCantidad
                            + ",0);";

                } else if ("0".equals(flg_ing)) {
                    mQuery = " update tbl_existencias set det_can=(det_can+" + mCantidad + ") "
                            + " where cod_pai=" + cod_pai + " and cod_bod = " + cod_bod
                            + " and cod_ubi = '" + cod_ubi + "' "
                            + " and cod_pie=" + cod_pie + " ;";
                } else {
                    mQuery = " update tbl_existencias set det_can=(det_can-" + mCantidad + ") "
                            + " where cod_pai=" + cod_pai + " and cod_bod = " + cod_bod
                            + " and cod_ubi = '" + cod_ubi + "' "
                            + " and cod_pie=" + cod_pie + " ;";
                }

                macc.dmlSQLvariable(mQuery);

//              ********************************* Fin Existencias ************************************
                mValues = mValues + "," + "("
                        + cod_lis_pie + ",1,"
                        + cod_pie + ","
                        + cod_bod + ",'"
                        + cod_ubi + "',"
                        + mCantidad + ","
                        + 0 + ","
                        + 0
                        + ")";

                // ******************* Inserta Detalles*****************
                mValues = mValues.substring(1);
                mQuery = " insert into tbl_pie_det(cod_enc,cod_det,"
                        + "cod_pie,cod_bod,cod_ubi,det_can,det_cos,det_sta) "
                        + "values " + mValues + ";";

                macc.dmlSQLvariable(mQuery);

                //Borrado Lógico el registro del Histórico
                mQuery = " update tbl_pie_his set det_sta=1 "
                        + " where cod_pie = " + cod_pie + " and cod_pai=" + cod_pai + " and cod_bod=" + cod_bod + " and des_ubi='" + cod_ubi + "';";
                macc.dmlSQLvariable(mQuery);

                macc.Desconectar();
                addMessage("Delete Part-Location", "Information has been successfully removed.", 1);

            } catch (Exception e) {
                addMessage("Delete Part-Location", "Error when Deliting data. " + e.getMessage(), 2);
                System.out.println("Error al EliminarLocation de ManInventarioNew. " + e.getMessage());
            }

            cod_lis_pie = "";
            por_qbo = "";
            cod_pro = "0";
            cod_mov = "0";
            doc_tra = "";
            cod_sol = "0";
            det_obs = "";
            flg_ing = "0";
            det_sta = "0";
            cod_enc = "";
            cod_det = "";
            cod_pie = "0";
            cod_ubi = "0";
            det_can = 0.0;
            det_cos = "0";
            det_sta2 = "0";
            nompie = "";
            canexi = "0";
            canpen = "0";

            catinventarioview = null;

            //RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");
            refreshlist();

        }

        nuevapieza();

    }

    public void onEdit() {
        try {
            boolean mValidar = true;
            if (fec_tra.equals("") || fec_tra == null) {
                mValidar = false;
                addMessage("Edit", "You have to select a valid date.", 2);
            }
            if (cod_pai.equals("") || cod_pai.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Country.", 2);
            }
            if (cod_bod.equals("") || cod_bod.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Warehouse.", 2);
            }
            if (cod_pie.equals("") || cod_pie.equals("0")) {
                mValidar = false;
            }
            if (mValidar) {
                doc_tra = "inv-movement0002";
                det_obs = "Edit Stock by hand";
                flg_ing = "0";
                boolinout = "false";
                booleditable = "true";
                inputnumber = 0.00;
                mHeight = "250";
                //Accesos macc = new Accesos();
                /*
                macc.Conectar();
                det_ima = macc.strQuerySQLvariable("Select ifnull(det_ima,'/resources/images/piezas/noimage.png') as ima from cat_pie where cod_pie=" + cod_pie + ";");
                macc.Desconectar();
                if (det_ima.equals("")) {
                    det_ima = "/resources/images/piezas/noimage.png";
                }
                 */
                recuperarimagenEdit();
                llenarProveedores();
                RequestContext.getCurrentInstance().update("frmInventarioFinalEdit");
                RequestContext.getCurrentInstance().execute("PF('wInventarioFinalEdit').show()");
            }
        } catch (Exception ex) {
            System.out.println("Error en onEdit ManInventarioNew. " + ex.getMessage());
        }
        nuevapieza();
    }

    public void onPOView() {
        try {
            boolean mValidar = true;
            if (fec_tra.equals("") || fec_tra == null) {
                mValidar = false;
                addMessage("Edit", "You have to select a valid date.", 2);
            }
            if (cod_pai.equals("") || cod_pai.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Country.", 2);
            }
            if (cod_bod.equals("") || cod_bod.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Warehouse.", 2);
            }
            if (cod_pie.equals("") || cod_pie.equals("0")) {
                mValidar = false;
                addMessage("Edit", "You have to select a Part.", 2);
            }
            if (canpen.equals("") || canpen.equals("0")) {
                mValidar = false;
                addMessage("Edit", "This Part does not have pending orders.", 2);
            }

            if (mValidar) {

                try {
                    manseg.iniciarventana();
                    manseg.llenarMaestroForaneo(cod_pai, cod_pie);

                } catch (Exception e) {
                    System.out.println("Error en el llenado Maestro en POView ManInventarionNew. " + e.getMessage());
                }

            }
        } catch (Exception ex) {
            System.out.println("Error en onPOView ManInventarioNew. " + ex.getMessage());
        }
        nuevapieza();
    }

    public void refreshlist() {
        cod_pie = "0";
        nuevapieza();
        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");

        boolean mValidar = true;
        String mQuery = "";

        catinventarioview = null;
        vista.clear();

        if ("0".equals(cod_pai) || "".equals(cod_pai)) {
            mValidar = false;

        }

        if ("0".equals(cod_bod) || "".equals(cod_bod)) {
            mValidar = false;

        }

        if (mValidar) {

            try {

                mQuery = "SELECT "
                        + cod_pai + " as pais, "
                        + cod_bod + " as bodega, "
                        + "piezas.des_ubi as ubicacion, "
                        + "piezas.cod_pie, "
                        + "IFNULL(piezas.cod_ref,'') as referencia, "
                        + "IFNULL(piezas.nom_pie,'') as nombre, "
                        + "piezas.existencias, "
                        + "piezas.pendientes "
                        + "from( "
                        + "select  "
                        + "pie.cod_pie,  "
                        + "pie.cod_ref,  "
                        + "pie.nom_pie,  "
                        + "pie.cod_equ,  "
                        + "pie.cod_cat, "
                        + "ifnull(ubis.des_ubi,'') as des_ubi, "
                        + "ifnull( "
                        + "(select exi.can_exi "
                        + "from tbl_pie_his as exi  "
                        + "where exi.det_sta = 0  "
                        + "and exi.cod_pie = pie.cod_pie  "
                        + "and exi.cod_pai =  " + cod_pai + "  "
                        + "and exi.cod_bod =  " + cod_bod + "   "
                        + "and exi.des_ubi = ubis.des_ubi "
                        + "and exi.fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')  "
                        + "order by exi.fec_his desc,exi.ord_dia desc limit 1),0) as existencias, "
                        + "ifnull((select sum(det_can_pen)  "
                        + "from sol_det as det  "
                        + "left join sol_mae as mae on mae.cod_mae = det.cod_mae  "
                        + "where mae.cod_pai =  " + cod_pai + "   "
                        + "and det.cod_ite = pie.cod_pie  "
                        + "and det.det_sta <> 2 and mae.det_sta not in (0,1,3) ),0) as pendientes  "
                        + "from "
                        + "( "
                        + " select cpi.cod_pie,  "
                        + "cpi.cod_ref,  "
                        + "cpi.cod_equ,  "
                        + "cpi.nom_pie,  "
                        + "cpi.des_pie,  "
                        + "cpi.cod_cat,  "
                        + "cpi.det_ima,  "
                        + "cpi.vid_uti,  "
                        + "cpi.cod_gru,  "
                        + "cpi.cod_lin,  "
                        + "cpi.flg_anu  "
                        + "from cat_pie as cpi  "
                        + "where cpi.cod_pie in ( "
                        + "select distinct phi.cod_pie  "
                        + "from tbl_pie_his as phi "
                        + "where cod_pai = " + cod_pai + " and cod_bod =" + cod_bod + " "
                        + "order by phi.cod_pie) "
                        + " ) "
                        + "as pie  "
                        + "left join ( "
                        + "select exiubi.cod_pie, exiubi.des_ubi "
                        + "from tbl_pie_his as exiubi "
                        + "where exiubi.det_sta=0 "
                        + "and exiubi.cod_pai = " + cod_pai + " and exiubi.cod_bod = " + cod_bod + " group by exiubi.cod_pie, exiubi.des_ubi "
                        + ") as ubis on pie.cod_pie= ubis.cod_pie "
                        + "and pie.flg_anu = 0 "
                        + " ) as piezas "
                        + "order by referencia, ubicacion;";

                //                        + "where (piezas.existencias > 0 or piezas.pendientes > 0) "
                ResultSet resVariable;
                //Accesos mAccesos = new Accesos();
                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    vista.add(new CatInventarioView(
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
                macc.Desconectar();

            } catch (Exception e) {
                System.out.println("Error en Refreshlist en ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);
            }
        }

    }

    //**************************** Llenado de Catálogo Principal ***********
    public void llenarPaises() {
        try {

            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by nom_pai;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
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

            catinventarioview = null;
            vista.clear();

            bodegas.clear();
            cod_bod = "0";

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en ManInventarioView. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {
            bodegas.clear();

            mQuery = "select id_bod, nom_bod,cod_pai "
                    + "from cat_bodegas "
                    + "where cod_pai=" + cod_pai + " "
                    + "order by id_bod;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatBodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3), ""
                ));
            }
            resVariable.close();
            macc.Desconectar();

            catinventarioview = null;
            vista.clear();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarBodegasReo() {
        String mQuery = "";
        try {
            bodegasreo.clear();

            mQuery = "select id_bod, nom_bod "
                    + "from cat_bodegas "
                    + "where cod_pai=" + reo_cod_pai + " "
                    + "order by id_bod;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegasreo.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

            ubicacionesreo.clear();
            reo_pun_reo = 0.0;

        } catch (Exception e) {
            System.out.println("Error en el llenado de BodegasReo en ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicacionesReo() {
        String mQuery = "";
        try {
            ubicacionesreo.clear();

            mQuery = "select "
                    + "exiubi.cod_pie, "
                    + "exiubi.des_ubi "
                    + "from tbl_pie_his as exiubi "
                    + "where exiubi.det_sta = 0 "
                    + "and exiubi.cod_pai = " + reo_cod_pai + " "
                    + "and exiubi.cod_bod = " + reo_cod_bod + " "
                    + "and exiubi.cod_pie = " + reo_cod_pie + " "
                    + "group by exiubi.cod_pie, exiubi.des_ubi "
                    + "order by exiubi.fec_his desc;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicacionesreo.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

            reo_pun_reo = 0.0;

        } catch (Exception e) {
            System.out.println("Error en el llenado de UbicacionesReo en ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarProveedores() {
        String mQuery = "";
        try {
            proveedores.clear();

            mQuery = "select cod_pro,cod_pai,nom_pro,per_con,tel_con,det_mai,flg_pro_pie  "
                    + "from cat_sup where flg_pro_pie = 1 order by cod_pro;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Proveedores en ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEquipos() {
        String mQuery = "";
        try {
            equipos.clear();

            mQuery = "select equ.cod_equ,equ.nom_equ "
                    + "from cat_equ as equ "
                    + "order by equ.cod_equ;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Equipos en Nuevo Inventario. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPiezas() {
        String mQuery = "";
        try {
            catpiezas = null;
            piezas.clear();

            mQuery = "select pie.cod_pie,pie.cod_ref,pie.cod_equ,"
                    + "pie.nom_pie,pie.des_pie,equ.nom_equ,pie.cod_cat,"
                    + "pie.det_ima,pie.vid_uti,pie.cod_gru,pie.cod_lin, "
                    + "gru.nom_pro,"
                    + "lin.nom_lin "
                    + "from cat_pie as pie "
                    + "left join cat_equ as equ on equ.cod_equ=pie.cod_equ "
                    + "left join cat_sup as gru on pie.cod_gru = gru.cod_pro "
                    + "left join cat_lin as lin on pie.cod_gru = lin.cod_gru and lin.cod_lin = pie.cod_lin "
                    + "where pie.flg_anu = 0 "
                    + "order by pie.cod_pie;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezas.add(new CatPiezas(
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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Piezas en ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarReorden() {
        String mQuery = "";
        try {
            catpuntosreorden = null;
            puntos.clear();

            mQuery = "select "
                    + "mae.cod_pai, "
                    + "mae.cod_bod, "
                    + "mae.cod_ubi, "
                    + "mae.cod_pie, "
                    + "mae.pun_reo, "
                    + "pai.nom_pai, "
                    + "bod.nom_bod, "
                    + "pie.nom_pie "
                    + "from cat_pie_reo as mae "
                    + "left join cat_pai as pai on mae.cod_bod = pai.cod_pai "
                    + "left join cat_bodegas as bod on mae.cod_pai = bod.cod_pai and mae.cod_bod = bod.id_bod "
                    + "left join cat_pie as pie on mae.cod_pie = pie.cod_pie "
                    + "where mae.cod_pai = " + reo_cod_pai + " and mae.cod_pie = " + pie_cod_pie + " and pie.flg_anu=0;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                puntos.add(new CatPuntosReorden(
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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Puntos de Reorden de Piezas en ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void ejecutarVista() {
        boolean mValidar = true;
        String mQuery = "";

        catinventarioview = null;
        vista.clear();

        if ("0".equals(cod_pai) || "".equals(cod_pai)) {
            mValidar = false;
            addMessage("View", "You have to select a Country.", 2);
        }

        if ("0".equals(cod_bod) || "".equals(cod_bod)) {
            mValidar = false;
            addMessage("View", "You have to select a Warehouse.", 2);
        }

        if (mValidar) {

            try {

                mQuery = "SELECT "
                        + cod_pai + " as pais, "
                        + cod_bod + " as bodega, "
                        + "piezas.des_ubi as ubicacion, "
                        + "piezas.cod_pie, "
                        + "IFNULL(piezas.cod_ref,'') as referencia, "
                        + "IFNULL(piezas.nom_pie,'') as nombre, "
                        + "piezas.existencias, "
                        + "piezas.pendientes "
                        + "from( "
                        + "select  "
                        + "pie.cod_pie,  "
                        + "pie.cod_ref,  "
                        + "pie.nom_pie,  "
                        + "pie.cod_equ,  "
                        + "pie.cod_cat, "
                        + "ifnull(ubis.des_ubi,'') as des_ubi, "
                        + "ifnull( "
                        + "(select exi.can_exi "
                        + "from tbl_pie_his as exi  "
                        + "where exi.det_sta = 0  "
                        + "and exi.cod_pie = pie.cod_pie  "
                        + "and exi.cod_pai =  " + cod_pai + "  "
                        + "and exi.cod_bod =  " + cod_bod + "   "
                        + "and exi.des_ubi = ubis.des_ubi "
                        + "and exi.fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')  "
                        + "order by exi.fec_his desc,exi.ord_dia desc limit 1),0) as existencias, "
                        + "ifnull((select sum(det_can_pen)  "
                        + "from sol_det as det  "
                        + "left join sol_mae as mae on mae.cod_mae = det.cod_mae  "
                        + "where mae.cod_pai =  " + cod_pai + "   "
                        + "and det.cod_ite = pie.cod_pie  "
                        + "and det.det_sta <> 2 and mae.det_sta not in (0,1,3) ),0) as pendientes  "
                        + "from "
                        + "( "
                        + " select cpi.cod_pie,  "
                        + "cpi.cod_ref,  "
                        + "cpi.cod_equ,  "
                        + "cpi.nom_pie,  "
                        + "cpi.des_pie,  "
                        + "cpi.cod_cat,  "
                        + "cpi.det_ima,  "
                        + "cpi.vid_uti,  "
                        + "cpi.cod_gru,  "
                        + "cpi.cod_lin,  "
                        + "cpi.flg_anu  "
                        + "from cat_pie as cpi  "
                        + "where cpi.cod_pie in ( "
                        + "select distinct phi.cod_pie  "
                        + "from tbl_pie_his as phi "
                        + "where cod_pai = " + cod_pai + " and cod_bod =" + cod_bod + " "
                        + "order by phi.cod_pie) "
                        + " )"
                        + " as pie  "
                        + "left join ( "
                        + "select exiubi.cod_pie, exiubi.des_ubi "
                        + "from tbl_pie_his as exiubi "
                        + "where exiubi.det_sta=0 and exiubi.cod_pai = " + cod_pai + " and exiubi.cod_bod = " + cod_bod + " group by exiubi.cod_pie, exiubi.des_ubi "
                        + ") as ubis on pie.cod_pie= ubis.cod_pie "
                        + "and pie.flg_anu = 0 "
                        + " ) as piezas "
                        + "order by referencia, ubicacion;";
                ResultSet resVariable;

                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    vista.add(new CatInventarioView(
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
                macc.Desconectar();

            } catch (Exception e) {
                System.out.println("Error en Ejecución de Vista en ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);
            }
        }
    }

    //*********************** Operaciones Edit Stock ***************************
    public void inout() {
        cod_pro = "0";
        if ("true".equals(boolinout)) {
            //proveedores.clear();
            booleditable = "false";
            flg_ing = "1";
        } else {
            llenarProveedores();
            booleditable = "true";
            flg_ing = "0";
        }
    }

    public boolean validardatosEdit() {
        boolean mvalidar = true;
        det_can = inputnumber;
        if (det_can == null) {
            det_can = 0.0;
        }
        if (det_can == 0.0) {
            mvalidar = false;
            addMessage("Save", "You have to enter a Quantity above zero.", 2);
        }
        if (flg_ing.equals("1")) {
            if ((Double.valueOf(canexi) - det_can) < 0) {
                mvalidar = false;
                addMessage("Save", "This Quantity exceeds stocks.", 2);
            }
        }
        return mvalidar;
    }

    public void guardarEdit() {
        if (guardarMovimiento()) {
            cod_lis_pie = "";
            por_qbo = "";
            cod_pro = "0";
            cod_mov = "0";
            doc_tra = "";
            cod_sol = "0";
            det_obs = "";
            flg_ing = "0";
            det_sta = "0";
            cod_enc = "";
            cod_det = "";
            cod_pie = "0";
            det_can = 0.0;
            det_cos = "0";
            det_sta2 = "0";
            nompie = "";
            det_ima = "";

            mHeight = "250";
            mimagen2 = null;

            boolinout = "false";
            booleditable = "true";

            //proveedores = null;
            catinventarioview = null;
        }
    }

    public boolean guardarMovimiento() {
        String mQuery = "";

        if (validardatosEdit()) {
            try {
                macc.Conectar();

                mQuery = "select ifnull(max(cod_lis_pie),0)+1 as codigo from tbl_pie;";
                cod_lis_pie = macc.strQuerySQLvariable(mQuery);
                mQuery = "insert into tbl_pie (cod_lis_pie,por_qbo,cod_pai,cod_pro,cod_mov,"
                        + "doc_tra,cod_sol,fec_tra,det_obs,flg_ing,det_sta) "
                        + "values (" + cod_lis_pie + ",'" + por_qbo + "'," + cod_pai + ","
                        + cod_pro + "," + cod_mov + ",'" + doc_tra + "'," + cod_sol + ","
                        + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + ",'"
                        + det_obs + "'," + flg_ing + "," + det_sta + ");";
                macc.dmlSQLvariable(mQuery);

                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);

                // -----------               mAccesos.dmlSQLvariable(mQuery);
                String mValues = "";

                //*************************************************************************************************************************
                //*********************************************** Manejo del Detalle ******************************************************
                //*************************************************************************************************************************
                //********************************** Existencias ****************************************
                Double mCantidad;
                mCantidad = det_can;
                //código correlativo existencia histórica de artículo
                String cod_cor_exi_art = macc.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                        + "as codigo from tbl_pie_his;");

                //Código correlativo diario existencia histórica de artículo
                String cor_dia = macc.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                        + "as cordia from tbl_pie_his "
                        + "where "
                        + "cod_pie=" + cod_pie + " "
                        + "and fec_his=STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + cod_bod + " "
                        + "and des_ubi = '" + cod_ubi + "' "
                        + ";");

                //Costo promedio
                Double cPromedio, exisAnt, cunitario, mNuevaExistencia;
                if ("1".equals(cod_cor_exi_art)) {
                    cPromedio = Double.valueOf(det_cos.replace(",", ""));
                    exisAnt = 0.0;
                } else {
                    if ("0".equals(flg_ing)) {
                        cPromedio = macc.doubleQuerySQLvariable("select (ifnull((can_exi*cos_pro),0)+"
                                + (det_can * Double.valueOf(det_cos)) + ")"
                                + "/(IFNULL(can_exi,0)+" + mCantidad + ") as Cpromedio "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + cod_pie + " "
                                + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    } else {
                        cPromedio = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + cod_pie + " "
                                + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }
                    //Existencia Anterior
                    exisAnt = macc.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                            + "from tbl_pie_his "
                            + "where "
                            + "cod_pie=" + cod_pie + " "
                            + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + cod_ubi + "' "
                            + "order by fec_his desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //Inserta Registro
                if ("0".equals(flg_ing)) {
                    mNuevaExistencia = (exisAnt + mCantidad);
                } else {
                    mNuevaExistencia = (exisAnt - mCantidad);
                }
                cunitario = Double.valueOf(det_cos.replace(",", ""));
                mQuery = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                        + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu,cod_pai,cod_bod,des_ubi) "
                        + "VALUES (" + cod_cor_exi_art + "," + cod_pie + ","
                        + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + "," + cor_dia + "," + flg_ing + ","
                        + cod_lis_pie + ",1," + mCantidad + "," + cunitario + ","
                        + mNuevaExistencia + ","
                        + cPromedio + "," + "0" + "," + "now()" + "," + cbean.getCod_usu() + ","
                        + cod_pai + "," + cod_bod + ",'" + cod_ubi + "');";

                macc.dmlSQLvariable(mQuery);

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                        + "from tbl_pie_his where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + "and ord_dia > " + cor_dia + " "
                        + "and cod_pie = " + cod_pie + " "
                        + "and det_sta = 0 "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + cod_bod + " "
                        + "and des_ubi = '" + cod_ubi + "' "
                        + ";");
                contasiguientes = String.valueOf(
                        Integer.valueOf(contasiguientes)
                        + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                + "from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + ";")));

                Double nuevacantidad = mNuevaExistencia;
                if ("0".equals(contasiguientes) == false) {
                    try {
                        historico.clear();

                        ResultSet resvariable;
                        resvariable = macc.querySQLvariable("select "
                                + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                + "fec_mod,cod_usu "
                                + "from tbl_pie_his "
                                + "where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and ord_dia > " + cor_dia + " "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + "order by fec_his asc,"
                                + "ord_dia asc "
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

                        resvariable = macc.querySQLvariable("select "
                                + "cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,"
                                + "fec_mod,cod_usu "
                                + "from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
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
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en actualización de costos posteriores Agregar. " + e.getMessage() + " mQuery: " + mQuery);
                    }

                }

                // Tratamiento tabla bol_exi_pai
                String mContador = macc.strQuerySQLvariable("select count(cod_pie) as contador from bol_exi_pai "
                        + "where "
                        + "cod_pai=" + cod_pai + " "
                        + "and ing_sal=" + flg_ing + " "
                        + "and cod_pie=" + cod_pie
                        + ";");

                if ("0".equals(mContador)) {

                    mQuery = "insert into bol_exi_pai(cod_pai,cod_pie,ing_sal,det_exi) "
                            + "VALUES ("
                            + cod_pai + ","
                            + cod_pie + ","
                            + flg_ing + ","
                            + det_can
                            + ");";

                } else {
                    mQuery = "update bol_exi_pai set "
                            + "det_exi= (det_exi + " + det_can + ") "
                            + "where "
                            + "cod_pai=" + cod_pai + " "
                            + "and ing_sal=" + flg_ing + " "
                            + "and cod_pie=" + cod_pie + ";";

                }

                macc.dmlSQLvariable(mQuery);

                // Tratamiento tabla tbl_existencias
                mContador = macc.strQuerySQLvariable("select count(cod_exi) as contador from tbl_existencias "
                        + "where "
                        + "cod_pai=" + cod_pai + " "
                        + "and cod_bod=" + cod_bod + " "
                        + "and cod_ubi = '" + cod_ubi + "' "
                        + "and cod_pie=" + cod_pie
                        + ";");

                if ("0".equals(mContador)) {

                    mQuery = "insert into tbl_existencias(cod_exi,cod_pie,cod_pai,cod_bod,cod_ubi,det_can,cos_pro) "
                            + "VALUES ("
                            + macc.strQuerySQLvariable("select (ifnull(max(cod_exi),0) + 1) as codigo from tbl_existencias;") + ","
                            + cod_pie + ","
                            + cod_pai + ","
                            + cod_bod + ",'"
                            + cod_ubi + "',"
                            + det_can
                            + ",0);";

                } else if ("0".equals(flg_ing)) {
                    mQuery = " update tbl_existencias set det_can=(det_can+" + det_can + ") "
                            + " where cod_pai=" + cod_pai + " and cod_bod = " + cod_bod
                            + " and cod_ubi = '" + cod_ubi + "' "
                            + " and cod_pie=" + cod_pie + " ;";
                } else {
                    mQuery = " update tbl_existencias set det_can=(det_can-" + det_can + ") "
                            + " where cod_pai=" + cod_pai + " and cod_bod = " + cod_bod
                            + " and cod_ubi = '" + cod_ubi + "' "
                            + " and cod_pie=" + cod_pie + " ;";
                }

                macc.dmlSQLvariable(mQuery);

//              ********************************* Fin Existencias ************************************
                mValues = mValues + "," + "("
                        + cod_lis_pie + ",1,"
                        + cod_pie + ","
                        + cod_bod + ",'"
                        + cod_ubi + "',"
                        + det_can + ","
                        + det_cos + ","
                        + det_sta
                        + ")";

                // ******************* Inserta Detalles*****************
                mValues = mValues.substring(1);
                mQuery = " insert into tbl_pie_det(cod_enc,cod_det,"
                        + "cod_pie,cod_bod,cod_ubi,det_can,det_cos,det_sta) "
                        + "values " + mValues + ";";

                macc.dmlSQLvariable(mQuery);

                macc.Desconectar();
                addMessage("Save", "successful.", 1);
                RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");
                ejecutarVista();
                RequestContext.getCurrentInstance().execute("PF('wInventarioFinalEdit').hide()");
                return true;
            } catch (Exception e) {
                addMessage("Save", "Error when saving data. " + e.getMessage(), 2);
                System.out.println("Error al Guardar ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
                return false;
            }

        } else {
            return false;
        }
    }

    public void iniciarMove() {
        boolean mvalidar = true;
        det_can = inputnumber;

        if (det_can == null) {
            det_can = 0.0;
        }
        if (det_can == 0.0) {
            mvalidar = false;
            addMessage("Move", "You have to enter a Quantity above zero", 2);
        }
        if (flg_ing.equals("1")) {
            if ((Double.valueOf(canexi) - det_can) < 0) {
                mvalidar = false;
                addMessage("Move", "This Quantity exceeds stocks", 2);
            }
        } else {
            mvalidar = false;
            addMessage("Move", "You have to select an output operation", 2);
        }

        if (mvalidar) {
            ubicacionesdestino.clear();

            doc_tra = "inv-movement0003";
            det_obs = "Move parts";

            boddestino = "0";
            ubidestino = "";

            RequestContext.getCurrentInstance().update("frmTrasladoPie");
            RequestContext.getCurrentInstance().execute("PF('wTrasladoPie').show()");

        }
    }

    public void cerrarMove() {
        ubicacionesdestino.clear();

        doc_tra = "inv-movement0002";
        det_obs = "Edit Stock by hand";

        boddestino = "0";
        ubidestino = "";
    }

    public void guardarMove() {
        boolean mvalidar = true;

        if (boddestino.equals("0")) {
            mvalidar = false;
            addMessage("Save", "You have to select a Warehouse", 2);
        }

        if (ubidestino.equals("")) {
            mvalidar = false;
            addMessage("Save", "You have to select a Location", 2);
        }

        if (mvalidar) {
            guardarMovimiento();

            cod_bod = boddestino;
            cod_ubi = ubidestino;
            flg_ing = "0";

            guardarMovimiento();

            boddestino = "0";
            ubidestino = "";

            cod_lis_pie = "";
            por_qbo = "";
            cod_pro = "0";
            cod_mov = "0";
            doc_tra = "";
            cod_sol = "0";
            det_obs = "";
            flg_ing = "0";
            det_sta = "0";
            cod_enc = "";
            cod_det = "";
            cod_pie = "0";
            det_can = 0.0;
            det_cos = "0";
            det_sta2 = "0";
            nompie = "";
            det_ima = "";

            mHeight = "250";
            mimagen2 = null;

            boolinout = "false";
            booleditable = "true";

            catinventarioview = null;
        }

    }

    public void llenarUbicacionesTras() {
        String mQuery = "";
        try {
            ubicacionesdestino.clear();

            ubidestino = "";

            mQuery = "select "
                    + "exiubi.cod_pie, "
                    + "exiubi.des_ubi "
                    + "from tbl_pie_his as exiubi "
                    + "where exiubi.det_sta = 0 "
                    + "and exiubi.cod_pai = " + cod_pai + " "
                    + "and exiubi.cod_bod = " + boddestino + " "
                    + "and exiubi.cod_pie = " + cod_pie + " "
                    + "group by exiubi.cod_pie, exiubi.des_ubi "
                    + "order by exiubi.fec_his desc;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicacionesdestino.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de UbicacionesReo en ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void cerrarventanaEdit() {
        cod_lis_pie = "";
        por_qbo = "";
        cod_pro = "0";
        cod_mov = "0";
        doc_tra = "";
        cod_sol = "0";
        det_obs = "";
        flg_ing = "0";
        det_sta = "0";
        cod_enc = "";
        cod_det = "";
        cod_pie = "0";
        cod_ubi = "0";
        det_can = 0.0;
        det_cos = "0";
        det_sta2 = "0";
        nompie = "";
        canexi = "0";
        canpen = "0";
        det_ima = "";
        inputnumber = 0.0;

        mHeight = "250";
        mimagen2 = null;

        boolinout = "false";
        booleditable = "true";

        //proveedores = null;
        catinventarioview = null;

        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");

    }

    //**************************** Operaciones New ****************************
    public boolean validardatosNuevo() {
        String mQuery = "";
        boolean mvalidar = true;

        if (cod_pie.equals("") || cod_pie.equals("0")) {
            mvalidar = false;
            addMessage("Save", "You have to select a part", 2);
        }
        if (det_can <= 0.0) {
            mvalidar = false;
            addMessage("Save", "You have to enter a Quantity above zero", 2);
        }
        if (!cod_ubi.trim().equals("")) {
            //Accesos macc = new Accesos();
            macc.Conectar();
            mQuery = "select count(cod_his) FROM tbl_pie_his "
                    + "where det_sta = 0"
                    + " and cod_pie =" + cod_pie
                    + " and cod_pai =" + cod_pai
                    + " and cod_bod =" + cod_bod
                    + " and upper(des_ubi) ='" + cod_ubi.trim().toUpperCase() + "';";
            if (!macc.strQuerySQLvariable(mQuery).equals("0")) {
                mvalidar = false;
                addMessage("Save", "This Part - Location already exists", 2);
            }
        } else {
            mvalidar = false;
            addMessage("Save", "You have to enter a Location", 2);
        }
        return mvalidar;
    }

    public void guardarNewLocation() {
        String mQuery = "";
        det_obs = "Creation of new location in stock.";

        if (validardatosNuevo()) {
            try {
                //Accesos mAccesos = new Accesos();
                macc.Conectar();

                mQuery = "select ifnull(max(cod_lis_pie),0)+1 as codigo from tbl_pie;";
                cod_lis_pie = macc.strQuerySQLvariable(mQuery);
                mQuery = "insert into tbl_pie (cod_lis_pie,por_qbo,cod_pai,cod_pro,cod_mov,"
                        + "doc_tra,cod_sol,fec_tra,det_obs,flg_ing,det_sta) "
                        + "values (" + cod_lis_pie + ",'" + por_qbo + "'," + cod_pai + ","
                        + cod_pro + "," + cod_mov + ",'" + doc_tra + "'," + cod_sol + ","
                        + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + ",'"
                        + det_obs + "'," + flg_ing + "," + det_sta + ");";
                macc.dmlSQLvariable(mQuery);

                // ******************* Borra Reservas *****************
                mQuery = " delete from tbl_res where cod_lis_pie = " + cod_lis_pie + ";";
                macc.dmlSQLvariable(mQuery);

                // -----------               mAccesos.dmlSQLvariable(mQuery);
                String mValues = "";

                //*************************************************************************************************************************
                //*********************************************** Manejo del Detalle ******************************************************
                //*************************************************************************************************************************
                //********************************** Existencias ****************************************
                Double mCantidad;
                mCantidad = det_can;
                //código correlativo existencia histórica de artículo
                String cod_cor_exi_art = macc.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                        + "as codigo from tbl_pie_his;");

                //Código correlativo diario existencia histórica de artículo
                String cor_dia = macc.strQuerySQLvariable("select ifnull(max(ord_dia),0)+1 "
                        + "as cordia from tbl_pie_his "
                        + "where "
                        + "cod_pie=" + cod_pie + " "
                        + "and fec_his=STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + cod_bod + " "
                        + "and des_ubi = '" + cod_ubi + "' "
                        + ";");

                //Costo promedio
                Double cPromedio, exisAnt, cunitario, mNuevaExistencia;
                if ("1".equals(cod_cor_exi_art)) {
                    cPromedio = Double.valueOf(det_cos.replace(",", ""));
                    exisAnt = 0.0;
                } else {
                    if ("0".equals(flg_ing)) {
                        cPromedio = macc.doubleQuerySQLvariable("select (ifnull((can_exi*cos_pro),0)+"
                                + (det_can * Double.valueOf(det_cos)) + ")"
                                + "/(IFNULL(can_exi,0)+" + mCantidad + ") as Cpromedio "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + cod_pie + " "
                                + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    } else {
                        cPromedio = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from tbl_pie_his "
                                + "where "
                                + "cod_pie=" + cod_pie + " "
                                + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and flg_ing = 0 "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + "order by fec_his desc,"
                                + "ord_dia desc "
                                + "limit 1;");
                    }
                    //Existencia Anterior
                    exisAnt = macc.doubleQuerySQLvariable("select ifnull(can_exi,0) as exisant "
                            + "from tbl_pie_his "
                            + "where "
                            + "cod_pie=" + cod_pie + " "
                            + "and fec_his <= STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                            + "and det_sta = 0 "
                            + "and cod_pai = " + cod_pai + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + cod_ubi + "' "
                            + "order by fec_his desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //Inserta Registro
                if ("0".equals(flg_ing)) {
                    mNuevaExistencia = (exisAnt + mCantidad);
                } else {
                    mNuevaExistencia = (exisAnt - mCantidad);
                }
                cunitario = Double.valueOf(det_cos.replace(",", ""));
                mQuery = " insert into tbl_pie_his (cod_his,cod_pie,fec_his,ord_dia,flg_ing,"
                        + "cod_enc,cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,cod_usu,cod_pai,cod_bod,des_ubi) "
                        + "VALUES (" + cod_cor_exi_art + "," + cod_pie + ","
                        + "STR_TO_DATE('" + fec_tra + "','%d/%b/%Y')" + "," + cor_dia + "," + flg_ing + ","
                        + cod_lis_pie + ",1," + mCantidad + "," + cunitario + ","
                        + mNuevaExistencia + ","
                        + cPromedio + "," + "0" + "," + "now()" + "," + cbean.getCod_usu() + ","
                        + cod_pai + "," + cod_bod + ",'" + cod_ubi + "');";

                macc.dmlSQLvariable(mQuery);

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción
                String contasiguientes = macc.strQuerySQLvariable("select count(cod_his) "
                        + "from tbl_pie_his where fec_his=STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                        + "and ord_dia >" + cor_dia + " "
                        + "and cod_pie=" + cod_pie + " "
                        + "and det_sta = 0 "
                        + "and cod_pai = " + cod_pai + " "
                        + "and cod_bod = " + cod_bod + " "
                        + "and des_ubi = '" + cod_ubi + "' "
                        + ";");
                contasiguientes = String.valueOf(
                        Integer.valueOf(contasiguientes)
                        + Integer.valueOf(macc.strQuerySQLvariable("select count(cod_his) "
                                + "from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
                                + ";")));

                Double nuevacantidad = mNuevaExistencia;
                if ("0".equals(contasiguientes) == false) {
                    try {
                        historico.clear();

                        ResultSet resvariable;
                        resvariable = macc.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                + "cod_usu from tbl_pie_his "
                                + "where fec_his = STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and ord_dia > " + cor_dia + " "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
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

                        resvariable = macc.querySQLvariable("select cod_his,cod_pie,fec_his,ord_dia,flg_ing,cod_enc,"
                                + "cod_det,det_can,det_cos,can_exi,cos_pro,det_sta,fec_mod,"
                                + "cod_usu from tbl_pie_his "
                                + "where fec_his > STR_TO_DATE('" + fec_tra + "','%d/%b/%Y') "
                                + "and cod_pie=" + cod_pie + " "
                                + "and det_sta = 0 "
                                + "and cod_pai = " + cod_pai + " "
                                + "and cod_bod = " + cod_bod + " "
                                + "and des_ubi = '" + cod_ubi + "' "
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
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en actualización de costos posteriores Agregar New. " + e.getMessage() + " mQuery: " + mQuery);
                    }

                }

                //********************************* Fin Existencias ************************************
                mValues = mValues + "," + "("
                        + cod_lis_pie + ",1,"
                        + cod_pie + ","
                        + cod_bod + ",'"
                        + cod_ubi + "',"
                        + det_can + ","
                        + det_cos + ","
                        + det_sta
                        + ")";

                // ******************* Inserta Detalles*****************
                mValues = mValues.substring(1);
                mQuery = " insert into tbl_pie_det(cod_enc,cod_det,"
                        + "cod_pie,cod_bod,cod_ubi,det_can,det_cos,det_sta) "
                        + "values " + mValues + ";";

                macc.dmlSQLvariable(mQuery);

                macc.Desconectar();
                addMessage("Save", "successful", 1);
            } catch (Exception e) {
                addMessage("Save", "Error when saving data. " + e.getMessage(), 2);
                System.out.println("Error al Guardar New ManInventarioView. " + e.getMessage() + " Query: " + mQuery);
            }
            cod_lis_pie = "";
            por_qbo = "";
            cod_pro = "0";
            cod_mov = "0";
            doc_tra = "";
            cod_sol = "0";
            det_obs = "";
            flg_ing = "0";
            det_sta = "0";
            cod_enc = "";
            cod_det = "";
            cod_pie = "0";
            cod_ubi = "0";
            det_can = 0.0;
            det_cos = "0";
            det_sta2 = "0";
            nompie = "";
            canexi = "0";
            inputnumber = 0.0;

            boolinout = "false";
            booleditable = "true";

            piezas.clear();

            catinventarioview = null;

            RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");
            ejecutarVista();
            RequestContext.getCurrentInstance().execute("PF('wInventarioFinalNew').hide()");

        }
    }

    public void cerrarventanaNew() {
        cod_lis_pie = "";
        por_qbo = "";
        cod_pro = "0";
        cod_mov = "0";
        doc_tra = "";
        cod_sol = "0";
        det_obs = "";
        flg_ing = "0";
        det_sta = "0";
        cod_enc = "";
        cod_det = "";
        cod_pie = "0";
        cod_ubi = "0";
        det_can = 0.0;
        det_cos = "0";
        det_sta2 = "0";
        nompie = "";
        canexi = "0";
        canpen = "0";
        inputnumber = 0.0;

        boolinout = "false";
        booleditable = "true";

        piezas.clear();

        catinventarioview = null;

        ejecutarVista();

        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");

    }

    //**************************** Operaciones Reorden ***********************
    public void cerrarReorden() {
        reo_cod_pai = "0";
        reo_cod_bod = "0";
        reo_cod_ubi = "";
        reo_cod_pie = "0";
        reo_pun_reo = 0.0;

        catpuntosreorden = null;
        puntos.clear();
    }

    public boolean validardatosReorden() {
        boolean mvalidar = true;

        if (reo_cod_pie.equals("") || reo_cod_pie.equals("0")) {
            mvalidar = false;
            addMessage("Save", "You have to select a part", 2);
        }

        if (reo_cod_pai.equals("") || reo_cod_pai.equals("0")) {
            mvalidar = false;
            addMessage("Save", "You have to select a Country", 2);
        }

        if (reo_cod_bod.equals("") || reo_cod_bod.equals("0")) {
            mvalidar = false;
            addMessage("Save", "You have to select a Warehouse", 2);
        }

        if (reo_cod_ubi.equals("0.")) {
            mvalidar = false;
            addMessage("Save", "You have to enter a Location", 2);
        }

        if (reo_pun_reo <= 0.0) {
            mvalidar = false;
            addMessage("Save", "You have to enter a Reorder Level above zero", 2);
        }

        return mvalidar;
    }

    public void guardarReorden() {
        String mQuery = "";
        if (validardatosReorden()) {
            try {
                macc.Conectar();
                mQuery = "select count(pun_reo) from cat_pie_reo "
                        + "where cod_pai = " + reo_cod_pai + " "
                        + "and cod_bod = " + reo_cod_bod + " "
                        + "and cod_ubi ='" + reo_cod_ubi + "' "
                        + "and cod_pie = " + reo_cod_pie + ";";

                if ("0".equals(macc.strQuerySQLvariable(mQuery))) {
                    mQuery = "insert into cat_pie_reo (cod_pai,cod_bod,cod_ubi,cod_pie,pun_reo) "
                            + "VALUES (" + reo_cod_pai + "," + reo_cod_bod + ",'" + reo_cod_ubi + "'," + reo_cod_pie + "," + reo_pun_reo + ");";
                } else {
                    mQuery = "update cat_pie_reo SET "
                            + "pun_reo = " + reo_pun_reo + " "
                            + "WHERE cod_pai = " + reo_cod_pai + " "
                            + "and cod_bod =  " + reo_cod_bod + " "
                            + "and cod_ubi = '" + reo_cod_ubi + "' "
                            + "and cod_pie =  " + reo_cod_pie + " "
                            + ";";
                }
                macc.dmlSQLvariable(mQuery);

                macc.Desconectar();
                addMessage("Save Reorder Level", "successful", 1);
            } catch (Exception e) {
                addMessage("Save Reorder Level", "Error when saving data. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Artículos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarReorden();
        }
        reo_cod_bod = "0";
        reo_cod_ubi = "0.";
        reo_pun_reo = 0.0;

        catpuntosreorden = null;

    }

    public void eliminarReorden() {
        String mQuery = "";
        macc.Conectar();

        if ("0".equals(reo_cod_pai) || "0".equals(reo_cod_bod) || "0.".equals(reo_cod_ubi) || "0".equals(reo_cod_pie)) {
            addMessage("Delete Reorder Level", "You have to select a Record", 2);
        } else {
            try {
                mQuery = "delete from cat_pie_reo "
                        + "where cod_pai = " + reo_cod_pai + " "
                        + "and cod_bod = " + reo_cod_bod + " "
                        + "and cod_ubi ='" + reo_cod_ubi + "' "
                        + "and cod_pie = " + reo_cod_pie + ";";

                macc.dmlSQLvariable(mQuery);
                addMessage("Delete Reorder Level", "Information deleted successfully", 1);
            } catch (Exception e) {
                addMessage("Delete Reorder Level", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Punto Reorden. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarReorden();
            reo_cod_bod = "0";
            reo_cod_ubi = "0.";
            reo_pun_reo = 0.0;
        }
        macc.Desconectar();

    }

    //********************* Tratamiento Imágenes *****************************
    public void recuperarimagen() {
        String mQuery = "";
        macc.Conectar();
        mimagen = null;
        try {
            mQuery = "select det_ima from cat_pie_img where cod_pie=" + pie_cod_pie + ";";
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mwidth = String.valueOf(Double.valueOf(img.getWidth()) / Double.valueOf(img.getHeight()) * 140.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from cat_pie_img where cod_pie=0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mwidth = String.valueOf(Double.valueOf(img.getWidth()) / Double.valueOf(img.getHeight()) * 140.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            System.out.println("Error en recuperarimagen ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);

        }

        macc.Desconectar();
    }

    public void upload(FileUploadEvent event) {
        try {
            Random rnd = new Random();
            String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));
            copyFile("pie_temp_" + prefijo + ".jpg", event.getFile().getInputstream());

            mimagen = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
            mwidth = "140";

        } catch (Exception e) {
            addMessage("Upload Image", "File " + event.getFile().getFileName() + " can´t be uploaded. " + e.getMessage(), 2);
            System.out.println("Error en subir archivo Producto." + e.getMessage());
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            pie_det_ima = "/resources/images/temp/" + fileName;

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
            pie_det_ima = "";
            addMessage("Upload Image", "Image " + fileName + " can´t be copied. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    public void recuperarimagenEdit() {
        String mQuery = "";
        macc.Conectar();
        mimagen2 = null;
        try {
            mQuery = "select det_ima from cat_pie_img where cod_pie = " + cod_pie + ";";
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mHeight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 250.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from cat_pie_img where cod_pie = 0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mHeight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 250.0);
                img = null;

                data = null;
            }

        } catch (Exception e) {
            System.out.println("Error en recuperarimagenEdit ManInventarioNew. " + e.getMessage() + " Query: " + mQuery);

        }

        macc.Desconectar();
    }

    //**************************** Funciones Varias ***************************
    public void onRowSelect(SelectEvent event) {
        cod_pie = ((CatInventarioView) event.getObject()).getCategoria();
        cod_ubi = ((CatInventarioView) event.getObject()).getEquipo();
        canexi = ((CatInventarioView) event.getObject()).getExistencia();
        nompie = ((CatInventarioView) event.getObject()).getReferencia() + " " + ((CatInventarioView) event.getObject()).getNombre();
        canpen = ((CatInventarioView) event.getObject()).getPendiente();

    }

    public void onRowSelectEditPart(SelectEvent event) {
        pie_cod_pie = ((CatPiezas) event.getObject()).getCod_pie();
        pie_cod_ref = ((CatPiezas) event.getObject()).getCod_ref();
        pie_cod_equ = ((CatPiezas) event.getObject()).getCod_equ();
        pie_nom_pie = ((CatPiezas) event.getObject()).getNom_pie();
        pie_des_pie = ((CatPiezas) event.getObject()).getDes_pie();
        pie_cod_cat = ((CatPiezas) event.getObject()).getCod_cat();
        pie_det_ima = ((CatPiezas) event.getObject()).getDet_ima();
        pie_vid_uti = Double.valueOf(((CatPiezas) event.getObject()).getVid_uti());
        pie_det_pre = Double.valueOf(((CatPiezas) event.getObject()).getCod_gru());
        pie_cod_lin = ((CatPiezas) event.getObject()).getCod_lin();

        recuperarimagen();

        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinalEditPart').clearFilters()");
        RequestContext.getCurrentInstance().execute("PF('wInventarioFinalEditPart').hide()");

    }

    public void onRowSelectReorder(SelectEvent event) {
        reo_cod_pai = ((CatPuntosReorden) event.getObject()).getCod_pai();
        llenarBodegasReo();
        reo_cod_bod = ((CatPuntosReorden) event.getObject()).getCod_bod();
        llenarUbicacionesReo();
        reo_cod_ubi = ((CatPuntosReorden) event.getObject()).getCod_ubi();
        reo_cod_pie = ((CatPuntosReorden) event.getObject()).getCod_pie();
        reo_pun_reo = Double.valueOf(((CatPuntosReorden) event.getObject()).getPun_reo());

    }

    public void onSeleccionarpais() {
        llenarBodegas();
        cod_bod = "0";
        catinventarioview = null;
        vista.clear();
        nuevapieza();
        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");
    }

    public void onSeleccionarpaisreo() {
        llenarBodegasReo();
        reo_cod_bod = "0";
        llenarReorden();
    }

    public void onSeleccionarbod() {
        ejecutarVista();
        nuevapieza();
        RequestContext.getCurrentInstance().execute("PF('wvInventarioFinal').clearFilters()");
        tabacordion = "1";
    }

    public void dateSelectedAction(SelectEvent f) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        Date date = (Date) f.getObject();
        fec_tra = format.format(date);
        cod_bod = "0";
        catinventarioview = null;
        vista.clear();
        piezas.clear();

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

    //******************** Getters y Setters ********************************
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

    public List<CatProveedores> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<CatProveedores> proveedores) {
        this.proveedores = proveedores;
    }

    public List<CatPiezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatPiezas> piezas) {
        this.piezas = piezas;
    }

    public List<CatHistorico> getHistorico() {
        return historico;
    }

    public void setHistorico(List<CatHistorico> historico) {
        this.historico = historico;
    }

    public CatInventarioView getCatinventarioview() {
        return catinventarioview;
    }

    public void setCatinventarioview(CatInventarioView catinventarioview) {
        this.catinventarioview = catinventarioview;
    }

    public List<CatInventarioView> getVista() {
        return vista;
    }

    public void setVista(List<CatInventarioView> vista) {
        this.vista = vista;
    }

    public String getCod_lis_pie() {
        return cod_lis_pie;
    }

    public void setCod_lis_pie(String cod_lis_pie) {
        this.cod_lis_pie = cod_lis_pie;
    }

    public String getPor_qbo() {
        return por_qbo;
    }

    public void setPor_qbo(String por_qbo) {
        this.por_qbo = por_qbo;
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

    public String getCod_mov() {
        return cod_mov;
    }

    public void setCod_mov(String cod_mov) {
        this.cod_mov = cod_mov;
    }

    public String getDoc_tra() {
        return doc_tra;
    }

    public void setDoc_tra(String doc_tra) {
        this.doc_tra = doc_tra;
    }

    public String getCod_sol() {
        return cod_sol;
    }

    public void setCod_sol(String cod_sol) {
        this.cod_sol = cod_sol;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFlg_ing() {
        return flg_ing;
    }

    public void setFlg_ing(String flg_ing) {
        this.flg_ing = flg_ing;
    }

    public String getDet_sta() {
        return det_sta;
    }

    public void setDet_sta(String det_sta) {
        this.det_sta = det_sta;
    }

    public String getCod_enc() {
        return cod_enc;
    }

    public void setCod_enc(String cod_enc) {
        this.cod_enc = cod_enc;
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

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getCod_ubi() {
        return cod_ubi;
    }

    public void setCod_ubi(String cod_ubi) {
        this.cod_ubi = cod_ubi;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public String getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(String det_cos) {
        this.det_cos = det_cos;
    }

    public String getDet_sta2() {
        return det_sta2;
    }

    public void setDet_sta2(String det_sta2) {
        this.det_sta2 = det_sta2;
    }

    public String getNompie() {
        return nompie;
    }

    public void setNompie(String nompie) {
        this.nompie = nompie;
    }

    public String getDet_ima() {
        return det_ima;
    }

    public void setDet_ima(String det_ima) {
        this.det_ima = det_ima;
    }

    public String getBoolinout() {
        return boolinout;
    }

    public void setBoolinout(String boolinout) {
        this.boolinout = boolinout;
    }

    public String getBooleditable() {
        return booleditable;
    }

    public String getCanexi() {
        return canexi;
    }

    public void setCanexi(String canexi) {
        this.canexi = canexi;
    }

    public Double getInputnumber() {
        return inputnumber;
    }

    public void setInputnumber(Double inputnumber) {
        this.inputnumber = inputnumber;
    }

    public void setBooleditable(String booleditable) {
        this.booleditable = booleditable;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

    public List<CatListados> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatListados> equipos) {
        this.equipos = equipos;
    }

    public CatPiezas getCatpiezas() {
        return catpiezas;
    }

    public void setCatpiezas(CatPiezas catpiezas) {
        this.catpiezas = catpiezas;
    }

    public String getPie_cod_pie() {
        return pie_cod_pie;
    }

    public void setPie_cod_pie(String pie_cod_pie) {
        this.pie_cod_pie = pie_cod_pie;
    }

    public String getPie_cod_ref() {
        return pie_cod_ref;
    }

    public void setPie_cod_ref(String pie_cod_ref) {
        this.pie_cod_ref = pie_cod_ref;
    }

    public String getPie_cod_equ() {
        return pie_cod_equ;
    }

    public void setPie_cod_equ(String pie_cod_equ) {
        this.pie_cod_equ = pie_cod_equ;
    }

    public String getPie_nom_pie() {
        return pie_nom_pie;
    }

    public void setPie_nom_pie(String pie_nom_pie) {
        this.pie_nom_pie = pie_nom_pie;
    }

    public String getPie_des_pie() {
        return pie_des_pie;
    }

    public void setPie_des_pie(String pie_des_pie) {
        this.pie_des_pie = pie_des_pie;
    }

    public String getPie_cod_cat() {
        return pie_cod_cat;
    }

    public void setPie_cod_cat(String pie_cod_cat) {
        this.pie_cod_cat = pie_cod_cat;
    }

    public String getPie_det_ima() {
        return pie_det_ima;
    }

    public void setPie_det_ima(String pie_det_ima) {
        this.pie_det_ima = pie_det_ima;
    }

    public Double getPie_vid_uti() {
        return pie_vid_uti;
    }

    public void setPie_vid_uti(Double pie_vid_uti) {
        this.pie_vid_uti = pie_vid_uti;
    }

    public Double getPie_det_pre() {
        return pie_det_pre;
    }

    public void setPie_det_pre(Double pie_det_pre) {
        this.pie_det_pre = pie_det_pre;
    }

    public String getPie_cod_lin() {
        return pie_cod_lin;
    }

    public void setPie_cod_lin(String pie_cod_lin) {
        this.pie_cod_lin = pie_cod_lin;
    }

    public DefaultStreamedContent getMimagen() {
        return mimagen;
    }

    public void setMimagen(DefaultStreamedContent mimagen) {
        ManInventarioNew.mimagen = mimagen;
    }

    public String getMwidth() {
        return mwidth;
    }

    public void setMwidth(String mwidth) {
        this.mwidth = mwidth;
    }

    public DefaultStreamedContent getMimagen2() {
        return mimagen2;
    }

    public void setMimagen2(DefaultStreamedContent mimagen2) {
        ManInventarioNew.mimagen2 = mimagen2;
    }

    public String getmHeight() {
        return mHeight;
    }

    public void setmHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getReo_cod_pai() {
        return reo_cod_pai;
    }

    public void setReo_cod_pai(String reo_cod_pai) {
        this.reo_cod_pai = reo_cod_pai;
    }

    public String getReo_cod_bod() {
        return reo_cod_bod;
    }

    public void setReo_cod_bod(String reo_cod_bod) {
        this.reo_cod_bod = reo_cod_bod;
    }

    public String getReo_cod_ubi() {
        return reo_cod_ubi;
    }

    public void setReo_cod_ubi(String reo_cod_ubi) {
        this.reo_cod_ubi = reo_cod_ubi;
    }

    public String getReo_cod_pie() {
        return reo_cod_pie;
    }

    public void setReo_cod_pie(String reo_cod_pie) {
        this.reo_cod_pie = reo_cod_pie;
    }

    public Double getReo_pun_reo() {
        return reo_pun_reo;
    }

    public void setReo_pun_reo(Double reo_pun_reo) {
        this.reo_pun_reo = reo_pun_reo;
    }

    public List<CatListados> getBodegasreo() {
        return bodegasreo;
    }

    public void setBodegasreo(List<CatListados> bodegasreo) {
        this.bodegasreo = bodegasreo;
    }

    public List<CatListados> getUbicacionesreo() {
        return ubicacionesreo;
    }

    public void setUbicacionesreo(List<CatListados> ubicacionesreo) {
        this.ubicacionesreo = ubicacionesreo;
    }

    public CatPuntosReorden getCatpuntosreorden() {
        return catpuntosreorden;
    }

    public void setCatpuntosreorden(CatPuntosReorden catpuntosreorden) {
        this.catpuntosreorden = catpuntosreorden;
    }

    public List<CatPuntosReorden> getPuntos() {
        return puntos;
    }

    public void setPuntos(List<CatPuntosReorden> puntos) {
        this.puntos = puntos;
    }

    public List<CatListados> getUbicacionesdestino() {
        return ubicacionesdestino;
    }

    public void setUbicacionesdestino(List<CatListados> ubicacionesdestino) {
        this.ubicacionesdestino = ubicacionesdestino;
    }

    public String getBoddestino() {
        return boddestino;
    }

    public void setBoddestino(String boddestino) {
        this.boddestino = boddestino;
    }

    public String getUbidestino() {
        return ubidestino;
    }

    public void setUbidestino(String ubidestino) {
        this.ubidestino = ubidestino;
    }

    public String getTabacordion() {
        return tabacordion;
    }

    public void setTabacordion(String tabacordion) {
        this.tabacordion = tabacordion;
    }

    public String getDeletedpart() {
        return deletedpart;
    }

    public void setDeletedpart(String deletedpart) {
        this.deletedpart = deletedpart;
    }

}
