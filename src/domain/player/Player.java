package domain.player;

import domain.card.Card; 

import java.util.List;

public class Player { 
    private String name;
    private final HandCardList handCards;

    public Player(String name, HandCardList handCards){ 
        this.name = name;
        this.handCards = handCards;
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHandCards() {
        return this.handCards.getCards();
    }

    public void addToHandCards(Card card){
        this.handCards.addCard(card);
    }

    public boolean removePlayedCard(Card card) {
        return this.handCards.removeCard(card);
    }

    public boolean hasHandCard(Card card) {
        return this.handCards.hasCard(card);
    }
    
}