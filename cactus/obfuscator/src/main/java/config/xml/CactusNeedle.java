package config.xml;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Nikolai Babinski
 */
@Root(name = "needle")
public class CactusNeedle {
	
	@Element(name = "output", required = true)
	private String outputFileName;
	
	@ElementList(name = "files", required = true)
	private List<File> files;

	/**
	 * @return the outputFileName
	 */
	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * @param outputFileName the outputFileName to set
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}	
}
