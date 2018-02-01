package tt.mantenimiento;

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
public class RepMantenimiento implements Serializable {

    private static final long serialVersionUID = 8411654786747168L;
    @Inject
    Login cbean;
    private Accesos macc;
    private String nombrereporte, nombreexportar;
    private String fec_ini, fec_fin, cod_pai, cod_usu, query;
    private Date dfechaini, dfechafin;
    private List<CatListados> paises;
    private List<CatListados> ingenieros;
    private List<CatListados> clientes;

    private Map<String, Object> parametros;

    public RepMantenimiento() {
    }

    public void iniciarVentana() {
        macc = new Accesos();
        parametros = new HashMap<>();
        cod_pai = cbean.getCod_pai();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        dfechaini = Date.from(Instant.now());
        dfechafin = Date.from(Instant.now());
        fec_ini = format.format(dfechaini);
        fec_fin = format.format(dfechafin);
        cod_usu = "0";
        query = "";

        paises = new ArrayList<>();
        ingenieros = new ArrayList<>();
        clientes = new ArrayList<>();

        llenarPaises();
        llenarIngenieros();
        llenarClientes();

    }

    public void cerrarventana() {

        parametros = null;
        cod_pai = "";
        dfechaini = null;
        dfechafin = null;
        fec_ini = "";
        fec_fin = "";
        cod_usu = "";
        query = "";

        paises = null;
        ingenieros = null;
        clientes = null;

        macc = null;
    }

    public void llenarPaises() {
        try {

            paises.clear();

            String mQuery = "select cod_pai, nom_pai "
                    + "from cat_pai order by nom_pai;";
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
            System.out.println("Error en el llenado de Paises en RepMantenimiento. " + e.getMessage());
        }
    }

