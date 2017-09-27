package ru.pflb.perft;


import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class PerftTest {

    @Test
    // @Ignore
    public void shortPerftTest() {
        long millis = System.currentTimeMillis();
        Board board = new Board("4kb2/8/8/8/8/8/8/4K2R w - -");
        System.out.println(board);
        long movesNb = Perft.search(board, 7);
        System.out.println(System.currentTimeMillis() - millis);

        assertThat(movesNb).isEqualTo(104744354);

        // 15512
    }

    @Test
    // @Ignore
    public void longPerftTest() {
        long millis = System.currentTimeMillis();
        Board board = new Board("r3kb1r/8/8/4K3/8/8/8/2BQ1B2 w - -");
        long movesNb = Perft.search(board, 5);
        System.out.println(System.currentTimeMillis()-millis);

        assertThat(movesNb).isEqualTo(23250211);

        // 3639
    }
}