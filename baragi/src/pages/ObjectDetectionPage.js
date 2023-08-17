import axios from "axios";
import './css/ObjectDetectionPage.css'
import {setIngredients,} from '../redux/objectDetectSlice';
import { updateIngredients2,updateIngredients } from "../redux/recipeSearchSlice";
import React, {useState, useEffect, useRef } from 'react';
import { useDispatch,useSelector  } from 'react-redux';
// import '../components/ui/SearchBar.css'
// import "./styles.css";
function ObjectDetectionPage({onValueChange}) {
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
  
  const handleSearch = ()=>{
    const allowClassname = uniqueClassname.filter(value => !forbidden.includes(value));
    onValueChange(1)
    dispatch(setIngredients(''))
    dispatch(updateIngredients2(allowClassname))

  }

  useEffect(() => {
    let stream;
    if (videoRef.current) {
      getWebcam((stream => {
        videoRef.current.srcObject = stream;
        stream.oninactive = () => {
          console.log('Camera stream inactive');
        };
      }));
    }return () => {
      // Clean up by stopping the camera stream when unmounting
      if (stream) {
        const tracks = stream.getTracks();
        tracks.forEach(track => {
          track.stop();
        });
      }
    };
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


  const sendImage = (e)=>{
    setCanvasState(''); // 켄버스 켜기
    setCameraState('none'); //비디오 끄기
    setanswerClass('')
    const image = e.target.files[0]
    const formData = new FormData();
    formData.append('image', image,"fileName.jpeg"); 
    axios.post('https://doggy-yummy.site/v1/object-detection/yolov5', formData,{
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
  }
  
  //

  //

  // useEffect(() => {
  //   getWebcam((stream => {
  //     videoRef.current.srcObject = stream;
  //   }));
  // }, []);

  // const getWebcam = (callback) => {
  //   try {
  //     const constraints = {
  //       'video': true,
  //       'audio': false
  //     }
  //     navigator.mediaDevices.getUserMedia(constraints)
  //       .then(callback);
  //   } catch (err) {
  //     console.log(err);
  //     return undefined;
  //   }
  // };

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
          axios.post('https://doggy-yummy.site/v1/object-detection/yolov5', formData,{
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
  
  
//   const s = videoRef.current.srcObject;
//   s.getTracks().forEach((track) => {
//     track.stop();
//   })
};

  return (
    
  <div className = 'grid-container'>
    <div className='item-1'></div>
    <div className = 'item-6'>
      <h2 className='objectdetection-h2' style={{alignItems:"center"}}>재료 탐색하기</h2>
      {CanvasState === 'none' ?
      <div style={{display:"inherit", justifyContent:"center",alignItems: "center" , width : '100%', height:'70%',  borderRadius:"100px", bottom:'5%', cursor:"pointer" }}>
        <div  className='objectdetection-video'>
          <video id="videoCam" ref={videoRef} autoPlay style={{display:CameraState, border : '1px solid #000', borderRadius:'15px', maxWidth:'100%', maxHeight :'80%' ,transform:"rotateY(180deg)"}}  /> 
        </div>
        <canvas id="canvas" style={{display: CanvasState, width:'90%', maxHeight:'90%' }}></canvas>
        <div className='objectdetection-icon'>
          <div onClick={sreenShot} className='camera-icon'>
            <ion-icon name="camera-outline"></ion-icon>
            <span>사진 촬영</span>
          </div>
          <div className='pick-picture'>
            <label> 
              <ion-icon name="add-outline"></ion-icon>
              <span>사진 첨부</span>
              <input type='file' onChange={sendImage} style={{ display: 'none'}}></input>
            </label>
          </div>
        </div>
        </div>: 
        <div onClick={GoToCamera} style={{display:"", justifyContent:"center",alignItems: "center",width:"60%",marginLeft:"20%", borderRadius:"10px",position:"", zIndex :"101", bottom:'5%', left:"46%", cursor:"pointer", backgroundColor:""}}>
          <img src={answerClass} alt="" style={{display:CanvasState, width:'100%', height:'auto', border : '2px solid #000', borderRadius:'15px'}}></img>
          <div className='objectdetection-icon'>
            <div className='camera-icon'>
              <ion-icon name="camera-outline"></ion-icon>
              <span>재촬영</span>
            </div>
          </div>
      </div>
    }
    </div>
    <div className ='item-2'> 
    </div>
    <div className = 'item-3'>
    <div>
      <h2 className="objectdetection-h2">재료 목록</h2>
      {uniqueClassname.length > 0 ? (
          <ul>
            {uniqueClassname.map((value, index) => (
              <li 
              style={{
                color: forbidden.includes(value) ? 'red' :
                       caution.includes(value) ? 'orange':
                       safe.includes(value)?  'green': 'black'}}
              key={index}>{value}</li>
            ))}
          </ul>
        ) 

        : (
          <p>데이터가 없습니다.
            
          </p>
        )}
 
        {/* <input type="text" value ={newIngredient} onChange={(e) => setNewIngredient(e.target.value)}/> */}
    </div>
    
      <div className="ingredient-btn">
        <button onClick={handleSearch}>레시피 검색</button>
      </div>
      <div className='obejectection-help-list'>
        <div className='objectdetection-help'>
          <ion-icon name="help-circle-outline"></ion-icon>
          <span>도움말</span>
        </div>
        <ul className="objectdetection-help-list-content">
          <li style ={{marginLeft:'5%', color:'red', textAlign : 'left'}}>빨간 색 표시 : 섭취할 수 없는 재료</li>
          <li style ={{marginLeft:'5%', color:'orange', textAlign : 'left'}}>주황 색 표시 : 주의가 필요한 재료</li>
          <li style ={{marginLeft:'5%', color:'green', textAlign : 'left'}}>녹색 색 표시 : 섭취해도 괜찮은 재료</li>
        </ul>
      </div>
    </div>
  </div>



  )};

export default ObjectDetectionPage;
