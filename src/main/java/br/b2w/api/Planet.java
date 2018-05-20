package br.b2w.api;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Planet")
public class Planet 
{
	
	@Id
	private String id;
	private String name;
	private String climate;
	private String terrain;
	private List<String> films;
	
	public Planet()
	{ 
	}
	
	public Planet(String name, String climate, String terrain, List<String> films)
	{
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.films = films;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getClimate()
	{
		return climate;
	}

	public void setClimate(String climate) 
	{
		this.climate = climate;
	}

	public String getTerrain() 
	{
		return terrain;
	}

	public void setTerrain(String terrain)
	{
		this.terrain = terrain;
	}

	public List<String> getFilms()
	{
		return films;
	}

	public void setFilms(List<String> films) 
	{
		this.films = films;
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append("\"id\": \""+id+"\"");
		
		return str.toString();
	}
	
}
