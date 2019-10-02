package models;

import org.sql2o.Connection;

import java.util.Objects;

public class Users {
    private String name;
    private String position;
    private String roles;

    private int id;

    public Users(String name,String position,String roles) {
        this.name = name;
        this.position = position;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return id == users.id &&
                Objects.equals(name, users.name) &&
                Objects.equals(position, users.position) &&
                Objects.equals(roles, users.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, roles, id);
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO  users(name, position,roles) VALUES (:name, :position, :roles)";
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name", this.name)
                    .addParameter("position", this.position)
                    .addParameter("roles", this.roles)
                    .executeUpdate()
                    .getKey();

        }

    }
}
