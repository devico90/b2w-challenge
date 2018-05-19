package br.b2w.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.b2w.api.Planet;

public interface IServicePlanet  
{
	ResponseEntity<Planet> insert(Planet planet);
	ResponseEntity<List<Planet>> getAll();
	ResponseEntity<?> find(String name, String id);
	ResponseEntity<String> deleteById(String id);
}
