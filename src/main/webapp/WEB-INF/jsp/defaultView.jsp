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


<link href="${BASEURL}css/contacts.css" type="text/css" rel="stylesheet" />
<link href="${BASEURL}css/ui.autocomplete.css" type="text/css" rel="stylesheet" />


<rs:resourceURL var="resourceURL" value="/rs/jquery/1.6.1/jquery-1.6.1.min.js"/>
<script src="${resourceURL}" type="text/javascript"></script>

<rs:resourceURL var="scriptPath" value="/js/jquery-ui-1.8.20.custom.min.js"/>
<script type="text/javascript" language="javascript" src="${scriptPath}"></script>

<script type="text/javascript" src="${BASEURL}js/bootstrapper.js"></script>


<portlet:resourceURL id="autocomplete" var="acUrl"/>

<script type="text/javascript">
     var ${NSPACE}CONTACTS = new PORTLET_JS_BOOTSTRAP(
            "contacts",
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

<div id="${NSPACE}Root" class="org-jasig-portlet-contacts fl-widget portlet" role="section">
    <div class="fl-widget-content portlet-body" role="main">

        <div id="${NSPACE}contact-domains">
            <ul>
                <c:forEach items="${domains}" var="domain">
                    <c:set var="domainName" value="${ fn:replace(domain.name,' ','') }"/>
                    <c:choose>
                        <c:when test="${activeDomain != null && activeDomain == domain.id}">
                            <li rel="selected"><a href="#${NSPACE}${domainName}">${domain.name}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="#${NSPACE}${domainName}">${domain.name}</a></li>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </ul>


            <c:forEach items="${domains}" var="domain">
                <c:set var="domainName" value="${ fn:replace(domain.name,' ','') }"/>

                <div class="contact-domain" id="${NSPACE}${domainName}" rel="${domain.name}" <c:if test="${ domain.hasPersist }">persist="true"</c:if>>

                    <c:if test="${ domain.hasSearch }">


                        <portlet:resourceURL id="search" var="searchURL">
                            <portlet:param name="filter" value="||FILTER||"/>
                            <portlet:param name="term" value="||TERM||"/>
                            <portlet:param name="domain" value="${domain.id}"/>
                            <portlet:param name="nspace" value="${NSPACE}"/>
                        </portlet:resourceURL>

                        <form  action="${searchURL}" class="searchForm form-inline" method="post">
                            <p>
                                <c:forEach items="${ domain.searchFilters }" var="filter" varStatus="status">
                                    <input type="radio" name="filter" value="${filter}" <c:if test="${ status.index == 0 }">checked="checked"</c:if> /> ${filter}&nbsp;&nbsp;&nbsp;&nbsp;
                                </c:forEach>
                            </p>
                            <p>
                                <input class="searchBox" id="${NSPACE}${domainName}Search" type="text" name="searchtext" rel="${domain.id}" size="50"/>

                                <input type="button" value="<spring:message code="search.button.name"/>" class="searchButton"/>
                            </p>
                        </form>

                    </c:if>

                    <div class="results-area ui-widget">
                            <%-- results loaded here --%>

                        <c:if test="${ domain.id == activeDomain && selectedContact != null }" >
                            <c:set var="contact" value="${selectedContact}"/>
                            <c:set var="source" value="${selectedContact.URN}"/>
                            <div class="ui-widget-content ui-corner-all">
                                <jsp:directive.include file="/WEB-INF/jsp/contact.jsp"/>
                            </div>
                        </c:if>

                    </div>

                    <c:if test="${ domain.hasPush }">
                        <div class="accordion">
                            <c:forEach var="entry" items="${domain.contactGroups}">

                                <str:encodeUrl var="setId">${entry.value}</str:encodeUrl>

                                <portlet:resourceURL id="setView" var="setViewResourceUrl">
                                    <portlet:param name="set" value="${setId}"/>
                                    <portlet:param name="domain" value="${domain.id}"/>
                                    <portlet:param name="nspace" value="${NSPACE}"/>
                                </portlet:resourceURL>

                                <h3><a href="#">${entry.key}</a></h3>
                                <div class="results-area" rel="${setViewResourceUrl}">
                                    <a href="${setViewResourceUrl}"><spring:message code="view.details.link"/></a>
                                </div>
                            </c:forEach>
                        </div>

                    </c:if>

                </div>
            </c:forEach>


        </div>
        <p>
            <spring:message code="version.label"/> : ${appversion}
        </p>
    </div> <!-- end: portlet-body -->
</div> <!-- end: portlet -->
