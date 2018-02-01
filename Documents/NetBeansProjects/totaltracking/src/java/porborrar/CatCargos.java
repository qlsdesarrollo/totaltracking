
package paquetes;

import java.io.Serializable;

public class CatCargos implements Serializable{
    
    private String id_car, des_car;

    public CatCargos() {
    }

    public CatCargos(String id_car, String des_car) {
        this.id_car = id_car;
        this.des_car = des_car;
    }

    public String getId_car() {
        return id_car;
    }

    public void setId_car(String id_car) {
        this.id_car = id_car;
    }

    public String getDes_car() {
        return des_car;
    }

    public void setDes_car(String des_car) {
        this.des_car = des_car;
    }
}
