package models;

import org.sql2o.Connection;

import java.util.Objects;

public class News {
    private int id;
    private String news;
    private int department_id;

    public News(String news, int department_id) {
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

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
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

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO  news(news, department_id) VALUES (:news, :department_id)";
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("news", this.news)
                    .addParameter("department_id", this.department_id)
                    .executeUpdate()
                    .getKey();

        }

    }

}