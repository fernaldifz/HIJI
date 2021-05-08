package domain.game;

import domain.card.*;

public class CardRules {
    private CardRules(){
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidNumberCard(Card topCard, NumberCard playedCard) {
        if(isSameColor(topCard, playedCard)){
            return true;
        }

        if (topCard.getRank() == CardRank.NUMBER) {
            return ((NumberCard) topCard).getNumber() == playedCard.getNumber();
        }

        return false;
    }

    public static boolean isValidActionCard(Card topCard, ActionCard playedCard) {
        if(isSameColor(topCard, playedCard)){
            return true;
        }

        return topCard.getRank() == playedCard.getRank();
    }

    public static boolean isValidWildCard(WildCard playedCard) {
        return playedCard.getColor() != null;
    }

    private static boolean isSameColor(Card topCard, Card playedCard) {
        return topCard.getColor() == playedCard.getColor();
    }
}