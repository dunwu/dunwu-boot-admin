import {encrypt} from '@/utils/rsaEncrypt'
import request from '@/utils/request'

export function login(username, password, code, uuid) {
  return request({
    url: 'auth/login',
    method: 'post',
    data: {
      username,
      password,
      code,
      uuid
    }
  })
}

export function logout() {
  return request({
    url: 'auth/logout',
    method: 'post'
  })
}

export function getInfo() {
  return request({
    url: 'auth/info',
    method: 'get'
  })
}

export function getCodeImg() {
  return request({
    url: 'auth/code',
    method: 'get'
  })
}

/**
 * 修改用户：个人中心
 * @param data
 * @returns {*}
 */
export function editCenter(data) {
  return request({
    url: 'api/auth/edit/center',
    method: 'post',
    data
  })
}

/**
 * 修改用户密码
 * @param data
 * @returns {*}
 */
export function editPassword(data) {
  return request({
    url: 'api/auth/edit/password',
    method: 'post',
    data: {
      oldPass: encrypt(data.oldPass),
      newPass: encrypt(data.newPass)
    }
  })
}

/**
 * 修改用户邮箱
 * @param data
 * @returns {*}
 */
export function editEmail(data) {
  return request({
    url: 'api/auth/edit/email/' + data.code,
    method: 'post',
    data: {
      password: encrypt(data.pass),
      email: data.email
    }
  })
}

/**
 * 重置用户邮箱
 * @param data
 * @returns {*}
 */
export function resetEmail(data) {
  return request({
    url: 'api/code/resetEmail?email=' + data,
    method: 'post'
  })
}

export default { login, logout, getInfo, getCodeImg, editCenter, editPassword, editEmail, resetEmail }
