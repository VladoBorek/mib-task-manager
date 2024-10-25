package cz.muni.fi.pv168.project.ui.actions.menu;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

/**
 * @author Maroš Pavlík
 */
public class ResetFilterAction extends AbstractAction {
    Map<Boolean, List<JCheckBox>> resetValuesCheckboxes;
    Map<JComboBox<Object>, String> resetValuesComboBoxes;
    DatePicker datePicker;

    public ResetFilterAction(Map<Boolean,List<JCheckBox>> resetValuesCheckboxes,
                             Map<JComboBox<Object>, String> resetValuesComboBoxes,
                             DatePicker datePicker){
        super("Reset filters", Icons.RESET_ICON);
        this.resetValuesCheckboxes = resetValuesCheckboxes;
        this.resetValuesComboBoxes = resetValuesComboBoxes;
        this.datePicker = datePicker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Boolean state: resetValuesCheckboxes.keySet()) {
            for (JCheckBox box: resetValuesCheckboxes.get(state)) {
                box.setSelected(state);
            }
        }
        for (JComboBox<Object> comboBox: resetValuesComboBoxes.keySet()) {
            comboBox.setEditable(true);
            comboBox.setSelectedItem(resetValuesComboBoxes.get(comboBox));
            comboBox.setEditable(false);
        }
        datePicker.setDateToToday();
        //TODO Implement filters
        System.out.println("User clicked on Reset Filters button");
    }

}
