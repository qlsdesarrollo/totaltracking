
package tt.mantenimiento;

import java.io.Serializable;

public class CatPiezasAbreviado implements Serializable{
    
    private String codigo, referencia, nombre, existencia, pendiente;

    public CatPiezasAbreviado() {
    }

    public CatPiezasAbreviado(String codigo, String referencia, String nombre, String existencia, String pendiente) {
        this.codigo = codigo;
        this.referencia = referencia;
        this.nombre = nombre;
        this.existencia = existencia;
        this.pendiente = pendiente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getPendiente() {
        return pendiente;
    }

    public void setPendiente(String pendiente) {
        this.pendiente = pendiente;
    }
    
    
    
}
