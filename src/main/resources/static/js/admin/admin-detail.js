$(function() {
  AdminDetailModule.init()
})

const AdminDetailModule = (function() {
  const settings = {
    $button: {
      editForm: $('#btnEditForm'),
      unlock: $('#btnUnlock'),
      failCnt: $('#btnFailCnt')
    },
    EDIT_TYPE: {
      UNLOCK: 'unlock',
      FAIL_CNT: 'failcnt'
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
      let adminNo =$(this).closest('div').data('adminNo')
      window.location.href = '/admins/' + adminNo + '/edit'
    })

    /**
     * unlock
     */
    settings.$button.unlock.on('click', function(event) {
      event.preventDefault()
      let adminNo =$(this).closest('div').data('adminNo')
      API.EDIT(adminNo, settings.EDIT_TYPE.UNLOCK)
    })

    /**
     * failCnt
     */
    settings.$button.failCnt.on('click', function(event) {
      event.preventDefault()
      let adminNo =$(this).closest('div').data('adminNo')
      API.EDIT(adminNo, settings.EDIT_TYPE.FAIL_CNT)
    })

  }

  const API = {
    EDIT: function(adminNo, editType) {
      $.ajax({
        type: 'POST',
        url: '/admins/' + adminNo + '/edit/' + editType,
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: null,
        dataType: 'json',
        processData: false,
        success: function(response) {
          if (response.code === 200) {
            alert(response.message || '정상적으로 처리되었습니다.')
            window.location.reload()
          } else {
            alert(response.message || '처리에 실패했습니다. 다시 시도해 주세요.')
          }
        },
        error: function(response) {
          alert('처리 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.')
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