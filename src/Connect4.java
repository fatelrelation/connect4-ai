import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Connect4 {

	public static void main(String args[]){
		play();
	}
	
	public static void play(){
		int board[][] = new int[6][7];
		int row = 0;
		printBoard(board);
		System.out.println();
		while(checkResult(board) != 10000 && checkResult(board) != -10000 && isTableAvailable(board)){
			row = playerChooseMove(board);
			board = dropCoin(board, row,1);
			printBoard(board);
			System.out.println();
			if(checkResult(board) == 10000 || checkResult(board) == -10000 || !isTableAvailable(board))
				break;
			row = findMove(board);
			System.out.println("Bot row number : "+(row+1));
			board = dropCoin(board, row, -1);
			printBoard(board);
			System.out.println();
		}
		if(checkResult(board) == 10000)
			System.out.println("Player win");
		else if(checkResult(board) == -10000)
			System.out.println("Computer win");
		else{
			System.out.println("Draw");
		}
	}
	
	public static int playerChooseMove(int board[][]){
		Scanner sc = new Scanner(System.in);
		int countWhile = 0;
		int row = 0;
		do{
			if(countWhile > 0){
				System.out.println("Row not Available please try again.");
			}
			System.out.print("Enter row number(1-7) : ");
			row = sc.nextInt() - 1;
			countWhile++;
		}while(!isRowAvailable(board, row));
		return row;
	}
	
	public static int findMove(int[][] board){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		ArrayList<Integer> index = new ArrayList<Integer>();
		int bestMove = Integer.MAX_VALUE;
		int move = -1;
		int num = -1;
		for(int j = 0 ; j < board[0].length ; j++){
			if(isRowAvailable(board, j)){
				board = dropCoin(board, j, -1);
				move = minimax(board,0,1,Integer.MIN_VALUE,Integer.MAX_VALUE);
				moves.add(move);
				index.add(j);
				System.out.print(move+" ");
				board = removeCoin(board, j);
				if(move < bestMove){
					bestMove = move;
					num = j;
				}
			}
		}
		System.out.println();
		//for random if moves have many best value**
		int min = getMin(moves);
		ArrayList<Integer> index2 = new ArrayList<Integer>();
		for(int i = 0 ; i < moves.size() ; i++){
			if(moves.get(i) == min){
				index2.add(index.get(i));
			}
		}
		if(index2.size() > 1){
			Random rd = new Random();
			do
				num = index2.get(rd.nextInt(index2.size()));
			while(!isRowAvailable(board, num));
		}
		return num;
	}
	
	public static int minimax(int board[][],int depth, int player,int alpha,int beta){
		int score = checkResult(board);
		if(score == 10000)
			return score - depth;
		if(score == -10000)
			return score + depth;
		if(!isTableAvailable(board))
			return 0;
		if(depth > 8)
			return score;
		
		if(player == 1){
			int bestMax = Integer.MIN_VALUE;
			for(int i = 0 ; i < board[0].length ; i++){
				if(isRowAvailable(board, i)){
					board = dropCoin(board, i, 1);
					bestMax = Math.max(minimax(board,depth+1,-1,alpha,beta), bestMax);
					board = removeCoin(board, i);
					alpha = Math.max(alpha,bestMax);
					if(beta <= alpha)
						return bestMax;
				}
			}
			return bestMax;
		}else {
			int bestMin = Integer.MAX_VALUE;
			for(int i = 0 ; i < board[0].length ; i++){
				if(isRowAvailable(board, i)){
					board = dropCoin(board, i, -1);
					bestMin = Math.min(minimax(board,depth+1,1,alpha,beta), bestMin);
					board = removeCoin(board, i);
					beta = Math.min(beta, bestMin);
					if(beta <= alpha)
						return bestMin;
				}
			}
			return bestMin;
		}
	}
	
	public static boolean isRowAvailable(int board[][],int row){
		if(row < 0 || row >= board[0].length)
			return false;
		for(int i = 0 ; i < board.length ; i ++){
			if(board[i][row] == 0)
				return true;
		}
		return false;
	}
	
	public static boolean isTableAvailable(int board[][]){
		for(int i = 0 ; i < board.length ; i++){
			for(int j = 0 ; j < board[i].length ; j++){
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
		int point = 0;
		//vertical check
		for(int i = 0 ; i < board.length - 3 ; i++){
			for(int j = 0 ; j < board[i].length ; j++){
				//check for player
				if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j] &&
				   board[i][j] == 1){
					return 10000;
				}
				else if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == 1){
					point += 100;
				}
				else if(board[i][j] == board[i+1][j] && board[i][j] == 1){
					point += 50;
				}
				
				
				//check for computer
				if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j] &&
				   board[i][j] == -1){
					return -10000;
				}
				else if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == -1){
					point -= 100;
				}
				else if(board[i][j] == board[i+1][j] && board[i][j] == -1){
					point -= 50;
				}
			}
		}
		//horizontal check
		for(int i = 0 ; i < board.length ; i++){
			for(int j = 0 ; j < board[i].length - 3 ; j++){
				//check for player
				if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] &&
				   board[i][j] == 1){
					return 10000;
				}
				else if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == 1){
					point += 100;
				}
				else if(board[i][j] == board[i][j+1] && board[i][j] == 1){
					point += 50;
				}
				//check for computer
				if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] &&
				   board[i][j] == -1){
					return -10000;
				}
				else if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == -1){
					point -= 100;
				}
				else if(board[i][j] == board[i][j+1] && board[i][j] == -1){
					point -= 50;
				}
			}
		}
		//diagonal go right check
		for(int i = 0 ; i < board.length - 3 ; i++){
			for(int j = 0 ; j < board[i].length - 3 ; j++){
				//check for player
				if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] &&
				   board[i][j] == 1){
					return 10000;
				}
				else if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == 1){
					point += 100;
				}
				else if(board[i][j] == board[i+1][j+1] && board[i][j] == 1){
					point += 50;
				}
				//check for computer
				if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] &&
				   board[i][j] == -1){
					return -10000;
				}
				else if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == -1){
					point -= 100;
				}
				else if(board[i][j] == board[i+1][j+1] && board[i][j] == -1){
					point -= 50;
				}
			}
		}
		//diagonal go left check
		for(int i = 0 ; i < board.length - 3 ; i++){
			for(int j = 3 ; j < board[i].length ; j++){
				//check for player
				if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] &&
				   board[i][j] == 1){
					return 10000;
				}
				else if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == 1){
					point += 100;
				}
				else if(board[i][j] == board[i+1][j-1] && board[i][j] == 1){
					point += 50;
				}
				//check for computer
				if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] &&
				   board[i][j] == -1){
					return -10000;
				}
				else if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == -1){
					point -= 100;
				}
				else if(board[i][j] == board[i+1][j-1] && board[i][j] == -1){
					point -= 50;
				}
			}
		}
		return point;
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

	public static int getMin(ArrayList<Integer> moves){
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < moves.size() ; i++){
			if(moves.get(i) < min){
				min = moves.get(i);
			}
		}
		return min;
	}
}