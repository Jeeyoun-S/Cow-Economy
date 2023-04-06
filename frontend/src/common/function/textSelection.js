// import memoStore from "@/store/modules/memoStore";
import store from '@/store/index';

/**
 * selection 이벤트 함수
 */
// async function addSelectionEvent() {
//   var selection = window.getSelection();
//   // console.log(store.getters['memoStore/getMemoBtn'])
//   console.log(selection.getRangeAt(0))
//   if (!store.getters['memoStore/getMemoBtn']) {
//     store.dispatch("memoStore/changeMemoBtn");
//     console.log(selection)
//     console.log("getSelection1", selection.getRangeAt(0).toString())
//   }
//   // if (selection.getRangeAt(0).toString().length > 0) {
//   // memoStore.state.memoBtn = !selection.isCollapsed;
//   // }
//   return await Promise.resolve(true);
// }

/** 
 * 드래그해서 선택된 텍스트의 정보를 가져오는 함수r
*/
function getSelection() {
  var selection = window.getSelection();
  if (!selection) {
    selection = document.getSelection();
  }
  // console.log("getSelection2", selection.getRangeAt(0).toString())

  // if (selection.getRangeAt(0).toString().length > 0) {
  // 스크롤 위치 시작 index, 끝 index, 시작 위치, 끝 위치
  var result = {
    "text": null,
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

  if (result.startNode.parentElement.tagName == "SPAN") {
    result.startNode = result.startNode.parentElement;
  }
  if (result.endNode.parentElement.tagName == "SPAN") {
    result.endNode = result.endNode.parentElement;
  }

  // 요소를 반복하며 마지막 node와 시작 node 인덱스 값 저장
  for (var i = 0; i < contents.length; i++) {
    var node = contents[i];
    if (!result.startRange && node === result.startNode) result.startRange = i;
    if (!result.endRange && node === result.endNode) result.endRange = i;
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

  // 거꾸로 선택된 경우를 고려
  if (result.startRange > result.endRange) {
    result.startIndex = selection.focusOffset;
    result.endIndex = selection.baseOffset;
    const startRange = result.startRange;
    const endRange = result.endRange;
    result.startRange = endRange;
    result.endRange = startRange;
  } else if (result.startRange == result.endRange) {
    result.startIndex = Math.min(selection.focusOffset, selection.baseOffset);
    result.endIndex = Math.max(selection.focusOffset, selection.baseOffset);
  }

  result.text = getReferenceHTML(result.startRange, result.endRange, result.startIndex, result.endIndex);

  return result;
  // }
  // return null;
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
function getReferenceHTML(startRange, endRange, startIndex, endIndex, text) {

  // truncate를 위해 100자 넘어가는 경우
  var going = true;

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = null;
  if (text) {
    var newOne = document.createElement("div");
    newOne.innerHTML = text;
    contents = newOne.childNodes;
  }
  else contents = document.getElementById("article").childNodes;

  // 인용문의 outerHTML 가져오기
  var reference = "";

  // element 범위 반복하기
  for (var i = startRange; i <= endRange; i++) {
    // k 인덱스의 element 가져오기
    var item = contents[i];

    // 해당 element가 text만 있는 경우
    if (item.nodeType === 3 || item.nodeName == "SPAN") {
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

      if (going && reference.length > 100) {
        reference = reference.slice(0, 100) + "@@@" + reference.slice(100);
        going = false;
      }
    }
    // 그 외의 경우
    else {
      // HTML 그대로 문자열 변환 후 추가
      if (item.nodeName == "BR") {
        // reference += "<br />";
        reference += String(item.outerHTML);
      }
      if (going && reference.length > 80) {
        reference += "@@@";
        going = false;
      }
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

  // 기존에 존재하는 highlight 삭제
  removeHighlightReference();

  // vuex에 저장
  // memoStore.state.highlightReference.startRange = startRange;
  store.dispatch("memoStore/updateHightlightStartRange", startRange)
  // memoStore.state.highlightReference.endRange = endRange;
  store.dispatch("memoStore/updateHightlightEndRange", endRange)

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = document.getElementById("article").childNodes;

  // 범위 element 반복
  for (var i = endRange; i >= startRange; i--) {
    var node = contents[i];

    // hightlight가 될 요소 생성
    var highlight = document.createElement("b");
    highlight.id = "highlight-pointer";

    // node가 텍스트로만 이뤄져 있는 경우
    if (node.nodeType === 3) {

      // 시작 element인 경우
      if (i == startRange) {

        // 종료 element인 경우
        if (i == endRange) {
          // vuex에 마지막 노드 저장
          store.dispatch("memoStore/updateHightlightEndNode", node);
          // memoStore.state.highlightReference.endNode = node;
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
      // 종료 element인 경우
      else if (i == endRange) {
        // vuex에 마지막 노드 저장
        // memoStore.state.highlightReference.endNode = node;
        store.dispatch("memoStore/updateHightlightEndNode", node);
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
    } else if (node.nodeName == 'SPAN') {
      const text = node.textContent;
      node.innerText = '';

      // 시작 element인 경우
      if (i == startRange) {

        // 종료 element인 경우
        if (i == endRange) {
          // vuex에 마지막 노드 저장
          // memoStore.state.highlightReference.endNode = node;
          store.dispatch("memoStore/updateHightlightEndNode", node);
          // 0 ~ startIndex까지 넣고,
          node.innerHTML += text.slice(0, startIndex);
          // startIndex ~ endIndex까지 highlight해서 넣고,
          highlight.innerText = text.slice(startIndex, endIndex);
          node.appendChild(highlight);
          // endIndex부터 끝까지 넣기
          node.innerHTML += text.slice(endIndex);
        } else {
          // 0 ~ startIndex까지 넣고,
          node.innerHTML += text.slice(0, startIndex);
          // startIndex부터 끝까지 highlight해서 넣기
          highlight.innerText = text.slice(startIndex);
          node.appendChild(highlight);
        }
      }
      // 종료 element인 경우
      else if (i == endRange) {
        // vuex에 마지막 노드 저장
        // memoStore.state.highlightReference.endNode = node;
        store.dispatch("memoStore/updateHightlightEndNode", node)
        // 0 ~ endIndex까지 highlight해서 넣고,
        highlight.innerText = text.slice(0, endIndex);
        node.appendChild(highlight);
        // endIndex부터 끝까지 넣기
        node.innerHTML += text.slice(endIndex);
      } else {
        highlight.innerText = text;
        node.appendChild(highlight);
      }
    }
  }
}

/**
 * 기사 속 형광펜 표시 삭제하기
 */
function removeHighlightReference() {

  // 기사 내용 내의 자식 요소들 가져오기
  var contents = document.getElementById("article").childNodes;

  // vuex에서 정보 가져오기
  const hightlightReference = store.getters['memoStore/getHighlightReference'];
  var startRange = hightlightReference.startRange;
  var endRange = hightlightReference.endRange;
  var endNode = hightlightReference.endNode;

  // var startRange = memoStore.state.highlightReference.startRange;
  // var endRange = memoStore.state.highlightReference.endRange;
  // var endNode = memoStore.state.highlightReference.endNode;

  if (startRange >= 0 && endRange >= 0 && !!endNode) {

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
        if (tag == 'B') {
          // 일반 text로 바꿔서 넣기
          insertBefore(target.parentElement, document.createTextNode(target.textContent), target);
          // highlight 삭제
          target.remove();
        } else if (tag == 'SPAN') {
          var text = '';
          const children = target.childNodes;
          for (var j = 0; j < children.length; j++) {
            text += children[j].textContent;
          }
          target.innerText = text;
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

  // event 삭제
  document.removeEventListener("mousedown", removeHighlightReference);

  // vuex에서 정보 삭제
  // memoStore.state.highlightReference.startRange = null;
  store.dispatch("memoStore/updateHightlightStartRange", null)
  // memoStore.state.highlightReference.endRange = null;
  store.dispatch("memoStore/updateHightlightEndRange", null)
  // memoStore.state.highlightReference.endNode = null;
  store.dispatch("memoStore/updateHightlightEndNode", null)
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
  window.addEventListener("mousedown", removeHighlightReference);
}

export { getSelection, moveReference, insertBefore, getReferenceHTML, moveReferenceScroll, addHighlightReference, removeHighlightReference }