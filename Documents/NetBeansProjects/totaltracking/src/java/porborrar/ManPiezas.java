package porborrar;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
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
import org.primefaces.model.UploadedFile;
import tt.general.Accesos;
import tt.general.Login;
import tt.mantenimiento.CatCategorias;
import tt.mantenimiento.CatEquipos;
import tt.mantenimiento.CatGruposPiezas;
import tt.mantenimiento.CatLineas;
import tt.mantenimiento.CatPiezas;

@Named
@ConversationScoped
public class ManPiezas implements Serializable {

    private static final long serialVersionUID = 8797678674685238L;
    @Inject
    Login cbean;
    private List<CatGruposPiezas> grupos;
    private List<CatLineas> lineas;
    
    private CatCategorias catcategorias;
    private List<CatCategorias> categorias;
    private CatEquipos catequipos;
    private List<CatEquipos> equipos;
    private CatPiezas catpiezas;
    private List<CatPiezas> piezas;
    private String cod_pie, cod_ref, cod_equ, nom_pie, des_pie, cod_cat, det_ima, vid_uti, cod_gru, cod_lin;

    private UploadedFile file;

    public ManPiezas() {
    }

    public void iniciarventana() {
        cod_pie = "";
        cod_ref = "";
        cod_equ = "0";
        nom_pie = "";
        des_pie = "";
        cod_cat = "";
        det_ima = "";
        vid_uti = "0";
        cod_gru = "0";
        cod_lin = "0";
        llenarCategorias();
        llenarEquipos();
        llenarGrupos();
        llenarPiezas();

    }

    public void cerrarventana() {
        cod_pie = "";
        cod_ref = "";
        cod_equ = "0";
        nom_pie = "";
        des_pie = "";
        cod_cat = "";
        det_ima = "";
        vid_uti = "";
        cod_gru = "0";
        cod_lin = "0";
        equipos = new ArrayList<>();
        piezas = new ArrayList<>();
        categorias = new ArrayList<>();
        grupos = new ArrayList<>();
        lineas = new ArrayList<>();
    }

