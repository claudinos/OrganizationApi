package models;

import java.util.Objects;

public class Department {
    private String name;
    private String description;
    private int no_OfEmployees;
    private int id;

    public Department(String name, String description, int no_OfEmployees) {
        this.name = name;
        this.description = description;
        this.no_OfEmployees = no_OfEmployees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNo_OfEmployees() {
        return no_OfEmployees;
    }

    public void setNo_OfEmployees(int no_OfEmployees) {
        this.no_OfEmployees = no_OfEmployees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return no_OfEmployees == that.no_OfEmployees &&
                id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, no_OfEmployees, id);
    }
}
