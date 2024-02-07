$(function() {
  AdminEditFormModule.init()
})

const AdminEditFormModule = (function() {
  const settings = {
    $form: {
      editForm: $('#editForm')
    },
    $button: {
      edit: $('#btnEdit')
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
      let adminNo = settings.$form.editForm.find('#adminNo').val()
      let $adminPwd = settings.$form.editForm.find('#adminPwd'), adminPwdVal = $.trim($adminPwd.val()) || ''
      let $adminRePwd = settings.$form.editForm.find('#adminRePwd'), adminRePwdVal = $.trim($adminRePwd.val()) || ''
      let $adminAuth = settings.$form.editForm.find('#adminAuth'), adminAuthVal = $.trim($adminAuth.find('option:selected').val()) || ''
      let $adminStatus = settings.$form.editForm.find('#adminStatus'), adminStatusVal = $.trim($adminStatus.find('option:selected').val()) || ''

      if (adminPwdVal !== '' && !DA.regex.ADMIN_PWD.test(adminPwdVal)) {
        alert('비밀번호는 영문 숫자 특수기호 조합 8~16자리로 입력 가능합니다.')
        $adminPwd.focus()
        return false
      }

      if (adminPwdVal !== '' && adminPwdVal !== adminRePwdVal) {
        alert('비밀번호와 비밀번호 확인 값이 일치하지 않습니다. 다시 확인해 주세요.')
        $adminRePwd.focus()
        return false
      }

      if (adminAuthVal === '') {
        alert('관리자 권한을 지정해 주세요.')
        $adminAuth.focus()
        return false
      }

      if (adminStatusVal === '') {
        alert('관리자 상태를 지정해 주세요.')
        $adminStatus.focus()
        return false
      }

      API.EDIT(adminNo, {
        adminPwd: adminPwdVal,
        adminRePwd: adminRePwdVal,
        adminAuth: adminAuthVal,
        adminStatus: adminStatusVal
      })
    })
  }

  const API = {
    EDIT: function(adminNo, param) {
      $.ajax({
        type: 'POST',
        url: '/admins/' + adminNo + '/edit',
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: JSON.stringify(param),
        dataType: 'json',
        processData: false,
        success: function(response) {
          if (response.code === 200) {
            alert(response.message || '정상적으로 처리되었습니다.')
            window.location.href = '/admins'
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