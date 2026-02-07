public abstract  class piece {
    
    int row ;
    int col ;
    int value ;
    boolean IsBlack ;
    public piece( int r , int c,boolean isBlack) {
        this.col = c ;
        this.row = r ;
        this.IsBlack = isBlack ;

    }
    
   
    
    abstract boolean CanMakeThemove(Board board,int DestRow, int DestCol);
         // no need to check that the coord are in 0-7 it we who gonna send them and there gonna be nowhere to click to send 8 or more 
         // this fnc as it name indicates it doesn t change theBoard it only check if we do that move
         
  boolean isMoveLegal(Board board , int DestRow , int DestCol){
    
      return (!isPinnedMove(board, DestRow, DestCol) && this.CanMakeThemove(board, DestRow, DestCol) ) ;
   }
   boolean isPinnedMove(Board board,int DestRow ,int DestCol){
    // this sees if the move is not legal cz the piece is pinned 
      Board tempBoard = new Board(board) ;
      tempBoard.board[row][col] = null ;
     tempBoard.board[DestRow][DestCol] = this ;
     
   // i wanted to do : return getKing(tempBoard,this.IsBlack).isChecked(tempBoard) ;
   // but there would have an exception in case of that getKing will return null which is technically impossible
   // cz the game will be ended once the king is dead
   king k = getKing(tempBoard, this.IsBlack) ;
   if(k == null) return false ; // technically impossibe but for preventing an exception
   else return k.isChecked(tempBoard) ;
   }
   king getKing(Board board,boolean isBlack){
     for(int i = 0 ; i<8 ; i++){
        for(int j = 0 ; j < 8 ; j++){
            if(board.board[i][j] instanceof king && board.board[i][j].IsBlack == isBlack) return (king) board.board[i][j] ;
        }
     }
     return null ; // incase of any error . however won t call this fnc after a checkmate so mostly this line won t be executed 
   }
    boolean CanMakeAtLeastOneMove(Board board){
        for(int i = 0 ; i < 8 ; i ++){
            for(int j = 0 ; j< 8 ; j++ ){
                if(this.isMoveLegal(board, i, j)) return true ;
            }
        }
        return false ;
       }
    
    
}
