/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545project;

import java.sql.Connection;
import java.util.ArrayList;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import java.util.List;
/**
 *
 * @author Tyler
 */
public class Recipe {
    private string id;
    private String name;
    private String category;
    private String instruction;
    public List<String> ingriedents;
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    public Recipe(String id, String name, String category, String instruction, List<String> ingriedients){
        this.id = id;
        this.name = name;
        this.category = category;
        this.instruction = instruction;
        this.ingriedents = ingriedients;
    }
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public List<String> getIngriedents(){
        return this.ingriedents;
    }
    public String getInstructions(){
        return this.instruction;
    }
    public String getCategory(){
        return this.category;
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
    
    public  List<Recipe> searchByCategory(String c){
        conn = ConnectDb.setupConnection();
        List<Recipe> recipes = new ArrayList<Recipe>();
        try
        {
            String sqlStatement = "select name, category from recipe where category=?";
            // Do we want to print the category along with this considering this is what we searched by.
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, c);
            
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next())
            {
                
                recipes.add(new Recipe(rs.getString("REC_NAME"), rs.getString("CATEGORY"),rs.getString("INSTRUCTIONS"),getIngriedents(rs.getString("REC_NAME"))));
                
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
        return recipes;
    }
    
    public List<Recipe> searchByIngredient(String i){
        conn = ConnectDb.setupConnection();
        List<Recipe> recipes = new ArrayList<Recipe>();
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
                recipes.add(new Recipe(rs.getString("REC_NAME"), rs.getString("CATEGORY"),rs.getString("INSTRUCTIONS"),getIngriedents(rs.getString("REC_NAME"))));
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
        return recipes;
    }
   
    public List<String> getIngriedents(String recipe){
        List<String> ingriendents = new ArrayList<String>();
        conn = ConnectDb.setupConnection();
        try{ 
            String sqlStatement = "select ingredient.name from (recipe natural join usesingredient natural join ingredient) where recipet.name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, recipe);
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
}
