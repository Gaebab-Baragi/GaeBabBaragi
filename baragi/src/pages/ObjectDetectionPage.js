import axios from "axios";
import './css/ObjectDetectionPage.css'
import {setIngredients,} from '../redux/objectDetectSlice';
import IngredientTagBar from "../components/ui/IngredientTagBar";
import React, {useState, useEffect, useRef } from 'react';
import { useDispatch,useSelector  } from 'react-redux';
// import '../components/ui/SearchBar.css'
// import "./styles.css";
function ObjectDetectionPage() {
  const dispatch = useDispatch();
  const classname = useSelector(state => state.objectDetect.Ingredients);
  const videoRef = useRef(null);
  const forbidden = ['포도','마늘', '우유', '양파']
  const caution = ['사과','쳥경채','브로콜리','시금치','두부']
  const safe = ['바나나','양배추','당근','닭 가슴살','계란','감자','호박','고구마']
  const [filterO, setfilter] = useState('');
  const video = document.getElementById('videoCam');
  const canvas = document.getElementById("canvas");
  const [CanvasState, setCanvasState] = useState('none'); //사
  const [CameraState, setCameraState] = useState(''); //사
  const [answerClass, setanswerClass] = useState('');
  const [newIngredient, setNewIngredient] = useState('');
  const uniqueClassname = [...new Set(classname)]


  //

  //

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
  const s = videoRef.current.srcObject;
  s.getTracks().forEach((track) => {
    track.stop();
  })
};

  return (
  <div className = 'grid-container'>
    <div className='item-1'></div>
    <div className = 'item-6'>
      <h2>객체탐지</h2>
      {CanvasState === 'none' ?
      <div style={{display:"", justifyContent:"center",alignItems: "center" , width : '100%',hegiht:'auto',  borderRadius:"100px", bottom:'5%', cursor:"pointer" }}>
        <video id="videoCam" ref={videoRef} autoPlay style={{display:CameraState, border : '2px solid #000', borderRadius:'15px', width:'90%', hegiht :'auto' ,transform:"rotateY(180deg)"}}  /> 
        {/* width : 682 , height 682  */}
        <canvas id="canvas" style={{display: CanvasState, width:'90%', hegiht :'auto' }}></canvas>
        <div onClick={sreenShot} style={{backgroundColor : 'red', textAlign:"center",justifyContent: 'center', width:"60px",height:"60px",border:"2px solid", borderRadius:"100px", display:'flex', margin:'auto',bottom:'5%'}}></div>

        </div>: 
        <div onClick={GoToCamera} style={{display:"", justifyContent:"center",alignItems: "center",width:"60%",marginLeft:"20%", borderRadius:"10px",position:"", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:""}}>
          <img src={answerClass} alt="" style={{display:CanvasState, width:'90%', height:'auto'}}></img>
          <div style={{textAlign:"center", justifyContent: 'center', width:"60px", height:"60px", border:"2px solid", borderRadius:"100px", margin:'auto'}}>재촬영</div> 
      </div>
    }
    </div>
    <div className ='item-2'> 
 
    </div>



    <div className = 'item-3'>
    <div>
      <h2>재료 목록:</h2>
      {uniqueClassname.length > 0 ? (
          <ul>
            {uniqueClassname.map((value, index) => (
              <li 
              style={{
                color: forbidden.includes(value) ? 'red' :
                       caution.includes(value) ? 'orange':
                       safe.includes(value)?  '#00FF09': 'black'}}
              key={index}>{value}</li>
            ))}
          </ul>
        ) 

        
        : (
          <p>데이터가 없습니다.</p>
        )}
        <div style ={{width:'80%', marginLeft:'10%', display:'flex'}}>
          <IngredientTagBar style={{}}></IngredientTagBar>  
        </div>
        {/* <input type="text" value ={newIngredient} onChange={(e) => setNewIngredient(e.target.value)}/> */}
    </div>
    <div style = {{border : '2px solid black', width :'80%', hegiht:'auto', borderRadius:'15px', margin : '10% auto 0 auto'}}>
      <li style ={{marginLeft:'5%', color:'red', textAlign : 'left'}}>빨간색 : 금지</li>
      <li style ={{marginLeft:'5%', color:'orange', textAlign : 'left'}}>주황색 : 주의</li>
      <li style ={{marginLeft:'5%', color:'green', textAlign : 'left'}}>녹색 : 허용</li>
    </div>
    <div style={{display: 'flex', justifyContent:'center'}}>
      {/* <button 
      style ={{
        width: '100px',
        height: '40px',
        marginTop: '30px',
        borderRadius: '10px',
        backgroundColor: '#ffaa00',
        color: 'white',
        border: 'none',
        fontWeight: '500'}} 
      onClick={() => {
        if (newIngredient.trim() !== '') {
          dispatch(AddIngredients(newIngredient));
          setNewIngredient(''); // 입력 내용 초기화
    }
  }}
>
  재료추가
</button> */}
<button style ={{
        width: '100px',
        height: '40px',
        marginTop: '30px',
        marginLeft : '10px',
        borderRadius: '10px',
        backgroundColor: '#ffaa00',
        color: 'white',
        border: 'none',
        fontWeight: '500'}} >레시피 검색</button>
        </div>
    </div>
  </div>



  )};

export default ObjectDetectionPage;
