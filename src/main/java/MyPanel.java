import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

class MyPanel extends JFrame {

    public MyPanel() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            setSize(700, 500);
            MyPanel.this.setLocationRelativeTo(null);
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    System.exit(0);
                }
            });
        });
    }

    private List<Country> countries;
    private List<Path> paths;
    private JFileChooser chooser = new JFileChooser();
    private SecondTab secondTask;
    private FirstTab firstPanel;
    private Map<String, String> stringStringMap;

    @Override
    public JRootPane createRootPane() {
        JRootPane pane = new JRootPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        countries = new ArrayList<>();
        paths = new ArrayList<>();
        JTabbedPane tabbedPane = createTabbedPane();
        JMenuBar menuBar = createMenuBar();
        initialMap();
        panel.add(menuBar, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        pane.setContentPane(panel);

        return pane;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu submenu = new JMenu("File");
        JMenuItem open = createOpenButton();
        menuBar.add(submenu);
        submenu.add(open);
        return menuBar;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        firstPanel = new FirstTab(countries);
        secondTask = new SecondTab(countries);
        tabbedPane.addTab("FirstTab", firstPanel);
        tabbedPane.addTab("SecondTab", secondTask);
        return tabbedPane;
    }

    private JMenuItem createOpenButton() {
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(e -> {
            try (PrintWriter pr = new PrintWriter(new File("output.txt"))) {
                chooser = new JFileChooser();
                chooser.setDialogTitle("Октрытие файла");
                chooser.setCurrentDirectory(new File("."));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    fillPaths(chooser.getSelectedFile().getPath());
                    fillCountries();
                    for (Country country : countries) {
                        pr.println(country.getName());
                    }
                    secondTask.update();
                    firstPanel.update();
                }
            } catch (IOException er) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });
        return open;
    }

    private void fillPaths(String path) throws IOException {
        paths.addAll(Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList()).stream().filter(f -> f.toString().endsWith("png")).collect(Collectors.toList()));
    }

    private void initialMap() {
        stringStringMap = new TreeMap<>();
        try (Scanner sc = new Scanner(new File("src\\main\\java\\input.txt"))) {
            while (sc.hasNext()) {
                stringStringMap.put(sc.next(), sc.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fillCountries() {
        for (Path path : paths) {
            Country country = new Country(path);
            if (stringStringMap.get(country.getName()) != null) {
                country.setCapital(stringStringMap.get(country.getName()));
            }
            countries.add(country);
        }
    }

}