package cz.muni.fi.pv168.project.ui.actions.menu;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

/**
 * @author Maroš Pavlík
 */
public class ResetFilterAction extends AbstractAction {
    Map<Boolean, List<JCheckBox>> resetValuesCheckboxes;
    JComboBox<Either<SpecialFilterCategoryValues, Category>> categoryComboBox;
    List<DatePicker> datePickers;

    public ResetFilterAction(Map<Boolean, List<JCheckBox>> resetValuesCheckboxes,
                             JComboBox<Either<SpecialFilterCategoryValues, Category>> categoryComboBox,
                             List<DatePicker> datePickers) {
        super("Reset filters", Icons.RESET_ICON);
        this.resetValuesCheckboxes = resetValuesCheckboxes;
        this.categoryComboBox = categoryComboBox;
        this.datePickers = datePickers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetCheckboxes();
        resetComboBoxes();
        resetDatePickers();
    }

    private void resetCheckboxes() {
        for (Boolean state : resetValuesCheckboxes.keySet()) {
            for (JCheckBox checkBox : resetValuesCheckboxes.get(state)) {
                checkBox.setSelected(!state);
                checkBox.doClick();
            }
        }
    }

    public void resetComboBoxes() {
        categoryComboBox.setSelectedItem(categoryComboBox.getItemAt(0));
    }

    private void resetDatePickers() {
        for (DatePicker datePicker : datePickers) {
            datePicker.setDate(null);
        }
    }
}
