package br.b2w;

import java.util.ArrayList;
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
		this.planetRepository.deleteAll();
		
		List<String> listaFilmes = new ArrayList<String>();
		
		listaFilmes.add("Filme terra");
		Planet terra = new Planet("TERRA","Quente","Favela", listaFilmes);
		planetRepository.save(terra);
		
		listaFilmes.add("Filmes terra e marte");
		Planet marte = new Planet("MARTE", "Quente", "Rochoso", listaFilmes);
		planetRepository.save(marte);
		
		listaFilmes.add("Filmes terra e marte e venus");
		Planet venus = new Planet("VENUS", "FRIO", "Rochoso", listaFilmes);
		planetRepository.save(venus);
		
		listaFilmes.add("Filmes terra e marte e venus e plutao");
		Planet plutao = new Planet("PLUTAO", "geladasso", "montanhoso", listaFilmes);
		planetRepository.save(plutao);		
				
	}

}
