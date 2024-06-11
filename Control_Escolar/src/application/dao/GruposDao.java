package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import application.modelo.Alumno;
import application.modelo.Grupo;
import application.modelo.Profesor;
import application.modelo.EnumBusquedas.BUSQUEDA;


public class GruposDao {
    
    private final Conexion administrador;
    
    
    public GruposDao(){
        administrador = new Conexion();
    }
    // metodo para insertar un grupo
    public void insertar(Grupo grupo){
    	Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        try{
            conexion.setAutoCommit(false);
            query = "INSERT INTO Grupos_registrados VALUES (?,?,?,?);"
            		+"CREATE TABLE G"+ grupo.getId() + "_"+ grupo.getGrupo() + "("
            		+"matricula VARCHAR(12),"
            		+ "proyectos DECIMAL(10,2) NOT NULL,"
            		+ "tareas DECIMAL(10,2) NOT NULL,"
            		+ "examenes DECIMAL (10,2) NOT NULL,"
            		+ "promedio DECIMAL (10,2) NOT NULL,"
            		+ "FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula) ON UPDATE CASCADE);";
            
            // llenamos los parametros del comando
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
                ex.printStackTrace();
            }
        }
        administrador.cerrarConexion(); // cerramos la conexión
    }
    // metodo para eliminar un grupo
    public void eliminar(Grupo grupo){
    	Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query;
        
        try{
            conexion.setAutoCommit(false);
            query = "DELETE FROM Grupos_registrados WHERE id = ? AND grupo = ?;"
            	  + "DROP TABLE G" + grupo.getId() + "_" + grupo.getGrupo()+";";
            comando = conexion.prepareStatement(query);
            // llenamos los parametros del comando
            comando.setInt(1, grupo.getId());
            comando.setInt(2, grupo.getGrupo());
            
            // damos de baja a todos los alumnos de ese grupo
            for(Alumno alumno : new AlumnoDao().obtenerAlumnos(grupo))
            	darBajaGrupo(grupo, alumno);
            
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
    // metodo para buscar un grupo
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
            // llenamos los parametros del comando
            comando.setInt(1, grupo.getId());
            comando.setInt(2, grupo.getGrupo());
            
            resultado = comando.executeQuery();
            if(resultado.next()){ // en caso de existir creamos un objeto grupo 
                
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
        return grupoBuscado; // regresamos ese objeto creado
    }
    // metodo que hace exactamente lo mismo que el metdo buscar solo que por parametros exogidos desde el enum
    public ArrayList<Grupo> buscar(Grupo grupo, BUSQUEDA busqueda) {
    	Connection conexion = administrador.establecerConexion();
    	ArrayList<Grupo> gruposBuscados = new ArrayList<>();
    	String query = "SELECT * FROM Grupos_registrados WHERE ";
    	
    	if(busqueda == BUSQUEDA.IDCURSO)
    		query += "id = ?;";
    		
    	else if(busqueda == BUSQUEDA.GRUPO) 
    		query += "grupo = ?;";
    		
    	else 
    		query += "id_profesor = ?;";    		
    	
    	PreparedStatement comando;
    	ResultSet resultado;
    	try {
	    	comando = conexion.prepareStatement(query);
	    	// llenamos los parametros
	    	comando.setInt(1, grupo.getId());
	    	resultado = comando.executeQuery();
	    	Grupo grupoIteracion;
	    	while(resultado.next()) { // llenamos la lista con todos los grupos encontrados
	    		grupoIteracion = new Grupo(resultado.getInt("id"), resultado.getInt("grupo"),
						 resultado.getInt("id_profesor"),
						 resultado.getInt("alumnos_registrados"));
	    		gruposBuscados.add(grupoIteracion);
	    	}
	    	comando.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	administrador.cerrarConexion();
    	
    	return gruposBuscados; // regresamos los grupos encontrados
    }
    // metodo para actualizar un gruo
    public void actualizar(Grupo grupo, Grupo oldGrupo) {
	    Connection conexion = administrador.establecerConexion();
    	String nombreAntiguo = "G" + oldGrupo.getId() + "_" + oldGrupo.getGrupo();
    	String nombreNuevo = "G" + grupo.getId() + "_" + grupo.getGrupo();
    	String query = "UPDATE Grupos_registrados "
    	        + "SET id = ?,"
    	        + "grupo = ?,"
    	        + "id_profesor = ? "
    	        + "WHERE id = ? AND grupo = ?;";

    	String query2 = "EXEC sp_rename ?, ?;";
    	PreparedStatement comando = null;
    	PreparedStatement comandoNombre = null;
    	try {
    	    conexion.setAutoCommit(false);
    	    
    	    // Realizar otras operaciones necesarias
    	    for (Alumno alumno : new AlumnoDao().obtenerAlumnos(oldGrupo)) {
    	    	actualizarGrupoAlumno(grupo, oldGrupo, alumno, conexion);
    	    }
    	    // Actualizar información del grupo
    	    comando = conexion.prepareStatement(query);
    	    comando.setInt(1, grupo.getId());
    	    comando.setInt(2, grupo.getGrupo());
    	    comando.setInt(3, grupo.getIdProfesor());
    	    comando.setInt(4, oldGrupo.getId());
    	    comando.setInt(5, oldGrupo.getGrupo());
    	    comando.executeUpdate();
    	    
    	    // Actualizar nombre de la tabla
    	    comandoNombre = conexion.prepareStatement(query2);
    	    comandoNombre.setString(1, nombreAntiguo);
    	    comandoNombre.setString(2, nombreNuevo);
    	    comandoNombre.executeUpdate();

    	    // Commit de la transacción
    	    conexion.commit();
    	    comando.close();
    	    comandoNombre.close();
    	} catch (SQLException e) {
    	    // Manejo de errores y rollback
    		 try {
 	            conexion.rollback();
 	        }catch (SQLException e1) {
 	            e1.printStackTrace();
 	        }
    	   
    	    e.printStackTrace();
    	} 
    	administrador.cerrarConexion(); // cerramos la conexión

    }
    // metodo que obtiene todos los grupos disponibles del sistema
    public ArrayList<Grupo> obtenerGruposDisponibles(){
    	ArrayList<Grupo> gruposDisponibles = new ArrayList<>();
    	Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_registrados;";
        try{
            comando = conexion.prepareStatement(query);
            resultado = comando.executeQuery();
            while(resultado.next()){  // llenamos la lista de grupos            
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
        return gruposDisponibles; // regresamos los grupos disponibles
    }
    // metodo que obtiene los grupos a los un alumno esta inscrito
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
            if(resultado.next()){ // llenamos la lista medainte la busqueda de los cursos
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
        return gruposInscritos; // regresamos los grupos inscritos del alumno
    }
    // metodo para obtener los grupos impartidos por un profesor
    public ArrayList<Grupo> obtenerGrupos(Profesor profesor){
        ArrayList<Grupo> gruposInscritos = new ArrayList<>();
        Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_registrados WHERE id_profesor = ?;";
        try{
            conexion.setAutoCommit(false);
            // llenamos el parametro del query
            comando = conexion.prepareStatement(query);
            comando.setInt(1, profesor.getId());
            
            resultado = comando.executeQuery();
            while(resultado.next()){ // llenamos la lista con los grupos 
                
                Grupo grupo = new Grupo(resultado.getInt("id"),
                                        resultado.getInt("grupo"),
                                        profesor.getId(),
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
        return gruposInscritos; // retornamos los grupos impartidos por ese profesor
    }
    // metodo para inscribir al alumno a un grupo
    public void inscribirGrupo(Grupo grupo, Alumno alumno){
    	Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query = "UPDATE Grupos_inscritos_alumnos "
                    +"SET id_curso" + (alumno.getNumeroGrupos() + 1) + " = ?,"
                    + "grupo"+ (alumno.getNumeroGrupos() + 1) +" = ? "
                    + "WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);  
            // llenamos los parametros necesarios
            comando = conexion.prepareStatement(query);
            comando.setInt(1, grupo.getId());
            comando.setInt(2, grupo.getGrupo());
            comando.setString(3, alumno.getMatricula());
            comando.executeUpdate();
            actualizarGruposInscritos(alumno, 1, conexion); // actualizamos el numero de  grupos inscritos
            actualizarNumeroAlumnos(grupo, 1, conexion); // actualizamos el numero de alumnos inscritos
            insertarGrupo(alumno, grupo, conexion); // lo insertamos en la tabla de alumnos del grupo
            conexion.commit();
            comando.close();
            // cambios los respectivos atributos de las clases alumno y grupo
            alumno.setNumeroGrupos(alumno.getNumeroGrupos() + 1);
            grupo.setAlumnosInscritos(grupo.getAlumnosInscritos() + 1);
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
    // metodo para dar de baja al alumno
    public void darBajaGrupo(Grupo grupo, Alumno alumno){
    	Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        ResultSet resultado;
        String query = "SELECT * FROM Grupos_inscritos_alumnos WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);
            comando = conexion.prepareStatement(query);
            // llenamos el parametro
            comando.setString(1, alumno.getMatricula());
            resultado = comando.executeQuery();
            int indice = 0;
            if(resultado.next()){
                
                for(int i = 1; i <= alumno.getNumeroGrupos(); i++){
                    int id = resultado.getInt("id_curso" + i);
                    int numGrupo = resultado.getInt("grupo" + i);
                    
                    if(id == grupo.getId() && numGrupo == grupo.getGrupo()){
                       indice = i;
                       System.out.println(i);
                       break;
                    }
                }
                // actualizamos todo lo necesario en la base de datos para que el grupo sea dado de baja
                if(indice > 0){
                	eliminarGrupoTabla(alumno, grupo, conexion); // lo eliminamos de la tabla
                    recorrerGrupos(alumno, indice, conexion); // quitamos al grupo inscrito
                    actualizarGruposInscritos(alumno, -1, conexion); // reducimos el numero de grupos inscritos
                    actualizarNumeroAlumnos(grupo, -1, conexion); // reducimos el numero de alumnos registrados en el grupo
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
        
        administrador.cerrarConexion(); // cerramos la conexión
    }
    // metodo para actualizar las calificaciones de un alumno en un grupo
    public void actualizarCalificaciones(Grupo grupo, Alumno alumno) {
    	Connection conexion = administrador.establecerConexion();
        PreparedStatement comando;
        String query = "UPDATE G" + grupo.getId() + "_" + grupo.getGrupo() + " "
                    +"SET proyectos = ?,"
                    +"tareas = ?, "
                    +"examenes = ?,"
                    +"promedio = ? "
                    +"WHERE matricula like ?;";
        try{
            conexion.setAutoCommit(false);  
            comando = conexion.prepareStatement(query);
            // llenamos los parametros
            for(int i = 0; i < 3; i ++)
            	comando.setDouble(i + 1, alumno.getPorcentaje(i));
            
            comando.setDouble(4, alumno.getPromedio());
            comando.setString(5, alumno.getMatricula());
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
    // metodo para insertar al alumno en un grupo
    private void insertarGrupo(Alumno alumno, Grupo grupo, Connection conexion) throws SQLException {
    	PreparedStatement comando;
        String query = "INSERT INTO G" + grupo.getId()+ "_" + grupo.getGrupo() + " VALUES(?,0,0,0,0);";
                    
        
        comando = conexion.prepareStatement(query);
        comando.setString(1, alumno.getMatricula());
        comando.executeUpdate();
        comando.close();
    	
    }
    // metodo para recorrer los grupos desde un indice
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
    // metodo para actualizar los grupos inscritos por el alumno
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
    // metodo para actualizar el numero de alumnos en un grupo
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
    // metodo para eliminar a un alumno de la tabla del grupo  
    private void eliminarGrupoTabla(Alumno alumno, Grupo grupo, Connection conexion) throws SQLException{
    	PreparedStatement comando;
        String query = "DELETE FROM G" + grupo.getId()+ "_" + grupo.getGrupo() + " WHERE matricula like ?;";           
        comando = conexion.prepareStatement(query);
        comando.setString(1, alumno.getMatricula());
        comando.executeUpdate();
        comando.close();
    	
    }
    // metodo para acutializar el grupo al alumno
    private void actualizarGrupoAlumno(Grupo grupo, Grupo oldGrupo, Alumno alumno, Connection conexion) throws SQLException{
    	String query = "SELECT * FROM Grupos_inscritos_alumnos WHERE matricula like ?;";
    	PreparedStatement comando;
    	ResultSet resultado;
    	//conexion.setAutoCommit(false);
    	comando = conexion.prepareStatement(query);
    	comando.setString(1, alumno.getMatricula());
    	resultado = comando.executeQuery();
    	if(resultado.next()) {
    		for(int i = 1; i <= 7; i++) {
    			if(resultado.getInt("id_curso"+i) == oldGrupo.getId() && resultado.getInt("grupo"+i) == oldGrupo.getGrupo()){
    				String query2 = "UPDATE Grupos_inscritos_alumnos "
    	    				 + "SET id_curso"+ i + " = ?,"
    	    				 + "grupo" + i + " = ? "
    	    				 + "WHERE matricula like ?;";
	    	    	PreparedStatement comando2;
	    	    	comando2 = conexion.prepareStatement(query2);
	    	    	comando2.setInt(1, grupo.getId());
	    	    	comando2.setInt(2, grupo.getGrupo());
	    	    	comando2.setString(3, alumno.getMatricula());
	    	    	comando2.executeUpdate();
	    	    	comando2.close();
	    			break;
    			}
    		}
    	}
    	comando.close();
    }
    
    
}
