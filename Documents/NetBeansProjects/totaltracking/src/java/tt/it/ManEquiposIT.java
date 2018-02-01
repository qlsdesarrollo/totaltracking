package tt.it;

import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatPaises;
import tt.general.Login;

@Named
@ConversationScoped

public class ManEquiposIT implements Serializable {

    private static final long serialVersionUID = 8797968735428638L;
    @Inject
    Login cbean;
    private Accesos macc;

    private List<CatPaises> paises;

    private CatLisEqu catlisequ;
    private List<CatLisEqu> equipos;

    private String cod_lis_equ, cod_pai, tip_equ, cod_equ, rut_ima_enc;

    private String mancho_enc;

    private static DefaultStreamedContent mimagen_enc;

    public ManEquiposIT() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_lis_equ = "";
        cod_pai = cbean.getCod_pai();
        tip_equ = "";
        cod_equ = "";
        rut_ima_enc = "";

        mimagen_enc = null;
        mancho_enc = "135";

        paises = new ArrayList<>();

        catlisequ = new CatLisEqu();
        equipos = new ArrayList<>();

        llenarPaises();
        llenarEquipos();

    }

    public void nuevo() {
        cod_lis_equ = "";
        cod_pai = "";
        tip_equ = "";
        cod_equ = "";
        rut_ima_enc = "";

        mimagen_enc = null;
        mancho_enc = "150";

        catlisequ = null;

    }

    public void cerrarventana() {
        cod_lis_equ = null;
        cod_pai = null;
        tip_equ = null;
        cod_equ = null;
        rut_ima_enc = null;

        mimagen_enc = null;
        mancho_enc = null;

        paises = null;

        catlisequ = null;
        equipos = null;

        macc = null;
    }

    public void llenarPaises() {
        String mQuery = "";
        try {
            paises.clear();

            mQuery = "select cod_pai,nom_pai from cat_pai order by nom_pai;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paises.add(new CatPaises(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEquipos() {
        String mQuery = "", mWhere;

        if (!cod_pai.equals("0")) {
            mWhere = "where cod_pai = " + cod_pai + " ";
        } else {
            mWhere = "";
        }

        try {
            catlisequ = null;
            equipos.clear();

            mQuery = "select "
                    + "cod_lis_equ,cod_pai,tip_equ,cod_equ,nom_pai,nom_cli "
                    + "from( "
                    + " select    "
                    + " leq.cod_lis_equ,  "
                    + " leq.cod_pai,  "
                    + " leq.tip_equ,  "
                    + " leq.cod_equ,  "
                    + " pai.nom_pai, "
                    + " ifnull((select   "
                    + "  cli.nom_cli "
                    + "  from it_asi_equ_cli as asi "
                    + "  left join inv_cat_cli as cli on asi.cod_pai = cli.cod_pai and asi.cod_cli = cli.cod_cli "
                    + "  where asi.cod_lis_equ = leq.cod_lis_equ "
                    + "  order by asi.fec_asi desc limit 1),'No Asignado') as nom_cli "
                    + " from it_lis_equ as leq "
                    + " left join cat_pai as pai on leq.cod_lis_equ = pai.cod_pai "
                    + " ) as total "
                    + mWhere
                    + "order by nom_pai, nom_cli, tip_equ, cod_equ;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatLisEqu(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Encabezado Equipos en Equipos IT. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public boolean validardatosEquipos() {
        boolean mValidar = true;
        if ("0".equals(cod_pai)) {
            addMessage("Guardar", "Debe Seleccionar un País", 2);
            return false;
        }

        if ("".equals(tip_equ)) {
            addMessage("Guardar", "Debe Seleccionar un Tipo de Equipo", 2);
            return false;
        }

        if ("".equals(cod_equ)) {
            addMessage("Guardar", "Debe Ingresar un Código para el Equipo", 2);
            return false;
        } else {
            macc.Conectar();
            if (cod_lis_equ.equals("")) {
                if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ where upper(cod_equ) = '" + cod_equ.toUpperCase() + "';").equals("0")) {
                    addMessage("Guardar", "El código Ingresado ya está asignado a otro Equipo", 2);
                    macc.Desconectar();
                    return false;
                }
            } else if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_lis_equ where upper(cod_equ) = '" + cod_equ.toUpperCase() + "' and cod_lis_equ != " + cod_lis_equ + " ;").equals("0")) {
                addMessage("Guardar", "El código Ingresado ya está asignado a otro Equipo", 2);
                macc.Desconectar();
                return false;
            }
        }

        return mValidar;

    }

    public void guardarEquipo() {
        String mQuery = "";
        if (validardatosEquipos()) {
            try {
                macc.Conectar();

                if ("".equals(cod_lis_equ)) {
                    mQuery = "select ifnull(max(cod_lis_equ),0)+1 as codigo from it_lis_equ;";
                    cod_lis_equ = macc.strQuerySQLvariable(mQuery);
                    mQuery = "insert into it_lis_equ (cod_lis_equ ,cod_pai ,tip_equ ,cod_equ ,fec_edi ,usu_edi ) "
                            + "values (" + cod_lis_equ + "," + cod_pai + ",'" + tip_equ + "','"
                            + cod_equ + "', now()," + cbean.getCod_usu() + ");";
                } else {
                    mQuery = "update it_lis_equ SET "
                            + "cod_pai = " + cod_pai + " "
                            + ",tip_equ = '" + tip_equ + "' "
                            + ",cod_equ = '" + cod_equ + "' "
                            + ",fec_edi = now() "
                            + ",usu_edi = " + cbean.getCod_usu() + " "
                            + "WHERE cod_lis_equ = " + cod_lis_equ;

                }
                macc.dmlSQLvariable(mQuery);
                macc.Desconectar();

                addMessage("Guardar", "Información almacenada con Éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEquipos();
        }
        nuevo();

    }

    public boolean validareliminarEquipo() {
        boolean mvalidar = true;

        if ("".equals(cod_lis_equ) || "0".equals(cod_lis_equ)) {
            addMessage("Eliminar", "Debe seleccionar un registro", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_asi_equ_cli where cod_lis_equ = " + cod_lis_equ + ";").equals("0")) {
            addMessage("Eliminar", "Este equipo tiene asignaciones de Cliente", 2);
            return false;
        }
        macc.Desconectar();

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_lis_equ) from it_man_equ where cod_lis_equ = " + cod_lis_equ + ";").equals("0")) {
            addMessage("Eliminar", "Este equipo tiene mantenimientos relacionados", 2);
            return false;
        }
        macc.Desconectar();

        return mvalidar;
    }

    public void eliminarEquipo() {
        String mQuery = "";

        if (validareliminarEquipo()) {
            try {
                macc.Conectar();

                mQuery = "delete from it_lis_equ_ima where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ_det_ima where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ_det where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from it_lis_equ where cod_lis_equ=" + cod_lis_equ + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con Éxito", 1);
                macc.Desconectar();

            } catch (Exception e) {
                addMessage("Eliominar", "Error al intentar Eliminar. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Equipo IT. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEquipos();
            nuevo();
        }

    }

    public void onRowSelectEnc(SelectEvent event) {
        cod_lis_equ = ((CatLisEqu) event.getObject()).getCod_lis_equ();
        cod_pai = ((CatLisEqu) event.getObject()).getCod_pai();
        tip_equ = ((CatLisEqu) event.getObject()).getTip_equ();
        cod_equ = ((CatLisEqu) event.getObject()).getCod_equ();
    }

    //************************* Funciones para guardar archivos que ya no se utilizan ******************************
    public static void copiarImagen(String filePath, String copyPath, int wh) {
        BufferedImage nuevaImagen = cargarImagen(filePath);
        if (nuevaImagen.getHeight() > nuevaImagen.getWidth()) {
            int heigt = (nuevaImagen.getHeight() * wh) / nuevaImagen.getWidth();
            nuevaImagen = resize(nuevaImagen, wh, heigt);
            int width = (nuevaImagen.getWidth() * wh) / nuevaImagen.getHeight();
            nuevaImagen = resize(nuevaImagen, width, wh);
        } else {
            int width = (nuevaImagen.getWidth() * wh) / nuevaImagen.getHeight();
            nuevaImagen = resize(nuevaImagen, width, wh);
            int heigt = (nuevaImagen.getHeight() * wh) / nuevaImagen.getWidth();
            nuevaImagen = resize(nuevaImagen, wh, heigt);
        }
        GuardarImagen(nuevaImagen, copyPath);
    }

    /*
     Con este método, cargamos la imagen inicial, indicándole el path
     */
    public static BufferedImage cargarImagen(String pathName) {
        BufferedImage nuevaImagen = null;
        try {
            nuevaImagen = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuevaImagen;
    }

    /*
     Este método se utiliza para redimensionar la imagen
     */
    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage imagenRedimensionada = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = imagenRedimensionada.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return imagenRedimensionada;
    }

    /*
     Con este método almacenamos la imagen en el disco
     */
    public static void GuardarImagen(BufferedImage bufferedImage, String pathName) {
        try {
            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String summary, String detail, int tipo) {
        FacesMessage message = new FacesMessage();
        switch (tipo) {
            case 1:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
                break;
            case 2:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
                break;
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //********************************* GETTERS Y SETTERS ***********************************
    public List<CatPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<CatPaises> paises) {
        this.paises = paises;
    }

    public CatLisEqu getCatlisequ() {
        return catlisequ;
    }

    public void setCatlisequ(CatLisEqu catlisequ) {
        this.catlisequ = catlisequ;
    }

    public List<CatLisEqu> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatLisEqu> equipos) {
        this.equipos = equipos;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getTip_equ() {
        return tip_equ;
    }

    public void setTip_equ(String tip_equ) {
        this.tip_equ = tip_equ;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getMancho_enc() {
        return mancho_enc;
    }

    public void setMancho_enc(String mancho_enc) {
        this.mancho_enc = mancho_enc;
    }

    public static DefaultStreamedContent getMimagen_enc() {
        return mimagen_enc;
    }

    public static void setMimagen_enc(DefaultStreamedContent mimagen_enc) {
        ManEquiposIT.mimagen_enc = mimagen_enc;
    }

}
