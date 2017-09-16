import java.util.Random;
import java.util.ArrayList;
public class Deck {
    public ArrayList<CardName> inDeck;

// an array used for storing card names
    Deck(ArrayList<CardName> cardlist)
    {
        inDeck = cardlist;
    }

    public void setDeckContent(ArrayList<CardName> inDeck) {
        this.inDeck = inDeck;
    }

// draw card from deck
    public CardName cardDrawn()
    {
        int dealt = new Random().nextInt(inDeck.size());
        CardName choosenCard = inDeck.get(dealt);
        inDeck.remove(dealt);
        return choosenCard;
    }

// check the content of deck
    public ArrayList<CardName> getDeckContent() {
        return inDeck;
    }
}
