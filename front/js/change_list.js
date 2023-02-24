import axios from "axios";

const change = document.querySelector("#write-submit");
change.addEventListener("submit", (event) => {
  event.preventDefault();
  axios
    .put("http://localhost:8080/api/board/delivery/edit/{게시글 번호}", {
      title: title,
      content: content,
      deliveryStatus: match,
    })
    .then(function (response) {
      console.log(response);
      window.location.href = "../html/deliverydetail.html";
    })
    .catch(function (error) {
      console.log(error);
      alert("실패");
    });
});
