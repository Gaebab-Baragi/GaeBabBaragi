import axios from "axios";

const current = "deploy";

const URL = {
    local: "http://localhost:8083/api",
    deploy : "https://doggy-yummy.site/api"
};

const instance = axios.create({//백쪽으로 보내는 경우임
    baseURL: `${URL[current]}`,
    headers : {
        'Content-Type' : 'application/json'
    }
})

export {URL, current};
export default instance;
