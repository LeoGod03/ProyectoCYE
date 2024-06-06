/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package application.dao;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.ini4j.Ini;

/**
 *
 * @author leopa
 */
public class Conexion {
    
    private String base = "";
    private String usuario = "";
    private String contrasenia = "";
    private String ipConexion = "";
    private Connection conexion = null;
    
    public Conexion(){
    	String url = "src/application/files/config.ini";
    	StringBuilder cadena;
    	String[] cadenas = new String[4];
    	String[] cabeceras =  new String[] {"base", "usuario", "contrasenia", "ip"};
    	try {
    		Ini ini =  new Ini(new File(url));
    		for(int i = 0; i < 4; i++) {
    			cadena =  new StringBuilder(ini.get("BASEDEDATOS", cabeceras[i]));
        		cadena.deleteCharAt(0);
        		cadena.deleteCharAt(cadena.length()-1);
        		cadenas[i] = cadena.toString();
    		}
    		
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	base = cadenas[0];
    	usuario = cadenas[1];
    	contrasenia = cadenas[2];
    	ipConexion = cadenas[3];
    	 
       
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
