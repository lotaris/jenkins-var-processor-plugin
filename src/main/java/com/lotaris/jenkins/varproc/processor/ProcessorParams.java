package com.lotaris.jenkins.varproc.processor;

import java.util.HashMap;
import java.util.Map;

/**
 * Parameters storage
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class ProcessorParams {
	/**
	 * The real parameters
	 */
	Map<String, IProcessor> params = new HashMap<String, IProcessor>();

	/**
	 * Add a new parameter
	 * 
	 * @param key The name of the parameter
	 * @param value The value of the paremeter
	 * @throws RuntimeException When a parameter is already present
	 */
	public void add(String key, IProcessor value) {
		if (params.containsKey(key)) {
			throw new RuntimeException("The parameter [" + key + "] is already present in the call." );
		}
		else {
			params.put(key, value);
		}
	}
	
	/**
	 * Retrieve a mandatory parameter
	 * 
	 * @param key The name of the mandatory parameter
	 * @return The parameter found
	 * @throws RuntimeException When the parameter is not found
	 */
	public IProcessor getMandatory(String key) {
		if (!params.containsKey(key)) {
			throw new RuntimeException("The [" + key + "] parameter is missing.");
		}
		else {
			return params.get(key);
		}
	}
	
	/**
	 * Retrieve a parameter
	 * 
	 * @param key The name of the parameter
	 * @return The parameter found, null if not found
	 */
	public IProcessor get(String key) {
		return params.get(key);
	}
	
	/**
	 * Check if a parameter is present
	 * 
	 * @param key The name of the parameter
	 * @return True if present, false otherwise
	 */
	public boolean has(String key) {
		return params.containsKey(key);
	}
	
	/**
	 * Validate the presence of the mandatory parameters given in parameters
	 * 
	 * @param mandatoryParameters The name of the mandatory parameters
	 * @throws RuntimeException When at least one parameter is missing, message contains all the parameter names missing
	 */
	public void validatePresence(String ... mandatoryParameters) {
		StringBuilder sbError = new StringBuilder();
		for (String mandatoryParameter : mandatoryParameters) {
			if (!params.containsKey(mandatoryParameter)) {
				sbError.append("The parameter [").append(mandatoryParameter).append("] is missing.\n");
			}
		}
		
		if (sbError.length() > 0) {
			throw new RuntimeException(sbError.toString());
		}
	}
}
