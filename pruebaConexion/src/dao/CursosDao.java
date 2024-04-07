
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Curso;

public class CursosDao {
   
    private Conexion administrador;
    
    public CursosDao(){
        administrador = new Conexion();
    }
    
    public void insertar(Curso curso, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        query = "INSERT INTO Lista_cursos VALUES(?,?,?,?)";
            
        comando = conexion.prepareStatement(query);
        comando.setInt(1,curso.getId());
        comando.setString(2, curso.getNombre());
        comando.setString(3, curso.getCiclo());
        comando.setString(4, curso.getColegio());
       
        comando.executeUpdate();
        comando.close();
    }
    
    public void eliminar(Curso curso, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        query = "DELETE FROM Lista_cursos WHERE id = ?";
            
        comando = conexion.prepareStatement(query);
        comando.setInt(1,curso.getId());       
        comando.executeUpdate();
        comando.close(); 
    }
}
