import axios from 'axios'

class SiteEventService {
    deleteAll() {
        return axios.delete('api/site/events')
    }

    getEvents(sortBy, descending, page, rowsPerPage) {
        return axios
            .get('api/site/events', {
                params: {
                    sortBy, descending, page, rowsPerPage
                }
            })
            .then(result => result.data)
    }
}

export default new SiteEventService()