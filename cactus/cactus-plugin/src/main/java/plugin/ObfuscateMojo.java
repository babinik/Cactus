package plugin;

import java.io.File;

import obfuscator.DirectoryTraverser;
import obfuscator.MojoData;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * @goal obfuscate
 * 
 * @requiresProject false
 * 
 * @phase process-sources
 */
public class ObfuscateMojo extends AbstractMojo {
    /**
     * Location of the file.
     * 
     * @parameter expression="${project.basedir}" default-value="./"
     * @required
     */
    private File baseDirectory;

    /**
     * @parameter expression="${confingDirectory}"
     */
    private File confingDirectory;
    
    /**
     * Location of the JavaScript sources.
     * 
     * @parameter expression="${jsBaseDirectory}" 
     */
    private String jsBaseDirectory;    
    
    /**
     * Output directory for needles files
     * 
     * @parameter
     */    
    private String outputDirectory;
    
    /**
     * @parameter default-value="PRODUCTION"
     * 
     * acceptable values: PRODUCTION/DEBUG
     */
    private String mode;
    
	public void execute() throws MojoExecutionException {
		Log log = getLog();
		log.info("Start files minification");
		log.info("mode: " + mode);
		
		File jsBase = getJavaScriptBaseDir();
		if (outputDirectory != null) {
			MojoData.obfuscate.put("outputDirectory", new File(jsBase, outputDirectory));
		}
		MojoData.obfuscate.put("jsBase", jsBase);		
		MojoData.obfuscate.put("log", log);
		MojoData.obfuscate.put("mode", mode);
		
		try {
			if (confingDirectory == null) {
				confingDirectory = jsBase;
			}
			DirectoryTraverser.getInstance().processMainDirectory(jsBase, confingDirectory);
		} catch (Exception e) {
			throw new MojoExecutionException("JavaScript minification failed", e);
		}
		
		log.info("End files minification");
    }
	
	/**
	 * Returns JavaScript base directory path.
	 * @return js path 
	 */
	private File getJavaScriptBaseDir() {
		File jsDir = baseDirectory;		
		if (jsBaseDirectory != null) {
			jsDir = new File(baseDirectory, jsBaseDirectory); 
		}
		
		return jsDir;
	}
    
}
