Ext.define("am.manager.controller.ManagerController", {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
			'treepanel' : {
				itemclick : function(treepanel, record, item, index, e, eOpts) {
					var tab = treepanel.up('managermainlayout').down('managercenterlayout');
					tab.add([{text:'aaa'}]);
				}
			}
		});
	},
	views : [ 'am.manager.view.ManagerMainLayout', 'am.manager.view.ManagerCenterLayout', 'am.manager.view.ManagerWestLayout', 'am.manager.view.ManagerTopLayout' ],
	stores : [ 'am.manager.base.TrueOrFalseStore' ]
});