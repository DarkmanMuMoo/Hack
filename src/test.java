import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;

/**
 * 
 */

/**
 * @author mumoo
 *
 */
public class test {

	public static void main(String[] args) throws IOException {
		getTs("https://r4---sn-npo7en7k.googlevideo.com/videoplayback?requiressl=yes&shardbypass=yes&cmbypass=yes&id=4cc3f8ff6e24884f&itag=22&source=picasa&ip=58.11.59.34&ipbits=0&expire=1424715269&sparams=cmbypass,expire,id,ip,ipbits,itag,mm,ms,mv,pl,requiressl,shardbypass,source&signature=7793EFDE986B332B9359AFBF51712471DEC35480.0BA1C3AA989AB55A0B3F07597A8CEABDC3273526&key=cms1&cms_redirect=yes&mm=30&ms=nxu&mt=1422123439&mv=m&pl=22","test");
	}

	/**
	 * @param index
	 * @param line
	 */
	private static void getTs(String url, String fileName) throws IOException {
		File test = new File( fileName + ".mp4");
		if (!test.exists()) {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			System.out.println(test.getAbsolutePath());
			InputStream input = con.getInputStream();
			FileOutputStream out = new FileOutputStream(test);
			System.out.println("Processing");
			IOUtils.copyLarge(input, out);
			System.out.println("Done");
			input.close();
			out.close();
		}
	}
}
