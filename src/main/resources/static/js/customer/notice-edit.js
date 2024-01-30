$(function() {
  NoticeEditModule.init()
})

var NoticeEditModule = (function() {
  var settings = {
    $form: {
      editForm: $('#editForm')
    },
    $button: {
      edit: $('#btnEdit'),
    }
  }

  var init = function() {
    bind()
  }

  var bind = function() {

    /**
     * edit
     */
    settings.$button.edit.on('click', function(event) {
      event.preventDefault()
      settings.$form.editForm.submit()
    })
  }

  var API = {
  }

  return {
    init: init
  }
})()