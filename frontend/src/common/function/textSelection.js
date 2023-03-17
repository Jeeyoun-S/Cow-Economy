import memoStore from "@/store/modules/memoStore";

/**
 * selection 이벤트 함수
 */
function addSelectionEvent() {
  var selection = document.getSelection();
  memoStore.state.memoBtn = !selection.isCollapsed;
}

/** 
 * 드래그해서 선택된 텍스트의 정보를 가져오는 함수
*/
function getSelection() {
  var selection = window.getSelection();

  // 스크롤 위치 시작 index, 끝 index, 시작 위치, 끝 위치
  var result = {
    "text": selection.toString(),
    "startIndex": selection.baseOffset,
    "endIndex": selection.focusOffset,
    "startNode": selection.baseNode,
    "endNode": selection.focusNode,
    "startRange": null,
    "endRange": null
  };

  // article이 있는 곳의 모든 요소 가져오기
  var article = document.getElementById("article")
  var contents = article.childNodes;

  // 요소를 반복하며 마지막 node와 시작 node 인덱스 값 저장
  for (var i = 0; i < contents.length; i++) {
    var node = contents[i];
    if (node === result.startNode) result.startRange = i;
    if (node === result.endNode) result.endRange = i;
  }

  // <br> 또는 공백이 시작 node로 선택된 경우
  if (result.startNode === article) {
    result.startRange = result.startIndex;
    result.startIndex = 0;
  }

  // <br> 또는 공백이 마지막 node로 선택된 경우
  if (result.endNode === article) {
    result.endRange = result.endIndex;
    result.endIndex = 0;
  }

  return result;
}

/** 
 * parentElement 내에
 * 1. referenceNode 존재, referenceNode 앞에 newNode 삽입
 * 2. referenceNode 부재, 맨 뒤에 newNode 삽입
 * 
 * (newNode, referenceNode는 parentElement 내에 존재)
*/
function insertBefore(parentElement, newNode, referenceNode) {
  if (referenceNode) parentElement.insertBefore(newNode, referenceNode);
  else parentElement.appendChild(newNode);
}

/**
 * 입력 받은 인용문의 HTML 가져오기
 * @param {*} startRange 시작 element의 index
 * @param {*} endRange 종료 element의 index
 * @param {*} startIndex 시작 element 내의 시작 index
 * @param {*} endIndex 종료 element 내의 종료 index
 */
function getReferenceHTML(startRange, endRange, startIndex, endIndex) {

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = document.getElementById("article").childNodes;

  // 인용문의 outerHTML 가져오기
  var reference = "";

  // element 범위 반복하기
  for (var i = startRange; i <= endRange; i++) {

    // k 인덱스의 element 가져오기
    var item = contents[i];

    // 해당 element가 text만 있는 경우
    if (item.nodeType === 3) {
      // 시작 위치인 경우
      if (i == startRange) {
        // 시작 위치이면 끝 위치인 경우 : startIndex ~ endIndex
        if (i == endRange) {
          reference += item.textContent.slice(startIndex, endIndex);
        }
        // 끝 위치는 아닌 경우 : startIndex ~ 끝
        else {
          reference += item.textContent.slice(startIndex);
        }
      }
      // 끝 위치인 경우 : 0 ~ endIndex
      else if (i == endRange) {
        reference += item.textContent.slice(0, endIndex);
      }
      // 그 외의 경우 : 전체
      else {
        reference += item.textContent;
      }
    }
    // 그 외의 경우
    else {
      // HTML 그대로 문자열 변환 후 추가
      reference += String(item.outerHTML);
    }
  }

  return reference;
}

/**
 * 인용문의 위치로 스크롤 이동하기
 * @param {*} startRange 시작 element의 index
 */
function moveReferenceScroll(startRange) {

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = document.getElementById("article").childNodes;

  // 이동할 스크롤 위치에 추가할 bookmark 정의
  var bookmark = document.createElement("div");
  bookmark.id = "bookmark-pointer";

  // 시작 노드 가져오기
  var startNode = contents[startRange];

  // 시작 위치 문단 앞에 북마크 넣기
  insertBefore(startNode.parentElement, bookmark, startNode);

  // 북마크 위치로 스크롤 이동
  scrollTo(0, bookmark.offsetTop);

  // 북마크 제거
  bookmark.remove();
}

/**
 * 입력 받은 인용문에 형광펜 표시
 * @param {*} startRange 시작 element의 index
 * @param {*} endRange 종료 element의 index
 * @param {*} startIndex 시작 element 내의 시작 index
 * @param {*} endIndex 종료 element 내의 종료 index
 */
