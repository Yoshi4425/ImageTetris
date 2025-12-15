package main;

import paint.GamePaint;
import paint.TetriMinoPaint;
import container.BonusFrame;
import container.SimpleTetriMino;

public class Constant {//定数表

	//---------プログラム外で変更可----------------------------------------------------------//
	public static int tetX;	//テトリスの横のマス数	4~30
	public static int tetY;	//テトリスの縦のマス数	10~50
	public static int nextTet;	//次に流れてくるテトリミノの表示数	0~10
	public static int frameX;	//表示するJFrameの横幅
	public static int frameY;	//表示するJFrameの縦幅
	public static final int frameNumber = setFrameNumber();	//フレーム用写真数	10~30

	//---------プログラムの変更に伴い変更----------------------------------------------------------//
	//テトリミノの追加は未対応 ホールドや次のテトリミノの表示部分に大幅な変更を要する
	public static final int tetType = 7; 	//テトリミノの種類 *****Containerのジェネレートテトも要変更*****
	public static final int maxDelete = 4;	//一度に消去できる最大の行数
	public static final int maxHeight = 2;	//テトリミノの最大の高さ（初期位置）
	public static final int maxWidth = 4;	//テトリミノの最大の幅（初期位置）
	
	//---------上記定数から定まる定数----------------------------------------------------------//
	public static int spaceLength;	//マスの一辺の長さ
	public static int centerTet;	//tetXの中心
	
	public static int boardX;	//盤面の左上x座標
	public static int boardY;	//盤面の左上y座標
	public static int boardWidth;	//盤面の横幅
	public static int blueFrameX;	//青枠の左上x座標
	public static int blueFrameY;	//青枠の左上y座標
	public static int effectSize;	//演出表示サイズ　正方形で表示する
	
	public static int holdX;	//ホールドの左上x座標
	public static int holdY;	//ホールドの左上y座標
	
	public static int tetBoxX;	//tetBox描画スペースの左上座標
	public static int tetBoxY;
	public static int marginX;	//tetBox表示時の余白
	public static int marginY;
	public static int spaceTetBox;		//tetBoxのマスの大きさ
	public static int unit;		//tetBoxの一つのテトリミノ表示スペースの高さ
	
	
	
	public static void set(int width, int height){	//static変数の初期化
		//各定数の設定
		frameX = width;
		frameY = height;
		tetX = FileSystem.numScan("System/Data/tetX.txt");
		tetY = FileSystem.numScan("System/Data/tetY.txt");
		nextTet = FileSystem.numScan("System/Data/nextTet.txt");
		
		//規定サイズより小さければ再調整
		if(frameX < 300) frameX = 300;
		if(frameY < 270) frameY = 270;
		
		//テトリスの１マスの大きさを決定
		int space = (frameY-60)/(tetY+2);	//フレームの縦サイズを縦マスの数で割る　+2は上下の空白と枠の分
		if(space*(tetX+16) > frameX) {	//この大きさで横幅も問題ないかを確認 +16はホールド(4)、フレームバー(1)、次のテトリミノ(4)、左右のスペースと枠(7)
			space = (frameX)/(tetX+16);	//問題があれば横幅基準でマスの大きさを決定
		}
		spaceLength = space;	//マスの長さを決定
	
		//tetXの中心を設定
		centerTet = tetX/2 + tetX%2 -1;
		
		//枠の座標を決定
		int halfTetX = tetX/2 + tetX%2;	//盤面のマス数の半分
		int halfTetY = tetY/2 + tetY%2;	//奇数の場合切り上げ
		
		boardX = frameX/2 - halfTetX*spaceLength;	//盤面の左上座標を設定
		boardY = frameY/2 - halfTetY*spaceLength +20;
		blueFrameX = boardX - spaceLength;	//青枠の左上座標を設定
		blueFrameY = boardY - spaceLength/2;
		
		//規定サイズより小さければ再調整
		if(blueFrameX < 85) {
			int gapX = 85 - blueFrameX;
			frameX += gapX;
			boardX += gapX;
			blueFrameX += gapX;
		}
		if(frameY < blueFrameY+spaceLength*7/2 + 75) {
			frameY = blueFrameY+spaceLength*7/2 + 75;
		}
		
		//フレーム表示、演出に使う定数の設定
		boardWidth = spaceLength * tetX;
		if(frameX > frameY) effectSize = frameY *9 /10; //フレームの縦横小さい方から演出サイズを決定
		else effectSize = frameX *9 /10;
		
		//ホールドの枠決定
		holdX = blueFrameX - 4*spaceLength;	//4マス分のスペース
		holdY = blueFrameY;	//青枠のy座標と一緒
		
		//次のテトリミノ表示に関係する定数
		tetBoxX = boardX + (int)(spaceLength*(tetX+0.5));	//盤面から0.5マス右
		tetBoxY = boardY - spaceLength/6;	//盤面から1/6マス上
		if(nextTet==0)
			unit = (spaceLength*tetY + spaceLength/3);
		else
			unit = (spaceLength*tetY + spaceLength/3) /nextTet;	//盤面+1/3マスの高さを次のテトリミノ表示数で割る
		int length = unit *4 /10;	//縦の空白は１マスの1/4 10=1+4+4+1
		if(length > spaceLength*4 /18 *4)	//横幅をオーバーしてしまう場合 18=1+4+4+4+4+1
			length = spaceLength *4 /18 *4;	
		spaceTetBox = length;	//マスの大きさを設定
		marginX =  (spaceLength *4 - length * 4)/2;	//黒い部分から4マス分のスペースを覗いた余白を二分する
		marginY = (unit - length*2)/2;	//ひとつあたりの高さから2マス分のスペースを覗いた余白を二分する
		
		GamePaint.setScoreSize();
		TetriMinoPaint.set();
		SimpleTetriMino.setStatic();
		BonusFrame.setStatic();
	}
	
	private static int setFrameNumber() {	//フレーム用写真数の決定 10~31の間、10+3nという条件のもと、写真数によって決定
		int num = FileSystem.imageScan("System").length;	//写真の数
		
		if(num <= 10) return 10;	//10以下は10
		else if(num < 31) {
			int x = num +1;	//ずれを補正 	ex)11,12,13 → 12,13,14
			x = x - x%3;	//3の剰余をひく		ex)12,13,14 → 12
			return x+1;	//10+3nにする		ex)12 → 13					
		}else return 31;	//31以上は31
	}
}
