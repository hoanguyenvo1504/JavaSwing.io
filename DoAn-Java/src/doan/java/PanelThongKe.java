/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Hoang Nguyen
 */
public class PanelThongKe extends javax.swing.JPanel {

    /**
     * Creates new form PanelThongKe
     */
    public PanelThongKe() {
        initComponents();
        chart();
    }
    ArrayList<ThongKe> tkList = new ArrayList<>();
   

    public JFreeChart createChart(){
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ số sản phẩm các loại",
                "Tên sản phẩm", "Số lượng đã bán",
                createDateset(),PlotOrientation.VERTICAL, true, true ,true);
        return barChart;
    }
    
    private CategoryDataset createDateset(){
        this.ThongKe();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        
        for (ThongKe tk: tkList) {     
            dataset.addValue(tk.getThanhTien(), "Tên Sản Phẩm", tk.getTenSP());
        }
        return dataset;
    }
    public void chart(){
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
        
        panelMain.setVisible(true);
        panelMain.removeAll();
        panelMain.add(chartPanel);
        panelMain.updateUI();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();

        panelMain.setBackground(new java.awt.Color(204, 255, 204));
        panelMain.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelMain.setLayout(new javax.swing.BoxLayout(panelMain, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    ArrayList<ClassSanPham> spList = new ArrayList<>();
    ArrayList<ClassDonHang> dhList = new ArrayList<>();

    public void selectDataFromGioHang() {
        try {
            Connection connection = new dbconnection().connectionDB();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Message", "Error connect to database", JOptionPane.INFORMATION_MESSAGE);
            }
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM giohang WHERE trangthai='Đã thanh Toán' Order by tensanpham";

            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst() == false) {
                JOptionPane.showMessageDialog(this, "Message", "Not Data!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dhList = new ArrayList<>();
            while (rs.next()) {
                ClassDonHang data = new ClassDonHang();
                data.setStt(rs.getInt("stt"));
                data.setMadonhang(rs.getString("madonhang"));
                data.setTenkhachhang(rs.getString("tenkhachhang"));
                data.setSdtkhachhang(rs.getString("sdtkhachhang"));
                data.setLoaikhachhang(rs.getString("loaikhachhang"));
                data.setTensanpham(rs.getString("tensanpham"));
                data.setMasanpham(rs.getString("masanpham"));
                data.setDongia(rs.getInt("dongia"));
                data.setSoluong(rs.getInt("soluong"));
                data.setTrangthai(rs.getString("trangthai"));
                dhList.add(data);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ThongKe(){
        this.selectDataFromGioHang();
        
        for (int i = 0; i < dhList.size(); i++) {
            int tt = 0;
            ThongKe tk = new ThongKe();
            for (ClassDonHang dh : dhList) {
                if (dhList.get(i).getTensanpham().equals(dh.getTensanpham())) {
                    tt += dh.getSoluong();
                }
            }
            tk.setTenSP(dhList.get(i).getTensanpham());
            tk.setThanhTien(tt);
            if (!tkList.contains(tk)) {
                tkList.add(tk);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelMain;
    // End of variables declaration//GEN-END:variables
}
