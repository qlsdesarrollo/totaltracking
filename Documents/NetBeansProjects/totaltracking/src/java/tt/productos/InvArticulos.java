package tt.productos;

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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import tt.general.Accesos;
import tt.general.CatMarcas;
import tt.general.Login;

@Named
@ConversationScoped
public class InvArticulos implements Serializable {

    private static final long serialVersionUID = 8799478674716638L;
    @Inject
    Login cbean;
    private Accesos macc;

    private List<CatFamilia> familia;
    private List<CatMarcas> marcas;
    private List<CatEmbalaje> embalaje;
    //private List<CatMovimientos> movimientos;
    //private List<CatClientes> clientes;

    //private List<CatProveedores> proveedores;
    private CatArticulos catarticulos;
    private List<CatArticulos> articulos;

    private String id_art, cod_art, det_mar, det_fam, det_nom, det_des, det_lot, fec_ven, flg_ref, tem_ref, img_pro, det_emb, reg_san, pai_ori, cod_pai, cod_alt;
    private String boolref;

    private String mheight;

    private static DefaultStreamedContent mimagen;

    public InvArticulos() {
    }

    public void iniciarventana() {
        macc = new Accesos();
        id_art = "";
        cod_art = "";
        det_mar = "0";
        det_fam = "0";
        det_nom = "";
        det_des = "";
        det_lot = "";
        fec_ven = "";
        flg_ref = "0";
        tem_ref = "";
        img_pro = "";
        det_emb = "";
        reg_san = "";
        boolref = "true";
        pai_ori = "";

        cod_pai = cbean.getCod_pai();

        mimagen = null;
        mheight = "175";

        familia = new ArrayList<>();
        marcas = new ArrayList<>();
        embalaje = new ArrayList<>();

        catarticulos = new CatArticulos();
        articulos = new ArrayList<>();

        llenarFamilias();
        llenarMarcas();
        llenarEmbalaje();
    }

    public void nuevo() {
        id_art = "";
        cod_art = "";
        det_mar = "0";
        det_fam = "0";
        det_nom = "";
        det_des = "";
        det_lot = "";
        fec_ven = "";
        flg_ref = "0";
        tem_ref = "";
        img_pro = "";
        det_emb = "0";
        reg_san = "";
        pai_ori = "";
        cod_pai = cbean.getCod_pai();
        boolref = "true";

        mimagen = null;
        mheight = "175";

        catarticulos = null;
        articulos.clear();

    }

    public void cerrarventana() {
        id_art = "";
        cod_art = "";
        det_mar = "";
        det_fam = "";
        det_nom = "";
        det_des = "";
        det_lot = "";
        fec_ven = "";
        flg_ref = "0";
        tem_ref = "";
        img_pro = "";
        det_emb = "0";
        reg_san = "";
        boolref = "true";
        pai_ori = "";

        mimagen = null;
        mheight = "";

        familia = null;
        marcas = null;
        embalaje = null;

        catarticulos = null;
        articulos = null;

        macc = null;
    }

