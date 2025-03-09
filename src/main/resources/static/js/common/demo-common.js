'use strict'

/**
 * set csrfToken for ajax request
 */
$.ajaxSetup({
  beforeSend: function(xhr) {
    let csrfHeader = $('meta[name="_csrf_header"]').attr('content')
    let csrfToken = $('meta[name="_csrf"]').attr('content')
    xhr.setRequestHeader(csrfHeader, csrfToken)
  }
})

/**
 * Demo Admin 공통 스크립트
 */
const DA = {}

/**
 * 정규식 정의
 * - ADMIN_ID(아이디): 4~16자리 영문 소문자 숫자
 * - ADMIN_PWD(패스워드): 영문 숫자 특수기호 조합 8~16자리
 */
DA.regex = {
  'ADMIN_ID': /^[a-z0-9_]{4,16}$/,
  'ADMIN_PWD': /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/
}