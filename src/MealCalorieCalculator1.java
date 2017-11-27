import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Scanner;

public class MealCalorieCalculator {
    private int totalCalories = 0;
    private String menuItems = "";

    public static void main(String [] args){
        MealCalorieCalculator q = new MealCalorieCalculator();
        q.buildQuestionaire();
    }

    private void buildQuestionaire() {
        menuItems = scrapeChipotle();
        if(menuItems.length()>0) {
            askTypeOfMeal();
            itemsInMeal();
        }
    }
    private String scrapeChipotle(){ //Finds all the food items on Chipotle's Website
        String itemsInMenu = "";
        try {
            final Document document = Jsoup.connect("https://www.chipotle.com/menu").get();
            itemsInMenu = document.select("div.grid.grid--height.menu-ingredients__row h5").text();
        }

        catch (IOException ex) {
            System.out.println("For Some Reason We Cannot Connect to the Website"); //In case connection to website does not work
        }
        return itemsInMenu;
    }

    private void askTypeOfMeal(){
        Scanner s = new Scanner(System.in);
        System.out.println("Do you want a Burrito or Bowl?");
        String answer = s.nextLine();
        answer = answer.toLowerCase();

        if(answer.equals("burrito")){
            totalCalories+=300; //Tortilla is 300 Calories
        }
        else if(answer.equals("bowl")){
            totalCalories+=0;
        }
        else{
            System.out.println("Not Available");
        }
        drawLine();
    }

    private void itemsInMeal() {
        Scanner s = new Scanner(System.in);
        CalorieFinder calorieHelper = new CalorieFinder();
        int startIndex = 0;
        int endIndex = menuItems.indexOf(":");

        for(;  endIndex < menuItems.length() && endIndex != -1; ){
            String food = "";

             food = menuItems.substring(startIndex, endIndex);

             startIndex = endIndex;
             endIndex = menuItems.indexOf(":", endIndex + 1);

                if (food.length() >= 1 && food.charAt(0) == ':') {
                    food = food.replace(" Learn More (A Lot More)", "");
                    food = food.replace(": ", "");

                    System.out.println("Do you want "+ food + " (Enter Name of Food)");
                    String answer = s.nextLine();
                    answer = answer.toLowerCase();

                    try {
                        if (answer.equals("no") || answer.equals("")) {
                            int calorieOfThisItem = 0;
                            totalCalories += calorieOfThisItem;
                        }

                        else {
                            int calorieOfThisItem = calorieHelper.getCalories(answer);
                            totalCalories += calorieOfThisItem;
                        }
                    }
                    catch (NumberFormatException ex){
                        System.out.println("***Sorry, Food Entered Not Recognized***");
                    }
                    catch (StringIndexOutOfBoundsException ex){
                        System.out.println("***Sorry, Food Entered Not Recognized***");
                    }
                    drawLine();
                }
            }
            System.out.println("Your Total is: " +totalCalories +" calories"); 
        }
         private void drawLine(){
             System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }
