function crudLoadSchema() {
    $.ajax(apiUrl + "schema", {
        async: false
    })
    .success(function(data, statusText, jqXHR) {
        $("#create-entity").data("schema", data);
        $("title").text(data.tableName);
        $("#tableName").text(data.tableName);
        
        // All
        $("#allHead").empty();
        $("#allHead").append("<th>" + data.primaryKeyName + "</th>");
        
        // Create
        $("#createFieldset").append("<label for='create_" + data.primaryKeyName + "'>" + data.primaryKeyName + "</label>" +
            "<input id='create_" + data.primaryKeyName + "' name='" + data.primaryKeyName + "' type='" + data.primaryKeyType + 
            "' placeholder='primary key' /><br/>");
        if ("number" == data.primaryKeyType) {
            $("#create_" + data.primaryKeyName).attr("disabled", "disabled");
        }
        
        // Update
        $("#updateFieldset").append("<div class='control-group'>" + 
            "<label class='control-label' for='update_" + data.primaryKeyName + "'>" + data.primaryKeyName + "</label>" +
            "<div class='controls' >" +
            "<input id='update_" + data.primaryKeyName + "' name='" + data.primaryKeyName + "' type='" + data.primaryKeyType + 
            "' readonly='readonly' />" +
            "</div></div>");
        
        $.map(data.columns, function(item, key) {
            if (!crudIsAuditField(key)) {
                $("#allHead").append("<th>" + key + "</th>");
            }
            
            // Create
            if (!crudIsAuditField(key)) {
                $("#createFieldset").append("<label for='create_" + key + "'>" + key + "</label>" +
                    ('text' == item ? 
                    "<textarea id='create_" + key + "' name='" + key + "'></textarea>" :
                    	('Collection' == item ?
                    			"<input size='100' id='create_" + key + "' name='" + key + "' type='" + item + "' />" +
                    					" <br/>param array mapping <input id='create_" + key +"_param'/>":
                    "<input id='create_" + key + "' name='" + key + "' type='" + item + "' />" ))
                    + "<br/>");
            }
            
            //Collection Field
            
            // Update
            $("#updateFieldset").append("<div class='control-group'>" +
                "<label class='control-label' for='update_" + key + "'>" + key + "</label>" +
                "<div class='controls' >" +
                ('text' == item ? 
                "<textarea id='update_" + key + "' name='" + key + "'></textarea>" :
                	('Collection' == item ?
                			"<input id='update_" + key + "' name='" + key + "' type='" + item + "' />" +
                					" param array mapping <input id='update_" + key +"_param'/>"
                :"<input id='update_" + key + "' name='" + key + "' type='" + item + "' />" )) + 
                "</div></div>");
            if (crudIsAuditField(key)) {
//                $("#update_" + key).attr("readonly", "readonly");
            }
        });
    })
}

function crudCreateEntity() {
    var body = crudUpsertEntity("#create_");
    
    crudCreate(body, function(data, statusText, jqXHR) {
        document.getElementById("createForm").reset();
    });
    
}

function crudUpsertEntity(idPrefix) {
    var body = {};
    var schema = $("#create-entity").data("schema");
    var val;
    
    // add primary key?
    if ("#update_" == idPrefix || "text" == schema.primaryKeyType) {
        val = $(idPrefix + schema.primaryKeyName).val();
        if (val && 0 < val.length) {
            body[schema.primaryKeyName] = val;
        }
    }
    
    // map properties
    $.map(schema.columns, function(item, key) {
        if ("#create_" == idPrefix && crudIsAuditField(key)) {
            // do not map to body when creating
        }
        else {
        	if ("Collection" == item) {
        		//alert('Collection Type');
        		var params = $(idPrefix + key+"_param").val();
        		body[params] = $(idPrefix + key).val().split(",");
        	} else {
        		if (!crudIsAuditField(key))
        		body[key] = $(idPrefix + key).val();
        	}
        }
    });
    return body;
}

function crudCreate(body, successFunction) {
    $.post(apiUrl + "../v10", body)
    .success(successFunction);
}

function crudAddEntity(item, index, schema) {
    var primaryKey = item[schema.primaryKeyName];
    $("#allBody").append("<tr id='all_" + primaryKey + "' ><td>" +
        "<label class='checkbox'><input type='checkbox' id='" + 
        primaryKey + "." + schema.primaryKeyName + "' value='" + primaryKey + "'/>" +
        primaryKey + "</label></td></tr>");
    var value;
    var title = "";
    var first = true;
    $.map(schema.columns, function(clazz, key) {
    	
        value = item[key];
        
                
        if ("datetime" == clazz) {
            value = crudFormatMillis(value);
        }
        if (crudIsAuditField(key)) {
            title = title + key + " " + value + ", ";
            $("#all_" + primaryKey).append("<input type='hidden' id='" + primaryKey + "X" + key + "' value='" + value + "' />");
        }
        else {
            if (first) {
                $("#all_" + primaryKey).append("<td><a href='#update-entity' onclick='crudPopulateUpdate(\"" + 
                    primaryKey + "\");' data-toggle='tab' id='" + primaryKey + "X" + key + "'>" + value + "</a></td>");
                first = false;
            }
            else {
                $("#all_" + primaryKey).append("<td id='" + primaryKey + "X" + key + "'>" + value + "</td>");
            }
        }
    });
    $("#all_" + primaryKey).attr('title', title);
}

function crudFormatMillis(millis) {
    var d = new Date(millis);
//    return d.toUTCString();
    return d.toLocaleString();
}

function crudIsAuditField(key) {
   return "createdBy" == key || "createdDate" == key || "updatedBy" == key || "updatedDate" == key; 
}

function crudLoadMore() {
    $("#allBody").empty();
    var body = {};
    var schema = $("#create-entity").data("schema");
    crudGetPage(body, function(data, statusText, jqXHR) {
        $.map(data.items, function(item,index) {
            crudAddEntity(item, index, schema);
        });
    });
}

function crudGetPage(body, successFunction) {
    $.getJSON(apiUrl + "../v10", body)
    .success(successFunction);
}

function crudPopulateUpdate(primaryKey) {
	
    var schema = $("#create-entity").data("schema");
    var value = primaryKey;
    $("#update_" + schema.primaryKeyName).val(value);
    $.map(schema.columns, function(clazz, key) {
        if (crudIsAuditField(key)) {
            value = $("#" + primaryKey + "X" + key).val();
        }
        else {
            value = $("#" + primaryKey + "X" + key).text();
        }
            
        if ("text" == clazz) {
            $("#update_" + key).text(value);
        }     
         else {
            $("#update_" + key).val(value);
        }
    });
    
}

function crudUpdate(body, successFunction) {
    $.post(apiUrl + "../v10", body)
    .success(successFunction);
}

function crudUpdateEntity() {
    var body = crudUpsertEntity("#update_");
    
    crudCreate(body, function(data, statusText, jqXHR) {
        alert("Successful update.");
    });
    
}