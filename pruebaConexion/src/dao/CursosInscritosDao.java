package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Alumno;
import modelo.Curso;
import modelo.Profesor;


public class CursosInscritosDao {
    
    private final Conexion administrador;
    
    public CursosInscritosDao(){
        administrador = new Conexion();
    }
    
    public void insertar(Curso curso){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Cursos_registrados VALUES (?,?,?,?);";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1,curso.getId());
            comando.setInt(2, curso.getGrupo());
            comando.setInt(3,0);
            comando.setInt(4,curso.getIdProfesor());
            comando.executeUpdate();
            conexion.commit();
            comando.close();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        administrador.cerrarConexion();
    }
    
    public void eliminar(Curso curso){
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Cursos_registrados WHERE id = ? && grupo = ?;";
            comando = conexion.prepareStatement(query);
            comando.setInt(1, curso.getId());
            comando.setInt(2, curso.getGrupo());
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
  
    public Curso buscar(Curso curso){
        Curso cursoBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Cursos_registrados WHERE id = ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, curso.getId());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                
                Curso cursoTemp = new CursosDao().buscar(curso, conexion);
                
                cursoBuscado = new Curso(curso.getId(),
                                         cursoTemp.getNombre(),
                                         resultado.getInt("grupo"),
                                         resultado.getInt("id_profesor"),
                                         cursoTemp.getCiclo(),
                                         cursoTemp.getColegio());
            }
            conexion.commit();
            comando.close();
        }
        catch(SQLException sqle){
            System.out.println(sqle.getMessage());
            
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        administrador.cerrarConexion();
        return cursoBuscado;
    }
    
    public Curso buscarGrupo(Curso curso){
        Curso cursoBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Cursos_registrados WHERE id = ? AND grupo = ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, curso.getId());
            comando.setInt(2, curso.getGrupo());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                
                Curso cursoTemp = new CursosDao().buscar(curso, conexion);
                
                cursoBuscado = new Curso(curso.getId(),
                                         cursoTemp.getNombre(),
                                         resultado.getInt("grupo"),
                                         resultado.getInt("id_profesor"),
                                         cursoTemp.getCiclo(),
                                         cursoTemp.getColegio());
            }
            conexion.commit();
            comando.close();
        }
        catch(SQLException sqle){
            System.out.println(sqle.getMessage());
            
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        administrador.cerrarConexion();
        return cursoBuscado;
    }
    
    public ArrayList<Curso> obtenerCursos(Alumno alumno){
        ArrayList<Curso> cursosInscritos = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Cursos_inscritos_alumnos WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            resultado = comando.executeQuery();
            if(resultado.next()){
                System.out.println("entre");
                for(int i = 1; i <= alumno.getNumeroCursos(); i++){
                    int id = resultado.getInt("id_curso" + i);
                    int grupo = resultado.getInt("grupo"+i);
                    //if(id > 0){
                        Curso curso = buscarGrupo(new Curso(id,grupo,0));
                        cursosInscritos.add(curso);
                    //}
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
        return cursosInscritos;
    }
    
    public ArrayList<Curso> obtenerCursos(Profesor profesor){
        ArrayList<Curso> cursosInscritos = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Cursos_registrados WHERE id_profesor = ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            
            resultado = comando.executeQuery();
            while(resultado.next()){
                int id = resultado.getInt("id");
                Curso cursoTemp = new CursosDao().buscar( new Curso(id), conexion);
                
                Curso curso = new Curso(id,
                                         cursoTemp.getNombre(),
                                         resultado.getInt("grupo"),
                                         resultado.getInt("id_profesor"),
                                         cursoTemp.getCiclo(),
                                         cursoTemp.getColegio());
                cursosInscritos.add(curso);
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
        return cursosInscritos;
    }
    
    
    
    public void inscribir(Curso curso, Alumno alumno){
        
    }
}
