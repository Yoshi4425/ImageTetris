package main;

public class Timer implements Runnable{	//タイマー
	private Game game;
	private int time;
	private boolean running = true;	//実行中はtrue
	
	public Timer(Game gm, int t) {	//生成時にゲームオブジェクトと測る時間を設定
		game = gm;	
		time = t;
	}
	
	public void run() {
		while(running) {	//実行中ならば
			try {
				Thread.sleep(time);	//一定時間待機して
			}catch( InterruptedException ex){
				ex.printStackTrace();
			}
			if(running)	//下移動や急降下などの処理に備える
				game.down();	//落下処理
		}
	}

	public void stop() {	//タイマーの停止処理
		running = false;	//実行フラグを折る
		game = null;		//参照をやめる
	}
}
