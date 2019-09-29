package models;

import java.util.Objects;

public class News {
    private int id;
    private String news;
    private int department_id;

    public News(String news, int department_Id) {
        this.news = news;
        this.department_id = department_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public int getDepartment_Id() {
        return department_id;
    }

    public void setDepartment_id(int department_Id) {
        this.department_id = department_Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news1 = (News) o;
        return id == news1.id &&
                department_id == news1.department_id &&
                Objects.equals(news, news1.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, news, department_id);
    }
}