window.onload = function () {
  const list = document.querySelector(".list-section");
  axios.defaults.withCredentials = true;

  axios.get("http://localhost:8080/api/board/delivery")
    .then((response) => {
      console.log(response);
      const data = response.data
      console.log(data)
      data.forEach(element => {
        list.innerHTML += `
        <!-- 게시글 추가되는 부분 -->
        <div class="show_list" style="margin-bottom:30px;">
          <a href="./deliverydetail.html?id=${element.id}" id="detail_list01">
            <span class="title">${element.title}</span>
            <div class="list-content">
              <span class="content">${element.content}</span>
            </div>
          </a>
          <img class="dib" />
          <div class="apply">
            <img id="checkimg" src="../static/image/모집중.png" />
            <span class="apply-text">모집 중</span>
          </div>
        </div>
          `;
      });
    })
    .catch((error) => {
      console.log(error);
      alert("실패");
    });
}

