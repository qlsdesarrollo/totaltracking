package tt.mantenimiento;

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
import java.io.Serializable;
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
import tt.general.CatMarcas;
import tt.general.Login;


@Named
@ConversationScoped
public class ManEquipos implements Serializable {

    private static final long serialVersionUID = 8797671468316638L;
    @Inject
    Login cbean;
    private Accesos macc;
    
    private CatEquipos catequipos;
    private List<CatEquipos> equipos;

    private List<CatMarcas> marcas;
    private String cod_equ, cod_mar, cod_ref, nom_equ, des_equ, det_ima;

    private String mheight;

    private static DefaultStreamedContent mimagen;

    public ManEquipos() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        cod_equ = "";
        nom_equ = "";
        cod_mar = "";
        cod_ref = "";
        des_equ = "";
        det_ima = "";
        
        mimagen = null;
        mheight = "135";
        
        marcas = new ArrayList<>();
        
        catequipos = new CatEquipos();
        equipos = new ArrayList<>();
        
        
        llenarMarcas();
        llenarEquipos();
    }
    
    public void nuevo() {
        cod_equ = "";
        nom_equ = "";
        cod_mar = "";
        cod_ref = "";
        des_equ = "";
        det_ima = "";
        
        mimagen = null;
        mheight = "135";
        
        catequipos = null;
    }

    public void cerrarventana() {
        cod_equ = "";
        nom_equ = "";
        cod_mar = "";
        cod_ref = "";
        des_equ = "";
        det_ima = "";
        
        mimagen = null;
        mheight = "";
        
        marcas = null;
        
        catequipos = null;
        equipos = null;
        
        macc = null;
    }

    public void llenarMarcas() {
        String mQuery = "";
        try {
            marcas.clear();

            mQuery = "select id_mar, nom_mar from cat_mar order by id_mar;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                marcas.add(new CatMarcas(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Marcas en Catálogo Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEquipos() {
        String mQuery = "";
        try {
            catequipos = null;
            equipos.clear();

            mQuery = "select equ.cod_equ, equ.cod_mar,equ.cod_ref, "
                    + "equ.nom_equ, equ.des_equ,mar.nom_mar,equ.det_ima "
                    + "from cat_equ as equ "
                    + "left join cat_mar as mar on mar.id_mar = equ.cod_mar "
                    + "order by equ.cod_equ;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
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
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Equipos. " + e.getMessage() + " Query: " + mQuery);
        }
    }
    
    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(nom_equ) == true) {
            mValidar = false;
            addMessage("Save", "You have to enter a Name.", 2);
        }

        return mValidar;

    }

    public void guardar() {
        String mQuery = ""; //, ntemporal=det_ima;
        if (validardatos()) {
            try {

                //Accesos mAccesos = new Accesos();
                macc.Conectar();
                
                if ("".equals(cod_equ)) {
                    mQuery = "select ifnull(max(cod_equ),0)+1 as codigo from cat_equ;";
                    cod_equ = macc.strQuerySQLvariable(mQuery);
                    //det_ima = "/resources/images/equipos/" + "equ_" + cod_equ + ".jpg";
                    mQuery = "insert into cat_equ (cod_equ, cod_mar,cod_ref, nom_equ, des_equ,det_ima) "
                            + "values (" + cod_equ + "," + cod_mar + ",'" + cod_ref + "','"
                            + nom_equ + "','" + des_equ + "','" + det_ima + "');";
                } else {
                    //det_ima = "/resources/images/equipos/" + "equ_" + cod_equ + ".jpg";
                    mQuery = "update cat_equ SET "
                            + "cod_mar = " + cod_mar + " "
                            + ",cod_ref = '" + cod_ref + "' "
                            + ",nom_equ = '" + nom_equ + "' "
                            + ",des_equ = '" + des_equ + "' "
                            + ",det_ima = '" + det_ima + "' "
                            + "WHERE cod_equ = " + cod_equ;

                }
                
                macc.dmlSQLvariable(mQuery);
                if (!"".equals(det_ima)) {
                    try {
                        // ****************************  Inserta Imagen en tabla ************************************************************
                        try {

                            FileInputStream fis = null;
                            PreparedStatement ps = null;
                            File file = null;
                            try {
                                macc.Conectar().setAutoCommit(false);
                                file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(det_ima));
                                fis = new FileInputStream(file);
                                if ("0".equals(macc.strQuerySQLvariable("select count(cod_equ) conta from cat_equ_img where cod_equ=" + cod_equ + ";"))) {
                                    mQuery = "insert into cat_equ_img(cod_equ,det_ima) values(?,?)";
                                    ps = macc.Conectar().prepareStatement(mQuery);
                                    ps.setString(1, cod_equ);
                                    ps.setBinaryStream(2, fis, (int) file.length());
                                } else {
                                    mQuery = "update cat_equ_img set det_ima = ? where cod_equ=?;";
                                    ps = macc.Conectar().prepareStatement(mQuery);
                                    ps.setBinaryStream(1, fis, (int) file.length());
                                    ps.setString(2, cod_equ);
                                }

                                ps.executeUpdate();
                                macc.Conectar().commit();

                            } catch (Exception ex) {

                            } finally {
                                try {
                                    ps.close();
                                    fis.close();
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    file = null;
                                } catch (Exception ex) {

                                }
                            }
                            //recuperarimagen();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Guardar archivo Imagen de Producto." + e.getMessage());
                    }
                }
                
                macc.Desconectar();

                

                addMessage("Save", "Information saved successfully", 1);
            } catch (Exception e) {
                addMessage("Save", "Error while saving. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Equipo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEquipos();
        }
        nuevo(); 

    }
    
     public boolean validareliminar() {
        boolean mvalidar = true;

        if ("".equals(cod_equ) || "0".equals(cod_equ)) {
            addMessage("Delete", "You have to select a record", 2);
            return false;
        }

        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_equ) from lis_equ where cod_equ = " + cod_equ + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "This type has associated equipment", 2);
        }
        macc.Desconectar();
        
        macc.Conectar();
        if (!macc.strQuerySQLvariable("select count(cod_equ) from cat_pie where cod_equ = " + cod_equ + ";").equals("0")) {
            mvalidar = false;
            addMessage("Delete", "type client has associated parts", 2);
        }
        macc.Desconectar();
        
        
        
        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";
      
        if (validareliminar()) {
            try {
                macc.Conectar();

                mQuery = "delete from cat_equ_img where cod_equ=" + cod_equ + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from cat_equ where cod_equ=" + cod_equ + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Delete", "Information deleted successfully", 1);
                macc.Desconectar();
                
            } catch (Exception e) {
                addMessage("Delete", "Error while erasing. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Equipo. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarEquipos();
            nuevo();
        }
       

    }

    public void onRowSelect(SelectEvent event) {
        cod_equ = ((CatEquipos) event.getObject()).getCod_equ();
        cod_mar = ((CatEquipos) event.getObject()).getCod_mar();
        cod_ref = ((CatEquipos) event.getObject()).getCod_ref();
        nom_equ = ((CatEquipos) event.getObject()).getNom_equ();
        des_equ = ((CatEquipos) event.getObject()).getDes_equ();
        det_ima = ((CatEquipos) event.getObject()).getDet_ima();
        
        recuperarimagen();
    }
    
    public void recuperarimagen() {
        String mQuery = "";
        macc.Conectar();
        mimagen = null;
        try {
            mQuery = "select det_ima from cat_equ_img where cod_equ=" + cod_equ + ";";
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 140.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from cat_equ_img where cod_equ=0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 140.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            System.out.println("Error en recuperarimagen ManEquipos. " + e.getMessage() + " Query: " + mQuery);

        }

        macc.Desconectar();
    }

    public void upload(FileUploadEvent event) {
        try {
            //dbImage = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
            Random rnd = new Random();
            String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));
            copyFile("equ_temp_" + prefijo + ".jpg", event.getFile().getInputstream());
            
            mimagen = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
            mheight = "135";

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
                File mfile = new File(destination + fileName);
                if (mfile.exists()) {
                    mfile.delete();
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
            det_ima ="";
            addMessage("Copiar Imagen Equipo", "La Imagen en copyFyle" + fileName + " No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

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
    
     //************************* Funciones para guardar archivos que ya no se utilizan ******************************
    public void handleFileUpload(FileUploadEvent event) {
        //file = event.getFile();

        //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String newFileName = servletContext.getRealPath("") + File.separator + "uploaded" + File.separator + file.getFileName();
       // try {
            /* FileOutputStream fos = new FileOutputStream(new File(newFileName));
            InputStream is = file.getInputstream();
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];
            int a;
            while (true) {
                a = is.read(buffer);
                if (a < 0) {
                    break;
                }
                fos.write(buffer, 0, a);
                fos.flush();
            }
             */
            //fos.close();
            //  is.close();
            //Random rnd = new Random();
            //String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));

           // copyFile("equ_" + cod_equ + ".jpg", file.getInputstream(), 1);

      //  } catch (IOException e) {
            //addMessage("Procesar Imagen", "La Imagen Upload de Equipo " + event.getFile().getFileName() + " No se ha podido procesar. " + e.getMessage(), 2);
            //System.out.println("Error en subir archivo Imagen Equipo." + e.getMessage());
       // }
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

    //*************************** Getters y Setters *********************************
    
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

    public List<CatMarcas> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<CatMarcas> marcas) {
        this.marcas = marcas;
    }

    public String getCod_equ() {
        return cod_equ;
    }

    public void setCod_equ(String cod_equ) {
        this.cod_equ = cod_equ;
    }

    public String getCod_mar() {
        return cod_mar;
    }

    public void setCod_mar(String cod_mar) {
        this.cod_mar = cod_mar;
    }

    public String getNom_equ() {
        return nom_equ;
    }

    public void setNom_equ(String nom_equ) {
        this.nom_equ = nom_equ;
    }

    public String getDes_equ() {
        return des_equ;
    }

    public void setDes_equ(String des_equ) {
        this.des_equ = des_equ;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getDet_ima() {
        return det_ima;
    }

    public void setDet_ima(String det_ima) {
        this.det_ima = det_ima;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public DefaultStreamedContent getMimagen() {
        return mimagen;
    }

    public void setMimagen(DefaultStreamedContent mimagen) {
        ManEquipos.mimagen = mimagen;
    }

    
   
}
