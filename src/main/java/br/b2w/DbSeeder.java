package br.b2w;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.b2w.api.Planet;
import br.b2w.repository.PlanetRepository;

@Component
public class DbSeeder implements CommandLineRunner 
{
	
	private PlanetRepository planetRepository;
	
	public DbSeeder(PlanetRepository planetRepository)
	{
		this.planetRepository = planetRepository;
	}
	
	@Override
	public void run(String...strings) throws Exception
	{
		Planet terra = new Planet("Terra","Quente","Favela", 20);
		
		Planet marte = new Planet("Marte", "Quente", "Rochoso", 15);
		
		this.planetRepository.deleteAll();
		
		List<Planet> planets = Arrays.asList(terra, marte);
		
		this.planetRepository.save(planets);
				
	}

}