    public void llenarCategorias() {
        String mQuery = "";
        try {
            catcategorias = new CatCategorias();
            categorias = new ArrayList<>();

            mQuery = "select cod_pro, nom_pro "
                    + "from cat_sup "
                    + "order by cod_pro;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                categorias.add(new CatCategorias(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Categorías en Catálogo Piezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEquipos() {
        String mQuery = "";
        try {
            catequipos = new CatEquipos();
            equipos = new ArrayList<>();

            mQuery = "select equ.cod_equ, equ.cod_mar,equ.cod_ref, "
                    + "equ.nom_equ, equ.des_equ,mar.nom_mar,equ.det_ima "
                    + "from cat_equ as equ "
                    + "left join cat_mar as mar on mar.id_mar = equ.cod_mar "
                    + "order by equ.cod_equ;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                equipos.add(new CatEquipos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Equipos en Catálogo Piezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarGrupos() {
        String mQuery = "";
        try {
            grupos = new ArrayList<>();

            mQuery = "select cod_gru,nom_gru "
                    + "from cat_gru  "
                    + "order by cod_gru;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                grupos.add(new CatGruposPiezas(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Grupos en Catálogo de Piezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarLineas() {
        String mQuery = "";
        try {
            lineas = new ArrayList<>();

            mQuery = "select lin.cod_gru,lin.cod_lin,lin.nom_lin,gru.nom_gru "
                    + "from cat_lin as lin "
                    + "left join cat_gru as gru on lin.cod_gru = gru.cod_gru "
                    + "where lin.cod_gru = " + cod_gru + " "
                    + "order by lin.cod_gru,lin.cod_lin;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lineas.add(new CatLineas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Líneas en Catálogo de Piezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarPiezas() {
        String mQuery = "";
        try {
            catpiezas = new CatPiezas();
            piezas = new ArrayList<>();

            mQuery = "select pie.cod_pie,pie.cod_ref,pie.cod_equ,"
                    + "pie.nom_pie,pie.des_pie,equ.nom_equ,pie.cod_cat,"
                    + "pie.det_ima,pie.vid_uti,pie.cod_gru,pie.cod_lin, "
                    + "gru.nom_gru,"
                    + "lin.nom_lin "
                    + "from cat_pie as pie "
                    + "left join cat_equ as equ on equ.cod_equ=pie.cod_equ "
                    + "left join cat_gru as gru on pie.cod_gru = gru.cod_gru "
                    + "left join cat_lin as lin on pie.cod_gru = lin.cod_gru and lin.cod_lin = pie.cod_lin "
                    + "order by pie.cod_pie;";
            ResultSet resVariable;
            Accesos mAccesos = new Accesos();
            mAccesos.Conectar();
            resVariable = mAccesos.querySQLvariable(mQuery);
            while (resVariable.next()) {
                piezas.add(new CatPiezas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(12),
                        resVariable.getString(13)
                ));
            }
            resVariable.close();
            mAccesos.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo de Piezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void nuevo() {
        cod_pie = "";
        cod_ref = "";
        cod_equ = "0";
        nom_pie = "";
        des_pie = "";
        cod_cat = "";
        det_ima = "";
        vid_uti = "";
        cod_gru = "0";
        cod_lin = "0";
        catpiezas = new CatPiezas();
    }

    public void guardar() {
        String mQuery = "", ntemporal = det_ima;
        if (validardatos()) {
            try {
                Accesos mAccesos = new Accesos();
                mAccesos.Conectar();
                if ("".equals(cod_pie)) {
                    mQuery = "select ifnull(max(cod_pie),0)+1 as codigo from cat_pie;";
                    cod_pie = mAccesos.strQuerySQLvariable(mQuery);
                    det_ima = "/resources/images/piezas/" + "pie_" + cod_pie + ".jpg";
                    mQuery = "insert into cat_pie (cod_pie,cod_ref,cod_equ,nom_pie,des_pie,cod_cat,det_ima,vid_uti,cod_gru,cod_lin) "
                            + "values (" + cod_pie + ",'" + cod_ref + "'," + cod_equ + ",'" + nom_pie
                            + "','" + des_pie + "'," + cod_cat + ",'" + det_ima + "'," + vid_uti + "," + cod_gru + "," + cod_lin + ");";
                } else {
                    det_ima = "/resources/images/piezas/" + "pie_" + cod_pie + ".jpg";
                    mQuery = "update cat_pie SET "
                            + " cod_ref = '" + cod_ref + "' "
                            + ",cod_equ = '" + cod_equ + "' "
                            + ",nom_pie = '" + nom_pie + "' "
                            + ",des_pie = '" + des_pie + "' "
                            + ",cod_cat = " + cod_cat + " "
                            + ",det_ima = '" + det_ima + "' "
                            + ",vid_uti = " + vid_uti + " "
                            + ",cod_gru = " + cod_gru + " "
                            + ",cod_lin = " + cod_lin + " "
                            + "WHERE cod_pie = " + cod_pie;

                }

                try {

                    if (!"/resources/images/piezas/".equals(ntemporal.replace("pie_" + cod_pie + ".jpg", ""))) {
                        File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
                        String destinationO = mIMGFile.getPath().replace("config.xml", "");

                        File mIMGFile2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/piezas/config.xml"));
                        String destinationD = mIMGFile2.getPath().replace("config.xml", "");

                        //Verifica que no exista otro archivo con el nombre destino y si hay lo borra.
                        File mfileDestino = new File(destinationD + "pie_" + cod_pie + ".jpg");
                        if (mfileDestino.exists()) {
                            mfileDestino.delete();
                        }
                        //Copia el nuevo archivo
                        File mfileOrigen = new File(destinationO + ntemporal.replace("/resources/images/temp/", ""));
                        if (mfileOrigen.exists()) {
                            mfileOrigen.renameTo(new File(destinationD + "pie_" + cod_pie + ".jpg"));
                            mfileOrigen.delete();
                        }

                    }
                } catch (Exception mex) {

                }

                mAccesos.dmlSQLvariable(mQuery);
                mAccesos.Desconectar();
                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Pieza. " + e.getMessage() + " Query: " + mQuery);
            }
        }
        llenarPiezas();
        nuevo();

    }

    public void eliminar() {
        String mQuery = "";
        Accesos mAccesos = new Accesos();
        mAccesos.Conectar();
        if ("".equals(cod_pie) == false) {
            try {
                File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/piezas/" + "pie_" + cod_pie + ".jpg"));
                if (mIMGFile.exists()) {
                    mIMGFile.delete();
                }
                mQuery = "delete from cat_pie where cod_pie=" + cod_pie + ";";
                mAccesos.dmlSQLvariable(mQuery);
                addMessage("Delete", "Information deleted successfully.", 1);
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Pieza. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarPiezas();
            nuevo();
        } else {
            addMessage("Delete", "You have to select a Record.", 2);
        }
        mAccesos.Desconectar();

    }

    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_pie)) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name.", 2);
        } else {
            /* Accesos macc = new Accesos();
            macc.Conectar();
            String contador = macc.strQuerySQLvariable("select ifnull(count(cod_pie),0) as cont from cat_pie where upper(nom_pie) = '" + nom_pie.toUpperCase() + "' "
                    + "and cod_pie <> " + cod_pie + ";");
            macc.Desconectar();
            if (!"0".equals(contador)) {
                mValidar = false;
                addMessage("Save", "This Name already exists.", 2);
            }*/
        }
        return mValidar;

    }

    public void onRowSelect(SelectEvent event) {
        cod_pie = ((CatPiezas) event.getObject()).getCod_pie();
        cod_ref = ((CatPiezas) event.getObject()).getCod_ref();
        cod_equ = ((CatPiezas) event.getObject()).getCod_equ();
        nom_pie = ((CatPiezas) event.getObject()).getNom_pie();
        des_pie = ((CatPiezas) event.getObject()).getDes_pie();
        cod_cat = ((CatPiezas) event.getObject()).getCod_cat();
        det_ima = ((CatPiezas) event.getObject()).getDet_ima();
        vid_uti = ((CatPiezas) event.getObject()).getVid_uti();
        cod_gru = ((CatPiezas) event.getObject()).getCod_gru();
        cod_lin = ((CatPiezas) event.getObject()).getCod_lin();
    }

    //************* Tratamiento Imagen *************************************************
    public void upload(FileUploadEvent event) {
        try {
            //dbImage = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
            Random rnd = new Random();
            String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));
            copyFile("pie_temp_" + prefijo + ".jpg", event.getFile().getInputstream());

        } catch (Exception e) {
            addMessage("Procesar Imagen", "La Imagen " + event.getFile().getFileName() + " No se ha podido Cargar. " + e.getMessage(), 2);
            System.out.println("Error en subir archivo Imagen Equipo." + e.getMessage());
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            det_ima = "/resources/images/temp/" + fileName;

            destination = mIMGFile.getPath().replace("config.xml", "");

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                File file = new File(destination + fileName);
                if (file.exists()) {
                    file.delete();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName.toLowerCase()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            addMessage("Copiar Imagen Pieza", "La Imagen en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

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

    public List<CatGruposPiezas> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<CatGruposPiezas> grupos) {
        this.grupos = grupos;
    }

    public List<CatLineas> getLineas() {
        return lineas;
    }

    public void setLineas(List<CatLineas> lineas) {
        this.lineas = lineas;
    }

    public CatCategorias getCatcategorias() {
        return catcategorias;
    }

    public void setCatcategorias(CatCategorias catcategorias) {
        this.catcategorias = catcategorias;
    }

    public List<CatCategorias> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CatCategorias> categorias) {
        this.categorias = categorias;
    }

    public CatEquipos getCatequipos() {
        return catequipos;
    }

    public void setCatequipos(CatEquipos catequipos) {
        this.catequipos = catequipos;
    }

    public List<CatEquipos> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<CatEquipos> equipos) {
        this.equipos = equipos;
    }

    public CatPiezas getCatpiezas() {
        return catpiezas;
    }

    public void setCatpiezas(CatPiezas catpiezas) {
        this.catpiezas = catpiezas;
    }

    public List<CatPiezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<CatPiezas> piezas) {
        this.piezas = piezas;
    }

    public String getCod_pie() {
        return cod_pie;
    }

    public void setCod_pie(String cod_pie) {
        this.cod_pie = cod_pie;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getNom_pie() {
        return nom_pie;
    }

    public void setNom_pie(String nom_pie) {
        this.nom_pie = nom_pie;
    }

    public String getDes_pie() {
        return des_pie;
    }

    public void setDes_pie(String des_pie) {
        this.des_pie = des_pie;
    }

    public String getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(String cod_cat) {
        this.cod_cat = cod_cat;
    }

    public String getDet_ima() {
        return det_ima;
    }

    public void setDet_ima(String det_ima) {
        this.det_ima = det_ima;
    }

    public String getVid_uti() {
        return vid_uti;
    }

    public void setVid_uti(String vid_uti) {
        this.vid_uti = vid_uti;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getCod_lin() {
        return cod_lin;
    }

    public void setCod_lin(String cod_lin) {
        this.cod_lin = cod_lin;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
