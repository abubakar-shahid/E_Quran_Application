import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Listen extends JFrame implements ActionListener {
    private Connection connection;
    private Mp3Player mp3Player;
    private JButton b1, b2, b3;
    private JScrollPane scrollPane;
    private JPanel body;
    private List<JButton> surahs;
    private HashMap<String, Integer> map = new HashMap<>();
    private String selection, reciter;
    String[] data = {
            "الفاتحة - al-Fātihah [THE OPENING]",
            "البقرة - al-Baqarah [THE COW]",
            "آل عمران - Āl-i-‘Imrān [THE FAMILY OF ‘IMRĀN]",
            "النساء - an-Nisā’ [WOMEN]",
            "المائدة - al-Mā’idah [THE TABLE SPREAD]",
            "الأنعام - al-An‘ām [THE CATTLE]",
            "الأعراف - al-A‘rāf [THE HEIGHTS]",
            "الأنفال - al-Anfāl [SPOILS OF WAR]",
            "التوبة - at-Tawbah [REPENTANCE]",
            "يونس - Yūnus [JONAH]",
            "هود - Hūd [HUD]",
            "يوسف - Yūsuf [JOSEPH]",
            "الرعد - ar-Ra‘d [THUNDER]",
            "إبراهيم - Ibrāhīm [ABRAHAM]",
            "الحجر - al-Hijr [THE ROCKY TRACT]",
            "النحل - an-Nahl [THE BEE]",
            "الإسراء - al-Isrā’ [THE NIGHT JOURNEY]",
            "الكهف - al-Kahf [THE CAVE]",
            "مريم - Maryam [MARY]",
            "طه - Tāhā [TA-HA]",
            "الأنبياء - al-Ambiyā’ [THE PROPHETS]",
            "الحج - al-Hajj [THE PILGRIMAGE]",
            "المؤمنون - al-Mu’minūn [THE BELIEVERS]",
            "النور - an-Nūr [THE LIGHT]",
            "الفرقان - al-Furqān [THE CRITERION]",
            "الشعراء - ash-Shu‘arā’ [THE POETS]",
            "النمل - an-Naml [THE ANTS]",
            "القصص - al-Qasas [THE NARRATIVES]",
            "العنكبوت - al-‘Ankabūt [THE SPIDER]",
            "الروم - ar-Rūm [THE ROMAN EMPIRE]",
            "لقمان - Luqmān [LUQMAN]",
            "السجدة - as-Sajdah [PROSTRATION]",
            "الأحزاب - al-Ahzāb [THE CONFEDERATES]",
            "سبأ - Saba’ [SHEBA]",
            "فاطر - Fātir [THE ORIGINATOR]",
            "يس - Yāsīn [YA-SIN]",
            "الصافات - as-Sāffāt [THOSE RANGED IN RANKS]",
            "ص - Sād [SAD]",
            "الزمر - az-Zumar [THE CROWDS]",
            "غافر - Ghāfir [THE FORGIVING]",
            "فصلت - Fussilat [CLEARLY SPELLED OUT]",
            "الشورى - ash-Shūrā [THE CONSULTATION]",
            "الزخرف - az-Zukhruf [ORNAMENTS OF GOLD]",
            "الدخان - ad-Dukhān [SMOKE]",
            "الجاثية - al-Jāthiyah [THE KNEELING]",
            "الأحقاف - al-Ahqāf [THE SAND-DUNES]",
            "محمد - Muhammad [MUHAMMAD]",
            "الفتح - al-Fath [VICTORY]",
            "الحجرات - al-Hujurāt [THE APARTMENTS]",
            "ق - Qāf [QAF]",
            "الذاريات - adh-Dhāriyāt [THE SCATTERING WINDS]",
            "الطور - at-Tūr [MOUNT SINAI]",
            "النجم - an-Najm [THE STAR]",
            "القمر - al-Qamar [THE MOON]",
            "الرحمن - ar-Rahmān [THE MOST MERCIFUL]",
            "الواقعة - al-Wāqi‘ah [THE INEVITABLE EVENT]",
            "الحديد - al-Hadīd [IRON]",
            "المجادلة - al-Mujādalah [THE QUARREL]",
            "الحشر - al-Hashr [THE GATHERING]",
            "الممتحنة - al-Mumtahinah [THE WOMAN TO BE EXAMINED]",
            "الصف - as-Saff [BATTLE ARRAY]",
            "الجمعة - al-Jumu‘ah [FRIDAY, THE CONGREGATION]",
            "المنافقون - al-Munāfiqūn [THE HYPOCRITES]",
            "التغابن - at-Taghābun [MUTUAL LOSS AND GAIN]",
            "الطلاق - at-Talāq [DIVORCE]",
            "التحريم - at-Tahrīm [PROHIBITION]",
            "الملك - al-Mulk [DOMINION]",
            "القلم - al-Qalam [THE PEN]",
            "الحاقة - al-Hāqqah [THE CONCRETE REALITY]",
            "المعارج - al-Ma‘ārij [THE WAYS OF ASCENT]",
            "نوح - Nūh [NOAH]",
            "الجن - al-Jinn [THE JINN]",
            "المزمل - al-Muzzammil [FOLDED IN GARMENTS]",
            "المدثر - al-Muddaththir [THE ENWRAPPED]",
            "القيامة - al-Qiyāmah [THE RESURRECTION]",
            "الإنسان - al-Insān [MAN]",
            "المرسلات - al-Mursalāt [THE EMISSARIES]",
            "النبأ - an-Naba’ [THE NEWS]",
            "النازعات - an-Nāzi‘āt [THOSE WHO PULL]",
            "عبس - ‘Abasa [HE FROWNED]",
            "التكوير - at-Takwīr [SHROUDING IN DARKNESS]",
            "الإنفطار - al-Infitār [THE CLEAVING ASUNDER]",
            "المطففين - al-Mutaffifīn [DEALING IN FRAUD]",
            "الإنشقاق - al-Inshiqāq [THE SPLITTING ASUNDER]",
            "البروج - al-Burūj [THE ZODIAC]",
            "الطارق - at-Tāriq [THAT WHICH SEEMS AT NIGHT]",
            "الأعلى - al-A‘lā [THE MOST HIGH]",
            "الغاشية - al-Ghāshiyah [THE OVERWHELMING EVENT]",
            "الفجر - al-Fajr [THE DAWN]",
            "البلد - al-Balad [THE CITY]",
            "الشمس - ash-Shams [THE SUN]",
            "الليل - al-Layl [THE NIGHT]",
            "الضحى - ad-Duhā [THE FORENOON]",
            "الشرح - ash-Sharh [THE OPENING UP]",
            "التين - at-Tīn [THE FIG]",
            "العلق - al-‘Alaq [THE HANGING MASS]",
            "القدر - al-Qadr [DETERMINATION]",
            "البينة - al-Bayyinah [THE CLEAR EVIDENCE]",
            "الزلزلة - az-Zalzalah [THE EARTHQUAKE]",
            "العاديات - al-‘Ādiyāt [THOSE THAT RUN]",
            "القارعة - al-Qāri‘ah [RATTLING VIOLENT JERK AND THUNDER]",
            "التكاثر - at-Takāthur [PILING UP]",
            "العصر - al-‘Asr [THE TIME]",
            "الهمزة - al-Humazah [THE SLANDERER]",
            "الفيل - al-Fīl [THE ELEPHANT]",
            "قريش - al-Quraysh [QURAISH]",
            "الماعون - al-Mā‘ūn [THINGS OF COMMON USE]",
            "الكوثر - al-Kawthar [THE ABUNDANCE]",
            "الكافرون - al-Kāfirūn [THE UNBELIEVERS]",
            "النصر - an-Nasr [HELP]",
            "المسد - al-masad [THE TWISTED STRANDS]",
            "الإخلاص - al-Ikhlās [PURITY]",
            "الفلق - al-Falaq [THE FISSION]",
            "الناس - al-Nās [MEN]"
    };

    //---------------------------------------------------------------------------------------------------------
    public Listen(Connection conn){
        connection = conn;
    }

    public void startListening() {
        //Frame
        setLayout(new BorderLayout());
        setTitle("E-Qura'an Application");
        setBounds(400, 10, 600, 700);
        addWindowListener(new MyWindowListener());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);

        b1 = new JButton("Qari Abdul Basit");
        b1.addActionListener(this);
        b2 = new JButton("Qari Abdul Rahman Sudais");
        b2.addActionListener(this);
        b3 = new JButton("Qari Mishary bin Rashid");
        b3.addActionListener(this);

        JPanel header = new JPanel(new GridLayout(1, 3));
        header.add(b1);
        header.add(b2);
        header.add(b3);
        add(header, BorderLayout.NORTH);

        //Body
        surahs = new ArrayList<>();
        for (int i = 0; i < 114; i++) {
            String st = (i + 1) + ".  " + data[i];
            JButton temp = new JButton(st);
            temp.addActionListener(this);
            surahs.add(temp);
            map.put(st, i + 1);
        }

        body = new JPanel(new GridLayout(114, 1));
        for (int i = 0; i < 114; i++) {
            body.add(surahs.get(i));
        }
        scrollPane = new JScrollPane(body);
        add(scrollPane, BorderLayout.CENTER);

        sortByQ1();
    }

    public void continueListening() throws SQLException {
        String path = " ", title = " ";
        String query = "select * from savedData;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            path = resultSet.getString(2);
            title = resultSet.getString(3);
        }
        if(path.equals("0") || title.equals("0")){
            JOptionPane.showMessageDialog(this, "No Data Found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        mp3Player = new Mp3Player(connection, title);
        mp3Player.playAudio(path);
    }

    //---------------------------------------------------------------------------------------------------------
    private void sortByQ1() {
        b1.setBackground(Color.GREEN);
        b2.setBackground(Color.ORANGE);
        b3.setBackground(Color.ORANGE);
        selection = "AB";
        reciter = "Qari Abdul Basit";
        revalidate();
        repaint();
    }

    private void sortByQ2() {
        b2.setBackground(Color.GREEN);
        b1.setBackground(Color.ORANGE);
        b3.setBackground(Color.ORANGE);
        selection = "AR";
        reciter = "Qari Abdul Rahman Sudais";
        revalidate();
        repaint();
    }

    private void sortByQ3() {
        b3.setBackground(Color.GREEN);
        b1.setBackground(Color.ORANGE);
        b2.setBackground(Color.ORANGE);
        selection = "MR";
        reciter = "Qari Mishary bin Rashid";
        revalidate();
        repaint();
    }

    //---------------------------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Qari Abdul Basit") {
            sortByQ1();
        } else if (e.getActionCommand() == "Qari Abdul Rahman Sudais") {
            sortByQ2();
        } else if (e.getActionCommand() == "Qari Mishary bin Rashid") {
            sortByQ3();
        } else {
            String path = "D:\\FAST-NUCES l215845\\5th Semester\\Software Construction & Development\\Project\\Extras\\Audios\\" + selection + "\\" + map.get(e.getActionCommand()) + ".mp3";
            String title = e.getActionCommand() + " by " + reciter;
            mp3Player = new Mp3Player(connection, title);
            mp3Player.playAudio(path);
        }
    }

    //---------------------------------------------------------------------------------------------------------
    class MyWindowListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            dispose();
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
    //---------------------------------------------------------------------------------------------------------
}
