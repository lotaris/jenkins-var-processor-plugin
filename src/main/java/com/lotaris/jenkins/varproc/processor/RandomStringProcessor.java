package com.lotaris.jenkins.varproc.processor;

import java.security.SecureRandom;

/**
 * Random string processor. This processor generates a random string based
 * on the arguments given.
 * 
 * <h2>Mandatory parameters</h2>
 * <ul>
 *	<li>len: define the length of the string generated</li>
 * </ul>
 * 
 * <h2>Optional parameters
 * <ul>
 *  <li>range: define the type of caracters to use to generate the string. Default: alpha-numeric.</li>
 *  <li>prefix: define a prefix to pre-prend. Default: none. Rermark: Prefix applied as given without additional caracter.</li>
 *  <li>suffix: define a suffix to append. Default: none. Rermark: Suffix applied as given without additional caracter.</li>
 * </ul>
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class RandomStringProcessor extends AbstractProcessor {
	/**
	 * Default alpha-numeric caracters
	 */
	private static final String ALPHA_NUM = "abcdefghijklmnopqrstuvwxyz0987654321";
	
	/**
	 * Random generator
	 */
	private SecureRandom rnd = new SecureRandom();

	public RandomStringProcessor(ProcessorParams params) {
		super(params);
	}

	public String process(IVariableResolver resolver) {
		// Get the length for the string to generate
		int length = Integer.parseInt(params.getMandatory("len").process(resolver));
		
		// Check if a custom range
		String range;
		if (params.has("range")) {
			range = params.get("range").process(resolver);
		}
		else {
			range = ALPHA_NUM;
		}
		
		StringBuilder sb = new StringBuilder();

		// Add a prefix if present
		if (params.has("prefix")) {
			sb.append(params.get("prefix").process(resolver));
		}

		// Generate the random string
		for (int i = 0; i < length; i++) {
			sb.append(range.charAt(rnd.nextInt(range.length())));
		}
		
		// Add a suffix if present
		if (params.has("suffix")) {
			sb.append(params.get("suffix").process(resolver));
		}

		return sb.toString();
	}
}