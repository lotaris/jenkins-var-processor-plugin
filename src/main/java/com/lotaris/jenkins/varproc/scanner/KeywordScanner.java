package com.lotaris.jenkins.varproc.scanner;

import com.lotaris.jenkins.varproc.processor.EProcessors;
import com.lotaris.jenkins.varproc.processor.ILogger;
import com.lotaris.jenkins.varproc.processor.IProcessor;
import com.lotaris.jenkins.varproc.processor.ProcessorParams;

/**
 * Scanner for the processors
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class KeywordScanner {
	private ILogger logger;
	
	public KeywordScanner(ILogger logger) {
		this.logger = logger;
	}
	
	/**
	 * Scan a line to get a processor
	 * 
	 * @param line The line in the current state
	 * @return The processor found
	 */
	public IProcessor scan(Line line) {
		StringBuilder keyWordBuilder = new StringBuilder();
		
		ProcessorParams params = null;
		
		while (line.hasNext()) {
			Character c = line.next();
			
			// Keyword caracter detected
			if (Character.isLetter(c)) {
				logger.log("Keyword letter detected: " + c);
				keyWordBuilder.append(c);
			}
			// Argument list start detected
			else if (c == '(') {
				logger.log("Keyword start of args detected: " + c);
				// Just ingore the token and start the next tokenization
				params = new ArgumentScanner(logger).scan(line);
			}
		}

		// If no arguments, return a processor with an empty parameters storage
		if (params == null) {
			return EProcessors.fromKeyword(keyWordBuilder.toString()).createProcessor(new ProcessorParams());
		}
		else {
			return EProcessors.fromKeyword(keyWordBuilder.toString()).createProcessor(params);
		}
	}
}
