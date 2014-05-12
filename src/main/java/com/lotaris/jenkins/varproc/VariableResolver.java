package com.lotaris.jenkins.varproc;

import com.lotaris.jenkins.varproc.processor.IVariableResolver;
import hudson.EnvVars;

/**
 * Wrapper of the EnvVars resolver
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class VariableResolver implements IVariableResolver {
	/**
	 * Environment variables
	 */
	private EnvVars env;

	/**
	 * Constructor
	 * 
	 * @param env Environment variables
	 */
	public VariableResolver(EnvVars env) {
		this.env = env;
	}
	
	/**
	 * Resolve variables in a string
	 * 
	 * @param toResolve The string to resolve
	 * @return The string resolved
	 */
	public String resolve(String toResolve) {
		return env.expand(toResolve);
	}
}
