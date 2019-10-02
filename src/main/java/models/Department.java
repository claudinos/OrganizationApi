package models;

import org.sql2o.Connection;

import java.util.Objects;

public class Department {
    private String name;
    private String description;
    private int no_ofemployees
            ;
    private int id;

    public Department(String name, String description, int no_ofemployees
    ) {
        this.name = name;
        this.description = description;
        this.no_ofemployees = no_ofemployees;
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

    public int getNo_ofemployees() {
        return no_ofemployees;
    }

    public void setNo_ofemployees(int no_ofemployees) {
        this.no_ofemployees = no_ofemployees;
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
        return no_ofemployees == that.no_ofemployees &&
                id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, no_ofemployees, id);
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO  departments(name, description,no_ofemployees) VALUES (:name, :description, :no_ofemployees)";
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name", this.name)
                    .addParameter("description", this.description)
                    .addParameter("no_ofemployees", this.no_ofemployees)
                    .executeUpdate()
                    .getKey();

        }

    }
}
