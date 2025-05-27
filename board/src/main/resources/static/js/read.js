// 기본형태
// axios.get().then().catch().finally()
// axios.post().then().catch().finally()
// axios.put().then().catch().finally()
// axios.delete().then().catch().finally()

// 날짜 포맷 함수
const formatDate = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

const replyListElement = document.querySelector(".replyList");
const replyForm = document.querySelector("#replyForm");

// 댓글 보여주기
const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    console.log(res.data);
    const data = res.data;
    console.log("댓글 갯수 ", data.length);

    // 댓글 갯수 수정
    replyListElement.previousElementSibling.querySelector("span").innerHTML = data.length;

    let result = "";
    data.forEach((reply) => {
      result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno} data-email=${reply.replyerEmail}>`;
      result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" />`;
      result += `</div><div class="flex-grow-1 align-self-center"><div>${reply.replyerName}</div>`;
      result += `<div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;

      // 로그인 사용자와 댓글 작성자가 같은지 확인필요
      if (loginUser == reply.replyerEmail) {
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }

      result += `</div></div>`;
    });

    replyListElement.innerHTML = result;
  });
};

// 댓글 삭제
// 삭제 버튼 클릭 시 data-rno 가져오기

document.querySelector(".replyList").addEventListener("click", (e) => {
  // 어느버튼의 이벤트인지??
  console.log(e.target);
  const btn = e.target;

  // rno 가져오기
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log(rno);
  // 댓글 작성자 가져오기
  const replyerEmail = btn.closest(".reply-row").dataset.email;

  // 삭제 or 수정 확인
  if (btn.classList.contains("btn-outline-danger")) {
    // 삭제
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    axios
      .delete(`/replies/${rno}`, {
        data: { replyerEmail: replyerEmail },
        headers: {
          "X-CSRF-TOKEN": csrf,
        },
      })

      .then((res) => {
        console.log(res.data);

        // 삭제 성공 후 댓글리스트 다시 불러오기
        replyList();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    // 수정
    axios.get(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      const data = res.data;

      // replyForm 안에 보여주기
      console.log(replyForm);
      replyForm.rno.value = data.rno;
      replyForm.replyerName.value = data.replyerName;
      replyForm.replyerEmail.value = data.replyerEmail;
      replyForm.text.value = data.text;
    });
  }
});

// 폼 submit => 수정 / 삽입
if (replyForm) {
  replyForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const form = e.target;
    const rno = form.rno.value;

    if (rno) {
      // rno 존재하면 수정

      axios
        .put(`/replies/${rno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data);
          alert("댓글 수정 완료");

          // form 기존 내용 지우기
          replyForm.rno.value = "";
          replyForm.replyerEmail.value = "";
          replyForm.replyerName.value = "";
          replyForm.text.value = "";
          // form 수정된 내용 반영
          replyList();
        });
    } else {
      // rno 없으면 삽입

      axios
        .post(`/replies/new`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data + "댓글 등록");
          alert("댓글 추가 완료");
          // form 기존 내용 지우기
          replyForm.rno.value = "";
          // replyForm.replyerEmail.value = "";
          // replyForm.replyerName.value = "";
          replyForm.text.value = "";
          // form 삽입 내용 반영
          replyList();
        });
    }
  });
}

// 페이지 로드시 호출
replyList();
