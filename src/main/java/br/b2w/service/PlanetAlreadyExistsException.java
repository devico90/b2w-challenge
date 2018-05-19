package br.b2w.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlanetAlreadyExistsException extends RuntimeException 
{
	
	private static final String STR_PLANET_ALREADY_EXISTS = "[ID] Existe um planeta cadastrado com esse identificador";
	
	public PlanetAlreadyExistsException() 
	{
		super(STR_PLANET_ALREADY_EXISTS);
	}
}

