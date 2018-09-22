import axios from 'axios'

class SiteEventService {
    getAll() {
        return axios.get('api/site/events')
            .then(result => result.data)
    }

    deleteAll() {
        return axios.delete('api/site/events')
    }
}

export default new SiteEventService()