package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements KeyListener, WindowListener, ComponentListener, MouseListener{
	private static final long serialVersionUID = 0;
	private Game game;
	
	public GameFrame(String title) {
		super(title);	//タイトルの設定
		setLocation(0,0);	//描画位置を左上に設定
		//画面サイズの設定
		setMinimumSize(new Dimension(300, 270));
		setSize(FileSystem.numScan("System/Data/frameX.txt"),
				FileSystem.numScan("System/Data/frameY.txt"));	//前回の設定からサイズ決定
		setConstant();
		setBackground(Color.white);	//背景色の設定
		setVisible(true);	//可視化
		
		game = new Game();
		game.start(getGraphics(),this);	//ゲームの開始
		
		addKeyListener(this);//キーリスナーの追加
		addWindowListener(this);	//ウィンドウリスナーの追加
		addComponentListener(this);	//コンポーネントリスナーの追加
		addMouseListener(this);	//マウスリスナーの追加
	}
	
	public static void main(String[] args) {	//プログラムの開始
		new GameFrame("Tetris");
	}
	
	public void setConstant() {	//定数の更新、設定
		Constant.set(getWidth(), getHeight());
		setSize(Constant.frameX, Constant.frameY);	//Constantで調整された画面サイズの再設定
	}
	
	public void changeSetting() {	//変更の実行
		setConstant();	//定数の変更後、リセット処理
		game.reset();	
		game = new Game();
		game.start(getGraphics(),this);	
	}
	
	
	public synchronized void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_D:	//右移動
			game.right();
			break;
		case KeyEvent.VK_A:	//左移動
			game.left();
			break;
		case KeyEvent.VK_S:	//下移動
			game.down();
			break;
		case KeyEvent.VK_W:	//急降下
			game.drop();
			break;
		case KeyEvent.VK_J:	//左回転
			game.rotateL();
			break;
		case KeyEvent.VK_K:	//右回転
			game.rotateR();
			break;
		case KeyEvent.VK_L:	//ホールド
			game.hold();
			break;
		case KeyEvent.VK_P:	//ポーズ
			game.pause();
			game.paintAll();
			break;
		case KeyEvent.VK_T:	//設定
			game.setting(this);
			break;
		case KeyEvent.VK_ESCAPE:	//リセット
			game.reset();	//リセット後
			game = new Game();	//ゲームを新規生成
			game.start(getGraphics(),this);	//ゲーム開始
			break;
		case KeyEvent.VK_DELETE:	//終了
			FileSystem.numWrite("System/Data/frameX.txt", getWidth());
			FileSystem.numWrite("System/Data/frameY.txt", getHeight());
			System.exit(0);
			break;
		}
	}
	
	public void windowActivated(WindowEvent arg0) {		//ウィンドウを選択時に表示
		game.paintAll();
	}

	public void windowDeactivated(WindowEvent arg0) {	//ウィンドウが選択されなくなった時ポーズ
		game.windowStop();
	}
	
	public void windowClosing(WindowEvent arg0) {			//ウィンドウを閉じるとき終了
		FileSystem.numWrite("System/Data/frameX.txt", getWidth());
		FileSystem.numWrite("System/Data/frameY.txt", getHeight());
		System.exit(0);		
	}
	
	public void componentResized(ComponentEvent arg0) {	//ウィンドウのサイズを変更する時ポーズ
		game.windowStop();
	}
	
	public void mouseEntered(MouseEvent arg0) {	//マウスが入った時、出た時、クリックした時にサイズ更新、ポーズ
		setConstant();
		game.paintAll();
	}
	public void mouseExited(MouseEvent arg0) {
		setConstant();
		game.paintAll();
	}
	public void mousePressed(MouseEvent arg0) {		
		setConstant();
		game.paintAll();
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
	public void windowClosed(WindowEvent arg0) {		
	}
	public void windowDeiconified(WindowEvent arg0) {	
	}
	public void windowIconified(WindowEvent arg0) {	
	}
	public void windowOpened(WindowEvent arg0) {
	}
	public void mouseClicked(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
}