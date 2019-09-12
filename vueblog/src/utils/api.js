import axios from 'axios'

let base = '';
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      // ret = JSON.parse(JSON.stringify(data));//需要转成json对象
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
      // 'Content-Type': 'application/json;charset=UTF-8'  //这里加上头部信息
    }
  });
}
export const postRequest1 = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params, timeout: 500000, //超时时间设置，单位毫秒
    dataType: "json",
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  });
}
export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}
export const deleteRequest = (url) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`
  });
}
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    data: params,
    timeout: 500000, //超时时间设置，单位毫秒
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    url: `${base}${url}`
  });
}
export const getRequest1 = (url, params) => {
  return axios({
    method: 'get',
    data: params,
    dataType: "json",
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    url: `${base}${url}`
  });
}
