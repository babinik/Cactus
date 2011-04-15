package config.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * @author Nikolai Babinski
 *
 */
@Root(name="file")
public class File {
	
	@Attribute(name="recursive", required=false)
	private Boolean recursive;
	
	@Text(required=true)
	private String fileName;

	/**
	 * @return the recursive
	 */
	public Boolean getRecursive() {
		return recursive;
	}

	/**
	 * @param recursive the recursive to set
	 */
	public void setRecursive(Boolean recursive) {
		this.recursive = recursive;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
