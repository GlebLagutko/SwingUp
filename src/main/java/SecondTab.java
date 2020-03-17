import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SecondTab extends JPanel {

    private List<Country> countries = new ArrayList<>();
    boolean selected = false;
    private JTable table;
    private DefaultTableModel tableModel;
    private String[] namesOfColumns = {"Country", "Description", "Price", "Activate trip"};

    private JTextField country = new JTextField();
    private JTextField description = new JTextField();
    private JTextField price = new JTextField();


    public SecondTab(List<Country> countries) {
        this.countries = countries;
        JPanel panelForTextFields = new JPanel(new GridLayout(3, 4));
        JButton calcSum = createCalcSumButton();
        JButton addButton = createAddButton();

        panelForTextFields.add(new JLabel("Country:"));
        panelForTextFields.add(new JLabel("Description:"));
        panelForTextFields.add(new JLabel("Price:"));
        panelForTextFields.add(country);
        panelForTextFields.add(description);
        panelForTextFields.add(price);
        panelForTextFields.add(addButton);
        panelForTextFields.add(calcSum);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(1000, 600));
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        tableModel = new DefaultTableModel(namesOfColumns, 0) {
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            public boolean isCellEditable(int row, int column) {
                if (row == 0)
                    return false;
                else
                    return super.isCellEditable(row, column);
            }
        };


        tableModel.addTableModelListener(e -> {
                    if (!selected) {
                        int sum = 0;
                        for (int i = 1; i < tableModel.getRowCount(); i++) {
                            if ((boolean) tableModel.getValueAt(i, 3)) {
                                sum += (int) tableModel.getValueAt(i, 2);
                            }
                        }
                        selected = true;
                        tableModel.setValueAt(sum, 0, 2);
                        selected = false;
                    }
                }
        );
        tableModel.addRow(new Object[]{new ImageIcon(), "Summa : ", 0, false});

        table.setModel(tableModel);
        //  this.add(table);
        this.setSize(new Dimension(600, 600));
        this.add(panelForTextFields);
        this.add(scrollPane);
        //this.add(list);
    }

    private JButton createAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                int priceField = Integer.valueOf(price.getText());
                String countryName = country.getText();
                if (countries.stream().anyMatch(c -> c.getName().equals(countryName))) {
                    JOptionPane.showMessageDialog(this, "Already exists");
                } else {
                    Country cntr = new Country(countryName);
                    countries.add(cntr);
                    Object[] obj = new Object[]{cntr.getFlag(), description.getText(), priceField, false};
                    tableModel.insertRow(tableModel.getRowCount(), obj);
                }
            } catch (NumberFormatException exc) {
                JOptionPane.showMessageDialog(null, exc.getMessage());
            }
        });
        return addButton;
    }

    private JButton createCalcSumButton() {
        JButton calcSum = new JButton("Calc summa");
        calcSum.addActionListener(e -> {
            int sum = 0;
            for (int i = 1; i < tableModel.getRowCount(); i++) {
                if ((boolean) tableModel.getValueAt(i, 3)) {
                    sum += (int) tableModel.getValueAt(i, 2);
                }
            }
            if (sum != 0) {
                tableModel.setValueAt(sum, 0, 2);
            }
        });
        return calcSum;
    }

    public void update() {
        for (Country country : countries) {
            tableModel.insertRow(tableModel.getRowCount(), (new Object[]{country.getFlag(), country.getName() + " " + country.getCapital(),
                    country.calcPrice(), false}));
        }
    }
}
