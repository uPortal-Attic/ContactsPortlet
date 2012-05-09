<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="NSPACE"><portlet:namespace/></c:set>
<c:set var="BASEURL"><c:url value="/"/></c:set>


<link href="${BASEURL}css/contacts.css" type="text/css" rel="stylesheet" />
<link href="${BASEURL}css/ui.autocomplete.css" type="text/css" rel="stylesheet" />

<portlet:renderURL portletMode="VIEW" var="returnLink"/>
<portlet:actionURL var="SAVE"/>

<div id="${NSPACE}parent" class="org-jasig-portlet-contacts">
    
    
    <h3 class="portlet-note info-message" role="heading" ><spring:message code="edit.mode.heading"/></h3>
    
    <div  role="region">
    
    <form:form action="${SAVE}" commandName="domains">
        <ul class="defined-lists">
            <c:forEach items="${domains.map}" var="config">
                <li><form:checkbox path="map['${config.key}']" value="${config.key}"/> ${config.key}</li>
            </c:forEach>
        </ul>
        <div class="editcontrols" >
            <input type="submit" value="<spring:message code="edit.mode.save"/>" class="portlet-button"/> <a href="${returnLink}" class="portlet-button"><spring:message code="edit.mode.cancel"/></a>
        </div>
            
    </form:form>
    </div>
    
</div>
