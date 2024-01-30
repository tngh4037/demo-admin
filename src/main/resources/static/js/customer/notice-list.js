$(function() {
  NoticeListModule.init()
})

var NoticeListModule = (function() {
  var settings = {
    $form: {
      searchForm: $('#searchForm')
    },
    $button: {
      addForm: $('#btnAddForm'),
      search: $('#btnSearch')
    },
    $nav: {
      pageArea: $('#pageArea')
    }
  }

  var init = function() {
    bind()
  }

  var bind = function() {

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
     * page
     */
    settings.$nav.pageArea.on('click', 'a.page-link', function(event) {
      event.preventDefault()
      settings.$form.searchForm.find('input[name=pageNo]').val($(this).data('pageNo') || 1)
      settings.$form.searchForm.submit()
    })
  }

  var API = {}

  return {
    init: init
  }
})()