package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrame_Demo {
    public static void main(String[] args) {
        new MyWindow();
    }

    public static class MyWindow extends JFrame {
        public MyWindow() {
            setTitle("Hello");
            setBounds(50,200,400,400);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

           JPanel upperPanel = new JPanel();
            JPanel bottomPanel = new JPanel();
            JPanel centerPanel = new JPanel();

            centerPanel.setBackground(Color.gray);
            bottomPanel.setBackground(new Color( 160, 240, 225));

            bottomPanel.setPreferredSize(new Dimension(1,40));
            upperPanel.setPreferredSize(new Dimension(1,50));

            add(bottomPanel, BorderLayout.SOUTH);
            add(centerPanel, BorderLayout.CENTER);

            add(upperPanel, BorderLayout.NORTH);
            upperPanel.setLayout(new GridLayout(2,2));
            JPanel[ ] jp = new  JPanel[ 4 ] ;
            JLabel[] jl = new JLabel[4];
            for  ( int i = 0 ; i < 4 ; i++)  {
                jp[i] = new  JPanel();
                jl[i] = new JLabel();
                jp[i].add(jl[i]);
                jp[i].setBackground(new Color( 160, 240, 225) );
                //jp[i].setBackground(new Color( 100 + i*40, 100 + i*40, 100 + i*40) );
                upperPanel.add(jp[i]);
            }
            jl[0].setText(" Здесь может быть");
            jl[1].setText("т. 8-888-888-88-88 ");
            jl[2].setText(" ваша реклама");
            jl[3].setText(" Круглосуточно  ");

            centerPanel.setLayout(new BorderLayout());
            bottomPanel.setLayout(new FlowLayout());

            //JTextArea rec = new JTextArea();
            JTextArea jta = new JTextArea();
            JScrollPane jsp = new JScrollPane(jta);
            JTextField jtf = new JTextField();
            JButton jb = new JButton("Send");

            centerPanel.add(jsp, BorderLayout.CENTER);
            bottomPanel.add(jtf);
            bottomPanel.add(jb);

            jtf.setPreferredSize(new Dimension(300,28));
            jta.setEditable(false);

            //rec.append("\tЗдесь может быть ваша реклама.\n\t Звоните 8-888-888-88-88 круглосуточно\n");

            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jta.append(jtf.getText() + "\n");
                    jtf.setText("");
                    jtf.grabFocus();
                }
            });


            setVisible(true);
        }
    }
}
