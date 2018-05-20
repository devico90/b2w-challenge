package br.b2w.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.b2w.api.Planet;
import br.b2w.api.PlanetSWAPI;
import br.b2w.api.Planets;
import br.b2w.repository.PlanetRepository;

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
			if ( planetRepository.exists(planet.getId()) )
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
	public ResponseEntity<String> find(String name, String id) 
	{
		StringBuilder listPlanetStr = new StringBuilder();
		
		
		if (name != null && !name.equals("")) //caso possua o parâmetro NOME, realiza a busca por nome
		{
			List<Planet> planets = planetRepository.findByName(name.toUpperCase()); //procura na base de dados local se já existe o planeta cadastrado
			if (planets.size() == 0)
			{
				Planet planet = getPlanetSWAPIByName(name.toUpperCase()); //caso negativo, busca na SWAPI
				if (planet != null) 
				{
					planetRepository.insert(planet); //se encontrar na SWAPI, insere o planeta no banco
					planets.add(planet);
				}
				else
				{
					throw new PlanetNotFoundException(1); //se não encontrar, informa que o planeta não foi encontrado
				}
			}
			listPlanetStr.append("[");
			for (Planet planet : planets)
			{
				listPlanetStr.append(planet);
				listPlanetStr.append(",");
			}
			listPlanetStr.deleteCharAt(listPlanetStr.lastIndexOf(","));
			listPlanetStr.append("]");
		}
		else
		{
			Planet planet = null;
			if (id != null && !id.equals("")) //caso o parâmetro NOME não esteja preenchido, mas tenha o parâmetro ID, faz a busca por ID
			{
				planet = planetRepository.findOne(id);
				listPlanetStr.append(planet);
			}
			
			if (planet == null)
			{
				throw new PlanetNotFoundException(0);
			}
		}
		
		return ResponseEntity.ok(listPlanetStr.toString());
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
	
	public int getCountFilmsSWAPIByName(String name)
	{
		String URL_PLANETS = "https://swapi.co/api/planets/?search="+name;
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "HTTPie/1.0.0-dev");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Planets> planets = restTemplate.exchange(URL_PLANETS, HttpMethod.GET, entity, Planets.class);
        
        List<PlanetSWAPI> listPlanetSWAPI = planets.getBody().getResults();
        
        int countFilms = 0;
        if (listPlanetSWAPI.size() > 0)
        {
        	countFilms = listPlanetSWAPI.get(0).getFilms().size();
        }
        
		return countFilms;
	}
	
	public Planet getPlanetSWAPIByName(String name)
	{
		String URL_PLANETS = "https://swapi.co/api/planets/?search="+name;
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "HTTPie/1.0.0-dev");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Planets> planets = restTemplate.exchange(URL_PLANETS, HttpMethod.GET, entity, Planets.class);
        
        List<PlanetSWAPI> listPlanetSWAPI = planets.getBody().getResults();
        
        Planet planet = null;
        if (listPlanetSWAPI.size() > 0)
        {
        	planet = new Planet();
        	PlanetSWAPI planetSWAPI = listPlanetSWAPI.get(0);
        	planet.setName(planetSWAPI.getName());
        	planet.setClimate(planetSWAPI.getClimate());
        	planet.setTerrain(planetSWAPI.getTerrain());
        	planet.setFilms(planetSWAPI.getFilms());
        }
        
		return planet;
	}
	
}
