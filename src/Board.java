public class Board {
  

  enum GameState {BlackWon , WhiteWon , Draw , OverDraw} ;
  // Draw means ongoing and overDraw means the game is over by draw 
  //  // for checkmate the idea :
     
        //. no checkmate as usual games . you will need eat the king by your hands .
      // we will just need to check if the king is still somewhere in the board to declare mate  
     
  int lastCapture = 0 ;
  pawn possibleEnPassentPawn = null ;
  // save for each board the pawn that moved two squars after the oponent move i will set it to null
  // if a player moved a pawn two squars it will be saved here . he can get an enPassant 
  GameState status = GameState.Draw; // default value 
    piece[][] board = new piece[8][8]  ;

    Board(){
      for(int i=0;i<8;i++){
        board[6][i] = new pawn(6,i,false) ;
        board[1][i] = new pawn(1,i,true) ;
        if(i==0 || i == 7) {
        board[7][i] = new rook(7,i,false) ;
        board[0][i] = new rook(0,i,true) ;
        }
        
        if(i==1 || i == 6){
         board[7][i] = new knight(7,i,false) ;
        board[0][i] = new knight(0,i,true) ;
        }
        if(i==2 || i == 5){
         board[7][i] = new elephant(7,i,false) ;
        board[0][i] = new elephant(0,i,true) ;
        }
      
      if(i==3){
         board[7][i] = new queen(7,i,false) ;
        board[0][i] = new queen(0,i,true) ;
      }
       if(i==4){
         board[7][i] = new king(7,i,false) ;
        board[0][i] = new king(0,i,true) ;
      }
      
    }

    }
    
   
   void showBoard(){

    }
    boolean isOverByDraw(boolean playerIsBlack){
      // the color refers to the player
      // for safety we call this fnc after calling ifcheckmate cz or after making sure 
      // or after checking that there is no checkmate cz it can in case of checkmate 
      // it can signal that it overByDraw (by returning true) (case of lake of material)
      // however we will check locally that it is not
      if(status == GameState.BlackWon || status == GameState.WhiteWon) return false ;
      // first case :over than 50 moves 
      if(lastCapture == 50) return true ;
      // first case: stale mate
       // i could do another implementation by calling getAllPossibleMovesEvaluated() fnc that
       // exists in MiniMax class and we check the returned array
       // if it was empty so it is staleMate 
       // however this one down here is a bit faster cz we stop once we found a single possible one 
       // but the other could save some lines of code 
      
        boolean staleMate = true ;
        outer:
      for (int i = 0; i < 8; i++) {
        for(int j = 0 ; j < 8 ; j++){
          if( board[i][j] != null && board[i][j].IsBlack == playerIsBlack && board[i][j].CanMakeAtLeastOneMove(this)  )
            {
            staleMate = false ;
            break outer; 
            } 
      }
      }
      if(staleMate) return true ;

      // case 2 : insuffisiant material
      int PiecesValueSum = 0 ;
     
      piece lastPiece = null ;
      piece lasterPiece = null ; // laster is not an english word but it refers to the one the two last non king found piece
      
      // if they don't exist they will stay null
      // we use this two var for the case where K+E vs K+E (where ELEPHANTs don t have the same color)
      // in other cases they are not considered and they dont affect any decision
       for (int i = 0; i < 8; i++) {
        for(int j = 0 ; j < 8 ; j++){
          if(board[i][j] != null) {
            if(board[i][j] instanceof rook || board[i][j] instanceof pawn || board[i][j] instanceof queen ) return false ;
            PiecesValueSum += board[i][j].value ;  
            if(board[i][j] instanceof elephant || board[i][j] instanceof knight) {
              if(lastPiece == null) lastPiece = board[i][j] ;
              else lasterPiece = board[i][j] ;
            }
          }
           if(PiecesValueSum > 2000000004) return false ; // this means we have more than two bishops and the two kings
            // we had sure that there is no staleMate up there so it safe 
          
        }}
        if(PiecesValueSum < 2000000004) return true ; // we are sure we have no pawns
        // if we arrived here we are sure that no stalemate no checkmate and we have exactly two 3 points valued piece(bishop/elephant)
        
        if(lastPiece instanceof elephant && lasterPiece instanceof elephant && ((lasterPiece.IsBlack && lastPiece.IsBlack)||(!lasterPiece.IsBlack && !lastPiece.IsBlack) )) return true ;
        else return false ;
      
    }
    GameState getGameState(boolean playerIsBlack){
      // the color refers to the player 
      // this methode updates the game status so it must be called with each move
     if(status==GameState.BlackWon || status == GameState.WhiteWon) return status ; // game over someone won , we modified the status in the makeMove fnc
      if(this.isOverByDraw(playerIsBlack)){
        this.status = GameState.OverDraw ;
         return GameState.OverDraw ;} 
      return GameState.Draw ; // the basic state (if the game wasn t over)
    
    }
    public Board(Board board){
      this.status = board.status ;
      this.lastCapture = board.lastCapture ;
      if(board.possibleEnPassentPawn != null)
      this.possibleEnPassentPawn = new pawn(board.possibleEnPassentPawn.row,board.possibleEnPassentPawn.col,board.possibleEnPassentPawn.IsBlack)  ;
     else this.possibleEnPassentPawn = null ;
      for(int i = 0 ; i<8 ; i++){
        for(int j = 0 ; j<8 ; j++){
          if(board.board[i][j] == null) this.board[i][j] = null ;
         else if(board.board[i][j] instanceof pawn){
            this.board[i][j] = new pawn(board.board[i][j].row,board.board[i][j].col,board.board[i][j].IsBlack)  ;
          }
           else if(board.board[i][j] instanceof elephant){
            this.board[i][j] = new elephant(board.board[i][j].row,board.board[i][j].col,board.board[i][j].IsBlack)  ;
          }
           else if(board.board[i][j] instanceof queen){
            this.board[i][j] = new queen(board.board[i][j].row,board.board[i][j].col,board.board[i][j].IsBlack)  ;
          }
           else if(board.board[i][j] instanceof rook){
            this.board[i][j] = new rook(board.board[i][j].row,board.board[i][j].col,board.board[i][j].IsBlack)  ;
          }
           else if(board.board[i][j] instanceof knight){
            this.board[i][j] = new knight(board.board[i][j].row,board.board[i][j].col,board.board[i][j].IsBlack)  ;
          }
          else if(board.board[i][j] instanceof king){
            this.board[i][j] = new king(board.board[i][j].row,board.board[i][j].col,board.board[i][j].IsBlack)  ;
          }
          
        }
      }
    }
    
       void DesactivateEnPassant(){
      // this must be called after one move from that the openent dont take by enpassent 
       this.possibleEnPassentPawn = null ;

    }

  
 int evaluateBoardForAPlayer(boolean color){
  int PlayerEvaluation = 0 ;
  int OpponantEvaluation = 0 ;
     for (int i = 0; i < 8; i++) {
    for(int j = 0 ; j<8 ;j++){
      if(board[i][j] != null   ){
        if(board[i][j].IsBlack == color) PlayerEvaluation += board[i][j].value ;
        else OpponantEvaluation += board[i][j].value ; // an opponant piece 
      }
        

    }
 }
      return (PlayerEvaluation - OpponantEvaluation) ;
 }
    
    void makeMove(piece ThePiece,int DestRow ,int DestCol,Player player){
     
     if(ThePiece.isMoveLegal(this, DestRow, DestCol)){
       if(ThePiece instanceof king && board[DestRow][DestCol] != null && ThePiece.IsBlack == board[DestRow][DestCol].IsBlack){
        // since you could take your own piece (by returning isMoveLegale a true value) it means you were castiling
           ((king) ThePiece).DidntMove = false ;
              ( (rook) board[DestRow][DestCol]).DidnotMove = false ;
               piece ThatRook = this.board[DestRow][DestCol] ;
               this.lastCapture++ ;
               if(DestCol == 7){
                board[DestRow][4] = ThatRook ;
                board[DestRow][7] = null ;
                board[DestRow][6] = ThePiece ; // the king was saved in the params (Thepiece) not like the rook
               // no need to modify the row in castling 
               ThatRook.col = 4 ;
               ThePiece.col = 6 ;
              }else{ // destCol == 0 castling queen side 

                board[DestRow][4] = null ;
                board[DestRow][0] = null ;
                board[DestRow][2] = ThePiece ;
                board[DestRow][3] = ThatRook ;
                 ThatRook.col = 3 ;
                 ThePiece.col = 2 ;

               }
               this.DesactivateEnPassant(); 
            
      }else{
        
       piece oldPiece = this.board[DestRow][DestCol] ;
        this.lastCapture = (oldPiece == null || !(ThePiece instanceof pawn)) ? ++this.lastCapture : 0 ;
        // we had to put the ++ before the var otherwise it will assign the old value before incrementation
        // than it will increment . it will work too since it is same var in both sides , but like this it seems better
        
        // this methode could be just a void method with anothor name (not a constructor) 
        if(oldPiece instanceof king){
          this.status =   (ThePiece.IsBlack== true) ?  GameState.BlackWon:  GameState.WhiteWon ;
   
                }
                if(ThePiece instanceof king) ((king) ThePiece).DidntMove = false ;
                if(ThePiece instanceof rook) ((rook) ThePiece).DidnotMove = false ;
            
           int oldCol = ThePiece.col ;
           int oldRow = ThePiece.row ;
           
            this.board[DestRow][DestCol] = ThePiece ;
           this.board[oldRow][oldCol] = null ;
            ThePiece.row = DestRow ; 
            ThePiece.col = DestCol ; 

            //store move in array of moves. you should store the takken piece too or null if empty square
            // and you may store the old boards if the player wants to undo a move 
        if(ThePiece instanceof pawn && (ThePiece.IsBlack && DestRow == 7 || !ThePiece.IsBlack && DestRow == 0 ))
             ((pawn) ThePiece).Promote(this,DestRow,DestCol,player)  ;

            this.DesactivateEnPassant();
            
      }

     }
    else{System.out.println("Error: can't make that move");} 
    // later we may turn this to an exeption 
    // we are not expecting to call this constructor without checking if it is a valid move 
    // so the line was just made for an extra safety 
    // if you want to save the played moves you can make a class (or at least a struct if it exists in java) called move where you store starting coords and dest coords
    // and the moving piece and the takken piece(null if moved to an empty square)
    // at the end of of the part where the move is legal call a func add the move to an array of the type move
     }

}

 