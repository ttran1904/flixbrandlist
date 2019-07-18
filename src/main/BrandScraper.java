import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;


public class BrandScraper {

    private static final int MAX_DEPTH = 1; // Has to be 2
    private HashSet<String> links;
    private HashSet<String> brand;

    private BrandScraper() {
        links = new HashSet<String>();
    }

    private HashSet<String> getPageLinks(String URL, int depth) {

        if ((!links.contains(URL) && (depth <= MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                Document doc = Jsoup.connect(URL).get();
                linksOnPage = doc.select("a[href]");


                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }

        } else if (links.contains(URL)) {
            brand.add(URL);
        }

        return brand;
    }


    public static void main(String[] args) {
        new BrandScraper().getPageLinks("https://en.wikipedia.org/wiki/Lists_of_brands#Lists_of_brands", 0);
    }

}
