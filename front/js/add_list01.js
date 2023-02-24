const write_container = document.querySelector("#write_container");
const list = document.querySelector(".list");

write_container.addEventListener('submit', (event) => {
  event.preventDefault();

  const title = document.querySelector("#titlewrite");
  const writer = document.querySelector("#writerwrite");

  const store = document.querySelector("#store");
  const place = document.querySelector("#place");
  const amount = document.querySelector("#amount");
  const minimum = document.querySelector("#minimum");
  const content = document.querySelector("#contentwrite");

  axios.post('http://localhost:8080/board/delivery/write', {
    title: title,
    store: store,
    place: place,
    amount: amount,
    minimun: minimum,
    content: content,
  })
  .then((response) => { // 성공
    const data = response.data
    console.log(data)

    data.forEach(element => {
      // 게시글 목록에 추가
      list.innerHTML += `
        <div class="show_list" style="margin-bottom:30px;">
          <a href="./deliverydetail.html?id=${element.id}">
            <span class="title">${element.title}</span>
            <div class="list-content">
              <span class="content">${element.content}</span>
            </div>
          </a>
          <img class="dib" />
          <div class="apply">
            <img id="checkimg" src="../html/images/모집중.png" />
            <span class="apply-text">모집 중</span>
          </div>
        </div>
          `;
    });
    // 게시글 목록으로 이동
    window.location.href = './deliverylist.html';
    alert('글 올리기 완료!');
  })
  .catch((error) => { // 실패
    console.error(error);
    alert('글 올리기 실패');
  });
});
