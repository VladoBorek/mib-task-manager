package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;

import java.util.Objects;

public class TimeUnit extends Entity {
    private String name;
    private String shortName;
    private Integer rate;

    /**
     * Creates a custom TimeUnit
     *
     * @param name name of the unit
     * @param rate conversion rate to the base time unit
     */
    public TimeUnit(Long id, String name, String shortName, Integer rate) {
        super(id);
        this.name = name;
        this.shortName = shortName;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeUnit category = (TimeUnit) o;
        return Objects.equals(id, category.id);
    }
}
