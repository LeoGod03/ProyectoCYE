/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Alumno;
import modelo.Curso;
import modelo.Usuario;
/**
 *
 * @author leopa
 */
public class AlumnoDao{
    
    private final Conexion administrador;
    
    public static enum BUSQUEDA{
        MATRICULA,
        NOMBREAPELLIDO
    }
    
    public AlumnoDao(){
        administrador = new Conexion();
    }
    public void insertar(Alumno alumno) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Alumnos_registrados VALUES (?,?,?,?,?,?,?,?);"
                  + "INSERT INTO Cursos_inscritos_alumnos (matricula) VALUES(?);";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.setString(2, alumno.getNombre());
            comando.setString(3, alumno.getApellidoPaterno());
            comando.setString(4, alumno.getApellidoMaterno());
            comando.setInt(5, alumno.getIdCarrera());
            comando.setInt(6, alumno.getEdad());
            comando.setString(7, alumno.getUsuario().getCorreo());
            comando.setInt(8, 0);
            comando.setString(9, alumno.getMatricula());
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
                                           resultado.getInt("cursos_inscritos"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
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
    
     public ArrayList<Alumno> buscar(Alumno alumno, BUSQUEDA busqueda){
        ArrayList<Alumno> alumnosBuscados = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Alumnos_registrados WHERE ";
            if(busqueda == BUSQUEDA.MATRICULA)
                query += "matricula like '%' + ? + '%';";
            else
                query += "nombre like '%(?)%' OR apellido_paterno like '%?%' OR apellido_materno like '%(?)%'";
            
            
            comando = conexion.prepareStatement(query);
            
            if(busqueda == BUSQUEDA.MATRICULA)
                comando.setString(1, alumno.getMatricula());
            else{
                comando.setString(1, alumno.getNombre());
                comando.setString(2, alumno.getApellidoPaterno());
                comando.setString(3, alumno.getApellidoMaterno());
            }
            
            resultado = comando.executeQuery();
            Alumno alumnoIteracion;
            while(resultado.next()){
                alumnoIteracion = new Alumno(resultado.getString("matricula"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getInt("edad"),
                                           resultado.getInt("id_carrera"),
                                           resultado.getInt("cursos_inscritos"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                
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
                    + "edad = ?,"
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
            comando.setInt(6, alumno.getEdad());
            comando.setString(7, alumno.getUsuario().getCorreo());
            comando.setInt(8, alumno.getNumeroCursos());
            comando.setString(9, oldAlumno.getMatricula());
            comando.setString(10, alumno.getMatricula());
            comando.setString(11, oldAlumno.getMatricula());
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
