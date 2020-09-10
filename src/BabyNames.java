import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BabyNames {

    //class constants for open area in graphics (Do NOT change)
    private static final Integer OPEN_AREA_WIDTH = 780;
    private static final Integer OPEN_AREA_HEIGHT = 500;

    //prompt msg class constants (Do NOT change)
    private static final String MESSAGE_PREFIX = "This program allows you to search through the\n" +
            "data from the Social Security Administration\n" +
            "to see how popular a particular name has been\n" +
            "since ";

    // class constant for meaning file (Do NOT change)
    private static final String MEANING_FILENAME = "meanings.txt";

    // class constant for name file (Change the value only. Do NOT change the names of the constant)
    // Test with both "names.txt" and "names2.txt" (Before submission, change back to "names.txt")
    private static final String NAME_FILENAME = "names2.txt";

    // Other class constants (Change the value only. Do NOT change the names of the constants)
    private static final Integer STARTING_YEAR = 1863; // change the value according to spec
    private static final Integer DECADE_WIDTH = 50; // change the value according to spec
    private static final Integer LEGEND_HEIGHT = 20; // change the value according to spec

    //class variables for name and gender which are userInput
    private static String userInputName = "";
    private static String userInputGender = "";

    //the main method
    public static void main(String[] args) throws FileNotFoundException {

        //prompt the user and get name and gender
        getUserInput();

        //output text info and graphics info
        outputInfo();
    }

    //prompts the user and gets the name and gender
    //saves to userInputName and userInputGender class variables
    private static void getUserInput(){
        System.out.println(MESSAGE_PREFIX + STARTING_YEAR);
        System.out.println();
        Scanner console = new Scanner(System.in);
        System.out.print("Name: ");
        userInputName = console.next();
        System.out.print("Gender (M or F): ");
        userInputGender = console.next();
    }

    //output text info
    //if found also output graphic info
    private static void outputInfo() throws FileNotFoundException {
        String ranking = findLine(NAME_FILENAME);
        if(ranking.isEmpty()) {
            //not found
            System.out.println("\"" + userInputName + "\" not found.");
            return;
        }

        // output text info
        String meaning = findLine(MEANING_FILENAME);
        System.out.println(ranking);
        System.out.println(meaning);

        // output graphics info
        outputGraphics(ranking, meaning);
    }

    //finds the line from the given file name based on the user inputted name and gender
    private static String findLine(String fileName) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File(fileName));
        return findByNameGender(fileScan);
    }

    //finds a string from scanner that contains the name and the gender
    //if not found then returns empty string
    private static String findByNameGender(Scanner input){
        while(input.hasNextLine()){
            String line = input.nextLine();
            Scanner lineScan = new Scanner(line);
            String name = lineScan.next(); //safe to assume there is name
            String gender = lineScan.next(); //safe to assume there is gender
            if(userInputName.compareToIgnoreCase(name) == 0 &&
                    userInputGender.compareToIgnoreCase(gender) == 0 )
                return line;
        }
        return ""; //not found thus return an empty string
    }

    //draw all graphics given ranking and meaning string
    private static void outputGraphics(String ranking, String meaning){
        //set drawing panel
        DrawingPanel panel = new DrawingPanel(OPEN_AREA_WIDTH,OPEN_AREA_HEIGHT + LEGEND_HEIGHT * 2);
        panel.setBackground(Color.WHITE);
        Graphics g = panel.getGraphics();

        //draw fixed graphics
        drawFixedGraphics(g);

        //draw user data graphics
        drawDataGraphics(g, ranking, meaning);
    }

    //draw only fixed graphics
    private static void drawFixedGraphics(Graphics g){
        //top and bottom legend rectangles
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, OPEN_AREA_WIDTH, LEGEND_HEIGHT); //top
        g.fillRect(0, OPEN_AREA_HEIGHT + LEGEND_HEIGHT, OPEN_AREA_WIDTH, LEGEND_HEIGHT);

        //top and bottom black line
        g.setColor(Color.BLACK);
        g.drawLine(0, LEGEND_HEIGHT, OPEN_AREA_WIDTH, LEGEND_HEIGHT);
        g.drawLine(0, OPEN_AREA_HEIGHT + LEGEND_HEIGHT, OPEN_AREA_WIDTH, OPEN_AREA_HEIGHT + LEGEND_HEIGHT);
    }

    //draws graphics for user data
    private static void drawDataGraphics(Graphics g, String ranking, String meaning){
        //draw top legend meaning string
        g.setColor(Color.BLACK);
        g.drawString(meaning, 0, 16);

        //draw bar data (year string, rank bar and rank string)
        drawBarData(g, ranking);
    }

    //draw bar related data
    private static void drawBarData(Graphics g, String ranking){
        Scanner strScan = new Scanner(ranking);
        strScan.next(); // get rid of name
        strScan.next(); // get rid of gender
        int i = 0;
        while(strScan.hasNext()){
            int rank = Integer.parseInt(strScan.next());
            drawRank(g, rank, i);
            drawYear(g, i);
            i++;
        }
    }

    //draw year legend
    private static void drawYear(Graphics g, int index){
        g.drawString(""+ (STARTING_YEAR + index * 10) , index * DECADE_WIDTH, OPEN_AREA_HEIGHT + LEGEND_HEIGHT * 2 - 8);
    }

    //draw rank bar and rank string
    private static void drawRank(Graphics g, int rank, int index){
        //draw rank green bar
        if(rank != 0){
            g.setColor(Color.GREEN);
            g.fillRect(index * DECADE_WIDTH , rank / 2 + LEGEND_HEIGHT, DECADE_WIDTH/2,
                    OPEN_AREA_HEIGHT + LEGEND_HEIGHT * 2 - rank / 2 - LEGEND_HEIGHT - LEGEND_HEIGHT);
        }
        //draw rank black string
        g.setColor(Color.BLACK);
        int rankPrime = rank;
        if(rank == 0) rankPrime = 1000;
        g.drawString(""+rank, index * DECADE_WIDTH, rankPrime / 2 + LEGEND_HEIGHT);
    }
}
