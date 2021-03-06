package domain.game;

import domain.card.Card;
import domain.card.CardDeck;
import domain.player.Player;
import domain.player.PlayerRoundIterator;

import java.util.ArrayList;
import java.util.List;

public class GameBuilder {
    private List<String> playerNames = new ArrayList<>();

    public GameBuilder withPlayer(String name){
        playerNames.add(name);

        return this;
    }

    public Game build() {
        var cards = new CardDeck().getImmutableCards();

        var drawPile = buildDrawPile(cards);
        var players = buildPlayers(drawPile);

        return new Game(drawPile, players);
    }

    private DrawPile buildDrawPile(List<Card> cards) {
        var shuffledCards = DealerService.shuffle(cards);
        for (int i = 0; i<5; i++){
            shuffledCards = DealerService.shuffle(shuffledCards);
        }

        return new DrawPile(shuffledCards);
    }

    private PlayerRoundIterator buildPlayers(DrawPile drawPile){
        if(playerNames.size() < 2 || playerNames.size() > 6) {
            throw new IllegalStateException("2-6 players are required to create a game");
        }

        var handCardLists = DealerService.dealInitialHandCards(drawPile, playerNames.size());
        var players = new Player[playerNames.size()];

        for (int i = 0; i < playerNames.size(); i++) {
         players[i] = new Player(playerNames.get(i), handCardLists[i]);
        }

        return new PlayerRoundIterator(players);
    }

}