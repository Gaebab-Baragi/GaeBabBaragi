import axios from "axios";
import './css/ObjectDetectionPage.css'

import React, {useState, useEffect, useRef } from 'react';
// import "./styles.css";

function ObjectDetectionPage() {
  const videoRef = useRef(null);
  // const [filterO, setfilter] = useState('');
  const video = document.getElementById('videoCam');
  const canvas = document.getElementById("canvas");
  const [CanvasState, setCanvasState] = useState('none'); //사
  const [CameraState, setCameraState] = useState(''); //사
  const [answerClass, setanswerClass] = useState('')

  useEffect(() => {
    getWebcam((stream => {
      videoRef.current.srcObject = stream;
    }));
  }, []);

  const getWebcam = (callback) => {
    try {
      const constraints = {
        'video': true,
        'audio': false
      }
      navigator.mediaDevices.getUserMedia(constraints)
        .then(callback);
    } catch (err) {
      console.log(err);
      return undefined;
    }
  };

  function GoToCamera(target) { // 다시 촬영
    const context = canvas.getContext('2d');
    context.scale(-1, 1); // 좌우 반전
    context.translate(-1024, 0); // 좌우 반전
    context.drawImage(video, 0, 0,1024,768);
    setCanvasState('none');
    setCameraState('');
 
    getWebcam((stream => {
      videoRef.current.srcObject = stream;
    }));
  }

  function sreenShot(target) { // 카메라 촬영
    setCanvasState(''); // 켄버스 켜기
    setCameraState('none'); //비디오 끄기
  

    const video = document.getElementById('videoCam');
    const canvas = document.getElementById("canvas");
    const context = canvas.getContext('2d');
    
      context.scale(-1, 1); // 좌우 반전
      context.translate(-1024, 0); // 좌우 반전
      context.drawImage(video, 0, 0, 1024, 768);

      canvas.toBlob((blob) => { //캔버스의 이미지를 파일 객체로 만드는 과정
        //캔버스 크기 조정하자
        // const maxSize = 800;
        // const img = new Image();
        // img.src = URL.createObjectURL(blob);
        // img.onload = () => {
        // const width = img.width;
        // const height = img.height;
        // let newWidth = width;
        // let newHeight = height;

        // if (width > height && width > maxSize) {
        //   newWidth = maxSize;
        //   newHeight = (height / width) * maxSize;
        // } else if (height > width && height > maxSize) {
        //   newHeight = maxSize;
        //   newWidth = (width / height) * maxSize;
        // }
 
        // const canvasResized = document.createElement('canvas');
        // canvasResized.width = newWidth;
        // canvasResized.height = newHeight;
        // const contextResized = canvasResized.getContext('2d');
        // contextResized.drawImage(img, 0, 0, newWidth, newHeight);
    
        // canvasResized.toBlob((resizedBlob) => {
        //   const formData = new FormData();
        //   formData.append('image', resizedBlob, 'fileName.jpeg');
        const formData = new FormData();
        formData.append('image', blob, "fileName.jpeg"); 
          
          for (let key of formData.keys()) {
            console.log(key);
          }
          for (let value of formData.values()) {
            console.log(value);
          }


 
        // Send FormData using Axios
          axios.post('http://localhost:5000/v1/object-detection/yolov5', formData,{
            withCredentials: true,
            headers: {
              'Content-Type': 'multipart/form-data'
          },
        })
          .then(response => {
            console.log('Image uploaded successfully:', response.data);
            setanswerClass(response.data['image_url'])
            console.log(answerClass)
          })
          .catch(error => {
            console.error('Error uploading image:', error);
          });
    // ... rest of the code
  }, 'image/jpeg',0.5);
};

// }, 'image/jpeg');
// }
        

        //
    //     let file = new File([blob], "fileName.jpg", { type: "image/jpeg" })
    //     const uploadFile = [file] //이미지 객체
    
    //  }, 'image/jpeg');
    
     //

    //  const image = canvas.toDataURL(); // 이미지 저장하는 코드
    //  const link = document.createElement("a");
    //  link.href = image;
    //  link.download = "PaintJS[🎨]";
    //  link.click();

      // const s = videoRef.current.srcObject;
      // s.getTracks().forEach((track) => {
      //   track.stop();
      // });
     
  

  return (
 
  <div className = 'grid-container'>
    <div className = 'item-8'>
      {CanvasState === 'none' ?
      <div onClick={sreenShot} style={{display:"flex", justifyContent:"center",alignItems: "center",width:"70px",height:"70px",margin:"10px", borderRadius:"100px",position:"", zIndex :"", bottom:'5%', left:"", cursor:"pointer", backgroundColor:"red"}}>
        <video id="videoCam" ref={videoRef} autoPlay style={{display:CameraState,width:"768px", height:"576px", transform:"rotateY(180deg)"}}  />
        <canvas id="canvas" width="768px" height="576px" style={{display: CanvasState}}></canvas>
        <div style={{textAlign:"center",width:"60px",height:"60px",border:"2px solid", borderRadius:"100px"}}>찰캌</div>
        </div>:
        <div onClick={GoToCamera} style={{display:"flex", justifyContent:"center",alignItems: "center",width:"70px",height:"70px",margin:"10px", borderRadius:"10px",position:"", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:"red"}}>
          <img src= {answerClass}  alt="" style={{display:CanvasState}}></img>
          <p>다시1 촬영</p>
          {/* <img src="./기본이미지.png" alt=""style={{display:"flex", marginTop:'400px', justifyContent:"center"}} ></img> */}
        </div>    
    }
    </div>
   
    <div className = 'item-4'> 재료 들어가라 </div>
    


  

  </div>
  )};

export default ObjectDetectionPage;
