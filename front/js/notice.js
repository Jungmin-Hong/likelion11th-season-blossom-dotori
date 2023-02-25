// let noticeCount = 0;
// var isFrameOpen = false;
// var myFrame = document.getElementById("noticeFrame");

// document.getElementById("dotori").addEventListener("click", function () {
//   // Iframe 창 열기/닫기
//   if (isFrameOpen) {
//     myFrame.style.display = "none";
//     isFrameOpen = false;
//   } else {
//     myFrame.style.display = "block";
//     isFrameOpen = true;
//   }
// });

// var iframeWindow = myFrame.contentWindow;

// let iframeDocument = iframeWindow.document;
// window.onload = function () {
//   const noticeContent = document.querySelector("#noticeFrame");
//   console.log(noticeContent);
//   axios.defaults.withCredentials = true;
//   axios
//     .get("http://localhost:8080/api/user/notice")
//     .then(function (response) {
//       const notices = response.data;
//       noticeCount = notices.length;
//       const noticeLength = document.querySelector("#noticeLength");
//       noticeLength.innerText = noticeCount;
//       notices.forEach((notice) => {
//         const postType = notice.postType;
//         const postId = notice.postId;
//         let noticeUrl;
//         if (postType === "DELIVERY") {
//           noticeUrl = `../html/deliverydetail.html?postId=${postId}`;
//         } else {
//           noticeUrl = `../html/roommatedetail.html?postId=${postId}`;
//         }
//         const list = document.getElementsByClassName("lists");
//         list.innerHTML += `<div class="show_list"  style="margin-bottom:30px;">
//               <img class="dib" />
//               <div class="list">
//                 <span class="notice-title"><span class="list-title">'${notice.postTitle}' </span> 글에 댓글이 달렸어요!</span>
//                 <a href="${noticeUrl}"><span class="notice-content"><span class="list-writer">익명의 다람쥐</span>
//                 <span class="list-content">:${notice.content}</span></span></a>
//                 <span class="time">1분 전</span>
//               </div>
//           </div>`;
//       });
//     })
//     .catch(function (error) {
//       console.log(error);
//       alert("실패");
//     });
// };