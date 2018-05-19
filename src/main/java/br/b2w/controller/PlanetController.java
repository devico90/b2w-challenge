package br.b2w.controller;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	/*
	 * Método PUT para fazer inserção de planeta
	 * URL: http://localhost:8080/planets
	 * BODY: {
	 * 		 	"name": "Nome do planeta"
	 * 			"climate": "Clima do planeta"
	 * 			"terrain": "Terreno do planeta"
	 * 		 }	
	 */
	
	@PutMapping
	public ResponseEntity<Planet> insert(@RequestBody Planet planet)
	{
		return servicePlanet.insert(planet); 
	}
	
	/*
	 * Método GET para realizar a listagem de todos os planetas
	 * URL: http://localhost:8080/planets/all
	 * TODO: paginação
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Planet>> getAll()
	{
		return servicePlanet.getAll();
	}
	
	/*
	 * Método GET para realizar a busca por NOME ou ID
	 * URL's: http://localhost:8080/planets/find?name=Nome do planeta
	 * 		  http://localhost:8080/planets/find?id=ID do planeta 
	 */
	@GetMapping("/find")
	public ResponseEntity<?> find(@RequestParam(value="name",required=false) String name, @RequestParam(value="id",required=false) String id)
	{
		return servicePlanet.find(name, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") String id)
	{
		servicePlanet.deleteById(id);
	}
	
}
