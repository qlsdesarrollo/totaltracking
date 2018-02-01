package tt.general;

import tt.mantenimiento.CatPiezasAbreviado;
import tt.mantenimiento.ManTblPiezas;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("codigoproducto")
public class ConvertidorListaPiezas implements Converter {

    ManTblPiezas mc = new ManTblPiezas();

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String codigo) {
        //System.out.println("mcodigo: " + codigo);
        if (codigo != null && codigo.trim().length() > 0 && !"0".equals(codigo)) {
            try {
                //ManTblPiezas tblp = (ManTblPiezas) ctx.getExternalContext().getApplicationMap().get("manTblPiezas");
                CatPiezasAbreviado mo = new CatPiezasAbreviado();
                mc = (ManTblPiezas) ctx.getELContext().getELResolver().getValue(ctx.getELContext(), null, "manTblPiezas");

                for (int i = 0; i < mc.getPiezas().size(); i++) {
                    if (codigo.equals(mc.getPiezas().get(i).getCodigo())) {
                        // mo = (CatPiezasAbreviado) mc.getPiezas().get(i);
                    }
                }

                return mo;
            } catch (ConverterException e) {
                System.out.println("error: " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        try {
            if (object != null && !"0".equals(object.toString())) {
                return ((CatPiezasAbreviado) object).getCodigo();
            } else {
                return "0";
            }
        } catch (ConverterException e) {
            System.out.println("error: " + e.getMessage());
            return null;

        }
    }

}
