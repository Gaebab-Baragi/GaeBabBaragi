import axios from "axios";

import React, {useState, useEffect, useRef } from 'react';
// import "./styles.css";

function ObjectDetectionPage() {
  const videoRef = useRef(null);
  // const [filterO, setfilter] = useState('');
  const video = document.getElementById('videoCam');
  const canvas = document.getElementById("canvas");
  const [CanvasState, setCanvasState] = useState('none'); //사
  const [CameraState, setCameraState] = useState(''); //사
  const [CanvasState2, setCanvasState2] = useState('')
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
    setCanvasState2('');
    getWebcam((stream => {
      videoRef.current.srcObject = stream;
    }));
  }

  function sreenShot(target) { // 카메라 촬영
    setCanvasState(''); // 켄버스 켜기
    setCameraState('none'); //비디오 끄기
    setCanvasState2('none');
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
    <div style={{ position:"absolute", zIndex :"100", width:"1024px", backgroundColor:"white"}} >
    <video id="videoCam" ref={videoRef} autoPlay style={{display:CameraState,width:"1024px", height:"768px", webkitTransform:"rotateY(180deg)"}}  />
    <img src="./기본이미지" alt="" style={{dispaly:CanvasState, marginTop:'400px'}}/>
    <canvas id="canvas" width="1024px" height="768px" style={{display: CanvasState}}></canvas>
    
  {CanvasState === 'none' ?
   <div onClick={sreenShot} style={{display:"flex", justifyContent:"center",alignItems: "center",width:"70px",height:"70px",margin:"10px", borderRadius:"100px",position:"absolute", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:"white"}}>
          <div style={{textAlign:"center",width:"60px",height:"60px",border:"2px solid", borderRadius:"100px"}}></div>
          
      </div>:
      
      <div onClick={GoToCamera} style={{display:"flex", justifyContent:"center",alignItems: "center",width:"70px",height:"70px",margin:"10px", borderRadius:"10px",position:"absolute", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:"white"}}>
        <p>다시1 촬영</p>
        {/* <img src="./기본이미지.png" alt=""style={{display:"flex", marginTop:'400px', justifyContent:"center"}} ></img> */}
      </div>    
  }
  </div>

  )};

export default ObjectDetectionPage;
