/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.modelo.Administrador;

/**
 *
 * @author Acer Aspire
 */
public class AdministradorDao {
    private final Conexion administrador;
    
    public AdministradorDao(){
        administrador = new Conexion();
    }
    
    public void insertar(Administrador admin) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Administradores VALUES (?,?,?,?,?);";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, admin.getId_administrador());
            comando.setString(2, admin.getNombre());
            comando.setString(3, admin.getApellidoPaterno());
            comando.setString(4, admin.getApellidoMaterno());
            comando.setString(5, admin.getUsuario().getCorreo());
            new UsuarioDao().insertar(admin.getUsuario(), conexion);
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
    
    public Administrador buscar(Administrador admi){
        Administrador adminBuscado = null;
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query;
         try{
            conexion.setAutoCommit(false);
            query = "SELECT * FROM Administradores WHERE id like ?;";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, admi.getId_administrador());
            
            resultado = comando.executeQuery();
            if(resultado.next()){
               adminBuscado = new Administrador(resultado.getInt("id"),
                                           resultado.getString("nombre"),
                                           resultado.getString("apellido_paterno"),
                                           resultado.getString("apellido_materno"),
                                           new UsuarioDao().buscar(admi.getUsuario(), conexion));
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
        
        return adminBuscado;
    }
    
    
    public void actualizar(Administrador admin, Administrador oldadmin) {
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "UPDATE Administradores "
                    + "SET id = ?,"
                    + "nombre = ?,"
                    + "apellido_paterno = ?,"
                    + "apellido_materno = ?,"
                    + "correo = ? "
                    + "WHERE id = ?";
              

            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, admin.getId_administrador());
            comando.setString(2, admin.getNombre());
            comando.setString(3, admin.getApellidoPaterno());
            comando.setString(4, admin.getApellidoMaterno());
            comando.setString(5, admin.getUsuario().getCorreo());
            comando.setInt(6, oldadmin.getId_administrador());

            comando.setInt(7, admin.getId_administrador());
            comando.setInt(8, oldadmin.getId_administrador());

            comando.executeUpdate();
            new UsuarioDao().actualizar(admin.getUsuario(), oldadmin.getUsuario(), conexion);
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
    

    public void eliminar(Administrador admin) {

        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Administradores WHERE id like ?;";
                  
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, admin.getId_administrador());
            comando.executeUpdate();
            new UsuarioDao().eliminar(admin.getUsuario(), conexion);
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
    
    
         
