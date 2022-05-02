/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545project;

import java.sql.Connection;
import java.sql.ResultSet;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class MealPlan {
    
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    String planID;
    ArrayList<String> list = new ArrayList<String>();
    String[] mealList;
    String[] recipeList;
    String[] ingredientList;
    String instructions = "";
    
    public String[] FillList() {
        conn = ConnectDb.setupConnection();
        try
        {
            String sqlStatement = "select planID from mealPlan";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            
            mealList = new String[list.size()];
            for(int i = 0; i < list.size(); i++) {
                mealList[i] = list.get(i);
            }
            
            return mealList;
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
        
        return mealList;
    }
    
    public String[] RecipeList(int plan) {
        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "select recipeID from containsRecipe where planID = ?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, plan);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                list.add(rs.getString(2));
            }
            
            mealList = new String[list.size()];
            for(int i = 0; i < list.size(); i++) {
                mealList[i] = list.get(i);
            }
            
            recipeList = new String[mealList.length];
            for(int i = 0; i < mealList.length; i++) {
                sqlStatement = "select name from recipe where recipeID = ?";
                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                int id = Integer.parseInt(mealList[i]);
                pst.setInt(1, id);
                
                rs = pst.executeQuery();
                while(rs.next()) {
                    recipeList[i] = rs.getString(2);
                }
            }
            
            return recipeList;
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }
        
        return recipeList;
    }
    
    public String[] ingredientList(int recipe) {
        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "select ingredientID from usesIngredient where recipeID = ?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, recipe);
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                list.add(rs.getString(2));
            }
            
            ingredientList = new String[list.size()];
            for(int i = 0; i < ingredientList.length; i++) {
                sqlStatement = "select name from ingredient where ingredientID = ?";
                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                int place = Integer.parseInt(list.get(i));
                pst.setInt(1, place);
                
                rs = pst.executeQuery();
                while(rs.next()) {
                    ingredientList[i] = rs.getString(2);
                }
            }
            return ingredientList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }
        return ingredientList;
    }
    
    public String directions(int recipe) {
        conn = ConnectDb.setupConnection();
        try {
            String sqlStatement = "select instructions from recipe where recipeID = ?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                instructions = rs.getString(4);
            }
            
            return instructions;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }
        
        return instructions;
    }
}
