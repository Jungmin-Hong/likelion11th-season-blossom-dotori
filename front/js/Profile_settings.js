window.onload = function() { 
}
function log(){
    const form = document.querySelector('form');
    const formData = new FormData(form);
    const data = {};
    
    for (let [key, value] of formData.entries()) {
      data[key] = value;
    }

    const birth = data.birth
    const calling = data.call === "calling-y"
    const cleaningCycle = data.cleaningCycle
    const dorm = data.dormName
    const eating = data.eat === "eat-y"
    const floor = data.floor
    const gender = data.gender === "man" 
    const smokeMate = data.roommateSmoke === "roommate-smoke-y"
    const sleepHabits = data.sleepingHabits
    const smoking = data.smoke === "smoke-y"
    const useTime = data.usingTime
    const wantToSay = data.wantToSay


    axios.defaults.withCredentials = true;
    axios.post('http://localhost:8080/api/user/profile', {
        birth: birth,
        calling: calling,
        cleaningCycle: cleaningCycle,
        dorm: dorm,
        eating: eating,
        floor: floor,
        gender: gender,
        smokeMate: smokeMate,
        sleepHabits: sleepHabits,
        smoking: smoking,
        useTime: useTime,
      }, {
        withCredentials: true
      })
        .then((response) => { // 성공
          console.log(response); // test
          alert('작성이 완료 되었습니다.');
        })
        .catch((error) => { // 실패
          console.error(error);
          window.location.href = '#'; // 로그인 페이지 재로딩
          alert('작성실패');
        });
}
