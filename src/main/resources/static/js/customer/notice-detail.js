$(function() {
  NoticeDetailModule.init()
})

const NoticeDetailModule = (function() {
  const settings = {
    $button: {
      editForm: $('#btnEditForm'),
      download: $('button.btnDownload'),
      remove: $('button.btnRemove')
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
      let noticeNo = $(this).data('noticeNo')
      window.location.href = '/customer/notices/' + noticeNo + '/edit'
    })

    /**
     * download
     */
    settings.$button.download.on('click', function(event) {
      event.stopPropagation()
      let noticeNo = $(this).data('noticeNo')
      let noticeFileNo = $(this).data('noticeFileNo')
      window.location.href = '/customer/notices/' + noticeNo + '/files/' + noticeFileNo + '/download'
    })

    /**
     * remove
     */
    settings.$button.remove.on('click', function(event) {
      event.preventDefault()
      let noticeNo = $(this).data('noticeNo')
      let noticeFileNo = $(this).data('noticeFileNo')
      if (confirm('삭제 시 복구할 수 없습니다.\n정말로 삭제하시겠습니까?')) {
        API.REMOVE(noticeNo, noticeFileNo)
      }
    })
  }

  const API = {
    REMOVE: function(noticeNo, noticeFileNo) {
      $.ajax({
        type: 'POST',
        url: '/customer/notices/' + noticeNo + '/files/' + noticeFileNo +
            '/remove',
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: null,
        dataType: 'json',
        processData: false,
        success: function(response) {
          alert(response.message || '정상적으로 처리되었습니다.')
          window.location.reload()
        },
        error: function(response) {
          if (response.responseJSON === undefined || !response.responseJSON.hasOwnProperty('message')) {
            alert('처리에 실패했습니다. 다시 시도해 주세요.')
          } else {
            alert(response.responseJSON.message)
          }
          return false
        },
        complete: function() {
        }
      })
    }
  }

  return {
    init: init
  }
})()