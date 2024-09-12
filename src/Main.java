

public class Main {

    public static void main(String[] args) {
        int[][] tiles = { {1,5, 3 }, { 6, 2, 0 }, { 7, 8, 4 } };
        Board board = new Board(tiles);
         new Solver(board);
        System.out.println(recurs(5));
    }
    public static int recurs(int i){
        if(i==0) return 0;
        i--;
        recurs(i);
        return i;
    }
}