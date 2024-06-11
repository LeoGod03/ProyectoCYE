package application.dao;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.ini4j.Ini;

public class Conexion {
	
	// atributos necesarios para la conexión
    
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
    	// leemos el ini para obtener los datos de la conexión
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
    // metodo para establecer la conexión
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
    
    // metodo para cerrar la conexión
    public void cerrarConexion(){
        try{
            conexion.close();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
    }
}
