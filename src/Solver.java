
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class Solver {
    private static boolean isSolvabe;
    Board current;
    private int moves;
    public static final Comparator<Board> priority = new Priority();
    static PriorityQueue<Board> pq = new PriorityQueue<>(priority);

    private static class Priority implements Comparator<Board> {

        public int compare(Board b1, Board b2) {
            int b1Priority = b1.manhattan() + b1.s.moves();
            int b2Priority = b2.manhattan() + b2.s.moves();
            if (b1Priority > b2Priority) return +1;
            else if (b1Priority < b2Priority) return -1;
            return 0;
        }
    }

    public Solver() {
    }

    public Solver(Board initial) {
        initial.previous = null;
        this.moves = 0;
        initial.s = this;
        isSolvabe = initial.isSolvable();
        if (isSolvabe) {
            pq.add(initial);
            ArrayList<Board> neighbors;
            do {
                current = pq.remove();
                neighbors = (ArrayList<Board>) current.neighbors();
                for (Board neighbor : neighbors) {
                    if (neighbor.equals(current.previous))
                        continue;
                    neighbor.previous = current;
                    neighbor.s.moves = current.s.moves + 1;
                    pq.add(neighbor);
                }
            } while (!current.isGoal());
            System.out.println("number of minimum moves  required is : " + current.s.moves());
            Stack<Board> solution = (Stack) solution();
            while (!solution.isEmpty()) {
                System.out.println(solution.pop());
            }
        }
        else System.out.println("this board can't be solved");

    }


    public int moves() {
        if (isSolvabe)
            return this.moves;
        return -1;
    }

    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<>();
        while (current != null) {
            solution.push(current);
            current = current.previous;
        }
        return solution;
    }

}
