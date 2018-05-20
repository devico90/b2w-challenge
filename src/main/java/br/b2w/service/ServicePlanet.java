package br.b2w.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.b2w.api.Planet;
import br.b2w.api.Planets;
import br.b2w.repository.PlanetRepository;

@Service
public class ServicePlanet implements IServicePlanet
{

	@Autowired
	private PlanetRepository planetRepository;
	
	private RestTemplate restTemplate;
	
	final String URL_PLANETS = "https://swapi.co/planets";
	
	@Bean
	public RestTemplate restTemplate()
	{
	    return new RestTemplate();
	}
	
	@Override
	public List<Planets> getAllPlanets()
	{
		ResponseEntity<Planets[]> response = restTemplate.getForEntity(URL_PLANETS, Planets[].class);
		
		return Arrays.asList(response.getBody());
	}
	
	@Override
	public ResponseEntity<Planet> insert(Planet planet) 
	{
		Planet insertPlanet = new Planet();
		
		String idPlanet = planet.getId();
		if (idPlanet != null)
		{
			if (planetRepository.exists(planet.getId()))
			{
				throw new PlanetAlreadyExistsException();
			}
			insertPlanet.setId(idPlanet);
		}

		insertPlanet.setName(planet.getName().toUpperCase());
		insertPlanet.setClimate(planet.getClimate().toUpperCase());
		insertPlanet.setTerrain(planet.getTerrain().toUpperCase());
		insertPlanet.setFilms(planet.getFilms());
		
		planetRepository.insert(insertPlanet);
		
		return ResponseEntity.ok(insertPlanet);
		
	}

	@Override
	public ResponseEntity<String> getAll() 
	{
		StringBuilder listPlanetStr = new StringBuilder();
		List<Planet> listPlanet = null;
		
		listPlanet = planetRepository.findAll();
		
		if (listPlanet != null)
		{
			listPlanetStr.append("[");
			for (Planet planet : listPlanet)
			{
				listPlanetStr.append(planet);
				listPlanetStr.append(",");
			}
			listPlanetStr.deleteCharAt(listPlanetStr.lastIndexOf(","));
			listPlanetStr.append("]");
		}
		
		return ResponseEntity.ok(listPlanetStr.toString());
	}

	@Override
	public String find(String name, String id) 
	{
		
		ResponseEntity<?> re = null;
		
		if (name != null && !name.equals("")) //caso possua o parâmetro NOME, realiza a busca por nome
		{
			List<Planet> planets = planetRepository.findByName(name.toUpperCase());
			if (planets.size() == 0)
			{
				throw new PlanetNotFoundException(1);
			}
			re = new ResponseEntity<List<Planet>>(planets, HttpStatus.OK);
		}
		else
		{
			Planet planet = null;
			if (id != null && !id.equals("")) //caso o parâmetro NOME não esteja preenchido, mas tenha o parâmetro ID, faz a busca por ID
			{
				planet = planetRepository.findOne(id);
				re = new ResponseEntity<Planet>(planet, HttpStatus.OK);
			}
			
			if (planet == null)
			{
				throw new PlanetNotFoundException(0);
			}
		}
		
		return re.getBody().toString();
	}
	
	@Override
	public ResponseEntity<String> delete(String name, String id) 
	{
		ResponseEntity<String> re = null;
		if (name != null && !name.equals("")) //caso possua o parâmetro NOME, realiza a busca por nome
		{
			List<Planet> planets = planetRepository.findByName(name.toUpperCase());
			if (planets.size() == 0)
			{
				throw new PlanetNotFoundException(1);
			}
			for (Planet planet : planets)
			{
				planetRepository.delete(planet);
			}
			re = new ResponseEntity<String>("{\"Sucesso:\":{\"Mensagem:\":\"Planeta(s) removido(s)\",\"Quantidade\":\""+planets.size()+"\"}}", HttpStatus.OK);
		}
		else
		{
			if (id != null && !id.equals("")) //caso o parâmetro NOME não esteja preenchido, mas tenha o parâmetro ID, faz a busca por ID
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
				
				planetRepository.delete(id);
				
				re = new ResponseEntity<String>("{\"Sucesso:\":\"Planeta removido\"}", HttpStatus.OK);
			}
		}
		
		return re;
	}	
	
}
