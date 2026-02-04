import static java.lang.Math.* ;
public class pawn extends piece {
   enum promotionPiece {Rook , Elephant , Queen ,Knight  } 

   // boolean enPassant = false ;
    // this must became false after one move from that the openent dont take by enpassent and becoms true after the 2 squars first move
     
    public pawn(int row , int col,boolean isBlack) {
       super(row, col , isBlack) ;
       value = 1 ;
    }
     
    
    
    boolean CanMakeThemove(Board board,int DestRow, int DestCol){
    
      if(board.board[DestRow][DestCol] != null) {
         if(IsBlack == board.board[DestRow][DestCol].IsBlack) return false ;
      }

       if(DestCol == col && DestRow == row) return  false; // staying in the same square doesn t represant a move 
         if(!((DestCol == col && abs(DestRow-row) <= 2) || (abs(DestCol - col) == 1 && (DestRow == row + 1 || DestRow == row - 1)) ) ) return false ;
        // the only possible moves for a pawn in any situation
        // Up here we don t need to seperate between the first and the non first move it's general 
        if(board.board[DestRow][DestCol] != null && this.IsBlack ==board.board[DestRow][DestCol].IsBlack) return false ;
        // we need to make sure that false == false is true 
        // you can't take same colored pieces
       if(IsBlack){
         // here we seperate between black and white pawns cz there is a diff in the coordinates 
         // we start by black pawns
         if(DestCol == col && board.board[DestRow][DestCol] == null){
            // forward move , no taking 
            if(row == 1){
              // this means that it is the firstMove
              if((DestRow - row) <= 2 && board.board[row+1][col]== null && !isPinnedMove(board,DestRow ,DestCol)){
              board.possibleEnPassentPawn = this ;
              // it is the only case where we need to make sure that it is not pinned inside the funcion can make the move
              // cz we want to make sure that this we will be played before we modify board.possibleEnPassentPawn = this ;
              // cz it would be changed even if not played
               return true ;
              } 

            }
            else { // row is bigger than one and 0 as we define a pawn
              if(DestRow - row == 1) return true ;
            }

         }else if(abs(DestCol - col) == 1 && DestRow - row == 1){ // captures now 
            if(board.board[DestRow][DestCol] != null) return true ;// usual capture
            // en passant capture 
            else if(col < 7 && board.possibleEnPassentPawn !=null && board.board[row][col+1]==board.possibleEnPassentPawn ) return true ;
            else if(col > 0 && board.possibleEnPassentPawn !=null && board.board[row][col-1]==board.possibleEnPassentPawn ) return true ;
           
               
            // it is important that we let this case as the last one so that in case of any mistake , the usual moves won t get affected
         }

       } else if(!IsBlack){
         // here we seperate between black and white pawns cz there is a diff in the coordinates 
         // we start by black pawns
         if(DestCol == col && board.board[DestRow][DestCol] == null){
            // forward move , no taking 
            if(row == 6){
              // this means that it is the firstMove
              if((DestRow - row) <= 2 && board.board[row-1][col]== null && !isPinnedMove(board,DestRow ,DestCol)){
              board.possibleEnPassentPawn = this ;
              // it is the only case where we need to make sure that it is not pinned inside the funcion can make the move
              // cz we want to make sure that this we will be played before we modify board.possibleEnPassentPawn = this ;
              // cz it would be changed even if not played
               return true ;
              } 

            }
            else { // row is bigger than one and 0 as we define a pawn
              if(row- DestRow== 1) return true ;
            }

         }else if(abs(DestCol - col) == 1 &&  row-DestRow  == 1){ // captures now 
            if(board.board[DestRow][DestCol] != null) return true ;// usual capture
            // en passant capture 
            else if(col < 7 && board.possibleEnPassentPawn !=null && board.board[row][col+1]==board.possibleEnPassentPawn ) return true ;
            else if(col > 0 && board.possibleEnPassentPawn !=null && board.board[row][col-1]==board.possibleEnPassentPawn ) return true ;
           
            // it is important that we let this case as the last one so that in case of any mistake , the usual moves won t get affected
         }
      }
       return false ; // mostly we want arrive to it but up we will return true to all the valid cases so if it wasn t valid 
       // it is invalid so this one last instruction covers the first ones 
       // also  the first instructions covers this one but i ll keep both for safety 
       // this fnc we coded to be readable cz it is complecated , i could do a lot of technicall optimizations
      }
    
      void Promote(Board board , int Row , int Col , Player player){
        promotionPiece PromotionPiece = player.getPromotionPiece() ;
        // the compiler needs to know what type of player is playing to know which version of
        // getPromotionPiece to call cz the fnc exists in player and aiplayer classes. however
        //  we send the player from main to move clss and finally here 
        if(PromotionPiece == promotionPiece.Rook){
         board.board[Row][Col] = new rook(Row, Col, player.isBlack) ;
        }else  if(PromotionPiece == promotionPiece.Elephant){
         board.board[Row][Col] = new elephant(Row, Col, player.isBlack) ;
        }else   if(PromotionPiece == promotionPiece.Knight){
         board.board[Row][Col] = new knight(Row, Col, player.isBlack) ;
        }else // queen case
         {
         board.board[Row][Col] = new queen(Row, Col, player.isBlack);
        }
         
      }

}