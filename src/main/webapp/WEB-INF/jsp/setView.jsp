<%@ page contentType="text/html" isELIgnored="false" %>
<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<c:set var="BASEURL"><c:url value="/"/></c:set>
<rs:resourceURL var="addImg" value="rs/famfamfam/silk/1.3/add.png"/>
<rs:resourceURL var="delImg" value="rs/famfamfam/silk/1.3/delete.png"/>

<div id="content">
    <ol>
        <c:forEach var="contact" items="${contactList}">
            <li class="ui-widget-content ui-corner-all">


                <div class="contact" title="${contact.surname}, ${contact.title} ${contact.firstname}">
                    <strong>${contact.surname}, ${contact.title} ${contact.firstname}</strong><br />
                    ${contact.position} - ${contact.department}
                    <br />                                     


                    <c:forEach items="${contact.phoneNumbers}" var="phoneNumber">
                        <img style="vertical-align:middle" src="${pageContext.request.contextPath}/images/telephone.png" height="16" width="16" alt="<spring:message code="contact.telephone.title"/>" title="<spring:message code="contact.telephone.title"/>"/> 
                        ${ phoneNumber.label }: ${ phoneNumber.phoneNumber }
                        <br /> 
                    </c:forEach>

                    <c:forEach items="${contact.emailAddresses}" var="emailAddress">
                        <img style="vertical-align:middle" src="${pageContext.request.contextPath}/images/email.png" height="16" width="16" alt="Email" title="<spring:message code="contact.email.title"/>"/>
                        <c:out value="${ emailAddress.label }" default="NULL"/>: <c:out value="${ emailAddress.emailAddress }" default="NULL"/><br/>
                    </c:forEach>


                    <c:if test="${!empty contact.primaryAddress}">
                        ${contact.primaryAddress.building}, ${contact.primaryAddress.street}<br/>
                    </c:if>


                    <div class="controls">    
                        <c:if test="${domain.hasPersist}">
                            <portlet:resourceURL var="persistURL" id="persist">
                                <portlet:param name="domain" value="${domain.id}"/>
                                <portlet:param name="contact" value="${contact.urn}"/>
                                <portlet:param name="source" value="${source}"/>
                                <portlet:param name="persist" value="true"/>
                            </portlet:resourceURL>
                            <a href="${entry.value}" class="persist" title="<spring:message code="contact.save.title"/>"><img src="${addImg}"/></a>                  
                        </c:if>
                        <c:if test="${domain.hasRemove}">
                            <portlet:resourceURL var="persistURL" id="delete">
                                <portlet:param name="domain" value="${domain.id}"/>
                                <portlet:param name="contact" value="${contact.urn}"/>
                                <portlet:param name="source" value="${source}"/>
                                <portlet:param name="delete" value="true"/>
                            </portlet:resourceURL>
                            <a href="${entry.value}" class="delete" title="<spring:message code="contact.delete.title"/>"><img src="${delImg}"/></a>
                        </c:if>
                    </div>
                </div>
            </li>
        </c:forEach>            
    </ol>
</div>
