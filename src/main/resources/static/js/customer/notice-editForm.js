$(function() {
  NoticeEditFormModule.init()
})

const NoticeEditFormModule = (function() {
  const settings = {
    $form: {
      editForm: $('#editForm')
    },
    $button: {
      edit: $('#btnEdit'),
    }
  }

  const init = function() {
    bind()
  }

  const bind = function() {

    /**
     * edit
     */
    settings.$button.edit.on('click', function(event) {
      event.preventDefault()
      settings.$form.editForm.submit()
    })
  }

  const API = {
  }

  return {
    init: init
  }
})()