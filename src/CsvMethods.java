import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class CsvMethods {
    private BufferedReader in;


    public TModel csvReader (TModel tm) {
        try {

            in = new BufferedReader(new FileReader("adatok.csv"));

            String s = in.readLine();
            List<Object> a = new ArrayList<>();
            while (s != null) {
                a.clear();
                String[] st = s.split(";");
                for (int i = 0; i < st.length; i ++) {
                    a.add(st[i]);
                }
                tm.addRow(a.toArray());
                tm.fireTableDataChanged();
                s = in.readLine();
            }
            in.close();


        } catch (IOException ee) {
            System.out.println("Csv err:" + ee.getMessage());
        }

        return tm;
    }

    public void list (JTable jt) {
       if (jt.getRowCount() != 0) {
            TableColumn tc = null;
            for (int i = 0; i < jt.getColumnCount(); i++) {
                tc = jt.getColumnModel().getColumn(i);
                tc.setPreferredWidth(100);
            }

            jt.setAutoCreateRowSorter(true);
            TableRowSorter<TModel> trs =
                    (TableRowSorter<TModel>) jt.getRowSorter();

            jt.setVisible(true);
        } else System.out.println("Üres JTable");
    }

    public void insert (int butor_id, String meret, String szin, String anyag, String date) {
        String x = ";";
        try {
            PrintStream out = new PrintStream(new FileOutputStream("adatok.csv", true));
            out.println(butor_id + x + meret + x + szin + x + anyag + x + date);
            out.close();
            JOptionPane.showMessageDialog(null, "Adatok sikeresen kiirva!", "Program  üzenet", 1);
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(), "Program  üzenet", 0);
        }
    }

    public void insert (TModel tm) {
        String x = ";";
        try {
            PrintStream out = new PrintStream(new FileOutputStream("adatok.csv"));
            for (int i = 0; i < tm.getRowCount(); i ++) {

                out.println((String)tm.getValueAt(i, 0) + x + (String)tm.getValueAt(i, 1) + x + (String)tm.getValueAt(i, 2) + x + (String)tm.getValueAt(i, 3) + x + (String)tm.getValueAt(i, 4));
            }
            out.close();
            JOptionPane.showMessageDialog(null, "Adatok sikeresen kiírvaa!", "Program  üzenet", 1);
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(), "Program  üzenet", 0);
        }
    }

    public void delete (TModel tm, int j) {
        System.out.println("del: "+j);
        String x = ";";
        try {
            PrintStream out = new PrintStream(new FileOutputStream("adatok.csv"));

            for (int i = 0; i < tm.getRowCount(); i ++) {
                if (i != j) {
                    System.out.println((String)tm.getValueAt(i, 0) + x + (String)tm.getValueAt(i, 1) + x + (String)tm.getValueAt(i, 2) + x + (String)tm.getValueAt(i, 3) + x + (String)tm.getValueAt(i, 4));
                    out.println((String)tm.getValueAt(i, 0) + x + (String)tm.getValueAt(i, 1) + x + (String)tm.getValueAt(i, 2) + x + (String)tm.getValueAt(i, 3) + x + (String)tm.getValueAt(i, 4));
                } //else {
                tm.deleteRow(j);
                tm.fireTableRowsDeleted(j,j);
                //}
            }
            //tm.deleteRow(tm.rowOf(String.valueOf(j)));
            //tm.fireTableRowsDeleted(tm.rowOf(String.valueOf(j)),tm.rowOf(String.valueOf(j)));
            out.close();
            JOptionPane.showMessageDialog(null, "Adatok sikeresen torolve!", "Program  üzenet", 1);
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(), "Program  üzenet", 0);
        } catch (IndexOutOfBoundsException ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(), "Program  üzenet", 0);

        }

        //System.out.println(tm.getRowCount());
        //tm.deleteRow(j);
        //tm.fireTableRowsDeleted(j,j);

    }


}
