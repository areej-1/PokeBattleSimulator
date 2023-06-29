import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class PokemonDescription {

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
