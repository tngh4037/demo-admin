$(function() {
  NoticeListModule.init()
})

var NoticeListModule = (function() {
  var settings = {
    $form: {
      searchFrm: $('#searchFrm')
    },
    $button: {
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
      settings.$form.searchFrm.find('input[name=pageNo]').val(1)
      settings.$form.searchFrm.submit()
    })

    /**
     * page
     */
    settings.$nav.pageArea.on('click', 'a.page-link', function(event) {
      event.preventDefault()
      settings.$form.searchFrm.find('input[name=pageNo]').val($(this).data('pageNo') || 1)
      settings.$form.searchFrm.submit()
    })
  }

  var API = {}

  return {
    init: init,
  }
})()