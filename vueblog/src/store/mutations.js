import Vue from 'vue'
import {
  FLAG, SITUATIONS
} from './mutation-types'

export default {
  [FLAG](state, flag) {
    state.flag = flag
  },
  [SITUATIONS](state, { patterns }) {
    state.patterns = patterns
  },
}