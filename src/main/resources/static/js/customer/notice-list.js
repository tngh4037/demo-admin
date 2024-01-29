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
  }

  var API = {}

  var Page = {
    list: function(pageNo) {
      settings.$form.searchFrm.find('input[name=pageNo]').val(pageNo)
      settings.$form.searchFrm.submit()
    }
  }

  return {
    init: init,
    list: Page.list
  }
})()