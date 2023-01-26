package com.google.sps.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import com.google.sps.data.PostEntity;
import com.google.sps.data.Post;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html;");
    //request.getRequestDispatcher("../webapp/index.html").forward(request, response); 
    
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    Query<Entity> query =
        Query.newEntityQueryBuilder().setKind(PostEntity.POST).setOrderBy(OrderBy.desc(PostEntity.TIMESTAMP)).build();
    QueryResults<Entity> results = datastore.run(query);

    List<Post> posts = new ArrayList<>();
    while (results.hasNext()) {
      Entity entity = results.next();
      String petType = entity.getString(PostEntity.PET_TYPE);
      String breed = entity.getString(PostEntity.BREED);
      String age = entity.getString(PostEntity.AGE);
      String location = entity.getString(PostEntity.LOCATION);
      String name = entity.getString(PostEntity.NAME);
      String email = entity.getString(PostEntity.EMAIL);
      String phone = entity.getString(PostEntity.PHONE);
      String pic = entity.getString(PostEntity.PIC);

      Post post = new Post(petType, breed, age, location, name, email, phone, pic);
      posts.add(post);
    }

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(posts));
  }
}
