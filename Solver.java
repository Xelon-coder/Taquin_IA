import java.util.*;

public class Solver {

    private static class SearchNode implements Comparable<SearchNode> {

        private int g;
        private int h;
        private Board bd;
        private SearchNode parent;

        public SearchNode(int g, int h, Board b, SearchNode parent){
            this.g = g;
            this.h = h;
            this.bd = b;
            this.parent = parent;
        }

        @Override
        public int compareTo(SearchNode searchNode) {
            if(this.g + this.h == searchNode.g + searchNode.h) return 0;
            if(this.g + this.h < searchNode.g + searchNode.h) return -1;
            else return 1;
        }
    }

    private Board initial;
    private MinPQ<SearchNode> pq;
    public Solver(Board initial) {
        this.initial = initial;
        this.pq = new MinPQ<>();
        SearchNode init = new SearchNode(0,0, initial, null);
        this.pq.insert(init);
        this.aStar();
    }

    private void aStar() {
        HashSet<Board> set = new HashSet<>();
        set.add(this.pq.min().bd);
        while(this.isSolvable() && !this.pq.min().bd.isGoal()){
            SearchNode now = this.pq.delMin();
            Iterable<Board> next = now.bd.neighbors();
            for(Board b : next){
                if(!set.contains(b)) {
                    this.pq.insert(new SearchNode(now.g + 1, b.manhattan(), b, now));
                    set.add(b);
                }
            }
        }
    }

    public boolean isSolvable() {
        return !pq.isEmpty();
    }

    public int moves() {
        if(!this.isSolvable()) return -1;
        return this.pq.min().g;
    }

    public Iterable<Board> solution() {
        ArrayList<Board> b = new ArrayList<>();
        SearchNode tmp = this.pq.min();
        while (tmp.parent != null){
            b.add(tmp.bd);
            tmp = tmp.parent;
        }
        Collections.reverse(b);
        return b;
    }

    public static void main(String[] args) {

            In in = new In("8puzzle-testing/8puzzle/puzzle50.txt");
            byte N = in.readByte();
            byte[][] blocks = new byte[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    blocks[i][j] = in.readByte();
            Board initial = new Board(blocks);

            // solve the puzzle
            Solver solver = new Solver(initial);

            // print solution to standard output
            if (!solver.isSolvable()) {
                StdOut.println("No solution possible");
            }
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution()) {
                    StdOut.println(board);
                }
            }
    }
}
