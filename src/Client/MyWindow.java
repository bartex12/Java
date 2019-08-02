package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
    public MyWindow() {
        super("Hello");
        ImageIcon imageWindow = new ImageIcon("src/smile.png");
        System.out.println(" Ширина иконки приложения = " + imageWindow.getIconWidth());
        this.setIconImage(imageWindow.getImage());
        //setTitle("Hello");
        setBounds(50,200,400,450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        //****************************

        JPanel reclamPanel = new JPanel();
        reclamPanel.setPreferredSize(new Dimension(1,75));
        add(reclamPanel, BorderLayout.NORTH);
        reclamPanel.setLayout(new GridLayout(1,3));
        reclamPanel.setBackground(new Color( 160, 240, 225));
        reclamPanel.setToolTipText("Рекламный баннер");
        //добавляем не просто Jlabel, а JPanel, для возможного расширения
        JPanel[] jp = new  JPanel[3] ;
        ImageIcon imageIcon = new ImageIcon("src/kollaider100x67_2.jpg");
        ImageIcon imageIconRec = new ImageIcon("src/panda100x67.jpg");
        ImageIcon imageIconRecTel = new ImageIcon("src/rekTel.png");

        JLabel jlab1 = new JLabel(imageIcon);
        JLabel jlab2 = new JLabel(imageIconRec);
        JLabel jlab3 = new JLabel(imageIconRecTel);
        jp[0] = new JPanel();
        jp[0].setBackground(new Color( 160, 240, 225) );
        jp[0].add(jlab1);
        reclamPanel.add(jp[0]);

        jp[1] = new JPanel();
        jp[1].setBackground(new Color( 160, 240, 225) );
        jp[1].add(jlab2);
        reclamPanel.add(jp[1]);

        jp[2] = new JPanel();
        jp[2].setBackground(new Color( 160, 240, 225) );
        jp[2].add(jlab3);
        reclamPanel.add(jp[2]);

        //**********************************

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.gray);
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        jta.setFont(new Font("Dialog", Font.PLAIN, 20));
        JScrollPane jsp = new JScrollPane(jta);
        centerPanel.add(jsp, BorderLayout.CENTER);

        //*************************************

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color( 160, 240, 225));
        bottomPanel.setPreferredSize(new Dimension(1,40));
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new FlowLayout());
        JTextField jtf = new JTextField();
        jtf.setForeground(Color.BLUE);
        jtf.setPreferredSize(new Dimension(280,28));
        jtf.setFont(new Font("Dialog", Font.PLAIN, 20));
        jtf.setToolTipText("Поле ввода текста");
        ImageIcon imageIconBtn = new ImageIcon("src/letters16x10.png");
        //System.out.println(imageIconBtn);
        JButton jb = new JButton("Send",imageIconBtn);
        jb.setToolTipText("Отправить сообщение");
        bottomPanel.add(jtf);
        bottomPanel.add(jb);

        //**************************************

        JMenuBar menuBar = new JMenuBar();
        Font f = new Font("sans-serif", Font.PLAIN, 16);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        JMenu mFile = new JMenu("File");
        JMenu mEdit = new JMenu("Edit");
        JMenu mHelp = new JMenu("Help");
        //JMenuItem mNew = new JMenuItem("New");
        JMenuItem mExit = new JMenuItem("Exit");
       // JMenuItem mCopy = new JMenuItem("Copy");
       // JMenuItem mSave = new JMenuItem("Save");
        JMenuItem mClear = new JMenuItem("Clear");
        JMenuItem mFaq = new JMenuItem("FAQ");
        JMenuItem mAbout = new JMenuItem("About");

        setJMenuBar(menuBar);
        menuBar.add(mFile);
        menuBar.add(mEdit);
        menuBar.add(mHelp);

        //mFile.add(mNew);
       // mFile.addSeparator();
        mFile.add(mExit);

        //mEdit.add(mCopy);
        //mEdit.addSeparator();
        //mEdit.add(mSave);
        //mEdit.addSeparator();
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

        mClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.setText("");
            }
        });

        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.append(jtf.getText() + "\n");
                jtf.setText("");
                jtf.grabFocus();
            }
        });
    }

    
}

