<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- WEB-INF/views/employees/index  -->
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
        <table>
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>役職</th>
                    <th>フォロー</th>
                    <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                        <th>操作</th>
                    </c:if>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row%{status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td><c:out value="${employee.official_position}" /></td>
                        <td>
<!-- ***************************************************************************************************** -->
                            <c:set var="data" value="${Button_flag = 0}" />
                            <c:choose>
                                <c:when test="${employee.code == sessionScope.login_employee.code}">
                                    <c:set var="data" value="${Button_flag = 2}" />
                                </c:when>
                                <c:when test="${followListSize > 0 && employee.code != sessionScope.login_employee.code}">
                                    <c:forEach var="followList" items="${followList}" varStatus="followStatus">
                                            <c:if test="${followList.follow_code == employee.code}">
                                                <form method="POST" action="<c:url value='../follow/destroy' />" onsubmit="return confirmFollowDestroy()">
                                                    <input type="submit" value="フォローを解除する">
                                                    <input type="hidden" name="_token" value="${_token}" />
                                                    <input type="hidden" name="fid" value="${followList.id}" />
                                                </form>
                                                <c:set var="data" value="${Button_flag = 1}" />
                                            </c:if>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                             <c:if test="${Button_flag == 0}">
                                 <form method="POST" action="<c:url value='../follow/create' />" onsubmit="return confirmFollowCreate()">
                                     <input type="submit" value="フォローする">
                                     <input type="hidden" name="_token" value="${_token}" />
                                     <input type="hidden" name="ec" value="${employee.code}" />
                                     <input type="hidden" name="ei" value="${employee.id}" />
                                 </form>
                             </c:if>
<!-- ***************************************************************************************************** -->
                        </td>
                        <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                            <td>
                                <c:choose>
                                    <c:when test="${employee.delete_flag == 1}">
                                        (削除済み)
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <div id="pagination">
            (全 ${employees_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>

        <script>
            function confirmFollowDestroy(){
                if(confirm("フォローを解除しますか？")){
                    return true;
                }
                return false;
            }


            function confirmFollowCreate(){
                if(confirm("フォローしますか？")){
                    return true;
                }
                return false;
            }
        </script>

    </c:param>
</c:import>


