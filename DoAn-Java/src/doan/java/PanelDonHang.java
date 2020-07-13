/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hoang Nguyen
 */
public class PanelDonHang extends javax.swing.JPanel {

    private DefaultListModel listModel = new DefaultListModel();
    /**
     * Creates new form PanelGioHang
     */

    private String header[] = {"Họ và Tên", "Số Điện Thoại", "Loại khách hàng"};
    private DefaultTableModel tblModel = new DefaultTableModel(header, 0);
    private String header2[] = {"STT","Mã đơn hàng", "Tên khách hàng", "Số Điện Thoại", "Loại khách hàng", "Tên sản phẩm", "Mã sản phẩm", "Đơn giá", "Số lượng","Trạng thái"};

    public PanelDonHang() {
        initComponents();
        loadDBtoLoaiCB();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    ArrayList<ClassSanPham> spList = new ArrayList<>();
    ArrayList<ClassSanPham> spList2 = new ArrayList<>();

    public void updateDataIntoDonHang(String sql) {
        try {
            Connection conn = new dbconnection().connectionDB();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Message", "Error connect to DB", JOptionPane.INFORMATION_MESSAGE);
            }

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, this.lb_madonhang.getText());
            pst.setString(2, this.lb_tenkhachhang.getText());
            pst.setString(3, this.lb_sodienthoai.getText());
            pst.setString(4, this.lb_loaikhachhang.getText());
            pst.setString(5, this.cb_panelgiohang_sanpham.getSelectedItem().toString());
            pst.setString(6, this.lb_masanpham.getText());
            pst.setString(7, this.lb_dongia.getText());
            pst.setString(8, this.txt_panelgiohang_soluong.getText());
            JOptionPane.showMessageDialog(this, "Updated success", "Message", JOptionPane.INFORMATION_MESSAGE);
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
        }
    }

    public void loadDBtoLoaiCB() {
        try {
            Connection connection = new dbconnection().connectionDB();
            String loaiCB = "SELECT * FROM sanpham";
            PreparedStatement pst = connection.prepareStatement(loaiCB);
            ResultSet rs = pst.executeQuery();
            spList2 = new ArrayList<>();
            while (rs.next()) {
                ClassSanPham sp = new ClassSanPham();
                sp.setMasanpham(rs.getNString("masanpham"));
                sp.setTensanpham(rs.getNString("tensanpham"));
                sp.setLoai(rs.getNString("loai"));
                sp.setDongia(rs.getInt("dongia"));
                if (!spList2.contains(sp)) {
                    spList2.add(sp);
                    cb_panelgiohang_loai.addItem(sp.getLoai());
                }
            }
        } catch (Exception e) {
        }
    }

    public void loadDataFromGioHang() {
        try {
            Connection connection = new dbconnection().connectionDB();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Error connect to database", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            Statement st = connection.createStatement();

            String sql = "select * from giohang WHERE madonhang=" + "'" + lb_madonhang.getText() + "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst() == false) {
                JOptionPane.showMessageDialog(this, "Not Data!", "Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            tblModel = new DefaultTableModel(header2, 0);
            Vector data = null;
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("stt"));
                data.add(rs.getString("madonhang"));
                data.add(rs.getString("tenkhachhang"));
                data.add(rs.getString("sdtkhachhang"));
                data.add(rs.getString("loaikhachhang"));
                data.add(rs.getString("tensanpham"));
                data.add(rs.getString("masanpham"));
                data.add(rs.getInt("dongia"));
                data.add(rs.getInt("soluong"));
                data.add(rs.getString("trangthai"));
                tblModel.addRow(data);
            }
            tb_giohangpanel_thongtindonhang.setModel(tblModel);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchDataFromKhachHang(String sql) {
        try {
            Connection connection = new dbconnection().connectionDB();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Error connect to database", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst() == false) {
                JOptionPane.showMessageDialog(this, "Khách hàng chưa tồn tại, vui lòng thêm mới", "Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            tblModel = new DefaultTableModel(header, 0);
            Vector data = null;
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("tenkhachhang"));
                data.add(rs.getString("sdtkhachhang"));
                data.add(rs.getString("loaikhachhang"));
                tblModel.addRow(data);
            }
            tb_giohangpanel_thongtinkhachhang.setModel(tblModel);
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Not Found!", "Message", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
    }

    public void selectDataFromSanPham() {
        try {
            Connection connection = new dbconnection().connectionDB();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Message", "Error connect to database", JOptionPane.INFORMATION_MESSAGE);
            }
            Statement st = connection.createStatement();

            String sql;
            sql = "select * from sanpham";
            System.out.println(sql);
            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst() == false) {
                JOptionPane.showMessageDialog(this, "Not Data!", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            spList = new ArrayList<>();
            while (rs.next()) {
                ClassSanPham sp = new ClassSanPham();
                sp.setMasanpham(rs.getString("masanpham"));
                sp.setTensanpham(rs.getString("tensanpham"));
                sp.setLoai(rs.getString("loai"));
                sp.setDongia(rs.getInt("dongia"));
                spList.add(sp);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchDataFromSanPham(String sql) {
        try {
            Connection connection = new dbconnection().connectionDB();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Message", "Error connect to database", JOptionPane.INFORMATION_MESSAGE);
            }
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst() == false) {
                JOptionPane.showMessageDialog(this, "Message", "Not Data!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            while (rs.next()) {
                ClassSanPham data = new ClassSanPham();
                data.setTensanpham(rs.getString("tensanpham"));
                data.setMasanpham(rs.getString("masanpham"));
                data.setLoai(rs.getString("loai"));
                data.setDongia(rs.getInt("dongia"));
                spList.add(data);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_giohangpanel_thongtinkhachhang = new javax.swing.JTable();
        txt_giohangpanel_tim = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cb_giohangpanel_timkiem = new javax.swing.JComboBox<>();
        btn_giohangpanel_tim = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_tenkhachhang = new javax.swing.JLabel();
        lb_sodienthoai = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_giohangpanel_thongtindonhang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cb_panelgiohang_loai = new javax.swing.JComboBox<>();
        cb_panelgiohang_sanpham = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txt_panelgiohang_soluong = new javax.swing.JTextField();
        btn_them = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lb_dongia = new javax.swing.JLabel();
        btn_sua = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lb_masanpham = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lb_tamtinh = new javax.swing.JLabel();
        lb_loaikhachhang = new javax.swing.JLabel();
        lb_madonhanggggg = new javax.swing.JLabel();
        lb_madonhang = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        tb_giohangpanel_thongtinkhachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Tên khách hàng", "Số điện thoại"
            }
        ));
        tb_giohangpanel_thongtinkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_giohangpanel_thongtinkhachhangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_giohangpanel_thongtinkhachhang);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 255));
        jLabel1.setText("Tìm kiếm:");

        cb_giohangpanel_timkiem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cb_giohangpanel_timkiem.setForeground(new java.awt.Color(255, 51, 51));
        cb_giohangpanel_timkiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tên", "SĐT" }));

        btn_giohangpanel_tim.setBackground(new java.awt.Color(51, 51, 51));
        btn_giohangpanel_tim.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_giohangpanel_tim.setForeground(new java.awt.Color(204, 255, 255));
        btn_giohangpanel_tim.setText("Tìm");
        btn_giohangpanel_tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_giohangpanel_timActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Tìm kiếm thông tin khách hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_giohangpanel_tim, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_giohangpanel_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_giohangpanel_tim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_giohangpanel_tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cb_giohangpanel_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_giohangpanel_tim))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("Tên khách hàng:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("Số điện thoại:");

        lb_tenkhachhang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_tenkhachhang.setForeground(new java.awt.Color(255, 0, 51));
        lb_tenkhachhang.setText("Tên");

        lb_sodienthoai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_sodienthoai.setForeground(new java.awt.Color(255, 0, 51));
        lb_sodienthoai.setText("Số điện thoại");

        tb_giohangpanel_thongtindonhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_giohangpanel_thongtindonhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_giohangpanel_thongtindonhangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_giohangpanel_thongtindonhang);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 102, 0));
        jLabel15.setText("Thông tin sản phẩm");

        cb_panelgiohang_loai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_panelgiohang_loaiActionPerformed(evt);
            }
        });

        cb_panelgiohang_sanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cb_panelgiohang_sanphamMouseClicked(evt);
            }
        });
        cb_panelgiohang_sanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_panelgiohang_sanphamActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 255));
        jLabel16.setText("Số lượng:");

        btn_them.setBackground(new java.awt.Color(51, 51, 51));
        btn_them.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_them.setForeground(new java.awt.Color(255, 255, 255));
        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 204, 102));
        jLabel5.setText("Đơn giá:");

        lb_dongia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_dongia.setForeground(new java.awt.Color(102, 102, 255));
        lb_dongia.setText("$$$");

        btn_sua.setBackground(new java.awt.Color(51, 51, 51));
        btn_sua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 204, 102));
        jLabel6.setText("Mã sản phẩm:");

        lb_masanpham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_masanpham.setForeground(new java.awt.Color(102, 102, 255));
        lb_masanpham.setText("code");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 255));
        jLabel7.setText("Tình trạng:");

        lb_tamtinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_tamtinh.setForeground(new java.awt.Color(255, 0, 0));
        lb_tamtinh.setText("Tạm tính");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cb_panelgiohang_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_panelgiohang_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cb_panelgiohang_loai, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lb_masanpham))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(42, 42, 42)
                                        .addComponent(lb_dongia))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(lb_tamtinh)
                                .addGap(86, 86, 86)
                                .addComponent(btn_them)
                                .addGap(26, 26, 26)
                                .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_panelgiohang_loai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(lb_masanpham))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_panelgiohang_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(lb_dongia))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_panelgiohang_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_them)
                    .addComponent(btn_sua)
                    .addComponent(jLabel7)
                    .addComponent(lb_tamtinh))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        lb_loaikhachhang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_loaikhachhang.setForeground(new java.awt.Color(255, 0, 51));
        lb_loaikhachhang.setText("Khách hàng");

        lb_madonhanggggg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_madonhanggggg.setForeground(new java.awt.Color(255, 0, 51));
        lb_madonhanggggg.setText("Mã Đơn hàng:");

        lb_madonhang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_madonhang.setForeground(new java.awt.Color(255, 0, 51));
        lb_madonhang.setText("~~~~~~~~");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(lb_loaikhachhang))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_sodienthoai)
                                    .addComponent(lb_tenkhachhang))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_madonhanggggg)
                                    .addComponent(lb_madonhang))
                                .addGap(32, 32, 32)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lb_tenkhachhang)
                            .addComponent(lb_madonhanggggg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lb_sodienthoai)
                            .addComponent(lb_madonhang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_loaikhachhang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_giohangpanel_timActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_giohangpanel_timActionPerformed
        if (cb_giohangpanel_timkiem.getSelectedItem().toString().equals("SĐT")) {
            String sql = "SELECT * FROM khachhang WHERE sdtkhachhang LIKE '%" + txt_giohangpanel_tim.getText() + "%'";
            this.searchDataFromKhachHang(sql);

        } else if (cb_giohangpanel_timkiem.getSelectedItem().toString().equals("Tên")) {
            String sql = "SELECT * FROM khachhang WHERE tenkhachhang LIKE '%" + txt_giohangpanel_tim.getText() + "%'";
            this.searchDataFromKhachHang(sql);
        }
    }//GEN-LAST:event_btn_giohangpanel_timActionPerformed

    private void tb_giohangpanel_thongtinkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_giohangpanel_thongtinkhachhangMouseClicked
        RandomStringExmple rand = new RandomStringExmple();
        String random = rand.randomAlphaNumeric(8);

        lb_tenkhachhang.setText(tb_giohangpanel_thongtinkhachhang.getValueAt(tb_giohangpanel_thongtinkhachhang.getSelectedRow(), 0).toString());
        lb_sodienthoai.setText(tb_giohangpanel_thongtinkhachhang.getValueAt(tb_giohangpanel_thongtinkhachhang.getSelectedRow(), 1).toString());
        lb_loaikhachhang.setText(tb_giohangpanel_thongtinkhachhang.getValueAt(tb_giohangpanel_thongtinkhachhang.getSelectedRow(), 2).toString());
        lb_madonhang.setText(random);
    }//GEN-LAST:event_tb_giohangpanel_thongtinkhachhangMouseClicked

    private void cb_panelgiohang_loaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_panelgiohang_loaiActionPerformed
        this.selectDataFromSanPham();
        cb_panelgiohang_sanpham.removeAllItems();
        for (ClassSanPham sp : spList) {
            if (cb_panelgiohang_loai.getSelectedItem().toString().equals(sp.getLoai())) {
                cb_panelgiohang_sanpham.addItem(sp.getTensanpham());
            }
        }

    }//GEN-LAST:event_cb_panelgiohang_loaiActionPerformed

    private void cb_panelgiohang_sanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_panelgiohang_sanphamActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cb_panelgiohang_sanphamActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        dbconnection db = new dbconnection();
        String sql = "INSERT INTO giohang (madonhang,tenkhachhang,sdtkhachhang,loaikhachhang,tensanpham,masanpham,dongia,soluong,trangthai) VALUES ('";
        sql += this.lb_madonhang.getText() + "','";
        sql += this.lb_tenkhachhang.getText() + "','";
        sql += this.lb_sodienthoai.getText() + "','";
        sql += this.lb_loaikhachhang.getText() + "','";
        sql += this.cb_panelgiohang_sanpham.getSelectedItem().toString() + "','";
        sql += this.lb_masanpham.getText() + "','";
        sql += this.lb_dongia.getText() + "','";
        sql += this.txt_panelgiohang_soluong.getText() + "','";
        sql += this.lb_tamtinh.getText() + "')";
        System.out.println(sql);
        db.insertDataIntoKhachHang(sql);
        this.loadDataFromGioHang();

    }//GEN-LAST:event_btn_themActionPerformed

    private void cb_panelgiohang_sanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cb_panelgiohang_sanphamMouseClicked
        // TODO add your handling code here:
        for (ClassSanPham sp : spList) {
            if (cb_panelgiohang_sanpham.getSelectedItem().toString().equals(sp.getTensanpham())) {
                this.lb_masanpham.setText(sp.getMasanpham());
                this.lb_dongia.setText(sp.getDongia() + "");
            }
        }
    }//GEN-LAST:event_cb_panelgiohang_sanphamMouseClicked

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        String value = (tb_giohangpanel_thongtindonhang.getValueAt(tb_giohangpanel_thongtindonhang.getSelectedRow(), 0).toString());
        String sql = "UPDATE giohang SET madonhang=?,tenkhachhang=?,sdtkhachhang=?,loaikhachhang=?,tensanpham=?, masanpham=?, dongia=?, soluong=? WHERE stt="+value ;
        System.out.println(sql);
        this.updateDataIntoDonHang(sql);
        this.loadDataFromGioHang();
    }//GEN-LAST:event_btn_suaActionPerformed

    private void tb_giohangpanel_thongtindonhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_giohangpanel_thongtindonhangMouseClicked
        cb_panelgiohang_sanpham.setSelectedItem(tb_giohangpanel_thongtindonhang.getValueAt(tb_giohangpanel_thongtindonhang.getSelectedRow(), 5).toString());
        lb_masanpham.setText(tb_giohangpanel_thongtindonhang.getValueAt(tb_giohangpanel_thongtindonhang.getSelectedRow(), 6).toString());
        lb_dongia.setText(tb_giohangpanel_thongtindonhang.getValueAt(tb_giohangpanel_thongtindonhang.getSelectedRow(), 7).toString());
        txt_panelgiohang_soluong.setText(tb_giohangpanel_thongtindonhang.getValueAt(tb_giohangpanel_thongtindonhang.getSelectedRow(), 8).toString());
    }//GEN-LAST:event_tb_giohangpanel_thongtindonhangMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_giohangpanel_tim;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JComboBox<String> cb_giohangpanel_timkiem;
    private javax.swing.JComboBox<String> cb_panelgiohang_loai;
    private javax.swing.JComboBox<String> cb_panelgiohang_sanpham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_dongia;
    private javax.swing.JLabel lb_loaikhachhang;
    private javax.swing.JLabel lb_madonhang;
    private javax.swing.JLabel lb_madonhanggggg;
    private javax.swing.JLabel lb_masanpham;
    private javax.swing.JLabel lb_sodienthoai;
    private javax.swing.JLabel lb_tamtinh;
    private javax.swing.JLabel lb_tenkhachhang;
    private javax.swing.JTable tb_giohangpanel_thongtindonhang;
    private javax.swing.JTable tb_giohangpanel_thongtinkhachhang;
    private javax.swing.JTextField txt_giohangpanel_tim;
    private javax.swing.JTextField txt_panelgiohang_soluong;
    // End of variables declaration//GEN-END:variables
}
