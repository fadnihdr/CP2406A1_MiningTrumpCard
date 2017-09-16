import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainGUI extends JFrame implements ActionListener
{
    JPanel textHelpPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel passPanel = new JPanel();
    JPanel selectionPanel = new JPanel(new GridLayout(2,8));
    JPanel drawPanel = new JPanel();
    JLabel textHelp = new JLabel("");
    JTextField playerNameInput = new JTextField(15);
    ArrayList<String> playersNameList = new ArrayList<>();
    ArrayList<JButton> cardToShow = new ArrayList<>();
    int noOfPlayer = 0;
    int playerEnterNameCount = 1;
    int playerTurn = 0;
    int passCounter = 0;
    Deck gameDeck;
    Player currentPlayer;
    InGame gameprogress;

    public MainGUI()
    {
        super("The game");
        setSize(1080,720);
        setLayout(new BorderLayout());
        add(textHelpPanel,BorderLayout.NORTH);
        add(mainPanel,BorderLayout.CENTER);
        add(passPanel,BorderLayout.EAST);
        add(selectionPanel,BorderLayout.SOUTH);
        selectionPanel.setPreferredSize(new Dimension(1080,400));
        add(drawPanel,BorderLayout.WEST);
        textHelpPanel.add(textHelp);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        selectNoOfPlayer();
        setVisible(true);

    }

    public void selectNoOfPlayer()
    {
        textHelp.setText("Enter No of player");
        mainPanel.removeAll();
        JButton input3Player = new JButton("3");
        JButton input4Player = new JButton("4");
        JButton input5Player = new JButton("5");
        input3Player.addActionListener(this);
        input4Player.addActionListener(this);
        input5Player.addActionListener(this);
        mainPanel.add(input3Player);
        mainPanel.add(input4Player);
        mainPanel.add(input5Player);

    }

    public void enterPlayerName()
    {
        textHelp.setText("Enter player "+ playerEnterNameCount+" name");
        mainPanel.removeAll();
        mainPanel.add(playerNameInput);
        JButton enterPlayerName = new JButton("Enter");
        enterPlayerName.addActionListener(this);
        mainPanel.add(enterPlayerName);
    }

    public void readCard()
    {
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
        gameDeck = new Deck(createCards);
    }

    public void gameStarter(Deck gameDeck)
    {
        gameprogress = new InGame(playersNameList, gameDeck);
    }

    public void pickMode()
    {
        currentPlayer = gameprogress.getPlayer(playerTurn%gameprogress.getPlayingPlayers().size());
        textHelp.setText(currentPlayer.getName()+ " choose the trump card you wanted");
        mainPanel.removeAll();
        selectionPanel.removeAll();
        mainPanel.add(new JLabel("Choose the mode"));
        JButton hardnessButton = new JButton("Hardness");
        JButton specificGravityButton = new JButton("Specific Gravity");
        JButton cleavageButton = new JButton("Cleavage");
        JButton crustalAbundanceButton = new JButton("Crustal Abundance");
        JButton economicValueButton = new JButton("Economic Value");
        hardnessButton.addActionListener(this);
        specificGravityButton.addActionListener(this);
        cleavageButton.addActionListener(this);
        crustalAbundanceButton.addActionListener(this);
        economicValueButton.addActionListener(this);
        selectionPanel.add(hardnessButton);
        selectionPanel.add(specificGravityButton);
        selectionPanel.add(cleavageButton);
        selectionPanel.add(crustalAbundanceButton);
        selectionPanel.add(economicValueButton);
    }

    public void pickCard()
    {
        mainPanel.removeAll();
        passPanel.removeAll();
        if(gameprogress.getGraveyard().size()>0)
        {
            ImageIcon imageLast = new ImageIcon("C:\\Users\\Fadni\\Downloads\\CP2406A1\\CP2406A1\\src\\image\\"+gameprogress.getLastCard().getName()+".jpg");
            JButton cardButton = new JButton(new ImageIcon(imageLast.getImage().getScaledInstance(140,200,Image.SCALE_SMOOTH)));
            cardButton.setSize(140,260);
            mainPanel.add(cardButton);
        }
        selectionPanel.removeAll();
        cardToShow.clear();
        JButton passButton = new JButton("Pass");
        passButton.addActionListener(this);
        passPanel.add(passButton);
        currentPlayer = gameprogress.getPlayer(playerTurn%gameprogress.getPlayingPlayers().size());
        textHelp.setText(currentPlayer.getName()+ " choose the card you want to play. Current trump mode = "+gameprogress.getTrumpMode());
        for(CardName card : currentPlayer.getHand())
        {   System.out.println("images\\"+card.getName()+".jpg");
            ImageIcon image = new ImageIcon("C:\\Users\\Fadni\\Downloads\\+--------------------------------------------------------------------------------------------------------------------------" +
                    "................................................................................................................................................................................................................................................................................................................................... CP2406A1\\CP2406A1\\src\\image\\"+card.getName()+".jpg");
            JButton cardButton = new JButton(new ImageIcon(image.getImage().getScaledInstance(140,200,Image.SCALE_SMOOTH)));
            cardButton.setSize(140,260);
            cardToShow.add(cardButton);
        }
        for(JButton cardBtn : cardToShow)
        {
            cardBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton cardChosen = (JButton)e.getSource();
                    CardName cardplayed = currentPlayer.getHand().get(cardToShow.indexOf(cardChosen));
                    boolean playAllowed = gameprogress.playCard(cardplayed,currentPlayer,passCounter);
                    if(playAllowed)
                    {
                        if(gameprogress.getPickedGameMode().equals("NEW"))
                        {
                            gameprogress.useCard(cardplayed);
                            currentPlayer.getHand().remove(cardplayed);
                            if(checkPlayerWin(currentPlayer))
                            {
                                gameprogress.getPlayingPlayers().remove(currentPlayer);
                                passCounter=0;
                                pickMode();
                            }
                            else
                            {
                                gameprogress.setPreviousPlayerTurn(currentPlayer.getName());
                                passCounter=0;
                                pickMode();
                            }
                        }
                        else if(gameprogress.getPickedGameMode().equals("S"))
                        {
                            if(currentPlayer.playerWin())
                            {
                                for(CardName cards: currentPlayer.getHand())
                                {
                                    gameprogress.useCard(cards);
                                    currentPlayer.getHand().remove(cards);
                                    gameprogress.setPreviousPlayerTurn(currentPlayer.getName());
                                }
                                if(checkPlayerWin(currentPlayer))
                                {
                                    gameprogress.getPlayingPlayers().remove(currentPlayer);
                                }
                            }
                            else
                            {
                                gameprogress.useCard(cardplayed);
                                currentPlayer.getHand().remove(cardplayed);
                                gameprogress.setPreviousPlayerTurn(currentPlayer.getName());
                                if(checkPlayerWin(currentPlayer))
                                {
                                    gameprogress.getPlayingPlayers().remove(currentPlayer);
                                }
                                else
                                {
                                    playerTurn++;

                                }
                            }
                            passCounter=0;
                            pickCard();

                        }
                        else
                        {
                            gameprogress.useCard(cardplayed);
                            currentPlayer.getHand().remove(cardplayed);
                            gameprogress.setPreviousPlayerTurn(currentPlayer.getName());
                            if(checkPlayerWin(currentPlayer))
                            {
                                gameprogress.getPlayingPlayers().remove(currentPlayer);
                            }
                            else
                            {
                                playerTurn++;
                            }
                            passCounter=0;
                            pickCard();
                        }
                    }
                    else
                    {
                        textHelp.setText("The card you choose is not playable, pick another card or pass. Current Trump mode = "+gameprogress.getTrumpMode());
                    }
                    resultTest();
                    revalidate();
                    repaint();
                }
            });
            selectionPanel.add(cardBtn);
        }
    }

    public boolean checkPlayerWin(Player playerMoved)
    {
        boolean playerWinIt = false;
        if(playerMoved.getHand().size()==0)
        {
            playerWinIt=true;
        }
        return playerWinIt;
    }

    public void resultTest()
    {
        if(gameprogress.getPlayingPlayers().size()==1)
        {
            mainPanel.removeAll();
            selectionPanel.removeAll();
            passPanel.removeAll();
            textHelp.setText(gameprogress.getPlayingPlayers().get(0).getName()+" has lost");
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("3"))
        {
            noOfPlayer = 3;
            enterPlayerName();
        }
        else if(e.getActionCommand().equals("4"))
        {
            noOfPlayer=4;
            enterPlayerName();
        }
        else if(e.getActionCommand().equals("5"))
        {
            noOfPlayer=5;
            enterPlayerName();
        }
        else if(e.getActionCommand().equals("Enter"))
        {
            if(playerEnterNameCount<noOfPlayer)
            {
                textHelp.setText("Player "+playerEnterNameCount +" name have been entered, please enter player "
                        +(playerEnterNameCount+1)+" name");
                playersNameList.add(playerNameInput.getText());
                playerEnterNameCount++;
                playerNameInput.setText("");
            }
            else
            {
                playersNameList.add(playerNameInput.getText());
                readCard();
                gameStarter(gameDeck);
                pickMode();
            }
        }
        else if(e.getActionCommand().equals("Hardness"))
        {
            gameprogress.setPickedGameMode("H");
            pickCard();
        }
        else if(e.getActionCommand().equals("Specific Gravity"))
        {
            gameprogress.setPickedGameMode("S");
            pickCard();
        }
        else if(e.getActionCommand().equals("Cleavage"))
        {
            gameprogress.setPickedGameMode("C");
            pickCard();
        }
        else if(e.getActionCommand().equals("Crustal Abundance"))
        {
            gameprogress.setPickedGameMode("A");
            pickCard();
        }
        else if(e.getActionCommand().equals("Economic Value"))
        {
            gameprogress.setPickedGameMode("E");
            pickCard();
        }
        else if(e.getActionCommand().equals("Pass"))
        {
            if(passCounter==(gameprogress.getPlayingPlayers().size()-1) || gameprogress.getGraveyard().size()==0)
            {
                textHelp.setText("You cannot pass, you have picked the mode");
            }
            else
            {
                currentPlayer = gameprogress.getPlayingPlayers().get(playerTurn % gameprogress.getPlayingPlayers().size());
                currentPlayer.drawCard(gameprogress.getDeckCard().cardDrawn());
                passCounter++;
                playerTurn++;
                if(passCounter == (gameprogress.getPlayingPlayers().size())-1)
                {
                    pickMode();
                }
                else {
                    pickCard();
                }
            }
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        MainGUI ASD = new MainGUI();
    }
}
