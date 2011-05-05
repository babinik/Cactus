package config.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="css")
public class CssSection {

	@ElementList(name = "needles", required = false, entry = "needle")
	private List<CactusNeedle> needles;
	
	/**
	 * @return the needles
	 */
	public List<CactusNeedle> getNeedles() {
		return needles;
	}

	/**
	 * @param needles
	 *            the needles to set
	 */
	public void setNeedles(List<CactusNeedle> needles) {
		this.needles = needles;
	}	
}
