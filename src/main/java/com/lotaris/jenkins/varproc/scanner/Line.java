package com.lotaris.jenkins.varproc.scanner;

/**
 * Manage the state of a line to parse
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class Line {
	/**
	 * The line content
	 */
	private String line;

	/**
	 * The character pointer for the parsing
	 */
	private int position = 0;

	public Line(String line) {
		this.line = line;
	}

	/**
	 * Check if there remaining characters
	 * @return True if there is at least one remaining character
	 */
	public boolean hasNext() {
		return position < line.length();
	}

	/**
	 * Go to the next character
	 * @return The current character before moving to the next character
	 */
	public char next() {
		return line.charAt(position++);
	}

	/**
	 * Go back to the previous character
	 */
	public void back() {
		position--;
	}
}
