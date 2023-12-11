package homework;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CipherGUI {
    private JFrame frame;//创建窗口变量
    private JButton setKeyButton;//创建设置密匙按钮变量
    private JButton setPlaintextButton;//创建设置明文按钮变量
    private JButton setCiphertextButton;//创建设置密文按钮变量
    private JButton encryptButton;//创建加密按钮变量
    private JButton decryptButton;//创建解密按钮变量
    private JButton encryptFileButton;//创建加密文件按钮变量
    private JButton decryptFileButton;//创建解密文件按钮变量
    private JButton exitButton;//创建退出程序按钮变量

    //创建接收密码、明文、密文文本框变量
    private JTextField keyField;
    private JTextField plaintextField;
    private JTextField ciphertextField;

    //创建Cipher类变量
    private Cipher cipher;

    //创建ifValidKey()方法判断密匙输入位数是否合法
    private boolean ifValidKey(String key) {
        return key.length() >= 6 && key.length() <= 12;
    }

    //构造方法,给变量赋值
    public CipherGUI() {
        frame = new JFrame("李杭基加密解密系统");//创建标题为加解密系统窗口
        frame.setSize(370, 300);//窗口初始大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击X关闭窗口
        frame.setResizable(false);//固定窗口大小
        cipher = new Cipher();

        //给各个按钮赋值
        setKeyButton = new JButton("设置密钥");
        setPlaintextButton = new JButton("设置明文");
        setCiphertextButton = new JButton("设置密文");
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        encryptFileButton = new JButton("加密文件");
        decryptFileButton = new JButton("解密文件");
        exitButton = new JButton("退出");

        //给文本框赋值
        keyField = new JTextField(cipher.getKey(), 20);
        plaintextField = new JTextField(cipher.getPlaintext(), 20);
        ciphertextField = new JTextField(cipher.getCiphertext(), 20);

        //通过匿名的方式创建监听器，使事件发生
        setKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();//获取文本框中的密钥
                if (ifValidKey(key)) {//判断密钥设置是否合法，只有合法其他按钮才可以激活，才能继续操作
                    cipher.setKey(key);
                    //启用其他按钮
                    setPlaintextButton.setEnabled(true);
                    setCiphertextButton.setEnabled(true);
                    encryptButton.setEnabled(true);
                    decryptButton.setEnabled(true);
                    encryptFileButton.setEnabled(true);
                    decryptFileButton.setEnabled(true);
                    JOptionPane.showMessageDialog(frame,"设置成功！","密码设置",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // 密钥不合法，禁用其他按钮
                    setPlaintextButton.setEnabled(false);
                    setCiphertextButton.setEnabled(false);
                    encryptButton.setEnabled(false);
                    decryptButton.setEnabled(false);
                    encryptFileButton.setEnabled(false);
                    decryptFileButton.setEnabled(false);
                    JOptionPane.showMessageDialog(frame,"密码设置不合法，请重新设置！","密码设置",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        setPlaintextButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText();//接受文本框的内容
                cipher.setPlaintext(plaintext);//设置明文
                JOptionPane.showMessageDialog(frame,"明文设置成功");//弹出提示窗口
            }
        });

        setCiphertextButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String ciphertext = ciphertextField.getText();
                cipher.setCiphertext(ciphertext);
                JOptionPane.showMessageDialog(frame,"密文设置成功");
            }
        });

        encryptButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String str = cipher.encryption(cipher.plaintext);
                ciphertextField.setText(str);//密文文本框被设置成当前加密后的密文
                cipher.setCiphertext(str);   //密文被设置为当前加密的密文
                JOptionPane.showMessageDialog(null,cipher.encryption(cipher.plaintext),"加密成功",JOptionPane.INFORMATION_MESSAGE);//弹出消息提示框，展示加密后的密文
            }
        });

        decryptButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = cipher.decryption(cipher.ciphertext);
                plaintextField.setText(str);//明文文本框被设置成当前解密后的明文
                cipher.setPlaintext(str);   //明文被设置成当前解密后的明文
                JOptionPane.showMessageDialog(null,cipher.decryption(cipher.ciphertext),"解密成功",JOptionPane.INFORMATION_MESSAGE);//弹出消息提示框，展示解密后的明文
            }
        });

        encryptFileButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(null,cipher.encryption1(),"文件加密成功",JOptionPane.INFORMATION_MESSAGE);//弹出消息提示框，展示加密后的文件内容
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        decryptFileButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(null, cipher.decryption1(), "解密文件成功", JOptionPane.INFORMATION_MESSAGE);//弹出消息提示框，展示解密后的文件内容
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        exitButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("密钥:"));
        panel.add(keyField);
        panel.add(setKeyButton);
        panel.add(new JLabel("明文:"));
        panel.add(plaintextField);
        panel.add(setPlaintextButton);
        panel.add(new JLabel("密文:"));
        panel.add(ciphertextField);
        panel.add(setCiphertextButton);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(encryptFileButton);
        panel.add(decryptFileButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new CipherGUI();
    }
}
