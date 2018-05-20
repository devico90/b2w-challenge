package br.b2w.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends RuntimeException 
{
	
	private static final String STR_PLANET_ID_NOT_FOUND = "[ID] Planeta não encontrado";
	private static final String STR_PLANET_NAME_NOT_FOUND = "[NOME] Planeta não encontrado";
	private static final String STR_PLANET_LIST_EMPTY = "Lista de planetas vazia";
	
	
	/*
	 * ERROR = 0 -> ID não encontrada
	 * ERROR = 1 -> Nome não encontrado
	 * ERROR = 2 -> Lista de planetas vazia
	 */
	public PlanetNotFoundException(int error) 
	{
		super(error == 0 ? STR_PLANET_ID_NOT_FOUND : error == 1 ? STR_PLANET_NAME_NOT_FOUND : STR_PLANET_LIST_EMPTY);
	}
}

