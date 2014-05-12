package com.lotaris.jenkins.varproc.processor;

/**
 * List of available processors
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public enum EProcessors {
	/**
	 * Random string processor
	 */
	RANDOM_STRING("rnd", new String[] {"len"}, new String[] {"range", "prefix", "suffix"}) {
		@Override
		public IProcessor createInternalProcessor(ProcessorParams params) {
			return new RandomStringProcessor(params);
		}
	},
	
	/**
	 * SHA processor
	 */
	SHA("sha", new String[] {"strength", "str"}) {
		@Override
		public IProcessor createInternalProcessor(ProcessorParams params) {
			return new ShaProcessor(params);
		}
	};
	
	/**
	 * Statement name
	 */
	private String procName;
	
	/**
	 * Mandatory arguments
	 */
	private String[] mandatoryArgs;
	
	/**
	 * Optional arguments
	 */
	private String[] optionalArgs;

	/**
	 * Constructor
	 * 
	 * @param procName Statement name
	 * @param mandatoryArgs The mandatory arguments
	 */
	private EProcessors(String procName, String[] mandatoryArgs) {
		this.procName = procName;
		this.mandatoryArgs = mandatoryArgs;
	}

	/**
	 * Constructor
	 * 
	 * @param procName Statement name
	 * @param mandatoryArgs The mandatory arguments
	 * @param optionalArgs The optional arguments
	 */
	private EProcessors(String procName, String[] mandatoryArgs, String[] optionalArgs) {
		this(procName, mandatoryArgs);
		this.optionalArgs = optionalArgs;
	}

	/**
	 * Retrieve the statement based on a string
	 * 
	 * @param keyword The keyword to look for
	 * @return The statement found
	 */
	public static EProcessors fromKeyword(String keyword) {
		for (EProcessors processor : EProcessors.values()) {
			if (processor.getProcName().equals(keyword)) {
				return processor;
			}
		}
		
		throw new RuntimeException("Unable to find the keyword: " + keyword);
	}
	
	public String getProcName() {
		return procName;
	}

	public String[] getMandatoryArgs() {
		return mandatoryArgs;
	}

	public String[] getOptionalArgs() {
		return optionalArgs;
	}
	
	/**
	 * Create a processor with the parameters given
	 * 
	 * @param params The parameters
	 * @return The processor created
	 */
	public IProcessor createProcessor(ProcessorParams params) {
		return createInternalProcessor(validateParams(params));
	}
	
	/**
	 * Internal call to create the real implementation of a processor
	 * 
	 * @param params The parameters for the processor
	 * @return The processor created
	 */
	protected abstract IProcessor createInternalProcessor(ProcessorParams params);
	
	/**
	 * Validate the arguments that are mandatory. No type check are done, only presence
	 * validation is done at the moment.
	 * 
	 * @param params The parameters to validate
	 * @return Parameters given in parameter as a builder pattern
	 */
	private ProcessorParams validateParams(ProcessorParams params) {
		params.validatePresence(getMandatoryArgs());
		return params;
	}
}
