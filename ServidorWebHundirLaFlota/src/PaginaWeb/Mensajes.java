/**
 * 
 */
package PaginaWeb;

/**
 *
 */
public class Mensajes {
	public static final String lineaInicial_OK = "HTTP/1.1 200 OK";
	public static final String lineaInicial_NotFound = "HTTP/1.1 404 Not Found";
	public static final String primeraCabecera = "Content-Type:text/html;charset=UTF-8";
	public static final String pgError = "<!DOCTYPE html>\n"
			+ "<html>\n"
			+ "<body>\n"
			+ "<h1>Error 404: Pagina no encontrada</h1>\n"
			+ "<p>Parece que has intentado petar el servidor, pero no podrás.</p>\n"
			+ "</body>\n"
			+ "</html>\n";
	
	
}
