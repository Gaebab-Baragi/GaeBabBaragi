import React from 'react';
import './Test.css';

function Test() {
  return (
    <div className="container">
    <div className="cont_box pad_l_60">
      <div id="divMainPhotoUpload" className="cont_pic2">
        <input type="hidden" name="main_photo" id="main_photo" value="" />
        <input type="hidden" name="new_main_photo" id="new_main_photo" value="" />
        <input type="hidden" name="del_main_photo" id="del_main_photo" value="" />
        <div style={{ position: 'absolute', left: '-3000px' }}>
          <input type="file" name="q_main_file" id="q_main_file" file_gubun="main" accept="jpeg,png,gif" style={{ display: 'none', width: '0px', height: '0px', fontSize: '0px' }} />
        </div>
        <div id="divMainPhotoBox" is_over="0">
          {/* <img id="mainPhotoHolder" onClick={browseMainFile} src="https://recipe1.ezmember.co.kr/img/pic_none4.gif" style={{ width: '250px', height: '250px', cursor: 'pointer' }} /> */}
        </div>
      </div>
      <div className="cont_line">
        <p className="cont_tit4">레시피 제목</p>
        <input type="text" name="cok_title" id="cok_title" value="" className="form-control" placeholder="예) 소고기 미역국 끓이기" style={{ width: '610px' }} />
      </div>
      <div className="cont_line pad_b_25">
        <p className="cont_tit4">요리소개</p>
        <textarea name="cok_intro" id="cok_intro" className="form-control step_cont" placeholder="이 레시피의 탄생배경을 적어주세요. 예) 남편의 생일을 맞아 소고기 미역국을 끓여봤어요. 어머니로부터 배운 미역국 레시피를 남편의 입맛에 맞게 고안했습니다." style={{ height: '100px', width: '610px', resize: 'none' }} />
      </div>

      <div className="cont_line pad_b_25">
        <p className="cont_tit4">동영상</p>
        <input type="hidden" name="video_photo" id="video_photo" value="" />
        <input type="hidden" name="new_video_photo" id="new_video_photo" value="" />
        <input type="hidden" name="del_video_photo" id="del_video_photo" value="" />
        <input type="hidden" name="cok_video_src" id="cok_video_src" value="" />
        <textarea name="cok_video_url" id="cok_video_url" className="form-control step_cont" prev_url="" placeholder="동영상이 있으면 주소를 입력하세요.(Youtube,네이버tvcast,다음tvpot 만 가능) 예)http://youtu.be/lA0Bxo3IZmM" style={{ height: '100px', width: '380px', resize: 'none' }} />
        <div style={{ position: 'absolute', left: '-3000px' }}>
          <input type="file" name="q_video_file" id="q_video_file" file_gubun="video" accept="jpeg,png,gif" style={{ display: 'none', width: '0px', height: '0px', fontSize: '0px' }} />
        </div>
        <div id="divVideoPhotoBox" is_over="0" className="thumb_m">
          {/* <img id="videoPhotoHolder" src="https://recipe1.ezmember.co.kr/img/pic_none5.gif" style={{ width: '177px', height: '100px' }} /> */}
        </div>
      </div>

      <div className="cont_line">
        <p className="cont_tit4">카테고리</p>
        <select name="cok_sq_category_4" id="cok_sq_category_4" text="종류별">
          <option value="">종류별</option>
          <option value="63">밑반찬</option>
          <option value="56">메인반찬</option>
          <option value="54">국/탕</option>
          <option value="55">찌개</option>
          {/* 이하 옵션들 */}
        </select>
        <select name="cok_sq_category_2" id="cok_sq_category_2" text="상황별">
          <option value="">상황별</option>
          <option value="12">일상</option>
          <option value="18">초스피드</option>
          <option value="13">손님접대</option>
          <option value="19">술안주</option>
          {/* 이하 옵션들 */}
        </select>
        <select name="cok_sq_category_1" id="cok_sq_category_1" text="방법별">
          <option value="">방법별</option>
          <option value="6">볶음</option>
          <option value="1">끓이기</option>
          <option value="7">부침</option>
          <option value="36">조림</option>
          {/* 이하 옵션들 */}
        </select>
        <span className="guide" style={{ margin: '-22px 0 0 146px' }}>분류를 바르게 설정해주시면, 이용자들이 쉽게 레시피를 검색할 수 있어요.</span>
      </div>
      <div className="cont_line">
        <p className="cont_tit4">요리정보</p>
        인원
        <select name="cok_portion" id="cok_portion" text="인원">
          <option value="">인원</option>
          <option value="1">1인분</option>
          <option value="2">2인분</option>
          <option value="3">3인분</option>
          <option value="4">4인분</option>
          {/* 이하 옵션들 */}
        </select>
        시간
        <select name="cok_time" id="cok_time" text="요리시간">
          <option value="">시간</option>
          <option value="5">5분이내</option>
          <option value="10">10분이내</option>
          <option value="15">15분이내</option>
          <option value="20">20분이내</option>
          {/* 이하 옵션들 */}
        </select>
        난이도
        <select name="cok_degree" id="cok_degree" text="난이도">
          <option value="">난이도</option>
          <option value="1">아무나</option>
          <option value="2">초급</option>
          <option value="3">중급</option>
          <option value="4">고급</option>
          {/* 이하 옵션들 */}
        </select>
      </div>
    </div>
    </div>
  );
}

export default Test;
