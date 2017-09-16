import javax.swing.*;
import java.util.ArrayList;

public class InGame {
    public ArrayList<CardName> graveyard;
    public Deck deckCard;
    public ArrayList<Player> playingPlayers;
    public String pickedGameMode;
    public String previousPlayerTurn;

// getters and setters
    public void setDeckCard(Deck deckCard) {
        this.deckCard = deckCard;
    }

    public void setPlayingPlayers(ArrayList<Player> playingPlayers) {
        this.playingPlayers = playingPlayers;
    }

    public void setGraveyard(ArrayList<CardName> graveyard) {
        this.graveyard = graveyard;
    }

    public ArrayList<CardName> getGraveyard() {
        return graveyard;
    }

    public String getPickedGameMode() {
        return pickedGameMode;
    }

    public String getTrumpMode()
    {
        String trumpMode="";
        if (getPickedGameMode().equals("H"))
        {
            trumpMode="Hardness";
        }
        else if (getPickedGameMode().equals("S"))
        {
            trumpMode="Specific Gravity";
        }
        else if (getPickedGameMode().equals("C"))
        {
            trumpMode="Cleavage";
        }
        else if (getPickedGameMode().equals("A"))
        {
            trumpMode="Crustal Abundance";
        }
        else if (getPickedGameMode().equals("E"))
        {
            trumpMode="Economic Value";
        }
        return trumpMode;
    }

    public Player getPlayer(int x)
    {
        return playingPlayers.get(x);
    }

    public ArrayList<Player> getPlayingPlayers() {
        return playingPlayers;
    }

    public String getPreviousPlayerTurn() {
        return previousPlayerTurn;
    }

    public void setPreviousPlayerTurn(String previousPlayerTurn) {
        this.previousPlayerTurn = previousPlayerTurn;
    }

    public void setPickedGameMode(String pickedGameMode) {
        this.pickedGameMode = pickedGameMode;
    }

    public Deck getDeckCard() {
        return deckCard;
    }

// determining instruction based on game mode
    public String chosenMode()
    {
        String description = "";
        if(pickedGameMode.equals("H"))
        {
            description = "Choose a larger Hardness";
        }
        else if(pickedGameMode.equals("S"))
        {
            description = "Choose a larger Specific Gravity";
        }
        else if(pickedGameMode.equals("C"))
        {
            description = "Choose a larger Cleavage";
        }
        else if(pickedGameMode.equals("A"))
        {
            description = "Choose a larger Abundance Value";
        }
        else if(pickedGameMode.equals("E")) {
            description = "Choose a larger E Value";
        }
        return description;
    }



// names for each player
    InGame(ArrayList<String> playerList, Deck deckcard)
    {
        this.deckCard = deckcard;
        pickedGameMode = "";
        graveyard = new ArrayList<CardName>();
        previousPlayerTurn = "";
// counter act as the number of players participating, pcounter used for string output
        int counter;
        playingPlayers = new ArrayList<Player>();
        for(int player = 0; player<playerList.size();player++)
        {
            playingPlayers.add(new Player(playerList.get(player)));
        }
        for(counter = 0; counter<8; counter++) {
            for (Player currentPlayer : playingPlayers) {
                currentPlayer.drawCard(this.deckCard.cardDrawn());
            }
        }
    }

// current card on table
    public CardName getLastCard() {
        return graveyard.get(graveyard.size()-1);
    }

// game will continue if a card is put, without skipping and drawing
    public boolean continueGame()
    {
        boolean used = false;
        if(graveyard.size()>0)
        {used = true;}
        return used;
    }

// when the cards in deck reaches zero, previously used cards will be returned to the deck
    public void returnCards()
    {
        CardName cardLastPlayed = getLastCard();
        graveyard.remove(cardLastPlayed);
        deckCard = new Deck(graveyard);
        graveyard.clear();
        graveyard.add(cardLastPlayed);

    }

// comparing attributes in cards based on gamemode
    public boolean playCard(CardName card, Player play, int passcount)
    {
        boolean valid = false;
        int difference = 0;
        if(graveyard.size()==0 || this.repeatTurn(play) || (passcount==playingPlayers.size()-1)) {
            if(card  instanceof TypeSuperTrump) {
                pickedGameMode = ((TypeSuperTrump) card).cardeffect();
            }
            valid = true;
        }
// compare stats only for normal type cards, if a supretrumpcard used then game continues with the card effect
        else {
            if(card instanceof TypeNormal) {
                if (getLastCard() instanceof TypeNormal) {
                    if (getPickedGameMode().equals("H")) {
                        Float current = new Float(((TypeNormal) card).getHardness());
                        Float previous = new Float(((TypeNormal) getLastCard()).getHardness());
                        difference = current.compareTo(previous);
                    } else if (getPickedGameMode().equals("S")) {
                        Float current = new Float(((TypeNormal) card).getGravScoreValue());
                        Float previous = new Float(((TypeNormal) getLastCard()).getGravScoreValue());
                        difference = current.compareTo(previous);
                    } else if (getPickedGameMode().equals("C")) {
                        Float current = new Float(((TypeNormal) card).getCleavageValue());
                        Float previous = new Float(((TypeNormal) getLastCard()).getCleavageValue());
                        difference = current.compareTo(previous);
                    } else if (getPickedGameMode().equals("A")) {
                        Float current = new Float(((TypeNormal) card).getCrustalAbundanceValue());
                        Float previous = new Float(((TypeNormal) getLastCard()).getCrustalAbundanceValue());
                        difference = current.compareTo(previous);
                    } else if (getPickedGameMode().equals("E")) {
                        Float current = new Float(((TypeNormal) card).getEcoValueValue());
                        Float previous = new Float(((TypeNormal) getLastCard()).getEcoValueValue());
                        difference = current.compareTo(previous);
                    }

                    if (difference > 0) {
                        valid = true;
                    }
                    else if(difference<=0){
                        valid = false;
                    }
                } else {
                    valid = true;
                }
            }
// for supertrump cards
            else
            {
                setPickedGameMode(((TypeSuperTrump) card).cardeffect());
                valid = true;
            }
        }
        return valid;
    }

    public void useCard(CardName used)
    {
        graveyard.add(used);
    }

// if every other players skip turn, the current player gets another turn
    public boolean repeatTurn(Player currentPlayerTurn)
    {
        boolean valid = false;
        if(getPreviousPlayerTurn().equals(currentPlayerTurn.getName()))
        {
            valid = true;
        }
        return valid;
    }

}
