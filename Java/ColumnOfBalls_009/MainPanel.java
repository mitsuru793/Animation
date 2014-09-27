import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	// パネルサイズ
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;
	// ボールを縦幅の中心に描く為のy座標
	public static int center_ball_y;
	// ボールの数
	private static int NUM_BALL;

	// ボールを格納する配列
	private Ball[] ball;
	// アニメーション用スレッド
	private Thread thread;

	// 何ミリ秒後にアニメーションを開始するか
	private int startTime = 1000;

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);

		// ボールを横場の中心に描く為のx座標
		center_ball_y = HEIGHT / 2 - Ball.SIZE / 2;

		// 縦に作成するボールの数を求める
		NUM_BALL = HEIGHT / Ball.SIZE;
		System.out.println(NUM_BALL);
		// ボールを格納する配列を作成
		ball = new Ball[NUM_BALL];

		// スレッドを起動
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 各ボールを描画
		for (int i = 0; i < NUM_BALL; i++) {
			if (ball[i] != null) {
				ball[i].draw(g);
			}
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

		// 作成したボールの数
		int numDeployBall = 0;
		// ボール作成をずらす為に使う
		int delayCreateTime = 5;
		// プログラムが終了するまでフレーム処理を繰り返す
		while (true) {
			// 最後のボールが作成されてない場合
			if (ball[NUM_BALL - 1] == null && delayCreateTime <= 0) {
				// 交互に縦に逆方向に進むよう設定
				if (numDeployBall % 2 == 0) {
					ball[numDeployBall] = new Ball(0, center_ball_y, 1, 1);
				} else {
					ball[numDeployBall] = new Ball(0, center_ball_y, 1, -1);
				}
				numDeployBall++;
				delayCreateTime = 5;
			}
			if (delayCreateTime > 0) {
				delayCreateTime--;
			}
			// 各ボールを速度分だけ移動させる
			for (int i = 0; i < numDeployBall; i++) {
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
