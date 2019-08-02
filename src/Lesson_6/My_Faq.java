package JFrame;

import javax.swing.*;
import java.awt.*;

public class My_Faq extends JFrame{
    public My_Faq(){
        ImageIcon imageWindow = new ImageIcon("scr/smile.png");
        this.setIconImage(imageWindow.getImage());
        setTitle("FAQ");
        setBounds(450,200,400,450);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setResizable(false);

        JTextArea jta_faq = new JTextArea();
        jta_faq.setBackground(new Color( 230,250,245));
        jta_faq.setFont(new Font("sans-serif", Font.PLAIN, 16));
        jta_faq.setText("\n   1 Что стоит между окном и дверью? \n   (Буква “и”) \n\n" +
                "   2 Чем оканчиваются день и ночь? \n   (Мягким знаком)\n\n" +
                "   3 Что такое кульбит?\n   (кулёк с битами)\n\n" +
                "   4 Какой компьютерный термин английского\n   происхождения при дословном переводе \n" +
                "   означает междумордие»? \n  (интерфейс)\n\n" +
                "   https://www.anekdotovmir.ru\n" +
                "   http://kat-lv.ru/?page_id=119");
        //jta_faq.setBounds();
        jta_faq.setEditable(false);
        JScrollPane jsp = new JScrollPane(jta_faq);
        add(jsp, BorderLayout.CENTER);
        setVisible(true);
    }
}
