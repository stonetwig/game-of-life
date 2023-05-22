public class GameServer extends Thread implements Runnable {
    private boolean running = true;
    private static final Board board = new Board();
    private int genEquals = 0;
    public void run() {
        board.initGenerations();
        board.logGeneration();
        while (running) {
            var restartGame = false;
            try {
                Thread.sleep(1000);
                board.setNextGen();
                if (board.isPreviousAndCurrentGenTheSame()) {
                    genEquals++;
                    if (genEquals > 4) {
                        genEquals = 0;
                        board.initGenerations();
                        restartGame = true;
                    }
                }
                var currentGen = board.getCurrentGen();

                GameOfLife.updateBoard(currentGen, restartGame);

            } catch (InterruptedException e) {
                running = false;
                e.printStackTrace();
            }
        }
    }

}
