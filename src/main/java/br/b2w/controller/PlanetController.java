package br.b2w.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.b2w.api.Planet;
import br.b2w.repository.PlanetRepository;

@RestController
@RequestMapping("/planets")
public class PlanetController
{
	
	@Autowired
	private PlanetRepository planetRepository;
	
	public PlanetController(PlanetRepository planetRepository)
	{
		this.planetRepository = planetRepository;
	}
	
	@PutMapping
	public void insert(@RequestBody Planet planet)
	{
		this.planetRepository.insert(planet);
	}
	
	@GetMapping("/all")
	public List<Planet> getAll()
	{
		List<Planet> planets = planetRepository.findAll();
		
		return planets;
	}
	
	@GetMapping("/find/name/{name}")
	public List<Planet> findByName(@PathVariable("name") String name)
	{
		List<Planet> planets = this.planetRepository.findByName(name);
		
		return planets;
	}
	
	@GetMapping("/find/id/{id}")
	public Planet findById(@PathVariable("id") String id)
	{
		Planet planet = this.planetRepository.findById(id);
		
		return planet;
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") String id)
	{
		this.planetRepository.delete(id);
	}
	
}
