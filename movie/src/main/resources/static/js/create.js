document.querySelector("form").addEventListener("submit", (e) => {
  // 등록 버튼을 누르면 폼 submit 중지
  e.preventDefault();

  // upload.js에서 업로드한 li 정보 수집
  const output = document.querySelectorAll(".uploadResult li");

  // 속성 찾기 : . or getAttribute()
  // data- 찾기 : dataset.속성명
  let result = "";
  output.forEach((obj, idx) => {
    console.log("obj.dataset.uuid : ", obj.dataset.uuid);

    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}"/>`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}"/>`;
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}"/>`;
  });
  // li 태그 정보 수집 후 폼에 append(추가)
  e.target.insertAdjacentHTML("beforeend", result);

  // 폼 전송
  e.target.submit();
});
