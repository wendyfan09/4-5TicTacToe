package tictactoe;
import javax.swing.JButton;
import java.util.Random;
public class CPU	{
	static char [] board = new char [20];
    static int [] bestMoves = new int [20];
    static   final   int   INFINITY = 100 ;   
    static   final   int   WIN = +INFINITY ;  
    static   final   int   LOSE = -INFINITY ;  
    static   final   int   DOUBLE_LINK = INFINITY / 3;     
    static   final   int   INPROGRESS = 1 ;  
    static   final   int   DRAW = 0 ;    
    static   final   int [][] WIN_STATUS =   {  
    	{ 0, 1, 2 ,3}, 			{1,2, 3, 4}, 		{5, 6, 7, 8},    {6,7, 8, 9},
		{10,11, 12, 13},       {11, 12, 13, 14},   {15,16, 17, 18},   {16, 17, 18, 19}, 
		{0, 5, 10, 15},   {1, 6, 11, 16},  {2, 7, 12, 17}, {3, 8, 13, 18}, {4, 9, 14, 19},
		{1, 7, 13, 19},   {0, 6, 12, 18},   {4, 8, 12, 16},    {3, 7, 11, 15 }
  };  
    public   static int   minmax( char [] board,   int   depth){  
        int   index = 0;  
         
        int   bestValue = - INFINITY ;  
        for ( int   pos=0; pos<20; pos++){  
               
              if (board[pos]== 'E' ){  
                   board[pos] =   'X' ;  
                    
                    int   value = max(board, depth, - INFINITY , + INFINITY );  
                    if (value>bestValue){  
                         bestValue = value;  
                         index = 0;  
                         bestMoves[index] = pos;  
                   } else  if (value==bestValue){  
                         index++;  
                         bestMoves[index] = pos;  
                   }  
                     
                   board[pos] =   'E' ;  
             }  
               
       }  
         
        if (index>1){  
             index = ( new   Random (System. currentTimeMillis ()).nextInt()>>>1)%index;  
       }  
        return   bestMoves[index];  
         
 }  
 
 public  static  int   max( char [] board,   int   depth,   int   alpha,   int   beta){  
         
        int   evalValue =   gameState (board);  
         
        boolean   isGameOver = (evalValue== WIN   || evalValue== LOSE   || evalValue== DRAW );  
        if (depth==0 || isGameOver|| beta<=alpha){  
              return   evalValue;  
       }  
         
        int   bestValue = - INFINITY ;  
        for ( int   pos=0; pos<20; pos++){  
               
              if (board[pos]== 'E' ){  
                    // try  
                   board[pos] =   'X' ;  
                     
                    //   maximixing  
                   bestValue = Math. max (bestValue, min(board, depth-1, Math. max (bestValue, alpha), beta)); 
                     
                    // reset  
                   board[pos] =   'E' ;  
             }  
               
       }  
        	return evalValue;
 }  
 public   static int   min( char [] board,   int   depth,   int   alpha,   int   beta){  
         
        int   evalValue =   gameState (board);  
         
        boolean   isGameOver = (evalValue== WIN   || evalValue== LOSE   || evalValue== DRAW );  
        if (depth==0 || isGameOver||alpha>=beta){  
              return   evalValue;  
       }  
         
        int   bestValue = + INFINITY ;  
        for ( int   pos=0; pos<20; pos++){  
               
              if (board[pos]== 'E' ){  
                    // try  
                   board[pos] =   'O' ;  
                     
                    //   minimixing  
                   bestValue = Math.min(bestValue, max(board, depth-1, alpha, Math.min(bestValue, beta)));  
                  
                    // reset  
                   board[pos] =   'E' ;  
             }  
               
       }  
        return   evalValue;  
         
 }   
 static   final   int []   INITIAL_POS_VALUE   = {  
     3, 4, 3, 4, 3,   
     2, 4, 5, 4, 2, 
     2, 4, 5, 4, 2,
     3, 4, 3, 4, 3
};  
 public   static int   gameState ( char []   board ) {  
     int   result =   INPROGRESS ;  
     boolean   isFull =   true ;  
     int   sum = 0;  
     int   index = 0;  
     // is game over?  
     for ( int   pos=0; pos<20; pos++){  
           char   chess = board[pos];  
           if ( 'E' ==chess){  
                isFull =   false ;  
          } else {  
                sum += chess;  
                index = pos;  
          }  
    }  
      
     boolean   isInitial = (sum== 'X' ||sum== 'O' );  
     if (isInitial){  
           return   (sum== 'X' ?1:-1)*INITIAL_POS_VALUE[index];  
    }  
      
     // is Max win/lose?  
     for ( int [] status :   WIN_STATUS ){  
           char   chess = board[status[0]];  
           if (chess== 'E' ){  
                 break ;  
          }  
           int   i = 1;  
           for (; i<status.length-1; i++){  
                 if (board[status[i]]!=chess){  
                       break ;  
                }  
          }  
           if (i==status.length-1){  
                result = chess== 'X'   ?   WIN   :   LOSE ;  
                 break ;  
          }  
    }  
      
     if (result!= WIN   & result!= LOSE ){  
            
           if (isFull){  
                 // is draw  
                result =   DRAW ;  
          } else {  
                 // check double link  
                 // finds[0]->'x', finds[1]->'o'  
                 int [] finds =   new   int [2];  
                 for ( int [] status :   WIN_STATUS ){  
                       char   chess =   'E' ;  
                       boolean   hasEmpty =   false ;  
                       int   count = 0;  
                       for ( int   i=0; i<status.length-1; i++){  
                             if (board[status[i]]== 'E' ){  
                                  hasEmpty =   true ;  
                            } else {  
                                   if (chess== 'E' ){  
                                        chess = board[status[i]];  
                                  }  
                                   if (board[status[i]]==chess){  
                                        count++;  
                                  }  
                            }  
                      }  
                       if (hasEmpty && count>1){  
                             if (chess== 'X' ){  
                                  finds[0]++;  
                            } else {  
                                  finds[1]++;  
                            }  
                      }  
                }  
                  
                 // check if two in one line  
                 if (finds[1]>0){  
                      result = - DOUBLE_LINK ;  
                } else  
                 if (finds[0]>0){  
                      result =   DOUBLE_LINK ;  
                }  
                  
          }  
            
    }  
      
     return   result; 
      
}
	public static boolean doRandomMove(JButton button)	{
		if(button.getText().equals("O") || button.getText().equals("X"))
			return false;
		else	{
			return true;
		}
	}
}