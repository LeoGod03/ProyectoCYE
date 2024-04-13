
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Curso;

public class CursosDao {
   
    private final Conexion administrador;
    
    public CursosDao(){
        administrador = new Conexion();
    }
    
    public void insertar(Curso curso){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        query = "INSERT INTO Lista_cursos VALUES(?,?,?,?)";
        try{    
            comando = conexion.prepareStatement(query);
            comando.setInt(1,curso.getId());
            comando.setString(2, curso.getNombre());
            comando.setString(3, curso.getCiclo());
            comando.setString(4, curso.getColegio());

            comando.executeUpdate();
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
    }
    
    public void eliminar(Curso curso){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        query = "DELETE FROM Lista_cursos WHERE id = ?";
        try{    
            comando = conexion.prepareStatement(query);
            comando.setInt(1,curso.getId());       
            comando.executeUpdate();
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
    }
    // metodo para buscar desde otro metodo con conexión compartida
    public Curso buscar(Curso curso, Connection conexion) throws SQLException{
        Curso cursoBusqueda = null;
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Lista_cursos WHERE id = ?;";
            
        comando = conexion.prepareStatement(query);
        comando.setInt(1, curso.getId());
        resultado = comando.executeQuery();
        if(resultado.next()){
            cursoBusqueda = new Curso(curso.getId(),
                                      resultado.getString("nombre"),
                                      resultado.getString("ciclo"),
                                      resultado.getString("colegio"));
        }
        comando.close();
         
        return cursoBusqueda;
    }
    // metodo para buscar desde el sistema
    public Curso buscar(Curso curso){
        Connection conexion = administrador.establecerConexion();
        Curso cursoBusqueda = null;
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Lista_cursos WHERE id = ?;";
        try{    
            comando = conexion.prepareStatement(query);
            comando.setInt(1, curso.getId());
            resultado = comando.executeQuery();
            if(resultado.next()){
                cursoBusqueda = new Curso(curso.getId(),
                                          resultado.getString("nombre"),
                                          resultado.getString("ciclo"),
                                          resultado.getString("colegio"));
            }
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
        return cursoBusqueda;
    }
}
