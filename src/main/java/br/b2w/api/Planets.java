package br.b2w.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Planets 
{
	
	int count;
	String next;
	String previous;
	List<PlanetSWAPI> results;
	
	public Planets()
	{
	}

	public int getCount() 
	{
		return count;
	}

	public void setCount(int count) 
	{
		this.count = count;
	}

	public String getNext()
	{
		return next;
	}

	public void setNext(String next) 
	{
		this.next = next;
	}

	public String getPrevious() 
	{
		return previous;
	}

	public void setPrevious(String previous) 
	{
		this.previous = previous;
	}

	public List<PlanetSWAPI> getResults()
	{
		return results;
	}

	public void setResults(List<PlanetSWAPI> results) 
	{
		this.results = results;
	}

	
	
}
