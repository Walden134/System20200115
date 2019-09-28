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
const EXPFREQUENCY_KEY = 'expfrequency_key'
const PARAMS_KEY = 'params_key'
const UNCERTAIN_KEY = 'uncertain_key'
const THEORYFREQUENCY_KEY = 'theoryfrequency_key'
const DESIGNP_KEY = "designp_key";
const EX_KEY = "ex_key";
const CV_KEY = "cv_key";
const CS_KEY = "cs_key";

export default {

  readEx() {
    if (localStorage.getItem(EX_KEY))
      return JSON.parse(localStorage.getItem(EX_KEY)); return []
  },
  saveEx(ex) {
    if (!ex)
      localStorage.setItem(EX_KEY, JSON.stringify(ex));
  },
  readCv() {
    if (localStorage.getItem(CV_KEY))
      return JSON.parse(localStorage.getItem(CV_KEY)); return []
  },
  saveCv(cv) {
    if (!cv)
      localStorage.setItem(CV_KEY, JSON.stringify(cv));
  },
  readCs() {
    if (localStorage.getItem(CS_KEY))
      return JSON.parse(localStorage.getItem(CS_KEY)); return []
  },
  saveCs(cs) {
    if (!cs)
      localStorage.setItem(CS_KEY, JSON.stringify(cs));
  },

  readDesignP() {
    if (localStorage.getItem(DESIGNP_KEY))
      return JSON.parse(localStorage.getItem(DESIGNP_KEY)); return []
  },
  saveDesignP(designp) {
    if (!designp)
      localStorage.setItem(DESIGNP_KEY, JSON.stringify(designp));
  },
  readTheoryFrequency() {
    if (localStorage.getItem(THEORYFREQUENCY_KEY))
      return JSON.parse(localStorage.getItem(THEORYFREQUENCY_KEY));
    return []
  },
  saveTheoryFrequency(theoryfrequency) {
    if (!theoryfrequency)
      localStorage.setItem(THEORYFREQUENCY_KEY, JSON.stringify(theoryfrequency));
  },
  readQ() {
    if (localStorage.getItem(UNCERTAIN_KEY))
      return JSON.parse(localStorage.getItem(UNCERTAIN_KEY));
    return []
  },
  saveQ(q) {
    if (!q)
      localStorage.setItem(UNCERTAIN_KEY, JSON.stringify(q));
  },
  readParams() {
    if (localStorage.getItem(PARAMS_KEY))
      return JSON.parse(localStorage.getItem(PARAMS_KEY));
    return []
  },
  saveParams(params) {
    if (!params)
      localStorage.setItem(PARAMS_KEY, JSON.stringify(params))
  },

  readExpFrequency() {
    if (localStorage.getItem(EXPFREQUENCY_KEY))
      return JSON.parse(localStorage.getItem(EXPFREQUENCY_KEY));
    return []
  },
  saveExpFrequency(expfrequency) {
    if (!expfrequency)
      localStorage.setItem(EXPFREQUENCY_KEY, JSON.stringify(expfrequency))
  },

  readPowers() {
    if (localStorage.getItem(POWERS_KEY))
      return JSON.parse(localStorage.getItem(POWERS_KEY));
    return []
  },
  savePowers(powers) {
    if (!powers)
      localStorage.setItem(POWERS_KEY, JSON.stringify(powers))
  },
  readOutputs() {
    if (localStorage.getItem(OUTPUTS_KEY))
      return JSON.parse(localStorage.getItem(OUTPUTS_KEY));
    return []
  },
  saveOutputs(outputs) {
    if (!outputs)
      localStorage.setItem(OUTPUTS_KEY, JSON.stringify(outputs))
  },
  readCategory() {
    if (localStorage.getItem(CATEGORY_KEY))
      return JSON.parse(localStorage.getItem(CATEGORY_KEY));
    return []
  },
  saveCategory(category) {
    if (!category)
      localStorage.setItem(CATEGORY_KEY, JSON.stringify(category))
  },
  readOutputRateList() {
    if (localStorage.getItem(OUTPUTRATELIST_KEY))
      return JSON.parse(localStorage.getItem(OUTPUTRATELIST_KEY))
    return []
  },
  saveOutputRateList(outputratelist) {
    if (!outputratelist)
      localStorage.setItem(OUTPUTRATELIST_KEY, JSON.stringify(outputratelist))
  },
  readOutputRatexAxis() {
    if (localStorage.getItem(OUTPUTRATEXAXIS_KEY))
      return JSON.parse(localStorage.getItem(OUTPUTRATEXAXIS_KEY));
    return []
  },
  saveOutputRatexAxis(outputratexaxis) {
    if (!outputratexaxis)
      localStorage.setItem(OUTPUTRATEXAXIS_KEY, JSON.stringify(outputratexaxis))
  },

  readRiskRes() {
    if (localStorage.getItem(RISKRES_KEY))
      return JSON.parse(localStorage.getItem(RISKRES_KEY));
    return []
  },
  saveRiskRes(riskres) {
    if (!riskres)
      localStorage.setItem(RISKRES_KEY, JSON.stringify(riskres))
  }
}
