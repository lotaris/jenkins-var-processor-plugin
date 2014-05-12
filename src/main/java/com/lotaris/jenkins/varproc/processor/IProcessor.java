package com.lotaris.jenkins.varproc.processor;

/**
 * Processor that execute a command
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IProcessor {
	/**
	 * Process the command
	 * 
	 * @param resolver A resolver to interpolate variables injected in the language (only in strings for the moment)
	 * @return The result of the processor (only string results supported)
	 */
	String process(IVariableResolver resolver);
}
