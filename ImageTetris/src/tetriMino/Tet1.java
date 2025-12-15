package tetriMino;

import container.TetriMino;

public class Tet1 extends TetriMino {	//黄 Oミノ
	
	public Tet1() {
		super(new int[][]{{0,center},{1,center},{0,center+1},{1,center+1}});
	}
	
	public int[][] defP(){
		return new int[][]{{0,center},{1,center},{0,center+1},{1,center+1}};
	}
	
	public void rotateL(int[][] board) {//回転なし
	}
	public void rotateR(int[][] board) {//回転なし
	}
	
	
	//-------各状態への遷移------------------------------------------------------//
		
	public boolean changeTo0(int y, int x) {
		return false;
	}
	public boolean changeTo1(int y, int x) {
		return false;
	}
	public boolean changeTo2(int y, int x) {
		return false;
	}
	public boolean changeTo3(int y, int x) {
		return false;
	}
}
