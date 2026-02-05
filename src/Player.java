

public class Player {
    boolean isBlack ;
    boolean myTurn ; // mostly useless but anyways




    int[] getPlayerMove(Board board){
        int fromRow = 1 ;
        int fromCol = 1 ;  // just initilisation
        int toRow  =  2;
        int toCol =  1;
        int fromRow = 6 ;
        int fromCol = 4 ;  // just initilisation
        int toRow  =  4;
        int toCol =  4;
        do{
        // gui implementation
        // using getAllPossible moves function in piece class when the user clicks on a piece 
        // show him the squars where he can go to using that selected piece 
        // i verifying that this move legal before returnig it by putting this in a do while
        }while(!board.board[fromRow][fromCol].isMoveLegal(board, toRow, toCol)) ;
        

      int[] move = new int[]{fromRow,fromCol,toRow,toCol} ;
        return move ;
       
    }
      pawn.promotionPiece getPromotionPiece(){
        // gui must be handled
       return pawn.promotionPiece.Queen ; // just a temprary val
      }
      

    
}
