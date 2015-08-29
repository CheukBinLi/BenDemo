Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Loader.setConfig({
				enabled : true
			});
	Ext.application({
				// requires : ['Ext.container.Viewport'],
				name : 'am',
				appFolder : 'resource/app',
				launch : function() {
					Ext.create('Ext.container.Viewport', {
								layout : 'fit',
								items : [{
											xtype : 'managermainlayout'
										}]
							});
				},
				controllers : ['am.manager.controller.ManagerController']
			});
		//
	});