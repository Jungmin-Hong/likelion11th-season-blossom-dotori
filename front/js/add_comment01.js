const writeComment = document.querySelector("form");

writeComment.addEventListener('submit', (event) => {
  event.preventDefault();

  const content = document.getElementById("commenttext").value;
  const isSecret = document.querySelector(".isSecret").checked;

  alert(content);
  console.log(content, isSecret);
});
