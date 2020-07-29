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

import org.json.simple.JSONObject;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns the login status of the user */
@WebServlet("/loginstatus")

// originally adapted from example

public class LoginStatus extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String query_string = request.getQueryString();

    // create instance of userservice
    UserService userService = UserServiceFactory.getUserService();

    if (query_string != null) {
        response.setContentType("application/json");
        JSONObject object = new JSONObject();
        object.put("boolean", userService.isUserLoggedIn());
        response.getWriter().println(object);
        return;
    }    

    response.setContentType("text/html");

    // if user is logged in, show email greeting
    // else if user is not logged in, show generic greeting
    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/";
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

      response.getWriter().println("<p style=\"color:red;\">Hello " + userEmail + "!</p>");
      response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");

    } else {
      String urlToRedirectToAfterUserLogsIn = "/";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

      response.getWriter().println("<p>Hello stranger.</p>");
      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }

  }

}


