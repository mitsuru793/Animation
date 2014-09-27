import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

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

		// 縦に作成するボールの数を求める
		NUM_BALL = HEIGHT / Ball.SIZE;
		System.out.println(NUM_BALL);
		// ボールを格納する配列を作成
		ball = new Ball[NUM_BALL];
		Random random = new Random();
		// x座標をばらばらに縦にボールを並べる
		// ボールの移動方向・速度はランダム
		for (int i = 0; i < NUM_BALL; i++) {
			int vx = random.nextInt(5) - 2;
			ball[i] = new Ball(random.nextInt(WIDTH - Ball.SIZE),
					i * Ball.SIZE, vx <= 0 ? vx - 1 : vx, 0);
		}

		// スレッドを起動
		thread = new Thread(this);
		thread.start();
	}

	@Override
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
				ball[i].moveX();
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
