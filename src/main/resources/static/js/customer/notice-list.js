$(function() {
  NoticeListModule.init()
})

const NoticeListModule = (function() {
  const settings = {
    $form: {
      searchForm: $('#searchForm'),
      removeForm: $('#removeForm')
    },
    $button: {
      addForm: $('#btnAddForm'),
      search: $('#btnSearch'),
      remove: $('#btnRemove')
    },
    $checkbox: {
      chkAll: $('#chkAll'),
      chkEle: $('input:checkbox[name="chkEle"]'),
      chkDisplay: $('.demo-chk')
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
      window.location.href = '/customer/notices/add'
    })

    /**
     * chkAll click
     */
    settings.$checkbox.chkAll.on('click', function(event) {
      if ($(this).is(':checked')) {
        settings.$checkbox.chkEle.prop('checked', true)
      } else {
        settings.$checkbox.chkEle.prop('checked', false)
      }
    })

    /**
     * chkEle click
     */
    settings.$checkbox.chkEle.on('click', function(event) {
      if (!$(this).is(':checked')) {
        settings.$checkbox.chkAll.prop('checked', false)
      }
    })

    /**
     * edit display
     */
    settings.$checkbox.chkDisplay.on('click', function(event) {
      event.preventDefault()
      if (confirm('노출 여부를 변경하시겠습니까?')) {
        API.EDIT_DISPLAY($(this).data('noticeNo'), $(this).data('displayYn') === 'Y' ? 'N' : 'Y')
      }
    })

    /**
     * remove
     */
    settings.$button.remove.on('click', function(event) {
      event.preventDefault()
      let noticeNos = []

      settings.$checkbox.chkEle.each(function (index) {
        if ($(this).is(':checked')) {
          noticeNos.push($(this).data('noticeNo') || 0)
        }
      })

      if (noticeNos.length === 0) {
        alert('삭제할 게시물이 없습니다.')
        return false
      }

      if (confirm(noticeNos.length + '개의 글을 삭제하시겠습니까?')) {
        settings.$form.removeForm.find('#searchParams').val(window.location.search)
        settings.$form.removeForm.find('#noticeNos').val(noticeNos)
        settings.$form.removeForm.submit()
      }
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
    EDIT_DISPLAY: function(noticeNo, displayYn) {
      $.ajax({
        type: 'POST',
        url: '/customer/notices/' + noticeNo + '/edit/display',
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: displayYn,
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