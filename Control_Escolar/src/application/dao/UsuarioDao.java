/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import application.modelo.Usuario;

/**
 *
 * @author leopa
 */
public class UsuarioDao {
    
    public void insertar(Usuario usuario, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        //try{
        query = "INSERT INTO Usuarios VALUES(?,?,?,?)";
            
        comando = conexion.prepareStatement(query);
        comando.setString(1,usuario.getCorreo());
        comando.setString(2, usuario.getContrasenia());
        comando.setInt(3, usuario.getLlave());
        comando.setString(4, usuario.getRol());
        comando.executeUpdate();
        comando.close();
       
        
    }
    
    public Usuario buscar (Usuario usuario, Connection conexion) throws SQLException{
        Usuario usuarioBusqueda = null;
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Usuarios WHERE correo like ?;";
            
        comando = conexion.prepareStatement(query);
        comando.setString(1, usuario.getCorreo());
        resultado = comando.executeQuery();
        if(resultado.next()){
            usuarioBusqueda = new Usuario(resultado.getString("correo"),
                                          resultado.getString("contraseña"),
                                          resultado.getInt("llave"),
                                          resultado.getString("rol"));
        }
        comando.close();
         
        return usuarioBusqueda;
    }
    
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
        comando.setString(1, usuario.getCorreo());
        comando.setString(2, usuario.getContrasenia());
        comando.setInt(3, usuario.getLlave());
        comando.setString(4, usuario.getRol());
        comando.setString(5,oldUsuario.getCorreo());
        comando.executeUpdate();
        comando.close();
    }
    
    public void eliminar(Usuario usuario, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query;
        //try{
        query = "DELETE FROM Usuarios WHERE correo like ?;";
            
        comando = conexion.prepareStatement(query);
        comando.setString(1, usuario.getCorreo());
        comando.executeUpdate();
        comando.close();
    }
    
}
