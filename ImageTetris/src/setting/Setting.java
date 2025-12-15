package setting;

import java.awt.Graphics;
import main.FileSystem;

public class Setting {
	
	private SetPaint paint;
	private SettingFrame sf;
	
	private int[] value = new int[]{
			FileSystem.numScan("System/Data/tetX.txt"),	//tetX 4~30
			FileSystem.numScan("System/Data/tetY.txt"),	//tetY 10~50	
			FileSystem.numScan("System/Data/nextTet.txt")	//nextTet 0~10
		};
	private int edit = 0;	//編集中の箇所 0でtetX 1でtetY 2でnextTet 3で決定ボタン 4でキャンセルボタン
	
	public Setting(Graphics gra, SettingFrame sf) {
		paint = new SetPaint(gra);
		this.sf = sf;
	}
	
	public void paintAll() {	//全て描画
		paint.back();
		paint.data(value[0],0);
		paint.data(value[1],1);
		paint.data(value[2],2);
		paint.edit(edit,true);
	}
	
	public void inputData(int data) {	//数字入力で呼び出し　各値を変更する　backSpaceは-1
		if(edit<0 && edit>2) return;	//0,1,2でなければキャンセル
		if(data == -1) 
			value[edit] /= 10;	//桁を下げる
		else {
			value[edit] *= 10;	//桁を上げ
			value[edit] %= 100;	//下二桁を残す
			value[edit] += data;	//変更を加える
		}
		paint.data(value[edit], edit);
	}
	
	public void changeEdit(int to) {	//編集位置の変更 引数は変更先
		checkValue();
		if(edit == to) {	//選択中の位置と新しい編集位置が同じ
			if(edit >= 3) {
				if(edit==3) {
					changeFile();	//変更をファイルに反映
					sf.finishSetting(true);	//決定ボタンなら変更
				}
				else sf.finishSetting(false);	//キャンセルボタンなら変更なし
				sf = null;
			}
		}else {
			paint.edit(edit,false);
			edit = to;
			paint.edit(edit,true);
		}
	}
	
	private void checkValue() {	//値は範囲内に修正する
		if(edit==0) {
			if(value[0] < 4) value[0] = 4;
			if(value[0] > 30) value[0] = 30;
			paint.data(value[0],0);
		}else if(edit==1) {
			if(value[1] < 10) value[1] = 10;
			if(value[1] > 50) value[1] = 50;
			paint.data(value[1],1);
		}else if(edit==2) {
			if(value[2] < 0) value[2] = 0;
			if(value[2] > 10) value[2] = 10;
			paint.data(value[2],2);
		}
	}
	
	public void keyCode(int key) {	//キー入力を調整 引数は、0がenter、-2が上、-1が左、1が右、2が下
		if(key == 0) { 	//enterなら現在と同じ位置
			if(edit >= 3)	//決定、キャンセルなら
				changeEdit(edit);
			else	//-1,0,1,2なら
				changeEdit(edit+1);
		}else if(key == -2) {	//上に移動
			if(edit==1 || edit==2)	//1,2なら一つ上へ
				changeEdit(edit-1);
			else if(edit>2) 	//決定、キャンセルなら2へ
				changeEdit(2);
		}else if(key == -1) {	//左に移動
			if(edit != 3) //決定以外なら決定へ
				changeEdit(3);
		}else if(key == 1) {		//右に移動
			if(edit != 4)	//キャンセル以外ならキャンセルへ
				changeEdit(4);
		}else {	//下に移動
			if(edit < 3)	//0,1,2なら一つ下へ
				changeEdit(edit+1);
		}
	}
	
	private void changeFile() {
		FileSystem.numWrite("System/Data/tetX.txt", value[0]);
		FileSystem.numWrite("System/Data/tetY.txt", value[1]);
		FileSystem.numWrite("System/Data/nextTet.txt", value[2]);
	}

}
