import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
	
	public HTMLParser(){
		
	}

	public Restaurant getRestaurantInfo(Document contactDoc) throws IOException {
		// TODO Auto-generated method stub
		Restaurant rest = new Restaurant();
		
		String addrRegex = "\\d{1,3}.?\\d{0,3}\\s[a-zA-Z]{2,30}\\s[a-zA-Z]{2,15}";//"\\d{1,}.*\\s[a-zA-Z]{2,30},\\s[a-zA-Z]{2,15}\\s?";//"\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
		String phoneRegex = "\\(?(\\d{3})\\).?-?(\\d{3})-(\\d{4})";
		String text = contactDoc.select(":matchesOwn((?i)"+phoneRegex+")").first().text();
		//System.out.println("Contact Info ="+text);
		String address = getRegex(addrRegex, text).group();
		String phone = getRegex(phoneRegex, text).group();
		//System.out.println("address = "+address);
		//System.out.println("phone = "+phone);
		rest.setAddress(address); 
		rest.setPhone(phone);
		//rest.setAddress(address);
		//contactDoc.select("")
		
		return rest;
	}

	private Matcher getRegex(String addrRegex, String text) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile(addrRegex);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()){
			return matcher;
		}
		return null;
	}

	public Document getDoc(String baseUrl) throws IOException {
		// TODO Auto-generated method stub
		Document doc = Jsoup.connect(baseUrl).get();
		//System.out.println(doc.title());
		
		return doc;
	}

	public String getMenuUrl(Document doc, String baseUrl) {
		// TODO Auto-generated method stub
		String menuUrl = baseUrl+doc.select("a:matchesOwn((?i)menu)").first().attr("href");
		//System.out.println("menuUrl = "+menuUrl);
		
		return menuUrl;
	}

	public String getContactUrl(Document doc, String baseUrl) {
		// TODO Auto-generated method stub
		String contactUrl = baseUrl+doc.select("a:matchesOwn((?i)contact us)").first().attr("href");
		//System.out.println("contactUrl = "+contactUrl);
		
		return contactUrl;
	}

	public String getMenuInfo(Document menuDoc, String baseUrl) throws IOException {
		// TODO Auto-generated method stub
		Element element = menuDoc.select(":matchesOwn((?i)appetizer)").first();
		if(element.tagName().equals("a")){
			getSubMenuInfo(element.parent(),baseUrl);
		}
		return null;
	}

	private void getSubMenuInfo(Element element, String baseUrl) throws IOException {
		// TODO Auto-generated method stub
		List<String> subMenus = new ArrayList<String>();
		String parentTag = null;
		if(!(parentTag = element.tagName()).equals("div")){
			element = element.parent();
		}
		for(Element e : element.select("a")){
			subMenus.add(e.attr("href"));
		}
		
		for(String s : subMenus){
			String url = baseUrl+s;
			Document subMenuDoc = getDoc(url);
			Elements ele = subMenuDoc.select(parentTag);
			for(Element e : ele){
				System.out.println(e.text());
			}
			
		}
		
	}

	

	

}
