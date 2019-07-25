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

            //****************************

           JPanel upperPanel = new JPanel();
            upperPanel.setPreferredSize(new Dimension(1,50));
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

           //**********************************

            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(new Color( 160, 240, 225));
            bottomPanel.setPreferredSize(new Dimension(1,40));
            add(bottomPanel, BorderLayout.SOUTH);
            bottomPanel.setLayout(new FlowLayout());
            JTextField jtf = new JTextField();
            jtf.setPreferredSize(new Dimension(300,28));
            JButton jb = new JButton("Send");
            bottomPanel.add(jtf);
            bottomPanel.add(jb);

            //*************************************

            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(Color.gray);
            add(centerPanel, BorderLayout.CENTER);
            centerPanel.setLayout(new BorderLayout());
            JTextArea jta = new JTextArea();
            jta.setEditable(false);
            JScrollPane jsp = new JScrollPane(jta);
            centerPanel.add(jsp, BorderLayout.CENTER);

            //**************************************

            JMenuBar menuBar = new JMenuBar();
            JMenu mFile = new JMenu("File");
            JMenu mEdit = new JMenu("Edit");
            JMenu mHelp = new JMenu("Help");
            JMenuItem mNew = new JMenuItem("New");
            JMenuItem mExit = new JMenuItem("Exit");
            JMenuItem mCopy = new JMenuItem("Copy");
            JMenuItem mSave = new JMenuItem("Save");
            JMenuItem mClear = new JMenuItem("Clear");
            JMenuItem mFaq = new JMenuItem("FAQ");
            JMenuItem mAbout = new JMenuItem("About");

            setJMenuBar(menuBar);
            menuBar.add(mFile);
            menuBar.add(mEdit);
            menuBar.add(mHelp);

            mFile.add(mNew);
            mFile.addSeparator();
            mFile.add(mExit);

            mEdit.add(mCopy);
            mEdit.addSeparator();
            mEdit.add(mSave);
            mEdit.addSeparator();
            mEdit.add(mClear);

            mHelp.add(mFaq);
            mHelp.addSeparator();
            mHelp.add(mAbout);

            //**************************************

            setVisible(true);

            //******************************************

            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jta.append(jtf.getText() + "\n");
                    jtf.setText("");
                    jtf.grabFocus();
                }
            });

            mExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            mFaq.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new My_Faq();
                }
            });

            mAbout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new My_Help();
                }
            });
        }
    }

    public static class My_Faq extends JFrame{
        public My_Faq(){
            setTitle("FAQ");
            setBounds(450,200,400,400);
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

            setVisible(true);
        }
    }

    public static class My_Help extends JFrame{
        public My_Help(){
            setTitle("About");
            setBounds(450,200,400,100);
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            JLabel mLabel1 = new JLabel("     Программма сделана на расслабоне.");
            setLayout(new BorderLayout());
            add(mLabel1,BorderLayout.LINE_START );
            setVisible(true);
        }
    }

}
