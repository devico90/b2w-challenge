package br.b2w.service;

import org.springframework.http.ResponseEntity;

import br.b2w.api.Planet;

public interface IPlanetService  
{
	ResponseEntity<Planet> insert(Planet planet);
	
	ResponseEntity<String> getAll();
	
	ResponseEntity<String> find(String name, String id);
	
	ResponseEntity<String> delete(String name, String id);
}
