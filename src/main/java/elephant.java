import static java.lang.Math.*;
 

public class elephant extends piece {
     public elephant(int row , int col,boolean isBlack) {
        super(row, col , isBlack) ;
        value = 3 ;
    }
    
    @Override
     boolean CanMakeThemove(Board board,int DestRow, int DestCol)
    {
      if(board.board[DestRow][DestCol] != null) { 
         if(IsBlack == board.board[DestRow][DestCol].IsBlack) return false ;
      } // you can t take your piece . maybe i ll make a special version where you can do so 

        if(DestRow == row && DestCol == col ) return false ;
        
        if(abs(DestCol - col)!=abs(row - DestRow) ) return false ; 
        
        // we pose the two squares as two points , that forms a line with a formula ax+b , the a must be 1 or -1
        // to englobe both we use abs , here must be equal to 1 , this means abs(DestCol - col)==abs(row - DestRow)
        //if not , it is not a valid elephant move 

        // as alwalys we first only check if it is a normal feel move(a diagnol move), if not it is of course false 
        // now we check if there was nothing between the two squars since the the elephant can t jump
     int distance = abs(DestRow - row) ; // the diagnol(or anti diagnol) distance we were able to use col too since we have checked before 
       // that it is a diagnol move  this means abs(DestCol - col)==abs(row - DestRow)
        if( (DestRow - row)/(DestCol - col) == -1) {
          // we have checked that DestCol - col != 0 up there 
            /*diagnol */
          
        for(int i = 1;i<distance;i++){
          if(board.board[min(row,DestRow)+i][min(col, DestCol)+i] != null) return false ;
        } 
     
      } else{
        /*anti diagnoal move case */
        for(int i= 1 ;i<distance;i++){
           if(board.board[min(row,DestRow)+i][max(col, DestCol)-i] != null) return false ;
        }
      }
     
        return true ;
    }
    
}
