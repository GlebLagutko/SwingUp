import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class MyLabel implements ListCellRenderer<Country>{
    @Override
    public Component getListCellRendererComponent(
            JList<? extends Country> list, Country value, int index,
            boolean isSelected, boolean cellHasFocus) {
        Icon iconFlag = value.getFlag();
        String name = value.getName();
        JLabel label = new JLabel(name , iconFlag, JLabel.LEFT);
        return label;
    }
}