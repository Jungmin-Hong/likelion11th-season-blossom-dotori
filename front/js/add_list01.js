const write_container = document.querySelector("#write-submit");
const list = document.querySelector(".list");



write_container.addEventListener('click', (event) => {
  event.preventDefault();
  axios.defaults.withCredentials = true;
  const title = document.querySelector("#titlewrite");

  const store = document.querySelector("#store");
  const place = document.querySelector("#place");
  const amount = document.querySelector("#sum");
  const minimum = document.querySelector("#minimum");
  const content = document.querySelector("#contentwrite");
  console.log(store);

  axios.post('http://localhost:8080/board/delivery/write', {
    title: title,
    store: store,
    place: place,
    amount: amount,
    minimun: minimum,
    content: content,
  }, {
    withCredentials: true
  })
  .then((response) => { // 성공
    const data = response.data
    console.log(data);
    // 게시글 목록으로 이동
    window.location.href = './deliverylist.html';
    alert('글 올리기 완료!');
  })
  .catch((error) => { // 실패
    console.error(error);
    alert('글 올리기 실패');
  });
});
