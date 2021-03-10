import variables from '@/assets/styles/element-variables.scss'
import defaultSettings from '@/settings'

const {
  tagsView,
  fixedHeader,
  sidebarLogo,
  uniqueOpened,
  showFooter,
  showNavbar,
  showSidebar,
  footerTxt
} = defaultSettings

const state = {
  theme: variables.theme,
  showSettings: false,
  tagsView: tagsView,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo,
  uniqueOpened: uniqueOpened,
  showFooter: showFooter,
  showNavbar: showNavbar,
  showSidebar: showSidebar,
  footerTxt: footerTxt
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
