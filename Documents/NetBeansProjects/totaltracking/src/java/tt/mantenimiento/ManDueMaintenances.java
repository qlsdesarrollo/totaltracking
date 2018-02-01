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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.event.SelectEvent;
import tt.general.Accesos;
import tt.general.CatListados;
import tt.general.Login;

@Named
@ConversationScoped
public class ManDueMaintenances implements Serializable {

    private static final long serialVersionUID = 879767456879215638L;
    @Inject
    Login cbean;
    private CatDueMaintenance catduemaintenance;
    private List<CatDueMaintenance> duemaintenance;
    private List<CatListados> equipos;
    private List<CatListados> frecuencia;
    private List<CatListados> orden;
    private List<CatListados> piezas;
    private String cod_pro, cod_tip, cod_per, ord_per, cod_pie;
    private double qty_use;

    private String nombrereporte, nombreexportar;
    private String fec_ini, fec_fin, cod_pai;
    private Date dfechaini, dfechafin;
    private List<CatListados> paises;

    private Map<String, Object> parametros;

    private Accesos macc;

    public ManDueMaintenances() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_pro = "";
        cod_tip = "0";
        cod_per = "0";
        ord_per = "0";
        cod_pie = "0";
        qty_use = 0.0;

        equipos = new ArrayList<>();
        frecuencia = new ArrayList<>();
        orden = new ArrayList<>();
        piezas = new ArrayList<>();

        catduemaintenance = new CatDueMaintenance();
        duemaintenance = new ArrayList<>();

        llenarEquipos();
        llenarFrecuencia();
        llenarPiezas();
        llenarMatriz();

        parametros = new HashMap<>();
        cod_pai = cbean.getCod_pai();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        dfechaini = Date.from(Instant.now());
        dfechafin = Date.from(Instant.now());
        fec_ini = format.format(dfechaini);
        fec_fin = format.format(dfechafin);

        paises = new ArrayList<>();