    // ***************** Llenado de Catálogos **************************
    public void llenarFamilias() {
        String mQuery = "";
        try {

            familia.clear();

            mQuery = "select id_fam, nom_fam from inv_cat_familia order by id_fam;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                familia.add(new CatFamilia(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Familias en Catálogo Articulos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarMarcas() {
        String mQuery = "";
        try {

            marcas.clear();

            mQuery = "select id_mar, nom_mar from inv_cat_mar order by id_mar;";
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
            System.out.println("Error en el llenado de Marcas en Catálogo Articulos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarEmbalaje() {
        String mQuery = "";
        try {

            embalaje.clear();

            mQuery = "select id_emb, nom_emb from inv_cat_embalaje order by id_emb;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                embalaje.add(new CatEmbalaje(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Embalaje en Catálogo Articulos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarArticulos() {
        String mQuery = "";
        try {
            catarticulos = null;
            articulos.clear();

            String mWhere = "";
            if (!"".equals(cod_art)) {
                mWhere = " and cod_art='" + cod_art + "' ";
            }
            if (!"0".equals(det_mar)) {
                mWhere = " and det_mar=" + det_mar + " ";
            }
            if (!"0".equals(det_fam)) {
                mWhere = " and det_fam=" + det_fam + " ";
            }
            if (!"".equals(det_lot)) {
                mWhere = " and det_lot='" + det_lot + "' ";
            }

            mQuery = "select "
                    + "id_art,"
                    + "cod_art,"
                    + "det_mar,"
                    + "det_fam,"
                    + "det_nom,"
                    + "det_des,"
                    + "det_lot,"
                    + "fec_ven,"
                    + "flg_ref,"
                    + "tem_ref,"
                    + "img_pro,"
                    + "det_emb,"
                    + "reg_san,"
                    + "(select nom_emb from inv_cat_embalaje as emb where emb.id_emb=det_emb) as nomemb,"
                    + "pai_ori,"
                    + "cod_pai,"
                    + "cod_alt "
                    + "from "
                    + "inv_cat_articulo "
                    + "where cod_pai =" + cod_pai + " "
                    + mWhere
                    + " order by cod_art, det_emb;";
            ResultSet resVariable;
            //Accesos mAccesos = new Accesos();
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                articulos.add(new CatArticulos(
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
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Catálogo Artículos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    // ****************** Procesos Mantenimiento ***********************
    public boolean validardatos() {
        boolean mValidar = true;
        if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            mValidar = false;
            addMessage("Validar Datos", "Este Usuario no está relacionado a Ningún País.", 2);
        }
        if ("".equals(det_nom)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre para el Artículo.", 2);
        } else {
            macc.Conectar();
            if ("".equals(id_art)) {
                if (!"0".equals(macc.strQuerySQLvariable("select count(id_art) from inv_cat_articulo "
                        + "where upper(det_nom)='" + det_nom.toUpperCase()
                        + "' and cod_pai = " + cod_pai + ";"))) {
                    mValidar = false;
                    addMessage("Validar Datos", "El Nombre del Artículo ya existe", 2);
                }
            } else if (!"0".equals(macc.strQuerySQLvariable("select count(id_art) from inv_cat_articulo "
                    + "where upper(det_nom)='" + det_nom.toUpperCase()
                    + "' and id_art <> " + id_art + " and det_emb=" + det_emb + " and cod_pai = " + cod_pai + ";"))) {
                mValidar = false;
                addMessage("Validar Datos", "El Nombre del Artículo ya existe", 2);
            }
            macc.Desconectar();
        }
        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                //String nTemporal = img_pro.replace("/resources/images/temp/", "").replace("/resources/images/articulos/", "");
                //Accesos mAccesos = new Accesos();
                macc.Conectar();
                if ("".equals(id_art)) {
                    mQuery = "select ifnull(max(id_art),0)+1 as codigo from inv_cat_articulo;";
                    id_art = macc.strQuerySQLvariable(mQuery);
                    //img_pro = "/resources/images/articulos/" + "art_" + id_art + ".jpg";
                    mQuery = "insert into inv_cat_articulo (id_art,cod_art,det_mar,det_fam,"
                            + "det_nom,det_des,det_lot,fec_ven,flg_ref,tem_ref,img_pro,det_emb,reg_san,pai_ori,cod_pai,cod_alt) "
                            + "values (" + id_art + ",'" + cod_art + "'," + det_mar
                            + "," + det_fam + ",'" + det_nom + "','" + det_des + "','" + det_lot
                            + "',now()," + flg_ref + ",'" + tem_ref
                            + "','" + img_pro + "'," + det_emb + ",'" + reg_san + "','" + pai_ori + "'," + cod_pai + ",'" + cod_alt + "');";
                } else {
                    mQuery = "update inv_cat_articulo SET "
                            + "cod_art = '" + cod_art
                            + "',det_mar = " + det_mar
                            + ",det_fam = " + det_fam
                            + ",det_nom = '" + det_nom
                            + "',det_des = '" + det_des
                            + "',det_lot = '" + det_lot
                            + "',flg_ref = " + flg_ref
                            + ",tem_ref = '" + tem_ref
                            + "',det_emb = " + det_emb
                            + ",reg_san = '" + reg_san + "' "
                            + ",pai_ori = '" + pai_ori + "' "
                            + ",cod_alt = '" + cod_alt + "' "
                            + "WHERE id_art = " + id_art + ";";

                }
                macc.dmlSQLvariable(mQuery);

                if (!"".equals(img_pro)) {
                    try {
                        // ****************************  Inserta Imagen en tabla ************************************************************
                        try {

                            FileInputStream fis = null;
                            PreparedStatement ps = null;
                            File file = null;
                            try {
                                macc.Conectar().setAutoCommit(false);
                                file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(img_pro));
                                fis = new FileInputStream(file);
                                if ("0".equals(macc.strQuerySQLvariable("select count(id_art) conta from inv_cat_articulo_img where id_art=" + id_art + ";"))) {
                                    mQuery = "insert into inv_cat_articulo_img(id_art,det_ima) values(?,?)";
                                    ps = macc.Conectar().prepareStatement(mQuery);
                                    ps.setString(1, id_art);
                                    ps.setBinaryStream(2, fis, (int) file.length());
                                } else {
                                    mQuery = "update inv_cat_articulo_img set det_ima = ? where id_art=?;";
                                    ps = macc.Conectar().prepareStatement(mQuery);
                                    ps.setBinaryStream(1, fis, (int) file.length());
                                    ps.setString(2, id_art);
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
                addMessage("Guardar Artículos", "Información Almacenada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Guardar Artículos", "Error al momento de guardar la información. " + e.getMessage(), 2);
                System.out.println("Error al Guardar Artículos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarArticulos();
        }
        nuevo();

    }

    public boolean validareliminar() {
        boolean mvalidar = true;
        if ("".equals(id_art)) {
            addMessage("Eliminar Artículos", "Debe elegir un Registro.", 2);
            return false;
        } else {
            macc.Conectar();
            if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) as contador from inv_tbl_transacciones_det where cod_art =" + id_art + ";"))) {
                addMessage("Eliminar Artículos", "Este Artículo tiene registros relacionados en inventario y no puede ser eliminado", 2);
                mvalidar = false;
            }
            if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) as contador from inv_tbl_historico where cod_art =" + id_art + ";"))) {
                addMessage("Eliminar Artículos", "Este Artículo tiene registros relacionados en registro histórico y no puede ser eliminado", 2);
                mvalidar = false;
            }
            if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) as contador from inv_tbl_lot_ven where cod_art =" + id_art + ";"))) {
                addMessage("Eliminar Artículos", "Este Artículo tiene registros relacionados en tabla control de lotes y no puede ser eliminado", 2);
                mvalidar = false;
            }
            macc.Desconectar();
        }
        return mvalidar;
    }

    public void eliminar() {
        String mQuery = "";
     
        if (validareliminar()) {
            try {
                macc.Conectar();
                mQuery = "delete from inv_cat_articulo_img where id_art=" + id_art + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from inv_cat_articulo where id_art=" + id_art + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Eliminar Artículos", "Información Eliminada con éxito.", 1);
                macc.Desconectar();
            } catch (Exception e) {
                addMessage("Eliminar Artículos", "Error al momento de Eliminar la información. " + e.getMessage(), 2);
                System.out.println("Error al Eliminar Artículos. " + e.getMessage() + " Query: " + mQuery);
            }
            llenarArticulos();
            nuevo();
        }

    }

    public void recuperarimagen() {
        String mQuery = "";
        macc.Conectar();
        mimagen = null;
        try {
            mQuery = "select det_ima from inv_cat_articulo_img where id_art=" + id_art + ";";
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 175.0);
                img = null;

                data = null;
            } else {
                mQuery = "select det_ima from inv_cat_articulo_img where id_art=0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 175.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            System.out.println("Error en recuperarimagen InvArticulos. " + e.getMessage() + " Query: " + mQuery);

        }

        macc.Desconectar();
    }

    public void upload(FileUploadEvent event) {
        try {
            Random rnd = new Random();
            String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));
            copyFile("art_temp_" + prefijo + ".jpg", event.getFile().getInputstream());

            mimagen = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
            mheight = "175";

        } catch (Exception e) {
            addMessage("Procesar Archivo", "El Archivo " + event.getFile().getFileName() + " No se ha podido Cargar. " + e.getMessage(), 2);
            System.out.println("Error en subir archivo Producto." + e.getMessage());
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            img_pro = "/resources/images/temp/" + fileName;

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
            img_pro = "";
            addMessage("Copiar Imagen Artículo", "La Imagen en copyFyle " + fileName + " de ManArtículos No se ha podido procesar. " + e.getMessage(), 2);
            System.out.println(e.getMessage());

        }
    }

    public void onRowSelect(SelectEvent event) {
        id_art = ((CatArticulos) event.getObject()).getId_art();
        flg_ref = ((CatArticulos) event.getObject()).getFlg_ref();
        if ("1".equals(flg_ref)) {
            flg_ref = "false";
            boolref = "false";
            tem_ref = "Temp. Ambiente";
        } else {
            flg_ref = "true";
            boolref = "true";
        }

        cod_art = ((CatArticulos) event.getObject()).getCod_art();
        det_mar = ((CatArticulos) event.getObject()).getDet_mar();
        det_fam = ((CatArticulos) event.getObject()).getDet_fam();
        det_nom = ((CatArticulos) event.getObject()).getDet_nom();
        det_des = ((CatArticulos) event.getObject()).getDet_des();
        det_lot = ((CatArticulos) event.getObject()).getDet_lot();
        fec_ven = ((CatArticulos) event.getObject()).getFec_ven();

        tem_ref = ((CatArticulos) event.getObject()).getTem_ref();
        img_pro = ((CatArticulos) event.getObject()).getImg_pro();
        det_emb = ((CatArticulos) event.getObject()).getDet_emb();
        reg_san = ((CatArticulos) event.getObject()).getReg_san();
        pai_ori = ((CatArticulos) event.getObject()).getPai_ori();
        if ("1".equals(flg_ref)) {
            flg_ref = "false";
            boolref = "false";
            tem_ref = "Temp. Ambiente";
        } else {
            flg_ref = "true";
            boolref = "true";
        }

        recuperarimagen();

        RequestContext.getCurrentInstance().update("frmArt");
    }

    public void onChangeSwitch() {
        if ("false".equals(flg_ref)) {
            boolref = "false";
            tem_ref = "Temp. Ambiente";

        } else {
            boolref = "true";

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

    public List<CatFamilia> getFamilia() {
        return familia;
    }

    public void setFamilia(List<CatFamilia> familia) {
        this.familia = familia;
    }

    public List<CatMarcas> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<CatMarcas> marcas) {
        this.marcas = marcas;
    }

    public List<CatEmbalaje> getEmbalaje() {
        return embalaje;
    }

    public void setEmbalaje(List<CatEmbalaje> embalaje) {
        this.embalaje = embalaje;
    }

    public CatArticulos getCatarticulos() {
        return catarticulos;
    }

    public void setCatarticulos(CatArticulos catarticulos) {
        this.catarticulos = catarticulos;
    }

    public List<CatArticulos> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<CatArticulos> articulos) {
        this.articulos = articulos;
    }

    public String getId_art() {
        return id_art;
    }

    public void setId_art(String id_art) {
        this.id_art = id_art;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getDet_mar() {
        return det_mar;
    }

    public void setDet_mar(String det_mar) {
        this.det_mar = det_mar;
    }

    public String getDet_fam() {
        return det_fam;
    }

    public void setDet_fam(String det_fam) {
        this.det_fam = det_fam;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getDet_des() {
        return det_des;
    }

    public void setDet_des(String det_des) {
        this.det_des = det_des;
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

    public String getFlg_ref() {
        return flg_ref;
    }

    public void setFlg_ref(String flg_ref) {
        this.flg_ref = flg_ref;
    }

    public String getTem_ref() {
        return tem_ref;
    }

    public void setTem_ref(String tem_ref) {
        this.tem_ref = tem_ref;
    }

    public String getImg_pro() {
        return img_pro;
    }

    public void setImg_pro(String img_pro) {
        this.img_pro = img_pro;
    }

    public String getDet_emb() {
        return det_emb;
    }

    public void setDet_emb(String det_emb) {
        this.det_emb = det_emb;
    }

    public String getReg_san() {
        return reg_san;
    }

    public void setReg_san(String reg_san) {
        this.reg_san = reg_san;
    }

    public String getPai_ori() {
        return pai_ori;
    }

    public void setPai_ori(String pai_ori) {
        this.pai_ori = pai_ori;
    }

    public String getBoolref() {
        return boolref;
    }

    public void setBoolref(String boolref) {
        this.boolref = boolref;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
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
        InvArticulos.mimagen = mimagen;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

}
