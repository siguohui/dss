
import axios from 'axios';

const service = axios.create({
    baseURL: 'http://117.72.71.62:7001',
    timeout: 5000, // 设置超时时间
    headers: {'Content-Type': 'application/json'}
});

// 添加请求拦截器
service.interceptors.request.use(
    config => {
        // 在发送请求之前做些什么，例如添加 token 到请求头
        const token = localStorage.getItem('token'); // 示例：从本地存储获取 token
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

// 添加响应拦截器
service.interceptors.response.use(
    response => {
        // 处理响应数据
        const res = response.data;
        if (res.code !== 200) {
            // 处理错误
            console.error(res.message);
            return Promise.reject(new Error(res.message || 'Error'));
        } else {
            return res;
        }
    },
    error => {
        // 对响应错误做点什么
        if (error.response.status === 401) {
            // 处理未授权的情况
            // 可能需要重定向到登录页面或者其他操作
        }
        return Promise.reject(error);
    }
);

export default service;
