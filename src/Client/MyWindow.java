package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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

    JList  list;
    JScrollPane myScroll;
    DefaultListModel model;

    String nick = "";

    ArrayList<String> msgInLog = new ArrayList<>();
    public final String FILENAME = "_chatHelloLogs.txt";
    public final int maxMsg = 100;
    File fileLogs;

    Box boxReclam;
    Box  boxAuth;
    Box boxChat;
    //Box boxChatNew;
    Box boxInput;

    private  boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if (!isAuthorized){
            boxAuth.setVisible(true);
            boxChat.setVisible(true);
            boxReclam.setVisible(false);
            myScroll.setVisible(false);
            //boxChatNew.setVisible(false);
           // list.setVisible(false);
            boxInput.setVisible(false);

        }else {
            boxAuth.setVisible(false);
            boxChat.setVisible(false);
            boxReclam.setVisible(true);
            //boxChatNew.setVisible(true);
            //list.setVisible(true);
            myScroll.setVisible(true);
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
                        //************* читаем из файла историю на maxMsg=100 сообщений
                        getHistory();
                        //*************************************************************

                        ///блок обработки сообщений
                        while (true){
                            String str = in.readUTF();
                            System.out.println("Получена строка: " + str);
                            //если строка не начинается с /
                            if (!str.startsWith("/")){
                                msgInLog.add(str); //пишем поступившую строку пока в ArrayList
                                String[] message = str.split(" ", 3);
                                if (message[0].equals(nick)){
                                    model.addElement("                                             " +  str);
                                }else {
                                    model.addElement("<html><font color = blue>" + str);
                                }
                            }else if (str.equals("/server Cloused")){
                                //************* записываем  в файл последние maxMsg =100 сообщений чата
                                setHistory();
                                //*********************************************************************
                                setAuthorized(false);
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

    //************* читаем из файла историю на maxMsg=100 сообщений *************
    private void getHistory(){
        fileLogs = new File(nick+ FILENAME);
        if (fileLogs.exists()){
            try( BufferedReader br = new BufferedReader(new FileReader(fileLogs))) {
                String ss;
                //пока не закончится файл добавляем строки на экран
                while ((ss = br.readLine())!=null){
                    msgInLog.add(ss); //пишем поступившую строку в ArrayList
                    String[] message = ss.split(" ", 3);
                    if (message[0].equals(nick)){
                        model.addElement("                                             " +  ss);
                    }else {
                        model.addElement("<html><font color = blue>" + ss);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //************* записываем  в файл последние maxMsg =100 сообщений чата ***********
    private void setHistory(){
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(fileLogs))){
            //берём самые свежие сообщения в количестве не более заданного maxMsg
            int count = msgInLog.size()>maxMsg ? msgInLog.size()-maxMsg :0;
            for (int i = count; i<msgInLog.size(); i++){
                bw.write(msgInLog.get(i));
                bw.write(System.getProperty("line.separator"));
                System.out.println("В файл записана строка: " + (msgInLog.get(i)));
            }
            bw.flush();
            msgInLog=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //****************  конструктор - готовим и выводим окно чата, взаимодействуем с сервером
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

       model = new DefaultListModel();
       list = new JList(model);
        list.setSelectionModel(new NoSelectionModel());
       myScroll = new JScrollPane(list);
       myScroll.setPreferredSize(new Dimension(400,250));


        //******************настройка строки и кнопки ввода****************
        boxInput = Box.createHorizontalBox();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color( 160, 240, 225));
        bottomPanel.setPreferredSize(new Dimension(1,40));
        //add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new FlowLayout());

        JTextField jtf = new JTextField();
        //jtf.setForeground(Color.BLUE);
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
        //JMenuItem mBlacklist = new JMenuItem("Blacklist");
        JMenuItem mClear = new JMenuItem("Clear");
        JMenuItem mFaq = new JMenuItem("FAQ");
        JMenuItem mAbout = new JMenuItem("About");

        setJMenuBar(menuBar);
        menuBar.add(mFile);
        menuBar.add(mEdit);
        menuBar.add(mHelp);

        mFile.add(mExit);

        //mEdit.add(mBlacklist);
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
        //boxMain.add(boxChatNew);
        //boxMain.add(list);
        boxMain.add(myScroll);
        boxMain.add(boxInput);



        add(boxMain);  //добавляем boxMain на MyWindow


        //*************  выводим окно чата на экран компа  *************************
        boxReclam.setVisible(false);  //****************
        boxAuth.setVisible(true);  //**********
        boxChat.setVisible(true);
        //list.setVisible(false);
        myScroll.setVisible(false);
        //boxChatNew.setVisible(false);
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

//        //Автопрокрутка вниз
//        myScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
//                //myScroll.setViewportView(list);
//            }
//        });


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

//        mBlacklist.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    List<String> blacklist = new ArrayList<String>() { };
//                  blacklist = Auth_DB_Service.getNicksFromBlacklist(nick);
//                    //String s = ClientHandler.getBlacklistString(blacklist);
//                    System.out.println(blacklist.size());
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        //Стираем записи чата при нажатии Меню-> Edit->Clear
        mClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jta.setText("");
                model.removeAllElements();
            }
        });
    }

    private static class NoSelectionModel extends DefaultListSelectionModel {
        @Override
        public void setAnchorSelectionIndex(final int anchorIndex) {}
        @Override
        public void setLeadAnchorNotificationEnabled(final boolean flag) {}
        @Override
        public void setLeadSelectionIndex(final int leadIndex) {}
        @Override
        public void setSelectionInterval(final int index0, final int index1) { }
    }
}