    public void llenarIngenieros() {
        try {

            ingenieros.clear();

            String mQuery = "select usu.cod_usu, usu.det_nom "
                    + "from cat_usu as usu "
                    + "left join lis_esp as lis on usu.cod_usu = lis.cod_usu "
                    + "where lis.cod_lis = 1 "
                    + "order by det_nom;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ingenieros.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de ingenieros en RepMantenimiento. " + e.getMessage());
        }
    }

    public void llenarClientes() {
        try {

            clientes.clear();

            String mQuery = "select cod_cli,concat( ifnull(nom_cli,''),' (',ifnull(pai.nom_pai,''),')') as nomcli "
                    + "from cat_cli as cli "
                    + "left join cat_pai as pai on cli.cod_pai = pai.cod_pai "
                    + "order by pai.nom_pai, cli.nom_cli;";
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
            System.out.println("Error en el llenado de Clientes en RepMantenimiento. " + e.getMessage());
        }
    }

    public void ejecutarreporte(int nreporte, int ntipo) {
        try {
            switch (nreporte) {
                case 0:
                    paramRepPartesUsadasPorPeriodo();
                    break;
                case 1:
                    paramRepOrdenesAbiertas();
                    break;
                case 2:
                    paramRepOrdenesPorIngeniero();
                    break;
                case 3:
                    paramRepPMHechos();
                    break;
                case 4:
                    paramRepSalidaPiezas();
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
            System.out.println("Error en EjecutarReporte de RepMantenimiento. " + e.getMessage());
        }
    }

    public void paramRepPartesUsadasPorPeriodo() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        parametros.clear();
        parametros.put("fechaini", fec_ini + " 00:00");
        parametros.put("fechafin", fec_fin + " 23:59");
        if ("0".equals(cod_pai)) {
            parametros.put("query", "");
        } else {
            parametros.put("query", " and  leq.cod_pai =" + cod_pai + " ");
        }
        parametros.put("fechaini2", format.format(dfechaini));
        parametros.put("fechafin2", format.format(dfechafin));
        nombrereporte = "/reportes/manPartesPorPeriodo.jasper";
        nombreexportar = "UsedPartsByPeriod";
        format = null;
    }

    public void paramRepOrdenesAbiertas() {
        parametros.clear();
        if ("0".equals(cod_pai)) {
            parametros.put("codpai", "");
        } else {
            parametros.put("codpai", " and  leq.cod_pai =" + cod_pai + " ");
        }
        nombrereporte = "/reportes/manOrdenesAbiertas.jasper";
        nombreexportar = "OpenedOrders";
    }

    public void paramRepOrdenesPorIngeniero() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        parametros.clear();
        parametros.put("fec_ini", fec_ini + " 00:00");
        parametros.put("fec_fin", fec_fin + " 23:59");
        parametros.put("fecini", format.format(dfechaini));
        parametros.put("fecfin", format.format(dfechafin));
        if ("0".equals(cod_usu)) {
            parametros.put("codusu", "");
        } else {
            parametros.put("codusu", " and mae.cod_usu =" + cod_usu + " ");
        }
        if ("0".equals(cod_pai)) {
            parametros.put("codpai", "");
        } else {
            parametros.put("codpai", " and leq.cod_pai =" + cod_pai + " ");
        }
        nombrereporte = "/reportes/manOrdenesPorInge.jasper";
        nombreexportar = "OrdersByEngineer";
    }

    public void paramRepPMHechos() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        parametros.clear();
        parametros.put("fec_ini", fec_ini + " 00:00");
        parametros.put("fec_fin", fec_fin + " 23:59");
        parametros.put("fecini", format.format(dfechaini));
        parametros.put("fecfin", format.format(dfechafin));
        if ("0".equals(cod_pai)) {
            parametros.put("codpai", "");
        } else {
            parametros.put("codpai", " and  leq.cod_pai =" + cod_pai + " ");
        }
        if ("0".equals(cod_usu)) {
            parametros.put("codusu", "");
        } else {
            parametros.put("codusu", " and mae.cod_usu =" + cod_usu + " ");
        }
        nombrereporte = "/reportes/manPMCompletos.jasper";
        nombreexportar = "PMCompliance";
    }

    public void paramRepSalidaPiezas() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        parametros.clear();
        parametros.put("fec_ini", fec_ini + " 00:00");
        parametros.put("fec_fin", fec_fin + " 23:59");
        parametros.put("fecini", format.format(dfechaini));
        parametros.put("fecfin", format.format(dfechafin));
        if ("0".equals(cod_pai)) {
            parametros.put("country", "");
        } else {
            parametros.put("country", " and  mae.cod_pai =" + cod_pai + " ");
        }
        if ("0".equals(cod_usu)) {
            parametros.put("query", "");
        } else {
            parametros.put("query", " and mae.cod_pro =" + cod_usu + " ");
        }
        nombrereporte = "/reportes/man_salida_piezas.jasper";
        nombreexportar = "SPF_Outwards";
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
            System.out.println("Error en verPDF en VisReportes." + e.getMessage());
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
            System.out.println("Error en Exportar PDF en VisReportes." + e.getMessage());
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
            System.out.println("Error en Exportar XLS en VisReportes." + e.getMessage());
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
            System.out.println("Error en Exportar DOC en VisReportes." + e.getMessage());
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
            System.out.println("Error en Exportar PPT en VisReportes." + e.getMessage());
        }
    }

    public void onSelectPais() {
        cod_usu = "0";
        llenarClientes();
    }

    public void dateiniSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_ini = format.format(date);
    }

    public void datefinSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
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

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
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

    public List<CatListados> getPaises() {
        return paises;
    }

    public void setPaises(List<CatListados> paises) {
        this.paises = paises;
    }

    public List<CatListados> getIngenieros() {
        return ingenieros;
    }

    public void setIngenieros(List<CatListados> ingenieros) {
        this.ingenieros = ingenieros;
    }

    public List<CatListados> getClientes() {
        return clientes;
    }

    public void setClientes(List<CatListados> clientes) {
        this.clientes = clientes;
    }

}
