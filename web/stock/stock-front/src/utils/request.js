import service from "@/utils/service.js";

export function get(url, params) {
    return service({
        url: url,
        method: 'get',
        params: params
    });
}

export function post(url, data) {
    return service({
        url: url,
        method: 'post',
        data: data
    });
}


export function put(url, data) {
    return service({
        url: url,
        method: 'put',
        data: data
    });
}


export function del(url) {
    return service({
        url: url,
        method: 'delete'
    });
}

export default service;
