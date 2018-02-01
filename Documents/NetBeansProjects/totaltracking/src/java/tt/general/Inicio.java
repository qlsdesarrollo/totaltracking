package tt.general;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@Named
@ConversationScoped
public class Inicio implements Serializable {

    @Inject
    Login cbean;

    private Accesos maccess;

    private CatSchMain catschmain;
    private List<CatSchMain> scheduler;

    private List<CatListados> estados;

    private String correla, sch_cor, fec_sch, cod_usu, usu_sol, cod_man, det_obs, det_sta, det_col, flg_tar_man;
    private String status;

    //**************************************************************************
    private CatSchMain catschmaintareas;
    private List<CatSchMain> schedulertareas;

    private List<CatListados> usuarios;

    private String codtarea, fechatarea, usuariotarea, tarea;

    private Date dfecha1;

    public Inicio() {

    }

    @PostConstruct
    public void init() {
        maccess = new Accesos();
        catschmain = new CatSchMain();
        scheduler = new ArrayList<>();
        estados = new ArrayList<>();

        llenarTareas();
        llenarEstados();

        maccess = null;
    }

    //************************* TABLA TAREAS ********************************
    public void iniciarventana() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");

        maccess = new Accesos();

        dfecha1 = Date.from(Instant.now());
        codtarea = "";
        fechatarea = format.format(dfecha1);
        usuariotarea = "0";
        tarea = "";

        usuarios = new ArrayList<>();
        catschmaintareas = new CatSchMain();
        schedulertareas = new ArrayList<>();

