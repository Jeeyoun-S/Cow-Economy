
// api instance 가져오기
// const api = apiInstance();

// [POST /memo] 메모 작성
async function updateMemo(newMemo, selectionResult, selectionText) {

  var memo = {
    memoContent: newMemo.memoContent,
    memoPublicScope: newMemo.memoPublicScope,
    memoStartIndex: selectionResult.startIndex,
    memoEndIndex: selectionResult.endIndex,
    memoStartRange: selectionResult.startRange,
    memoEndRange: selectionResult.endRange
  };

  // api 요청 보내기
  if (newMemo.isModify) {
    alert("수정 요청")
    memo.memoId = newMemo.memoId;
  } else {
    alert("등록 요청")
  }

  memo.referenceText = selectionText;

  return Promise.resolve(memo);
}

// [POST /memo/{memo_id}] 메모 공개 여부 변경하기
async function updateMemoPublicScope() {
  return Promise.resolve(true);
}

// [DELETE /memo]  메모 삭제하기
async function deleteMemo(memoId) {
  console.log(memoId);
  return Promise.resolve(true);
}

export { updateMemo, updateMemoPublicScope, deleteMemo };
