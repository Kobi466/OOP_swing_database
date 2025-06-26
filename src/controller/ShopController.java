package controller;

import dao.ShopDAO;
import model.SanPhamModel;
import view.ShopView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShopController implements ActionListener, MouseListener, TableModelListener {
    public SanPhamModel model;
    public ShopView view;

    public ShopController(ShopView view) {
        this.view = view;
        this.model = new SanPhamModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Thêm")||command.equals("Insert")) {
            try {
                int id = Integer.parseInt(this.view.text_id.getText().trim());
                String ten = this.view.text_ten.getText().trim();
                float gia = Float.parseFloat(this.view.text_gia.getText().trim());
                int soLuong = Integer.parseInt(this.view.text_sl.getText().trim());
                String moTa = this.view.text_mota.getText().trim();

                if (ten.isEmpty() || moTa.isEmpty()) {
                    throw new IllegalArgumentException("Tên sản phẩm và mô tả không được để trống!");
                }

                this.model = new SanPhamModel(id, ten, gia, soLuong, moTa);
                ShopDAO.getInstance().insert(this.model);
                JOptionPane.showMessageDialog(null, "Bạn đã thêm sản phẩm thành công");
                this.view.loadDataArrayList();
                this.view.lamTrong();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID, giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else if (command.equals("Xóa")||command.equals("Delete")) {
            String input = JOptionPane.showInputDialog(this.view, "Nhập ID sản phẩm:");
            model.setId(Integer.parseInt(input));
            model.setTenSanPham(this.model.getTenSanPham() + "");
            model.setGiaBan(Float.parseFloat(this.model.getGiaBan() + ""));
            model.setSoLuong(Integer.parseInt(this.model.getSoLuong() + ""));
            model.setMoTa(this.model.getMoTa() + "");

            if (input != null) { // Nếu không bấm Cancel
                try {
                    int id = Integer.parseInt(input);
                    int kq = ShopDAO.getInstance().deletebyID(this.model);
                    if (kq != 0) {
                        JOptionPane.showMessageDialog(this.view, "Xóa thành công ID: " + id);
                        // Load lại bảng dữ liệu sau khi xóa
                        this.view.loadDataArrayList();
                    } else {
                        JOptionPane.showMessageDialog(this.view, "ID không tồn tại: " + id, "Lỗi", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.view, "ID phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else if (command.equals("Tìm")||command.equals("Find")) {
            String input = JOptionPane.showInputDialog(this.view, "Nhập ID sản phẩm:");
            this.model.setId(Integer.parseInt(input));
            try {
                int id = Integer.parseInt(input);
                SanPhamModel sanPhamModel = ShopDAO.getInstance().selectById(this.model);
                if(input==null||input.trim().isEmpty()){
                    JOptionPane.showMessageDialog(this.view, "ID không được để trống!");
                    return;
                }
                if(sanPhamModel!=null){
                    JOptionPane.showMessageDialog(this.view,
                            "Kết quả tìm kiếm:\n"+
                                    "ID: "+sanPhamModel.getId()+""+"\n"+
                                    "Tên: "+sanPhamModel.getTenSanPham()+"" +"\n"+
                                    "Giá: "+sanPhamModel.getGiaBan()+""+"\n" +
                                    "Số lượng: "+sanPhamModel.getSoLuong()+""+"\n"+
                                    "Mô tả: "+sanPhamModel.getMoTa()+"",
                            "Kết quả tìm kiếm",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this.view, "Không tìm thấy sản phẩm nào với ID: "
                                    + id, "Kết quả tìm kiếm",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.view, "ID phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.view, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else if (command.equals("Thoát") || command.equals("Exit")) {
            System.exit(0);
        } else if (command.equals("Làm mới")||command.equals("Reset")) {
            this.view.lamTrong();
            this.view.loadDataArrayList();
        } else if (command.equals("Xóa tất cả")||command.equals("Delete All")) {
            ShopDAO.getInstance().deleteAll(this.model);
            this.view.loadDataArrayList();
        }else if(command.equals("Thống kê")){
            ShopDAO.getInstance().showchary();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow(); // Hàng bị thay đổi
        int column = e.getColumn(); // Cột bị thay đổi

        // Kiểm tra nếu có sự thay đổi ở cột không phải cột ID
        if (column!=-1&&column!=0) {
            // Lấy ID của sản phẩm từ hàng hiện tại
            int id = (int) view.tableModel.getValueAt(row, 0);

            // Lấy giá trị hiện tại của hàng từ bảng
            String tenSanPham = (String) view.tableModel.getValueAt(row, 1);
            float giaBan = Float.parseFloat(view.tableModel.getValueAt(row, 2).toString());
            int soLuong = Integer.parseInt(view.tableModel.getValueAt(row, 3).toString());
            String moTa = (String) view.tableModel.getValueAt(row, 4);

            // Tạo một đối tượng SanPhamModel với dữ liệu đầy đủ
            SanPhamModel sanPhamChinhSua = new SanPhamModel(id, tenSanPham, giaBan, soLuong, moTa);

            // Cập nhật giá trị cột thay đổi
            Object newValue = this.view.tableModel.getValueAt(row, column);
            String columnName = this.view.tableModel.getColumnName(column);

            switch (columnName) {
                case "Tên sản phẩm":
                    sanPhamChinhSua.setTenSanPham(newValue.toString());
                    break;
                case "Giá bán":
                    sanPhamChinhSua.setGiaBan(Float.parseFloat(newValue.toString()));
                    break;
                case "Số lượng":
                    sanPhamChinhSua.setSoLuong(Integer.parseInt(newValue.toString()));
                    break;
                case "Mô tả":
                    sanPhamChinhSua.setMoTa(newValue.toString());
                    break;
            }
            ShopDAO.getInstance().update(sanPhamChinhSua);
            this.view.loadDataArrayList();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        }
    }
    public void dangNhap(){

    }
}




