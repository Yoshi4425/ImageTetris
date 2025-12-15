package setting;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import main.GameFrame;

public class SettingFrame extends JFrame implements KeyListener, WindowListener, ComponentListener, MouseListener{
	private static final long serialVersionUID = 0;
	private GameFrame gf;
	private Setting set;
	
	public SettingFrame(GameFrame gf) {
		super("Setting");	//タイトルの設定
		setLocation(100,100);	//描画位置を左上に設定
		setSize(700,500);	//画面サイズの設定
		setResizable(true);	//サイズ変更不可
		setBackground(Color.white);	//背景色の設定
	    setVisible(true);	//可視化
	    
	    this.gf= gf;
	    set = new Setting(getGraphics(),this);
		
		addKeyListener(this);//キーリスナーの追加
		addWindowListener(this);	//ウィンドウリスナーの追加
		addComponentListener(this);	//コンポーネントリスナーの追加
		addMouseListener(this);	//マウスリスナーの追加
	}
	
	public void finishSetting(boolean change) {	//設定の終了
		if(change) gf.changeSetting();	//変更するならgf呼び出し
		
		//終了処理
		set = null;
		setVisible(false); //不可視
		removeKeyListener(getKeyListeners()[0]);	//リスナーの除去
		removeWindowListener(getWindowListeners()[0]);
		removeComponentListener(getComponentListeners()[0]);
		removeMouseListener(getMouseListeners()[0]);
	}
	
	public void keyPressed(KeyEvent e) {	//入力
		switch(e.getKeyCode()) {
		//数字入力
		case KeyEvent.VK_0:
			set.inputData(0);
			break;
		case KeyEvent.VK_1:
			set.inputData(1);
			break;
		case KeyEvent.VK_2:
			set.inputData(2);
			break;
		case KeyEvent.VK_3:
			set.inputData(3);
			break;
		case KeyEvent.VK_4:
			set.inputData(4);
			break;
		case KeyEvent.VK_5:
			set.inputData(5);
			break;
		case KeyEvent.VK_6:
			set.inputData(6);
			break;
		case KeyEvent.VK_7:
			set.inputData(7);
			break;
		case KeyEvent.VK_8:
			set.inputData(8);
			break;
		case KeyEvent.VK_9:
			set.inputData(9);
			break;
		case KeyEvent.VK_BACK_SPACE:
			set.inputData(-1);
			break;
			
		//操作位置の移動
		case KeyEvent.VK_ENTER:
			set.keyCode(0);
			break;	
		case KeyEvent.VK_UP:
			set.keyCode(-2);
			break;
		case KeyEvent.VK_W:
			set.keyCode(-2);
			break;
		case KeyEvent.VK_LEFT:
			set.keyCode(-1);
			break;
		case KeyEvent.VK_A:
			set.keyCode(-1);
			break;
		case KeyEvent.VK_RIGHT:
			set.keyCode(1);
			break;
		case KeyEvent.VK_D:
			set.keyCode(1);
			break;
		case KeyEvent.VK_DOWN:
			set.keyCode(2);
			break;
		case KeyEvent.VK_S:
			set.keyCode(2);
			break;
		}
	}
	
	public void mousePressed(MouseEvent e) {	//クリック入力
	    set.paintAll();
	    
	    int x = e.getX();	// イベントが発生したところのＸ座標を記憶
	    int y = e.getY();	// イベントが発生したところのＹ座標を記憶
	    
	    //文字か入力ボックス付近をクリック
	    if((x>145 && x<300 && y>70 && y<115) || (x>465 && x<575 && y>65 && y<135))
	    	set.changeEdit(0);
	    else if((x>145 && x<300 && y>160 && y<205) || (x>465 && x<575 && y>155 && y<225))
	    	set.changeEdit(1);
	    else if((x>80 && x<400 && y>250 && y<295) || (x>465 && x<575 && y>245 && y<315))
	    	set.changeEdit(2);
	    else if(x>130 && x<290 && y>350 && y<430)
	    	set.changeEdit(3);
	    else if(x>390 && x<590 && y>350 && y<430)
	    	set.changeEdit(4);
	    else 
	    	set.changeEdit(-1);
	}	
	
	public void mouseEntered(MouseEvent arg0) {	//マウスが入った時、出た時、クリックした時に表示
		set.paintAll();
	}
	public void mouseExited(MouseEvent arg0) {
		set.paintAll();
	}
	
	public void windowActivated(WindowEvent arg0) {	
		set.paintAll();
	}
	
	public void windowClosing(WindowEvent arg0) {		//消去時はキャンセル扱い
		finishSetting(false);
	}
	
	
	public void keyTyped(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void componentHidden(ComponentEvent arg0) {
	}
	public void componentMoved(ComponentEvent arg0) {
	}
	public void componentShown(ComponentEvent arg0) {	
	}
	public void componentResized(ComponentEvent arg0) {	
	}
	public void windowClosed(WindowEvent arg0) {		
	}
	public void windowDeiconified(WindowEvent arg0) {	
	}
	public void windowIconified(WindowEvent arg0) {	
	}
	public void windowDeactivated(WindowEvent arg0) {
	}
	public void windowOpened(WindowEvent arg0) {
	}
	public void mouseClicked(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
}
