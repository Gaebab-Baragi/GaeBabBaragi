/* eslint-disable */
import React, {useState} from 'react';
import "./ServiceInfo.css"

function ServiceInfo() {
    return (
        <>
        <div className="main-page">
            <section id="features" className="service-info-features">
                <div className="service-info-header">
                    <h2>서비스 소개</h2>
                    <p>개밥바라기를 통해 반려견 맞춤 메뉴를 선택하고 스트리밍으로 함께 만들며 맛있게 먹는 반려견의 모습을 볼 수 있어요!</p>
                </div>
                <div className="container">
                    <div className="row gy-4 align-items-center features-item">
                        <div className="col-md-5">
                            <img src="/image/개밥바라기.png" className="img-fluid" alt="레시피 검색 페이지" />
                        </div>
                        <div className="col-md-7 content">
                            <h3>나의 반려견에 딱 맞는 레시피 찾기!</h3>
                            <ul>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 가지고 있는 재료를 태그 해 해당 재료를 포함하는 레시피를 검색할 수 있어요.</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 반려견이 기피하는 재료를 저장해두고 이를 제외한 레시피를 검색할 수 있어요.</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 반려견을 여러 마리 등록하고 선택해 모두의 취향에 맞는 레시피를 검색할 수 있어요.</li>
                            </ul>
                        </div>
                    </div> {/* Features Item */}

                    <div className="row gy-4 align-items-center features-item">
                        <div className="col-md-5 order-1 order-md-2">
                            <img src="/image/개밥바라기.png" className="img-fluid" alt="재료 객체 탐지 페이지" />
                        </div>
                        <div className="col-md-7 order-2 order-md-1 content">
                            <h3>사진 촬영을 통한 재료 리스트 얻기!</h3>
                            <p className="sub-title">
                                가지고 있는 재료를 일일이 입력할 필요 없이 사진 한 장만 찍어주세요!
                            </p>
                            <p>
                                개밥바라기가 알아서 재료를 인색해서 검색해 드립니다.<br/>
                                또한 반려견이 섭취할 수 없는 재료가 포함되어 있다면 경고를 드려요.<br/>
                                손쉽게 가지고 있는 재료 중 반려견이 섭취 가능한 재료들로 만들 수 있는 레시피를 추천해 드립니다!
                            </p>
                        </div>
                    </div>{/* Features Item */}

                    <div className="row gy-4 align-items-center features-item">
                        <div className="col-md-5">
                            <img src="/image/개밥바라기.png" className="img-fluid" alt="스트리밍 페이지" />
                        </div>
                        <div className="col-md-7 content">
                            <h3>다 함께 모여서 간식 만들기!</h3>
                            <p className="sub-title">
                            아래와 같은 분들이 함께 모여 간식을 만들 수 있게 스트리밍 룸을 제공해 드려요.<br/>
                            간편하게 스트리밍을 예약하고 여러 사람들과 즐겁게 수제 간식을 만들어봐요.
                            </p>
                            <ul>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 요리는 처음이지만 반려견에게 수제 간식을 만들어 주고 싶으신 분</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 친구들과 모여 즐겁게 요리하고 싶으신 분</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 자신만의 레시피를 여러 사람들에게 자랑하고 싶으신 분</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon> 함께 간식을 만들고 맛있게 먹는 반려견의 모습을 자랑하고 싶으신 분</li>
                            </ul>
                        </div>
                    </div>{/* Features Item */}
                </div>
            </section>
        </div>
        </>
)};

export default ServiceInfo;