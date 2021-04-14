package org.geektimes.projects.user.web.controller;

import org.geektimes.context.ClassicComponentContext;
import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

@Path("/register")
public class RegisterSubmitController implements PageController {

    private UserService userService = ClassicComponentContext.getInstance().getComponent("bean/UserService");

    @Path("/submit")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        boolean result = userService.register(user);
        return result ? "register-success.jsp" : "register-fail.jsp";
    }
}
