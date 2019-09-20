/*
向local中存储数据的工具模块
1. 向外暴露一个函数(功能)
   只有一个功能需要暴露
2. 向外暴露一个对象(包含多个功能)
   有多个功能需要暴露
 */
const POWERS_KEY = 'powers_key'
const OUTPUTS_KEY = 'outputs_key'
const CATEGORY_KEY = 'category_key'
const OUTPUTRATELIST_KEY = 'outputratelist_key'
const OUTPUTRATEXAXIS_KEY = 'outputratexaxis_key'
const RISKRES_KEY = 'riskres_key'

export default {
  readPowers() {
    return JSON.parse(localStorage.getItem(POWERS_KEY) || '[]')
  },
  savePowers(powers) {
    localStorage.setItem(POWERS_KEY, JSON.stringify(powers))
  },
  readOutputs() {
    return JSON.parse(localStorage.getItem(OUTPUTS_KEY) || '[]')
  },
  saveOutputs(outputs) {
    localStorage.setItem(OUTPUTS_KEY, JSON.stringify(outputs))
  },
  readCategory() {
    return JSON.parse(localStorage.getItem(CATEGORY_KEY) || '[]')
  },
  saveCategory(category) {
    localStorage.setItem(CATEGORY_KEY, JSON.stringify(category))
  },


  readOutputRateList() {
    return JSON.parse(localStorage.getItem(OUTPUTRATELIST_KEY) || '[]')
  },
  saveOutputRateList(outputratelist) {
    localStorage.setItem(OUTPUTRATELIST_KEY, JSON.stringify(outputratelist))
  },
  readOutputRatexAxis() {
    return JSON.parse(localStorage.getItem(OUTPUTRATEXAXIS_KEY) || '[]')
  },
  saveOutputRatexAxis(outputratexaxis) {
    localStorage.setItem(OUTPUTRATEXAXIS_KEY, JSON.stringify(outputratexaxis))
  },

  readRiskRes() {
    return JSON.parse(localStorage.getItem(RISKRES_KEY) || '[]')
  },
  saveRiskRes(riskres) {
    localStorage.setItem(RISKRES_KEY, JSON.stringify(riskres))
  }
}

/*
export function xxx() {

}

export function yyy () {

}*/