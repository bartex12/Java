package JFrame;

import javax.swing.*;
import java.awt.*;

public class My_Faq extends JFrame{
    public My_Faq(){
        setTitle("FAQ");
        setBounds(450,200,400,400);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JTextArea jta_faq = new JTextArea();
        jta_faq.setBackground(new Color( 230,250,245));
        jta_faq.setText("\n\t1 Что стоит между окном и дверью? \n\t(Буква “и”) \n\n" +
                "\t2 Чем оканчиваются день и ночь? \n\t(Мягким знаком)\n\n" +
                "\t3 Что такое кульбит?\n\t(кулёк с битами)\n\n" +
                "\t4 Какой компьютерный термин английского\n\t происхождения при дословном переводе \n" +
                "\tозначает междумордие»? \n\t(интерфейс)\n\n" +
                "\t https://www.anekdotovmir.ru\n" +
                "\thttp://kat-lv.ru/?page_id=119");
        //jta_faq.setBounds();
        jta_faq.setEditable(false);
        JScrollPane jsp = new JScrollPane(jta_faq);
        add(jsp, BorderLayout.CENTER);
        setVisible(true);
    }
}
