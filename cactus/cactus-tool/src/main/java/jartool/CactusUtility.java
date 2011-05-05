package jartool;

import java.io.File;

import obfuscator.DirectoryTraverser;
import obfuscator.MojoData;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class CactusUtility {

	private Log log;
    
    /**
     * Location of the JavaScript sources.
     */
    private File baseDirectory = new File(System.getProperty("user.dir"));
    
    private File configDirectory;
    	
	/**
	 * PRODUCTION/DEBUG
	 */
	private String mode = "PRODUCTION";
	
	private String outputDirectory;
	
	public static void main(String[] args) throws MojoExecutionException {
		
		CactusUtility tool = new CactusUtility();

		if (args.length > 0) {			
			for (int i = 0; i < args.length; i = i + 2) {
				
				if (args[i].equalsIgnoreCase("-d")) {
					tool.baseDirectory = new File(args[i + 1]);
					continue;
				}
				
				if (args[i].equalsIgnoreCase("-c")) {
					tool.configDirectory = new File(args[i + 1]);
					continue;
				}
				
				if (args[i].equalsIgnoreCase("-m")) {
					tool.mode = args[i + 1];
					continue;
				}
				
				if (args[i].equalsIgnoreCase("-o")) {
					tool.outputDirectory = args[i + 1]; 
					MojoData.obfuscate.put("outputDirectory", new File(tool.baseDirectory, tool.outputDirectory));
				}
			}	
		}
		
		Log log = tool.getLog();
		log.info("Start files minification");
		log.info("mode: " + tool.mode);
		
		MojoData.obfuscate.put("jsBase", tool.baseDirectory);		
		MojoData.obfuscate.put("log", log);
		MojoData.obfuscate.put("mode", tool.mode);
		
		try {			
			DirectoryTraverser.getInstance().processMainDirectory(tool.baseDirectory, tool.configDirectory);			
		} catch (Exception e) {
			throw new MojoExecutionException("JavaScript minification failed", e);
		}
		
		log.info("End files minification");		
	}
	
    public Log getLog() {
		if (log == null) {
			log = new SystemStreamLog();
		}
        return log;
    }
}
