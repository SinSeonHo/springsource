// checkbox 가 클릭이 되면
// checkbox value, data-id 가져오기

// 이벤트 버블링 이용 (자식의 이벤트가 부모한테 전달됨)
document.querySelector(".list-group").addEventListener("click", (e) => {
  // 어떤 label 안의 checkbox에서 이벤트가 발생했는지 확인
  const chk = e.target;
  console.log(chk);
  // checkbox 체크, 해제 여부확인
  console.log(chk.checked);

  // id 값 가져오기
  // closest("선택자") : 부모중에서 제일 가까운 요소 찾기
  // data-변수명 속성 가져오기 : dataset.변수명
  const id = chk.closest("label").dataset.id;
  console.log(id);

  // actionForm 찾은 후 값 변경하기
  const actionForm = document.querySelector("#actionForm");
  actionForm.id.value = id;
  actionForm.completed.value = chk.checked;

  // actionForm 스크립트단에서 submit하기
  actionForm.submit();
});

// document.querySelector("[type=submit]");
