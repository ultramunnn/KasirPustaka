/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

/**
 *
 * @author HP
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import model.Buku;

public class TambahBukuView extends javax.swing.JFrame {

    private DefaultTableModel table;

    /**
     * Creates new form TambahBukuView
     */
    public TambahBukuView() {
        initComponents();

        int nextId = Buku.getNextBookId();
        Tid_buku.setText(String.valueOf(nextId));
        Tid_buku.setEditable(false);

        // Ambil data dari DB
        String[][] data = Buku.getAllBook();

        // Nama kolom
        String[] columnNames = {"ID", "Judul", "Penulis", "Harga", "Stock"};

        // Set model langsung ke JTable
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        Ttable.setModel(model);

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Tid_buku = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Tjudul_buku = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Tpenulis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Tharga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Tstock = new javax.swing.JTextField();
        Bsimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Ttable = new javax.swing.JTable();
        Bedit = new javax.swing.JButton();
        Bdelete = new javax.swing.JButton();
        Tcari = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Bdeleteall = new javax.swing.JButton();
        Bcari = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(225, 223, 171));

        jPanel1.setBackground(new java.awt.Color(0, 204, 153));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 3, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tambah Buku");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("id_buku:");

        Tid_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tid_bukuActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Judul Buku");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Penulis");

        Tpenulis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TpenulisActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Harga");

        Tharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThargaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Stok");

        Tstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TstockActionPerformed(evt);
            }
        });

        Bsimpan.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Bsimpan.setText("Simpan");
        Bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsimpanActionPerformed(evt);
            }
        });

        Ttable.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Ttable.setModel(new javax.swing.table.DefaultTableModel(
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
        Ttable.setSelectionBackground(new java.awt.Color(0, 204, 153));
        Ttable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Ttable.setShowGrid(false);
        Ttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TtableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TtableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Ttable);

        Bedit.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Bedit.setText("Edit");
        Bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeditActionPerformed(evt);
            }
        });

        Bdelete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Bdelete.setText("Delete");
        Bdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BdeleteActionPerformed(evt);
            }
        });

        Tcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TcariActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cari");

        Bdeleteall.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Bdeleteall.setText("Delete All");
        Bdeleteall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BdeleteallActionPerformed(evt);
            }
        });

        Bcari.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Bcari.setText("Cari");
        Bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BcariActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/burung.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Bedit)
                        .addGap(10, 10, 10)
                        .addComponent(Bdelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Bdeleteall))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Tid_buku, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bcari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Tharga, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                                .addComponent(Tpenulis, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Tjudul_buku))
                            .addComponent(Tstock, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Bsimpan))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Tid_buku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(Tjudul_buku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(Tpenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(Tharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(Tstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(Bsimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Tcari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Bcari))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addGap(361, 361, 361)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Bedit)
                    .addComponent(Bdelete)
                    .addComponent(Bdeleteall))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BdeleteActionPerformed

        try {
            int book_id = Integer.parseInt(Tid_buku.getText());

            Buku.deleteBook(book_id);

            JOptionPane.showMessageDialog(this, "Buku berhasil dihapus!");

            // Refresh tabel setelah delete
            String[][] data = Buku.getAllBook();
            String[] columnNames = {"ID", "Judul", "Penulis", "Harga", "Stock"};
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            Ttable.setModel(model);

            // Bersihkan form
            Tid_buku.setText("");
            Tjudul_buku.setText("");
            Tpenulis.setText("");
            Tharga.setText("");
            Tstock.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID buku tidak valid!");
        }


    }//GEN-LAST:event_BdeleteActionPerformed

    private void BeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeditActionPerformed
        try {
            int book_id = Integer.parseInt(Tid_buku.getText());
            String judul = Tjudul_buku.getText();
            String penulis = Tpenulis.getText();
            int harga = Integer.parseInt(Tharga.getText());
            int stock = Integer.parseInt(Tstock.getText());

            boolean isSuccess = Buku.editBook(book_id, judul, penulis, harga, stock);
            if (isSuccess) {
                JOptionPane.showMessageDialog(this, "Buku berhasil diupdate!");
                // Refresh tabel setelah update
                String[][] data = Buku.getAllBook();
                String[] columnNames = {"ID", "Judul", "Penulis", "Harga", "Stock"};
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                Ttable.setModel(model);

                // Bersihkan form
                Tid_buku.setText("");
                Tjudul_buku.setText("");
                Tpenulis.setText("");
                Tharga.setText("");
                Tstock.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate buku di database.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan Stock harus berupa angka!", "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BeditActionPerformed

    private void BsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsimpanActionPerformed

        try {
            String judul = Tjudul_buku.getText().trim();
            String penulis = Tpenulis.getText().trim();
            String stockText = Tstock.getText().trim();
            String hargaText = Tharga.getText().trim();

            // Validasi kosong
            if (judul.isEmpty() || penulis.isEmpty() || stockText.isEmpty() || hargaText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Input Kosong", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Parsing angka dengan try-catch terpisah untuk validasi lebih spesifik
            int stock, harga;
            try {
                stock = Integer.parseInt(stockText);
                harga = Integer.parseInt(hargaText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock dan Harga harus berupa angka!", "Input Kosong", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Buku.addBook(judul, penulis, harga, stock);
            int nextId = Buku.getNextBookId();
            Tid_buku.setText(String.valueOf(nextId));

            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan");

            // Reload data langsung tanpa fungsi
            String[][] data = Buku.getAllBook();
            String[] columnNames = {"ID", "Judul", "Penulis", "Harga", "Stock"};
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            Ttable.setModel(model);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka");
        }
    }//GEN-LAST:event_BsimpanActionPerformed

    private void TcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TcariActionPerformed

    private void BdeleteallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BdeleteallActionPerformed

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus semua data buku?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Buku.deleteAllBooks();

            JOptionPane.showMessageDialog(this, "Semua buku berhasil dihapus!");

            // Refresh tabel
            String[][] data = Buku.getAllBook();
            String[] columnNames = {"ID", "Judul", "Penulis", "Harga", "Stock"};
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            Ttable.setModel(model);

            // Bersihkan form
            Tid_buku.setText("");
            Tjudul_buku.setText("");
            Tpenulis.setText("");
            Tharga.setText("");
            Tstock.setText("");
        }
    }//GEN-LAST:event_BdeleteallActionPerformed

    private void Tid_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tid_bukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tid_bukuActionPerformed

    private void BcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BcariActionPerformed
        String keyword = Tcari.getText();
        String[][] data = Buku.searchBook(keyword);
        String[] columnNames = {"ID", "Judul", "Penulis", "Harga", "Stock"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        Ttable.setModel(model);
    }//GEN-LAST:event_BcariActionPerformed

    private void TtableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TtableMouseReleased

    }//GEN-LAST:event_TtableMouseReleased

    private void TtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TtableMouseClicked
        int selectedRow = Ttable.getSelectedRow();
        if (selectedRow != -1) {
            Tid_buku.setText(Ttable.getValueAt(selectedRow, 0).toString());
            Tjudul_buku.setText(Ttable.getValueAt(selectedRow, 1).toString());
            Tpenulis.setText(Ttable.getValueAt(selectedRow, 2).toString());
            Tharga.setText(Ttable.getValueAt(selectedRow, 3).toString());
            Tstock.setText(Ttable.getValueAt(selectedRow, 4).toString());
        }
    }//GEN-LAST:event_TtableMouseClicked

    private void ThargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThargaActionPerformed

    private void TpenulisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TpenulisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TpenulisActionPerformed

    private void TstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TstockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TstockActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TambahBukuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TambahBukuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TambahBukuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TambahBukuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TambahBukuView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bcari;
    private javax.swing.JButton Bdelete;
    private javax.swing.JButton Bdeleteall;
    private javax.swing.JButton Bedit;
    private javax.swing.JButton Bsimpan;
    private javax.swing.JTextField Tcari;
    private javax.swing.JTextField Tharga;
    private javax.swing.JTextField Tid_buku;
    private javax.swing.JTextField Tjudul_buku;
    private javax.swing.JTextField Tpenulis;
    private javax.swing.JTextField Tstock;
    private javax.swing.JTable Ttable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
