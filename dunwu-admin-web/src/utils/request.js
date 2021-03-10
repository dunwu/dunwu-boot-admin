import axios from 'axios'
import Cookies from 'js-cookie'
import qs from 'qs'
import {Notification} from 'element-ui'
import router from '@/router/routers'
import store from '@/store'
import Config from '@/settings'
import {getToken} from '@/utils/auth'
import Constant from '@/utils/constant'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_API : '/', // api 的 base_url
  timeout: Config.timeout, // 请求超时时间
  // 请求参数序列化
  paramsSerializer(params) {
    return qs.stringify(params, { indices: false })
  }
})

// request拦截器
service.interceptors.request.use(
  config => {
    // console.group('%c%s', 'color:blue', '[Http Request]')
    // console.info('[request info]', config)
    if (getToken()) {
      config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    return config
  },
  error => {
    // Do something with request error
    // console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    // console.info('[response info]', response)
    // console.groupEnd()
    if (response.status < 200 || response.status > 300) {
      Notification.error({ title: response.message, duration: 5000 })
      return Promise.reject('error')
    } else {
      if (!response.data) {
        Notification.error({ title: '应答消息数据部分为空', duration: 5000 })
        return Promise.reject('error')
      }

      if (response.headers['content-type'].indexOf('application/json') !== -1) {
        // 包装过的消息体，形式为：{"code":0,"data":{},"message":"成功","ok":true}
        if (response.data.code !== Constant.SUCCESS) {
          Notification.error({ title: response.data.message, duration: 5000 })
          return Promise.reject('error')
        }

        if (response.data.data) {
          return response.data.data
        } else {
          return true
        }
      } else {
        return response.data
      }
    }
  },
  error => {
    // 兼容blob下载出错json提示
    if (error.response.data instanceof Blob && error.response.data.type.toLowerCase().indexOf('json') !== -1) {
      const reader = new FileReader()
      reader.readAsText(error.response.data, 'utf-8')
      reader.onload = function(e) {
        const errorMsg = JSON.parse(reader.result).message
        Notification.error({ title: errorMsg, duration: 5000 })
      }
    } else {
      let code = 0
      try {
        code = error.response.status
      } catch (e) {
        if (error.toString().indexOf('Error: timeout') !== -1) {
          Notification.error({ title: '网络请求超时', duration: 5000 })
          return Promise.reject(error)
        }
      }
      if (code) {
        if (code === 401) {
          store.dispatch('LogOut').then(() => {
            // 用户登录界面提示
            Cookies.set('point', 401)
            location.reload()
          })
        } else if (code === 403) {
          router.push({ path: '/401' })
        } else {
          const statusText = error.response.statusText
          const errorMsg = error.response.data.message
          if (errorMsg) {
            Notification.error({ title: errorMsg, duration: 5000 })
          } else {
            if (statusText) {
              Notification.error({ title: statusText, duration: 5000 })
            } else {
              Notification.error({ title: '接口请求失败', duration: 5000 })
            }
          }
        }
      } else {
        Notification.error({ title: '接口请求失败', duration: 5000 })
      }
    }
    return Promise.reject(error)
  }
)
export default service
