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
public class PlanetService implements IPlanetService
{

	@Autowired
	private PlanetRepository planetRepository;
	
	@Override
	public ResponseEntity<Planet> insert(Planet planet) 
	{
		Planet newPlanet = null;
		
		String name = planet.getName();
		
		List<Planet> planets = planetRepository.findByNameIgnoreCase(name); //busca na base pelo nome
		
		if (planets.size() > 0) 
		{
			throw new PlanetAlreadyExistsException(0); //caso exista um planeta com esse nome ou com essa id, dispara exceção
		}
		else
		{
			if (planet.getId() != null && planetRepository.exists(planet.getId()))
			{
				throw new PlanetAlreadyExistsException(1); //caso exista um planeta com esse nome ou com essa id, dispara exceção
			}
			else
			{
				newPlanet = getPlanetSWAPIByName(name); //caso contrário, busca na SWAPI pelo nome
				if (newPlanet != null)
				{
					
					//caso o planeta com o respectivo nome seja encontrado
					//irá inserir a ID passada pelo usuário
					//e irá sobrescrever dados de clima, terreno e filmes, caso o usuário passe esses parâmetros
					
					String idPlanet = planet.getId();
					if (idPlanet != null)
					{
						newPlanet.setId(idPlanet);
					}
					String climate = planet.getClimate();
					if (climate != null)
					{
						newPlanet.setClimate(climate);
					}
					String terrain = planet.getTerrain();
					if (terrain != null)
					{
						newPlanet.setTerrain(terrain);
					}
					List<String> films = planet.getFilms();
					if (films.size() > 0)
					{
						newPlanet.setFilms(films);
					}
					planetRepository.insert(newPlanet); //adiciona o novo planeta ao banco
				}
				else
				{
					throw new PlanetNotFoundException(1); //caso o planeta não seja encontrado na SWAPI
				}
			}
		}
		
		return ResponseEntity.ok(newPlanet);
		
	}

	@Override
	public ResponseEntity<String> getAll() 
	{
		StringBuilder listPlanetStr = new StringBuilder();
		List<Planet> listPlanet = null;
		
		listPlanet = planetRepository.findAll();
		
		if (listPlanet.size() > 0)
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
		else
		{
			throw new PlanetNotFoundException(2);
		}
		
		return ResponseEntity.ok(listPlanetStr.toString());
	}
	
	@Override
	public ResponseEntity<String> find(String name, String id) 
	{
		StringBuilder listPlanetStr = new StringBuilder();
		
		
		if (name != null && !name.equals("")) //caso possua o parâmetro NOME, realiza a busca por nome
		{
			List<Planet> planets = planetRepository.findByNameIgnoreCase(name); //procura na base de dados local se já existe o planeta cadastrado
			if (planets.size() == 0)
			{
				Planet planet = getPlanetSWAPIByName(name); //caso negativo, busca na SWAPI
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
		ResponseEntity<String> responseEntity = null;
		if (name != null && !name.equals("")) //caso possua o parâmetro NOME, realiza a busca por nome
		{
			List<Planet> planets = planetRepository.findByNameIgnoreCase(name);
			if (planets.size() == 0)
			{
				throw new PlanetNotFoundException(1); //caso não encontre o planeta com esse nome na base de dados 
			}
			for (Planet planet : planets)
			{
				planetRepository.delete(planet); //caso encontre, remove-o
			}
			responseEntity = new ResponseEntity<String>("{\"Sucesso:\":\"Planeta removido\"}", HttpStatus.OK);
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
				
				responseEntity = new ResponseEntity<String>("{\"Sucesso:\":\"Planeta removido\"}", HttpStatus.OK);
			}
		}
		
		return responseEntity;
	}
	
	/*
	 * Método para fazer a busca na SWAPI pública pelo nome do planeta e retornar o planeta, caso encontre
	 */
	public Planet getPlanetSWAPIByName(String name)
	{
		Planet planet = null;
		
		String URL_PLANETS = "https://swapi.co/api/planets/?search="+name;
		
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "HTTPie/1.0.0-dev");
        
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        
        ResponseEntity<Planets> planets = restTemplate.exchange(URL_PLANETS, HttpMethod.GET, entity, Planets.class);
        
        List<PlanetSWAPI> listPlanetSWAPI = planets.getBody().getResults();
        
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
