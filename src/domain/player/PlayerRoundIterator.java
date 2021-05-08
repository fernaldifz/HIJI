package domain.player;

import java.util.Arrays;

public class PlayerRoundIterator {
    private final Kumpulan<Player> players;
    private int current = 0;
    private Direction direction = Direction.CLOCKWISE;

    public PlayerRoundIterator(Player[] players) {
        this.players = new Kumpulan<Player>(players);
    }

    // Generic class
    public class Kumpulan<T> {
        private T[] array;

        public Kumpulan(T[] array) {
           this.array = array;
        }

        public T[] getArray() {
            return this.array;
        }

        public void setIsi(int i, T element) {
            this.array[i] = element;
        }

        public T getIsi(int i) {
            return array[i];
        }
    }

    public Player[] getPlayers() {
        return this.players.getArray();
    }

    public Player getCurrentPlayer() {
        return this.players.getIsi(current);
    }

    public void reverseDirection() {
        if (this.direction == Direction.CLOCKWISE){
            this.direction = Direction.COUNTER_CLOCKWISE;
        } else {
            this.direction = Direction.CLOCKWISE;
        }
    }

    public Player next() {
        this.current = getNextIndex();
        return getCurrentPlayer();
    }

    public Player viewNextPlayer(){
        return this.players.getIsi(getNextIndex());
    }

    private int getNextIndex() {
        var increment = this.direction == Direction.CLOCKWISE ? 1 : -1;
        return (this.players.getArray().length + this.current + increment) % this.players.getArray().length;
    }
}