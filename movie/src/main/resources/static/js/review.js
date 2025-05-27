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

// 리뷰 영역 가져오기
const reviewDiv = document.querySelector(".reviewList");
const reviewForm = document.querySelector("#reviewForm");
const reviewCnt = document.querySelector(".review-cnt");

const reviewList = () => {
  // 리뷰 가져오기
  axios.get(`/reviews/${mno}/all`).then((res) => {
    console.log(res.data);
    const data = res.data;

    reviewCnt.innerHTML = data.length;

    let result = "";
    data.forEach((review) => {
      result += `<div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno=${review.rno} data-email=${review.email}>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div><span class="font-semibold">${review.text}</span></div>`;
      result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
      result += `평점 : <span class="grade">${review.grade}</span><div class="starrr"></div></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(review.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;

      // 로그인 사용자와 댓글 작성자가 같은지 확인필요
      if (loginUser == review.email) {
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }
      result += `</div></div>`;
    });

    reviewDiv.innerHTML = result;
  });
};

reviewList();

// 리뷰 삭제 및 수정
reviewDiv.addEventListener("click", (e) => {
  // 어느버튼의 이벤트인지??
  const btn = e.target;

  // rno 가져오기
  const rno = btn.closest(".review-row").dataset.rno;
  console.log(rno);
  // 리뷰 작성자 가져오기
  const email = btn.closest(".review-row").dataset.email;

  // 삭제 or 수정 확인
  if (btn.classList.contains("btn-outline-danger")) {
    // 삭제
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    axios
      .delete(`/reviews/${mno}/${rno}`, {
        // data: { email: email },
        headers: {
          //   "Content-Type": "application/json",
          "X-CSRF-TOKEN": csrf,
        },
      })

      .then((res) => {
        console.log(res.data);

        // 삭제 성공 후 리뷰리스트 다시 불러오기
        reviewList();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    // 수정
    // 리뷰 하나 가져오기
    axios.get(`/reviews/${mno}/${rno}`).then((res) => {
      console.log(res.data);
      const data = res.data;

      // reviewForm 안에 보여주기
      console.log(reviewForm);
      reviewForm.rno.value = data.rno;
      reviewForm.nickname.value = data.nickname;
      // 멤버 아이디 mid
      reviewForm.mid.value = data.mid;
      reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
      reviewForm.text.value = data.text;
    });
  }
});

// 리뷰 등록 및 수정
if (reviewForm) {
  reviewForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const form = e.target;
    const rno = form.rno.value;

    form.grade.value = grade;

    if (rno) {
      // rno 존재하면 수정
      axios
        .put(`/reviews/${mno}/${rno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data);
          alert("리뷰 수정 완료");

          // form 기존 내용 지우기
          reviewForm.rno.value = "";
          reviewForm.text.value = "";
          reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

          // form 수정된 내용 반영
          reviewList();
        });
    } else {
      // rno 없으면 삽입
      axios
        .post(`/reviews/${mno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          alert(res.data + "리뷰 등록");
          // form 기존 내용 지우기
          reviewForm.rno.value = "";
          reviewForm.mid.value = "";
          reviewForm.nickname.value = "";
          reviewForm.text.value = "";
          reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();
          reviewList();
        });
    }
  });
}
