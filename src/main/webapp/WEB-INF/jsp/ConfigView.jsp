<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
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
        <p class="portlet-msg-success"><spring:message code="config.mode.active.success"/></p>
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
        <p class="portlet-msg-success"><spring:message code="config.mode.default.success"/></p>
    </c:if>
    

    <portlet:renderURL portletMode="VIEW" var="returnLink"/>
    <p><a href="${returnLink}" class="portlet-button"><spring:message code="return"/></a></p>
</div>