import Vue from 'vue'
import {
  FLAG, SITUATIONS, LOGIN, LOGOUT, TITLE
} from './mutation-types'

export default {
  [FLAG](state, flag) {
    state.flag = flag
  },
  [SITUATIONS](state, { patterns }) {
    state.patterns = patterns
  }, [LOGIN]: (state, data) => {
    localStorage.token = data;
    state.token = data;
  },
  [LOGOUT]: (state) => {
    localStorage.removeItem('token');
    state.token = null
  },
  [TITLE]: (state, data) => {
    state.title = data;
  }
}