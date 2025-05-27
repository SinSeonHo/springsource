// 삭제 클릭 시 removeForm 전송

document.querySelector(".move").addEventListener("click", (e) => {
  // a태그 기능 중지
  e.preventDefault();
  console.log(e.target);
  document.querySelector("#removeForm").submit();
});
