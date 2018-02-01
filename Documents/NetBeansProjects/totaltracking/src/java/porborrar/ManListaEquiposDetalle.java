package porborrar;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import tt.mantenimiento.CatMantenimientosAne;
import tt.mantenimiento.CatMantenimientosGen;
import tt.mantenimiento.CatMantenimientosPie;
import tt.mantenimiento.CatOperaciones;
import tt.seguridad.CatUsuarios;
import tt.general.Login;
import tt.mantenimiento.ManListaEquipos;

@Named
@ConversationScoped

public class ManListaEquiposDetalle implements Serializable {

    private static final long serialVersionUID = 8715874748765398L;
    @Inject
    Login cbean;
    @Inject
    ManListaEquipos enc;
    private CatMantenimientosGen catmantenimientosgen;
    private List<CatMantenimientosGen> general;
    private CatMantenimientosPie catmantenimientospie;
    private List<CatMantenimientosPie> piezas;
    private CatMantenimientosAne catmantenimientosane;
    private List<CatMantenimientosAne> anexos;
    private CatOperaciones catoperaciones;
    private List<CatOperaciones> operaciones;
    private CatUsuarios catusuarios;
    private List<CatUsuarios> usuarios;

    private String cod_lis_equ, cod_man, cod_usu;
    private String g_det_man, g_fec_man, g_hor_man, g_cod_ope, g_det_obs;
    private String p_det_man, p_det_can, p_cod_lis_pie, p_num_ser;
    private String a_det_man, a_det_obs, a_tip_ane, a_rut_ane;

    private String boolean_e_s, tabindex;

    public ManListaEquiposDetalle() {
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

    public CatOperaciones getCatoperaciones() {
        return catoperaciones;
    }

    public void setCatoperaciones(CatOperaciones catoperaciones) {
        this.catoperaciones = catoperaciones;
    }

    public List<CatOperaciones> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<CatOperaciones> operaciones) {
        this.operaciones = operaciones;
    }

    public CatUsuarios getCatusuarios() {
        return catusuarios;
    }

    public void setCatusuarios(CatUsuarios catusuarios) {
        this.catusuarios = catusuarios;
    }

