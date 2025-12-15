package paint;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

import main.Constant;

public class BonusFramePaint {	//BonusFrame用の描画クラス
	public static Graphics gra;
	private static final Color smoke = new Color(50,50,50,200);
	
	private final Color color;
	private final Image image;
	
	public BonusFramePaint(int frame, int img) {
		color = setFrameC(frame);	//フレームサイズから色を決定
		image = ContainerPaint.frame[img];	//フレーム用の写真
	}
	
	private Color setFrameC(int frame) {	//フレームサイズに応じて色を決定
		switch(frame) {
		case 1:
			return Color.green;
		case 2:
			return Color.orange;
		case 3:
			return Color.red;
		case 4:
			return Color.blue;
		}
		return null;
	}
	
	public void frame(int now, int upper) {	//盤面にフレームの写真を表示
		if(upper < 0) {	//上からはみ出してしまう場合、フレームサイズを下げ上底を0に
			now = now + upper;
			upper = 0;
		}
			
		int y = upper*Constant.spaceLength + Constant.boardY;	//上端の座標を設定
		int centerH = image.getHeight(null)/2;	//画像の縦幅の中心座標を計算
		
		//フレームの範囲に、画像の中心からフレームサイズを計算して切り抜き画像表示
		gra.drawImage(image, Constant.boardX, y, Constant.boardX+Constant.boardWidth, y+now*Constant.spaceLength,
					0, centerH-now* (Constant.spaceLength *3 /2), 2000,
					centerH+now* (Constant.spaceLength *3 /2), null);
		
		for(int i=upper; i<upper+now; i++) {	//全マスにフレームの枠を描画
			for(int j=0; j<Constant.tetX; j++) {
				gra.setColor(color);	
				gra.drawRect(j*Constant.spaceLength + Constant.boardX+1,
						i*Constant.spaceLength + Constant.boardY+1, Constant.spaceLength-3, Constant.spaceLength-3);
			}
		}
	}
	
	public void bar(int upper, int leng, boolean can) {	//フレームバーの表示 canは一気に消去すればボーナスが発生するかどうか
		if(upper < 0) {	//上からはみ出してしまう場合、フレームサイズを下げ上底を0に
			leng = leng + upper;
			upper = 0;
		}
		gra.setColor(color);	//フレームカラーに設定
		int y = upper*Constant.spaceLength + Constant.boardY;	//上底を設定
		gra.fillRect(Constant.blueFrameX + Constant.spaceLength/3, y,
				Constant.spaceLength/3, Constant.spaceLength*leng-2);	//フレームバーを表示
		if(can) {
			gra.setColor(Color.white);	//フレーム同士を区切るため、色を変えて枠づけ
			gra.drawRect(Constant.blueFrameX + Constant.spaceLength/3 +1, y+1,
					Constant.spaceLength/3-3, Constant.spaceLength*leng-5);
		}else {
			gra.setColor(smoke);	//ボーナスが発生しない時、フレームバーを暗く表示
			gra.fillRect(Constant.blueFrameX + Constant.spaceLength/3, y,
					Constant.spaceLength/3, Constant.spaceLength*leng-2);
		}
	}
	
	public void bonus() {	//フレームのボーナス演出
		
		int h = image.getHeight(null);	//画像の縦幅、横幅を入手
		int w = image.getWidth(null);
		float ratio;
		int x,y;
		
		if(h > w) {	//縦が大きければ
			ratio = (float)Constant.effectSize/h;	//縦幅で比率を計算
			w = (int)(w*ratio);	//横幅と比率をかける
			h = Constant.effectSize;
		}else {	//横が大きい場合も同様
			ratio = (float)Constant.effectSize/w;
			h = (int)(h*ratio);
			w = Constant.effectSize;
		}
		y = Constant.frameY/2 - h/2;	//中心から縦幅の半分を左上とする
		x = Constant.frameX/2 - w/2;	//中心から横幅の半分を左上とする
		
		gra.setColor(smoke);	//スモークをたく
		gra.fillRect(0, 0, Constant.frameX, Constant.frameY);	
		gra.drawImage(image, x,y,w,h, null);	//画像表示
		
		try {
			Thread.sleep(1000);	//１秒待機
		}catch( InterruptedException ex){
			ex.printStackTrace();
		}
	}
	
}
