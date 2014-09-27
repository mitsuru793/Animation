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
		NUM_BALL = HEIGHT / Ball.SIZE / 2;
		System.out.println(NUM_BALL);
		// ボールを格納する配列を作成
		ball = new Ball[NUM_BALL];
		for (int i = 0; i < NUM_BALL; i++) {
			ball[i] = new Ball(0, 2 * i * Ball.SIZE, 0, 1);
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
		// プログラムが終了するまでフレーム処理を繰り返す
		int i = 0;
		while (true) {
			// 各ボールを速度分だけ移動させる
			ball[i].moveY();
			// ボール同士の縦方向の衝突判定
			for (int bumpBall = 0; bumpBall < NUM_BALL; bumpBall++) {
				if (i == bumpBall) {
					continue;
				}
				// ボールが次のボールがある場合は追い抜かないようにする
				if (bumpBall > i && ball[i].vy > 0
						&& ball[i].y + Ball.SIZE >= ball[bumpBall].y) {
					// ボールが上から下に次のボールにぶつかる時
					ball[i].y = ball[bumpBall].y - Ball.SIZE;
					// 次のボールに速度が吸収される
					ball[bumpBall].vy = ball[i].vy;
					ball[i].vy = 0;
					// 次のボールがあるかチェック
					if (i < ball.length - 1) {
						i++;
					}
					break;
				} else if (ball[i].vy < 0 && bumpBall < i
						&& ball[i].y <= ball[bumpBall].y + Ball.SIZE) {
					// ボールが下から上に次のボールにぶつかる時
					ball[i].y = ball[bumpBall].y + Ball.SIZE;
					// 次のボールに速度が吸収される
					ball[bumpBall].vy = ball[i].vy;
					ball[i].vy = 0;
					// 次のボールがあるかチェック
					if (i > 0) {
						i--;
					}
					break;
				}
			}

			// ボールを再描画
			repaint();

			// 10ミリ秒だけ休止
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
