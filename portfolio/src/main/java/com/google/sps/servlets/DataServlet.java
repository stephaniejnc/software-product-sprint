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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")

public class DataServlet extends HttpServlet {

  private ArrayList<String> comments = new ArrayList<String>();

  // gets data from server
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // convert array list to JSON
    String json = convertToJsonUsingGson(comments);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

   /**
   * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(ArrayList list) {
    Gson gson = new Gson();
    String json = gson.toJson(list);
    return json;
  }

  // pushes data onto server
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // this will be executed after the user clicks the "Submit" button

    // getting user comment and populating comments data structure
    String comment = getUserComment(request);
    comments.add(comment);

    // send back to home page
    response.sendRedirect("/index.html");
  }

  private String getUserComment(HttpServletRequest request) {
    // gets user input from the form
    return request.getParameter("comment");
  }
}


