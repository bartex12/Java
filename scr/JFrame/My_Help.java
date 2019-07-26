package JFrame;

import javax.swing.*;

public class My_Help extends JFrame {
    public My_Help(){
        setTitle("About");
        setBounds(450,200,400,120);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        //можно указать абсолютный путь к картинку на компьютере
        //ImageIcon imageIcon = new ImageIcon("D:\\Java_Programs\\GeekBrains\\smile.png");
        //но лучше - указать путь к картинке  в проекте
        ImageIcon imageIcon = new ImageIcon("scr/smile.png");
        //System.out.println(imageIcon.getIconWidth());
        JLabel mLabel1 = new JLabel("  Программма сделана на расслабоне.", JLabel.CENTER);
        mLabel1.setIcon(imageIcon);
        add(mLabel1);
        setVisible(true);
    }
}
