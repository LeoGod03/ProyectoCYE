package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import application.modelo.Profesor;
import application.modelo.Usuario;

public class ProfesorDao {
     private final Conexion administrador;
    
    public ProfesorDao(){
        administrador = new Conexion();
    }
    // metodo para obtener a los profesores del sistema
    public ArrayList<Profesor> obtenerProfesoresSistema(){
        ArrayList<Profesor> profesores = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Profesores_registrados;";
            
            comando = conexion.prepareStatement(query);
            resultado = comando.executeQuery();
            Profesor profesor;
            while(resultado.next()){ // llenamos la lista con los profesores encontrados
                profesor = new Profesor(resultado.getInt("id"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getString("cubiculo"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                                          
            
                profesores.add(profesor);
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
        
        return profesores; // regresamos esa lista de profesores
    }
    // metodo para insertar alumnos al sistema
    public void insertar(Profesor profesor) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Profesores_registrados VALUES (?,?,?,?,?,?);";
            comando = conexion.prepareStatement(query);
            // llenamos los datos con los datos necesarios del profesor
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoPaterno());
            comando.setString(4, profesor.getApellidoMaterno());
            comando.setString(5, profesor.getCubiculo());
            comando.setString(6, profesor.getUsuario().getCorreo());
            new UsuarioDao().insertar(profesor.getUsuario(), conexion);
            comando.executeUpdate();
            conexion.commit();
            comando.close();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        administrador.cerrarConexion(); // cerramos la conexión
    
    }
    // metodo para buscar a un profesor
    public Profesor buscar(Profesor profesor){
        Profesor profesorBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Profesores_registrados WHERE id like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId()); // llenamos el parametro
            resultado = comando.executeQuery();
            if(resultado.next()){ // si hay coincidencia creamos un objeto profesor y lo llenamos con los datos obtenidos
                profesorBuscado = new Profesor(resultado.getInt("id"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getString("cubiculo"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                                          
            
                profesorBuscado.setGruposImpartidos(new GruposDao().obtenerGrupos(profesorBuscado));
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
        
        return profesorBuscado; // regresamos el profesor buscado
    }
    // metodo que hace exactamente lo mismo que el metodo buscar pero hace la busqueda por correo
    public Profesor buscar(Usuario usuario){
        Profesor profesorBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Profesores_registrados WHERE correo like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setString(1, usuario.getCorreo());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
                profesorBuscado = new Profesor(resultado.getInt("id"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getString("cubiculo"),
                                           usuario);
                
                profesorBuscado.setGruposImpartidos(new GruposDao().obtenerGrupos(profesorBuscado));
                                          
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
        
        return profesorBuscado;
    }
    // metodo para buscar a profesores por coincidencia 
    public ArrayList<Profesor> buscarCoincidencia(Profesor profesor){
    	ArrayList<Profesor> profesoresBuscados = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Profesores_registrados WHERE ";
            query += "nombre like '%' + ? + '%' OR apellido_paterno like '%' + ? + '%' OR apellido_materno like '%' + ? + '%' ";      
            query += "ORDER BY apellido_paterno ASC;";
            comando = conexion.prepareStatement(query);
            // llenamos los parametros necesarios           
            comando.setString(1, profesor.getNombre());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getNombre());
            
            
            resultado = comando.executeQuery();
            Profesor profesorIteracion;
            while(resultado.next()){ // llenamos la lista con los profesores encontrados
                
            	 profesorIteracion = new Profesor(resultado.getInt("id"),
                         resultado.getString("nombre"),
                         resultado.getString("apellido_paterno"),
                         resultado.getString("apellido_materno"),
                         resultado.getString("cubiculo"),
                         new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                        

            	profesoresBuscados.add(profesorIteracion);
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
        
        return profesoresBuscados; // retornamos los profesores encontrados
    }
    //metodo para actualizar un profesor
    public void actualizar(Profesor profesor, Profesor oldprofesor) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "UPDATE Profesores_registrados "
                    + "SET id = ?,"
                    + "nombre = ?,"
                    + "apellido_paterno = ?,"
                    + "apellido_materno = ?,"
                    + "cubiculo = ? "
                    + "WHERE id = ?";
         
            comando = conexion.prepareStatement(query);
            // llenamos los parametros necesarios del comando
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoPaterno());
            comando.setString(4, profesor.getApellidoMaterno());
            comando.setString(5, profesor.getCubiculo());
            comando.setInt(6, oldprofesor.getId());
            new UsuarioDao().actualizar(profesor.getUsuario(), oldprofesor.getUsuario(), conexion);
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
        
        administrador.cerrarConexion(); // cerramos la conexión
       }
    // metodo para eliminar a un profesor 
    public void eliminar(Profesor profesor) {
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Profesores_registrados WHERE id like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId()); // llenamos el paramtro del comando 
            comando.executeUpdate();
            new UsuarioDao().eliminar(profesor.getUsuario(), conexion); // eliminamos el usuario
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
        
        administrador.cerrarConexion(); // cerramos la conexión
    }
    
    
}
