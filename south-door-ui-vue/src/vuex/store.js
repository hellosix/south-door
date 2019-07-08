import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import * as Cookies from 'js-cookie'
import { isEmpty } from '../utils/validate';

Vue.use(Vuex)

// 应用初始状态
const state = {
    keywords: '',
    siteList: [],
    groupList: [],
    siteRateMap: {}
}

// 定义所需的 mutations，状态变更操作
const mutations = {
    setKeywords(state, keywords) {
        state.keywords = keywords
    },
    setSiteList(state, siteList) {
        state.siteList = siteList
    },
    setGroupList(state, groupList) {
        state.groupList = groupList
    },
    setSiteRateMap(state, siteRate) {
        state.siteRateMap[siteRate.siteId] = siteRate.rate
    }
}

// 获取数据操作
const getters = {
    getKeywords: state => {
        return state.keywords
    },
    filterSiteList: state => {
        let keywords = state.keywords.toLocaleLowerCase();
        return state.siteList.filter(site => site.siteName.toLocaleLowerCase().includes(keywords)
        ).sort(function (site1, site2) {
            let siteName1 = site1.siteName
            let rate1 = site1.rate
            if (isEmpty(rate1)) {
                rate1 = 0
            }
            let siteName2 = site2.siteName
            let rate2 = site2.rate
            if (isEmpty(rate2)) {
                rate2 = 0
            }
            if (rate1 < rate2) {
                return 1;
            } else if (rate1 > rate2) {
                return -1;
            } else {
                return siteName1.localeCompare(siteName2, 'zh-CN');
            }
        })
    },
    getGroupList: state => {
        return state.groupList
    },
    getSiteRateMap: state => {
        return state.siteRateMap
    },
}

function arrayCompare(site1, site2) {

}

// 分发 Action
const actions = {
    setKeywords(context, keywords) {
        context.commit('setKeywords', keywords)
    },
    setSiteList(context, siteList) {
        context.commit('setSiteList', siteList)
    },
    setGroupList(context, groupList) {
        context.commit('setGroupList', groupList)
    },
    setSiteRateMap(context, siteRate) {
        context.commit('setSiteRateMap', siteRate)
    }
}

// 创建 store 实例
export const store = new Vuex.Store({
    state,
    getters,
    actions,
    mutations,
    plugins: [createPersistedState({
        storage: {
            getItem: key => Cookies.get(key),
            setItem: (key, value) => Cookies.set(key, value, { expires: 36500 }),
            removeItem: key => Cookies.remove(key)
        }
    })]
})
