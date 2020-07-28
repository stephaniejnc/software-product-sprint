// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Objects; 
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.json.simple.JSONObject;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")

public class DataServlet extends HttpServlet {

  private class Comment {
        String commentText;
        String email;
    }

  // gets data from server
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    ArrayList<Comment> comments = new ArrayList<>();

    // load comment entities
    Query query = new Query("UpdatedComment").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      Comment comment = new Comment();
      String commentText = (String) entity.getProperty("comment");
      String email = (String) entity.getProperty("email");
      comment.commentText = commentText;
      comment.email = email;
      comments.add(comment);
    }

    // convert array list to JSON
    String json = convertToJsonUsingGson(comments);

    // Send the JSON as the response
    response.setContentType("text/html;");
    response.getWriter().println(json);
  }

   /**
   * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(ArrayList<Comment> list) {
    Gson gson = new Gson();
    String json = gson.toJson(list);
    return json;
  }

  // pushes data onto server
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // this will be executed after the user clicks the "Submit" button

    // getting user comment and populating comments data structure
    long timestamp = System.currentTimeMillis();
    String comment = getParameter(request, "text-input", "");
    // comments.add(comment);

    // create new Entity instance of kind UpdatedComment
    Entity commentEntity = new Entity("UpdatedComment");
    // get email from current user
    UserService userService = UserServiceFactory.getUserService();
    String email;

    if (userService.isUserLoggedIn()) {
      email = userService.getCurrentUser().getEmail();
    } else {
      email = "";
    }

    commentEntity.setProperty("comment", comment);
    commentEntity.setProperty("timestamp", timestamp);
    commentEntity.setProperty("email", email);

    // put entity into Datastore
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    // send back to home page
    response.sendRedirect("/index.html");
  }

  private String getUserComment(HttpServletRequest request) {
    // gets user input from the form
    return request.getParameter("comment");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}


