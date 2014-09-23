import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	// パネルサイズ
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;
	// ボールの数
	private static int NUM_BALL;
	// ボールを格納する配列
	private Ball[] ball;
	// アニメーション用スレッド
	private Thread thread;

	// 何ミリ秒後にアニメーションを開始するか
	private int startTime = 500;

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);

		// 縦一列に作成できるボールの数を求める
		NUM_BALL = HEIGHT / Ball.getSize();
		System.out.println(NUM_BALL);
		// ボールを格納する配列を作成
		ball = new ColorChangeBall[NUM_BALL];
		// ボールを作成
		for (int i = 0; i < NUM_BALL; i++) {
			ball[i] = new ColorChangeBall(0, Ball.getSize() * i, 1, 0);
		}

		// スレッドを起動
		thread = new Thread(this);
		thread.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 各ボールを描画
		for (int i = 0; i < NUM_BALL; i++) {
			ball[i].draw(g);
		}
	}

	// メインループ
	public void run() {
		// アニメーションの開始時間を遅らせる
		try {
			Thread.sleep(startTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// プログラムが終了するまでフレーム処理を繰り返す
		while (true) {
			// 各ボールを速度分だけ移動させる
			for (int i = 0; i < NUM_BALL; i++) {
				ball[i].move();
				
			}

			// ボールを再描画
			repaint();

			// 10ミリ秒だけ休止
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}