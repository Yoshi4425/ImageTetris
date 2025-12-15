package tetriMino;

import container.TetriMino;

public class Tet6 extends TetriMino {	//水色 Iミノ
	
	public Tet6() {
		super(new int[][]{{1,center-1},{1,center},{1,center+1},{1,center+2}}); //回転の基準は1
	}
	
	public int[][] defP(){
		return new int[][]{{1,center-1},{1,center},{1,center+1},{1,center+2}};
	}
	
	public void rotateL(int[][] board) {	//中心座標を変更
		boolean change=false;
		
		switch(rotation) {
		case 0:
			change = changeTo1(position[1][0], position[1][1]);
			break;
		case 1:
			change = changeTo2(position[1][0], position[1][1]);
			break;
		case 2:
			change = changeTo3(position[1][0], position[1][1]);
			break;
		case 3:
			change = changeTo0(position[1][0], position[1][1]);
			break;
		}
		if(change)	
			change = checkRota(board);
		
		if(!change)
			change = srsL(board);
		
		if(change) {
			paint.renew(position,estimate);	//現在のテトの位置を黒塗り
			rotation++;
			if(rotation==4) rotation=0;
			position = newP;
			changeEstimate(board);	//落下位置の更新
			paint.tet(position,estimate);		//更新されたテトの位置を表示
		}
	}
	
	public void rotateR(int [][] board) {	//6は特別な回転方法 中心をずらして渡す
		boolean change=false;
		
		switch(rotation) {
		case 0:
			change = changeTo3(position[2][0]+1, position[2][1]);
			break;
		case 1:
			change = changeTo0(position[2][0], position[2][1]+1);
			break;
		case 2:
			change = changeTo1(position[2][0]-1, position[2][1]);
			break;
		case 3:
			change = changeTo2(position[2][0], position[2][1]-1);
			break;
		}
		if(change) 
			change = checkRota(board);
		
		if(!change)
			change = srsR(board);
		
		if(change) {
			paint.renew(position,estimate);	//現在のテトの位置を黒塗り
			rotation--;
			if(rotation==-1) rotation=3;
			position = newP;
			changeEstimate(board);	//落下位置の更新
			paint.tet(position,estimate);		//更新されたテトの位置を表示
		}
	}
	
	public boolean srsL(int[][] board) {
		int i;
		int signX,signY;
		
		//変化1
		if(rotation==0) signX = -1;//0のとき
		else if(rotation<3) signX = 1;//1,2のとき
		else signX = 2;
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		//変化2
		if(rotation==0) signX = 3;//0のとき
		else signX = -3;//1,2,3のとき
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		//変化3
		if(rotation==0) {
			signX = -3;
			signY = -2;
		}else if(rotation==1) {
			signX = 0;
			signY = 1;
		}else if(rotation==2) {
			signX = 3;
			signY = 2;
		}else{//3
			signX = 3;
			signY = -1;
		}
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		//変化4
		if(rotation==0) {
			signX = 3;
			signY = 3;
		}else if(rotation==1) {
			signX = 3;
			signY = -3;
		}else if(rotation==2) {
			signX = -3;
			signY = -3;
		}else{//3
			signX = -3;
			signY = 3;
		}
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		return false;
	}
	
	public boolean srsR(int[][] board) {
		int i;
		int signX,signY;
		
		//変化1
		if(rotation<2) signX = -2;//0,1のとき
		else if(rotation==2) signX = 2;//2のとき
		else signX = -1;//3
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		//変化2
		if(rotation==2) signX = -3;//2のとき
		else signX = 3;//0,1,3のとき
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		//変化3
		if(rotation==0) {
			signX = -3;
			signY = 1;
		}
		else if(rotation==1) {
			signX = 0;
			signY = 2;
		}else if(rotation==2) {
			signX = 3;
			signY = -1;
		}else{//3
			signX = -3;
			signY = -2;
		}
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		//変化4
		if(rotation==0) {
			signX = 3;
			signY = -3;
		}else if(rotation==1) {
			signX = -3;
			signY = -3;
		}else if(rotation==2) {
			signX = -3;
			signY = 3;
		}else{//3
			signX = 3;
			signY = 3;
		}
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
			newP[i][1] += signX;
		}
		if(srsIndex() && checkRota(board)) return true;
		
		return false;
	}
	
	public boolean srsIndex() {//SRSの際のインデックスチェック　オーバーでfalse
		if(newP[0][0]<0 || newP[0][1]<0 || newP[3][0]>=tetY || newP[3][1]>=tetX) 
			return false;
		return true;
	}
	
	
	//-------各状態への遷移------------------------------------------------------//
			
	public boolean changeTo0(int y, int x) {
		newP = new int[][]{{y,x-2},{y,x-1},{y,x},{y,x+1}};
		
		if(x-2 < 0 || x+1 >= tetX) return false;
		else return true;
	}
	
	public boolean changeTo1(int y, int x) {
		newP = new int[][]{{y-1,x},{y+1,x},{y,x},{y+2,x}};
		
		if(y+2 >= tetY || y-1 < 0) return false;
		else return true;
	}
	
	public boolean changeTo2(int y, int x) {
		newP = new int[][]{{y,x-1},{y,x+1},{y,x},{y,x+2}};
		
		if(x+2 >= tetX || x-1 <0) return false;
		else return true;
	}
	
	public boolean changeTo3(int y, int x) {
		newP = new int[][]{{y-2,x},{y-1,x},{y,x},{y+1,x}};
		
		if(y-2 < 0 || y+1 >= tetY) return false;
		else return true;
	}
}
