package domain.card;

public class WildCard extends GeneralCard {

    /* Konstruktor */
    public WildCard(CardRank rank) {
        super(null, rank);
    } 

    @Override
    public String toString() {
        String str1 = "";
        String str2 = "";

        if (super.getRank() == CardRank.DRAW4){
            str1 = ("Draw 4");
        } else {
            str1 = ("WildCard");
        }

        if (super.getColor() == CardColor.MERAH){
            str2 = "Merah";
        } else if (super.getColor() == CardColor.HIJAU){
            str2 = "Hijau";
        } else if (super.getColor() == CardColor.KUNING){
            str2 = "Kuning";
        } else {
            str2 = "Biru";
        }

        String message = String.format("%s %s", str1, (super.getColor() != null) ? str2 : "");
        return message;
    }
} 