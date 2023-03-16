function getSelection() {
  console.log("getSelection");
  var selection = window.getSelection();

  // 선택한 텍스트 문자열
  var text = selection.toString();
  console.log(text);
}

export { getSelection }