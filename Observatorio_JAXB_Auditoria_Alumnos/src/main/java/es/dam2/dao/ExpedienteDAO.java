package es.dam2.dao;

import es.dam2.expedientes.*;
import java.util.List;

public interface ExpedienteDAO {
	List<ExpedienteType> listarExpedientes();

	ExpedienteType buscarPorCodigo(String codigo);

	long contarInvestigaciones(int desde, int hasta);

	List<ExpedienteType> buscarPorTipoEvidencia(String tipo);

	boolean agregarInvestigacion(String codigo, InvestigacionType investigacion);

	long eliminarInvestigacionesAnteriores(int anio);

	boolean cerrarExpediente(String codigo);

	void guardar();
}
