$(function() {
  FaqDetailModule.init()
})

const FaqDetailModule = (function() {
  const settings = {
    $button: {
      editForm: $('#btnEditForm'),
    }
  }

  const init = function() {
    bind()
  }

  const bind = function() {

    /**
     * editForm
     */
    settings.$button.editForm.on('click', function(event) {
      event.preventDefault()
      window.location.href = '/customer/faqs/'+ $(this).data('faqNo') +'/edit'
    })
  }

  const API = {
  }

  return {
    init: init
  }
})()