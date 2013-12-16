function formatXml(xml) {
	var formatted = '';
	var reg = /(>)(<)(\/*)/g;
	xml = xml.replace(reg, '$1\r\n$2$3');
	var pad = 0;
	jQuery.each(xml.split('\r\n'), function(index, node) {
		var indent = 0;
		if (node.match( /.+<\/\w[^>]*>$/ )) {
			indent = 0;
		} else if (node.match( /^<\/\w/ )) {
			if (pad != 0) {
				pad -= 1;
			}
		} else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
			indent = 1;
		} else {
			indent = 0;
		}

		var padding = '';
		for (var i = 0; i < pad; i++) {
			padding += '  ';
		}

		formatted += padding + node + '\r\n';
		pad += indent;
	});

	return formatted;
}


//formatJson() :: formats and indents JSON string
function formatJson(val) {
	var retval = '';
	var str = val;
    var pos = 0;
    var strLen = str.length;
	var indentStr = '&nbsp;&nbsp;&nbsp;&nbsp;';
    var newLine = '<br />';
	var char = '';
	
	for (var i=0; i<strLen; i++) {
		char = str.substring(i,i+1);
		
		if (char == '}' || char == ']') {
			retval = retval + newLine;
			pos = pos - 1;
			
			for (var j=0; j<pos; j++) {
				retval = retval + indentStr;
			}
		}
		
		retval = retval + char;	
		
		if (char == '{' || char == '[' || char == ',') {
			retval = retval + newLine;
			
			if (char == '{' || char == '[') {
				pos = pos + 1;
			}
			
			for (var k=0; k<pos; k++) {
				retval = retval + indentStr;
			}
		}
	}
	
	return retval;
}

function renderXml(xml){
	var xmlformatted=formatXml(xml);
	var xml_escaped = xmlformatted.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/ /g, '&nbsp;').replace(/\n/g,'<br />');
	var prettified='<code class="prettyprint">'+xml_escaped+'</code>';
	$('#ajaxformgetAll').append("<table id='countryList'></table>");

	$('#countryList').append(
			'<tr><td>'+prettified+'</td></tr>');
}

function renderSingleXml(xml){
	var xmlformatted=formatXml(xml);
	var endheader=xmlformatted.indexOf("?>", 0);
    var xmlwithoutheader=xmlformatted.substring(endheader+2, xmlformatted.length);
	var xml_escaped = xmlwithoutheader.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/ /g, '&nbsp;').replace(/\n/g,'<br />');
	//remove xml header

	var prettified='<code class="prettyprint">'+xml_escaped+'</code>';
	$('#capitalRetrieved').html(
			prettified);
}


function renderJson(json){
	var jsonformatted=formatJson(json);
	var prettified='<code class="prettyprint">'+jsonformatted+'</code>';
	$('#ajaxformgetAll').append("<table id='countryList'></table>");
	$('#countryList').append(
			'<tr><td>'+prettified+'</td></tr>');
}

function renderSingleJson(json){
	var jsonformatted=formatJson(json);
	var prettified='<code class="prettyprint">'+jsonformatted+'</code>';
	$('#capitalRetrieved').html(
			prettified);
}


function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	data=data.countries;
	var list = data == null ? []
	: (data instanceof Array ? data : [ data ]);
	$('#ajaxformgetAll').append("<table id='countryList'></table>");
	$.each(list, function(index, country) {
		$('#countryList').append(
				'<tr><td>' + country.name + '</td><td>'
				+ country.capital
				+ '</td></tr>');
	});
}