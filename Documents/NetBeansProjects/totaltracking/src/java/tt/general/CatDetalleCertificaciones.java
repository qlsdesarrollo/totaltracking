package tt.general;

import java.io.Serializable;

public class CatDetalleCertificaciones implements Serializable{
    
    private String id_det_com, id_per, id_com, titulo, institucion, ano_graduacion, no_acreditacion;

    public CatDetalleCertificaciones() {
    }

    public CatDetalleCertificaciones(String id_det_com, String id_per, String id_com, String titulo, String institucion, String ano_graduacion, String no_acreditacion) {
        this.id_det_com = id_det_com;
        this.id_per = id_per;
        this.id_com = id_com;
        this.titulo = titulo;
        this.institucion = institucion;
        this.ano_graduacion = ano_graduacion;
        this.no_acreditacion = no_acreditacion;
    }

    public String getId_det_com() {
        return id_det_com;
    }

    public void setId_det_com(String id_det_com) {
        this.id_det_com = id_det_com;
    }

    public String getId_per() {
        return id_per;
    }

    public void setId_per(String id_per) {
        this.id_per = id_per;
    }

    public String getId_com() {
        return id_com;
    }

    public void setId_com(String id_com) {
        this.id_com = id_com;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getAno_graduacion() {
        return ano_graduacion;
    }

    public void setAno_graduacion(String ano_graduacion) {
        this.ano_graduacion = ano_graduacion;
    }

    public String getNo_acreditacion() {
        return no_acreditacion;
    }

    public void setNo_acreditacion(String no_acreditacion) {
        this.no_acreditacion = no_acreditacion;
    }

    
}
