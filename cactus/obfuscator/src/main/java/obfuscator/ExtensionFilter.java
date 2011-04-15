package obfuscator;

import java.io.File;
import java.io.FilenameFilter;

public class ExtensionFilter implements FilenameFilter {

	private String extension;
	
	public ExtensionFilter(String extension){
		this.extension = extension.toLowerCase();
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(extension);
	}
}
