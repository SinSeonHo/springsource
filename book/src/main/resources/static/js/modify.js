// 삭제 버튼이 클릭되면 actionForm submit
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  const actionForm = document.querySelector("#actionForm");
  actionForm.submit();
});
