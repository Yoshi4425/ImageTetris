package main;

import javax.swing.JFrame;
import java.awt.Graphics;
import paint.GamePaint;

import container.*;
import setting.SettingFrame;

public class Game{
	//このプログラムでは、落ちてくるテトリミノをテト、落下し固定されているテトリミノをミノと呼称する
	private int[][] board = new int[Constant.tetY][Constant.tetX];	//テトリスの盤面 空欄を0、ミノがあれば1とする　左上が(0,0)
	private GamePaint paint;	//描画用インスタンス
	private Container container;	//次のテトやフレームを用意するコンテナ
	private TetriMino tet;	//持ちテト
	private TetriMino hold;	//ホールド
	private Timer timer;
	
	private int score = 0;
	private int highScore;
	private float percent = 1;	//得点にかかるフレーム倍率
	private int time = 1500; 	//テトが落ちる時間
	private boolean change = false;	//変更中はtrue
	private boolean checkHold = false;	//ホールドを交換したらtrue
	private boolean pause = false;	//ポーズ中はtrue
	private boolean reset = false;	//リセットしたらtrue
	private boolean finish = false;	//終了したらtrue
	
	public void start(Graphics gra, JFrame jf) {	//開始　開始時のみ呼び出される
		paint = new GamePaint(gra, jf);
		container = new Container(gra, jf);	
		highScore = FileSystem.numScan("System/Data/highScore.txt");	//ハイスコアの呼び出し
		tet = container.popTet();	//コンテナからテトを取り出す
		tet.changeEstimate(board);	//落下位置の更新
		pause();	//開始時はポーズ画面から
		paintAll();
	}
	
	
//--------ゲームの基本進行-----------------------------------------------------------------------------------------------//
	
	public void tetStart(){	//サイクルの開始	
		//背景、盤面、スコア、次のテト、ホールド、持ちテトの表示
		paintAll();
		
		if(tet.finish(board)) {	//持ちテトで終了判定
			paint.finish(renewHighScore(), score, highScore);	//ハイスコアの設定、終了画面表示
			finish = true;	//終了フラグ
		}else {
			setTime();	//テトの落ちる時間の変更
			timer = new Timer(this,time);	//timeの時間を測るタイマーの生成
			
			change = false;	//テトの変更可
			stay(200);	//事前入力受付のため待機
			if(!reset)	//リセットが入力された場合タイマーの開始をキャンセル
				new Thread(timer).start();
		}
	}
	
	private void tetToMino() {	//テトが地面に落下したときに呼び出し、落下できないテトがミノになる
		tet.changeMino(board);
		container.paintFrame();	//フレーム表示
		paint.board(board);	//盤面表示
		checkDelete();	//ミノの削除判定
		
		tet = container.popTet();	//次のテトをコンテナから取り出す
		tet.changeEstimate(board);	//落下位置の更新
		checkHold = false;	//ホールドの再使用を可能にする
		new Thread(new Wait(this)).start();	//入力をリセットするため、一定時間待機
	}
	
	private void checkDelete() {	//ミノが揃っていれば消去
		boolean del;	//ある行について削除できればtrue
		int count = 0;//消去できる行数
		int[] delete = new int[Constant.maxDelete];	//消去できる行のインデックス
		
		for(int i=0; i<board.length; i++) {
			del = true;
			for(int j=0; j<board[i].length; j++) {
				if(board[i][j]==0) {	//ある行について空白の座標があれば
					del = false;	//フラグを折る	
					break;	//次の行へ
				}
			}
			if(del) {	//ある行について埋まっていれば
				for(int j=0; j<board[i].length; j++) {
					board[i][j] = 0;	//ミノを空欄に変更
				}
				delete[count] = i;	//削除した行のインデックスを記録
				count++;	//削除した行数を増やす
			}
		}
		if(count!=0) {	//削除できる行があれば
			stay(300);		//待機
			container.paintFrame();	
			paint.board(board);	//ミノに変わった盤面を表示
			paint.effect(count);	//削除の演出を表示	
			stay(300);	//待機
			percent = container.bonus(count, delete);	//ボーナスフレームによるボーナスの計算
			addScore(count);		//スコアの加算
			fall(count, delete);	//空いたスペースへミノを落下
		}
	}
	
	private void fall(int count, int[] del) {//消去してできたスペースへ落下
		int i,k;
		for(k=0; k<count; k++) {	//削除した行数だけ
			for(i=del[k]; i>0; i--) {	//削除した行から上へと
				board[i] = board[i-1];	//配列の付替で上の行をひとつ下の行へ移動
			}
			board[0] = new int[Constant.tetX];		//一番上の行に新しい配列を設定
		}
	}
	
	private void addScore(int count) {	//スコアを加算 一度に削除した行数を引数とする
		//削除した行数に応じて得点を加算　その際に倍率をかける
		switch(count) {
		case 1:
			score += 100*percent;
			break;
		case 2:
			score += 300*percent;
			break;
		case 3:
			score += 600*percent;
			break;
		case 4:
			score += 1000*percent;
			break;
		}
		paint.score(score,highScore);	//スコア表示
		stay(600);
	}
	
	private void setTime() {	//落ちる時間間隔の更新
		if(time > 1300) {		//現在の時間によって時間間隔の減少量を決定
			time -= 50;
		}else if(time > 1000) {
			time -= 30;
		}else if(time > 700) {
			time -= 10;
		}else if(time > 500){
			time -= 5;
		}else if(time > 300) {
			time -= 2;
		}else if(time > 200) {
			time -= 1;
		}
	}
	
