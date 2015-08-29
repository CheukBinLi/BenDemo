Ext.define('am.manager.view.ManagerTopLayout', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.managertoplayout',
			layout : "absolute",
			margin : "0 0 0 0",
			padding : '0 0 0 0',
			frame : true,
			animate:false,
			defaults : {
				// closable : true,
				closeAction : 'destroy',
				bodyBorder : false
			},
			items : [{
						x : Ext.getBody().getWidth() - 200,
						y : 60,
						// xtype : "displayfield",
						// id : "displaylogin0",
						// value : "<font color=red><b> 用户名：" + userName+
						// "</b></font>"
						xtype : 'label',
						text : '用户名：'/*+realName*/
					}, {
						x : Ext.getBody().getWidth() - 100,
						y : 57,
						xtype : 'button',
						text : '退出系统',
						ref : 'exit'
						// xtype : "displayfield",
					// id : "displaylogin3",
					// value : "<a><font color=red><b>退出系统</b></font></a>",
				}]
		});
