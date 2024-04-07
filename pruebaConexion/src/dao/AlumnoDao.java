/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import modelo.Alumno;
/**
 *
 * @author leopa
 */
public class AlumnoDao{
    
    private final Conexion administrador;
    
    public AlumnoDao(){
        administrador = new Conexion();
    }
    public void insertar(Alumno alumno) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Alumnos_registrados VALUES (?,?,?,?,?,?,?);"
                  + "INSERT INTO Cursos_inscritos_alumnos (matricula) VALUES(?);";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.setString(2, alumno.getNombre());
            comando.setString(3, alumno.getApellidoPaterno());
            comando.setString(4, alumno.getApellidoMaterno());
            comando.setInt(5, alumno.getIdCarrera());
            comando.setInt(6, alumno.getEdad());
            comando.setString(7, alumno.getUsuario().getCorreo());
            comando.setString(8, alumno.getMatricula());
            comando.executeUpdate();
            new UsuarioDao().insertar(alumno.getUsuario(), conexion);
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
        
    }

    public Alumno buscar(Alumno alumno){
        Alumno alumnoBuscado = null;
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Alumnos_registrados WHERE matricula like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                alumnoBuscado = new Alumno(resultado.getString("matricula"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getInt("edad"),
                                           resultado.getInt("id_carrera"),
                                           new UsuarioDao().buscar(alumno.getUsuario(), conexion));
            }
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
        
        return alumnoBuscado;
    }

    public void actualizar(Alumno alumno, Alumno oldAlumno) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "UPDATE Alumnos_registrados "
                    + "SET matricula = ?,"
                    + "nombre = ?,"
                    + "apellido_paterno = ?,"
                    + "apellido_materno = ?,"
                    + "id_carrera = ?,"
                    + "edad = ?,"
                    + "correo = ? "
                    + "WHERE matricula = ?;"
                  + "UPDATE Cursos_inscritos_alumnos "
                    + "SET matricula = ? "
                    + "WHERE matricula = ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.setString(2, alumno.getNombre());
            comando.setString(3, alumno.getApellidoPaterno());
            comando.setString(4, alumno.getApellidoMaterno());
            comando.setInt(5, alumno.getIdCarrera());
            comando.setInt(6, alumno.getEdad());
            comando.setString(7, alumno.getUsuario().getCorreo());
            comando.setString(8, oldAlumno.getMatricula());
            comando.setString(9, alumno.getMatricula());
            comando.setString(10, oldAlumno.getMatricula());
            comando.executeUpdate();
            new UsuarioDao().actualizar(alumno.getUsuario(), oldAlumno.getUsuario(), conexion);
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
    }

    public void eliminar(Alumno alumno) {
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Alumnos_registrados WHERE matricula like ?;"
                  + "DELETE FROM Cursos_inscritos_alumnos WHERE matricula like ?";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.setString(2, alumno.getMatricula());
            comando.executeUpdate();
            new UsuarioDao().eliminar(alumno.getUsuario(), conexion);
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
    }

    
}
