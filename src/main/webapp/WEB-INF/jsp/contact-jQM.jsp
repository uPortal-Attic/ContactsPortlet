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

<div class="contact" title="${contact.surname}, ${contact.title} ${contact.firstname}">

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

        <div style="clear: both;"></div>
        
            
            
</div>