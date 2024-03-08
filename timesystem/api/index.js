import http from '@/api/request.js'
export default {
	getall() {
		return http.get('/user/getalluserdata')
	}
}