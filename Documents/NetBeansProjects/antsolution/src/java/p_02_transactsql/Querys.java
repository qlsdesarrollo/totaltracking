package p_02_transactsql;
import p_01_conexion.Conexion;


import java.sql.*;

public class Querys extends Conexion {

    private ResultSet resultado;
    

    public Querys() {
    }

    public String strQuerySQLvariable(String mQuery) {
        try {
            getStmt();
            String miSTR = "";
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miSTR = resultado.getString(1);
            }
            return miSTR;
        } catch (Exception e) {
            System.err.println("Sql Consulta String Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            return null;
        } finally {

        }
    }
    
    public int intQuerySQLvariable(String mQuery) {
        try {
            getStmt();
            int miSTR = 0;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miSTR = resultado.getInt(1);
            }
            return miSTR;
        } catch (Exception e) {
            System.err.println("Sql Consulta Integer Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            return 0;
        } finally {

        }
    }

    public Double doubleQuerySQLvariable(String mQuery) {
        try {
            getStmt();
            Double miDBL = 0.0;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miDBL = resultado.getDouble(1);
            }
            return miDBL;
        } catch (Exception e) {
            System.err.println("Sql Consulta Double Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            return null;
        } finally {

        }
    }

    public Date dateQuerySQLvariable(String mQuery) {
        try {
            getStmt();
            Date miDate = null;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miDate = resultado.getDate(1);
            }
            return miDate;
        } catch (Exception e) {
            System.err.println("Sql Consulta Date Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            return null;
        } finally {

        }
    }
    
    public ResultSet querySQLvariable(String mQuery) {
        try {
            getStmt();
            resultado = stmt.executeQuery(mQuery);
            return resultado;
        } catch (Exception e) {
            System.err.println("Sql Consulta ResultSet Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            return null;
        } finally {

        }
    }

    public void dmlSQLvariable(String mQuery) {
        try {
            getStmt();
            stmt.executeUpdate(mQuery);
        } catch (Exception e) {
            System.err.println("Sql Operación dml Variable Exception:" + e.getMessage() + " Query: " + mQuery);
        } finally {

        }
    }

}
