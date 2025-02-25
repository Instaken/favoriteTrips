
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author No2Mo
 */
public class EditVisitFrame extends javax.swing.JFrame {

    /**
     * Creates new form EditVisitFrame
     */
    public EditVisitFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   
    private void updateVisit() throws SQLException {
        int visitID;
        Connection conn = DriverManager.getConnection("-", "-", "-");
        try {
            visitID = Integer.parseInt(EditVisitIDField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid visit ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String column = (String) OptionComboBox.getSelectedItem();
        String newValue = updatedField.getText();
if(column.equals("Year")){
    column = "yearvisited" ;
}
        
        String query = "UPDATE visits SET " + column + " = ? WHERE visitid = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            if (column.equals("Rating")) {
                ps.setInt(1, Integer.parseInt(newValue));
            } 
            else if(column.equals("yearvisited")){
                ps.setInt(1, Integer.parseInt(newValue));
            }
            else {
                ps.setString(1, newValue);
            }
            ps.setInt(2, visitID);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Visit updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Visit ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OptionComboBox = new javax.swing.JComboBox<>();
        editLabel = new javax.swing.JLabel();
        updatedField = new javax.swing.JTextField();
        editVisitIDLabel = new javax.swing.JLabel();
        EditVisitIDField = new javax.swing.JTextField();
        EditVisitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        OptionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Country", "City", "Year", "Season" , "Feature", "Comment", "Rating", }));

        editLabel.setText("will be updated to ");

        editVisitIDLabel.setText("Visit ID :");

        EditVisitButton.setText("Update Visit");
        EditVisitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditVisitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(OptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(editLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(updatedField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(editVisitIDLabel)
                        .addGap(30, 30, 30)
                        .addComponent(EditVisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(EditVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editVisitIDLabel)
                    .addComponent(EditVisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updatedField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editLabel)
                    .addComponent(OptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(EditVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EditVisitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditVisitButtonActionPerformed
        try {
            // TODO add your handling code here:
            updateVisit();
        } catch (SQLException ex) {
            Logger.getLogger(EditVisitFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EditVisitButtonActionPerformed

    /**
     * @param args the command line arguments
     */

      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditVisitButton;
    private javax.swing.JTextField EditVisitIDField;
    private javax.swing.JComboBox<String> OptionComboBox;
    private javax.swing.JLabel editLabel;
    private javax.swing.JLabel editVisitIDLabel;
    private javax.swing.JTextField updatedField;
    // End of variables declaration//GEN-END:variables

    
}
