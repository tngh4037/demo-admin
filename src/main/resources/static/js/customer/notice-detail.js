$(function() {
  NoticeDetailModule.init()
})

var NoticeDetailModule = (function() {
  var settings = {
    $button: {
      editForm: $('#btnEditForm'),
    }
  }

  var init = function() {
    bind()
  }

  var bind = function() {

    /**
     * editForm
     */
    settings.$button.editForm.on('click', function(event) {
      event.preventDefault()
      window.location.href = '/customer/notices/'+ $(this).data('noticeNo') +'/edit'
    })
  }

  var API = {
  }

  return {
    init: init
  }
})()