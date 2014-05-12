package com.lotaris.jenkins.varproc;

import com.lotaris.jenkins.varproc.processor.ILogger;
import java.io.PrintStream;

/**
 * Wrapper of the Logger from Jenkins
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class Logger implements ILogger {
	private PrintStream logger;
	private boolean enabled;

	/**
	 * Constructor
	 * 
	 * @param logger Logger
	 * @param enabled Define if the logger should activated or not
	 */
	public Logger(PrintStream logger, boolean enabled) {
		this.logger = logger;
		this.enabled = enabled;
	}

	public void log(String log) {
		if (enabled) {
			logger.println("[DEBUG] - " + log);
		}
	}
}
