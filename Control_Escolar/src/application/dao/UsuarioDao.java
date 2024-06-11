
package application.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import application.modelo.Usuario;

public class UsuarioDao {
	// metodo para insertar un usuario
    public void insertar(Usuario usuario, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        //try{
        query = "INSERT INTO Usuarios VALUES(?,?,?,?)";
            
        comando = conexion.prepareStatement(query);
        // llenamos los parametros
        comando.setString(1,usuario.getCorreo());
        comando.setString(2, usuario.getContrasenia());
        comando.setInt(3, usuario.getLlave());
        comando.setString(4, usuario.getRol());
        comando.executeUpdate();
        comando.close();
       
        
    }
    // metodo para buscar un usuario
    public Usuario buscar (Usuario usuario, Connection conexion) throws SQLException{
        Usuario usuarioBusqueda = null;
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Usuarios WHERE correo like ?;";        
        comando = conexion.prepareStatement(query);
        comando.setString(1, usuario.getCorreo()); // llenamos el parametro
        resultado = comando.executeQuery();
        if(resultado.next()){ // si hay un registro creamos el objeto usuario
            usuarioBusqueda = new Usuario(resultado.getString("correo"),
                                          resultado.getString("contraseña"),
                                          resultado.getInt("llave"),
                                          resultado.getString("rol"));
        }
        comando.close();
         
        return usuarioBusqueda; // regresamos el usario buscado
    }
    // metodo para actualizar un usuario
    public void actualizar(Usuario usuario,Usuario oldUsuario, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        //try{
        query = "UPDATE Usuarios "
              + "SET correo = ?,"
              + "contraseña = ?,"
              + "llave = ?,"
              + "rol = ? "
              + "WHERE correo = ?;";
            
        comando = conexion.prepareStatement(query);
        // llenamos los parametros del comando
        comando.setString(1, usuario.getCorreo());
        comando.setString(2, usuario.getContrasenia());
        comando.setInt(3, usuario.getLlave());
        comando.setString(4, usuario.getRol());
        comando.setString(5,oldUsuario.getCorreo());
        comando.executeUpdate();
        comando.close();
    }
    // metodo para eliminar un usuario 
    public void eliminar(Usuario usuario, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        query = "DELETE FROM Usuarios WHERE correo like ?;";
        // llenamos los parametros necesarios del comando    
        comando = conexion.prepareStatement(query);
        comando.setString(1, usuario.getCorreo());
        comando.executeUpdate();
        comando.close();
    }
    
}
