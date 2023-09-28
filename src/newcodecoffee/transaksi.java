/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newcodecoffee;
import newcodecoffee.Koneksi;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JLabel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LENOVO
 */
public class transaksi extends javax.swing.JInternalFrame {
    private Koneksi db = new Koneksi();
    Connection konek = Koneksi.koneksiDb();
    
    /**
     * Creates new form transaksi
     */
    public String nama;
    public transaksi(String nama) {
        initComponents();
        this.nama = nama;
        kasir.setText(this.nama);
        cbmenu();
        nostruk();
        nodetailstruk();
        tampilData();
        kodecustomer();
        kode_customer.setVisible(false);
        kasir.setVisible(false);
        kasir.enable(false);
        new Thread(){
            public void run(){
                while (true){
                    Calendar kal = new GregorianCalendar();
                    int tahun = kal.get(Calendar.YEAR);
                    int bulan = kal.get(Calendar.MONTH)+1;
                    int hari = kal.get(Calendar.DAY_OF_MONTH);
                    String tanggal = tahun+"-"+bulan+"-"+hari;
                    tgl_transaksi.setText(tanggal);
                }
            }
        }.start();
        varkode();
        varkode.setVisible(false);
        varkode.enable(false);
    }

    
public void varkode(){
    String kode = kode_transaksi.getText();
        varkode.setText(kode);
}
public void cbmenu(){
            db.koneksiDb();
        try {
            String query = "SELECT * FROM menu";
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {                
                menuchooser.addItem(rs.getString("nama_menu"));
            }
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
            
        } catch (SQLException e) {
        }
        harga.enable(false);
        total.enable(false);
}

private void nostruk()
    {
       try {
            String query = "select * from transaksi order by kode_transaksi desc";
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String nostr = rs.getString("kode_transaksi").substring(1);
                String AN = "" + (Integer.parseInt(nostr) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               kode_transaksi.setText("T" + Nol + AN);
            } else {
               kode_transaksi.setText("T0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
       kode_transaksi.enable(false);
     }
private void nodetailstruk(){
    try {
            String query = "select * from detail_transaksi order by kode_detail_transaksi desc";
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String nostr = rs.getString("kode_detail_transaksi").substring(1);
                String AN = "" + (Integer.parseInt(nostr) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               kode_detail_transaksi.setText("D" + Nol + AN);
            } else {
               kode_detail_transaksi.setText("D0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
       kode_detail_transaksi.enable(false);
}

private void kodecustomer(){
    try {
            String query = "select * from pelanggan order by kode_pelanggan desc";
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String nostr = rs.getString("kode_pelanggan").substring(1);
                String AN = "" + (Integer.parseInt(nostr) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               kode_customer.setText("P" + Nol + AN);
            } else {
               kode_customer.setText("P0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
}
private void tampilData() {
    //memberikan nama kolom di jtable ketika di tampilkan di form
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Kode Detail Transaksi");
    model.addColumn("Menu");
    model.addColumn("Harga");
    model.addColumn("Qty");
    model.addColumn("Total");
    //atur jtable dengan nama kolom diatas
    tbl_transaksi.setModel(model);
    
    
    //menampilkan data dari database ke dalam table
    
   try{
       Statement stat = konek.createStatement(); //Membuat var untuk statement
       ResultSet data = stat.executeQuery ("SELECT * FROM detail_transaksi where kode_transaksi = '"+kode_transaksi.getText()+"'"); //mengeksekusi query
       while (data.next()){
           //menambahkan baris di model sesuai dengan data dari database
           model.addRow(new Object[]{
           data.getString ("kode_detail_transaksi"),
           data.getString ("nama_menu"),
           data.getString ("harga_menu"),
           data.getString ("qty"),
           data.getString ("total_menu"),
       });
           //atur jtable dengan data dari model
           tbl_transaksi.setModel(model);
            kode_menu.enable(false);
        harga.enable(false);
       }
   } catch (Exception e){
       System.err.println("Terjadi Kesalahan :"+e);
   }
    }

private void sum(){
        //menjumlahkan isi colom ke 4 dalam tabel
            int total = 0;
            for (int i =0; i< tbl_transaksi.getRowCount(); i++){
                   int amount = Integer.parseInt((String)tbl_transaksi.getValueAt(i, 4));
                   total += amount;
            }
            subTotal.setText(""+total); 
    }
void filterhuruf(KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
            JOptionPane.showMessageDialog(null,"Pada Kolom Jumlah Hanya Bisa Memasukan Karakter Angka");
        }
    }

void filterangka(KeyEvent b){
        if(Character.isDigit(b.getKeyChar())){
            b.consume();
            JOptionPane.showMessageDialog(null,"Pada Kolom Jumlah Hanya Bisa Memasukan Karakter Huruf");
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
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        kode_transaksi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nama_menu = new javax.swing.JLabel();
        hg = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        menuchooser = new javax.swing.JComboBox<>();
        kode_menu = new javax.swing.JTextField();
        harga = new javax.swing.JTextField();
        qty = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_transaksi = new javax.swing.JTable();
        btnbatal = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        nama_menu1 = new javax.swing.JLabel();
        kode_detail_transaksi = new javax.swing.JTextField();
        customer = new javax.swing.JTextField();
        subTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        telp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        kode_customer = new javax.swing.JTextField();
        tgl_transaksi = new javax.swing.JLabel();
        kasir = new javax.swing.JTextField();
        varkode = new javax.swing.JTextField();

        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(215, 82, 129));

        jPanel2.setBackground(new java.awt.Color(185, 49, 96));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Transaksi");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(579, 579, 579))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(185, 49, 96));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kode Transaksi :");

        kode_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_transaksiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tgl. Transaksi    :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama Customer  :");

        nama_menu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nama_menu.setForeground(new java.awt.Color(255, 255, 255));
        nama_menu.setText("Menu           :");

        hg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        hg.setForeground(new java.awt.Color(255, 255, 255));
        hg.setText("Harga          :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Qty              :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total           :");

        menuchooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih Menu--" }));
        menuchooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuchooserActionPerformed(evt);
            }
        });

        kode_menu.setEnabled(false);
        kode_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_menuActionPerformed(evt);
            }
        });

        harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaActionPerformed(evt);
            }
        });

        qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyActionPerformed(evt);
            }
        });
        qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyKeyTyped(evt);
            }
        });

        total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalActionPerformed(evt);
            }
        });

        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        tbl_transaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_transaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_transaksi);

        btnbatal.setText("Batal");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        nama_menu1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nama_menu1.setForeground(new java.awt.Color(255, 255, 255));
        nama_menu1.setText("Detail Transaksi");

        kode_detail_transaksi.setEnabled(false);
        kode_detail_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_detail_transaksiActionPerformed(evt);
            }
        });

        customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerActionPerformed(evt);
            }
        });
        customer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                customerKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sub Total :");

        telp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telpActionPerformed(evt);
            }
        });
        telp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telpKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("No. Telp                :");

        kode_customer.setEditable(false);
        kode_customer.setEnabled(false);

        tgl_transaksi.setBackground(new java.awt.Color(255, 255, 255));
        tgl_transaksi.setForeground(new java.awt.Color(255, 255, 255));
        tgl_transaksi.setText("tgl");

        varkode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varkodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(kode_detail_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kode_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kode_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kasir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tgl_transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kode_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(varkode, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(telp)
                            .addComponent(customer, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(qty))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(hg)
                                .addGap(18, 18, 18)
                                .addComponent(harga))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(total))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(nama_menu)
                                .addGap(18, 18, 18)
                                .addComponent(menuchooser, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnhapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btntambah)))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(nama_menu1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnbatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnsimpan)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(kode_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(varkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(tgl_transaksi))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addGap(42, 42, 42)
                .addComponent(nama_menu1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nama_menu)
                            .addComponent(menuchooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hg)
                            .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btntambah)
                            .addComponent(btnhapus)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsimpan)
                    .addComponent(btnbatal))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kode_detail_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kode_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kode_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void menuchooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuchooserActionPerformed
        try
            {
                String query = "SELECT * FROM menu where nama_menu ='"+ menuchooser.getSelectedItem().toString().trim()+"'";
                Statement st = konek.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {
                    kode_menu.setText(rs.getString("kode_menu"));
                    harga.setText(rs.getString("harga_menu"));
                }
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,"GAGAL");
            }
        kode_menu.enable(false);
        harga.enable(false);
    }//GEN-LAST:event_menuchooserActionPerformed

   
    private void kode_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_menuActionPerformed
        
    }//GEN-LAST:event_kode_menuActionPerformed

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed
        total.enable(false);
    }//GEN-LAST:event_totalActionPerformed

    private void qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyActionPerformed
        int price = Integer.valueOf(harga.getText());
        int amt = Integer.valueOf(qty.getText());
        int hsl = price * amt;
    
    // tampilkan hasil ke txtfield
        total.setText(String.valueOf(hsl));   
    }//GEN-LAST:event_qtyActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        String kosong = total.getText();
        
        try{
            Statement stat = konek.createStatement();
            ResultSet data = stat.executeQuery("select*from detail_transaksi");
        if(kosong.equals("")){
            JOptionPane.showMessageDialog(null,"Tekan enter setelah mengisi Qty"); //validasi utk qty dan total
            qty.requestFocus();
        } else { //input data
            String sql = "INSERT INTO detail_transaksi VALUES('"+kode_detail_transaksi.getText() + "'"
                        +",'" + kode_transaksi.getText()+ "'"
                        +",'" + kode_menu.getText()+ "'"
                        +",'"+ menuchooser.getSelectedItem().toString().trim()+"'"
                        +",'" + harga.getText()+ "'"
                        +",'" + qty.getText()+ "'"
                        +",'" + total.getText()+ "')";
                 stat.executeUpdate(sql);
                 
                 //membersihkan komponen inputan
                 kode_menu.setText("");
                 menuchooser.setSelectedIndex(0);
                 harga.setText("");
                 qty.setText("");
                 total.setText("");
                
                 tampilData();
                 nodetailstruk();
                 sum();
        } 
        stat.close();
        
        }
        catch (Exception exc)
        {
            System.err.println("Terjadi Kesalahan:" +exc);
        }
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        kode_menu.setText("");
        menuchooser.setSelectedIndex(0);
        harga.setText("");
        qty.setText("");
        total.setText("");
        
    }//GEN-LAST:event_btnhapusActionPerformed

    private void kode_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_transaksiActionPerformed
        
    }//GEN-LAST:event_kode_transaksiActionPerformed

    private void kode_detail_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_detail_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kode_detail_transaksiActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        String custkosong = customer.getText();
        //String datekosong = tgl_transaksi.getDateFormatString();
        try{
            Statement stat = konek.createStatement();
            ResultSet data = stat.executeQuery("select*from transaksi");
            ResultSet datacust = stat.executeQuery("select*from pelanggan");
        if(custkosong.equals("")){
            JOptionPane.showMessageDialog(null,"Nama Customer kosong!"); //validasi utk qty dan total
            customer.requestFocus();
        //} else if (datekosong.equals("")) {
        // JOptionPane.showMessageDialog(null,"Tanggal Transaksi kosong!"); //validasi utk qty dan total
         //   tgl_transaksi.requestFocus();
        }else { //input data
            //String tampilan = "yyyy-MM-dd";
            //SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            //String tanggal = String.valueOf(fm.format(tgl_transaksi.getText()));
            double hrg=Double.parseDouble(subTotal.getText());
            
                String transaksi = "INSERT INTO transaksi VALUES('"+kode_transaksi.getText() + "'"
                        +",'" + customer.getText()+ "'"
                        +",'" + tgl_transaksi.getText()+ "'"
                        +",'"+ hrg+"'"
                        +",'"+kasir.getText()+"')";
                 stat.executeUpdate(transaksi);
                 
                 String cust = "INSERT INTO pelanggan VALUES('"+kode_customer.getText() + "'"
                        +",'" + customer.getText()+ "'"
                        +",'" + telp.getText()+ "')";
                 stat.executeUpdate(cust);
                 
                 //membersihkan komponen inputan
                 kode_menu.setText("");
                 menuchooser.setSelectedIndex(0);
                 harga.setText("");
                 qty.setText("");
                 total.setText("");
                 subTotal.setText("");
                 customer.setText("");
                 telp.setText("");
                 nostruk();
                 nodetailstruk();
                 kodecustomer();
                 tampilData();
                 JOptionPane.showMessageDialog(null,"Pesanan berhasil di proses");
                 int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda ingin mencetak struk?", "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
                    if (ok == 0)
                    {
                     try {
                        File namafile = new File("src/newcodecoffee/struk.jasper");
                        HashMap hash = new HashMap();
                        hash.put("kode", varkode.getText());
                        JasperPrint jp = JasperFillManager.fillReport(namafile.getPath(), hash, Koneksi.koneksiDb());
                        JasperViewer.viewReport(jp, false);
                        varkode();
                    } catch (JRException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }
                    }
        } 
        stat.close();
        
        }
        catch (Exception exc)
        {
            System.err.println("Terjadi Kesalahan:" +exc);
        }
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void tbl_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_transaksiMouseClicked

    }//GEN-LAST:event_tbl_transaksiMouseClicked

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin membatalkan orderan?", "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        try{
            Statement stat = konek.createStatement();
            ResultSet hapus = stat.executeQuery("select*from detail_transaksi");
        if (ok == 0)
        {
            
            String hapuschild = "delete from detail_transaksi where kode_transaksi = '"+kode_transaksi.getText()+"'";
                stat.executeUpdate(hapuschild);
                 //membersihkan komponen inputan
                 kode_menu.setText("");
                 menuchooser.setSelectedIndex(0);
                 harga.setText("");
                 qty.setText("");
                 total.setText("");
                 subTotal.setText("");
                 telp.setText("");
                 nostruk();
                 nodetailstruk();
                 kodecustomer();
                 tampilData();
        }
        stat.close();
        
        }
        catch (Exception exc)
        {
            System.err.println("Terjadi Kesalahan:" +exc);
        }
    }//GEN-LAST:event_btnbatalActionPerformed

    private void telpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telpKeyTyped
        filterhuruf(evt);
    }//GEN-LAST:event_telpKeyTyped

    private void qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyKeyTyped

    }//GEN-LAST:event_qtyKeyTyped

    private void customerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerKeyTyped
        filterangka(evt);
    }//GEN-LAST:event_customerKeyTyped

    private void hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.dispose();
    }//GEN-LAST:event_formInternalFrameClosed

    private void customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerActionPerformed

    private void telpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telpActionPerformed

    private void varkodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varkodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_varkodeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JTextField customer;
    private javax.swing.JTextField harga;
    private javax.swing.JLabel hg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kasir;
    private javax.swing.JTextField kode_customer;
    private javax.swing.JTextField kode_detail_transaksi;
    private javax.swing.JTextField kode_menu;
    private javax.swing.JTextField kode_transaksi;
    private javax.swing.JComboBox<String> menuchooser;
    private javax.swing.JLabel nama_menu;
    private javax.swing.JLabel nama_menu1;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField subTotal;
    private javax.swing.JTable tbl_transaksi;
    private javax.swing.JTextField telp;
    private javax.swing.JLabel tgl_transaksi;
    private javax.swing.JTextField total;
    private javax.swing.JTextField varkode;
    // End of variables declaration//GEN-END:variables
}
