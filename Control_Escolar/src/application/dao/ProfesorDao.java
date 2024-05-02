/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.modelo.EnumBusquedas;
import application.modelo.Profesor;
import application.modelo.Usuario;

/**
 *
 * @author Acer Aspire
 */
public class ProfesorDao {
     private final Conexion administrador;
    
    public ProfesorDao(){
        administrador = new Conexion();
    }
    
    public void insertar(Profesor profesor) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Profesores_registrados VALUES (?,?,?,?,?,?,?);";
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoPaterno());
            comando.setString(4, profesor.getApellidoMaterno());
            comando.setInt(5,profesor.getEdad());
            comando.setString(6, profesor.getCubiculo());
            comando.setString(7, profesor.getUsuario().getCorreo());
            comando.executeUpdate();
            new UsuarioDao().insertar(profesor.getUsuario(), conexion);
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
            comando.setInt(1, profesor.getId());
            resultado = comando.executeQuery();
            if(resultado.next()){
                profesorBuscado = new Profesor(resultado.getInt("id"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getInt("edad"),
                                           resultado.getString("cubiculo"),
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
        
        return profesorBuscado;
    }
    
    public ArrayList<Profesor> buscar(Profesor profesor, EnumBusquedas.BUSQUEDA busqueda){
        ArrayList<Profesor> profesorBuscados = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Profesores_registrados WHERE ";
             if(busqueda == EnumBusquedas.BUSQUEDA.IDPROFESOR)
                query += "id = ?;";
            else
                query += "nombre like '%' + ? + '%' OR apellido_paterno like '%' + ? + '%' OR apellido_materno like '%' + ? + '%';";
            
             comando = conexion.prepareStatement(query);
             
             if(busqueda == EnumBusquedas.BUSQUEDA.IDPROFESOR) 
                comando.setInt(1, profesor.getId());
             else{
                 comando.setString(1, profesor.getNombre());
                 comando.setString(2, profesor.getNombre());
                 comando.setString(3, profesor.getNombre());
             }
            
           
            resultado = comando.executeQuery();
            while(resultado.next()){
                Profesor profesorIteracion = new Profesor(resultado.getInt("id"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           resultado.getInt("edad"),
                                           resultado.getString("cubiculo"),
                                           new UsuarioDao().buscar(new Usuario(resultado.getString("correo")), conexion));
                
                profesorBuscados.add(profesorIteracion);
                                          
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
        
        return profesorBuscados;
        
    }
    
    
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
                    + "cubiculo = ?,"
                    + "edad = ?,"
                    + "correo = ? "
                    + "WHERE id = ?";
         
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoPaterno());
            comando.setString(4, profesor.getApellidoMaterno());
            comando.setString(5, profesor.getCubiculo());
            comando.setInt(6, profesor.getEdad());
            comando.setString(6, profesor.getUsuario().getCorreo());
            comando.setInt(7, oldprofesor.getId());
            comando.executeUpdate();
            new UsuarioDao().actualizar(profesor.getUsuario(), oldprofesor.getUsuario(), conexion);
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
     
    
    public void eliminar(Profesor profesor) {
         Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Profesores_registrados WHERE id like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.executeUpdate();
            new UsuarioDao().eliminar(profesor.getUsuario(), conexion);
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
