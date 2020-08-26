var current;
var total;
var index;


function paging() {
	var pag=10;
	$(".pageli").remove();
	var pages = Math.ceil(total / 5);
	var pageli = "<li class='pageli disabled previousPage'><a href='javascript:void(0)'>&laquo;</a></li>";
	if(index <= pages - 1 && index > 0) {
		pageli = "<li class='pageli previousPage'><a href='javascript:void(0)' onclick='previousPage()'>&laquo;</a></li>";
	}

	for(var i = index * pag + 1; i <= index * pag + pag && i <= total; i++) {
		if(i == current) {
			pageli = pageli + "<li class='active pageli'><a href='javascript:void(0)' onclick='onclikPage(this)'>" + i + "</a></li>";
		} else {
			pageli = pageli + "<li class='pageli'><a href='javascript:void(0)' onclick='onclikPage(this)'>" + i + "</a></li>";
		}
	}
	if(index == pages - 1) {
		pageli = pageli + "<li class='disabled pageli nextPage'><a href='javascript:void(0)'>&raquo;</a></li>";
	} else {
		pageli = pageli + "<li class='pageli nextPage'><a href='javascript:void(0)' onclick='nextPage()'>&raquo;</a></li>";
	}
	$(".pagination").append(pageli);

}

function nextPage() {
	index = index + 1;
	paging();
}

function previousPage() {
	index = index - 1;
	paging();
}

function onclikPage(a) {
	$(".active.pageli").removeClass("active");
	$(a).parents("li").addClass("active");
	goPage();
}
//引用该js文件需要实现goPage()方法
//function goPage() {
//	current = $(".active").children("a").text();
//	if(current == "") {
//		current = 1;
//	}
//	$.ajax({
//		type: "get",
//		url: "./titleType/getPage",
//		data: "size=4&current=" + current,
//		success: function(res) {
//			total = res.pages;
//			index = Math.floor((current - 1) / 5);
//			paging();
//			$(".titleTypetr").remove();
//			$(res.records).each(function() {
//				var temptr = "<tr class='titleTypetr'><td class='hidden'>" + this.id + "</td><td>" + this.name + "</td><td>" + this.displayOrder + "</td><td>" + this.status + "</td></tr>"
//				$("#titleType-main").append(temptr);
//			})
//		}
//	});
//}
//状态按钮 需实现updateAjax方法
var typeId;
var status;
function updateStatus(b, s) {
	var result = $(b).hasClass("button-active");
	if(!result) {
		status = s;
		typeId = $(b).parents("tr").children("td:first").text();
		$(b).parent("td").children(".button-active").removeClass("button-active");
		$(b).addClass("button-active");
		updateAjax();
	}
}