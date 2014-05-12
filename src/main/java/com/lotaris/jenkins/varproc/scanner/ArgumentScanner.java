package com.lotaris.jenkins.varproc.scanner;

import com.lotaris.jenkins.varproc.processor.ILogger;
import com.lotaris.jenkins.varproc.processor.IProcessor;
import com.lotaris.jenkins.varproc.processor.ProcessorParams;
import com.lotaris.jenkins.varproc.processor.StringProcessor;

/**
 * Argument scanner is able to parse arguments
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class ArgumentScanner {
	private ILogger logger;
	
	public ArgumentScanner(ILogger logger) {
		this.logger = logger;
	}
	
	/**
	 * Scan a line to get the parameters
	 * 
	 * @param line The line in the current state
	 * @return The arguments parsed
	 */
	public ProcessorParams scan(Line line) {
		StringBuilder argName = new StringBuilder();
		
		ProcessorParams params = new ProcessorParams();
		
		while (line.hasNext()) {
			Character c = line.next();
			// Parse a name caracter
			if (Character.isLetter(c)) {
				logger.log("Argument letter detected: " + c);
				argName.append(c);
			}
			// Parse an argument separator
			else if (c == ':') {
				logger.log("Argument value separator: " + c);
				//line.back();
				params.add(argName.toString(), scanValue(line));
			}
			// Parse arguments separator (new argument is coming)
			else if (c == ',') {
				logger.log("Argument separator: " + c);
				argName = new StringBuilder();
			}
			// Parse the end of an argument list (list is one or more arguments)
			else if (c == ')') {
				logger.log("Argument end of args: " + c);
				break;
			}
		}
		
		return params;
	} 
	
	/**
	 * Scan a value when detected
	 * 
	 * @param line The line in the current state
	 * @return The Processor that represent the value
	 */
	private IProcessor scanValue(Line line) {
		while (line.hasNext()) {
			Character c = line.next();

			// Read the start of a string
			if (c == '"') {
				logger.log("Argument start of string: " + c);
				return new StringProcessor(scanString(line));
			}
			// Read a new processor definition
			else {
				logger.log("New keyword detected: " + c);
				line.back();
				return new KeywordScanner(logger).scan(line);
			}
		}
		
		return null;
	}
	
	/**
	 * Scan a string value
	 * 
	 * @param line The line in the current state
	 * @return The string scanned
	 */
	private String scanString(Line line) {
		StringBuilder str = new StringBuilder();
		
		boolean escape = false;

		while (line.hasNext()) {
			Character c = line.next();
			
			// Read an escaping caracter
			if (c == '\\') {
				logger.log("Argument escaping: " + c);
				escape = true;
			}
			// Read a string end if there is no escaping detected in the caracter before
			else if (!escape && c == '"') {
				logger.log("Argument end of string: " + c);
				return str.toString();
			}
			// Read the caracter a string component and reset the escaping
			else {
				logger.log("Argument string: " + c);
				str.append(c);
				escape = false;
			}
		}
		
		return "";
	}
}
