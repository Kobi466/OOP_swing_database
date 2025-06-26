package dao;

import controller.ShopController;
import database.JDBC;
import model.SanPhamModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import view.ShopView;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ShopDAO implements DAOInterface<SanPhamModel>{
    public ShopController controller;
    public ShopView view;

    public static ShopDAO getInstance(){
        return new ShopDAO();
    }
    @Override
    public int insert(SanPhamModel sanPhamModel) {
        int kq = 0;
        try{
            Connection con = JDBC.getConnection();
            String sql = "INSERT INTO cuahang VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sanPhamModel.getId());
            ps.setString(2, sanPhamModel.getTenSanPham());
            ps.setFloat(3, sanPhamModel.getGiaBan());
            ps.setInt(4, sanPhamModel.getSoLuong());
            ps.setString(5, sanPhamModel.getMoTa());
            kq = ps.executeUpdate();
            System.out.println("Bạn đã thực thi: "+ sql);
            System.out.println("Có "+ kq+" dòng bị thay đổi!");
            JDBC.close(con);
        }catch(Exception e){
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int update(SanPhamModel sanPhamModel) {
        int result = 0;
        try {
            Connection con = JDBC.getConnection();
            String sql = "UPDATE cuahang SET tensanpham = ?, giaban = ?, soluong = ?, mota = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            // Gán giá trị vào các tham số trong câu lệnh SQL
            ps.setString(1, sanPhamModel.getTenSanPham());
            ps.setFloat(2, sanPhamModel.getGiaBan());
            ps.setInt(3, sanPhamModel.getSoLuong());
            ps.setString(4, sanPhamModel.getMoTa());
            ps.setInt(5, sanPhamModel.getId());

            result = ps.executeUpdate(); // Thực thi câu lệnh SQL

            System.out.println("Cập nhật thành công: " + result + " dòng bị thay đổi.");
            JDBC.close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @Override
    public int deletebyID(SanPhamModel sanPhamModel) {
        int kq = 0;
        try{
            Connection con = JDBC.getConnection();
            String sql = "DELETE FROM cuahang WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sanPhamModel.getId());
            System.out.println("Bạn đã thực thi: "+ sql);
            System.out.println("Có "+ kq+" dòng bị thay đổi!");
            kq = ps.executeUpdate();
            JDBC.close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int deleteAll(SanPhamModel sanPhamModel) {
        int kq = 0;
        try{
            Connection con = JDBC.getConnection();
            String sql = "DELETE FROM cuahang";
            PreparedStatement ps = con.prepareStatement(sql);
            kq = ps.executeUpdate();
            JDBC.close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public ArrayList<SanPhamModel> selectAll() {
        ArrayList kq = new ArrayList();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "select * from cuahang";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                SanPhamModel sanPhamModel = new SanPhamModel();
                sanPhamModel.setId(rs.getInt("id"));
                sanPhamModel.setTenSanPham(rs.getString("tensanpham"));
                sanPhamModel.setGiaBan(rs.getFloat("giaban"));
                sanPhamModel.setSoLuong(rs.getInt("soluong"));
                sanPhamModel.setMoTa(rs.getString("mota"));
                kq.add(sanPhamModel);
            }
            JDBC.close(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public SanPhamModel selectById(SanPhamModel sanPhamModel) {
        SanPhamModel kq = null;
        try{
            Connection con = JDBC.getConnection();
            String sql = "select * from cuahang where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sanPhamModel.getId());
            ResultSet rs = ps.executeQuery();
            System.out.println("Ban da tim kiem 1 san pham");
            while(rs.next()){
                kq = new SanPhamModel();
                kq.setId(rs.getInt("id"));
                kq.setTenSanPham(rs.getString("tensanpham"));
                kq.setGiaBan(rs.getFloat("giaban"));
                kq.setSoLuong(rs.getInt("soluong"));
                kq.setMoTa(rs.getString("mota"));
            }
            JDBC.close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public ArrayList<SanPhamModel> selectByCondition(String condition) {
        return null;
    }

    public void showchary(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try  {
            String query = "SELECT tensanpham, giaban FROM cuahang";
            Connection con = JDBC.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tenSanPham = rs.getString("tensanpham");
                double giaBan = rs.getDouble("giaban");
                dataset.addValue(giaBan, "Giá tiền sản phẩm", tenSanPham);
            }
            JDBC.close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Giá tiền theo sản phẩm",
                "Tên sản phẩm",
                "Giá tiền (VND)",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame frame = new JFrame("Biểu đồ giá tiền");
        frame.add(chartPanel);
        frame.setSize(800 , 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

