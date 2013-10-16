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

<portlet:defineObjects/>
<c:set var="NSPACE"><portlet:namespace/></c:set>
<c:set var="BASEURL"><c:url value="/"/></c:set>


    <link href="${BASEURL}css/contacts-jQM.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
    var $ = jQuery = up.jQuery;
</script>

<script type="text/javascript" src="${BASEURL}js/bootstrapper.js"></script>


<portlet:resourceURL id="autocomplete" var="acUrl"/>

<script type="text/javascript">
    
    var ${NSPACE}CONTACTS = new PORTLET_JS_BOOTSTRAP(
    "contacts-mobile",
    {
        baseUrl: "${BASEURL}",
        nspace: "${NSPACE}",
        autocomplete: "${acUrl}",
        messages: {
            "saved-success": '<spring:message code="js.contact.saved.success"/>',
            "saved-failed": '<spring:message code="js.contact.saved.failed"/>',
            "delete-success": '<spring:message code="js.contact.delete.success"/>',
            "delete-failed": '<spring:message code="js.contact.delete.failed"/>'
        },
        rootID: "${NSPACE}Root"
    }
);
</script>

<div id="${NSPACE}Root" class="org-jasig-portlet-contacts fl-widget portlet "  role="section">


    <div id="${NSPACE}domainsContainer">
        <div data-role="content" class="portlet-content">
            
                <ul data-role="listview" data-inset="false">
                    <c:forEach items="${domains}" var="domain">
                        <c:set var="domainName" value="${ fn:replace(domain.name,' ','') }"/>
                        <li class="contactDomain">
                            <a href="javascript:;" rel="${NSPACE}${domainName}"><h3>${domain.name}</h3></a>
                        </li>
                    </c:forEach>
                    
                </ul>
        </div>
    </div>
        
    <c:forEach items="${domains}" var="domain">
        <c:set var="domainName" value="${ fn:replace(domain.name,' ','') }"/>
        <div id="${NSPACE}${domainName}" class="setContainer" style="display: none;">
        
            <div data-role="header" class="titlebar portlet-titlebar">
                <a href="javascript:;" class="backButton" data-role="button" data-icon="back" data-inline="true"><spring:message code="back"/></a>
                <h1 class="title">${domain.name}</h1>
            </div>
            <c:if test="${ domain.hasSearch }">
                <div class="portlet-content">        
                   <portlet:resourceURL id="search" var="searchURL">
                       <portlet:param name="filter" value="||FILTER||"/>
                       <portlet:param name="term" value="||TERM||"/>
                       <portlet:param name="domain" value="${domain.id}"/>
                       <portlet:param name="nspace" value="${NSPACE}"/>
                   </portlet:resourceURL>
                   
                   <!--  domain search form -->
                   <form  action="${searchURL}" class="searchForm mobile-search-form" method="post">
                       
                       <form  action="${searchURL}" class="searchForm form-inline" method="post">
                       <div class="mobile-search-form">
                          <input class="searchBox mobile-search-box" id="${NSPACE}${domainName}Search" type="search" name="searchtext" rel="${domain.id}" value="<spring:message code="search.input.name"/>"/>
                          <div class="mobile-search-options hidden">
                          	<select name="filter" data-native-menu="false">
	                              <c:forEach items="${ domain.searchFilters }" var="filter" varStatus="status">
	                                  <option value="${filter}">${filter}</option>
	                              </c:forEach>
	                          </select>
	                          <button class="searchButton" data-role="button"><spring:message code="search.button.name"/></button>
                          </div>
                       </div>
                   </form>
                       
                   </form>
                   
                </div>
            </c:if>
            <div data-role="content" class="portlet-content">
                <c:if test="${ domain.hasPush }">
                    <ul data-role="listview">
                        <c:forEach var="entry" items="${domain.contactGroups}">
                            <portlet:resourceURL id="setView" var="setViewResourceUrl">
                                <portlet:param name="set" value="${entry.value}"/>
                                <portlet:param name="domain" value="${domain.id}"/>
                                <portlet:param name="nspace" value="${NSPACE}"/>
                            </portlet:resourceURL>
                            <li class="contactSet">
                                <a href="javascript:;" rel="${setViewResourceUrl}"><h3>${entry.key}</h3></a>
                            </li>  
                        </c:forEach>
                    </ul>
                </c:if>   
            </div>
            
        </div>
        
    </c:forEach>

    <div id="${NSPACE}contactContainer" class="contactList" style="display: none;">
        <div data-role="header" class="titlebar portlet-titlebar searchResultsTitleBar">
            <a href="javascript:;" class="contactBackButton" data-role="button" data-icon="back" data-inline="true"><spring:message code="back"/></a>
            <h1 class="title"></h1>
        </div>
        <div data-role="header" class="titlebar portlet-titlebar contactListTitleBar">
           	<a href="javascript:;" class="contactListBackButton" data-role="button" data-icon="back" data-inline="true"><spring:message code="back"/></a>
           	<h1 class="title detailsHeader"></h1>
       	</div>
        <div class="mobile-list-view">
            <ul data-role="list-view">
            	
            </ul>
        </div>
        <div class="mobile-single-contact-view">
        	
        </div>
    </div>

    <p>
        <spring:message code="version.label"/> : ${appversion}
    </p>

</div> <!-- end: portlet-body -->

