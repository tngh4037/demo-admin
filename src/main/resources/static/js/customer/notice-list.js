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
      settings.$form.searchFrm.submit();
    })
  }

  var API = {}

  return {
    init: init
  }
})()