'use strict'

$(function() {
  FaqListModule.init()
})

const FaqListModule = (function() {
  const settings = {
    $form: {
      searchForm: $('#searchForm')
    },
    $button: {
      addForm: $('#btnAddForm'),
      search: $('#btnSearch'),
      remove: $('#btnRemove')
    },
    $checkbox: {
      chkAll: $('#chkAll'),
      chkEle: $('input:checkbox[name="chkEle"]')
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
      window.location.href = '/customer/faqs/add'
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
     * remove
     */
    settings.$button.remove.on('click', function(event) {
      event.preventDefault()
      let faqNos = []

      settings.$checkbox.chkEle.each(function (index) {
        if ($(this).is(':checked')) {
          faqNos.push($(this).data('faqNo') || 0)
        }
      })

      if (faqNos.length === 0) {
        alert('삭제할 게시물이 없습니다.')
        return false
      }

      if (confirm(faqNos.length + '개의 글을 삭제하시겠습니까?')) {
        API.REMOVE(faqNos)
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
    REMOVE: function(param) {
      $.ajax({
        type: 'POST',
        url: '/customer/faqs/remove',
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: JSON.stringify(param),
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