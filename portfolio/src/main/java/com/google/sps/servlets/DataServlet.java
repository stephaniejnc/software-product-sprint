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

  ArrayList<String> list = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    list.add("This is a string inside DataServlet.java");
    list.add("The doGet function returns this array as a JSON string!");
    list.add("script.js fetches the JSON string from the server.");

    // response.setContentType("text/html;");
    // response.getWriter().println("Hello Stephanie!");

    // Convert the hard-coded arraylist to JSON
    String json = convertToJsonUsingGson(list);

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
}


