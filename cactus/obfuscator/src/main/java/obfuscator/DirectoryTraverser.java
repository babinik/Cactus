package obfuscator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import config.xml.Cactus;
import config.xml.CactusNeedle;

public class DirectoryTraverser {

	private DirectoryTraverser(){
		serializer = new Persister();
	}
	
	private static DirectoryTraverser instance = null;
		
	private Serializer serializer;
	
	public static DirectoryTraverser getInstance() {
		if (instance == null){
			instance = new DirectoryTraverser();
		}
		return instance;
	}
	
	public void processMainDirectory(File directory) 
	throws Exception {
		long start = System.currentTimeMillis();		
		Log log = (Log)MojoData.obfuscate.get("log");
		String mode = (String)MojoData.obfuscate.get("mode");
		
		log.info("Base JavaScript directory: " + directory.getAbsolutePath());
		
		File cactusXml = new File(directory, "cactus.xml"); 
		Cactus config = null;
		
		if (cactusXml.exists()) {
			config = serializer.read(Cactus.class, cactusXml);			
			if (config != null) {
				List<CactusNeedle> needles = config.getNeedles();				
				for (CactusNeedle needle : needles) {
					processNeedle(directory, needle, mode);
				}
			}
			
		} else {
			//no cactus.xml configuration file
			//do nothing for now
		}
		
		long end = System.currentTimeMillis();
		log.info("time: " + (end - start) + " msec.");
	}
	
	private void processNeedle(File directory, CactusNeedle needle, String mode) 
	throws Exception {
		
		File needleFile = null;
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
		
		if (!content.isEmpty()) {
			if (!mode.equals("DEBUG")) {				
				content = Minifier.minify(new ByteArrayInputStream(content.getBytes("UTF-8")));
			}
			Writer w = new FileWriter(needleFile);
			w.write(content);
			w.close();
		}
	}	
}
