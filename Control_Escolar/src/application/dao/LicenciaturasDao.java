package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LicenciaturasDao {
	private final Conexion administrador;
	
	public LicenciaturasDao() {
		administrador = new Conexion();
	}
	
	public ArrayList<String> obtenerLicenciaturas(){
		ArrayList<String> nombres = new ArrayList<>();
		Connection conexion = administrador.establecerConexion();
		PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT nombre FROM Licenciaturas;";
            
            comando = conexion.prepareStatement(query);
            
            resultado = comando.executeQuery();
            
            while(resultado.next())
            	nombres.add(resultado.getString("nombre"));
            
            conexion.commit();
            comando.close();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
            
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        administrador.cerrarConexion();
		
		
		return nombres;
	}
}
