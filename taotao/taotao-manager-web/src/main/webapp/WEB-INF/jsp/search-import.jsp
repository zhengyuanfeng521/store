<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<a href="javascript:void(0)" class="easyui-linkbutton" onclick="importAll()">一键导入</a>
<script type="text/javascript">
	function importAll(){
		alert("开始导入...");
		$.ajax({
			url:"/index/importAll",
			type:"GET",
			success:function(data){
				alert(data.status);
				if(data.status == 200){
					$.messager.alert('消息','导入成功'); 
				}else{
					$.messager.alert('消息','导入失败'); 
				}
			}
		});		
	}
</script>