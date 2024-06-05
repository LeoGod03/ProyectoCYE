package application.modelo;


public class Grupo {
	
	private int id;
	private int grupo;
	private int idProfesor;
	private int alumnosInscritos;
	
	
	public Grupo(int id, int grupo, int idProfesor, int alumnosInscritos) {
		setId(id);
		setGrupo(grupo);
		setIdProfesor(idProfesor);
		setAlumnosInscritos(alumnosInscritos);
	}
	
	public Grupo(int id, int grupo) {
		setId(id);
		setGrupo(grupo);
		idProfesor = -1;
		alumnosInscritos = 0;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	public int getIdProfesor() {
		return idProfesor;
	}
	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}
	public int getAlumnosInscritos() {
		return alumnosInscritos;
	}
	public void setAlumnosInscritos(int alumnosInscritos) {
		this.alumnosInscritos = alumnosInscritos;
	}
	@Override
	
	public String toString() {
		String cadena = "Id: " + id + " Grupo: " + grupo + " Id profesor: " + idProfesor 
				+ " #Alumnos: " + alumnosInscritos;
		
		return cadena;
	}
	 @Override
	    public boolean equals(Object obj) {
	    	boolean resultado = false;
	    	if(obj instanceof Grupo) {
	    		Grupo grupo = (Grupo) obj;
	    		resultado = (grupo.getGrupo() == this.grupo && grupo.getId() == id);
	    				
	    	}
	    	
	    	return resultado;
	    }
}
