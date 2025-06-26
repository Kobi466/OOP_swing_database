package test;

import database.JDBC;
import view.LoginView;
import javax.swing.*;


public class Test {
    public static void main(String[] args) {
        System.out.println(JDBC.getConnection());
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new LoginView();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
