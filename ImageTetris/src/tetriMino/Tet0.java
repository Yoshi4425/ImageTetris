package tetriMino;

import container.TetriMino;

public class Tet0 extends TetriMino {	//紫 Tミノ
	
	public Tet0() {
		super(new int[][]{{1,center},{1,center-1},{0,center},{1,center+1}});	//初期位置
	}
	
	public int[][] defP(){	//初期位置を返す
		return new int[][]{{1,center},{1,center-1},{0,center},{1,center+1}};	
	}
	
	
	//-------各状態への遷移------------------------------------------------------//
		
	public boolean changeTo0(int y, int x) {	
		newP = new int[][]{{y,x},{y,x-1},{y-1,x},{y,x+1}};
		
		if(x-1 < 0 || x+1 >= tetX) return false;
		else return true;
	}
	
	public boolean changeTo1(int y, int x) {
		newP = new int[][]{{y,x},{y,x-1},{y-1,x},{y+1,x}};
		
		if(y+1 >= tetY || y-1 < 0) return false;
		else return true;
	}
	
	public boolean changeTo2(int y, int x) {
		newP = new int[][]{{y,x},{y,x-1},{y,x+1},{y+1,x}};
		
		if(x+1 >= tetX || x-1 < 0) return false;
		return true;
	}
	
	public boolean changeTo3(int y, int x) {
		newP = new int[][]{{y,x},{y-1,x},{y,x+1},{y+1,x}};
		
		if(y-1 < 0 || y+1 >= tetY) return false;
		else return true;
	}
}
