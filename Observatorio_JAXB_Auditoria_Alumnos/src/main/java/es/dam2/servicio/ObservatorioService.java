package es.dam2.servicio;

import es.dam2.dao.ExpedienteDAO;
import es.dam2.expedientes.*;
import java.util.*;

public class ObservatorioService {
	private final ExpedienteDAO dao;

	public ObservatorioService(ExpedienteDAO dao) {
		this.dao = dao;
	}

	public void imprimirResumen() {
		for (ExpedienteType e : dao.listarExpedientes()) {
			System.out.printf("%s | %s | %s | investigaciones: %d%n", e.getCodigo(), e.getTitulo(),
					e.getUbicacion().getValue(), dao.listarExpedientes().size());
		}
	}

	public Map<String, Integer> actividadPorResponsable() {
		Map<String, Integer> conteo = new TreeMap<>();
		for (ExpedienteType e : dao.listarExpedientes()) {
			for (InvestigacionType i : e.getInvestigaciones().getInvestigacion()) {
				
//				conteo.put(i.getResponsable(), 1);
				if(!conteo.containsKey(i.getResponsable())) {
					conteo.put(i.getResponsable(), 1);
				} else {
					conteo.put(i.getResponsable(), conteo.get(i.getResponsable()) + 1);
				}
			}
		}
		return conteo;
	}

	public List<String> expedientesConInvestigacionesRecientes(int desde) {
		List<String> codigos = new ArrayList<>();
		for (ExpedienteType e : dao.listarExpedientes()) {
			for (InvestigacionType i : e.getInvestigaciones().getInvestigacion()) {
				if (i.getAño().getYear() >= desde)
					codigos.add(e.getCodigo());
			}
		}
		return codigos;
	}
}
