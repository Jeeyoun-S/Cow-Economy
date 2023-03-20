import { getReferenceHTML } from "@/common/function/textSelection";

// api instance 가져오기
// const api = apiInstance();

// [POST /memo] 메모 작성
async function createMemo(publicScope, content, selectionResult) {
  var newMemo = {
    memoContent: content,
    memoPublicScope: publicScope,
    memoStartIndex: selectionResult.startIndex,
    memoEndIndex: selectionResult.endIndex,
    memoStartRange: selectionResult.startRange,
    memoEndRange: selectionResult.endRange
  };

  // api 요청 보내기

  if (newMemo.memoStartIndex != null) {
    const referenceText = getReferenceHTML(newMemo.memoStartRange, newMemo.memoEndRange, newMemo.memoStartIndex, newMemo.memoEndIndex);
    newMemo["referenceText"] = referenceText;
  } else {
    newMemo["referenceText"] = null;
  }

  return Promise.resolve(newMemo);
}

// [POST /memo/{memo_id}] 메모 공개 여부 변경하기
async function updateMemoPublicScope() {
  return Promise.resolve(true);
}

// [DELETE /memo]  메모 삭제하기
async function deleteMemo() {
  return Promise.resolve(true);
}

export { createMemo, updateMemoPublicScope, deleteMemo };