        llenarUsuarios();
        llenarListaTareas();
    }

    public void cerrarventana() {
        dfecha1 = null;
        codtarea = null;
        fechatarea = null;
        usuariotarea = null;
        tarea = null;

        usuarios = null;
        catschmaintareas = null;
        schedulertareas = null;

        maccess = null;

        init();
    }

    public void nuevo() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        dfecha1 = Date.from(Instant.now());
        codtarea = "";
        fechatarea = format.format(dfecha1);
        usuariotarea = "0";
        tarea = "";

        catschmaintareas = null;

    }

    public void llenarUsuarios() {
        String mQuery = "";
        try {
            usuarios.clear();

            mQuery = "select usu.cod_usu, concat( ifnull(usu.det_nom,''), ' (', ifnull(pai.nom_pai,''),')') as nombre "
                    + "from cat_usu as usu "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai "
                    + "where ucase(usu.nom_usu) <> 'ADMIN' and ucase(usu.nom_usu) <> 'ADMINISTRADOR' and ucase(usu.nom_usu) <> 'ADMINISTRATOR' "
                    + "order by det_nom;";
            ResultSet resVariable;

            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                usuarios.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Usuarios en Inicio" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarListaTareas() {
        String mQuery = "";
        try {
            catschmaintareas = null;
            schedulertareas.clear();

            mQuery = "select  "
                    + "0, "
                    + "main.sch_cor, "
                    + "date_format(main.fec_sch,'%d/%b/%Y %H:%i'), "
                    + "main.cod_usu, "
                    + "main.usu_sol, "
                    + "main.cod_man, "
                    + "main.det_obs, "
                    + "main.det_sta, "
                    + "'', "
                    + "0, "
                    + "usu.det_nom, "
                    + "'', "
                    + "case main.det_sta "
                    + "when 1 then 'PENDING'   "
                    + "when 2 then 'CANCELED'  "
                    + "when 3 then 'IN PROGRESS'  "
                    + "when 4 then 'COMPLETED'   "
                    + "end as estado, "
                    + "null  "
                    + "from sch_main as main "
                    + "left join cat_usu as usu on main.cod_usu = usu.cod_usu "
                    + "where usu_sol = " + cbean.getCod_usu() + " "
                    + "and main.det_sta in (1) "
                    + "order by main.fec_sch;";
            ResultSet resVariable;

            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                schedulertareas.add(new CatSchMain(
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
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de ListaTareas en Inicio. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("0".equals(usuariotarea)) {
            addMessage("Save", "You have to Select an User", 2);
            return false;
        }
        if ("".equals(tarea)) {
            addMessage("Save", "You have to enter a Task", 2);
            return false;
        }

        return mValidar;
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                maccess.Conectar();
                if ("".equals(codtarea)) {
                    mQuery = "select ifnull(max(sch_cor),0)+1 as codigo from sch_main;";
                    codtarea = maccess.strQuerySQLvariable(mQuery);
                    mQuery = "insert into sch_main (sch_cor,fec_sch,cod_usu,usu_sol,cod_man,det_obs,det_sta,fec_fin,fec_cre) "
                            + "values (" + codtarea + ",str_to_date('" + fechatarea + "','%d/%b/%Y %H:%i')," + usuariotarea + ", "
                            + cbean.getCod_usu() + ",0,'" + tarea + "',1,null,now());";
                } else {
                    mQuery = "update sch_main SET "
                            + "cod_usu = " + usuariotarea + " "
                            + ",det_obs = '" + tarea + "' "
                            + ",fec_sch = str_to_date('" + fechatarea + "','%d/%b/%Y %H:%i') "
                            + "WHERE sch_cor = " + codtarea + ";";

                }
                maccess.dmlSQLvariable(mQuery);
                maccess.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Tarea. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarListaTareas();
            nuevo();
        }

    }

    public boolean validareliminar() {
        boolean mvalidar = true;
        String mQuery = "";

        if ("".equals(codtarea) || "0".equals(codtarea)) {
            addMessage("Delete", "You have to select a record.", 2);
            return false;
        }

        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";

        if (validareliminar()) {
            try {
                maccess.Conectar();
                mQuery = "delete from sch_main where sch_cor=" + codtarea + ";";
                maccess.dmlSQLvariable(mQuery);
                maccess.Desconectar();
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Tarea. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarListaTareas();
            nuevo();
        }
    }

    public void seleccionarTarea(SelectEvent event) {
        codtarea = ((CatSchMain) event.getObject()).getSch_cor();
        fechatarea = ((CatSchMain) event.getObject()).getFec_sch();
        usuariotarea = ((CatSchMain) event.getObject()).getCod_usu();
        tarea = ((CatSchMain) event.getObject()).getDet_obs();

        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        try {
            dfecha1 = format.parse(fechatarea);
            fechatarea = format2.format(dfecha1);
        } catch (Exception ex) {
            dfecha1 = null;
        }

    }

    //************************* DASHBOARD ***********************************
    public void llenarTareas() {
        String mQuery = "";
        try {
            catschmain = null;
            scheduler.clear();

            mQuery = "SELECT  "
                    + "@rownum:=@rownum+1 AS rownum,  "
                    + "t.sch_cor, "
                    + "date_format(t.fec_sch,'%d/%b/%Y %H:%i') as fec_sch,  "
                    + "t.cod_usu,  "
                    + "t.usu_sol, "
                    + "t.cod_man,  "
                    + "t.det_obs,  "
                    + "t.det_sta,  "
                    + "t.det_col, "
                    + "t.flg_tar_man, "
                    + "t.solicita, "
                    + "t.equipo, "
                    + "t.estado, "
                    + "t.cod_lis_equ as fec_fin "
                    + "FROM  "
                    + "(SELECT @rownum:=0) r, "
                    + "( "
                    + "(select    "
                    + "main.sch_cor,  "
                    + "main.fec_sch,  "
                    + "main.cod_usu,  "
                    + "main.usu_sol, "
                    + "main.cod_man,  "
                    + "main.det_obs,  "
                    + "main.det_sta,  "
                    + "if((TIMESTAMPDIFF(day,main.fec_sch,now()))<=0,'lime',if((TIMESTAMPDIFF(day,main.fec_sch,now()))<=1,'yellow','red')) as det_col, "
                    + "0 as flg_tar_man, "
                    + "usu.det_nom as solicita, "
                    + "'' as equipo, "
                    + "case main.det_sta  "
                    + "when 1 then 'PENDING'  "
                    + "when 2 then 'CANCELED'  "
                    + "when 3 then 'IN PROGRESS'  "
                    + "when 4 then 'COMPLETED'  "
                    + "end as estado, "
                    + "0 as cod_lis_equ "
                    + "from sch_main as main "
                    + "left join cat_usu as usu on main.usu_sol = usu.cod_usu "
                    + "where main.cod_usu = " + cbean.getCod_usu() + " "
                    + "and main.det_sta in (1,3) "
                    + ") "
                    + "UNION ALL "
                    + "( "
                    + "select  "
                    + "'', "
                    + "man.fec_ini, "
                    + "man.cod_usu, "
                    + "0, "
                    + "man.cod_man, "
                    + "man.det_obs, "
                    + "man.det_sta, "
                    + "if((TIMESTAMPDIFF(MONTH,man.fec_ini,now()))<=1,'lime',if((TIMESTAMPDIFF(MONTH,man.fec_ini,now()))<=2,'yellow','red')), "
                    + "1 as flg, "
                    + "pai.nom_pai, "
                    + "concat(ifnull(equ.nom_equ,''),' ',lequ.num_ser, '(', pai2.nom_pai,')'), "
                    + "case man.det_sta  "
                    + "when 1 then 'PENDING'  "
                    + "when 2 then 'CANCELED'  "
                    + "when 3 then 'IN PROGRESS'  "
                    + "when 4 then 'COMPLETED'  "
                    + "end as estado, "
                    + "man.cod_lis_equ "
                    + "from tbl_mae_man as man "
                    + "left join cat_pai as pai on man.ord_por = pai.cod_pai "
                    + "left join lis_equ as lequ on man.cod_lis_equ = lequ.cod_lis_equ "
                    + "left join cat_equ as equ on lequ.cod_equ = equ.cod_equ "
                    + "left join cat_pai as pai2 on lequ.cod_pai = pai2.cod_pai "
                    + "where man.cod_usu = " + cbean.getCod_usu() + " "
                    + "and man.det_sta in (1,3) "
                    + ") "
                    + ") AS t "
                    + "ORDER BY t.fec_sch;";
            ResultSet resVariable;

            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                scheduler.add(new CatSchMain(
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
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Tareas en Inicio. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEstados() {
        String mQuery = "";
        try {
            estados.clear();

            mQuery = "SELECT 'PENDING' AS C, 'PENDING' AS D "
                    + "UNION ALL "
                    + "SELECT 'CANCELED' AS C, 'CANCELED' AS D "
                    + "UNION ALL "
                    + "SELECT 'IN_PROGRESS' AS C, 'IN PROGRESS' AS D "
                    + "UNION ALL "
                    + "SELECT 'COMPLETED' AS C, 'COMPLETED' AS D "
                    + ";";
            ResultSet resVariable;

            maccess.Conectar();
            resVariable = maccess.querySQLvariable(mQuery);
            while (resVariable.next()) {
                estados.add(new CatListados(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            maccess.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Estados en Inicio. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        /*FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);*/
        String mQuery = "";
        //CatSchMain cat = (CatSchMain) event.getObject();
        //cat.setEstado(status);

        if (((CatSchMain) event.getObject()).getFlg_tar_man().equals("0")) {
            maccess = new Accesos();
            maccess.Conectar();
            //System.out.println("value: " + ((CatSchMain) event.getObject()).getEstado() + " sch_cor: " + ((CatSchMain) event.getObject()).getSch_cor());
            try {
                if (((CatSchMain) event.getObject()).getEstado().equals("PENDING")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 1, "
                            + "fec_fin = null "
                            + "where sch_cor = " + ((CatSchMain) event.getObject()).getSch_cor() + ";";
                }

                if (((CatSchMain) event.getObject()).getEstado().equals("CANCELED")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 2, "
                            + "fec_fin = now() "
                            + "where sch_cor = " + ((CatSchMain) event.getObject()).getSch_cor() + ";";
                }

                if (((CatSchMain) event.getObject()).getEstado().equals("IN_PROGRESS")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 3, "
                            + "fec_fin = null "
                            + "where sch_cor = " + ((CatSchMain) event.getObject()).getSch_cor() + ";";
                }

                if (((CatSchMain) event.getObject()).getEstado().equals("COMPLETED")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 4, "
                            + "fec_fin = now() "
                            + "where sch_cor = " + ((CatSchMain) event.getObject()).getSch_cor() + ";";
                }

                maccess.dmlSQLvariable(mQuery);

                llenarTareas();
            } catch (Exception e) {
                System.out.println("Error en Editar Fila. " + e.getMessage() + " Query: " + mQuery);
            }
            maccess.Desconectar();
            maccess = null;
        } else {
            maccess = new Accesos();
            maccess.Conectar();
            CatSchMain cat = (CatSchMain) event.getObject();
            if (maccess.strQuerySQLvariable("select det_sta "
                    + "from tbl_mae_man "
                    + "where cod_lis_equ =" + ((CatSchMain) event.getObject()).getFec_fin()
                    + " and cod_man =" + ((CatSchMain) event.getObject()).getCod_man() + ";").equals("1")) {
                cat.setEstado("PENDING");
            }else{
                cat.setEstado("IN PROGRESS");
            }

            cat = null;
            maccess.Desconectar();
            maccess = null;
        }

    }

    public void onRowCancel(RowEditEvent event) {
        /*FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);*/
    }

    public void onCellEdit(CellEditEvent event) {

        String mQuery = "";
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        //System.out.println("value: " + newValue.toString() + " old: " + oldValue.toString());
        if (newValue != null && !newValue.equals(oldValue) && scheduler.get(event.getRowIndex()).getFlg_tar_man().equals("0")) {
            /*FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);*/
            maccess = new Accesos();
            maccess.Conectar();
            //System.out.println("value: " + newValue.toString() + " sch_cor: " + scheduler.get( event.getRowIndex()).getSch_cor());
            try {
                if (newValue.toString().equals("PENDING")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 1, "
                            + "fec_fin = null "
                            + "where sch_cor = " + scheduler.get(event.getRowIndex()).getSch_cor() + ";";
                }

                if (newValue.toString().equals("CANCELED")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 2, "
                            + "fec_fin = now() "
                            + "where sch_cor = " + scheduler.get(event.getRowIndex()).getSch_cor() + ";";
                }

                if (newValue.toString().equals("IN_PROGRESS")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 3, "
                            + "fec_fin = null "
                            + "where sch_cor = " + scheduler.get(event.getRowIndex()).getSch_cor() + ";";
                }

                if (newValue.toString().equals("COMPLETED")) {
                    mQuery = "update sch_main set "
                            + "det_sta = 4, "
                            + "fec_fin = now() "
                            + "where sch_cor = " + scheduler.get(event.getRowIndex()).getSch_cor() + ";";
                }

                maccess.dmlSQLvariable(mQuery);

            } catch (Exception e) {
                System.out.println("Error en Editar Celda. " + e.getMessage() + " Query: " + mQuery);
            }
            maccess.Desconectar();
            llenarTareas();
            maccess = null;
        }
    }

    public void onRowSelect(SelectEvent event) {
        correla = ((CatSchMain) event.getObject()).getCorrela();
        sch_cor = ((CatSchMain) event.getObject()).getSch_cor();
        fec_sch = ((CatSchMain) event.getObject()).getFec_sch();
        cod_usu = ((CatSchMain) event.getObject()).getCod_usu();
        usu_sol = ((CatSchMain) event.getObject()).getUsu_sol();
        cod_man = ((CatSchMain) event.getObject()).getCod_man();
        det_obs = ((CatSchMain) event.getObject()).getDet_obs();
        det_sta = ((CatSchMain) event.getObject()).getDet_sta();
        det_col = ((CatSchMain) event.getObject()).getDet_col();
        flg_tar_man = ((CatSchMain) event.getObject()).getFlg_tar_man();

    }

    public void onRowUnselect(UnselectEvent event) {
        catschmain = null;
    }

    public void dateSchedul(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        fechatarea = format.format(date);
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

    public CatSchMain getCatschmain() {
        return catschmain;
    }

    public void setCatschmain(CatSchMain catschmain) {
        this.catschmain = catschmain;
    }

    public List<CatSchMain> getScheduler() {
        return scheduler;
    }

    public void setScheduler(List<CatSchMain> scheduler) {
        this.scheduler = scheduler;
    }

    public String getUsu_sol() {
        return usu_sol;
    }

    public void setUsu_sol(String usu_sol) {
        this.usu_sol = usu_sol;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public Date getDfecha1() {
        return dfecha1;
    }

    public void setDfecha1(Date dfecha1) {
        this.dfecha1 = dfecha1;
    }

    public List<CatListados> getEstados() {
        return estados;
    }

    public void setEstados(List<CatListados> estados) {
        this.estados = estados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CatSchMain getCatschmaintareas() {
        return catschmaintareas;
    }

    public void setCatschmaintareas(CatSchMain catschmaintareas) {
        this.catschmaintareas = catschmaintareas;
    }

    public List<CatSchMain> getSchedulertareas() {
        return schedulertareas;
    }

    public void setSchedulertareas(List<CatSchMain> schedulertareas) {
        this.schedulertareas = schedulertareas;
    }

    public List<CatListados> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatListados> usuarios) {
        this.usuarios = usuarios;
    }

    public String getCodtarea() {
        return codtarea;
    }

    public void setCodtarea(String codtarea) {
        this.codtarea = codtarea;
    }

    public String getUsuariotarea() {
        return usuariotarea;
    }

    public void setUsuariotarea(String usuariotarea) {
        this.usuariotarea = usuariotarea;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

}
