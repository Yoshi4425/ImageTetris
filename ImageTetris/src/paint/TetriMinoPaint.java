package paint;

import java.awt.Graphics;
import java.awt.Color;

import main.Constant;

public class TetriMinoPaint {	//テトリミノの描画クラス
	public static Graphics gra;	
	private final Color abricot = new Color(255,143,34);	//オレンジ
	private final Color redhot = new Color(255,0,0,200);	//薄赤
	
	private static int spaceHold;	//ホールド描画時の1マスの大きさ
	public static void set() {
		spaceHold = Constant.spaceLength*3/4;
	}
	
	public void tet(int[][] p, int[][] e) {	//もちテト
		for(int i=0; i<e.length; i++) {
			space(e[i][0],e[i][1],redhot,abricot);	//落下予測
		}
		for(int i=0; i<p.length; i++) {
			space(p[i][0],p[i][1],Color.blue,Color.cyan);	//もちテト
		}
	}
	
	public void renew(int[][] p, int[][] e) {	//持ちテト消去
		for(int i=0; i<p.length; i++) {
			space(p[i][0],p[i][1], Color.black, Color.gray);
			space(e[i][0],e[i][1], Color.black, Color.gray);
		}
	}
	
	private void space(int i, int j, Color c1, Color c2){ //マス表示
		
		int x = j*Constant.spaceLength + Constant.boardX;
		int y = i*Constant.spaceLength + Constant.boardY;
		gra.setColor(c1);	//c1の色でマスを表示
		gra.fillRect(x,y, Constant.spaceLength, Constant.spaceLength);
		gra.setColor(c2);	//c2の色で枠取り
		gra.drawRect(x+1,y+1,Constant.spaceLength-3,Constant.spaceLength-3);
	}
	
	public void hold(int[][] p, boolean check) {	//ホールド表示
		if(check)	gra.setColor(Color.gray);	//ホールド使用済みならばグレー、そうでなければオレンジ
		else gra.setColor(abricot);
		
		for(int i=0; i<p.length; i++) {	
			gra.fillRect(Constant.holdX + Constant.spaceLength/2 + spaceHold*(p[i][1]-Constant.centerTet+1),	//ホールドの描画位置+青枠
					Constant.holdY + Constant.spaceLength/2 + spaceHold*p[i][0],
					spaceHold -1, spaceHold -1);
		}
	}
	
	public void tetBox(int[][] position, int num) {	//次のテトリミノの表示　numはtetBoxの何番目かを示す
		for(int k=0; k<position.length; k++) {
			gra.fillRect(Constant.tetBoxX + Constant.marginX + Constant.spaceTetBox * (position[k][1]-Constant.centerTet+1),	//表示位置＋余白分の位置から描画
					Constant.tetBoxY + Constant.unit * num + Constant.marginY + Constant.spaceTetBox * position[k][0],	//何番目によってさらに位置を下げる
					Constant.spaceTetBox-1, Constant.spaceTetBox-1);
		}
	}
	
}
