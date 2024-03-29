import com.google.gson.Gson;
import dao.NewsDao;
import exception.ApiException;
import models.Department;
import models.News;
import models.Users;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Sql2oUsersDao usersDao;
        Connection clo;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/organization";
        Sql2o sql2o = new Sql2o(connectionString, "claudine", "1990");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        clo = sql2o.open();

        //CREATE
//        post("/departments/:department_id/users/:users_id", "application/json", (req, res) -> {
//            int department_id = Integer.parseInt(req.params("department_id"));
//            int users_id = Integer.parseInt(req.params("users_id"));
//            Department department = departmentDao.findById(department_id);
//            Users users = usersDao.findById(users_id);
//
//            if (department != null && users != null){
//                usersDao.addUsersToDepartment(users, department);
//                res.status(201);
//                return gson.toJson(String.format("User '%s' and Department '%s' have been associated",users.getName(), department.getName()));
//            }
//            else {
//                throw new ApiException(404, String.format("Department or Users does not exist"));
//            }
//        });
//
//        get("/departments/:id/users", "application/json", (req, res) -> {
//            int department_id = Integer.parseInt(req.params("id"));
//            Department departmentToFind = departmentDao.findById(department_id);
//            if (departmentToFind == null){
//                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
//            }
//            else if (departmentDao.getAllUsersInDepartment(department_id).size()==0){
//                return "{\"message\":\"I'm sorry, but no users were found for this department.\"}";
//            }
//            else {
//                return gson.toJson(departmentDao.getAllUsersInDepartment(department_id));
//            }
//        });
//
//        post("/departments/new", "application/json", (req, res) -> {
//            Department department = gson.fromJson(req.body(), Department.class);
//            departmentDao.add(department);
//            res.status(201);
//            return gson.toJson(department);
//        });
//
//        post("/departments/:department_id/news/new", "application/json", (req, res) -> {
//            int department_id = Integer.parseInt(req.params("department_id"));
//            News news = gson.fromJson(req.body(), News.class);
//            news.setDepartment_id(department_id); //we need to set this separately because it comes from our route, not our JSON input.
//            newsDao.add(news);
//            res.status(201);
//            return gson.toJson(news);
//        });
//
//
//        get("/departments", "application/json", (req, res) -> {
//            return gson.toJson(departmentDao.getAll());
//        });
//
//        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
//            int department_id = Integer.parseInt(req.params("id"));
//            return gson.toJson(departmentDao.findById(department_id));
//        });
//
//        get("/departments/:id/news", "application/json", (req, res) -> {
//            int department_id = Integer.parseInt(req.params("id"));
//            Department departmentToFind = departmentDao.findById(department_id);
//            List<News> allNews;
//            if (departmentToFind == null){
//                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
//            }
//            allNews = newsDao.getAllNewsByDepartment(department_id);
//            return gson.toJson(allNews);
//        });
//
//
//        get("/users", "application/json", (req, res) -> {
//            return gson.toJson(usersDao.getAll());
//        });
//
//        post("/users/new", "application/json", (req, res) -> {
//            Users users = gson.fromJson(req.body(), Users.class);
//            usersDao.add(users);
//            res.status(201);
//            return gson.toJson(users);
//        });
//
//
//        exception(ApiException.class, (exception, req, res) -> {
//            ApiException err = exception;
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("status", err.getStatusCode());
//            jsonMap.put("errorMessage", err.getMessage());
//            res.type("application/json");
//            res.status(err.getStatusCode());
//            res.body(gson.toJson(jsonMap));
//        });
//
//        after((req, res) ->{
//            res.type("application/json");
//        });
        get("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "dep_form.hbs");
        },new HandlebarsTemplateEngine());

        get("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "new_form.hbs");
        },new HandlebarsTemplateEngine());

        get("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "user_form.hbs");
        },new HandlebarsTemplateEngine());


        post("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String description = request.queryParams("description");
            int no_ofemployees = Integer.parseInt(request.queryParams("no_ofemployees"));
            Department newDepartment = new Department(name, description, no_ofemployees);
            newDepartment.save();
            request.session().attribute("item",newDepartment);
            model.put("item",request.session().attribute("item"));
            model.put("name", name);
            model.put("description", description);
            model.put("no_ofemployees", no_ofemployees);
            System.out.println(newDepartment);
            return new ModelAndView(model, "department.hbs");
        }, new HandlebarsTemplateEngine());

        get("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "department.hbs");
        },new HandlebarsTemplateEngine());
        get("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "user.hbs");
        },new HandlebarsTemplateEngine());
        post("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String position = request.queryParams("position");
            String roles = request.queryParams("roles");
            Users newUser = new Users(name, position, roles);
            newUser.save();
            request.session().attribute("item",newUser);
            model.put("item",request.session().attribute("item"));
            model.put("name", name);
            model.put("position", position);
            model.put("roles", roles);
            System.out.println(newUser);
            return new ModelAndView(model, "user.hbs");
        }, new HandlebarsTemplateEngine());
        get("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "news-form.hbs");
        },new HandlebarsTemplateEngine());
        post("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String news = request.queryParams("news");
            int department_id = Integer.parseInt(request.queryParams("department_id"));
            News newNews = new News(news, department_id);
            newNews.save();
            request.session().attribute("item",newNews);
            model.put("item",request.session().attribute("item"));
            model.put("news", news);
            model.put("department_id", department_id);
            return new ModelAndView(model, "news.hbs");
        }, new HandlebarsTemplateEngine());
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        },new HandlebarsTemplateEngine());

    }
}
