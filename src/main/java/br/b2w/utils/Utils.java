package br.b2w.utils;

import br.b2w.api.Planet;
import br.b2w.repository.PlanetRepository;

public class Utils {

	public static boolean exists(PlanetRepository planetRepository, String id)
	{
		Planet planet = planetRepository.findOne(id);
		if (planet == null)
		{
			return false;
		}
		return true;
	}
	
}
