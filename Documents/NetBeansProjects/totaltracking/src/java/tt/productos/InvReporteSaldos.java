package tt.productos;

import java.io.File;
import java.io.IOException;
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
import org.primefaces.event.SelectEvent;
import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.Login;

@Named
@ConversationScoped
public class InvReporteSaldos implements Serializable {

    @Inject
    Login cbean;
    private Accesos macc;

    private List<CatListados> paises;
    private List<CatListados> bodegas;

    private Map<String, Object> parametros;

    private String cod_bod, nom_bod, cod_pai, fec_ini, fec_fin;
    private String nombrereporte, nombreexportar;

    private Date dfechaini, dfechafin;

    public InvReporteSaldos() {
    }

    public void iniciarVentana() {
        macc = new Accesos();

        cod_bod = "0";
        parametros = new HashMap<>();
        cod_pai = cbean.getCod_pai();
        nom_bod = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        dfechaini = Date.from(Instant.now());
        dfechafin = Date.from(Instant.now());
        fec_ini = format.format(dfechaini);
        fec_fin = format.format(dfechafin);

        paises = new ArrayList<>();
        bodegas = new ArrayList<>();

        llenarPaises();
        llenarBodegas();

    }

    public void cerrarventana() {

        cod_bod = null;
        nom_bod = null;
        parametros = null;
        cod_pai = null;
        dfechaini = null;
        dfechafin = null;
        fec_ini = null;
        fec_fin = null;

        paises = null;
        bodegas = null;

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
                paises.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en InvReporteSaldos. " + e.getMessage());
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
            System.out.println("Error en el llenado de Bodegas en InvReporteSaldos. " + e.getMessage());
        }
    }

    public void ejecutarreporte(int ntipo) {
        try {

            paramRepSaldos();

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
            System.out.println("Error en EjecutarReporte invReporteSaldos. " + e.getMessage());
        }
    }

    public void paramRepSaldos() {
        macc.Conectar();
        nom_bod = macc.strQuerySQLvariable("select nom_bod from inv_cat_bodegas where id_bod = " + cod_bod + ";");
        macc.Desconectar();
        parametros.clear();
        parametros.put("codpai", cod_pai);
        parametros.put("codbod", cod_bod);
        parametros.put("fecini", fec_ini);
        parametros.put("fecfin", fec_fin);
        parametros.put("nombod", nom_bod);
        nombrereporte = "/reportes/inv_sat.jasper";
        nombreexportar = "InformeSaldosPeríodo";
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
            System.out.println("Error en verPDF en invReporteSaldos." + e.getMessage());
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
            System.out.println("Error en Exportar PDF en invReporteSaldos." + e.getMessage());
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
            System.out.println("Error en Exportar XLS en invReporteSaldos." + e.getMessage());
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
            System.out.println("Error en Exportar DOC en invReporteSaldos." + e.getMessage());
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
            System.out.println("Error en Exportar PPT en invReporteSaldos." + e.getMessage());
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

    public List<CatListados> getPaises() {
        return paises;
    }

    public void setPaises(List<CatListados> paises) {
        this.paises = paises;
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

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
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

}
