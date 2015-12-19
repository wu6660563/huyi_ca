/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {

	 config.filebrowserImageUploadUrl= "ckeditor/uploader.do"; //待会要上传的action或servlet
	 config.height = 400; 
	 config.toolbar = [
	               	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline' ] },
	               	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
	               	{ name: 'insert', items: [ 'Image' ] },
	               	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }
	               ];

	               // Toolbar groups configuration.
	               config.toolbarGroups = [
	               	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
	               	{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
	               	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
	               	{ name: 'forms' },
	               	'/',
	               	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	               	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
	               	{ name: 'links' },
	               	{ name: 'insert' },
	               	'/',
	               	{ name: 'styles' },
	               	{ name: 'colors' },
	               	{ name: 'tools' },
	               	{ name: 'others' },
	               	{ name: 'about' }
	               ];
	
};



