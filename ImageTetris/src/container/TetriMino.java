package container;

public abstract class TetriMino extends SimpleTetriMino{	//srs機能の拡張
	public TetriMino(int[][] p) {
		super(p);
	}
	public void rotateL(int[][] board) {	//左回転
		super.rotateL(board);
		
		if(!(position == newP)) {	//回転できなければSRS
	
			if(srsL(board)) {	//srs成功で回転処理
				paint.renew(position,estimate);	//現在のテトの位置を黒塗り
				rotation++;
				if(rotation==4) rotation=0;
				position = newP;
				changeEstimate(board);	//落下位置の更新
				paint.tet(position,estimate);		//更新されたテトの位置を表示
			}
		}
	}
	
	public void rotateR(int[][] board) {	//右回転
		super.rotateR(board);
		
		if(!(position == newP)) {	//回転できなければSRS
			
			if(srsL(board)) {	//srs成功で回転処理
				paint.renew(position,estimate);	//現在のテトの位置を黒塗り
				rotation--;
				if(rotation==-1) rotation=3;
				position = newP;
				changeEstimate(board);	//落下位置の更新
				paint.tet(position,estimate);		//更新されたテトの位置を表示
			}
		}
	}
	
	
	public boolean srsL(int[][] board) {
		int i;
		int signX,signY;
		int rotate = rotation+1;
		
		//変化1
		if(rotation==0 || rotation==3) signX = 1;//0,3のとき
		else signX = -1;//1,2のとき
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		//変化2
		if(rotation==0 || rotation==2) signY = -1;//0,2のとき
		else signY = 1;//1,3のとき
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		//変化3
		if(rotation==0) {
			signX = -1;
			signY = 3;
		}else if(rotation==1) {
			signX = 1;
			signY = -3;
		}else if(rotation==2) {
			signX = 1;
			signY = 3;
		}else{
			signX = -1;
			signY = -3;
		}
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
			newP[i][1] += signX;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		//変化4
		if(rotation==0 || rotation==3) signX = 1;//0,3のとき
		else signX = -1;//1,2のとき
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		return false;
	}
	
	public boolean srsR(int[][] board) {
		int i;
		int signX,signY;
		int rotate = rotation-1;
		
		//変化1
		if(rotation<2) signX = -1;//0,1のとき
		else signX = 1;//2,3のとき
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		//変化2
		if(rotation==0 || rotation==2) signY = -1;//0,2のとき
		else signY = 1;//1,3のとき
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		//変化3
		if(rotation==0) {
			signX = 1;
			signY = 3;
		}else if(rotation==1) {
			signX = 1;
			signY = -3;
		}else if(rotation==2) {
			signX = -1;
			signY = 3;
		}else{
			signX = -1;
			signY = -3;
		}
		for(i=0; i<newP.length; i++) {
			newP[i][0] += signY;
			newP[i][1] += signX;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		//変化4
		if(rotation<2) signX = -1;//0,1のとき
		else signX = 1;//2,3のとき
		for(i=0; i<newP.length; i++) {
			newP[i][1] += signX;
		}
		if(srsIndex(rotate) && checkRota(board)) return true;
		
		return false;
	}
	
	public boolean srsIndex(int rotate) {//SRSの際のインデックスチェック　オーバーでfalse
		int y = newP[0][0];
		int x = newP[0][1];
	
		switch(rotate) {//回転成功後のrotation
		case 0:
			if(y-1<0 || y>=tetY || x-1<0 || x+1>=tetX) 
				return false;
			break;
		case 1:
			if(y-1<0 || y+1>=tetY || x-1<0 || x>=tetX) 
				return false;
			break;
		case 2:
			if(y<0 || y+1>=tetY || x-1<0 || x+1>=tetX) 
				return false;
			break;
		case 3:
			if(y-1<0 || y+1>=tetY || x<0 || x+1>=tetX) 
				return false;
			break;	
		}
		return true;
	}
}
