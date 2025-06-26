package view;

import controller.ShopController;
import dao.ShopDAO;
import model.SanPhamModel;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;

public class ShopView extends JFrame {
    public SanPhamModel model;
    public JTable table;
    ArrayList<SanPhamModel> list;
    public JTextField text_id;
    public JTextField text_ten;
    public JTextField text_gia;
    public JTextField text_sl;
    public JTextArea text_mota;
    public JButton bt_them;
    public JButton bt_xoa;
    public JButton bt_tim;
    public JButton bt_thoat;
    public JButton bt_lammoi;
    public JButton bt_xoaall;
    public JButton bt_thongke;
    public DefaultTableModel tableModel;

    public ShopView() {
        this.model = new SanPhamModel();
        this.list = new ArrayList<>();
        this.init();
//        this.loadDataArrayList();
        this.setVisible(true);
    }

    public void init() {
        this.setTitle("Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        URL url_icon = ShopView.class.getResource("/image/shopping-icon.png");
        Image img = Toolkit.getDefaultToolkit().createImage(url_icon);
        this.setIconImage(img);


        ImageIcon originalImage = new ImageIcon(getClass().getResource("/image/cach-ban-hang-tren-shopee.png"));
        Image scaledImage = originalImage.getImage().getScaledInstance(1110, 900, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledImage);

        // Tạo JLabel cho hình ảnh nền
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(-30, -30, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

//        this.getContentPane().setBackground(Color.white);

        JMenuBar menuBar = new JMenuBar();
        JMenu setting = new JMenu("Setting");
        setting.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(KeyEvent.VK_S));
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        exit.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ShopView.class.getResource("/image/close.png"))));
        JMenuItem insert = new JMenuItem("Insert", KeyEvent.VK_I);
        insert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        insert.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ShopView.class.getResource("/image/add.png"))));
        JMenu subMenu = new JMenu("Delete(1 or all)");
        JMenuItem delete = new JMenuItem("Delete", KeyEvent.VK_D);
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        delete.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ShopView.class.getResource("/image/delete.png"))));
        JMenuItem deleteAll = new JMenuItem("Delete All", KeyEvent.VK_D);
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_DOWN_MASK));
        deleteAll.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ShopView.class.getResource("/image/deleteall.png"))));
        JMenuItem findID = new JMenuItem("Find", KeyEvent.VK_F);
        findID.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        findID.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ShopView.class.getResource("/image/find.png"))));
        JMenuItem Reset = new JMenuItem("Reset", KeyEvent.VK_R);
        Reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_DOWN_MASK));
        Reset.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(ShopView.class.getResource("/image/reload.png"))));
        exit.addActionListener(new ShopController(this));
        insert.addActionListener(new ShopController(this));
        delete.addActionListener(new ShopController(this));
        deleteAll.addActionListener(new ShopController(this));
        findID.addActionListener(new ShopController(this));
        Reset.addActionListener(new ShopController(this));

        setting.addSeparator();
        setting.add(insert);
        subMenu.add(delete);
        setting.add(findID);
        subMenu.add(deleteAll);
        setting.add(Reset);
        setting.add(exit);
        setting.add(subMenu);
        menuBar.add(setting);
        this.setJMenuBar(menuBar);

        String[] tenCot = {"ID", "Tên sản phẩm", "Giá bán", "Số lượng", "Mô tả"};
        Object[][] duLieu = {};
        tableModel = new DefaultTableModel(duLieu, tenCot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép chỉnh sửa các cột trừ cột ID (cột đầu tiên)
                return column != 0;
            }
        };

        table = new JTable(tableModel);
        table.setBackground(Color.LIGHT_GRAY);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMinWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setMinWidth(200);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(75);
        table.getColumnModel().getColumn(3).setMaxWidth(75);

        // Thêm dòng này vào phần khởi tạo JTable
        table.setCellSelectionEnabled(true); // Cho phép chọn ô
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        // Tắt chỉnh sửa cho cột ID
        table.getColumnModel().getColumn(0).setCellEditor(null);  // Không cho phép chỉnh sửa cột ID
        // Cho phép chỉnh sửa các cột khác ngoài ID
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
        }

        TableModelListener tableModelListener = new ShopController(this);
        tableModel.addTableModelListener(tableModelListener);
        table.setCellEditor(new DefaultCellEditor(new JTextField()));// Cho phép chỉnh sửa trực tiếp các ô trong bảng

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(30, 30, 1030, 600);
        this.add(scrollPane);

        JLabel label_id = new JLabel("ID:");
        label_id.setFont(new Font("Arial", Font.BOLD, 12));
        text_id = new JTextField(10);
        text_id.setToolTipText("Nhập ID sản phẩm");
        JLabel label_ten = new JLabel("Tên sản phẩm:");
        label_ten.setFont(new Font("Arial", Font.BOLD, 12));
        text_ten = new JTextField(10);
        text_ten.setToolTipText("Nhập tên sản phẩm");
        JLabel label_gia = new JLabel("Giá:");
        label_gia.setFont(new Font("Arial", Font.BOLD, 12));
        text_gia = new JTextField(10);
        text_gia.setToolTipText("Nhập giá tiền sản phẩm");
        JLabel label_sl = new JLabel("Số lượng:");
        label_sl.setFont(new Font("Arial", Font.BOLD, 12));
        text_sl = new JTextField(10);
        text_sl.setToolTipText("Nhập số lượng sản phẩm");
        JLabel label_mota = new JLabel("Mô tả:");
        label_mota.setFont(new Font("Arial", Font.BOLD, 12));
        text_mota = new JTextArea();
        text_mota.setToolTipText("Mô tả về sản phẩm");
        text_mota.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane thanhcuonmota = new JScrollPane(text_mota, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        label_id.setBounds(15, 650, 30, 30);
        label_ten.setBounds(15, 700, 100, 30);
        label_sl.setBounds(15, 750, 100, 30);
        text_id.setBounds(110, 652, 250, 30);
        text_ten.setBounds(110, 702, 250, 30);
        text_sl.setBounds(110, 752, 250, 30);
        label_mota.setBounds(420, 650, 60, 30);
        label_gia.setBounds(15, 800, 100, 30);
        thanhcuonmota.setBounds(420, 675, 300, 155);
        text_gia.setBounds(110, 802, 250, 30);

        this.add(label_id);
        this.add(text_id);
        this.add(label_ten);
        this.add(text_ten);
        this.add(label_sl);
        this.add(text_sl);
        this.add(label_mota);
        this.add(thanhcuonmota);
        this.add(label_gia);
        this.add(text_gia);


        ActionListener ac = new ShopController(this);
        bt_them = new JButton("Thêm");
        bt_them.setToolTipText("Bạn sẽ thêm sản phẩm vào cửa hàng");
        bt_them.setIcon(new ImageIcon(ShopView.class.getResource("/image/add-product.png")));
        bt_them.setFont(new Font("Arial", Font.BOLD, 10));
        bt_them.setBackground(Color.RED);
        bt_them.setOpaque(true);
        bt_them.addActionListener(ac);
        bt_them.setBounds(755, 675, 150, 45);
        this.add(bt_them);
        bt_xoa = new JButton("Xóa");
        bt_xoa.setToolTipText("Bạn sẽ xóa sản phẩm qua ID bạn nhập");
        bt_xoa.setIcon(new ImageIcon(ShopView.class.getResource("/image/delete-product.png")));
        bt_xoa.setFont(new Font("Arial", Font.BOLD, 10));
        bt_xoa.setBackground(Color.BLUE);
        bt_xoa.setOpaque(true);
        bt_xoa.addActionListener(ac);
        bt_xoa.setBounds(755, 785, 150, 45);
        this.add(bt_xoa);
        bt_tim = new JButton("Tìm");
        bt_tim.setToolTipText("Bạn sẽ tìm kiếm thông tin sản phẩm qua ID bạn nhập");
        bt_tim.setIcon(new ImageIcon(ShopView.class.getResource("/image/find product.png")));
        bt_tim.setFont(new Font("Arial", Font.BOLD, 10));
        bt_tim.setBackground(Color.YELLOW);
        bt_tim.setOpaque(true);
        bt_tim.setBounds(755, 731, 150, 45);
        bt_tim.addActionListener(ac);
        this.add(bt_tim);
        bt_thoat = new JButton("Thoát");
        bt_thoat.setToolTipText("Bạn sẽ thoát cửa hàng");
        bt_thoat.setIcon(new ImageIcon(ShopView.class.getResource("/image/close.png")));
        bt_thoat.addActionListener(ac);
        bt_thoat.setBounds(920, 675, 150, 155);
        this.add(bt_thoat);
        bt_lammoi= new JButton("Làm mới");
        bt_lammoi.setToolTipText("Bạn sẽ làm mới cửa hàng");
        bt_lammoi.setIcon(new ImageIcon(ShopView.class.getResource("/image/reload.png")));
        bt_lammoi.setBounds(960, 5, 100, 20);
        bt_lammoi.addActionListener(ac);
        this.add(bt_lammoi);
        bt_xoaall = new JButton("Xóa tất cả");
        bt_xoaall.setToolTipText("Bạn sẽ xóa tất cả sản phẩm");
        bt_xoaall.setBounds(850, 5, 100, 20);
        bt_xoaall.addActionListener(ac);
        this.add(bt_xoaall);
        bt_thongke = new JButton("Thống kê");
        bt_thongke.setToolTipText("Thống kê sản phẩm theo giá tiền");
        bt_thongke.setBounds(740, 5, 100, 20);
        bt_thongke.addActionListener(ac);
        this.add(bt_thongke);
        this.add(backgroundLabel);
    }

    public void loadDataArrayList() {
        ArrayList<SanPhamModel> list = ShopDAO.getInstance().selectAll();
        tableModel.setRowCount(0);
        for (SanPhamModel sanPham : list) {
            tableModel.addRow(new Object[] {
                    sanPham.getId(),
                    sanPham.getTenSanPham(),
                    sanPham.getGiaBan(),
                    sanPham.getSoLuong(),
                    sanPham.getMoTa()
            });
        }
    }

    public void lamTrong(){
        this.text_id.setText("");
        this.text_ten.setText("");
        this.text_gia.setText("");
        this.text_sl.setText("");
        this.text_mota.setText("");
    }
}