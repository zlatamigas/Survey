package epam.zlatamigas.surveyplatform.model.command.impl;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.command.Command;
import jakarta.servlet.http.HttpServletRequest;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.DEFAULT;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(DEFAULT, FORWARD);
    }
}
