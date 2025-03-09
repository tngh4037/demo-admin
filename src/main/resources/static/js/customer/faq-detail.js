'use strict'

$(function() {
  FaqDetailModule.init()
})

const FaqDetailModule = (function() {
  const settings = {
    $button: {
      editForm: $('#btnEditForm')
    }
  }

  const init = function() {
    bind()
  }

  const bind = function() {

    /**
     * editor init
     */
    ClassicEditor.create(document.querySelector('#answer'), {
      ckfinder: {
        uploadUrl: '/customer/faqs/images/upload'
      }
    }).then(editor => {
      editor.enableReadOnlyMode('editor')
      console.log('ckEditor was initialized')
    }).catch(error => {
      console.error(error)
    })

    /**
     * editForm
     */
    settings.$button.editForm.on('click', function(event) {
      event.preventDefault()
      window.location.href = '/customer/faqs/' + $(this).data('faqNo') + '/edit'
    })
  }

  const API = {
  }

  return {
    init: init
  }
})()