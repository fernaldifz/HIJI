package domain.game;

import domain.card.*; 
import domain.player.Player;
import domain.player.PlayerRoundIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack; 

public class Game {
    private final PlayerRoundIterator players; 
    private DrawPile drawPile;

    private final Stack<Card> discardPile = new Stack<>(); 
    private Player winner = null;

    public Game(DrawPile drawPile, PlayerRoundIterator players) { 
        this.drawPile = drawPile;
        this.players = players;

        startDiscardPile();
    }

    public Player[] getPlayers() {
        return players.getPlayers();
    }

    public Player getCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    public Player viewNextPlayer(){
        return players.viewNextPlayer();
    }

    public List<Card> getHandCards(Player player) {
        return player.getHandCards();
    }

    public Card peekTopCard() {
        return discardPile.peek();
    }

    private void startDiscardPile() {
        Card card = drawPile.drawCard();

        while (card.getRank() != CardRank.NUMBER){
            recreateDrawPile(card);
            card = drawPile.drawCard();
        }

        discard(card);
    }

    public void playCard(Player player, Card playedCard) {
        if (isOver()) {
            throw new IllegalStateException("Game selesai!");
        }

        validatePlayedCard(player, playedCard);

        switch (playedCard.getRank()) {
            case NUMBER -> {
                checkNumberCardRule(playedCard);
                acceptPlayedCard(playedCard);

                players.next();
            }
            case SKIP -> {
                checkActionCardRule(playedCard);
                acceptPlayedCard(playedCard);

                players.next();
                players.next();
            }
            case REVERSE -> {
                checkActionCardRule(playedCard);
                acceptPlayedCard(playedCard);

                reverse();
                players.next();
            }
            case DRAW2 -> {
                checkActionCardRule(playedCard);
                acceptPlayedCard(playedCard);

                players.next();
                drawTwoCards(players.getCurrentPlayer());
            }
            case WILD -> {
                checkWildCardRule(playedCard);
                acceptPlayedCard(playedCard);

                players.next();
            }
            case DRAW4 -> {
                checkWildCardRule(playedCard);
                acceptPlayedCard(playedCard);

                players.next();
                drawFourCards(players.getCurrentPlayer()); 
            }
            default -> rejectPlayedCard(playedCard);
        }

    } 

    public boolean isOver() {
        return this.winner != null;
    }

    public Player getWinner() {
        return this.winner;
    } 

    public void declareHiji(Player player) {
        if (player.getHandCards().size() != 1){
            drawTwoCards(player);
        } else {
            System.out.println("Player " + player.getName() + " berhasil mendeklarasikan Hiji!");
        }
    }

    private void validatePlayedCard(Player player, Card card) {
        var currentPlayer = players.getCurrentPlayer();
        if (currentPlayer != player){
            throw new IllegalArgumentException(String.format("Bukan giliran Player %s!", player));
        }

        if (!currentPlayer.hasHandCard(card)) {
            throw new IllegalArgumentException(String.format("Kartu %s tidak ada di kartu tangan player!", card));
        }
    }

    private void checkNumberCardRule(Card playedCard) {
        var topCard = peekTopCard();

        if (CardRules.isValidNumberCard(topCard, (NumberCard) playedCard)) {
            return;
        }

        rejectPlayedCard(playedCard);
    }

    private void checkActionCardRule(Card playedCard) {
        var topCard = peekTopCard();

        if (CardRules.isValidActionCard(topCard, (ActionCard) playedCard)) {
            return;
        }

        rejectPlayedCard(playedCard);
    }

    private void checkWildCardRule(Card playedCard) {
        if (!CardRules.isValidWildCard((WildCard) playedCard)) {
            rejectPlayedCard(playedCard);
        }
    } 

    private void recreateDrawPile(Card card) {
        if (drawPile.getSize() == 0) {
            throw new IllegalStateException("Tidak cukup kartu untuk kembali membuat DrawPile!");
        }

        this.drawPile = DealerService.shuffle(this.drawPile, card);
    }

    private void acceptPlayedCard(Card card) {
        players.getCurrentPlayer().removePlayedCard(card);
        discard(card);

        int remainingTotalCards = players.getCurrentPlayer().getHandCards().size();

        if (remainingTotalCards == 1){
            declareHiji(players.getCurrentPlayer());
        }

        if (remainingTotalCards == 0) {
            this.winner = getCurrentPlayer();
        }
    }

    private void discard(Card card) {
        discardPile.add(card);
    }

    private void reverse() {
        players.reverseDirection(); 
    }

    private void drawTwoCards(Player player) {
        drawCards(player, 2);
    }

    private void drawFourCards(Player player) {
        drawCards(player, 4);
    }

    public void drawCards(Player player, int total) {
        int min = Math.min(total, drawPile.getSize());

        for (int i = 0; i < min; i++) {
            var drawnCard = drawPile.drawCard(); 
            player.addToHandCards(drawnCard);
        } 

        players.next();
    }

    private void rejectPlayedCard(Card playedCard) {
        throw new IllegalArgumentException(
            String.format("Kartu yang dimainkan %s tidak valid untuk %s", playedCard, peekTopCard())
        );
    }

    public void playMultipleCards(Player player, List<Card> cards){
        //validation
        if (isOver()) {
            throw new IllegalStateException("Game selesai!");
        }

        for(Card card: cards){
            validatePlayedCard(player, card);
        }

        boolean validMultipleCards = true;
        for(int i = 0; i < (cards.size() - 1); i++){
            if(!cards.get(i).toString().equals(cards.get(i+1).toString())){
                validMultipleCards = false;
            }
        }
        if(!validMultipleCards){
            throw new IllegalStateException("Kartu-kartu pilihan Anda tidak sama!");
        } else {
            switch (cards.get(0).getRank()) {
                case NUMBER -> {
                    for(Card card: cards){
                        checkNumberCardRule(card);
                        acceptPlayedCard(card);
                    }

                    players.next();
                }
                case SKIP -> {
                    for(Card card: cards){
                        checkActionCardRule(card);
                        acceptPlayedCard(card);
                    }

                    players.next();
                    for(int i = 0; i < cards.size(); i++){
                        players.next();
                    }
                }
                case REVERSE -> {
                    for(Card card: cards){
                        checkActionCardRule(card);
                        acceptPlayedCard(card);
                    }

                    for(int i = 0; i < cards.size(); i++){
                        reverse();
                    }
                    players.next();
                }
                case DRAW2 -> {
                    for(Card card: cards){
                        checkActionCardRule(card);
                        acceptPlayedCard(card);
                    }

                    players.next();
                    drawCards(players.getCurrentPlayer(), 2*cards.size());
                    
                }
                case WILD -> {
                    for(Card card: cards){
                        checkWildCardRule(card);
                        acceptPlayedCard(card);
                    }

                    players.next();
                }
                case DRAW4 -> {
                    for(Card card: cards){
                        checkWildCardRule(card);
                        acceptPlayedCard(card);
                    }

                    players.next();
                    drawCards(players.getCurrentPlayer(), 4*cards.size());

                }
                default -> {
                    for(Card card: cards){
                        rejectPlayedCard(card);
                    }
                }
            }
        }
    }
}