/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

function PORTLET_JS_BOOTSTRAP(jsFile, opts) {

    var context = this;
    this.context_opts = opts;
    
    var nspace = opts.nspace;
    
    if (opts.rootID == undefined)
        context.rootNode = $("#"+opts.nspace+"Root");
    else
        context.rootNode = $("#"+opts.rootID);
    
    this.loadJS = function(jsFile, loadOpts) {
        var $ = this.jQuery;
        var opts = loadOpts || {};
        var context = this;

        $.each(this.context_opts, function(key, val){
            opts[key] = val;
        });


        var contextName = jsFile.replace("/","_");
        if (context[contextName] == null) {
            var date = new Date().getTime();
            $.getScript(opts.baseUrl+'js/'+jsFile+'.js?'+date, function(data, status){
                context[contextName] = $.proxy(PORTLET_JS_CONTROL, context);
                context[contextName](opts);
                //$.proxy(PORTLET_JS_CONTROL, context)(opts);
            });
        } else {
            context[contextName](opts);
        }
    };

    this.ErrorDialog = function(htmlContent, data) {
        var $ = this.jQuery;
        
        var content = $("<div/>").html(htmlContent);
        if (data != null && data != undefined) {
            content.append($("<p/>").append("<strong/>").html(data.status+": "+data.error));
            if (data.hasGlobalErrors == "true") {
                $.each(data.globalerrors, function(index, globalError) {
                    content.append($("<p/>").html("ERROR: "+globalError));
                });
            }
            if (data.hasFieldErrors == "true") {
                $.each(data.fielderrors, function(index, fieldError) {
                    content.append($("<p/>").html("ERROR: "+fieldError));
                });
            }
        }
        content.dialog({
            title: "ERROR",
            modal: true,
            dialogClass: "portlet-content uk-ac-manchester-mleportlet-ERRORDialog",
            close: function() {
                $(this).dialog("destroy");
                $(this).remove();
            },
            buttons: {
                "OK" : function() {
                    $(this).dialog("destroy");
                    $(this).remove();
                }
            }
        });
        
    };
    
    this.SuccessDialog = function(htmlContent) {
        var $ = this.jQuery;
        $("<div/>").html(htmlContent).dialog({
            title: "Success",
            modal: true,
            dialogClass: "portlet-content uk-ac-manchester-mleportlet-SUCCESSDialog",
            close: function() {
                $(this).dialog("destroy");
                $(this).remove();
            },
            buttons: {
                "OK" : function(){
                    $(this).dialog("destroy");
                    $(this).remove();
                }
            }
        });
    };
    
    this.ConfirmDialog = function(htmlContent, callback) {
        var $ = this.jQuery;
        var cback = callback;
        $("<div/>").html(htmlContent).dialog({
            title: "Confirm",
            close: function() {
                $(this).dialog("destroy");
                $(this).remove();
            },
            buttons: {
                "Cancel" : function() {
                    $(this).dialog('destroy');
                    $(this).remove();
                },
                "OK" : function() {
                    cback();
                    $(this).dialog('destroy');
                    $(this).remove();
                }
            }
        });
    };

    this.$id = function(id) {
        
        var $ = context.jQuery;
        return $("#"+nspace+id);
        
    };
    
    this.$class = function(className) {
        var $ = context.jQuery;
        return $("."+className, context.rootNode);
    }
        
    
    context.jQuery = jQuery.noConflict(true);
    context.jQuery(document).ready(
        context.jQuery.proxy(function() {
            this.loadJS(jsFile, {});
        }, context)
    );
    
    

}
