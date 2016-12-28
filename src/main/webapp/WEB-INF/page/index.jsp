<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" href="extjs/resources/css/ext-all.css" rel="stylesheet">
<script type="text/javascript" src="extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
	//The data store containing the list of states
	// 数据的仓库，用来定义数据的模型
	var states = Ext.create('Ext.data.Store', {
		 storeId:'simpsonsStore',
		// 模型字段
	    fields: ['id', 'name','age','sex'],
	    pageSize: 2, // 每页的条目数量
	    // 代理，远程加载
	    proxy: {
	    	 // 异步加载
	         type: 'ajax',
	         // 请求地址
	         url: 'http://localhost:80/XJ_Running_01_UI/user/queryUserList',
	         reader: {
	        	 // 使用json解析
	             type: 'json',
	             root: 'rows',
	             totalProperty: 'total'
	         }
	     },
	     // 自动加载数据
	     autoLoad: true
	    // 静态数据
	    /* data : [
	        {"abbr":"AL", "name":"Alabama"},
	        {"abbr":"AK", "name":"Alaska"},
	        {"abbr":"AZ", "name":"Arizona"}
	        //...
	    ] */
	});
	states.loadPage(2);
	Ext.create('Ext.grid.Panel', {
	    title: 'Simpsons',
	    store: Ext.data.StoreManager.lookup('simpsonsStore'),// GridPanel使用相同的数据源
	    columns: [
	        { header: 'Id',  dataIndex: 'id' },
	        { header: 'Name',  dataIndex: 'name' },
	        { header: 'Age', dataIndex: 'age', flex: 1 },
	        { header: 'Sex', dataIndex: 'sex',
	        	renderer : function(value){
        		if(value == '1'){
        			return '男';
        		}else{
        			return '女';
        		}
        }}
	    ],
	    height: 200,
	    width: 400,
	    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: states,   // GridPanel使用相同的数据源
	        dock: 'bottom',
	        displayInfo: true
	    }],
	    renderTo: Ext.getBody(),
	    
	    // 添加点击事件
	    listeners :{
	    	select:function(model, record, index){
	    		alert(record.data.name);
	    	}
	    }
	});
	// Create the combo box, attached to the states data store
	// 创建一个可视化的下拉框，Ext.form.ComboBox
	Ext.create('Ext.form.ComboBox', {
		// 标签名
	    fieldLabel: 'Choose State',
	    // 定义仓库，对应上面定义好的仓库
	    store: states,
	    // 从本地加载数据
	    queryMode: 'local',
	    // 显示名，引用的是字段的定义name
	    displayField: 'name',
	    // 值定义，引用的是字段abbr
	    valueField: 'id',
	    // 根据html元素的id渲染到页面
	    renderTo: "comboId",
	    listeners:{
	    	// *定义选择事件
	    	select : function(combo, records, eOpts){
	    		alert(this.getValue());
	    	}
	    }
	});
});
</script>
</head>
<body>
<h4>下拉框</h4>
<div id="comboId"></div>
<a href="/XJ_Running_01_UI/user/queryUserCombox">dude</a>
</body>
</html>