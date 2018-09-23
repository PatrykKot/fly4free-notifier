import axios from 'axios'

class SiteEventService {
    deleteAll() {
        return axios.delete('api/site/events')
    }

    getEvents(sortBy, descending, page, rowsPerPage, search) {
        return axios
            .get('api/site/events', {
                params: {
                    sortBy, descending, page, rowsPerPage, search
                }
            })
            .then(result => result.data)
    }
}

export default new SiteEventService()