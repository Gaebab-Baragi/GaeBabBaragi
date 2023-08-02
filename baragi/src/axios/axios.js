import axios from "axios";

const current = "local";

const URL = {
    local: "http://localhost:8083",
    deploy : "https://doggy-yummy.site/api"
};

const instance = axios.create({
    baseURL: "http://localhost:8083/api",
    headers : {
        'Content-Type' : 'application/json'
    }
})

export {URL, current};
export default instance;