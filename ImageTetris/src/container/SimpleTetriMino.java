package container;

import main.Constant;
import paint.TetriMinoPaint;

public abstract class SimpleTetriMino {
	protected int[][] position; //[要素数][0]=y座標、[要素数][1]=x座標
	protected int rotation = 0;//回転の状態 rotation*90° 
	//0,2,3,4,5,6のミノの中心座標は position[0] で管理
	protected int[][] newP;
	protected int[][] estimate;	//落下位置
	
	protected TetriMinoPaint paint;
	
	protected static int tetX;	//テトリスの横のマス数
	protected static int tetY;	//テトリスの縦のマス数
	protected static int center;//x座標の中心
	
	public static void setStatic() {
		tetX = Constant.tetX;
		tetY = Constant.tetY;
		center = Constant.centerTet;
	}
	
	public SimpleTetriMino(int[][] p) {
		position = p; 
		estimate = new int[p.length][2];
		paint = new TetriMinoPaint();
	}
	
	public boolean down(int[][] board, int p[][]) { //ある位置についての落下判定及び落下 落下失敗でtrueを返す
		
		for(int i=0; i<p.length; i++) {	//全座標についてインデントと盤面のチェック
			if(p[i][0]+1 >= tetY || 
					board[p[i][0]+1][p[i][1]] == 1)
				return true;	//移動できなければtrue
		}
		for(int i=0; i<p.length; i++) {	//全座標の移動
			p[i][0]++;	
		}
		return false;	//成功
	}
	
	public boolean down(int[][] board) { //テトの落下操作時に呼び出される
		
		paint.renew(position,estimate);	//現在のテトの位置を黒塗り
		boolean re = down(board,position);
		paint.tet(position,estimate);		//更新されたテトの位置を表示
		
		return re;	//成否
	}
	
	public void right(int[][] board) {//テトの右移動判定および移動
	
		for(int i=0; i<position.length; i++) {	//全座標についてインデントと盤面のチェック
			if(position[i][1]+1 >= tetX || 
					board[position[i][0]][position[i][1]+1] == 1)
				return;
		}
		paint.renew(position,estimate);	//現在のテトの位置を黒塗り
		for(int i=0; i<position.length; i++) {	//全座標の移動
			position[i][1] += 1;
		}
		changeEstimate(board);	//落下位置の更新
		paint.tet(position,estimate);		//更新されたテトの位置を表示
	}
	
	public void left(int[][] board) {//テトの左移動判定および移動
		
		for(int i=0; i<position.length; i++) {	//全座標についてインデントと盤面のチェック
			if(position[i][1]-1 < 0  || 
					board[position[i][0]][position[i][1]-1] == 1)
				return;
		}
		
		paint.renew(position,estimate);	//現在のテトの位置を黒塗り
		for(int i=0; i<position.length; i++) {	//全座標の移動
			position[i][1] -= 1;
		}
		changeEstimate(board);	//落下位置の更新
		paint.tet(position,estimate);		//更新されたテトの位置を表示
	}
	
	public void rotateL(int[][] board) {	//左回転
		boolean change=false;	//回転フラグ
		
		switch(rotation) {//changeToで移動、インデント確認、check()への委託をする
		case 0:
			change = changeTo1(position[0][0], position[0][1]);
			break;
		case 1:
			change = changeTo2(position[0][0], position[0][1]);
			break;
		case 2:
			change = changeTo3(position[0][0], position[0][1]);
			break;
		case 3:
			change = changeTo0(position[0][0], position[0][1]);
			break;
		}
		if(change) //盤面のチェック
			change = checkRota(board);
		
		if(change) {	//回転処理
			paint.renew(position,estimate);	//現在のテトの位置を黒塗り
			rotation++;
			if(rotation==4) rotation=0;
			position = newP;
			changeEstimate(board);	//落下位置の更新
			paint.tet(position,estimate);		//更新されたテトの位置を表示
		}
	}
	
	public void rotateR(int [][] board) {	//右回転
		boolean change=false;	//回転フラグ
		
		switch(rotation) {//changeToで移動、インデント確認、check()への委託をする
		case 0:
			change = changeTo3(position[0][0], position[0][1]);
			break;
		case 1:
			change = changeTo0(position[0][0], position[0][1]);
			break;
		case 2:
			change = changeTo1(position[0][0], position[0][1]);
			break;
		case 3:
			change = changeTo2(position[0][0], position[0][1]);
			break;
		}
		if(change)	//盤面のチェック
			change = checkRota(board);
		
		if(change) {	//回転処理
			paint.renew(position,estimate);	//現在のテトの位置を黒塗り
			rotation--;
			if(rotation==-1) rotation=3;
			position = newP;
			changeEstimate(board);	//落下位置の更新
			paint.tet(position,estimate);		//更新されたテトの位置を表示
		}
	}
	
	public boolean checkRota(int[][] board) {	//newPにブロックがあればfalse
		for(int i=0; i<newP.length; i++) {
			if(board[newP[i][0]][newP[i][1]] == 1)
				return false;
		}
		return true;
	}
	
	public void hold() {
		position = defP();
		rotation = 0;
	}
	
	public boolean finish(int[][] board) {//終了判定 終了ならtrue
		for(int i=0; i<position.length; i++) {
			if(board[position[i][0]][position[i][1]] == 1)
				return true;
		}
		return false;
	}
	
	public void changeMino(int[][] board) {

		for(int i=0; i<position.length; i++) {
			board[position[i][0]][position[i][1]] = 1;	//テトのある座標を1に変更
		}
	}
	
	public void changeEstimate(int[][] board) {	//落下位置の更新
		for(int i=0; i<estimate.length; i++) {
			for(int j=0; j<2; j++) {
				estimate[i][j] = position[i][j];	//現在位置をコピー
			}
		}
		while(!(down(board,estimate)));	//落下できなくなるまでテトを落下
	}
	
	public abstract boolean changeTo0(int y, int x); //newPに各回転後の座標を新規配列として登録
	public abstract boolean changeTo1(int y, int x); //y,xは回転の中心座標
	public abstract boolean changeTo2(int y, int x); 
	public abstract boolean changeTo3(int y, int x);
	
	public abstract int[][] defP();
	
	
	//----------描画------------------------------------------------------------------------------------//
	public void paintTet() {
		paint.tet(position,estimate);	//現在位置の表示
	}
	
	public void paintHold(boolean check) {
		paint.hold(position,check);
	}
	
	public void paintTetBox(int num) {
		paint.tetBox(position,num);
	}
}
