package com.mumoo;

/**
 * @author mumoo
 *
 */
public class TsRequest {

	private String url;
	private String fileName;

	public TsRequest(String url, String fileName) {
		super();
		this.url = url;
		this.fileName = fileName;
	}

	/**
	 * Return a url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * set a url.
	 * 
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Return a fileName.
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * set a fileName.
	 * 
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
