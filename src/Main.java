public class Main {
    enum Mode { BlackVsAi , WhiteVsAi , TwoPlayers } 
    // we dont need a Main obj to call these methods
    static void finalShow(Board.GameState status){
            // gui implementation
           }
          static Mode getMode(){
            // GUI implementation
            // here i can informe the player that he has to take the king by his own hands 
            return Mode.WhiteVsAi ;
        }
        public static void main(String[] args) {
          
      
       
        Board board = new Board() ;
        // there would be two ways to play : single player with differant levels (the depth of minimax)
        // and two players mode 
       Player Player1 ;
       Player Player2 ;
       // player 1 is the always the white one 
       Mode mode = getMode() ;
       if(mode== Mode.WhiteVsAi){
        Player1 = new Player() ;
        Player2 = new AiPlayer() ;
         Player1.isBlack = false ;
         Player2.isBlack = true ;
        ((AiPlayer) Player2).level =  ((AiPlayer) Player2).getLevel() ; 
       }else if (mode== Mode.BlackVsAi){
        Player2 = new Player() ;
        Player1 = new AiPlayer() ;

         Player1.isBlack = false ;
         Player2.isBlack = true ;
        ((AiPlayer) Player1).level =  ((AiPlayer) Player1).getLevel() ; 

       }else // 2 players 
       {
        Player1 = new Player() ;
        Player2 = new Player() ;
        Player1.isBlack = false;
        Player2.isBlack = true ;
       }
      // now the game loop 
       int[] move = {0 , 0 , 0 , 0} ;
      do { 
        board.showBoard(); 
        do { 
            move = Player1.getPlayerMove(board) ;
        } while ( board.board[move[0]][move[1]] == null || !board.board[move[0]][move[1]].isMoveLegal(board, move[2], move[3]));
         // like getPlayer move will just need to give us the chosen coords by the player that it 
         // no need to check that it is legal
          board.makeMove(board.board[move[0]][move[1]] ,move[2], move[3],Player1);
          board.showBoard(); 
          if(board.getGameState(Player1.isBlack) != Board.GameState.Draw) break ;
          else{
            do { 
            move = Player2.getPlayerMove(board) ;
        } while (board.board[move[0]][move[1]] == null || !board.board[move[0]][move[1]].isMoveLegal(board, move[2], move[3]));
       // it is safe cz of short circut evaluation
          board.makeMove(board.board[move[0]][move[1]] ,move[2], move[3],Player2);
          board.showBoard(); 
          } 
      } while (board.getGameState(Player1.isBlack) == Board.GameState.Draw);
      // draw means ongoing overdraw means over by draw 

      
      finalShow(board.status) ; // we will show a final stuff (statistics and score and)

    }
}
