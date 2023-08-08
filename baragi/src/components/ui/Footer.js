import React from "react";
import "./Footer.css";

function App() {
  return (
    <div className="App">
      <div className="footer__inner">
        <div className="footer__content_first">
          <div className="footer_content left">
            <p className="title">고객센터</p>
            <p className="tel">02-783-9006</p>
            <p className="work_time">
              오전 10시 ~ 오후 5시 (주말, 공휴일 제외)
            </p>
            <div className="footer__sns_link">
              <ul>
                <li>
                  <a
                    href="https://www.youtube.com/channel/UC_hIgz9bSiAp0HT8_lF07vg?view_as=subscriber"
                    target="_blank"
                    rel="noreferrer"
                  ></a>
                </li>
                <li>
                  <a
                    href="http://www.bluerecipe.co.kr"
                    target="_blank"
                    rel="noreferrer"
                  ></a>
                </li>
                <li id="androidIcon">
                  <a
                    href="https://play.google.com/store/apps/details?id=com.sample.recipebank_app"
                    target="_blank"
                    rel="noreferrer"
                  ></a>
                </li>
                <li>
                  <a
                    href="https://apps.apple.com/kr/app/%EB%A0%88%EC%8B%9C%ED%94%BC%EB%B1%85%ED%81%AC/id1546587955"
                    target="_blank"
                    rel="noreferrer"
                  ></a>
                </li>
              </ul>
            </div>
          </div>

          <div className="footer_content right">
            <p className="title">(주)개밥바라기</p>
            <div className="contentus_link">
              <ul>
                <li>회사소개</li>|<li>이용약관</li>|<li>개인정보 처리방침</li>|
                <li>환불정책</li>
              </ul>
            </div>
            <p className="footer__content_sub_txt">
              법인명(상호) : (주)개밥바라기 | 사업자등록번호 : 312-78-01684 |
              벤처기업 : 제 20105551103호
              <br />
              특허 제 10-2210476호 | 통신판매업신고 : 제2016-서울강남-00515호 |
              개인정보보호책임 임영관 이사
              <br />
              주소 : 서울특별시 강남구 언주로 508 14층(역삼동, 서울상록빌딩) | 대표이사 :
              정석목
              <br />
              제휴/협력 문의 : aidk@aidkr.com | 채용문의 : job123@aidkr.com
            </p>
          </div>

          <div className="clear__both"></div>
        </div>

        <div className="footer__content_second">
          <h5 className="footer__info_txt">
            주식회사 개밥바라기는 전자상거래 등에서의 소비자보호에 관한 법률에
            따른 통신판매업과 통신판매중개업을 영위하고 있습니다.
            <br />
            주식회사 개밥바라기는 통신판매중개자로서 중개하는 통신판매에
            관하여서는 통신판매의 당사자가 아니므로 책임을 부담하지 않습니다.
            <br />
            다만, 정산/환불 등에 관한 책임을 '주식회사 개밥바라기' 에서 지고
            있음을 알려드립니다.
            <br />
            담당자 : 정석목 / 연락처 : 1544-9001
          </h5>
        </div>
      </div>
    </div>
  );
}

export default App;
