/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import application.modelo.Alumno;
import application.modelo.EnumBusquedas;
import application.modelo.Usuario;
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
                  + "INSERT INTO Grupos_inscritos_alumnos (matricula) VALUES(?);";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.setString(2, alumno.getNombre());
            comando.setString(3, alumno.getApellidoPaterno());
            comando.setString(4, alumno.getApellidoMaterno());
            comando.setInt(5, alumno.getIdCarrera());
            comando.setString(6, alumno.getUsuario().getCorreo());
            comando.setInt(7, 0);
            comando.setString(8, alumno.getMatricula());
            new UsuarioDao().insertar(alumno.getUsuario(), conexion);
            comando.executeUpdate();
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
                                           resultado.getInt("id_carrera"),
                                           resultado.getInt("grupos_inscritos"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                
                alumnoBuscado.setGruposInscritos(new GruposDao().obtenerGrupos(alumnoBuscado));
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
    
    public Alumno buscar(Usuario usuario){
        Alumno alumnoBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Alumnos_registrados WHERE correo like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, usuario.getCorreo());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                alumnoBuscado = new Alumno(resultado.getString("matricula"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getInt("id_carrera"),
                                           resultado.getInt("grupos_inscritos"),
                                           usuario);
                alumnoBuscado.setGruposInscritos(new GruposDao().obtenerGrupos(alumnoBuscado));
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

    
    public ArrayList<Alumno> buscar(Alumno alumno, EnumBusquedas.BUSQUEDA busqueda){
    	ArrayList<Alumno> alumnosBuscados = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Alumnos_registrados WHERE ";
            if(busqueda == EnumBusquedas.BUSQUEDA.MATRICULA)
                query += "matricula like '%' + ? + '%';";
            else
                query += "nombre like '%' + ? + '%' OR apellido_paterno like '%' + ? + '%' OR apellido_materno like '%' + ? + '%';";
            
            
            comando = conexion.prepareStatement(query);
            
            if(busqueda == EnumBusquedas.BUSQUEDA.MATRICULA)
                comando.setString(1, alumno.getMatricula());
            else{
                comando.setString(1, alumno.getNombre());
                comando.setString(2, alumno.getNombre());
                comando.setString(3, alumno.getNombre());
            }
            
            resultado = comando.executeQuery();
            Alumno alumnoIteracion;
            while(resultado.next()){
                
                // creamos un alumno con todos los datos que obtuvimos
                alumnoIteracion = new Alumno(resultado.getString("matricula"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getInt("id_carrera"),
                                           resultado.getInt("cursos_inscritos"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                
                alumnoIteracion.setGruposInscritos(new GruposDao().obtenerGrupos(alumnoIteracion));
                alumnosBuscados.add(alumnoIteracion);
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
        
        return alumnosBuscados;
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
                    + "correo = ?,"
                    + "grupos_inscritos = ?"
                    + "WHERE matricula = ?;"
                  + "UPDATE Grupos_inscritos_alumnos "
                    + "SET matricula = ? "
                    + "WHERE matricula = ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.setString(2, alumno.getNombre());
            comando.setString(3, alumno.getApellidoPaterno());
            comando.setString(4, alumno.getApellidoMaterno());
            comando.setInt(5, alumno.getIdCarrera());
            comando.setString(6, alumno.getUsuario().getCorreo());
            comando.setInt(7, alumno.getNumeroGrupos());
            comando.setString(8, oldAlumno.getMatricula());
            comando.setString(9, alumno.getMatricula());
            comando.setString(10, oldAlumno.getMatricula());
            new UsuarioDao().actualizar(alumno.getUsuario(), oldAlumno.getUsuario(), conexion);
            comando.executeUpdate();
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
            query = "DELETE FROM Grupos_inscritos_alumnos WHERE matricula like ?" +
            		"DELETE FROM Alumnos_registrados WHERE matricula like ?;";
            
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
