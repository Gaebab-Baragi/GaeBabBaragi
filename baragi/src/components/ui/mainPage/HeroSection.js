/* eslint-disable */
import './HeroSection.css'
import React, {useState} from 'react';

function HeroSection() {
    return (
        <>
            <section id="hero" className="main-hero">
                <div className="container position-relative">
                    <div className="row gy-5">
                        <div className="col-lg-6 order-2 order-lg-1 d-flex flex-column justify-content-center text-center text-lg-start">
                            <h2>반려견 수제 간식은<br/><span>개밥바라기</span></h2>
                            <p>다양한 레시피들을 살펴보고,<br/>반려견이 즐길만한 요리를 만들어 보는 여정에 도전해 보세요.<br/>사랑하는 강아지의 입맛을 만족시킬 참신한 요리들이 여러분을 기다리고 있습니다!</p>
                            <div className="d-flex justify-content-center justify-content-lg-start">
                                {/* 여기 나중에 영상 url 넣으면 좋을 거 같아서 남겨둠 */}
                                <a href="" className="btn-watch-video d-flex align-items-center">
                                    <ion-icon name="caret-forward-circle-outline"></ion-icon><span>홍보 영상 보러 가기</span>
                                </a>
                            </div>
                        </div>
                        <div className="col-lg-6 order-1 order-lg-2">
                            <img src="/image/메인페이지.png" className="img-fluid" alt="메인 페이지 강아지 사진"/>
                        </div>
                    </div>
                </div>

                <div className="icon-boxes position-relative">
                    <div className="container position-relative">
                        <div className="row gy-4 mt-5">

                        <div className="col-xl-3 col-md-6">
                                <div className="icon-box">
                                    <div className="icon"><ion-icon name="library-outline"></ion-icon></div>
                                    <h4 className="title"><a href="/recipe-register" className="stretched-link"><span>나만의<br/></span>레시피 공유</a></h4>
                                </div>
                            </div>{/* End Icon Box */}

                            <div className="col-xl-3 col-md-6">
                                <div className="icon-box">
                                    <div className="icon"><ion-icon name="videocam-outline"></ion-icon></div>
                                    <h4 className="title"><a href="/streaming-list" className="stretched-link"><span>함께 만들어요!<br/></span>스트리밍 참여</a></h4>
                                </div>
                            </div>{/* End Icon Box */}

                            <div className="col-xl-3 col-md-6">
                                <div className="icon-box">
                                    <div className="icon"><ion-icon name="search-outline"></ion-icon></div>
                                    <h4 className="title"><a href="/recipe-list" className="stretched-link"><span>반려견 맞춤<br/></span>레시피 검색</a></h4>
                                </div>
                            </div>{/* End Icon Box */}

                            <div className="col-xl-3 col-md-6">
                                <div className="icon-box">
                                    <div className="icon"><ion-icon name="camera-outline"></ion-icon></div>
                                    <h4 className="title"><a href="/object-detect" className="stretched-link"><span>어떤 재료를 활용할 수 있을까?<br/></span>재료 확인</a></h4>
                                </div>
                            </div>{/* End Icon Box */}

                        </div>
                    </div>
                </div>
            </section>
        </>
    )};
    
    export default HeroSection;
