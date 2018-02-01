package tt.general;

import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class Conexion {

    protected Connection con;
    protected Statement stmt;
    private String serverName = "localhost";
    private String portNumber = "3306";
    public static String databaseName = "totalt"; //"harolda1_distasoft001";
    private String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private String userName = "root"; //"root"; "harolda1_root";
    private String password = ""; //"Dist@soft.."; //"W3bS0l..."; //"H@rold2015..";
    private String errString = "";

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
 
    public Conexion() {
    }
    
    private String getConnectionUrl(){
        return url;
        
    }
    
    public Connection Conectar(){
        con = null;
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            con=DriverManager.getConnection(getConnectionUrl(), userName, password);
            stmt = con.createStatement();
            //System.out.println(stmt);
            //System.out.println("Conectado");
        }catch (ClassNotFoundException | SQLException e){            
            errString = "Error mientras se conectaba a la BD. " + e.getMessage();
            System.out.println(errString);
            return null;
            
        }
        return con;
    }
    
    public void Desconectar(){
        try{
            stmt.close();
            con.close();
            //System.out.println(stmt + " Desconectado");
        }catch(Exception e){
            errString="Error mientras se cerraba la Conexión " + e.getMessage();
            System.out.println(errString);
        }
    }
    
    public Statement getStmt(){
        return this.stmt;
        
    }
    
    
}
