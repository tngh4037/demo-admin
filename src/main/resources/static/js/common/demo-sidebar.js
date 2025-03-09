'use strict'

$(function() {
  DemoSideBarModule._setActiveLink()
})

const DemoSideBarModule = (function() {
  const settings = {
    $a: {
      link: $('a.nav-link[data-multi-page]')
    }
  }

  /**
   * (참고) coreui.bundle.min.js > _setActiveLink
   */
  const _setActiveLink = function() {
    settings.$a.link.each(function() {
      if (window.location.href !== $(this).prop('href') &&
            window.location.href.startsWith($(this).prop('href'))) {
        $(this).closest('li.nav-group').addClass('show').attr('aria-expanded', true)
        $(this).addClass('active')
      }
    })
  }

  return {
    _setActiveLink: _setActiveLink
  }
})()