package application;

import domain.card.Card;
import domain.game.Game;
import domain.game.GameBuilder; 
import domain.player.Player;

import java.util.List;

public class GameAppService{
    private Game game;

    public GameAppService(List<String> playerNames) {
        var newGame = new GameBuilder();
        for (int i = 0; i < playerNames.size() ; i++) {
            newGame.withPlayer(playerNames.get(i));
        }
        game = newGame.build();
    }

    public Game getGame(){
        return this.game;
    }

    public void getListPlayer(){
        var players = game.getPlayers();
        var currentPlayer = game.getCurrentPlayer().getName();
        for(int i = 1; i <= players.length; i++){
            System.out.println("Pemain " + i + ": " + players[i-1].getName());
            System.out.println("Jumlah kartu: " + players[i-1].getHandCards().size());
            if (!(currentPlayer).equals(players[i-1].getName())){
                System.out.println("Tidak sedang giliran\n");
            }
            else{
                System.out.println("Sedang giliran\n");
            }
        }
    }

    public void getHandCards() {
        for(int i = 1; i <= game.getCurrentPlayer().getHandCards().size(); i++){
            System.out.println(i + ". " + game.getCurrentPlayer().getHandCards().get(i-1).toString());
        }
    }

    public void viewPlayerInTurn(){
        System.out.println("Sekarang giliran " + game.getCurrentPlayer().getName());
        System.out.println("Berikutnya giliran " + game.viewNextPlayer().getName());
    }

    public void viewHelp(){
        System.out.println("HIJI dimainkan oleh 2-6 pemain.");
        System.out.println("Di awal permainan, semua pemain akan mendapatkan 7 buah kartu, " +
                "dan satu kartu angka dipilih secara acak untuk dijadikan kartu awal.");
        System.out.println("Pemain yang akan memulai giliran pertama akan diacak.");
        System.out.println("Aturan permainan adalah sebagai berikut.");
        System.out.println("Pada setiap giliran, pemain boleh mengeluarkan satu atau lebih kartu yang dapat" +
                " dimainkan pada giliran tersebut. Ketentuan kartu apa saja yang boleh dikeluarkan" +
                " ada pada tabel Jenis Kartu.");
        System.out.println("Apabila pemain tidak mengeluarkan kartu, pemain wajib mengambil satu kartu dari deck");
        System.out.println("Apabila kartu yang baru diambil tersebut bisa dikeluarkan, pemain boleh" +
                " mengeluarkan kartu tersebut (tidak wajib)");
        System.out.println("Apabila kartu tersebut tidak dapat dimainkan, maka giliran diselesaikan tanpa" +
                " mengeluarkan kartu.");
        System.out.println("Beberapa jenis kartu memiliki power tertentu yang dapat memengaruhi jalannya" +
                " permainan (lihat di bagian selanjutnya).");
        System.out.println("Apabila pemain memiliki sisa satu kartu, maka pemain harus melakukan 'Declare HIJI'" +
                " dalam waktu 3 detik. Apabila tidak, pemain wajib mengambil dua kartu dari deck.");
        System.out.println("Pemain dinyatakan menang apabila kartu yang dipegangnya sudah habis, dan permainan" +
                " selesai.");
    }

    public void playCard(Player player, Card card) {
        game.playCard(player, card);
        var message = String.format("Pemain %s mengeluarkan %s", player.getName(), card.toString());
        System.out.println(message);
    }

    public void playMultipleCards(Player player, List<Card> cards){
        game.playMultipleCards(player, cards);
        var message = String.format("Pemain %s mengeluarkan %d kartu %s", player.getName(), cards.size(), cards.get(0).toString());
        System.out.println(message);
    }

    public void declareHiji(Player player){
        game.declareHiji(player);
        System.out.println("Pemain mendeklarasikan Hiji!");
    }

    public void drawCard(Player player) {
        var message = String.format("Pemain %s menarik sebuah kartu!", player.getName());
        game.drawCards(player, 1);
        System.out.println(message);
    }

    public void peekTopCard() {
        System.out.println("Kartu teratas adalah " + game.peekTopCard().toString());
    }

    public void isGameOver() {
        if(game.isOver()){
            System.out.println("Permainan HIJI telah berakhir!");
        }
        else{
            System.out.println("Permainan Hiji belum berakhir!");
        }
    }

    public void getWinner() {
        if(game.isOver()){
            System.out.println("Pemenang adalah " + game.getWinner().getName());
        }
        else{
            System.out.println("Belum ada pemenang!");
        }
    }
}