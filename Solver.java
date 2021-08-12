import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {

    private int moves;
    private SearchNode finalNode;
    private final Board initial;
    private boolean solvableSearched;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.initial = initial;
        isSolvable();


    }

    public Iterable<Board> solution() {
        if (!solvable) return null;

        SearchNode currentNode = finalNode;
        Stack<Board> sol = new Stack<>();

        while (currentNode != null) {
            sol.push(currentNode.getBoard());

            currentNode = currentNode.getPrev();

        }

        moves = sol.size() - 1;
        return sol;
    }

    public int moves() {
        if (!solvable) return -1;

        this.solution();
        return moves;
    }

    public boolean isSolvable() {
        if (solvableSearched)
            return solvable;

        solvableSearched = true;
        MinPQ<SearchNode> firstQu = new MinPQ<>();
        MinPQ<SearchNode> twinQu = new MinPQ<>();

        SearchNode currentNode = new SearchNode(initial, 0, null);
        SearchNode currentNodeTwin = new SearchNode(initial.twin(), 0, null);


        while (!currentNode.getBoard().isGoal() && !currentNodeTwin.getBoard().isGoal()) {
            Iterable<Board> ab = currentNode.getBoard().neighbors();
            Iterable<Board> abTwin = currentNodeTwin.getBoard().neighbors();
            for (Board b : ab) {
                if (currentNode.getPrev() == null || !currentNode.getPrev().getBoard().equals(b)) {
                    firstQu.insert(new SearchNode(b, currentNode.n + 1, currentNode));
                }
            }
            for (Board b : abTwin) {
                if (currentNodeTwin.getPrev() == null || !currentNodeTwin.getPrev().getBoard().equals(b)) {
                    twinQu.insert(new SearchNode(b, currentNodeTwin.n + 1, currentNodeTwin));
                }
            }
            moves++;

            currentNode = firstQu.delMin();
            currentNodeTwin = twinQu.delMin();
        }
        if (currentNode.getBoard().isGoal()) {
            solvable = true;
            finalNode = currentNode;
            return true;
        }
        solvable = false;
        finalNode = null;
        return false;

    }


    private static class SearchNode implements Comparable<SearchNode> {
        //(the initial board, 0 moves, and a null previous search node)
        private final Board b;
        private final int n;
        private final SearchNode prev;
        private final int priority;

        public SearchNode(Board b, int n, SearchNode prev) {
            this.b = b;
            this.prev = prev;
            this.n = n;
            this.priority = b.manhattan() + n;

        }

        public int getPriority() {
            return this.priority;
        }

        public SearchNode getPrev() {
            return this.prev;
        }


        public Board getBoard() {
            return this.b;
        }

        public int compareTo(SearchNode other) {

            return this.priority - other.getPriority();
        }

        public String toString() {
            return b.toString();
        }
    }

    public static void main(String[] args) {
//        Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
//        Board b = new Board(new int[][]{{1, 6, 4}, {0, 3, 5}, {8, 2, 7}});
        //   Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});

//        Board b = new Board(new int[][]{{2, 1, 3}, {0, 5, 6}, {4, 8, 7}});
//
//        Solver s = new Solver(b);
//        System.out.println(s.moves);
//        System.out.println(s.finalNode);
//
//        System.out.println(s.isSolvable());
//
//        Iterable<Board> sol = s.solution();
//        for (Board board : sol) {
//            System.out.println(board);
//        }
//        System.out.println(s.moves());


        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
// 891
// 552963
// 552963
//
// 552963
// 400176
// 601204
// 1184511
// 1450338
// 1482928
// 1503423
// 2868395
// 3238811
// 3338957
// 5375636
// 5375636
