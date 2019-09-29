package dao;

import models.News;

import java.util.List;

public interface NewsDao {
    static void add(News news);
    //void addNewsToDepartment(News news, Department department);

    static List<News> getAll();
    List<News>getAllNewsByDepartment(int department_id);

    void deleteById(int id);
    void clearAll();
}

