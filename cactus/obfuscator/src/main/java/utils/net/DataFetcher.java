package utils.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author Nikolai Babinski
 *
 */
public class DataFetcher {	
    	    
    protected URLConnection urlConnection;
    	    
    public URLConnection getUrlConnection() {
        return urlConnection;        
    }
    
    public void setUrlConnection(URLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    /**
     * @throws IOException
     */
    public DataFetcher(String connectionUrl) throws IOException {
        URL url = new URL(connectionUrl);
        urlConnection = url.openConnection();        
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 GTB7.0");        
    }
    
    public DataFetcher(URL url) throws IOException {
    	urlConnection = url.openConnection();        
    	urlConnection.setDoOutput(true);
    	urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 GTB7.0");        
    }
    
    /**
     * Simple not general implementation 
     * @return fetched data
     * @throws IOException
     */
    public String fetchData() throws IOException { 
    	String newLine = System.getProperty("line.separator");	
        Scanner in = new Scanner(urlConnection.getInputStream());
        StringBuilder sb = new StringBuilder();
        for (int n = 1; in.hasNextLine(); n++) {
            sb.append(in.nextLine() + newLine);
        }
        in.close();
        
        return sb.toString();
    }
    
    public InputStream getDataInputStream() throws IOException {
    	return urlConnection.getInputStream();
    }
}
