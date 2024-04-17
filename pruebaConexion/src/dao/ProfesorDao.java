/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import modelo.Profesor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class ProfesorDao {
    
    private final Conexion administrador;

    public ProfesorDao(){
        administrador = new Conexion ();
    }
    
    public void insertar(Profesor profesor){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query= "INSERT INTO Profesores_registrados VALUES (?,?,?,?,?,?);";
                        
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoP());
            comando.setString(4, profesor.getApellidoM());
            comando.setInt(5, profesor.getCubiculo());
            comando.setString(6, profesor.getUsuario().getCorreo());
            comando.executeUpdate();
            new UsuarioDao().insertar(profesor.getUsuario(), conexion);
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
    
    public Profesor buscar(Profesor profesor){
        Profesor profesorbuscado=null;
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
                                           resultado.getInt("cubiculo"),
                                           new UsuarioDao().buscar(profesor.getUsuario(), conexion));
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
    
    public void actualizar(Profesor profesor, Profesor oldProfesor) {
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
                    + "WHERE id= ?;";
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoP());
            comando.setString(4, profesor.getApellidoM());
            comando.setInt(5, profesor.getCubiculo());
            comando.setString(6, profesor.getUsuario().getCorreo());
            comando.setInt(7, oldProfesor.getId());
            comando.setInt(8, profesor.getId());
            comando.setInt(9, oldProfesor.getId());
            comando.executeUpdate();
            new UsuarioDao().actualizar(profesor.getUsuario(), oldProfesor.getUsuario(), conexion);
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
    
    public void eliminar (Profesor profesor){
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Profesores_registrados WHERE matricula like ?;";
                  
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.setInt(2, profesor.getId());
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
