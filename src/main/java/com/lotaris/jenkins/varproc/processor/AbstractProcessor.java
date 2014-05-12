package com.lotaris.jenkins.varproc.processor;

/**
 * Abstract processor for complex statements
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public abstract class AbstractProcessor implements IProcessor {
	/**
	 * Parameters
	 */
	protected ProcessorParams params;

	/**
	 * Constructor
	 * 
	 * @param params Parameters
	 */
	public AbstractProcessor(ProcessorParams params) {
		this.params = params;
	}
}
