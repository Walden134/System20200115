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
    if (sessionStorage.getItem(EX_KEY))
      return JSON.parse(sessionStorage.getItem(EX_KEY));
    return []
  },
  saveEx(ex) {
    if (ex)
      sessionStorage.setItem(EX_KEY, JSON.stringify(ex));
  },
  readCv() {
    if (sessionStorage.getItem(CV_KEY))
      return JSON.parse(sessionStorage.getItem(CV_KEY));
    return []
  },
  saveCv(cv) {
    if (cv)
      sessionStorage.setItem(CV_KEY, JSON.stringify(cv));
  },
  readCs() {
    if (sessionStorage.getItem(CS_KEY))
      return JSON.parse(sessionStorage.getItem(CS_KEY));
    return []
  },
  saveCs(cs) {
    if (cs)
      sessionStorage.setItem(CS_KEY, JSON.stringify(cs));
  },

  readDesignP() {
    if (sessionStorage.getItem(DESIGNP_KEY))
      return JSON.parse(sessionStorage.getItem(DESIGNP_KEY));
    return []
  },
  saveDesignP(designp) {
    if (designp)
      sessionStorage.setItem(DESIGNP_KEY, JSON.stringify(designp));
  },
  readTheoryFrequency() {
    if (sessionStorage.getItem(THEORYFREQUENCY_KEY))
      return JSON.parse(sessionStorage.getItem(THEORYFREQUENCY_KEY));
    return []
  },
  saveTheoryFrequency(theoryfrequency) {
    if (theoryfrequency)
      sessionStorage.setItem(THEORYFREQUENCY_KEY, JSON.stringify(theoryfrequency));
  },
  readQ() {
    if (sessionStorage.getItem(UNCERTAIN_KEY))
      return JSON.parse(sessionStorage.getItem(UNCERTAIN_KEY));
    return []
  },
  saveQ(q) {
    if (q)
      sessionStorage.setItem(UNCERTAIN_KEY, JSON.stringify(q));
  },
  readParams() {
    if (sessionStorage.getItem(PARAMS_KEY))
      return JSON.parse(sessionStorage.getItem(PARAMS_KEY));
    return []
  },
  saveParams(params) {
    if (params)
      sessionStorage.setItem(PARAMS_KEY, JSON.stringify(params))
  },

  readExpFrequency() {
    if (sessionStorage.getItem(EXPFREQUENCY_KEY))
      return JSON.parse(sessionStorage.getItem(EXPFREQUENCY_KEY));
    return []
  },
  saveExpFrequency(expfrequency) {
    if (expfrequency)
      sessionStorage.setItem(EXPFREQUENCY_KEY, JSON.stringify(expfrequency))
  },

  readPowers() {
    if (sessionStorage.getItem(POWERS_KEY))
      return JSON.parse(sessionStorage.getItem(POWERS_KEY));
    return []
  },
  savePowers(powers) {
    if (powers)
      sessionStorage.setItem(POWERS_KEY, JSON.stringify(powers))
  },
  readOutputs() {
    if (sessionStorage.getItem(OUTPUTS_KEY))
      return JSON.parse(sessionStorage.getItem(OUTPUTS_KEY));
    return []
  },
  saveOutputs(outputs) {
    if (outputs)
      sessionStorage.setItem(OUTPUTS_KEY, JSON.stringify(outputs))
  },
  readCategory() {
    if (sessionStorage.getItem(CATEGORY_KEY))
      return JSON.parse(sessionStorage.getItem(CATEGORY_KEY));
    return []
  },
  saveCategory(category) {
    if (category)
      sessionStorage.setItem(CATEGORY_KEY, JSON.stringify(category))
  },
  readOutputRateList() {
    if (sessionStorage.getItem(OUTPUTRATELIST_KEY))
      return JSON.parse(sessionStorage.getItem(OUTPUTRATELIST_KEY))
    return []
  },
  saveOutputRateList(outputratelist) {
    if (outputratelist)
      sessionStorage.setItem(OUTPUTRATELIST_KEY, JSON.stringify(outputratelist))
  },
  readOutputRatexAxis() {
    if (sessionStorage.getItem(OUTPUTRATEXAXIS_KEY))
      return JSON.parse(sessionStorage.getItem(OUTPUTRATEXAXIS_KEY));
    return []
  },
  saveOutputRatexAxis(outputratexaxis) {
    if (outputratexaxis)
      sessionStorage.setItem(OUTPUTRATEXAXIS_KEY, JSON.stringify(outputratexaxis))
  },

  readRiskRes() {
    if (sessionStorage.getItem(RISKRES_KEY))
      return JSON.parse(sessionStorage.getItem(RISKRES_KEY));
    return []
  },
  saveRiskRes(riskres) {
    if (riskres)
      sessionStorage.setItem(RISKRES_KEY, JSON.stringify(riskres))
  }
}
