<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>
        <h2>日報管理システムへようこそ</h2>
        <h3>【自分の日報　一覧】</h3>
        <table class="repot_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="report" items="${reports}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${report.employee.name}" /></td>
                        <td class="report_date"><fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${report.title}</td>
                        <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <div id="pagination">
            (全 ${reports_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((reports_count - 1) / 5) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}&followReports_page=${followReports_page}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>


        <p><a href="<c:url value='/reports/new' />">新規日報の登録</a></p>


        <h3>【フォローしている人の日報　一覧】</h3>
        <table class="repot_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="followReports" items="${followReports}" varStatus="followReportsStatus">
                    <tr class="row${followReportsStatus.count % 2}">
                        <td class="report_name"><c:out value="${followReports.employee.name}" /></td>
                        <td class="report_date"><fmt:formatDate value='${followReports.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${followReports.title}</td>
                        <td class="report_action"><a href="<c:url value='/reports/show?id=${followReports.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <div id="pagination">
            (全 ${followReports_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((followReports_count - 1) / 5) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == followReports_page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?followReports_page=${i}&page=${page}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>