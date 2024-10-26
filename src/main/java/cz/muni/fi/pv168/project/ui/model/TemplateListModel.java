package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Template;

import javax.swing.*;
import java.util.List;

public class TemplateListModel extends AbstractListModel<Template> {

    private final List<Template> templateList;

    public TemplateListModel(List<Template> templateList) {
        this.templateList = templateList;
    }

    @Override
    public int getSize() {
        return templateList.size();
    }

    @Override
    public Template getElementAt(int index) {
        return templateList.get(index);
    }

    public Template[] toArray() {
        return templateList.toArray(new Template[0]);
    }

    public void addTemplate(Template template) {
        templateList.add(template);
    }

    public void removeTemplate(Template template) {
        templateList.remove(template);
    }
}
