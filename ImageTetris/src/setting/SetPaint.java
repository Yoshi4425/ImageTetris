package setting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SetPaint {
	
	private Graphics gra;
	
	public SetPaint(Graphics g) {
		gra = g;
		gra.setFont(new Font("IPAGothic", Font.PLAIN, 35));
	}
	
	public void back() {	//全て描画
		gra.setColor(Color.white);
		gra.fillRect(0, 0, 1500, 800);
		
		gra.setColor(Color.black);
		//名称
		gra.drawString("横のマス", 150,110); 
		gra.drawString("縦のマス", 150,200);
		gra.drawString("Nextブロック表示数", 80,290);
		
		//枠
		gra.fillRect(470, 65, 100, 60);
		gra.fillRect(470, 155, 100, 60);
		gra.fillRect(470, 245, 100, 60);
		
		//決定ボタン
		gra.setColor(Color.red);
		gra.fillRect(130, 350, 150, 80);
		gra.setColor(Color.orange);
		gra.drawRect(140, 360, 130, 60);
		
		//キャンセルボタン
		gra.setColor(Color.blue);
		gra.fillRect(390, 350, 200, 80);
		gra.setColor(Color.cyan);
		gra.drawRect(400, 360, 180, 60);
		
		gra.setFont(new Font("IPAGothic", Font.PLAIN, 35));
		gra.setColor(Color.white);
		gra.drawString("決定",170,400);
		gra.drawString("キャンセル",402,400);
		
		gra.setColor(Color.blue);
		gra.setFont(new Font("IPAGothic", Font.PLAIN, 17));
		gra.drawString(" 4〜30で設定　推奨10", 170, 135);
		gra.drawString("10〜50で設定　推奨20", 170, 225);
		gra.drawString(" 0〜10で設定　推奨 6", 170, 315);
		gra.drawString("「決定」を押した場合、現在のゲームを終了して新しくゲームを開始します", 60, 460);
	}
	
	public void data(int num, int index) {	//値を記載 indexが0ならtetX、1ならtetY、2ならnextTet
		gra.setColor(Color.white);
		gra.fillRect(475, 70 +90*index, 90, 50);	//白埋め
		gra.setFont(new Font("IPAGothic", Font.PLAIN, 35));
		gra.setColor(Color.black);
		gra.drawString(String.format("%2d",num),500,107 +90*index);
	}

	public void edit(int edit, boolean c) {	//編集中の箇所に色付け trueで色付け falseで塗り消し
		if(c) gra.setColor(Color.red);
		else gra.setColor(Color.white);
			
		if(edit < 0) return;
		else if(edit < 3) {	//各値
			gra.drawRect(455, 50 +90*edit, 130, 90);
		}else if(edit == 3) {	//決定
			gra.drawRect(120, 340, 170, 100);
		}else if(edit == 4) {	//キャンセル
			gra.drawRect(380, 340, 220, 100);
		}
	}
	
}
