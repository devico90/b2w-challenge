package br.b2w;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
		planetRepository.deleteAll();
	}

}
