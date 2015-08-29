Ext.define('am.manager.view.ManagerWestLayout', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.managerwestlayout',
	layout : 'accordion',
	margin : "5 5 5 5",
	frame : true,
	animate : false,
	items : [ {
		title : '用户管理',
		xtype : 'treepanel',
		rootVisible : false,
		menu : true,
		root : {
			expanded : true,
			children : [ {
				text : "管理员管理",
				id : 'admininfolayout',
				leaf : true
			}, {
				text : "管理员权限管理",
				id : 'adminpermissionlayout',
				leaf : true
			}, {
				text : "用户管理",
				id : 'baseuserinfolayout',
				leaf : true
			} ]
		}
	}, {
		title : '序列号管理',
		xtype : 'treepanel',
		rootVisible : false,
		menu : true,
		root : {
			expanded : true,
			children : [ {
				text : "用户序列号",
				// id : 'payedinfolayout',
				id : 'userinfolayout',
				leaf : true
			}, {
				text : "到期管理",
				id : 'expireinfo',
				leaf : true
			}, {
				text : "序列号信息",
				id : 'usermachineinfo',
				leaf : true
			} ]
		}
	}, {
		title : '快递量管理',
		xtype : 'treepanel',
		rootVisible : false,
		menu : true,
		root : {
			expanded : true,
			children : [ {
				text : "用户快递量",
				// id : 'payedinfolayout',
				id : 'expressquantity',
				leaf : true
			}, {
				text : "快递量列表",
				id : 'expressquantitylist',
				leaf : true
			} ]
		}
	} ]
})