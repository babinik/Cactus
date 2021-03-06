package obfuscator;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import utils.net.DataFetcher;


public abstract class Minifier {

	public static String concatenate(File directory) throws IOException {
		StringBuilder sb = new StringBuilder();		
		for (File file : directory.listFiles(new ExtensionFilter(".js"))) {
			sb.append(Minifier.getFileContext(file));
		}	
		return sb.toString();
	}
	
	public static String concatenate(File directory, List<String> files) throws Exception{
		StringBuilder sb = new StringBuilder();
		
		for (String fileName : files) {
			
			if (Minifier.isUrl(fileName)) {
				sb.append( Minifier.getUrlContext(fileName) );				
			} else {
				File currentFile = new File(directory, fileName);				
				if (currentFile.isDirectory()) {
					sb.append( Minifier.concatenate(currentFile) );
				} else {
					sb.append( Minifier.getFileContext(currentFile) );
				}				
			}
			
		}
	
		return sb.toString();		
	}
	
	public static boolean isUrl(String path) {
		try {
			new URL(path);			
			return true;
		} catch (MalformedURLException e) {
			return false;	
		}
	}

	public static String getFileContext(File file) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line).append("\n");
		}
		rd.close();		
		sb.append("\n/*[  " + file.getAbsolutePath() + "  ]*/\n\n");
		return sb.toString();
	}
	
	public static String getUrlContext(String url) throws IOException {
		DataFetcher fetcher = new DataFetcher(url);		
		return fetcher.fetchData() +  ("\n/*[  " + url + "  ]*/\n\n");
	}
	
	public static String minify(InputStreamReader in) {
		String min = "";
		
		try {
			JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
                public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
                    if (line < 0) {
                        System.err.println("\n[WARNING] " + message);
                    } else {
                        System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
                    }
                }

                public void error(String message, String sourceName, int line, String lineSource, int lineOffset)
                {
                    if (line < 0) {
                        System.err.println("\n[ERROR] " + message);
                    } else {
                        System.err.println("\n[ERROR] source line: " + lineSource + "\n" + line + ':' + lineOffset + ':' + message);
                    }
                }

                public EvaluatorException runtimeError(
                        String message, String sourceName, int line, String lineSource, int lineOffset
                ) {
                    error(message, sourceName, line, lineSource, lineOffset);
                    return new EvaluatorException(message);
                }
            });
			
			CharArrayWriter cw = new CharArrayWriter();             
			compressor.compress(cw, -1, true, false, false, true);
			min = cw.toString();
			cw.close();            
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return min;
	}	
	
	public static String minifyCss(InputStreamReader in) throws IOException {
		CssCompressor compressor = new CssCompressor(in);
		CharArrayWriter cw = new CharArrayWriter();             
		compressor.compress(cw, -1);
        String min = cw.toString();
		cw.close();
		
		return min;
	}
}
