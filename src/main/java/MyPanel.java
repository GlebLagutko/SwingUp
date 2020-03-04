import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
    private Task1 firstPanel;
    private Map<String, String> stringStringMap;

    @Override
    public JRootPane createRootPane() {
        JRootPane pane = new JRootPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        JTabbedPane tabbedPane = new JTabbedPane();
        countries = new ArrayList<>();
        paths = new ArrayList<>();
        firstPanel = new Task1(countries);
        tabbedPane.addTab("Task1", firstPanel);
        JMenu submenu = new JMenu("File");
        menuBar.add(submenu);
        JMenuItem open = new JMenuItem("Open");
        submenu.add(open);
        stringStringMap = new TreeMap<>();
        try (Scanner sc = new Scanner(new File("src\\main\\java\\input.txt"))) {
            while (sc.hasNext()){
                stringStringMap.put(sc.next(),sc.next());
            }
        } catch (Exception ex) {
         ex.printStackTrace();
        }

        open.addActionListener(e -> {
            try (PrintWriter pr = new PrintWriter(new File("output.txt"))) {
                chooser = new JFileChooser();
                chooser.setDialogTitle("Октрытие файла");
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    paths.addAll(Files.walk(Paths.get(""))
                            .filter(Files::isRegularFile)
                            .collect(Collectors.toList()).stream().filter(f -> f.toString().endsWith("png")).collect(Collectors.toList()));
                    fillCountries();
                    for (Country country : countries) {
                        pr.println(country.getName());
                    }
                    firstPanel.update();
                }
            } catch (IOException er) {
                er.printStackTrace();
            }
        });

        panel.add(menuBar, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        pane.setContentPane(panel);

        return pane;
    }

    private void fillCountries() {
        for (Path path : paths) {
            Country country = new Country(path);
            if(stringStringMap.get(country.getName())!= null){
                country.setCapital(stringStringMap.get(country.getName()));
            }
            countries.add(country);
        }
    }

}