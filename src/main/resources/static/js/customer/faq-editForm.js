'use strict'

$(function() {
  FaqEditFormModule.init()
})

const FaqEditFormModule = (function() {
  const settings = {
    $form: {
      editForm: $('#editForm')
    },
    $button: {
      edit: $('#btnEdit')
    },
    $answerEditor: null
  }

  const init = function() {
    bind()
  }

  const bind = function() {

    /**
     * editor init
     */
    ClassicEditor.create(document.querySelector('#answer'), {
      ckfinder: {
        uploadUrl: '/customer/faqs/images/upload'
      }
    }).then(editor => {
      settings.$answerEditor = editor
      console.log('ckEditor was initialized')
    }).catch(error => {
      console.error(error)
    })

    /**
     * edit
     */
    settings.$button.edit.on('click', function(event) {
      event.preventDefault()
      let faqNo = settings.$form.editForm.find('#faqNo').val()
      let $question = settings.$form.editForm.find('#question'), questionVal = $.trim($question.val()) || ''
      let $answer = settings.$answerEditor, answerVal = $.trim($answer.getData()) || ''
      let $faqType = settings.$form.editForm.find('#faqType'), faqTypeVal = $.trim($faqType.find('option:selected').val()) || ''
      let displayYnVal = settings.$form.editForm.find('input[name=displayYn]:checked').val() || ''
      let displayTopYnVal = settings.$form.editForm.find('input[name=displayTopYn]:checked').val() || ''

      if (questionVal === '') {
        alert('질문을 입력해 주세요.')
        $question.focus()
        return false
      }

      if (answerVal === '') {
        alert('답변을 입력해 주세요.')
        $answer.focus()
        return false
      }

      if (faqTypeVal === '') {
        alert('질문 유형을 선택해 주세요.')
        $faqType.focus()
        return false
      }

      if (displayYnVal === '') {
        alert('노출 여부를 선택해 주세요.')
        return false
      }

      if (displayTopYnVal === '') {
        alert('상단 노출 여부를 선택해 주세요.')
        return false
      }

      API.EDIT(faqNo, {
        question: questionVal,
        answer: answerVal,
        faqType: faqTypeVal,
        displayYn: displayYnVal,
        displayTopYn: displayTopYnVal
      })
    })
  }

  const API = {
    EDIT: function(faqNo, param) {
      $.ajax({
        type: 'POST',
        url: '/customer/faqs/' + faqNo + '/edit',
        headers: {'Accept': 'application/json'},
        contentType: 'application/json',
        data: JSON.stringify(param),
        dataType: 'json',
        processData: false,
        success: function(response) {
          alert(response.message || '정상적으로 처리되었습니다.')
          window.location.href = '/customer/faqs'
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