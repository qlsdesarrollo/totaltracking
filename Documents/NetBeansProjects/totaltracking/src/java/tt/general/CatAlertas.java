
package tt.general;

import java.io.Serializable;

public class CatAlertas implements Serializable{
    
    private String id_ale, proceso, tabla_ctrl, campo_ctrl, alerta, aviso, recordatorio, id_estado;

    public CatAlertas() {
    }

    public CatAlertas(String id_ale, String proceso, String tabla_ctrl, String campo_ctrl, String alerta, String aviso, String recordatorio, String id_estado) {
        this.id_ale = id_ale;
        this.proceso = proceso;
        this.tabla_ctrl = tabla_ctrl;
        this.campo_ctrl = campo_ctrl;
        this.alerta = alerta;
        this.aviso = aviso;
        this.recordatorio = recordatorio;
        this.id_estado = id_estado;
    }

    public String getId_ale() {
        return id_ale;
    }

    public void setId_ale(String id_ale) {
        this.id_ale = id_ale;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getTabla_ctrl() {
        return tabla_ctrl;
    }

    public void setTabla_ctrl(String tabla_ctrl) {
        this.tabla_ctrl = tabla_ctrl;
    }

    public String getCampo_ctrl() {
        return campo_ctrl;
    }

    public void setCampo_ctrl(String campo_ctrl) {
        this.campo_ctrl = campo_ctrl;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getId_estado() {
        return id_estado;
    }

    public void setId_estado(String id_estado) {
        this.id_estado = id_estado;
    }   
}
