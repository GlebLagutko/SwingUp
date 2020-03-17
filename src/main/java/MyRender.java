import javax.swing.*;
import java.awt.*;

class MyRender implements ListCellRenderer<Country>{
    @Override
    public Component getListCellRendererComponent(
            JList<? extends Country> list, Country value, int index,
            boolean isSelected, boolean cellHasFocus) {
        Icon iconFlag = value.getFlag();
        String name = value.getName();
        JLabel label = new JLabel(name , iconFlag, JLabel.LEFT);
        if(isSelected){
            label.setBackground(Color.BLUE);
            label.setForeground(Color.RED);
        }
        return label;
    }
}