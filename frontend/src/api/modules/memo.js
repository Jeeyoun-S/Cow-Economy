import { apiInstance } from "../index";

// api instance 가져오기
const api = apiInstance();

// [POST /memo/{articleId} & PUT /memo/{memoId}] 메모 작성
async function updateMemo(newMemo, selectionResult, selectionText, articleId) {
  var memo = {
    memoContent: newMemo.memoContent,
    memoPublicScope: newMemo.memoPublicScope,
    memoStartIndex: selectionResult.startIndex,
    memoEndIndex: selectionResult.endIndex,
    memoStartRange: selectionResult.startRange,
    memoEndRange: selectionResult.endRange,
  };
  var result = null;

  // 수정 상태인 경우
  if (newMemo.isModify) {
    // 수정 요청 API 요청
    await api
      .put(`/memo/${newMemo.memoId}`, JSON.stringify(memo))
      .then((res) => {
        if (res.data.statusCode == 200) {
          result = res.data.data;
          result.referenceText = selectionText;
        }
      })
      .catch();
  } else {
    // 그 외에는 등록 요청 API 요청
    await api
      .post(`/memo/${articleId}`, JSON.stringify(memo))
      .then((res) => {
        if (res.data.statusCode == 200) {
          result = res.data.data;

          var regtime = new Date(result.regtime);
          var year = regtime.getFullYear();
          var month = ('0' + (regtime.getMonth() + 1)).slice(-2);
          var day = ('0' + regtime.getDate()).slice(-2);
          var hours = ('0' + regtime.getHours()).slice(-2);
          var minutes = ('0' + regtime.getMinutes()).slice(-2);
          var seconds = ('0' + regtime.getSeconds()).slice(-2);
          var dateString = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
          result.regtime = dateString;

          result.referenceText = selectionText;
        }
      })
      .catch();
  }

  return await Promise.resolve(result);
}

// [POST /memo/{memo_id}] 메모 공개 여부 변경하기
async function updateMemoPublicScope(memoId) {
  var result = null;
  // 메모 공개 여부 수정 API 요청
  await api.post(`/memo?memoId=${memoId}`).then((res) => {
    if (res.data.statusCode == 200) {
      result = res.data.data;
    }
  });
  return await Promise.resolve(result);
}

// [DELETE /memo]  메모 삭제하기
async function deleteMemo(memoId) {
  var result = false;
  // 메모 삭제 API 요청
  await api.delete(`/memo?memoId=${memoId}`).then((res) => {
    if (res.data.statusCode == 200) {
      result = true;
    }
  });
  return await Promise.resolve(result);
}

export { updateMemo, updateMemoPublicScope, deleteMemo };
