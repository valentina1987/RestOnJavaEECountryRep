/*
 * Copyright 2010-2013, CloudBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// wait for the DOM to be loaded 
	$(document)
			.ready(
					function() {
						$('#ajaxformpost')
								.submit(
										function(e) {
											$('#outputpost').remove();
											if($('#nationNamePost').val()==""){
												$('#postTable').append(
												'<tr id="outputpost"class="output"><td></td><td>Insert a country!</td></tr>');
												return false;
											}
											var postData = $(this).serialize();
											var formURL = $(this)
													.attr("action");
											$.ajax({
														url : formURL,
														type : "POST",
														contentType : "application/x-www-form-urlencoded;charset=utf-8",
														data : postData,
														success : function(
																data,
																textStatus,
																jqXHR) {
															$('#postTable').append(
															'<tr id="outputpost" class="output"><td></td><td>Saved!</td></tr>');
														},
														error : function(jqXHR,
																textStatus,
																errorThrown) {
															$('#postTable').append(
																	'<tr id="outputpost" class="output"><td></td><td>Country already existis!</td></tr>');
														}
													});
											e.preventDefault(); //STOP default action
										});

						$('#ajaxformupdate')
						.submit(
								function(e) {
									$('#outputupdate').remove();
									if($('#nationNameUpdate').val()==""){
										$('#updateTable').append(
										'<tr id="outputupdate"class="output"><td></td><td>Insert a country!</td></tr>');
										return false;
									}
									var postData = $(this).serialize();
									var formURL = $(this)
											.attr("action");
									$.ajax({
												url : formURL,
												type : "PUT",
												contentType : "application/x-www-form-urlencoded;charset=utf-8",
												data : postData,
												success : function(
														data,
														textStatus,
														jqXHR) {
													$('#updateTable').append(
													'<tr id="outputupdate" class="output"><td></td><td>Saved!</td></tr>');
												},
												error : function(jqXHR,
														textStatus,
														errorThrown) {
													$('#updateTable').append(
															'<tr id="outputupdate" class="output"><td></td><td>Country does not existis!</td></tr>');
												}
											});
									e.preventDefault(); //STOP default action
								});

						$('#ajaxformget').submit(
								function(e) {		
									$('#outputget').remove();
									if($('#nationName').val()==""){
										$('#getTable').append(
										'<tr id="outputget"class="output"><td></td><td></td><td>Insert a country!</td></tr>');
										return false;
									}
									
									var formURL = $(this).attr("action")
											+ $('#nationName').val();
									$.ajax({
										url : formURL,
										type : "GET",
										dataType : "json",
										success : function(data, textStatus,
												jqXHR) {
										
											$('#capitalRetrieved').html(
													data.capital);
										},
										error : function(jqXHR, textStatus,
												errorThrown) {
											$('#capitalRetrieved').html(
													"country does not exist");
										}
									});
									e.preventDefault(); //STOP default action
								});
						
						$('#xmlbynamebutton').on('click', function () {
							$('#outputget').remove();
							if($('#nationName').val()==""){
								$('#getTable').append(
								'<tr id="outputget"class="output"><td></td><td></td><td>Insert a country!</td></tr>');
								return false;
							}
							
							var formURL = $('#ajaxformget').attr("action")
							+ $('#nationName').val();
							$.ajax({
								url : formURL,
								type : "GET",
								dataType : "xml",
								success : function(data, status, response) {
					                var tmp=response.responseText; // THIS IS THE TRICK
					                renderSingleXml(tmp);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
									
									$('#capitalRetrieved').html(
									"country does not exist");
									
								
								}
							});
							e.preventDefault(); //STOP default action
						});
						
						$('#jsonbynamebutton').on('click', function () {
							$('#outputget').remove();
							if($('#nationName').val()==""){
								$('#getTable').append(
								'<tr id="outputget"class="output"><td></td><td></td><td>Insert a country!</td></tr>');
								return false;
							}
							var formURL = $('#ajaxformget').attr("action")
							+ $('#nationName').val();
							$.ajax({
								url : formURL,
								type : "GET",
								dataType : "json",
								success : function(data, status, response) {
					                var tmp=response.responseText; // THIS IS THE TRICK
					                renderSingleJson(tmp);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
									
									$('#capitalRetrieved').html(
									"country does not exist");
								
								}
							});
							e.preventDefault(); //STOP default action
						});
						
						$('#ajaxformdelete').submit(
								function(e) {
									$('#outputdelete').remove();
									if($('#nameToDelete').val()==""){
										$('#deleteTable').append(
										'<tr id="outputdelete"class="output"><td></td><td>Insert a country!</td></tr>');
										return false;
									}
									var formURL = $(this).attr("action")
											+ $('#nameToDelete').val();
									$.ajax({
										url : formURL,
										type : "DELETE",
										success : function(data, textStatus,
												jqXHR) {
											$('#deleteTable').append(
											'<tr id="outputdelete" class="output"><td></td><td>Country deleted!</td></tr>');
											
										},
										error : function(jqXHR, textStatus,
												errorThrown) {
											$('#deleteTable').append(
											'<tr id="outputdelete" class="output"><td></td><td>Error while deleting!</td></tr>');
										
										}
									});
									e.preventDefault(); //STOP default action
								});

						$('#ajaxformgetAll').submit(
								function(e) {
									$('#countryList').remove();
									var formURL = $(this).attr("action");
									$.ajax({
										url : formURL,
										type : "GET",
										dataType : "json",
										success : renderList,
										error : function(jqXHR, textStatus,
												errorThrown) {
											
										}
									});
									e.preventDefault(); //STOP default action
								});
						
						$('#xmlbutton').on('click', function () {
							$('#countryList').remove();
							var formURL = $('#ajaxformgetAll').attr("action");
							$.ajax({
								url : formURL,
								type : "GET",
								dataType : "xml",
								success : function(data, status, response) {
					                var tmp=response.responseText; // THIS IS THE TRICK
					                renderXml(tmp);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
								
								}
							});
							e.preventDefault(); //STOP default action
						});
						
						$('#jsonbutton').on('click', function () {
							$('#countryList').remove();
							var formURL = $('#ajaxformgetAll').attr("action");
							$.ajax({
								url : formURL,
								type : "GET",
								dataType : "json",
								success : function(data, status, response) {
					                var tmp=response.responseText; // THIS IS THE TRICK
					                renderJson(tmp);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
								
								}
							});
							e.preventDefault(); //STOP default action
						});
					
					});