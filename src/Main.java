import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
public class Main {

    public static void main(String[] args)
    {
        // user choose how many players from 3-5
        Object[] options = {3,4,5};
        int joinPlayers = JOptionPane.showOptionDialog(null,"Choose", "Choose how many players",JOptionPane.YES_NO_CANCEL_OPTION
                ,JOptionPane.QUESTION_MESSAGE,null,options,options[0]) + 3;
        ArrayList<CardName> createCards = new ArrayList<CardName>();
        String[] properties;
        String string = "";
        //reading from the txt file
        Path file =
                Paths.get("C:\\Users\\Fadni\\Downloads\\CP2406A1\\CP2406A1\\src\\card.txt");
        // deciding properties and assigning them
        try
        {
            InputStream userInput = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader readFile = new BufferedReader(new InputStreamReader(userInput));
            readFile.readLine();
            while ((string = readFile.readLine()) != null){
                properties = string.split(",");
                createCards.add(new TypeNormal(properties[0],Float.valueOf(properties[1]),Float.valueOf(properties[2]),properties[3],properties[4],properties[5]));
            }
            //creating supertrump cards
            createCards.add(new TypeSuperTrump("The Petrologist"));
            createCards.add(new TypeSuperTrump("The Miner"));
            createCards.add(new TypeSuperTrump("The Gemmologist"));
            createCards.add(new TypeSuperTrump("The Mineralogist"));
            createCards.add(new TypeSuperTrump("The Geologist"));
            createCards.add(new TypeSuperTrump("The Geophysicist"));

        }
        catch(Exception e)
        {
            System.out.println("");
        }
        Deck gameDeck = new Deck(createCards);
        //starts game
        /*InGame mainGame = new InGame(joinPlayers,gameDeck);
        int counter = 0;
        // will loop as long as it has more than 1 players. last player wins
        while (mainGame.getPlayingPlayers().size()>1)
        {
            int playerno = counter%mainGame.getPlayingPlayers().size();
            if(mainGame.getDeckCard().getDeckContent().size()==0)
            {
                mainGame.returnCards();
            }
            else{
                    mainGame.getPlayingPlayers().get(playerno).getTurn(mainGame);
            }
            counter+=1;
        }*/
    }
}
