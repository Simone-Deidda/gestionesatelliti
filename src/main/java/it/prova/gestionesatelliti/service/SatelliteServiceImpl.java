package it.prova.gestionesatelliti.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {
	@Autowired
	private SatelliteRepository satelliteRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllElements() {
		return (List<Satellite>) satelliteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return satelliteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Satellite satelliteInstance) {
		satelliteRepository.save(satelliteInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Satellite satelliteInstance) {
		satelliteRepository.save(satelliteInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> findByExample(Satellite example) {
		Specification<Satellite> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getDenominazione()))
				predicates.add(cb.like(cb.upper(root.get("denominazione")),
						"%" + example.getDenominazione().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCodice()))
				predicates.add(cb.like(cb.upper(root.get("codice")), "%" + example.getCodice().toUpperCase() + "%"));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDataLancio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLancio"), example.getDataLancio()));

			if (example.getDataRientro() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataRientro"), example.getDataRientro()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		return satelliteRepository.findAll(specificationCriteria);
	}

	@Override
	@Transactional
	public void rimuovi(Long idImpiegato) {
		satelliteRepository.deleteById(idImpiegato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllSatellitiOlderThan2() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -2);
		
		return satelliteRepository.findAllByDataLancioBeforeAndStatoNot(calendar.getTime(), StatoSatellite.DISATTIVATO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllSatellitiNotActiveAndNotBack() {
		return satelliteRepository.findAllByStatoAndDataRientroIsNull(StatoSatellite.DISATTIVATO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllSatellitiOlderThan10() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -10);
		
		return satelliteRepository.findAllByDataLancioBeforeAndStato(calendar.getTime(), StatoSatellite.FISSO);
	}

}
