
export function titleEdit(title) {
  let editTitle1 = title;
  let editTitle2 = '';
  
  let idx1 = 0;
  let idx2 = 0;
  
  // [e북]이 포함되어 있을 경우 삭제
  if (editTitle1.includes('[e북]')) {
    idx1 = editTitle1.indexOf('[e북]');
    
    editTitle1 = editTitle1.substring(idx1 + 5);
  }
  
  // (총 n권/완결여부) 또는 (총 n권) 관련 내용이 제일 마지막에 있을 때 지움
  if (editTitle1.includes('(총')) {
    idx1 = editTitle1.lastIndexOf('(총');
    editTitle1 = editTitle1.substring(0, idx1);
  }
  
  // 불필요한 장르명을 나타내는 [BL]이 포함되어 있는 경우
  if (editTitle1.includes('[BL]')) {
    idx1 = editTitle1.indexOf('[BL]');
    
    // 뒤에 붙일 문자열을 먼저 정의
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  if (editTitle1.includes('[로맨스]')) {
    idx1 = editTitle1.indexOf('[로맨스]')
    
    editTitle2 = editTitle1.substring(idx1 + 5);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  if (editTitle1.includes('[판타지]')) {
    idx1 = editTitle1.indexOf('[판타지]')
    
    editTitle2 = editTitle1.substring(idx1 + 5);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  
  // [단행본/할인중] 과 같이 할인정보 추가로 붙어있는 경우는 그대로 유지
  if (editTitle1.includes('[단행본]')) {
    idx1 = editTitle1.indexOf('[단행본]');
    editTitle2 = editTitle1.substring(idx1 + 5);
    editTitle1 = editTitle1.substring(0, idx1);
  }
  else if (editTitle1.includes('(단행본)')) {
    idx1 = editTitle1.indexOf('(단행본)');
    editTitle2 = editTitle1.substring(idx1 + 5);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  if (editTitle1.includes('[19세 완전판]')) {
    idx1 = editTitle1.indexOf('[19세 완전판]');
    editTitle2 = editTitle1.substring(idx1 + 9);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  else if (editTitle1.includes('(19세 완전판)')) {
    idx1 = editTitle1.indexOf('(19세 완전판)');
    editTitle2 = editTitle1.substring(idx1 + 9);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  if (editTitle1.includes('[완결]')) {
    idx1 = editTitle1.indexOf('[완결]');
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  else if (editTitle1.includes('(완결)')) {
    idx1 = editTitle1.indexOf('(완결)');
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  if (editTitle1.includes('[독점]')) {
    idx1 = editTitle1.indexOf('[독점]');
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  else if (editTitle1.includes('(독점)')) {
    idx1 = editTitle1.indexOf('(독점)');
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  if (editTitle1.includes('[연재]')) {
    idx1 = editTitle1.indexOf('[연재]');
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  else if (editTitle1.includes('(연재)')) {
    idx1 = editTitle1.indexOf('(연재)');
    editTitle2 = editTitle1.substring(idx1 + 4);
    editTitle1 = editTitle1.substring(0, idx1);
    editTitle1 = editTitle1 + editTitle2;
  }
  
  editTitle1 = editTitle1.trim();
  
  return editTitle1;
}























