import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Task1 extends JPanel {


    private JList<Country> list;
    private DefaultListModel<Country> listModel;
    List<Country> countries = new ArrayList<>();

    public Task1(List<Country> countries) {
        this.countries = countries;
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new MyLabel());
        JScrollPane scrollPane = new JScrollPane();
        // list.setCellRenderer(new MyLabel());
        list.setBounds(100, 100, 75, 75);
        list.addListSelectionListener(e -> {
            String str = list.getSelectedValue().getName();
            JOptionPane.showMessageDialog(this, "Capital of " + str);
        });

        scrollPane.setViewportView(list);
        scrollPane.setPreferredSize(new Dimension(250, 400));
        this.add(scrollPane);
        this.setSize(new Dimension(500, 500));
        //this.add(list);
    }

    public void update() {
        for (Country country : countries) {
            listModel.add(listModel.size(), country);
        }
    }
}
