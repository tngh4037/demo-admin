$(function() {
  FaqAddFormModule.init()
})

const FaqAddFormModule = (function() {
  const settings = {
    $form: {
      addForm: $('#addForm')
    },
    $button: {
      add: $('#btnAdd')
    }
  }

  const init = function() {
    bind()
  }

  const bind = function() {

    /**
     * add
     */
    settings.$button.add.on('click', function(event) {
      event.preventDefault()
      let $question = settings.$form.addForm.find('#question'), questionVal = $.trim($question.val()) || ''
      let $answer = settings.$form.addForm.find('#answer'), answerVal = $.trim($answer.val()) || ''
      let $faqType = settings.$form.addForm.find('#faqType'), faqTypeVal = $.trim($faqType.find('option:selected').val()) || ''
      let displayYnVal = settings.$form.addForm.find('input[name=displayYn]:checked').val() || ''
      let displayTopYnVal = settings.$form.addForm.find('input[name=displayTopYn]:checked').val() || ''

      if (questionVal === '') {
        alert('질문을 입력해 주세요.')
        $question.focus()
        return false
      }

      if (answerVal === '') {
        alert('답변을 입력해 주세요.')
        $question.focus()
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

      API.ADD({
        question: questionVal,
        answer: answerVal,
        faqType: faqTypeVal,
        displayYn: displayYnVal,
        displayTopYn: displayTopYnVal
      })
    })
  }

  const API = {
    ADD: function(param) {
      $.ajax({
        type: 'POST',
        url: '/customer/faqs/add',
        contentType: 'application/json',
        data: JSON.stringify(param),
        dataType: 'json',
        processData: false,
        success: function(response) {
          if (response.code === 200) {
            alert(response.message || '정상적으로 처리되었습니다.')
            window.location.href = '/customer/faqs'
          } else {
            alert(response.message || '처리에 실패했습니다. 다시 시도해 주세요.')
          }
        },
        error: function(response) {
          alert('처리 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.')
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