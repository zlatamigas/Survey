package com.zlatamigas.surveyplatform.controller.command.impl;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.DEFAULT;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class DefaultCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = DEFAULT;

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
