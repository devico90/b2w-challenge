package br.b2w.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Planets")
public class Planet 
{
	
	@Id
	private String id;
	private String name;
	private String climate;
	private String terrain;
	private int countFilms;
	
	public Planet() { }
	
	public Planet(String name, String climate, String terrain, int countFilms)
	{
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.countFilms = countFilms;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public int getCountFilms() {
		return countFilms;
	}

	public void setCountFilms(int countFilms) {
		this.countFilms = countFilms;
	}

	
	
}
