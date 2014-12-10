public class NQueens {
    
    private static long[] boards = new long[64];
    private static int solutions = 0;
    private static boolean[][] displayBoard = new boolean[8][8];
    
    private static void generateBoards(long[] boards){  
        
        for(int i = 0;i<64;++i){  
            boards[i] = boards[i] | (1L << 63-i);
//            System.out.println("\ni = "+i+" Board before: "+Long.toBinaryString(boards[i]));
            
            //Setting down bits to one
            int shiftLeft = 63-i-8;
            while(shiftLeft >= 0){  
                boards[i] = boards[i] | (1L << shiftLeft);
                shiftLeft = shiftLeft-8;
            } 
            
            
            //Setting left (diagonal) bits to one
            shiftLeft = 63-i-7;
            int currentSetBitIndex = i%8;
            int diagonalBitIndex = (i+7)%8;
            int i2 = i;
            while(diagonalBitIndex < currentSetBitIndex && i2<=56){
                boards[i] = boards[i] | (1L << shiftLeft);
                shiftLeft = shiftLeft-7;
                i2+=7;
                currentSetBitIndex = i2%8;
                diagonalBitIndex = (i2+7)%8;
            }
            
            //Setting right (diagonal) bits to one 
            shiftLeft = 63-i-9;
            currentSetBitIndex = i%8;
            diagonalBitIndex = (i+9)%8;
            i2 = i;
            while(diagonalBitIndex > currentSetBitIndex && i2<56){
                boards[i] = boards[i] | (1L << shiftLeft);
                shiftLeft = shiftLeft - 9;
                i2+=9;
                currentSetBitIndex = i2%8;
                diagonalBitIndex = (i2+9)%8;
            }
//            System.out.println("Board after: "+Long.toBinaryString(boards[i]));
        }
    }
    
    public static boolean isPlaceable(long board, int position){
        return ((1L << 63-position) & board) == 0; 
    } 
    
    public static boolean solve(long board, int position){ 
        if(position>63) {solutions++;printBoard();return true;} 
        
        long saveBoard = board;
     
        for(int i = 0; i <= 7 ; ++i){
            
            if(!isPlaceable(board, position+i))continue;
            displayBoard[position/8][i] = true;
            board = board | boards[position+i];
            solve(board, position+8);
            board = saveBoard;
            displayBoard[position/8][i] = false;
        }  
        return false;
    }
     
    private static void printBoard(){
        System.out.println();
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if(displayBoard[i][j])System.out.print("Q ");
                else System.out.print("_ ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void main(String[] args){
        generateBoards(boards);
        solve(0L, 0);
        System.out.println("No of solutions found = "+solutions);
    }
}
