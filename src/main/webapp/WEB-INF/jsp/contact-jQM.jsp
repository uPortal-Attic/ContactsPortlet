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

<rs:resourceURL var="addImg" value="rs/famfamfam/silk/1.3/add.png"/>
<rs:resourceURL var="delImg" value="rs/famfamfam/silk/1.3/delete.png"/>

<div class="contact" title="${contact.title} ${contact.firstname} ${contact.surname}">

	<div class="contactDetailsHolder">
		<c:choose>
	        <c:when test="${!empty contact.imageURI}">
	            <div class="contact-photo" rel="${contact.imageURI}" ></div>
	        </c:when>
	        <c:otherwise>
	            <div class="contact-photo">
	                <img src="${BASEURL}/images/image_unavailable.jpg"/>
	            </div>
	        </c:otherwise>
	    </c:choose>
	    <div class="clear"></div>
	    
	    <div class="contactName">${contact.title} ${contact.firstname} ${contact.surname}</div>
	    <div class="contactPosition">${contact.position}</div>
	    <div class="contactDepartment">${contact.department}</div>
	    
	    <c:forEach items="${contact.phoneNumbers}" var="phoneNumber">
	        <img style="vertical-align:middle" src="${pageContext.request.contextPath}/images/telephone.png" height="16" width="16" alt="<spring:message code="contact.telephone.title"/>" title="<spring:message code="contact.telephone.title"/>"/> 
	        ${ phoneNumber.displayType }: <a href="tel:${ phoneNumber.phoneNumber }">${ phoneNumber.phoneNumber }</a>
	        <br /> 
	    </c:forEach>
	
	    <c:forEach items="${contact.emailAddresses}" var="emailAddress">
	        <img style="vertical-align:middle" src="${pageContext.request.contextPath}/images/email.png" height="16" width="16" alt="Email" title="<spring:message code="contact.email.title"/>"/>
	             <c:out value="${ emailAddress.displayType }" default="NULL"/>: <a href="mailto:"><c:out value="${ emailAddress.emailAddress }" default="NULL"/></a><br/>
	    </c:forEach>
	
	
	    <c:if test="${!empty contact.primaryAddress}">
	        ${contact.primaryAddress.building}, ${contact.primaryAddress.street}<br/>
	    </c:if>		
	</div>
	
	<div class="contactActionGrid ui-grid-a">
		<div class="ui-block-a">
			<a href="tel:${ phoneNumber.phoneNumber }" class="callContactButton" data-role="button" data-inline="true">Call</a>
		</div>
		<div class="ui-block-b">
			<a href="mailto:<c:out value="${ emailAddress.emailAddress }"/>" class="emailContactButton" data-role="button" data-inline="true">Email</a>
		</div>	   
	</div>

	<div class="clear"></div>

</div>