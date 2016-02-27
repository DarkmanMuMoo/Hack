package com.mumoo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;

/**
 * @author mumoo
 *
 */
public class main {

	private ExecutorService executor = Executors.newFixedThreadPool(20);

	private List<String> error = new ArrayList<String>();
	private AtomicInteger progress = new AtomicInteger(0);

	private static int LIMIT = 708;

	/**
	 * main constructor.
	 * 
	 * @throws IOException
	 * 
	 */
	public static void main(String[] args) throws IOException {
		main hack = new main();

		hack.loadFromURL(
				"https://cdn10-dooneetv.wisstream.com/series/cab1f6fd73th480/cab1f6fd73th480",
				"8qvUQWye2Gnui7LF_cZ3Pw", "1422300700", LIMIT, "HustleEP4-");

	}

	private void loadFromURL(String url, String m, String e, int limit,
			String fileName) {
		int buffer = 10;
		String fullUrl = url + "{0}.ts?m=" + m + "&e=" + e;
		List<TsRequest> requests = new ArrayList<TsRequest>(buffer);
		for (int i = 0; i < limit; i++) {
			System.out.println("index = " + i);
			if (requests.size() == buffer) {
				executor.execute(new main.GetTs(requests));
				requests = new ArrayList<TsRequest>(buffer);
				requests.add(new TsRequest(MessageFormat.format(fullUrl, i),
						fileName + String.format("%04d", i)));
			} else {
				requests.add(new TsRequest(MessageFormat.format(fullUrl, i),
						fileName + String.format("%04d", i)));
			}

		}
		// fetch last one
		executor.execute(new main.GetTs(requests));

		executor.shutdown();
		// Wait until all threads are finish
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Finished all threads");
		for (String file : error) {
			System.out.println("Error File = " + file);
		}
	}

	class GetTs implements Runnable {

		private List<TsRequest> requests;

		public GetTs(List<TsRequest> requests) {
			super();
			this.requests = requests;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */

		public void run() {
			for (TsRequest request : requests) {
				try {
					getTs(request.getUrl(), request.getFileName());
					System.out.println("Progress " + progress.incrementAndGet()
							+ "/" + LIMIT);
				} catch (IOException e) {
					System.out.println("Error  'GET' request to URL : "
							+ request.getUrl());
					error.add(request.getFileName());
					e.printStackTrace();
				}
			}

		}

		/**
		 * @param index
		 * @param line
		 */
		private void getTs(String url, String fileName) throws IOException {
			File test = new File("temp\\" + fileName + ".ts");
			if (!test.exists()) {
				URL obj = new URL(url);
				HttpsURLConnection con = (HttpsURLConnection) obj
						.openConnection();

				con.setRequestMethod("GET");

				int responseCode = con.getResponseCode();
				if (responseCode == 404) {
					System.out.println("\nNot Found  URL : " + url);
					System.out.println("Response Code : " + responseCode);
				} else {
					System.out.println("\nSending 'GET' request to URL : "
							+ url);
					System.out.println("Response Code : " + responseCode);

					System.out.println(test.getAbsolutePath());
					InputStream input = con.getInputStream();
					FileOutputStream out = new FileOutputStream(test);
					IOUtils.copy(input, out);
					input.close();
					out.close();
				}
			}
		}
	}
}
