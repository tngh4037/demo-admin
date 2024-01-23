$(function() {
  NoticeListModule.init()
})

var NoticeListModule = (function() {
  var settings = {
    $button: {
      search: $('#btnSearch')
    }
  }

  var init = function() {
    bind()
  }

  var bind = function() {
    settings.$button.search.on('click', function(event) {
      event.preventDefault()
    })
  }

  var API = {}

  return {
    init: init
  }
})()