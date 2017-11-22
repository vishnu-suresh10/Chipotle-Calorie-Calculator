import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Scanner;

public class Questions {
    private String MEATS;
    private String RICEBEANSFAJITAS;
    private String TOPITOFF;
    private int TotalCalories = 0;

    public static void main(String [] args){
        Questions startOff = new Questions();
        startOff.getItems();
    }
    private void meatUrl() { //Finds All the available Meats on the Chipotle Website
        String meats = null;
        try {
            final Document document = Jsoup.connect("https://www.chipotle.com/menu").get();

            meats = document.select("div.grid.grid--height.menu-ingredients__row.menu-grid-fillings h5").text();
        }

        catch (IOException ex) {
            System.out.println("No");
        }

        finally {
            MEATS = meats;
        }
    }

    private void ricebeansfajitasUrl() { //Finds all the rice, beans on Chipotle Website
        String ricebeansfajita = null;
        try {
            final Document document = Jsoup.connect("https://www.chipotle.com/menu").get();

            ricebeansfajita = document.select("div.grid.grid--height.menu-ingredients__row h5").text();
            ricebeansfajita = ricebeansfajita.replace("Steak Marinated Grilled Perfectly Charred Learn More (A Lot More): Carnitas Responsibly Raised® Seasoned Braised Learn More (A Lot More): Chicken Responsibly Raised® Marinated Grilled Learn More (A Lot More): Barbacoa seasoned Braised Learn More (A Lot More): Sofritas Organic Braised Learn More (A Lot More): ", "");
            ricebeansfajita = ricebeansfajita.replace(": FRESH TOMATO SALSA (Mild) Diced Tossed Learn More (A Lot More): TOMATILLO RED-CHILI SALSA (Hot) Simmered Seasoned Learn More (A Lot More): Sour Cream Fermented Learn More (A Lot More): TOMATILLO GREEN-CHILI SALSA (Medium Hot) Roasted Seasoned Learn More (A Lot More): ROASTED CHILI-CORN SALSA (Medium) Tossed Seasoned Romaine Lettuce Crisp Sliced Learn More (A Lot More): Guacamole Hand Sliced Mashed Learn More (A Lot More): monterey jack Cheese Shredded Learn More (A Lot More):", "");
        }

        catch (IOException ex) {
            System.out.println("No");
        }

        finally {
            RICEBEANSFAJITAS = ricebeansfajita;
        }
    }

    private void TopItOffUrl() { //Finds all toppings on Chipotle Website
        String topitoff = null;
        try {
            final Document document = Jsoup.connect("https://www.chipotle.com/menu").get();

            topitoff = document.select("div.grid.grid--height.menu-ingredients__row h5").text();
            topitoff = topitoff.replace("Steak Marinated Grilled Perfectly Charred Learn More (A Lot More): Carnitas Responsibly Raised® Seasoned Braised Learn More (A Lot More): Chicken Responsibly Raised® Marinated Grilled Learn More (A Lot More): Barbacoa seasoned Braised Learn More (A Lot More): Sofritas Organic Braised Learn More (A Lot More): Cilantro-Lime Brown Rice Steamed Seasoned Garnished Learn More (A Lot More): Cilantro-Lime White Rice Steamed Seasoned Garnished Learn More (A Lot More): Black Beans Simmered Seasoned Learn More (A Lot More): Pinto Beans Simmered Seasoned Learn More (A Lot More): Fajita Veggies Caramelized Seasoned GENUINE QUESO Simmered seasoned Learn More (A Lot More): ", "");
        }

        catch (IOException ex) {
            System.out.println("No");
        }

        finally {
            TOPITOFF = topitoff;
        }
    }

    private void TypeOfMeal(){
        Scanner s = new Scanner(System.in);
        System.out.println("Do you want a Burrito or Bowl?");
        String answer = s.nextLine();
        answer = answer.toLowerCase();

        if(answer.equals("burrito")){
            TotalCalories+=300; //Tortilla is 300 Calories
        }
        else if(answer.equals("bowl")){
            TotalCalories+=0;
        }
        else{
            System.out.println("Not Available");
        }
        Line();
    }

