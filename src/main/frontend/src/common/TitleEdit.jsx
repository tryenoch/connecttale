
export function titleEdit(title) {
  let originalTitle = '';
  let editTitle = '';
  
  let idx1 = 0;
  let idx2 = 0;
  
  // [e북]이 포함되어 있을 경우 삭제
  if (originalTitle.includes('[e북]')) {
    idx1 = originalTitle.indexOf('[e북]');
    
    originalTitle = originalTitle.substring(idx1 + 5);
  }
  
  
  
  
}