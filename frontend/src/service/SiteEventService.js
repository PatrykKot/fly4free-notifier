import axios from 'axios'

class SiteEventService {
    getAll() {
        return axios.get('api/site/events')
            .then(result => result.data)
    }
}

export default new SiteEventService()