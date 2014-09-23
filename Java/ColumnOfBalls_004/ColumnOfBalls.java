import java.awt.*;
import javax.swing.*;

public class ColumnOfBalls extends JFrame {
    public ColumnOfBalls() {
        // タイトルを設定
        setTitle("ボールの色を常にランダムに変える");
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
        ColumnOfBalls frame = new ColumnOfBalls();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}