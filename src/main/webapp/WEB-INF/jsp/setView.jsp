<%@ page contentType="text/html" isELIgnored="false" %>
<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="BASEURL"><c:url value="/"/></c:set>


<div id="content">
    <ol>
        <c:forEach var="contact" items="${contactList}">
            <li class="ui-widget-content ui-corner-all">

                <jsp:directive.include file="/WEB-INF/jsp/contact.jsp"/>
                
            </li>
        </c:forEach>            
    </ol>
</div>
