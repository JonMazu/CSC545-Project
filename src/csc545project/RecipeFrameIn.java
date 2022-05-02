/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package csc545project;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import java.sql.ResultSet;

/**
 *
 * @author knux1
 */
public class RecipeFrameIn extends javax.swing.JInternalFrame {
    String[] RecipesStr; 
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    List<Recipe> recipes = new ArrayList<Recipe>();
    Recipe selectedRecipe = null;
    Ingredient SelectedFood = null;
    List<Ingredient> allFood = null;
    List<String> allFoodID = null;
    boolean inFridge = false;
    /**
     * Creates new form RecipeFrameIn
     */
    public RecipeFrameIn() {
        initComponents();
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesktopPane = new javax.swing.JDesktopPane();
        AddBTTN = new javax.swing.JButton();
        SearchBTTN = new javax.swing.JButton();

        javax.swing.GroupLayout DesktopPaneLayout = new javax.swing.GroupLayout(DesktopPane);
        DesktopPane.setLayout(DesktopPaneLayout);
        DesktopPaneLayout.setHorizontalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        DesktopPaneLayout.setVerticalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        AddBTTN.setText("Add");
        AddBTTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBTTNActionPerformed(evt);
            }
        });

        SearchBTTN.setText("Search");
        SearchBTTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBTTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DesktopPane)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(SearchBTTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 667, Short.MAX_VALUE)
                .addComponent(AddBTTN)
                .addGap(169, 169, 169))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddBTTN)
                    .addComponent(SearchBTTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(DesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchBTTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBTTNActionPerformed
        DesktopPane.removeAll();
        NewNewRecipeIn search = new NewNewRecipeIn();
        DesktopPane.add(search).setVisible(true);
    }//GEN-LAST:event_SearchBTTNActionPerformed

    private void AddBTTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBTTNActionPerformed
        DesktopPane.removeAll();
        newRecipeIn add = new newRecipeIn();
        DesktopPane.add(add).setVisible(true);
    }//GEN-LAST:event_AddBTTNActionPerformed
    public String getIngredientID(String name){
        conn = ConnectDb.setupConnection();
        String out = "";
            try{ 
                String sqlStatement = "select ingredientID from ingredient where name=?";
                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                pst.setString(1, name);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next())
                {
                    out = rs.getString("ingredientID");
                }

              }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                ConnectDb.close(rs);
                ConnectDb.close(pst);
                ConnectDb.close(conn);
            }
            return out;
    }
    public List<String> getIngriedents(String recipeID){
            List<String> ingriendents = new ArrayList<String>();
            conn = ConnectDb.setupConnection();
            try{ 
                String sqlStatement = "select name from ingredient where ingredientID in (select recipeID,ingredientID from containsRecipe where recipeID =?";
                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                pst.setString(1, recipeID);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next())
                {
                    ingriendents.add(rs.getString("ING_NAME"));
                }

              }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                ConnectDb.close(rs);
                ConnectDb.close(pst);
                ConnectDb.close(conn);
            }
              return ingriendents;
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBTTN;
    private javax.swing.JDesktopPane DesktopPane;
    private javax.swing.JButton SearchBTTN;
    // End of variables declaration//GEN-END:variables
}
