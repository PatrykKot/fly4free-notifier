import Vue from 'vue'
import MobileApp from './MobileApp'
import Vuetify from 'vuetify'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import {initPolishTimeago} from "./util/timeAgoUtils";

Vue.use(Vuetify)
Vue.config.productionTip = false

initPolishTimeago()

new Vue({
    render: h => h(MobileApp)
}).$mount('#app')