import static java.lang.Math.*;
import java.util.ArrayList;


// you will will need to empty this treemap at the end of using miniMax do that in getPlayerMove fnc 
public class AiPlayer extends Player {
   static Board tmpBoard = new Board() ;
   // will be used for simulating moves
   int level ; // level represents depth of the MiniMax algorithm

  
@Override
int[] getPlayerMove(Board board) {
   // this will use miniMax alpha beta func , i gave it this name so that we can use polymorphism and runtime polymorphisme in Main
   // so that i won't need to seperate between the modes getPlayerMove() will get the move in both cases ai or user
   // based on what the player will choose as a mode the referances Player1 and Player 2 will refer to a differant obj types ai/human but
   // we won't need to specify that in the main loop we will use polymorphism
   
   // this func can't replace the minimax func cz it doesnt have the same parameters even tho they do the same thing 
   // so this func is just a  man in the middle between minimax and the main it doesn t do much diffrance

   // here we will get The best evaluation possible for miniMax 
   int BestMoveOnTheEngineEvaluation = Integer.MIN_VALUE ; // we will start by -infinty to make sure that 
   // that we will get max evalution cz if initiliaze by 0 if all evals were neg , we will get the 
   int PieceRow = 0 ; int PieceCol= 0 ; int DestRow = 0; int DestCol = 0;
   int[] theMove = {PieceRow , PieceCol , DestRow , DestCol} ;
   int[][] myPossibleNextMoves = getAllPossibleMoves(board,isBlack) ;
   int tmpEval ;
   for (int i = 0; i < myPossibleNextMoves.length; i++) {
    
         tmpBoard = new Board(board) ;
         // we need to create a special board for each call cz if we didnt (by using a static board like the refrence)
         // it will be changed after the calls of makeMove on that board object , we we need to the parent node board to be fixed 
         // to get the same board for each child .the same thing with minimax function 
         tmpBoard.makeMove(tmpBoard.board[myPossibleNextMoves[i][0]][myPossibleNextMoves[i][1]],myPossibleNextMoves[i][2],myPossibleNextMoves[i][3],this) ;
         tmpEval = MiniMaxAlphBeta(tmpBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, this.level, false) ; // depth is same as the game level 
        // we need to start by false cz we search for minimazing our evaluation in the next move cz it will be the opponent turn
         if (BestMoveOnTheEngineEvaluation < tmpEval ) {
         BestMoveOnTheEngineEvaluation = tmpEval ;
         theMove[0] = myPossibleNextMoves[i][0]  ;
         theMove[1] = myPossibleNextMoves[i][1]  ;
         theMove[2] =  myPossibleNextMoves[i][2] ;
         theMove[3] = myPossibleNextMoves[i][3]  ;
       }
           
       
   }

       return theMove ;
       
    }
   int MiniMaxAlphBeta(Board board ,int alpha  , int beta  , int depth,boolean maximazing){
   
       

 if(depth == 0 || board.status == Board.GameState.BlackWon || board.status == Board.GameState.WhiteWon ||board.status == Board.GameState.OverDraw ) {
       return board.evaluateBoardForAPlayer(isBlack) ;
        // we could check here if game over we give infinity(Integer.Max or Min or 0 if draw) as usual algorithms 
        // but since i gave the kings very high evalution , we get to the same point by calling this fnc 
    } else if(maximazing){
      int maxEvaluation = Integer.MIN_VALUE ; // represents -infinity 
      int tmpEval  ;

      int[][] moves = getAllPossibleMoves(board , isBlack) ;
      for (int i = 0; i < moves.length; i++) {
          tmpBoard = new Board(board) ;
          tmpBoard.makeMove(tmpBoard.board[moves[i][0]][moves[i][1]],moves[i][2],moves[i][3],this) ;
          // makeMove meth changes the first parameter that why whe have to send the object of tmpBoard not board
          // even if both obj have the same charachtersitics(row , col ...) but if we have send board obj it will change it 
          // we dont wanna do that cz it is the original and we don t want to change it  
          tmpEval = MiniMaxAlphBeta(tmpBoard, alpha, beta, depth - 1 , false) ;
          maxEvaluation = max(maxEvaluation,tmpEval) ;
          alpha = max(alpha,maxEvaluation) ;
          if(alpha >= beta) break ; 
      }
      return maxEvaluation ;
      }

   
   else  // minimazing
      {
   
      int minEvaluation = Integer.MAX_VALUE ; // represents -infinity 
      int tmpEval = 0 ;

      int[][] moves = getAllPossibleMoves(board,!isBlack) ;
      for (int i = 0; i < moves.length; i++) {
          tmpBoard = new Board(board) ;
           tmpBoard.makeMove(tmpBoard.board[moves[i][0]][moves[i][1]],moves[i][2],moves[i][3],this) ;
          tmpEval = MiniMaxAlphBeta(tmpBoard, alpha, beta, depth - 1 , true) ;
          minEvaluation = min(minEvaluation,tmpEval) ;
          beta = min(beta,minEvaluation) ;
          if(alpha >= beta) break ; 
      }
      return minEvaluation ;
   }
}
 int getLevel(){
   // gui implementation
          return 1 ; // temprary value , the real one  will be defined depanding on user choice
        }

        @Override
         pawn.promotionPiece getPromotionPiece(){
        // we will always promote to a queen . however later i prefer to make the ai choose using
        // minimax algorithm instead
       return pawn.promotionPiece.Queen ; // just a temprary val
      }
      
int[][] getAllPossibleMoves(Board board , boolean IsBlackPlayer) {
  ArrayList<int[]> moves = new ArrayList<>();

 for (int i = 0; i < 8; i++) { 
    for(int j = 0 ; j<8 ;j++){

      for (int k = 0; k < 8; k++) {
        for(int l = 0 ; l<8 ;l++){
         if(board.board[i][j] != null && board.board[i][j].IsBlack==IsBlackPlayer && board.board[i][j].isMoveLegal(board,k,l)){
                  
              moves.add(new int[] {i,j,k,l,0} ) ;
               // 0 is just a temperary value , the real value will be filled using minimax and evalute function
       
                    }
    }
 }
    }
 }

   return moves.toArray(new int[0][]);
}

}
