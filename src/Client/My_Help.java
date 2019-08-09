package Client;

import javax.swing.*;
import java.awt.*;

public class My_Help extends JFrame {
    public My_Help(){

        ImageIcon imageWindow = new ImageIcon("src/smile.png");
        this.setIconImage(imageWindow.getImage());
        setTitle("About");
        setBounds(450,200,400,120);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBackground(new Color(160,240,225));
        setResizable(false);

        JPanel aboutPanel = new JPanel();
        aboutPanel.setBackground(new Color( 160, 240, 225));
        aboutPanel.setPreferredSize(new Dimension(1,50));
        //можно указать абсолютный путь к картинку на компьютере
        //ImageIcon imageIcon = new ImageIcon("D:\\Java_Programs\\GeekBrains\\smile.png");
        //но лучше - указать путь к картинке  в проекте
        ImageIcon imageIcon = new ImageIcon("src/cat_winner.png");
        JLabel mLabel1 = new JLabel("  Программма сделана на расслабоне.", JLabel.CENTER);
        mLabel1.setIcon(imageIcon);
        mLabel1.setFont(new Font("sans-serif", Font.BOLD|Font.ITALIC, 16));
        aboutPanel.add(mLabel1);
        add(aboutPanel);
        setVisible(true);
    }
}
