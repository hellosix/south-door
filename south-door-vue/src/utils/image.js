import axios from 'axios'
const host = 'localhost:8000';

//预上传
export function uploadBefore(md5, size, ext) {
    const url = host + "/iamges/upload/before";
    return axios.post(url, {
        md5: md5,
        size: size,
        ext: ext
    }).then((res) => {
        return Promise.resolve(res)
    })
}
//保存文件
export function saveImage(key) {
    const url = host + "/iamges/site";
    return axios.put(url, {
        key: key
    }).then((res) => {
        return Promise.resolve(res)
    })
}