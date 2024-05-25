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
            query = "SELECT * FROM Grupos_registrados WHERE id = ? AND grupo = ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, grupo.getId());
            comando.setInt(2, grupo.getGrupo());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                
                grupoBuscado = new Grupo(grupo.getId(), resultado.getInt("grupo"),
                						 resultado.getInt("id_profesor"),
                						 resultado.getInt("alumnos_registrados"));
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
    
    public ArrayList<Grupo> obtenerGruposDisponibles(){
    	ArrayList<Grupo> gruposDisponibles = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_registrados;";
        try{
            comando = conexion.prepareStatement(query);
            resultado = comando.executeQuery();
            while(resultado.next()){              
            	Grupo grupoIteracion = new Grupo(resultado.getInt("id"),
                        							  resultado.getInt("grupo"),
                        							  resultado.getInt("id_profesor"),
                        							  resultado.getInt("alumnos_registrados"));
            	
                gruposDisponibles.add(grupoIteracion);
                
                
            }
            conexion.commit();
            comando.close();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
            sqle.printStackTrace();
           
        }
        
        administrador.cerrarConexion();
        return gruposDisponibles;
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
                                         resultado.getInt("alumnos_registrados"));
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
    
    
    public void inscribirGrupo(Grupo grupo, Alumno alumno){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query = "UPDATE Grupos_inscritos_alumnos "
                    +"SET id_curso" + (alumno.getNumeroGrupos() + 1) + " = ?,"
                    + "grupo"+ (alumno.getNumeroGrupos() + 1) +" = ? "
                    + "WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);  
            comando = conexion.prepareStatement(query);
            comando.setInt(1, grupo.getId());
            comando.setInt(2, grupo.getGrupo());
            comando.setString(3, alumno.getMatricula());
            comando.executeUpdate();
            actualizarGruposInscritos(alumno, 1, conexion);
            actualizarNumeroAlumnos(grupo, 1, conexion);
            insertarGrupo(alumno, grupo, conexion);
            grupo.setAlumnosInscritos(grupo.getAlumnosInscritos() + 1);
            conexion.commit();
            comando.close();
            alumno.setNumeroGrupos(alumno.getNumeroGrupos() + 1);
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
    
    
    public void darBajaGrupo(Grupo grupo, Alumno alumno){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_inscritos_alumnos WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            resultado = comando.executeQuery();
            int indice = 0;
            if(resultado.next()){
                
                for(int i = 1; i <= alumno.getNumeroGrupos(); i++){
                    int id = resultado.getInt("id_curso" + i);
                    int numGrupo = resultado.getInt("grupo"+i);
                    
                    if(id == grupo.getId() && numGrupo == grupo.getGrupo()){
                       indice = i;
                       System.out.println(i);
                       break;
                    }
                }
                if(indice > 0){
                    recorrerGrupos(alumno, indice, conexion);
                    actualizarGruposInscritos(alumno, -1, conexion);
                    actualizarNumeroAlumnos(grupo, 1, conexion);
                    grupo.setAlumnosInscritos(grupo.getAlumnosInscritos() - 1);
                    alumno.setNumeroGrupos(alumno.getNumeroGrupos() -1);
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
    
    private void recorrerGrupos(Alumno alumno, int indice, Connection conexion)throws SQLException{
        PreparedStatement comando;
        String query;
        for(int i = indice; i < 7; i++ ){
            query = "UPDATE Grupos_inscritos_alumnos "
                   +"SET id_curso" + i + " = " + "id_curso" + (i+1) + ","
                  + "grupo" + i + " = " + "grupo" + (i+1) + " "
                  + "WHERE matricula like ?;";

            comando = conexion.prepareStatement(query);
            comando.setString(1, alumno.getMatricula());
            comando.executeUpdate();
            comando.close();
        }
    }
    
    private void actualizarGruposInscritos(Alumno alumno, int factor, Connection conexion) throws SQLException{
        PreparedStatement comando;
        String query = "UPDATE Alumnos_registrados "
                     + "SET grupos_inscritos = grupos_inscritos + " + factor + " "
                     + "WHERE matricula like ?;";
        
        comando = conexion.prepareStatement(query);
        comando.setString(1, alumno.getMatricula());
        comando.executeUpdate();
        comando.close();
    }
    
    private void actualizarNumeroAlumnos(Grupo grupo ,int factor, Connection conexion) throws SQLException{
    	PreparedStatement comando;
        String query = "UPDATE Grupos_registrados "
                     + "SET alumnos_registrados = alumnos_registrados + " + factor + " "
                     + "WHERE id = ? AND grupo = ?;";
        
        comando = conexion.prepareStatement(query);
        comando.setInt(1, grupo.getId());
        comando.setInt(2, grupo.getGrupo());
        comando.executeUpdate();
        comando.close();
    }
    
    private void insertarGrupo(Alumno alumno, Grupo grupo, Connection conexion) throws SQLException {
    	PreparedStatement comando;
        String query = "INSERT INTO G" + grupo.getId()+ "_" + grupo.getGrupo() + " VALUES(?,0,0,0,0);";
                    
        
        comando = conexion.prepareStatement(query);
        comando.setString(1, alumno.getMatricula());
        comando.executeUpdate();
        comando.close();
    	
    }
    
    public ArrayList<Alumno> obtenerAlumnos(Grupo grupo){
    	ArrayList<Alumno> alumnosInscritos = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM G" + grupo.getId() + "_" + grupo.getGrupo() +";";
        try{
            comando = conexion.prepareStatement(query);
            
            resultado = comando.executeQuery();
            while(resultado.next()){
                
                Alumno alumno = new AlumnoDao().buscar(new Alumno(resultado.getString("matricula")));
                alumnosInscritos.add(alumno);
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
        return alumnosInscritos;
    	
    }
    
    
    
}
