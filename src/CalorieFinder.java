import org.jsoup.Jsoup; //Helps Scrape Website
import org.jsoup.nodes.Document;
import java.io.IOException;

public class CalorieFinder {
    private String allItemsOnMenu;

    public CalorieFinder(){
        allItemsOnMenu = initializeCalorie();
    }

    public String initializeCalorie() { //Gets Source Code from Website that has nutritional information of all items on Chipotle's menu
        String allItemsOnMenu = "";
        try {
            final Document document = Jsoup.connect("https://www.thrillist.com/health/nation/chipotle-calories-menu-nutrition-facts").get(); //Gets source code of website
            allItemsOnMenu = document.select("section.body-text.u-color--dark-gray.u-color--health-links.font--body.has-mobile-padding.is-standard li").text(); //Selects text on the website that is a food
        }

        catch (IOException ex) {
            System.out.println("***Cannot Connect To Website***");
        }

        finally {
            return allItemsOnMenu;
        }
    }

    public int getCalories(String food){
        String calorie = "";
        int caloriesInFood = 0;

        food = food.toLowerCase();

        int indexOfFood = allItemsOnMenu.indexOf(food);
        int indexOfSemicolon = allItemsOnMenu.indexOf("calories",indexOfFood );

        String subItem = allItemsOnMenu.substring(indexOfFood, indexOfSemicolon-1);

        calorie = subItem.replaceAll(food+": ", "");

        caloriesInFood = Integer.parseInt(calorie);

        return caloriesInFood;

    }
}
