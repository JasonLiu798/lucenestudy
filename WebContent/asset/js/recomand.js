$().ready(function() {
	
	
	$("body").click(function(){
		 $("#recomand").html("");
	});
	
	var highlightindex = -1;// 当前高亮的节点

	var wordInput = $("#searchtext");
	var wordInputOffset = wordInput.offset();
	var recomandDiv = $("#recomand");
	recomandDiv.hide()
		.css("position","absolute")
		.css("top",		wordInputOffset.top + wordInput.height() + 15 + "px")
		.css("left", 	wordInputOffset.left + "px")
		.width(	wordInput.outerWidth() );//+wordInputOffset.width  + 2);

	wordInput.keyup(function(event) {
		 
		var myEvent = event || window.event;
		var keyCode = myEvent.keyCode;
		//A~Z0~9,space,backspace,del
		if (keyCode >= KEY_M0 && keyCode <= KEY_9 || keyCode == KEY_SPACE || keyCode == KEY_BACK	|| keyCode == KEY_DEL 
				) {
			recomandDiv.html("");//clear box
			
			var wordText = wordInput.val();
//					var autoNode = recomandDiv;
			if (wordText != "") {
				
				$.ajax({
		            url:  	URL_REC,
		            async: 	false,
		            data: { usertext: encodeURI(encodeURI(wordText)) },
		            success: function (data) {
		                if (  data.error != null ) {
		                    if (data.error != '') {
		                    	console.log("error:"+data.error);
//				                        alert("出错了:"+ data.error);
		                    }
		                }else{
		                	if(data.status == 'yes'){
		                		for(i=0;i<data.datas.length;i++){
			                		var word = data.datas[i];
			                		console.log(word);
			                		var newDivNode = $("<div>")
			                			.attr("id", i )
			                			.attr("class", cls_recoditem_normal );
			                		
			                		newDivNode.html( word.searchWord ).appendTo( recomandDiv );
			                		newDivNode.mouseover(function() {//mouse into div
			                			console.log( highlightindex );
										if (highlightindex != -1) {
											recomandDiv.children("div")
												.eq(highlightindex).attr("class", cls_recoditem_normal );
										}
										highlightindex = $(this).attr("id");
										$(this).attr("class", cls_recoditem_hl );
										console.log( highlightindex );
									});
			                		
			                		newDivNode.mouseout(function() {//mouse out div
			                			$(this).attr("class", cls_recoditem_normal );
									});
			                		
									newDivNode.click(function() {// click
										var comText = $(this).text();
										recomandDiv.hide();
										highlightindex = -1;
										wordInput.val(comText);
									});									
			                	}
		                		
		                		if (data.datas.length > 0) {
		                			recomandDiv.show();
								} else {
									recomandDiv.hide();
									highlightindex = -1;
								}
		                	}
		                }
		            },
		            error: function (msg) {
		                alert(msg.responseText);
		            }
		        });
			}
		} else if (keyCode == KEY_UP || keyCode == KEY_DOWN) {
			if (keyCode == KEY_UP) {// up
				var recomandDivs = $("#recomand").children("div");
				if (highlightindex != -1) {
					recomandDivs.eq( highlightindex ).attr("class", cls_recoditem_normal );
					highlightindex--;
				} else {
					highlightindex = recomandDivs.length - 1;
				}

				if (highlightindex == -1) {
					highlightindex = recomandDivs.length - 1;
				}
				console.log('up idx '+highlightindex);
				recomandDivs.eq(highlightindex).attr("class", cls_recoditem_hl );
			}
			if (keyCode == KEY_DOWN ) {//down 
				var recomandDivs = $("#recomand").children("div");
//				console.log( 'mouse down '+highlightindex );
				if (highlightindex != -1) {
					recomandDivs.eq(highlightindex).attr("class", cls_recoditem_normal);
				}
				highlightindex++;
//				console.log( 'mouse down ++ '+highlightindex +" len:"+recomandDivs.length );
				if (highlightindex == recomandDivs.length) {
					highlightindex = 0;
				}
				console.log('down idx '+highlightindex);
				recomandDivs.eq(highlightindex).attr("class",cls_recoditem_hl );
			}
		}else if (keyCode == KEY_ENTER ) {//enter
			console.log('enter');
			if (highlightindex != -1) {
				var comText =  $("#recomand").hide().children("div").eq( highlightindex).text();
				console.log(comText + 'submit ');
				highlightindex = -1;
				wordInput.val(comText);
				
			} else {
				console.log( wordInput.val()+"submit");
				recomandDiv.hide();
				wordInput.get(0).blur();// lose focus
			}
		}
	});
});
