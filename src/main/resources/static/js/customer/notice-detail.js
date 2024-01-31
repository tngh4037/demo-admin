$(function() {
  NoticeDetailModule.init()
})

const NoticeDetailModule = (function() {
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
      window.location.href = '/customer/notices/'+ $(this).data('noticeNo') +'/edit'
    })
  }

  const API = {
  }

  return {
    init: init
  }
})()