    public List<CatUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<CatUsuarios> usuarios) {
        this.usuarios = usuarios;
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

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getG_det_man() {
        return g_det_man;
    }

    public void setG_det_man(String g_det_man) {
        this.g_det_man = g_det_man;
    }

    public String getG_fec_man() {
        return g_fec_man;
    }

    public void setG_fec_man(String g_fec_man) {
        this.g_fec_man = g_fec_man;
    }

    public String getG_hor_man() {
        return g_hor_man;
    }

    public void setG_hor_man(String g_hor_man) {
        this.g_hor_man = g_hor_man;
    }

    public String getG_cod_ope() {
        return g_cod_ope;
    }

    public void setG_cod_ope(String g_cod_ope) {
        this.g_cod_ope = g_cod_ope;
    }

    public String getG_det_obs() {
        return g_det_obs;
    }

    public void setG_det_obs(String g_det_obs) {
        this.g_det_obs = g_det_obs;
    }

    public String getP_det_man() {
        return p_det_man;
    }

    public void setP_det_man(String p_det_man) {
        this.p_det_man = p_det_man;
    }

    public String getA_det_man() {
        return a_det_man;
    }

    public void setA_det_man(String a_det_man) {
        this.a_det_man = a_det_man;
    }

    public String getA_det_obs() {
        return a_det_obs;
    }

    public void setA_det_obs(String a_det_obs) {
        this.a_det_obs = a_det_obs;
    }

    public String getA_tip_ane() {
        return a_tip_ane;
    }

    public void setA_tip_ane(String a_tip_ane) {
        this.a_tip_ane = a_tip_ane;
    }

    public String getA_rut_ane() {
        return a_rut_ane;
    }

    public void setA_rut_ane(String a_rut_ane) {
        this.a_rut_ane = a_rut_ane;
    }

    public String getBoolean_e_s() {
        return boolean_e_s;
    }

    public void setBoolean_e_s(String boolean_e_s) {
        this.boolean_e_s = boolean_e_s;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getP_det_can() {
        return p_det_can;
    }

    public void setP_det_can(String p_det_can) {
        this.p_det_can = p_det_can;
    }

    public String getP_cod_lis_pie() {
        return p_cod_lis_pie;
    }

    public void setP_cod_lis_pie(String p_cod_lis_pie) {
        this.p_cod_lis_pie = p_cod_lis_pie;
    }

    public String getP_num_ser() {
        return p_num_ser;
    }

    public void setP_num_ser(String p_num_ser) {
        this.p_num_ser = p_num_ser;
    }

    public void iniciarventana() {

        tabindex = "0";

        cod_lis_equ = enc.getCod_lis_equ();
        cod_man = enc.getM_cod_man();
        cod_usu = cbean.getCod_usu();
        g_det_man = "";
        g_fec_man = "";
        g_hor_man = "";
        g_cod_ope = "";
        g_det_obs = "";
        p_det_man = "";
        p_det_can = "";
        p_cod_lis_pie = "";
        p_num_ser = "";
        a_det_man = "";
        a_det_obs = "";
        a_tip_ane = "";
        a_rut_ane = "";
        llenarOperaciones();
        llenarGeneral();
        llenarPiezas();
        llenarAnexos();
        llenarUsuarios();

    }

    public void cerrarventana() {
        tabindex = "0";

        cod_lis_equ = enc.getCod_lis_equ();
        cod_man = enc.getM_cod_man();
        cod_usu = cbean.getCod_usu();
        g_det_man = "";
        g_fec_man = "";
        g_hor_man = "";
        g_cod_ope = "";
        g_det_obs = "";
        p_det_man = "";
        p_det_can = "";
        p_cod_lis_pie = "";
        p_num_ser = "";
        a_det_man = "";
        a_det_obs = "";
        a_tip_ane = "";
        a_rut_ane = "";
        operaciones = new ArrayList<>();
        general = new ArrayList<>();
        piezas = new ArrayList<>();
        anexos = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void llenarUsuarios() {
        try {
            catusuarios = new CatUsuarios();
            usuarios = new ArrayList<>();

            String mQuery = "select usu.cod_usu, usu.nom_usu, usu.des_pas, usu.tip_usu, usu.cod_pai, "
                    + "usu.cod_dep, usu.det_nom, usu.det_mai,ifnull(pai.nom_pai,'') as nom_pai, ifnull(dep.nom_dep,'') as nom_dep "
                    + "from cat_usu as usu "
                    + "left join cat_dep as dep on usu.cod_dep = dep.cod_dep and usu.cod_pai = dep.cod_pai "
                    + "left join cat_pai as pai on usu.cod_pai = pai.cod_pai order by cod_usu;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Usuarios ManListaEquiposDetalle. " + e.getMessage());
        }
    }
    
    public void llenarOperaciones() {
        String mQuery = "";
        try {
            catoperaciones = new CatOperaciones();
            operaciones = new ArrayList<>();

            mQuery = "select cod_ope, nom_ope from cat_ope order by cod_ope;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                operaciones.add(new CatOperaciones(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Operaciones ManListaEquiposDetalle" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarGeneral() {
        String mQuery = "";
        try {
            catmantenimientosgen = new CatMantenimientosGen();
            general = new ArrayList<>();

            mQuery = "select gen.cod_lis_equ,gen.cod_man,gen.det_man,"
                    + "date_format(gen.fec_man,'%d/%m/%Y'),"
                    + "time_format(gen.hor_man,'%H:%i'),"
                    + "gen.cod_ope,gen.det_obs,"
                    + "gen.cod_usu,ope.nom_ope "
                    + "from tbl_det_man_gen as gen "
                    + "left join cat_ope as ope on gen.cod_ope = ope.cod_ope "
                    + "where gen.cod_lis_equ=" + cod_lis_equ + " "
                    + "and gen.cod_man=" + cod_man + " "
                    + "order by gen.det_man;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
                        resVariable.getString(8),
                        resVariable.getString(8)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalle General ManListaEquiposDetalle" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPiezas() {
        String mQuery = "";
        try {
            catmantenimientospie = new CatMantenimientosPie();
            piezas = new ArrayList<>();

            mQuery = "select gen.cod_lis_equ,gen.cod_man,gen.det_man,"
                    + "gen.det_can,gen.cod_lis_pie,gen.num_ser,"
                    + "gen.cod_usu,pie.nom_pie "
                    + "from tbl_det_man_pie as gen "
                    + "left join lis_pie as lis on gen.cod_lis_pie = lis.cod_lis_pie "
                    + "left join cat_pie as pie on lis.cod_pie = pie.cod_pie "
                    + "where gen.cod_lis_equ=" + cod_lis_equ + " "
                    + "and gen.cod_man=" + cod_man + " "
                    + "order by gen.det_man;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(8),
                        resVariable.getString(8)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Detalle Piezas ManListaEquiposDetalle" + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarAnexos() {
        String mQuery = "";
        try {
            catmantenimientosane = new CatMantenimientosAne();
            anexos = new ArrayList<>();

            mQuery = "select cod_lis_equ, cod_man, det_man, "
                    + "det_obs, tip_ane, rut_ane, cod_usu, "
                    + "case tip_ane "
                    + "when 1 then 'PDF' "
                    + "when 2 then 'IMAGEN' "
                    + "when 3 then 'OTRO' "
                    + "end as nomtip "
                    + "from tbl_det_man_ane "
                    + "where cod_lis_equ=" + cod_lis_equ + " "
                    + "and cod_man=" + cod_man + " "
                    + "order by det_man;";

            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
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
                        resVariable.getString(8)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado Anexos ManListaEquiposDetalle" + e.getMessage() + " Query: " + mQuery);
        }
    }
    
    
    
    /*
    public void llenarMantenimientosP() {
        String mQuery = "";
        try {
            catmantenimientos2 = new CatMantenimientos();
            mantenimientos2 = new ArrayList<>();

            mQuery = "select mm.cod_lis_equ, mm.cod_man, mm.cod_tip, mm.det_obs, "
                    + "date_format(mm.fec_ini,'%d/%m/%Y'), "
                    + "date_format(mm.fec_fin,'%d/%m/%Y'), "
                    + "mm.det_sta, mm.cod_usu,tip.nom_tip,"
                    + "case mm.det_sta "
                    + "when 1 then 'PENDIENTE' "
                    + "when 2 then 'CANCELADO' "
                    + "when 3 then 'EN PROCESO' "
                    + "when 4 then 'FINALIZADO' "
                    + "end as status  "
                    + "from tbl_mae_man as mm "
                    + "left join cat_tip as tip on mm.cod_tip = tip.cod_tip "
                    + "where "
                    + "mm.det_sta IN (1,3) "
                    + "and mm.cod_usu = " + cbean.getCod_usu() + " "
                    + "and mm.cod_lis_equ =" + cod_lis_equ + " "
                    + "order by mm.cod_man;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                mantenimientos2.add(new CatMantenimientos(
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
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Mantenimientos Pendientes en Lista Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }
    
    
     //********************* MANTENIMIENTOS ************
     

    public void agregarmantenimiento() {
        if (validarmantenimiento()) {
            int correlativo = 0, insert = 0;
            try {
                for (int i = 0; i < mantenimientos2.size(); i++) {
                    if (Integer.valueOf(mantenimientos2.get(i).getCod_man()) > correlativo) {
                        correlativo = Integer.valueOf(mantenimientos2.get(i).getCod_man());
                    }
                }
                Accesos macc = new Accesos();
                macc.Conectar();
                String nomtip = macc.strQuerySQLvariable("select nom_tip from cat_tip where cod_tip =" + m_cod_tip + ";");
                String status = macc.strQuerySQLvariable("select case " + m_cod_tip + " when 1 then 'PENDIENTE' when 2 then 'CANCELADO' when 3 then 'EN PROCESO' when 4 then 'FINALIZADO' end as status;");
                macc.Desconectar();

                if (insert == 0) {
                    mantenimientos2.add(new CatMantenimientos(
                            cod_lis_equ,
                            String.valueOf(correlativo),
                            m_cod_tip,
                            m_det_obs,
                            m_fec_ini,
                            m_fec_fin,
                            m_det_sta,
                            cbean.getCod_usu(),
                            nomtip,
                            status
                    ));

                    m_cod_man = "";
                    m_cod_tip = "";
                    m_det_obs = "";
                    m_fec_ini = "";
                    m_fec_fin = "";
                    m_det_sta = "";

                }

            } catch (Exception e) {
                System.out.println("Error en Agregar Mantenimiento ManListaEquipos." + e.getMessage());
            }
        }

    }

    public boolean validarmantenimiento() {
        boolean mvalidar = true;
        if ("".equals(m_cod_tip)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Escoger un Tipo de Mantenimiento.", 2);
        }
        if ("".equals(m_fec_ini)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar La Fecha de Inicio del Mantenimiento.", 2);
        }
        if ("".equals(m_det_sta)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar el Estado del Mantenimiento.", 2);
        }

        return mvalidar;

    }

    public void eliminarmantenimiento() {
        m_cod_man = catmantenimientos2.getCod_man();
        catmantenimientos2 = null;
        if ("".equals(c_cod_con)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un Mantenimiento para Remover.", 2);
        } else {
            for (int i = 0; i < mantenimientos2.size(); i++) {
                if (c_cod_con.equals(mantenimientos2.get(i).getCod_man())) {
                    mantenimientos2.remove(i);
                }
            }
            m_cod_man = "";
            m_cod_tip = "";
            m_det_obs = "";
            m_fec_ini = "";
            m_fec_fin = "";
            m_det_sta = "";
        }
    }
    
    
    
    
    
    
    
    
    <p:tab title="Mantenimiento" id="tabMantenimiento">
                        <p:toolbar>
                            <f:facet name="right">
                                <p:commandButton title="Agregar" style=" width: 30px;"
                                                 update=":frmListaEquipos"
                                                 icon="ui-icon-plus" 
                                                 actionListener="#{manListaEquipos.agregarmantenimiento()}"
                                                 />
                            </f:facet>
                        </p:toolbar>
                        <p:panel id="panelmantenimientos">
                            <h:panelGrid columns="5" cellpadding="5">
                                <h:outputText value="Tipo" style="font-weight:bold;"/>
                                <h:outputText value="Observaciones" style="font-weight:bold;"/>
                                <h:outputText value="Fecha Inicio" style="font-weight:bold;"/>
                                <h:outputText value="Fecha Fin" style="font-weight:bold;"/>
                                <h:outputText value="Estado" style="font-weight:bold;"/>
                                <p:selectOneMenu value="#{manListaEquipos.m_cod_tip}" 
                                                 style=" width: 80px;">
                                    <f:selectItems value="#{manListaEquipos.tipos}" var="tiplsEqu"
                                                   itemLabel="#{tiplsEqu.nom_tip}" itemValue="#{tiplsEqu.cod_tip}"/> 
                                </p:selectOneMenu>
                                <p:inputText size="35"
                                             maxlength="200"
                                             value="#{manListaEquipos.m_det_obs}"
                                             onfocus="this.select()"
                                             />
                                <p:inputText size="20"
                                             maxlength="10"
                                             value="#{manListaEquipos.m_fec_ini}"
                                             onfocus="this.select()"
                                             />
                                <p:inputText size="20"
                                             maxlength="10"
                                             value="#{manListaEquipos.m_fec_fin}"
                                             onfocus="this.select()"
                                             />
                                <p:selectOneMenu value="#{manListaEquipos.m_det_sta}" 
                                                 style=" width: 80px;">
                                    <f:selectItem itemLabel="PENDIENTE" itemValue="1"/>
                                    <f:selectItem itemLabel="EN PROCESO" itemValue="3"/>
                                </p:selectOneMenu>
                            </h:panelGrid>
                        </p:panel>

                        <p:contextMenu for="dtEncMan">
                            <p:menuitem value="Detalle" 
                                        update=":frmMantenimientos" 
                                        icon="ui-icon-search" 
                                        onclick="PF('wMantenimientos').show()"
                                        actionListener="#{manListaEquiposDetalle.iniciarventana()}"
                                        />
                            <p:menuitem value="Eliminar" 
                                        update="panelmantenimientos,dtEncMan" 
                                        icon="ui-icon-close" 
                                        actionListener="#{manListaEquipos.eliminarmantenimiento()}"/>
                        </p:contextMenu>
                        <p:dataTable id="dtEncMan" var="vEncMan" 
                                     value="#{manListaEquipos.mantenimientos2}" 
                                     emptyMessage="No se encontraron resultados" 
                                     selection="#{manListaEquipos.catmantenimientos2}" 
                                     rowKey="#{vEncMan.cod_man}"
                                     editable="true"
                                     scrollable="true"
                                     scrollHeight="170"
                                     style="width:787px"
                                     >

                            <p:ajax event="rowSelectRadio" listener="#{manListaEquipos.onRowSelectMan}" 
                                    update=":frmListaEquipos:tvLE:panelmantenimientos"/>

                            <p:column selectionMode="single" style="width:16px;text-align:center"/>
                            <p:column style="width:60px;" headerText="Tipo">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.nomtip}" /></f:facet>
                                    <f:facet name="input">
                                        <p:selectOneMenu style=" width: 100%;">
                                            <f:selectItems value="#{manListaEquipos.tipos}" var="tiplsEqu"
                                                           itemLabel="#{tiplsEqu.nom_tip}" itemValue="#{tiplsEqu.cod_tip}"/> 
                                        </p:selectOneMenu>
                                    </f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:130px" headerText="Observaciones">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.det_obs}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vEncMan.det_obs}" style="width:100%" /></f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:60px" headerText="Fecha Inicio">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.fec_ini}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vEncMan.fec_ini}" style="width:100%" required="true"/></f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:60px" headerText="Fecha Fin">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.fec_fin}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vEncMan.fec_fin}" style="width:100%" /></f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:60px" headerText="Estado">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.status}" /></f:facet>
                                    <f:facet name="input">
                                        <p:selectOneMenu style=" width: 100%;">
                                            <f:selectItem itemLabel="PENDIENTE" itemValue="1"/>
                                            <f:selectItem itemLabel="EN PROCESO" itemValue="3"/> 
                                        </p:selectOneMenu>
                                    </f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:32px">
                                <p:rowEditor />
                            </p:column>
                        </p:dataTable>
                    </p:tab>
    
    
    
    
    <!--******************************************************************** 
        ************************* Pantalla xxxxxxxxxxxxx ***********************
        *********************************************************************-->

        <p:dialog  id="dlgMantenimiento" widgetVar="wMantenimiento" modal="true" 
                   width="850" height="460" closable="true" resizable="false" 
                   header="Mantenimientos" rendered="true" 
                   dynamic="true" visible="false"  
                   >
            <h:form id="frmMantenimiento">
                <p:scrollPanel style="width: 100%; min-height: 50px;border: none;"
                               mode="native">
                    <ui:include src="mantenimiento.xhtml" />
                </p:scrollPanel>
            </h:form>
        </p:dialog>
        
        <!--******************************************************************** 
        ************************** Pantalla Mantenimientos *********************
        *********************************************************************-->

        <p:dialog  id="dlgMantenimientos" widgetVar="wMantenimientos" modal="true" 
                   width="860" height="510" closable="true" resizable="false" 
                   header="Mantenimiento #{manListaEquipos.nombre_mantenimiento}" rendered="true" 
                   dynamic="false" visible="false" 
                   >
            <p:ajax event="close" update=":frmMantenimientos" listener="#{manListaEquiposDetalle.cerrarventana()}" />
            <h:form id="frmMantenimientos">
                <p:growl id="msgMantenimientos" showDetail="true" life="4500"/>

                <p:panel style=" line-height: 0.3;">
                    <h:panelGrid columns="3" cellpadding="5"  style="line-height: 4pt;">
                        <h:outputText value="Tipo" style="font-weight:bold;"/>
                        <h:outputText value="Fecha Inicio" style="font-weight:bold;"/>
                        <h:outputText value="Estado" style="font-weight:bold;"/>

                        <p:inputText value="#{manListaEquipos.m_nomtip}" style="font-weight:bold;" readonly="true" size="15"/>
                        <p:inputText value="#{manListaEquipos.m_fec_ini}" style="font-weight:bold;" readonly="true" size="15"/>
                        <p:inputText value="#{manListaEquipos.m_estado}" style="font-weight:bold;" readonly="true" size="15"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" cellpadding="5">
                        <h:outputText value="Observaciones" style="font-weight:bold;"/>
                        <p:inputTextarea value="#{manListaEquipos.m_det_obs}" style="font-weight:bold;" readonly="true" cols="110" rows="2"/>
                    </h:panelGrid>
                </p:panel>
                <p:tabView id="tvDetMan" activeIndex="#{manListaEquiposDetalle.tabindex}" >
                    <p:tab id="tabDetalleGen" title="General">
                        <p:panel  style=" line-height: 1.1;">
                            <h:panelGrid columns="4" cellpadding="5">
                                <h:outputText value="Fecha" style="font-weight:bold;"/>
                                <h:outputText value="Hora" style="font-weight:bold;"/>
                                <h:outputText value="Operación" style="font-weight:bold;"/>
                                <h:outputText value="Realizó" style="font-weight:bold;"/>
                                <p:calendar  pattern="dd/MM/yyyy" locale="es" readonlyInput="true" showOn="button" size="10"
                                             style="width: 10px"/> 
                                <p:calendar  pattern="HH:mm" timeOnly="true" readonlyInput="true" showOn="button" style="width: 10px" size="10"/>
                                <p:selectOneMenu style=" width: 160px;">
                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                    <f:selectItems value="#{manListaEquiposDetalle.operaciones}" var="tipope"
                                                   itemLabel="#{tipope.nom_ope}" itemValue="#{tipope.cod_ope}"/> 
                                </p:selectOneMenu>
                                <p:selectOneMenu style=" width: 200px;" value="#{manListaEquiposDetalle.cod_usu}">
                                    <f:selectItem itemLabel="Seleccione Uno" itemValue="0"/>
                                    <f:selectItems value="#{manListaEquiposDetalle.usuarios}" var="usudet"
                                                   itemLabel="#{usudet.det_nom}" itemValue="#{usudet.cod_usu}"/> 
                                </p:selectOneMenu>
                            </h:panelGrid>
                            <h:panelGrid columns="1" cellpadding="5">
                                <h:outputText value="Observaciones" style="font-weight:bold;"/>
                                <p:inputText maxlength="200" size="123" />
                            </h:panelGrid>
                        </p:panel>
                        <p:dataTable id="dtDetGen" var="vDetGen" 
                                     value="#{manListaEquiposDetalle.general}" 
                                     emptyMessage="No se encontraron resultados" 
                                     selection="#{manListaEquiposDetalle.catmantenimientosgen}" 
                                     selectionMode="single"
                                     rowKey="#{vDetGen.det_man}"
                                     editable="false"
                                     scrollable="true"
                                     scrollHeight="80"
                                     style="width:790px"
                                     >

                            <p:column style="width:15px;" headerText="Fecha">
                                <h:outputText value="#{vEncMan.nomtip}" />
                            </p:column>
                            <p:column style="width:15px" headerText="Hora">
                                <h:outputText value="#{vEncMan.nomtip}" />
                            </p:column>
                            <p:column style="width:50px" headerText="Operación">
                                <h:outputText value="#{vEncMan.nomtip}" />
                            </p:column>
                            <p:column style="width:80px" headerText="Observaciones">
                                <h:outputText value="#{vEncMan.nomtip}" />
                            </p:column>
                            <p:column style="width:50px" headerText="Realizó">
                                <h:outputText value="#{vEncMan.nomtip}" />
                            </p:column>
                        </p:dataTable>
                    </p:tab>
                    <p:tab id="tabDetallePie" title="Piezas">
                        <p:panel>
                            <h:panelGrid columns="5" cellpadding="5">
                                <h:outputText value="Fecha y hora" style="font-weight:bold;"/>
                                <h:outputText value="Responsable" style="font-weight:bold;"/>
                                <h:outputText value="País" style="font-weight:bold;"/>
                                <h:outputText value="Bodega" style="font-weight:bold;"/>
                                <h:outputText value="Ubicación" style="font-weight:bold;"/>
                                <p:calendar  pattern="dd/MM/yyyy HH:mm" locale="es" 
                                             readonlyInput="true" showOn="button" size="14"
                                             style="width: 10px"/> 
                                <p:selectOneMenu style=" width: 120px;">
                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                </p:selectOneMenu>
                                <p:selectOneMenu style=" width: 110px;">
                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                </p:selectOneMenu>
                                <p:selectOneMenu style=" width: 110px;">
                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                </p:selectOneMenu>
                                <p:selectOneMenu style=" width: 120px;">
                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                </p:selectOneMenu>
                            </h:panelGrid>

                            <h:panelGrid columns="3" cellpadding="5">
                                <h:outputText value="Cantidad" style="font-weight:bold;"/>
                                <h:outputText value="Pieza Entrante" style="font-weight:bold;"/>
                                <h:outputText value="Números de Serie" style="font-weight:bold;"/>
                                <p:inputText maxlength="12" size="10" />
                                <p:selectOneMenu style=" width: 270px;">
                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                </p:selectOneMenu>
                                <p:inputText maxlength="200" size="60" />
                            </h:panelGrid>
                        </p:panel>
                        <p:dataTable id="dtDetPie" var="vDetPie" 
                                     value="#{manListaEquiposDetalle.piezas}" 
                                     emptyMessage="No se encontraron resultados" 
                                     selection="#{manListaEquiposDetalle.catmantenimientospie}" 
                                     selectionMode="single"
                                     rowKey="#{vDetPie.det_man}"
                                     editable="true"
                                     scrollable="true"
                                     scrollHeight="80"
                                     style="width:790px"
                                     >
                            <p:column style="width:20px" headerText="Cantidad">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vDetPie.det_can}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vDetPie.det_can}" style="width:100%" /></f:facet>
                                </p:cellEditor>
                            </p:column>

                            <p:column style="width:100px;" headerText="Pieza">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vDetPie.nompie}" /></f:facet>
                                    <f:facet name="input">
                                        <p:selectOneMenu style=" width: 100%;" value="#{vDetPie.cod_lis_pie}">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                        </p:selectOneMenu>
                                    </f:facet>
                                </p:cellEditor>
                            </p:column>

                            <p:column style="width:80px" headerText="Series">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vDetPie.num_ser}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vDetPie.num_ser}" style="width:100%"/></f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:32px">
                                <p:rowEditor />
                            </p:column>
                        </p:dataTable>
                    </p:tab>
                    <p:tab id="tabDetalleAne" title="Anexos">
                        <p:panel>
                            <h:panelGrid columns="2" cellpadding="5" >
                                <h:outputText value="Tipo" style="font-weight:bold;"/>
                                <h:outputText value="Observaciones" style="font-weight:bold;"/>
                                <p:selectOneMenu style=" width: 120px;" value="#{manListaEquiposDetalle.a_tip_ane}">
                                    <f:selectItem itemLabel="Seleccione Uno" itemValue="0"/>
                                    <f:selectItem itemLabel="PDF" itemValue="1"/>
                                    <f:selectItem itemLabel="IMAGEN" itemValue="2"/>
                                    <f:selectItem itemLabel="OTRO" itemValue="3"/>
                                </p:selectOneMenu>
                                <p:inputText value="#{manListaEquiposDetalle.a_det_obs}" style="font-weight:bold;" size="95" maxlength="200"/>
                            </h:panelGrid>
                            <p:fileUpload id="fupman" 
                                          fileUploadListener="#{manEquipos.upload}" 
                                          mode="advanced" dragDropSupport="true"
                                          fileLimit="1" 
                                          label="Seleccionar" cancelLabel="Cancelar" uploadLabel="Subir" 
                                          />
                        </p:panel>
                        <p:dataTable id="dtDetAne" var="vDetAne" 
                                     value="#{manListaEquiposDetalle.anexos}" 
                                     emptyMessage="No se encontraron resultados" 
                                     selection="#{manListaEquiposDetalle.catmantenimientosane}" 
                                     selectionMode="single"
                                     rowKey="#{vDetPie.det_man}"
                                     editable="true"
                                     scrollable="true"
                                     scrollHeight="100"
                                     style="width:790px"
                                     >
                            <p:column style="width:10px;" headerText="Tipo">
                                <h:outputText value="#{vDetPie.det_can}" />
                            </p:column>
                            <p:column style="width:150px" headerText="Observaciones">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vDetAne.det_obs}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vDetAne.det_obs}" style="width:100%" /></f:facet>
                                </p:cellEditor>
                            </p:column>
                        </p:dataTable>
                    </p:tab>
                </p:tabView>


            </h:form>

        </p:dialog>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     <!--******************************************************************** 
        ************************* Pantalla Maestra ***********************
        *********************************************************************-->

        <p:dialog  id="dlgMaestraMan" widgetVar="wMaestra" modal="true" 
                   width="1100" height="480" closable="false" resizable="false" 
                   header="Mantenimientos" rendered="true" 
                   dynamic="false" visible="false" 
                   >
            <h:form id="frmMaestra" >
                <p:growl id="msgMaestra" showDetail="true" life="4500"/>

                <h:panelGrid columns="2" cellpadding="2">
                    <h:panelGrid columns="1" styleClass="noBorders">
                        <h:outputText value="Número de Serie" style="font-weight:bold;"/>
                        <p:inputText id="txtbusns" maxlength="12" size="34" value="#{manMaestroMan.buscar_serie}"/>
                        <p:commandButton value="Buscar" icon="ui-icon-search" 
                                         style="width: 200px;" 
                                         actionListener="#{manMaestroMan.llenarMantenimientos()}"
                                         update="dtEncMan,tvDetMan2"
                                         />
                        <p:tree value="#{manMaestroMan.root}" var="node" dynamic="true" style=" width: 198px;" 
                                selectionMode="single"
                                selection="#{manMaestroMan.selectednode}"
                                >
                            <p:ajax event="select" update=":frmMaestra:txtbusns,:frmMaestra:dtEncMan,:frmMaestra:tvDetMan2" listener="#{manMaestroMan.onNodeSelect}"  />
                            <p:treeNode collapsedIcon="ui-icon ui-icon-folder-collapsed" expandedIcon="ui-icon ui-icon-folder-open">
                                <h:outputText value="#{node}"  />
                            </p:treeNode>
                        </p:tree>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="noBorders">
                        <p:toolbar>
                            <f:facet name="left">
                                <p:commandButton value="Aprobar" style=" width: 100px;"
                                                 actionListener="#{manAprobacion.aprobar()}"
                                                 update=":frmAprobado"
                                                 icon="ui-icon-circle-check"
                                                 > 
                                    <p:confirm header="Aprobar Solicitud" 
                                               message="¿Realmente desea Aprobar esta Solicitud?" 
                                               icon="ui-icon-alert"/>
                                </p:commandButton>

                                <p:commandButton value="Denegar" style=" width: 100px;"
                                                 actionListener="#{manAprobacion.rechazar()}"
                                                 update=":frmAprobado"
                                                 icon="ui-icon-circle-close"
                                                 > 
                                    <p:confirm header="Denegar Solicitud" 
                                               message="¿Realmente desea Denegar esta Solicitud?" 
                                               icon="ui-icon-alert"/>
                                </p:commandButton>

                                <p:commandButton value="Actualizar" style=" width: 100px;"

                                                 update=":frmMaestra"
                                                 icon="ui-icon-refresh"
                                                 /> 

                                <p:commandButton value="Cerrar" style=" width: 100px;"
                                                 onclick="PF('wAprobado').hide()"
                                                 actionListener="#{manAprobacion.cerrarventana()}"
                                                 update=":frmAprobado"
                                                 icon="ui-icon-close"
                                                 />
                                <p:confirmDialog global="true" showEffect="fade" hideEffect="fade">
                                    <p:commandButton value="Sí" type="button" 
                                                     styleClass="ui-confirmdialog-yes" 
                                                     icon="ui-icon-check" />
                                    <p:commandButton value="No" type="button" 
                                                     styleClass="ui-confirmdialog-no" 
                                                     icon="ui-icon-close" />
                                </p:confirmDialog>
                            </f:facet>

                        </p:toolbar>
                        <p:dataTable id="dtEncMan" var="vEncMan" 
                                     value="#{manMaestroMan.mantenimientos}" 
                                     emptyMessage="No se encontraron resultados" 
                                     selection="#{manMaestroMan.catmantenimientos}" 
                                     selectionMode="single"
                                     rowKey="#{vEncMan.cod_man}"
                                     editable="true"
                                     scrollable="true"
                                     scrollHeight="100"
                                     style="width:820px;"
                                     >
                            <p:column style="width:60px" headerText="Fecha Inicio">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.fec_ini}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vEncMan.fec_ini}" style="width:100%" required="true"/></f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:60px;" headerText="Tipo">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.nomtip}" /></f:facet>
                                    <f:facet name="input">
                                        <p:selectOneMenu style=" width: 100%;">
                                            <f:selectItems value="#{manMaestroMan.tipos}" var="tiplsEqu"
                                                           itemLabel="#{tiplsEqu.nom_tip}" itemValue="#{tiplsEqu.cod_tip}"/> 
                                        </p:selectOneMenu>
                                    </f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:200px" headerText="Observaciones">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.det_obs}" /></f:facet>
                                    <f:facet name="input"><p:inputText value="#{vEncMan.det_obs}" style="width:100%" /></f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:60px" headerText="Estado">
                                <p:cellEditor>
                                    <f:facet name="output"><h:outputText value="#{vEncMan.status}" /></f:facet>
                                    <f:facet name="input">
                                        <p:selectOneMenu style=" width: 100%;">
                                            <f:selectItem itemLabel="PENDIENTE" itemValue="1"/>
                                            <f:selectItem itemLabel="EN PROCESO" itemValue="3"/> 
                                        </p:selectOneMenu>
                                    </f:facet>
                                </p:cellEditor>
                            </p:column>
                            <p:column style="width:32px;">
                                <p:rowEditor/>
                            </p:column>
                        </p:dataTable>
                        <p:tabView id="tvDetMan2" activeIndex="#{manMaestroMan.tabindex}" >
                            <p:tab id="tabDetalleGen2" title="Detalle Técnico">
                                <p:panel   >
                                    <h:panelGrid columns="4" cellpadding="5">
                                        <h:outputText value="Fecha" style="font-weight:bold;"/>
                                        <h:outputText value="Hora" style="font-weight:bold;"/>
                                        <h:outputText value="Operación" style="font-weight:bold;"/>
                                        <h:outputText value="Realizó" style="font-weight:bold;"/>
                                        <p:calendar  pattern="dd/MM/yyyy" locale="es" readonlyInput="true" showOn="button" size="10"
                                                     style="width: 10px"/> 
                                        <p:calendar  pattern="HH:mm" timeOnly="true" readonlyInput="true" showOn="button" style="width: 10px" size="10"/>
                                        <h:selectOneMenu style=" width: 200px;">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                            <f:selectItems value="#{manMaestroMan.operaciones}" var="tipope"
                                                           itemLabel="#{tipope.nom_ope}" itemValue="#{tipope.cod_ope}"/> 
                                        </h:selectOneMenu>
                                        <h:selectOneMenu style=" width: 210px;" value="#{manListaEquiposDetalle.cod_usu}">
                                            <f:selectItem itemLabel="Seleccione Uno" itemValue="0"/>
                                            <f:selectItems value="#{manMaestroMan.usuarios}" var="usudet"
                                                           itemLabel="#{usudet.det_nom}" itemValue="#{usudet.cod_usu}"/> 
                                        </h:selectOneMenu>
                                    </h:panelGrid>
                                    <h:panelGrid columns="1" cellpadding="5">
                                        <h:outputText value="Observaciones" style="font-weight:bold;"/>
                                        <p:inputText maxlength="200" size="125" />
                                    </h:panelGrid>
                                </p:panel>
                                <p:dataTable id="dtDetGen2" var="vDetGen" 
                                             value="#{manMaestroMan.general}" 
                                             emptyMessage="No se encontraron resultados" 
                                             selection="#{manMaestroMan.catmantenimientosgen}" 
                                             selectionMode="single"
                                             rowKey="#{vDetGen.det_man}"
                                             editable="false"
                                             scrollable="true"
                                             scrollHeight="80"
                                             style="width:790px"
                                             >

                                    <p:column style="width:15px;" headerText="Fecha">
                                        <h:outputText value="#{vEncMan.nomtip}" />
                                    </p:column>
                                    <p:column style="width:15px" headerText="Hora">
                                        <h:outputText value="#{vEncMan.nomtip}" />
                                    </p:column>
                                    <p:column style="width:50px" headerText="Operación">
                                        <h:outputText value="#{vEncMan.nomtip}" />
                                    </p:column>
                                    <p:column style="width:80px" headerText="Observaciones">
                                        <h:outputText value="#{vEncMan.nomtip}" />
                                    </p:column>
                                    <p:column style="width:50px" headerText="Realizó">
                                        <h:outputText value="#{vEncMan.nomtip}" />
                                    </p:column>
                                </p:dataTable>
                            </p:tab>
                            <p:tab id="tabDetallePie2" title="Piezas">
                                <p:panel>
                                    <h:panelGrid columns="5" cellpadding="5">
                                        <h:outputText value="Fecha y hora" style="font-weight:bold;"/>
                                        <h:outputText value="Responsable" style="font-weight:bold;"/>
                                        <h:outputText value="País" style="font-weight:bold;"/>
                                        <h:outputText value="Bodega" style="font-weight:bold;"/>
                                        <h:outputText value="Ubicación" style="font-weight:bold;"/>
                                        <p:calendar  pattern="dd/MM/yyyy HH:mm" locale="es" 
                                                     readonlyInput="true" showOn="button" size="14"
                                                     style="width: 10px"/> 
                                        <h:selectOneMenu style=" width: 120px;">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                        </h:selectOneMenu>
                                        <h:selectOneMenu style=" width: 110px;">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                        </h:selectOneMenu>
                                        <h:selectOneMenu style=" width: 110px;">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                        </h:selectOneMenu>
                                        <h:selectOneMenu style=" width: 120px;">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                        </h:selectOneMenu>
                                    </h:panelGrid>

                                    <h:panelGrid columns="3" cellpadding="5">
                                        <h:outputText value="Cantidad" style="font-weight:bold;"/>
                                        <h:outputText value="Pieza Entrante" style="font-weight:bold;"/>
                                        <h:outputText value="Números de Serie" style="font-weight:bold;"/>
                                        <p:inputText maxlength="12" size="10" />
                                        <h:selectOneMenu style=" width: 270px;">
                                            <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                        </h:selectOneMenu>
                                        <p:inputText maxlength="200" size="60" />
                                    </h:panelGrid>
                                </p:panel>
                                <p:dataTable id="dtDetPie2" var="vDetPie" 
                                             value="#{manMaestroMan.piezas}" 
                                             emptyMessage="No se encontraron resultados" 
                                             selection="#{manMaestroMan.catmantenimientospie}" 
                                             selectionMode="single"
                                             rowKey="#{vDetPie.det_man}"
                                             editable="true"
                                             scrollable="true"
                                             scrollHeight="80"
                                             style="width:790px"
                                             >
                                    <p:column style="width:20px" headerText="Cantidad">
                                        <p:cellEditor>
                                            <f:facet name="output"><h:outputText value="#{vDetPie.det_can}" /></f:facet>
                                            <f:facet name="input"><p:inputText value="#{vDetPie.det_can}" style="width:100%" /></f:facet>
                                        </p:cellEditor>
                                    </p:column>

                                    <p:column style="width:100px;" headerText="Pieza">
                                        <p:cellEditor>
                                            <f:facet name="output"><h:outputText value="#{vDetPie.nompie}" /></f:facet>
                                            <f:facet name="input">
                                                <p:selectOneMenu style=" width: 100%;" value="#{vDetPie.cod_lis_pie}">
                                                    <f:selectItem itemLabel="Seleccione Una" itemValue="0"/>
                                                </p:selectOneMenu>
                                            </f:facet>
                                        </p:cellEditor>
                                    </p:column>

                                    <p:column style="width:80px" headerText="Series">
                                        <p:cellEditor>
                                            <f:facet name="output"><h:outputText value="#{vDetPie.num_ser}" /></f:facet>
                                            <f:facet name="input"><p:inputText value="#{vDetPie.num_ser}" style="width:100%"/></f:facet>
                                        </p:cellEditor>
                                    </p:column>
                                    <p:column style="width:32px">
                                        <p:rowEditor />
                                    </p:column>
                                </p:dataTable>
                            </p:tab>
                            <p:tab id="tabDetalleAne2" title="Anexos">
                                <p:panel>
                                    <h:panelGrid columns="2" cellpadding="5" >
                                        <h:outputText value="Tipo" style="font-weight:bold;"/>
                                        <h:outputText value="Observaciones" style="font-weight:bold;"/>
                                        <h:selectOneMenu style=" width: 120px;" value="#{manListaEquiposDetalle.a_tip_ane}">
                                            <f:selectItem itemLabel="Seleccione Uno" itemValue="0"/>
                                            <f:selectItem itemLabel="PDF" itemValue="1"/>
                                            <f:selectItem itemLabel="IMAGEN" itemValue="2"/>
                                            <f:selectItem itemLabel="OTRO" itemValue="3"/>
                                        </h:selectOneMenu>
                                        <p:inputText value="#{manListaEquiposDetalle.a_det_obs}" style="font-weight:bold;" size="90" maxlength="200"/>
                                    </h:panelGrid>
                                    <p:fileUpload id="fupman2" 
                                                  fileUploadListener="#{manEquipos.upload}" 
                                                  mode="advanced" dragDropSupport="true"
                                                  fileLimit="1" 
                                                  label="Seleccionar" cancelLabel="Cancelar" uploadLabel="Subir" 
                                                  />
                                </p:panel>
                                <p:dataTable id="dtDetAne2" var="vDetAne" 
                                             value="#{manListaEquiposDetalle.anexos}" 
                                             emptyMessage="No se encontraron resultados" 
                                             selection="#{manListaEquiposDetalle.catmantenimientosane}" 
                                             selectionMode="single"
                                             rowKey="#{vDetPie.det_man}"
                                             editable="true"
                                             scrollable="true"
                                             scrollHeight="90"
                                             style="width:790px"
                                             >
                                    <p:column style="width:10px;" headerText="Tipo">
                                        <h:outputText value="#{vDetPie.det_can}" />
                                    </p:column>
                                    <p:column style="width:150px" headerText="Observaciones">
                                        <p:cellEditor>
                                            <f:facet name="output"><h:outputText value="#{vDetAne.det_obs}" /></f:facet>
                                            <f:facet name="input"><p:inputText value="#{vDetAne.det_obs}" style="width:100%" /></f:facet>
                                        </p:cellEditor>
                                    </p:column>
                                </p:dataTable>
                            </p:tab>
                        </p:tabView>
                    </h:panelGrid>
                </h:panelGrid>

            </h:form>
        </p:dialog>
    
    
    */

}
