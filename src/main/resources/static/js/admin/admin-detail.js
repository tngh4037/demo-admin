'use strict'

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
          alert(response.message || '정상적으로 처리되었습니다.')
          window.location.reload()
        },
        error: function(response) {
          if (response.responseJSON === undefined || !response.responseJSON.hasOwnProperty('message')) {
            alert('처리에 실패했습니다. 다시 시도해 주세요.')
          } else {
            alert(response.responseJSON.message)
          }

          if (response.status === 401) {
            window.location.href = "/login"
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