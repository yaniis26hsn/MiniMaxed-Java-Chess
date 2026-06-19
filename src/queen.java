public class queen extends piece {
    static rook Arook = new rook(0, 0,true);
    static elephant AnElephant = new elephant(0, 0,true);
    // even there are static , there would be no issue with minimax cz there would be no interfered calls 
    // for each call , the queen sets the coords for it self and then directly modify the calculations then another takes the static ones
    // there is no two calls in the same time (one after the other)

    // instead of creating a new ones each time , we just modify their coordinates 
     public queen(int row , int col,boolean isBlack) {
        super(row, col , isBlack) ;
        value = 8 ;
    }
    @Override
     boolean CanMakeThemove(Board board,int DestRow, int DestCol)
    {
         Arook.col = col ;
         Arook.row = row ; 
         Arook.IsBlack = IsBlack ;
         AnElephant.col = col ;
         AnElephant.row = row ;
         AnElephant.IsBlack = IsBlack ;
        return(Arook.CanMakeThemove(board, DestRow, DestCol) || AnElephant.CanMakeThemove(board, DestRow, DestCol)) ;
    };
    
    
}
