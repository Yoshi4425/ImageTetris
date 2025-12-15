package paint;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JFrame;

import main.Constant;

public class GamePaint{	//ゲームの描画クラス
	
	private Graphics gra;
	
	private final Image backGround = Toolkit.getDefaultToolkit().getImage("System/utyu.png");	//背景画像
	private final Image[] effect = {	//消去時のエフェクト
			Toolkit.getDefaultToolkit().getImage("System/single.png"),
			Toolkit.getDefaultToolkit().getImage("System/double.png"),
			Toolkit.getDefaultToolkit().getImage("System/triple.png"),
			Toolkit.getDefaultToolkit().getImage("System/tetris.png")};
	
	private final Image pause = Toolkit.getDefaultToolkit().getImage("System/pause.png");	//ポーズ画面

	private final Color smoke = new Color(50,50,50,200);	//スモーク
	private final Color purple = new Color(102,0,153);	//紫
	
	//スコア表示の定数
	private static int sizeX;	//横方向の大きさ
	private static int sizeY;	//縦方向の大きさ
	private static int thick;	//厚み
	private static int[] font;	//フォントの文字サイズ
	private static int[] stringX, stringY;	//文字の表示位置
	
	public GamePaint(Graphics g, JFrame jf) {
		gra = g;
		BonusFramePaint.gra = g;
		TetriMinoPaint.gra = g;
	
		//画像の読み込み
		MediaTracker tracker = new MediaTracker(jf);
		
		for(int i=0; i<effect.length; i++) {
			tracker.addImage(effect[i],0);
		}
		tracker.addImage(backGround,0);
		tracker.addImage(pause,0);
		
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
	}
	
	public static void setScoreSize() {	//スコア表示サイズの設定
		if(Constant.blueFrameX > 200 &&  Constant.frameY > Constant.blueFrameY+Constant.spaceLength*7/2 + 145) {
			sizeX = 170;
			sizeY = 130;
			thick = 15;
			font = new int[] {30,20};
			stringX = new int[] {10,5,5,-30};
			stringY = new int[] {40,70,90,110};
		}
		else if(Constant.blueFrameX > 140 && Constant.frameY > Constant.blueFrameY+Constant.spaceLength*7/2 + 125) {
			sizeX = 120;
			sizeY = 115;
			thick = 10;
			font = new int[] {22,18};
			stringX = new int[] {5,5,5,-10};
			stringY = new int[] {32,57,80,100};
		}
		else {
			sizeX = 75;
			sizeY = 70;
			thick = 5;
			font = new int[] {14,11};
			stringX = new int[] {1,-1,1,-8};
			stringY = new int[] {18,32,50,60};
		}
	}
	
	public void back(){ //背景
		gra.drawImage(backGround,0,0,Constant.frameX,Constant.frameY,null);	//背景画像
		//青枠
		gra.setColor(Color.cyan);	
		gra.fillRect(Constant.blueFrameX, Constant.blueFrameY, 
				(Constant.tetX+6)*Constant.spaceLength, (Constant.tetY+1)*Constant.spaceLength);	//盤面
		gra.fillRect(Constant.holdX,Constant.holdY,Constant.spaceLength*4,Constant.spaceLength*5/2);	//ホールド
		
		//黒埋め
		gra.setColor(Color.black);
		gra.fillRect(Constant.holdX + Constant.spaceLength/4,Constant.holdY + Constant.spaceLength/4,	//ホールドの黒い部分
				Constant.spaceLength*7/2,Constant.spaceLength*2);
		
	}
	
