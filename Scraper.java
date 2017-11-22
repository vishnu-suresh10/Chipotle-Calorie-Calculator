import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class Scraper {
    private String urlOfFood;
    private String chipsUrl;
    public static void main(String [] args){
        Scraper obj = new Scraper();

       int n = obj.getCalories("chips");
       System.out.println(n);
    }

    private void generateUrl() { //Gets Source Code from Website
        String allItemsOnMenu = null;
        try {
            final Document document = Jsoup.connect("https://www.thrillist.com/health/nation/chipotle-calories-menu-nutrition-facts").get();

            allItemsOnMenu = document.select("section.body-text.u-color--dark-gray.u-color--health-links.font--body.has-mobile-padding.is-standard li").text();
        }

        catch (IOException ex) {
            System.out.println("No");
        }

        finally {
            urlOfFood = allItemsOnMenu;
        }
    }

    public int getCalories(String food){
        generateUrl();
        String calorie = "";
        int CALORIES_IN_FOOD = 0;

        food = food.toLowerCase();

        int indexOfFood = urlOfFood.indexOf(food);
        int indexOfSemicolon = urlOfFood.indexOf("calories",indexOfFood );

        String subItem = urlOfFood.substring(indexOfFood, indexOfSemicolon-1);

        calorie = subItem.replaceAll(food+": ", "");

        CALORIES_IN_FOOD = Integer.parseInt(calorie);

        return CALORIES_IN_FOOD;

    }
}
