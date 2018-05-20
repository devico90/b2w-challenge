package br.b2w.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.b2w.api.Planet;


@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> 
{
	
	List<Planet> findAll();
	
	@Query("{ 'name' : ?0 }")
	List<Planet> findByName(String name);
	
	Planet findOne(String id);
	
}
	
