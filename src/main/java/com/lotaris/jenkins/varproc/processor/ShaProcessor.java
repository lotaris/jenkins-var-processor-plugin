package com.lotaris.jenkins.varproc.processor;

import java.security.MessageDigest;

/**
 * SHA string generator. Take a string and a strength, then generate the
 * hash of string.
 * 
 * <h2>Mandatory parameters</h2>
 * <ul>
 *	<li>strength: define the SHA algorithm to use. Possible: 1, 256, 512.</li>
 *	<li>str: define the string to hash. Default: none.</li>
 * </ul>
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class ShaProcessor extends AbstractProcessor {
	public ShaProcessor(ProcessorParams params) {
		super(params);
	}

	public String process(IVariableResolver resolver) {
		try{
			// Retrieve the algorithm
			int strength = Integer.parseInt(params.getMandatory("strength").process(resolver));
			
			// Build the right digest
			MessageDigest digest;
			if (strength == 1) {
				digest = MessageDigest.getInstance("SHA");
			}
			else {
				digest = MessageDigest.getInstance("SHA-" + strength);
			}
				
			// Get the string and hash it
			byte[] hash = digest.digest(params.getMandatory("str").process(resolver).getBytes("UTF-8"));

			// Convert the result to HEX string
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
			
				if(hex.length() == 1) {
					hexString.append('0');
				}
				
				hexString.append(hex);
			}

			return hexString.toString();
    } 
		catch(Exception ex){
       throw new RuntimeException(ex);
    }
	}
}