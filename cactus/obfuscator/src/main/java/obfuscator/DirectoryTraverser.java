package obfuscator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import config.xml.Cactus;
import config.xml.CactusNeedle;
import config.xml.CssSection;
import config.xml.JavaScriptSection;

public class DirectoryTraverser {
	
	private static DirectoryTraverser instance = null;
	private Serializer serializer;
    private static Log log;
	
	private DirectoryTraverser() {
		serializer = new Persister();
        log = (Log)MojoData.obfuscate.get("log");
	}	
	
	public static DirectoryTraverser getInstance() {
		if (instance == null){
			instance = new DirectoryTraverser();
		}
		
		return instance;
	}
	
	public void processMainDirectory(File directory, File configDirectory) 
	throws Exception {
		long start = System.currentTimeMillis();
		log.info("Base working directory: " + directory.getAbsolutePath());

		File cactusXml = new File(configDirectory, "cactus.xml");
		
		if (cactusXml.exists()) {
            Cactus config = serializer.read(Cactus.class, cactusXml);
            String mode = (String)MojoData.obfuscate.get("mode");

			if (config != null) {
				List<CactusNeedle> needles;

				// JavaScript section
				JavaScriptSection js = config.getJavaScript();
				if (js != null) {
					needles = js.getNeedles();
					if (needles != null) {
						for (CactusNeedle needle : needles) {
							processNeedle(directory, needle, mode, "js");
						}					
					}
				}

				// Css section
				CssSection css = config.getCss();
				if (css != null) {
					needles = css.getNeedles();
					if (needles != null) {
						for (CactusNeedle needle : needles) {
							processNeedle(directory, needle, mode, "css");
						}					
					}
				}
			}			
		} else {
			log.error("cactus.xml configuration file was not found");
		}
		
		long end = System.currentTimeMillis();
		log.info("time: " + (end - start) + " milli seconds.");
	}
	
	/**
	 * Obfuscates needles.
     *
	 * @param directory The base directory
	 * @param needle The needle configuration
	 * @param mode The mode
	 * @throws Exception
	 */
	private void processNeedle(File directory, CactusNeedle needle, String mode, String type)
	throws Exception {
		File needleFile;
		String outFileName = needle.getOutputFileName();
		if (MojoData.obfuscate.get("outputDirectory") != null) {
			File outDirectory = (File)MojoData.obfuscate.get("outputDirectory");
			needleFile = new File(outDirectory, outFileName);
		} else {
			needleFile = new File(directory, outFileName);
		}
		
		List<config.xml.File> files = needle.getFiles();
		List<String> fileNames = new ArrayList<String>();
		for (config.xml.File file : files) {
			fileNames.add(file.getFileName());
		}

		String content = Minifier.concatenate(directory, fileNames);
		if (content != null && !content.isEmpty()) {
			if (!mode.equals("DEBUG")) {
                InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(content.getBytes("UTF-8")), "UTF-8");
                content = type.equals("js") ? Minifier.minify(in) : Minifier.minifyCss(in);
			}

			Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(needleFile), "UTF8"));
			w.write(content);
			w.close();
		}
	}
}
