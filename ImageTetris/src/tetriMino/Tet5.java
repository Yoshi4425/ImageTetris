package tetriMino;

import container.TetriMino;

public class Tet5 extends TetriMino {	//オレンジ Lミノ
	
	public Tet5() {
		super(new int[][]{{1,center},{0,center+1},{1,center-1},{1,center+1}});
	}
	
	public int[][] defP(){
		return new int[][]{{1,center},{0,center+1},{1,center-1},{1,center+1}};
	}
	
	
	//-------各状態への遷移------------------------------------------------------//
		
	public boolean changeTo0(int y, int x) {
		newP = new int[][]{{y,x},{y-1,x+1},{y,x-1},{y,x+1}};
		
		if(x-1 < 0 || x+1 >= tetX) return false;
		else return true;
	}
	
	public boolean changeTo1(int y, int x) {
		newP = new int[][]{{y,x},{y-1,x-1},{y+1,x},{y-1,x}};
		
		if(y+1 >= tetY || y-1 < 0) return false;
		else return true;
	}
	
	public boolean changeTo2(int y, int x) {
		newP = new int[][]{{y,x},{y+1,x-1},{y,x+1},{y,x-1}};
		
		if(x+1 >= tetX || x-1 < 0) return false;
		return true;
	}
	
	public boolean changeTo3(int y, int x) {
		newP = new int[][]{{y,x},{y+1,x+1},{y-1,x},{y+1,x}};
		
		if(y-1 < 0 || y+1 >= tetY) return false;
		else return true;
	}
}