	private boolean renewHighScore() {	//ハイスコアの更新　更新すればtrue
		if(score > highScore) {	//スコアのほうが大きければ
			FileSystem.numWrite("System/Data/highScore.txt", score);	//ハイスコアを更新しファイルに記録
			highScore = score;	//ハイスコアの更新	
			return true;	//更新したのでtrue
		}
		return false;		//更新しなかったのでfalse
	}
	

	//---------キー操作の実装-------------------------------------------------------------------------//
	
	public void down() {	//落下
		if(change) return;	//変更中なら中止
		change = true;		//変更フラグを立てる
		boolean stop = false;//落下できない場合true
		
		stop = tet.down(board);		//落下移動　落下できるかの判定

		change = false;	//変更終了
		if(stop) {		//落下できなければ
			change = true;	//変更中に設定
			timer.stop();		//タイマーの停止
			tetToMino();		//テトの接地時の処理
		}
	}
	
	public void drop() {	//一気に落下
		if(change) return;	//変更中なら中止
		change = true;		//変更フラグを立てる	

		while(!(tet.down(board)));	//落下できなくなるまでテトを落下
		
		timer.stop();	//タイマーの停止
		tetToMino();	//テトの接地時の処理
	}
	
	public void right() {	//右に移動
		if(change) return;	//変更中なら中止
		change = true;		//変更フラグを立てる
		
		tet.right(board);				//テトの右移動
		
		change = false;	//変更終了
	}
	
	public void left() {	//左に移動
		if(change) return;	//変更中なら中止
		change = true;		//変更フラグを立てる
		
		tet.left(board);				//テトの左移動
		
		change = false;	//変更終了
	}
	
	public void rotateL() {	//左に回転
		if(change) return;	//変更中なら中止
		change = true;		//変更フラグを立てる
		
		tet.rotateL(board);			//左回転
		
		change = false;	//変更終了
	}
	
	public void rotateR() {	//右に回転
		if(change) return;	//変更中なら中止
		change = true;		//変更フラグを立てる
		
		tet.rotateR(board);			//右回転
		
		change = false;	//変更終了
	}
	
	public void hold() {	//ホールド
		if(change || checkHold) return;	//変更中、ホールド使用済みならば中止
		change = true;	//変更フラグを立てる
		
		tet.hold();	//持ちテトの位置を初期化
		if(hold == null) {	//ホールドがなければ
			hold = tet;		//持ちテトをホールドに設定
			tet = container.popTet();	//新しいホールドを取り出す
			tet.changeEstimate(board);	//落下位置の更新
		}
		else {	//ホールドがあればホールドと持ちテトを入れ替える
			TetriMino tmp;
			tmp = tet;
			tet = hold;
			tet.changeEstimate(board);	//落下位置の更新
			hold = tmp;
		}
		
		checkHold = true;	//ホールドを使用済みに
		timer.stop();		//タイマーを停止
		tetStart();		//サイクルのリスタート
	}
	
	public void pause() {	//ポーズ
		if(reset) return;	//またはリセット済みならば中止
		
		if(pause) {	//ポーズ中なら
			pause = false;	//ポーズ終了
			tetStart();	//サイクルの再開
			
		}else {	//ポーズ中でなければ
			pause = true;	//ポーズする
			while(change) {	//変更が終わるまで待機
				if(finish) break; 	//終了済みなら
				stay(10);
			}
			if(timer != null) timer.stop();	//タイマーがあれば停止
			change = true;	//変更終了
			paint.pause();
		}
	}
	
	public void reset() {	//リセットしてリスタート
		
		reset = true;	//リセットフラグを立てる
		if(!finish && !pause) {	//終了済みかつポーズ中でなければ
			while(change) {	//変更が終わるまで待機
				stay(10);
			}
		}
		if(timer != null) timer.stop();	//タイマーがあれば停止
		change = true;	//変更終了
		paint.clear();	//画面をクリア
	}
	
	public void setting(GameFrame gf) {	//設定画面を開く
		if(!pause) //ポーズ中でなければポーズしてから設定	
			pause();
		
		new SettingFrame(gf);
	}
	
	
//----------その他の部品------------------------------------------------------------------------------//

	public void windowStop() {	//ウィンドウの変更によるポーズ
		if(!pause) pause();	//ポーズ中でなければポーズ
	}
	
	public void paintAll() {	//ポーズ画面を描画
		//背景、盤面、スコア、次のテト、ホールド、持ちテトの表示
		paint.back();
		container.paintFrame();
		paint.board(board);
		paint.score(score,highScore);
		container.paintTetBox();
		if(hold != null) hold.paintHold(checkHold);
		tet.paintTet();
		
		if(finish) paint.finish(score==highScore, score, highScore);	//終了画面表示
		if(pause) paint.pause();	//ポーズ画面を表示
	}
	
	private void stay(int stay) {	//一定時間待機 stayミリ秒だけ待機
		try {
			Thread.sleep(stay);
		}catch( InterruptedException ex){
			ex.printStackTrace();
		}
	}
}

class Wait implements Runnable{	//入力リセット用のモジュール
	Game game;
	public Wait(Game game) {	//ゲームを保存
		this.game = game;
	}
	public void run() {	//0.5秒待機し、サイクルを開始
		try {	
			Thread.sleep(500);
		}catch( InterruptedException ex){
			ex.printStackTrace();
		}
		game.tetStart();
	}
}
