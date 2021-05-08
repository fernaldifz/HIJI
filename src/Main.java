import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import application.GameAppService;
import domain.card.*;

public class Main {
    public static void showMenu(){
        //System.out.println("1. F01 : Start Game");
        System.out.println("--------------------");
        System.out.println("1. F01 : List Cards");
        System.out.println("2. F02 : Discard");
        System.out.println("3. F03 : Draw");
        System.out.println("4. F04 : Declare HIJI");
        System.out.println("5. F05 : List Players");
        System.out.println("6. F06 : View Player in Turn");
        System.out.println("7. F07 : Help");
        System.out.println("--------------------");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Start Game? (1/0): ");
        int str = sc.nextInt();
        System.out.println("--------------------");

        if (str == 1){
            GameAppService gameappservice;

            System.out.print("Yuk mulai! Masukkan jumlah pemain: ");
            int jml_Pemain = sc.nextInt();
            
            while (jml_Pemain < 2 ||  jml_Pemain > 6){
                System.out.println("Jumlah pemain harus lebih dari 2 atau kurang dari 6"); 
                jml_Pemain = sc.nextInt();
            }

            ArrayList<String> playerNames = new ArrayList<>(jml_Pemain);
            for (int i = 1; i < jml_Pemain+1; i++){
                System.out.print("Nama Pemain ke " + i + ": ");
                String name = sc.next();
                playerNames.add(name);
            }

            gameappservice = new GameAppService(playerNames);
            gameappservice.peekTopCard();
            
            while(!gameappservice.getGame().isOver()){
                showMenu();
                System.out.println("MENU YANG DIPILIH?");
                System.out.print("No : ");
                int noMenu = sc.nextInt();

                if (noMenu == 1){
                    gameappservice.getHandCards();
                }
                
                else if (noMenu == 2){
                    gameappservice.getHandCards();
                    System.out.println();
                    gameappservice.peekTopCard();

                    System.out.print("Ketik 1 untuk single discard dan 2 untuk multiple discard: ");
                    int pil = sc.nextInt();
                    
                    if (pil == 1) {
                        System.out.print("Pilih kartu yang: ");
                        int index = sc.nextInt();

                        if (gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1).getRank() == CardRank.WILD || gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1).getRank() == CardRank.DRAW4){
                            System.out.println("List warna: ");
                            System.out.println("1. Merah");
                            System.out.println("2. Kuning");
                            System.out.println("3. Hijau");
                            System.out.println("4. Biru");
                            System.out.print("Pilih index warna: ");
                            int warna = sc.nextInt();
                            
                            if (warna == 1){
                                gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1).setColor(CardColor.MERAH);
                            } else if (warna == 2){
                                gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1).setColor(CardColor.KUNING);
                            } else if (warna == 3){
                                gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1).setColor(CardColor.HIJAU); 
                            } else {
                                gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1).setColor(CardColor.BIRU);;
                            } 
                        }

                        try{
                            gameappservice.playCard(gameappservice.getGame().getCurrentPlayer(), gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(index-1));
                        } catch(Exception e){
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Pilih kartu yang ingin dikeluarkan, ketik -1 saat selesai memilih!");
                        int idx = 0;
                        List<Integer> numberlist = new ArrayList<>();
                        List<Card> cardlist = new ArrayList<>();
                        while (idx != -1){
                            System.out.print("Pilih kartu yang: "); 
                            idx = sc.nextInt();
                            
                            if (idx != -1){
                                numberlist.add(idx); 
                            }
                        }

                        boolean checkWild = true;
                        for(int i = 0; i < numberlist.size(); i++){
                            var rank = gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(numberlist.get(i)-1).getRank();

                            if (rank != CardRank.WILD || rank != CardRank.DRAW4){
                                checkWild = false;
                            }
                        }

                        if (checkWild){
                            System.out.println("List warna: ");
                            System.out.println("1. Merah");
                            System.out.println("2. Kuning");
                            System.out.println("3. Hijau");
                            System.out.println("4. Biru");
                            System.out.print("Pilih index warna: ");
                            int warna = sc.nextInt();

                            if (warna == 1){
                                for(int i = 0; i < numberlist.size(); i++){
                                    gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(numberlist.get(i)-1).setColor(CardColor.MERAH);
                                }
                            } else if (warna == 2){
                                for(int i = 0; i < numberlist.size(); i++){
                                    gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(numberlist.get(i)-1).setColor(CardColor.KUNING);
                                }
                            } else if (warna == 3){
                                for(int i = 0; i < numberlist.size(); i++){
                                    gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(numberlist.get(i)-1).setColor(CardColor.HIJAU);
                                } 
                            } else {
                                for(int i = 0; i < numberlist.size(); i++){
                                    gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(numberlist.get(i)-1).setColor(CardColor.BIRU);
                                }
                            } 
                        }

                        for(int i = 0; i < numberlist.size(); i++){
                            cardlist.add(gameappservice.getGame().getHandCards(gameappservice.getGame().getCurrentPlayer()).get(numberlist.get(i)-1));  
                        }

                        try{
                            gameappservice.playMultipleCards(gameappservice.getGame().getCurrentPlayer(), cardlist);
                        } catch(Exception e){
                            System.out.println(e);
                        }
                    }
                }
                else if (noMenu == 3){
                    gameappservice.drawCard(gameappservice.getGame().getCurrentPlayer());
                }
                else if (noMenu == 4){
                    gameappservice.declareHiji(gameappservice.getGame().getCurrentPlayer());
                }
                else if (noMenu == 5){
                    gameappservice.getListPlayer();
                }
                else if (noMenu == 6){
                    gameappservice.viewPlayerInTurn();
                }
                else if (noMenu == 7){
                    gameappservice.viewHelp();
                }
                else {
                    System.out.println("Masukan gak valid bro!!!");
                }
            }
            System.out.println("------------------------------");
            gameappservice.isGameOver();
            gameappservice.getWinner();
        } 
        else {
            System.exit(0);
        }
        sc.close();
    } 
} 