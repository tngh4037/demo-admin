$(function() {
  AdminListModule.init()
})

const AdminListModule = (function() {
  const settings = {
    $form: {
      searchForm: $('#searchForm')
    },
    $button: {
      search: $('#btnSearch'),
      addForm: $('#btnAddForm')
    },
    $a: {
      detail: $('a.btnDetail')
    },
    $modal: {
      detail: $('#adminDetailModal')
    },
    $nav: {
      pageArea: $('#pageArea')
    }
  }

  const init = function() {
    bind()
  }

  const bind = function() {

    /**
     * search
     */
    settings.$button.search.on('click', function(event) {
      event.preventDefault()
      settings.$form.searchForm.find('input[name=pageNo]').val(1)
      settings.$form.searchForm.submit()
    })

    /**
     * addForm
     */
    settings.$button.addForm.on('click', function(event) {
      event.preventDefault()
      window.location.href = '/admins/add'
    })

    /**
     * 상세 정보 조회
     */
    settings.$a.detail.on('click', function(event) {
      event.preventDefault()
      API.FIND($(this).closest('tr').data('adminNo'))
    })

    /**
     * Modal 을 닫을 때, 내부 데이터 삭제
     */
    settings.$modal.detail.on('hidden.coreui.modal', function(event) {
      $(this).find('form').children().remove()
    })

    /**
     * page
     */
    settings.$nav.pageArea.on('click', 'a.page-link', function(event) {
      event.preventDefault()
      let pageNo = $(this).data('pageNo') || 1

      settings.$form.searchForm.find('input[name=pageNo]').val(pageNo)
      settings.$form.searchForm.submit()
    })
  }

  const API = {
    FIND: function(adminNo) {
      $.ajax({
        type: 'POST',
        url: '/admins/' + adminNo,
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: null,
        dataType: 'json',
        processData: false,
        success: function(response) {
          if (response.code === 200) {
            Modal.makeContents(response)
            Modal.show()
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

  const Modal = {
    $context: new coreui.Modal(settings.$modal.detail),
    show : function() {
      this.$context.show()
    },
    hide : function() {
      this.$context.hide()
    },
    makeContents: function(response) {
      let $form = settings.$modal.detail.find('form')
      $form.append(this.getElement('adminId', '아이디', response.data.adminId))
      $form.append(this.getElement('adminAuth', '권한', response.data.adminAuth.title))
      $form.append(this.getElement('adminStatus', '상태', response.data.adminStatus.title))
      $form.append(this.getElement('regDt', '등록일', response.data.regDt))
      $form.append(this.getElement('modDt', '수정일', response.data.modDt))
    },
    getElement: function(id, label, value) {
      let appendHtml = ''
      appendHtml += '<div class="row mb-3">'
      appendHtml += '<label id="D-'+id+'" class="col-md-2 col-form-label">'+label+'</label>'
      appendHtml += '<div class="col-md-10">'
      appendHtml += '<input type="text" id="D-'+id+'" class="form-control" value="'+value+'" disabled>'
      appendHtml += '</div>'
      appendHtml += '</div>'
      return appendHtml
    }
  }

  return {
    init: init
  }
})()