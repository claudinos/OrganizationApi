package dao;

import models.Department;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.ArrayList;
import java.util.List;

public class Sql2oUsersDao implements UsersDao {
    private final Sql2o sql2o;

    public Sql2oUsersDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users users) {
        String sql = "INSERT INTO users (name, position, roles) VALUES (:name, :position, :roles)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(users)
                    .executeUpdate()
                    .getKey();
            users.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Users> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id = :id";
        String deleteJoin = "DELETE from departments_users WHERE usersId = :usersId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            con.createQuery(deleteJoin)
                    .addParameter("usersId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Users findById(int usersId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", usersId)
                    .executeAndFetchFirst(Users.class);
        }    }

    @Override
    public void addUsersToDepartment(Users users, Department department) {
        String sql = "INSERT INTO departments_users (departmentId, usersId) VALUES (:departmentId, :usersId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("usersId", users.getId())
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAllDepartmentsForUsers(int usersId) {
        List<Department> departments = new ArrayList();
        String joinQuery = "SELECT departmentId FROM departments_users WHERE usersId = :usersId";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentIds = con.createQuery(joinQuery)
                    .addParameter("usersId", usersId)
                    .executeAndFetch(Integer.class); //what is happening in the lines above?
            for (Integer departmentId : allDepartmentIds){
                String departmentQuery = "SELECT * FROM departments WHERE id = :departmentId";
                departments.add(
                        con.createQuery(departmentQuery)
                                .addParameter("departmentId", departmentId)
                                .executeAndFetchFirst(Department.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }
}
