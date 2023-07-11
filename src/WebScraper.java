import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {
    public static String getGender(String name) {
    	try {
    		Document document = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/"+name+"_(game)").get();
            Elements thElements = document.select("th");

            for (Element th : thElements) {
                if (th.text().equals("Gender")) {
                    Element genderElement = th.nextElementSibling();
                    String gender = genderElement.text();
                    return gender ;               
                }
            }
    		} catch (IOException e) {
    		  // handle the exception
    		  try {
    			  Document document = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/"+name).get();
    	            Elements thElements = document.select("th");

    	            for (Element th : thElements) {
    	                if (th.text().equals("Gender")) {
    	                    Element genderElement = th.nextElementSibling();
    	                    String gender = genderElement.text();
    	                    return gender;
    	                }
    	            }
    		  } catch (IOException f) {
    			  e.printStackTrace();
    		  }
    		}
    		return "oops, didn't work";
  
     
    }
    public static String readDescription(String name) throws IOException{
    	String url = "https://www.pokemon.com/us/pokedex/"+name;

        // Fetch and parse the HTML using Jsoup
        Document document = Jsoup.connect(url).get();

        // Look for the specific element using its class name
        Element element = document.selectFirst(".version-x.active");

        // Get the text content of the element
        String content = element.text();

        
        Element e = document.selectFirst(".version-y");

        // Get the text content of the element
        content += "\n" + e.text();

        // Print the content
        return content;
    }
}
