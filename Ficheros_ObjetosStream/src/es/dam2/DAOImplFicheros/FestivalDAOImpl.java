package es.dam2.DAOImplFicheros;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.dam2.accesoFicheros.ObjectOutputStreamNoHeader;
import es.dam2.dao.FestivalDAO;
import es.dam2.entity.Festival;

public class FestivalDAOImpl implements FestivalDAO {

	// En esta clase, hacemos las implementaciones de los métodos abstractos de la
	// interface FestivalDAO. Con esto, luego podemos conseguir hacer inyección de
	// dependencias: desde el Main, llamamos a los métodos de la interface, y la
	// cabecera de esos métodos siempre va a ser la misma. Pero luego, podemos hacer
	// varias clases que hagan implementaciones diferentes de esos métodos, y con
	// sólo cambiar 1 línea de código, podemos hacer que, cada vez que se llamen a
	// métodos de la interface, se inyecten las implementaciones de una u otra clase

	private Path ficheroFestivales; // Esto se inyectará por constructor cuando se cree una instancia de la clase
	// Obtención del logger utilizando la API nativa de Log4j 2
    private static final Logger logger = LogManager.getLogger(FestivalDAOImpl.class);

	public FestivalDAOImpl(Path ficheroFestivales) {
		this.ficheroFestivales = ficheroFestivales;
	}

	@Override
	public void insert(Festival festival) {

		// try-catch con recursos que instancia la implementación base de
		// ObjectOutputStream o la custom nuestra, que no escribe cabecera, en función
		// de si el archivo no existe o existe, respectivamente

		// Super importante poner el true como append del constructor de
		// FileOutputStream, porque si no lo ponemos, por defecto será false. Si está
		// puesto en true, escribirá los nuevos bytes al final del fichero, pero si está
		// puesto en false (u omitido), escribirá los nuevos bytes al principio del
		// archivo, y entonces la cabecera no será lo primero que se lea al leer el
		// fichero y saldrá un StreamCorruptedException
		try (ObjectOutputStream oos = (ficheroFestivales.toFile().exists())
				? new ObjectOutputStreamNoHeader(new FileOutputStream(ficheroFestivales.toFile(), true))
				: new ObjectOutputStream(new FileOutputStream(ficheroFestivales.toFile(), true))) {

			if (!existeFestival(festival)) {
				oos.writeObject(festival);
			}
		} catch (FileNotFoundException e) {
			// Aquí normalmente se sacaría una entrada a un log, y se comentaría el
			// e.printStackTrace() en el despliegue
			logger.error("No se encontró el fichero");
			e.printStackTrace();
		} catch (IOException e) {
			// Aquí normalmente se sacaría una entrada a un log
			e.printStackTrace();
		}

	}


	@Override
	public void delete(String nombre) {

		Path aux = Path.of("aux.dat");
		boolean fin = false;
		Festival f = null;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroFestivales.toFile()));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(aux.toFile()))) {

			while (!fin) {
				try {
					f = (Festival) ois.readObject();
					if (!f.getNombre().equalsIgnoreCase(nombre)) {
						oos.writeObject(f);
					}
				} catch (EOFException e) {
					fin = true;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Es importante que esto vaya fuera del try-catch anterior, pues si no, el ois
		// y oos tendrán abiertos los ficheros y no se podrán hacer operaciones con
		// ellos
		try {
			// Método estático de Files para mover o renombrar un archivo a un archivo de
			// destino. Los parámetros son un Path con el archivo origen, un Path con el
			// archivo destino y el tipo de movimiento que se quiere hacer
			Files.move(aux, ficheroFestivales, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Festival festival) {

		Path aux = Path.of("aux.dat");
		boolean fin = false;
		Festival f = null;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroFestivales.toFile()));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(aux.toFile()))) {

			while (!fin) {
				try {
					f = (Festival) ois.readObject();
					if (!f.equals(festival)) {
						oos.writeObject(f);
					} else {
						oos.writeObject(festival);
					}
				} catch (EOFException e) {
					fin = true;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Files.move(aux, ficheroFestivales, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds a Festival, given its name
	 * 
	 * @param nombre the name of the festival
	 * @return an Optional that may contain a Festival found with the name provided,
	 *         or null if no Festival was found
	 */
	@Override
	public Optional<Festival> findById(String nombre) throws NoSuchElementException {

		boolean fin = false;
		Festival f = null;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroFestivales.toFile()))) {

			while (!fin) {
				try {
					Festival aux = (Festival) ois.readObject();
					if (aux.getNombre().equalsIgnoreCase(nombre)) {
						fin = true;
						f = aux;
					}
				} catch (EOFException e) {
					fin = true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(f); // Se convierte el Festival, que podría ser null, a un Optional nullable
	}

	@Override
	public List<Festival> findAll() {

		List<Festival> festivales = new ArrayList<>();
		boolean fin = false;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroFestivales.toFile()))) {

			while (!fin) {
				try {
					festivales.add((Festival) ois.readObject());
				} catch (EOFException e) {
					fin = true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return festivales;
	}

	/**
	 * Método para comprobar si un objeto Festival ya existe en el fichero de
	 * objetos Festival, según su método equals
	 * 
	 * @param festival el objeto Festival a comprobar
	 * @return true si existe, false si no existe
	 */
	public boolean existeFestival(Festival festival) {
		boolean existe = false;
		if (ficheroFestivales.toFile().exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroFestivales.toFile()))) {
				boolean fin = false;
				while (!fin && !existe) {
					try {
						if (((Festival) ois.readObject()).equals(festival)) {
							existe = true;
						}
					} catch (EOFException e) {
						fin = true;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return existe;
	}

}
