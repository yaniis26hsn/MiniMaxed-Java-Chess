

import javax.swing.*;

public class Player {
    boolean isBlack ;
    boolean myTurn ; // mostly useless but anyways




    int[] getPlayerMove(Board board){
        int fromRow = 6 ;
        int fromCol = 4 ;  // just initilisation
        int toRow  =  4;
        int toCol =  4;
        do{
        // gui implementation
        // using getAllPossible moves function in piece class when the user clicks on a piece 
        // show him the squars where he can go to using that selected piece 
        // i verifying that this move legal before returnig it by putting this in a do while
        }while(board.board[fromRow][fromCol] == null || !board.board[fromRow][fromCol].isMoveLegal(board, toRow, toCol)) ;
        // like this we won't need to check board.board[fromRow][fromCol] != null inside of isMoveLegal and
        // also inside each canMaketheMove 

      int[] move = new int[]{fromRow,fromCol,toRow,toCol} ;
        return move ;
       
    }
      pawn.promotionPiece getPromotionPiece(){
        String[] options = {"Queen", "Rook", "Elephant", "Knight"};
        int choice = JOptionPane.showOptionDialog(null,
            "Promote pawn to:", "Promotion",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        if (choice < 0) return pawn.promotionPiece.Queen;
        return pawn.promotionPiece.values()[choice];
      }
      

    
}