	public void board(int[][] board) { //盤面
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(board[i][j] == 0) 
					space(i,j);
			}
		}
	}
	
	public void score(int score, int high) {	//スコア表示　スコアとハイスコアが引数

			gra.setColor(Color.blue);
			gra.fillRect(Constant.blueFrameX-(sizeX+thick*2),
					Constant.blueFrameY+Constant.spaceLength*7/2, sizeX,sizeY);
			
			gra.setColor(Color.black);
			gra.fillRect(Constant.blueFrameX-(sizeX+thick),
					Constant.blueFrameY+Constant.spaceLength*7/2+thick, sizeX-thick*2, sizeY-thick*2);
			
			gra.setColor(Color.white);
			gra.setFont(new Font("IPAGothic", Font.PLAIN, font[0]));
			gra.drawString("Score", Constant.blueFrameX-(sizeX+stringX[0]),
					Constant.blueFrameY+Constant.spaceLength*7/2 + stringY[0]);
			gra.drawString(String.format("%8d",score), Constant.blueFrameX-(sizeX+stringX[1]),
					Constant.blueFrameY+Constant.spaceLength*7/2 + stringY[1]);
			gra.setFont(new Font("IPAGothic", Font.PLAIN, font[1]));
			gra.drawString("High Score", Constant.blueFrameX-(sizeX+stringX[2]),
					Constant.blueFrameY+Constant.spaceLength*7/2 + stringY[2]);
			gra.drawString(String.format("%8d",high), Constant.blueFrameX-(sizeX+stringX[3]),
					Constant.blueFrameY+Constant.spaceLength*7/2 + stringY[3]);
	}
	
	public void effect(int num) {	//消去時の演出 引数は消去した行数
		int effectSize;
		if(num==4) {	//テトリスのときは文字は大きめ
			effectSize = Constant.effectSize *12 /10;
		}else {
			effectSize = Constant.effectSize;
		}
		gra.drawImage(effect[num-1], Constant.frameX/2 - effectSize/2, Constant.frameY/2 - effectSize/2,
				effectSize, effectSize, null);
	}
	
	public void pause() {	//ポーズ画面
		int x = Constant.frameX;
		int y = Constant.frameY;
		
		gra.setColor(smoke);
		gra.fillRect(0, 0, x, y);
		
		if(x/2 > y)	//横幅が縦幅の半分より大きければ
			gra.drawImage(pause, x/2 - y, 0, y*2, y, null);	//縦幅基準で表示
		else 
			gra.drawImage(pause, 0, y/2 - x/4, x, x/2, null);	//横幅基準で表示
	}
	
	public void finish(boolean renew, int score, int high) {	//終了表示　ハイスコア更新の判定、スコア、ハイスコアが引数
	
		int sizeX;	//横方向の大きさの半分
		int sizeY;	//縦方向の大きさの半分
		int thick;	//厚み
		int[] font;	//フォントの文字サイズ
		int[] stringX, stringY;	//文字の表示位置 名目、値、new
		
		if(Constant.frameX > 520 && Constant.frameY > 440) {
			sizeX = 260;
			sizeY = 200;
			thick = 20;
			font = new int[] {80,50,30};
			stringX = new int[] {30,100,20};
			stringY = new int[] {80,170,250,330,200};
		}else if(Constant.frameX > 440 && Constant.frameY > 380) {
			sizeX = 220;
			sizeY = 170;
			thick = 15;
			font = new int[] {70,40,20};
			stringX = new int[] {20,90,15};
			stringY = new int[] {70,140,220,290,180};
		}else {
			sizeX = 150;
			sizeY = 115;
			thick = 10;
			font = new int[] {50,30,15};
			stringX = new int[] {15,60,10};
			stringY = new int[] {50,100,150,200,120};
		}
		
		//枠表示
		gra.setColor(purple);
		gra.fillRect(Constant.frameX/2 - sizeX, Constant.frameY/2 - sizeY +20, sizeX*2, sizeY*2);
		gra.setColor(Color.white);
		gra.fillRect(Constant.frameX/2 -sizeX +thick, Constant.frameY/2 -sizeY +thick +20, (sizeX-thick)*2, (sizeY-thick)*2);

		//スコア表示
		gra.setFont(new Font("IPAGothic", Font.PLAIN, font[0]));
		gra.setColor(Color.blue);
		gra.drawString("Score",Constant.frameX/2 -sizeX +thick +stringX[0],
				Constant.frameY/2 -sizeY +thick +stringY[0] +20);
		gra.drawString(String.format("%8d",score),Constant.frameX/2 -sizeX +thick +stringX[1],
				Constant.frameY/2 -sizeY +thick +stringY[1] +20);
		
		//ハイスコア更新ならば赤文字で演出
		if(renew) {
			gra.setColor(Color.red);
			gra.setFont(new Font("IPAGothic", Font.PLAIN, font[2]));
			gra.drawString("new!",Constant.frameX/2 -sizeX +thick +stringX[2],
					Constant.frameY/2 -sizeY +thick +stringY[4] +20);
		}
		//ハイスコア表示
		gra.setFont(new Font("IPAGothic", Font.PLAIN, font[1]));
		gra.drawString("High Score",Constant.frameX/2 -sizeX +thick +stringX[0],
				Constant.frameY/2 -sizeY +thick +stringY[2] +20);
		gra.setFont(new Font("IPAGothic", Font.PLAIN, font[0]));
		gra.drawString(String.format("%8d",high),Constant.frameX/2 -sizeX +thick +stringX[1],
				Constant.frameY/2 -sizeY +thick +stringY[3] +20);		
	}
	
	public void clear() {	//画面をリセット
		gra.setColor(Color.white);
		gra.fillRect(0, 0, Constant.frameX, Constant.frameY);
	}
	
	private void space(int i, int j){ //空欄表示
	
		int x = j*Constant.spaceLength + Constant.boardX;
		int y = i*Constant.spaceLength + Constant.boardY;
		gra.setColor(Color.black);
		gra.fillRect(x,y,Constant.spaceLength,Constant.spaceLength);
		gra.setColor(Color.gray);
		gra.drawRect(x+1,y+1,Constant.spaceLength-3,Constant.spaceLength-3);
	}
	
}
