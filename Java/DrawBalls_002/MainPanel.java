import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	// パネルサイズ
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;

	// ボールの数
	private static int NUM_BALL = 100;
	// ボールを格納する配列
	private Ball[] ball;

	// ダブルバッファリング（db）用
	private Graphics dbg;
	private Image dbImage = null;

	// アニメーション用スレッド
	private Thread thread;

	// 何ミリ秒後にアニメーションを開始するか
	private int startTime = 1000;

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);

		// ボールを格納する配列を作成
		ball = new Ball[NUM_BALL];
		for (int i = 0; i < NUM_BALL; i++) {
			ball[i] = new Ball();
		}

		// スレッドを起動
		thread = new Thread(this);
		thread.start();
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
			// ボールの位置を更新
			for (int i = 0; i < NUM_BALL; i++) {
				ball[i].update();
			}
			objectUpdate();
			objectRender();
			paintScreen();
			// 100ミリ秒だけ休止
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * オブジェクトの状態を更新
	 */
	private void objectUpdate() {
		// ボールの移動
		for (int i = 0; i < NUM_BALL; i++) {
			ball[i].update();
		}
	}

	/**
	 * バッファにレンダリング（ダブルバッファリング）
	 */
	public void objectRender() {
		// バッファイメージ
		if (dbImage == null) {
			dbImage = createImage(WIDTH, HEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				// バッファイメージの描画オブジェクト
				dbg = dbImage.getGraphics();
			}
		}

		// バッファをクリアする
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, WIDTH, HEIGHT);

		// ボールを描画
		for (int i = 0; i < NUM_BALL; i++) {
			ball[i].draw(dbg);
		}
	}

	/**
	 * バッファを画面に描画
	 */
	private void paintScreen() {
		try {
			Graphics g = getGraphics(); // グラフィックオブジェクトを取得
			if ((g != null) && (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null); // バッファイメージを画面に描画
			}
			Toolkit.getDefaultToolkit().sync();
			if (g != null) {
				g.dispose(); // グラフィックオブジェクトを破棄
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
