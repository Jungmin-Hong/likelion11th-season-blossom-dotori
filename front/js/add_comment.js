import { axios } from "axios";

const button = document.querySelector("#pencil");
const secret = document.getElementById("check_btn");
const isSecret = secret.checked;

button.addEventListener("submit", (event) => {
  event.preventDefault();

  const content = document.getElementById("commenttext").value;

  axios
    .post("http://localhost:8080/api/board/delivery/comment/{게시글 번호}", {
      content: content,
      isSecret: isSecret,
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
