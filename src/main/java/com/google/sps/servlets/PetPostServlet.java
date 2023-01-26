package com.google.sps.servlets;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.StringValue;
import com.google.sps.data.PostEntity;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@WebServlet("/pet/post")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                maxFileSize=1024*1024*10,      // 10MB
                maxRequestSize=1024*1024*50)   // 50MB

public class PetPostServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    //Information requested in the form
    String petType = Jsoup.clean(request.getParameter(PostEntity.PET_TYPE), Safelist.none()) ;
    String breed = Jsoup.clean(request.getParameter(PostEntity.BREED), Safelist.none());
    String age = Jsoup.clean(request.getParameter(PostEntity.AGE), Safelist.none());
    String location = Jsoup.clean(request.getParameter(PostEntity.LOCATION), Safelist.none());
    String name = Jsoup.clean(request.getParameter(PostEntity.NAME), Safelist.none()); 
    String email = Jsoup.clean(request.getParameter(PostEntity.EMAIL), Safelist.none()); 
    String phone = Jsoup.clean(request.getParameter(PostEntity.PHONE), Safelist.none()); 



    //Get the picPart of the data
    Part pic = request.getPart(PostEntity.PIC);
    //Convert pic to base 64 string
    byte[] byteData = IOUtils.toByteArray(pic.getInputStream());
    String picBase64 = Base64.getEncoder().encodeToString(byteData);
    //Get the timestamp of post
    long timestamp = System.currentTimeMillis();

    //Save the information in the database
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind(PostEntity.POST);
    FullEntity postEntity = 
    Entity.newBuilder(keyFactory.newKey()) 
        .set(PostEntity.PET_TYPE, petType)
        .set(PostEntity.BREED, breed)
        .set(PostEntity.AGE, age)
        .set(PostEntity.LOCATION, location)
        .set(PostEntity.PIC, StringValue.newBuilder(picBase64).setExcludeFromIndexes(true).build())
        .set(PostEntity.NAME, name)
        .set(PostEntity.EMAIL, email)
        .set(PostEntity.PHONE, phone)
        .set(PostEntity.TIMESTAMP, timestamp)
        .build();
    datastore.put(postEntity);

    //Redirect to home page
    String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length()) + request.getContextPath();
    response.sendRedirect(baseUrl);
  }
}