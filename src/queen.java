public class queen extends piece {
    static rook Arook = new rook(0, 0,true);
    static elephant AnElephant = new elephant(0, 0,true);
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
