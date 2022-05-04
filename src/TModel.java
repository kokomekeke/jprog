import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TModel extends AbstractTableModel {
    private List<Object> columnNames = new ArrayList<>();
    private List<Object[]> data = new ArrayList<>();

    public TModel (Object[] columnNames) {
        super();
        this.columnNames = Arrays.asList(columnNames);
    }

    public String getColumnName(int col) {
        return (String)columnNames.get(col);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int row, int col) {

        System.out.println("getv" + data.get(row)[col]);
        return data.get(row)[col];
    }

    public void setValueAt(Object a, int row, int col) {

        data.get(row)[col] = a;
        System.out.println(data.get(row)[col]);
        fireTableDataChanged();
    }

    public void addRow (Object[] a) {
        if (!(a.length < columnNames.size())) {
            data.add(a);
            fireTableDataChanged();
        }

    }

    public void deleteRow (int i) {
        data.remove(i);
    }

    public Class<?> getColumnClass (int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col)
    { return true; }

    public int rowOf (String value) {
        System.out.println("size:::" + data.size());
        for (int i = 0; i < data.size(); i ++) {
            if ((String)getValueAt(i, 0)==value) {
                System.out.println("rowOf: " + i);
                return i;
            }
        }
        return -1;
    }


}
