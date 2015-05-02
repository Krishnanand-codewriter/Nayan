package cognizant.nayan.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cognizant.nayan.commons.Commons;
import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.serialized.ImagesState;

/**
 * @author Krishnanand
 *
 */
public class URLExtractor {
	private static HashMap<String, String> sUrls = new HashMap<>();
	private NayanProperties mNayanProp = new NayanProperties();
	private Document mDoc;
	private Elements mTags;
	private String mBaseUrl;
	private ArrayList<String> mNamesList = new ArrayList<>();
	private static int sSimilarNamecount=1; //To assign unique names to sUrls

	public URLExtractor(String mBaseUrl) {
		this.mBaseUrl = mBaseUrl;
	}
	public void extractURLs(ImagesState state) {
		setUp();
		getAnchorElements();
		getUrls();
		state.setUrlsMap(sUrls);
	}

	private void setUp(){
		try{
			String username = mNayanProp.getProxyUser();
			String password = mNayanProp.getProxyPassword();
			String login = username + ":" + password;
			String base64login = new String(Base64.encodeBase64(login.getBytes()));
			mDoc = Jsoup
					.connect(mBaseUrl)
					.header("Authorization", "Basic " + base64login)
					.timeout(10*1000)
					.get();
		}catch(Exception e){
			String proxyHost = mNayanProp.getProxyHost();
			String proxyPort = mNayanProp.getProxyPort();
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);
			String username = mNayanProp.getProxyUser();
			String password = mNayanProp.getProxyPassword();
			String login = username + ":" + password;
			String base64login = new String(Base64.encodeBase64(login.getBytes()));
			try {
				mDoc = Jsoup
						.connect(mBaseUrl)
						.header("Authorization", "Basic " + base64login)
						.timeout(10*1000)
						.get();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	private void getAnchorElements(){
		mTags = mDoc.getElementsByTag("a");
	}

	private void getUrls(){
		for(Element url : mTags) {
			if(url.hasAttr("href")) {
				String href = url.attr("href");
				if(isHrefValid(href)){
					String trimUrl = mBaseUrl.substring(0, mBaseUrl.indexOf(".com")+4);
					String validUrl = trimUrl+href;
					String eleName = getText(url);
					sUrls.put(validUrl, eleName);
				}
			}
		}
	}

	private boolean isHrefValid(String href) {
		boolean validHref = false;
		boolean allLinksIncluded = Commons.isAllLinksIncluded();
		String brandname = mNayanProp.getBrandName();
		String brandname1 = mNayanProp.getBrandName1();
		if(!(href.equals("")) && !(href.equals("#") || href.equals("##")) && !(href.equals("/"))&& !(href.startsWith("http")) && !(href.startsWith("javascript"))){
			if(!allLinksIncluded){
				if(href.contains(brandname.toLowerCase()) || href.contains(brandname1.toLowerCase())){
					validHref = true;
				}else if(href.contains("terms") || href.contains("faq")){
					validHref = true;
				}else{
					validHref = false;
				}
			}else{
				validHref = true;
			}

		}else{
			validHref = false;
		}

		return validHref;
	}

	private String getText(Element ele){
		String urlText = null;
		String eleText = ele.text();
		String uniqueText = getUniqueText(eleText);
		if(uniqueText.equals("")){
			String eleHref = ele.attr("href");
			urlText = eleHref.substring(eleHref.lastIndexOf("/"), eleHref.length());
			String formatText = "";
			if(urlText.contains("<") || urlText.contains(">") || urlText.contains(":") || urlText.contains("/") 
					|| urlText.contains("\\") || urlText.contains("|") || urlText.contains("?") || urlText.contains("*")){
				formatText = urlText.replace("<", "").replace(">", "").replace(":", "").replace("/", "")
						.replace("\\", "").replace("|", "").replace("?", "").replace("*", "").replace(".", "");
			}else{
				formatText = urlText;
			}
			return formatText;
		}else{
			return uniqueText;
		}
	}

	private String getUniqueText(String paramEleText) {
		String tempText = null;
		if(paramEleText!="") {
			if(mNamesList.contains(paramEleText)) {
				tempText = paramEleText+sSimilarNamecount;
				sSimilarNamecount++;
				return tempText;
			} else {
				mNamesList.add(paramEleText);
				return paramEleText;
			}
		} else {
			return paramEleText;
		}
	}

}
