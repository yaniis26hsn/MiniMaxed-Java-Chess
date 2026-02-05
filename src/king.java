import static java.lang.Math.* ;
public class king extends piece {
     public king(int row , int col , boolean isBlack) {
        super(row, col,isBlack) ;
        value = 999999999 ; // we just have to make sure that in minimax we want pass the Integer.MAX_VALUE
        // and since we have only one king it totally fine with this is fine we are still away 
        // this value represnts ifinity we will use it in MINIMAX
    }
    
    @Override
     boolean CanMakeThemove(Board board,int DestRow, int DestCol)
    {
        
        if(DestRow == row && DestCol == col ) return false ;

        if(!(abs(DestCol - col)<=1 && abs(DestRow - row) <=1)) return false ;
    
        return true ;
    };
      // a better idea . no check or checkmate as usual games . you will need eat the king by your hands .
      // we will just need to check if the king is still somewhere in the board to declare mate  
      boolean isChecked(Board board){
       // we will pass through all the squars . if there is a piece with a differant color that can 
       // make a move to the square where the currant king is(the currant object) , the king is checked
      // this means we will return directy a true inside the loop
      // if there wasn't , this means there is no check ,we we will return false 
      for(int i = 0;i<8;i++){
       for(int j = 0;j<8;j++){
        if(board.board[i][j] != null &&board.board[i][j].isMoveLegal(board, row, col) && board.board[i][j].IsBlack != this.IsBlack) return true ; // the row and col are the king's currant coordinates
        if(board.board[i][j] != null && board.board[i][j].CanMakeThemove(board, row, col) && board.board[i][j].IsBlack != this.IsBlack) return true ; // the row and col are the king's currant coordinates
      // short circut evaluation
      // this may sound weird but a pinned piece can deliver a check (also chekmate ) so we should use 
      //can makethemove insted of isMoveLegal cz even if a move was't legal (cz it was pinned)
      // is still checks the king 
    }
      }

      return false ; // this means that no piece can check
    }
}
