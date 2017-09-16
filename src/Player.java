import javax.swing.*;
import java.util.ArrayList;

public class Player {
    public ArrayList<CardName> onHand;
    public String name;

    Player(String name) {
        onHand = new ArrayList<CardName>();
        this.name = name;
    }
//getter and setter
    public String getName() {
        return name;
    }

    public ArrayList<CardName> getHand() {
        return onHand;
    }

    public CardName getCard(int num) {
        return onHand.get(num);
    }

    public void drawCard(CardName card) {
        onHand.add(card);
    }
// first turn gui
    public void startGame(InGame gameStarter)
    {
        String userInput = JOptionPane.showInputDialog(null,"(H)ardness \n(S)pecific Gravity" +
                " \n(C)leavage \n(A)bundance, \n(E)conomic Value",getName()+ ", " +
                "choose mode",JOptionPane.QUESTION_MESSAGE).toUpperCase();
        while(!(userInput.equals("H") ||userInput.equals("S")||userInput.equals("C")||userInput.equals("A")||userInput.equals("E")
        ) ) {
            JOptionPane.showMessageDialog(null,"Please type in the letter bracketted letter of prefered mode");
            userInput = JOptionPane.showInputDialog(null, "(H)ardness \n(S)pecific Gravity \n(C)leavage, \n(A)bundance \n(E)conomic Value", getName() + ", " +
                            "\n\nPut 99 to draw and skip turn",
                    JOptionPane.QUESTION_MESSAGE).toUpperCase();
        }
        gameStarter.setPickedGameMode(userInput);
    }

//  current player turn gui
    public void getTurn(InGame inGame)
    {
        int cardNum;
        boolean skipTurn = false;
        String toBeat;
        if(inGame.continueGame() && !(inGame.repeatTurn(this))) {
            if(inGame.getLastCard() instanceof TypeNormal){
            toBeat =
                    "\nLast card \nName: " + inGame.getLastCard().getName() + " " + "\nHardness: " + ((TypeNormal) inGame.getLastCard()).getHardness() + " " +"\nSpecific Gravity: " + ((TypeNormal) inGame.getLastCard()).getGravScoreValue() + " " +
                            "\nCleavage: " + ((TypeNormal) inGame.getLastCard()).getCleavage() + " " +"\nCrystal Abundance: " + ((TypeNormal) inGame.getLastCard()).getCrustalAbundance() + " " +"\nEconomic Value: " + ((TypeNormal) inGame.getLastCard()).getEcoValue() + "\n";}
            else{
                toBeat = "\nTo Beat:\n" +((TypeSuperTrump) inGame.getLastCard()).getName()+ "\n";
            }
        }
// current player gets to chose a trump mode if he repeats his turn
        else if(inGame.continueGame() && inGame.repeatTurn(this)) {
            JOptionPane.showMessageDialog(null,"Choose the trump mode");
            toBeat = "You may pick again";
            startGame(inGame); // begin game again
        }
        else {
            toBeat = "---\n";
            startGame(inGame);
        }
// will loop until an input was typed in
        while (!skipTurn) {
            String userInput = JOptionPane.showInputDialog(null, inGame.chosenMode() + "\n" + toBeat +"\n"
                            +showAllCard() +"\n\nType and enter 99 to draw a card and skip turn", getName() + ", choose card by number" ,
                    JOptionPane.QUESTION_MESSAGE);
            // option for current player to skip turn
            if (userInput.equals("99")) {
                drawCard(inGame.getDeckCard().cardDrawn());
                skipTurn = true; //proceed to next turn
            }
            // choosing card from userinput
            else {
                    cardNum = Integer.parseInt(userInput);
                    CardName playedCard = getCard(cardNum);
                    boolean valid = inGame.playCard(playedCard,this,2);
                    // if under effect of The Geologist supertrump
                    if(inGame.getPickedGameMode().equals("NEW"))
                    {   inGame.useCard(playedCard);
                        onHand.remove((cardNum));
                        startGame(inGame);
                        inGame.setPreviousPlayerTurn(this.getName());
                        skipTurn = true;
                    }
                    // if under effect of The Geophysicist supertrump or in specific gravity gamemode
                    else if(inGame.getPickedGameMode().equals("S"))
                    {
                        if(playerWin())
                        {
                            for(CardName cardInHand : onHand)
                            {
                                inGame.useCard(cardInHand);
                                onHand.remove(cardInHand);
                                inGame.setPreviousPlayerTurn(this.getName());
                            }
                            skipTurn = true;
                        }
                    }
                    else if (valid) {
                            inGame.useCard(playedCard);
                            onHand.remove(cardNum);
                            inGame.setPreviousPlayerTurn(this.getName());
                            skipTurn = true;
                        }

                }
        }
        // when cards on hand reaches zero, player won and leave the game
        if(onHand.size() == 0) {
            quit(inGame);
        }

    }

// to show all cards on hand for current player, sorted from 0
    public String showAllCard() {
        String allHand = "";
        int cardNo = 0;
        for(CardName cards : onHand) {

            String cardDescription = "";
            if(cards instanceof TypeNormal) {
                cardDescription = "No     Name      Hardness        Grav Score        Cleavage        Abu Score       Eco Value\n"+ cardNo + "   "+
                        "   " + cards.getName() + "      " +
                        "      " + ((TypeNormal) cards).getHardness() + "      " +
                        "      " + ((TypeNormal) cards).getGravScoreValue() + "      " +
                        "      " + ((TypeNormal) cards).getCleavage() + "      " +
                        "     " + ((TypeNormal) cards).getCrustalAbundance() + "      " +
                        "      " + ((TypeNormal) cards).getEcoValue()+"   \n";
            }
            else {
                cardDescription = "No     Name      Description\n"+ cardNo+ "   " + cards.getName()+ "      "+
                        ((TypeSuperTrump) cards).cardDescription()+"\n";
            }
            cardNo+=1;
            allHand += cardDescription;
        }
        return allHand;
    }


// instant win for due to Magnetite supertrump effect
    public boolean playerWin() {
        boolean valid = false;
        for(CardName cards: onHand) {
            if(cards.getName().equals("Magnetite")) {
                valid = true;
            }
        }
        return valid;
    }
// players left game when won
    public void quit(InGame inGame) {
        inGame.getPlayingPlayers().remove(this);
        JOptionPane.showMessageDialog(null,this.getName() + " has left");
    }

}
