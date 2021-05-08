package domain.player;

import domain.card.Card;
import domain.card.CardRank; 

import java.util.ArrayList;
import java.util.List;

public class HandCardList {
    private final List<Card> handCards = new ArrayList<>();

    public void addCard(Card newCard) {
        this.handCards.add(newCard);
    }

    public boolean removeCard(Card card) { 
        return this.handCards.remove(card);
    }

    public List<Card> getCards() {
        return this.handCards;
    }

    public boolean hasCard(Card card) {
        return this.handCards.contains(card);
    }

    public int size() {
        return this.handCards.size();
    }
}