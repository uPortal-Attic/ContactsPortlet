<%@ page contentType="text/html" isELIgnored="false" %>

<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<portlet:defineObjects/>

<div>


    <h2><spring:message code="config.mode.heading.active"/></h2>

    <portlet:actionURL var="MakeAvailable" escapeXml="false" >
        <portlet:param name="action" value="active"/>
    </portlet:actionURL>   
    <form:form  action="${MakeAvailable}" commandName="domainsActive" method="POST">
        <ul class="defined-lists">

            <c:forEach items="${domainsActive.map}" var="domain">
                <li><form:checkbox path="map['${domain.key}']" value="${domain.key}"/> ${domain.key}</li>
            </c:forEach>

        </ul>
        <div class="editcontrols" >
            <button type="submit" class="portlet-button"><spring:message code="config.mode.save"/></button>
        </div>
    </form:form>

    <c:if test="${activeSaved}">
        <p class="portlet-msg-success">Availability Saved!</p>
    </c:if>
    

    <h2><spring:message code="config.mode.heading.default"/></h2>


    <portlet:actionURL var="makeDefault" escapeXml="false">
        <portlet:param name="action" value="default"/>
    </portlet:actionURL>   
    <form:form action="${makeDefault}" commandName="domainsDefault" method="POST">
        <ul class="defined-lists">
            <c:forEach items="${domainsDefault.map}" var="domain">
                <li><form:checkbox path="map['${domain.key}']" value="${domain.key}"/> ${domain.key}</li>
            </c:forEach>
        </ul>
        <div class="editcontrols" >
            <button type="submit" class="portlet-button"><spring:message code="config.mode.save"/></button>
        </div>
    </form:form>

    <c:if test="${defaultSaved}">
        <p class="portlet-msg-success">Defaults Saved!</p>
    </c:if>
    

    <portlet:renderURL portletMode="VIEW" var="returnLink"/>
    <p><a href="${returnLink}" class="portlet-button">Finish</a></p>
</div>