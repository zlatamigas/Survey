package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;

import java.util.List;

public interface ThemeService {
    List<Theme> findAllConfirmed() throws ServiceException;
    List<Theme> findAllWaiting() throws ServiceException;
    boolean confirmTheme(int themeId) throws ServiceException;
    boolean insertConfirmedTheme(Theme theme) throws ServiceException;
    boolean insertWaitingTheme(Theme theme) throws ServiceException;
    boolean delete(int themeId) throws ServiceException;
}
