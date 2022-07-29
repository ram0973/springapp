import axios from "axios";

const baseURL = "http://localhost:8080/api";

const axiosInstance = axios.create({
    baseURL: baseURL,
    timeout: 5000,
    headers: {
        Authorization: localStorage.getItem('access_token') 
            ? 'Bearer ' + localStorage.getItem('access_token') 
            : null,
        'Content-type': 'application/json',
        accept: 'application/json',
    },
})