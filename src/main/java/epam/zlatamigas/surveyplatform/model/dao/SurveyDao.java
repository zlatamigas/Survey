package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.SurveyUserAttempt;

import java.util.List;

public interface SurveyDao {

    /**
     * Find preview info (name, description) about all started surveys.
     *
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException
     */
    List<Survey> findParticipantSurveysCommonInfo() throws DaoException;

    /**
     * Find preview info (name, description, status) about all surveys created by user.
     *
     * @param userId User id to use for search.
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException
     */
    List<Survey> findCreatorSurveysCommonInfo(int userId) throws DaoException;

    /**
     * Find info about specified survey: question info without statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data without statistics.
     * @throws DaoException
     */
    Survey findParticipantSurveyInfo(int surveyId) throws DaoException;

    /**
     * Find info about specified survey: question info with statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data with statistics.
     * @throws DaoException
     */
    Survey findCreatorSurveyInfo(int surveyId) throws DaoException;

    boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws DaoException;

    boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws DaoException;
}
