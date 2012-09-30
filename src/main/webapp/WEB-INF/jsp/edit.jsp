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
