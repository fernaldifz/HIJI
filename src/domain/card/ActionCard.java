package domain.card;

public class ActionCard extends GeneralCard {

    /* Konstruktor */
    public ActionCard(CardColor color, CardRank rank) {
        super(color, rank); 
    }

    @Override
    public String toString(){
    	String str1 = "";
    	String str2 = "";

        if(super.getRank() == CardRank.SKIP){
            str1 = "Skip";
        } else if (super.getRank() == CardRank.DRAW2){
            str1 = "Draw 2";
        } else {
            str1 = "Reverse";
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

    	return (str1 + " " + str2);
    }
}
