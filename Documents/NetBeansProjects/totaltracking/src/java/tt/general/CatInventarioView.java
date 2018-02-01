package tt.general;

import java.io.Serializable;

public class CatInventarioView implements Serializable {

    private String pais, bodega, equipo, categoria, referencia, nombre, existencia, pendiente;

    public CatInventarioView() {
    }

    public CatInventarioView(String pais, String bodega, String equipo, String categoria, String referencia, String nombre, String existencia, String pendiente) {
        this.pais = pais;
        this.bodega = bodega;
        this.equipo = equipo;
        this.categoria = categoria;
        this.referencia = referencia;
        this.nombre = nombre;
        this.existencia = existencia;
        this.pendiente = pendiente;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
