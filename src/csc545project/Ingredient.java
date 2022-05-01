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
public class Ingredient {
    
    private String name;
    private String foodGroup;
    private int calories;
    private int sugar;
    private int protein;
    private int sodium;
    private int fat;
    private boolean inFridge;
    
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    public Ingredient(String name, String foodGroup, int calories, int sugar, int protein, int sodium, int fat, boolean inFridge){
        this.name = name;
        this.foodGroup = foodGroup;
        this.calories = calories;
        this.sugar = sugar;
        this.protein = protein;
        this.sodium = sodium;
        this.fat = fat;
        this.inFridge = inFridge;
        
        this.saveToDatabase();
    }
    
    public void saveToDatabase(){
        conn = ConnectDb.setupConnection();
        try
        {
            String sqlStatement = "insert into ingredient(name, foodgroup, calories, sugar, protein, sodium, fat, infridge) values(?, ?, ?, ?, ?, ?, ?, ?)";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, this.name);
            pst.setString(2, this.foodGroup);
            pst.setInt(3, this.calories);
            pst.setInt(4, this.sugar);
            pst.setInt(5, this.protein);
            pst.setInt(6, this.sodium);
            pst.setInt(7, this.fat);
            pst.setBoolean(8, this.inFridge);
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
    
    
}
