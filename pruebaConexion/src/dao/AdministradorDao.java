/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Administrador;

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
            query = "INSERT INTO Administradores VALUES (?,?,?,?,?,?);";
            
            comando = conexion.prepareStatement(query);
            comando.setInt(1, admin.getId_administrador());
            comando.setString(2, admin.getNombre());
            comando.setString(3, admin.getApellidoPaterno());
            comando.setString(4, admin.getApellidoMaterno());
            comando.setInt(5, admin.getEdad());
            comando.setString(6, admin.getUsuario().getCorreo());
            comando.executeUpdate();
            new UsuarioDao().insertar(admin.getUsuario(), conexion);
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
    
}
