import java.awt.*;
import javax.swing.*;

public class DrawText extends JFrame {
	public DrawText() {
		// タイトルを設定
		setTitle("縦に残像を残しながら、端から中央にテキストがやってくる");
		// サイズ変更禁止
		setResizable(false);

		// パネルを作成してフレームに追加
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// パネルサイズに合わせてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		DrawText frame = new DrawText();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}