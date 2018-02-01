
package paquetes;

import java.io.Serializable;
import java.util.Date;

public class CatPersonas implements Serializable{
    
    private String id_per, nombres, apellidos, direccion, telefono, celular, email, dui, nit, isss, cod_dep, codigo, fingreso, id_jef, id_car, usuario;
        
    public CatPersonas() {
    }

    public CatPersonas(String id_per, String nombres, String apellidos, String direccion, String telefono, String celular, String email, String dui, String nit, String isss, String cod_dep, String codigo, String fingreso, String id_jef, String id_car, String usuario) {
        this.id_per = id_per;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.dui = dui;
        this.nit = nit;
        this.isss = isss;
        this.cod_dep = cod_dep;
        this.codigo = codigo;
        this.fingreso = fingreso;
        this.id_jef = id_jef;        
        this.id_car = id_car;
        this.usuario = usuario;
    }

    public String getId_per() {
        return id_per;
    }

    public void setId_per(String id_per) {
        this.id_per = id_per;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFingreso() {
        return fingreso;
    }

    public void setFingreso(String fingreso) {
        this.fingreso = fingreso;
    }

    public String getId_jef() {
        return id_jef;
    }

    public void setId_gef(String id_jef) {
        this.id_jef = id_jef;
    }
    
    

    public String getId_car() {
        return id_car;
    }

    public void setId_car(String id_car) {
        this.id_car = id_car;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
