//배달 게시글 상세조회-

window.onload = function() { 
  const post = document.querySelector(".detail_container");
  const comments = document.querySelector(".detailborder");
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const postId = urlParams.get('id');

  axios.defaults.withCredentials = true;

  axios
    .get("http://localhost:8080/api/board/delivery/" + postId)
    .then((response) => {
      // console.log(response);
      post.innerHTML += `
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

  const data = response.data.comments
  let htmlString = "";
  htmlString += `<div class="writecomment">
  <div id="writeComment">
    <input type="checkbox" class='isSecret' id="check_btn" name="secret" value="비밀" />
    <label for="check_btn"><span>비밀</span></label>

    <input type="text" name="commenttext" id="commenttext" placeholder="댓글을 입력하세요">
    <input type="submit" value="" id="pencil">
  </div>
</div>`;
  console.log(data)
  if (data) {
  data.forEach(comment => {
  htmlString += `
        <div class="showcomment">
          <img class="comment-daramgy">
          <span class="commentwriter">${comment.writer}</span>
          <div class="maincomment">
            <span class="maincomment-detail">${comment.content}</span>
            <img src="../static/image/댓글기능.png" class="commentfunc">
          </div>
          `;
          console.log(comment.childCommentList)
          if (comment.childCommentList) {
          comment.childCommentList.forEach(childComment => {
            // console.log(childComment)
            htmlString += `
            <!-- 대댓글 -->

            <div class="showsubcomment">
              <img class="subcomment-daramgy">
              <span class="subcommentwriter">${childComment.writer}</span>
              <div class="subcomment">
                <span class="subcomment-detail">${childComment.content}</span>
                <img src="../static/image/댓글기능.png" class="subcommentfunc">
              </div>
            </div>
          </div>
            `;
            
          })
        }
      })
      // console.log(htmlString)
    }
    comments.innerHTML += htmlString
          
  
    })
    .catch((error) => {
      console.log(error);
      alert("실패");
    });
}


// const show_list = document.querySelector('.show_list');

// show_list.addEventListener("click", (event) => { 
   
// });
