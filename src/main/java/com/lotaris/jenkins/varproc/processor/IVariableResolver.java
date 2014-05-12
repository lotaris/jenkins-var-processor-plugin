package com.lotaris.jenkins.varproc.processor;

/**
 * Variable resolver to interpolate variables from the Jenkins build
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IVariableResolver {
	/**
	 * Interpolate strings
	 * 
	 * @param toResolve The string to resolve
	 * @return The string interpolated
	 */
	String resolve(String toResolve);
}
