/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package application.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author leopa
 */
public class Conexion {
    
    private final String base;
    private final String usuario;
    private final String contrasenia;
    private final String ipConexion;
    private Connection conexion;
    
    public Conexion(){
        
        base = "BaseDatosCyES";
        usuario = "Fatima";
        contrasenia = "Adrifati1.";
        ipConexion = "58281";
    }
    
    public Connection establecerConexion(){
         String url = "jdbc:sqlserver://localhost:"+ ipConexion +";"
                   + "database ="+ base +";"
                   + "user = "+ usuario +";"
                   + "password ="+ contrasenia +";"
                   + "loginTimeout = 30;"
                   + "TrustServerCertificate=True;";
        conexion = null;
        try{
            conexion = DriverManager.getConnection(url);
            System.out.println("conexion exitosa");
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return conexion;
    }
    
    public void cerrarConexion(){
        try{
            conexion.close();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
    }
}
