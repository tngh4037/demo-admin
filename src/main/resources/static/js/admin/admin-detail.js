$(function() {
  AdminDetailModule.init()
})

const AdminDetailModule = (function() {
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
      window.location.href = '/admins/'+ $(this).data('adminNo') +'/edit'
    })
  }

  const API = {
  }

  return {
    init: init
  }
})()