/* eslint-disable */
import React, {useState} from 'react';
import "./ServiceInfo.css"

function ServiceInfo() {
    return (
        <>
        <div>
            <section id="features" className="service-info-features">
                <div className="service-info-header">
                    <h2>서비스 소개</h2>
                    <p>개밥바라기를 통해 반려견 맞춤 메뉴를 선택하고 스트리밍으로 함께 만들며 맛있게 먹는 반려견의 모습을 볼 수 있어요!</p>
                </div>
                <div className="container">
                    <div className="row gy-4 align-items-center features-item" data-aos="fade-up">
                        <div className="col-md-5">
                            <img src="/image/개밥바라기.png" className="img-fluid" alt="" />
                        </div>
                        <div className="col-md-7 content">
                            <h3>나의 반려견에 딱 맞는 레시피 찾기!</h3>
                            <ul>
                                <li><ion-icon name="checkmark-outline"></ion-icon>  가지고 있는 재료를 태그해 해당 재료를 포함하는 레시피를 검색할 수 있어요.</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon>  반려견이 기피하는 재료를 저장해두고 이를 제외한 레시피를 검색할 수 있어요.</li>
                                <li><ion-icon name="checkmark-outline"></ion-icon>  반려견을 여러 마리 등록하고 선택해 모두의 취향에 맞는 레시피를 검색할 수 있어요.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        </>
)};

export default ServiceInfo;