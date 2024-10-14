package cz.muni.fi.pv168.project.model;

import java.awt.*;

/**
 * The Category class represents a task category with a name and a color.
 */
public class Category {
    private String name;
    private Color color;

    /**
     * Constructs a new Category object with a specified name and color.
     *
     * @param name  the name of the category.
     * @param color the color associated with the category.
     */
    public Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }
}
