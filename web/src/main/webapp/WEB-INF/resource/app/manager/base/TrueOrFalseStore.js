Ext.define('am.manager.base.TrueOrFalseStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'name'
					}, {
						name : 'value'
					}],
			data : [{
						name : 'false',
						value : 0
					}, {
						name : 'true',
						value : 1
					}],
			autoLoad : false
		});
