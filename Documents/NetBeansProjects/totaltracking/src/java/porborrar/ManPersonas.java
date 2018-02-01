package paquetes;

import tt.seguridad.CatUsuarios;
import tt.general.Accesos;
import tt.general.Login;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@ConversationScoped
public class ManPersonas implements Serializable {

    private static final long serialVersionUID = 8797678674711638L;
    @Inject
    Login cbean;
    private CatPersonas catpersonas;
    private List<CatPersonas> personas;
    private CatCargos catcargos;
    private List<CatCargos> cargos;
    private CatUsuarios catusuarios;
    private List<CatUsuarios> usuarios;
    private String id_per, nombres, apellidos, direccion, telefono, celular, email, dui, nit, isss, cod_dep, id_jef, fingreso, codigo, id_car, usuario;
    private Date dfingreso;
    
    public ManPersonas() {
    }

    public CatPersonas getCatpersonas() {
        return catpersonas;
    }

    public void setCatpersonas(CatPersonas catpersonas) {
        this.catpersonas = catpersonas;
    }

    public List<CatPersonas> getPersonas() {
        return personas;
    }

    public void setPersonas(List<CatPersonas> personas) {
        this.personas = personas;
    }

    public CatCargos getCatcargos() {
        return catcargos;
    }

    public void setCatcargos(CatCargos catcargos) {
        this.catcargos = catcargos;
    }

    public List<CatCargos> getCargos() {
        return cargos;
    }

