package tt.productos;

import java.io.Serializable;

public class CatLotes implements Serializable {

    private String num_lot;

    public CatLotes() {
    }

    public CatLotes(String num_lot) {
        this.num_lot = num_lot;
    }

    public String getNum_lot() {
        return num_lot;
    }

    public void setNum_lot(String num_lot) {
        this.num_lot = num_lot;
    }

}
