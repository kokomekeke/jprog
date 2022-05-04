import javax.swing.*;
import java.sql.*;

public class DbMethods {
    private Statement s = null;
    private Connection c = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public void reg (){
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Sikeres driver regisztráció");
        } catch (ClassNotFoundException e) {
            System.out.println("Hibas driver regisztráció!" + e.getMessage());
        }
    }

    public void connect () {
        try {
            String url = "jdbc:sqlite:C:/sqlite/adatok.db";
            c = DriverManager.getConnection(url);
            System.out.println("Connection OK!");
        } catch (SQLException e) {
            System.out.println("JDBC Connect: " + e.getMessage());
        }
    }

    public void disconnect () {
        try {
            c.close();
            System.out.println("Disconnection OK!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean insertButor (int butor_id, String meret, String szin, String anyag, String date) {
        boolean ok = false;
        String sqlp = "insert into butor values("+butor_id+",'"+meret+"','"+szin+"','"+anyag+ "','"+date+ "')";

        try {
            s = c.createStatement();
            s.execute(sqlp);
            System.out.println("Insert OK!");
            ok = true;
        } catch (SQLException ee) {
            System.out.println("JDBC insert: " + ee.getMessage());
            JOptionPane.showMessageDialog(null, "SQL hiba!", "Program  üzenet", 0);
        }

        return ok;
    }

    public boolean modifyButor (String item, String newText, String kod) {
        boolean ok = false;
        connect();
        String sqlp = "update butor set "+item+"=? where butor_id=?";
        try{
            ps = c.prepareStatement(sqlp);
            ps.setString(1, newText);
            ps.setInt(2, Integer.parseInt(kod));
            ps.executeUpdate();
            System.out.println(item + " | " + newText + " | " + kod);
            System.out.println("Modify OK!");
            ok = true;
        } catch (SQLException ee) {
            System.out.println("JDBC insert: " + ee.getMessage());
            JOptionPane.showMessageDialog(null, "SQL hiba!", "Program  üzenet", 0);
        }
        return ok;
    }

    public boolean modifyButor1 (String item, String newText, String kod) {
        boolean ok = false;
        connect();
        String sqlp = "update butor set "+item+"='"+newText+"' where butor_id='"+kod +"'";
        try{
            s = c.createStatement();
            s.execute(sqlp);
            System.out.println("Modify OK!");
            ok = true;
        } catch (SQLException ee) {
            System.out.println("JDBC insert: " + ee.getMessage());
            JOptionPane.showMessageDialog(null, "SQL hiba!", "Program  üzenet", 0);

        }
        return ok;
    }

    public boolean deleteButor (int kod) {
        boolean ok = false;
        String sqlp = "delete from butor where butor_id='"+kod+"'";
        System.out.println("kod"+kod);
        try {
            s = c.createStatement();
            s.executeUpdate(sqlp);
            System.out.println("Insert OK!");
            ok = true;
        } catch (SQLException ee) {
            System.out.println("JDBC insert: " + ee.getMessage());
            JOptionPane.showMessageDialog(null, "SQL hiba!", "Program  üzenet", 0);

        }

        return ok;
    }
}
