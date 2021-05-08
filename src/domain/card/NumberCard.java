package domain.card;

public class NumberCard extends GeneralCard{
    private int number;

    public NumberCard(CardColor color, int number) {
        super(color, CardRank.NUMBER);
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString(){
    	String str = "";

        if (super.getColor() == CardColor.MERAH){
            str = "Merah";
        } else if (super.getColor() == CardColor.HIJAU){
            str = "Hijau";
        } else if (super.getColor() == CardColor.KUNING){
            str = "Kuning";
        } else {
            str = "Biru";
        }

    	return ("" + getNumber() + " " + str);
    }
}
