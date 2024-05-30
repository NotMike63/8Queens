public class main {
    public static void main(String[] args) {
        int restarts = 0;
        ChessBoard chess = new ChessBoard(8);
        chess.start();
        while (chess.getH() != 0) {
			System.out.println("Current h: " + chess.heuristicScore());
			System.out.println("Current State: ");
			chess.printBoard();
            chess.transitionModel();
            if (chess.getH() != 0) {
                restarts++;
            }
        }

        System.out.println("Solution found!");
        System.out.println("State changes: " + chess.stateChanges);
        System.out.println("Restarts: " + restarts);
    }
}