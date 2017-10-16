import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Connect4 {

	public static void main(String args[]){
		int board[][] = new int[6][7];
		int row = 0;
		Scanner sc = new Scanner(System.in);
		Random rn = new Random();
		printBoard(board);
		while(checkResult(board) == 0 && isTableAvailable(board)){
			int countWhile = 0;
			do{
				if(countWhile > 0){
					System.out.println("Row not Available please try again.");
				}
				System.out.println("Enter row number(1-7) : ");
				row = sc.nextInt() - 1;
				countWhile++;
			}while(!isRowAvailable(board, row));
			board = dropCoin(board, row,1);
			printBoard(board);
			System.out.println();
			if(checkResult(board) != 0 && isTableAvailable(board))
				break;
//			do{
//				row = rn.nextInt(7);
//			}while(!isRowAvailable(board, row));
			row = findMove(board);
			board = dropCoin(board, row, -1);
			printBoard(board);
			System.out.println();
		}
		if(checkResult(board) == 100)
			System.out.println("Player win");
		else if(checkResult(board) == -100)
			System.out.println("Computer win");
		else
			System.out.println("Draw");
	}
	
	public static int findMove(int[][] board){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		int bestMove = 1000;
		int move = -1;
		int num = -1;
		for(int j = 0 ; j < board[0].length ; j++){
			if(isRowAvailable(board, j)){
				board = dropCoin(board, j, -1);
				move = minimax(board,0,1);
				moves.add(move);
				System.out.print(move+" ");
				board = removeCoin(board, j);
				if(move < bestMove){
					bestMove = move;
					num = j;
				}
			}
		}
		System.out.println();
		int count = 0;
		for(int i = 0 ; i < moves.size() ; i++){
			if(moves.get(i) == 0){
				count++;
			}
		}
		if(count == moves.size()){
			Random rd = new Random();
			num = rd.nextInt(7);
		}
		return num;
	}
	
	public static int minimax(int board[][],int depth, int player){
		int score = checkResult(board);
		if(score == 100)
			return score-depth;
		if(score == -100)
			return score+depth;
		if(!isTableAvailable(board))
			return 0;
		if(depth > 7)
			return 0;
		
		if(player == 1){
			int bestMax = -1000;
			for(int i = 0 ; i < board[0].length ; i++){
				if(isRowAvailable(board, i)){
					board = dropCoin(board, i, 1);
					bestMax = Math.max(minimax(board,depth+1,-1), bestMax);
					board = removeCoin(board, i);
				}
			}
			return bestMax;
		}else {
			int bestMin = 1000;
			for(int i = 0 ; i < board[0].length ; i++){
				if(isRowAvailable(board, i)){
					board = dropCoin(board, i, -1);
					bestMin = Math.min(minimax(board,depth+1,1), bestMin);
					board = removeCoin(board, i);
				}
			}
			return bestMin;
		}
	}
	
	public static boolean isRowAvailable(int board[][],int row){
		for(int i = 0 ; i < board.length ; i ++){
			if(board[i][row] == 0)
				return true;
		}
		return false;
	}
	
	public static boolean isTableAvailable(int board[][]){
		for(int i = 0 ; i < board.length ; i++){
			for(int j = 0 ; j < board.length ; j++){
				if(board[i][j] == 0)
					return true;
			}
		}
		return false;
	}
	
	public static int[][] dropCoin(int board[][],int row, int player){
		for(int i = board.length - 1 ; i >= 0 ; i--){
			if(board[i][row] == 0){
				board[i][row] = player;
				break;
			}
		}
		return board;
	}
	
	public static int[][] removeCoin(int board[][],int row){
		for(int i = 0 ; i < board.length ; i++){
			if(board[i][row] != 0){
				board[i][row] = 0;
				break;
			}
		}
		return board;
	}
	
	public static int checkResult(int[][] board){
		//vertical check
		for(int i = 0 ; i < board.length - 3 ; i++){
			for(int j = 0 ; j < board[i].length ; j++){
				//check for player
				if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j] &&
				   board[i][j] == 1){
					return 100;
				}
				//check for computer
				if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j] &&
				   board[i][j] == -1){
					return -100;
				}
			}
		}
		//horizontal check
		for(int i = 0 ; i < board.length ; i++){
			for(int j = 0 ; j < board[i].length - 3 ; j++){
				//check for player
				if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] &&
				   board[i][j] == 1){
					return 100;
				}
				//check for computer
				if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] &&
				   board[i][j] == -1){
					return -100;
				}
			}
		}
		//diagonal go right check
		for(int i = 0 ; i < board.length - 3 ; i++){
			for(int j = 0 ; j < board[i].length - 3 ; j++){
				//check for player
				if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] &&
				   board[i][j] == 1){
					return 100;
				}
				//check for computer
				if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] &&
				   board[i][j] == -1){
					return -100;
				}
			}
		}
		//diagonal go left check
		for(int i = 0 ; i < board.length - 3 ; i++){
			for(int j = 3 ; j < board[i].length ; j++){
				//check for player
				if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] &&
				   board[i][j] == 1){
					return 100;
				}
				//check for computer
				if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] &&
				   board[i][j] == -1){
					return -100;
				}
			}
		}
		return 0;
	}
	
	public static void printBoard(int[][] board){
		for(int i = 0 ; i < board.length ; i++){
			System.out.print("| ");
			for(int j = 0 ; j < board[i].length ; j++){
				if(board[i][j] == 0)
					System.out.print("  | ");
				else if(board[i][j] == 1)
					System.out.print("o | ");
				else if(board[i][j] == -1)
					System.out.print("x | ");
			}
			System.out.println();
		}
	}
}
