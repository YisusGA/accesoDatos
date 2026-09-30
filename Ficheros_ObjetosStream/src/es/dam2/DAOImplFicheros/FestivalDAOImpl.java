package es.dam2.DAOImplFicheros;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

import es.dam2.accesoFicheros.ObjectOutputStreamNoHeader;
import es.dam2.dao.FestivalDAO;
import es.dam2.entity.Concierto;
import es.dam2.entity.Festival;

public class FestivalDAOImpl implements FestivalDAO {

	private Path ficheroFestivales;

	public FestivalDAOImpl(Path ficheroFestivales) {
		this.ficheroFestivales = ficheroFestivales;
	}

	@Override
	public void insert(Festival festival) {

		try (ObjectOutputStream oos = (ficheroFestivales.toFile().exists())
				? new ObjectOutputStreamNoHeader(new FileOutputStream(ficheroFestivales.toFile()))
				: new ObjectOutputStream(new FileOutputStream(ficheroFestivales.toFile()))) {
			oos.writeObject(festival);
		} catch (FileNotFoundException e) {
			// Aquí normalmente se sacaría una entrada a un log, y se comentaría el
			// e.printStackTrace() en el despliegue
			e.printStackTrace();
		} catch (IOException e) {
			// Aquí normalmente se sacaría una entrada a un log
			e.printStackTrace();
		}

	}

	@Override
	public Concierto findById(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String nombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Festival festival) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Concierto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
