package SourceControl.Services;

import SourceControl.Vuelo.*;


import java.util.Date;
import java.util.List;


public interface BusquedaService {
	List<Vuelo> buscarVuelos(String destino, Date fecha, String clase);
	
	List<Vuelo> filtrarVuelos(String tipoClase);
}