        llenarPaises();

    }

    public void nuevo() {
        cod_pro = "";
        cod_tip = "0";
        cod_per = "0";
        ord_per = "0";
        cod_pie = "0";
        qty_use = 0.0;
        catduemaintenance = null;
    }

    public void cerrarventana() {
        cod_pro = null;
        cod_tip = null;
        cod_per = null;
        ord_per = null;
        cod_pie = null;
        qty_use = 0.0;

        equipos = null;
        frecuencia = null;
        orden = null;
        piezas = null;

        catduemaintenance = null;
        duemaintenance = null;

        parametros = null;
        cod_pai = null;
        dfechaini = null;
        dfechafin = null;
        fec_ini = null;
        fec_fin = null;

        paises = null;

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
            System.out.println("Error en el llenado de Paises en ManDueMaintenances. " + e.getMessage());
        }
    }

    public void llenarEquipos() {
        String mQuery = "";
        try {
            equipos.clear();

            mQuery = "select cod_equ,nom_equ "
                    + "from cat_equ "
                    + "order by nom_equ;";
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
            System.out.println("Error en el llenado de Equipos ManDueMaintenances. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarFrecuencia() {
        String mQuery = "";
        try {
            frecuencia.clear();
            orden.clear();

            mQuery = "select cod_per, nom_per from cat_per;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                frecuencia.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Frecuencia ManDueMaintenances. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarOrden() {
        String mQuery = "";
        try {
            orden.clear();

            switch (cod_per) {
                case "5":
                    mQuery = "select 6, '6 MONTHS' "
                            + "union all "
                            + "select 12, '12 MONTHS'";
                    break;
                case "6":
                    mQuery = "select 1, 'FIRST 6 MONTHS' "
                            + "union all "
                            + "select 2, 'SECOND 6 MONTHS' "
                            + "union all "
                            + "select 3, '12 MONTHS' ";
                    break;
                default:
                    mQuery = "select 0, '----------';";
                    break;
            }

            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                orden.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Orden ManDueMaintenances. " + e.getMessage() + " Query: " + mQuery);
        }

    }

    public void llenarPiezas() {
        String mQuery = "";
        try {
            piezas.clear();

            mQuery = "select cod_pie, concat(ifnull(cod_ref,''),'--',nom_pie) as nompie from cat_pie order by cod_ref;";
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
            System.out.println("Error en el llenado de Piezas ManDueMaintenances. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMatriz() {
        String mQuery = "";
        try {
            catduemaintenance = null;
            duemaintenance.clear();

            mQuery = "select   "
                    + "rep.cod_pro,  "
                    + "rep.cod_tip,  "
                    + "rep.cod_per,  "
                    + "rep.ord_per,  "
                    + "rep.cod_pie,  "
                    + "rep.qty_use, "
                    + "equ.nom_equ, "
                    + "per.nom_per, "
                    + "case rep.ord_per  "
                    + "when 0 then '---------------'  "
                    + "when 1 then 'FIRST 6 MONTHS'  "
                    + "when 2 then 'SECOND 6 MONTHS'  "
                    + "when 3 then '12 MONTHS'  "
                    + "when 6 then '6 MONTHS'  "
                    + "when 12 then '12 MONTHS'  "
                    + "end as ord_per, "
                    + "pie.cod_ref, "
                    + "pie.nom_pie "
                    + "from rep_pro_par as rep "
                    + "left join cat_equ as equ on rep.cod_tip = equ.cod_equ "
                    + "left join cat_per as per on rep.cod_per = per.cod_per "
                    + "left join cat_pie as pie on rep.cod_pie = pie.cod_pie "
                    + "order by rep.cod_tip, rep.cod_per, rep.ord_per, rep.cod_pie;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                duemaintenance.add(new CatDueMaintenance(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getDouble(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Matriz ManDueMaintenances. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(cod_tip)) {
            addMessage("Save", "You have to select a Equipment Type", 2);
            return false;
        }

        if ("".equals(cod_per)) {
            addMessage("Save", "You have to select a Frequency", 2);
            return false;
        }

        if ("".equals(ord_per)) {
            addMessage("Save", "You have to select an Ordinal", 2);
            return false;
        }

        if ("".equals(cod_pie)) {
            addMessage("Save", "You have to select a Part", 2);
            return false;
        }

        if (qty_use <= 0.0) {
            addMessage("Save", "You have to enter a Quantity above Zero", 2);
            return false;
        }

        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {

                macc.Conectar();
                if ("".equals(cod_pro)) {
                    mQuery = "select ifnull(max(cod_pro),0)+1 as codigo from rep_pro_par;";
                    cod_pro = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into rep_pro_par (cod_pro,cod_tip,cod_per,ord_per,cod_pie,qty_use) "
                            + "values (" + cod_pro + "," + cod_tip + "," + cod_per + "," + ord_per + "," + cod_pie + "," + qty_use + ");";
                } else {
                    mQuery = "update rep_pro_par SET "
                            + "cod_tip = " + cod_tip + ", "
                            + "cod_per = " + cod_per + ", "
                            + "ord_per = " + ord_per + ", "
                            + "cod_pie = " + cod_pie + ", "
                            + "qty_use = " + qty_use + " "
                            + "WHERE cod_pro = " + cod_pro + ";";

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Matriz. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMatriz();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_pro) || "0".equals(cod_pro)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from rep_pro_par where cod_pro=" + cod_pro + ";";
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Marca. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarMatriz();
            nuevo();
        }
    }

    public void onRowSelect(SelectEvent event) {
        cod_pro = ((CatDueMaintenance) event.getObject()).getCod_pro();
        cod_tip = ((CatDueMaintenance) event.getObject()).getCod_tip();
        cod_per = ((CatDueMaintenance) event.getObject()).getCod_per();
        llenarOrden();
        ord_per = ((CatDueMaintenance) event.getObject()).getOrd_per();
        cod_pie = ((CatDueMaintenance) event.getObject()).getCod_pie();
        qty_use = ((CatDueMaintenance) event.getObject()).getQty_use();
    }

    public void onSelectFrecuencia() {
        switch (cod_per) {
            case "5":
                ord_per = "6";
                break;
            case "6":
                ord_per = "1";
                break;
            default:
                ord_per = "0";
                break;
        }
        llenarOrden();
    }

    //*************************************************************************
    public void ejecutarreporte(int ntipo) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        
            //System.out.println(fec_ini + " " + fec_fin);
            
            parametros.clear();
            parametros.put("fecini", fec_ini + " 00:00");
            parametros.put("fecfin", fec_fin + " 23:59");
            if ("0".equals(cod_pai)) {
                parametros.put("query", "");
            } else {
                parametros.put("query", " and  leq.cod_pai =" + cod_pai + " ");
            }
            parametros.put("estado", "1,3");
            parametros.put("fec1", format.format(dfechaini));
            parametros.put("fec2", format.format(dfechafin));
            nombrereporte = "/reportes/man_proyectivo_piezas.jasper";
            nombreexportar = "DueMaintenances";

            switch (ntipo) {
                case 1:
                    verPDF();
                    break;
                case 3:
                    exportarXLS();
                    break;

            }
        } catch (Exception e) {
            System.out.println("Error en EjecutarReporte de ManDueMaintenances. " + e.getMessage());
        }
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

    public void dateiniSelectedAction(SelectEvent f) {
        try{
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_ini = format.format(date);
        }catch(Exception e){
           // System.out.println(fec_ini + " error en esta fecha");
        }
    }

    public void datefinSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
        fec_fin = format.format(date);
    }

    //*************************************************************************
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

    public CatDueMaintenance getCatduemaintenance() {
        return catduemaintenance;
    }

    public void setCatduemaintenance(CatDueMaintenance catduemaintenance) {
        this.catduemaintenance = catduemaintenance;
    }

    public List<CatDueMaintenance> getDuemaintenance() {
        return duemaintenance;
    }

    public void setDuemaintenance(List<CatDueMaintenance> duemaintenance) {
        this.duemaintenance = duemaintenance;
    }

    public List<CatListados> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatListados> equipos) {
        this.equipos = equipos;
    }

    public List<CatListados> getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(List<CatListados> frecuencia) {
        this.frecuencia = frecuencia;
    }

    public List<CatListados> getOrden() {
        return orden;
    }

    public void setOrden(List<CatListados> orden) {
        this.orden = orden;
    }

    public List<CatListados> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatListados> piezas) {
        this.piezas = piezas;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_tip() {
        return cod_tip;
    }

    public void setCod_tip(String cod_tip) {
        this.cod_tip = cod_tip;
    }

    public String getCod_per() {
        return cod_per;
    }

    public void setCod_per(String cod_per) {
        this.cod_per = cod_per;
    }

    public String getOrd_per() {
        return ord_per;
    }

    public void setOrd_per(String ord_per) {
        this.ord_per = ord_per;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public double getQty_use() {
        return qty_use;
    }

    public void setQty_use(double qty_use) {
        this.qty_use = qty_use;
    }

    public Accesos getMacc() {
        return macc;
    }

    public void setMacc(Accesos macc) {
        this.macc = macc;
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

    public List<CatListados> getPaises() {
        return paises;
    }

    public void setPaises(List<CatListados> paises) {
        this.paises = paises;
    }
    

}
