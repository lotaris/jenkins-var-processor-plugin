package com.lotaris.jenkins.varproc.processor;

/**
 * Dummy processor to handle the string in the same way than the other processor.
 * This greatly simplify the implementation of the overall mechanism
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class StringProcessor implements IProcessor {
	private String str;
	
	public StringProcessor(String str) {
		this.str = str;
	}

	public String process(IVariableResolver resolver) {
		return resolver.resolve(str);
	}
}
