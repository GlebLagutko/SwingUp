import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FirstTab extends JPanel {

    private JList<Country> list;
    private DefaultListModel<Country> listModel;
    private List<Country> countries;
    private JLabel label = new JLabel();

    public FirstTab(List<Country> countries) {
        this.countries = countries;
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new MyRender());
        JScrollPane scrollPane = new JScrollPane(list);
        // list.setCellRenderer(new MyRender());
        list.setBounds(100, 100, 75, 75);
        list.addListSelectionListener(e -> {
            Country country = list.getSelectedValue();
            label.setIcon(country.getFlag());
            label.setText(country.getName() + " " + country.getCapital());
        });

        scrollPane.setPreferredSize(new Dimension(250, 400));

        this.add(scrollPane);
        this.add(label);
        this.setSize(new Dimension(500, 500));
        //this.add(list);
    }

    public void update() {
        for (Country country : countries) {
            listModel.add(listModel.size(), country);
        }
    }
}
