package com.lotaris.jenkins.varproc;

import com.lotaris.jenkins.varproc.processor.ILogger;
import com.lotaris.jenkins.varproc.processor.IVariableResolver;
import com.lotaris.jenkins.varproc.scanner.KeywordScanner;
import com.lotaris.jenkins.varproc.scanner.Line;
import hudson.EnvVars;
import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;
import javax.servlet.ServletException;

/**
 * Build step to extend the current variables with dynamic variable generation.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class VarProcessorBuilder extends Builder {
	/**
	 * The content to parse
	 */
	private final String varContent;
	
	/**
	 * 
	 */
	private final boolean logEnabled;
	
	@DataBoundConstructor
	public VarProcessorBuilder(String varContent, boolean logEnabled) {
		this.varContent = varContent;
		this.logEnabled = logEnabled;
	}

	public String getVarContent() {
		return varContent;
	}

	public boolean islogEnabled() {
		return logEnabled;
	}
	
	@Override
	public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
		EnvVars env = null;
		
		try {
			// Get the build variables
			env = build.getEnvironment(listener);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}
		
		Map<String, String> extendedParameters = new HashMap<String, String>();
		IVariableResolver resolver = new VariableResolver(env);
		ILogger logger = new Logger(listener.getLogger(), logEnabled);
		
		// Parsing of the lines
		for (String line : varContent.split("\n")) {
			if (line.contains("=")) {
				String[] keyValue = line.split("=");
				extendedParameters.put(keyValue[0], new KeywordScanner(logger).scan(new Line(keyValue[1])).process(resolver));
			}
		}
		
		if (extendedParameters.size() > 0) {
			build.addAction(new VarProcExtendVariablesAction(extendedParameters));
		}

		return true;
	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		public DescriptorImpl() {
			load();
		}

		/**
		 * Performs on-the-fly validation of the form field 'varContent'.
		 *
		 * @param value This parameter receives the value that the user has typed.
		 * @return Indicates the outcome of the validation. This is sent to the browser.
		 * <p>
		 * Note that returning {@link FormValidation#error(String)} does not prevent the form from being saved. It just means that a message will be displayed to
		 * the user.
		 */
		public FormValidation doCheckVarContent(@QueryParameter String value)
			throws IOException, ServletException {
			if (value.length() == 0) {
				return FormValidation.error("Please add var generation content.");
			}
			if (value.length() < 4) {
				return FormValidation.warning("Isn't the var content too short?");
			}
			return FormValidation.ok();
		}

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}

		public String getDisplayName() {
			return "Process new parameters and extend the build variables.";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
			save();
			return super.configure(req, formData);
		}
	}
}
