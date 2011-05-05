package config.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Nikolai Babinski
 */
@Root(name = "cactus")
public class Cactus {

	@Element(name="js", required=false)
	private JavaScriptSection javaScript;

	@Element(name="css", required=false)
	private CssSection css;

	/**
	 * @return the javaScript
	 */
	public JavaScriptSection getJavaScript() {
		return javaScript;
	}

	/**
	 * @param javaScript the javaScript to set
	 */
	public void setJavaScript(JavaScriptSection javaScript) {
		this.javaScript = javaScript;
	}

	/**
	 * @return the css
	 */
	public CssSection getCss() {
		return css;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(CssSection css) {
		this.css = css;
	}	

	

}
