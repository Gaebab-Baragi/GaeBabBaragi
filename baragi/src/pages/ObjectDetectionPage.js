import axios from "axios";
import './css/ObjectDetectionPage.css'
import {setIngredients, AddIngredients} from '../redux/objectDetectSlice'
import React, {useState, useEffect, useRef } from 'react';
import { useDispatch,useSelector  } from 'react-redux';
// import "./styles.css";
function ObjectDetectionPage() {
  const dispatch = useDispatch();
  const classname = useSelector(state => state.objectDetect.Ingredients);
  const videoRef = useRef(null);
  const forbidden = ['포도','사과']
  const [filterO, setfilter] = useState('');
  const video = document.getElementById('videoCam');
  const canvas = document.getElementById("canvas");
  const [CanvasState, setCanvasState] = useState('none'); //사
  const [CameraState, setCameraState] = useState(''); //사
  const [answerClass, setanswerClass] = useState('');
  const [newIngredient, setNewIngredient] = useState('');
  const uniqueClassname = [...new Set(classname)]
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
    dispatch(setIngredients(''))

    const canvas = document.getElementById("canvas");
    if (canvas) {
      const context = canvas.getContext('2d');
      context.scale(-1, 1); // 좌우 반전
      context.translate(-1024, 0); // 좌우 반전
      context.drawImage(video, 0, 0,1024,512);
    }
      setCanvasState('none');
      setCameraState('');
 
    getWebcam((stream => {
      videoRef.current.srcObject = stream;
    }));
  }

  function sreenShot(target) { // 카메라 촬영
    setCanvasState(''); // 켄버스 켜기
    setCameraState('none'); //비디오 끄기
    setanswerClass('')

    const video = document.getElementById('videoCam');
    const canvas = document.getElementById("canvas");
    const context = canvas.getContext('2d');
    const webcamWidth = video.videoWidth;
    const webcamHeight = video.videoHeight;
    canvas.width = webcamWidth;
    canvas.height = webcamHeight;

      context.scale(-1, 1); // 좌우 반전
      context.translate(-webcamWidth, 0); // 좌우 반전
      context.drawImage(video, 0, 0, webcamWidth, webcamHeight);

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
        // 'https://doggy-yummy.site/v1/object-detection/yolov5' master 입력시  
          axios.post('http://localhost:5000/v1/object-detection/yolov5', formData,{
            withCredentials: true,
            headers: {
              'Content-Type': 'multipart/form-data'
          },
        })
          .then(response => {

            console.log('Image uploaded successfully:', response.data);
            setanswerClass(response.data['image_url'])
            dispatch(setIngredients(response.data['name']))
           

          })
          .catch(error => {
            console.error('Error uploading image:', error);
          });
    // ... rest of the code
  }, 'image/jpeg',1);


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
     
  const s = videoRef.current.srcObject;
  s.getTracks().forEach((track) => {
    track.stop();
  })
};

  return (
  <div className = 'grid-container'>
    <div className = 'item-8'>
      <h2>객체탐지</h2>
      {CanvasState === 'none' ?
      <div style={{display:"", justifyContent:"center",alignItems: "center" , width : '60%',hegiht:'auto', marginLeft:'20%', borderRadius:"100px", bottom:'5%', cursor:"pointer" }}>
        <video id="videoCam" ref={videoRef} autoPlay style={{display:CameraState, border : '2px solid #000', width:'100%', hegiht :'auto' ,transform:"rotateY(180deg)"}}  /> 
        {/* width : 682 , height 682  */}
        <canvas id="canvas" style={{display: CanvasState, width:'60%', marginLeft:'20%', hegiht :'auto' }}></canvas>
        <div onClick={sreenShot} style={{textAlign:"center",justifyContent: 'center', width:"60px",height:"60px",border:"2px solid", borderRadius:"100px", display:'flex', margin:'auto',bottom:'5%'}}>찰캌</div>

        </div>:
        // <div onClick={GoToCamera} style={{display:"", justifyContent:"center",alignItems: "center",width:"682px",margin:"10px", borderRadius:"10px",position:"", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:""}}>
        //   <img src= {answerClass}  alt="" style={{display:CanvasState , width:'682px'}}></img>
        //   {/* <img src="./기본이미지.png" alt=""style={{display:"flex", marginTop:'400px', justifyContent:"center"}} ></img> */}
        // <div style ={{textAlign:"center",justifyContent: 'center', width:"60px",height:"60px",border:"2px solid", borderRadius:"100px", margin:'auto'}}>재촬영</div> 
        // </div>   
        <div onClick={GoToCamera} style={{display:"", justifyContent:"center",alignItems: "center",width:"60%",marginLeft:"20%", borderRadius:"10px",position:"", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:""}}>
          <img src={answerClass} alt="" style={{display:CanvasState, width:'60%', marginLeft:'20%', height:'auto'}}></img>
          <div style={{textAlign:"center", justifyContent: 'center', width:"60px", height:"60px", border:"2px solid", borderRadius:"100px", margin:'auto'}}>재촬영</div> 
      </div>
    }









    </div>
    <div className = 'item-4'>
    <div>
      <h2>재료 목록:</h2>
      {uniqueClassname.length > 0 ? (
          <ul>
            {uniqueClassname.map((value, index) => (
              <li 
              style={{color: forbidden.includes(value) ? 'red' : 'black'}}
              key={index}>{value}</li>
            ))}
          </ul>
        ) 

        
        : (
          <p>데이터가 없습니다.</p>
        )}
        <input type="text" value ={newIngredient} onChange={(e) => setNewIngredient(e.target.value)}/>
    </div>
      <button onClick={() => {
        if (newIngredient.trim() !== '') {
        dispatch(AddIngredients(newIngredient));
        setNewIngredient(''); // 입력 내용 초기화
    }
  }}
>
  재료추가
</button>

    </div>
  

  </div>

  )};

export default ObjectDetectionPage;
