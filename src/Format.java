import javax.swing.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.lang.Integer.parseInt;

public class Format {

    public boolean isNumberFormat (TModel tm, JTextField jtf1) {
        boolean good = false;
        try{
            for (int i = 0; i < tm.getRowCount(); i ++) {
                System.out.println(i);
                if (tm.getValueAt(i, 0).equals(jtf1.getText())) {
                    JOptionPane.showMessageDialog(null, "Nem egyedi azonosító!", "Program  üzenet", 0);
                    return false;
                }
            }
            parseInt(jtf1.getText());
            good = true;
        } catch (NumberFormatException ee) {
            JOptionPane.showMessageDialog(null, "Érvénytelen formátum!", "Program  üzenet", 0);
        }

        return good;
    }

    public boolean isDateFormat (JTextField jtf5) {

        String[] parts =  jtf5.getText().split("\\.");
        if (jtf5.getText().matches("([0-9]{4}).([0-9]{2}).([0-9]{2})") && Integer.parseInt(parts[0]) > 1000 && Integer.parseInt(parts[1]) < 13 && Integer.parseInt(parts[2]) < 32) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Hibás formátum!", "Program  üzenet", 0);
            System.out.println(
                    "hibás formátum"
            );
        }

        return false;
    }

}
