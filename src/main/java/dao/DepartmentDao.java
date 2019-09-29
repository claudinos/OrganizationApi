package dao;

import models.Department;
import models.Users;

import java.util.List;

public interface DepartmentDao {

    void add(Department department);
    void addDepartmentToUser(Department department, Users users);

    List<Department> getAll();
    List<Users> getAllUsersInDepartment(int department_id);

    Department findById(int id);

    void update(int id, String name, String description, int  no_OfEmployees);

    void deleteById(int id);
    void clearAll();
}