
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author No2Mo
 */
public class MainFrame extends javax.swing.JFrame {
 private int userId;
   
    /**
     * @param 
     * @param 
     */
    public MainFrame(int userID) {
        initComponents();
        this.userId = userID ;
        loadVisits();
        loadVisitsforShare();
    }

   
   
    
    private void addVisit() {
        String country = countryField.getText();
        String city = cityField.getText();
        int year = Integer.parseInt(yearField.getText());
        String season = seasonField.getText();
        String feature = featureField.getText();
        String comment = commentField.getText();
        int rating = Integer.parseInt(ratingField.getText());

        try (Connection conn = DriverManager.getConnection("---", "---", "-----")) {
            String query = "INSERT INTO visits (userid, country, city, yearvisited, season, feature, comment, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setString(2, country);
            pst.setString(3, city);
            pst.setInt(4, year);
            pst.setString(5, season);
            pst.setString(6, feature);
            pst.setString(7, comment);
            pst.setInt(8, rating);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Visit added successfully!");
            loadVisits();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void deleteVisit() {
        int visitId = Integer.parseInt(VisitIDField.getText());

        String DB_URL = "------";
        String DB_USER = "-------";
        String DB_PASS = "----";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM visits WHERE visitid=?")) {

            stmt.setInt(1, visitId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Visit deleted successfully!");
                loadVisits();
            } else {
                JOptionPane.showMessageDialog(null, "Visit ID not found.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public void loadVisits() {
        try (Connection conn = DriverManager.getConnection("-----", "-----", "------")) {
            String query = "SELECT * FROM visits WHERE userid = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
 
            DefaultTableModel DTM = (DefaultTableModel)VisitsTable.getModel();
            
            
            DTM.setRowCount(0); 
            while (rs.next()) {
                DTM.addRow(new Object[]{
                    rs.getInt("visitid"),
                    rs.getString("country"),
                    rs.getString("city"),
                    rs.getInt("yearvisited"),
                    rs.getString("season"),
                    rs.getString("feature"),
                    rs.getString("comment"),
                    rs.getInt("rating")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
      private void filterBestFeatureFood() {
    try (Connection conn = DriverManager.getConnection("-", "---", "-")){
        String query = "SELECT * FROM visits WHERE feature = 'Food' ORDER BY rating DESC";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery(query);
        DefaultTableModel model = (DefaultTableModel)VisitsTable.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            model.addRow(new Object[]{
               rs.getInt("visitid"),
                rs.getString("country"),
               rs.getString("city"),
               rs.getInt("yearvisited"),
               rs.getString("season"),
               rs.getString("feature"),
               rs.getString("comment"),
               rs.getInt("rating")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


private void searchByYear() {
    try (Connection conn = DriverManager.getConnection("--", "-", "--")){
        String yearvisited = searchByYearField.getText();
        String query = "SELECT * FROM visits WHERE yearvisited = '" + yearvisited + "'";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery(query);
        DefaultTableModel model = (DefaultTableModel)VisitsTable.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            model.addRow(new Object[]{ 
               rs.getInt("visitid"),
                rs.getString("country"),
               rs.getString("city"),
               rs.getInt("yearvisited"),
               rs.getString("season"),
               rs.getString("feature"),
               rs.getString("comment"),
               rs.getInt("rating")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void showMostVisitedCountry() {
    try (Connection conn = DriverManager.getConnection("-", "----", "-")){

        String query = "SELECT country, COUNT(*) as visit_count FROM visits GROUP BY country ORDER BY visit_count DESC LIMIT 1";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery(query);
        DefaultTableModel model = (DefaultTableModel) VisitsTable.getModel();
        model.setRowCount(0); 

        if (rs.next()) {
            String mostVisitedCountry = rs.getString("country");
            String queryAll = "SELECT * FROM visits WHERE country = '" + mostVisitedCountry + "'";
            ResultSet rsAll = pst.executeQuery(queryAll);

            while (rsAll.next()) {
                model.addRow(new Object[]{
                rsAll.getInt("visitid"),
                rsAll.getString("country"),
                rsAll.getString("city"),
                rsAll.getInt("yearvisited"),
                rsAll.getString("season"),
                rsAll.getString("feature"),
                rsAll.getString("comment"),
                rsAll.getInt("rating")
                });
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void showSpringVisits() {
    try (Connection conn = DriverManager.getConnection("--", "-", "-")){
        String query = "SELECT * FROM visits WHERE season = 'Spring'";
       PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery(query);
        DefaultTableModel model = (DefaultTableModel)VisitsTable.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            model.addRow(new Object[]{
             rs.getInt("visitid"), 
             rs.getString("country"),
             rs.getString("city"),
             rs.getInt("yearvisited"),
             rs.getString("season"),
             rs.getString("feature"),
             rs.getString("comment"),
             rs.getInt("rating")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void loadVisitsforShare() {
try (Connection conn = DriverManager.getConnection("-", "-", "-")) {
            String query = "SELECT sender.username AS sender_username, receiver.username AS receiver_username, visits.visitid, "
                    + "visits.country, visits.city, visits.season, visits.feature , visits.rating "
                    + "FROM sharedvisits JOIN userinfo AS sender ON sharedvisits.senderid = sender.userid"
                    + " JOIN userinfo AS receiver ON sharedvisits.receiverid = receiver.userid JOIN visits ON sharedvisits.visitid = visits.visitid";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel DTM = (DefaultTableModel)shareVisitTable.getModel();
            
            DTM.setRowCount(0);
            while (rs.next()) {
                int Id = rs.getInt("visitid");            
                String senderUsername = rs.getString("sender_username");
                String receiverUsername = rs.getString("receiver_username");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String season = rs.getString("season");
                String feature = rs.getString("feature");
                int rating = rs.getInt("rating");
                DTM.addRow(new Object[]{Id , senderUsername, receiverUsername, country, city, season, feature, rating});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    
private void shareVisit() throws SQLException {
        String visitIDStr = sharedVisitIDField.getText();
        String senderUsername = fromField.getText();
        String receiverUsername = toField.getText();
Connection conn = DriverManager.getConnection("-", "--", "-");
        try {
            int visitID = Integer.parseInt(visitIDStr);
            int senderID = getUserID(senderUsername);
            int receiverID = getUserID(receiverUsername);

            if (senderID != -1 && receiverID != -1) {
                insertSharedVisit(senderID, receiverID, visitID);
                JOptionPane.showMessageDialog(this, "Visit shared successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid sender or receiver username.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid visit ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getUserID(String username) throws SQLException {
        String query = "SELECT userid FROM userinfo WHERE username = ?";
        Connection conn = DriverManager.getConnection("-", "-", "-");
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("userid");
                }
            }
        }
        return -1; // If there is not a any user with the given username.
    }

    private void insertSharedVisit(int senderID, int receiverID, int visitID) throws SQLException {
        String query = "INSERT INTO sharedvisits (senderid, receiverid, visitid) VALUES (?, ?, ?)";
       Connection conn = DriverManager.getConnection("-", "--", "-");
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, senderID);
            ps.setInt(2, receiverID);
            ps.setInt(3, visitID);
            ps.executeUpdate();
        }
    }


    
   private static void showSharedVisitsToMe(int loggedInUserID, JTable shareVisitTable) {
    try (Connection conn = DriverManager.getConnection("-", "-", "-")) {
        String query = "SELECT sender.username AS sender_username, receiver.username AS receiver_username,visits.visitid , visits.country, visits.city, visits.season, visits.feature, visits.rating " +
                       "FROM sharedvisits " +
                       "JOIN userinfo AS sender ON sharedvisits.senderid = sender.userid " +
                       "JOIN userinfo AS receiver ON sharedvisits.receiverid = receiver.userid " +
                       "JOIN visits ON sharedvisits.visitid = visits.visitid " +
                       "WHERE sharedvisits.receiverid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, loggedInUserID);
        ResultSet rs = ps.executeQuery();
        DefaultTableModel model = (DefaultTableModel) shareVisitTable.getModel();
        
        model.setRowCount(0); 
        while (rs.next()){
            int Id = rs.getInt("visitid");
            String senderUsername = rs.getString("sender_username");
            String receiverUsername = rs.getString("receiver_username");
            String country = rs.getString("country");
            String city = rs.getString("city");
            String season = rs.getString("season");
            String feature = rs.getString("feature");
            int rating = rs.getInt("rating");

            model.addRow(new Object[]{Id,senderUsername, receiverUsername, country, city, season, feature, rating});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        ShareVisit = new javax.swing.JTabbedPane();
        DisplayVisits = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VisitsTable = new javax.swing.JTable();
        EditVisitButton = new javax.swing.JButton();
        BestFeatureFoodButton = new javax.swing.JButton();
        searchbyYearLabel = new javax.swing.JLabel();
        searchByYearField = new javax.swing.JTextField();
        SearchByYearButton = new javax.swing.JButton();
        MostVisitedButton = new javax.swing.JButton();
        springButton = new javax.swing.JButton();
        RefreshButton = new javax.swing.JButton();
        DisplayImgButton = new javax.swing.JButton();
        ImageLabel = new javax.swing.JLabel();
        displayVisitIDLabel = new javax.swing.JLabel();
        displayVisitIDField = new javax.swing.JTextField();
        AddVisit = new javax.swing.JPanel();
        countryLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        ratingLabel = new javax.swing.JLabel();
        featureLabel = new javax.swing.JLabel();
        commentLabel = new javax.swing.JLabel();
        seasonLabel = new javax.swing.JLabel();
        seasonField = new javax.swing.JTextField();
        countryField = new javax.swing.JTextField();
        ratingField = new javax.swing.JTextField();
        commentField = new javax.swing.JTextField();
        yearField = new javax.swing.JTextField();
        cityField = new javax.swing.JTextField();
        featureField = new javax.swing.JTextField();
        AddVisitButton = new javax.swing.JButton();
        DeleteVisit = new javax.swing.JPanel();
        VisitIDLabel = new javax.swing.JLabel();
        VisitIDField = new javax.swing.JTextField();
        DeleteVisitButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        fromLabel = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();
        fromField = new javax.swing.JTextField();
        sharedVisitIDField = new javax.swing.JTextField();
        sharedVisitIDLabel = new javax.swing.JLabel();
        toField = new javax.swing.JTextField();
        shareVisitButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        shareVisitTable = new javax.swing.JTable();
        sharedToMeButton = new javax.swing.JButton();
        refreshForShareButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        VisitsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id" , "Country", "City", "Year", "Season", "Feature", "Comment", "Rating",
            }
        ));
        jScrollPane1.setViewportView(VisitsTable);

        EditVisitButton.setText("Edit Visit");
        EditVisitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditVisitButtonActionPerformed(evt);
            }
        });

        BestFeatureFoodButton.setText("Best Feature Food");
        BestFeatureFoodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BestFeatureFoodButtonActionPerformed(evt);
            }
        });

        searchbyYearLabel.setText("Year :");

        SearchByYearButton.setText("Search by Year");
        SearchByYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchByYearButtonActionPerformed(evt);
            }
        });

        MostVisitedButton.setText("Show Most Visited Country");
        MostVisitedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostVisitedButtonActionPerformed(evt);
            }
        });

        springButton.setText("Only Spring Visits");
        springButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                springButtonActionPerformed(evt);
            }
        });

        RefreshButton.setText("Refresh Table");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });

        DisplayImgButton.setText("Display Image ");
        DisplayImgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayImgButtonActionPerformed(evt);
            }
        });

        ImageLabel.setText("         ");

        displayVisitIDLabel.setText("Visit ID :");

        javax.swing.GroupLayout DisplayVisitsLayout = new javax.swing.GroupLayout(DisplayVisits);
        DisplayVisits.setLayout(DisplayVisitsLayout);
        DisplayVisitsLayout.setHorizontalGroup(
            DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayVisitsLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(BestFeatureFoodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(MostVisitedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(springButton, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DisplayVisitsLayout.createSequentialGroup()
                        .addComponent(searchbyYearLabel)
                        .addGap(40, 40, 40)
                        .addComponent(searchByYearField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SearchByYearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addComponent(RefreshButton)
                .addGap(58, 58, 58))
            .addGroup(DisplayVisitsLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(EditVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DisplayVisitsLayout.createSequentialGroup()
                        .addComponent(displayVisitIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(displayVisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(DisplayImgButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36)
                .addComponent(ImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        DisplayVisitsLayout.setVerticalGroup(
            DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayVisitsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DisplayVisitsLayout.createSequentialGroup()
                        .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchByYearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchbyYearLabel)
                            .addComponent(RefreshButton))
                        .addGap(19, 19, 19)
                        .addComponent(SearchByYearButton))
                    .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BestFeatureFoodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MostVisitedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(springButton, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DisplayVisitsLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(EditVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DisplayVisitsLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DisplayVisitsLayout.createSequentialGroup()
                                .addGroup(DisplayVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(displayVisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(displayVisitIDLabel))
                                .addGap(29, 29, 29)
                                .addComponent(DisplayImgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        ShareVisit.addTab("Display Visit", DisplayVisits);

        countryLabel.setText("Country  :");

        cityLabel.setText("City :");

        yearLabel.setText("Year :");

        ratingLabel.setText("Rating :");

        featureLabel.setText("Feature :");

        commentLabel.setText("Comment :");

        seasonLabel.setText("Season :");

        AddVisitButton.setText("Add Visit");
        AddVisitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddVisitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddVisitLayout = new javax.swing.GroupLayout(AddVisit);
        AddVisit.setLayout(AddVisitLayout);
        AddVisitLayout.setHorizontalGroup(
            AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddVisitLayout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(commentLabel)
                    .addGroup(AddVisitLayout.createSequentialGroup()
                        .addComponent(countryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(countryField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddVisitLayout.createSequentialGroup()
                        .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearLabel)
                            .addComponent(seasonLabel)
                            .addComponent(ratingLabel)
                            .addComponent(featureLabel)
                            .addComponent(cityLabel))
                        .addGap(252, 252, 252)
                        .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(seasonField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(commentField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ratingField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(featureField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(AddVisitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(371, Short.MAX_VALUE))
        );
        AddVisitLayout.setVerticalGroup(
            AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddVisitLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countryLabel)
                    .addComponent(countryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seasonLabel)
                    .addComponent(seasonField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(featureLabel)
                    .addComponent(featureField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ratingLabel)
                    .addComponent(ratingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentLabel)
                    .addComponent(commentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addComponent(AddVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        ShareVisit.addTab("Add Visit", AddVisit);

        VisitIDLabel.setText("Visit ID :");

        DeleteVisitButton.setText("Delete Visit");
        DeleteVisitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteVisitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DeleteVisitLayout = new javax.swing.GroupLayout(DeleteVisit);
        DeleteVisit.setLayout(DeleteVisitLayout);
        DeleteVisitLayout.setHorizontalGroup(
            DeleteVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteVisitLayout.createSequentialGroup()
                .addContainerGap(398, Short.MAX_VALUE)
                .addComponent(VisitIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(364, 364, 364))
            .addGroup(DeleteVisitLayout.createSequentialGroup()
                .addGap(490, 490, 490)
                .addComponent(DeleteVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DeleteVisitLayout.setVerticalGroup(
            DeleteVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteVisitLayout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addGroup(DeleteVisitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VisitIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(DeleteVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        ShareVisit.addTab("Delete Visit", DeleteVisit);

        fromLabel.setText("From :");

        toLabel.setText("To :");

        sharedVisitIDLabel.setText("Visit ID :");

        shareVisitButton.setText("Share Visit");
        shareVisitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shareVisitButtonActionPerformed(evt);
            }
        });

        shareVisitTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Sender", "Sent To", "Country", "City", "Season", "Feature", "Rating"
            }
        ));
        jScrollPane2.setViewportView(shareVisitTable);

        sharedToMeButton.setText("Show Shared Visits To Me");
        sharedToMeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sharedToMeButtonActionPerformed(evt);
            }
        });

        refreshForShareButton.setText("Refresh Table");
        refreshForShareButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshForShareButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sharedToMeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshForShareButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sharedVisitIDLabel)
                                    .addComponent(toLabel)
                                    .addComponent(fromLabel))
                                .addGap(67, 67, 67)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sharedVisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fromField, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(toField, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(165, 165, 165)
                                        .addComponent(shareVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(42, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sharedToMeButton)
                    .addComponent(refreshForShareButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sharedVisitIDLabel)
                    .addComponent(sharedVisitIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fromLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(toLabel)
                            .addComponent(toField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(126, 126, 126))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(shareVisitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))))
        );

        ShareVisit.addTab("Share Visit", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ShareVisit)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ShareVisit)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteVisitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteVisitButtonActionPerformed
        // TODO add your handling code here:
        deleteVisit();
    }//GEN-LAST:event_DeleteVisitButtonActionPerformed

    private void AddVisitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddVisitButtonActionPerformed
        // TODO add your handling code here:
        addVisit();
    }//GEN-LAST:event_AddVisitButtonActionPerformed

    private void BestFeatureFoodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BestFeatureFoodButtonActionPerformed
        // TODO add your handling code here:
        filterBestFeatureFood();
    }//GEN-LAST:event_BestFeatureFoodButtonActionPerformed

    private void SearchByYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchByYearButtonActionPerformed
        // TODO add your handling code here:
        searchByYear();
    }//GEN-LAST:event_SearchByYearButtonActionPerformed

    private void MostVisitedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostVisitedButtonActionPerformed
        // TODO add your handling code here:
        showMostVisitedCountry();
    }//GEN-LAST:event_MostVisitedButtonActionPerformed

    private void springButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_springButtonActionPerformed
        // TODO add your handling code here:
        showSpringVisits();
    }//GEN-LAST:event_springButtonActionPerformed

    private void EditVisitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditVisitButtonActionPerformed
        // TODO add your handling code here:
        new EditVisitFrame().setVisible(true);        
    }//GEN-LAST:event_EditVisitButtonActionPerformed

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        // TODO add your handling code here:
        loadVisits();
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void refreshForShareButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshForShareButtonActionPerformed
        // TODO add your handling code here:
        loadVisitsforShare();
    }//GEN-LAST:event_refreshForShareButtonActionPerformed

    private void shareVisitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shareVisitButtonActionPerformed
     try {
         // TODO add your handling code here:
         shareVisit();
     } catch (SQLException ex) {
         Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_shareVisitButtonActionPerformed

    private void sharedToMeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sharedToMeButtonActionPerformed
        // TODO add your handling code here:
        int loggedInUserID = getLoggedInUserID(); // Assume you have this method to get the logged-in user's ID
        showSharedVisitsToMe(loggedInUserID, shareVisitTable);
    }//GEN-LAST:event_sharedToMeButtonActionPerformed

    private void DisplayImgButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayImgButtonActionPerformed
        // TODO add your handling code here:
               try {
            int visitID = Integer.parseInt(displayVisitIDField.getText());
            String imagePath = "Images/location" + visitID + ".jpg"; 
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage(); 
                Image newImage = image.getScaledInstance(500, 600, Image.SCALE_SMOOTH);
                ImageLabel.setIcon(new ImageIcon(newImage));
            } else {
                JOptionPane.showMessageDialog(this, "Image not found for Visit ID: " + visitID);
                ImageLabel.setIcon(null);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Visit ID.");
        }


    }//GEN-LAST:event_DisplayImgButtonActionPerformed

    /**
     * @param args the command line arguments
     */
   
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddVisit;
    private javax.swing.JButton AddVisitButton;
    private javax.swing.JButton BestFeatureFoodButton;
    private javax.swing.JPanel DeleteVisit;
    private javax.swing.JButton DeleteVisitButton;
    private javax.swing.JButton DisplayImgButton;
    private javax.swing.JPanel DisplayVisits;
    private javax.swing.JButton EditVisitButton;
    private javax.swing.JLabel ImageLabel;
    private javax.swing.JButton MostVisitedButton;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JButton SearchByYearButton;
    private javax.swing.JTabbedPane ShareVisit;
    private javax.swing.JTextField VisitIDField;
    private javax.swing.JLabel VisitIDLabel;
    private javax.swing.JTable VisitsTable;
    private javax.swing.JTextField cityField;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField commentField;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField countryField;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JTextField displayVisitIDField;
    private javax.swing.JLabel displayVisitIDLabel;
    private javax.swing.JTextField featureField;
    private javax.swing.JLabel featureLabel;
    private javax.swing.JTextField fromField;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField ratingField;
    private javax.swing.JLabel ratingLabel;
    private javax.swing.JButton refreshForShareButton;
    private javax.swing.JTextField searchByYearField;
    private javax.swing.JLabel searchbyYearLabel;
    private javax.swing.JTextField seasonField;
    private javax.swing.JLabel seasonLabel;
    private javax.swing.JButton shareVisitButton;
    private javax.swing.JTable shareVisitTable;
    private javax.swing.JButton sharedToMeButton;
    private javax.swing.JTextField sharedVisitIDField;
    private javax.swing.JLabel sharedVisitIDLabel;
    private javax.swing.JButton springButton;
    private javax.swing.JTextField toField;
    private javax.swing.JLabel toLabel;
    private javax.swing.JTextField yearField;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables

    private int getLoggedInUserID() {
        return userId;
    }
};

