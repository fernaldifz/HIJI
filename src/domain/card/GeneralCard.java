package domain.card;

public abstract class GeneralCard implements Card{
    private CardRank rank;
    private CardColor color;

    /* Konstruktor */
    public GeneralCard (CardColor color, CardRank rank){
        this.color = color;
        this.rank = rank;
    }

    /* Getter */
    public CardRank getRank() {
        return rank;
    }
    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color){
        this.color = color;
    }

    public abstract String toString();

}
