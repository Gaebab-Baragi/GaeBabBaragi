import React from "react";
import "./Footer.css";

function Footer() {
  return (
    <div className="footer">
      <div className="container">
        <a href="/" className="logo d-flex align-items-center">
          <p>개밥바라기</p>
        </a>
        <div className="row gy-5">
          <div className="col-lg-5 col-md-12 footer-info">
            <div className="team-info">
              <h4>Contact Us</h4>
              <div className="team">
                <span className="team-name">팀장 박영서:</span>
                <span className="team-email"></span>
                <span className="team-github"></span>
              </div>
              <div className="team">
                <span className="team-name">팀원 김선형: </span>
                <span className="team-email"><ion-icon name="mail-open-outline"></ion-icon>tjsguddl96@gmail.com</span>
                <span className="team-github"></span>
              </div>
              <div className="team">
                <span className="team-name">팀원 김하늘: </span>
                <span className="team-email"></span>
                <span className="team-github"><a href="https://github.com/hanulkimm"><ion-icon name="logo-github"></ion-icon></a></span>
              </div>
              <div className="team">
                <span className="team-name">팀원 박준형: </span>
                <span className="team-email"></span>
                <span className="team-github"></span>
              </div>
              <div className="team">
                <span className="team-name">팀원 배찬일: </span>
                <span className="team-email"></span>
                <span className="team-github"><a href="https://github.com/qocksdlf123"><ion-icon name="logo-github"></ion-icon></a></span>
              </div>
              <div className="team">
                <span className="team-name">팀원 유승아: </span>
                <span className="team-email"><ion-icon name="mail-open-outline"></ion-icon>ysa8497@gmail.com</span>
                <span className="team-github"><a href="https://github.com/SeungAh-Yoo99"><ion-icon name="logo-github"></ion-icon></a></span>
              </div>
            </div>
          </div>

          <div className="col-lg-4 col-md-12 footer-our-project text-md-start">
            <h4>Our Project</h4>
            <p>
              SSAFY 9기 서울 A703 <br/>
              공통 프로젝트 (웹 기술)<br/>
            </p>
            <a href="https://lab.ssafy.com/s09-webmobile1-sub2/S09P12A703"><ion-icon name="logo-gitlab"></ion-icon></a>
          </div>

          <div className="col"></div>

        </div>
      </div>

      <div className="container mt-4">
        <div className="copyleft">
          &copy; Copyleft <strong><span>개밥바라기</span></strong>
        </div>
      </div>
    </div>
  );
}

export default Footer;
