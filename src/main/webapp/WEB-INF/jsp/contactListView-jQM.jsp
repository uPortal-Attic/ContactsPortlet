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
<c:set var="NSPACE"><portlet:namespace/></c:set>

<div class="contactListView" title="${contact.title} ${contact.firstname} ${contact.surname}">
	
	<portlet:resourceURL id="search" var="searchURL">
        <portlet:param name="filter" value="${searchFilter}"/>
        <portlet:param name="term" value="${contact.firstname} ${contact.surname}"/>
        <portlet:param name="domain" value="${domain.id}"/>
        <portlet:param name="nspace" value="${NSPACE}"/>
    </portlet:resourceURL>

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
    
    <div class="contactDetails">
    	<span class="contactName"><a class="contactDetailsLink" href="javascript:;" rel="${searchURL}">${contact.title} ${contact.firstname} ${contact.surname}</a></span>
	    <span class="contactPosition">${contact.position}</span>
	    <span class="contactDepartment">${contact.department}</span>
    </div>
     
    <div style="clear: both;"></div>

</div>