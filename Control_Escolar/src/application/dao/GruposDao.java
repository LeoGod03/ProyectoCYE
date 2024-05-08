package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.modelo.Alumno;
import application.modelo.Grupo;
import application.modelo.Profesor;


public class GruposDao {
    
    private final Conexion administrador;
    
    public GruposDao(){
        administrador = new Conexion();
    }
    
    public void insertar(Grupo grupo){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Grupos_registrados VALUES (?,?,?,?);";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1,grupo.getId());
            comando.setInt(2, grupo.getGrupo());
            comando.setInt(3,0);
            comando.setInt(4,grupo.getIdProfesor());
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
    
    public void eliminar(Grupo grupo){
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Grupos_registrados WHERE id = ? && grupo = ?;";
            comando = conexion.prepareStatement(query);
            comando.setInt(1, grupo.getId());
            comando.setInt(2, grupo.getGrupo());
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
  
    public Grupo buscar(Grupo grupo){
        Grupo grupoBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Grupos_registrados WHERE id = ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, grupo.getId());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                
                grupoBuscado = new Grupo(grupo.getId(), resultado.getInt("grupo"),
                						 resultado.getInt("alumnos_inscirots"),
                						 resultado.getInt(""));
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
        return grupoBuscado;
    }
    
     
    public ArrayList<Grupo> obtenerGrupos(Alumno alumno){
        ArrayList<Grupo> gruposInscritos = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_inscritos_alumnos WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            resultado = comando.executeQuery();
            if(resultado.next()){
                System.out.println("entre");
                for(int i = 1; i <= alumno.getNumeroGrupos(); i++){
                    int id = resultado.getInt("id_curso" + i);
                    int grupo = resultado.getInt("grupo"+i);
                    
                        Grupo grupoIteracion = buscar(new Grupo(id, grupo));
                        gruposInscritos.add(grupoIteracion);
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
        return gruposInscritos;
    }
    
    public ArrayList<Grupo> obtenerGrupos(Profesor profesor){
        ArrayList<Grupo> gruposInscritos = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_registrados WHERE id_profesor = ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            
            resultado = comando.executeQuery();
            while(resultado.next()){
                
                Grupo grupo = new Grupo(resultado.getInt("id"),
                                        resultado.getInt("grupo"),
                                        resultado.getInt("id_profesor"),
                                         resultado.getInt("alumnos_inscritos"));
                gruposInscritos.add(grupo);
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
        return gruposInscritos;
    }
    
    
    
   
}
