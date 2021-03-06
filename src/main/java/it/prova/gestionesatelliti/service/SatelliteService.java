package it.prova.gestionesatelliti.service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	public List<Satellite> listAllElements();

	public Satellite caricaSingoloElemento(Long id);

	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public List<Satellite> findByExample(Satellite example);

	public void rimuovi(Long idImpiegato);

	public List<Satellite> listAllSatellitiOlderThan2();

	public List<Satellite> listAllSatellitiNotActiveAndNotBack();

	public List<Satellite> listAllSatellitiOlderThan10();
}
