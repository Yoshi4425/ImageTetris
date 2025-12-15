package container;

import main.Constant;
import paint.BonusFramePaint;

public class BonusFrame {
	private static int frameNumber;	//フレーム用写真数
	private static int tetY;	//テトリスの縦のマス数
	
	private int defFrame;	//初期のフレームサイズ
	private int nowFrame;	//現在のフレームサイズ
	private int index;	//一番下のy座標を記録
	
	private BonusFramePaint paint;
	
	public static void setStatic() {
		frameNumber = Constant.frameNumber;
		tetY = Constant.tetY;
	}
	
	public BonusFrame(int r, int in) {	//写真番号と下底のindexからフレームを生成
		float ratio = (float)r / frameNumber;	//フレーム番号が最大数の何割かを計算
		//それによりフレームのサイズを決定
		if(ratio<0.3f) defFrame = 1;			//三割未満
		else if(ratio<0.6f) defFrame = 2;	//六割未満
		else if(ratio<0.9f) defFrame = 3;	//九割未満
		else defFrame = 4;					//それ以上
	
		nowFrame = defFrame;	//初期フレームを現在のフレームに設定
		index = in;	//下底を設定
		
		paint = new BonusFramePaint(defFrame, r);
	}
	
	public float remove(int count, int[] del) {	//消去による影響の反映
		int change = 0;	//変更した行数
		for(int i=0; i<count; i++) {	//消去した行すべてについて
			if(del[i]>index-nowFrame && del[i]<=index) {	//消去インデックスがフレームの範囲内であれば
				change++;	//変更数を加算
			}
		}
		nowFrame -= change;	//フレーム数減少
		if(change == defFrame) {	//変更数と初期フレーム数が同じ（フレームを一気に消去した）ならば
			paint.bonus();
			return bonus();	//ボーナス発生
		}
		return 1.0f;
	}
	
	public float bonus() {	//ボーナスの倍率を返す
		switch(defFrame) {	//フレームの大きさにより倍率を決定
		case 1:
			return 1.2f;
		case 2:
			return 2.5f;
		case 3:
			return 5.0f;
		case 4:
			return 10.0f;
		default: 
			return 1.0f;
		}
	}
	
	public void fall(BonusFrame below) {	//消去による落下 引数は一個下のボーナスフレーム
		if(below==null) index = tetY-1;	//一個下のフレームがなければ下底を一番下に
		else index = below.upper() -1;	//一個下の上底の一つ上を下底に
	}
	
	public int upper() {	//上底のインデックス
		return index - nowFrame +1; 
	}
	
	public boolean none() {	//フレームが空になればtrue
		return (defFrame == 0);
	}
	
	
	//----------描画------------------------------------------------------------------------------------//
	
	public void bonusFrame() {
		paint.frame(nowFrame, upper());
	}
	
	public void frameBar(){
		paint.bar(upper(), nowFrame, nowFrame==defFrame);
	}
	
}
