import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {
    private int N;
    public byte[][] blocks;

    public Board(byte[][] blocks) {
        this.N = blocks.length;
        this.blocks = new byte[this.N][this.N];
        for(int i = 0; i <N; i++){
            for(int j =0; j < N; j++){
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return this.N;
    }

    public int hamming() {
        int somme = 0;
        for(int i = 0; i< N; i++){
            for(int j = 0; j<N; j++){
                if((this.blocks[i][j] != N*i + j +1) && (this.blocks[i][j]!=0)){
                    somme++;
                }
            }
        }
        return somme;
    }

    public int manhattan() {
        int somme = 0;
        for(int i = 0; i< N; i++){
            for(int j = 0; j<N; j++){
                int kezako = this.blocks[i][j];
                if(kezako!=0){
                    int realI = (kezako-1)/this.N;
                    int realJ = (kezako-1)%this.N;
                    somme += Math.abs(i-realI) + Math.abs(j-realJ);
                }
            }
        }
        return somme;
    }

    public boolean isGoal() {
        return this.hamming()==0;
    }

    private byte [][] swap(byte twin[][],int i1, int j1, int i2,int j2){
        byte temp = twin[i1][j1];
        twin[i1][j1] = twin[i2][j2];
        twin[i2][j2]= temp;
        return twin;
    }

    public Board twin() {
        Board twin = new Board(this.blocks);
        int i = (int) (Math.random() * N-1);
        if(twin.blocks[i][0] == 0){
            twin.blocks = swap(twin.blocks, i,1,i,2);
        } else if(this.blocks[i][1] == 0){
            twin.blocks = swap(twin.blocks,i+1,0,i+1,1);
        } else {
            twin.blocks = swap(twin.blocks,i,0,i,1);
        }
        return twin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return Arrays.deepEquals(blocks, board.blocks);
    }


    public Iterable<Board> neighbors() {
        ArrayList<Board> ret = new ArrayList<>();
        // coordonnées du blanc
        int i0 = 0;
        int j0 = 0;
        for(int i = 0; i< this.N; i++){
            for(int j = 0; j< this.N; j++){
                if(this.blocks[i][j] == 0){
                    i0 = i;
                    j0 = j;
                }
            }
        }
        // determiner nb mouvements possibles
        if(j0-1 > -1){
            Board neighbor1 = new Board(this.blocks);
            neighbor1.blocks = neighbor1.swap(neighbor1.blocks,i0, j0, i0, j0-1);
            ret.add(neighbor1);
        }
        if(j0+1 < this.N){
            Board neighbor1 = new Board(this.blocks);
            neighbor1.blocks = neighbor1.swap(neighbor1.blocks,i0, j0, i0, j0+1);
            ret.add(neighbor1);
        }
        if(i0-1 > -1){
            Board neighbor1 = new Board(this.blocks);
            neighbor1.blocks = neighbor1.swap(neighbor1.blocks,i0, j0, i0-1, j0);
            ret.add(neighbor1);
        }
        if(i0+1 < this.N){
            Board neighbor1 = new Board(this.blocks);
            neighbor1.blocks = neighbor1.swap(neighbor1.blocks,i0, j0, i0+1, j0);
            ret.add(neighbor1);
        }
        return ret;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(blocks);
    }
}
