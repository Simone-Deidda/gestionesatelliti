package it.prova.gestionesatelliti.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SatelliteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Satellite.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Satellite satellite = (Satellite) target;

		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataLancio().after(satellite.getDataRientro())) {
			errors.rejectValue("dataLancio", "dataLancio.after.dataRientro");
		}

		if (satellite.getDataLancio() == null && satellite.getDataRientro() != null) {
			errors.rejectValue("dataRientro", "dataRientro.exists.without.dataLancio");
		}

		if ((satellite.getStato() == StatoSatellite.IN_MOVIMENTO || satellite.getStato() == StatoSatellite.FISSO)
				&& satellite.getDataRientro() != null) {
			errors.rejectValue("stato", "dataRientro.exists.with.Stato.not.DISATTIVATO");
		}

		if (satellite.getStato() == StatoSatellite.DISATTIVATO && satellite.getDataRientro() == null) {
			errors.rejectValue("stato", "dataRientro.not.exists.with.Stato.DISATTIVATO");
		}
	}

	public boolean isValidForDelete(Object target) {
		Satellite satellite = (Satellite) target;
		if (satellite.getDataLancio() == null) {
			return true;
		}
		
		if (satellite.getDataRientro() != null && satellite.getStato() == StatoSatellite.DISATTIVATO) {
			return true;
		}
		
		return false;
	}
}
