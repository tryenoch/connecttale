

/* console에 에러 메세지 띄우기 */
export function errorMessage(unitName, message) {
  console.log(`${unitName}에서 통신 중 오류가 발생했습니다.`);
  console.log(`오류 내용 : ${message}`);
}

/* 안내 메세지 */
export function infoMessage(message) {
  alert(`${message}`);
  /*navigate(-1);*/
}


