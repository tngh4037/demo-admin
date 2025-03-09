'use strict'

$(function() {
  NoticeAddFormModule.init()
})

const NoticeAddFormModule = (function() {
  const settings = {
    $form: {
      addForm: $('#addForm')
    },
    $button: {
      add: $('#btnAdd'),
      filePolicy: $('#btnFilePolicy')
    }
  }

  const init = function() {
    settings.$button.filePolicy.trigger('click')
    bind()
  }

  const bind = function() {

    /**
     * add
     */
    settings.$button.add.on('click', function(event) {
      event.preventDefault()
      settings.$form.addForm.submit()
    })
  }

  const API = {
  }

  return {
    init: init
  }
})()