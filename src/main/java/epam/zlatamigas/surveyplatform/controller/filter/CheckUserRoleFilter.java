package epam.zlatamigas.surveyplatform.controller.filter;


import epam.zlatamigas.surveyplatform.controller.command.CommandType;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.command.CommandType.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.model.entity.UserRole.*;

@WebFilter(
        filterName = "CheckUserRoleFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller", "/view/page/controller"})
public class CheckUserRoleFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private Map<UserRole, EnumSet<CommandType>> userCommands ;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userCommands = Map.of(
                ADMIN, EnumSet.of(
                        DEFAULT,
                        HOME,
                        CHANGE_LOCALISATION,
                        LOGOUT,
                        TO_FORGOT_PASSWORD,
                        SEND_FORGOTTEN_PASSWORD_KEY,
                        CONFIRM_CHANGE_PASSWORD_KEY,
                        CHANGE_PASSWORD,
                        // List data
                        LIST_SURVEYS,
                        SEARCH_SURVEYS,
                        LIST_USERS,
                        SEARCH_USERS,
                        LIST_USER_CREATED_SURVEYS,
                        SEARCH_USER_CREATED_SURVEYS,
                        // CRUD survey and its parts
                        START_EDIT_SURVEY,
                        FINISH_EDIT_SURVEY,
                        START_EDIT_QUESTION,
                        FINISH_EDIT_QUESTION,
                        REMOVE_QUESTION,
                        CANCEL_EDIT_SURVEY,
                        CANCEL_EDIT_QUESTION,
                        DELETE_SURVEY,
                        CHANGE_SURVEY_STATUS_CLOSED,
                        CHANGE_SURVEY_STATUS_STARTED,
                        // Participate in survey
                        START_SURVEY_ATTEMPT,
                        FINISH_SURVEY_ATTEMPT,
                        CANCEL_SURVEY_ATTEMPT,
                        VIEW_SURVEY_RESULT,
                        LIST_THEMES_CONFIRMED,
                        SEARCH_THEMES_CONFIRMED,
                        LIST_THEMES_WAITING,
                        CONFIRM_THEME,
                        REJECT_THEME,
                        DELETE_THEME,
                        ADD_THEME,
                        USER_ACCOUNT,
                        START_EDIT_USER,
                        FINISH_EDIT_USER,
                        CANCEL_EDIT_USER,
                        START_CREATE_USER,
                        FINISH_CREATE_USER,
                        CANCEL_CREATE_USER
                ),
                USER, EnumSet.of(
                        DEFAULT,
                        HOME,
                        CHANGE_LOCALISATION,
                        LOGOUT,
                        TO_FORGOT_PASSWORD,
                        SEND_FORGOTTEN_PASSWORD_KEY,
                        CONFIRM_CHANGE_PASSWORD_KEY,
                        CHANGE_PASSWORD,
                        // List data
                        LIST_SURVEYS,
                        SEARCH_SURVEYS,
                        LIST_USER_CREATED_SURVEYS,
                        SEARCH_USER_CREATED_SURVEYS,
                        // CRUD survey and its parts
                        START_EDIT_SURVEY,
                        FINISH_EDIT_SURVEY,
                        START_EDIT_QUESTION,
                        FINISH_EDIT_QUESTION,
                        REMOVE_QUESTION,
                        CANCEL_EDIT_SURVEY,
                        CANCEL_EDIT_QUESTION,
                        DELETE_SURVEY,
                        CHANGE_SURVEY_STATUS_CLOSED,
                        CHANGE_SURVEY_STATUS_STARTED,
                        // Participate in survey
                        START_SURVEY_ATTEMPT,
                        FINISH_SURVEY_ATTEMPT,
                        CANCEL_SURVEY_ATTEMPT,
                        VIEW_SURVEY_RESULT,
                        USER_ACCOUNT
                ),
                GUEST, EnumSet.of(
                        DEFAULT,
                        HOME,
                        CHANGE_LOCALISATION,
                        // User authentication
                        START_SIGN_IN,
                        FINISH_SIGN_IN,
                        START_SIGN_UP,
                        FINISH_SIGN_UP,
                        TO_FORGOT_PASSWORD,
                        SEND_FORGOTTEN_PASSWORD_KEY,
                        CONFIRM_CHANGE_PASSWORD_KEY,
                        CHANGE_PASSWORD,
                        // List data
                        LIST_SURVEYS,
                        SEARCH_SURVEYS,
                        // Participate in survey
                        START_SURVEY_ATTEMPT,
                        FINISH_SURVEY_ATTEMPT,
                        CANCEL_SURVEY_ATTEMPT
                )
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String commandStr = request.getParameter(PARAMETER_COMMAND);

        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        UserRole userRole = user != null ? user.getRole() : GUEST;

        EnumSet<CommandType> allowedCommands = userCommands.get(userRole);
        CommandType command = CommandType.defineCommandType(commandStr);

        if (!allowedCommands.contains(command)) {
            session.setAttribute(ATTRIBUTE_CURRENT_PAGE, PageNavigation.DEFAULT);
            response.sendRedirect(request.getContextPath() + PageNavigation.DEFAULT);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
