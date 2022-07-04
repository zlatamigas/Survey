<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.SurveyStatus" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.surveys.user"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
</head>

<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container-fluid">

    <div class="row">
        <div class="col-3">
            <jsp:include page="/view/fragment/account_left_navbar.jsp"/>
            <script>
                let activeLink = document.getElementById("navUserSurveys");
                activeLink.classList.add("active");
            </script>
        </div>
        <div class="col-9">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="${CommandType.START_EDIT_SURVEY}">
                <input type="hidden" name="${DataHolder.PARAMETER_CREATE_NEW_SURVEY}" value="true">
                <button type="submit" class="btn btn-primary"><fmt:message key="button.create"/></button>
            </form>

            <div class="accordion" id="userSurveys">

                <div id="pagination-page-container">

                    <c:set var="surveyPage" value="1" scope="page"/>
                    <div id="pagination-page-${surveyPage}" style="display: none">
                        <c:if test="${requestScope.user_surveys != null && requestScope.user_surveys.size() > 0}">
                        <c:forEach var="surveyIndex" begin="0" end="${requestScope.user_surveys.size() - 1}">
                        <c:set var="survey" value="${requestScope.user_surveys.get(surveyIndex)}" scope="page"/>
                        <c:if test="${surveyIndex / DataHolder.PAGINATION_ITEMS_PER_PAGE >= surveyPage}">
                    </div>
                    <c:set var="surveyPage" value="${surveyPage + 1}"/>
                    <div id="pagination-page-${surveyPage}" style="display: none">
                        </c:if>
                        <div class="card">
                                <div class="card-header" id="heading${survey.surveyId}">
                                    <div class="row justify-content-between">
                                        <div class="col">
                                            <h5 class="card-title">${survey.name}</h5>
                                        </div>
                                        <div class="col col-auto">
                                            <button class="btn" type="button" data-toggle="collapse"
                                                    data-target="#collapse${survey.surveyId}" aria-expanded="true"
                                                    aria-controls="collapse${survey.surveyId}"><i class="fas fa-angle-down"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <div id="collapse${survey.surveyId}" class="collapse"
                                     aria-labelledby="heading${survey.surveyId}"
                                     data-parent="#userSurveys">
                                    <div class="card-body">
                                        <h6 class="card-subtitle mb-2 text-muted">${survey.theme.themeName}</h6>
                                        <p class="card-subtitle mb-2 text-muted">
                                            <c:choose>
                                                <c:when test="${survey.status == SurveyStatus.NOT_STARTED}">
                                                    <fmt:message key="status.survey.notstarted"/>
                                                </c:when>
                                                <c:when test="${survey.status == SurveyStatus.STARTED}">
                                                    <fmt:message key="status.survey.started"/>
                                                </c:when>
                                                <c:when test="${survey.status == SurveyStatus.CLOSED}">
                                                    <fmt:message key="status.survey.closed"/>
                                                </c:when>
                                            </c:choose>
                                        </p>
                                        <p class="card-text">${survey.description}</p>
                                        <div class="btn-toolbar justify-content-end" role="toolbar">
                                            <form id="startEditSurveyForm${survey.surveyId}" action="controller" method="post">
                                                <input type="hidden" name="command" value="${CommandType.START_EDIT_SURVEY}">
                                                <input type="hidden" name="${DataHolder.PARAMETER_CREATE_NEW_SURVEY}"
                                                       value="false">
                                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}"
                                                       value="${survey.surveyId}">
                                            </form>

                                            <form id="deleteSurveyForm${survey.surveyId}" action="controller" method="post">
                                                <input type="hidden" name="command" value="${CommandType.DELETE_SURVEY}">
                                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}"
                                                       value="${survey.surveyId}">
                                            </form>

                                            <form id="stopSurveyForm${survey.surveyId}" action="controller" method="post">
                                                <input type="hidden" name="command"
                                                       value="${CommandType.CHANGE_SURVEY_STATUS_CLOSED}">
                                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}"
                                                       value="${survey.surveyId}">
                                            </form>

                                            <form id="startSurveyForm${survey.surveyId}" action="controller" method="post">
                                                <input type="hidden" name="command"
                                                       value="${CommandType.CHANGE_SURVEY_STATUS_STARTED}">
                                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}"
                                                       value="${survey.surveyId}">
                                            </form>

                                            <form id="viewResultSurveyForm${survey.surveyId}" action="controller" method="post">
                                                <input type="hidden" name="command" value="${CommandType.VIEW_SURVEY_RESULT}">
                                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}"
                                                       value="${survey.surveyId}">
                                            </form>

                                            <div class="btn-group" role="group">
                                                <c:choose>
                                                    <c:when test="${survey.status == SurveyStatus.NOT_STARTED}">
                                                        <button form="startSurveyForm${survey.surveyId}" type="submit"
                                                                class="btn btn-success">
                                                            <i class="fas fa-play"></i>
                                                                <%--                                                    <fmt:message key="usersurvey.startsurvey"/>--%>
                                                        </button>
                                                        <button form="startEditSurveyForm${survey.surveyId}" type="submit"
                                                                class="btn btn-primary">
                                                            <fmt:message key="button.edit"/>
                                                        </button>
                                                    </c:when>
                                                    <c:when test="${survey.status == SurveyStatus.STARTED}">
                                                        <button form="stopSurveyForm${survey.surveyId}" type="submit"
                                                                class="btn btn-primary">
                                                            <fmt:message key="button.survey.stop"/>
                                                        </button>
                                                    </c:when>
                                                    <c:when test="${survey.status == SurveyStatus.CLOSED}">
                                                        <button form="viewResultSurveyForm${survey.surveyId}" type="submit"
                                                                class="btn btn-primary">
                                                            <fmt:message key="button.survey.view.results"/>
                                                        </button>
                                                    </c:when>
                                                </c:choose>

                                                <c:if test="${survey.status != SurveyStatus.STARTED}">
                                                    <button form="deleteSurveyForm${survey.surveyId}" type="submit"
                                                            class="btn btn-warning">
                                                        <fmt:message key="button.delete"/>
                                                    </button>
                                                </c:if>

                                            </div>

                                        </div>
                                    </div>
                                </div>

                            </div>
                        <c:remove var="survey" scope="page"/>
                        </c:forEach>
                        </c:if>
                    </div>
                    <c:remove var="surveyPage" scope="page"/>

                </div>

            </div>


            <div class="pagination">
                <ul></ul>
            </div>

            <script>
                // selecting required element
                const element = document.querySelector(".pagination ul");
                let totalPages = Math.max(Math.ceil(${requestScope.user_surveys.size() / DataHolder.PAGINATION_ITEMS_PER_PAGE}), 1);
                let page = 1;
                console.log(totalPages, page);
                //calling function with passing parameters and adding inside element which is ul tag
                element.innerHTML = createPagination(totalPages, page);
            </script>

            <c:remove var="user_surveys" scope="request"/>
        </div>
    </div>

</div>

</body>
</html>
