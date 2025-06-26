package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    public JPasswordField matKhau;
    public LoginView(){
        this.init();
        this.setVisible(true);
    }
    public void init(){
        this.setTitle("Nhập mật khẩu để dăng nhập");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        JLabel mk = new JLabel("24ite060"+"");

        JLabel matKhau_label = new JLabel("Nhập mật khẩu: ");
        matKhau_label.setBounds(10, 15, 100,17);
        this.add(matKhau_label);

        matKhau = new JPasswordField();
        matKhau.setBounds(120, 12, 135,30);
        this.add(matKhau);


        JButton hienThiPass = new JButton("👁");
        hienThiPass.setBounds(255, 11, 30, 30);
        hienThiPass.addActionListener(new ActionListener() {
            boolean[] hienMatKhau = new boolean[]{false};
            @Override
            public void actionPerformed(ActionEvent e) {
                if(this.hienMatKhau[0]){
                    matKhau.setEchoChar('•');// Đặt ký tự che mật khẩu
                    this.hienMatKhau[0] = false;
                } else {
                    matKhau.setEchoChar((char) 0); // Hiển thị mật khẩu
                    this.hienMatKhau[0] = true;
                }
            }
        });
        this.add(hienThiPass);


        JButton loginButton = new JButton("OK");
        loginButton.setBounds(120, 50, 70, 30);
        this.add(loginButton);
        loginButton.addActionListener(e -> {
            String nhapPass = new String(matKhau.getPassword());
            if(nhapPass.equals(mk.getText())){
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new ShopView().setVisible(true);
                    SwingUtilities.getWindowAncestor(loginButton).dispose();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Mật khẩu không đúng!");
            }
        });

        JButton quenMK = new JButton("Quen mat khau");
        quenMK.setBounds(200, 50, 70, 30);
        this.add(quenMK);
        quenMK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane pane = new JOptionPane();
                String input1 = JOptionPane.showInputDialog("Nhap mat khau moi");
                mk.setText(input1);
            }
        });
    }
}