    private void MeatQuestions(){
        meatUrl();
        Scanner s = new Scanner(System.in);
        Scraper obj = new Scraper();
        String Questions = "Pick a Meat: ";
        String[] words=MEATS.split(": "); //Finds all examples of meat on the website

        for(int i = 0; i <= 4 ; i++)
        {
            for(int k = 0; k < words[i].length(); k++) {
                if (words[i].charAt(k) == ' ') {
                    Questions += words[i].substring(0, k) + " ";
                    break;
                }
            }
        }

        Questions += " or Skip";
        System.out.println(Questions);
        String answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("steak") || answer.equals("carnitas") || answer.equals("chicken") || answer.equals("barbacoa")|| answer.equals("sofritas")) {
            int k = obj.getCalories(answer);
            TotalCalories+=k;
        }
        else if(answer.equals("skip")){
            TotalCalories+=0;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }
        Line();
    }

    private void RiceBeanFajitaQuestions(){
        ricebeansfajitasUrl();
        Scanner s = new Scanner(System.in);
        Scraper obj = new Scraper();
        String[] rice=RICEBEANSFAJITAS.split("Steamed ");

        for(int i = 0; i <= 1 ; i++)
        {
            rice[i] = rice[i].replace("Seasoned Garnished Learn More (A Lot More): ", "");
            rice[i] = rice[i].replace("Cilantro-Lime", "");
            rice[i] = rice[i].replace("Rice ", "");
        }

        System.out.println("Pick a type of Rice:" + rice[0]+" or "+rice[1]+ ", or Skip");
        String answer = s.nextLine();
        answer = answer.toLowerCase();
        answer += " rice";

        if(answer.equals("brown rice") || answer.equals("white rice")) {
            int k = obj.getCalories(answer);
            TotalCalories+=k;
        }
        else if(answer.equals("skip rice")){
            TotalCalories+=0;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }

        Line();

        RICEBEANSFAJITAS = RICEBEANSFAJITAS.replace("Cilantro-Lime Brown Rice Steamed Seasoned Garnished Learn More (A Lot More): Cilantro-Lime White Rice Steamed Seasoned Garnished Learn More (A Lot More): ", "");
        String[] beans=RICEBEANSFAJITAS.split("Simmered ");
        for(int i = 0; i <= 1 ; i++)
        {
            beans[i] = beans[i].replace("Seasoned Learn More (A Lot More): ", "");
            beans[i] = beans[i].replace("Simmered Seasoned Learn More (A Lot More): ", "");
            beans[i] = beans[i].replace("Fajita Veggies Caramelized Seasoned GENUINE QUESO Simmered seasoned Learn More (A Lot More)", "");
            beans[i] = beans[i].replace("Beans ", "");
        }
        System.out.println("Pick a type of Beans: " + beans[0]+"or "+beans[1]+ ", or Skip");
        answer = s.nextLine();
        answer += " beans";
        answer = answer.toLowerCase();
        if(answer.equals("black beans") || answer.equals("pinto beans")) {
            int k = obj.getCalories(answer);
            TotalCalories+=k;
        }
        else if(answer.equals("skip beans")){
            TotalCalories+=0;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }

        Line();

        System.out.println("Would you Like Fajitas? (yes or no)");
        answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("yes")) {
            int k = obj.getCalories("fajita veggies");
            TotalCalories+=k;
        }
        else if(answer.equals("no")){
            TotalCalories+=0;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }
        Line();
    }

    private void TopItOffQuestions(){
        TopItOffUrl();
        Scanner s = new Scanner(System.in);
        Scraper obj = new Scraper();
        String[] toppings=TOPITOFF.split(": ");
        String[] others=TOPITOFF.split("Tossed ");

        for(int i = 0; i <= 6 ; i++)
        {
            toppings[i] = toppings[i].replace("Learn More (A Lot More)", "");
            toppings[i] = toppings[i].replace("Tossed Seasoned Romaine Lettuce Crisp Sliced ", "");
        }

        for(int i = 0; i <= 2 ; i++)
        {
            others[i] = others[i].replace(" Learn More (A Lot More): Guacamole Hand Sliced Mashed Learn More (A Lot More): monterey jack Cheese Shredded Learn More (A Lot More):", "");
        }

        System.out.println("Pick a Type of Salsa: "+toppings[0]+toppings[1]+toppings[3]+toppings[4]+", or Skip");
        String answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("tomato")) {
            int k = obj.getCalories("fresh tomato salsa");
            TotalCalories+=k;
        }
        else if(answer.equals("red")){
            int k = obj.getCalories("tomatillo red-chili salsa");
            TotalCalories+=k;
        }
        else if(answer.equals("green")){
            int k = obj.getCalories("tomatillo red-chili salsa");
            TotalCalories+=k;
        }
        else if(answer.equals("corn")){
            int k = obj.getCalories("roasted chili-corn salsa");
            TotalCalories+=k;
        }
        else if(answer.equals("skip")){
            TotalCalories+=0;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }

        Line();

        toppings[2] = toppings[2].replace(" Fermented", "");
        System.out.println("Do you want " +toppings[2] + " (yes or no)");
        answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("yes")) {
            int k = obj.getCalories("sour cream");
            TotalCalories+=k;
        }
        else if(answer.equals("no")){
            int k = 0;
            TotalCalories+=k;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }
        Line();

        toppings[5] = toppings[5].replace("Hand Sliced Mashed", "");
        System.out.println("Do you want " +toppings[5] + " (yes or no)");
        answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("yes")) {
            int k = obj.getCalories("guacamole");
            TotalCalories+=k;
        }
        else if(answer.equals("no")){
            int k = 0;
            TotalCalories+=k;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }
        Line();

        System.out.println("Do you want " +toppings[6] + " (yes or no)");
        answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("yes")) {
            int k = obj.getCalories("cheese");
            TotalCalories+=k;
        }
        else if(answer.equals("no")){
            int k = 0;
            TotalCalories+=k;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }
        Line();

        System.out.println("Do you Want: "+others[2]);
        answer = s.nextLine();
        answer = answer.toLowerCase();
        if(answer.equals("yes")) {
            int k = obj.getCalories("romaine lettuce");
            TotalCalories+=k;
        }
        else if(answer.equals("no")){
            int k = 0;
            TotalCalories+=k;
        }
        else{
            System.out.println("NOT AVAILABLE");
        }

        Line();

        System.out.println("Your Total Calories is: "+TotalCalories);

    }

    private void getItems(){
        Questions q = new Questions();
        q.TypeOfMeal();
        q.MeatQuestions();
        q.RiceBeanFajitaQuestions();
        q.TopItOffQuestions();
    }

    private void Line(){
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }
}
