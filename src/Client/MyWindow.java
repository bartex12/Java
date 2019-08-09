package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyWindow extends JFrame  {

    private JTextArea jta;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    JTextField loginField;
    JPasswordField passField;
    JLabel anotherLabel;
    JLabel myLabel;

    String nick = "";

    Box boxReclam;
    Box  boxAuth;
    Box boxChat;
    Box boxChatNew;
    Box boxInput;

    private  boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if (!isAuthorized){
            boxAuth.setVisible(true);
            boxChat.setVisible(true);
            boxReclam.setVisible(false);
            boxChatNew.setVisible(false);
            boxInput.setVisible(false);

        }else {
            boxAuth.setVisible(false);
            boxChat.setVisible(false);
            boxReclam.setVisible(true);
            boxChatNew.setVisible(true);
            boxInput.setVisible(true);

        }
    }

    public void tryToAuth(){
        if (socket==null||socket.isClosed()){
            this.connect();
        }
    }

    //***************** метод взаимодействия с сервером  ***************
    public void connect() {
        try {
            System.out.println("********** connect() ***************");
            //подготовительные действия
            socket = new Socket(IP_ADRESS,PORT );  //создаём сокет на основе адреса и порта
            in = new DataInputStream(socket.getInputStream());  //получаем входной поток из сокета
            out = new DataOutputStream(socket.getOutputStream());  //получаем выходной поток из сокета

            //создаём новый поток
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //блок авторизации
                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/authok")){
                                String[] newNick  = str.split(" ");
                                nick = newNick[1];
                                System.out.println("MyWindow connect() authok" + " nick = " + nick);
                                setAuthorized(true);
                                break;
                            }else {
                                jta.append(str + "\n");
                            }
                        }

                        ///блок обработки сообщений
                        while (true){
                            String str = in.readUTF();
                            if (!str.startsWith("/")){
                                String[] message = str.split(" ", 2);
                                if (message[0].equals(nick)){

                                    Box boxMyMsg = Box.createHorizontalBox();
                                    myLabel = new JLabel();
                                    myLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
                                    myLabel.setText(message[1]);

                                    boxMyMsg.add(Box.createHorizontalGlue());
                                    boxMyMsg.add(myLabel);

                                    boxChatNew.add(boxMyMsg);

                                    //myLabel.setText(message[1]);
                                }else {
                                    Box boxAnotherMsg = Box.createHorizontalBox();
                                    anotherLabel = new JLabel();
                                    anotherLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
                                    anotherLabel.setText(str);

                                    boxAnotherMsg.add(anotherLabel);
                                    boxAnotherMsg.add(Box.createHorizontalGlue());

                                    boxChatNew.add(boxAnotherMsg);
                                    

                                    //anotherLabel.setText(str);
                                }
                            }
                            //jta.append(str + "\n");
                            if (str.equals("/server Cloused")){
                                setAuthorized(false);
                                //tryToAuth();
                                //System.exit(0);
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }catch (IOException e1){
            e1.printStackTrace();
        }
    }

    //****************  конструктор - готови и выводим окно чата, взаимодействуем с сервером *************
    public MyWindow() {
        //**************задаём общие настройки**************
        super("Hello");


        ImageIcon imageWindow = new ImageIcon("src/smile.png");
        //System.out.println(" Ширина иконки приложения = " + imageWindow.getIconWidth());
        this.setIconImage(imageWindow.getImage());
        //setTitle("Hello");
        setBounds(50,200,400,450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        //************ задаём настройки рекламного баннера ****************
        boxReclam = Box.createHorizontalBox();
        JPanel reclamPanel = new JPanel();
        reclamPanel.setPreferredSize(new Dimension(400,75));
        reclamPanel.setLayout(new GridLayout(1,3));
        reclamPanel.setBackground(new Color( 160, 240, 225));

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

        boxReclam.add(reclamPanel);

        //************ задаём настройки панели авторизации  ****************

        boxAuth = Box.createVerticalBox();
        boxAuth.setOpaque(true);
        boxAuth.setBackground(new Color( 160, 240, 225));
        //настраиваем гор панель логина
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Логин:");
        loginField = new JTextField(15);
        loginField.setFont(new Font("Dialog", Font.PLAIN, 20));

        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(loginField);

        //настраиваем гор панель пароля
        Box box2 = Box.createHorizontalBox();
        JLabel passLabel = new JLabel("Пароль:");
        passField = new JPasswordField(15);
        passField.setFont(new Font("Dialog", Font.PLAIN, 20));

        box2.add(passLabel);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(passField);

        //настраиваем гор панель кнопки ввода
        Box box3 = Box.createHorizontalBox();
        JButton input  = new JButton("Ввод");
        box3.add(Box.createHorizontalGlue());
        box3.add(input);


        boxAuth.setBorder(new EmptyBorder(12,12,12,12));
        boxAuth.add(box1);
        boxAuth.add(Box.createVerticalStrut(12));
        boxAuth.add(box2);
        boxAuth.add(Box.createVerticalStrut(20));
        boxAuth.add(box3);
        boxAuth.setBackground(new Color( 160, 240, 225) ); // не работает почему то


        //******************настройка окна отображения чата****************
        boxChat = Box.createHorizontalBox();
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color( 160, 240, 225));
        //add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout());
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setFont(new Font("Dialog", Font.PLAIN, 16));
        JScrollPane jsp = new JScrollPane(jta);
        centerPanel.add(jsp, BorderLayout.CENTER);

        boxChat.add(centerPanel);


        //******************настройка окна отображения чата New  ****************
        boxChatNew = Box.createVerticalBox();
        boxChatNew.setPreferredSize(new Dimension(400, 275));
        boxChatNew.setOpaque(true);
        boxChatNew.setBackground(new Color(255,255,255));

        Box boxMyMsg = Box.createHorizontalBox();
        myLabel = new JLabel();
        myLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        myLabel.setText(" ");

       boxMyMsg.add(Box.createHorizontalGlue());
       boxMyMsg.add(myLabel);

        boxChatNew.add(boxMyMsg);

        Box boxAnotherMsg = Box.createHorizontalBox();
        anotherLabel = new JLabel();
        anotherLabel.setText(" ");
        anotherLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        boxAnotherMsg.add(anotherLabel);
        boxAnotherMsg.add(Box.createHorizontalGlue());

        boxChatNew.add(boxAnotherMsg);

        //******************настройка строки и кнопки ввода****************
        boxInput = Box.createHorizontalBox();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color( 160, 240, 225));
        bottomPanel.setPreferredSize(new Dimension(1,40));
        //add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new FlowLayout());

        JTextField jtf = new JTextField();
        jtf.setForeground(Color.BLUE);
        jtf.setPreferredSize(new Dimension(280,28));
        jtf.setFont(new Font("Dialog", Font.PLAIN, 20));
        jtf.setToolTipText("Поле ввода текста");

        ImageIcon imageIconBtn = new ImageIcon("src/letters16x10.png");

        JButton jb = new JButton("Send",imageIconBtn);
        jb.setToolTipText("Отправить сообщение");

        bottomPanel.add(jtf);
        bottomPanel.add(jb);

        boxInput.add(bottomPanel);


        //****************Блок меню**********************
        JMenuBar menuBar = new JMenuBar();
        Font f = new Font("sans-serif", Font.PLAIN, 16);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        JMenu mFile = new JMenu("File");
        JMenu mEdit = new JMenu("Edit");
        JMenu mHelp = new JMenu("Help");
        JMenuItem mExit = new JMenuItem("Disconnect");
        JMenuItem mClear = new JMenuItem("Clear");
        JMenuItem mFaq = new JMenuItem("FAQ");
        JMenuItem mAbout = new JMenuItem("About");

        setJMenuBar(menuBar);
        menuBar.add(mFile);
        menuBar.add(mEdit);
        menuBar.add(mHelp);

        mFile.add(mExit);

        mEdit.add(mClear);

        mHelp.add(mFaq);
        mHelp.addSeparator();
        mHelp.add(mAbout);

        //************** добавляем созданные панели на MyWindow ***************

        loginLabel.setPreferredSize(passLabel.getPreferredSize());
        Box boxMain = Box.createVerticalBox();
        boxMain.add(boxReclam);
        boxMain.add(boxAuth);
        boxMain.add(boxChat);
        boxMain.add(boxChatNew);
        boxMain.add(boxInput);

        add(boxMain);  //добавляем boxMain на MyWindow


        //*************  выводим окно чата на экран компа  *************************
        boxReclam.setVisible(false);  //****************
        boxAuth.setVisible(true);  //**********
        boxChat.setVisible(true);
        boxChatNew.setVisible(false);
        boxInput.setVisible(false);  //**********

        setVisible(true);

        //***************** Слушатели событий *************************

        //слушаем кнопку авторизации
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryToAuth();
                try {
                    if (loginField.getText().trim().length()==0){
                        System.out.println("Введите логин");
                        jta.append("Введите логин \n");
                    }else if (passField.getText().length()==0){
                        System.out.println("Введите пароль");
                        jta.append("Введите пароль \n");
                    }else {
                        out.writeUTF("/auth "+ loginField.getText()+ " " + passField.getText());
                        loginField.setText("");
                        passField.setText("");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //слушаем кнопку Send - при нажатии отправляем текст на сервер
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //по кнопке отправляем текст, если это не пустая строка, на сервер,
                    // стираем строку и устанавливаем на неё фокус
                    if ((jtf.getText()).trim().length()>0){
                        out.writeUTF(jtf.getText());
                    }
                    jtf.setText("");
                    jtf.grabFocus();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //закрываем программу при нажатии на крестик в правом верхнем углу окна
        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Выход через закрытие окна");
                try {
                    if (socket==null||socket.isClosed()){
                        System.exit(0);
                    }else {
                        out.writeUTF("/end");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //закрываем программу при нажатии Меню-> File->Exit
        mExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Выход через меню");
                try {
                    out.writeUTF("/end");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Выводим экран FAQ при нажатии Меню-> Help->FAQ
        mFaq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new My_Faq();
            }
        });

        //Выводим экран About при нажатии Меню-> Help->About
        mAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new My_Help();
            }
        });

        //Стираем записи чата при нажатии Меню-> Edit->Clear
        mClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.setText("");
            }
        });
    }
}