    public void setCargos(List<CatCargos> cargos) {
        this.cargos = cargos;
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
    
    public String getId_per() {
        return id_per;
    }

    public void setId_per(String id_persona) {
        this.id_per = id_persona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getIsss() {
        return isss;
    }

    public void setIsss(String isss) {
        this.isss = isss;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getId_jef() {
        return id_jef;
    }

    public void setId_jef(String id_jef) {
        this.id_jef = id_jef;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
    public String getId_car() {
        return id_car;
    }

    public void setId_car(String id_car) {
        this.id_car = id_car;
    }

    public String getFingreso() {
        return fingreso;
    }

    public void setFingreso(String fingreso) {
        this.fingreso = fingreso;
    }

    public Date getDfingreso() {
        return dfingreso;
    }

    public void setDfingreso(Date dfingreso) {
        this.dfingreso = dfingreso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    public void iniciarventana() {
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        id_per = "";
        nombres = "";
        apellidos = "";
        direccion = "";
        telefono = "";
        celular = "";
        email = "";
        dui = "";
        nit = "";
        isss = "";
        codigo = "";
        cod_dep = "";
        id_jef = "";
        dfingreso = Date.from(Instant.now());
        id_car = "";
        usuario = "";
        fingreso = format.format(dfingreso);
        llenarCargos();
        llenarUsuarios();
        llenarPersonas();
    }

    public void cerrarventana() {
        id_per = "";
        nombres = "";
        apellidos = "";
        direccion = "";
        telefono = "";
        celular = "";
        email = "";
        dui = "";
        nit = "";
        isss = "";
        codigo = "";
        cod_dep = "";
        id_jef = "";
        fingreso = "";
        id_car = "";
        usuario = "";
        personas = new ArrayList<>();
    }

    public void llenarCargos() {
        String mQuery = "";
        try {
            catcargos = new CatCargos();
            cargos = new ArrayList<>();

            mQuery = "select id_car, des_car from cat_car order by id_car;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                cargos.add(new CatCargos(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Cargos de Personal. " + e.getMessage() + " Query: " + mQuery);
        }
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
            System.out.println("Error en el llenado de Catálogo de Usuarios. " + e.getMessage());
        }
    }
    
    public void llenarPersonas() {
        String mQuery = "";
        try {
            catpersonas = new CatPersonas();
            personas = new ArrayList<>();

            mQuery = "select id_per, nombres, apellidos, direccion, "
                    + "telefono, celular, email, dui, nit, isss, codigo, "
                    + "cod_dep, id_jef, fingreso, id_car, cod_usu "
                    + "from cat_persona order by id_per;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                personas.add(new CatPersonas(
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
                        resVariable.getString(16)
                ));
            }
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Registro de Personas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        id_per = "";
        nombres = "";
        apellidos = "";
        direccion = "";
        telefono = "";
        celular = "";
        email = "";
        dui = "";
        nit = "";
        isss = "";
        codigo = "";
        cod_dep = "";
        id_jef = "";
        fingreso = "";
        id_car = "";
        id_car = "";
        usuario = "";
        catpersonas = new CatPersonas();
    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(id_per)) {
                    mQuery = "select ifnull(max(id_per),0)+1 as codigo from cat_persona;";
                    id_per = mAccesos.strQuerySQLvariable(mQuery);
                    mQuery = "insert into cat_persona (id_per, nombres, apellidos, direccion, telefono, celular, email, dui, nit, isss, codigo, cod_dep, id_jef, fingreso, id_car, cod_usu) "
                            + "values (" + id_per + ",'" + nombres + "','" + apellidos + "','" + direccion + "','" + telefono 
                            + "','" + celular + "','" + email + "','" + dui + "','" + nit + "','" + isss + "','" + codigo + "'," + cod_dep + "," + id_jef + ", " + "str_to_date('" + fingreso + "','%d/%m/%Y')" + "," + id_car + ",'" + usuario + "');";
                } else {
                    mQuery = "update cat_persona SET "
                            + " nombres = '" + nombres + "',"
                            + " apellidos = '" + apellidos + "',"
                            + " direccion = '" + direccion + "',"
                            + " telefono = '" + telefono + "',"
                            + " celular = '" + celular + "',"
                            + " email = '" + email + "',"
                            + " dui = '" + dui + "',"
                            + " nit = '" + nit + "',"
                            + " isss = '" + isss + "',"
                            + " codigo = '" + codigo + "',"
                            + " cod_dep = " + cod_dep + ","
                            + " id_jef = '" + id_jef + "',"
                            + " fingreso = " + "str_to_date('" + fingreso + "','%d/%m/%Y'),"
                            + " id_car = " + id_car + ","
                            + " cod_usu = " + usuario 
                            + " WHERE id_per = " + id_per + ";";

                }
                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Guardar Persona", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Persona", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Persona. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarPersonas();
            nuevo();
        }
 
    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(id_per) == false) {
            try {
                mQuery = "delete from cat_persona where id_per=" + id_per + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Eliminar Persona", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar Persona", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Persona. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarPersonas();
            nuevo();
        } else {
            addMessage("Eliminar Persona", "Debe elegir un Registro.", 2);
        }
        mAccesos.Desconectar();

    }

    public void dateSelectedFingreso(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fingreso = format.format(date);
    }
    
    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nombres) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar Nombre de la persona.", 2);
        }
        
        if ("".equals(apellidos) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar Apellidos de la persona.", 2);
        }
        
        if ("".equals(dui) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar DUI de la persona.", 2);
        }
        
        if ("".equals(email) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar E-Mail de la persona.", 2);
        }
        if ("0".equals(id_car) == true) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar El cargo de la persona.", 2);
        }
        
        Accesos maccesos = new Accesos();
        maccesos.Conectar();
        if ("0".equals(maccesos.strQuerySQLvariable("select count(id_per) from cat_persona "
                + "where dui='" + dui + "';")) == false && "".equals(id_per)) {
            mValidar = false;
            addMessage("Validar Datos", "El DUI de la Persona ya existe.", 2);
        }
        maccesos.Desconectar();
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        id_per = ((CatPersonas) event.getObject()).getId_per();
        nombres = ((CatPersonas) event.getObject()).getNombres();
        apellidos = ((CatPersonas) event.getObject()).getApellidos();
        direccion = ((CatPersonas) event.getObject()).getDireccion();
        telefono = ((CatPersonas) event.getObject()).getTelefono();
        celular = ((CatPersonas) event.getObject()).getCelular();
        email = ((CatPersonas) event.getObject()).getEmail();
        dui = ((CatPersonas) event.getObject()).getDui();
        nit = ((CatPersonas) event.getObject()).getNit();
        isss = ((CatPersonas) event.getObject()).getIsss();
        codigo = ((CatPersonas) event.getObject()).getCodigo();
        cod_dep = ((CatPersonas) event.getObject()).getCod_dep();
        fingreso = ((CatPersonas) event.getObject()).getFingreso();
        id_jef = ((CatPersonas) event.getObject()).getId_jef();
        id_car = ((CatPersonas) event.getObject()).getId_car();
        usuario = ((CatPersonas) event.getObject()).getUsuario();
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
}