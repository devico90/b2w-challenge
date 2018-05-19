package br.b2w.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.b2w.api.Planet;
import br.b2w.repository.PlanetRepository;
import br.b2w.utils.Utils;

@Service
public class ServicePlanet implements IServicePlanet
{

	@Autowired
	private PlanetRepository planetRepository;

	@Override
	public ResponseEntity<Planet> insert(Planet planet) 
	{
		Planet insertPlanet = new Planet();
		
		String idPlanet = planet.getId();
		if (idPlanet != null)
		{
			if (Utils.exists(planetRepository, idPlanet))
			{
				throw new PlanetAlreadyExistsException();
			}
			insertPlanet.setId(idPlanet);
		}

		insertPlanet.setName(planet.getName().toUpperCase());
		insertPlanet.setClimate(planet.getClimate().toUpperCase());
		insertPlanet.setTerrain(planet.getTerrain().toUpperCase());
		
		planetRepository.insert(insertPlanet);
		
		return ResponseEntity.ok(insertPlanet);
		
	}

	@Override
	public ResponseEntity<List<Planet>> getAll() {
		List<Planet> planets = null;
		
		planets = planetRepository.findAll();
		
		if (planets != null)
		{
			return ResponseEntity.ok(planets);
		}
		
		return null;
	}

	@Override
	public ResponseEntity<List<Planet>> findByNameIgnoreCase(String name) {
		List<Planet> planets = planetRepository.findByNameIgnoreCase(name.toUpperCase());
		
		if (planets.size() == 0)
		{
			throw new PlanetNotFoundException(1);
		}
		
		return ResponseEntity.ok(planets);
	}
	
	@Override
	public ResponseEntity<Planet> findById(String id)
	{
		Planet planet = null;
		if (id != null && !id.equals(""))
		{
			planet = planetRepository.findOne(id);
		}
		
		if (planet == null)
		{
			throw new PlanetNotFoundException(0);
		}
		
		return ResponseEntity.ok(planet);
	}

	@Override
	public ResponseEntity<String> deleteById(String id) 
	{
		Planet planet = null;
		if (id != null && !id.equals(""))
		{
			planet = planetRepository.findOne(id);
		}
		
		if (planet == null)
		{
			throw new PlanetNotFoundException(0);
		}
		
		planetRepository.delete(planet);
		
		return ResponseEntity.ok("Planeta excluído com sucesso");
	}

//	@Override
//	public ResponseEntity<String> deleteById(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<String> deleteByName(String name) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
	
}
