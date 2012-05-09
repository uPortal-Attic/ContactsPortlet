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



<rs:resourceURL var="resourceURL" value="/rs/jquery-ui/jquery.bgiframe.min.js"/>
<script src="${resourceURL}" type="text/javascript"></script>

<rs:resourceURL var="resourceURL" value="/rs/jquery-ui/ui.autocomplete.js"/>
<!--<script src="${resourceURL}" type="text/javascript"></script>-->


<script type="text/javascript" src="${BASEURL}js/bootstrapper.js"></script>

<script type="text/javascript">
     var ${NSPACE}CONTACTS = new PORTLET_JS_BOOTSTRAP(
            "contacts",
            {
                baseUrl: "${BASEURL}",
                nspace: "${NSPACE}",
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
                    <li><a href="#${NSPACE}${domainName}">${domain.name}</a></li>
                </c:forEach>
            </ul>
           
            
            <c:forEach items="${domains}" var="domain">
                <c:set var="domainName" value="${ fn:replace(domain.name,' ','') }"/>

                <div class="contact-domain" id="${NSPACE}${domainName}" <c:if test="${ domain.hasPersist }">rel="${domain.name}" persist="true"</c:if>>

                    <c:if test="${ domain.hasSearch }">
                        
                        <portlet:actionURL var="searchURL">
                            <portlet:param name="filter" value="||FILTER||"/>
                            <portlet:param name="term" value="||TERM||"/>
                            <portlet:param name="domain" value="${domain.id}"/>
                            <portlet:param name="nspace" value="${NSPACE}"/>
                        </portlet:actionURL>
                        
                        <form  action="${searchURL}" class="searchForm" method="post">
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
                        <div class="results-area ui-widget">
                            <%-- results loaded here --%>
                        </div>
                    </c:if>

                    <c:if test="${ domain.hasPush }">
                        <div class="accordion">
                            <c:forEach var="entry" items="${domain.contactGroups}">


                                <portlet:actionURL var="setViewActionUrl">
                                    <portlet:param name="set" value="${entry.value}"/>
                                    <portlet:param name="domain" value="${domain.id}"/>
                                    <portlet:param name="nspace" value="${NSPACE}"/>
                                </portlet:actionURL>

                                <h3><a href="#">${entry.key}</a></h3>
                                <div class="results-area" rel="${setViewActionUrl}">
                                    <a href="${setViewActionUrl}"><spring:message code="view.details.link"/></a>
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