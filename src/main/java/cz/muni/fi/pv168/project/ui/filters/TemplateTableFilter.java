package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatchers;
import cz.muni.fi.pv168.project.ui.filters.matchers.entityWithCategory.EntityCategoryMatcher;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.table.TableRowSorter;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EmployeeTable.
 */
public final class TemplateTableFilter {
    private final TemplateCompoundMatcher templateCompoundMatcher;

    public TemplateTableFilter(TableRowSorter<TemplateTableModel> rowSorter) {
        templateCompoundMatcher = new TemplateCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(templateCompoundMatcher);
    }

    public void filterCategory(Either<SpecialFilterCategoryValues, Category> selectedItem) {
        selectedItem.apply(
                l -> templateCompoundMatcher.setCategoryMatcher((EntityMatcher<Template>) l.getMatcher()),
                r -> templateCompoundMatcher.setCategoryMatcher(new EntityCategoryMatcher<Template>(r))
        );
    }

    /**
     * Container class for all matchers for the EmployeeTable.
     * <p>
     * This Matcher evaluates to true, if all contained {@link EntityMatcher} instances
     * evaluate to true.
     */
    private static class TemplateCompoundMatcher extends EntityMatcher<Template> {

        private final TableRowSorter<TemplateTableModel> rowSorter;
        private EntityMatcher<Template> categoryMatcher = EntityMatchers.all();

        private TemplateCompoundMatcher(TableRowSorter<TemplateTableModel> rowSorter) {
            this.rowSorter = rowSorter;
        }

        private void setCategoryMatcher(EntityMatcher<Template> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
        }

        @Override
        public boolean evaluate(Template template) {
            return Stream.of(categoryMatcher)
                    .allMatch(m -> m.evaluate(template));
        }
    }
}
