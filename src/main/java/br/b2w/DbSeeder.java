package br.b2w;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.b2w.repository.PlanetRepository;
import br.b2w.service.ServicePlanet;

@Component
public class DbSeeder implements CommandLineRunner 
{
	
	private PlanetRepository planetRepository;
	
	private ServicePlanet servicePlanet;
	
	public DbSeeder(PlanetRepository planetRepository)
	{
		this.planetRepository = planetRepository;
	}
	
	@Override
	public void run(String...strings) throws Exception
	{
		planetRepository.deleteAll();
		
		
		
	}

}
