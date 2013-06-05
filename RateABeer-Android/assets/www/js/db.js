// Source: http://www.raymondcamden.com/index.cfm/2013/3/4/PhoneGap-Sample--Diary-Database-and-Camera-support
function DBStorage() { that = this; }

DBStorage.prototype.setup = function(callback) {	 

	this.db = window.openDatabase("rate_a_beer", 1, "rate_a_beer", 1000000);
	this.db.transaction(this.initDB, this.dbErrorHandler, this.dbSuccessHandler);

	// Sample data
//	var data = {
//			username : 'u2',
//			name : 'name u2',
//			lastname : 'last name u2',
//			email : 'u2@email',
//			fbid : '0',
//			id : 2
//	}
//	
//	this.saveEntry(data, this.dbSuccessHandler);
}
 
// Generic error handler
DBStorage.prototype.dbErrorHandler = function(e) {
	console.log('DB Error');
	console.dir(JSON.stringify(e));
}

// Generic success handler
DBStorage.prototype.dbSuccessHandler = function(e) {
	console.log('Operation successful!');
	console.dir(JSON.stringify(e));
}
 
// Database structure
DBStorage.prototype.initDB = function(t) {
	t.executeSql('create table if not exists user(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, username TEXT, name TEXT, lastname TEXT, fbid TEXT)');
}
 
DBStorage.prototype.getEntries = function(callback) {
	console.log('Running getEntries');
	if(arguments.length === 1) callback = arguments[0];
 
	this.db.transaction(
		function(t) {
			t.executeSql('select id, email, username, name, lastname, fbid from user order by id asc',[],
				function(t,results) {
					//console.log('getEntries result: ' + JSON.stringify(results));
					callback(that.fixResults(results));
//					return that.fixResults(results)
				}, this.dbErrorHandler);
		}, this.dbErrorHandler);
 
}
 
DBStorage.prototype.getEntry = function(id, callback) {
 
	this.db.transaction(
		function(t) {
			t.executeSql('select email, username, name, lastname, fbid from user where id = ?', [id],
				function(t, results) {
					callback(that.fixResult(results));
				}, this.dbErrorHandler);
			}, this.dbErrorHandler);
 
}
 
DBStorage.prototype.saveEntry = function(data, callback) {
	if (callback == null) {
		callback = this.dbSuccessHandler;
	}
	console.dir("Save entry data: " + data);
	this.db.transaction(
		function(t) {
			t.executeSql('INSERT OR REPLACE INTO user(id,email,username,name,lastname,fbid) values(?,?,?,?,?,?)', 
					[data.id, data.email, data.username, data.name, data.lastname, data.fbid],
					function() { 
						console.log("Saved: " + JSON.stringify(data));
						callback();
					}, this.dbErrorHandler);
		}, this.dbErrorHandler);
}
 
DBStorage.prototype.deleteEntry = function(data, callback) {
	this.db.transaction(
			function(t) {
				t.executeSql('DELETE FROM user WHERE id=?', 
						[data.id],
						function() { 
							callback();
						}, this.dbErrorHandler);
			}, this.dbErrorHandler);
}

//Utility to convert record sets into array of obs
DBStorage.prototype.fixResults = function(res) {
	var result = [];
	for(var i=0, len=res.rows.length; i<len; i++) {
		var row = res.rows.item(i);
		result.push(row);		
	}
	console.log(JSON.stringify(result));
	return result;
}
 
//I'm a lot like fixResults, but I'm only used in the context of expecting one row, so I return an ob, not an array
DBStorage.prototype.fixResult = function(res) {
	if(res.rows.length) {
		return res.rows.item(0);
	} else return {};
}