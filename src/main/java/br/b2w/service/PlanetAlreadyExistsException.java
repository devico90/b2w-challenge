package br.b2w.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlanetAlreadyExistsException extends RuntimeException 
{
	
	private static final String STR_PLANET_NAME_ALREADY_EXISTS = "[NAME] Existe um planeta cadastrado com esse nome";
	private static final String STR_PLANET_ID_ALREADY_EXISTS = "[ID] Existe um planeta cadastrado com esse identificador";
	
	/*
	 * ERROR = 0 -> NAME duplicado
	 * ERROR = 1 -> ID duplicado
	 */
	public PlanetAlreadyExistsException(int error) 
	{
		super(error == 0 ? STR_PLANET_NAME_ALREADY_EXISTS : STR_PLANET_ID_ALREADY_EXISTS);
	}
}

