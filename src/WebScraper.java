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
        String fullText = "";
        try {
            String url = "https://bulbapedia.bulbagarden.net/wiki/"+name+"_(Pok%C3%A9mon)";
            Document document = Jsoup.connect(url).get();

            // Find the 'Biology' header
            Element biologyHeader = document.select("span#Biology").first().parent();

            // Find the next sibling that is a paragraph
            Element firstParagraph = biologyHeader.nextElementSibling();
            
            // Check if the first paragraph is not null
            if (firstParagraph != null) {
                String firstParagraphText = firstParagraph.text();

                // Find the next sibling that is also a paragraph (assumes the second paragraph is immediately after the first)
                Element secondParagraph = firstParagraph.nextElementSibling();
                
                // Check if the second paragraph is not null
                if (secondParagraph != null) {
                    String secondParagraphText = secondParagraph.text();

                    // Combine the first and second paragraphs
                    fullText = firstParagraphText + "\n\n" + secondParagraphText;

                    
                } else {
                    return (firstParagraphText);
                }
            } else {
                return ("First paragraph not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullText;
    }
}
