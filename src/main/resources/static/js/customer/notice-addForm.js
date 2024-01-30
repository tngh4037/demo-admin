$(function() {
  NoticeAddFormModule.init()
})

var NoticeAddFormModule = (function() {
  var settings = {
    $form: {
      addForm: $('#addForm')
    },
    $button: {
      add: $('#btnAdd'),
    }
  }

  var init = function() {
    bind()
  }

  var bind = function() {

    /**
     * add
     */
    settings.$button.add.on('click', function(event) {
      event.preventDefault()
      settings.$form.addForm.submit()
    })
  }

  var API = {
  }

  return {
    init: init
  }
})()