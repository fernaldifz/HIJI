package domain.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private final List<Card> cards = new ArrayList<>(108);

    public CardDeck() {
        createNumberCards();
        createActionCards();
        createWildCards();
    }

    public List<Card> getImmutableCards() {
        return Collections.unmodifiableList(cards);
    }

    private void createNumberCards() {
        for (CardColor color : CardColor.values()) {
            cards.add(new NumberCard(color, 0));

            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
                cards.add(new NumberCard(color, i));
            }
        }
    }

    private void createActionCards() {
        for (CardColor color : CardColor.values()) {
            cards.add(new ActionCard(color, CardRank.SKIP));
            cards.add(new ActionCard(color, CardRank.REVERSE));
            cards.add(new ActionCard(color, CardRank.DRAW2));
            
            cards.add(new ActionCard(color, CardRank.SKIP));
            cards.add(new ActionCard(color, CardRank.REVERSE));
            cards.add(new ActionCard(color, CardRank.DRAW2));
        }
    }

    private void createWildCards() {
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard(CardRank.WILD));
            cards.add(new WildCard(CardRank.DRAW4));
        }
    }
}

