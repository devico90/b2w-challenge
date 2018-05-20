package br.b2w.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.b2w.api.Planet;
import br.b2w.service.PlanetService;


@RestController
@RequestMapping("/planets")
public class PlanetController
{
	
	private PlanetService servicePlanet;
	
	public PlanetController(PlanetService servicePlanet)
	{
		this.servicePlanet = servicePlanet;
	}
	
	/*
	 * Método PUT para fazer inserção de planeta
	 * Verifica, pelo nome, se o planeta já existe na base de dados
	 * Caso o planeta não exista na base de dados, irá buscar na SWAPI
	 * Se encontrar, faz a inserção automática do planeta na base
	 * Não achei necessário criar o objeto "Film" para este projeto, visto que não existe especificação para tal e o uso atualmente seria inútil
	 * 
	 * URL: http://localhost:8080/planets
	 * BODY: {
	 * 			"id": "ID", (não é obrigatório)
	 * 		 	"name": "Nome do planeta",
	 * 			"climate": "Clima do planeta",
	 * 			"terrain": "Terreno do planeta",
	 * 			"films": [
	 * 				"Link do filme",
	 * 				"Link do filme"
	 * 			]
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
	@GetMapping
	public ResponseEntity<String> getAll()
	{
		return servicePlanet.getAll();
	}
	
	/*
	 * Método GET para realizar a busca por NOME ou ID
	 * URL's: http://localhost:8080/planets/find?name=Nome do planeta
	 * 		  http://localhost:8080/planets/find?id=ID do planeta 
	 */
	@GetMapping("/find")
	public ResponseEntity<String> find(@RequestParam(value="name",required=false) String name, @RequestParam(value="id",required=false) String id)
	{
		return servicePlanet.find(name, id);
	}
	
	/*
	 * Método DELETE para remover planetas por ID ou por NAME
	 * Caso o parâmetro seja NAME, remove TODOS os planetas com esse respectivo NAME
	 * Caso o parâmetro seja ID, remove apenas o planeta com o respectivo ID
	 * URL's: http://localhost:8080/remove?name=Nome do planeta
	 * 		  http://localhost:8080/remove?id=ID do planeta
	 */
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam(value="name",required=false) String name, @RequestParam(value="id",required=false) String id)
	{
		return servicePlanet.delete(name, id);
	}
	
}
