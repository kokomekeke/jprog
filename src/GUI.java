import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static java.lang.String.valueOf;

public class GUI extends JFrame {
    private JScrollPane jsp;
    private JTable jt;
    private JButton jb1, jb2, jb3;
    private JCheckBox jcb1, jcb2;
    private JToggleButton jtb;
    private JPanel jp;
    private JFrame jf;
    private JLabel jl1, jl2, jl3, jl4, jl5;
    private JTextField jtf1, jtf2, jtf3, jtf4, jtf5;
    private CsvMethods csv = new CsvMethods();
    private boolean methodIsRunning = false;
    private TModel butorTM = new TModel(new String[]{"butor_id", "meret", "szin", "anyag"});
    private TModel megrendeloTM = new TModel(new String[]{"id", "nev", "kiszallitas_id", "butorid"});
    private  Format form = new Format();
    private DbMethods dbm = new DbMethods();

    public  GUI (TModel tm) {
        dbm.reg();
        dbm.connect();

        jt = new JTable(csv.csvReader(tm));
        jt.setFillsViewportHeight(true);
        jt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (jt.getSelectedColumn() == -1) {
                    JOptionPane.showMessageDialog(null, "Jelölje ki a módosított cellát újból!!", "Program  üzenet", 0);
                    return;
                }
                String item = tm.getColumnName(jt.getSelectedColumn());
                String newText = (String)tm.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
                int kod =  Integer.valueOf((String)tm.getValueAt(jt.getSelectedRow(), 0));

                //
                System.out.println("edit");

                if (e.getKeyCode() == KeyEvent.VK_ENTER && jt.getSelectedColumn() != -1 && dbm.modifyButor(item,newText,valueOf(kod))) {
                    System.out.println(item + " | " + newText + " | " +kod);
                    tm.setValueAt(tm.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn()), jt.getSelectedRow(), jt.getSelectedColumn());
                    csv.insert(tm);
                }
            }
        });

        jsp = new JScrollPane(jt);
        jsp.setBounds(10, 10, 350, 150);

        //csv.list(jt);
        jp = new JPanel();
        jp.setLayout(null);
        jp.add(jsp);
        jp.setOpaque(true);

        jb1 = new JButton("Insert");
        jb1.setBounds(10, 180, 100, 30);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertWindowButor(tm);
            }
        });

        jb2 = new JButton("Delete");
        jb2.setBounds(259, 180, 100, 30);
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButorWindow(tm);
                System.out.println(tm.getRowCount());
            }
        });

        jp.add(jb1);
        jp.add(jb2);
        add(jp);
        setContentPane(jp);
        setLayout(null);
        setBounds(300, 300, 384, 260);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbm.disconnect();
            }
        });
    }

    public void insertWindowButor (TModel tm) {
        if ( jf != null) {
            jf.dispose();
            jf = null;
            methodIsRunning = false;
        }
        if (methodIsRunning == false) {

            methodIsRunning = true;
            jf = new JFrame();

            jl1 = new JLabel("butor_id");
            jl1.setBounds(10, 10, 100, 30);
            jl2 = new JLabel("meret");
            jl2.setBounds(10, 60, 100, 30);
            jl3 = new JLabel("szin");
            jl3.setBounds(10, 110, 100, 30);
            jl4 = new JLabel("anyag");
            jl4.setBounds(10, 160, 100, 30);
            jl5 = new JLabel("datum");
            jl5.setBounds(10, 210, 100, 30);


            jtf1 = new JTextField();
            jtf1.setBounds(140, 10, 100, 30);
            jtf2 = new JTextField();
            jtf2.setBounds(140, 60, 100, 30);
            jtf3 = new JTextField();
            jtf3.setBounds(140, 110, 100, 30);
            jtf4 = new JTextField();
            jtf4.setBounds(140, 160, 100, 30);
            jtf5 = new JTextField();
            jtf5.setBounds(140, 210, 100, 30);

            jb1 = new JButton("insert");
            jb1.setBounds(10, 270, 100, 30);
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dbm.connect();
                    if (form.isDateFormat(jtf5) && form.isNumberFormat(tm, jtf1) && dbm.insertButor(Integer.parseInt(jtf1.getText()), jtf2.getText(), jtf3.getText(), jtf4.getText(), jtf5.getText())) {
                        csv.insert(Integer.parseInt(jtf1.getText()), jtf2.getText(), jtf3.getText(), jtf4.getText(), jtf5.getText());
                        jsp.revalidate();
                        jsp.repaint();
                        dispose();
                        new GUI(new TModel(new String[]{"butor_id", "meret", "szin", "anyag", "datum"}));
                    }
                    jf.dispose();
                }
            });

            jb2 = new JButton("close");
            jb2.setBounds(140, 270, 100, 30);
            jb2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jf.dispose();
                    jf = null;
                    methodIsRunning = false;
                }
            });

            jf.add(jl1);
            jf.add(jl2);
            jf.add(jl3);
            jf.add(jl4);
            jf.add(jl5);
            jf.add(jtf1);
            jf.add(jtf2);
            jf.add(jtf3);
            jf.add(jtf4);
            jf.add(jtf5);
            jf.add(jb1);
            jf.add(jb2);
            jf.setLayout(null);
            jf.setBounds(100, 100, 300, 350);
            jf.setVisible(true);
            jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jf.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jf.dispose();
                    methodIsRunning = false;
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Mar megvan nyitva a beszuras!", "Program  üzenet", 0);
        }
    }

    public void deleteButorWindow (TModel tm) {
        if ( jf != null) {
            jf.dispose();
            jf = null;
        }
        jf = new JFrame();

        jl1 = new JLabel("Adja meg a torlendo elem sorszamat:");
        jl1.setBounds(10, 10, 230, 30);

        jtf1 = new JTextField();
        jtf1.setBounds(250, 10, 100, 30);

        jb1 = new JButton("Delete");
        jb1.setBounds(10, 80, 100, 30);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String i = jtf1.getText();
                    System.out.println("jtf1 text: " + i);
                    if (dbm.deleteButor(Integer.parseInt((String)tm.getValueAt(Integer.parseInt(i), 0)))) {
                        csv.delete(tm, Integer.parseInt(jtf1.getText()));
                        dispose();
                        new GUI(new TModel(new String[]{"butor_id", "meret", "szin", "anyag", "datum"}));
                        jf.dispose();
                    } else System.out.println("Err");
                } catch (IndexOutOfBoundsException ee) {
                    JOptionPane.showMessageDialog(null, "SQL hiba!", "Program  üzenet", 0);
                }


            }
        });

        jb2 = new JButton("Close");
        jb2.setBounds(250, 80, 100, 30);
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        jf.add(jl1);
        jf.add(jtf1);
        jf.add(jb1);
        jf.add(jb2);
        jf.setLayout(null);
        jf.setBounds(100, 100, 400, 150);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE & DISPOSE_ON_CLOSE);
    }

    public JScrollPane tablePane (TModel tm) {
        jt = null;
        jsp = null;
        jt = new JTable( csv.csvReader(tm));
        jt.setFillsViewportHeight(true);
        jt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER && jt.getSelectedColumn() != -1) {
                    System.out.println("gyaaah " + tm.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn()));
                    tm.setValueAt(tm.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn()), jt.getSelectedRow(), jt.getSelectedColumn());
                    csv.insert(tm);
                    tm.fireTableDataChanged();
                }

            }
        });

        jsp = new JScrollPane(jt);
        jsp.setBounds(10, 10, 350, 150);

        return jsp;
    }

}
