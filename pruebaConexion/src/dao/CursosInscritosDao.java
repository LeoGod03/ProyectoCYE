package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            new CursosDao().insertar(curso, conexion);
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
            query = "DELETE FROM Cursos_registrados WHERE id like ?;";
            comando = conexion.prepareStatement(query);
            comando.setInt(1, curso.getId());
            comando.executeUpdate();
            new CursosDao().eliminar(curso, conexion);
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
  

    
    public ArrayList<Curso> obtenerCursosInscritos(Alumno alumno){
        ArrayList<Curso> cursosInscritos = new ArrayList<>();
        
        return cursosInscritos;
    }
    
    public ArrayList<Curso> obtenerCursosInscritos(Profesor profesor){
        ArrayList<Curso> cursosInscritos = new ArrayList<>();
        
        return cursosInscritos;
    }
    
}
