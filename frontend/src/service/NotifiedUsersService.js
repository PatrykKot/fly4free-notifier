import axios from 'axios'

class NotifiedUsersService {
    getAll() {
        return axios.get('api/notified/user')
            .then(result => result.data)
    }

    updateAll(notifiedUsersDto) {
        return axios.post('api/notified/user', notifiedUsersDto)
    }
}

export default new NotifiedUsersService()