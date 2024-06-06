package application.modelo;

public class Cifrar {
	
	private static char[] diccionario = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q',
			 'r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
	
	
	
	private static int posicion(char caracter) {
		int resultado = 0;
		for(int i = 0; i < diccionario.length; i++) {
			if(caracter == diccionario[i]) {
				resultado = i;
				break;
			}
		}
		return resultado;
	}
    
	public static String cifrar(char[] cadena, int llave) {
	    String resultado = "";
	    int posicion;
	    for (char caracter : cadena) {
	        posicion = posicion(caracter) + llave;
	        if (posicion >= diccionario.length)
	            posicion = posicion % diccionario.length;
	        resultado += diccionario[posicion];
	    }
	    return resultado;
	}

	public static String descifrar(char[] cadena, int llave) {
	    String resultado = "";
	    int posicion;
	    for (char caracter : cadena) {
	        posicion = posicion(caracter) - llave;
	        if (posicion < 0)
	            posicion = diccionario.length + posicion;
	        resultado += diccionario[posicion];
	    }
	    return resultado;
	}

}
