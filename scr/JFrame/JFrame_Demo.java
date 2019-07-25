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

            upperPanel.setBackground(Color.gray);
            centerPanel.setBackground(Color.gray);
            bottomPanel.setBackground(Color.green);

            bottomPanel.setPreferredSize(new Dimension(1,40));
            upperPanel.setPreferredSize(new Dimension(1,40));

            add(upperPanel, BorderLayout.NORTH);
            add(bottomPanel, BorderLayout.SOUTH);
            add(centerPanel, BorderLayout.CENTER);

            upperPanel.setLayout(new BorderLayout());
            centerPanel.setLayout(new BorderLayout());
            bottomPanel.setLayout(new FlowLayout());

            JTextArea rec = new JTextArea();
            JTextArea jta = new JTextArea();
            JScrollPane jsp = new JScrollPane(jta);
            JTextField jtf = new JTextField();
            JButton jb = new JButton("Send");

            upperPanel.add(rec, BorderLayout.CENTER);
            centerPanel.add(jsp, BorderLayout.CENTER);
            bottomPanel.add(jtf);
            bottomPanel.add(jb);

            jtf.setPreferredSize(new Dimension(300,28));
            jta.setEditable(false);
            rec.setEditable(false);

            rec.setBackground(Color.orange);
            rec.append("\tЗдесь может быть ваша реклама.\n\t Звоните 8-888-888-88-88 круглосуточно\n");

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
