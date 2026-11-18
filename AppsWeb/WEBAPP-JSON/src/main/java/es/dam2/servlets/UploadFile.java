package es.dam2.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import es.dam2.service.AlumnoService;
import es.dam2.utilidades.UtilidadesJson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AlumnoService alumnoService = new AlumnoService();
	
	private static final Path DIRECTORIO =
		       Path.of("C:\\Users\\jezu2\\Desktop\\archivos_servidor");


    /**
     * Default constructor. 
     */
    public UploadFile() {
        // TODO Auto-generated constructor stub
    }

	// doGet se ejeuctará cuando la REQUEST que llame al servlet use el método GET
    // Recibe un objeto HttpServletRequest con toda la info de la Request, y un objeto
    // HttpServletResponse para añadir datos a esa response y añadirla al Request
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Te sirvo en: ").append(request.getContextPath());
	}

	// doPost se ejeuctará cuando la REQUEST que llame al servlet use el método POST
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperar el archivo enviado. file es el name que pusimos en el  input del index.html
        Part parte = request.getPart("file");

        // Obtener únicamente el nombre del archivo
        String nombre = parte.getSubmittedFileName();
        // Sustituimos todos los \\ por /
        nombre = nombre.replace('\\', '/');
        // Y pillamos una substring con lo que hay a continuación del último /, que es básicamente el fichero
        nombre = nombre.substring(nombre.lastIndexOf('/') + 1);

        if (nombre.isBlank()) {
            response.sendError(400, "Nombre de archivo no válido");
            return;
        }

        
        Path destino = DIRECTORIO.resolve(nombre);

        // Generamos un flujo de bytes desde el fichero y usamos el método Files.copy 
        // para copiar todo del origen al destino, reemplazando. Con Java.io, habría que 
        // hacer un bucle de lectura del fichero
        try (InputStream entrada = parte.getInputStream()) {
            Files.copy(entrada, destino,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        
        alumnoService.insertarAlumno(UtilidadesJson.leeAlumno(destino));

        
        //AQUÍ LO QUE SEA CON EL FICHERO destino SUBIDO...

        // Generar respuesta HTML
        response.setContentType("text/html;charset=UTF-8");

        // Construimos un html
        String html = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <title>Descargar fichero</title>
                </head>
                <body>
                    <h1>Has subido el fichero con éxito</h1>

                    <form action="download" method="get">
                        <input type="hidden" name="file" value="%s">
                        <input type="submit"
                               value="Descargar fichero JSON">
                    </form>
                </body>
                </html>
                """.formatted(nombre); // Nombre del fichero a descargar

        response.getWriter().print(html);
	}

}
