
package paquetes;

import java.io.Serializable;

public class CatSalidasDet implements Serializable{

    private String id_sal,id_det,id_art,id_bod,id_ubi,det_lot,fec_ven,det_can,nomart,nombod,nomubi;

    public CatSalidasDet(String id_sal, String id_det, String id_art, String id_bod, String id_ubi, String det_lot, String fec_ven, String det_can, String nomart, String nombod, String nomubi) {
        this.id_sal = id_sal;
        this.id_det = id_det;
        this.id_art = id_art;
        this.id_bod = id_bod;
        this.id_ubi = id_ubi;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.det_can = det_can;
        this.nomart = nomart;
        this.nombod = nombod;
        this.nomubi = nomubi;
    }

    public CatSalidasDet() {
    }

    public String getId_sal() {
        return id_sal;
    }

    public void setId_sal(String id_sal) {
        this.id_sal = id_sal;
    }

    public String getId_det() {
        return id_det;
    }

    public void setId_det(String id_det) {
        this.id_det = id_det;
    }

    public String getId_art() {
        return id_art;
    }

    public void setId_art(String id_art) {
        this.id_art = id_art;
    }

    public String getId_bod() {
        return id_bod;
    }

    public void setId_bod(String id_bod) {
        this.id_bod = id_bod;
    }

    public String getId_ubi() {
        return id_ubi;
    }

    public void setId_ubi(String id_ubi) {
        this.id_ubi = id_ubi;
    }

    public String getDet_lot() {
        return det_lot;
    }

    public void setDet_lot(String det_lot) {
        this.det_lot = det_lot;
    }

    public String getFec_ven() {
        return fec_ven;
    }

    public void setFec_ven(String fec_ven) {
        this.fec_ven = fec_ven;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
    }

    public String getNombod() {
        return nombod;
    }

    public void setNombod(String nombod) {
        this.nombod = nombod;
    }

    public String getNomubi() {
        return nomubi;
    }

    public void setNomubi(String nomubi) {
        this.nomubi = nomubi;
    }
    
    
    
}
