package br.b2w.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.b2w.api.Planet;
import br.b2w.service.ServicePlanet;


@RestController
@RequestMapping("/planets")
public class PlanetController
{
	
	private ServicePlanet servicePlanet;
	
	public PlanetController(ServicePlanet servicePlanet)
	{
		this.servicePlanet = servicePlanet;
	}
	
	@PutMapping
	public ResponseEntity<Planet> insert(@RequestBody Planet planet)
	{
		return servicePlanet.insert(planet); 
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Planet>> getAll()
	{
		return servicePlanet.getAll();
	}
	
	@GetMapping("/find/name/{name}")
	public ResponseEntity<List<Planet>> findByName(@PathVariable("name") String name)
	{
		return servicePlanet.findByNameIgnoreCase(name);
	}
	
	@GetMapping("/find/id/{id}")
	public ResponseEntity<Planet> findById(@PathVariable("id") String id)
	{
		return servicePlanet.findById(id);
	}
	
//	@DeleteMapping("/delete/{id}")
//	public void delete(@PathVariable("id") String id)
//	{
//		this.planetRepository.delete(id);
//	}
	
}
