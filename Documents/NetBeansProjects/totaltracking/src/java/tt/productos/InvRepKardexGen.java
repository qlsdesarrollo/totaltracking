package tt.productos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped
public class InvRepKardexGen implements Serializable {

    private static final long serialVersionUID = 8411654786747168L;
    @Inject
    Login cbean;
    private Accesos macc;
    private String nombrereporte, nombreexportar;
    private String fec_ini, fec_fin, cod_pai, tipkardex, cod_mar, cod_fam, cod_art;
    private Date dfechaini, dfechafin;
    private List<CatPaises> paises;
    private List<CatListados> bodegas;
    private List<CatListados> tipodoc;
    private List<CatListados> filtro;

    private List<CatListados> marcas;
    private List<CatListados> familias;
    private List<CatListados> articulos;

    private List<CatListados> clientes;

    private List<CatRepConsolidado> consolidado;

    private Map<String, Object> parametros;

    private String mcodfiltro, enabledfiltro, cod_bod;
    private int diasven;

    public InvRepKardexGen() {
    }

    public void iniciarVentana() {
        macc = new Accesos();
        mcodfiltro = "0";
        tipkardex = "general";
        enabledfiltro = "false";
        cod_bod = "0";

        cod_mar = "0";
        cod_fam = "0";
        cod_art = "0";

        parametros = new HashMap<>();
        diasven = 15;
        cod_pai = cbean.getCod_pai();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        dfechaini = Date.from(Instant.now());
        dfechafin = Date.from(Instant.now());
        fec_ini = format.format(dfechaini);
        fec_fin = format.format(dfechafin);

        paises = new ArrayList<>();
        bodegas = new ArrayList<>();
        tipodoc = new ArrayList<>();
        filtro = new ArrayList<>();

        marcas = new ArrayList<>();
        familias = new ArrayList<>();
        articulos = new ArrayList<>();

        clientes = new ArrayList<>();

        consolidado = new ArrayList<>();

        llenarPaises();
        llenarBodegas();
        llenarTipodoc();
        llenarFiltro();

        llenarMarcas();
        llenarFamilias();
        llenarArticulos();

        llenarClientes();
    }

    public void cerrarventana() {
        tipkardex = null;
        enabledfiltro = null;
        mcodfiltro = null;
        cod_bod = null;
        parametros = null;
        cod_pai = null;
        dfechaini = null;
        dfechafin = null;
        fec_ini = null;
        fec_fin = null;
        diasven = 0;

        cod_mar = null;
        cod_fam = null;
        cod_art = null;

        paises = null;
        bodegas = null;
        tipodoc = null;
        filtro = null;

        marcas = null;
        familias = null;
        articulos = null;

        clientes = null;

        consolidado = null;

        macc = null;
    }

