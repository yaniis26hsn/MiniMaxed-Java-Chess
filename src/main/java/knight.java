import static java.lang.Math.*;

public class knight extends piece {
        public knight(int row , int col,boolean isBlack) {
        super(row, col , isBlack) ;
        value = 3 ;
    }
    @Override
     boolean CanMakeThemove(Board board,int DestRow, int DestCol){

      if(board.board[DestRow][DestCol] != null) {
         if(IsBlack == board.board[DestRow][DestCol].IsBlack) return false ;
      }

       if(DestCol == col && DestRow == row) return  false; // staying in the same square it not considered a move
       if(!((abs(DestRow - row) == 2  && abs(DestCol - col) == 1 ) || (abs(DestRow - row) == 1  && abs(DestCol - col) == 2) ) ) return  false ; // a smart check 
       // we check if not (!) a possible move , we could do the inverse , if possible return true but , but we don t want to do that 
       // cz even if it is a normal knight move (L move) , it doesnt mean that it is valid , for exapmle if it is protecting the king
       // but if it is not a normal knight move (not L move) it is of course invalid (we return false)
       return true ;
     }


}

