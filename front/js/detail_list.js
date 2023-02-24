//배달 게시글 상세조회-

window.onload = function() { 
  const comments = document.querySelector(".detail_container");
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const postId = urlParams.get('id');

  axios.defaults.withCredentials = true;

  axios
    .get("http://localhost:8080/api/board/delivery/" + postId)
    .then((response) => {
      console.log(response);
      comments.innerHTML += `
      <div id="inputtitle">
      <div name="titlewrite" id="titlewrite">${response.data.title}</div>
      <div name="writerwrite" id="writerwrite">다람쥐</div>
    </div>
    <div>
      <span class="order"><label for="store">배달시킬 가게(배달 플랫폼)</label><input type="text" name="store" class="orderwrite" placeholder="${response.data.store}" disabled></span>
      <span class="order"><label for="place">픽업 장소</label><input type="text" name="place" class="orderwrite" placeholder="${response.data.place}" disabled></span>
      <span class="order"><label for="sum">작성자 주문 금액</label><span class="price">(<input type="text" name="sum" class="orderprice" placeholder="${response.data.amount}" disabled>)</span>원</span>
      <span class="order"><label for="minimum">최소 주문 금액</label><span class="price">(<input type="text" name="minimum" class="orderprice" placeholder="${response.data.minimum}" disabled>)</span>원</span>
    </div>
    <div name="ordercontent" class="ordercontentshow">${response.data.content}</div>
        
  `;
    })
    .catch((error) => {
      console.log(error);
      alert("실패");
    });

}


/*
      <span class="order"><label for="store">${response.data.store}</label><input type="text" name="store" class="orderwrite" placeholder="내용 입력" disabled></span>
      <span class="order"><label for="place">${response.data.place}</label><input type="text" name="place" class="orderwrite" placeholder="내용 입력" disabled></span>
      <span class="order"><label for="sum">${response.data.amount}</label><span class="price">(<input type="text" name="sum" class="orderprice" disabled>)</span>원</span>
      <span class="order"><label for="minimum">${response.data.minimum}</label><span class="price">(<input type="text" name="minimum" class="orderprice" disabled>)</span>원</span>
*/



// const show_list = document.querySelector('.show_list');

// show_list.addEventListener("click", (event) => { 
   
// });
