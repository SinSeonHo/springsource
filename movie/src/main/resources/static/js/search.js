// enter 발생 시 searchForm 가져오기
document.querySelector("[name='keyword']").addEventListener("keyup", (e) => {
  if (e.keyCode == 13) {
    // alert("엔터");
    // 폼 가져오기
    const searchForm = document.querySelector("#searchForm");
    // 폼 submit
    searchForm.submit();
  }
});
