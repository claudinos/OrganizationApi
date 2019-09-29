package dao;

import models.Department;
import models.Users;

import java.util.List;

public interface UsersDao {
    void add(Users users);
    void addUsersToDepartment(Users users, Department department);

    List<Users> getAll();
    List<Department>getAllDepartmentsForUsers(int id);

    void deleteById(int id);
    void clearAll();

    Users findById(int users_id);
}
