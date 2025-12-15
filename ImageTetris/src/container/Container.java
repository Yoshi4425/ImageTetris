package container;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import java.awt.Graphics;
import javax.swing.JFrame;
import paint.ContainerPaint;

import tetriMino.*;
import main.Constant;

public class Container{	//次のテトリミノやフレームを管理する
	private final int nextTet = Constant.nextTet;	//次に流れてくるテトリミノの表示数
	private final int tetType = Constant.tetType;	//テトリミノの種類 通常7
	private final int frameNumber = Constant.frameNumber;	//フレーム用写真数
	
	private Random rand = new Random();	
	
	private TetriMino[] tetBox = new TetriMino[nextTet];	//横に表示される次に流れるテトリミノ
	private List<TetriMino> sevenTet = generateTet();//七種一巡のために全種のテトリミノを生成し取り出していく袋
	
	private List<BonusFrame> frameBox = new ArrayList<BonusFrame>();	//フレームを管理する箱
	
	private ContainerPaint paint;
	
	public Container(Graphics gra, JFrame jf) {
		paint = new ContainerPaint(gra,jf);
		
		for(int i=0; i<nextTet; i++) {
			tetBox[i] = getSevenTet();	//sevenTetから一つ取り出す
		}
		generateFrame(Constant.tetY-1);	//一番下の座標を下底としてフレームを生成
	}
	
	
	//------------------TetriMino---------------------------------------------------------------	
	
	public TetriMino popTet() {	//テトリミノをtetBoxから取り出す
		TetriMino tet;
		if(nextTet==0)
			tet = getSevenTet();
		else {
			tet = tetBox[0];	//一番上のものを選択
			for(int i=0; i<nextTet-1; i++) {
				tetBox[i] = tetBox[i+1];	//tetBoxをひとつずつ次へ進める
			}
			tetBox[nextTet-1] = getSevenTet();	//tetBoxの最後にsevenTetから取り出したテトを与える
		}
		
		return tet;	//一番上のテトリミノを返す
	}
	
	private TetriMino getSevenTet() {	//七種一巡の管理をする袋からテトリミノを取り出す
		int item = sevenTet.size();	//袋の中身の数を取得
		TetriMino tet;
		
		if(item == 0) {	//袋が空なら新しく生成
			sevenTet = generateTet();	
			item = tetType;	//itemを更新
		}
			
		int r = rand.nextInt(item);	//袋の中身の何番目を取り出すかを乱数で決定
		tet = sevenTet.get(r);	//袋の中身を入手
		sevenTet.remove(r);		//取り出したテトリミノを削除
		
		return tet;	//取り出したテトリミノを返す
	}
		
	private List<TetriMino> generateTet() {	//七種のテトリミノをまとめて生成

		return new ArrayList<TetriMino>(Arrays.asList(new Tet0(), new Tet1(), new Tet2(),
																new Tet3(), new Tet4(), new Tet5(), new Tet6()));
	}
	
	
	//------------------BonusFrame---------------------------------------------------------------
	
	private void generateFrame(int index) {	//フレームの生成、引数はフレームの下底のy座標
		int r;
		BonusFrame frame;
		do {
			r = rand.nextInt(frameNumber);	//フレームの写真番号を決定
			frame = new BonusFrame(r,index);	//フレームの生成
			frameBox.add(frame);	//作成したフレームをframeBoxに追加
			index = frame.upper() -1;	//indexを作成したフレームの上底の一つ上に設定
		}while(index >= 0);	//次に作成するフレームが枠内に入るうちは生成を繰り返す
	}
	
	public float bonus(int count, int[] del) {	//ミノ消去時に呼び出され、ボーナスポイントの判定を行い、倍率を返す
		BonusFrame frame;
		float percent = 1.0f;
		
		for(int i=0; i<frameBox.size(); i++) {	//全てのフレームについて
			frame = frameBox.get(i);	//要素を取得
			percent *= frame.remove(count,del);	//消去による影響をフレームに反映
			
			if(frame.none()) {	//フレームがなくなればframeBoxから消去
				frameBox.remove(i);
				i--;	//インデックスを調整
			}
		}
		fall();	//消去により空いたスペースにフレームを落下
		
		if(frameBox.get(frameBox.size()-1).upper() > 0) {	//最後尾（一番上）のフレームの上底が0より大きければ
			generateFrame(frameBox.get(frameBox.size()-1).upper() -1);	//その一つ上を下底とする新しいフレームを生成
		}
		return percent;
	}
	
	public void fall() {	//フレームの落下
		frameBox.get(0).fall(null);	//先頭（一番下）のフレームを落下
		for(int i=1; i<frameBox.size(); i++) {	//それ以降の全てのフレームについて
			frameBox.get(i).fall(frameBox.get(i-1));	//ひとつ下のフレームを基準にして落下
		}
	}
	
	
	//----------描画------------------------------------------------------------------------------------//
	
	public void paintTetBox() {
		paint.tetBox(tetBox);
	}
	
	public void paintFrame() {
		paint.frame(frameBox);
	}
}
