package br.b2w.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.b2w.api.Planet;

public interface IServicePlanet  
{
	ResponseEntity<Planet> insert(Planet planet);
	ResponseEntity<List<Planet>> getAll();
	ResponseEntity<List<Planet>> findByNameIgnoreCase(String name);
	ResponseEntity<Planet> findById(String id);
//	ResponseEntity<String> delete(Planet planet);
	ResponseEntity<String> deleteById(String id);
//	ResponseEntity<String> deleteByName(String name);
}
