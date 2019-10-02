package dao;

import models.Department;
import models.News;

import java.util.List;

public interface NewsDao {
    void add(News news);
    void addNewsToDepartment(News news, Department department);

    List<News> getAll();
    List<News>getAllNewsByDepartment(int department_id);

    void deleteById(int id);
    void clearAll();
}

