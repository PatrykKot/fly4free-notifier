import axios from 'axios'

class SiteTypeService {
    getAll() {
        return axios.get('api/site/type')
            .then(result => result.data)
    }
}

export default new SiteTypeService()