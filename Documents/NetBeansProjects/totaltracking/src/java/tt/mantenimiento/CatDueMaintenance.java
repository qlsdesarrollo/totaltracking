package tt.mantenimiento;

import java.io.Serializable;

public class CatDueMaintenance implements Serializable {

    private String cod_pro, cod_tip, cod_per, ord_per, cod_pie;
    private double qty_use;
    private String equipo, periodicidad, orden, coderef, namepart;

    public CatDueMaintenance() {
    }

    public CatDueMaintenance(String cod_pro, String cod_tip, String cod_per, String ord_per, String cod_pie, double qty_use, String equipo, String periodicidad, String orden, String coderef, String namepart) {
        this.cod_pro = cod_pro;
        this.cod_tip = cod_tip;
        this.cod_per = cod_per;
        this.ord_per = ord_per;
        this.cod_pie = cod_pie;
        this.qty_use = qty_use;
        this.equipo = equipo;
        this.periodicidad = periodicidad;
        this.orden = orden;
        this.coderef = coderef;
        this.namepart = namepart;
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

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getCoderef() {
        return coderef;
    }

    public void setCoderef(String coderef) {
        this.coderef = coderef;
    }

    public String getNamepart() {
        return namepart;
    }

    public void setNamepart(String namepart) {
        this.namepart = namepart;
    }

}