    public void llenarPaises() {
        try {

            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by cod_pai;";
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
            System.out.println("Error en el llenado de Paises en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void llenarBodegas() {
        try {

            bodegas.clear();

            String mQuery = "select id_bod, nom_bod "
                    + "from inv_cat_bodegas where cod_pai = " + cod_pai + " order by nom_bod;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void llenarTipodoc() {
        try {

            tipodoc.clear();

            String mQuery = "select id_mov, concat(case flg_tip when 0 then 'Entrada ' when 1 then 'Salida: ' end, nom_mov) as nom "
                    + "from inv_cat_mov where cod_pai = " + cod_pai + " order by flg_tip, nom_mov;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tipodoc.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Tipos Documento en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void llenarFiltro() {
        String mQuery = "";
        try {
            filtro.clear();

            switch (tipkardex) {
                case "general":
                    mQuery = "select art.id_art, concat(ifnull(art.cod_art,''),' - ',ifnull(art.cod_alt,''),' - ', ifnull(art.det_nom,'')) as art "
                            + "from inv_cat_articulo as art "
                            + "where art.cod_pai = " + cod_pai + " "
                            + "order by art.cod_art; ";
                    enabledfiltro = "false";
                    mcodfiltro = "0";
                    break;
                case "lote":
                    mQuery = "";
                    enabledfiltro = "true";
                    mcodfiltro = "";
                    break;
                case "documento":
                    mQuery = "";
                    enabledfiltro = "true";
                    mcodfiltro = "";
                    break;
                case "cliente":
                    mQuery = "select cod_cli, nom_cli from inv_cat_cli where cod_pai = " + cod_pai + " order by nom_cli;";
                    enabledfiltro = "false";
                    mcodfiltro = "0";
                    break;
                case "proveedor":
                    mQuery = "select cod_pro, nom_pro from inv_cat_pro where cod_pai = " + cod_pai + " order by nom_pro;";
                    enabledfiltro = "false";
                    mcodfiltro = "0";
                    break;
            }

            if (!"".equals(mQuery)) {
                ResultSet resVariable;
                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    filtro.add(new CatListados(
                            resVariable.getString(1),
                            resVariable.getString(2)
                    ));
                }
                resVariable.close();
                macc.Desconectar();
            }

        } catch (Exception e) {
            System.out.println("Error en el llenado de Filtro en  InvRepKardexGen. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMarcas() {
        try {

            marcas.clear();

            String mQuery = "select id_mar, nom_mar "
                    + "from inv_cat_mar order by nom_mar;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                marcas.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Marcas en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void llenarFamilias() {
        try {

            familias.clear();

            String mQuery = "select id_fam, nom_fam "
                    + "from inv_cat_familia order by nom_fam;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                familias.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Familias en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void llenarArticulos() {
        try {

            articulos.clear();

            String mQuery = "select id_art, concat(ifnull(cod_art,''),'--',ifnull(cod_alt,''),'--',ifnull(det_nom,'')) as art "
                    + "from inv_cat_articulo "
                    + "where cod_pai = " + cod_pai + " "
                    + "order by cod_art;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                articulos.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Artículos en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void llenarClientes() {
        try {

            clientes.clear();

            String mQuery = "select cod_cli, nom_cli "
                    + "from inv_cat_cli "
                    + "where cod_pai = " + cod_pai + " "
                    + "order by nom_cli;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Clientes en  InvRepKardexGen. " + e.getMessage());
        }
    }

    public void CSVConsolidado() {
        try {
            String mConsolidado = "", mQuery = "";
            int mContaCols = 0, mSeparaNom = 0;
            consolidado.clear();

            mQuery = "call inv_rep_consolidado('" + fec_ini + "', " + cod_pai + ", '";

            if (!"0".equals(cod_mar)) {
                mQuery = " and art.det_mar = " + cod_mar + " ";
            }

            if (!"0".equals(cod_fam)) {
                mQuery = mQuery + " and art.det_fam = " + cod_fam + " ";
            }

            if (!"0".equals(cod_art)) {
                mQuery = mQuery + " and art.id_art = " + cod_art + " ";
            }

            mQuery = mQuery + "');";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                consolidado.add(new CatRepConsolidado(
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

            for (int i = 0; i < consolidado.size(); i++) {
                String[] parts = consolidado.get(i).getLotes().split("/-/");
                if (parts.length > mContaCols) {
                    mContaCols = parts.length;
                }
            }
            mConsolidado = "Marca,Plataforma,Código,Nombre,Unid.Medida,Cotizado,Orden,Vale,Cambio,Cantidad_Transito,Existencia";

            if (mContaCols > 0) {
                for (int i = 1; i < mContaCols; i++) {
                    switch (mSeparaNom) {
                        case 0:
                            mConsolidado = mConsolidado + ",Detalle_Existencia_Lote";
                            mSeparaNom = 1;
                            break;
                        case 1:
                            mConsolidado = mConsolidado + ",Lote";
                            mSeparaNom = 2;
                            break;
                        case 2:
                            mConsolidado = mConsolidado + ",Fecha_Vencimiento";
                            mSeparaNom = 0;
                            break;
                    }

                }
            }

            mConsolidado = mConsolidado + " \n";

            //System.out.println("Encabezado: " + mConsolidado);
            try {
                File mXMLFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                String outputFile = mXMLFile.getPath().replace("config.xml", "") + "consolidado.csv";
                FileWriter fw = new FileWriter(outputFile);

                boolean alreadyExists = new File(outputFile).exists();

                if (alreadyExists) {
                    File ArchivoEmpleados = new File(outputFile);
                    ArchivoEmpleados.delete();
                    ArchivoEmpleados = null;
                }

                //System.out.println("Creación de archivo");
                fw.append(mConsolidado);
                for (int i = 0; i < consolidado.size(); i++) {
                    mConsolidado = consolidado.get(i).getNom_mar() + "," + consolidado.get(i).getNom_fam() + "," + consolidado.get(i).getCod_art() + ","
                            + consolidado.get(i).getDet_nom() + "," + consolidado.get(i).getNom_emb() + "," + consolidado.get(i).getCan_pen_val() + "," + consolidado.get(i).getCan_pen_ord_com() + ","
                            + consolidado.get(i).getCan_pen_cot() + "," + consolidado.get(i).getCan_pen_rep() + "," + consolidado.get(i).getTransito() + "," + consolidado.get(i).getExi_act_tot();

                    if (consolidado.get(i).getLotes().equals("") || consolidado.get(i).getLotes() == null) {
                        for (int j = 1; j < mContaCols; j++) {
                            mConsolidado = mConsolidado + ", ";
                        }
                    } else {
                        String[] parts = consolidado.get(i).getLotes().split("/-/");

                        for (int j = 1; j < mContaCols; j++) {

                            if (j < parts.length) {
                                mConsolidado = mConsolidado + ", " + parts[j].replace("Exis:", "").replace("Lote:", "").replace("FV:", "");
                            } else {
                                mConsolidado = mConsolidado + ", ";
                            }
                        }
                    }

                    mConsolidado = mConsolidado.replace("\n", "") + " \n ";
                    fw.append(mConsolidado);

                }

                fw.flush();
                fw.close();

                try {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("window.open('" + "/totaltracking/faces/resources/images/temp/consolidado.csv', '_blank')");
                } catch (Exception e) {
                    System.out.println("Error en redireccionar a descarga CSV en InvRepKardexGen. " + e.getMessage());
                }

                mXMLFile = null;
                fw = null;
                outputFile = null;

            } catch (IOException ex) {
                System.out.println("Error en Crear CSV de Consolidado en InvRepKardexGen. " + ex.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en CSV de Consolidado en InvRepKardexGen. " + e.getMessage());
        }
    }

    public void XLSConsolidado() {
        try {
            String mQuery = "";
            int mContaCols = 0, mSeparaNom = 0;
            consolidado.clear();

            mQuery = "call inv_rep_consolidado('" + fec_ini + "', " + cod_pai + ", '";

            if (!"0".equals(cod_mar)) {
                mQuery = " and art.det_mar = " + cod_mar + " ";
            }

            if (!"0".equals(cod_fam)) {
                mQuery = mQuery + " and art.det_fam = " + cod_fam + " ";
            }

            if (!"0".equals(cod_art)) {
                mQuery = mQuery + " and art.id_art = " + cod_art + " ";
            }

            mQuery = mQuery + "');";

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                consolidado.add(new CatRepConsolidado(
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

            for (int i = 0; i < consolidado.size(); i++) {
                String[] parts = consolidado.get(i).getLotes().split("/-/");
                if (parts.length > mContaCols) {
                    mContaCols = parts.length;
                }
            }

            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("Datos Exportados");
            HSSFRow row = (HSSFRow) sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Marca");
            cell = row.createCell(1);
            cell.setCellValue("Plataforma");
            cell = row.createCell(2);
            cell.setCellValue("Código");
            cell = row.createCell(3);
            cell.setCellValue("Nombre");
            cell = row.createCell(4);
            cell.setCellValue("Unid.Medida");
            cell = row.createCell(5);
            cell.setCellValue("Cotizado");
            cell = row.createCell(6);
            cell.setCellValue("Orden");
            cell = row.createCell(7);
            cell.setCellValue("Vale");
            cell = row.createCell(8);
            cell.setCellValue("Cambio");
            cell = row.createCell(9);
            cell.setCellValue("Cantidad_Transito");
            cell = row.createCell(10);
            cell.setCellValue("Existencia");

            if (mContaCols > 0) {
                for (int i = 1; i < mContaCols; i++) {
                    switch (mSeparaNom) {
                        case 0:
                            cell = row.createCell(10 + i);
                            cell.setCellValue("Detalle_Existencia_Lote");
                            mSeparaNom = 1;
                            break;
                        case 1:
                            cell = row.createCell(10 + i);
                            cell.setCellValue("Lote");
                            mSeparaNom = 2;
                            break;
                        case 2:
                            cell = row.createCell(10 + i);
                            cell.setCellValue("Fecha_Vencimiento");
                            mSeparaNom = 0;
                            break;
                    }

                }
            }

            try {
                File mXMLFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                String outputFile = mXMLFile.getPath().replace("config.xml", "") + "consolidado.xls";

                boolean alreadyExists = new File(outputFile).exists();

                if (alreadyExists) {
                    File ArchivoEmpleado = new File(outputFile);
                    ArchivoEmpleado.delete();
                    ArchivoEmpleado = null;
                }

                for (int i = 0; i < consolidado.size(); i++) {
                    row = (HSSFRow) sheet.createRow(i + 1);

                    cell = row.createCell(0);
                    cell.setCellValue(consolidado.get(i).getNom_mar());
                    cell = row.createCell(1);
                    cell.setCellValue(consolidado.get(i).getNom_fam());
                    cell = row.createCell(2);
                    cell.setCellValue(consolidado.get(i).getCod_art());
                    cell = row.createCell(3);
                    cell.setCellValue(consolidado.get(i).getDet_nom());
                    cell = row.createCell(4);
                    cell.setCellValue(consolidado.get(i).getNom_emb());
                    cell = row.createCell(5);
                    cell.setCellValue(consolidado.get(i).getCan_pen_val());
                    cell = row.createCell(6);
                    cell.setCellValue(consolidado.get(i).getCan_pen_ord_com());
                    cell = row.createCell(7);
                    cell.setCellValue(consolidado.get(i).getCan_pen_cot());
                    cell = row.createCell(8);
                    cell.setCellValue(consolidado.get(i).getCan_pen_rep());
                    cell = row.createCell(9);
                    cell.setCellValue(consolidado.get(i).getTransito());
                    cell = row.createCell(10);
                    cell.setCellValue(consolidado.get(i).getExi_act_tot());

                    if (consolidado.get(i).getLotes().equals("") || consolidado.get(i).getLotes() == null) {

                    } else {
                        String[] parts = consolidado.get(i).getLotes().split("/-/");
                        for (int j = 1; j < mContaCols; j++) {
                            if (j < parts.length) {
                                cell = row.createCell(10 + j);
                                cell.setCellValue(parts[j].replace("Exis:", "").replace("Lote:", "").replace("FV:", ""));
                            }
                        }
                    }
                }

                // Write the output to a file
                FileOutputStream fileOut = new FileOutputStream(outputFile);
                wb.write(fileOut);
                fileOut.close();

                try {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("window.open('" + "/totaltracking/faces/resources/images/temp/consolidado.xls', '_blank')");
                } catch (Exception e) {
                    System.out.println("Error en redireccionar a descarga XLS en InvRepKardexGen. " + e.getMessage());
                }

                mXMLFile = null;
                outputFile = null;

            } catch (IOException ex) {
                System.out.println("Error en Crear XLS de Consolidado en InvRepKardexGen. " + ex.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en XLS de Consolidado en InvRepKardexGen. " + e.getMessage());
        }
    }

    public void ejecutarreporte(int nreporte, int ntipo) {
        try {
            switch (nreporte) {
                case 0:
                    paramKardex();
                    break;
                case 1:
                    paramRepVarios();
                    break;
                case 3:
                    paramRepTipDoc();
                    break;
                case 4:
                    paramRepProxVen();
                    break;
                case 5:
                    paramRepExistencias();
                    break;
                case 6:
                    paramRepEntradaSalidaPro();
                    break;
                case 7:
                    paramRepTipDocSinCosto();
                    break;
                case 8:
                    paramRepMovPorCliente();
                    break;
                case 9:
                    paramRepConsolidado();
                    break;
                case 10:
                    paramRepPendientes();
                    break;
            }

            switch (ntipo) {
                case 1:
                    verPDF();
                    break;
                case 2:
                    exportarPDF();
                    break;
                case 3:
                    exportarXLS();
                    break;
                case 4:
                    exportarDOC();
                    break;
                case 5:
                    exportarPPT();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte" + e.getMessage());
        }
    }

    public void paramKardex() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);
        parametros.put("fecfin", fec_fin);

        String miquery = "";
        if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim())) {
            miquery = miquery + " and det.cod_art =" + mcodfiltro + " ";
        }

        if ("".equals(miquery)) {
            parametros.put("query", "");
        } else {
            parametros.put("query", miquery);
        }

        nombrereporte = "/reportes/inv_kardex_total.jasper";
        nombreexportar = "KardexProductos";
    }

    public void paramRepVarios() {
        parametros.clear();
        parametros.put("fechaini", fec_ini);
        parametros.put("fechafin", fec_fin);
        parametros.put("codpai", cod_pai);
        nombrereporte = "/reportes/inv_entradassalidas.jasper";
        if (mcodfiltro == null) {
            mcodfiltro = "";
        }
        switch (tipkardex) {
            case "general":
                nombreexportar = "entradasysalidasProducto";
                if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim()) && mcodfiltro != null) {
                    parametros.put("query", " and det.cod_art=" + mcodfiltro + " ");
                } else {
                    parametros.put("query", "");
                }
                break;
            case "lote":
                nombreexportar = "entradasysalidasLote";
                if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim()) && mcodfiltro != null) {
                    parametros.put("query", " and det.det_lot='" + mcodfiltro + "' ");
                } else {
                    parametros.put("query", " and mae.cod_pai <> 0 ");
                }
                break;
            case "documento":
                nombreexportar = "entradasysalidasDocumento";
                if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim()) && mcodfiltro != null) {
                    parametros.put("query", " and mae.doc_tra='" + mcodfiltro + "' ");
                } else {
                    parametros.put("query", "");
                }
                break;
            case "cliente":
                nombreexportar = "entradasysalidasCliente";
                if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim()) && mcodfiltro != null) {
                    parametros.put("query", " and mae.cod_cli_pro=" + mcodfiltro + " and mae.flg_ing_sal = 'true' ");
                } else {
                    parametros.put("query", "");
                }
                break;
            case "proveedor":
                nombreexportar = "entradasysalidasProveedor";
                if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim()) && mcodfiltro != null) {
                    parametros.put("query", " and mae.cod_cli_pro=" + mcodfiltro + " and mae.flg_ing_sal = 'false' ");
                } else {
                    parametros.put("query", "");
                }
                break;
        }

    }

    public void paramRepTipDoc() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);
        parametros.put("fecfin", fec_fin);
        if (!"0".equals(mcodfiltro)) {
            parametros.put("query", " and mae.tip_mov = " + mcodfiltro + " ");
        } else {
            parametros.put("query", "");
        }

        nombrereporte = "/reportes/inv_tipdoc.jasper";
        nombreexportar = "InformeTipoDocumento";
    }

