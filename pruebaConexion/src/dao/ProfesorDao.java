/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import modelo.Profesor;
import java.sql.Connection;

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
            query= "INSERT INTO Administradores VALUES (?,?,?,?,?,?);";
                   +"INSERT INTO Cursos_impartidos_profesor (id_curso) VALUES(?);";
                   
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            comando.setString(2, profesor.getNombre());
            comando.setString(3, profesor.getApellidoP());
            comando.setString(4, profesor.getApellidoM());
            comando.setInt(5, profesor.getCubiculo());
            comando.setString(6, profesor.getUsuario().getCorreo());
            
            
            
        }catch
    }
}
