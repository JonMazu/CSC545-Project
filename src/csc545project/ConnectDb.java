/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545project;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Tyler
 */
public class ConnectDb {
    public static Connection setupConnection()
    {
        /*
         Specify the database you would like to connect with:
         - protocol (e.g., jdbc)
         - vendor (e.g., oracle)
         - driver (e.g., thin)
         - server (e.g., 157.89.28.31)
         - port number (e.g., default 1521)
         - database instance name (e.g., cscdb)
         */
        
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String jdbcUrl = "jdbc:oracle:thin:@157.89.28.31:1521:cscdb";  // URL for the database
        String username = "yourUsername";
        String password = "yourPassword";

        /*
         Specify the user account you will use to connect with the database:
         - user name (e.g., myName)
         - password (e.g., myPassword)
         */
        /*
        try{
            Scanner creds = new Scanner(new File("./pass.ini"));
            while(creds.hasNextLine()){
                String option = creds.nextLine();
                if(option.indexOf("username:")!= -1){
                    username = option.substring((option.indexOf(":")+1));
                }
                if(option.indexOf("pass:")!= -1){
                    password = option.substring((option.indexOf(":")+1));
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
*/
        
        try
        {
            // Load jdbc driver.            
            Class.forName(jdbcDriver);
            
            // Connect to the Oracle database
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    static void close(Connection conn) 
    {
        if(conn != null) 
        {
            try
            {
                conn.close();
            }
            catch(Throwable whatever)
            {}
        }
    }

    static void close(OraclePreparedStatement st)
    {
        if(st != null)
        {
            try
            {
                st.close();
            }
            catch(Throwable whatever)
            {}
        }
    }

    static void close(OracleResultSet rs)
    {
        if(rs != null)
        {
            try
            {
                rs.close();
            }
            catch(Throwable whatever)
            {}
        }
    }
}
