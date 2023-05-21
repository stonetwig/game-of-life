public class GameServer extends Thread implements Runnable {
    private boolean running = true;
    private static final Board board = new Board();
    private int genEquals = 0;
    public void run() {
        board.initGenerations();
        board.logGeneration();
        while (running) {
            try {
                Thread.sleep(1000);
                board.setNextGen();
                if (board.isPreviousAndCurrentGenTheSame()) {
                    genEquals++;
                    if (genEquals > 4) {

                    }
                }
                var currentGen = board.getCurrentGen();

                GameOfLife.updateBoard(currentGen, false);

            } catch (InterruptedException e) {
                running = false;
                e.printStackTrace();
            }
        }
    }

}
