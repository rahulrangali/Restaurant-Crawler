import java.io.IOException;
import java.util.Scanner;

import org.jsoup.nodes.Document;

public class MainClass {
	//String baseUrl = "http://dinebombaygarden.com/";
	//String baseUrl = "http://allspicerestaurant.com/";
	static String baseUrl = "";
	String menuUrl = "";
	String contactsUrl = "";
	
	public MainClass(){
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("Please enter the url of Restaurant");
		Scanner sc = new Scanner(System.in);
		baseUrl = sc.nextLine();
		MainClass mc = new MainClass();
		mc.initialize();
		
	}

	private void initialize() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		HTMLParser hp = new HTMLParser();
		
		Document doc = hp.getDoc(baseUrl);
		
		menuUrl = hp.getMenuUrl(doc,baseUrl);
		contactsUrl = hp.getContactUrl(doc,baseUrl);
		Document contactDoc = hp.getDoc(contactsUrl);
		
		Document menuDoc = hp.getDoc(menuUrl);
		
		Restaurant rest = hp.getRestaurantInfo(contactDoc);
		
		String menuText = hp.getMenuInfo(menuDoc,baseUrl);
		
		//System.out.println("initialize = "+doc.title());
		rest.setName(doc.title());
		System.out.println("name = "+rest.getName()+", addr = "+rest.getAddress()+", phone = "+rest.getPhone());
		
		//String info = hp.getRestaurantInfo(baseUrl);
		
		
		
	}

}
