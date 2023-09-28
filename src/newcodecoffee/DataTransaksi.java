/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newcodecoffee;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LENOVO
 */
public class DataTransaksi extends javax.swing.JInternalFrame {
    private Koneksi db = new Koneksi();
    Connection konek = Koneksi.koneksiDb();
    DefaultTableModel tabModel;
    /**
     * Creates new form DataTransaksi
     */
    public DataTransaksi() {
        initComponents();
        dataparent();
        kode_transaksi.enable(false);//agar kode tdk bisa d edit
        kode_transaksi.setVisible(false);
    }
    
    
    private void cariData(String key) throws SQLException{
    try{
        Object[] judul_kolom = {"Kode Transaksi","Nama Customer", "Tgl. Transaksi","Sub Total", "Kasir"};
        tabModel= new DefaultTableModel(null, judul_kolom);
        tblTransaksi.setModel(tabModel);
        
        Connection conn = (Connection)Koneksi.koneksiDb();
        Statement stat = conn.createStatement();
        tabModel.getDataVector().removeAllElements();
        
        ResultSet datacari = stat.executeQuery("select * from transaksi where kode_transaksi like '%"+key+"%' "
                +"or nama_customer like '%"+key+"%' "
                +" or tanggal_transaksi like '%"+key+"%'"
                +" or sub_total like '%"+key+"%'"
                +"or kasir like '%"+key+"%'");
        while(datacari.next()){
            Object[] data = {
                datacari.getString ("kode_transaksi"),
                datacari.getString ("nama_customer"),
                datacari.getString ("tanggal_transaksi"),
                datacari.getString ("sub_total"),
                datacari.getString ("kasir"),
            };
            tabModel.addRow(data);
        }
    } catch (Exception ex){
        System.err.println(ex.getMessage());
    }
}
    
    private void dataparent(){
        String caridata = cari.getText();
        //memberikan nama kolom di jtable ketika di tampilkan di form
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Kode Transaksi");
    model.addColumn("Nama Customer");
    model.addColumn("Tgl. Transaksi");
    model.addColumn("Sub Total");
    model.addColumn("Kasir");
    //atur jtable dengan nama kolom diatas
    tblTransaksi.setModel(model);
    
    //menampilkan data dari database ke dalam table
    
   try{
       Statement stat = konek.createStatement(); //Membuat var untuk statement
       ResultSet data = stat.executeQuery ("SELECT * FROM transaksi"); //mengeksekusi query
       while (data.next()){
           //menambahkan baris di model sesuai dengan data dari database
           model.addRow(new Object[]{
           data.getString ("kode_transaksi"),
           data.getString ("nama_customer"),
           data.getString ("tanggal_transaksi"),
           data.getString ("sub_total"),
           data.getString ("kasir"),
       });
           //atur jtable dengan data dari model
           tblTransaksi.setModel(model);
       }
   } catch (Exception e){
       System.err.println("Terjadi Kesalahan :"+e);
   }
    }
    
    private void datachild(){
        //memberikan nama kolom di jtable ketika di tampilkan di form
    DefaultTableModel model1 = new DefaultTableModel();
    model1.addColumn("Kode Detail Transaksi");
    model1.addColumn("Nama Menu");
    model1.addColumn("qty");
    model1.addColumn("Total / menu");
    //atur jtable dengan nama kolom diatas
    tblDetail.setModel(model1);
    
    //menampilkan data dari database ke dalam table
    
   try{
       Statement stat = konek.createStatement(); //Membuat var untuk statement
       ResultSet data = stat.executeQuery ("SELECT * FROM detail_transaksi where kode_transaksi = '"+kode_transaksi.getText()+"'"); //mengeksekusi query
       while (data.next()){
           //menambahkan baris di model sesuai dengan data dari database
           model1.addRow(new Object[]{
           data.getString ("kode_detail_transaksi"),
           data.getString ("nama_menu"),
           data.getString ("qty"),
           data.getString ("total_menu"),
       });
           //atur jtable dengan data dari model
           tblTransaksi.setModel(model1);
       }
   } catch (Exception e){
       System.err.println("Terjadi Kesalahan :"+e);
   }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        kode_transaksi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cari = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cetakperbulan = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(215, 82, 129));

        jPanel2.setBackground(new java.awt.Color(185, 49, 96));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Data Transaksi");

        kode_transaksi.setText("jTextField1");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cari :");

        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(452, 452, 452)
                .addComponent(kode_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kode_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(185, 49, 96));

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTransaksiMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblTransaksi);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Table Transaksi");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(185, 49, 96));

        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDetail);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Table Detail Transaksi");

        cetakperbulan.setText("Cetak Laporan");
        cetakperbulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakperbulanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cetakperbulan)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cetakperbulan)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        int baris = tblTransaksi.getSelectedRow();
        if (baris !=-1) //jika ada data
        {
            //masukkan data dr table ke field
            kode_transaksi.setText(tblTransaksi.getValueAt (baris,0).toString());
        }
        kode_transaksi.enable(false);//agar kode tdk bisa d edit
        kode_transaksi.setVisible(false);
        datachild();
        dataparent();
    }//GEN-LAST:event_tblTransaksiMouseClicked

    private void tblTransaksiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMousePressed
       
    }//GEN-LAST:event_tblTransaksiMousePressed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariActionPerformed

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        String key = cari.getText();
        System.out.println(key);
        
        if(key!=""){
            try {
                cariData(key);
            } catch (SQLException ex) {
                Logger.getLogger(DataTransaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            dataparent();
        }
    }//GEN-LAST:event_cariKeyReleased

    private void cetakperbulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakperbulanActionPerformed
        CetakLaporanBln panggil = new CetakLaporanBln();
        panggil.show();
    }//GEN-LAST:event_cetakperbulanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cari;
    private javax.swing.JButton cetakperbulan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kode_transaksi;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblTransaksi;
    // End of variables declaration//GEN-END:variables
}
