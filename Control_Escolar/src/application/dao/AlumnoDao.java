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
import application.modelo.Curso;
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
                  + "INSERT INTO Cursos_inscritos_alumnos (matricula) VALUES(?);";
            
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
                                           resultado.getInt("cursos_inscritos"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                
                alumnoBuscado.setCursosInscritos(new CursosInscritosDao().obtenerCursos(alumnoBuscado));
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
                                           resultado.getInt("cursos_inscritos"),
                                           usuario);
                alumnoBuscado.setCursosInscritos(new CursosInscritosDao().obtenerCursos(alumnoBuscado));
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
                
                alumnoIteracion.setCursosInscritos(new CursosInscritosDao().obtenerCursos(alumnoIteracion));
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
                    + "cursos_inscritos = ?"
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
            comando.setString(6, alumno.getUsuario().getCorreo());
            comando.setInt(7, alumno.getNumeroCursos());
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
            query = "DELETE FROM Cursos_inscritos_alumnos WHERE matricula like ?" +
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
    
    public void inscribirCurso(Curso curso, Alumno alumno){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query = "UPDATE Cursos_inscritos_alumnos "
                    +"SET id_curso" + (alumno.getNumeroCursos() + 1) + " = ?,"
                    + "grupo"+ (alumno.getNumeroCursos() + 1) +" = ? "
                    + "WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);  
            comando = conexion.prepareStatement(query);
            comando.setInt(1, curso.getId());
            comando.setInt(2, curso.getGrupo());
            comando.setString(3, alumno.getMatricula());
            comando.executeUpdate();
            actualizarCursosInscritos(alumno, 1, conexion);
            conexion.commit();
            comando.close();
            alumno.setNumeroCursos(alumno.getNumeroCursos() + 1);
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
    
    public void darBajaCurso(Curso curso, Alumno alumno){
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Cursos_inscritos_alumnos WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            resultado = comando.executeQuery();
            int indice = 0;
            if(resultado.next()){
                System.out.println("entre");
                for(int i = 1; i <= alumno.getNumeroCursos(); i++){
                    int id = resultado.getInt("id_curso" + i);
                    int grupo = resultado.getInt("grupo"+i);
                    if(id == curso.getId() && grupo == curso.getGrupo()){
                       indice = i;
                       break;
                    }
                }
                if(indice > 0){
                    recorrerCursos(alumno, indice, conexion);
                    actualizarCursosInscritos(alumno, -1, conexion);
                    alumno.setNumeroCursos(alumno.getNumeroCursos() -1);
                }
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
    }
    
    private void recorrerCursos(Alumno alumno, int indice, Connection conexion)throws SQLException{
        PreparedStatement comando;
        String query;
        for(int i = indice; i < 7; i++ ){
            query = "UPDATE Cursos_inscritos_alumnos "
                   +"SET id_curso" + i + " = " + "id_curso" + (i+1) + ","
                  + "grupo" + i + " = " + "grupo" + (i+1) + " "
                  + "WHERE matricula like ?;";

            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.executeUpdate();
            comando.close();
        }
    }
    
    private void actualizarCursosInscritos(Alumno alumno, int factor, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query = "UPDATE Alumnos_registrados "
                     + "SET cursos_inscritos = cursos_inscritos + " + factor + " "
                     + "WHERE matricula like ?;";
        
        comando = conexion.prepareStatement(query);
        comando.setString(1, alumno.getMatricula());
        comando.executeUpdate();
        comando.close();
    }
    
}
