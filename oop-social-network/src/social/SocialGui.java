package social;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SocialGui extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public JTextField id;
    public JButton login;
    public JLabel name;
    public JList<String> friends;

    private final Social social;

    public SocialGui(Social m) {
        this.social = m;

        id = new JTextField(18);
        login = new JButton("Login");
        name = new JLabel("< user name >");
        friends = new JList<>(new DefaultListModel<>());

        friends.setVisibleRowCount(8);
        friends.setPrototypeCellValue("XXXXXXXXXXXXXXX");

        setTitle("MyFacebook");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel upper = new JPanel(new FlowLayout());
        upper.add(id);
        upper.add(login);

        JPanel center = new JPanel(new BorderLayout());
        center.add(name, BorderLayout.NORTH);
        center.add(friends, BorderLayout.CENTER);

        add(upper, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        login.addActionListener(e -> performLogin());
        id.addActionListener(e -> performLogin());

        id.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        pack();
        setSize(420, 260);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performLogin() {
        String code = id.getText();

        try {
            String personInfo = social.getPerson(code);
            name.setText(personInfo);

            Collection<String> friendCodes = social.listOfFriends(code);
            friends.setListData(friendCodes.toArray(new String[0]));
        }
        catch(NoSuchCodeException e) {
            name.setText("< user name >");
            friends.setListData(new String[0]);
            showLoginErrorDialog();
        }
    }

    private void showLoginErrorDialog() {
        JOptionPane optionPane = new JOptionPane(
            "Unknown person code",
            JOptionPane.ERROR_MESSAGE,
            JOptionPane.DEFAULT_OPTION
        );

        JDialog dialog = optionPane.createDialog(this, "Login error");
        dialog.setName("loginErrorDialog");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(false);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        dialog.toFront();
        dialog.requestFocus();
    }
}