    public void paramRepProxVen() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fectra", fec_ini);
        parametros.put("dias", diasven);
        parametros.put("codbod", cod_bod);
        nombrereporte = "/reportes/inv_proxvencer.jasper";
        nombreexportar = "ProductosPróximosVencer";
    }

    public void paramRepExistencias() {
        String mQuery = "";
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);

        if (!"0".equals(cod_mar)) {
            mQuery = " and art.det_mar = " + cod_mar + " ";
        }

        if (!"0".equals(cod_fam)) {
            mQuery = mQuery + " and art.det_fam = " + cod_fam + " ";
        }

        if (!"0".equals(cod_art)) {
            mQuery = mQuery + " and art.id_art = " + cod_art + " ";
        }

        parametros.put("query", mQuery);
        nombrereporte = "/reportes/inv_existencias.jasper";
        nombreexportar = "Existencias";
    }

    public void paramRepEntradaSalidaPro() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);
        parametros.put("fecfin", fec_fin);

        String miquery = "";
        if (!"".equals(mcodfiltro.trim()) && !"0".equals(mcodfiltro.trim())) {
            miquery = miquery + " and det.cod_art =" + mcodfiltro + " ";
        }

        if ("".equals(miquery)) {
            parametros.put("query", "");
        } else {
            parametros.put("query", miquery);
        }

        nombrereporte = "/reportes/inv_ent_sal.jasper";
        nombreexportar = "Entrada_Salida_Productos";
    }

    public void paramRepTipDocSinCosto() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);
        parametros.put("fecfin", fec_fin);
        if (!"0".equals(mcodfiltro)) {
            parametros.put("query", " and mae.tip_mov = " + mcodfiltro + " ");
        } else {
            parametros.put("query", "");
        }

        nombrereporte = "/reportes/inv_tipdocsincosto.jasper";
        nombreexportar = "MovimientosPorTipoDocumento";
    }

    public void paramRepMovPorCliente() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);
        parametros.put("fecfin", fec_fin);
        if (!"0".equals(mcodfiltro)) {
            parametros.put("query", " and mae.cod_cli_pro = " + mcodfiltro + " ");
        } else {
            parametros.put("query", "");
        }

        nombrereporte = "/reportes/inv_movporcliente.jasper";
        nombreexportar = "MovimientosPorCliente";
    }

    public void paramRepConsolidado() {
        String mQuery = "";
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("fecini", fec_ini);

        if (!"0".equals(cod_mar)) {
            mQuery = " and art.det_mar = " + cod_mar + " ";
        }

        if (!"0".equals(cod_fam)) {
            mQuery = mQuery + " and art.det_fam = " + cod_fam + " ";
        }

        if (!"0".equals(cod_art)) {
            mQuery = mQuery + " and art.id_art = " + cod_art + " ";
        }

        parametros.put("query", mQuery);
        nombrereporte = "/reportes/inv_consolidadoporproducto.jasper";
        nombreexportar = "ConsolidadoPorProducto";
    }

    public void paramRepPendientes() {
        parametros.clear();
        parametros.put("codpai", cod_pai);
        String mquery = "";
        if (!"0".equals(mcodfiltro)) {
            mquery = " and ped.flg_val = '" + mcodfiltro + "' ";
        }
        if (!"0".equals(cod_art)) {
            mquery = mquery + " and det.cod_art = " + cod_art + " ";
        }
        if (!"0".equals(cod_fam)) {
            mquery = mquery + " and ped.cod_cli_pro = " + cod_fam + " ";
        }
        parametros.put("query", mquery);

        switch (mcodfiltro) {
            case "true":
                mquery = "VALES";
                break;
            case "false":
                mquery = "ORDENES";
                break;
            case "cotiza":
                mquery = "COTIZACIONES";
                break;
            case "repos":
                mquery = "CAMBIO / REPOSICIÓN";
                break;
        }
        parametros.put("tipo", mquery);

        nombrereporte = "/reportes/inv_ped_pendientes.jasper";
        nombreexportar = "PendientesEntregar";
    }

    public void verPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, macc.Conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en verPDF en  InvRepKardexGen." + e.getMessage());
        }
    }

    public void exportarPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, macc.Conectar());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + nombreexportar + ".pdf;");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en Exportar PDF en  InvRepKardexGen." + e.getMessage());
        }

    }

    public void exportarXLS() {

        try {

            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, macc.Conectar());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + nombreexportar + ".xlsx");
            try (ServletOutputStream outStreamxls = response.getOutputStream()) {
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStreamxls);
                exporter.exportReport();

                outStreamxls.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();

        } catch (JRException | IOException e) {
            System.out.println("Error en Exportar XLS en  InvRepKardexGen." + e.getMessage());
        }

    }

    public void exportarDOC() {

        try {

            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, macc.Conectar());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + nombreexportar + ".doc");
            ServletOutputStream outStream = response.getOutputStream();

            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
            exporter.exportReport();

            outStream.flush();
            outStream.close();
            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en Exportar DOC en  InvRepKardexGen." + e.getMessage());
        }
    }

    public void exportarPPT() {
        try {

            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, macc.Conectar());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + nombreexportar + ".ppt");
            ServletOutputStream outStream = response.getOutputStream();

            JRPptxExporter exporter = new JRPptxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
            exporter.exportReport();

            outStream.flush();
            outStream.close();
            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();
        } catch (JRException | IOException e) {
            System.out.println("Error en Exportar PPT en  InvRepKardexGen." + e.getMessage());
        }
    }

    public void dateiniSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_ini = format.format(date);
    }

    public void datefinSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_fin = format.format(date);
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

    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
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

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getTipkardex() {
        return tipkardex;
    }

    public void setTipkardex(String tipkardex) {
        this.tipkardex = tipkardex;
    }

    public Date getDfechaini() {
        return dfechaini;
    }

    public void setDfechaini(Date dfechaini) {
        this.dfechaini = dfechaini;
    }

    public Date getDfechafin() {
        return dfechafin;
    }

    public void setDfechafin(Date dfechafin) {
        this.dfechafin = dfechafin;
    }

    public List<CatListados> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<CatListados> filtro) {
        this.filtro = filtro;
    }

    public String getMcodfiltro() {
        return mcodfiltro;
    }

    public void setMcodfiltro(String mcodfiltro) {
        this.mcodfiltro = mcodfiltro;
    }

    public String getEnabledfiltro() {
        return enabledfiltro;
    }

    public void setEnabledfiltro(String enabledfiltro) {
        this.enabledfiltro = enabledfiltro;
    }

    public List<CatListados> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<CatListados> bodegas) {
        this.bodegas = bodegas;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public List<CatListados> getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(List<CatListados> tipodoc) {
        this.tipodoc = tipodoc;
    }

    public int getDiasven() {
        return diasven;
    }

    public void setDiasven(int diasven) {
        this.diasven = diasven;
    }

    public String getCod_mar() {
        return cod_mar;
    }

    public void setCod_mar(String cod_mar) {
        this.cod_mar = cod_mar;
    }

    public String getCod_fam() {
        return cod_fam;
    }

    public void setCod_fam(String cod_fam) {
        this.cod_fam = cod_fam;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public List<CatListados> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<CatListados> marcas) {
        this.marcas = marcas;
    }

    public List<CatListados> getFamilias() {
        return familias;
    }

    public void setFamilias(List<CatListados> familias) {
        this.familias = familias;
    }

    public List<CatListados> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<CatListados> articulos) {
        this.articulos = articulos;
    }

    public List<CatListados> getClientes() {
        return clientes;
    }

    public void setClientes(List<CatListados> clientes) {
        this.clientes = clientes;
    }

}
