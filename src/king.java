import static java.lang.Math.* ;
public class king extends piece {

  boolean DidntMove = true ; // cz he haven't moved yet 
     public king(int row , int col , boolean isBlack) {
        super(row, col,isBlack) ;
        value = 999999999 ; // we just have to make sure that in minimax we want pass the Integer.MAX_VALUE
        // and since we have only one king it totally fine with this is fine we are still away 
        // this value represnts ifinity we will use it in MINIMAX
    }
    
    @Override
     boolean CanMakeThemove(Board board,int DestRow, int DestCol)
    {
       boolean normalMove = ((DestRow != row || DestCol != col) && (abs(DestCol - col)<=1 && abs(DestRow - row) <=1) && (board.board[DestRow][DestCol] != null && board.board[DestRow][DestCol].IsBlack != this.IsBlack)) ;
        if(normalMove) return true ;
         else if(DidntMove && (DestCol == 7 || DestCol == 0) && (DestRow==row) && board.board[DestRow][DestCol] != null && board.board[DestRow][DestCol] instanceof rook && board.board[DestRow][DestCol].IsBlack == this.IsBlack  &&( (rook) board.board[DestRow][DestCol]).DidnotMove && !this.isChecked(board)){
           // that condition means that the player wanned to castle by choosing to move his king to his rook
           // here are the remaining conditional rules :
           for(int i = min(DestCol , col) + 1 ; i < max(DestCol , col) ; i ++){
            if(board.board[row][i] != null ) return false ; // nothing in between king and rook
           }
            for(int i = min(DestCol , col) + 1 ; i < max(DestCol , col) ; i ++){
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    if(i != 1 && board.board[j][k] != null && board.board[j][k].IsBlack != this.IsBlack && board.board[j][k].CanMakeThemove(board, row, i) && !board.board[j][k].isPinnedMove(board,row, i)) return false ;
                    // we do verify that no opponent piece can attack what is between the king and the rook 
                    // i used can makeTheMove and isPinned instead of isLegalMove to avoid infinite loop 
                  //  we removed i == 1 cz in castling queen side we don't need to check that no piece can attack it
                }
            }
           }
            
         }else if( board.board[DestRow][DestCol] != null && this.IsBlack == board.board[DestRow][DestCol].IsBlack) return false ; 
         // since it wasn't castling you can't take your own piece (not litterally)
         
       
          
    
        return false ; // nor normal move nor castling ,
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
