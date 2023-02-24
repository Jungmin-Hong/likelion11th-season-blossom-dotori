/* 찜 */
const dibs = document.querySelectorAll(".dib");
for (let i = 0; i < dibs.length; i++) {
  dibs[i].addEventListener("click", function () {
    this.classList.toggle("active");
  });
}

/* 모집 전 후 */
const match = "";
function toggle() {
  const change_checkimg = document.querySelector("#change-checkimg");
  const change_apply = document.querySelector("#change-apply");
  const change_text = document.querySelector("#change-text");
  const write_checkimg = document.querySelector("#write-checkimg");
  const write_apply = document.querySelector("#write-apply");
  const write_text = document.querySelector("#write-text");
  change_checkimg.classList.toggle("active");
  change_apply.classList.toggle("active");
  change_text.classList.toggle("active");
  write_checkimg.classList.toggle("active");
  write_apply.classList.toggle("active");
  write_text.classList.toggle("active");
  if (change_text.innerText === "모집 중") {
    change_text.innerText = "모집 완료";
    match = "MATCHED";
  } else {
    change_text.innerText = "모집 중";
    match = "MATCHING";
  }
}
