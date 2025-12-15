package paint;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.util.List;

import container.BonusFrame;
import container.TetriMino;
import main.FileSystem;
import main.Constant;

public class ContainerPaint {	//Containerの描画クラス
	static Image[] frame = new Image[Constant.frameNumber];
	private Graphics gra;
	
	public ContainerPaint(Graphics g, JFrame jf) {
		gra = g;
		
		//画像の読み込み
		MediaTracker tracker = new MediaTracker(jf);
		setFrame(tracker);
		for(int i=0; i<frame.length; i++) {
			tracker.addImage(frame[i],0);
		}
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
	}

	private void setFrame(MediaTracker tracker) {	//フレーム画像のセット
		Image image;
		int h,w;
		float ratio;
		
		//Imageフォルダ内の画像ファイルのパスを配列で入手、パスの順番を整列
		String[] path = sortFrame(FileSystem.imageScan("Image"));	
		
		for(int i=0; i<path.length; i++) {	//全てのパスについて
			image = Toolkit.getDefaultToolkit().getImage(path[i]);	//画像読み込み
			
			tracker.addImage(image,0);	//画像の準備をし
			try {
				tracker.waitForAll();
			} catch (InterruptedException e) {
			}
			h = image.getHeight(null);	//画像の縦横サイズを入手
			w = image.getWidth(null);
				
			ratio = 2000f/w;	//横幅が盤面の横幅の三倍になるような比率
			w = 2000;	
			h = (int)(h*ratio);	//縦幅を計算	
			frame[i] = image.getScaledInstance(w, h, 0);	//画像の大きさを変更
		}
	}
	
	private String[] sortFrame(Object[] files) {	//ファイル名の配列の順番をフレーム用に整列
		
		String[] path = new String[frame.length];	//写真の数と同じ大きさの配列を作成
		String dummy = "System/dummyFrame.png";	//写真が足りない場合のダミー画像
		int i;
		
		for(i=0; i<path.length; i++) {	//初期化
			path[i] = dummy;
		}
		
		int dif = path.length/3;	//一回あたりの変化量
		int index = path.length-1;	//パスの最後のインデックスを設定
		for(i=0; (i<files.length && i<path.length); i++) {
			path[index] = (String)files[i];	//ファイルのパスをパス配列に移す
			index -= dif;
			if(index<0) index+=path.length;	//マイナスならば写真の数だけ足す
		}
		
		for(i=1; i<path.length; i++) {//空欄埋め
			if(path[i] == dummy)
				path[i] = path[i-1];
		}
		
		return path;
	}

	public void tetBox(TetriMino[] tetBox) {//次のテトリミノ表示
		gra.setColor(Color.black);	//黒く埋める
		gra.fillRect(Constant.tetBoxX, Constant.tetBoxY, 
				Constant.spaceLength*4, Constant.spaceLength*Constant.tetY + Constant.spaceLength/3);	//tetBoxの黒い部分を表示
		
		gra.setColor(Color.green);	//緑色でテトリミノを表示
		for(int i=0; i<Constant.nextTet; i++) {
			tetBox[i].paintTetBox(i);	//各テトを表示、iは何番目かを表す
		}
	}

	public void frame(List<BonusFrame> frameBox) {	//盤面表示の前にフレーム画像を表示する　その際にフレームバーも表示する
		BonusFrame bonus;
		
		gra.fillRect(Constant.blueFrameX + Constant.spaceLength/3, Constant.boardY,	//フレームバーを黒塗り
				Constant.spaceLength/3, Constant.spaceLength*Constant.tetY);
		
		for(int i=0; i<frameBox.size(); i++) {	//全てのフレームについて
			bonus = frameBox.get(i);	//要素を取得
			gra.setColor(Color.black);	
			bonus.bonusFrame();	//フレーム画像表示
			bonus.frameBar();		//フレームバーの表示
		}
	}

}
