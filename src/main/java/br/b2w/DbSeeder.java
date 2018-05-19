package br.b2w;

import java.util.Arrays;

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
		this.planetRepository.deleteAll();

		Planet terra = new Planet("TERRA","Quente","Favela", 20);
		
		Planet marte = new Planet("MARTE", "Quente", "Rochoso", 15);
		
		Planet venus = new Planet("VENUS", "FRIO", "Rochoso", 12);
		
		Planet plutao = new Planet("PLUTAO", "geladasso", "montanhoso", 5);
		
		this.planetRepository.save(Arrays.asList(terra, marte, venus, plutao));
				
	}

}
