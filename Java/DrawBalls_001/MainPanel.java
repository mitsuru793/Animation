import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	// パネルサイズ
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;

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

		// スレッドを起動
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void drawToBuff() {
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

		// 各ボールを描画
		Ball.randomDraw(dbg);
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
			drawToBuff();
			repaint();
			// 100ミリ秒だけ休止
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
