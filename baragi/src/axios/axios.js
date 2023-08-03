import axios from "axios";

const current = "local";

const URL = {
    local: "http://localhost:8083/api",
    deploy : "https://doggy-yummy.site/api"
};

const instance = axios.create({//백쪽으로 보내는 경우임
    baseURL: `${URL[current]}`,
    headers : {
        'Access-Control-Allow-Credentials':"true",
        'Content-Type' : 'application/json',
        'Access-Control-Allow-Origin' : '*',
        'Access-Control-Request-Methods' : 'GET, POST, PUT, DELETE, OPTION'
    } 
})

export {URL, current};
export default instance;