function addHighlightReference(startRange, endRange, startIndex, endIndex) {

  // vuex에 저장
  memoStore.state.highlightReference.startRange = startRange;
  memoStore.state.highlightReference.endRange = endRange;

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = document.getElementById("article").childNodes;

  // 범위 element 반복
  for (var i = endRange; i >= startRange; i--) {
    var node = contents[i];

    // node가 텍스트로만 이뤄져 있는 경우
    if (node.nodeType === 3) {

      // hightlight가 될 요소 생성
      var highlight = document.createElement("span");
      highlight.id = "highlight-pointer";

      // 시작 element인 경우
      if (i == startRange) {

        // 종료 element인 경우
        if (i == endRange) {
          // vuex에 마지막 노드 저장
          memoStore.state.highlightReference.endNode = node;
          // 0 ~ startIndex까지 넣고,
          insertBefore(node.parentElement, document.createTextNode(node.textContent.slice(0, startIndex)), node);
          // startIndex ~ endIndex까지 highlight해서 넣고,
          highlight.innerText = node.textContent.slice(startIndex, endIndex);
          insertBefore(node.parentElement, highlight, node);
          // endIndex부터 끝까지 넣기
          insertBefore(node.parentElement, document.createTextNode(node.textContent.slice(endIndex)), node);
        } else {
          // 0 ~ startIndex까지 넣고,
          insertBefore(node.parentElement, document.createTextNode(node.textContent.slice(0, startIndex)), node);
          // startIndex부터 끝까지 highlight해서 넣기
          highlight.innerText = node.textContent.slice(startIndex);
          insertBefore(node.parentElement, highlight, node);
        }
      }
      // 마지막 element인 경우
      else if (i == endRange) {
        // vuex에 마지막 노드 저장
        memoStore.state.highlightReference.endNode = node;
        // 0 ~ endIndex까지 highlight해서 넣고,
        highlight.innerText = node.textContent.slice(0, endIndex);
        insertBefore(node.parentElement, highlight, node);
        // endIndex부터 끝까지 넣기
        insertBefore(node.parentElement, document.createTextNode(node.textContent.slice(endIndex)), node);
      }
      // 그 외의 경우 (시작 element와 마지막 element 사이)
      else {
        // 전체 hightlight해서 넣기
        highlight.innerText = node.textContent;
        insertBefore(node.parentElement, highlight, node);
      }

      // 새로 넣어줬으니, 기존 node는 삭제
      node.remove();
    }
  }
}

function removeHighlightReference() {

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = document.getElementById("article").childNodes;

  // vuex에서 정보 가져오기
  var startRange = memoStore.state.highlightReference.startRange;
  var endRange = memoStore.state.highlightReference.endRange;
  var endNode = memoStore.state.highlightReference.endNode;

  if (!!startRange && !!endRange && !!endNode) {

    // startRange부터 endRange까지 범위 반복하기
    for (var i = startRange; i <= endRange; i++) {
      var target = contents[i];

      // text만 있는 element인 경우
      if (i == startRange && target.nodeType === 3) {
        // 다음 요소 가져오기 [highlight O]
        var nextTarget = target.nextSibling;

        // hightlight 제거하고, 요소 붙여서 넣기
        insertBefore(target.parentElement, document.createTextNode(target.textContent + nextTarget.innerText), target);

        // 삭제하기
        target.remove();
        nextTarget.remove();
      }

      // 그 외의 경우
      else {
        var tag = target.tagName;
        // highlight된 태그인 경우
        if (tag == 'SPAN') {
          // 일반 text로 바꿔서 넣기
          insertBefore(target.parentElement, document.createTextNode(target.textContent), target);
          // highlight 삭제
          target.remove();

        }
      }

      // 만약 마지막인 경우
      if (i == endRange && endNode != contents[i]) {
        target = contents[i];

        // 다음 element 가져오기
        nextTarget = target.nextSibling;

        // 현재 element랑 다음 element 합치기
        insertBefore(target.parentElement, document.createTextNode(target.textContent + nextTarget.textContent), target);

        // 삭제하기
        nextTarget.remove();
        target.remove();
      }
    }
  }
}

/**
 * 입력 받은 인용문의 위치로 스크롤 이동 및 형광펜 표시
 * @param {*} startRange 시작 element의 index
 * @param {*} endRange 종료 element의 index
 * @param {*} startIndex 시작 element 내의 시작 index
 * @param {*} endIndex 종료 element 내의 종료 index
 */
function moveReference(startRange, endRange, startIndex, endIndex) {
  moveReferenceScroll(startRange);
  addHighlightReference(startRange, endRange, startIndex, endIndex);
  setTimeout(removeHighlightReference, 1000);
}

export { getSelection, addSelectionEvent, moveReference, insertBefore, getReferenceHTML, moveReferenceScroll, addHighlightReference, removeHighlightReference }