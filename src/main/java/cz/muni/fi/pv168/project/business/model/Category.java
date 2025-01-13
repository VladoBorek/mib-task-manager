package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.utils.CustomColor;

import java.util.Objects;

/**
 * The Category class represents a task category with a name and a color.
 */
public class Category extends Entity {
    private String name;
    private CustomColor color;

    /**
     * Constructs a new Category object with a specified name and color.
     *
     * @param name  the name of the category.
     * @param color the color associated with the category.
     */
    public Category(Long id, String name, CustomColor color) {
        super(id);
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomColor getColor() {
        return color;
    }

    public void setColor(CustomColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }
}
