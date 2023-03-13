<template>
  <v-sheet class="pa-5">
    <h4>메모</h4>
    <v-btn
      elevation="0"
      color="var(--main-col-1)"
      @click="moveIndex()"
      dark
      block
      >이동</v-btn
    >
    <div id="article" @mouseup="getSelectedText()">
      <div v-html="html"></div>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetail",
  data() {
    return {
      html: "인천공항을 출발해 필리핀 마닐라로 가려던 대한항공 여객기 좌석 밑에서 실탄 2발이 발견돼 승객 218명이 대피했다. 이 항공기는 애초보다 3시간 늦게 출발했다 인천공항경찰단은 10일 오전 8시쯤 인천공항 제2여객터미널 대한항공 여객기에서 실탄 2발이 발견돼 기내 반입 여부를 조사하고 있다고 밝혔다. 당시 한 승객은 좌석 밑에 떨어져 있는 실탄을 발견해 승무원에게 알려고, 승무원은 112에 신고했다. 이 항공기는 이날 오전 7시 45분 마닐라로 출발할 예정이었으나, 이륙 직전 터미널로 되돌아왔다. 항공기 탄 승객 218명과 승무원 12명 등 230명은 비행기에서 내려 대피했다. 경찰은 실탄이 기내에 유입된 경위 등을 조사하고 있다.<br/> 경찰 대테러 기동팀과 폭발물처리반(EOD)이 투입돼 기내를 수색, 테러 위협이 없는 것으로 판단되자 대한항공은 애초보다 3시간 늦은 오전 11시 5분쯤 여객기를 마닐라로 출발시켰다.인천공항을 출발해 필리핀 마닐라로 가려던 대한항공 여객기 좌석 밑에서 실탄 2발이 발견돼 승객 218명이 대피했다. <br/>이 항공기는 애초보다 3시간 늦게 출발했다 인천공항경찰단은 10일 오전 8시쯤 인천공항 제2여객터미널 대한항공 여객기에서 실탄 2발이 발견돼 기내 반입 여부를 조사하고 있다고 밝혔다. 당시 한 승객은 좌석 밑에 떨어져 있는 실탄을 발견해 승무원에게 알려고, 승무원은 112에 신고했다. 이 항공기는 이날 오전 7시 45분 마닐라로 출발할 예정이었으나, 이륙 직전 터미널로 되돌아왔다. 항공기 탄 승객 218명과 승무원 12명 등 230명은 비행기에서 내려 대피했다. </p><p>경찰은 실탄이 기내에 유입된 경위 등을 조사하고 있다. 경찰 대테러 기동팀과 폭발물처리반(EOD)이 투입돼 기내를 수색, 테러 위협이 없는 것으로 판단되자 대한항공은 애초보다 3시간 늦은 오전 11시 5분쯤 여객기를 마닐라로 출발시켰다.</p>",
      html2: "인<br/>천<br/>공항",
      start: 0,
    };
  },
  methods: {
    getSelectedText() {
      // 선택돼 있는 영역 가져오기
      var selection = document.getSelection();
      console.log(selection);
      var text = selection.toString();

      console.log("selection", selection.getRangeAt(0));
      // var inner = document.getElementById("article").innerHTML;
      // console.log(inner);

      // 선택된 텍스트가 있는 경우
      if (text.length > 0) {
        var { baseOffset: nodeIndex, baseNode: textNode } =
          window.getSelection();

        Array.prototype.some.call(
          document.querySelectorAll("#article *"),
          function (dom) {
            return Array.prototype.some.call(dom.childNodes, function (node) {
              // console.log(">>>>>", node);
              if (node.nodeType !== 3) return false;
              if (node !== textNode)
                return (nodeIndex += node.textContent.length), false;
              return true;
            });
          }
        );

        console.log(nodeIndex);
        this.start = nodeIndex;
      }
    },
    insertBefore(parentElement, newNode, referenceNode) {
      if (referenceNode) parentElement.insertBefore(newNode, referenceNode);
      else parentElement.appendChild(newNode);
    },
    moveIndex() {
      var bookmark = document.getElementById("bookmark-pointer");
      if (bookmark) bookmark.remove();

      var textNode;
      var index = this.start;

      Array.prototype.some.call(
        document.querySelectorAll("#article *"),
        function (dom) {
          return (textNode = Array.prototype.find.call(
            dom.childNodes,
            function (node) {
              if (node.nodeType !== 3) return false;
              if (index >= node.textContent.length)
                return (index -= node.textContent.length), false;
              return true;
            }
          ));
        }
      );

      if (!textNode) throw Error("Index out of bounds");

      bookmark = document.createElement("div");
      bookmark.id = "bookmark-pointer";

      var nextNode = textNode.nextSibling;

      this.insertBefore(textNode.parentElement, bookmark, nextNode);
      this.insertBefore(
        textNode.parentElement,
        document.createTextNode(textNode.textContent),
        nextNode
      );

      textNode.remove();

      // Wait for the changes to be rendered and then scroll to the bookmark
      setTimeout(function () {
        scrollTo(0, bookmark.offsetTop);
      }, 0);
    },
  },
};
</script>

<style></style>
