import static java.lang.Math.* ;
public class rook extends piece{
     public rook (int row , int col,boolean isBlack) {
        super(row, col , isBlack) ;
        value = 5 ;
    }
    

    boolean CanMakeThemove(Board board,int DestRow, int DestCol){
      if(board.board[DestRow][DestCol] != null) {
         if(IsBlack == board.board[DestRow][DestCol].IsBlack) return false ;
         // i could write :  if(board.board[DestRow][DestCol] != null && IsBlack == board.board[DestRow][DestCol].IsBlack ) return false ; 
         //cz of short circut evaluation 
      }
      
        if(DestCol != col && DestRow != row || DestCol == col && DestRow == row) return false ; // a rook can t change both or keep both
        // if it didn t change it is genearly a valid one . we will deal with other cases down here

        if(DestCol == col){
           // moving vertically 
         int MinRow = min(row,DestRow) ;
            int MaxRow = max(row,DestRow)  ;
        for(int i = MinRow+1;i<MaxRow;i++ ) {
        if(board.board[i][col] != null) return false ;
     }

        }
        else{
            // moving horizantly
            int MinCol = min(col,DestCol) ;
          int MaxCol = max(col,DestCol) ;
           for(int i = MinCol+1;i<MaxCol;i++ ) {
        if(board.board[row][i] != null) return false ;
     }
        }
       
       
      // later well check if pinned and other or in check and the other cases ..
       
       return true ; // the last instruction only if all conditions were satisfied it gonna be executed
    }
    
    
}
