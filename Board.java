import java.util.ArrayList;

public class Board {
    private final int[][] tiles;
    private final int n;
    private int man;
    private int ham;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (tiles[i][j] != i * n + j + 1 && j + i != 2 * n - 2) {
                    this.ham++;
//                    man += Math.abs(i - tiles[i][j] / n) + Math.abs(j - (tiles[i][j] - 1) % n);
                }
                int value = tiles[i][j]; // tiles array contains board elements
                if (value != 0) { // we don't compute MD for element 0
                    int targetX = (value - 1) / n; // expected x-coordinate (row)
                    int targetY = (value - 1) % n; // expected y-coordinate (col)
                    int dx = i - targetX; // x-distance to expected coordinate
                    int dy = j - targetY; // y-distance to expected coordinate
                    man += Math.abs(dx) + Math.abs(dy);
                }
                  /*
                    1 2 3
                    6 4 0
                    7 8 5
                    2+2+2

                     */

            }
        }


    }

    // string representation of this board
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ").append(tiles[i][j]);
            }
            sb.append("\n");
        }
        return new String(sb);
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {

        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        return man;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j + i == 2 * n - 2)
                    return true;
                if (tiles[i][j] != i * n + j + 1)
                    return false;
            }

        }
        return true;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (!y.getClass().equals(this.getClass())) return false;


        Board other = (Board) y;
        if (this.dimension() != other.dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != other.tiles[i][j])
                    return false;
            }
        }
        return true;


    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    if (i == 0) {
                        // top  border
                        list.add(new Board(exch(tiles, i, j, i + 1, j)));
                        exch(tiles, i, j, i + 1, j);
//                        System.out.println(this.toString());

                    } else if (i == n - 1) {
                        // down border
                        list.add(new Board(exch(tiles, i, j, i - 1, j)));
                        exch(tiles, i, j, i - 1, j);
//                        System.out.println(this.toString());


                    } else {
                        list.add(new Board(exch(tiles, i, j, i + 1, j)));
                        exch(tiles, i, j, i + 1, j);
                        list.add(new Board(exch(tiles, i, j, i - 1, j)));
                        exch(tiles, i, j, i - 1, j);
//                        System.out.println(this.toString());

                    }
                    if (j == 0) {
                        // left border
                        list.add(new Board(exch(tiles, i, j, i, j + 1)));
                        exch(tiles, i, j, i, j + 1);
//                        System.out.println(this.toString());

                    } else if (j == n - 1) {
                        // right  border
                        list.add(new Board(exch(tiles, i, j, i, j - 1)));
                        exch(tiles, i, j, i, j - 1);
//                        System.out.println(this.toString());


                    } else {
                        list.add(new Board(exch(tiles, i, j, i, j + 1)));
                        exch(tiles, i, j, i, j + 1);
                        list.add(new Board(exch(tiles, i, j, i, j - 1)));
                        exch(tiles, i, j, i, j - 1);
//                        System.out.println(this.toString());

                    }

                }

            }
        }
        return list;
    }


    private int[][] exch(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
        return a;

    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] otherTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                otherTiles[i][j] = tiles[i][j];
            }

        }
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = 0; j < n - 1; j++) {
//                if (otherTiles[i][j] != 0 && otherTiles[(i + 1) % 3][(j + 1) % 3] != 0) {
//                    int temp = otherTiles[i][j];
//                    otherTiles[i][j] = otherTiles[(i + 1) % 3][(j + 1) % 3];
//                    otherTiles[(i + 1) % 3][(j + 1) % 3] = temp;
//                    return new Board(otherTiles);
//
//                }
//
//
//            }
//
//        }
        if (otherTiles[0][0] != 0 && otherTiles[0][1] != 0) exch(otherTiles, 0, 0, 0, 1);
        else exch(otherTiles, 1, 0, 1, 1);

        return new Board(otherTiles);

    }


    // unit testing (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}});
        Board a = new Board(new int[][]{{1, 2, 3}, {6, 4, 0}, {7, 8, 5}});
        Board bb = new Board(new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}});
        /*
        1 2 3
        6 4 0
        7 8 5
        2+2+2

         */
        System.out.println(b.twin());
        System.out.println(a.manhattan());
        System.out.println(b.hamming());

        System.out.println(b.equals(bb));

        Iterable<Board> ab = b.neighbors();
        for (Board board : ab) {
            System.out.println(board.toString());
        }
        System.out.println(b.isGoal());

    }


}
