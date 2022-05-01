/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545project;

import java.sql.Connection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
/**
 *
 * @author Tyler
 */
public class Recipe {
    
    private String name;
    private String category;
    
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    public Recipe(String name, String category){
        this.name = name;
        this.category = category;
    }
    
    public void saveToDatabase(){
        conn = ConnectDb.setupConnection();
        try
        {
            String sqlStatement = "insert into ingredient(name, category) values(?, ?)";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, this.name);
            pst.setString(2, this.category);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }
    }
    
    public void searchByCategory(String c){
        conn = ConnectDb.setupConnection();
        try
        {
            String sqlStatement = "select name, category from recipe where category=?";
            // Do we want to print the category along with this considering this is what we searched by.
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, c);
            
            rs = (OracleResultSet) pst.executeQuery();
           System.out.println("Recipes returned:");
            while (rs.next())
            {
                String rec_name = rs.getString("REC_NAME");
                String cat = rs.getString("CATEGORY");

                // print names and prices and left align them
                // Same point as above, are we wanting to print out the category along with this considering this
                // is what the user searched for in the first place. Possibly a place of redundancy.
                System.out.printf("%-32s%%-32s", rec_name, cat);
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
    }
    
    public void searchByIngredient(String i){
        conn = ConnectDb.setupConnection();
        try
        {
            String sqlStatement = "select recipe.name, category from (recipe natural join usesingredient natural join ingredient) where ingredient.name=?";
            // Do we just want the recipe and category here? Are we also wanting to include the associated ingredients with these, or the instructions?
            // What about days of these meals? Are we wanting to give the user information on when this meal is scheduled for?
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, i);
            
            rs = (OracleResultSet) pst.executeQuery();
           System.out.println("Recipes returned:");
            while (rs.next())
            {
                String rec_name = rs.getString("REC_NAME");
                String cat = rs.getString("CATEGORY");

                // print names and prices and left align them
                System.out.printf("%-32s%%-32s", rec_name, cat);
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
    }
}
