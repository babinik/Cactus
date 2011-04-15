package config.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Nikolai Babinski
 */
@Root(name="cactus")
public class Cactus {

	@ElementList(name="needles", required=false, entry="needle")
	private List<CactusNeedle> needles;
	
//	@ElementList(name="exclude", entry="file", required=false)
//	private Set<String> exclusions;

	
	/**
	 * @return the needles
	 */
	public List<CactusNeedle> getNeedles() {
		return needles;
	}

	/**
	 * @param needles the needles to set
	 */
	public void setNeedles(List<CactusNeedle> needles) {
		this.needles = needles;
	}

//	/**
//	 * @return the exclusions
//	 */
//	public Set<String> getExclusions() {
//		return exclusions;
//	}
//
//	/**
//	 * @param exclusions the exclusions to set
//	 */
//	public void setExclusions(Set<String> exclusions) {
//		this.exclusions = exclusions;
//	}	
}
